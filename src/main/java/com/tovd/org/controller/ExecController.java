package com.tovd.org.controller;

import com.tovd.org.mapper.DomainMapper;
import com.tovd.org.service.domain.DomainInsert;
import com.tovd.org.service.doReturn;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exec")
@Mapper
public class ExecController {

    @Autowired
    doReturn doreturn;

    @Autowired
    DomainMapper domainMapper;

    @Autowired
    DomainInsert doinsert;

//    @GetMapping("/tyc/do")
//    public Map<String,Object> doTyc(@RequestParam String companyName) throws Exception {
//        companyName=URLEncoder.encode(companyName,"utf-8");
//        doreturn.doTycRetuen(companyName);
//        return null;
//    }


}
