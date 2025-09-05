package com.lingyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author yangzicheng
 * @Date Created in 10:55 2023/3/16
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LingyangGoodsWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(LingyangGoodsWebApplication.class);
    }

}
