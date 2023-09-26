package vip.xiaonuo.pan.modular.file.constant;


public enum FsEventType{

    /**
     * 类型标识：即将创建文件/目录<br>
     * 有效参数：fileInfo
     */
    PRE_CREATE,

    /**
     * 类型标识：创建文件/目录完毕<br>
     * 有效参数：fileInfo
     */
    POST_CREATE,

    /**
     * 类型标识：即将设置文件大小<br>
     * 有效参数：fileInfo, newSize
     */
    PRE_RESIZE,

    /**
     * 类型标识：设置文件大小完毕<br>
     * 有效参数：fileInfo, newSize
     */
    POST_RESIZE,

    /**
     * 类型标识：即将写入文件数据<br>
     * 有效参数：fileInfo
     */
    PRE_WRITE,

    /**
     * 类型标识：写入文件数据完毕<br>
     * 有效参数：fileInfo
     */
    POST_WRITE,

    /**
     * 类型标识：A文件即将转换成M文件<br>
     * 有效参数：fileInfo
     */
    PRE_STOR_CHANGE,

    /**
     * 类型标识：即将读取文件数据<br>
     * 有效参数：fileInfo
     */
    PRE_READ,

    /**
     * 类型标识：读取文件数据完毕<br>
     * 有效参数：fileInfo
     */
    POST_READ,

    /**
     * 类型标识：即将删除文件记录<br>
     * 有效参数：fileList, permanently
     */
    PRE_DELETE,

    /**
     * 类型标识：删除文件中，用于区别PRE，默认PRE不用于处理数据。<br>
     * 有效参数：fileList, permanently
     */
    DELETEING,
    /**
     * 类型标识：删除文件记录完毕<br>
     * 有效参数：fileList, permanently
     */
    POST_DELETE,

    /**
     * 类型标识：即将对文件进行加密排重<br>
     * 有效参数：fileInfo
     */
    PRE_CLOSE,

    /**
     * 类型标识：对文件加密排重完毕<br>
     * 有效参数：fileInfo
     */
    POST_CLOSE,

    /**
     * 类型标识：即将复制文件记录<br>
     * 有效参数：fileInfo, destFileInfo, newName
     */
    PRE_COPY,

    /**
     * 类型标识：复制文件记录完毕<br>
     * 有效参数：fileInfo, destFileInfo, newName
     */
    POST_COPY,

    /**
     * 类型标识：即将移动文件记录<br>
     * 有效参数：fileInfo, destFileInfo, newName
     */
    PRE_MOVE,

    /**
     * 类型标识：移动文件记录完毕<br>
     * 有效参数：fileInfo, destFileInfo, newName
     */
    POST_MOVE,

    /**
     * 类型标识：跨文件系统移动文件记录完毕<br>
     * 有效参数：fileInfo, destFileInfo, newFileInfo
     */
    POST_MOVE_ACROSS_FS,
    /**
     * 类型标识：即将重命名文件<br>
     * 有效参数：fileInfo
     */
    PRE_RENAME,

    /**
     * 类型标识：重命名文件完毕<br>
     * 有效参数：fileInfo
     */
    POST_RENAME,

    /**
     * 类型标识：即将获取文件的下级列表<br>
     * 有效参数：fileInfo， fileList
     */
    PRE_LIST,

    /**
     * 类型标识：获取文件的下级列表完毕<br>
     * 有效参数：fileInfo， fileList
     */
    POST_LIST,

    /**
     * 类型标识：即将生成文件外链<br>
     * 有效参数：fileInfo
     */
    PRE_SHARE,

    /**
     * 类型标识：生成文件外链完毕<br>
     * 有效参数：fileInfo
     */
    POST_SHARE,

    /**
     * 类型标识：即将更新文件分享外链
     */
    PRE_UPDATE_SHARE,

    /**
     * 类型标识：文件分享外链的更新 有效参数：url,password,uids,dids 如果文件指定到了人或者部门，则会调用此接口，其他模块可以监听向用户发送消息
     */
    POST_UPDATE_SHARE,

    /**
     * 类型标识：即将锁定文件<br>
     * 有效参数：fileInfo
     */
    PRE_LOCK,

    /**
     * 类型标识：锁定文件完毕<br>
     * 有效参数：fileInfo
     */
    POST_LOCK,

    /**
     * 类型标识：请求文件上传<br>
     * 有效参数：fileInfo(仅fsid, parent, name, size字段有值) parentFileInfo
     */
    REQUEST_UPLOAD,

    /**
     * 类型标识：请求文件下载<br>
     * 有效参数：fileInfo
     */
    REQUEST_DWONLOAD,

    /**
     * 类型标识：文件修改完毕<br>
     * 有效参数：fileInfo
     */
    POST_MODIFYFILE,

    /**
     * 类型标识：搜索文件<br>
     * 有效参数：fileInfo， fileList
     */
    SEARCH_FILE,

    /**
     * 类型标识:上传文件完成，提交文件信息
     */
    POST_COMMIT,

    /**
     * 类型标识：回收access标记操作<br>
     * 有效参数：fileInfo， fileList
     */
    REVOKE_ACCESS,

    /**
     * 类型标识：文件更新操作<br>
     * 有效参数：fileInfo
     */
    POST_UPDATE_FILE,

    /**
     * 类型标识:即将上传文件，即将提交文件信息
     */
    PRE_COMMIT,

    /**
     * 类型标识：文件预览操作<br>
     * 有效参数：fileInfo
     */
    PRE_VIEW,

    /**
     * 类型标识：全文检索返回前
     */
    PRE_FULLTEXTSEARCH,

    /**
     * 类型标识：全文检索 之后
     */
    POST_FULLTEXTSEARCH,

    /**
     * 类型标识：分享文件被查看<br>
     * 有效参数:
     */
    SHARE_VIEW,

    /**
     * 类型标识：个人文件收藏
     */
    LIST_FAVORITE,

    /**
     * 类型标识：加入文件收藏之前
     */
    PRE_ADD_FAVORITE,

    /**
     * 类型标识：取消收藏之前
     */
    PRE_CANCEL_FAVORITE,

    /**
     * 类型标识：编辑文件之前
     */
    PRE_EDIT_FILE,

    /**
     * 类型标识：编辑文件之后
     */
    POST_EDIT_FILE,

    /**
     * 类型标识：删除恢复之前
     */
    PRE_RECYCLE_RESTORE,

    /**
     * 类型标识：订阅之前
     */
    PRE_SUBSCRIBE_FILE,

    /**
     * 类型标识：评论之前
     */
    PRE_COMMENT,

    /**
     * 类型标识：上传节点选择之前
     */
    PRE_UFA_NODE_SELECT,

    /**
     * 类型标识：检查外链文件
     */
    CHECK_SHARE_FILE,

    /**
     * 类型标识：分享列表
     */
    LIST_SHARE,

    /**
     * 类型标识：发送给同时列表
     */
    LIST_RECEIVE,

    /**
     * 类型标识：设置文件权限之前
     */
    PRE_SETACL,

    /**
     * 类型标识：查询文件权限列表之前
     */
    PRE_ACLLIST,

    /**
     * 类型标识：查询文件信息之后
     */
    POST_GETFILE,

    /**
     * 类型标识：设置文件标签之前
     */
    PRE_ADD_FILELABEL,

    /**
     * 类型标识：移除文件标签之前
     */
    PRE_REMOVE_FILELABEL
}
