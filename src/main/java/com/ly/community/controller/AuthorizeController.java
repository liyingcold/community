package com.ly.community.controller;

import com.ly.community.dto.AccessTokenDTO;
import com.ly.community.dto.GithubUser;
import com.ly.community.mapper.UserMapper;
import com.ly.community.model.User;
import com.ly.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 将当前的类作为路由api的一个承载者
*/
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

//    读取配置文件里面的key，并且为其赋值
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录成功后返回index页面
     * HttpServletRequest request当写入这个参数，spring会自动将上下文中的request放到这里，我们可以去使用
     * @param code 需要传递的参数
     * @param state 需要传的参数
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
//        得到user信息，
        GithubUser githubUser=githubProvider.getUser(accessToken);
        if (githubUser!= null){
//            将user对象写入session
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            request.getSession().setAttribute("user",githubUser);
//            重定向
            return "redirect:/";
//            登陆成功，写cookie和session
        }else {
//            登录失败，重新登陆
            return "redirect:/";

        }
    }
}
