package vip.xiaonuo.dev.modular.file.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class DevFileVo implements Serializable {
    private static final long serialVersionUID = -1852052280814556913L;

    private String name;

    private String url;

    private String fileId;

    private String sizeKb;

    private String sizeInfo;

    public DevFileVo(String name, String url, String fileId, String sizeKb, String sizeInfo) {
        this.name = name;
        this.url = url;
        this.fileId = fileId;
        this.sizeKb = sizeKb;
        this.sizeInfo = sizeInfo;
    }

    public DevFileVo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getSizeKb() {
        return sizeKb;
    }

    public void setSizeKb(String sizeKb) {
        this.sizeKb = sizeKb;
    }

    public String getSizeInfo() {
        return sizeInfo;
    }

    public void setSizeInfo(String sizeInfo) {
        this.sizeInfo = sizeInfo;
    }
}
