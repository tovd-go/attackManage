package com.tovd.org.service.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.io.BaseEncoding;
import com.tovd.org.utils.httpClientDemo;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.annotations.Param;

import javax.net.ssl.SSLContext;
import java.io.*;

import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tovd.org.utils.httpClientDemo.httpClient;
import com.google.common.hash.Hashing;
import org.apache.logging.log4j.util.Base64Util;
import sun.misc.BASE64Encoder;

public class FingerPrint {


        static String[] User_agent = {
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/68.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:61.0) Gecko/20100101 Firefox/68.0",
            "Mozilla/5.0 (X11; Linux i586; rv:31.0) Gecko/20100101 Firefox/68.0"
    };
    public String readJsonFile(String Filename ) {
        String jsonStr = "";
        try {
            File jsonFile = new File(Filename);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map httpGetBanner(String url){

        url="https://"+url;
        String Charset="utf-8";
        Random random=new Random();
        int ua=random.nextInt(6);
        Map map=new HashMap();
        List list = new ArrayList();
        httpClientDemo httpClientDemo=new httpClientDemo();

        HttpGet httpGet=new HttpGet(url);
        httpGet.addHeader("Connection","close");

        httpGet.addHeader("User-Agent",User_agent[ua]);
        httpGet.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.setConfig(httpClientDemo.requestConfig);
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        httpClient= HttpClients.custom().setSslcontext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();

        try {
            HttpResponse response= httpClient.execute(httpGet);
            int code =response.getStatusLine().getStatusCode();


            String Server=response.getFirstHeader("Server").getValue();

            map.put("Server",Server);
            byte[] body=EntityUtils.toByteArray(response.getEntity());

            String body1=new String(body);

            Pattern patternCharset=Pattern.compile("charset=(\\S*)\"");
            Matcher matcherCharset=patternCharset.matcher(body1);
            while (matcherCharset.find()){
               Charset=matcherCharset.group(1);
            }

            if (Charset.contains("\"")){
                Charset=Charset.replace("\"","");
            }
            String body2="";
            try {
                 body2 = new String(body, Charset);
            }catch (UnsupportedEncodingException e){
                System.out.println("123123123");
            }


            /*获取iconHash*/
            Pattern patternIcon=Pattern.compile("href=\"(\\S*.ico)\"");
            Matcher matcherIcon=patternIcon.matcher(body2);
            String iconPath="";

            while (matcherIcon.find()){
                iconPath=matcherIcon.group(1);
            }
            if (!iconPath.contains("http")){
                iconPath=url+iconPath;
            }
            HttpGet httpGet1=new HttpGet(iconPath);
            HttpResponse response1= httpClient.execute(httpGet1);
            byte[] temp=EntityUtils.toByteArray(response1.getEntity());
            BASE64Encoder base64Encoder=new BASE64Encoder();
            String test1=base64Encoder.encode(temp);
            String iconHash= String.valueOf(Hashing.murmur3_32().hashString(test1.replaceAll("\r","")+"\n", StandardCharsets.UTF_8).asInt());




            FingerPrint fingerprint=new FingerPrint();
            List listFinger=new ArrayList();
            Map mapFinger1=new HashMap();
            String test=fingerprint.readJsonFile("D:\\Code\\Java\\Spring-all\\attackManage\\src\\main\\resources\\finger.json");

            Map mapFinger= JSON.parseObject(test);

            /*指纹识别*/
            listFinger = (List) mapFinger.get("fingerprint");
            for (int i=0;i< listFinger.size();i++){
                mapFinger1= (Map) listFinger.get(i);
                String method=mapFinger1.get("method").toString();
                List keyword= (List) mapFinger1.get("keyword");
                int flag=0;
                /*body*/
                if(method.equals("keyword")){
                    for (int j=0;j<keyword.size();j++){
                        if (body2.contains(keyword.get(j).toString())){
                            flag=flag-1;
                        }else {
                            flag=0;
                        }
                    }
                }
                /*iconhash*/
                if (method.equals("faviconhash")) {

                    if (iconHash.equals(keyword.get(0).toString())) {
                        flag=flag+3;
                        System.out.println(iconHash);
                    }
                }
                if (flag>=(keyword.size())){
                    map.put("fingerprint",listFinger.get(i).toString());
                    break;
                }else {
                    map.put("fingerprint","null");
                }

            }

            /*指纹识别hash*/


            /*Title get*/

            Pattern patternTitle=Pattern.compile("<title>(.*?)</title>");
            Matcher matcherTitle= patternTitle.matcher(body2);
            if (matcherTitle.find()) {
                map.put("title", matcherTitle.group(1));
            }else {
                map.put("title","null");
            }

            map.put("code",code);
        }catch (Exception e){
            e.printStackTrace();
            map.put("Server","null");
            map.put("fingerprint","null");
            map.put("title","null");
            map.put("code","协议问题待解决");
        }


//        JSONArray jsonArray= JSONArray.parseArray(test);
//        boolean flag=false;
//        for(int i=0;i<jsonArray.size();i++) {
//            List listMatch = new ArrayList();
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            Map map1Json = (Map) JSONObject.parseObject(jsonObject.toJSONString(), Object.class);
//        }

//        Header headers[]= response.getAllHeaders();
//        for (int i =0;i<headers.length;i++) {
//            map.put(headers[i].getName(), headers[i].getValue());
//        }

        /*header*/
//        fingerPrint fingerprint =new fingerPrint();
//        List listRules=new ArrayList();
//        List listRulesList=new ArrayList();
//        Map mapMatch=new HashMap();
//        String test=fingerprint.readJsonFile("src/main/resources/goby.json");
//        JSONArray jsonArray= JSONArray.parseArray(test);
//        boolean flag=false;
//        for(int i=0;i<jsonArray.size();i++){
//            List listMatch=new ArrayList();
//            JSONObject jsonObject=jsonArray.getJSONObject(i);
//            Map map1Json= (Map) JSONObject.parseObject(jsonObject.toJSONString(),Object.class);
//
//            listRules= (List) map1Json.get("rules");
//
//            for(int j=0;j<listRules.size();j++) {
//                listRulesList= (List) listRules.get(j);
//                for (int k=0;k<listRulesList.size();k++){
//                    mapMatch= (Map) listRulesList.get(k);
//                    if (mapMatch.get("match").toString().equals("header_contains")){
//                        listMatch.add(mapMatch.get("content"));
//                    }
//                }
//            }
//            for (int m=0;m<listMatch.size();m++){
//                if (map.toString().contains(listMatch.get(m).toString())){
//                    flag=true;
//                }else {
//                    flag=false;
//                }
//            }
//            if (flag){
//                return map;
//            }
//        }


        /*body*/

       return map;
    }


    public Map jsonToMap(){
        FingerPrint fingerprint =new FingerPrint();
        Map map=new HashMap();
        List list=new ArrayList();
        List list1=new ArrayList();
        String test=fingerprint.readJsonFile("src/main/resources/goby.json");
        JSONArray jsonArray= JSONArray.parseArray(test);
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            Map map1= (Map) JSONObject.parseObject(jsonObject.toJSONString(),Object.class);
//            System.out.println(map1);
            for(int j=0;j<((List)map1.get("rules")).size();j++) {
                for (int k = 0; k < ((List) ((List) map1.get("rules")).get(j)).size(); k++) {
                 //   System.out.println(((Map) ((List) ((List) map1.get("rules")).get(j)).get(k)).get("match").toString());
                    if ((((Map) ((List) ((List) map1.get("rules")).get(j)).get(k)).get("match").toString()).equals("header_contains")) {

                        ((Map) ((List) ((List) map1.get("rules")).get(j)).get(k)).get("content");
                    }
                }
            }
//
//
//            }
//            System.out.println(map1.get("rules"));
//            map1.get("rules")
//            System.out.println(map1.get("rules"));
        }

        return map;
    }


    public static void main(String args[]) throws Exception {
        FingerPrint fingerPrint=new FingerPrint();
        System.out.println(fingerPrint.httpGetBanner("185.25.253.32/"));
    }
}
