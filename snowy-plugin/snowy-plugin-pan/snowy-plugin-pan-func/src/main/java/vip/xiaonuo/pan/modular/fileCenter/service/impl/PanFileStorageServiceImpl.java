/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.pan.modular.fileCenter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.pan.modular.file.constant.FileConstant;
import vip.xiaonuo.pan.modular.fileCenter.entity.PanFileStorage;
import vip.xiaonuo.pan.modular.fileCenter.mapper.PanFileStorageMapper;
import vip.xiaonuo.pan.modular.fileCenter.param.PanFileStorageAddParam;
import vip.xiaonuo.pan.modular.fileCenter.param.PanFileStorageEditParam;
import vip.xiaonuo.pan.modular.fileCenter.param.PanFileStorageIdParam;
import vip.xiaonuo.pan.modular.fileCenter.param.PanFileStoragePageParam;
import vip.xiaonuo.pan.modular.fileCenter.service.PanFileStorageService;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 存储实体类Service接口实现类 ----  StorageService
 *
 * @author mayike
 * @date  2023/09/07 14:57
 **/
@Service
public class PanFileStorageServiceImpl extends ServiceImpl<PanFileStorageMapper, PanFileStorage> implements PanFileStorageService {

    public static  Long TOTAL_STORAGE_SIZE = 1024*1024*50L;
    @Resource
    private UserFileService userFileService;
    @Override
    public Page<PanFileStorage> page(PanFileStoragePageParam panFileStoragePageParam) {
        QueryWrapper<PanFileStorage> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(panFileStoragePageParam.getSortField(), panFileStoragePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panFileStoragePageParam.getSortOrder());
            queryWrapper.orderBy(true, panFileStoragePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panFileStoragePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanFileStorage::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanFileStorageAddParam panFileStorageAddParam) {
        PanFileStorage panFileStorage = BeanUtil.toBean(panFileStorageAddParam, PanFileStorage.class);
        this.save(panFileStorage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanFileStorageEditParam panFileStorageEditParam) {
        PanFileStorage panFileStorage = this.queryEntity(panFileStorageEditParam.getId());
        BeanUtil.copyProperties(panFileStorageEditParam, panFileStorage);
        this.updateById(panFileStorage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanFileStorageIdParam> panFileStorageIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panFileStorageIdParamList, PanFileStorageIdParam::getId));
    }

    @Override
    public PanFileStorage detail(PanFileStorageIdParam panFileStorageIdParam) {
        return this.queryEntity(panFileStorageIdParam.getId());
    }

    @Override
    public PanFileStorage queryEntity(String id) {
        PanFileStorage panFileStorage = this.getById(id);
        if(ObjectUtil.isEmpty(panFileStorage)) {
            throw new CommonException("存储实体类不存在，id值为：{}", id);
        }
        return panFileStorage;
    }

    @Override
    public Boolean checkStorage(long totalSize) {
        LambdaQueryWrapper<PanFileStorage> lambdaQueryWrapper = new LambdaQueryWrapper<PanFileStorage>().eq(PanFileStorage::getUserId, StpLoginUserUtil.getLoginUser().getId());
        List<PanFileStorage> panFileStorages = list(lambdaQueryWrapper);
        PanFileStorage panFileStorage =null;
        if(CollUtil.isNotEmpty(panFileStorages)){
            panFileStorage = panFileStorages.get(0);
        }
        Long totalStorageSize = 0L;
        if(ObjectUtil.isEmpty(panFileStorage) || ObjectUtil.isEmpty(panFileStorage.getTotalStorageSize())){
             panFileStorage = new PanFileStorage();
             totalStorageSize = FileConstant.totalStorageSize;
             panFileStorage.setTotalStorageSize(FileConstant.totalStorageSize);
             panFileStorage.setUserId(StpLoginUserUtil.getLoginUser().getId());
             save(panFileStorage);
        }else {
           totalStorageSize = panFileStorage.getTotalStorageSize();
        }
        if(null!=totalStorageSize){
            totalStorageSize = totalStorageSize*1024*1024;
        }
        //获取文件的总文件大小
        Long storageSize = userFileService.getTotalStorageSize();
        if(storageSize == null){
            storageSize = 0L;
        }
        if(storageSize + totalSize > totalStorageSize){
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    @Override
    public Long getTotalStorageSize(String userId) {
        PanFileStorage panFileStorage = getOne(new LambdaQueryWrapper<PanFileStorage>().eq(PanFileStorage::getUserId, StpLoginUserUtil.getLoginUser().getId()));
        //判断是否给该用户设置了网盘空间大小
        Long totalStorageSize = null;
        if(panFileStorage == null || panFileStorage.getTotalStorageSize() ==null){
            totalStorageSize = TOTAL_STORAGE_SIZE;
            panFileStorage = new PanFileStorage();
            panFileStorage.setUserId(StpLoginUserUtil.getLoginUser().getId());
           panFileStorage.setStorageSize(totalStorageSize);
           save(panFileStorage);
        }else {
            totalStorageSize = panFileStorage.getTotalStorageSize();
        }
        if(null!=totalStorageSize){
            totalStorageSize = totalStorageSize * 1024 * 1024;
        }
        return totalStorageSize;
    }
}
