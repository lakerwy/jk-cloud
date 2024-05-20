package vip.xiaonuo.biz.modular.sealMessage.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if(ObjectUtil.isNotEmpty(token)){
                conn.setRequestProperty("Authorization","Bearer " + token);
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

    public static JSONObject sendPostRequest(String url, String postParams, String token, String contentType) throws Exception {
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
    }
}
