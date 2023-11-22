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
package vip.xiaonuo.sys.modular.hr.sub.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubSalary;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubSalaryMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryPageParam;
import vip.xiaonuo.sys.modular.hr.sub.service.SubSalaryService;

import java.util.List;

/**
 * HR-分公司-工资条Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/22 11:20
 **/
@Service
public class SubSalaryServiceImpl extends ServiceImpl<SubSalaryMapper, SubSalary> implements SubSalaryService {

    @Override
    public Page<SubSalary> page(SubSalaryPageParam subSalaryPageParam) {
        QueryWrapper<SubSalary> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(subSalaryPageParam.getOrgName())) {
            queryWrapper.lambda().like(SubSalary::getOrgName, subSalaryPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(subSalaryPageParam.getYear())) {
            queryWrapper.lambda().eq(SubSalary::getYear, subSalaryPageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(subSalaryPageParam.getMonth())) {
            queryWrapper.lambda().eq(SubSalary::getMonth, subSalaryPageParam.getMonth());
        }
        if(ObjectUtil.isAllNotEmpty(subSalaryPageParam.getSortField(), subSalaryPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subSalaryPageParam.getSortOrder());
            queryWrapper.orderBy(true, subSalaryPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subSalaryPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SubSalary::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubSalaryAddParam subSalaryAddParam) {
        SubSalary subSalary = BeanUtil.toBean(subSalaryAddParam, SubSalary.class);
        this.save(subSalary);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubSalaryEditParam subSalaryEditParam) {
        SubSalary subSalary = this.queryEntity(subSalaryEditParam.getId());
        BeanUtil.copyProperties(subSalaryEditParam, subSalary);
        this.updateById(subSalary);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubSalaryIdParam> subSalaryIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(subSalaryIdParamList, SubSalaryIdParam::getId));
    }

    @Override
    public SubSalary detail(SubSalaryIdParam subSalaryIdParam) {
        return this.queryEntity(subSalaryIdParam.getId());
    }

    @Override
    public SubSalary queryEntity(String id) {
        SubSalary subSalary = this.getById(id);
        if(ObjectUtil.isEmpty(subSalary)) {
            throw new CommonException("HR-分公司-工资条不存在，id值为：{}", id);
        }
        return subSalary;
    }
}
