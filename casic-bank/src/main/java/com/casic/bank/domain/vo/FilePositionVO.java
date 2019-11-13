package com.casic.bank.domain.vo;

import lombok.Data;

/**
 * @author qh
 * @Classname FilePositionVO
 * @Description TODO
 * @Date 2019/10/17 19:09
 */
@Data
public class FilePositionVO {

    /**
     * 文件id
     */
    private String id;

    /**
     * 载体柜id
     */
    private String equipmentId;

    /**
     * 单元门id
     */
    private String capBoardId;
}
