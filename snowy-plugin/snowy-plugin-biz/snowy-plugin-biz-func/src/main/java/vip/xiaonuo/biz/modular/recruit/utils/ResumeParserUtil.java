package vip.xiaonuo.biz.modular.recruit.utils;

import java.io.File;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


/**
 * @author yong.ye
 * @date 2023/8/23 10:36
 *
 * 简历解析工具类
 */

@Slf4j
@Component
public class ResumeParserUtil {

    /**
     * APPCODE  简单身份认证调用方法（APPCODE） 配置文件读取
     */
    @Value("${resumeParser.appCode}")
    private String resumeParserAppCode;

    /*
     * APPCODE  简单身份认证调用方法（APPCODE）
     */
    private static String APPCODE;

    @PostConstruct
    public void setAPPCODE(){
        APPCODE = this.resumeParserAppCode;
    }

    /** ResumeSDK简历解析 API接口地址 */
    private static final String API_URL = "http://resumesdk.market.alicloudapi.com/ResumeParser";

    /**
     * 简历解析
     * @param fileName 简历附件路径
     * @return
     */
    public static JSONObject ResumeParser(String fileName){
        try {
            // 设置头字段
            HttpPost httpPost = new HttpPost(API_URL);
            httpPost.setHeader("Authorization", "APPCODE " + APPCODE);
            httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");
            httpPost.addHeader("Content-Type", "application/json");

            // 读取简历内容
            byte[] bytes = org.apache.commons.io.FileUtils.readFileToByteArray(new File(fileName));
            String data = new String(Base64.encodeBase64(bytes), Consts.UTF_8);

            // 设置内容信息
            JSONObject json = new JSONObject();
            json.put("file_name", fileName);    // 文件名
            json.put("file_cont", data);    // 经base64编码过的文件内容
            json.put("need_avatar", 0);        // 是否需要解析头像
            json.put("ocr_type", 1);        // 1为高级ocr
            StringEntity params = new StringEntity(json.toString(), Consts.UTF_8);
            httpPost.setEntity(params);

            // 发送请求
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httpPost);

            // 处理返回结果
            String resCont = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
            JSONObject res = new JSONObject(resCont);

            //日志
            log.info("【简历解析】文件名："+fileName +" \n 返回结果：" +res.toString());
            return res;

        }catch (Exception e){
            log.info("【简历解析】文件名："+fileName +" \n 异常信息：" +e.getMessage());
            return null;
        }
    }
}
