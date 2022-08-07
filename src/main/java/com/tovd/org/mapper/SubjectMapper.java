package com.tovd.org.mapper;

import com.tovd.org.entity.Subject;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface SubjectMapper {

    @Select("select * from sys_subject limit #{pageNum},#{pageSize}")
    List<Subject> findAll(int pageNum,int pageSize);

    @Select("select count(*) from sys_subject")
    int selectId();

    @Select("select * from sys_subject where subjectName like #{subjectName} and subjectCode=#{subjectCode}")
    List<Subject> selectSubject(String subjectName,String subjectCode);

    @Select("select * from sys_subject")
    List<Subject> selectSubjectAll();

    @Select("select * from sys_subject where subjectName like #{subjectName}")
    List<Subject> selectSubjectName(String subjectName);

    @Select("select subjectName from sys_subject where subjectId =#{subjectId}" )
    String selectSubjectById(int subjectId);

    @Select("select subjectName from sys_subject")
    List<Subject> selectSubjectNameAll();

    @Select("select * from sys_subject where subjectCode = #{subjectCode}")
    List<Subject> selectSubjectCode(String subjectCode);

    @Select("select * from sys_subject where subjectLabel = #{subjectLabel}")
    List<Subject> selectSubjectLabel(String subjectLabel);

    @Insert("insert into sys_subject (subjectName,subjectCode,subjectLabel) values (#{subjectName},#{subjectCode},#{subjectLabel})")
    int saveSubject(String subjectName,String subjectCode, String subjectLabel);

    @Update("update sys_subject set subjectName=#{subjectName},subjectCode=#{subjectCode},subjectLabel=#{subjectLabel}")
    int updateSubject(String subjectName,String subjectCode, String subjectLabel);

    @Delete("delete from sys_subject where subjectId = #{subjectId}")
    int delSubject(int subjectId);

}
