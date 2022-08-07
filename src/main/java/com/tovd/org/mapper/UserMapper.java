package com.tovd.org.mapper;

import com.tovd.org.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Transactional
public interface UserMapper {


    @Select("select * from sys_user where username =#{username}")
    String getPassword(String username);

    User login(User user);
}
