package com.casic.bank.domain;

import com.casic.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * 文件清退计划表
 */
@Getter
@Setter
@ToString
public class BankAnalysisManage extends BaseEntity {
    /** 清退计划Id */
    private String id;
    /** 单位名称 */
    private String deptId;
    /** 所属部门id */
    private String belongDeptId;
    /** 起始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;
    /** 结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;
    /** 位置 */
    private String location;
    /** 计划状态 */
    private String planStatus;
    /** 下发时间 */
    private Date issueTime;
    /** 清退份数 */
    private String num;
}
