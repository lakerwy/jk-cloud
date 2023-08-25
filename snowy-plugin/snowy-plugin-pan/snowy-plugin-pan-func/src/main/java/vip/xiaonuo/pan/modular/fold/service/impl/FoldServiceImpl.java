package vip.xiaonuo.pan.modular.fold.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.prop.CommonProperties;
import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.pan.modular.fold.param.DevFileAddParam;
import vip.xiaonuo.dev.modular.file.service.DevFileService;
import vip.xiaonuo.pan.modular.fold.param.DevFoldListParam;
import vip.xiaonuo.pan.modular.fold.service.FoldService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/8/25 14:29
 */
@Service
@Slf4j
public class FoldServiceImpl implements FoldService {

    /**顶级父目录id*/
    private final static  String ROOT_FOLD_ID = "0";

    private final static String DEFAULT_BUCKET_NAME = "jk";

    private final static  Integer IS_DIR = 1;
    @Resource
    private DevFileService devFileService;

    @Resource
    private CommonProperties commonProperties;


    @Override
    public void createFold(String engine, DevFileAddParam devFileAddParam) {
        DevFile devFile = new DevFile();
        String fileId = IdWorker.getIdStr();
        // 设置文件id
        devFile.setId(fileId);
        // 设置存储引擎类型
        devFile.setEngine(engine);
        devFile.setBucket(DEFAULT_BUCKET_NAME);
        devFile.setName(devFileAddParam.getFileName());
        devFile.setObjName(devFileAddParam.getFileName());
        // 存储路径 文件夹无存储路径
       // devFile.setStoragePath(devFileAddParam.getFilePath());
        devFile.setIsDir(1);
        devFile.setParentId(devFileAddParam.getParentId());
        devFileService.save(devFile);

    }

    @Override
    public Boolean isDirExist(DevFileAddParam devFileAddParam) {
        LambdaQueryWrapper<DevFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DevFile::getParentId,devFileAddParam.getParentId())
                .eq(DevFile::getName,devFileAddParam.getFileName())
                .eq(DevFile::getCreateUser, StpLoginUserUtil.getLoginUser().getId())
                .eq(DevFile::getDeleteFlag,"NOT_DELETE");
        List<DevFile> devFiles = devFileService.list(lambdaQueryWrapper);
        if(CollUtil.isEmpty(devFiles)){
            return  Boolean.TRUE;
        }
        return  Boolean.FALSE;
    }

    @Override
    public List<DevFile> pageList(DevFoldListParam devFoldListParam) {
        LambdaQueryWrapper<DevFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DevFile::getParentId,StrUtil.isBlank(devFoldListParam.getParentId())?ROOT_FOLD_ID:devFoldListParam.getParentId())
                .eq(DevFile::getDeleteFlag,"NOT_DELETE");
        if(ObjectUtil.isNotEmpty(devFoldListParam.getEngine())) {
            lambdaQueryWrapper.eq(DevFile::getBucket,devFoldListParam.getEngine());
        }
        if(ObjectUtil.isNotEmpty(devFoldListParam.getSearchKey())) {
            lambdaQueryWrapper.like(DevFile::getName, devFoldListParam.getSearchKey());
        }
        List<DevFile> list = devFileService.list(lambdaQueryWrapper);
        List<DevFile> newRecords = new ArrayList<>();
        list.forEach(devFile -> {
            if(devFile.getEngine().equals("LOCAL") && !IS_DIR.equals(devFile.getIsDir())){
                devFile.setDownloadPath(commonProperties.getBackendUrl()+devFile.getDownloadPath());
            }
            newRecords.add(devFile);
        });
        return newRecords;
    }
}
