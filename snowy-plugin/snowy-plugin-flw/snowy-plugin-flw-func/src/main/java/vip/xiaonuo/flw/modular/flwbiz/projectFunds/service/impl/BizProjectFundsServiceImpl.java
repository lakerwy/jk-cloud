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
package vip.xiaonuo.flw.modular.flwbiz.projectFunds.service.impl;

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
import vip.xiaonuo.flw.modular.flwbiz.projectFunds.mapper.BizProjectFundsMapper;
import vip.xiaonuo.flw.modular.flwbiz.projectFunds.param.BizProjectFundsIdParam;
import vip.xiaonuo.flw.modular.flwbiz.projectFunds.entity.BizProjectFunds;
import vip.xiaonuo.flw.modular.flwbiz.projectFunds.param.BizProjectFundsAddParam;
import vip.xiaonuo.flw.modular.flwbiz.projectFunds.param.BizProjectFundsEditParam;
import vip.xiaonuo.flw.modular.flwbiz.projectFunds.param.BizProjectFundsPageParam;
import vip.xiaonuo.flw.modular.flwbiz.projectFunds.service.BizProjectFundsService;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务表-工程款拨付单Service接口实现类
 *
 * @author mayike
 * @date  2023/06/19 17:26
 **/
@DS("slave_1")
@Service
public class BizProjectFundsServiceImpl extends ServiceImpl<BizProjectFundsMapper, BizProjectFunds> implements BizProjectFundsService {

    @Resource
    FormRelationTableService formRelationTableService;

    @Override
    public Page<BizProjectFunds> page(BizProjectFundsPageParam bizProjectFundsPageParam) {
        QueryWrapper<BizProjectFunds> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizProjectFundsPageParam.getSn())) {
            queryWrapper.lambda().like(BizProjectFunds::getSn, bizProjectFundsPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizProjectFundsPageParam.getChance())) {
            queryWrapper.lambda().like(BizProjectFunds::getChance, bizProjectFundsPageParam.getChance());
        }
        if(ObjectUtil.isNotEmpty(bizProjectFundsPageParam.getName())) {
            queryWrapper.lambda().like(BizProjectFunds::getName, bizProjectFundsPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(bizProjectFundsPageParam.getHead())) {
            queryWrapper.lambda().like(BizProjectFunds::getHead, bizProjectFundsPageParam.getHead());
        }
        if(ObjectUtil.isNotEmpty(bizProjectFundsPageParam.getSource())) {
            queryWrapper.lambda().like(BizProjectFunds::getSource, bizProjectFundsPageParam.getSource());
        }
        if(ObjectUtil.isAllNotEmpty(bizProjectFundsPageParam.getSortField(), bizProjectFundsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizProjectFundsPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizProjectFundsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizProjectFundsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizProjectFunds::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    //@Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizProjectFundsAddParam bizProjectFundsAddParam) {
        BizProjectFunds bizProjectFunds = BeanUtil.toBean(bizProjectFundsAddParam, BizProjectFunds.class);
        bizProjectFunds.setSn(HandleExecutionUtil.getTableSn(BizCommon.PROJECT_FUNDS.getBizTable()));
        bizProjectFunds.setStatus("0");
        this.save(bizProjectFunds);

        formRelationTableService.updateTable(bizProjectFundsAddParam.getModelId(),BizCommon.PROJECT_FUNDS.getBizTable());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizProjectFundsEditParam bizProjectFundsEditParam) {
        BizProjectFunds bizProjectFunds = this.queryEntity(bizProjectFundsEditParam.getId());
        BeanUtil.copyProperties(bizProjectFundsEditParam, bizProjectFunds);
        this.updateById(bizProjectFunds);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizProjectFundsIdParam> bizProjectFundsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizProjectFundsIdParamList, BizProjectFundsIdParam::getId));
    }

    @Override
    public BizProjectFunds detail(BizProjectFundsIdParam bizProjectFundsIdParam) {
        return this.queryEntity(bizProjectFundsIdParam.getId());
    }

    @Override
    public BizProjectFunds queryEntity(String id) {
        BizProjectFunds bizProjectFunds = this.getById(id);
        if(ObjectUtil.isEmpty(bizProjectFunds)) {
            throw new CommonException("业务表-工程款拨付单不存在，id值为：{}", id);
        }
        return bizProjectFunds;
    }
}
