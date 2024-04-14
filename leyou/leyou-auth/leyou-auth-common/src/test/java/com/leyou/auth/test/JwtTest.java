package com.leyou.auth.test;

import com.leyou.common.pojo.UserInfo;
import com.leyou.common.utils.JwtUtils;
import com.leyou.common.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

  private static final String pubKeyPath = "G:\\IdeaProjects\\temp\\rsa\\rsa.pub";

  private static final String priKeyPath = "G:\\IdeaProjects\\temp\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

//    @Test
//    public void testParseToken() throws Exception {
//    String token =
//        "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTYyOTgwMjM4NX0.RZOsXT_lUSEY3eYifc_Bz5AkNZgRCy7baorkcoFM0rBkcOfCuo-0aWS_F-1Ul0DN2-fNCtfTij0eX7uruSr2spj6i2kOMW2kLan6hPZ_elCJTru97fe2mV400tVsZKtn4sdvn6GdfGE5cx2DDLtO2zMz14De0Bo55hjvH_XTKS8";
//
//        // 解析token
//        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
//        System.out.println("id: " + user.getId());
//        System.out.println("userName: " + user.getUsername());
//    }
}
























