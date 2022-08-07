package com.tovd.org.mapper;

import com.tovd.org.entity.Dir;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface DirMapper {

    int insert(List<Dir> dirList);


    @Select("select dirOwner from sys_dir")
    List selectOwner();

    List<Dir> findPage(Dir dir);

    Integer findId(Dir dir);

    @Select("select * from sys_dir where subjectName=#{subjectName}")
    List<Dir> selectDirAll(String subjectName);

    int del(Dir dir);
//    int update(List<Dir> dirList1);
}
