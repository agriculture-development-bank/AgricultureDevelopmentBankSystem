package com.casic.bank.domain;

import com.casic.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Productincap extends BaseEntity{
    private String id;

    private String sysequipmentId;

    private String productId;

    /*ch, 启用第四个字段*/
    private String idOrEpc;

    private String position;


}