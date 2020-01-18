package com.lz;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lz
 * @date 2020-01-16 11:29
 */
@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EnableApolloConfig
@EnableFeignClients
public class AppWeixin {
    public static void main(String[] args) {
        SpringApplication.run(AppWeixin.class, args);
    }
}
