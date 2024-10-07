package com.example.bcp.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    //密钥
    private static final String SECRET = "JWT_SECRET";

    //过期时间
    private static final long EXPIRATION = 1800L; //单位为秒

    //生成用户token
    public static String createToken(String userName, String password){
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
        Map<String,Object> map = new HashMap<>();
        map.put("alg","HS256");
        map.put("typ","JWT");
        String token = JWT.create()
                .withHeader(map)//添加头部
                //基本信息
                .withClaim("userName",userName)
                .withClaim("password",password)
                .withExpiresAt(expireDate)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    //校验token并解析
    public static Map<String, Claim> verifyToken(String token){
        DecodedJWT jwt = null;
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("token解码异常");
            return null;
        }
        return jwt.getClaims();
    }
}
