package com.leyou.cart.interceptor;


import com.leyou.cart.config.JwtProperties;
import com.leyou.common.pojo.UserInfo;
import com.leyou.common.utils.CookieUtils;
import com.leyou.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//cart拦截器：cart微服务之前解析用户信息 便于后续使用降低代码耦合性
@EnableConfigurationProperties(JwtProperties.class)
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static  final  ThreadLocal<UserInfo> THREAD_LOCAL=new ThreadLocal<>();

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取本地中cookie中的token
        String token = CookieUtils.getCookieValue(request, this.jwtProperties.getCookieName());
        //对token进行排空（可省略） 如果为空 在zuul网关过滤器中肯定被拦截了
        //Jwt工具解析token 获取用户信息
        UserInfo userInfo = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
        if (userInfo==null){
            return false;
        }
        //将用户信息提供给后续使用
        // 方法1 用request域传递user（不够优雅） 方法2 使用ThreadLocal线程变量,但是注意回收
        //此处一定要回收 因为此处使用的是tomcat线程池 线程不会结束
        THREAD_LOCAL.set(userInfo);

        return true;
    }

    //通过静态方法供别的类调用
    public static UserInfo getUserInfo(){
        return THREAD_LOCAL.get();
    }


     // 这里可以释放变量 他是等到线程不被用了就释放

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        THREAD_LOCAL.remove();//清空线程局部变量

    }
}





























