package com.tovd.org.controller;


import com.tovd.org.entity.Dir;
import com.tovd.org.mapper.DirMapper;
import com.tovd.org.service.dirsearch.RunMain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dir")
@Mapper
public class DirController {


    @Autowired
    DirMapper dirMapper;


    @GetMapping("/insert")
    public void dirInsert(@RequestParam String url, @RequestParam String subjectName) throws InterruptedException {

        Map map=new HashMap();
        List<Dir> dirList=new ArrayList<>();
        RunMain runMain=new RunMain();
        map=runMain.Run(url);

        List listPath=new ArrayList();
        List listUrl=new ArrayList();
        List listCode=new ArrayList();


        listPath=(List)map.get("dirPath");
        listUrl=(List)map.get("dirUrl");
        listCode=(List)map.get("dirCode");




        for (int i=0;i<listPath.size();i++){
            Dir dir=new Dir();
            dir.setDirOwner(url);
            dir.setDirPath(listPath.get(i).toString());
            dir.setDirUrl(listUrl.get(i).toString());
            dir.setDirCode(listCode.get(i).toString());
            dir.setSubjectName(subjectName);
            dirList.add(dir);

        }
        dirMapper.insert(dirList);
    }

    @GetMapping("/find")
    public Map dirGet(@RequestParam(required = false) String subjectName,@RequestParam(required = false) String dirOwner,
                      @RequestParam(required = false) String dirCode, @RequestParam (required = false) Integer pageNum,@RequestParam (required = false) Integer pageSize){
        Map map =new HashMap();
        pageNum=(pageNum-1)*pageSize;
        Dir dir=new Dir();
        if(subjectName!=""&&subjectName!=null){
            dir.setSubjectName(subjectName);
        }else {
            subjectName=null;
        }
        if(dirOwner!=""&&dirOwner!=null){
            dir.setDirOwner(dirOwner);
        }else {
            dirOwner=null;
        }
        if(dirCode!=""&&dirCode!=null)
        {
            dir.setDirCode(dirCode);
        }else {
            dirCode=null;
        }

        dir.setPageNum(pageNum);
        dir.setPageSize(pageSize);

        map.put("totalDir",dirMapper.findId(dir));
        map.put("data",dirMapper.findPage(dir));
        return map;
    }

    @GetMapping("/del")
    public Map dirDel(@RequestParam(required = false) String subjectName,@RequestParam(required = false) String dirOwner,
                      @RequestParam(required = false) String dirCode){
        Dir dir=new Dir();
        if(subjectName!=""&&subjectName!=null){
            dir.setSubjectName(subjectName);
        }else {
            subjectName=null;
        }
        if(dirOwner!=""&&dirOwner!=null){
            dir.setDirOwner(dirOwner);
        }else {
            dirOwner=null;
        }
        if(dirCode!=""&&dirCode!=null)
        {
            dir.setDirCode(dirCode);
        }else {
            dirCode=null;
        }
        if (subjectName==null&&dirOwner==null&&dirCode==null){
            Map map=new HashMap();
            map.put("code","201");
            return map;
        }else {
            dirMapper.del(dir);
            Map map=new HashMap();
            map.put("code","200");
            return map;
        }
    }

}
