package com.lingyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author yangzicheng
 * @Date Created in 17:11 2023/3/5
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class LingyangGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LingyangGatewayApplication.class);
    }
}
