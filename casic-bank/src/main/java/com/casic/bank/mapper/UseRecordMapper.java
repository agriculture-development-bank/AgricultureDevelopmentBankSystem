package com.casic.bank.mapper;

import com.casic.bank.domain.UseRecord;
import com.casic.bank.domain.vo.BankFileVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UseRecordMapper {

    List<UseRecord> selectPageByUseRecord(UseRecord useRecord);

    List<BankFileVo> getBankFileVo(String id);
}
