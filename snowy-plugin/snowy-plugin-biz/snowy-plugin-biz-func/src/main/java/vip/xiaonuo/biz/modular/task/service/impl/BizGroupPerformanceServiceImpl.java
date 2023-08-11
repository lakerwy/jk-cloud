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
import vip.xiaonuo.biz.modular.task.entity.BizGroupPerformance;
import vip.xiaonuo.biz.modular.task.mapper.BizGroupPerformanceMapper;
import vip.xiaonuo.biz.modular.task.param.BizGroupPerformanceAddParam;
import vip.xiaonuo.biz.modular.task.param.BizGroupPerformanceEditParam;
import vip.xiaonuo.biz.modular.task.param.BizGroupPerformanceIdParam;
import vip.xiaonuo.biz.modular.task.param.BizGroupPerformancePageParam;
import vip.xiaonuo.biz.modular.task.service.BizGroupPerformanceService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * 业务表-绩效考核-集团经营业绩绩点分Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/11 14:07
 **/

@DS("slave_1")
@Service
public class BizGroupPerformanceServiceImpl extends ServiceImpl<BizGroupPerformanceMapper, BizGroupPerformance> implements BizGroupPerformanceService {

    @Override
    public Page<BizGroupPerformance> page(BizGroupPerformancePageParam bizGroupPerformancePageParam) {
        QueryWrapper<BizGroupPerformance> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizGroupPerformancePageParam.getAssYear())) {
            queryWrapper.lambda().eq(BizGroupPerformance::getAssYear, bizGroupPerformancePageParam.getAssYear());
        }
        if(ObjectUtil.isAllNotEmpty(bizGroupPerformancePageParam.getSortField(), bizGroupPerformancePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizGroupPerformancePageParam.getSortOrder());
            queryWrapper.orderBy(true, bizGroupPerformancePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizGroupPerformancePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizGroupPerformance::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizGroupPerformanceAddParam bizGroupPerformanceAddParam) {
        BizGroupPerformance bizGroupPerformance = BeanUtil.toBean(bizGroupPerformanceAddParam, BizGroupPerformance.class);
        //同一个年度只能存在一条有效数据
        BizGroupPerformance tmp = this.getOne(new QueryWrapper<BizGroupPerformance>().lambda()
                .eq(BizGroupPerformance::getAssYear,bizGroupPerformanceAddParam.getAssYear()));
        if(ObjectUtil.isNotEmpty(tmp)){
            throw new CommonException("已存在{}年度数据",bizGroupPerformanceAddParam.getAssYear());
        }
        this.save(bizGroupPerformance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizGroupPerformanceEditParam bizGroupPerformanceEditParam) {
        BizGroupPerformance bizGroupPerformance = this.queryEntity(bizGroupPerformanceEditParam.getId());
        BeanUtil.copyProperties(bizGroupPerformanceEditParam, bizGroupPerformance);
        this.updateById(bizGroupPerformance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizGroupPerformanceIdParam> bizGroupPerformanceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizGroupPerformanceIdParamList, BizGroupPerformanceIdParam::getId));
    }

    @Override
    public BizGroupPerformance detail(BizGroupPerformanceIdParam bizGroupPerformanceIdParam) {
        return this.queryEntity(bizGroupPerformanceIdParam.getId());
    }

    @Override
    public BizGroupPerformance queryEntity(String id) {
        BizGroupPerformance bizGroupPerformance = this.getById(id);
        if(ObjectUtil.isEmpty(bizGroupPerformance)) {
            throw new CommonException("业务表-绩效考核-集团经营业绩绩点分不存在，id值为：{}", id);
        }
        return bizGroupPerformance;
    }
}
