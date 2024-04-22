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
import vip.xiaonuo.biz.modular.plan.entity.ContractBidDept;
import vip.xiaonuo.biz.modular.plan.mapper.ContractBidDeptMapper;
import vip.xiaonuo.biz.modular.plan.param.ContractBidDeptAddParam;
import vip.xiaonuo.biz.modular.plan.param.ContractBidDeptEditParam;
import vip.xiaonuo.biz.modular.plan.param.ContractBidDeptIdParam;
import vip.xiaonuo.biz.modular.plan.param.ContractBidDeptPageParam;
import vip.xiaonuo.biz.modular.plan.service.ContractBidDeptService;

import java.util.List;

/**
 * 合约规划 - 投标组织Service接口实现类
 *
 * @author yong.ye
 * @date  2024/04/22 11:42
 **/
@Service
@DS("slave_1")
public class ContractBidDeptServiceImpl extends ServiceImpl<ContractBidDeptMapper, ContractBidDept> implements ContractBidDeptService {

    @Override
    public Page<ContractBidDept> page(ContractBidDeptPageParam contractBidDeptPageParam) {
        QueryWrapper<ContractBidDept> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(contractBidDeptPageParam.getDeptType())) {
            queryWrapper.lambda().eq(ContractBidDept::getDeptType, contractBidDeptPageParam.getDeptType());
        }
        if(ObjectUtil.isNotEmpty(contractBidDeptPageParam.getDeptName())) {
            queryWrapper.lambda().like(ContractBidDept::getDeptName, contractBidDeptPageParam.getDeptName());
        }
        if(ObjectUtil.isNotEmpty(contractBidDeptPageParam.getManager())) {
            queryWrapper.lambda().like(ContractBidDept::getManager, contractBidDeptPageParam.getManager());
        }
        if(ObjectUtil.isAllNotEmpty(contractBidDeptPageParam.getSortField(), contractBidDeptPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(contractBidDeptPageParam.getSortOrder());
            queryWrapper.orderBy(true, contractBidDeptPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(contractBidDeptPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ContractBidDept::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ContractBidDeptAddParam contractBidDeptAddParam) {
        ContractBidDept contractBidDept = BeanUtil.toBean(contractBidDeptAddParam, ContractBidDept.class);
        this.save(contractBidDept);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ContractBidDeptEditParam contractBidDeptEditParam) {
        ContractBidDept contractBidDept = this.queryEntity(contractBidDeptEditParam.getId());
        BeanUtil.copyProperties(contractBidDeptEditParam, contractBidDept);
        this.updateById(contractBidDept);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ContractBidDeptIdParam> contractBidDeptIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(contractBidDeptIdParamList, ContractBidDeptIdParam::getId));
    }

    @Override
    public ContractBidDept detail(ContractBidDeptIdParam contractBidDeptIdParam) {
        return this.queryEntity(contractBidDeptIdParam.getId());
    }

    @Override
    public ContractBidDept queryEntity(String id) {
        ContractBidDept contractBidDept = this.getById(id);
        if(ObjectUtil.isEmpty(contractBidDept)) {
            throw new CommonException("合约规划 - 投标组织不存在，id值为：{}", id);
        }
        return contractBidDept;
    }
}
