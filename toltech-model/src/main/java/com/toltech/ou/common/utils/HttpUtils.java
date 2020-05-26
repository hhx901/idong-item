package com.toltech.ou.system.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2019/7/11 11:04
 * @Version 1.0
 */
@Slf4j
public class HttpUtils {

    public static JSONObject getLocationJson(String url, int redirectCount) throws IOException {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http POST请求，访问开源中国
        HttpPost httpPost = new HttpPost("http://10.255.247.124:8080/serve/api/buyClient/authPackage.do");
        // 根据开源中国的请求需要，设置post请求参数
        List<NameValuePair> parameters = new ArrayList<>(0);
        parameters.add(new BasicNameValuePair("UserID", "0571000220"));
        parameters.add(new BasicNameValuePair("ProductID", "700059"));
        parameters.add(new BasicNameValuePair("Returnurl", "http://www.baidu.com"));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);
        CloseableHttpResponse response = null;
        JSONObject object = new JSONObject();
        try {
            // 执行请求
            response = httpclient.execute(httpPost);
            if(response != null && response.getAllHeaders() != null) {
                Header header = null;
                for (Header temp : response.getAllHeaders()) {
                    if (temp.getName().toLowerCase().equals("location")) {
                        header = temp;
                        break;
                    }
                }
                if(header != null){
                    String location=header.getValue();
                    log.info("获取业务数据结果：{}", location);
                    if(StringUtils.isBlank(location) || location.endsWith("?"))
                        return null;
                    if(!location.startsWith("http://localhost")){
                        if(redirectCount < 2) {
                            return getLocationJson(location, redirectCount + 1);
                        } else {
                            return null;
                        }
                    }
                    String temp = StringUtils.split(location,"?")[1];
                    if(StringUtils.isBlank(temp)) {
                        return null;
                    }
                    String[] temps = temp.split("&");
                    if(temps == null || temps.length ==0) {
                        return null;
                    }
                    for(String str : temps){
                        String[] datas = StringUtils.split(str,"=");
                        String name = datas[0];
                        if(datas.length > 1){
                            String value = datas[1];
                            object.put(name.toLowerCase(), value);
                        }else{
                            object.put(name.toLowerCase(), null);
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        httpclient.close();
        return object;
    }

    public static JSONObject getJson(String url, List<NameValuePair> parameters) throws IOException {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http POST请求，访问开源中国
        HttpPost httpPost = new HttpPost(url);
        // 根据开源中国的请求需要，设置post请求参数
//        List<NameValuePair> parameters = new ArrayList<>(0);
//        parameters.add(new BasicNameValuePair("UserID", "0571000220"));
//        parameters.add(new BasicNameValuePair("ProductID", "700059"));
//        parameters.add(new BasicNameValuePair("Returnurl", "http://www.baidu.com"));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);
        CloseableHttpResponse response = null;
        //response.setEntity(new StringEntity("", ""));
        String content = null;
        try {
            // 执行请求
            response = httpclient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应体
                content = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //关闭浏览器
        httpclient.close();
        if(StringUtils.isNotBlank(content)){
            return JSON.parseObject(content);
        }
        return null;
    }

    public void getParam() throws Exception{
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建URI对象，并且设置请求参数
        URI uri = new URIBuilder("http://www.baidu.com/s").setParameter("wd", "java").build();
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(uri);
        // HttpGet get = new HttpGet("http://www.baidu.com/s?wd=java");
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应数据
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpclient.close();
        }
    }

    public void get() throws Exception{
        // 创建Httpclient对象,相当于打开了浏览器
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建HttpGet请求，相当于在浏览器输入地址
        HttpGet httpGet = new HttpGet("http://www.baidu.com/");
        CloseableHttpResponse response = null;
        try {
            // 执行请求，相当于敲完地址后按下回车。获取响应
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应，获取数据
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }
        } finally {
            if (response != null) {
                // 关闭资源
                response.close();
            }
            // 关闭浏览器
            httpclient.close();
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            String urlNameString = url + "?" + param;
            log.info("sendPost - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendPost ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendPost SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendPost IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendPost Exception, url=" + url + ",param=" + param, e);
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return result.toString();
    }
}
