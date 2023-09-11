package vip.xiaonuo.pan.modular.fileCenter.operation.read.product;

import org.apache.commons.io.FilenameUtils;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.pan.modular.fileCenter.operation.read.Reader;
import vip.xiaonuo.pan.modular.fileCenter.operation.read.domain.ReadFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;
import vip.xiaonuo.pan.modular.fileCenter.util.ReadFileUtils;

import java.io.FileInputStream;
import java.io.IOException;

public class LocalStorageReader extends Reader {
    @Override
    public String read(ReadFile readFile) {

        String fileContent;
        try {
            String extendName = FilenameUtils.getExtension(readFile.getFileUrl());
            FileInputStream fileInputStream = new FileInputStream(FileCenterUtil.getStaticPath() + readFile.getFileUrl());
            fileContent = ReadFileUtils.getContentByInputStream(extendName, fileInputStream);
        } catch (IOException e) {
            throw new CommonException("文件读取出现异常", e);
        }
        return fileContent;
    }
}
