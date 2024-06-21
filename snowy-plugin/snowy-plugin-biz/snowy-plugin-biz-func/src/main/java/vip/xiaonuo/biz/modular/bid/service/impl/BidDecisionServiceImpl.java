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
package vip.xiaonuo.biz.modular.bid.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.bid.entity.BidDecision;
import vip.xiaonuo.biz.modular.bid.mapper.BidDecisionMapper;
import vip.xiaonuo.biz.modular.bid.param.BidDecisionAddParam;
import vip.xiaonuo.biz.modular.bid.param.BidDecisionEditParam;
import vip.xiaonuo.biz.modular.bid.param.BidDecisionIdParam;
import vip.xiaonuo.biz.modular.bid.param.BidDecisionPageParam;
import vip.xiaonuo.biz.modular.bid.service.BidDecisionService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 投标决策 - 申请流程Service接口实现类
 *
 * @author yong.ye
 * @date  2024/06/21 10:30
 **/
@Service
@DS("slave_1")
public class BidDecisionServiceImpl extends ServiceImpl<BidDecisionMapper, BidDecision> implements BidDecisionService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<BidDecision> page(BidDecisionPageParam bidDecisionPageParam) {
        QueryWrapper<BidDecision> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(BidDecision::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(bidDecisionPageParam.getSn())){
            queryWrapper.lambda().like(BidDecision::getSn,bidDecisionPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bidDecisionPageParam.getStatus())){
            queryWrapper.lambda().eq(BidDecision::getStatus,bidDecisionPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(bidDecisionPageParam.getPrjName())) {
            queryWrapper.lambda().like(BidDecision::getPrjName, bidDecisionPageParam.getPrjName());
        }
        if(ObjectUtil.isNotEmpty(bidDecisionPageParam.getPrjSn())) {
            queryWrapper.lambda().like(BidDecision::getPrjSn, bidDecisionPageParam.getPrjSn());
        }
        if(ObjectUtil.isNotEmpty(bidDecisionPageParam.getPrjApplyUser())) {
            queryWrapper.lambda().like(BidDecision::getPrjApplyUser, bidDecisionPageParam.getPrjApplyUser());
        }
        if(ObjectUtil.isNotEmpty(bidDecisionPageParam.getContractType())) {
            queryWrapper.lambda().eq(BidDecision::getContractType, bidDecisionPageParam.getContractType());
        }
        if(ObjectUtil.isNotEmpty(bidDecisionPageParam.getIsHead())) {
            queryWrapper.lambda().eq(BidDecision::getIsHead, bidDecisionPageParam.getIsHead());
        }
        if(ObjectUtil.isNotEmpty(bidDecisionPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BidDecision::getSn,bidDecisionPageParam.getSearchKey())
                    .or().like(BidDecision::getPrjName,bidDecisionPageParam.getSearchKey())
                    .or().like(BidDecision::getPrjSn,bidDecisionPageParam.getSearchKey())
                    .or().like(BidDecision::getPrjApplyUser,bidDecisionPageParam.getSearchKey()));
        }
        // 校验数据范围（由于本列表是根据组织架构决定的）
//        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
//        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
//            queryWrapper.lambda().in(BidDecision::getDataScopeId, loginUserDataScope);
//        } else {
//            queryWrapper.lambda().eq(BidDecision::getCreateUser, StpUtil.getLoginIdAsString());
//        }
        if(ObjectUtil.isAllNotEmpty(bidDecisionPageParam.getSortField(), bidDecisionPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bidDecisionPageParam.getSortOrder());
            queryWrapper.orderBy(true, bidDecisionPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bidDecisionPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BidDecision::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BidDecisionAddParam bidDecisionAddParam) {
        BidDecision bidDecision = BeanUtil.toBean(bidDecisionAddParam, BidDecision.class);
        //走审批流程
        String processInstanceId = bidDecisionAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bidDecision.setId(id);
            bidDecision.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bidDecision.setModelId(modelId);
            bidDecision.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_BID_DECISION.getBizTable(),id,processInstanceId,dbName);
        }else{
            bidDecision.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bidDecision);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BidDecisionEditParam bidDecisionEditParam) {
        String id = bidDecisionEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bidDecisionEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bidDecisionEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bidDecisionEditParam.setId(id);
            }
        }
        BidDecision bidDecision = this.queryEntity(bidDecisionEditParam.getId());
        BeanUtil.copyProperties(bidDecisionEditParam, bidDecision);
        this.updateById(bidDecision);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BidDecisionIdParam> bidDecisionIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bidDecisionIdParamList, BidDecisionIdParam::getId));
    }

    @Override
    public BidDecision detail(BidDecisionIdParam bidDecisionIdParam) {
        return this.queryEntity(bidDecisionIdParam.getId());
    }

    @Override
    public BidDecision queryEntity(String id) {
        BidDecision bidDecision = this.getById(id);
        if(ObjectUtil.isEmpty(bidDecision)) {
            throw new CommonException("投标决策 - 申请流程不存在，id值为：{}", id);
        }
        return bidDecision;
    }
}
