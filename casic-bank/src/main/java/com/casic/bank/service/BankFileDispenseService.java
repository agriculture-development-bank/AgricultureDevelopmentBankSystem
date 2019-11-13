package com.casic.bank.service;

import com.casic.bank.domain.BankFileDispense;

import java.util.List;

/**
 * @author renxw
 * @date 2019/9/23
 */
public interface BankFileDispenseService {

    /**
     * 根据Id查找对应的对象
     * @param id
     * @return
     */
    BankFileDispense selectFileDispenseById(String id);

    /**
     * 查找列表
     * @param bankFileDispense
     * @return
     */
    List<BankFileDispense> findBankFileDispenseList(BankFileDispense bankFileDispense);

    /**
     * 新增文件分发任务
     * @param bankFileDispense
     * @return
     */
    Integer insertBankFileDispense(BankFileDispense bankFileDispense);

    /**
     * 编辑文件分发任务
     * @param bankFileDispense
     * @return
     */
    Integer updateBankFileDispense(BankFileDispense bankFileDispense);

    /**
     * 删除文件分发任务
     * @param ids
     * @return
     */
    Integer deleteBankFileDispenseByIds(String ids);
}
