package com.tovd.org.service.email;


import com.tovd.org.mapper.EmailMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Service
public class EmailFind {
    @Autowired
    EmailMapper emailMapper;
    public boolean getEmail(String email){

        String[] num=emailMapper.selectEmail(email);
        if (num.length!=0){
            return true;
        }else {
            return false;
        }
    }
}
