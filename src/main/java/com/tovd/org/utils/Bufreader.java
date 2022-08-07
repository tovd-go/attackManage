package com.tovd.org.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Bufreader {
    public String bufreader(InputStream is, HttpURLConnection httpURLConnection) throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        // 为字符输入流添加缓冲
        BufferedReader br = new BufferedReader(isr);
        // 读取返回结果
        String data = null;
        while ((data = br.readLine()) != null) {
            sb.append(data);
        }
        // 释放资源
        br.close();
        isr.close();
        is.close();
        httpURLConnection.disconnect();
        return sb.toString();
    }
}