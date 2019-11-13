package com.casic.bank.domain;

import com.casic.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 文件台账表
 */
@Getter
@Setter
@ToString
public class BankAccount extends BaseEntity {

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
     * 来文单位
     */
    private String communicationUnit;

    /**
     * 联合单位
     */
    private String jointUnit;

    /**
     * 标题
     */
    private String title;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 部门
     */
    private String deptName;

    /**
     * 电话
     */
    private String phone;

    /**
     * 密级
     */
    private String secretLevel;

    /**
     * 成文日期
     */
    private Date handleTime;

    /**
     * 归档时间
     */
    private Date archive;

    /**
     * 当前位置
     */
    private String location;

    /**
     * RFID标签
     */
    private String rfid;

    /**
     * 文件状态
     */
    private String status;
}
