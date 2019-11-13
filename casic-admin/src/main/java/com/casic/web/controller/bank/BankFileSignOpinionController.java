package com.casic.web.controller.bank;

import com.casic.bank.domain.BankFileSignOpinion;
import com.casic.bank.service.BankFileSignOpinionService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.util.ShiroUtils;
import com.casic.framework.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 文件签署意见控制器
 */
@Controller
@RequestMapping(value = "/bank/sign")
public class BankFileSignOpinionController extends BaseController {

    private BankFileSignOpinionService bankFileSignOpinionService;

    @Autowired
    public BankFileSignOpinionController(BankFileSignOpinionService bankFileSignOpinionService) {
        this.bankFileSignOpinionService = bankFileSignOpinionService;
    }

    /**
     * 获取行领导审批意见列表数据
     *
     * @param bankFileSignOpinion 文件签署意见
     * @return list
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public TableDataInfo list(BankFileSignOpinion bankFileSignOpinion) {
        bankFileSignOpinion.setOpinionType("1");
        List<BankFileSignOpinion> list =  bankFileSignOpinionService.selectBankFileSignOpinionList(bankFileSignOpinion);
        return getDataTable(list);
    }

    /**
     * 保存文件签署意见
     *
     * @param bankFileSignOpinion 文件签署意见
     * @return
     */
    @Log(title = "保存文件签署意见", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/add")
    @ResponseBody
    public AjaxResult add(BankFileSignOpinion bankFileSignOpinion) {
        bankFileSignOpinion.setCreateBy(ShiroUtils.getUserId());
        return toAjax(bankFileSignOpinionService.insertBankFileSignOpinion(bankFileSignOpinion));
    }

    /**
     * 删除文件签署意见
     *
     * @param ids 签署意见id
     * @return
     */
    @Log(title = "删除文件签署意见", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(bankFileSignOpinionService.deleteBankFileSignOpinionById(ids));
    }
}
