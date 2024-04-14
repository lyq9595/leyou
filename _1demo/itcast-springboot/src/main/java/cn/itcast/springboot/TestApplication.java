package cn.itcast.springboot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
@EnableAutoConfiguration//启用boot引用的自动配置
@ComponentScan//类似于<component:component-scan base-packages="">扫描该类所在的包及子孙包
*/

@SpringBootApplication//组合注解 相当于@EnableAutoConfiguration @Component和@SpringBootConfiguration
public class TestApplication {

  public static void main(String[] args) {
      SpringApplication.run(TestApplication.class,args);
  }
}





















