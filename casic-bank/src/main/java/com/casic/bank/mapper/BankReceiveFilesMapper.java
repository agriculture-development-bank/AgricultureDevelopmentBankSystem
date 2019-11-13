package com.casic.bank.mapper;

import com.casic.bank.domain.BankReceiveFiles;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BankReceiveFilesMapper {

    /**
     * 查询文件收文登记列表
     *
     * @param bankReceiveFiles 文件收文登记实体
     * @return list列表
     */
    List<BankReceiveFiles> selectBankReceiveFilesList(BankReceiveFiles bankReceiveFiles);

    /**
     * 根据主键查询文件收文登记记录
     *
     * @param id 主键id
     * @return BankReceiveFiles对象
     */
    BankReceiveFiles selectBankReceiveFilesById(String id);

    /**
     * 查询文件收文登记记录
     *
     * @param bankReceiveFiles 文件收文登记实体
     * @return
     */
    BankReceiveFiles selectBankReceiveFiles(BankReceiveFiles bankReceiveFiles);

    /**
     * 新增文件收文登记
     *
     * @param bankReceiveFiles 文件收文登记实体
     * @return 受影响行数
     */
    int insertBankReceiveFiles(BankReceiveFiles bankReceiveFiles);

    /**
     * 修改文件收文登记记录
     *
     * @param bankReceiveFiles 文件收文登记实体
     * @return
     */
    int updateBankReceiveFiles(BankReceiveFiles bankReceiveFiles);

    /**
     * 删除文件收文登记记录
     *
     * @param ids 待删除id字符串
     * @return
     */
    int deleteBankReceiveFilesByIds(String[] ids);

    /**
     * 按文件类别统计
     *
     * @return
     */
    List<Map<String, Object>> queryFileTypeData();

    /**
     * 按紧急程度统计
     *
     * @return
     */
    List<Map<String, Object>> queryFileUrgencyData();

    /**
     * 按文件状态统计
     *
     * @return
     */
    List<Map<String, Object>> queryFileStatusData();

    /**
     * 校验登记号是否唯一
     *
     * @param registrationNum 登记号
     * @return 结果
     */
    BankReceiveFiles checkRegistrationNumUnique(@Param("registrationNum") String registrationNum);

    /**
     * 查询部门下的文件台账数量
     *
     * @param deptId 部门id
     * @return
     */
    int checkDeptExistFile(String deptId);
}
