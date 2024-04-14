package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserController {

  @Autowired
  private UserService userService;

  /**
   * 校验数据是否可用
   * @param data
   * @param type
   * @return
   */
  @GetMapping("/check/{data}/{type}")
  public ResponseEntity<Boolean> checkUser(@PathVariable("data")String data,@PathVariable("type")Integer type){
    Boolean bool=this.userService.checkUser(data,type);
    if (bool==null){
      return  ResponseEntity.badRequest().build();
    }
    return  ResponseEntity.ok(bool);
  }

  /**
   * 发送验证码
   * @param phone
   * @return
   */
  @PostMapping("code")
  public  ResponseEntity<Void> sendVerifyCode(@RequestParam("phone")String phone){
    this.userService.sendVerifyCode(phone);
    return  ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * 注册提交表单
   * @param user
   * @param code
   * @return
   */
  @PostMapping("register")
  public  ResponseEntity<Void> register(@Valid User user, @RequestParam("code")String code){
    Boolean bool = this.userService.register(user, code);
    if (bool == null || !bool) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }


  /**
   * 根据用户名和密码查询用户
   * @param username
   * @param password
   * @return
   */
  @GetMapping("query")
  public ResponseEntity<User> queryUser( @RequestParam("user")String username, @RequestParam("password")String password){
    User user=this.userService.queryUser(username,password);
    if (user==null){
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(user);
  }

}








































