package com.tovd.org.service.tyc;


import com.tovd.org.entity.Company;
import com.tovd.org.mapper.CompanyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
@RestController
@Mapper
@Transactional
public class DoTycInsert {

    @Autowired
    CompanyMapper companyMapper;

    @Async
    public void insertTyc(String subjectName,String companyName){

        Map map=new HashMap();
        try {
            Tyc tyc=new Tyc();
            companyName= URLEncoder.encode(companyName,"utf-8");
            map=tyc.Father_company(companyName);
            Company company=new Company();
            String companySupply=map.get("companySupply").toString();
            String companyChildName=map.get("companyChildName").toString();
            String companyId=map.get("companyId").toString();
            String companyWechatId=map.get("companyWechatId").toString();
            String companyWechatName=map.get("companyWechatName").toString();
            String companyHost=map.get("companyHost").toString();
            String companyDomain=map.get("companyDomain").toString();
            String companyWeibo=map.get("companyWeibo").toString();


            companyName= URLDecoder.decode(companyName,"utf-8");
            company.setSubjectName(subjectName);
            company.setCompanyName(companyName);
            company.setCompanySupply(companySupply);
            company.setCompanyChildName(companyChildName);
            company.setCompanyId(companyId);
            company.setCompanyWechatId(companyWechatId);
            company.setCompanyWechatName(companyWechatName);
            company.setCompanyHost(companyHost);
            company.setCompanyDomain(companyDomain);
            company.setCompanyWeibo(companyWeibo);
            if (companyMapper.selectCompanyId(companyId)!=null){
                companyMapper.updateall(company);
            }else {
                companyMapper.insertAll(company);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
