package vip.xiaonuo.pan.modular.file.util;


import vip.xiaonuo.common.exception.CommonException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CommonZipUtil {
    public static Long unzip(List<String> sourceFile,String inputFile) throws IOException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);//初始化线程池
        //long start = System.currentTimeMillis();
        ZipOutputStream zops = new ZipOutputStream(Files.newOutputStream(Paths.get(inputFile))); //初始化ZIP输出流
        List<ZipDownloadBean> list = new ArrayList<>();
        sourceFile.forEach(sourcePath -> {
            File file = new File(sourcePath);
            getFileList(file, list);//递归读取源文件夹下所有文件，并记录其路径信息
        });
        CountDownLatch countDownLatch = new CountDownLatch(list.size()); //初始化计数器，线程等待

        Map<String,Long> sizeMap = new HashMap<>();
        sizeMap.put("fileSize",0l);
        Lock lock = new ReentrantLock();// 锁初始化
        for (ZipDownloadBean bean : list) {
            executorService.execute(() -> {
                try(InputStream ips = Files.newInputStream(Paths.get(bean.getPath()))) {
                    String relativePath = bean.getPath().substring(bean.getPath().lastIndexOf("CodeFormer\\") + 11);
                    String fileName = relativePath.contains("\\") ? relativePath.substring(relativePath.lastIndexOf("\\") + 1) : relativePath;
                    ZipEntry zipEntry = new ZipEntry(fileName);//初始化ZipEntry

                    lock.lock();
                    zops.putNextEntry(zipEntry);
                    byte[] bytes = new byte[bean.getFileLength() == null ? (1024*10) : bean.getFileLength().intValue()];
                    Long fileSize = bean.getFileLength();
                    sizeMap.put("fileSize",sizeMap.get("fileSize")+fileSize);
                    int len;
                    // 通过二进制流写文件
                    while ((len = ips.read(bytes)) > 0) {
                        zops.write(bytes, 0, len);
                    }
                    zops.closeEntry();// 关闭ZipEntry
                    lock.unlock();
                    zops.flush();
                } catch (Exception e) {
                    throw new CommonException(Thread.currentThread().getName() + "读取文件异常，文件路径：" + bean.getPath());
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        zops.close(); // 关闭流
        //long end = System.currentTimeMillis();
        //System.out.println("耗时：" + (end - start));
        executorService.shutdownNow();
        return sizeMap.get("fileSize") != null ? sizeMap.get("fileSize") : 0l;
    }

    /**
     * 递归获取文件及其路径信息
     *
     * @param file 文件对象
     * @param fileList 文件信息集合
     */
    private static void getFileList(File file, List<ZipDownloadBean> fileList){
        if (file == null) { return; }
        // 如果是文件夹，就继续递归
        if (file.isDirectory()) {
            for (File tempFile : Objects.requireNonNull(file.listFiles())) {
                getFileList(tempFile, fileList);
            }
        }
        // 如果是文件，就解析一下，放到文件集合中
        if (file.isFile()) {
            if(file.length() != 0){
                ZipDownloadBean bean = new ZipDownloadBean(null, file.getAbsolutePath(),file.getPath());
                bean.setFileLength(file.length());
                fileList.add(bean);
            }
        }
    }
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException{
        List<String> sourceFile = new ArrayList<>();
        sourceFile.add("C:\\Users\\Administrator\\Desktop\\测试文件");
        sourceFile.add("C:\\Users\\Administrator\\Desktop\\任务管理SQL.txt");
        String uuid = UUID.randomUUID().toString();
        String inputFile = "C:\\Users\\Administrator\\Desktop\\测试文件\\中文名称.zip";
        unzip(sourceFile,inputFile);
    }
}
