package com.casic.web.controller.bank;

import com.casic.bank.domain.BankCapBoard;
import com.casic.bank.service.BankCapBoardService;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qh
 * @Classname BankCapBoardController
 * @Description 载体柜单元门控制器
 * @Date 2019/10/17 14:54
 */
@Controller
@RequestMapping(value = "/bank/bankCapBoard")
public class BankCapBoardController extends BaseController {

    private static final String PREFIX = "bank/capBoard";

    private BankCapBoardService bankCapBoardService;

    @Autowired
    public BankCapBoardController(BankCapBoardService bankCapBoardService) {
        this.bankCapBoardService = bankCapBoardService;
    }

    /**
     * 载体柜单元门列表视图
     *
     * @param equipmentId 载体柜id
     * @param modelMap map集合
     * @return
     */
    @GetMapping()
    public String index(String equipmentId, ModelMap modelMap) {
        modelMap.put("equipmentId", equipmentId);
        return PREFIX + "/list";
    }

    /**
     * 载体柜单元门列表
     *
     * @param bankCapBoard 载体柜单元门对象
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public TableDataInfo list(BankCapBoard bankCapBoard) {
        startPage();
        List<BankCapBoard> list = bankCapBoardService.selectBankCapBoardList(bankCapBoard);
        return getDataTable(list);
    }

    /**
     * 根据载体柜id查询该柜子中的所有单元门信息
     *
     * @param equipmentId 载体柜id
     * @return
     */
    @PostMapping(value = "/selectBankCapBoardByEquipmentId")
    @ResponseBody
    public List<BankCapBoard> selectBankCapBoardByEquipmentId(String equipmentId) {
        return bankCapBoardService.selectBankCapBoardByEquipmentId(equipmentId);
    }
}
