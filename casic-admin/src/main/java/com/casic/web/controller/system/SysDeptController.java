package com.casic.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.casic.bank.service.BankEquipmentService;
import com.casic.bank.service.BankReceiveFilesService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.framework.util.ShiroUtils;
import com.casic.framework.web.base.BaseController;
import com.casic.system.domain.SysDept;
import com.casic.system.domain.SysDeptVO;
import com.casic.system.domain.SysRole;
import com.casic.system.service.ISysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门信息
 *
 * @author yuzengwen
 */
@Controller
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {
    private String prefix = "system/dept";

    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private BankEquipmentService bankEquipmentService;
    @Autowired
    private BankReceiveFilesService bankReceiveFilesService;

    @RequiresPermissions("system:dept:view")
    @GetMapping()
    public String dept() {
        return prefix + "/dept";
    }

    @RequiresPermissions("system:dept:list")
    @GetMapping("/list")
    @ResponseBody
    public List<SysDept> list(SysDept dept) {
        List<SysDept> deptList = deptService.selectDeptList(dept);
        return deptList;
    }

    @GetMapping("/queryChildDeptListByParentId/{parentId}")
    @ResponseBody
    public Map<String, Object> queryChildDeptListByParentId(@PathVariable("parentId") String parentId, ModelMap mmap) {
        Map<String, Object> result = new HashMap<String, Object>();
        SysDept dept = new SysDept();
        dept.setParentId(parentId);
        List<SysDept> deptList = deptService.selectDeptList(dept);
        result.put("childCount", deptList.size());
        result.put("childList", deptList);
        return result;
    }

    /**
     * 新增部门
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") String parentId, ModelMap mmap) {
        SysDept sysDept = deptService.selectDeptById(parentId);
        if (StringUtils.isNull(sysDept)) {
            sysDept = new SysDept();
            sysDept.setDeptId("0");
            sysDept.setDeptName("");
        }
        mmap.put("dept", sysDept);
        return prefix + "/add";
    }

    /**
     * 新增保存部门
     */
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dept:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysDept dept) {
        if (dept != null) {
            if ("".equals(dept.getDeptId()) || dept.getDeptId() == null) {
                String uuid = UuidUtils.getUUIDString();
                dept.setDeptId(uuid);
            }
        }
        dept.setCreateBy(ShiroUtils.getUserId());
//        if("1".equals(dept.getSafeDept())){
//            Integer count = deptService.getSafeDeptCount(dept.getDeptId());
//            if(count>0){
//                return error("机要部门已存在，只能有一个机要部门");
//            }
//        }
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") String deptId, ModelMap mmap) {
        SysDept dept = deptService.selectDeptById(deptId);
        if (StringUtils.isNotNull(dept) && "100" == deptId) {
            dept.setParentName("无");
        }
        mmap.put("dept", dept);
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dept:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysDept dept) {
//        if("1".equals(dept.getSafeDept())){
//            Integer count = deptService.getSafeDeptCount(dept.getDeptId());
//            if(count>0){
//                return error("机要部门已存在，只能有一个机要部门");
//            }
//        }
        if (!"0".equals(dept.getStatus())) {
            List<SysDept> sysDepts = deptService.selectChildDept(dept.getDeptId());
            for (SysDept sysDept : sysDepts) {
                if ("0".equals(sysDept.getStatus())) {
                    return error("子部门有正常使用，父部门状态不可编辑");
                }
            }
            dept.setUpdateBy(ShiroUtils.getUserId());
            return toAjax(deptService.updateDept(dept));
        } else {
            dept.setUpdateBy(ShiroUtils.getUserId());
            return toAjax(deptService.updateDept(dept));
        }
    }

    /**
     * 删除
     */
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dept:remove")
    @PostMapping("/remove/{deptId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("deptId") String deptId) {
        if (deptService.selectDeptCount(deptId) > 0) {
            return error(1, "存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return error(1, "部门存在用户,不允许删除");
        }
        if (bankEquipmentService.CheckDeptExistEquipement(deptId)) {
            return error(1, "部门存在设备信息,不允许删除");
        }

        if (bankReceiveFilesService.checkDeptExistFile(deptId)) {
            return error(1, "部门下存在文件台账信息,不允许删除");
        }
        return toAjax(deptService.deleteDeptById(deptId));
    }

    /**
     * 校验部门名称
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public String checkDeptNameUnique(SysDept dept) {
        return deptService.checkDeptNameUnique(dept);
    }

    /**
     * 校验部门编号是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @PostMapping("/checkDeptCodeUnique")
    @ResponseBody
    public String checkDeptCodeUnique(SysDept dept) {
        return deptService.checkDeptCodeUnique(dept);
    }

    /**
     * 选择部门树
     */
    @GetMapping("/selectDeptTree/{deptId}")
    public String selectDeptTree(@PathVariable("deptId") String deptId, ModelMap mmap) {
        SysDept dept = deptService.selectDeptById(deptId);
        if (dept == null) {
            dept = new SysDept();
        }
        mmap.put("dept", dept);
        return prefix + "/tree";
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Map<String, Object>> treeData() {
        List<Map<String, Object>> tree = deptService.selectDeptTree();
        return tree;
    }

    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/roleDeptTreeData")
    @ResponseBody
    public List<Map<String, Object>> deptTreeData(SysRole role) {
        List<Map<String, Object>> tree = deptService.roleDeptTreeData(role);
        return tree;
    }

    /**
     * 根据部门id获取部门信息
     */
    @RequestMapping("/getDeptInfo")
    @ResponseBody
    public SysDept getDeptInfo(String deptId) {
        SysDept dept = deptService.selectDeptById(deptId);
        return dept;
    }

    /**
     * 查询部门树列表
     *
     * @return
     */
    @RequestMapping("/getSysDeptTree")
    @ResponseBody
    public Object getSysDeptTree() {
        SysDeptVO sysDeptVO = new SysDeptVO();
        sysDeptVO.setParentId("0");
        List<SysDeptVO> sysDeptList = deptService.getSysDeptList(sysDeptVO);
        String groupTreeJson = JSON.toJSONString(sysDeptList);
        groupTreeJson = groupTreeJson.replace("deptName", "text");
        groupTreeJson = groupTreeJson.replace("deptId", "href");
        groupTreeJson = groupTreeJson.replace("childDept", "nodes");
        groupTreeJson = groupTreeJson.replace("parentId", "tags");
        return JSON.parseArray(groupTreeJson);
    }
}
