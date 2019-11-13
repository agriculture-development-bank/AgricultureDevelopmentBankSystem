package com.casic.bank.mapper;

import com.casic.bank.domain.BankCapBoard;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author qh
 * @Classname BankCapBoardMapper
 * @Description 载体柜单元门数据访问接口
 * @Date 2019/10/17 13:47
 */
@Repository
public interface BankCapBoardMapper {

    /**
     * 批量保存载体柜单元门
     *
     * @param bankCapBoards
     * @return
     */
    int insertBankCapBoard(List<BankCapBoard> bankCapBoards);

    /**
     * 根据载体柜id查询该柜子中的所有单元门
     *
     * @param equipmentId 载体柜id
     * @return
     */
    List<BankCapBoard> selectBankCapBoardByEquipmentId(String equipmentId);

    /**
     * 根据载体柜id删除该柜子中的所有单元门
     *
     * @param equipmentId 载体柜id
     * @return
     */
    int deleteBankCapBoardByEquipmentId(String equipmentId);

    /**
     * 查询载体柜单元门列表数据
     *
     * @param bankCapBoard 载体柜单元门对象
     * @return
     */
    List<BankCapBoard> selectBankCapBoardList(BankCapBoard bankCapBoard);
}
