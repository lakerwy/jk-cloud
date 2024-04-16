/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.core.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.Deflater;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;



/**
 * 文件下载工具类，使用本类前，对参数校验的异常使用CommonResponseUtil.renderError()方法进行渲染
 *
 * @author xuyuxiang
 * @date 2020/8/5 21:45
 */
@Slf4j
public class CommonDownloadUtil {

    /**
     * 下载文件
     *
     * @param file     要下载的文件
     * @param response 响应
     * @author xuyuxiang
     * @date 2020/8/5 21:46
     */
    public static void download(File file, HttpServletResponse response) {
        download(file.getName(), FileUtil.readBytes(file), response);
    }

    /**
     * 下载文件
     *
     * @author xuyuxiang
     * @date 2022/7/31 10:57
     */
    public static void download(String fileName, byte[] fileBytes, HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLUtil.encode(fileName));
            response.addHeader("Content-Length", "" + fileBytes.length);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setContentType("application/octet-stream;charset=UTF-8");
            outputStream = response.getOutputStream();
            IoUtil.write(outputStream, true, fileBytes);
        }catch (ClientAbortException e){
            // 客户端已经关闭连接，这是一个正常的情况，无需记录错误信息
            log.debug("客户端连接已关闭");
        } catch (IOException e) {
            log.error(">>> 文件下载异常：", e);
        }finally {
            if(null!=outputStream){
                try {
                    outputStream.close();
                }catch (IOException e){
                    log.error("关闭输出流异常",e);
                }
            }
        }
    }

    /**
     * 批量下载下载文件
     *
     * @author ljy
     * @date 2024/4/11 16:57
     */
    public static void zipFilesAndDownload(HttpServletResponse response, String zipFileName,List<DownloadCompressedVo> downloadCompressedVoList) throws IOException {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeFileName(zipFileName) + "\"");

        try (ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(response.getOutputStream())) {
           for(DownloadCompressedVo vo : downloadCompressedVoList){
               addFileToZip(vo,zipOut);
           }
        }
    }
    private static void addFileToZip(DownloadCompressedVo downloadCompressedVo, ZipArchiveOutputStream zipOut) throws IOException {
        // 修改文件名
        String newFileName = downloadCompressedVo.getFileName();

        ZipArchiveEntry zipEntry = new ZipArchiveEntry(newFileName);
        zipOut.putArchiveEntry(zipEntry);

        try (FileInputStream fis = new FileInputStream(downloadCompressedVo.getFile())) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zipOut.write(buffer, 0, length);
            }
        }
        zipOut.closeArchiveEntry();
    }
    private static String encodeFileName(String fileName) throws UnsupportedEncodingException {
        return URLEncoder.encode(fileName, "UTF-8");
    }
}
