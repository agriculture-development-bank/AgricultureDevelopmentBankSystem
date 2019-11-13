package com.casic.web.controller.bank;

import com.casic.bank.domain.*;
import com.casic.bank.domain.vo.BankEquipmentVO;
import com.casic.bank.domain.vo.BankLocationVo;
import com.casic.bank.domain.vo.BankReceiveFilesDetailVO;
import com.casic.bank.mapper.BankReceiveFilesDetailMapper;
import com.casic.bank.service.BankEquipmentService;
import com.casic.bank.service.NotifyMessageService;
import com.casic.common.base.AjaxResult;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.web.base.BaseController;
import com.casic.system.domain.SysDept;
import com.casic.system.domain.SysUser;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/bank/inCap")
public class InCapController extends BaseController{

    private static final String PREFIX = "bank/inCap";

    private NotifyMessageService notifyMessageService;

    private BankEquipmentService bankEquipmentService;

    private BankReceiveFilesDetailMapper bankReceiveFilesDetailMapper;

    public InCapController(NotifyMessageService notifyMessageService,
                           BankEquipmentService bankEquipmentService,
                           BankReceiveFilesDetailMapper bankReceiveFilesDetailMapper) {
        this.notifyMessageService = notifyMessageService;
        this.bankEquipmentService = bankEquipmentService;
        this.bankReceiveFilesDetailMapper = bankReceiveFilesDetailMapper;
    }

    @GetMapping()
    public String getInCap(ModelMap modelMap){
        unionSelect(modelMap);
        return PREFIX + "/list";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list(InCap inCap, ModelMap modelMap) {
        AjaxResult result =AjaxResult.success();

        Integer allCount =
                bankReceiveFilesDetailMapper.selectCount();
        startPage();

        Integer inCount = notifyMessageService.getInCapListCount(inCap);

        List<InCap> list = notifyMessageService.getInCapList(inCap);

        result.put("code",0);
        result.put("rows",list);
        result.put("total",new PageInfo(list).getTotal());
        result.put("inCount",inCount);
        result.put("allCount",allCount);
        return result;
    }

    @GetMapping(value = "/detail")
    public String detail(String id, ModelMap modelMap){
        modelMap.put("id",id);
        return PREFIX + "/detail";
    }

    @RequestMapping(value = "/detail/list")
    @ResponseBody
    public TableDataInfo detailList(String id) {
        startPage();
        List<BankReceiveFilesDetailVO> list = notifyMessageService.getDetailList(id);
        return getDataTable(list);
    }

    private void unionSelect(ModelMap modelMap) {
        //责任人
        BankEquipment bankEquipment = new BankEquipment();
        List<BankEquipmentVO> bankEquipments = bankEquipmentService.selectBankEquipmentList(bankEquipment);
        modelMap.put("bankEquipments", bankEquipments);


    }

}
