package com.casic.bank.domain;

import com.casic.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 位置实体
 */
@Getter
@Setter
@ToString
public class BankLocation extends BaseEntity {
    /**
     * 主键
     */
    private String id;
    /**
     * 位置描述
     */
    private String description;
    /**
     * 位置名称
     */
    private String locationName;
    /**
     * 位置编码
     */
    private String locationCode;

    /**
     * 是否是存储位置
     */
    private String store;
    /**
     * 部门
     */
    private String belongDept;
}
