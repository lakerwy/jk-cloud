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
package vip.xiaonuo.biz.modular.crm.fpinformations.service.impl;

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
import vip.xiaonuo.biz.modular.crm.fpinformations.entity.Fpinformations;
import vip.xiaonuo.biz.modular.crm.fpinformations.mapper.FpinformationsMapper;
import vip.xiaonuo.biz.modular.crm.fpinformations.param.FpinformationsAddParam;
import vip.xiaonuo.biz.modular.crm.fpinformations.param.FpinformationsEditParam;
import vip.xiaonuo.biz.modular.crm.fpinformations.param.FpinformationsIdParam;
import vip.xiaonuo.biz.modular.crm.fpinformations.param.FpinformationsPageParam;
import vip.xiaonuo.biz.modular.crm.fpinformations.service.FpinformationsService;

import java.util.List;

/**
 * 业务表-CRM-合作伙伴人员信息Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 16:55
 **/

@DS("slave_1")
@Service
public class FpinformationsServiceImpl extends ServiceImpl<FpinformationsMapper, Fpinformations> implements FpinformationsService {

    @Override
    public Page<Fpinformations> page(FpinformationsPageParam fpinformationsPageParam) {
        QueryWrapper<Fpinformations> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(fpinformationsPageParam.getSn())) {
            queryWrapper.lambda().like(Fpinformations::getSn, fpinformationsPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(fpinformationsPageParam.getOpt())) {
            queryWrapper.lambda().like(Fpinformations::getOpt, fpinformationsPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(fpinformationsPageParam.getStartOptDate()) && ObjectUtil.isNotEmpty(fpinformationsPageParam.getEndOptDate())) {
            queryWrapper.lambda().between(Fpinformations::getOptDate, fpinformationsPageParam.getStartOptDate(), fpinformationsPageParam.getEndOptDate());
        }
        if(ObjectUtil.isNotEmpty(fpinformationsPageParam.getName())) {
            queryWrapper.lambda().like(Fpinformations::getName, fpinformationsPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(fpinformationsPageParam.getPartnerId())) {
            queryWrapper.lambda().eq(Fpinformations::getPartnerId, fpinformationsPageParam.getPartnerId());
        }
        if(ObjectUtil.isNotEmpty(fpinformationsPageParam.getDept())) {
            queryWrapper.lambda().like(Fpinformations::getDept, fpinformationsPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(fpinformationsPageParam.getIdNumber())) {
            queryWrapper.lambda().like(Fpinformations::getIdNumber, fpinformationsPageParam.getIdNumber());
        }
        if(ObjectUtil.isAllNotEmpty(fpinformationsPageParam.getSortField(), fpinformationsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(fpinformationsPageParam.getSortOrder());
            queryWrapper.orderBy(true, fpinformationsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(fpinformationsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Fpinformations::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(FpinformationsAddParam fpinformationsAddParam) {
        Fpinformations fpinformations = BeanUtil.toBean(fpinformationsAddParam, Fpinformations.class);
        this.save(fpinformations);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FpinformationsEditParam fpinformationsEditParam) {
        Fpinformations fpinformations = this.queryEntity(fpinformationsEditParam.getId());
        BeanUtil.copyProperties(fpinformationsEditParam, fpinformations);
        this.updateById(fpinformations);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FpinformationsIdParam> fpinformationsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(fpinformationsIdParamList, FpinformationsIdParam::getId));
    }

    @Override
    public Fpinformations detail(FpinformationsIdParam fpinformationsIdParam) {
        return this.queryEntity(fpinformationsIdParam.getId());
    }

    @Override
    public Fpinformations queryEntity(String id) {
        Fpinformations fpinformations = this.getById(id);
        if(ObjectUtil.isEmpty(fpinformations)) {
            throw new CommonException("业务表-CRM-合作伙伴人员信息不存在，id值为：{}", id);
        }
        return fpinformations;
    }
}
