package vip.xiaonuo.pan.modular.fileCenter.operation.delete;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import vip.xiaonuo.pan.modular.fileCenter.operation.delete.domain.DeleteFile;
import vip.xiaonuo.pan.modular.fileCenter.util.UFOPUtils;

import java.io.File;

@Slf4j
public abstract class Deleter {
    public abstract void delete(DeleteFile deleteFile);

    protected void deleteCacheFile(DeleteFile deleteFile) {
        if (UFOPUtils.isImageFile(FilenameUtils.getExtension(deleteFile.getFileUrl()))) {
            File cacheFile = UFOPUtils.getCacheFile(deleteFile.getFileUrl());
            if (cacheFile.exists()) {
                boolean result = cacheFile.delete();
                if (!result) {
                    log.error("删除本地缓存文件失败！");
                }
            }
        }
    }
}
