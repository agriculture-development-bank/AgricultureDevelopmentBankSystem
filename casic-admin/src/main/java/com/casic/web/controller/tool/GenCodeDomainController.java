package com.casic.web.controller.tool;

import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.util.ShiroUtils;
import com.casic.framework.web.base.BaseController;
import com.casic.generator.domain.GenCodeDomain;
import com.casic.generator.service.IGenCodeDomainService;
import com.casic.system.domain.SysUser;
import com.casic.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 行业领域controller
 * @author qh
 */
@Controller
@RequestMapping("/tool/genCodeDomain")
public class GenCodeDomainController extends BaseController {

    private String prefix = "/tool/genCodeDomain";

    @Autowired
    private IGenCodeDomainService genCodeDomainService;
    @Autowired
    private ISysUserService userService;

    /**
     * 列表视图
     * @return 页面路径
     */
    @RequiresPermissions("tool:genCodeDomain:view")
    @GetMapping
    public String view() {
        return prefix + "/genCodeDomain";
    }

    /**
     * 行业领域管理列表
     * @param genCodeDomain 行业领域实体类
     * @return
     */
    @RequiresPermissions("tool:genCodeDomain:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GenCodeDomain genCodeDomain) {
        startPage();
        List<GenCodeDomain> list = genCodeDomainService.selectGenCodeDomainList(genCodeDomain);
        list.stream().forEach(p -> {
            SysUser sysUser = userService.selectUserById(p.getCreateBy());
            p.setCreateBy(sysUser.getUserName());
            p.setUpdateBy(sysUser.getUserName());
        });
        return getDataTable(list);
    }

    /**
     * 新增行业领域页面
     *
     * @return
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 编辑行业领域页面
     *
     * @param id  领域id
     * @param modelMap
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap modelMap) {
        GenCodeDomain genCodeDomain = genCodeDomainService.selectGenCodeDomainById(id);
        modelMap.put("genCodeDomain", genCodeDomain);
        return prefix + "/edit";
    }

    /**
     * 新增行业领域
     *
     * @param genCodeDomain 行业领域实体类
     * @return map
     */
    @RequiresPermissions("tool:genCodeDomain:add")
    @Log(title = "行业领域管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GenCodeDomain genCodeDomain) {
        if (genCodeDomain != null && StringUtils.isEmpty(genCodeDomain.getId())) {
                genCodeDomain.setId(UuidUtils.getUUIDString());
                genCodeDomain.setDelFlag("0");
                genCodeDomain.setCreateBy(String.valueOf(ShiroUtils.getUserId()));
                genCodeDomain.setCreateTime(new Date());
        }
        return toAjax(genCodeDomainService.insertGenCodeDomain(genCodeDomain));
    }

    /**
     * 编辑行业领域
     *
     * @param genCodeDomain 行业领域
     * @return map
     */
    @RequiresPermissions("tool:genCodeDomain:edit")
    @Log(title = "行业领域管理", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/edit")
    @ResponseBody
    public AjaxResult editSave(GenCodeDomain genCodeDomain) {
        genCodeDomain.setUpdateBy(String.valueOf(ShiroUtils.getUserId()));
        genCodeDomain.setUpdateTime(new Date());
        return toAjax(genCodeDomainService.updateGenCodeDomain(genCodeDomain));
    }

    /**
     * 删除行业领域管理
     *
     * @param ids 主键字符串
     * @return map
     */
    @RequiresPermissions("tool:genCodeDomain:remove")
    @Log(title = "行业领域管理", businessType = BusinessType.DELETE)
    @PostMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(genCodeDomainService.deleteGenCodeDomainByIds(ids));
    }

    /**
     * 列表视图
     * @return 页面路径
     */
    @RequestMapping(value = "/selectView")
    public String selectView() {
        return prefix + "/selectView";
    }
}
