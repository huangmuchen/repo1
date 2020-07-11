package com.xuecheng.auth.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: HuangMuChen
 * @date: 2020/7/11 21:50
 * @version: V1.0
 * @Description: JWT生成与校验测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestJWT {

    /**
     * 生成一个jwt令牌
     */
    @Test
    public void createJWT() {
        // 秘钥库名称
        String keystoreName = "xc.keystore";
        // 秘钥库密码
        String keystorePwd = "xuechengkeystore";
        ClassPathResource classPathResource = new ClassPathResource(keystoreName);
        // 秘钥工厂创建：需要秘钥库路径和密码
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, keystorePwd.toCharArray());
        // 获取秘钥对：需要别名和秘钥访问密码
        String alias = "xcKey";
        String keyPwd = "xuecheng";
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, keyPwd.toCharArray());
        // 获取私钥
        RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
        // jwtPayload内容
        Map<String, String> map = new HashMap<>();
        map.put("name", "黄沐尘");
        String jsonString = JSON.toJSONString(map);
        // 生成JWT
        Jwt jwt = JwtHelper.encode(jsonString, new RsaSigner(aPrivate));
        // 打印令牌
        System.out.println(jwt.getEncoded());
    }

    /**
     * 资源服务使用公钥验证jwt的合法性，并对jwt解码
     */
    @Test
    public void verifyJWT() {
        // 公钥
        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiuJJu+q8k9mjx5a7nrEzce30VuBoz0UEdv+M7JU6SY7W3oCDZpGojlclm0CU8+ZvO0hGzUKZS2I2+9VcqzYWc2EDrNPtHoXXD/yiLSTk5WVMLT9aEAjoqSSzJhfpATXT9K0VSrnbk3r3+gPAuzL+NEBCoKBTEZpZxUHq51N0ExQPnjc2vyZ7kJO1dtzgayGF2xnxjazS1F1J6AZ2maL8G7Mxy0xKZnbgXvaO1yQd1XHEhzhJhx1W8KF1N4iln84rv8LbSHuaT8IiMaohphseNTbntWdyiQ2+TQxZOWwyWPxCBkAEjSQ/TOBIWeop88ubjR2DrMQaPcluicfOcF1s1wIDAQAB-----END PUBLIC KEY-----";
        // jwt令牌
        String jwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoi6buE5rKQ5bCYIn0.HOFJRl6ZmFSYYojqZFGTjF1SWmArP3R34TNdhkD0NoH9q6WGxw6kt7BB2Qa7z7229lR7A4F2w8PrlHYVtQ1ASnE8R_jObirmYBUmMziJjvSQwMvBP69jovo1EzNSeoO5O1TNS16-duMu3X1dAcQaXE7fSAvl9wiBOF9GnvT1TX0hjsfuyLNjbXhhVmEu2k-iyDqXlIR55bCEZqk7-e7yeCMb_x_HiMlDEpbmCQTkmbpUMeb1zCsu5by55KfzAAIMgRSQYCShBXDD__BCJ0bdxg5xK_zpwwM67J06XK3MsBbRMn4_H7sbpQ5dzcpfN4-QZhvlL2ndVxsb6uo_zJE51w";
        // 校验JWT
        Jwt jwt = JwtHelper.decodeAndVerify(jwtToken, new RsaVerifier(publicKey));
        // 拿到jwt中的自定义内容
        System.out.println(jwt.getClaims());
    }
}