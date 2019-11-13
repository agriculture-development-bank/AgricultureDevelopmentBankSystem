package com.casic.bank.domain.vo;

import com.casic.bank.domain.BankAnalysisManage;
import lombok.Data;

@Data
public class BankAnalysisManageVO extends BankAnalysisManage {

    /**
     * 所属部门
     */
    private String deptName;
}
