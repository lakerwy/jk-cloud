package vip.xiaonuo.pan.modular.fold.service;

import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.pan.modular.fold.param.DevFileAddParam;
import vip.xiaonuo.pan.modular.fold.param.DevFoldListParam;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/8/25 14:27
 */
public interface FoldService {
    /**
     * 创建文件夹
     * @param engine
     * @param devFileAddParam
     */
    void createFold(String engine, DevFileAddParam devFileAddParam);

    /**
     * 判断文件夹是否存在
     * @param devFileAddParam
     */
    Boolean isDirExist(DevFileAddParam devFileAddParam);

    List<DevFile> pageList(DevFoldListParam devFoldListParam);
}
