package vip.xiaonuo.dev.modular.file.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import static java.util.Objects.isNull;


public class ZipExtractor {

    /**
     * @Description 解压缩zip(多级目录) ZipInputStream和fileOutputStream
     * @param zipFilePath
     **/
    public static void zipDecompress(String zipFilePath) {
        long startTime = System.currentTimeMillis();
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath));
            File parentPath = new File(System.getProperty("user.dir"));  //解压到当前目录
            //如果要新建父目录，可用这个
//             File parentPath=new File(zipFilePath.replace(".zip",""));
//            if(!parentPath.exists()){
//                parentPath.mkdir();
//            }
            ZipEntry entry = zis.getNextEntry();
            FileOutputStream fos = null;

            while (entry != null) {
                File file = new File(parentPath, entry.getName());
                System.out.println("正在解压缩: "+entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    //输出文件
                    fos = new FileOutputStream(file);
                    int len = 0;
                    byte[] butter = new byte[1024];
                    while ((len = zis.read(butter)) > 0) {
                        fos.write(butter, 0, len);
                    }
                }
                entry = zis.getNextEntry();
            }
            fos.close();
            zis.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗费时间： " + (endTime - startTime) + " ms");
    }

    public static void main(String[] args) throws Exception {
        String zipFilePath="C:\\Users\\Administrator\\Desktop\\111\\test.zip";
        zipDecompress(zipFilePath);
        //compressToZip("D:\\java_projectsdk\\maventest\\test\\");
    }
}
