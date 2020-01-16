package com.casic.bank.service;

import com.casic.bank.domain.BankFileSignOpinion;
import com.alibaba.fastjson.JSONArray;import com.casic.bank.domain.BankReceiveFiles;

import java.util.List;
import java.util.Map;

public interface BankReceiveFilesService {

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
     * 新增文件收文登记记录
     *
     * @param bankReceiveFiles 文件收文登记实体
     * @return 受影响行数
     */
    int insertBankReceiveFiles(BankReceiveFiles bankReceiveFiles);

    /**
     * 修改文件收文登记记录
     *
     * @param bankReceiveFiles 文件收文登记实体
     * @param bankFileSignOpinions 文件签署意见实体
     * @return
     */
    int updateBankReceiveFiles(BankReceiveFiles bankReceiveFiles, List<BankFileSignOpinion> bankFileSignOpinions);

    /**
     * 删除
     *
     * @param ids 待删除id字符串
     * @return
     */
    int deleteBankReceiveFilesByIds(String ids);

    /**
     * 登记确认
     *
     * @param ids 待确认id字符串
     * @param userId 操作用户id
     * @return
     */
    int confirmFileByIds(String ids, String userId);

    /**
     * 校验登记号是否唯一
     *
     * @param bankReceiveFiles 台账信息
     * @return 结果
     */
    String checkRegistrationNumUnique(BankReceiveFiles bankReceiveFiles);

    /**
     * 文件上交
     * @param jsonArray
     * @return
     */
    String fileHandin(JSONArray jsonArray);

    /**
     * 文件下发
     * @param jsonArray
     * @return
     */
    String fileTrans(JSONArray jsonArray);

    /**
     * 同步基础数据
     * @return
     */
    List<Map> selectBankFileToPhone();

    /**
     * 检测部门下是否存在文件台账
     *
     * @param deptId 部门id
     * @return
     */
    boolean checkDeptExistFile(String deptId);

    /**
     * 获取最大登记号
     *
     * @return
     */
    String getMaxRegistrationNum();
}
