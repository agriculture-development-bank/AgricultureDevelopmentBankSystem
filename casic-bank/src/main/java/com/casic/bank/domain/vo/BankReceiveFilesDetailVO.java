package com.casic.bank.domain.vo;

import com.casic.bank.domain.BankReceiveFilesDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BankReceiveFilesDetailVO extends BankReceiveFilesDetail {

    /**
     * 登记号
     */
    private String registrationNum;

    /**
     * 文号
     */
    private String documentNum;

    /**
     * 标题
     */
    private String title;

    /**
     * 文件位置id
     */
    private String locationId;
    /**
     * 文件位置
     */
    private String locationName;
    /**
     * 当前部门名称
     */
    private String currentDeptName;
    /**
     * 文件密级
     */
    private String secretLevel;
    /**
     * 来文单位
     */
    private String communicationUnit;
    /**
     * 联合单位
     */
    private String jointUnit;
    /**
     * 总份数
     */
    private String numOfCopies;

    private String deptName;

    private String phone;

    private String contact;

    private String planDetailId;

    private String planId;

    private String editType;

    /**
     * 成文日期
     */
    private String handleTime;

    /**
     * 紧急程度
     */
    private String urgency;
}
