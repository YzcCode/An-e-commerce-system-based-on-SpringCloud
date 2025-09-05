package com.lingyang.auth.service;

import com.lingyang.auth.client.UserClient;
import com.lingyang.auth.config.JwtProperties;
import com.lingyang.common.pojo.UserInfo;
import com.lingyang.common.utils.JwtUtils;
import com.lingyang.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author yangzicheng
 * @Date Created in 0:50 2023/3/19
 */
@Service
public class AuthService {
    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties jwtProperties;

    public String accredit(String username, String password) {

        //1.根据用户名和密码查询
        User user = this.userClient.queryUser(username,password);

        //2.判断user
        if (user == null){
            return null;
        }

        try {
            //3.jwtUtils生成jwt类型的token
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setUsername(user.getUsername());
            return JwtUtils.generateToken(userInfo,this.jwtProperties.getPrivateKey(),this.jwtProperties.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
