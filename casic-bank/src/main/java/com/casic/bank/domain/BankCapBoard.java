package com.casic.bank.domain;

import com.casic.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author qh
 * @Classname BankCapBoard
 * @Description 载体柜中单元门实体类
 * @Date 2019/10/17 10:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BankCapBoard extends BaseEntity {
    /** 主键 */
    private String id;
    /** 单元门名称 */
    private String capBoardName;
    /** 单元门编号 */
    private int capBoardCode;
    /** 载体柜id */
    private String equipmentId;
}
