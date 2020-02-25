package com.casic.bank.domain;

import com.casic.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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
     * 意见及结果 ( 行领导意见--1，办公室主任意见--2，承办部门意见--3，协办部门意见--4 )
     */
    private String opinion;

    /**
     * 意见及结果
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date opinionTime;
    /**
     * 领导姓名
     */
    private String leaderName;
    /**
     * 领导职务
     */
    private String leaderPost;
}
