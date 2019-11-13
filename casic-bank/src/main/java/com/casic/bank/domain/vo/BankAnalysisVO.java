package com.casic.bank.domain.vo;

import com.casic.bank.domain.BankReceiveFiles;
import lombok.Data;

@Data
public class BankAnalysisVO extends BankReceiveFiles {

    private String selectedNum;

    private String fileDetailIds;

    private String planId;
}
