package com.casic.bank.domain;

import com.casic.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 文件流程记录履历表
 */
@Getter
@Setter
@ToString
public class BankRecord extends BaseEntity {

    private String id;

    private String userId;

    private String belongDept;

    private String receiveDept;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date operateTime;

    private String operateResult;

    private String fileId;

    private String analysisDetailId;
}
