package com.kklll.logwarningsystem.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Map;

/**
 * @Author DeepBlue
 * @Date 2020/11/12 15:20
 * jwt的一些工具类方法
 */
@Slf4j
@Component
public class JwtUtils {
    @Value("${jwt.secretKey}")
    private String SIGN;
    @Value("${jwt.timeout}")
    private int timeout;

    public String createToken(Map<String, String> map) {
        //获取时间
        Calendar calendar = Calendar.getInstance();
        //设置Token60分钟过期
        calendar.add(Calendar.MINUTE, timeout);
        //创建一个Token
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        builder.withExpiresAt(calendar.getTime());
        return builder.sign(Algorithm.HMAC256(SIGN));
    }


    /**
     * 如果抛出异常那么说明验签失败
     *
     * @param token
     */
    public DecodedJWT verify(String token) {
        //sha 256
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }

    public String getRequestToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        return token;
    }

    public String getSIGN() {
        return SIGN;
    }

    public Map<String, Claim> getTokenInfo(String token) {
        DecodedJWT decode = JWT.decode(token);
        Map<String, Claim> claims = decode.getClaims();
        return claims;
    }

    public String getUsername(String token) {
        Map<String, Claim> tokenInfo = getTokenInfo(token);
        Claim username = tokenInfo.get("username");
        return username.asString();
    }
}
