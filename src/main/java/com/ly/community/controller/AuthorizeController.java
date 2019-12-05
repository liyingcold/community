package com.ly.community.controller;

import com.ly.community.dto.AccessTokenDTO;
import com.ly.community.dto.GithubUser;
import com.ly.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * 登录成功后返回index页面
     * @param code 需要传递的参数
     * @param state 需要传的参数
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                            @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
//        得到user信息，
        GithubUser user=githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
