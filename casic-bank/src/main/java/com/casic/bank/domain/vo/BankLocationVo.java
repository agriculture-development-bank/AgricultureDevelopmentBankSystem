package com.casic.bank.domain.vo;

import com.casic.bank.domain.BankLocation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BankLocationVo extends BankLocation {

    private String deptName;

    private String createUserName;
}
