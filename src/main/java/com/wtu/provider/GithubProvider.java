package com.wtu.provider;

import com.alibaba.fastjson.JSON;
import com.wtu.dto.AccessTokenDto;
import com.wtu.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class GithubProvider {
        public  String getAccessToken(AccessTokenDto accessTokenDto)
        {
             MediaType mediaType = MediaType.get("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDto),mediaType);
            Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();
            try (Response response = client.newCall(request).execute()) {
               String string=response.body().string();
               String[] split=string.split("&");
               String tokenstr=split[0];
               String token=tokenstr.split("=")[1];
               System.out.println(token);
               return  token;

        } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        public GithubUser getUser(String accessToken) throws IOException {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken).build();

            Response response = client.newCall(request).execute();
            String string=response.body().string();
            GithubUser githubUser= JSON.parseObject(string,GithubUser.class);
                    return githubUser;


        }

}
