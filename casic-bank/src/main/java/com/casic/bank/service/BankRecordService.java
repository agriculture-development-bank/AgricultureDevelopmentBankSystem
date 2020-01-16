package com.casic.bank.service;

import com.casic.bank.domain.BankRecord;
import com.casic.bank.domain.vo.BankRecordVO;

import java.util.List;

public interface BankRecordService {

    /**
     * 查询流程记录
     *
     * @param bankRecordVO
     * @return
     */
    List<BankRecordVO> selectBankRecord(BankRecordVO bankRecordVO);

    List<BankRecord> findBankRecordList(BankRecord bankRecord);

    Integer deleteBankRecord(String ids);

    Integer insertBankRecord(BankRecord bankRecord);

    String selectReceiveDeptForMaxTimeByFileID(String fileId);

    List<String> getDeptName();
}
