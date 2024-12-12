package vip.xiaonuo.biz.modular.recruit.utils;

import cn.hutool.json.JSONObject;
import com.fhs.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.binary.Base64;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author yong.ye
 * @date 2023/8/23 10:36
 *
 * 简历解析工具类
 */

@Slf4j
@Component
public class ResumeParserUtil {
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
     * 请求接口格式：（更详细可参考官网：https://www.resumesdk.com/docs/rs-parser.html#reqType）
     * - uid: 必填，用户id；
     * - pwd: 必填，用户密码；
     * - file_name: 必填，简历文件名（请确保后缀正确）；
     * - file_cont: 必填，经based64编码的简历文件内容；
     * - need_avatar: 可选，是否需要解析头像，0为不需要，1为需要，默认为0；
     * - ocr_type: 可选，ocr（图片解析所用到的文字识别）类型，0为百度ocr，1为腾讯ocr（需要注册并配置百度或者腾讯的OCR服务）。腾讯ocr的效果更佳，默认为0。
     * - need_social_exp: 可选，是否需要解析实践经历，0为不需要，1为需要，默认为0；
     */
    public static JSONObject resumeParser(String fname,String furl) {
        try {
            // 设置头字段
            HttpPost httpPost = new HttpPost(SAAS_API_URL);
            httpPost.addHeader("content-type", "application/json");

            // 读取简历内容
            byte[] bytes = download(furl);
            String data = new String(Base64.encodeBase64(bytes), Consts.UTF_8);

            // 设置请求接口信息
            JSONObject json = new JSONObject();
            json.put("file_name", fname);   // 文件名
            json.put("file_cont", data);     // "https://y2.hbjkjt.com/api/webapp/dev/file/download?id=202308251090"
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
            log.info("【简历解析】文件名："+ fname +" \n 返回结果：" +resCont);
            return res;

        }catch (Exception ex){
            log.info("【简历解析】文件名："+ fname +" \n 异常信息：" +ex.getMessage());
            return null;
        }
    }


    public static byte[] download(String fileUrl) {
        try {
            // 开启URL链接
            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();
            //输出流
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // 结果
            byte[] result = null;
            // 读取
           try(InputStream inputStream = connection.getInputStream()) {
               byte[] buffer = new byte[4096];
               int bytesRead;
               while ((bytesRead = inputStream.read(buffer)) != -1) {
                   outputStream.write(buffer, 0, bytesRead);
               }
               result = outputStream.toByteArray();
           }
            outputStream.close();
            return result;
        }catch (Exception ex){
            log.info("【下载文件】URL："+ fileUrl +" \n 异常信息：" +ex.getMessage());
            return null;
        }
    }
}
