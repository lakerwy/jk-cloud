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
package vip.xiaonuo.biz.modular.mapBiz.Chartreviews.service.impl;

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
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.service.BizChartReviewDetailsService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.entity.BizChartReviewDetails;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.mapper.BizChartReviewDetailsMapper;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.param.BizChartReviewDetailsAddParam;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.param.BizChartReviewDetailsEditParam;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.param.BizChartReviewDetailsIdParam;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.param.BizChartReviewDetailsPageParam;

import java.util.List;

/**
 * 业务表-图审详情记录表Service接口实现类
 *
 * @author mayike
 * @date  2023/06/30 16:39
 **/
@Service
@DS("slave_1")
public class BizChartReviewDetailsServiceImpl extends ServiceImpl<BizChartReviewDetailsMapper, BizChartReviewDetails> implements BizChartReviewDetailsService {

    @Override
    public Page<BizChartReviewDetails> page(BizChartReviewDetailsPageParam bizChartReviewDetailsPageParam) {
        QueryWrapper<BizChartReviewDetails> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizChartReviewDetailsPageParam.getMajorName())) {
            queryWrapper.lambda().like(BizChartReviewDetails::getMajorName, bizChartReviewDetailsPageParam.getMajorName());
        }
        if(ObjectUtil.isNotEmpty(bizChartReviewDetailsPageParam.getLeader())) {
            queryWrapper.lambda().like(BizChartReviewDetails::getLeader, bizChartReviewDetailsPageParam.getLeader());
        }
        if(ObjectUtil.isAllNotEmpty(bizChartReviewDetailsPageParam.getSortField(), bizChartReviewDetailsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizChartReviewDetailsPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizChartReviewDetailsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizChartReviewDetailsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizChartReviewDetails::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizChartReviewDetailsAddParam bizChartReviewDetailsAddParam) {
        BizChartReviewDetails bizChartReviewDetails = BeanUtil.toBean(bizChartReviewDetailsAddParam, BizChartReviewDetails.class);
        this.save(bizChartReviewDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizChartReviewDetailsEditParam bizChartReviewDetailsEditParam) {
        BizChartReviewDetails bizChartReviewDetails = this.queryEntity(bizChartReviewDetailsEditParam.getId());
        BeanUtil.copyProperties(bizChartReviewDetailsEditParam, bizChartReviewDetails);
        this.updateById(bizChartReviewDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizChartReviewDetailsIdParam> bizChartReviewDetailsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizChartReviewDetailsIdParamList, BizChartReviewDetailsIdParam::getId));
    }

    @Override
    public BizChartReviewDetails detail(BizChartReviewDetailsIdParam bizChartReviewDetailsIdParam) {
        return this.queryEntity(bizChartReviewDetailsIdParam.getId());
    }

    @Override
    public BizChartReviewDetails queryEntity(String id) {
        BizChartReviewDetails bizChartReviewDetails = this.getById(id);
        if(ObjectUtil.isEmpty(bizChartReviewDetails)) {
            throw new CommonException("业务表-图审详情记录表不存在，id值为：{}", id);
        }
        return bizChartReviewDetails;
    }
}
