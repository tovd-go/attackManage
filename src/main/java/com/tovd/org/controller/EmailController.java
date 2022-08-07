package com.tovd.org.controller;


import com.tovd.org.entity.Email;
import com.tovd.org.mapper.EmailMapper;
import com.tovd.org.service.email.EmailFind;
import com.tovd.org.service.email.EmailInsert;
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
@RequestMapping("/email")
@Mapper
public class EmailController {

    @Autowired
    EmailInsert emailInsert;

    @Autowired
    EmailFind emailFind;

    @Autowired
    EmailMapper emailMapper;

    @GetMapping("/insert")
    public Map setEmail(@RequestParam String subjectName,@RequestParam String email){

        if (emailFind.getEmail(email)){
            Map map=new HashMap();
            map.put("code","200");
            map.put("message","已存在");
            return map;
        }else {
            emailInsert.insert(email,subjectName);
            Map map=new HashMap();
            map.put("code","201");
            map.put("message","查找中");
            return map;
        }
    }

    @GetMapping("/load")
    public Map load(@RequestParam(required = false) String subjectName,@RequestParam(required = false) String emailName,
                    @RequestParam(required = false) String emailFrom,@RequestParam(required = false) int pageSize,
                    @RequestParam(required = false) int pageNum){
        Map map =new HashMap();
        pageNum=(pageNum-1)*pageSize;
        Email email=new Email();

        if(subjectName!=""&&subjectName!=null){
            email.setSubjectName(subjectName);
        }else {
            subjectName=null;
        }

        if(emailName!=""&&emailName!=null){
            email.setEmailName(emailName);
        }else {
            emailName=null;
        }
        if(emailFrom!=""&&emailFrom!=null)
        {
            email.setEmailFrom(emailFrom);
        }else {
            emailFrom=null;
        }
        email.setPageNum(pageNum);
        email.setPageSize(pageSize);

        map.put("total",emailMapper.loadId(email));
        map.put("data",emailMapper.load(email));
        return map;

    }

    @GetMapping("/del")
    public Map del(@RequestParam(required = false) String subjectName,@RequestParam(required = false) String emailName,
                   @RequestParam(required = false) String emailFrom){

        Email email=new Email();

        if(subjectName!=""&&subjectName!=null){
            email.setSubjectName(subjectName);
        }else {
            subjectName=null;
        }

        if(emailName!=""&&emailName!=null){
            email.setEmailName(emailName);
        }else {
            emailName=null;
        }
        if(emailFrom!=""&&emailFrom!=null)
        {
            email.setEmailFrom(emailFrom);
        }else {
            emailFrom=null;
        }
        if (emailFrom==null&&emailName==null&&subjectName==null){
            Map map=new HashMap();
            map.put("code","201");
            return map;
        }else {
            int num=emailMapper.delEmail(email);
            Map map=new HashMap();
            map.put("code","200");
            return map;
        }
    }

    @GetMapping("/export")
    public void export(@RequestParam (required = false )String subjectName, HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet= workbook.createSheet();
        List<Email> list=emailMapper.selectAllEmail(subjectName);
        String fileName=subjectName+".xls";
        int rowNum=1;

        String[] headers={"subjectName","emailTime","emailName","emailFrom","emailAll"};

        HSSFRow row= sheet.createRow(0);
        for (int i=0;i<headers.length;i++){
            HSSFCell cell= row.createCell(i);
            HSSFRichTextString textString=new HSSFRichTextString(headers[i]);
            cell.setCellValue(textString);
        }
        for (Email email: list){
            HSSFRow row1=sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(email.getSubjectName());
            row1.createCell(1).setCellValue(email.getEmailTime());
            row1.createCell(2).setCellValue(email.getEmailName());
            row1.createCell(3).setCellValue(email.getEmailFrom());
            row1.createCell(4).setCellValue(email.getEmailAll());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }
}
