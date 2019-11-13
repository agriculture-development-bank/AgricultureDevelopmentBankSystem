package com.casic.bank.mapper;

import com.casic.bank.domain.BankReceiveFiles;
import com.casic.bank.domain.BankReceiveFilesDetail;
import com.casic.bank.domain.api.FileBase;
import com.casic.bank.domain.vo.BankReceiveFilesDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 台账详情数据访问接口
 */
@Repository
public interface BankReceiveFilesDetailMapper {

    /**
     * 插入文件台账文件份数信息
     *
     * @param bankReceiveFilesDetails 文件份数信息
     * @return 受影响行数
     */
    int insertBankReceiveFilesDetail(List<BankReceiveFilesDetail> bankReceiveFilesDetails);

    /**
     * 查询文件台账详情列表
     *
     * @param bankReceiveFilesDetail 文件台账详情
     * @return 列表数据
     */
    List<BankReceiveFilesDetailVO> findBankReceiveFilesDetail(BankReceiveFilesDetail bankReceiveFilesDetail);

    /**
     * 根据id删除台账的文件
     *
     * @param id 待删除文件id
     * @return 受影响的行数
     */
    int deleteBankReceiveFilesDetailById(String id);

    /**
     * 根据主键查询台账文件信息
     *
     * @param id 文件id
     * @return BankReceiveFilesDetail对象
     */
    BankReceiveFilesDetail selectBankReceiveFilesDetailById(String id);

    /**
     * 根据台账id查询文件数量
     *
     * @param fileId 台账id
     * @return 台账数量
     */
    int selectBankReceiveFilesDetailCountByFileId(String fileId);

    /**
     * 根据RFID编号查询台账文件信息
     *
     * @param rfid RFID编号
     * @return
     */
    BankReceiveFilesDetail selectBankReceiveFilesDetailRfid(String rfid);

    /**
     * 更新文件信息
     *
     * @param bankReceiveFilesDetail
     * @return
     */
    Integer updateBankReceiveFilesDetail(BankReceiveFilesDetail bankReceiveFilesDetail);

    List<Map> selectBankFileToPhone();

    List<BankReceiveFilesDetail> selectBankReceiveFileDetailByFileIds(String[] fileIds);

    /**
     * 根据清退计划子表id查询文件信息
     *
     * @param fileDetailIds 清退计划子表id
     * @return
     */
    List<BankReceiveFilesDetail> selectBankFileDetailByAnalysisDetailIds(String[] fileDetailIds);

    List<BankReceiveFilesDetailVO> getDetailList(String id);

    /**
     * 根据台账id查询子文件列表
     *
     * @param fileId 台账id
     * @return
     */
    List<BankReceiveFilesDetail> selectBankReceiveFileDetailByFileId(String fileId);

    Integer selectCount();

    /**
     * 批量更新台账子表数据
     *
     * @param bankReceiveFilesDetail 台账子表信息
     * @return
     */
    int updateBankReceiveFilesDetailsByAccountId(BankReceiveFilesDetail bankReceiveFilesDetail);

    /**
     * 根据文件id查询文件信息
     *
     * @param id 文件id
     * @return
     */
    BankReceiveFilesDetailVO selectBankReceiveFilesDetailVOById(String id);

    /**
     * 批量更新文件位置信息
     *
     * @param bankReceiveFilesDetails
     * @return
     */
    int batchUpdateBankReceiveFilesDetail(@Param("bankReceiveFilesDetails") List<BankReceiveFilesDetail> bankReceiveFilesDetails);

    List<BankReceiveFilesDetailVO> selectBankReceiveFileDetailVOByFileIds(String[] fileIds);

    /**
     * 根据RFID查询文件信息
     *
     * @param rfid
     * @return
     */
    BankReceiveFilesDetail selectBankReceiveFileDetailRfidNum(String rfid);

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

    /**
     * 根据设备id查询台账文件信息
     *
     * @param id 设备id
     * @return
     */
    List<BankReceiveFilesDetail> selectBankReceiveFileDetailByEquipmentId(String id);
}
