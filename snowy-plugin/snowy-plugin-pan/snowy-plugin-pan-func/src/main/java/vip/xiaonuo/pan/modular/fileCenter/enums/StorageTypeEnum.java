package vip.xiaonuo.pan.modular.fileCenter.enums;


public enum StorageTypeEnum {
    LOCAL(0, "本地存储"),
    ALIYUN_OSS(1, "阿里云OSS对象存储"),
    MINIO(3, "minio存储");
    private final int code;
    private final String name;

    StorageTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }


    public String getName() {
        return name;
    }

}
