package com.casic.bank.service;

import com.casic.bank.domain.BankEquipment;
import com.casic.bank.domain.vo.BankEquipmentVO;

import java.util.List;

/**
 * 设备管理业务逻辑层接口
 */
public interface BankEquipmentService {

    /**
     * 根据主键查询
     *
     * @param id 设备主键id
     * @return
     */
    BankEquipmentVO selectBankEquipmentById(String id);

    /**
     * 获取设备列表数据
     *
     * @param bankEquipment BankEquipment对象
     * @return
     */
    List<BankEquipmentVO> selectBankEquipmentList(BankEquipment bankEquipment);

    /**
     * 保存设备实体
     *
     * @param bankEquipment BankEquipment对象
     * @return 受影响的行数
     */
    int insertBankEquipment(BankEquipment bankEquipment);

    /**
     * 修改设备
     *
     * @param bankEquipment 设备实体
     * @return
     */
    int updateBankEquipment(BankEquipment bankEquipment);

    /**
     * 删除设备信息
     *
     * @param ids 待删除的id字符串
     * @return 受影响的行数
     */
    int deleteBankEquipmentByIds(String ids);

    /**
     * 检测设备IP的唯一性
     *
     * @param bankEquipment 设备实体
     * @return
     */
    String checkEquipmentIpUnique(BankEquipment bankEquipment);

    /**
     * 查询部门是否存在设备
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    boolean CheckDeptExistEquipement(String deptId);

    /**
     * 检测设备中存在位置信息
     *
     * @param locationId 位置id
     * @return
     */
    boolean checkEquipementExistLocation(String locationId);
}
