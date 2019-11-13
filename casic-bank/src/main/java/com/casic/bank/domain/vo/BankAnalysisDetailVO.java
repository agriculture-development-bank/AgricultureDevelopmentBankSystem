package com.casic.bank.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BankAnalysisDetailVO implements Serializable {

    private String planId;

    private String detailId;

    private String status;

    private String flowId;

    private String registrationNum;

    private String documentNum;

    private String title;

    private String secretLevel;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT + 08:00")
    private Date handleTime;

    private String detailStatus;

    private int sort;
}
