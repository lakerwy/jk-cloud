package vip.xiaonuo.dev.modular.file.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.exception.CommonException;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.*;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Component
@Slf4j
public class UnRarZip7Z {
    /**
     * rar解压缩
     * @param rarFile    rar文件的全路径
     * @param outPath  解压路径
     * @return  压缩包中所有的文件
     */
    public void unRarZip7Z(String rarFile, String outPath) throws Exception {
        if (rarFile.toLowerCase().endsWith(".zip")) {
            //unZip(rarFile, outPath, false);
            String command = "unzip "+rarFile+" -d  "+outPath+"/";
            String host = InetAddress.getLocalHost().getHostAddress();
            unzip(host,"root","Jianke66",command);
        } else if (rarFile.toLowerCase().endsWith(".rar")) {
            unRar(rarFile, outPath, "");
        } else if (rarFile.toLowerCase().endsWith(".7z")) {
            un7z(rarFile, outPath);
        }
    }


    /**
     * rar解压缩
     * @param rarFile    rar文件的全路径
     * @param outPath  解压路径
     * @return  压缩包中所有的文件
     */
    private void unRar(String rarFile, String outPath, String passWord) {
        Map<String,String> map = new HashMap<>();
        RandomAccessFile randomAccessFile = null;
        IInArchive inArchive = null;
        try {
            // 第一个参数是需要解压的压缩包路径，第二个参数参考JdkAPI文档的RandomAccessFile
            randomAccessFile = new RandomAccessFile(rarFile, "r");
            if (StringUtils.isNotEmpty(passWord)) {
                inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile), passWord);
            } else {
                inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile));
            }
            ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();
            for (final ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()) {
                final int[] hash = new int[]{0};
                if (!item.isFolder()) {
                    ExtractOperationResult result;
                    final long[] sizeArray = new long[1];

                    File outFile = new File(outPath + File.separator+ item.getPath());
                    File parent = outFile.getParentFile();
                    if ((!parent.exists()) && (!parent.mkdirs())){ continue;}
                    if (StringUtils.isNotBlank(passWord)) {
                        result = item.extractSlow(data -> {
                            try {
                                IOUtils.write(data, new FileOutputStream(outFile, true));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            hash[0] ^= Arrays.hashCode(data); // Consume data
                            sizeArray[0] += data.length;
                            return data.length; // Return amount of consumed
                        }, passWord);
                    } else {
                        result = item.extractSlow(data -> {
                            try {
                                IOUtils.write(data, new FileOutputStream(outFile, true));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            hash[0] ^= Arrays.hashCode(data); // Consume data
                            sizeArray[0] += data.length;
                            return data.length; // Return amount of consumed
                        });
                    }
                    if (result == ExtractOperationResult.OK) {
                        log.info("解压rar成功...." + String.format("%9X | %10s | %s", hash[0], sizeArray[0], item.getPath()));
                    } else if (StringUtils.isNotBlank(passWord)) {
                        log.error("解压rar成功：密码错误或者其他错误...." + result);
                    } else {
                        log.error("解压rar失败：" + result);;
                    }
                }
            }
        } catch (Exception e) {
            log.error("unRar error", e);
        } finally {
            try {
                inArchive.close();
                randomAccessFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取压缩包中的全部文件
     * @param path    文件夹路径
     * @childName   每一个文件的每一层的路径==D==区分层数
     * @return  压缩包中所有的文件
     */
    private Map<String, String> getFileNameList(String path, String childName) {
        log.info("解压文件路径:" + path + "---childName:"+childName);
        Map<String, String> files = new HashMap<>();
        File file = new File(path); // 需要获取的文件的路径
        String[] fileNameLists = file.list(); // 存储文件名的String数组
        File[] filePathLists = file.listFiles(); // 存储文件路径的String数组
        for (int i = 0; i < filePathLists.length; i++) {
            if (filePathLists[i].isFile()) {
                files.put(String.valueOf(i), path + File.separator + filePathLists[i].getName());
            } else {
                files.putAll(getFileNameList(path + File.separator + filePathLists[i].getName(), childName + "&" + filePathLists[i].getName()));
            }
        }
        return files;
    }

    /**
     * zip解压缩
     * @param zipFilePath            zip文件的全路径
     * @param unzipFilePath            解压后文件保存路径
     * @param includeZipFileName    解压后文件是否包含压缩包文件名，true包含，false不包含
     * @return 压缩包中所有的文件
     */
    public void unzip(String host,String name,String password,String command){
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(name, host, 22);
            session.setPassword(password);
            // Optional, but recommended
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) continue;
                    log.info("zip解压："+command+",exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {}
            }
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void unZip(String zipFilePath, String unzipFilePath, boolean includeZipFileName) throws Exception{
        File zipFile = new File(zipFilePath);

        //如果包含压缩包文件名，则处理保存路径
        if(includeZipFileName){
            String fileName = zipFile.getName();
            if(StringUtils.isNotEmpty(fileName)){
                fileName = fileName.substring(0,fileName.lastIndexOf("."));
            }
            unzipFilePath = unzipFilePath + File.separator + fileName;
        }

        //判断保存路径是否存在，不存在则创建
        File unzipFileDir = new File(unzipFilePath);
        if(!unzipFileDir.exists() || !unzipFileDir.isDirectory()){
            unzipFileDir.mkdirs();
        }

        //开始解压
        ZipEntry entry = null;
        String entryFilePath = null, entryDirPath = "";
        File entryFile = null, entryDir = null;
        int index = 0, count = 0, bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        Charset gbk = Charset.forName("GBK");
        ZipFile zip = new ZipFile(zipFile, gbk);
        try {
            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
            while (entries.hasMoreElements()) {
                entry = entries.nextElement();
                entryFilePath = unzipFilePath + File.separator + entry.getName();
                entryFilePath = entryFilePath.replace("/", File.separator);
                index = entryFilePath.lastIndexOf(File.separator);
                if (index != -1) {
                    entryDirPath = entryFilePath.substring(0, index);
                }
                entryDir = new File(entryDirPath);
                if (!entryDir.exists() || !entryDir.isDirectory()) {
                    entryDir.mkdirs();
                }
                entryFile = new File(entryFilePath);
                //判断当前文件父类路径是否存在，不存在则创建
                if(!entryFile.getParentFile().exists() || !entryFile.getParentFile().isDirectory()){
                    entryFile.getParentFile().mkdirs();
                }
                //不是文件说明是文件夹创建即可，无需写入
                if(entryFile.isDirectory()){
                    continue;
                }
                bos = new BufferedOutputStream(new FileOutputStream(entryFile));
                bis = new BufferedInputStream(zip.getInputStream(entry));
                while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
                    bos.write(buffer, 0, count);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bos.flush();
            bos.close();
            bis.close();
            zip.close();
        }
    }

    /**
     * zip解压缩
     * @param zipFilePath            zip文件的全路径
     * @param includeZipFileName    解压后文件是否包含压缩包文件名，true包含，false不包含
     * @return 压缩包中所有的文件
     */
    public void unZip(String zipFilePath, boolean includeZipFileName) throws Exception{
        File zipFile = new File(zipFilePath);
        String unzipFilePath = zipFilePath.substring(0, zipFilePath.lastIndexOf(File.separator));

        //如果包含压缩包文件名，则处理保存路径
        if(includeZipFileName){
            String fileName = zipFile.getName();
            if(StringUtils.isNotEmpty(fileName)){
                fileName = fileName.substring(0,fileName.lastIndexOf("."));
            }
            unzipFilePath = unzipFilePath + File.separator + fileName;
        }

        //判断保存路径是否存在，不存在则创建
        File unzipFileDir = new File(unzipFilePath);
        if(!unzipFileDir.exists() || !unzipFileDir.isDirectory()){
            unzipFileDir.mkdirs();
        }
        //开始解压
        ZipEntry entry = null;
        String entryFilePath = null, entryDirPath = "";
        File entryFile = null, entryDir = null;
        int index = 0, count = 0, bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        Charset gbk = Charset.forName("GBK");
        ZipFile zip = new ZipFile(zipFile, gbk);
        try {
            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
            while (entries.hasMoreElements()) {
                entry = entries.nextElement();
                entryFilePath = unzipFilePath + File.separator + entry.getName();
                entryFilePath = entryFilePath.replace("/", File.separator);
                index = entryFilePath.lastIndexOf(File.separator);
                if (index != -1) {
                    entryDirPath = entryFilePath.substring(0, index);
                }
                entryDir = new File(entryDirPath);
                if (!entryDir.exists() || !entryDir.isDirectory()) {
                    entryDir.mkdirs();
                }
                entryFile = new File(entryFilePath);
                //判断当前文件父类路径是否存在，不存在则创建
                if(!entryFile.getParentFile().exists() || !entryFile.getParentFile().isDirectory()){
                    entryFile.getParentFile().mkdirs();
                }
                //不是文件说明是文件夹创建即可，无需写入
                if(entryFile.isDirectory()){
                    continue;
                }
                bos = new BufferedOutputStream(new FileOutputStream(entryFile));
                bis = new BufferedInputStream(zip.getInputStream(entry));
                while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
                    bos.write(buffer, 0, count);
                }
                bos.flush();
                bos.close();
                bis.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            zip.close();
        }
        //Map<String, String> resultMap = getFileNameList(unzipFilePath, "");
        //return resultMap;
    }

    /**
     * 7z解压缩
     * @param z7zFilePath    7z文件的全路径
     * @param outPath  解压路径
     * @return  压缩包中所有的文件
     */
    public  void un7z(String z7zFilePath, String outPath){
        SevenZFile zIn = null;
        try {
            File file = new File(z7zFilePath);
            zIn = new SevenZFile(file);
            SevenZArchiveEntry entry = null;
            File newFile = null;
            while ((entry = zIn.getNextEntry()) != null){
                //不是文件夹就进行解压
                if(!entry.isDirectory()){
                    newFile = new File(outPath, entry.getName());
                    if(!newFile.exists()){
                        new File(newFile.getParent()).mkdirs();   //创建此文件的上层目录
                    }
                    OutputStream out = new FileOutputStream(newFile);
                    BufferedOutputStream bos = new BufferedOutputStream(out);
                    int len = -1;
                    byte[] buf = new byte[(int)entry.getSize()];
                    while ((len = zIn.read(buf)) != -1){
                        bos.write(buf, 0, len);
                    }
                    bos.flush();
                    bos.close();
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (zIn != null) {
                    zIn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        try {
            String inputFile = "C:\\Users\\Administrator\\Desktop\\压缩测试\\Desktop.7z";
            String destDirPath = "C:\\Users\\Administrator\\Desktop\\压缩测试\\"+UUID.randomUUID().toString();
            String suffix = inputFile.substring(inputFile.lastIndexOf("."));
            //unRarZip7Z(inputFile, destDirPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
