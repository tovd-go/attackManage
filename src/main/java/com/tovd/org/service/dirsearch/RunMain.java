package com.tovd.org.service.dirsearch;

import com.tovd.org.mapper.DirMapper;
import com.tovd.org.mapper.DomainMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.tovd.org.service.dirsearch.DirFind.User_agent;
import static java.lang.Thread.sleep;

@Service
@RestController
@Mapper
@Transactional
public class RunMain {

    public Map Run(String url) throws InterruptedException {
        Map map=new HashMap();
        DirFind dirFind=new DirFind();

        dirFind.setUrl(url);
        Vector<Thread> threads = new Vector<Thread>();
        for(int i=0; i<100; i++){
            Thread t = new Thread(dirFind);
            threads.add(t);

            t.start();
        }
        for (Thread t : threads) {
            try {
                // 等待所有线程执行完毕
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        map=dirFind.getMap();
        return map;

    }
    public static void main(String args[]) throws InterruptedException {
        RunMain runMain=new RunMain();
        runMain.Run("http://1.116.226.44/");
    }


}
