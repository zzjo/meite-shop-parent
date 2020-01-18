package com.lz.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author lz
 * @date 2020-01-16 9:57
 */
@SpringBootApplication
@EnableEurekaServer
public class AppEureka {
    public static void main(String[] args) {
        SpringApplication.run(AppEureka.class, args);
    }
}
