package com.casic.web.controller.bank;

import com.alibaba.fastjson.JSON;
import com.casic.bank.domain.BankAnalysisDetail;
import com.casic.bank.domain.BankAnalysisManage;
import com.casic.bank.domain.vo.BankAnalysisDetailVO;
import com.casic.bank.domain.vo.BankAnalysisManageVO;
import com.casic.bank.domain.vo.BankAnalysisVO;
import com.casic.bank.domain.vo.BankReceiveFilesDetailVO;
import com.casic.bank.service.BankAnalysisDetailService;
import com.casic.bank.service.BankAnalysisManageService;
import com.casic.bank.service.BankReceiveFilesService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文件清退控制器
 */
@Controller
@RequestMapping("/bank/analysisManage")
public class BankAnalysisManageController extends BaseController {

    private static final String PREFIX = "bank/analysisManage";

    private BankAnalysisManageService bankAnalysisManageService;

    private BankAnalysisDetailService bankAnalysisDetailService;

    private BankReceiveFilesService bankReceiveFilesService;

    @Autowired
    public BankAnalysisManageController(BankAnalysisManageService bankAnalysisManageService, BankAnalysisDetailService bankAnalysisDetailService, BankReceiveFilesService bankReceiveFilesService) {
        this.bankAnalysisManageService = bankAnalysisManageService;
        this.bankAnalysisDetailService = bankAnalysisDetailService;
        this.bankReceiveFilesService = bankReceiveFilesService;
    }

    @Override
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 列表视图
     *
     * @return
     */
    @GetMapping()
    @RequiresPermissions("bank:analysisManage:view")
    public String index() {
        return PREFIX + "/list";
    }

    /**
     * 清退任务新增视图
     *
     * @param modelMap map集合
     * @return
     */
    @GetMapping(value = "/add")
    public String add(ModelMap modelMap) {
        return PREFIX + "/add";
    }

