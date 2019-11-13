package com.casic.bank.domain.vo;

import com.casic.bank.domain.BankReceiveFilesDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 使用记录详情输出类
 */
@Getter
@Setter
@ToString
public class BankFileVo extends BankReceiveFilesDetailVO{

    private String opeType;

}
