package com.casic.web.controller.bank;

import com.casic.bank.domain.BankReceiveFilesDetail;
import com.casic.bank.domain.vo.BankEquipmentVO;
import com.casic.bank.domain.vo.BankReceiveFilesDetailVO;
import com.casic.bank.domain.vo.BankRecordVO;
import com.casic.bank.domain.vo.FilePositionVO;
import com.casic.bank.service.BankEquipmentService;
import com.casic.bank.service.BankFileDetailService;
import com.casic.bank.service.BankRecordService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.utils.StringUtils;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.web.base.BaseController;
import com.casic.system.domain.SysDept;
import com.casic.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件台账详情
 */
@Controller
@RequestMapping("/bank/fileDetail")
public class BankFileDetailController extends BaseController {

    private static final String PREFIX = "bank/fileDetail";

    private BankFileDetailService bankFileDetailService;

    private BankEquipmentService bankEquipmentService;

    private ISysDeptService sysDeptService;

    private BankRecordService bankRecordService;

    @Autowired
    public BankFileDetailController(BankFileDetailService bankFileDetailService, ISysDeptService sysDeptService, BankRecordService bankRecordService, BankEquipmentService bankEquipmentService) {
        this.bankFileDetailService = bankFileDetailService;
        this.sysDeptService = sysDeptService;
        this.bankRecordService = bankRecordService;
        this.bankEquipmentService = bankEquipmentService;
    }

    /**
     * 文件台账详情列表视图
     *
     * @return
     */
    @GetMapping()
    public String index(String fileId, ModelMap modelMap) {
        List<SysDept> depts = sysDeptService.selectDeptList(new SysDept());
        if (depts != null && depts.size() > 0) {
            modelMap.put("depts", depts);
        }
        modelMap.put("fileId", fileId);
        return PREFIX + "/fileDetail";
    }

    @GetMapping(value = "/fileSelect")
    public String fileSelect(String fileId, String fileDetailIds, String type, String planId, ModelMap modelMap) {
        List<BankReceiveFilesDetail> list = bankFileDetailService.selectBankFileDetailByAnalysisDetailIds(fileDetailIds);
        String collect = list.stream().map(BankReceiveFilesDetail::getId).collect(Collectors.joining(","));
        modelMap.put("fileId", fileId);
        modelMap.put("fileDetailIds", fileDetailIds);
        modelMap.put("list", list);
        modelMap.put("type", type);
        modelMap.put("planId", planId);
        modelMap.put("collect", StringUtils.isNotEmpty(collect) ? collect : fileDetailIds);
        return PREFIX + "/fileSelect";
    }

    /**
     * 查询文件台账详情列表
     *
     * @param bankReceiveFilesDetail 文件台账详情
     * @return 列表数据
     */
    @RequestMapping("/list")
    @ResponseBody
    public TableDataInfo list(BankReceiveFilesDetailVO bankReceiveFilesDetail) {
        startPage();
        List<BankReceiveFilesDetailVO> list = bankFileDetailService.findBankReceiveFilesDetail(bankReceiveFilesDetail);
        return getDataTable(list);
    }

    /**
     * 获取可清退文件列表
     *
     * @param bankReceiveFilesDetail
     * @return
     */
    @RequestMapping("/optionalList")
    @ResponseBody
    public TableDataInfo optionalList(BankReceiveFilesDetailVO bankReceiveFilesDetail) {
        startPage();
        List<BankReceiveFilesDetailVO> list = bankFileDetailService.selectOptionalBankReceiveFilesDetail(bankReceiveFilesDetail);
        return getDataTable(list);
    }

    /**
     * 根据id删除
     *
     * @param ids 待删除的id字符串
     * @return
     */
    @Log(title = "删除", businessType = BusinessType.DELETE)
    @PostMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return bankFileDetailService.deleteBankReceiveFilesDetailById(ids);
    }

    /**
     * 履历视图
     *
     * @param id       文件台账详情id
     * @param modelMap map集合
     */
    @GetMapping(value = "/resume/{id}")
    public String resumeView(@PathVariable("id") String id, ModelMap modelMap) {
        modelMap.put("fileDetailId", id);
        return PREFIX + "/resumeList";
    }

    /**
     * 履历列表数据
     *
     * @return
     */
    @RequestMapping(value = "/resumeList")
    @ResponseBody
    public TableDataInfo resumeList(BankRecordVO bankRecordVO) {
        startPage();
        List<BankRecordVO> list = bankRecordService.selectBankRecord(bankRecordVO);
        return getDataTable(list);
    }

    /**
     * 修改文件位置
     *
     * @param id       文件id
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/position/{id}")
    public String positionView(@PathVariable("id") String id, ModelMap modelMap) {
        BankReceiveFilesDetailVO bankReceiveFilesDetail = bankFileDetailService.selectBankReceiveFilesDetailVOById(id);
        List<BankEquipmentVO> bankEquipmentVOS = bankEquipmentService.selectBankEquipmentList(new BankEquipmentVO());
        modelMap.put("bankEquipments", bankEquipmentVOS);
        modelMap.put("bankReceiveFilesDetail", bankReceiveFilesDetail);
        return PREFIX + "/position";
    }

    @GetMapping(value = "/batchUpdateLocation/{ids}")
    public String batchUpdateLocation(@PathVariable("ids") String ids, ModelMap modelMap) {
        List<BankEquipmentVO> bankEquipmentVOS = bankEquipmentService.selectBankEquipmentList(new BankEquipmentVO());
        modelMap.put("bankEquipments", bankEquipmentVOS);
        modelMap.put("ids", ids);
        return PREFIX + "/batchUpdatePosition";
    }

    /**
     * 修改文件位置
     *
     * @param filePositionVO
     * @return
     */
    @Log(title = "修改文件位置", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/updatePosition")
    @ResponseBody
    public AjaxResult updatePosition(FilePositionVO filePositionVO) {
        return toAjax(bankFileDetailService.updatePosition(filePositionVO));
    }

    /**
     * 批量修改文件位置
     *
     * @param filePositionVO
     * @return
     */
    @Log(title = "批量修改文件位置", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/batchUpdatePosition")
    @ResponseBody
    public AjaxResult batchUpdatePosition(FilePositionVO filePositionVO) {
        return toAjax(bankFileDetailService.batchUpdatePosition(filePositionVO));
    }
}
