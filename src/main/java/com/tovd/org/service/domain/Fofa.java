package com.tovd.org.service.domain;
import com.alibaba.fastjson.JSON;
import com.tovd.org.utils.Bufreader;
import com.tovd.org.utils.readJson;
import org.apache.commons.codec.binary.Base64;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fofa {

    static String fofaApi="90bb148eabdb29e3eeae3248a6c9acd4";
    static String fofaEmail="994425414@qq.com";

    static String[] User_agent = {
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/68.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:61.0) Gecko/20100101 Firefox/68.0",
            "Mozilla/5.0 (X11; Linux i586; rv:31.0) Gecko/20100101 Firefox/68.0"
    };

    /*fafa数据处理*/
    public Map getFofa(String url) throws Exception {


        Map map=new HashMap();
        Map map1=new HashMap();
        Base64 base64=new Base64();
        String newurl=base64.encodeToString(url.getBytes());
        String fofaemil=getEmail();
        String fofaapikey=getApikey();
        String urla="https://fofa.info/api/v1/search/all?email="+fofaemil+"&key="+fofaapikey+"&qbase64="+newurl+"&size=10000";

        System.out.println(urla);
        Bufreader bufreader=new Bufreader();
        URL url1 = new URL(urla);
        Random random = new Random();

        int u1 = random.nextInt(6);
        String ua = User_agent[u1];

        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url1.openConnection();
        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setRequestMethod("GET");
        httpsURLConnection.setRequestProperty("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
        httpsURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpsURLConnection.setRequestProperty("User-Agent", ua);

        httpsURLConnection.connect();
        InputStream is = httpsURLConnection.getInputStream();

        map=JSON.parseObject(bufreader.bufreader(is,httpsURLConnection));
        List list= (List) map.get("results");
        List list1=new ArrayList();
        String value="";
        Pattern p = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv|icu)", Pattern.CASE_INSENSITIVE);
        for (int i=0;i<list.size();i++){
           value= (String) ((List)list.get(i)).get(0);
           if(value.contains(url)){
               Matcher matcher = p.matcher(value);
               while (matcher.find()) {
                   list1.add(matcher.group());
               }
           }
        }
        map1.put("fofa",list1);
        return map1;
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
    public Map getIp(List list){
        Map map=new HashMap();
        List list1=new ArrayList();
        List list2=new ArrayList();
        try{
            list=Remove_duplicate(list);
            for (int i=0;i<list.size();i++){
                InetAddress address=InetAddress.getByName(list.get(i).toString());

                list1.add(list.get(i).toString());
                list2.add(address.getHostAddress());
            }
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
        map.put("domain",list1);
        map.put("ip",list2);
        return map;
    }

    public String getEmail(){
        readJson read=new readJson();
        String configJson= read.readJsonFile("D:\\Code\\Java\\Spring-all\\attackManage\\src\\main\\resources\\config.json");
        Map map= JSON.parseObject(configJson);
        return map.get("fofaEmail").toString();
    }

    public String getApikey(){
        readJson read=new readJson();
        String configJson= read.readJsonFile("D:\\Code\\Java\\Spring-all\\attackManage\\src\\main\\resources\\config.json");
        Map map= JSON.parseObject(configJson);
        return map.get("fofaApiKey").toString();
    }

    public static void main(String args[]) throws Exception {

        Map map=new HashMap();
        Fofa fofa=new Fofa();
        map= fofa.getFofa("moresec.cn");
        System.out.println(map.toString());

    }
}
