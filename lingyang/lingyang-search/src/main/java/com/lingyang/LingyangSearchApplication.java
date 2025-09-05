package com.lingyang;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @Author yangzicheng
 * @Date Created in 22:36 2023/3/12
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LingyangSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(LingyangSearchApplication.class);
    }

}
