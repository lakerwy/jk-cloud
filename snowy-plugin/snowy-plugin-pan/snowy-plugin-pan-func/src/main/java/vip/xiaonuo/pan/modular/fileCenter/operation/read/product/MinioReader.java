package vip.xiaonuo.pan.modular.fileCenter.operation.read.product;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.modular.file.util.DevFileMinIoUtil;
import vip.xiaonuo.pan.modular.fileCenter.operation.read.Reader;
import vip.xiaonuo.pan.modular.fileCenter.operation.read.domain.ReadFile;
import vip.xiaonuo.pan.modular.fileCenter.util.ReadFileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class MinioReader extends Reader {

    @Override
    public String read(ReadFile readFile) {
        String fileUrl = readFile.getFileUrl();
        String fileType = FilenameUtils.getExtension(fileUrl);
        try {
            return ReadFileUtils.getContentByInputStream(fileType, getInputStream(readFile.getFileUrl()));
        } catch (IOException e) {
            throw new CommonException("读取文件失败", e);
        }
    }

    protected InputStream getInputStream(String fileUrl) {
        InputStream inputStream = null;
        try {

            MinioClient minioClient = DevFileMinIoUtil.getClient();

            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(DevFileMinIoUtil.getDefaultBucketName()).object(fileUrl).build());


        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error(e.getMessage());
        }


        return inputStream;
    }


}
