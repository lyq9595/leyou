package cn.itcast.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@SpringBootApplication
//@EnableDiscoveryClient
//@EnableCircuitBreaker //开启熔断
@SpringCloudApplication //组合注解相当于上面三个注解
@EnableFeignClients //启用feign组件
public class ItcastServiceConsumerApplication {

  /*@Bean
  @LoadBalanced //开启负载均衡
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }
*/
  public static void main(String[] args) {
    SpringApplication.run(ItcastServiceConsumerApplication.class, args);
  }
}

































