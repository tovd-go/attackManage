package com.tovd.org.service.domain;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tovd.org.service.domain.CdnFilter.cdns;
import static com.tovd.org.service.domain.CdnFilter.isInRange;

public class DomainFilter {
    public Map doFilter(String url) throws Exception {
        Map map=new HashMap();
        Map mapSecurityTrails=new HashMap();
        List listSecurityTrails=new ArrayList();
        SecurityTrails securityTrails=new SecurityTrails();
        mapSecurityTrails=securityTrails.getDomain(url);
        if (mapSecurityTrails.get("securityTrails").equals("false")){
            Fofa fofa=new Fofa();
            Map mapFofa=new HashMap();
            List listFofa=new ArrayList();
            mapFofa=fofa.getFofa(url);
            listFofa= (List) mapFofa.get("fofa");
            listFofa=Remove_duplicate(listFofa);
            map.put("fofa",getIp(listFofa));
            map.put("securityTrails","false");
            return map;

        }else {
            listSecurityTrails=(List) mapSecurityTrails.get("securityTrails");
            listSecurityTrails=Remove_duplicate(listSecurityTrails);

            Fofa fofa=new Fofa();
            Map mapFofa=new HashMap();
            List listFofa=new ArrayList();
            mapFofa=fofa.getFofa(url);
            listFofa= (List) mapFofa.get("fofa");
            listFofa=Remove_duplicate(listFofa);


            List listAll=new ArrayList();
            listAll=getCommon(listFofa,listSecurityTrails);  //获取list中相同的
            listSecurityTrails=getlist(listSecurityTrails,listAll); //去掉上面相同的
            listFofa=getlist(listFofa,listAll); //去掉上面相同的


            map.put("securityTrails",getIp(listSecurityTrails));
            map.put("fofa",getIp(listFofa));
            map.put("fofa_securityTrails",getIp(listAll));
            return map;
        }

    }


    public List Remove_duplicate(List list){
        List listTemp=new ArrayList();
        for(int i=0;i<list.size();i++){
            if (!listTemp.contains(list.get(i))){
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }

    public List getCommon(List list1,List list2){
        List result =new ArrayList();
        for (int i=0;i<list2.size();i++){
            if (list1.contains(list2.get(i))){
                result.add(list2.get(i));
            }

        }
        return result;
    }
    public List getlist(List list1,List list2){
        List result =new ArrayList();
        for (int i=0;i<list1.size();i++){
            if (!(list2.contains(list1.get(i)))){
                result.add(list1.get(i));
            }
        }
        return result;
    }


    public Map getIp(List list){


        Map map=new HashMap();
        List list1=new ArrayList();
        List list2=new ArrayList();
        List list3=new ArrayList();
        String domain="";
        String ip="";

            for (int i=0;i<list.size();i++){
                boolean flag=false;
                try {
                    InetAddress address=InetAddress.getByName(list.get(i).toString());
                    domain=list.get(i).toString();
                    list1.add(domain);
                    ip=address.getHostAddress().toString();
                    list2.add(ip);
                    for (int j=0;j<cdns.length;j++) {
                        if (isInRange(ip, cdns[i])) {
                            flag=true;
                            break;
                        }
                    }
                    if (flag){
                        list3.add("true");
                    }else {
                        list3.add("false");
                    }

                }catch (Exception e){
                }
            }
        map.put("domain",list1);
        map.put("ip",list2);
        map.put("cdn",list3);
        return map;
    }

//    public static void main(String args[]) throws Exception {
//        domainFilter domainFilter=new domainFilter();
//        domainFilter.doFilter("www.baidu.com");
//    }
}
