package com.casic.bank.service;

import com.casic.bank.domain.BankRecord;
import com.casic.bank.domain.vo.BankRecordVO;

import java.util.List;
import java.util.Map;

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
    /**
     * 获取离柜天数
     * @param dataMap  type = file 查询一套文件（多份）中最早离柜天数；type = detail 查询一份文件中最早离柜天数
     * @return
     */
    List<Map<String,String>>  getLeaveCupboardDays(Map<String,Object> dataMap);
}
