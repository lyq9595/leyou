package cn.itcast.service.client;

import cn.itcast.service.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-provider",fallback = UserClientFallback.class)//声明是feign接口 指出服务id
public interface UserClient {

  @GetMapping("user/{id}")
  public User queryUserById(@PathVariable("id") Long id);


}












