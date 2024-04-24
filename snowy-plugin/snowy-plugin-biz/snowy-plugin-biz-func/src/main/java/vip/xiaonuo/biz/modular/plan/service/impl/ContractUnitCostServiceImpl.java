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
package vip.xiaonuo.biz.modular.plan.service.impl;

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
import vip.xiaonuo.biz.modular.plan.entity.ContractUnitCost;
import vip.xiaonuo.biz.modular.plan.mapper.ContractUnitCostMapper;
import vip.xiaonuo.biz.modular.plan.param.ContractUnitCostAddParam;
import vip.xiaonuo.biz.modular.plan.param.ContractUnitCostEditParam;
import vip.xiaonuo.biz.modular.plan.param.ContractUnitCostIdParam;
import vip.xiaonuo.biz.modular.plan.param.ContractUnitCostPageParam;
import vip.xiaonuo.biz.modular.plan.service.ContractUnitCostService;

import java.util.List;

/**
 * 合约规划 - 申报（项目承包方）- 成本Service接口实现类
 *
 * @author yong.ye
 * @date  2024/04/22 11:44
 **/
@Service
@DS("slave_1")
public class ContractUnitCostServiceImpl extends ServiceImpl<ContractUnitCostMapper, ContractUnitCost> implements ContractUnitCostService {

    @Override
    public Page<ContractUnitCost> page(ContractUnitCostPageParam contractUnitCostPageParam) {
        QueryWrapper<ContractUnitCost> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(contractUnitCostPageParam.getItemName())) {
            queryWrapper.lambda().like(ContractUnitCost::getItemName, contractUnitCostPageParam.getItemName());
        }
        if(ObjectUtil.isAllNotEmpty(contractUnitCostPageParam.getSortField(), contractUnitCostPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(contractUnitCostPageParam.getSortOrder());
            queryWrapper.orderBy(true, contractUnitCostPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(contractUnitCostPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ContractUnitCost::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ContractUnitCostAddParam contractUnitCostAddParam) {
        ContractUnitCost contractUnitCost = BeanUtil.toBean(contractUnitCostAddParam, ContractUnitCost.class);
        this.save(contractUnitCost);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ContractUnitCostEditParam contractUnitCostEditParam) {
        ContractUnitCost contractUnitCost = this.queryEntity(contractUnitCostEditParam.getId());
        BeanUtil.copyProperties(contractUnitCostEditParam, contractUnitCost);
        this.updateById(contractUnitCost);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ContractUnitCostIdParam> contractUnitCostIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(contractUnitCostIdParamList, ContractUnitCostIdParam::getId));
    }

    @Override
    public ContractUnitCost detail(ContractUnitCostIdParam contractUnitCostIdParam) {
        return this.queryEntity(contractUnitCostIdParam.getId());
    }

    @Override
    public ContractUnitCost queryEntity(String id) {
        ContractUnitCost contractUnitCost = this.getById(id);
        if(ObjectUtil.isEmpty(contractUnitCost)) {
            throw new CommonException("合约规划 - 申报（项目承包方）- 成本不存在，id值为：{}", id);
        }
        return contractUnitCost;
    }
}
