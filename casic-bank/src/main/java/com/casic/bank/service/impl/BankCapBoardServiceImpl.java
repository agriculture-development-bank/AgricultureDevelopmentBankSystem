package com.casic.bank.service.impl;

import com.casic.bank.domain.BankCapBoard;
import com.casic.bank.mapper.BankCapBoardMapper;
import com.casic.bank.service.BankCapBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qh
 * @Classname BankCapBoardServiceImpl
 * @Description 载体柜单元门业务逻辑实现
 * @Date 2019/10/17 14:56
 */
@Service
public class BankCapBoardServiceImpl implements BankCapBoardService {

    private BankCapBoardMapper bankCapBoardMapper;

    @Autowired
    public BankCapBoardServiceImpl(BankCapBoardMapper bankCapBoardMapper) {
        this.bankCapBoardMapper = bankCapBoardMapper;
    }

    /**
     * 查询载体柜单元门列表数据
     *
     * @param bankCapBoard 载体柜单元门对象
     * @return
     */
    @Override
    public List<BankCapBoard> selectBankCapBoardList(BankCapBoard bankCapBoard) {
        List<BankCapBoard> list = bankCapBoardMapper.selectBankCapBoardList(bankCapBoard);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 根据载体柜id查询该柜子中的所有单元门信息
     *
     * @param equipmentId 载体柜id
     * @return
     */
    @Override
    public List<BankCapBoard> selectBankCapBoardByEquipmentId(String equipmentId) {
        return bankCapBoardMapper.selectBankCapBoardByEquipmentId(equipmentId);
    }
}
