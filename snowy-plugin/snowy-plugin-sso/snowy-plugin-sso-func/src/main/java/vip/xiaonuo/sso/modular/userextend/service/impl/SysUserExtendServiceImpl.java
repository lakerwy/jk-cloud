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
package vip.xiaonuo.sso.modular.userextend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.modular.epcCenter.service.SysProjectService;
import vip.xiaonuo.sso.modular.userextend.entity.SysUserExtend;
import vip.xiaonuo.sso.modular.userextend.mapper.SysUserExtendMapper;
import vip.xiaonuo.sso.modular.userextend.param.SysUserExtendAddParam;
import vip.xiaonuo.sso.modular.userextend.param.SysUserExtendEditParam;
import vip.xiaonuo.sso.modular.userextend.param.SysUserExtendIdParam;
import vip.xiaonuo.sso.modular.userextend.param.SysUserExtendPageParam;
import vip.xiaonuo.sso.modular.userextend.service.SysUserExtendService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 外部用户表Service接口实现类
 *
 * @author mayike
 * @date  2024/02/01 10:11
 **/
@Service
public class SysUserExtendServiceImpl extends ServiceImpl<SysUserExtendMapper, SysUserExtend> implements SysUserExtendService {


    @Resource
    SysProjectService sysProjectService;
    @Override
    public Page<SysUserExtend> page(SysUserExtendPageParam sysUserExtendPageParam) {
        QueryWrapper<SysUserExtend> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(sysUserExtendPageParam.getSortField(), sysUserExtendPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(sysUserExtendPageParam.getSortOrder());
            queryWrapper.orderBy(true, sysUserExtendPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(sysUserExtendPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SysUserExtend::getSortCode);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SysUserExtendAddParam sysUserExtendAddParam) {
        SysUserExtend sysUserExtend = BeanUtil.toBean(sysUserExtendAddParam, SysUserExtend.class);
        this.save(sysUserExtend);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SysUserExtendEditParam sysUserExtendEditParam) {
        SysUserExtend sysUserExtend = this.queryEntity(sysUserExtendEditParam.getId());
        BeanUtil.copyProperties(sysUserExtendEditParam, sysUserExtend);
        this.updateById(sysUserExtend);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SysUserExtendIdParam> sysUserExtendIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(sysUserExtendIdParamList, SysUserExtendIdParam::getId));
    }

    @Override
    public SysUserExtend detail(SysUserExtendIdParam sysUserExtendIdParam) {
        return this.queryEntity(sysUserExtendIdParam.getId());
    }

    @Override
    public SysUserExtend queryEntity(String id) {
        SysUserExtend sysUserExtend = this.getById(id);
        if(ObjectUtil.isEmpty(sysUserExtend)) {
            throw new CommonException("外部用户表不存在，id值为：{}", id);
        }
        return sysUserExtend;
    }

    @Override
    public void sync(SysUserExtendAddParam sysUserExtendAddParam) {
        //1、判断是否已经同步过
        SysUserExtend one = this.getOne(new LambdaQueryWrapper<SysUserExtend>().eq(SysUserExtend::getAccount, sysUserExtendAddParam.getAccount()));
        if(ObjectUtil.isNotEmpty(one)){
            //2、判断是否插入到过关联表中 projSn，是否关联过
            sysProjectService.syncUserAndProjectData(sysUserExtendAddParam.getProjSn(),sysUserExtendAddParam.getAccount());
        }
    }
}
