package com.casic.bank.domain;

import com.casic.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 载体柜实体类
 */
@Getter
@Setter
@ToString
public class BankEquipment extends BaseEntity {
    /**
     * 设备编号
     */
    private String id;
    /**
     * 载体柜名称
     */
    private String equipmentName;
    /**
     * 载体柜类型
     */
    private String equipmentType;
    /**
     * 载体柜单元数
     */
    private int unitLevel;
    /**
     * 所属部门
     */
    private String deptId;
    /**
     * 设备IP
     */
    private String ip;
    /**
     * 责任人
     */
    private String userId;
    /**
     * 设备状态
     */
    private String equipmentStatus;
    /**
     * 部署位置
     */
    private String position;
}
