package com.tovd.org.service.dirsearch;

import com.tovd.org.utils.Bufreader;
import com.tovd.org.utils.readJson;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.core.task.support.ExecutorServiceAdapter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;


@Component
public class DirFind implements Runnable{

    private static int ticket=10;
    private  int i=0;
    public String url;
    public Map map=new HashMap();
    public List listPath=new ArrayList();
    public List listUrl=new ArrayList();
    public List listCode=new ArrayList();
    Object obj = new Object();//创建一个object类的对象作监视器的参数
    static String[] User_agent = {
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/68.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:61.0) Gecko/20100101 Firefox/68.0",
            "Mozilla/5.0 (X11; Linux i586; rv:31.0) Gecko/20100101 Firefox/68.0"
    };

    public Map getMap(){
        return this.map;
    }
    public static synchronized List read() throws IOException {
        List list1=new ArrayList();
        Scanner scanner=new Scanner(new File("D:\\Code\\Java\\Spring-all\\attackManage\\src\\main\\resources\\dicc.txt"));
        while (scanner.hasNextLine()){
            list1.add(scanner.nextLine());
        }

        return list1;
    }
    public void setUrl(String url){
        this.url=url;
    }
    public String getUrl(){

        return url;
    }
    public static synchronized int get(){
        int i=0;
        return i;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true){

            List list=read();
            int temp= read().size();

            synchronized (obj){

                if (i<temp){
                    try {
                        String value1 = list.get(i).toString();
                        String urla = getUrl() + value1;
                        URL url = new URL(urla);
                        URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
                        Random random = new Random();

                        System.out.println(uri);
                        HttpClient httpClient = new DefaultHttpClient();
                        int u1 = random.nextInt(6);
                        String ua = User_agent[u1];

                        HttpHead head = new HttpHead(uri.toString());
                        head.setHeader("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
                        head.setHeader("User-Agent", ua);
                        HttpResponse response = httpClient.execute(head);
                        int code=response.getStatusLine().getStatusCode();
                        if (code!=404){
                            listUrl.add(urla);
                            listPath.add(value1);
                            listCode.add(code);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                        i++;
                }
                else {
                    map.put("dirUrl",listUrl);
                    map.put("dirPath",listPath);
                    map.put("dirCode",listCode);
                    break;
                }
            }
        }
    }
}
