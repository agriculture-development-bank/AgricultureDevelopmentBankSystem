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
public class ProductUseRecord extends BaseEntity{
    private String id;

    private String capUseRecordId;

    private String productId;

    private String outin;
    private String ProductInfo;
    /**
     * ch, 启用isOrEpc作为开元数据的标识
     */
    private String idOrEpc;


}