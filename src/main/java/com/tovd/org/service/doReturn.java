package com.tovd.org.service;

import com.tovd.org.mapper.CompanyMapper;
import com.tovd.org.service.tyc.Tyc;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Service
@RestController
@Mapper
public class doReturn {

    @Autowired
    CompanyMapper companyMapper;

    @Async
    public void doTycRetuen(String companyName) throws Exception {
//        Map map=new HashMap();
//        Tyc tyc=new Tyc();
//        map=tyc.Father_company(companyName);
//        map.get("companyHost");
//        map.get("companyDomain");
//        map.get("companyId");
//        companyName=URLDecoder.decode(companyName,"utf-8");
//        companyMapper.updateCompanyall(map.get("companyId").toString(),map.get("companyHost").toString(),map.get("companyDomain").toString(),map.get("companyChildName").toString(),companyName);

    }
}
