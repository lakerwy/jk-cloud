package vip.xiaonuo.pan.modular.file.constant;

public enum FileOperation {

    /**
     * 类型标识：文件预览操作<br>
     */
    FILE_VIEW,

    /**
     * 类型标识：重命名文件<br>
     */
    FILE_RENAME,

    /**
     * 类型标识：编辑文件<br>
     */
    FILE_EDIT,

    /**
     * 类型标识：请求文件上传<br>
     */
    REQUEST_UPLOAD,

    /**
     * 类型标识：请求文件下载<br>
     */
    REQUEST_DWONLOAD,

    /**
     * 类型标识：文件同步/目录<br>
     */
    FILE_SYNC,

    /**
     * 类型标识：创建目录/文件<br>
     */
    FILE_CREATE,

    /**
     * 类型标识：复制文件/目录<br>
     */
    FILE_COPY,

    /**
     * 类型标识：移动文件/目录<br>
     */
    FILE_MOVE,

    /**
     * 类型标识：获取目录的下级列表<br>
     */
    FILE_LIST,

    /**
     * 类型标识：删除文件记录<br>
     */
    FILE_DELETE,

    /*---------------------------*/
    /**
     * 类型标识：生成文件外链<br>
     */
    FILE_SHARE,

    /**
     * 类型标识：锁定文件<br>
     */
    FILE_LOCK,

    /**
     * 类型标识：搜索文件<br>
     */
    SEARCH_FILE,

    /**
     * 类型标识：全文检索
     */
    FILE_FULLTEXTSEARCH,

    /**
     * 类型标识：个人文件收藏
     */
    FILE_FAVORITE
}
