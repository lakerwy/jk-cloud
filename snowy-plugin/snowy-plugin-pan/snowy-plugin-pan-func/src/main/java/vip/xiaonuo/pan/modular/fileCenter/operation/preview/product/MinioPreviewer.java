package vip.xiaonuo.pan.modular.fileCenter.operation.preview.product;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import vip.xiaonuo.dev.modular.file.util.DevFileMinIoUtil;
import vip.xiaonuo.pan.modular.fileCenter.domain.ThumbImage;
import vip.xiaonuo.pan.modular.fileCenter.operation.preview.Previewer;
import vip.xiaonuo.pan.modular.fileCenter.operation.preview.domain.PreviewFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Getter
@Setter
@Slf4j
public class MinioPreviewer extends Previewer {

    public MinioPreviewer(ThumbImage thumbImage) {
        setThumbImage(thumbImage);
    }

    @Override
    protected InputStream getInputStream(PreviewFile previewFile) {
        InputStream inputStream = null;
        try {

            MinioClient minioClient = DevFileMinIoUtil.getClient();
            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(DevFileMinIoUtil.getDefaultBucketName()).object(previewFile.getFileUrl()).build());


        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error(e.getMessage());
        }


        return inputStream;
    }


}
