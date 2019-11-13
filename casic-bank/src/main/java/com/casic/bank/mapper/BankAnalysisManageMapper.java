package com.casic.bank.mapper;

import com.casic.bank.domain.BankAnalysisManage;
import com.casic.bank.domain.vo.BankAnalysisManageVO;
import com.casic.bank.domain.vo.BankAnalysisVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 文件清退计划数据访问接口
 */
@Repository
public interface BankAnalysisManageMapper {

    /**
     * 查询台账列表数据
     *
     * @param bankAnalysisManage 文件台账实体
     * @return 列表数据
     */
    List<BankAnalysisManageVO> findBankAnalysisManageList(BankAnalysisManage bankAnalysisManage);

    /**
     * 根据主键查询清退计划
     *
     * @param id 清退计划id
     * @return BankAnalysisManage对象
     */
    BankAnalysisManage findAnalysisManageById(String id);

    /**
     * 新增清退计划
     *
     * @param bankAnalysisManage 清退计划
     * @return 受影响的行数
     */
    int insertBankAnalysisManage(BankAnalysisManage bankAnalysisManage);

    /**
     * 编辑清退计划
     *
     * @param bankAnalysisManage 清退计划
     * @return 受影响的行数
     */
    int updateBankAnalysisManage(BankAnalysisManage bankAnalysisManage);

    /**
     * 根据主键删除
     *
     * @param id 清退计划id
     * @return
     */
    int deleteById(String id);

    /**
     * 批量删除清退任务
     *
     * @param ids 待删除id字符串
     * @return 受影响的行数
     */
    int deleteByIds(String[] ids);

    /**
     * 根据条件查询清退计划
     *
     * @return
     */
    List<Map<String, Object>> selectBankAnalysisManageByYear();

    /**
     * 根据清退计划id查询该计划下的所有文件信息
     * @param bankAnalysisVO
     * @return
     */
    List<BankAnalysisVO> findSelectedBankAnalysisDetail(BankAnalysisVO bankAnalysisVO);
}
