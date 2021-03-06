package com.hb0730.cloud.admin.gateway;

import com.hb0730.cloud.admin.gateway.rocketmq.ISink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 * 服务网关
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.hb0730.cloud.admin")
@EnableBinding({ISink.class})
@ComponentScan(basePackages = "com.hb0730.cloud.admin")
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
