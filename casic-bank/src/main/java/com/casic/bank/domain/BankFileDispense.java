package com.casic.bank.domain;

import com.casic.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BankFileDispense extends BaseEntity {

    private String id;

    /** 任务标题*/
    private String taskTitle;

    /**所属部门*/
    private String belongDept;

    /**接收部门*/
    private String receiveDept;

    /** 分发文件 */
    private String dispenseFile;

}
