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
import vip.xiaonuo.dev.modular.epcCenter.entity.SysProjectUser;
import vip.xiaonuo.dev.modular.epcCenter.mapper.SysProjectUserMapper;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectUserAddParam;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectUserEditParam;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectUserIdParam;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectUserPageParam;
import vip.xiaonuo.dev.modular.epcCenter.service.SysProjectUserService;

import java.util.List;

/**
 * 系统项目-用户关系表Service接口实现类
 *
 * @author ljy
 * @date  2024/01/31 11:42
 **/
@DS("EpcCenter")
@DSTransactional
@Service
public class SysProjectUserServiceImpl extends ServiceImpl<SysProjectUserMapper, SysProjectUser> implements SysProjectUserService {

    @Override
    public Page<SysProjectUser> page(SysProjectUserPageParam sysProjectUserPageParam) {
        QueryWrapper<SysProjectUser> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(sysProjectUserPageParam.getSysProjectId())) {
            queryWrapper.lambda().eq(SysProjectUser::getSysProjectId, sysProjectUserPageParam.getSysProjectId());
        }
        if(ObjectUtil.isNotEmpty(sysProjectUserPageParam.getAccount())) {
            queryWrapper.lambda().eq(SysProjectUser::getAccount, sysProjectUserPageParam.getAccount());
        }
        if(ObjectUtil.isAllNotEmpty(sysProjectUserPageParam.getSortField(), sysProjectUserPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(sysProjectUserPageParam.getSortOrder());
            queryWrapper.orderBy(true, sysProjectUserPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(sysProjectUserPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SysProjectUser::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SysProjectUserAddParam sysProjectUserAddParam) {
        SysProjectUser sysProjectUser = BeanUtil.toBean(sysProjectUserAddParam, SysProjectUser.class);
        this.save(sysProjectUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SysProjectUserEditParam sysProjectUserEditParam) {
        SysProjectUser sysProjectUser = this.queryEntity(sysProjectUserEditParam.getId());
        BeanUtil.copyProperties(sysProjectUserEditParam, sysProjectUser);
        this.updateById(sysProjectUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SysProjectUserIdParam> sysProjectUserIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(sysProjectUserIdParamList, SysProjectUserIdParam::getId));
    }

    @Override
    public SysProjectUser detail(SysProjectUserIdParam sysProjectUserIdParam) {
        return this.queryEntity(sysProjectUserIdParam.getId());
    }

    @Override
    public SysProjectUser queryEntity(String id) {
        SysProjectUser sysProjectUser = this.getById(id);
        if(ObjectUtil.isEmpty(sysProjectUser)) {
            throw new CommonException("系统项目-用户关系表不存在，id值为：{}", id);
        }
        return sysProjectUser;
    }
}
