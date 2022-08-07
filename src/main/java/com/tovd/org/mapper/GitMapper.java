package com.tovd.org.mapper;


import com.tovd.org.entity.Git;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Mapper
@Transactional
public interface GitMapper {


    @Insert("insert into sys_git(gitPath) values (#{gitPath})")
    Integer gitPathInsert(String gitPath);

    @Insert("insert into sys_git(subjectName,gitPath,gitDesc,gitFileTime,gitOwner,gitFileName) values (#{subjectName},#{gitPath},#{gitDesc},#{gitFileTime},#{gitOwner},#{gitFileName})")
    Integer gitInsert(String subjectName,String gitPath,String gitDesc,String gitFileTime,String gitOwner,String gitFileName );


    @Update("update sys_git set subjectName=#{subjectName},gitPath=#{gitPath},gitDesc=#{gitDesc},gitFileTime=#{gitFileTime}, gitOwner=#{gitOwner},gitFileName=#{gitFileName} where id=#{id}")
    Integer gitUpdate(String subjectName,String gitPath,String gitDesc,String gitFileTime,String gitOwner,String gitFileName,Integer id);

//    @Update("update sys_git set gitPath=#{gitPath} where id={id}")
//    Integer gittest(String gitPath,Integer id);

    @Select("select * from sys_git")
    List<Git> getAll();


    @Select("select id from sys_git where gitPath=#{gitPath}")
    Integer selectGitId(String gitPath);

    @Select("select gitPath from sys_git")
    List<String> getPath();

    Integer findId();

    List<Git> findPage(Git git);
}
