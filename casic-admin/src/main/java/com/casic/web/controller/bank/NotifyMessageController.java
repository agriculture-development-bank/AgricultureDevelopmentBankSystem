package com.casic.web.controller.bank;

import com.casic.bank.domain.NotifyMessage;
import com.casic.bank.domain.vo.NotifyMessageVo;
import com.casic.bank.service.NotifyMessageService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/bank/notifyMessage")
public class NotifyMessageController extends BaseController{

    private static final String PREFIX = "bank/notifyMessage";

    private NotifyMessageService notifyMessageService;

    public NotifyMessageController(NotifyMessageService notifyMessageService) {
        this.notifyMessageService = notifyMessageService;
    }

    @GetMapping()
    public String getNotifyMessage(){
        return PREFIX + "/list";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public TableDataInfo list(NotifyMessage notifyMessage) {
        startPage();
        List<NotifyMessageVo> list = notifyMessageService.getNotifyMessageList(notifyMessage);
        return getDataTable(list);
    }

    @Log(title = "删除设备信息", businessType = BusinessType.DELETE)
    @PostMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(notifyMessageService.deleteMessage(ids));
    }





}
