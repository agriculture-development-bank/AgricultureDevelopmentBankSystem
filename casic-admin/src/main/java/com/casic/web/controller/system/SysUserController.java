package com.casic.web.controller.system;

import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.poi.ExcelUtil;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.shiro.service.SysPasswordService;
import com.casic.framework.util.ShiroUtils;
import com.casic.framework.web.base.BaseController;
import com.casic.system.domain.SastindSysUserVo;
import com.casic.system.domain.SysRole;
import com.casic.system.domain.SysUser;
import com.casic.system.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author yuzengwen
 */
@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
    private String prefix = "system/user";

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ISysConfigService configService;

    @RequiresPermissions("system:user:view")
    @GetMapping()
    public String user() {
        return prefix + "/user";
    }

    @RequestMapping("/selectView")
    public String selectView(String clientId, Model model) {
        model.addAttribute("clientId", clientId);
        return prefix + "/selectView";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SastindSysUserVo> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysUser user) {
        List<SastindSysUserVo> list = userService.selectUserList(user);
        for (SastindSysUserVo sastindSysUserVo : list) {
            sastindSysUserVo.setSex(dictDataService.selectDictLabel("sys_user_sex", sastindSysUserVo.getSex()));
            //账号状态
            String status = sastindSysUserVo.getStatus();
            if ("0".equals(status)) {
                sastindSysUserVo.setStatus("正常");
            } else if ("1".equals(status)) {
                sastindSysUserVo.setStatus("停用");
            }
        }
        ExcelUtil<SastindSysUserVo> util = new ExcelUtil<SastindSysUserVo>(SastindSysUserVo.class);
        return util.exportExcel(list, "user");
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("roles", roleService.selectRoleAll());
        mmap.put("posts", postService.selectPostAll());
        return prefix + "/add";
    }

    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && SysUser.isAdmin(user.getUserId())) {
            return error("不允许修改超级管理员用户");
        }
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") String userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roles", roleService.selectRolesByUserId(userId));
        mmap.put("posts", postService.selectPostsByUserId(userId));
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && SysUser.isAdmin(user.getUserId())) {
            return error("不允许修改超级管理员用户");
        }

        user.setUpdateBy(ShiroUtils.getLoginName());
        SysUser oldUser = userService.selectUserById(user.getUserId());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), configService.selectConfigByKey("sys.user.initPassword"), oldUser.getSalt()));
        return toAjax(userService.updateUser(user));
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") String userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(SysUser user) {
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setChangePwdDate(new Date());
        return toAjax(userService.resetUserPwd(user));
    }

    @RequiresPermissions("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        SysUser user = ShiroUtils.getUser();
        if (user != null) {
            List<SysRole> roles = user.getRoles();
            List<String> collect = roles.stream().map(SysRole::getRoleKey).collect(Collectors.toList());
            if (collect.size() > 0) {
                boolean isAdmin = collect.contains("admin");
                if (isAdmin) {
                    return userService.deleteUserByIds(ids);
                } else {
                    return error(1, "您没有权限删除用户");
                }
            } else {
                return error(1, "您没有权限删除用户");
            }
        }
        return success();
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(SysUser user) {
        return userService.checkLoginNameUnique(user.getLoginName());
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkEditLoginNameUnique")
    @ResponseBody
    public String checkEditLoginNameUnique(SysUser user) {
        return userService.checkEditLoginNameUnique(user);
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(SysUser user) {
        return userService.checkPhoneUnique(user);
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(SysUser user) {
        return userService.checkEmailUnique(user);
    }

    /**
     * 校验身份真号码唯一
     *
     * @return String
     */
    @PostMapping("/checkIdCardUnique")
    @ResponseBody
    public String checkIdCardUnique(SysUser user) {
        return userService.checkIdCardUnique(user);
    }

    /**
     * 校验身份卡号的唯一性
     *
     * @param user
     * @return
     */
    @PostMapping("/checkCardNumUnique")
    @ResponseBody
    public String checkCardNumUnique(SysUser user) {
        return userService.checkCardNumUnique(user);
    }

    /**
     * 选择用户
     */
    @GetMapping("/selectUserView")
    public String selectUserView(ModelMap mmap) {
        mmap.put("clientId", "");
        return "sastind/common/selectUserView";
    }

    /**
     * 根据部门选择用户
     */
    @GetMapping("/selectUserByDept/{deptId}")
    public String selectUserByDept(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        mmap.put("deptId", deptId);
        return "sastind/common/selectUserByDept";
    }

    /**
     * 根据部门选择用户
     */
    @GetMapping("/selectUserByDepts/{deptId}")
    public String selectUserByDepst(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        mmap.put("deptId", deptId);
        return "sastind/common/selectUserByDepts";
    }

    @PostMapping("/listByDept")
    @ResponseBody
    public TableDataInfo listByDept(SysUser user) {
        startPage();
        List<SastindSysUserVo> list = userService.selectUserListByDept(user);
        return getDataTable(list);
    }

    /**
     * 导入用户信息
     */
    @Log(title = "导入用户信息", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importUser(MultipartFile file) {
        if (file == null) {
            return AjaxResult.success("文件为空");
        }

        try {
            Map<String, Object> map = userService.importExcel(file);
            if ("".equals(map.get("msg").toString())) {
                List<SysUser> list = (List<SysUser>) map.get("data");
                int count = insertImportData(list);
                if (count > 0) {
                    return success().put("count", count);
                } else {
                    return error();
                }

            } else {
                return error(map.get("msg").toString()).put("fileName", map.get("fileName").toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("导入失败！");
        }
    }

    /**
     * 导入数据校验后直接存入表中
     */
    private int insertImportData(List<SysUser> sysUserList) throws Exception {

        for (SysUser sysUser : sysUserList) {
            sysUser.setSalt(ShiroUtils.randomSalt());
            sysUser.setPassword(passwordService.encryptPassword(sysUser.getLoginName(), sysUser.getPassword(), sysUser.getSalt()));
            sysUser.setCreateBy(ShiroUtils.getLoginName());
            userService.insertUser(sysUser);
        }
        return sysUserList.size();
    }

}