package vip.xiaonuo.pan.modular.file.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import javax.servlet.http.*;

@Component
@Slf4j
public class DownloadZipUtil {
    public static void doGet(HttpServletResponse response,String filePath,String fileName) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "文件未找到！");//文件不存在时可以返回错误信息或重定向
            return;
        }
        response.setContentType("application/zip"); // 设置响应头，指示浏览器以附件形式下载并提供文件名
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        // 创建输入流读取文件
        InputStream inStream = new FileInputStream(file);

        try (OutputStream outStream = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;

            // 将文件内容写入HTTP响应输出流
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            // 清空缓冲区并确保所有数据发送完毕
            outStream.flush();
        } finally {
            inStream.close();
        }
    }

}
