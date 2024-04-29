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
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.task.entity.BizTaskRating;
import vip.xiaonuo.biz.modular.task.mapper.BizTaskRatingMapper;
import vip.xiaonuo.biz.modular.task.param.BizTaskRatingAddParam;
import vip.xiaonuo.biz.modular.task.param.BizTaskRatingEditParam;
import vip.xiaonuo.biz.modular.task.param.BizTaskRatingIdParam;
import vip.xiaonuo.biz.modular.task.param.BizTaskRatingPageParam;
import vip.xiaonuo.biz.modular.task.service.BizTaskRatingService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门考核 - 绩效评估流程业务表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/04/29 10:08
 **/
@Service
@DS("slave_1")
public class BizTaskRatingServiceImpl extends ServiceImpl<BizTaskRatingMapper, BizTaskRating> implements BizTaskRatingService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<BizTaskRating> page(BizTaskRatingPageParam bizTaskRatingPageParam) {
        QueryWrapper<BizTaskRating> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizTaskRatingPageParam.getSn())) {
            queryWrapper.lambda().like(BizTaskRating::getSn, bizTaskRatingPageParam.getSn());
        }
        if(ObjectUtil.isAllNotEmpty(bizTaskRatingPageParam.getSortField(), bizTaskRatingPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTaskRatingPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTaskRatingPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTaskRatingPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizTaskRating::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTaskRatingAddParam bizTaskRatingAddParam) {
        BizTaskRating taskRating = BeanUtil.toBean(bizTaskRatingAddParam, BizTaskRating.class);
        //走审批流程
        String processInstanceId = bizTaskRatingAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            taskRating.setId(id);
            taskRating.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            taskRating.setModelId(modelId);
            taskRating.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_TASK_RATING.getBizTable(),id,processInstanceId,dbName);
        }else{
            taskRating.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(taskRating);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTaskRatingEditParam bizTaskRatingEditParam) {
        String id = bizTaskRatingEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizTaskRatingEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizTaskRatingEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizTaskRatingEditParam.setId(id);
            }
        }
        BizTaskRating taskRating = this.queryEntity(bizTaskRatingEditParam.getId());
        BeanUtil.copyProperties(bizTaskRatingEditParam, taskRating);
        this.updateById(taskRating);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTaskRatingIdParam> bizTaskRatingIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizTaskRatingIdParamList, BizTaskRatingIdParam::getId));
    }

    @Override
    public BizTaskRating detail(BizTaskRatingIdParam bizTaskRatingIdParam) {
        return this.queryEntity(bizTaskRatingIdParam.getId());
    }

    @Override
    public BizTaskRating queryEntity(String id) {
        BizTaskRating bizTaskRating = this.getById(id);
        if(ObjectUtil.isEmpty(bizTaskRating)) {
            throw new CommonException("部门考核 - 绩效评估流程业务表不存在，id值为：{}", id);
        }
        return bizTaskRating;
    }
}
