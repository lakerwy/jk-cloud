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
package vip.xiaonuo.flw.modular.flwbiz.projectRegister.service.impl;

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
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.flw.modular.flwbiz.common.BizCommon;
import vip.xiaonuo.flw.modular.flwbiz.common.HandleExecutionUtil;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.entity.BizProjectRegister;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.mapper.BizProjectRegisterMapper;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.param.BizProjectRegisterAddParam;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.param.BizProjectRegisterEditParam;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.param.BizProjectRegisterIdParam;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.param.BizProjectRegisterPageParam;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.service.BizProjectRegisterService;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务表-立项报备Service接口实现类
 *
 * @author mayike
 * @date  2023/06/19 15:26
 **/
@DS("slave_1")
@Service
public class BizProjectRegisterServiceImpl extends ServiceImpl<BizProjectRegisterMapper, BizProjectRegister> implements BizProjectRegisterService {

    @Resource
    FormRelationTableService formRelationTableService;

    @Override
    public Page<BizProjectRegister> page(BizProjectRegisterPageParam bizProjectRegisterPageParam) {
        QueryWrapper<BizProjectRegister> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizProjectRegisterPageParam.getStartApplyTime()) && ObjectUtil.isNotEmpty(bizProjectRegisterPageParam.getEndApplyTime())) {
            queryWrapper.lambda().between(BizProjectRegister::getApplyTime, bizProjectRegisterPageParam.getStartApplyTime(), bizProjectRegisterPageParam.getEndApplyTime());
        }
        if(ObjectUtil.isNotEmpty(bizProjectRegisterPageParam.getSn())) {
            queryWrapper.lambda().like(BizProjectRegister::getSn, bizProjectRegisterPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizProjectRegisterPageParam.getEntryName())) {
            queryWrapper.lambda().like(BizProjectRegister::getEntryName, bizProjectRegisterPageParam.getEntryName());
        }
        if(ObjectUtil.isNotEmpty(bizProjectRegisterPageParam.getLocation())) {
            queryWrapper.lambda().like(BizProjectRegister::getLocation, bizProjectRegisterPageParam.getLocation());
        }
        if(ObjectUtil.isNotEmpty(bizProjectRegisterPageParam.getScale())) {
            queryWrapper.lambda().like(BizProjectRegister::getScale, bizProjectRegisterPageParam.getScale());
        }
        queryWrapper.lambda().eq(BizProjectRegister::getStatus,"1");
        if(ObjectUtil.isAllNotEmpty(bizProjectRegisterPageParam.getSortField(), bizProjectRegisterPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizProjectRegisterPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizProjectRegisterPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizProjectRegisterPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizProjectRegister::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

   // @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizProjectRegisterAddParam bizProjectRegisterAddParam) {
        BizProjectRegister bizProjectRegister = BeanUtil.toBean(bizProjectRegisterAddParam, BizProjectRegister.class);
        bizProjectRegister.setSn(HandleExecutionUtil.getTableSn(BizCommon.PROJECT_REGISTER.getBizTable()));
        bizProjectRegister.setStatus("0");
        this.save(bizProjectRegister);
        formRelationTableService.updateTable(bizProjectRegisterAddParam.getModelId(),BizCommon.PROJECT_REGISTER.getBizTable());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizProjectRegisterEditParam bizProjectRegisterEditParam) {
        BizProjectRegister bizProjectRegister = this.queryEntity(bizProjectRegisterEditParam.getId());
        BeanUtil.copyProperties(bizProjectRegisterEditParam, bizProjectRegister);
        this.updateById(bizProjectRegister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizProjectRegisterIdParam> bizProjectRegisterIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizProjectRegisterIdParamList, BizProjectRegisterIdParam::getId));
    }

    @Override
    public BizProjectRegister detail(BizProjectRegisterIdParam bizProjectRegisterIdParam) {
        return this.queryEntity(bizProjectRegisterIdParam.getId());
    }

    @Override
    public BizProjectRegister queryEntity(String id) {
        BizProjectRegister bizProjectRegister = this.getById(id);
        if(ObjectUtil.isEmpty(bizProjectRegister)) {
            throw new CommonException("业务表-立项报备不存在，id值为：{}", id);
        }
        return bizProjectRegister;
    }

}
