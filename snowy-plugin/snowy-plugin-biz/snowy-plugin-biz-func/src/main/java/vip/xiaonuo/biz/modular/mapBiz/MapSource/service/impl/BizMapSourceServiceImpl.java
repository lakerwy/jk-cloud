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
package vip.xiaonuo.biz.modular.mapBiz.MapSource.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.mapBiz.MapSource.entity.BizMapSource;
import vip.xiaonuo.biz.modular.mapBiz.MapSource.mapper.BizMapSourceMapper;
import vip.xiaonuo.biz.modular.mapBiz.MapSource.param.BizMapSourceAddParam;
import vip.xiaonuo.biz.modular.mapBiz.MapSource.param.BizMapSourceEditParam;
import vip.xiaonuo.biz.modular.mapBiz.MapSource.param.BizMapSourceIdParam;
import vip.xiaonuo.biz.modular.mapBiz.MapSource.param.BizMapSourcePageParam;
import vip.xiaonuo.biz.modular.mapBiz.MapSource.service.BizMapSourceService;

import java.util.List;

/**
 * 图审 - 原始资料录入 Service接口实现类
 *
 * @author yong.ye
 * @date  2024/06/11 17:36
 **/
@Service
@DS("slave_1")
public class BizMapSourceServiceImpl extends ServiceImpl<BizMapSourceMapper, BizMapSource> implements BizMapSourceService {

    @Override
    public Page<BizMapSource> page(BizMapSourcePageParam bizMapSourcePageParam) {
        QueryWrapper<BizMapSource> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizMapSourcePageParam.getUnitType())) {
            queryWrapper.lambda().eq(BizMapSource::getUnitType, bizMapSourcePageParam.getUnitType());
        }
        if(ObjectUtil.isNotEmpty(bizMapSourcePageParam.getOptName())) {
            queryWrapper.lambda().like(BizMapSource::getOptName, bizMapSourcePageParam.getOptName());
        }
        if(ObjectUtil.isNotEmpty(bizMapSourcePageParam.getHtProjectName())) {
            queryWrapper.lambda().like(BizMapSource::getHtProjectName, bizMapSourcePageParam.getHtProjectName());
        }
        if(ObjectUtil.isNotEmpty(bizMapSourcePageParam.getHtContractNo())) {
            queryWrapper.lambda().like(BizMapSource::getHtContractNo, bizMapSourcePageParam.getHtContractNo());
        }
        if(ObjectUtil.isNotEmpty(bizMapSourcePageParam.getHtOwner())) {
            queryWrapper.lambda().like(BizMapSource::getHtOwner, bizMapSourcePageParam.getHtOwner());
        }
        if(ObjectUtil.isNotEmpty(bizMapSourcePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BizMapSource::getHtContractNo,bizMapSourcePageParam.getSearchKey())
                    .or().like(BizMapSource::getHtProjectName,bizMapSourcePageParam.getSearchKey())
                    .or().like(BizMapSource::getOptName,bizMapSourcePageParam.getSearchKey())
                    .or().like(BizMapSource::getHtOwner,bizMapSourcePageParam.getSearchKey()));
        }

        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(BizMapSource::getDataScopeId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(BizMapSource::getCreateUser, StpUtil.getLoginIdAsString());
        }

        if(ObjectUtil.isAllNotEmpty(bizMapSourcePageParam.getSortField(), bizMapSourcePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizMapSourcePageParam.getSortOrder());
            queryWrapper.orderBy(true, bizMapSourcePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizMapSourcePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizMapSource::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizMapSourceAddParam bizMapSourceAddParam) {
        BizMapSource bizMapSource = BeanUtil.toBean(bizMapSourceAddParam, BizMapSource.class);
        this.save(bizMapSource);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizMapSourceEditParam bizMapSourceEditParam) {
        BizMapSource bizMapSource = this.queryEntity(bizMapSourceEditParam.getId());
        BeanUtil.copyProperties(bizMapSourceEditParam, bizMapSource);
        this.updateById(bizMapSource);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizMapSourceIdParam> bizMapSourceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizMapSourceIdParamList, BizMapSourceIdParam::getId));
    }

    @Override
    public BizMapSource detail(BizMapSourceIdParam bizMapSourceIdParam) {
        return this.queryEntity(bizMapSourceIdParam.getId());
    }

    @Override
    public BizMapSource queryEntity(String id) {
        BizMapSource bizMapSource = this.getById(id);
        if(ObjectUtil.isEmpty(bizMapSource)) {
            throw new CommonException("图审 - 原始资料录入 不存在，id值为：{}", id);
        }
        return bizMapSource;
    }
}
