package com.tovd.org.service.github;

import com.alibaba.fastjson.JSON;
import com.tovd.org.utils.httpClientDemo;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tovd.org.utils.httpClientDemo.httpClient;

public class Git {


    /*
    * 根据项目名称搜索  in:name 360
    * 根据描述进行搜索  in:description 360
    * 根据内容readme.md in:readme 360
    * 根据path进行搜索   in:path 360
    *
    *
    *
    *
    *
    *
    *
    * */
    public Map gitMap(String search,String pageNum) throws IOException {

        Map map=new HashMap();
        if (pageNum==null){
            pageNum="1";
        }
        String  url="https://github.com/search?p="+pageNum+"&q=in%3Apath+"+search+"&type=Code";

        System.out.println(url);
        List listPath=gitPath(url);
        map=gitOwner(listPath);
        map.put("gitPath",listPath);
        List listFileName= gitFileName(listPath);
        map.put("gitFileName",listFileName);
        return map;
    }




    public Map gitOwner(List list){


        httpClientDemo httpClientDemo=new httpClientDemo();
        Map mapOther=new HashMap();
        List listRepo=new ArrayList();
        List listOwner=new ArrayList();
        List listDesc=new ArrayList();
        List listFileTime=new ArrayList();

        for (int i=0;i<list.size();i++){
           String path= list.get(i).toString();
           Pattern patternOwner=Pattern.compile("https://github.com/(\\S*)/blob");
           Matcher matcherOwner=patternOwner.matcher(path);
           while (matcherOwner.find()){
               listRepo.add(matcherOwner.group(1));
           }
        }
        for (int i=0;i<listRepo.size();i++){
            try {
                String url="https://api.github.com/repos/"+listRepo.get(i).toString();
                HttpGet httpGet=new HttpGet(url);
                httpGet.setHeader("Authorization","token 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");
                httpGet.setHeader("Cookie","user_session=58Xg7KFU2kN3LqRW5_Nrq-sdXmsj2wex_yHwLQs1QI4tiPXc;");
                HttpResponse response=  httpClient.execute(httpGet);
                String results= EntityUtils.toString(response.getEntity(),"utf-8");
                Map map= JSON.parseObject(results);
                listOwner.add(((Map)(map.get("owner"))).get("login").toString());
                try {
                    listDesc.add(map.get("description").toString());
                }catch (NullPointerException nullPointerException){
                    listDesc.add("null");
                }
                listFileTime.add(map.get("updated_at").toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        mapOther.put("gitOwner",listOwner);
        mapOther.put("gitDesc",listDesc);
        mapOther.put("gitFileTime",listFileTime);


        return mapOther;

    }

    public List gitFileName(List list){
        List list1=new ArrayList();
        for (int i=0;i<list.size();i++){
            String temp=list.get(i).toString();
            String[] array=temp.split("/");
            list1.add(array[array.length-1]);
        }
        return list1;
    }


    public void gitDomain(String url) throws IOException {


        httpClientDemo httpClientDemo=new httpClientDemo();
        HttpGet httpGet=new HttpGet(url);
        httpGet.setHeader("Authorization","token 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");
        HttpResponse response=  httpClient.execute(httpGet);
        System.out.println(EntityUtils.toString(response.getEntity(),"utf-8"));


    }

    public void gitRepository(String url) throws IOException, InterruptedException {

        httpClientDemo httpClientDemo=new httpClientDemo();
        HttpGet httpGet=new HttpGet(url);
        httpGet.setHeader("Authorization","token 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");
        HttpResponse response=  httpClient.execute(httpGet);

        String results= EntityUtils.toString(response.getEntity(),"utf-8");
        String pageNum="";

        Pattern pattern=Pattern.compile("<a class=\"Link--muted\" href=\"(\\S*)/stargazers");
//        Pattern patternPage=Pattern.compile("<a aria-label=\"Page (\\S*)\"");
//
//        Matcher matcherPage=patternPage.matcher(results);
//        while (matcherPage.find()){
//            pageNum=matcherPage.group(1);
//        }

        List listResults=new ArrayList();
        for (int i=1;i<=4;i++) {
            url = "https://github.com/search?p=" + i + "&q=in%3Aname+360&type=Repositories";
            System.out.println(url);
            HttpGet httpGet1 = new HttpGet(url);
            httpGet.setHeader("Authorization", "token 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");
            HttpResponse response1 = httpClient.execute(httpGet1);
            System.out.println(response1.getStatusLine().getStatusCode());
            String results1 = EntityUtils.toString(response1.getEntity(), "utf-8");
//            System.out.println(results1);
            Matcher matcher = pattern.matcher(results1);
            while (matcher.find()) {
                listResults.add(matcher.group(1));
            }
        }
    }


    public List gitPath(String url) throws IOException {

        List listPath=new ArrayList();
        httpClientDemo httpClientDemo=new httpClientDemo();
        HttpGet httpGet=new HttpGet(url);
        httpGet.setHeader("Authorization","token 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");
        httpGet.setHeader("Cookie","user_session=58Xg7KFU2kN3LqRW5_Nrq-sdXmsj2wex_yHwLQs1QI4tiPXc;");
        HttpResponse response=  httpClient.execute(httpGet);

        String results= EntityUtils.toString(response.getEntity(),"utf-8");
        String temp="";
//        Pattern patternPath=Pattern.compile("<a title=\"(\\S*)");
//        Matcher matcherPath=patternPath.matcher(results);
//        while (matcherPath.find()){
//            System.out.println(matcherPath.group(1));
//        }

        Pattern patternOwner=Pattern.compile("&quot;,&quot;model_name&quot;:&quot;Repository&quot;,&quot;url&quot;:&quot;(\\S*)&quot;}");
        Matcher matcherOwner=patternOwner.matcher(results);

        while (matcherOwner.find()){
            temp=matcherOwner.group(1);
            listPath.add(temp);
        }
        return listPath;
    }


    public static void main(String args[]) throws IOException, InterruptedException {
        Git git=new Git();


    }



}
