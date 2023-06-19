package vip.xiaonuo.dev.modular.file.entity;

import java.io.Serializable;

public class DevFileVo implements Serializable {
    private static final long serialVersionUID = -1852052280814556913L;

    private String name;

    private String url;

    private String fileId;

    public DevFileVo(String name, String url, String fileId) {
        this.name = name;
        this.url = url;
        this.fileId = fileId;
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
}
