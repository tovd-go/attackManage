package com.tovd.org.controller;

import com.tovd.org.entity.Domain;
import com.tovd.org.mapper.DirMapper;
import com.tovd.org.mapper.DomainMapper;
import com.tovd.org.service.domain.DomainInsert;
import com.tovd.org.service.doReturn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/domain")
@Mapper
public class DomainController {


    @Autowired
    doReturn doreturn;

    @Autowired
    DomainMapper domainMapper;

    @Autowired
    DomainInsert doinsert;

    @Autowired
    DirMapper dirMapper;


    @GetMapping("/export")
    public void exportDomain(@RequestParam (required = false )String subjectName,HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet= workbook.createSheet();
        List<Domain> list=domainMapper.selectAllDomain(subjectName);
        String fileName=subjectName+".xls";
        int rowNum=1;

        String[] headers={"subjectName","domainTime","domainName","domainHost","domainCode","domainTitle","domainFrom","domainCdn","domainServer","domainFinger"};

        HSSFRow row= sheet.createRow(0);
        for (int i=0;i<headers.length;i++){
            HSSFCell cell= row.createCell(i);
            HSSFRichTextString textString=new HSSFRichTextString(headers[i]);
            cell.setCellValue(textString);
        }
        for (Domain domain: list){
            HSSFRow row1=sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(domain.getSubjectName());
            row1.createCell(1).setCellValue(domain.getDomainTime());
            row1.createCell(2).setCellValue(domain.getDomainName());
            row1.createCell(3).setCellValue(domain.getDomainHost());
            row1.createCell(4).setCellValue(domain.getDomainCode());
            row1.createCell(5).setCellValue(domain.getDomainTitle());
            row1.createCell(6).setCellValue(domain.getDomainFrom());
            row1.createCell(7).setCellValue(domain.getDomainCdn());
            row1.createCell(8).setCellValue(domain.getDomainServer());
            row1.createCell(9).setCellValue(domain.getDomainFinger());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }



    @GetMapping("/insert")
    public Map insertDomain(@RequestParam String subjectName, @RequestParam String domain) throws Exception {
        String[] test=domain.split("\n");
        Map map=new HashMap();
        for (int i=0;i<test.length;i++){
            String[] num=domainMapper.selectMain(test[i]);
            if (num.length!=0){

                map.put(test[i],"已存在");
            }else {
                domain=test[i];
                doinsert.insertFofa(subjectName,domain);
                map.put(test[i],"执行中");
            }
        }
        System.out.println(map);
        return map;
    }

    @GetMapping("/delete")
    public void deleteDomain(@RequestParam Integer id){
        domainMapper.delDomain(id);

    }

    @GetMapping("/del")
    public Map del(@RequestParam(required = false) String subjectName,@RequestParam(required = false) String domainName,
                   @RequestParam(required = false) String domainCode,@RequestParam(required = false) String domainFrom){

        Domain domain=new Domain();
        if(subjectName!=""&&subjectName!=null){
            domain.setSubjectName(subjectName);
        }else {
            subjectName=null;
        }

        if(domainName!=""&&domainName!=null){
            domain.setDomainName(domainName);
        }else {
            domainName=null;
        }
        if(domainCode!=""&&domainCode!=null)
        {
            domain.setDomainCode(domainCode);
        }else {
            domainCode=null;
        }
        if(domainFrom!=""&&domainFrom!=null){
            domain.setDomainFrom(domainFrom);
        }else {
            domainFrom=null;
        }
        if (subjectName==null&&domainName==null&&domainCode==null&&domainFrom==null){
            Map map=new HashMap();
            map.put("code","201");
            return map;
        }else {
            domainMapper.del(domain);
            Map map=new HashMap();
            map.put("code","200");
            return map;
        }
    }


    @GetMapping("/find")
    public Map<String,Object>find(@RequestParam(required = false) String subjectName,@RequestParam(required = false) String domainName,
                                  @RequestParam(required = false) String domainCode,@RequestParam(required = false) String domainFrom,
                                  @RequestParam (required = false) Integer pageNum,@RequestParam (required = false) Integer pageSize){
        Map map =new HashMap();
        pageNum=(pageNum-1)*pageSize;
        Domain domain=new Domain();
        if(subjectName!=""&&subjectName!=null){
            domain.setSubjectName(subjectName);
        }else {
            subjectName=null;
        }

        if(domainName!=""&&domainName!=null){
            domain.setDomainName(domainName);
        }else {
            domainName=null;
        }
        if(domainCode!=""&&domainCode!=null)
        {
            domain.setDomainCode(domainCode);
        }else {
            domainCode=null;
        }
        if(domainFrom!=""&&domainFrom!=null){
            domain.setDomainFrom(domainFrom);
        }else {
            domainFrom=null;
        }

        domain.setPageNum(pageNum);
        domain.setPageSize(pageSize);

        map.put("total",domainMapper.findId(domain));
        map.put("data",domainMapper.findPage(domain));
        return map;
    }




}
