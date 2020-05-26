package com.toltech.ou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: ouyang
 * @Date: 2019/6/18 14:15
 * @Version 1.0
 */
@EnableAsync
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.toltech.ou.mapper.*")
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class IdongApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdongApplication.class,args);
        System.out.println("(♥◠‿◠)ﾉﾞ  idong启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
