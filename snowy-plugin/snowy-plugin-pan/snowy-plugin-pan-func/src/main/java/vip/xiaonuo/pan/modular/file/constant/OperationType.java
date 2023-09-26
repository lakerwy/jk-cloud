package vip.xiaonuo.pan.modular.file.constant;

public enum OperationType {

    // 文件夹权限
    /**
     * 列表/
     */
    LIST,
    /** 可见 */
    SHOW,
    /** 文件可见 */
    SHOW_FILE,
    /**
     * 下载
     */
    DOWNLOAD,

    /**
     * 创建-创建文件夹
     */
    CREATE,

    /**
     * 移动
     */
    MOVE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 上传
     */
    UPLOAD,

    /**
     * 复制
     */
    COPY,

    /**
     * 收藏
     */
    COLLECT,

    /**
     * 删除恢复
     */
    DELETE_RECOVERY,

    /**
     * 完全控制
     */
    MANAGE_DIR,

    /**
     * 重命名
     */
    RENAME,

    /**
     * 同步
     */
    SYNC,

    // file right type 2
    /**
     * 预览
     */
    PREVIEW,

    /**
     * 创建者可见
     */
    CREATOR_VISIABLE,

    /**
     * 重命名
     */
    RENAME_FILE,

    /**
     * 编辑
     */
    EDIT,

    /**
     * 下载
     */
    DOWNLOAD_FILE,

    /**
     * 移动
     */
    MOVE_FILE,

    /**
     * 复制
     */
    COPY_FILE,

    /**
     * 删除
     */
    DELETE_FILE,

    /**
     * 收藏
     */
    COLLECT_FILE,

    /**
     * 打印
     */
    PRINT,

    /**
     * 锁定/解锁
     */
    LOCK,

    /**
     * 文件恢复
     */
    DELETE_RECOVERY_FILE,

    /**
     * 完全控制
     */
    MANAGE_FILE,

    /**
     * 禁止访问
     */
    FORBIDDEN,

    /**
     * 个人标签
     */
    PERSON_LABLE,

    /**
     * 群组标签
     */
    GROUP_LABLE,

    /**
     * 群组标签
     */
    FILE_CONTROL,

    /**
     * 群组标签
     */
    DIR_CONTROL,

    /**
     * 历史版本
     */
    HISTORY_VERSION,

    // operation right type 3

    /**
     * 生成外链
     */
    SHARE_LINK,

    /**
     * 发送同事
     */
    SHARE_SEND,

    /**
     * 创建协同
     */
    CREATE_COOPERATE,

    /**
     * 公开文件
     */
    PUBLIC_FILE,

    // other right type 4
    /**
     * 访问（老权限兼容项）
     */
    ACCESS,

    /**
     * 管理员（老权限兼容项）
     */
    ADMIN,

    /**
     * 所有权限
     */
    ALL_RIGHT,

    /**
     * 文件订阅
     */
    SUBSCRIBE_FILE,

    /**
     * 文件夹订阅
     */
    SUBSCRIBE_DIR,
    /**
     * 文件：完全控制
     */
    FILE_CONTRIL,
    /**
     * 文件夹：完全控制
     */
    DIR_CONTRIL,
    /**
     * 外链保存
     */
    SHARE_SEND_SAVE,
    /**
     * 外链保存
     */
    SHARE_SEND_SAVE_FILE,
    /**
     * 外链保存
     */
    SHARE_LINK_SAVE,
    /**
     * 外链保存
     */
    SHARE_LINK_SAVE_FILE;

    public static OperationType convertFileOp(OperationType op) {
        switch (op) {
            case RENAME:
                return OperationType.RENAME_FILE;
            case MOVE:
                return OperationType.MOVE_FILE;
            case COPY:
                return OperationType.COPY_FILE;
            case DELETE:
                return OperationType.DELETE_FILE;
            case DELETE_RECOVERY:
                return OperationType.DELETE_RECOVERY_FILE;
            case SHARE_SEND_SAVE:
                return OperationType.SHARE_SEND_SAVE_FILE;
            case SHARE_LINK_SAVE:
                return OperationType.SHARE_LINK_SAVE_FILE;
            default:
                return op;
        }

    }
}
