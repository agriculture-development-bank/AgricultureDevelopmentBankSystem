package com.casic.bank.domain;

import lombok.Data;

import java.util.List;

@Data
public class ResultBean {

    private BankReceiveFiles bankReceiveFiles;

    private List<BankFileSignOpinion> bankFileSignOpinions;

    private String directorOpinionId;

    private String hostOpinionId;

    private String coOrganzierOpinionId;

    private String directorOpinion;

    private String hostOpinion;

    private String coOrganzierOpinion;

}
