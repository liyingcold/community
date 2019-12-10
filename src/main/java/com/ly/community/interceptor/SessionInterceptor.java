package com.ly.community.interceptor;

import com.ly.community.mapper.UserMapper;
import com.ly.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//有了这个注解userMapper才能注入，Autowired这个注解才会生效
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        对所有地址进行拦截
//        请求cookie
        Cookie[] cookies=request.getCookies();
        if (cookies !=null && cookies.length !=0){
//            循环cookie拿到token
            for (Cookie cookie : cookies) {
//                命中后
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
//                    访问indexController时，拿出数据库的token和cookie中的token，进行比较，
                    User user=userMapper.findByToken(token);
//                    命中，将user放到session里面，由前端去判断是展示姓名还是登录
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
