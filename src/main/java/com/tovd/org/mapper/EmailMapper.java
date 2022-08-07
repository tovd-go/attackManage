package com.tovd.org.mapper;

import com.tovd.org.entity.Domain;
import com.tovd.org.entity.Email;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface EmailMapper {

    int insertEmail(List<Email> emailList);

    @Select("select emailName from sys_email where emailName=#{email}")
    String[] selectEmail(String email);

    Integer loadId(Email email);

    List<Email> load(Email email);

    int delEmail(Email email);

    @Select("select * from sys_email where subjectName=#{subjectName}")
    List<Email> selectAllEmail(String subjectName);

    @Delete("delete from sys_email where subjectName = #{subjectName}")
    int delSubject(String subjectName);
}
