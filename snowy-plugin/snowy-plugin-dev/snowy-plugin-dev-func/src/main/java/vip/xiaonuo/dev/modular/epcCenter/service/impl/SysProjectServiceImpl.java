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
package vip.xiaonuo.dev.modular.epcCenter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.modular.epcCenter.entity.SysProject;
import vip.xiaonuo.dev.modular.epcCenter.mapper.SysProjectMapper;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectAddParam;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectEditParam;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectIdParam;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectPageParam;
import vip.xiaonuo.dev.modular.epcCenter.service.SysProjectService;

import java.util.List;

/**
 * 系统项目表Service接口实现类
 *
 * @author ljy
 * @date  2024/01/31 11:39
 **/
@Service
@DS("EpcCenter")
@DSTransactional
public class SysProjectServiceImpl extends ServiceImpl<SysProjectMapper, SysProject> implements SysProjectService {

    @Override
    public Page<SysProject> page(SysProjectPageParam sysProjectPageParam) {
        QueryWrapper<SysProject> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(sysProjectPageParam.getPrjType())) {
            queryWrapper.lambda().like(SysProject::getPrjType, sysProjectPageParam.getPrjType());
        }
        if(ObjectUtil.isNotEmpty(sysProjectPageParam.getPrjSn())) {
            queryWrapper.lambda().eq(SysProject::getPrjSn, sysProjectPageParam.getPrjSn());
        }
        if(ObjectUtil.isNotEmpty(sysProjectPageParam.getPrjName())) {
            queryWrapper.lambda().like(SysProject::getPrjName, sysProjectPageParam.getPrjName());
        }
        if(ObjectUtil.isAllNotEmpty(sysProjectPageParam.getSortField(), sysProjectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(sysProjectPageParam.getSortOrder());
            queryWrapper.orderBy(true, sysProjectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(sysProjectPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SysProject::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SysProjectAddParam sysProjectAddParam) {
        SysProject sysProject = BeanUtil.toBean(sysProjectAddParam, SysProject.class);
        this.save(sysProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SysProjectEditParam sysProjectEditParam) {
        SysProject sysProject = this.queryEntity(sysProjectEditParam.getId());
        BeanUtil.copyProperties(sysProjectEditParam, sysProject);
        this.updateById(sysProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SysProjectIdParam> sysProjectIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(sysProjectIdParamList, SysProjectIdParam::getId));
    }

    @Override
    public SysProject detail(SysProjectIdParam sysProjectIdParam) {
        return this.queryEntity(sysProjectIdParam.getId());
    }

    @Override
    public SysProject queryEntity(String id) {
        SysProject sysProject = this.getById(id);
        if(ObjectUtil.isEmpty(sysProject)) {
            throw new CommonException("系统项目表不存在，id值为：{}", id);
        }
        return sysProject;
    }
}
