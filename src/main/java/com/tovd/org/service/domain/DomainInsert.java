package com.tovd.org.service.domain;

import com.tovd.org.mapper.DomainMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RestController
@Mapper
@Transactional
public class DomainInsert {
    @Autowired
    DomainMapper domainMapper;

    @Async
    /*没有的才插进去*/
    public void insertFofa(String subjectName,String domain) throws Exception {
        Map map=new HashMap();
        Map mapFlag=new HashMap();
        List listAll=new ArrayList();
        DomainFilter filter=new DomainFilter();
        map=filter.doFilter(domain);

        if (map.get("securityTrails").equals("false")){
            listAll.add("fofa");
        }else {
            listAll.add("fofa");
            listAll.add("securityTrails");
            listAll.add("fofa_securityTrails");
        }
        Map mapCode=new HashMap();
        FingerPrint fingerprint=new FingerPrint();
//        List listDomainName= domainMapper.selectFofa();

        for (int i=0;i<listAll.size();i++){
                Map map1= (Map) map.get(listAll.get(i).toString());
                List list= (List) map1.get("domain");
                List listip= (List) map1.get("ip");
                List listCdn= (List) map1.get("cdn");
                for (int j=0;j<list.size();j++) {
                    try {
                        mapCode = fingerprint.httpGetBanner(list.get(j).toString());
//                        if (!listDomainName.contains(list.get(j))) {
                        domainMapper.insertFofa(domain,mapCode.get("fingerprint").toString(), mapCode.get("Server").toString(), listCdn.get(j).toString(), subjectName, list.get(j).toString(), listip.get(j).toString(), mapCode.get("code").toString(), mapCode.get("title").toString(), listAll.get(i).toString());
//                        } else {
//                            Integer id = domainMapper.selectDomainId(list.get(j).toString());
//                            domainMapper.updataFofa(mapCode.get("fingerprint").toString(), mapCode.get("Server").toString(), listCdn.get(j).toString(), list.get(j).toString(), listip.get(j).toString(), mapCode.get("code").toString(), mapCode.get("title").toString(), listAll.get(i).toString(), id);
//                        }
                    }catch (Exception e){
                    }
                }
            }


    }
}
