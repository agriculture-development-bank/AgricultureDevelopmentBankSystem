package com.casic.bank.service;

import com.casic.bank.domain.BankCapBoard;

import java.util.List;

/**
 * @author qh
 * @Classname BankCapBoardService
 * @Description 载体柜单元门业务逻辑接口
 * @Date 2019/10/17 14:55
 */
public interface BankCapBoardService {

    /**
     * 查询载体柜单元门列表数据
     *
     * @param bankCapBoard 载体柜单元门对象
     * @return
     */
    List<BankCapBoard> selectBankCapBoardList(BankCapBoard bankCapBoard);

    /**
     * 根据载体柜id查询该柜子中的所有单元门信息
     *
     * @param equipmentId 载体柜id
     * @return
     */
    List<BankCapBoard> selectBankCapBoardByEquipmentId(String equipmentId);
}
