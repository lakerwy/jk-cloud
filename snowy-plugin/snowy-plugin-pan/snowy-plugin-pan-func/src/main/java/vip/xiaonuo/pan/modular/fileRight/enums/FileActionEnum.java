package vip.xiaonuo.pan.modular.fileRight.enums;

import lombok.Getter;

@Getter
public enum FileActionEnum {
    /** 下载 */
    DOWNLOAD("下载"),

    /** 创建文件夹 */
    CREATE_FOLD("创建文件夹"),

    /** 编辑 */
    EDIT("编辑"),

    /** 移动 */
    MOVE("移动"),

    /** 删除 */
    DELETE("删除"),

    /** 预览 */
    PREVIEW("预览"),

    /** 上传 */
    UPLOAD("上传"),

    /** 复制 */
    COPY("复制"),

    /** 重命名 */
    RENAME("重命名"),

    /** 共享 */
    SHARE("共享"),

    /** 分享 */
    SHARE_LINK("分享"),

    /** 创建文件 */
    CREATE_FILE("创建文件"),

    /** 清空回收站 */
    RECOVERY("清空回收站"),

    /** 设置权限 */
    RESTORE("还原回收站");

    private final String value;

    FileActionEnum(String value) {
        this.value = value;
    }
}
