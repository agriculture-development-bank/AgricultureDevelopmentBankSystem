package com.casic.bank.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author qh
 * @Classname SelectedBankAnalysisDetailVO
 * @Description TODO
 * @Date 2019/10/28 10:32
 */
public class SelectedBankAnalysisDetailVO {

    private String planId;

    private String detailId;

    private String accountId;

    private String fileId;

    private String registrationNum;

    private String documentNum;

    private String title;

    private String secretLevel;

    private String communicationUnit;

    private String jointUnit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date createTime;
}
