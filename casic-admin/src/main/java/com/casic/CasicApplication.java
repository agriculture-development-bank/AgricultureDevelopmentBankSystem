package com.casic;

import com.casic.common.config.Global;
import org.casic.workflow.config.ApplicationConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;

/**
 * 启动程序
 *
 * @author yuzengwen
 */
@Import({
        ApplicationConfiguration.class
})
@MapperScan({
        "com.casic.*.mapper",
        "org.casic.workflow.mapper"
})
@SpringBootApplication(
        exclude = {
                SecurityAutoConfiguration.class,
                DataSourceAutoConfiguration.class,
                LiquibaseAutoConfiguration.class
        }
)
@EnableTransactionManagement
public class CasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(CasicApplication.class, args);
        System.out.println("=====================启动成功=========================");
    }

    /**
     * 文件上传临时路径
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String path = Global.getConfig("casic.profile");
        factory.setLocation(path);
        return factory.createMultipartConfig();
    }

}

