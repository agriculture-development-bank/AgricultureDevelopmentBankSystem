package com.casic.bank.domain.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author qh
 * @Classname FileOut
 * @Description 文件取件返回信息  接口 接收参数实体类
 * @Date 2019/10/22 14:56
 */
@Data
public class FileOut {
    /** 公文二维条码编号 */
    private String filecode;
    /** 文件份号 */
    private String sendindex;
    /** 取件单位 */
    private String takeoutdept;
    /** 取件人 */
    private String takeoutuser;
    /** 取件时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date takeouttime;
}
