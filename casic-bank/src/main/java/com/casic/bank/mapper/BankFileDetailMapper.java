package com.casic.bank.mapper;

import com.casic.bank.domain.BankAccount;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankFileDetailMapper {

    /**
     *  根据查询条件 查找 文件台账（文件台账页面）
     * @param bankAccount
     * @return
     */
    List<BankAccount> findBankAccountList(BankAccount bankAccount);

    /**
     * 插入文件台账
     * @param bankAccount
     * @return
     */
    Integer insertBankAccount(BankAccount bankAccount);

    /**
     * 更新文件台账
     * @param bankAccount
     * @return
     */
    Integer updateBankAccount(BankAccount bankAccount);

    /**
     * 删除文件台账
     * @param id
     * @return
     */
    Integer deleteBankAccountById(String id);

    /**
     * 根据Id删除多个文件台账
     * @param ids
     * @return
     */
    Integer deleteBankAccountByIds(String[] ids);

    /**
     * 根据id查找台账
     * @param id
     * @return
     */
    BankAccount findBankAccountListById(String id);

    /**
     * 根据rfid查找台账
     * @param rfid
     * @return
     */
    BankAccount findBankAccountByRfid(String rfid);

    /**
     * 根据文号查找台账
     * @param documentNum
     * @return
     */
    BankAccount findBankAccountByDocumentNum(String documentNum);
}
