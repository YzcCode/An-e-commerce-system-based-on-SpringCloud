package com.lingyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author yangzicheng
 * @Date Created in 10:50 2023/3/7
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LingyangUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(LingyangUploadApplication.class);
    }
}
