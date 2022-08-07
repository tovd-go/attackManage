package com.tovd.org.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tovd.org.entity.User;
import com.tovd.org.exception.MyException;
import com.tovd.org.mapper.UserMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Map;


@Mapper
public class JwtUtils {

    private static  final  String SING="admin";
    public static String getToken(Map<String,String> map) {
        Calendar instance = Calendar.getInstance();
        //默认7天过期
        instance.add(Calendar.DATE,7);
        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });

        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SING));
        return token;
    }

    public static DecodedJWT verify(String token) throws MyException {
        if (StringUtils.isEmpty(token)){
            throw new MyException("token 不能为空");
        }

        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);

    }
}