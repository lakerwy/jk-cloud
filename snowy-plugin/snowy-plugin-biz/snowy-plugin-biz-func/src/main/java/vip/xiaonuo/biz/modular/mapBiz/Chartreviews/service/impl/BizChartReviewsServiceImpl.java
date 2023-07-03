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
import vip.xiaonuo.biz.core.enums.BizTableCommon;
import vip.xiaonuo.biz.core.utils.CommonUtils;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.mapper.BizChartReviewsMapper;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.service.BizChartReviewsService;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.vo.BizChartReviewsDetailsVo;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.vo.BizChartReviewsVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.entity.BizChartReviews;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.param.BizChartReviewsAddParam;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.param.BizChartReviewsEditParam;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.param.BizChartReviewsIdParam;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.param.BizChartReviewsPageParam;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 业务表-图审受理单
Service接口实现类
 *
 * @author mayike
 * @date  2023/06/30 16:07
 **/
@Service
@DS("slave_1")
public class BizChartReviewsServiceImpl extends ServiceImpl<BizChartReviewsMapper, BizChartReviews> implements BizChartReviewsService {

    @Resource
    BizChartReviewsMapper bizChartReviewsMapper;

    @Override
    public Page<BizChartReviews> page(BizChartReviewsPageParam bizChartReviewsPageParam) {
        QueryWrapper<BizChartReviews> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizChartReviewsPageParam.getSn())) {
            queryWrapper.lambda().like(BizChartReviews::getSn, bizChartReviewsPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizChartReviewsPageParam.getStartOptDate()) && ObjectUtil.isNotEmpty(bizChartReviewsPageParam.getEndOptDate())) {
            queryWrapper.lambda().between(BizChartReviews::getOptDate, bizChartReviewsPageParam.getStartOptDate(), bizChartReviewsPageParam.getEndOptDate());
        }
        if(ObjectUtil.isNotEmpty(bizChartReviewsPageParam.getCompanyOrDepartment())) {
            queryWrapper.lambda().like(BizChartReviews::getCompanyOrDepartment, bizChartReviewsPageParam.getCompanyOrDepartment());
        }
        if(ObjectUtil.isNotEmpty(bizChartReviewsPageParam.getProjectLeader())) {
            queryWrapper.lambda().like(BizChartReviews::getProjectLeader, bizChartReviewsPageParam.getProjectLeader());
        }
        if(ObjectUtil.isAllNotEmpty(bizChartReviewsPageParam.getSortField(), bizChartReviewsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizChartReviewsPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizChartReviewsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizChartReviewsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizChartReviews::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizChartReviewsAddParam bizChartReviewsAddParam) {
        BizChartReviews bizChartReviews = BeanUtil.toBean(bizChartReviewsAddParam, BizChartReviews.class);
        bizChartReviews.setSn(CommonUtils.getTableSn(BizTableCommon.BIZ_CHART_REVIEWS));
        bizChartReviews.setOpt(StpLoginUserUtil.getLoginUser().getName());
        bizChartReviews.setOptDate(DateUtil.date(new Date()));
        this.save(bizChartReviews);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizChartReviewsEditParam bizChartReviewsEditParam) {
        BizChartReviews bizChartReviews = this.queryEntity(bizChartReviewsEditParam.getId());
        BeanUtil.copyProperties(bizChartReviewsEditParam, bizChartReviews);
        this.updateById(bizChartReviews);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizChartReviewsIdParam> bizChartReviewsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizChartReviewsIdParamList, BizChartReviewsIdParam::getId));
    }

    @Override
    public BizChartReviewsDetailsVo detail(BizChartReviewsIdParam bizChartReviewsIdParam) {
        return bizChartReviewsMapper.getCRMDetailById(bizChartReviewsIdParam.getId());
        //return this.queryEntity(bizChartReviewsIdParam.getId());
    }

    @Override
    public BizChartReviews queryEntity(String id) {
        BizChartReviews bizChartReviews = this.getById(id);
        if(ObjectUtil.isEmpty(bizChartReviews)) {
            throw new CommonException("业务表-图审受理单 不存在，id值为：{}", id);
        }
        return bizChartReviews;
    }

    @Override
    public Page<BizChartReviewsVo> pageList(BizChartReviewsPageParam bizChartReviewsPageParam) {
        QueryWrapper<BizChartReviews> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizChartReviewsPageParam.getSn())){
            queryWrapper.like("a.SN",bizChartReviewsPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizChartReviewsPageParam.getProjectName())){
            queryWrapper.like("b.PROJECT_NAME",bizChartReviewsPageParam.getProjectName());
        }
        if(ObjectUtil.isNotEmpty(bizChartReviewsPageParam.getSecondLevel())){
            queryWrapper.like("b.SECOND_LEVEL",bizChartReviewsPageParam.getSecondLevel());
        }
        if(ObjectUtil.isNotEmpty(bizChartReviewsPageParam.getProjectLeader())){
            queryWrapper.like("a.PROJECT_LEADER",bizChartReviewsPageParam.getProjectLeader());
        }
        if(ObjectUtil.isNotEmpty(bizChartReviewsPageParam.getProjectNo())){
            queryWrapper.like("a.PROJECT_NO",bizChartReviewsPageParam.getProjectNo());
        }
        if(ObjectUtil.isNotEmpty(bizChartReviewsPageParam.getCompanyOrDepartment())){
            queryWrapper.like("a.COMPANY_OR_DEPARTMENT",bizChartReviewsPageParam.getCompanyOrDepartment());
        }
        if(ObjectUtil.isNotEmpty(bizChartReviewsPageParam.getStartOptDate()) && ObjectUtil.isNotEmpty(bizChartReviewsPageParam.getEndOptDate())) {
            queryWrapper.between("a.OPT_DATE", bizChartReviewsPageParam.getStartOptDate(), bizChartReviewsPageParam.getEndOptDate());
        }
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isAllNotEmpty(bizChartReviewsPageParam.getSortField(), bizChartReviewsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizChartReviewsPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizChartReviewsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizChartReviewsPageParam.getSortField()));
        } else {
            queryWrapper.orderByAsc("a.ID");
        }

        return bizChartReviewsMapper.getPageList(CommonPageRequest.defaultPage(),queryWrapper);
    }
}
