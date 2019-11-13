package com.casic.bank.domain.vo;

import com.casic.bank.domain.BankRecord;
import lombok.Data;

@Data
public class BankRecordVO extends BankRecord {

    private String userName;

    private String belongDeptName;

}
