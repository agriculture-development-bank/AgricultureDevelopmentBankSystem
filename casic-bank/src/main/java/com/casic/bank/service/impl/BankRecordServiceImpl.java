package com.casic.bank.service.impl;

import com.casic.bank.domain.BankRecord;
import com.casic.bank.domain.vo.BankRecordVO;
import com.casic.bank.mapper.BankRecordMapper;
import com.casic.bank.service.BankRecordService;
import com.casic.common.support.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BankRecordServiceImpl implements BankRecordService {

    private BankRecordMapper bankRecordMapper;

    @Autowired
    public BankRecordServiceImpl(BankRecordMapper bankRecordMapper) {
        this.bankRecordMapper = bankRecordMapper;
    }

    /**
     * 查询流程记录
     *
     * @param bankRecordVO
     * @return
     */
    @Override
    public List<BankRecordVO> selectBankRecord(BankRecordVO bankRecordVO) {
        List<BankRecordVO> list = bankRecordMapper.selectBankRecord(bankRecordVO);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<BankRecord> findBankRecordList(BankRecord bankRecord) {
        return bankRecordMapper.findBankRecordList(bankRecord);
    }

    @Override
    public Integer deleteBankRecord(String ids) {
        return bankRecordMapper.deleteBankRecord(Convert.toStrArray(ids));
    }

    @Override
    public Integer insertBankRecord(BankRecord bankRecord) {
        return bankRecordMapper.insertBankRecord(bankRecord);
    }

    @Override
    public String selectReceiveDeptForMaxTimeByFileID(String fileId) {
        return bankRecordMapper.selectReceiveDeptForMaxTimeByFileID(fileId);
    }

    @Override
    public List<String> getDeptName() {
        return bankRecordMapper.getDeptName();
    }

    @Override
    public List<Map<String,String>>  getLeaveCupboardDays(Map<String, Object> dataMap) {
        List<Map<String,String>>  leaveList = bankRecordMapper.getLeaveCupboardDays(dataMap);
        return leaveList;
    }
}
