package com.ly.community.provider;

/**
 * 提供第三方支持的技术
 */
import com.alibaba.fastjson.JSON;
import com.ly.community.dto.AccessTokenDTO;
import com.ly.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

//不需要实例化，可以拿到变量，即将当前的类初始化到容器的上下文
@Component
public class GithubProvider {
//    参数超过两个，可以封装起来，不要直接放在参数里面

    /**
     * ly社区会通过https://github.com/login/oauth/access_token重新携带code，访问GitHub
     * code如果是正确的，会返回一个正确的access_token,使用信息
     * @param accessTokenDTO
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO){

//        fastjson发送请求   POST
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
            String[] split=string.split("&");
            String tokenStr=split[0].split("=")[1];
            System.out.println(string);
            return tokenStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * user携带accessToken，给GitHub，验证成功后，返回用户信息
     * @param accessToken
     * @return
     */
    public GithubUser getUser(String accessToken){
//        GET请求
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string=response.body().string();
            GithubUser githubUser=JSON.parseObject(string,GithubUser.class);
            return githubUser;
        }catch (IOException e){

        }
        return null;
    }
}
