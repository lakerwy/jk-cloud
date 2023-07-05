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
package vip.xiaonuo.biz.modular.crm.bidopen.service.impl;

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
import vip.xiaonuo.auth.core.util.StpClientLoginUserUtil;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSnFormatEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.bidopen.entity.Bidopen;
import vip.xiaonuo.biz.modular.crm.bidopen.mapper.BidopenMapper;
import vip.xiaonuo.biz.modular.crm.bidopen.param.BidopenAddParam;
import vip.xiaonuo.biz.modular.crm.bidopen.param.BidopenEditParam;
import vip.xiaonuo.biz.modular.crm.bidopen.param.BidopenIdParam;
import vip.xiaonuo.biz.modular.crm.bidopen.param.BidopenPageParam;
import vip.xiaonuo.biz.modular.crm.bidopen.service.BidopenService;
import vip.xiaonuo.common.util.CommonSnUtil;

import java.util.Date;
import java.util.List;

/**
 * 业务表-CRM-开标登记Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 16:31
 **/
@DS("slave_1")
@Service
public class BidopenServiceImpl extends ServiceImpl<BidopenMapper, Bidopen> implements BidopenService {

    @Override
    public Page<Bidopen> page(BidopenPageParam bidopenPageParam) {
        QueryWrapper<Bidopen> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bidopenPageParam.getOpt())) {
            queryWrapper.lambda().like(Bidopen::getOpt, bidopenPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(bidopenPageParam.getStartOptDate()) && ObjectUtil.isNotEmpty(bidopenPageParam.getEndOptDate())) {
            queryWrapper.lambda().between(Bidopen::getOptDate, bidopenPageParam.getStartOptDate(), bidopenPageParam.getEndOptDate());
        }
        if(ObjectUtil.isNotEmpty(bidopenPageParam.getSn())) {
            queryWrapper.lambda().like(Bidopen::getSn, bidopenPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bidopenPageParam.getProjectId())) {
            queryWrapper.lambda().eq(Bidopen::getProjectId, bidopenPageParam.getProjectId());
        }
        if(ObjectUtil.isNotEmpty(bidopenPageParam.getProjectName())) {
            queryWrapper.lambda().like(Bidopen::getProjectName, bidopenPageParam.getProjectName());
        }
        if(ObjectUtil.isNotEmpty(bidopenPageParam.getBidNo())) {
            queryWrapper.lambda().like(Bidopen::getBidNo, bidopenPageParam.getBidNo());
        }
        if(ObjectUtil.isNotEmpty(bidopenPageParam.getType())) {
            queryWrapper.lambda().eq(Bidopen::getType, bidopenPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(bidopenPageParam.getStates())) {
            queryWrapper.lambda().eq(Bidopen::getStates, bidopenPageParam.getStates());
        }
        if(ObjectUtil.isNotEmpty(bidopenPageParam.getDept())) {
            queryWrapper.lambda().like(Bidopen::getDept, bidopenPageParam.getDept());
        }
        if(ObjectUtil.isAllNotEmpty(bidopenPageParam.getSortField(), bidopenPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bidopenPageParam.getSortOrder());
            queryWrapper.orderBy(true, bidopenPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bidopenPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Bidopen::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BidopenAddParam bidopenAddParam) {
        Bidopen bidopen = BeanUtil.toBean(bidopenAddParam, Bidopen.class);
        //处理Sn编号
        Bidopen tmp = this.getOne(new QueryWrapper<Bidopen>().lambda().orderByDesc(Bidopen::getCreateTime).last("limit 1"));
        String newSn = CommonSnUtil.GenerateSn(bidopenAddParam.getSn(), tmp == null ? "" : tmp.getSn(), CommonSnFormatEnum.SNYYYYMM0001.getValue());
        bidopen.setSn(newSn);
        bidopen.setOpt(StpLoginUserUtil.getLoginUser().getName());
        bidopen.setOptDate(new Date());
        this.save(bidopen);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BidopenEditParam bidopenEditParam) {
        Bidopen bidopen = this.queryEntity(bidopenEditParam.getId());
        BeanUtil.copyProperties(bidopenEditParam, bidopen);
        this.updateById(bidopen);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BidopenIdParam> bidopenIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bidopenIdParamList, BidopenIdParam::getId));
    }

    @Override
    public Bidopen detail(BidopenIdParam bidopenIdParam) {
        return this.queryEntity(bidopenIdParam.getId());
    }

    @Override
    public Bidopen queryEntity(String id) {
        Bidopen bidopen = this.getById(id);
        if(ObjectUtil.isEmpty(bidopen)) {
            throw new CommonException("业务表-CRM-开标登记不存在，id值为：{}", id);
        }
        return bidopen;
    }
}
