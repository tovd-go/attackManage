package com.tovd.org.service.email;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tovd.org.utils.httpClientDemo.httpClient;

public class PhoneBook {

    public String getId(String email) throws IOException {
        String url="https://public.intelx.io/phonebook/search?k=077424c6-7a26-410e-9269-c9ac546886a4";
        String parm="{\"term\":\""+email+"\",\"maxresults\":10000,\"media\":0,\"target\":2,\"terminate\":[null],\"timeout\":20}";
        System.out.println(parm);
        HttpPost httpPost=new HttpPost(url);

        httpPost.addHeader("Referer","https://phonebook.cz/");
        httpPost.addHeader("Accept", "* / *");
        httpPost.addHeader("Sec-Fetch-Mode","cors");
        httpPost.addHeader("Content-Type", "application / json");

        StringEntity stringEntity=new StringEntity(parm);
        httpPost.setEntity(stringEntity);

        HttpResponse httpResponse= httpClient.execute(httpPost);
        System.out.println(httpResponse.getStatusLine().getStatusCode());
        String id= EntityUtils.toString(httpResponse.getEntity(),"utf-8");
      //  System.out.println(id);
        Map map= JSON.parseObject(id);
        return map.get("id").toString();
    }

    public Map getEmail(String id,String email) throws IOException {
         String url="https://public.intelx.io/phonebook/search/result?k=077424c6-7a26-410e-9269-c9ac546886a4&id="+id+"&limit=10000";
         HttpGet httpGet=new HttpGet(url);
         httpGet.addHeader("Referer","https://phonebook.cz/");
         httpGet.addHeader("Accept", "* / *");
         httpGet.addHeader("Sec-Fetch-Mode","cors");
         HttpResponse httpResponse= httpClient.execute(httpGet);
         String result= EntityUtils.toString(httpResponse.getEntity(),"utf-8");
         Map map= JSON.parseObject(result);
         Map mapResult=new HashMap();
         List list= (List) map.get("selectors");
         List listEmail=new ArrayList();
         for (int i=0;i<list.size();i++){
             Map map1= (Map) list.get(i);
             String emails=map1.get("selectorvalue").toString();
             listEmail.add(emails);

         }
         mapResult.put("emailName",email);
         mapResult.put("emailFrom","PhoneBook");
         mapResult.put("emailAll",listEmail);
         return mapResult;
    }

    public static void main(String args[]) throws IOException {
        PhoneBook phoneBook=new PhoneBook();
        String id= phoneBook.getId("360.cn");
         Map map=  phoneBook.getEmail(id,"360.cn");
        List listEmail= (List) map.get("emailAll");
        System.out.println(listEmail);
    }
}
