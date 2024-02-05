package vip.xiaonuo.pan.modular.file.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ZipDownloadBean {

    private String path;

    private Long fileLength;

    private String absolutePath;

    private String fileName;//真实文件名称

    public ZipDownloadBean(){}

    public ZipDownloadBean(Long fileLength,String absolutePath,String path){
        this.fileLength = fileLength;
        this.absolutePath = absolutePath;
        this.path = path;
    }
}
