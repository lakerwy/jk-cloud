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
package vip.xiaonuo.biz.modular.mapBiz.AuditLists.service.impl;

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
import vip.xiaonuo.common.consts.CacheConstant;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.dbs.core.util.CommonUtils;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.vo.BizAuditInfoVo;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.entity.BizAuditListsDetails;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.service.BizAuditListsDetailsService;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.entity.BizChartReviews;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.service.BizChartReviewsService;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.entity.BizAuditLists;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.mapper.BizAuditListsMapper;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.param.BizAuditListsAddParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.param.BizAuditListsEditParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.param.BizAuditListsIdParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.param.BizAuditListsPageParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.service.BizAuditListsService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务表-设计校审主表Service接口实现类
 *
 * @author mayike
 * @date  2023/06/30 16:43
 **/
@DS("slave_1")
@Service
public class BizAuditListsServiceImpl extends ServiceImpl<BizAuditListsMapper, BizAuditLists> implements BizAuditListsService {

    @Resource
    BizChartReviewsService bizChartReviewsService;

    @Resource
    BizAuditListsDetailsService bizAuditListsDetailsService;

    @Resource
    CommonCacheOperator commonCacheOperator;

//    @Resource
//    FormRelationTableService formRelationTableService;

    @Override
    public Page<BizAuditLists> page(BizAuditListsPageParam bizAuditListsPageParam) {
        QueryWrapper<BizAuditLists> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizAuditListsPageParam.getSn())) {
            queryWrapper.lambda().like(BizAuditLists::getSn, bizAuditListsPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizAuditListsPageParam.getProjectName())) {
            queryWrapper.lambda().like(BizAuditLists::getProjectName, bizAuditListsPageParam.getProjectName());
        }
        if(ObjectUtil.isNotEmpty(bizAuditListsPageParam.getCrNo())) {
            queryWrapper.lambda().like(BizAuditLists::getCrNo, bizAuditListsPageParam.getCrNo());
        }
        if(ObjectUtil.isNotEmpty(bizAuditListsPageParam.getCompanyOrDepartment())) {
            queryWrapper.lambda().like(BizAuditLists::getCompanyOrDepartment, bizAuditListsPageParam.getCompanyOrDepartment());
        }
        if(ObjectUtil.isNotEmpty(bizAuditListsPageParam.getReviewId())){
            queryWrapper.lambda().eq(BizAuditLists::getReviewId,bizAuditListsPageParam.getReviewId());
        }
        if(ObjectUtil.isAllNotEmpty(bizAuditListsPageParam.getSortField(), bizAuditListsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizAuditListsPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizAuditListsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizAuditListsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizAuditLists::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizAuditListsAddParam bizAuditListsAddParam) {
        BizAuditLists bizAuditLists = BeanUtil.toBean(bizAuditListsAddParam, BizAuditLists.class);
        String processInstanceId = bizAuditListsAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizAuditLists.setId(id);
            bizAuditLists.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizAuditLists.setModelId(modelId);
            bizAuditLists.setApplyTime(DateUtil.date());
            bizAuditLists.setApplyUser(StpLoginUserUtil.getLoginUser().getName());
            //将流程实例和业务表关联起来
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            //formRelationTableService.save(modelId, BizCommon.BIZ_AUDIT_LISTS.getBizTable(),id,processInstanceId,dbName);
        }
        this.save(bizAuditLists);
    }

   // @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizAuditListsEditParam bizAuditListsEditParam) {
        String id = bizAuditListsEditParam.getId();
//        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizAuditListsEditParam.getProcessInstanceId())){
//            FormRelationTable formRelationTable = formRelationTableService.getInfoByProcessInstanceId(bizAuditListsEditParam.getProcessInstanceId());
//            if(ObjectUtil.isNotEmpty(formRelationTable)){
//                id=formRelationTable.getId();
//                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
//                bizAuditListsEditParam.setId(id);
//            }
//        }
        BizAuditLists bizAuditLists = this.queryEntity(id);
        BeanUtil.copyProperties(bizAuditListsEditParam, bizAuditLists);
        this.updateById(bizAuditLists);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizAuditListsIdParam> bizAuditListsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizAuditListsIdParamList, BizAuditListsIdParam::getId));
    }

    @Override
    public BizAuditLists detail(BizAuditListsIdParam bizAuditListsIdParam) {
        return this.queryEntity(bizAuditListsIdParam.getId());
    }

    @Override
    public BizAuditLists queryEntity(String id) {
        BizAuditLists bizAuditLists = this.getById(id);
        if(ObjectUtil.isEmpty(bizAuditLists)) {
            throw new CommonException("业务表-设计校审主表不存在，id值为：{}", id);
        }
        return bizAuditLists;
    }

    @Override
    public BizAuditInfoVo info(BizAuditListsIdParam bizAuditListsIdParam) {
        BizAuditLists bizAuditLists = this.queryEntity(bizAuditListsIdParam.getId());
        String profession = bizAuditLists.getProfession();
        if(CommonUtils.isNumeric(profession)){
            bizAuditLists.setProfession ( (String)commonCacheOperator.get(CommonUtils.MAJOR_TYPE + profession));
//            commonCacheOperator.get(CommonUtils.MAJOR_TYPE_USER+bizAuditLists.getReader())
            String o = String.valueOf(commonCacheOperator.get(CacheConstant.USER_INFO + bizAuditLists.getReader()));
            String s = "";
            if(StrUtil.isNotBlank(o)){
                s = " "+o;
            }
            bizAuditLists.setReader(commonCacheOperator.get(CommonUtils.MAJOR_TYPE_USER+bizAuditLists.getReader()) + s);
        }
        String reviewId = bizAuditLists.getReviewId();
        BizChartReviews bizChartReviews = bizChartReviewsService.getById(reviewId);
        QueryWrapper<BizAuditListsDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BizAuditListsDetails::getAuditId, bizAuditListsIdParam.getId());
        queryWrapper.lambda().orderByAsc(BizAuditListsDetails::getOpinionTime);
        List<BizAuditListsDetails> list = bizAuditListsDetailsService.list(queryWrapper);
        BizAuditInfoVo bizAuditInfoVo = new BizAuditInfoVo();
        bizAuditInfoVo.setBizChartReviews(bizChartReviews);
        bizAuditInfoVo.setBizAuditLists(bizAuditLists);
        bizAuditInfoVo.setBizAuditListsDetailsList(list);
        return bizAuditInfoVo;
    }
}
