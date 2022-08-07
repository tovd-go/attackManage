package com.tovd.org.service.domain;

import com.alibaba.fastjson.JSON;
import com.tovd.org.utils.httpClientDemo;
import com.tovd.org.utils.readJson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tovd.org.utils.httpClientDemo.httpClient;

public class SecurityTrails {

    public Map getDomain(String url){

        Boolean flag=true;
        String newUrl="https://api.securitytrails.com/v1/domain/"+url+"/subdomains?children_only=true&include_inactive=true";
        String securitytrailsApiKey1="ZUQbZ40gcx5gJk2h8kdkYkWzYkzhUXxB";
        String securitytrailsApiKey2="BS84Vnjcg2uuH7nQiUzo7SFHKmIuKSyW";
        String securitytrailsApiKey=getsecuritytrailsApiKey();

        Map map=new HashMap();
        List list=new ArrayList();
        List listTemp=new ArrayList();
        HttpGet httpGet=new HttpGet(newUrl);
        httpGet.addHeader("Accept", "application/json");
        httpGet.addHeader("APIKEY", securitytrailsApiKey);
        httpGet.setConfig(httpClientDemo.requestConfig);

//        HttpHost proxy=new HttpHost("127.0.0.1",9999,"http");
//        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
//        httpGet.setConfig(config);

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
            String subdomain=EntityUtils.toString(response.getEntity(),"utf-8");
            map= (Map) JSON.parse(subdomain);
            list= (List) map.get("subdomains");
            for (int i=0;i<list.size();i++){
                listTemp.add(list.get(i).toString()+"."+url);
            }
        }catch (Exception e){
            System.out.println("Api失效，请更换api");
            map.put("securityTrails","false");
            return map;
        }
        map.put("securityTrails",listTemp);
        return map;
    }

    public String getsecuritytrailsApiKey(){
        readJson read=new readJson();
        String configJson= read.readJsonFile("D:\\Code\\Java\\Spring-all\\attackManage\\src\\main\\resources\\config.json");
        Map map= JSON.parseObject(configJson);
        return map.get("securityTrailsApiKey").toString();
    }
//    public static void main(String args[]){
//        securityTrails securityTrails=new securityTrails();
//        securityTrails.getDomain("360.cn");
//    }

}
