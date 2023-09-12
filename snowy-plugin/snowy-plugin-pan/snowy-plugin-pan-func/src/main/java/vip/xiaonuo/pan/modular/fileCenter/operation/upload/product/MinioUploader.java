package vip.xiaonuo.pan.modular.fileCenter.operation.upload.product;

import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.modular.file.util.DevFileMinIoUtil;
import vip.xiaonuo.pan.modular.fileCenter.enums.StorageTypeEnum;
import vip.xiaonuo.pan.modular.fileCenter.enums.UploadFileStatusEnum;
import vip.xiaonuo.pan.modular.fileCenter.operation.upload.Uploader;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import vip.xiaonuo.pan.modular.fileCenter.operation.upload.domain.UploadFile;
import vip.xiaonuo.pan.modular.fileCenter.operation.upload.domain.UploadFileResult;
import vip.xiaonuo.pan.modular.fileCenter.operation.upload.request.MultipartFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;
import vip.xiaonuo.pan.modular.fileCenter.util.RedisUtil;

@Slf4j
@Component
public class MinioUploader extends Uploader {

    @Resource
    RedisUtil redisUtil;

//    @Resource
//    MinioClient minioClient = DevFileMinIoUtil.getClient();

    @Override
    public void cancelUpload(UploadFile uploadFile) {
    }

    @Override
    protected void doUploadFileChunk(MultipartFile multipartFile, UploadFile uploadFile) {

    }

    @Override
    protected UploadFileResult organizationalResults(MultipartFile multipartFile, UploadFile uploadFile) {
        return null;
    }

    protected UploadFileResult doUploadFlow(MultipartFile multipartFile, UploadFile uploadFile) {
        UploadFileResult uploadFileResult = new UploadFileResult();
        try {
            multipartFile.getFileUrl(uploadFile.getIdentifier());
            String fileUrl = FileCenterUtil.getUploadFileUrl(uploadFile.getIdentifier(), multipartFile.getExtendName());

            File tempFile =  FileCenterUtil.getTempFile(fileUrl);
            File processFile = FileCenterUtil.getProcessFile(fileUrl);

            byte[] fileData = multipartFile.getUploadBytes();

            writeByteDataToFile(fileData, tempFile, uploadFile);

            //判断是否完成文件的传输并进行校验与重命名
            boolean isComplete = checkUploadStatus(uploadFile, processFile);
            uploadFileResult.setFileUrl(fileUrl);
            uploadFileResult.setFileName(multipartFile.getFileName());
            uploadFileResult.setExtendName(multipartFile.getExtendName());
            uploadFileResult.setFileSize(uploadFile.getTotalSize());
            uploadFileResult.setStorageType(StorageTypeEnum.MINIO);

            if (uploadFile.getTotalChunks() == 1) {
                uploadFileResult.setFileSize(multipartFile.getSize());
            }
            uploadFileResult.setIdentifier(uploadFile.getIdentifier());
            if (isComplete) {

                minioUpload(fileUrl, tempFile, uploadFile);
                uploadFileResult.setFileUrl(fileUrl);
                tempFile.delete();

                if (FileCenterUtil.isImageFile(uploadFileResult.getExtendName())) {
                    InputStream inputStream = null;
                    try {
                        MinioClient minioClient = DevFileMinIoUtil.getClient();

                        inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(Uploader.defaultBucketName).object(uploadFileResult.getFileUrl()).build());

                        BufferedImage src  = ImageIO.read(inputStream);
                        uploadFileResult.setBufferedImage(src);
                    } catch (IOException | InternalException | XmlParserException | InvalidResponseException | InvalidKeyException | NoSuchAlgorithmException | ErrorResponseException | InsufficientDataException | ServerException e) {
                        e.printStackTrace();
                    } finally {
                        IOUtils.closeQuietly(inputStream);
                    }

                }

                uploadFileResult.setStatus(UploadFileStatusEnum.SUCCESS);
            } else {
                uploadFileResult.setStatus(UploadFileStatusEnum.UNCOMPLATE);
            }
        } catch (IOException e) {
            throw new CommonException(e.getMessage());
        }


        return uploadFileResult;
    }


    private void minioUpload(String fileUrl, File file,  UploadFile uploadFile) {
        InputStream inputStream = null;
        try {
            // 检查存储桶是否已经存在
            boolean isExist = DevFileMinIoUtil.getClient().bucketExists(BucketExistsArgs.builder().bucket(Uploader.defaultBucketName).build());
            if(!isExist) {
                DevFileMinIoUtil.getClient().makeBucket(MakeBucketArgs.builder().bucket(Uploader.defaultBucketName).build());
            }

            inputStream = new FileInputStream(file);
            DevFileMinIoUtil.getClient().putObject(
                    PutObjectArgs.builder().bucket(Uploader.defaultBucketName).object(fileUrl).stream(
                                    inputStream, uploadFile.getTotalSize(), 1024 * 1024 * 5)
//                            .contentType("video/mp4")
                            .build());
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

    }


}
