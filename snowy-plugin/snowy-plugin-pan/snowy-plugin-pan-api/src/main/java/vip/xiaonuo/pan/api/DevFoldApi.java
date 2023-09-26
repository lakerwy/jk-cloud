package vip.xiaonuo.pan.api;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/8/29 16:48
 * 文件夹api
 */
public interface DevFoldApi {

    /**
     * 创建个人文件夹根目录
     * @return 文件id
     */
    void createSelfFold();

    /**
     * 创建部门根目录
     * @return 文件id
     */
    void createGroupFold();
}
