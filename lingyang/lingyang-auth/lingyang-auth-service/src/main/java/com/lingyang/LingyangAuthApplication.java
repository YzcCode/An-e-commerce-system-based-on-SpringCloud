package com.lingyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author yangzicheng
 * @Date Created in 0:02 2023/3/19
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LingyangAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(LingyangAuthApplication.class);
    }

}
