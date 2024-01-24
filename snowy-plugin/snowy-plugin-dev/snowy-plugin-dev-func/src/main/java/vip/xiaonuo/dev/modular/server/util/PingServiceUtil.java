package vip.xiaonuo.dev.modular.server.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Component
public class PingServiceUtil {
    public boolean pingUrl(String url, int timeoutMillis) {
        try {
            URL siteUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteUrl.openConnection();
            connection.setConnectTimeout(timeoutMillis); // 设置连接超时时间
            connection.setRequestMethod("GET"); // 发送GET请求
            connection.connect(); // 连接到URL并获取响应码
            int responseCode = connection.getResponseCode();
            return responseCode >= 200 && responseCode < 300; // 检查响应状态码是否表示成功
        } catch (IOException e) { // 如果出现网络错误，则视为无法连接
            log.error("连接失败！e:"+e.getMessage());
            return false;
        }
    }
}
