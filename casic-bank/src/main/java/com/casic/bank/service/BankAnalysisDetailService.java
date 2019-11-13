package com.casic.bank.service;

import com.casic.bank.domain.BankAnalysisDetail;
import com.casic.bank.domain.vo.BankAnalysisDetailVO;
import com.casic.bank.domain.vo.BankAnalysisVO;
import com.casic.bank.domain.vo.BankReceiveFilesDetailVO;

import java.util.List;
import java.util.Map;

/**
 * 文件清退计划子表业务逻辑接口
 */
public interface BankAnalysisDetailService {

    /**
     * 根据文件清退计划id查询清退文件列表数据
     *
     * @param id 文件清退计划id
     * @return list列表
     */
    List<BankAnalysisDetail> selectBankAnalysisDetail(String id);

    /**
     * 查询台账信息
     *
     * @param bankAnalysisVO 台账继承类
     * @return
     */
    List<BankAnalysisVO> selectBankReceiveFilesList(BankAnalysisVO bankAnalysisVO);

    /**
     * 根据清退计划id查询待清退的文件
     *
     * @param id 清退计划id
     * @return
     */
    List<Map<String, Object>> selectBankAnalysisDetailMap(String id);

    /**
     * 查询待清退的文件
     *
     * @param bankAnalysisDetailVO
     * @return
     */
    List<BankAnalysisDetailVO> selectBankAnalysisDetailVO(BankAnalysisDetailVO bankAnalysisDetailVO);

    /**
     * 根据计划id查询台账信息
     *
     * @param bankAnalysisVO
     * @return
     */
    List<BankAnalysisVO> selectBankReceiveFilesListByPlanId(BankAnalysisVO bankAnalysisVO);

    /**
     * 根据台账id和清退计划id查询数据
     *
     * @param bankReceiveFilesDetail
     * @return
     */
    List<BankReceiveFilesDetailVO> selectBankReceiveFilesDetailByAccountAndPlanId(BankReceiveFilesDetailVO bankReceiveFilesDetail);

    /**
     * 根据计划id查询
     *
     * @param bankReceiveFilesDetail
     * @return
     */
    List<Map<String, Object>> selectBankReceiveFilesDetailByPlanId(BankReceiveFilesDetailVO bankReceiveFilesDetail);


    List<BankReceiveFilesDetailVO> selectBankReceiveFilesDetailByAccountAndPlanIdOnEdit(BankReceiveFilesDetailVO bankReceiveFilesDetail);
}
