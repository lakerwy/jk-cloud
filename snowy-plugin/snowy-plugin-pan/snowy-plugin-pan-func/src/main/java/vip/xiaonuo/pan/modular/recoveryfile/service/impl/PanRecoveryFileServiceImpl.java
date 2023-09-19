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
package vip.xiaonuo.pan.modular.recoveryfile.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.pan.modular.fileCenter.component.AsyncTaskComp;
import vip.xiaonuo.pan.modular.fileCenter.component.FileDealComp;
import vip.xiaonuo.pan.modular.fileCenter.util.CommonFile;
import vip.xiaonuo.pan.modular.recoveryfile.entity.PanRecoveryFile;
import vip.xiaonuo.pan.modular.recoveryfile.mapper.PanRecoveryFileMapper;
import vip.xiaonuo.pan.modular.recoveryfile.param.*;
import vip.xiaonuo.pan.modular.recoveryfile.param.dto.BatchDeleteDto;
import vip.xiaonuo.pan.modular.recoveryfile.param.dto.RestoreFileDto;
import vip.xiaonuo.pan.modular.recoveryfile.service.PanRecoveryFileService;
import vip.xiaonuo.pan.modular.recoveryfile.vo.RecoveryFileVo;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 网盘-回收站Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/07 15:54
 **/
@Service
public class PanRecoveryFileServiceImpl extends ServiceImpl<PanRecoveryFileMapper, PanRecoveryFile> implements PanRecoveryFileService {

    @Resource
    UserFileService userFileService;

    @Resource
    PanRecoveryFileMapper panRecoveryFileMapper;

    @Resource
    FileDealComp fileDealComp;

    @Resource
    AsyncTaskComp asyncTaskComp;


    @Override
    public Page<PanRecoveryFile> page(PanRecoveryFilePageParam panRecoveryFilePageParam) {
        QueryWrapper<PanRecoveryFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(panRecoveryFilePageParam.getTenantId())) {
            queryWrapper.lambda().eq(PanRecoveryFile::getTenantId, panRecoveryFilePageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(panRecoveryFilePageParam.getStartDeleteTime()) && ObjectUtil.isNotEmpty(panRecoveryFilePageParam.getEndDeleteTime())) {
            queryWrapper.lambda().between(PanRecoveryFile::getDeleteTime, panRecoveryFilePageParam.getStartDeleteTime(), panRecoveryFilePageParam.getEndDeleteTime());
        }
        if(ObjectUtil.isNotEmpty(panRecoveryFilePageParam.getDeleteBatchNum())) {
            queryWrapper.lambda().eq(PanRecoveryFile::getDeleteBatchNum, panRecoveryFilePageParam.getDeleteBatchNum());
        }
        if(ObjectUtil.isAllNotEmpty(panRecoveryFilePageParam.getSortField(), panRecoveryFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panRecoveryFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, panRecoveryFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panRecoveryFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanRecoveryFile::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanRecoveryFileAddParam panRecoveryFileAddParam) {
        PanRecoveryFile panRecoveryFile = BeanUtil.toBean(panRecoveryFileAddParam, PanRecoveryFile.class);
        this.save(panRecoveryFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanRecoveryFileEditParam panRecoveryFileEditParam) {
        PanRecoveryFile panRecoveryFile = this.queryEntity(panRecoveryFileEditParam.getId());
        BeanUtil.copyProperties(panRecoveryFileEditParam, panRecoveryFile);
        this.updateById(panRecoveryFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanRecoveryFileIdParam> panRecoveryFileIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panRecoveryFileIdParamList, PanRecoveryFileIdParam::getId));
    }

    @Override
    public PanRecoveryFile detail(PanRecoveryFileIdParam panRecoveryFileIdParam) {
        return this.queryEntity(panRecoveryFileIdParam.getId());
    }

    @Override
    public PanRecoveryFile queryEntity(String id) {
        PanRecoveryFile panRecoveryFile = this.getById(id);
        if(ObjectUtil.isEmpty(panRecoveryFile)) {
            throw new CommonException("网盘-回收站不存在，id值为：{}", id);
        }
        return panRecoveryFile;
    }

    @Override
    public IPage<RecoveryFileVo> listPage(PanRecoveryFilePageParam panRecoveryFilePageParam) {
        QueryWrapper<PanRecoveryFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("c.USER_ID", StpLoginUserUtil.getLoginUser().getId());
        if(ObjectUtil.isNotEmpty(panRecoveryFilePageParam.getTenantId())) {
            queryWrapper.eq("a.TENANT_ID", panRecoveryFilePageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(panRecoveryFilePageParam.getStartDeleteTime()) && ObjectUtil.isNotEmpty(panRecoveryFilePageParam.getEndDeleteTime())) {
            queryWrapper.between("a.DELETE_TIME", panRecoveryFilePageParam.getStartDeleteTime(), panRecoveryFilePageParam.getEndDeleteTime());
        }
        if(ObjectUtil.isNotEmpty(panRecoveryFilePageParam.getDeleteBatchNum())) {
            queryWrapper.eq("a.DELETE_BATCH_NUM", panRecoveryFilePageParam.getDeleteBatchNum());
        }
        if(ObjectUtil.isAllNotEmpty(panRecoveryFilePageParam.getSortField(), panRecoveryFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panRecoveryFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, panRecoveryFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panRecoveryFilePageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.DELETE_TIME");
        }
        return panRecoveryFileMapper.getRecoveryFileList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public void batchDelete(List<PanRecoveryFileIdParam> recoveryFileIdParamList) {
       List<String> Ids = CollStreamUtil.toList(recoveryFileIdParamList, PanRecoveryFileIdParam::getId);
        for (String userFileId:Ids) {
            PanRecoveryFile panRecoveryFile = this.getOne(new QueryWrapper<PanRecoveryFile>().lambda().eq(PanRecoveryFile::getUserFileId,userFileId));
            if(ObjectUtil.isNotEmpty(panRecoveryFile)){
                asyncTaskComp.deleteUserFile(panRecoveryFile.getUserFileId());
                this.removeById(panRecoveryFile.getId());
            }
        }
    }

    @Override
    public void restoreFile(RestoreFileDto restoreFileDto) {
        List<UserFile> restoreUserFileList = userFileService.list(new QueryWrapper<UserFile>().lambda().eq(UserFile::getDeleteBatchNum, restoreFileDto.getDeleteBatchNum()));
        for (UserFile restoreUserFile : restoreUserFileList) {
            restoreUserFile.setDeleteFlag("NOT_DELETE");
            restoreUserFile.setDeleteBatchNum(restoreFileDto.getDeleteBatchNum());
            String fileName = fileDealComp.getRepeatFileName(restoreUserFile, restoreUserFile.getFilePath());
            if (restoreUserFile.isDirectory()) {
                if (!StringUtils.equals(fileName, restoreUserFile.getFileName())) {
                    userFileService.removeById(restoreUserFile);
                } else {
                    userFileService.updateById(restoreUserFile);
                }
            } else if (restoreUserFile.isFile()) {
                restoreUserFile.setFileName(fileName);
                userFileService.updateById(restoreUserFile);
            }
        }
        CommonFile qiwenFile = new CommonFile(restoreFileDto.getFilePath(), true);
        fileDealComp.restoreParentFilePath(qiwenFile, StpLoginUserUtil.getLoginUser().getId());

    }

    @Override
    public void deleteUserFileByDeleteBatchNum(String deleteBatchNum) {
        QueryWrapper<UserFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserFile::getDeleteBatchNum,deleteBatchNum);
        userFileService.remove(queryWrapper);
    }
}
