package cn.itcast.springboot.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello2")
@EnableAutoConfiguration//启用自动配置
public class HelloController2 {

    @GetMapping("show")
    public  String test(){
        return "hello springboot 2";
    }

}




























