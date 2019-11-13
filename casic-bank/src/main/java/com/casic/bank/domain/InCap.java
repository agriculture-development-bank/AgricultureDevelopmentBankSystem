package com.casic.bank.domain;

import com.casic.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Created by 706 on 2017/7/6.
 */
@Getter
@Setter
@ToString
public class InCap extends BaseEntity{
    private String id;


    private String incapid;//在柜记录ID
    private String equipment;//监控设备
    private String location;//位置
    private String usename;//责任人
    private String productnum;//密品数量
    private String proCode;//列装号
    private String serNum;//出厂编号
    private String dictionaryType;//密品类别
    private String model;//密品型号
    private String rfidCode;//RDID编号
    private String application;//所属系统
    private String deptName;//所属单位
    private String pdeptname;//上级单位
    private String sysIp;//设备IP
    private  String productid;//设备编号
    private String sysEquipmentId;
    private String equipmentNumber;
    private String deviceName;//设备名称
    private String position;
    private String deptNameAll;//(上级单位)所属单位
    private String productName;//
    private String isMq;
    private String departMentId;
    private String idOrEpc;
    /**
     * 供电状态 0市电供电 1备用电源供电
     */
    private String state;
    /**
     * 电量
     */
    private String battery;
    /**
     * 是否在线 0 离线 1在线
     */
    private String online;


    /**
     * 登记号
     */
    private String registrationNum;
    /**
     * 密级
     */
    private String secretLevel;
    /**
     * 紧急程度
     */
    private String urgency;
    /**
     * 份数
     */
    private String numOfCopies;

    /**
     * 文号
     */
    private String documentNum;

    /**
     * 成文日期
     */
    private Date handleTime;

    /**
     * 标题
     */
    private String title;

    /**
     * 发文单位
     */
    private String communicationUnit;

    /**
     * 联合单位
     */
    private String jointUnit;

    /**
     * 部门id
     */
    private String deptId;


    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String phone;

    private String equipmentId;

}
