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
package vip.xiaonuo.biz.modular.develop.service.impl;

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
import vip.xiaonuo.biz.modular.develop.entity.DevConfirm;
import vip.xiaonuo.biz.modular.develop.mapper.DevConfirmMapper;
import vip.xiaonuo.biz.modular.develop.param.DevConfirmAddParam;
import vip.xiaonuo.biz.modular.develop.param.DevConfirmEditParam;
import vip.xiaonuo.biz.modular.develop.param.DevConfirmIdParam;
import vip.xiaonuo.biz.modular.develop.param.DevConfirmPageParam;
import vip.xiaonuo.biz.modular.develop.service.DevConfirmService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 需求单 - 确认流程Service接口实现类
 *
 * @author yong.ye
 * @date  2024/05/27 16:09
 **/
@Service
@DS("slave_1")
public class DevConfirmServiceImpl extends ServiceImpl<DevConfirmMapper, DevConfirm> implements DevConfirmService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<DevConfirm> page(DevConfirmPageParam devConfirmPageParam) {
        QueryWrapper<DevConfirm> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(DevConfirm::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(devConfirmPageParam.getRelatedName())) {
            queryWrapper.lambda().like(DevConfirm::getRelatedName, devConfirmPageParam.getRelatedName());
        }
        if(ObjectUtil.isNotEmpty(devConfirmPageParam.getRelatedSn())) {
            queryWrapper.lambda().like(DevConfirm::getRelatedSn, devConfirmPageParam.getRelatedSn());
        }
        if(ObjectUtil.isNotEmpty(devConfirmPageParam.getSettlementType())) {
            queryWrapper.lambda().eq(DevConfirm::getSettlementType, devConfirmPageParam.getSettlementType());
        }
        if(ObjectUtil.isNotEmpty(devConfirmPageParam.getSn())) {
            queryWrapper.lambda().like(DevConfirm::getSn, devConfirmPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(devConfirmPageParam.getStatus())) {
            queryWrapper.lambda().eq(DevConfirm::getStatus, devConfirmPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(devConfirmPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(DevConfirm::getSn,devConfirmPageParam.getSearchKey())
                    .or().like(DevConfirm::getRelatedSn,devConfirmPageParam.getSearchKey())
                    .or().like(DevConfirm::getRelatedName,devConfirmPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(devConfirmPageParam.getSortField(), devConfirmPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(devConfirmPageParam.getSortOrder());
            queryWrapper.orderBy(true, devConfirmPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(devConfirmPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(DevConfirm::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(DevConfirmAddParam devConfirmAddParam) {
        DevConfirm devConfirm = BeanUtil.toBean(devConfirmAddParam, DevConfirm.class);
        //走审批流程
        String processInstanceId = devConfirmAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            devConfirm.setId(id);
            devConfirm.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            devConfirm.setModelId(modelId);
            devConfirm.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_DEV_CONFIRM.getBizModel(),id,processInstanceId,dbName);
        }else{
            devConfirm.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(devConfirm);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(DevConfirmEditParam devConfirmEditParam) {
        String id = devConfirmEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(devConfirmEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(devConfirmEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                devConfirmEditParam.setId(id);
            }
        }
        DevConfirm devConfirm = this.queryEntity(devConfirmEditParam.getId());
        BeanUtil.copyProperties(devConfirmEditParam, devConfirm);
        this.updateById(devConfirm);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<DevConfirmIdParam> devConfirmIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(devConfirmIdParamList, DevConfirmIdParam::getId));
    }

    @Override
    public DevConfirm detail(DevConfirmIdParam devConfirmIdParam) {
        return this.queryEntity(devConfirmIdParam.getId());
    }

    @Override
    public DevConfirm queryEntity(String id) {
        DevConfirm devConfirm = this.getById(id);
        if(ObjectUtil.isEmpty(devConfirm)) {
            throw new CommonException("需求单 - 确认流程不存在，id值为：{}", id);
        }
        return devConfirm;
    }
}
