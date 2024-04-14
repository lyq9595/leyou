package cn.itcast.service.controller;


import cn.itcast.service.client.UserClient;
import cn.itcast.service.pojo.User;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("consumer/user")
//@DefaultProperties(defaultFallback ="fallbackMethod" )  //定义全局的熔断方法
public class UserController {

    @Autowired
    private UserClient userClient;

    /*@Autowired
    private RestTemplate restTemplate;*/

  /*  @Autowired
    private DiscoveryClient discoveryClient;//包含了拉取的所有服务信息
*/

    @GetMapping
    @ResponseBody
    @HystrixCommand //声明熔断的方法
    public String queryUserById(@RequestParam("id")Long id){
       /* List<ServiceInstance> instances = discoveryClient.getInstances("service-provider");
        ServiceInstance instance = instances.get(0);*/

       /* if (id==28){
            throw new RuntimeException();
        }*/
        return userClient.queryUserById(id).toString();
    }


    /*public  String queryUserByIdFallback(){
        return "服务器正忙，请稍后再试！";
    }
*/
    /*public  String fallbackMethod(){
        return "服务器正忙，请稍后再试！";
    }*/
}
























