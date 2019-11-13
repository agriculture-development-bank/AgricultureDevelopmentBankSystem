package com.casic.bank.mapper;

import com.casic.bank.domain.BankEquipment;
import com.casic.bank.domain.vo.BankEquipmentVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 设备管理逻辑访问层接口
 */
@Repository
public interface BankEquipmentMapper {

    /**
     * 根据主键查询
     *
     * @param id 设备主键id
     * @return
     */
    BankEquipmentVO selectBankEquipmentById(String id);

    /**
     * 根据设备IP查询设备信息
     *
     * @param ip 设备IP
     * @return
     */
    BankEquipment selectBankEquipmentByIp(String ip);

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
     * 编辑设备实体
     *
     * @param bankEquipment BankEquipment对象
     * @return 受影响的行数
     */
    int updateBankEquipment(BankEquipment bankEquipment);

    /**
     * 删除设备信息
     *
     * @param ids 待删除的id字符串
     * @return 受影响的行数
     */
    int deleteBankEquipmentByIds(String[] ids);

    /**
     * 查询部门是否存在设备
     *
     * @param deptId
     * @return
     */
    int CheckDeptExistEquipement(String deptId);

    /**
     * 检测设备中存在位置信息
     *
     * @param locationId 位置id
     * @return
     */
    int checkEquipementExistLocation(String locationId);

    /**
     * 根据id删除设备信息
     *
     * @param id 设备id
     * @return
     */
    int deleteBankEquipmentById(String id);
}
