package vip.xiaonuo.sys.modular.hr.conference.rez.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtil {

    public static JSONObject httpGet(String url, Map<String,String> mapParam) {
        url = url+"?";
        int i=1;
        for(String key : mapParam.keySet()){
            String value = mapParam.get(key);
            url = i == 1  ? url+key+"="+value :  url+"&"+key+"="+value;
            i++;
        }
        try {
            // 创建HttpClient实例
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url); // 创建HttpGet实例
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpGet.setConfig(requestConfig);
            // 执行请求并获取响应
            HttpResponse httpResponse = httpClient.execute(httpGet);
            // 从响应中获取状态码和响应体
            java.lang.String responseBody = EntityUtils.toString(httpResponse.getEntity());
            // 输出状态码和响应体
            return JSONUtil.parseObj(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public  static JSONObject  sendPost(String url,Map<String,String> pmap) throws IOException{
        // 请求结果
        JSONObject json = new JSONObject();
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        URL realUrl;
        HttpsURLConnection conn;
        String method = "POST";
        String queryString = url; //查询地址
        String postpar = ""; //请求参数获取
        if(pmap!=null){// map格式的请求参数
            StringBuffer mstr = new StringBuffer();
            for(String str:pmap.keySet()){
                String val = pmap.get(str);
                try {
                    val= URLEncoder.encode(pmap.get(str),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                mstr.append(str+"="+val+"&");
            }
            postpar = mstr.toString();// 最终参数
            int lasts=postpar.lastIndexOf("&");
            postpar=postpar.substring(0, lasts);
        }
        if(method.toUpperCase().equals("GET")){
            queryString+="?"+postpar;
        }
        try {
            realUrl= new URL(queryString);
            conn = (HttpsURLConnection)realUrl.openConnection();
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if(method.toUpperCase().equals("POST")){
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                out = new PrintWriter(conn.getOutputStream());
                out.print(postpar);
                out.flush();
            }else{
                conn.connect();
            }
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            json = JSONUtil.parseObj(result);
        }finally {
            try {
                if (out != null) { out.close(); }
                if (in != null) { in.close(); }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return json;
    }
}
