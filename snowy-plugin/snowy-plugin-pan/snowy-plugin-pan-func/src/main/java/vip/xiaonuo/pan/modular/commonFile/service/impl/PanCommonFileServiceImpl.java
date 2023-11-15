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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.api.DevLogApi;
import vip.xiaonuo.pan.modular.commonFile.mapper.PanCommonFileMapper;
import vip.xiaonuo.pan.modular.commonFile.param.*;
import vip.xiaonuo.pan.modular.commonFile.service.PanCommonFileService;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonFilePermissionVo;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonFileVo;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonToMeVo;
import vip.xiaonuo.pan.modular.fileCenter.entity.PanFilePermission;
import vip.xiaonuo.pan.modular.fileRight.enums.FileActionEnum;
import vip.xiaonuo.pan.modular.fileRight.param.ObjectRight;
import vip.xiaonuo.sys.api.SysOrgApi;

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
public class PanCommonFileServiceImpl extends ServiceImpl<PanCommonFileMapper, PanFilePermission> implements PanCommonFileService {

    @Resource
    PanCommonFileMapper panCommonFileMapper;

    @Resource
    SysOrgApi sysOrgApi;

    @Resource
    DevLogApi devLogApi;

    @Override
    public void saveCommon(CommonFileParam commonFileParam) {
        //共享本文件
        List<PanFilePermission> addList = new ArrayList<>();
        List<PanFilePermission> updateList = new ArrayList<>();
        for (ObjectRight obj : commonFileParam.getCommonUserList()) {
            //判断该文件、该对象是否已存在权限设置，若存在即为更新，否则新增
            PanFilePermission permission = this.getOne(new QueryWrapper<PanFilePermission>().lambda().
                    eq(PanFilePermission::getCommonFileId,commonFileParam.getUserFileId()).eq(PanFilePermission::getUserId,obj.getObjectId()));
            if(ObjectUtil.isNotEmpty(permission)){
                permission.setFilePermissionCode(obj.getRightValue());
                updateList.add(permission);
            }else{
                PanFilePermission filePermission = new PanFilePermission();
                filePermission.setCommonFileId(commonFileParam.getUserFileId());
                filePermission.setUserId(obj.getObjectId());
                filePermission.setFilePermissionCode(obj.getRightValue());
                addList.add(filePermission);
            }
        }
        if (CollUtil.isNotEmpty(addList)){
            this.saveBatch(addList);
        }
        if(CollUtil.isNotEmpty(updateList)){
            this.updateBatchById(updateList);
        }
        //记录文件操作日志
        devLogApi.executeFileActionLog(StpLoginUserUtil.getLoginUser().getName(), FileActionEnum.SHARE.getValue(),"", CollUtil.newArrayList(commonFileParam.getUserFileId()));
    }

    @Override
    public void removeCommon(RemoveCommonParam removeCommonParam) {
        if(CollUtil.isEmpty(removeCommonParam.getObjectIds())){
            throw new CommonException("请选择取消共享者");
        }
        QueryWrapper<PanFilePermission> queryWrapper =  new QueryWrapper<>();
        queryWrapper.lambda().eq(PanFilePermission::getCommonFileId,removeCommonParam.getUserFileId());
        queryWrapper.lambda().in(PanFilePermission::getUserId,removeCommonParam.getObjectIds());
        List<PanFilePermission> permissionList = this.list(queryWrapper);
        this.removeBatchByIds(permissionList);
    }

    @Override
    public CommonFilePermissionVo detailByObjId(CommonUserParam commonUserParam) {
        return panCommonFileMapper.detailByObjId(commonUserParam.getUserFileId(),commonUserParam.getObjectId());
    }

    @Override
    public List<CommonFilePermissionVo> detailByFileId(String userFileId) {
        return panCommonFileMapper.detailById(userFileId);
    }

    @Override
    public Page<CommonToMeVo> commonToMePage(PanCommonFilePageParam panCommonFilePageParam) {
        List<String> orgIds = sysOrgApi.getParentIds(StpLoginUserUtil.getLoginUser().getOrgId(),true);
        QueryWrapper<PanFilePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.and(q->{
            q.eq("a.USER_ID",StpLoginUserUtil.getLoginUser().getId());
            if(CollUtil.isNotEmpty(orgIds)) q.or().in("a.USER_ID",orgIds);
        });
        if(ObjectUtil.isNotEmpty(panCommonFilePageParam.getSearchKey())){
            queryWrapper.and(q->q.like("b.FILE_NAME",panCommonFilePageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(panCommonFilePageParam.getSortField(), panCommonFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panCommonFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, panCommonFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panCommonFilePageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("b.IS_DIR").orderByAsc("b.FILE_NAME");
        }
        return panCommonFileMapper.commonToMePage(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public Page<CommonFileVo> myCommonPage(PanCommonFilePageParam panCommonFilePageParam) {
        QueryWrapper<PanFilePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("a.USER_ID",StpLoginUserUtil.getLoginUser().getId());
        if(ObjectUtil.isNotEmpty(panCommonFilePageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.FILE_NAME",panCommonFilePageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(panCommonFilePageParam.getSortField(), panCommonFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panCommonFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, panCommonFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panCommonFilePageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.IS_DIR").orderByAsc("a.FILE_NAME");
        }
        return panCommonFileMapper.myCommonPage(CommonPageRequest.defaultPage(), queryWrapper);
    }
}
