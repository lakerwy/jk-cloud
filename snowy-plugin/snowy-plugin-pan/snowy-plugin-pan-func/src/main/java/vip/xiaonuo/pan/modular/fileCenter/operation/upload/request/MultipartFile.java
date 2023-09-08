package vip.xiaonuo.pan.modular.fileCenter.operation.upload.request;

import org.apache.commons.io.FilenameUtils;
import vip.xiaonuo.pan.modular.fileCenter.util.UFOPUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class MultipartFile {

    org.springframework.web.multipart.MultipartFile multipartFile = null;

    private MultipartFile() {
    }

    public MultipartFile(org.springframework.web.multipart.MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public String getFileName() {
        String originalName = getMultipartFile().getOriginalFilename();
        if (!originalName.contains(".")) {
            return originalName;
        }
        return originalName.substring(0, originalName.lastIndexOf("."));
    }

    public String getExtendName() {
        String originalName = getMultipartFile().getOriginalFilename();
        return FilenameUtils.getExtension(originalName);
    }

    public String getFileUrl() {
        String uuid = UUID.randomUUID().toString();
        return UFOPUtils.getUploadFileUrl(uuid, getExtendName());
    }

    public String getFileUrl(String identify) {
        return UFOPUtils.getUploadFileUrl(identify, getExtendName());
    }

    public InputStream getUploadInputStream() throws IOException {
        return getMultipartFile().getInputStream();
    }

    public byte[] getUploadBytes() throws IOException {
        return getMultipartFile().getBytes();
    }

    public long getSize() {
        return getMultipartFile().getSize();
    }

    public org.springframework.web.multipart.MultipartFile getMultipartFile() {
        return multipartFile;
    }

}
