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
package vip.xiaonuo.dev.modular.supervisionPlatform.service.impl;

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
import vip.xiaonuo.dev.modular.supervisionPlatform.entity.Tbcorpperformance;
import vip.xiaonuo.dev.modular.supervisionPlatform.mapper.TbcorpperformanceMapper;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TbcorpperformanceAddParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TbcorpperformanceEditParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TbcorpperformanceIdParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TbcorpperformancePageParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.service.TbcorpperformanceService;

import java.util.List;

/**
 * 市场监管公共服务平台-企业业绩技术指标信息表Service接口实现类
 *
 * @author ljy
 * @date  2024/01/11 10:57
 **/
@Service
public class TbcorpperformanceServiceImpl extends ServiceImpl<TbcorpperformanceMapper, Tbcorpperformance> implements TbcorpperformanceService {

    @Override
    public Page<Tbcorpperformance> page(TbcorpperformancePageParam tbcorpperformancePageParam) {
        QueryWrapper<Tbcorpperformance> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tbcorpperformancePageParam.getPerfNum())) {
            queryWrapper.lambda().eq(Tbcorpperformance::getPerfNum, tbcorpperformancePageParam.getPerfNum());
        }
        if(ObjectUtil.isNotEmpty(tbcorpperformancePageParam.getPrjNuim())) {
            queryWrapper.lambda().eq(Tbcorpperformance::getPrjNuim, tbcorpperformancePageParam.getPrjNuim());
        }
        if(ObjectUtil.isNotEmpty(tbcorpperformancePageParam.getCorpCode())) {
            queryWrapper.lambda().eq(Tbcorpperformance::getCorpCode, tbcorpperformancePageParam.getCorpCode());
        }
        if(ObjectUtil.isAllNotEmpty(tbcorpperformancePageParam.getSortField(), tbcorpperformancePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tbcorpperformancePageParam.getSortOrder());
            queryWrapper.orderBy(true, tbcorpperformancePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tbcorpperformancePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Tbcorpperformance::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TbcorpperformanceAddParam tbcorpperformanceAddParam) {
        Tbcorpperformance tbcorpperformance = BeanUtil.toBean(tbcorpperformanceAddParam, Tbcorpperformance.class);
        this.save(tbcorpperformance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbcorpperformanceEditParam tbcorpperformanceEditParam) {
        Tbcorpperformance tbcorpperformance = this.queryEntity(tbcorpperformanceEditParam.getId());
        BeanUtil.copyProperties(tbcorpperformanceEditParam, tbcorpperformance);
        this.updateById(tbcorpperformance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbcorpperformanceIdParam> tbcorpperformanceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tbcorpperformanceIdParamList, TbcorpperformanceIdParam::getId));
    }

    @Override
    public Tbcorpperformance detail(TbcorpperformanceIdParam tbcorpperformanceIdParam) {
        return this.queryEntity(tbcorpperformanceIdParam.getId());
    }

    @Override
    public Tbcorpperformance queryEntity(String id) {
        Tbcorpperformance tbcorpperformance = this.getById(id);
        if(ObjectUtil.isEmpty(tbcorpperformance)) {
            throw new CommonException("市场监管公共服务平台-企业业绩技术指标信息表不存在，id值为：{}", id);
        }
        return tbcorpperformance;
    }
}
