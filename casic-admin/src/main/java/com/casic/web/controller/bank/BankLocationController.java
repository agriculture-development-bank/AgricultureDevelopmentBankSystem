package com.casic.web.controller.bank;

import com.casic.bank.domain.BankLocation;
import com.casic.bank.domain.vo.BankLocationVo;
import com.casic.bank.service.BankEquipmentService;
import com.casic.bank.service.BankLocationService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.util.ShiroUtils;
import com.casic.framework.web.base.BaseController;
import com.casic.system.domain.SysDept;
import com.casic.system.service.ISysDeptService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/bank/location")
public class BankLocationController extends BaseController {

    private static final String PREFIX = "bank/location/";

    private BankLocationService bankLocationService;
    private BankEquipmentService bankEquipmentService;
    private ISysDeptService sysDeptService;

    @Autowired
    public BankLocationController(BankLocationService bankLocationService, BankEquipmentService bankEquipmentService, ISysDeptService sysDeptService) {
        this.bankLocationService = bankLocationService;
        this.bankEquipmentService = bankEquipmentService;
        this.sysDeptService = sysDeptService;
    }

    /**
     * 列表视图
     *
     * @return
     */
    @GetMapping
    @RequiresPermissions("bank:location:view")
    public String index(ModelMap modelMap) {
        return PREFIX + "list";
    }

    /**
     * 新增视图
     *
     * @return
     */
    @GetMapping(value = "/add")
    public String add(ModelMap modelMap) {
        return PREFIX + "add";
    }

    /**
     * 修改视图
     *
     * @param id       主键
     * @param modelMap map集合
     * @return
     */
    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap modelMap) {
        BankLocationVo bankLocation = bankLocationService.selectBankLocationById(id);
        modelMap.addAttribute("bankLocation", bankLocation);
        return PREFIX + "edit";
    }

    /**
     * 详情
     *
     * @param id       主键
     * @param modelMap map集合
     * @return
     */
    @GetMapping(value = "/detail/{id}")
    @RequiresPermissions("bank:location:detail")
    public String detail(@PathVariable("id") String id, ModelMap modelMap) {
        BankLocation bankLocation = bankLocationService.selectBankLocationById(id);
        modelMap.addAttribute("bankLocation", bankLocation);
        return PREFIX + "detail";
    }

    /**
     * 查询部门
     *
     * @param modelMap map集合
     */
    private void unionSelect(ModelMap modelMap) {
        //所属部门
        List<SysDept> sysDepts = sysDeptService.selectDeptList(new SysDept());
        modelMap.put("sysDepts", sysDepts);
    }

    /**
     * 获取列表数据
     *
     * @param bankLocation 位置实体
     * @return
     */
    @RequiresPermissions("bank:location:list")
    @RequestMapping(value = "/list")
    @ResponseBody
    public TableDataInfo list(BankLocation bankLocation) {
        startPage();
        List<BankLocationVo> list = bankLocationService.selectBankLocationList(bankLocation);
        return getDataTable(list);
    }


    /**
     * 新增位置
     *
     * @param bankLocation 位置实体
     * @return
     */
    @RequiresPermissions("bank:location:add")
    @Log(title = "位置管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "/add")
    @ResponseBody
    @ApiOperation(value = "新增位置")
    @ApiParam(name = "bankEquipment", required = true)
    public AjaxResult add(BankLocation bankLocation) {
        bankLocation.setCreateBy(ShiroUtils.getUserId());
        return toAjax(bankLocationService.insertBankLocation(bankLocation));
    }

    /**
     * 修改位置
     *
     * @param bankLocation 位置实体
     * @return
     */
    @RequiresPermissions("bank:location:edit")
    @Log(title = "位置管理", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(BankLocation bankLocation) {
        bankLocation.setUpdateBy(ShiroUtils.getUserId());
        return toAjax(bankLocationService.updateBankLocation(bankLocation));
    }

    /**
     * 删除
     *
     * @param id 待删除id字符串
     * @return
     */
    @RequiresPermissions("bank:location:remove")
    @Log(title = "位置管理", businessType = BusinessType.DELETE)
    @PostMapping(value = "/remove/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("id") String id) {
        if (bankEquipmentService.checkEquipementExistLocation(id)) {
            return error(1, "位置信息在设备中已使用，不能删除");
        }
        return toAjax(bankLocationService.deleteBankLocationById(id));
    }

    /**
     * 检测位置编码的唯一性
     *
     * @param bankLocation 位置实体
     * @return
     */
    @PostMapping(value = "/checkLocationCodeUnique")
    @ResponseBody
    public String checkLocationCodeUnique(BankLocation bankLocation) {
        return bankLocationService.checkLocationCodeUnique(bankLocation);
    }

    /**
     * 检测同一所属机构位置名称不能重复
     *
     * @param bankLocation 位置信息
     * @return
     */
    @PostMapping(value = "/checkDeptLocationUnique")
    @ResponseBody
    public String checkDeptLocationUnique(BankLocation bankLocation) {
        return bankLocationService.checkDeptLocationUnique(bankLocation);
    }
}