    /**
     * 清退计划编辑视图
     *
     * @param id       清退计划id
     * @param modelMap map集合
     * @return
     */
    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap modelMap) {
        BankAnalysisManage bankAnalysisManage = bankAnalysisManageService.selectBankAnalysisManageById(id);
        List<BankAnalysisDetail> bankAnalysisDetails = bankAnalysisDetailService.selectBankAnalysisDetail(id);
        modelMap.put("bankAnalysisManage", bankAnalysisManage);
        modelMap.put("bankAnalysisDetails", bankAnalysisDetails);
        modelMap.put("selectNum", bankAnalysisDetails.size());
        modelMap.put("planId", id);
        return PREFIX + "/edit";
    }

    /**
     * 清退计划详情视图
     *
     * @param id       清退计划id
     * @param modelMap map集合
     * @return
     */
    @GetMapping(value = "/detail/{id}")
    @RequiresPermissions("bank:analysisManage:detail")
    public String detail(@PathVariable("id") String id, ModelMap modelMap) {
        BankAnalysisManage bankAnalysisManage = bankAnalysisManageService.selectBankAnalysisManageById(id);
        modelMap.put("bankAnalysisManage", bankAnalysisManage);
        return PREFIX + "/detail";
    }

    /**
     * 查询清退计划列表
     *
     * @param bankAnalysisManage 文件清退计划
     * @return
     */
    @RequiresPermissions("bank:analysisManage:list")
    @RequestMapping("/list")
    @ResponseBody
    public TableDataInfo list(BankAnalysisManageVO bankAnalysisManage) {
        startPage();
        List<BankAnalysisManageVO> list = bankAnalysisManageService.findAnalysisManage(bankAnalysisManage);
        return getDataTable(list);

    }
    @RequestMapping("/findSelectedBankAnalysisDetail")
    @ResponseBody
    public List<BankAnalysisVO> findSelectedBankAnalysisDetail(BankAnalysisVO bankAnalysisVO) {
        return bankAnalysisManageService.findSelectedBankAnalysisDetail(bankAnalysisVO);
    }

    /**
     * 新增清退计划
     *
     * @param bankAnalysisVOs 清退计划
     * @return
     */
    @RequiresPermissions("bank:analysisManage:add")
    @Log(title = "文件清退管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "/add")
    @ResponseBody
    public AjaxResult add(String bankAnalysisVOs) {
        List<BankAnalysisVO> bankAnalysisVOS = JSON.parseArray(bankAnalysisVOs, BankAnalysisVO.class);
        return toAjax(bankAnalysisManageService.insertBankAnalysisManage(bankAnalysisVOS));
    }


    /**
     * 编辑清退计划
     *
     * @param bankAnalysisVOs 清退计划
     * @return
     */
    @RequiresPermissions("bank:analysisManage:edit")
    @Log(title = "文件清退管理", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(String bankAnalysisVOs, String planId) {
        List<BankAnalysisVO> bankAnalysisVOS = JSON.parseArray(bankAnalysisVOs, BankAnalysisVO.class);
        return toAjax(bankAnalysisManageService.updateBankAnalysisManage(bankAnalysisVOS, planId));
    }

    /**
     * 批量删除清退任务
     *
     * @param ids 待删除id字符串
     * @return
     */
    @RequiresPermissions("bank:analysisManage:remove")
    @Log(title = "文件清退管理", businessType = BusinessType.DELETE)
    @PostMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(bankAnalysisManageService.deleteBankAnalysisManageByIds(ids));
    }

    /**
     * 清退文件
     *
     * @param ids    待清退文件id字符串
     * @param planId 清退计划id
     * @return
     */
    @RequiresPermissions("bank:analysisManage:analysis")
    @Log(title = "文件清退管理", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/analysis")
    @ResponseBody
    public AjaxResult analysis(String ids, String planId) {
        return toAjax(bankAnalysisManageService.analysisBankAnalysisManageByIds(ids, planId));
    }

    /**
     * 取消已清退的文件
     *
     * @param ids    待清退文件id字符串
     * @param planId 清退计划id
     * @return
     */
    @RequiresPermissions("bank:analysisManage:cancelAnalysis")
    @Log(title = "文件清退管理", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/cancelAnalysis")
    @ResponseBody
    public AjaxResult cancelAnalysis(String ids, String planId) {
        return toAjax(bankAnalysisManageService.cancelAnalysisBankAnalysisManageByIds(ids, planId));
    }

    /**
     * 查询台账信息
     *
     * @param bankAnalysisVO
     * @return
     */
    @RequestMapping("/receiveList")
    @ResponseBody
    public TableDataInfo list(BankAnalysisVO bankAnalysisVO) {
        startPage();
        List<BankAnalysisVO> list = bankAnalysisDetailService.selectBankReceiveFilesListByPlanId(bankAnalysisVO);
        return getDataTable(list);
    }

    /**
     * 清退视图
     *
     * @param id       清退计划id
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/fileReturn/{id}")
    public String fileReturn(@PathVariable("id") String id, ModelMap modelMap) {
        List<Map<String, Object>> list = bankAnalysisDetailService.selectBankAnalysisDetailMap(id);
        modelMap.put("list", list);
        modelMap.put("id", id);
        return PREFIX + "/return";
    }

    @GetMapping(value = "/fileReturnDetail/{id}")
    public String fileReturnDetail(@PathVariable("id") String id, ModelMap modelMap) {
        modelMap.put("id", id);
        return PREFIX + "/process";
    }

    /**
     * 查询清退列表
     *
     * @param bankAnalysisDetailVO
     * @return
     */
    @PostMapping("/returnList")
    @ResponseBody
    public TableDataInfo list(BankAnalysisDetailVO bankAnalysisDetailVO) {
        startPage();
        List<BankAnalysisDetailVO> list = bankAnalysisDetailService.selectBankAnalysisDetailVO(bankAnalysisDetailVO);
        return getDataTable(list);
    }

    /**
     * 根据台账id和清退计划id查询数据
     * @param bankReceiveFilesDetail
     * @return
     */
    @RequestMapping("/selectBankReceiveFilesDetailByAccountAndPlanId")
    @ResponseBody
    public List<BankReceiveFilesDetailVO> selectBankReceiveFilesDetailByAccountAndPlanId(BankReceiveFilesDetailVO bankReceiveFilesDetail) {
        return bankAnalysisDetailService.selectBankReceiveFilesDetailByAccountAndPlanId(bankReceiveFilesDetail);
    }

    @RequestMapping("/selectBankReceiveFilesDetailByAccountAndPlanIdOnEdit")
    @ResponseBody
    public List<BankReceiveFilesDetailVO> selectBankReceiveFilesDetailByAccountAndPlanIdOnEdit(BankReceiveFilesDetailVO bankReceiveFilesDetail) {
        return bankAnalysisDetailService.selectBankReceiveFilesDetailByAccountAndPlanIdOnEdit(bankReceiveFilesDetail);
    }

    @RequestMapping("/selectBankReceiveFilesDetailByPlanId")
    @ResponseBody
    public List<Map<String, Object>> selectBankReceiveFilesDetailByPlanId(BankReceiveFilesDetailVO bankReceiveFilesDetail) {
        return bankAnalysisDetailService.selectBankReceiveFilesDetailByPlanId(bankReceiveFilesDetail);
    }


}
