package com.tovd.org.service.github;

import com.tovd.org.mapper.GitMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RestController
@Mapper
public class GitInsert {

    @Autowired
    GitMapper gitMapper;

    @Async
    public void insertAll(String subjectName,String search,String pageNum) throws IOException {
        Git git=new Git();
        Map map=git.gitMap(search,pageNum);

        List listOldPath= gitMapper.getPath();

        List listPath= (List) map.get("gitPath");
        List listDesc=(List) map.get("gitDesc");
        List listFileTime=(List) map.get("gitFileTime");
        List listOwner=(List) map.get("gitOwner");
        List listFileName=(List) map.get("gitFileName");
        for (int i=0;i<listPath.size();i++){
            if (!listOldPath.contains(listPath.get(i).toString())){
                gitMapper.gitInsert(subjectName,listPath.get(i).toString(),listDesc.get(i).toString(),listFileTime.get(i).toString(),listOwner.get(i).toString(),listFileName.get(i).toString());
            }else {
                Integer id= gitMapper.selectGitId(listPath.get(i).toString());
                System.out.println(listPath.get(i).toString());
                gitMapper.gitUpdate(subjectName,listPath.get(i).toString(),listDesc.get(i).toString(),listFileTime.get(i).toString(),listOwner.get(i).toString(),listFileName.get(i).toString(),id);


            }
        }
    }
}
