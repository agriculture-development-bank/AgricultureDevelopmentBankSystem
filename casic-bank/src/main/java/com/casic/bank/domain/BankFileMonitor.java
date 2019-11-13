package com.casic.bank.domain;

import com.casic.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件在位监控输入信息
 */
@Getter
@Setter
@ToString
public class BankFileMonitor extends BaseEntity {

    /**
     * 主键
     */
    private String id;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 登记号
     */
    private String registrationNum;

    /**
     * 文号
     */
    private String documentNum;

    /**
     * 份数
     */
    private String numOfCopies;

    /**
     * 密级
     */
    private String secretLevel;

    /**
     * 当前位置
     */
    private String location;
}
