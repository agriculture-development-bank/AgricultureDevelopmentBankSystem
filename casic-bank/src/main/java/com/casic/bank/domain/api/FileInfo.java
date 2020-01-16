package com.casic.bank.domain.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author qh
 * @Classname FileInfo
 * @Description 文件投箱接口 接收参数实体类
 * @Date 2019/10/22 13:33
 */
@Data
public class FileInfo {

    /** 公文二维条码编号 */
    private String filecode;
    /** 文件份号 */
    private String sendindex;
    /** 投箱单位 */
    private String putdept;
    /** 投箱人 */
    private String putuser;
    /** 投箱时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date puttime;
    /** 目标部门 */
    private String targetdept;
}
