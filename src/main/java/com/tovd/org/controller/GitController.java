package com.tovd.org.controller;


import com.tovd.org.entity.Git;
import com.tovd.org.mapper.GitMapper;
import com.tovd.org.service.github.GitInsert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/git")
@Mapper
public class GitController {

    @Autowired
    GitInsert gitInsert;
    @Autowired
    GitMapper gitMapper;

    @GetMapping("/getAll")
    public Map getAll(){

        Map map=new HashMap();
        map.put("data",gitMapper.getAll());
        return map;
    }

    @GetMapping("/find")
    public Map findPage(@RequestParam(required = false) String subjectName,@RequestParam(required = false) String gitFileName,
                        @RequestParam (required = false) Integer pageNum,@RequestParam (required = false) Integer pageSize){

        Map map=new HashMap();
        pageNum=(pageNum-1)*pageSize;
        Git git=new Git();

        if (subjectName!=""|| subjectName!=null){
            git.setSubjectName(subjectName);
        }else {
            subjectName=null;
        }
        if (gitFileName!=""||gitFileName!=null){
            git.setGitFileName(gitFileName);
        }else {
            gitFileName=null;
        }

        git.setPageNum(pageNum);
        git.setPageSize(pageSize);

        map.put("total",gitMapper.findId());
        map.put("data",gitMapper.findPage(git));
        return map;

    }

    @GetMapping("/insert")
    public void insertPath( @RequestParam(required = false) String subjectName, @RequestParam(required = false) String search,@RequestParam(required = false) String pageNum) throws IOException {
        gitInsert.insertAll(subjectName,search,pageNum);
    }

}
