package com.casic.web.controller.bank;

import com.casic.bank.domain.BankEquipment;
import com.casic.bank.domain.BankLocation;
import com.casic.bank.domain.vo.BankEquipmentVO;
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
import com.casic.system.domain.SysUser;
import com.casic.system.service.ISysDeptService;
import com.casic.system.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 载体柜管理控制器
 */
@Controller
@RequestMapping(value = "/bank/equipment")
public class BankEquipmentController extends BaseController {

    private static final String PREFIX = "bank/equipment/";

    private BankEquipmentService bankEquipmentService;

    private ISysUserService userService;

    private BankLocationService bankLocationService;

    private ISysDeptService sysDeptService;

    @Autowired
    public BankEquipmentController(BankEquipmentService bankEquipmentService,
                                   ISysUserService userService,
                                   ISysDeptService sysDeptService,
                                   BankLocationService bankLocationService) {
        this.bankEquipmentService = bankEquipmentService;
        this.userService = userService;
        this.sysDeptService = sysDeptService;
        this.bankLocationService = bankLocationService;
    }

    /**
     * 列表视图
     *
     * @return
     */
    @GetMapping
    @RequiresPermissions("bank:equipment:view")
    public String index(ModelMap modelMap) {
        //所属部门
        List<SysDept> sysDepts = sysDeptService.selectDeptList(new SysDept());
        modelMap.put("sysDepts", sysDepts);
        return PREFIX + "list";
    }

    /**
     * 新增视图
     *
     * @return
     */
    @GetMapping(value = "/add")
    public String add(ModelMap modelMap) {
        //责任人
        unionSelect(modelMap);
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
        BankEquipmentVO bankEquipment = bankEquipmentService.selectBankEquipmentById(id);
        modelMap.addAttribute("bankEquipment", bankEquipment);
        unionSelect(modelMap);
        return PREFIX + "edit";
    }

    /**
     * 查询责任人和部门
     *
     * @param modelMap map集合
     */
    private void unionSelect(ModelMap modelMap) {
        //责任人
        SysUser sysUser = new SysUser();
        sysUser.setStatus("0");
        List<SysUser> sysUsers = userService.selectSysUserList(sysUser);
        modelMap.put("sysUsers", sysUsers);
        //所属部门
        SysDept sysDept = new SysDept();
        sysDept.setStatus("0");
        List<SysDept> sysDepts = sysDeptService.selectDeptList(sysDept);
        modelMap.put("sysDepts", sysDepts);
        //所属位置
        List<BankLocationVo> bankLocations = bankLocationService.selectBankLocationList(new BankLocation());
        modelMap.put("bankLocations", bankLocations);
    }

    /**
     * 详情
     *
     * @param id       主键
     * @param modelMap map集合
     * @return
     */
    @GetMapping(value = "/detail/{id}")
    @RequiresPermissions("bank:equipment:detail")
    public String detail(@PathVariable("id") String id, ModelMap modelMap) {
        BankEquipment bankEquipment = bankEquipmentService.selectBankEquipmentById(id);
        modelMap.addAttribute("bankEquipment", bankEquipment);
        //责任人
        unionSelect(modelMap);
        return PREFIX + "detail";
    }

    /**
     * 获取列表数据
     *
     * @param bankEquipment 设备实体
     * @return
     */
    @RequiresPermissions("bank:equipment:list")
    @RequestMapping(value = "/list")
    @ResponseBody
    public TableDataInfo list(BankEquipment bankEquipment) {
        startPage();
        List<BankEquipmentVO> list = bankEquipmentService.selectBankEquipmentList(bankEquipment);
        return getDataTable(list);
    }

    /**
     * 新增设备
     *
     * @param bankEquipment 设备实体
     * @return
     */
    @RequiresPermissions("bank:equipment:add")
    @Log(title = "设备管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "/add")
    @ResponseBody
    @ApiOperation(value = "新增设备")
    @ApiParam(name = "bankEquipment", required = true)
    public AjaxResult add(BankEquipment bankEquipment) {
        bankEquipment.setCreateBy(ShiroUtils.getUserId());
        return toAjax(bankEquipmentService.insertBankEquipment(bankEquipment));
    }

    /**
     * 修改设备
     *
     * @param bankEquipment 设备实体
     * @return
     */
    @RequiresPermissions("bank:equipment:edit")
    @Log(title = "设备管理", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(BankEquipment bankEquipment) {
        bankEquipment.setUpdateBy(ShiroUtils.getUserId());
        return toAjax(bankEquipmentService.updateBankEquipment(bankEquipment));
    }

    /**
     * 删除
     *
     * @param ids 待删除id字符串
     * @return
     */
    @RequiresPermissions("bank:equipment:remove")
    @Log(title = "设备管理", businessType = BusinessType.DELETE)
    @PostMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        int flag = bankEquipmentService.deleteBankEquipmentByIds(ids);
        if (flag > 0) {
            return success();
        } else {
            if (flag == -1) {
                return error("该条记录正在被使用，不能删除");
            } else {
               return error();
            }
        }
    }

    /**
     * 检测设备IP的唯一性
     *
     * @param bankEquipment 设备实体
     * @return
     */
    @PostMapping(value = "/checkEquipmentIpUnique")
    @ResponseBody
    public String checkEquipmentIpUnique(BankEquipment bankEquipment) {
        return bankEquipmentService.checkEquipmentIpUnique(bankEquipment);
    }
}
