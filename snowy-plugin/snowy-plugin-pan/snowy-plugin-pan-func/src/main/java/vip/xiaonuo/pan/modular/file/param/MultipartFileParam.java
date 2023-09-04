package vip.xiaonuo.pan.modular.file.param;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/4 17:39
 */
@Setter
@Getter
public class MultipartFileParam {

    //任务ID
    private String id;
    //总分片数量
    private int chunks;
    //当前为第几块分片
    private int chunk;
    //当前分片大小
    private long size = 0L;
    //文件名
    private String name;
    //分片对象
    private MultipartFile file;
    // MD5
    private String md5;
}
