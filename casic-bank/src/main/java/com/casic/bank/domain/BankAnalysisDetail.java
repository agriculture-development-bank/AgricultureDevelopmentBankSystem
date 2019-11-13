package com.casic.bank.domain;

import com.casic.common.base.BaseEntity;
import lombok.Data;

/**
 * 文件清退计划子表
 */
@Data
public class BankAnalysisDetail extends BaseEntity {
    /** 主键 */
    private String id;
    /** 清退计划id */
    private String planId;
    /** 台账id */
    private String accountId;
    /** 文件id */
    private String fileId;
    /** 清退状态 */
    private String status;
}
