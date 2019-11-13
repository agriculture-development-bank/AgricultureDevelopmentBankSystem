package com.casic.bank.domain;


import com.casic.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Lettle
 */

@Getter
@Setter
@ToString
public class CapUseRecord extends BaseEntity{


    private String id;


    private String sysequipmentId;


    private String sysuserId;

    private String timeStart;

    private String timeEnd;

    private String openType;

    private String cause;

    private String caseNum;

    private String coordinate;


    private String sysequipmentDicValue;




}