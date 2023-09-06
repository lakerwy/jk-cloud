package vip.xiaonuo.pan.modular.file.service;

import vip.xiaonuo.pan.modular.file.param.MultipartFileParam;

import java.io.IOException;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/4 17:38
 * 文件存储操作
 */
public interface StorageService {

    /**
     * 删除全部数据
     */
    void deleteAll();

    /**
     * 初始化方法
     */
    void init();


    /**
     * 上传文件方法1
     *
     * @param param
     * @throws IOException
     */
    void uploadFileRandomAccessFile(MultipartFileParam param) throws IOException;

    /**
     * 上传文件方法2
     * 处理文件分块，基于MappedByteBuffer来实现文件的保存
     *
     * @param param
     * @throws IOException
     */
    void uploadFileByMappedByteBuffer(MultipartFileParam param) throws IOException;

    List<String> getMissChunk(String pathValue);
}
