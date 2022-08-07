package com.tovd.org.service.email;

import com.tovd.org.entity.Email;
import com.tovd.org.mapper.EmailMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
@Service
public class EmailInsert {

    @Autowired
    EmailMapper emailMapper;
    @Async
    public void insert(String email,String subjectName){
        PhoneBook phoneBook=new PhoneBook();
        try {
            String id=phoneBook.getId(email);
            Map map= phoneBook.getEmail(id,email);

            List<Email> emailList=new ArrayList<>();
            List listEmail= (List) map.get("emailAll");
            String emailFrom=map.get("emailFrom").toString();
            String emailName=map.get("emailName").toString();

            for (int i=0;i<listEmail.size();i++){
                Email email1=new Email();
                email1.setEmailAll(listEmail.get(i).toString());
                email1.setEmailName(emailName);
                email1.setEmailFrom(emailFrom);
                email1.setSubjectName(subjectName);
                emailList.add(email1);
            }
            emailMapper.insertEmail(emailList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
