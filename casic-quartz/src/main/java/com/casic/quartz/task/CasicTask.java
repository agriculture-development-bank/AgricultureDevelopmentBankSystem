package com.casic.quartz.task;

import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 * 
 * @author yuzengwen
 */
@Component("casicTask")
public class CasicTask
{
    public void casicParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void casicNoParams()
    {
        System.out.println("执行无参方法");
    }
}
