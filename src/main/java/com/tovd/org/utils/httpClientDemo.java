package com.tovd.org.utils;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

public class httpClientDemo {

    public static HttpClient httpClient;
    // 最大连接数
    public static final int MAX_CONNECTION = 100;
    // 每个route能使用的最大连接数，一般和MAX_CONNECTION取值一样
    public static final int MAX_CONCURRENT_CONNECTIONS = 100;
    // 建立连接的超时时间，单位毫秒
    public static final int CONNECTION_TIME_OUT = 5000;
    // 请求超时时间，单位毫秒
    public static final int REQUEST_TIME_OUT = 5000;
    // 最大失败重试次数
    public static final int MAX_FAIL_RETRY_COUNT = 3;
    // 请求配置，可以复用
    public static RequestConfig requestConfig;

    static {
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(REQUEST_TIME_OUT).setSoKeepAlive(true)
                .setTcpNoDelay(true).build();

        requestConfig = RequestConfig.custom()
                .setSocketTimeout(REQUEST_TIME_OUT)
                .setConnectTimeout(CONNECTION_TIME_OUT).build();
        /**
         * 每个默认的 ClientConnectionPoolManager 实现将给每个route创建不超过2个并发连接，最多20个连接总数。
         */
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(MAX_CONNECTION);
        connManager.setDefaultMaxPerRoute(MAX_CONCURRENT_CONNECTIONS);
        connManager.setDefaultSocketConfig(socketConfig);

        httpClient = HttpClients.custom().setConnectionManager(connManager).setRetryHandler(new httpClientDemo.MyHttpRequestRetryHandler()).build();
    }
    public static class MyHttpRequestRetryHandler implements
            HttpRequestRetryHandler {

        @Override
        public boolean retryRequest(IOException exception, int executionCount,
                                    HttpContext context) {
            if (executionCount >= MAX_FAIL_RETRY_COUNT) {
                return false;
            }

            if (exception instanceof InterruptedIOException) {
                // 超时
                return false;
            }
            if (exception instanceof UnknownHostException) {
                // 未知主机
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {
                // 连接被拒绝
                return false;
            }
            if (exception instanceof SSLException) {
                // SSL handshake exception
                return false;
            }

            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
            if (idempotent) {
                // 如果请求被认为是幂等的，则重试
                return true;
            }

            return false;
        }
    }
}
