package com.tovd.org.mapper;

import com.tovd.org.entity.Company;
import com.tovd.org.entity.Subject;
import org.apache.ibatis.annotations.*;
import org.springframework.jdbc.core.metadata.CallParameterMetaData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Mapper
@Transactional
public interface CompanyMapper {

    int insertAll(Company company);


    @Select("select companyId from sys_company where companyId=#{companyId}")
    String selectCompanyId(String companyId);

    @Insert("insert into sys_company (companyName,subjectName) values (#{companyName},#{subjectName})")
    int addCompany(String companyName,String subjectName);

    @Select("select * from sys_company where subjectName = #{subjectName}")
    List<Company> selectCompanyAll(String subjectName);

    int updateall(Company company);


    @Select("select * from sys_company where companyName=#{companyName}")
    List<Company> selectCompany(String companyName);

    @Select("select count(*) from sys_company")
    int selectId();

    List<Company> findAll(Company company);

    @Delete("delete from sys_company where id = #{id}")
    int delCompany(int id);

    @Delete("delete from sys_company where subjectName = #{subjectName}")
    int delSubject(String subjectName);
}
