package com.casic.web.controller.bank;

import com.casic.bank.domain.BankRecord;
import com.casic.bank.service.BankRecordService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bank/bankRecord")
public class BankRecordController extends BaseController {

    private static final String PREFIX = "bank/bankRecord/";

    private BankRecordService bankRecordService;

    @Autowired
    public BankRecordController(BankRecordService bankRecordService) {
        this.bankRecordService = bankRecordService;
    }

    /**
     * 列表视图
     *
     * @return
     */
    @GetMapping
    public String index(ModelMap modelMap) {
        return PREFIX + "list";
    }

    @PostMapping("/list")
    public TableDataInfo getRecordList(BankRecord bankRecord){

        startPage();
        List<BankRecord> bankRecords = bankRecordService.findBankRecordList(bankRecord);
        return getDataTable(bankRecords);
    }

    @Log(title = "流程记录", businessType = BusinessType.DELETE)
    @RequestMapping("/remove")
    public AjaxResult remove(String ids){
        Integer count  = bankRecordService.deleteBankRecord(ids);
        if(count>0){
            return success();
        }else{
            return AjaxResult.error(count,"删除失败");
        }

    }


}
