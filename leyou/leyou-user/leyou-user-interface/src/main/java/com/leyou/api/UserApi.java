package com.leyou.api;

import com.leyou.user.pojo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserApi {

  /**
   * 根据用户名和密码查询用户
   * @param username
   * @param password
   * @return
   */
  @GetMapping("query")
  public User queryUser(
          @RequestParam("user") String username, @RequestParam("password") String password) ;




















}