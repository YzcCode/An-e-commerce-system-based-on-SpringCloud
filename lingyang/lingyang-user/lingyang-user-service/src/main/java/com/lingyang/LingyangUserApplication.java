package com.lingyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author yangzicheng
 * @Date Created in 23:46 2023/3/17
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lingyang.user.mapper")
public class LingyangUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(LingyangUserApplication.class);
    }

}
