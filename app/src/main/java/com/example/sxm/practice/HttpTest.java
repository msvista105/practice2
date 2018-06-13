package com.example.sxm.practice;

import android.util.Log;

import com.example.sxm.utils.LogUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpTest {
    private static final String TAG = "HttpTest";
    private HttpClient createHttpClient(){
        HttpParams mDefaultHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(mDefaultHttpParams, 15000);
        //设置请求超时
        HttpConnectionParams.setSoTimeout(mDefaultHttpParams, 15000);
        HttpConnectionParams.setTcpNoDelay(mDefaultHttpParams, true);
        HttpProtocolParams.setVersion(mDefaultHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(mDefaultHttpParams, HTTP.UTF_8);
        //持续握手
        HttpProtocolParams.setUseExpectContinue(mDefaultHttpParams, true);
        HttpClient mHttpClient = new DefaultHttpClient(mDefaultHttpParams);
        return mHttpClient;
    }
    private void useHttpClientGet(String url) {
        HttpGet mHttpGet = new HttpGet(url);
        mHttpGet.addHeader("Connection", "Keep-Alive");
        try {
            HttpClient mHttpClient = createHttpClient();
            HttpResponse mHttpResponse = mHttpClient.execute(mHttpGet);
            HttpEntity mHttpEntity = mHttpResponse.getEntity();
            int code = mHttpResponse.getStatusLine().getStatusCode();
            if (null != mHttpEntity) {
                InputStream mInputStream = mHttpEntity.getContent();
                String respose = converStreamToString(mInputStream);
                LogUtils.d(TAG, "请求状态码:" + code + "\n请求结果:\n" + respose);
                mInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String converStreamToString(InputStream mInputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(mInputStream));
        String line = "";
        try{
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }
    public void startTest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                useHttpClientGet("http://www.baidu.com");
            }
        }).start();
    }
}
