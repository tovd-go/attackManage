package com.tovd.org.service.tyc;


import com.alibaba.fastjson.JSON;
import com.tovd.org.utils.Bufreader;
import com.tovd.org.utils.readJson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tovd.org.utils.httpClientDemo.httpClient;


public class Tyc {

   // static String Cooike = "ssuid=9188276112; _ga=GA1.2.2131644334.1637758782; creditGuide=1; aliyungf_tc=2ec70e5664bd183c785feef5fa1804baa904e7720f0b69acce2d961b60ae2dac; csrfToken=DpUaoDGxhi8hmbeoTuqJsYv9; jsid=https%3A%2F%2Fwww.tianyancha.com%2F%3Fjsid%3DSEM-BAIDU-PZ-SY-2021112-JRGW; bannerFlag=true; bdHomeCount=0; Hm_lvt_e92c8d65d92d534b0fc290df538b4758=1647829265,1648701457; _gid=GA1.2.705530056.1648701457; tyc-user-info={%22state%22:%220%22%2C%22vipManager%22:%220%22%2C%22mobile%22:%2217607034503%22}; tyc-user-info-save-time=1648701486175; tyc-user-phone=%255B%252217607034503%2522%255D; TYCID=61b942f04d2611eca7aed55447767c61; _bl_uid=v2k8awROdjhj19bUhjtar6RnjzUn; auth_token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNzYwNzAzNDUwMyIsImlhdCI6MTY0ODcwMTQ4NSwiZXhwIjoxNjUxMjkzNDg1fQ.IEDqFSoOfFbYmpTuj9NjR98_rwCEoWKZ_AZZA7zufd9Pv5Rr9UR5Av3JA1ViFfRtSna_VYGooRKfTJn0LxpVPg; bad_id658cce70-d9dc-11e9-96c6-833900356dc6=e8964b32-b0ac-11ec-a365-5b7375bec0a5; nice_id658cce70-d9dc-11e9-96c6-833900356dc6=e8964b33-b0ac-11ec-a365-5b7375bec0a5; searchSessionId=1648702271.61112975; relatedHumanSearchGraphId=2352406422; relatedHumanSearchGraphId.sig=_j60t7T_2UdWfQR0e6c8lSaraxsBIBfA8c2EPkEDjsY; RTYCID=59746c83ee0a487a84cfa42167a6f5e3; CT_TYCID=50890db275514e7c8a557c08e3e9d519; cloud_token=dcc7f3260aa14e01a69ef7764803a9f7; acw_tc=707c9f6616487231259376546e590ce7f2e2f17721217825ff6dec0a558db9; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22226233972%22%2C%22first_id%22%3A%2217d5207c7c22c5-042891fc3e8e02-978183a-1821369-17d5207c7c311eb%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22%24device_id%22%3A%2217d5207c7c22c5-042891fc3e8e02-978183a-1821369-17d5207c7c311eb%22%7D; Hm_lpvt_e92c8d65d92d534b0fc290df538b4758=1648723128; _gat_gtag_UA_123487620_1=1";
    static String[] User_agent = {
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/68.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:61.0) Gecko/20100101 Firefox/68.0",
            "Mozilla/5.0 (X11; Linux i586; rv:31.0) Gecko/20100101 Firefox/68.0"
    };
    static String Company_number="";
    public String getPageContent(String url) throws Exception {

        String Cookie=getCooike();

      //  String Cookie="auth_token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNzYwNzAzNDUwMyIsImlhdCI6MTY1NDU4Njg2NiwiZXhwIjoxNjU3MTc4ODY2fQ.x34zfBX1-RHC_HTsw_HOxKAJw7QSMv9A-nx2f4DbMvwKrl-2jt7nXnSUqy1XqqsR-FXu8nPmaqdj35advRwP8A;";
        Bufreader bufreader=new Bufreader();
        URL url1 = new URL(url);
        Random random = new Random();
        int u1 = random.nextInt(6);
        String ua = User_agent[u1];
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url1.openConnection();
        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setRequestMethod("GET");
        httpsURLConnection.setRequestProperty("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
        httpsURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpsURLConnection.setRequestProperty("Cookie", Cookie);
        httpsURLConnection.setRequestProperty("User-Agent", ua);

        httpsURLConnection.connect();
        InputStream is = httpsURLConnection.getInputStream();

        return bufreader.bufreader(is,httpsURLConnection);
    }

    public Map<String, Object> Father_company(String url) throws Exception{

        // 查询所有记录
        String num="1";
        String Icp_url="https://www.tianyancha.com/pagination/icp.xhtml?TABLE_DIM_NAME=icp&ps=10&pn="+num+"&id=";
        String Wechat_url="https://www.tianyancha.com/pagination/wechat.xhtml?ps=10&pn="+num+"&id=";
        String Weibo_url="https://www.tianyancha.com/pagination/weibo.xhtml?ps=10&pn="+num+"&id=";
        String Supply_url="https://www.tianyancha.com/pagination/supplies.xhtml?TABLE_DIM_NAME=supplies&ps=10&pn="+num+"&id=";
        String Owner_url="https://www.tianyancha.com/company/";
        String ChildName_url="https://capi.tianyancha.com/cloud-company-background/company/investListV2";
        List company_list=new ArrayList();
        Map all_map=new HashMap();



        url="https://www.tianyancha.com/search?key="+url;
        String father_company=getPageContent(url);
        String new_url;
        Pattern pattern=Pattern.compile("href=\"https://www.tianyancha.com/company/(\\d*)\"");

        Matcher matcher=pattern.matcher(father_company);
        while (matcher.find()){

            Company_number=matcher.group(1);
            all_map.put("companyId",Company_number);

            System.out.println(Company_number);
            break;
        }

        HttpPost httpPost=new HttpPost(ChildName_url);

        JSONObject parm=new JSONObject();
        parm.put("gid",Company_number);
        parm.put("pageSize",20);
        parm.put("pageNum",1);

        StringEntity parmentiry=new StringEntity(parm.toString());
        parmentiry.setContentType("application/json");
        httpPost.setEntity(parmentiry);
        HttpResponse httpResponse=httpClient.execute(httpPost);
        String result= EntityUtils.toString(httpResponse.getEntity(),"utf-8");

        JSONObject jsonObject=new JSONObject(result);
        Map map=new HashMap();
        map=jsonObject.toMap();
        List list= ((List)((Map) map.get("data")).get("result"));
        for (int i=0;i<list.size();i++){
            Map map1= (Map) list.get(i);
            if (Double.valueOf (map1.get("percent").toString().replace("%",""))>51.0){
                System.out.println(map1.get("name").toString());
                company_list.add(map1.get("name").toString());
            }
        }
        all_map.put("companyChildName",company_list);


        supply(Supply_url,num,all_map);
        Wechat(Wechat_url,num,all_map);
        weibo(Weibo_url,num,all_map);
        icp(Icp_url,Owner_url,num,all_map);


        return all_map;

    }


    public String getCooike(){
        readJson read=new readJson();
        String configJson= read.readJsonFile("D:\\Code\\Java\\Spring-all\\attackManage\\src\\main\\resources\\config.json");
        Map map= JSON.parseObject(configJson);
        return map.get("tycCookie").toString();
    }

    public void supply(String Supply_url,String num,Map all_map) throws Exception {
        String web=getPageContent(Supply_url+Company_number);
        Pattern pattern_num=Pattern.compile("(\\d)</a");
        Pattern pattern_supply=Pattern.compile("target=\"_blank\">(\\S*)</a>");
        Matcher matcher_num=pattern_num.matcher(web);
        List supplyName=new ArrayList();
        if (!matcher_num.find()){
            Supply_url="https://www.tianyancha.com/pagination/supplies.xhtml?TABLE_DIM_NAME=supplies&ps=10&pn="+num+"&id=";
            web=getPageContent(Supply_url+Company_number);
            Matcher matcher_name=pattern_supply.matcher(web);
            while (matcher_name.find()){
                supplyName.add(matcher_name.group(1));
            }
        }
        while (matcher_num.find()){
            num=matcher_num.group(1);
            Supply_url="https://www.tianyancha.com/pagination/supplies.xhtml?TABLE_DIM_NAME=supplies&ps=10&pn="+num+"&id=";
            web=getPageContent(Supply_url+Company_number);
            Matcher matcher_name=pattern_supply.matcher(web);
            while (matcher_name.find()){
                supplyName.add(matcher_name.group(1));
            }
        }
        all_map.put("companySupply",supplyName);
    }

    public void weibo(String Weibo_url,String num,Map all_map) throws Exception {
        String web=getPageContent(Weibo_url+Company_number);
        Pattern pattern_num=Pattern.compile("(\\d)</a");
        Pattern pattern_weibo=Pattern.compile(">(\\S*)</a></td>");
        Matcher matcher_num=pattern_num.matcher(web);
        List weiboName=new ArrayList();
        if (!matcher_num.find()){
            Weibo_url="https://www.tianyancha.com/pagination/weibo.xhtml?ps=10&pn="+num+"&id=";

            web=getPageContent(Weibo_url+Company_number);
            Matcher matcher_name=pattern_weibo.matcher(web);
            while (matcher_name.find()){
                weiboName.add(matcher_name.group(1));
            }
        }

        while (matcher_num.find()){
            num=matcher_num.group(1);
            Weibo_url="https://www.tianyancha.com/pagination/weibo.xhtml?ps=10&pn="+num+"&id=";

            web=getPageContent(Weibo_url+Company_number);
            Matcher matcher_name=pattern_weibo.matcher(web);
            while (matcher_name.find()){
                weiboName.add(matcher_name.group(1));
            }
        }
        all_map.put("companyWeibo",weiboName);
    }

    public void Wechat(String Wechat_url,String num,Map all_map) throws Exception {

        List webchatName=new ArrayList();
        List webchatId=new ArrayList();

        String web=getPageContent(Wechat_url+Company_number);
        Pattern pattern_num=Pattern.compile("(\\d)</a");
        Pattern pattern_WechatName=Pattern.compile("<td class=\"\"><span>(.*?)</span>");
        Pattern pattern_WechatId=Pattern.compile("class=\"left-col\"><span>(\\S*)</span>");
        Matcher matcher_num=pattern_num.matcher(web);
        if(!matcher_num.find()){
            Wechat_url="https://www.tianyancha.com/pagination/wechat.xhtml?ps=10&pn="+num+"&id=";
            web = getPageContent(Wechat_url + Company_number);
            Matcher matcher_name=pattern_WechatName.matcher(web);
            while (matcher_name.find()){
                webchatName.add(matcher_name.group(1));
            }

            Matcher matcher_id=pattern_WechatId.matcher(web);
            while (matcher_id.find()){
                webchatId.add(matcher_id.group(1));
            }
        }

        while (matcher_num.find()){
            num = matcher_num.group(1);
            Wechat_url="https://www.tianyancha.com/pagination/wechat.xhtml?ps=10&pn="+num+"&id=";
            web = getPageContent(Wechat_url + Company_number);
            Matcher matcher_name=pattern_WechatName.matcher(web);
            while (matcher_name.find()){
                webchatName.add(matcher_name.group(1));
            }

            Matcher matcher_id=pattern_WechatId.matcher(web);
            while (matcher_id.find()){
                webchatId.add(matcher_id.group(1));
            }
        }

        all_map.put("companyWechatName",webchatName);
        all_map.put("companyWechatId",webchatId);
    }


    public void icp(String Icp_url,String Owner_url,String num,Map all_map) throws Exception {
        String web=getPageContent(Icp_url+Company_number);
        Pattern pattern_ip=Pattern.compile("\">(\\d*.\\d*.\\d*.\\d*)</span>");
        Pattern pattern_web=Pattern.compile("block;\">([a-z]\\S*)</span>");

        String ip = null;
        String domain = null;
        List domain_list=new ArrayList();
        List ip_list=new ArrayList();
        Pattern pattern_num=Pattern.compile("(\\d)</a");
        Matcher matcher_num=pattern_num.matcher(web);
        while (matcher_num.find()) {
            num = matcher_num.group(1);
            Icp_url="https://www.tianyancha.com/pagination/icp.xhtml?TABLE_DIM_NAME=icp&ps=10&pn="+num+"&id=";
            web = getPageContent(Icp_url + Company_number);
            Matcher matcher_ip = pattern_ip.matcher(web);
            while (matcher_ip.find()) {
                ip = matcher_ip.group(1);
                ip_list.add(ip);
            }
            ip_list = Remove_duplicate(ip_list);
            Matcher matcher_web = pattern_web.matcher(web);
            while (matcher_web.find()) {
                domain = matcher_web.group(1);
                domain_list.add(domain);
            }
            domain_list = Remove_duplicate(domain_list);
        }
        if(!matcher_num.find()) {
            Icp_url = "https://www.tianyancha.com/pagination/icp.xhtml?TABLE_DIM_NAME=icp&ps=30&pn=" + num + "&id=";
            web = getPageContent(Icp_url + Company_number);
            Matcher matcher_ip = pattern_ip.matcher(web);
            while (matcher_ip.find()) {
                ip = matcher_ip.group(1);
                ip_list.add(ip);
            }
            ip_list = Remove_duplicate(ip_list);
            Matcher matcher_web = pattern_web.matcher(web);
            while (matcher_web.find()) {
                domain = matcher_web.group(1);
                domain_list.add(domain);
            }
            domain_list = Remove_duplicate(domain_list);
        }

        String web_domain=getPageContent(Owner_url+Company_number);

        Pattern pattern_domain=Pattern.compile("title=\"(\\S*)\"              >");

        Matcher matcher_domain=pattern_domain.matcher(web_domain);
        while (matcher_domain.find()){
            System.out.println(matcher_domain.group(1));
        }

        all_map.put("companyHost",ip_list);
        all_map.put("companyDomain",domain_list);
    }

    public String Email() throws Exception {
        String web = getPageContent("https://www.tianyancha.com/company/1489798");
        String web1="title=d-a-d@360.cn";
        // Pattern pattern = Pattern.compile("([a-z]*|\"-\\*\")@\\S*.cn");
        Pattern pattern=Pattern.compile("^(\\w+((-\\w+)|(\\.\\w+))*)\\+\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
        Matcher matcher = pattern.matcher(web1);
        while (matcher.find()) {
        }
        return null;

    }

    public String New_url(String url){

        Pattern pattern=Pattern.compile("search\\Skey=\\S*");
        Matcher matcher= pattern.matcher(url);
        while (matcher.find()){

            url=url.replace(matcher.group(),"company/"+Company_number);

            return url;
        }
        return null;
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
    public static void main(String args[]) throws Exception {
        Tyc tyc=new Tyc();
        Map map=new HashMap();
        String companyName="上海证券交易所";
        companyName= URLEncoder.encode(companyName,"utf-8");
        map=tyc.Father_company(companyName);
        System.out.println(JSON.toJSONString(map));
    }
}
