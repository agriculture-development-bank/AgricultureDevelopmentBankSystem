package com.casic.bank.domain;

import com.casic.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 文件收文登记输入信息
 */
@Getter
@Setter
@ToString
public class BankReceiveFiles extends BaseEntity {

    /**
     * 主键
     */
    private String id;
    /**
     * 登记号
     */
    private String registrationNum;
    /**
     * 密级
     */
    private String secretLevel;
    /**
     * 紧急程度
     */
    private String urgency;
    /**
     * 份数
     */
    private String numOfCopies;

    /**
     * 文号
     */
    private String documentNum;

    /**
     * 成文日期
     */
    private Date handleTime;

    /**
     * 标题
     */
    private String title;

    /**
     * 发文单位
     */
    private String communicationUnit;

    /**
     * 联合单位
     */
    private String jointUnit;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 办公室主任意见
     */
    private String directorOpinion;

    /**
     * 承办部门意见
     */
    private String hostOpinion;

    /**
     * 协办部门意见
     */
    private String coOrganzierOpinion;

    /**
     * 查询需要
     */
    private String status;

    /**
     * 已清退数量
     */
    private String analysisCount;

    /**
     * 载体柜id
     */
    private String equipmentId;

    /**
     * 单元门id
     */
    private String capBoardId;

    /**
     * 离柜天数
     */
    private String leaveCupboardDays;
}
