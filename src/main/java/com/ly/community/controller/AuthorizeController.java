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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
     * HttpServletRequest request当注入这个形参，spring会自动将上下文中的request放到这里，我们可以去使用
     * @param code 需要传递的参数
     * @param state 需要传的参数
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
//        当GitHub登录成功后，得到user信息，
        GithubUser githubUser=githubProvider.getUser(accessToken);
        if (githubUser!= null && githubUser.getId() !=null){
            User user = new User();
//            用token绑定前端和后端状态
            String token = UUID.randomUUID().toString();
//            将token写入user对象
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
//            将user信息写入数据库，用token当作登录的验证
//            每次请求时的session不同，所以我们自定义一个字段，分别存入数据库和cookie，再次访问时，就可以比较两次的token是否相同，而不用每次都需要用户登录
            userMapper.insert(user);
//            自动将token写入cookie，cookie在response里面
            response.addCookie(new Cookie("token",token));
//            重定向
            return "redirect:/";

        }else {
//            登录失败，重新登陆
            return "redirect:/";

        }
    }
}
