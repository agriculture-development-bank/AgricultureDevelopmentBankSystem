package com.casic.bank.domain.vo;

import com.casic.bank.domain.BankFileSignOpinion;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BankFileSignOpinionVO extends BankFileSignOpinion {
    /**
     * 主键
     */
    private String leaderUserName;
}
