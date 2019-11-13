package com.casic.bank.mapper;

import com.casic.bank.domain.BankRecord;
import com.casic.bank.domain.vo.BankRecordVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BankRecordMapper {

    List<BankRecord> findBankRecordList(BankRecord bankRecord);

    Integer deleteBankRecord(String[] ids);

    Integer insertBankRecord(BankRecord bankRecord);

    BankRecord selectBankRecordById(String id);

    Integer findCountRecordByFileId(List<String> fileIds);

    /**
     * 查询流程记录
     *
     * @param bankRecordVO
     * @return
     */
    List<BankRecordVO> selectBankRecord(BankRecordVO bankRecordVO);


    /**
     * 操作人数量统计
     *
     * @param type
     */

    List<Map<String, Object>> selectCountRecordByDept(@Param("type") String type);

    /**
     * 更新流程记录
     *
     * @param bankRecord
     * @return
     */
    int updateBankRecord(BankRecord bankRecord);
}
