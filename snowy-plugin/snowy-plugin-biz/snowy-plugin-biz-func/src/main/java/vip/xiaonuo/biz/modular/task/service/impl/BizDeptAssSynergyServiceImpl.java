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
package vip.xiaonuo.biz.modular.task.service.impl;

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
import vip.xiaonuo.biz.modular.task.entity.BizDeptAssSynergy;
import vip.xiaonuo.biz.modular.task.mapper.BizDeptAssSynergyMapper;
import vip.xiaonuo.biz.modular.task.param.BizDeptAssSynergyAddParam;
import vip.xiaonuo.biz.modular.task.param.BizDeptAssSynergyEditParam;
import vip.xiaonuo.biz.modular.task.param.BizDeptAssSynergyIdParam;
import vip.xiaonuo.biz.modular.task.param.BizDeptAssSynergyPageParam;
import vip.xiaonuo.biz.modular.task.service.BizDeptAssSynergyService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * 业务表-绩效考核-部门协同分Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/11 14:22
 **/
@DS("slave_1")
@Service
public class BizDeptAssSynergyServiceImpl extends ServiceImpl<BizDeptAssSynergyMapper, BizDeptAssSynergy> implements BizDeptAssSynergyService {

    @Override
    public Page<BizDeptAssSynergy> page(BizDeptAssSynergyPageParam bizDeptAssSynergyPageParam) {
        QueryWrapper<BizDeptAssSynergy> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizDeptAssSynergyPageParam.getOrgId())) {
            queryWrapper.lambda().eq(BizDeptAssSynergy::getOrgId, bizDeptAssSynergyPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizDeptAssSynergyPageParam.getAssYear())) {
            queryWrapper.lambda().eq(BizDeptAssSynergy::getAssYear, bizDeptAssSynergyPageParam.getAssYear());
        }
        if(ObjectUtil.isAllNotEmpty(bizDeptAssSynergyPageParam.getSortField(), bizDeptAssSynergyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizDeptAssSynergyPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizDeptAssSynergyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizDeptAssSynergyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizDeptAssSynergy::getAssYear).orderByAsc(BizDeptAssSynergy::getOrgName);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizDeptAssSynergyAddParam bizDeptAssSynergyAddParam) {
        BizDeptAssSynergy bizDeptAssSynergy = BeanUtil.toBean(bizDeptAssSynergyAddParam, BizDeptAssSynergy.class);
        //同一个年度同一个部门只能存在一条有效数据
        BizDeptAssSynergy tmp = this.getOne(new QueryWrapper<BizDeptAssSynergy>().lambda()
                .eq(BizDeptAssSynergy::getAssYear,bizDeptAssSynergyAddParam.getAssYear())
                .eq(BizDeptAssSynergy::getOrgId,bizDeptAssSynergyAddParam.getOrgId()));
        if(ObjectUtil.isNotEmpty(tmp)){
            throw new CommonException("当前部门已存在{}年度数据",bizDeptAssSynergyAddParam.getAssYear());
        }
        this.save(bizDeptAssSynergy);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizDeptAssSynergyEditParam bizDeptAssSynergyEditParam) {
        //同一个年度同一个部门只能存在一条有效数据
        BizDeptAssSynergy tmp = this.getOne(new QueryWrapper<BizDeptAssSynergy>().lambda()
                .eq(BizDeptAssSynergy::getAssYear,bizDeptAssSynergyEditParam.getAssYear())
                .eq(BizDeptAssSynergy::getOrgId,bizDeptAssSynergyEditParam.getOrgId())
                .ne(BizDeptAssSynergy::getId,bizDeptAssSynergyEditParam.getId()));
        if(ObjectUtil.isNotEmpty(tmp)){
            throw new CommonException("当前部门已存在{}年度数据",bizDeptAssSynergyEditParam.getAssYear());
        }
        BizDeptAssSynergy bizDeptAssSynergy = this.queryEntity(bizDeptAssSynergyEditParam.getId());
        BeanUtil.copyProperties(bizDeptAssSynergyEditParam, bizDeptAssSynergy);
        this.updateById(bizDeptAssSynergy);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizDeptAssSynergyIdParam> bizDeptAssSynergyIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizDeptAssSynergyIdParamList, BizDeptAssSynergyIdParam::getId));
    }

    @Override
    public BizDeptAssSynergy detail(BizDeptAssSynergyIdParam bizDeptAssSynergyIdParam) {
        return this.queryEntity(bizDeptAssSynergyIdParam.getId());
    }

    @Override
    public BizDeptAssSynergy queryEntity(String id) {
        BizDeptAssSynergy bizDeptAssSynergy = this.getById(id);
        if(ObjectUtil.isEmpty(bizDeptAssSynergy)) {
            throw new CommonException("业务表-绩效考核-部门协同分不存在，id值为：{}", id);
        }
        return bizDeptAssSynergy;
    }
}
