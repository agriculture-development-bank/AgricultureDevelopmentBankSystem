package com.casic.bank.service;

import com.casic.bank.domain.BankLocation;
import com.casic.bank.domain.vo.BankLocationVo;

import java.util.List;

/**
 * 位置管理业务逻辑访问接口
 */
public interface BankLocationService {
    /**
     * 获取列表数据
     *
     * @param bankLocation 位置实体
     * @return
     */
    List<BankLocationVo> selectBankLocationList(BankLocation bankLocation);

    /**
     * 根据主键查询位置实体
     *
     * @param id 主键id
     * @return
     */
    BankLocationVo selectBankLocationById(String id);

    /**
     * 新增位置
     *
     * @param bankLocation 位置实体
     * @return 受影响的行数
     */
    int insertBankLocation(BankLocation bankLocation);

    /**
     * 修改位置
     *
     * @param bankLocation 位置实体
     * @return 受影响的行数
     */
    int updateBankLocation(BankLocation bankLocation);

    /**
     * 删除
     *
     * @param ids 待删除id字符串
     * @return 受影响的行数
     */
    int deleteBankLocationByIds(String ids);

    /**
     * 检测位置编码的唯一性
     *
     * @param bankLocation 位置实体
     * @return
     */
    String checkLocationCodeUnique(BankLocation bankLocation);

    /**
     * 根据位置id删除
     *
     * @param id 位置id
     * @return
     */
    int deleteBankLocationById(String id);

    /**
     * 检测同一所属机构位置名称不能重复
     *
     * @param bankLocation 位置信息
     * @return
     */
    String checkDeptLocationUnique(BankLocation bankLocation);
}
