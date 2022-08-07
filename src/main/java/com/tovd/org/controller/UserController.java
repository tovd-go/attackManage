package com.tovd.org.controller;


import com.tovd.org.entity.User;
import com.tovd.org.service.UserService;
import com.tovd.org.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public Map<String,Object> login(User user){

        Map<String, Object> map = new HashMap<>();

        try {
            User userDB = userService.login(user);
            Map<String,String> payload = new HashMap<>();
            payload.put("id",userDB.getId().toString());
            payload.put("username",userDB.getUsername());
            //生成JWT令牌
            String token = JwtUtils.getToken(payload);
            map.put("state",true);
            map.put("token",token);
            map.put("msg","认证成功");
            map.put("code",200);
        }catch (Exception e){
            map.put("state",false);
            map.put("msg",e.getMessage());
        }
        return map;
    }

}
