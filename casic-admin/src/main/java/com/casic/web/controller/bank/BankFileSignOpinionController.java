package com.casic.web.controller.bank;

import com.casic.bank.domain.BankFileSignOpinion;
import com.casic.bank.domain.BankReceiveFiles;
import com.casic.bank.domain.vo.BankFileSignOpinionVO;
import com.casic.bank.service.BankFileSignOpinionService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.utils.DateUtils;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.util.ShiroUtils;
import com.casic.framework.web.base.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件签署意见控制器
 */
@Controller
@RequestMapping(value = "/bank/sign")
public class BankFileSignOpinionController extends BaseController {

    private BankFileSignOpinionService bankFileSignOpinionService;

    private static final String PREFIX = "bank/fileReceive/";

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
        List<BankFileSignOpinionVO> list =  bankFileSignOpinionService.selectBankFileSignOpinionVOList(bankFileSignOpinion);
        return getDataTable(list);
    }


    /**
     * 删除文件签署意见
     *
     * @param ids 签署意见id
     * @return
     */
    @Log(title = "删除意见信息", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(bankFileSignOpinionService.deleteBankFileSignOpinionById(ids));
    }

    /**
     * 新增角色
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap,HttpServletRequest request)
    {
        String id = request.getParameter("registrationNum");
        String opinionType = request.getParameter("opinionType");
        modelMap.addAttribute("registrationNum",id);
        modelMap.addAttribute("opinionType",opinionType);

        String nowTime = DateUtils.dateTimeNow("yyyy-MM-dd HH:mm:ss");
        modelMap.addAttribute("nowTime",nowTime);
        return PREFIX + "/addOpinion";
    }

    /**
     * 保存文件签署意见
     * @param bankFileSignOpinion 文件签署意见
     * @return
     */
    @Log(title = "新增批示意见", businessType = BusinessType.INSERT)
    @PostMapping(value = "/add")
    @ApiOperation(value = "新增批示意见")
    @ApiParam(name = "bankFileSignOpinion", required = true)
    @ResponseBody
    public AjaxResult add(BankFileSignOpinion bankFileSignOpinion) {
        bankFileSignOpinion.setCreateBy(ShiroUtils.getUserId());
        return toAjax(bankFileSignOpinionService.insertBankFileSignOpinion(bankFileSignOpinion));
    }

    /**
     * 修改视图
     * @param modelMap map集合
     * @return
     */
    @GetMapping(value = "/edit/{id}")
    public String edit(BankFileSignOpinionVO bankFileSignOpinionVO, ModelMap modelMap) {
        if(StringUtils.isNotEmpty(bankFileSignOpinionVO.getId()) && !"{id}".equals(bankFileSignOpinionVO.getId())){
            bankFileSignOpinionVO = bankFileSignOpinionService.selectBankFileSignOpinionById(bankFileSignOpinionVO.getId());
        }
        if(null == bankFileSignOpinionVO.getOpinionTime()){
            bankFileSignOpinionVO.setOpinionTime(DateUtils.getNowDate());
        }
        modelMap.put("bankFileSignOpinion", bankFileSignOpinionVO);
        return PREFIX + "/editOpinion";
    }

    /**
     * 修改审批意见
     *
     * @param bankFileSignOpinion 文件审批意见信息
     * @return
     */
    @Log(title = "修改批示意见", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/edit")
    @ApiOperation(value = "修改批示意见")
    @ApiParam(name = "bankFileSignOpinion", required = true)
    @ResponseBody
    public AjaxResult edit(BankFileSignOpinion bankFileSignOpinion) {
        List<BankFileSignOpinion> list = new ArrayList<>();
        list.add(bankFileSignOpinion);
        //一期
        return toAjax(bankFileSignOpinionService.updateBankFileSignOpinions(list));
        //二期
//        return null;
    }

}
