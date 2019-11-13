package com.casic.bank.mapper;

import com.casic.bank.domain.BankAnalysisDetail;
import com.casic.bank.domain.vo.BankAnalysisDetailVO;
import com.casic.bank.domain.vo.BankAnalysisVO;
import com.casic.bank.domain.vo.BankReceiveFilesDetailVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 文件清退计划子表数据访问层
 */
@Repository
public interface BankAnalysisDetailMapper {

    /**
     * 保存文件清退计划子表数据
     *
     * @param bankAnalysisDetail 文件清退计划子表实体
     * @return
     */
    int insertBankAnalysisDetail(BankAnalysisDetail bankAnalysisDetail);

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
     * @param id
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
     * 更新文件清退子表数据
     *
     * @param ids
     * @return
     */
    int analysisBankAnalysisDetailByIds(String[] ids);

    /**
     * 根据文件清退计划id删除子表信息
     * @param id
     * @return
     */
    int deleteBankAnalysisDetailByAnalysisManageId(String id);

    /**
     * 根据文件清退计划id批量删除子表信息
     * @param ids
     * @return
     */
    int deleteBankAnalysisDetailByAnalysisManageIds(String[] ids);

    /**
     * 取消已清退的文件
     * @param ids 待清退文件id字符串
     * @return
     */
    int cancelAnalysisBankAnalysisManageByIds(String[] ids);

    /**
     * 根据计划id查询台账信息
     *
     * @param bankAnalysisVO
     * @return
     */
    List<BankAnalysisVO> selectBankReceiveFilesListByPlanId(BankAnalysisVO bankAnalysisVO);

    /**
     * 根据清退子文件id查询文件信息
     * @param id
     * @return
     */
    BankAnalysisDetail selectBankAnalysisDetailById(String id);

    /**
     * 根据id清退计划文件
     *
     * @param id
     * @return
     */
    int analysisBankAnalysisDetailById(String id);

    /**
     * 根据id取消清退文件
     *
     * @param id
     * @return
     */
    int cancelAnalysisBankAnalysisManageById(String id);

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

    /**
     * 根据文件ID查询清退计划中的文件
     *
     * @param id 文件id
     * @return
     */
    List<BankAnalysisDetail> selectBankAnalysisDetailByFileId(String id);


    List<BankReceiveFilesDetailVO> selectBankReceiveFilesDetailByAccountAndPlanIdOnEdit(BankReceiveFilesDetailVO bankReceiveFilesDetail);
}
