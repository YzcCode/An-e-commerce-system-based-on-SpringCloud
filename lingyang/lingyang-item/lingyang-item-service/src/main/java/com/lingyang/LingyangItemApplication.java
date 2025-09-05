package com.lingyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author yangzicheng
 * @Date Created in 18:16 2023/3/5
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lingyang.item.mapper")
public class LingyangItemApplication {


    public static void main(String[] args) {
        SpringApplication.run(LingyangItemApplication.class);
    }
}
