package cn.itcast.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.itcast.service.mapper")//mapper接口包扫描
@EnableDiscoveryClient //开启eureka客户端
public class ItcastServiceProviderApplication {

  public static void main(String[] args) {
    SpringApplication.run(ItcastServiceProviderApplication.class, args);
  }
}
