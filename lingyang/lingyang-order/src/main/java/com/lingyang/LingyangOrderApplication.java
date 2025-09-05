package com.lingyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author yangzicheng
 * @Date Created in 23:55 2023/3/19
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.lingyang.order.mapper")
public class LingyangOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(LingyangOrderApplication.class, args);
    }

}
