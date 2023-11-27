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
package vip.xiaonuo.biz.modular.visit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.common.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.visit.entity.BizVisitList;
import vip.xiaonuo.biz.modular.visit.param.*;
import vip.xiaonuo.biz.modular.visit.service.BizVisitListService;
import vip.xiaonuo.biz.modular.visit.vo.BizVisitVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.visit.entity.BizVisit;
import vip.xiaonuo.biz.modular.visit.mapper.BizVisitMapper;
import vip.xiaonuo.biz.modular.visit.service.BizVisitService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 外访表Service接口实现类
 *
 * @author kaiminglu
 * @date 2023/10/12 10:35
 **/
@Service
@DS("slave_1")
public class BizVisitServiceImpl extends ServiceImpl<BizVisitMapper, BizVisit> implements BizVisitService {
    @Resource
    private BizVisitListService bizVisitListService;

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<BizVisit> page(BizVisitPageParam bizVisitPageParam) {
        QueryWrapper<BizVisit> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(BizVisit::getStatus,"0","1");
        if(ObjectUtil.isNotEmpty(bizVisitPageParam.getStatus())){
            queryWrapper.lambda().eq(BizVisit::getStatus,bizVisitPageParam.getStatus());
        }
        if (ObjectUtil.isNotEmpty(bizVisitPageParam.getSearchKey())) {
            queryWrapper.lambda().and(q->q.like(BizVisit::getPurpose, bizVisitPageParam.getSearchKey())
                    .or().like(BizVisit::getUnit,bizVisitPageParam.getSearchKey())
                    .or().like(BizVisit::getSn,bizVisitPageParam.getSearchKey()));
        }
        if (ObjectUtil.isAllNotEmpty(bizVisitPageParam.getSortField(), bizVisitPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizVisitPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizVisitPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizVisitPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizVisit::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizVisitAddParam bizVisitAddParam) {
        BizVisit bizVisit = BeanUtil.toBean(bizVisitAddParam, BizVisit.class);
        //走审批流程
        String processInstanceId = bizVisitAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizVisit.setId(id);
            bizVisit.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizVisit.setModelId(modelId);
            bizVisit.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_VISIT.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizVisit.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        bizVisit.setExtJson(JsonUtils.object2json(bizVisit));
        this.save(bizVisit);
        if (CollUtil.isNotEmpty(bizVisitAddParam.getParamList())) {
            List<BizVisitList> bizVisitList = bizVisitAddParam.getParamList();
            bizVisitList.forEach(v -> {
                v.setVisitId(bizVisit.getId());
            });
            bizVisitListService.saveBatch(bizVisitList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizVisitEditParam bizVisitEditParam) {
        String id = bizVisitEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizVisitEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizVisitEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizVisitEditParam.setId(id);
            }
        }
        BizVisit bizVisit = this.queryEntity(bizVisitEditParam.getId());
        BeanUtil.copyProperties(bizVisitEditParam, bizVisit);
        bizVisit.setExtJson(JsonUtils.object2json(bizVisit));
        this.updateById(bizVisit);
        if (CollUtil.isNotEmpty(bizVisitEditParam.getParamList())) {
            //本次传入的数据
            List<BizVisitList> bizVisitList = bizVisitEditParam.getParamList();
            bizVisitList.forEach(v -> {
                v.setVisitId(bizVisit.getId());
            });
            List<BizVisitList> oldVizVisitList = bizVisitListService.list(
                    new QueryWrapper<BizVisitList>().lambda().eq(BizVisitList::getVisitId, bizVisit.getId()));
            List<String> oldIds = oldVizVisitList.stream().map(v -> v.getId()).collect(Collectors.toList());
            //待新增
            List<BizVisitList> addList = bizVisitList.stream().filter(v -> !oldIds.contains(v.getId())).collect(Collectors.toList());
            //待更新
            List<BizVisitList> updateList = bizVisitList.stream().filter(v -> oldIds.contains(v.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(v -> v.getId()).collect(Collectors.toList());
            List<BizVisitList> removeList = oldVizVisitList.stream().filter(v -> !updateIds.contains(v.getId())).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(addList)) {
                bizVisitListService.saveBatch(addList);
            }
            if (CollUtil.isNotEmpty(updateList)) {
                bizVisitListService.updateBatchById(updateList);
            }
            if (CollUtil.isNotEmpty(removeList)) {
                bizVisitListService.removeByIds(removeList);
            }
        } else {
            //删除历史数据（历史有数据，本次全部删除）
            bizVisitListService.remove(new QueryWrapper<BizVisitList>().lambda().eq(BizVisitList::getVisitId, bizVisit.getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizVisitIdParam> bizVisitIdParamList) {
        List<String> ids = CollStreamUtil.toList(bizVisitIdParamList, BizVisitIdParam::getId);
        // 执行删除
        this.removeByIds(ids);
        bizVisitListService.remove((new QueryWrapper<BizVisitList>().lambda().in(BizVisitList::getVisitId, ids)));
    }

    @Override
    public BizVisitVo detail(BizVisitIdParam bizVisitIdParam) {
        BizVisit bizVisit = this.queryEntity(bizVisitIdParam.getId());
        BizVisitVo result = BeanUtil.toBean(bizVisit, BizVisitVo.class);
        result.setParamList(bizVisitListService.list(new QueryWrapper<BizVisitList>().lambda().eq(BizVisitList::getVisitId, bizVisitIdParam.getId()).orderByAsc(BizVisitList::getPlanDate)));
        return result;
    }

    @Override
    public BizVisit queryEntity(String id) {
        BizVisit bizVisit = this.getById(id);
        if (ObjectUtil.isEmpty(bizVisit)) {
            throw new CommonException("业务表-外访计划表，id值为：{}", id);
        }
        return bizVisit;
    }

    @Override
    public void summary(SummaryParam summaryParam) {
        BizVisit bizVisit = this.queryEntity(summaryParam.getId());
        bizVisit.setSumPurpose(summaryParam.getSumPurpose());
        bizVisit.setSumImplComplete(summaryParam.getSumImplComplete());
        bizVisit.setSumPlanComplete(summaryParam.getSumPlanComplete());
        bizVisit.setSumSummary(summaryParam.getSumSummary());
        this.updateById(bizVisit);
    }
}
