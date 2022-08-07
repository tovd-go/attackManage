package com.tovd.org.mapper;

import com.tovd.org.entity.Domain;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Mapper
@Transactional
public interface DomainMapper {

    @Insert("insert into sys_domain (domainMain,domainFinger,domainServer,domainCdn,subjectName,domainName,domainHost,domainCode,domainTitle,domainFrom ) values (#{domainMain},#{fingerprint},#{Server},#{domainCdn},#{subjectName},#{domainName},#{domainHost},#{domainCode},#{domainTitle},#{domainFrom})")
    int insertFofa(String domainMain,String fingerprint,String Server,String domainCdn,String subjectName,String domainName,String domainHost,String domainCode,String domainTitle,String domainFrom);

    @Update("update sys_domain set domainFinger=#{fingerprint},domainServer=#{Server},domainCdn=#{domainCdn},domainName=#{domain},domainHost =#{host},domainCode=#{domainCode},domainTitle=#{domainTitle},domainFrom=#{domainFrom} where id=#{id}")
    int updataFofa(String fingerprint,String Server,String domainCdn,String domain,String host,String domainCode,String domainTitle,String domainFrom,Integer id);

    @Select("select id from sys_domain where domainName=#{domain}")
    Integer selectDomainId(String domain);

    @Select("select domainName from sys_domain")
    List<String> selectFofa();

    @Select("select count(*) from sys_domain")
    int selectId();

    @Select("select * from sys_domain")
    List<Domain> selectAll();

    @Select("select * from sys_domain where subjectName = #{subjectName}")
    List<Domain> selectAllDomain(String subjectName);

    @Select("select * from sys_domain limit #{pageNum},#{pageSize}")
    List<Domain>findAll(Integer pageNum, Integer pageSize);

    @Delete("delete from sys_domain where id = #{id}")
    Integer delDomain(Integer id);

    @Delete("delete from sys_domain where subjectName = #{subjectName}")
    int delSubject(String subjectName);

    List<Domain>find(Domain domain);

    List<Domain>findPage(Domain domain);

    Integer findId(Domain domain);

    @Select("select domainMain from sys_domain where domainMain =#{domain}")
    String[] selectMain(String domain);

    int del(Domain domain);
}
