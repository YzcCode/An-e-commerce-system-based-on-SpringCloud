package com.lingyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author yangzicheng
 * @Date Created in 16:56 2023/3/5
 */
@SpringBootApplication
@EnableEurekaServer
public class LingyangRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LingyangRegistryApplication.class);
    }
}
