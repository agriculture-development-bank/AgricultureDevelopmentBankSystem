package com.casic.bank.service;

import com.casic.bank.domain.BankFileSignOpinion;

import java.util.List;

/**
 * 文件签署意见业务逻辑接口
 */
public interface BankFileSignOpinionService {
    /**
     * 根据文件id查询行领导审批意见
     *
     * @param id 文件id
     * @return BankFileSignOpinion对象
     */
    List<BankFileSignOpinion> selectBankFileSignOpinionByFileDetailId(String id);

    /**
     * 查询文件签署意见列表数据
     *
     * @param bankFileSignOpinion 文件签署意见
     * @return list
     */
    List<BankFileSignOpinion> selectBankFileSignOpinionList(BankFileSignOpinion bankFileSignOpinion);

    /**
     * 根据主键查询行领导文件签署意见
     *
     * @param id 主键id
     * @return
     */
    BankFileSignOpinion selectBankFileSignOpinionById(String id);

    /**
     * 新增文件签署意见
     *
     * @param bankFileSignOpinion 文件签署意见
     * @return 受影响行数
     */
    int insertBankFileSignOpinion(BankFileSignOpinion bankFileSignOpinion);

    /**
     * 删除文件签署意见
     *
     * @param id 签署意见id
     * @return 受影响行数
     */
    int deleteBankFileSignOpinionById(String id);
}
