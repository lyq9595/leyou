package com.leyou.sms.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SmsUtils {

    public  void sendVerifyCode(String phone,String code,String signName,String verifyCodeTemplate){
    System.out.println("注册手机号为："+phone);
    System.out.println(signName+verifyCodeTemplate+code);
}


}











