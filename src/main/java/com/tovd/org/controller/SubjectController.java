package com.tovd.org.controller;

import com.tovd.org.entity.Company;
import com.tovd.org.entity.Dir;
import com.tovd.org.entity.Domain;
import com.tovd.org.entity.Email;
import com.tovd.org.mapper.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.hssf.usermodel.*;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/subject")
@Mapper
public class SubjectController {
    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    DomainMapper domainMapper;

    @Autowired
    DirMapper dirMapper;

    @Autowired
    EmailMapper emailMapper;

    @GetMapping()
    public Map<String,Object>findSubject(@RequestParam(required = false) String subjectName,@RequestParam(required = false) String subjectCode,@RequestParam(required = false)String subjectLabel){
        Map<String,Object> map=new HashMap<String,Object>();
        List list=new ArrayList();
        if(subjectName!=null&&subjectCode!=null){
            subjectName="%"+subjectName+"%";
            list= subjectMapper.selectSubject(subjectName,subjectCode);
        }else if(subjectName!=null){
            subjectName="%"+subjectName+"%";
            list=subjectMapper.selectSubjectName(subjectName);
        }else if(subjectCode!=null){
            list=subjectMapper.selectSubjectCode(subjectCode);
        }
        map.put("name",list);
        return map;
    }
    @GetMapping("/page")
    public Map<String,Object>findPage(@RequestParam int pageNum,@RequestParam int pageSize){
        pageNum=(pageNum-1)*pageSize;
        Map map =new HashMap();
        int total=subjectMapper.selectId();
        map.put("total",total);
        map.put("data",subjectMapper.findAll(pageNum,pageSize));
        return map;
    }

    @GetMapping("/name")
    public Map<String,Object>findSubjectName(){
        Map map =new HashMap();
        map.put("data",subjectMapper.selectSubjectNameAll());
        return map;
    }
    @GetMapping("/all")
    public Map<String,Object>findSubjectALL(){
        Map map =new HashMap();
        map.put("data",subjectMapper.selectSubjectAll());
        return map;
    }

    @GetMapping("/save")
    public int saveSubject(@RequestParam String subjectName,@RequestParam String subjectCode,@RequestParam(required = false) String subjectLabel){
       return subjectMapper.saveSubject(subjectName,subjectCode,subjectLabel);
    }

    @GetMapping("/update")
    public int updateSubject(@RequestParam String subjectName,@RequestParam String subjectCode,@RequestParam(required = false) String subjectLabel){
        return subjectMapper.updateSubject(subjectName,subjectCode,subjectLabel);
    }

    /*依据项目删除所有*/
    @GetMapping("/delete")
    public int deleteSubject(@RequestParam int subjectId){
        String subjectName= subjectMapper.selectSubjectById(subjectId);
        subjectMapper.delSubject(subjectId);
        companyMapper.delSubject(subjectName);
        domainMapper.delSubject(subjectName);
        emailMapper.delSubject(subjectName);
        return 200;
    }


    @GetMapping("/export")
    public void subjectExport(@RequestParam String subjectName, HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet= workbook.createSheet("域名信息");
        HSSFSheet sheet1= workbook.createSheet("天眼查");
        HSSFSheet sheet2= workbook.createSheet("目录");
        HSSFSheet sheet3= workbook.createSheet("邮箱");
        List<Domain> list=domainMapper.selectAllDomain(subjectName);
        List<Company> list1=companyMapper.selectCompanyAll(subjectName);
        List<Dir> list2= dirMapper.selectDirAll(subjectName);
        List<Email> list3=emailMapper.selectAllEmail(subjectName);



        String fileName=subjectName+".xls";
        int rowNum=1;
        int rowNum1=1;
        int rowNum3=1;
        int rowNum5=1;
        String[] headers={"subjectName","domainTime","domainName","domainHost","domainCode","domainTitle","domainFrom","domainCdn","domainServer","domainFinger"};

        String[] headers1={"companyId","companyName","subjectName","companyTime","companyDomain","companyChildName","companyWechatId","companyWechatName","companyWeibo","companySupply"};
        String[] headers2={"subjectName","dirTime","dirOwner","dirUrl","dirPath","dirCode"};
        String[] headers3={"subjectName","emailTime","emailName","emailFrom","emailAll"};


        HSSFRow row= sheet.createRow(0);
        HSSFRow row2= sheet1.createRow(0);
        HSSFRow row4=sheet2.createRow(0);
        HSSFRow row6=sheet3.createRow(0);
        for (int i=0;i<headers.length;i++){
            HSSFCell cell= row.createCell(i);
            HSSFRichTextString textString=new HSSFRichTextString(headers[i]);
            cell.setCellValue(textString);
        }
        for (int i=0;i<headers1.length;i++){
            HSSFCell cell= row2.createCell(i);
            HSSFRichTextString textString=new HSSFRichTextString(headers1[i]);
            cell.setCellValue(textString);
        }

        for (int i=0;i<headers2.length;i++){
            HSSFCell cell= row4.createCell(i);
            HSSFRichTextString textString=new HSSFRichTextString(headers2[i]);
            cell.setCellValue(textString);
        }
        for (int i=0;i<headers3.length;i++){
            HSSFCell cell= row6.createCell(i);
            HSSFRichTextString textString=new HSSFRichTextString(headers2[i]);
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
        for (Company company: list1){
            HSSFRow row3=sheet1.createRow(rowNum1);
            row3.createCell(0).setCellValue(company.getCompanyId());
            row3.createCell(1).setCellValue(company.getCompanyName());
            row3.createCell(2).setCellValue(company.getSubjectName());
            row3.createCell(3).setCellValue(company.getCompanyTime());
            row3.createCell(4).setCellValue(company.getCompanyDomain());
            row3.createCell(5).setCellValue(company.getCompanyChildName());
            row3.createCell(6).setCellValue(company.getCompanyWechatId());
            row3.createCell(7).setCellValue(company.getCompanyWechatName());
            row3.createCell(8).setCellValue(company.getCompanyWeibo());
            row3.createCell(9).setCellValue(company.getCompanySupply());
            rowNum1++;
        }

        for (Dir dir: list2){
            HSSFRow row5=sheet2.createRow(rowNum3);
            row5.createCell(0).setCellValue(dir.getSubjectName());
            row5.createCell(1).setCellValue(dir.getDirTime());
            row5.createCell(2).setCellValue(dir.getDirOwner());
            row5.createCell(3).setCellValue(dir.getDirUrl());
            row5.createCell(4).setCellValue(dir.getDirPath());
            row5.createCell(5).setCellValue(dir.getDirCode());
            rowNum3++;
        }
        for (Email email: list3){
            HSSFRow row5=sheet3.createRow(rowNum5);
            row5.createCell(0).setCellValue(email.getSubjectName());
            row5.createCell(1).setCellValue(email.getEmailTime());
            row5.createCell(2).setCellValue(email.getEmailName());
            row5.createCell(3).setCellValue(email.getEmailFrom());
            row5.createCell(4).setCellValue(email.getEmailAll());
            rowNum5++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }

}
