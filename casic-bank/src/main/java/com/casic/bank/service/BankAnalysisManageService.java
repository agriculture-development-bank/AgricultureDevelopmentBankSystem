package com.casic.bank.service;


import com.casic.bank.domain.BankAnalysisManage;
import com.casic.bank.domain.vo.BankAnalysisManageVO;
import com.casic.bank.domain.vo.BankAnalysisVO;

import java.util.List;

/**
 * 文件清退计划业务逻辑接口
 */
public interface BankAnalysisManageService {
    /**
     * 查询台账列表数据
     *
     * @param bankAccount 文件台账实体
     * @return 列表数据
     */
    List<BankAnalysisManageVO> findAnalysisManage(BankAnalysisManage bankAccount);

    /**
     * 根据主键查询清退计划
     *
     * @param id 清退计划id
     * @return BankAnalysisManage对象
     */
    BankAnalysisManage selectBankAnalysisManageById(String id);

    /**
     * 新增清退计划
     *
     * @param bankAnalysisVOs 清退计划
     * @return 受影响的行数
     */
    int insertBankAnalysisManage(List<BankAnalysisVO> bankAnalysisVOs);

    /**
     * 编辑清退计划
     *
     * @param bankAnalysisVOS 台账信息
     * @param planId 清退计划id
     * @return 受影响的行数
     */
    int updateBankAnalysisManage(List<BankAnalysisVO> bankAnalysisVOS, String planId);

    /**
     * 批量删除清退任务
     *
     * @param ids 待删除id字符串
     * @return 受影响的行数
     */
    int deleteBankAnalysisManageByIds(String ids);

    /**
     * 清退文件
     *
     * @param ids 待清退文件id字符串
     * @param planId 清退计划id
     * @return 受影响的行数
     */
    int analysisBankAnalysisManageByIds(String ids, String planId);

    /**
     * 取消已清退的文件
     * @param ids    待清退文件id字符串
     * @param planId 清退计划id
     *
     * @return
     */
    int cancelAnalysisBankAnalysisManageByIds(String ids, String planId);

    /**
     * 根据清退计划id查询该计划下的所有文件信息
     *
     * @param bankAnalysisVO
     * @return
     */
    List<BankAnalysisVO> findSelectedBankAnalysisDetail(BankAnalysisVO bankAnalysisVO);
}
