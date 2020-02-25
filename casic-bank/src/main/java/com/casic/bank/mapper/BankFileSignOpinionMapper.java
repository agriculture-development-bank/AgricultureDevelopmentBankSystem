package com.casic.bank.mapper;

import com.casic.bank.domain.BankFileSignOpinion;
import com.casic.bank.domain.vo.BankFileSignOpinionVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件签署意见数据访问接口
 */
@Repository
public interface BankFileSignOpinionMapper {

    /**
     * 根据文件id查询行领导审批意见
     *
     * @param fileId 文件id
     * @return BankFileSignOpinion对象
     */
    List<BankFileSignOpinion> selectBankFileSignOpinionByFileDetailId(String fileId);

    /**
     * 根据台账流水号查询行领导审批意见
     *
     * @param registrationNum 台账流水号
     * @return BankFileSignOpinion对象
     */
    List<BankFileSignOpinion> selectBankFileSignOpinionByRegistrationum(String registrationNum);


    /**
     * 根据主键查询行领导文件签署意见
     *
     * @param id 主键id
     * @return
     */
    BankFileSignOpinionVO selectBankFileSignOpinionById(String id);

    /**
     * 新增文件签署意见
     *
     * @param bankFileSignOpinion 文件签署意见
     * @return 受影响行数
     */
    int insertBankFileSignOpinion(BankFileSignOpinion bankFileSignOpinion);

    /**
     * 查询文件签署意见列表数据
     *
     * @param bankFileSignOpinion 文件签署意见
     * @return list
     */
    List<BankFileSignOpinion> selectBankFileSignOpinionList(BankFileSignOpinion bankFileSignOpinion);


    List<BankFileSignOpinionVO> selectBankFileSignOpinionVOList(BankFileSignOpinion bankFileSignOpinion);

    /**
     * 删除文件签署意见
     *
     * @param id 签署意见id
     * @return 受影响行数
     */
    int deleteBankFileSignOpinionById(String id);

    /**
     * 批量更新文件签署意见
     *
     * @param bankFileSignOpinions 文件签署意见
     * @return
     */
    int updateBankFileSignOpinions(List<BankFileSignOpinion> bankFileSignOpinions);
}
