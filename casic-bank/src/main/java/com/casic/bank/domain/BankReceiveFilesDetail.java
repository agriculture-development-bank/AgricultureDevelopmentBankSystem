package com.casic.bank.domain;

import com.casic.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件台账详情表
 */
@Getter
@Setter
@ToString
public class BankReceiveFilesDetail extends BaseEntity {
    /** 主键 **/
    private String id;
    /** 文件台账id **/
    private String fileId;
    /** 流水号 **/
    private String flowId;
    /** 文件状态 **/
    private String status;
    /** RFID **/
    private String rfid;
    /** 文件位置id **/
    private String locationId;
    /** 文件当前所在部门Id **/
    private String currentDeptId;
    /** 序号 **/
    private int sort;
}
