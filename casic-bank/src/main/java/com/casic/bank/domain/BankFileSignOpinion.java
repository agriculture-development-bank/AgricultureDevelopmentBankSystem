package com.casic.bank.domain;

import com.casic.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件签署意见子表
 */
@Getter
@Setter
@ToString
public class BankFileSignOpinion extends BaseEntity {

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
     * 意见类型
     */
    private String opinionType;

    /**
     * 意见及结果
     */
    private String opinion;
    /**
     * 领导姓名
     */
    private String leaderName;
    /**
     * 领导职务
     */
    private String leaderPost;
}
