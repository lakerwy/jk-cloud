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
package vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.param.BizAuditListsDetailsIdParam;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.entity.BizAuditListsDetails;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.mapper.BizAuditListsDetailsMapper;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.param.BizAuditListsDetailsAddParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.param.BizAuditListsDetailsEditParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.param.BizAuditListsDetailsPageParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.service.BizAuditListsDetailsService;

import java.util.Date;
import java.util.List;

/**
 * 业务表-设计校审记录表Service接口实现类
 *
 * @author mayike
 * @date  2023/07/03 11:33
 **/
@Service
@DS("slave_1")
public class BizAuditListsDetailsServiceImpl extends ServiceImpl<BizAuditListsDetailsMapper, BizAuditListsDetails> implements BizAuditListsDetailsService {

    @Override
    public Page<BizAuditListsDetails> page(BizAuditListsDetailsPageParam bizAuditListsDetailsPageParam) {
        QueryWrapper<BizAuditListsDetails> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizAuditListsDetailsPageParam.getAuditId())){
            queryWrapper.lambda().eq(BizAuditListsDetails::getAuditId,bizAuditListsDetailsPageParam.getAuditId());
        }
        if(ObjectUtil.isAllNotEmpty(bizAuditListsDetailsPageParam.getSortField(), bizAuditListsDetailsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizAuditListsDetailsPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizAuditListsDetailsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizAuditListsDetailsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizAuditListsDetails::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizAuditListsDetailsAddParam bizAuditListsDetailsAddParam) {
        BizAuditListsDetails bizAuditListsDetails = BeanUtil.toBean(bizAuditListsDetailsAddParam, BizAuditListsDetails.class);
        bizAuditListsDetails.setOpinionTime(DateUtil.date(new Date()));
        this.save(bizAuditListsDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizAuditListsDetailsEditParam bizAuditListsDetailsEditParam) {
        BizAuditListsDetails bizAuditListsDetails = this.queryEntity(bizAuditListsDetailsEditParam.getId());
        BeanUtil.copyProperties(bizAuditListsDetailsEditParam, bizAuditListsDetails);
        bizAuditListsDetails.setReviseDate(DateUtil.date(new Date()));
        this.updateById(bizAuditListsDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizAuditListsDetailsIdParam> bizAuditListsDetailsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizAuditListsDetailsIdParamList, BizAuditListsDetailsIdParam::getId));
    }

    @Override
    public BizAuditListsDetails detail(BizAuditListsDetailsIdParam bizAuditListsDetailsIdParam) {
        return this.queryEntity(bizAuditListsDetailsIdParam.getId());
    }

    @Override
    public BizAuditListsDetails queryEntity(String id) {
        BizAuditListsDetails bizAuditListsDetails = this.getById(id);
        if(ObjectUtil.isEmpty(bizAuditListsDetails)) {
            throw new CommonException("业务表-设计校审记录表不存在，id值为：{}", id);
        }
        return bizAuditListsDetails;
    }
}
