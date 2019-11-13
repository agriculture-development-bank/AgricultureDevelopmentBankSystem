package com.casic.bank.domain.vo;

import com.casic.bank.domain.BankEquipment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 设备列表返回数据
 */
@Getter
@Setter
@ToString
public class BankEquipmentVO extends BankEquipment {

    /**
     * 责任人名称
     */
    private String userName;
    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 位置名称
     */
    private String locationName;

}
