package com.casic.bank.service;

import com.casic.bank.domain.BankReceiveFilesDetail;
import com.casic.bank.domain.api.FileBase;
import com.casic.bank.domain.vo.BankReceiveFilesDetailVO;
import com.casic.bank.domain.vo.FilePositionVO;
import com.casic.common.base.AjaxResult;

import java.util.List;
import java.util.Map;

/**
 * 文件台账详情业务逻辑接口
 */
public interface BankFileDetailService {
    /**
     * 查询文件台账详情列表
     *
     * @param bankReceiveFilesDetail 文件台账详情
     * @return 列表数据
     */
    List<BankReceiveFilesDetailVO> findBankReceiveFilesDetail(BankReceiveFilesDetail bankReceiveFilesDetail);

    /**
     * 根据id删除
     *
     * @param id 待删除的id字符串
     * @return 受影响行数
     */
    AjaxResult deleteBankReceiveFilesDetailById(String id);

    /**
     * 根据清退计划子表id查询文件信息
     *
     * @param fileDetailIds 清退计划子表id
     * @return
     */
    List<BankReceiveFilesDetail> selectBankFileDetailByAnalysisDetailIds(String fileDetailIds);

    /**
     * 根据文件id查询文件信息
     *
     * @param id 文件id
     * @return
     */
    BankReceiveFilesDetailVO selectBankReceiveFilesDetailVOById(String id);

    /**
     * 修改文件位置
     *
     * @param filePositionVO
     * @return
     */
    int updatePosition(FilePositionVO filePositionVO);

    /**
     * 批量修改文件位置
     *
     * @param filePositionVO
     * @return
     */
    int batchUpdatePosition(FilePositionVO filePositionVO);

    /**
     * 根据文件台账Id查詢文件信息
     *
     * @param fileIds
     * @return
     */
    List<BankReceiveFilesDetailVO> selectBankFileDetailByFileIds(String fileIds);

    /**
     * 更新文件信息
     *
     * @param detail BankReceiveFilesDetail对象
     * @return
     */
    int updateBankReceiveFilesDetail(BankReceiveFilesDetail detail);

    /**
     * 根据RFID编号查询文件信息
     *
     * @param rfid RFID编号
     * @return
     */
    BankReceiveFilesDetail selectBankFileDetailByRfid(String rfid);

    /**
     * 根据文件RFID编号更新文件信息
     *
     * @param detail
     * @return
     */
    int updateBankReceiveFilesDetailByRfid(BankReceiveFilesDetail detail);

    /**
     * 查询基本信息
     *
     * @param fileBase
     * @return
     */
    List<Map<String, Object>> selectBankFileDetailAnsyc(FileBase fileBase);

    /**
     * 查询可选的文件信息
     *
     * @param bankReceiveFilesDetail
     * @return
     */
    List<BankReceiveFilesDetailVO> selectOptionalBankReceiveFilesDetail(BankReceiveFilesDetailVO bankReceiveFilesDetail);
}
