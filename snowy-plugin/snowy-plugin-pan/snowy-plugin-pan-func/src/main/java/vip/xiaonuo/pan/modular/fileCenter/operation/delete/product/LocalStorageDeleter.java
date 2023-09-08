package vip.xiaonuo.pan.modular.fileCenter.operation.delete.product;

import org.springframework.stereotype.Component;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.pan.modular.fileCenter.operation.delete.Deleter;
import vip.xiaonuo.pan.modular.fileCenter.operation.delete.domain.DeleteFile;
import vip.xiaonuo.pan.modular.fileCenter.util.UFOPUtils;

import java.io.File;

@Component
public class LocalStorageDeleter extends Deleter {
    @Override
    public void delete(DeleteFile deleteFile) {
        File localSaveFile = UFOPUtils.getLocalSaveFile(deleteFile.getFileUrl());
        if (localSaveFile.exists()) {
            boolean result = localSaveFile.delete();
            if (!result) {
                throw new CommonException("删除本地文件失败");
            }
        }

        deleteCacheFile(deleteFile);
    }
}
