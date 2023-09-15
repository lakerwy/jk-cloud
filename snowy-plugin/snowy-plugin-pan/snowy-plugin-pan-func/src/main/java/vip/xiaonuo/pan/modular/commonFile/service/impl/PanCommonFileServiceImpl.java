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
package vip.xiaonuo.pan.modular.commonFile.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.pan.modular.commonFile.entity.PanCommonFile;
import vip.xiaonuo.pan.modular.commonFile.mapper.PanCommonFileMapper;
import vip.xiaonuo.pan.modular.commonFile.param.*;
import vip.xiaonuo.pan.modular.commonFile.service.PanCommonFileService;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonFileUserVo;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonFileVo;
import vip.xiaonuo.pan.modular.fileCenter.entity.PanFilePermission;
import vip.xiaonuo.pan.modular.fileCenter.service.PanFilePermissionService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 共享文件Service接口实现类
 *
 * @author mayike
 * @date  2023/09/06 21:07
 **/
@Service
public class PanCommonFileServiceImpl extends ServiceImpl<PanCommonFileMapper, PanCommonFile> implements PanCommonFileService {

    @Resource
    PanFilePermissionService panFilePermissionService;

    @Resource
    PanCommonFileMapper panCommonFileMapper;

    @Override
    public Page<PanCommonFile> page(PanCommonFilePageParam panCommonFilePageParam) {
        QueryWrapper<PanCommonFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(panCommonFilePageParam.getSortField(), panCommonFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panCommonFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, panCommonFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panCommonFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanCommonFile::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanCommonFileAddParam panCommonFileAddParam) {
        PanCommonFile panCommonFile = BeanUtil.toBean(panCommonFileAddParam, PanCommonFile.class);
        this.save(panCommonFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanCommonFileEditParam panCommonFileEditParam) {
        PanCommonFile panCommonFile = this.queryEntity(panCommonFileEditParam.getId());
        BeanUtil.copyProperties(panCommonFileEditParam, panCommonFile);
        this.updateById(panCommonFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanCommonFileIdParam> panCommonFileIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panCommonFileIdParamList, PanCommonFileIdParam::getId));
    }

    @Override
    public PanCommonFile detail(PanCommonFileIdParam panCommonFileIdParam) {
        return this.queryEntity(panCommonFileIdParam.getId());
    }

    @Override
    public PanCommonFile queryEntity(String id) {
        PanCommonFile panCommonFile = this.getById(id);
        if(ObjectUtil.isEmpty(panCommonFile)) {
            throw new CommonException("共享文件不存在，id值为：{}", id);
        }
        return panCommonFile;
    }

    @Override
    public void commonFile(CommonFileParam commonFileParam) {
        PanCommonFile commonFile = new PanCommonFile();
        commonFile.setUserFileId(commonFileParam.getUserFileId());
        this.save(commonFile);
        //权限
        List<PanFilePermission> filePermissionList = new ArrayList<>();
        for (String userId : commonFileParam.getCommonUserList()) {
            PanFilePermission filePermission = new PanFilePermission();
            filePermission.setCommonFileId(commonFile.getId());
            filePermission.setUserId(userId);
            filePermissionList.add(filePermission);
        }
        panFilePermissionService.saveBatch(filePermissionList);
    }

    @Override
    public List<CommonFileUserVo> commonFileUser() {
        return panCommonFileMapper.selectCommonFileUser(StpLoginUserUtil.getLoginUser().getId());
    }

    @Override
    public List<CommonFileVo> commonFileByUser(String userId) {
        return panCommonFileMapper.selectCommonFileByUser(userId,StpLoginUserUtil.getLoginUser().getId());
    }

    @Override
    public IPage<CommonFileVo> commonFileList(PanCommonFilePageParam panCommonFilePageParam) {
        QueryWrapper<PanCommonFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(panCommonFilePageParam.getSortField(), panCommonFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panCommonFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, panCommonFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panCommonFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanCommonFile::getId);
        }
        return panCommonFileMapper.myCommonFile(StpLoginUserUtil.getLoginUser().getId());

    }
}
