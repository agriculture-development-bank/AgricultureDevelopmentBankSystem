package com.casic.web.controller.bank;

import com.casic.bank.domain.UseRecord;
import com.casic.bank.domain.vo.BankFileVo;
import com.casic.bank.service.NotifyMessageService;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/bank/useRecord")
public class UseRecordController extends BaseController {

    private static final String PREFIX = "bank/useRecord";

    private NotifyMessageService notifyMessageService;

    public UseRecordController(NotifyMessageService notifyMessageService) {
        this.notifyMessageService = notifyMessageService;
    }

    @GetMapping()
    public String getNotifyMessage() {
        return PREFIX + "/list";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public TableDataInfo list(UseRecord useRecord) {
        startPage();
        List<UseRecord> list = notifyMessageService.getUseRecordList(useRecord);
        return getDataTable(list);
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(@PathVariable("id") String id, ModelMap modelMap) {
//        BankLocation bankLocation = bankLocationService.selectBankLocationById(id);
//        modelMap.addAttribute("bankLocation", bankLocation);
        UseRecord useRecord = new UseRecord();
        useRecord.setCapuseid(id);

        UseRecord useRecordTemp = notifyMessageService.getUseRecordList(useRecord).get(0);
        modelMap.put("id", id);
        modelMap.put("useName", useRecordTemp.getUsename());
        modelMap.put("timeStart", useRecordTemp.getTimeStart());
        modelMap.put("equipment", useRecordTemp.getEquipment());
        modelMap.put("countNum", Integer.parseInt(useRecordTemp.getProductOut()) + Integer.parseInt(useRecordTemp.getProductIn()));
        List<BankFileVo> bankFileVos = notifyMessageService.getBankFileVo(id);

        return PREFIX + "/detail";
    }

    @RequestMapping("/detailList/{id}")
    @ResponseBody
    public TableDataInfo findDetailList(@PathVariable("id") String id) {
        startPage();
        List<BankFileVo> maps = notifyMessageService.getBankFileVo(id);
        return getDataTable(maps);
    }


}
