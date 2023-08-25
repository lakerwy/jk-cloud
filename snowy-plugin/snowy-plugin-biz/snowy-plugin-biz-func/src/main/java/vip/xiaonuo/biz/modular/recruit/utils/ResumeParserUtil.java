package vip.xiaonuo.biz.modular.recruit.utils;

import java.io.File;

import cn.hutool.json.JSONObject;
import com.alibaba.excel.util.FileUtils;
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
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * @author yong.ye
 * @date 2023/8/23 10:36
 *
 * 简历解析工具类
 */

@Slf4j
@Component
public class ResumeParserUtil {

    /******************************************************/
//    /*
//     * APPCODE  简单身份认证调用方法（APPCODE） 配置文件读取
//     */
//    @Value("${resumeParser.appCode}")
//    private String resumeParserAppCode;
//
//    /*
//     * APPCODE  简单身份认证调用方法（APPCODE）
//     */
//    private static String APPCODE;
//
//    @PostConstruct
//    public void setAPPCODE(){
//        APPCODE = this.resumeParserAppCode;
//    }
//
//    /** ResumeSDK简历解析 [阿里云] API接口地址 */
//    private static final String API_URL = "http://resumesdk.market.alicloudapi.com/ResumeParser";

//    /**
//     * 简历解析
//     * @param fileName 简历附件路径 [阿里云接口 - 非正式]
//     * @return
//     */
//    public static JSONObject aliResumeParser(String fileName){
//        try {
//            // 设置头字段
//            HttpPost httpPost = new HttpPost(API_URL);
//            httpPost.setHeader("Authorization", "APPCODE " + APPCODE);
//            httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");
//            httpPost.addHeader("Content-Type", "application/json");
//
//            // 读取简历内容
//            byte[] bytes = org.apache.commons.io.FileUtils.readFileToByteArray(new File(fileName));
//            String data = new String(Base64.encodeBase64(bytes), Consts.UTF_8);
//
//            // 设置内容信息
//            JSONObject json = new JSONObject();
//            json.put("file_name", fileName);    // 文件名
//            json.put("file_cont", data);    // 经base64编码过的文件内容
//            json.put("need_avatar", 0);        // 是否需要解析头像
//            json.put("ocr_type", 1);        // 1为高级ocr
//            StringEntity params = new StringEntity(json.toString(), Consts.UTF_8);
//            httpPost.setEntity(params);
//
//            // 发送请求
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpResponse response = httpclient.execute(httpPost);
//
//            // 处理返回结果
//            String resCont = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
//            JSONObject res = new JSONObject(resCont);
//
//            //日志
//            log.info("【简历解析】文件名："+fileName +" \n 返回结果：" +res.toString());
//            return res;
//
//        }catch (Exception e){
//            log.info("【简历解析】文件名："+fileName +" \n 异常信息：" +e.getMessage());
//            return null;
//        }
//    }

    /********************************************************/



    /*
     * ResumeSDK简历解析 [官网Saas] API接口地址
     */
    private static final String SAAS_API_URL = "https://www.resumesdk.com/api/parse";

    /*
     * 账号：uid  配置文件
     */
    @Value("${resumeParser.uid}")
    private String resumeParserUid;

    private static String uid;

    /*
     * 密码：pwd 配置文件
     */
    @Value("${resumeParser.pwd}")
    private String resumeParserPwd;

    private static String pwd;

    @PostConstruct
    public void setUid(){
        uid = this.resumeParserUid;
        pwd = this.resumeParserPwd;
    }


    /**
     * 官网SaaS 版本接口【SaaS官方版本 - 正式应用】
     *
     * isUrl: true 基于简历url解析  false 基于简历解析
     *
     * 请求接口格式：（更详细可参考官网：https://www.resumesdk.com/docs/rs-parser.html#reqType）
     * - uid: 必填，用户id；
     * - pwd: 必填，用户密码；
     * - file_name: 必填，简历文件名（请确保后缀正确）；
     * - file_cont: 必填，经based64编码的简历文件内容；
     * - need_avatar: 可选，是否需要解析头像，0为不需要，1为需要，默认为0；
     * - ocr_type: 可选，ocr（图片解析所用到的文字识别）类型，0为百度ocr，1为腾讯ocr（需要注册并配置百度或者腾讯的OCR服务）。腾讯ocr的效果更佳，默认为0。
     * - need_social_exp: 可选，是否需要解析实践经历，0为不需要，1为需要，默认为0；
     */
    public static JSONObject resumeParser(String fname,boolean isUrl) {
        try {
            // 设置头字段
            HttpPost httpPost = new HttpPost(SAAS_API_URL);
            httpPost.addHeader("content-type", "application/json");

            // 读取简历内容
            byte[] bytes = null;
            String data = "";
            if(!isUrl) {
                bytes = FileUtils.readFileToByteArray(new File(fname));
                data = new String(Base64.encodeBase64(bytes), Consts.UTF_8);
            }

            // 设置请求接口信息
            JSONObject json = new JSONObject();
            if(isUrl){
                json.put("file_url", fname);    // 基于简历url解析
            }else{
                json.put("file_name", fname);   // 文件名
                json.put("file_cont", data);    // 经base64编码过的文件内容
            }
            json.put("uid", uid);            // 用户id
            json.put("pwd", pwd);            // 用户密码
            json.put("need_avatar", 0);
            json.put("ocr_type", 1);
            StringEntity params = new StringEntity(json.toString(), Consts.UTF_8);
            httpPost.setEntity(params);

            // 发送请求
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httpPost);

            // 处理返回结果
            String resCont = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
            JSONObject res = new JSONObject(resCont);

            //日志
            log.info("【简历解析】文件名："+fname +" \n 返回结果：" +resCont);
            return res;

        }catch (Exception ex){
            log.info("【简历解析】文件名："+fname +" \n 异常信息：" +ex.getMessage());
            return null;
        }
    }
}
