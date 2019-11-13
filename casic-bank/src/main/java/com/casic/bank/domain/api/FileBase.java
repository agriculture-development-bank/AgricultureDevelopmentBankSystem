package com.casic.bank.domain.api;

import lombok.Data;

import java.util.Date;

/**
 * @author qh
 * @Classname FileBase
 * @Description 文件交换系统基础数据接口  请求参数实体类
 * @Date 2019/10/24 15:52
 */
@Data
public class FileBase {
    /** rfid编号 */
    private String rfid;
    /** 文号 */
    private String filenoword;
    /** 文件标题 */
    private String filetitle;
    /** 文件密级 */
    private String filesecret;
    /** 紧急程度 */
    private String fileurgency;
    /** 来文单位 */
    private String senddeptname;
     /** 开始时间 */
    private Date start_time;
    /** 结束时间 */
    private Date end_time;
}
