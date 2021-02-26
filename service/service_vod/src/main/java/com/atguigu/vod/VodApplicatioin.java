package com.atguigu.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//不加载DataSourceAutoConfiguration.class
@ComponentScan(basePackages = {"com.atguigu"})
@EnableDiscoveryClient//nacos注册
public class VodApplicatioin {
    public static void main(String[] args) {
        SpringApplication.run(VodApplicatioin.class, args);
    }
}

