package com.casic.bank.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UseRecord {
    private String capuseid;//使用记录ID
    private String usename;//使用人
    private String rfidCode;//RDID编号
    private String proCode;//列装号
    private String serNum;//出厂编号
    private String dictionaryType;//密品类别
    private String model;//密品型号
    private String timeStart;//开柜时间
    private String timeEnd;//关柜时间
    private String application;//所属系统
    private String deptname;//所属单位
    private String pdeptname;//上级单位
    private String equipment;//监控设备
    private String outin;//操作类别
    private String seIp;
    private String deviceName;
    private String equipmentNumber;
    private String deptNameAll;//(上级单位)所属单位
    /**
     * 取出数量
     */
    private String productOut;
    /**
     * 存入数量
     */
    private String productIn;




}
