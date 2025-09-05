package com.lingyang.auth.test;

import com.lingyang.common.pojo.UserInfo;
import com.lingyang.common.utils.JwtUtils;
import com.lingyang.common.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Author yangzicheng
 * @Date Created in 0:24 2023/3/19
 */

public class JwtTest {
    private static final String pubKeyPath = "D:\\毕业设计\\tmp\\rsa\\rsa.pub";

    private static final String priKeyPath = "D:\\毕业设计\\tmp\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        // secret 为言，复杂一点好
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        // 获取公钥，私钥
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    // 由用户信息生产token
    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    // 由token解密生成用户信息
    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTY3OTE1NzIyMn0.Wc4OghubSzk2tF54sDU-sU57KkPJfhGM13Z_Yb5bw_AlEPVO9B0TrTe8Gy2Fe_L4-mkuiBanyCVrFQHftHl9mkSMmwPikOn6Kc3zN_1CoJo6fdDfKqCMoOiNliW6qL_mbfagLN1UGxqqcyWeJBTO6G8B37vpp5m33k_g7HqQWCc";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}
