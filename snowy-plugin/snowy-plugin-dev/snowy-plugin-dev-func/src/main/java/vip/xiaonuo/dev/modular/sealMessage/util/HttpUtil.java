package vip.xiaonuo.dev.modular.sealMessage.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    public  static JSONObject sendPost(String url,Map<String,String> pmap,String token,String contentType) throws IOException{
        // 请求结果
        JSONObject json = new JSONObject();
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        URL realUrl;
        HttpURLConnection conn;
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
            conn = (HttpURLConnection)realUrl.openConnection();
            conn.setRequestProperty("Content-Type",contentType);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if(ObjectUtil.isNotEmpty(token)){
                conn.setRequestProperty("Authorization", "Bearer " + token);
            }
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

    /*public static JSONObject sendPostRequest(String url, String postParams, String token, String contentType) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // 设置请求方法为POST
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", contentType);
        con.setDoOutput(true);
        con.setRequestProperty("Authorization","Bearer " + token);

        try (OutputStream os = con.getOutputStream()) {
            if(ObjectUtil.isNotEmpty(postParams)){
                byte[] input = postParams.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        }

        int responseCode = con.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        // 请求结果
        JSONObject json = new JSONObject();
        String inputLine;
        String result = "";

        while ((inputLine = in.readLine()) != null) {
            result += inputLine;
        }
        json = JSONUtil.parseObj(result);
        in.close();

        return json;
    }*/

    public static JSONObject sendPostRequest(String url, Map<String,Object> paramMap, String token) {

        // 设置请求头，包括Token
        Map<String, String> headers = new HashMap<>();

        headers.put(Header.CONTENT_TYPE.getValue(), "multipart/form-data");
        headers.put("Authorization", "Bearer "+token); // 这里放入你的Token

        // 使用Hutool的HttpRequest构建POST请求，并设置参数和请求头
        HttpResponse response = HttpRequest.post(url)
                .form(paramMap) // 设置请求参数
                .addHeaders(headers) // 添加请求头
                .execute(); // 执行请求

        // 检查响应状态码
        if (response.isOk()) {
            System.out.println("请求成功，响应内容：" + response.body());
        } else {
            System.out.println("请求失败，状态码：" + response.getStatus());
            System.out.println("响应内容：" + response.body());
        }
        return JSONUtil.parseObj(response.body());
    }
}
