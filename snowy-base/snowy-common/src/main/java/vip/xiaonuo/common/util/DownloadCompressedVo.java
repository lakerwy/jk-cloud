package vip.xiaonuo.common.util;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
public class DownloadCompressedVo {

    private String fileName;
    private File file;
    private String engine;

    public DownloadCompressedVo(){};

    public DownloadCompressedVo(String fileName,File file){
        this.file = file;
        this.fileName = fileName;
    };

    public DownloadCompressedVo(String fileName,File file,String engine){
        this.file = file;
        this.fileName = fileName;
        this.engine = engine;
    };
}
