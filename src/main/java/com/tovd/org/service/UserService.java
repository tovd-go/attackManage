package com.tovd.org.service;

import com.tovd.org.entity.User;
import com.tovd.org.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User login(User user){
        User userDB=userMapper.login(user);
        if (userDB!=null){
            return userDB;
        }
        throw new RuntimeException("登录失败");
    }

}
