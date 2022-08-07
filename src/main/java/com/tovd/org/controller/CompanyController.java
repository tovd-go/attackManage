package com.tovd.org.controller;


import com.tovd.org.entity.Company;
import com.tovd.org.mapper.CompanyMapper;
import com.tovd.org.service.tyc.DoTycInsert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/company")
@Mapper
public class CompanyController {


    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    DoTycInsert doTycInsert;

    @GetMapping("/add")
    public int addCompany (@RequestParam String companyName,@RequestParam String subjectName){
        return companyMapper.addCompany(companyName,subjectName);
    }
    @GetMapping("/all")
    public Map<String,Object> findCompanyALL(@RequestParam String subjectName){
        Map map=new HashMap();
        map.put("data",companyMapper.selectCompanyAll(subjectName));
        return map;
    }

    @GetMapping("/get")
    public Map<String,Object> findConpanyName(@RequestParam String companyName){
        Map map=new HashMap();
        map.put("data",companyMapper.selectCompany(companyName));
        return map;
    }
    @GetMapping("/delete")
    public int deleteCompany(@RequestParam int id){
        return companyMapper.delCompany(id);
    }

    @GetMapping("/insert")
    public void insertAll(@RequestParam String subjectName,@RequestParam String companyName){

        doTycInsert.insertTyc(subjectName,companyName);
    }


    @GetMapping("/find")
    public Map<String,Object>findPage(@RequestParam(required = false) String subjectName, @RequestParam(required = false)String companyName,
                                      @RequestParam int pageNum,@RequestParam int pageSize){
        Company company=new Company();
        pageNum=(pageNum-1)*pageSize;
        if (subjectName!=""||subjectName!=null){
            company.setSubjectName(subjectName);
        }else {
            subjectName=null;
        }
        if (companyName!=""||companyName!=null){
            company.setCompanyName(companyName);
        }else {
            companyName=null;
        }

        company.setPageNum(pageNum);
        company.setPageSize(pageSize);

        Map map =new HashMap();
        int total=companyMapper.selectId();
        map.put("total",total);
        map.put("data",companyMapper.findAll(company));
        return map;
    }
}
