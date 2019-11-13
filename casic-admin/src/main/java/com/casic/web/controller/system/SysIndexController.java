package com.casic.web.controller.system;

import com.casic.common.config.Global;
import com.casic.common.utils.DateUtils;
import com.casic.common.utils.MessageUtils;
import com.casic.framework.web.base.BaseController;
import com.casic.system.domain.SysConfig;
import com.casic.system.domain.SysMenu;
import com.casic.system.domain.SysRole;
import com.casic.system.domain.SysUser;
import com.casic.system.service.ISysConfigService;
import com.casic.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 首页 业务处理
 *
 * @author yuzengwen
 */
@Controller
public class SysIndexController extends BaseController {
    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysConfigService configService;

    /**
     * 系统首页
     */
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        // 取身份信息
        SysUser user = getSysUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        String[] configKeys = new String[]{"registe_center", "db_pool_monitor", "zipkin_monitor"};
        List<SysConfig> configList = configService.selectConfigListByKeys(configKeys);
        mmap.put("configList", configList);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", Global.getCopyrightYear());
        return "index";
    }

    /**
     * 系统介绍
     */
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        SysUser user = this.getSysUser();
        List<SysRole> roleList = user.getRoles();
        //是否有查看部门资产数据权限
        boolean deptFlag = false;
        //是否有查看所有资产数据权限
        boolean allFlag = false;
        for (SysRole role : roleList) {
            if ("admin".equals(role.getRoleKey()) || "SecurityAndSecrecyAdministrator".equals(role.getRoleKey()) || "SecurityAuditor".equals(role.getRoleKey())) {
                deptFlag = true;
            }
            if ("admin".equals(role.getRoleKey()) || "SecurityAndSecrecyAdministrator".equals(role.getRoleKey()) || "SecurityAuditor".equals(role.getRoleKey())) {
                allFlag = true;
            }
        }
        List<SysRole> roles = user.getRoles();
        boolean isAdmin = false;
        if (roles != null && roles.size() > 0) {
            List<SysRole> collect = roles.stream().filter(role -> "admin".equals(role.getRoleKey()) || "SecurityAndSecrecyAdministrator".equals(role.getRoleKey()) || "SecurityAuditor".equals(role.getRoleKey())).collect(Collectors.toList());
            if (collect.size() > 0) {
                isAdmin = true;
            }
        }
        //是管理员，则忽略
        if (!isAdmin) {
            Date changePwdDate = user.getChangePwdDate();
            boolean pwdExpire = DateUtils.getDayDiffFromToday(changePwdDate, 1);
            //密码过期
            if (!pwdExpire) {
                String message = MessageUtils.message("user.password.expire", user.getRemark());
                mmap.put("message", message);
            }
        }
        return "redirect:/bank/statistics";
    }
}
