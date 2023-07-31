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
package vip.xiaonuo.biz.modular.mapBiz.MapRegister.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.date.DateUtil;
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
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.service.BizMapRegisterService;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.entity.BizMapRegister;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.mapper.BizMapRegisterMapper;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterAddParam;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterEditParam;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterIdParam;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterPageParam;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FormRelationTableApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务表-出图登记记录表Service接口实现类
 *
 * @author mayike
 * @date  2023/07/03 11:46
 **/
@Service
@DS("slave_1")
public class BizMapRegisterServiceImpl extends ServiceImpl<BizMapRegisterMapper, BizMapRegister> implements BizMapRegisterService {

    @Resource
    CommonCacheOperator commonCacheOperator;

    @Override
    public Page<BizMapRegister> page(BizMapRegisterPageParam bizMapRegisterPageParam) {
        QueryWrapper<BizMapRegister> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizMapRegisterPageParam.getSearchKey())){
            queryWrapper.lambda().or(wrapper ->wrapper.like(BizMapRegister::getProjectName,bizMapRegisterPageParam.getSearchKey()).or().
                    like(BizMapRegister::getSn,bizMapRegisterPageParam.getSearchKey()));
        }
        queryWrapper.lambda().in(BizMapRegister::getStatus, FlwStatusEnum.ACTIVE_STATUS.getValue(),FlwStatusEnum.COMPLETED_STATUS.getValue());
        if(ObjectUtil.isAllNotEmpty(bizMapRegisterPageParam.getSortField(), bizMapRegisterPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizMapRegisterPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizMapRegisterPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizMapRegisterPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizMapRegister::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizMapRegisterAddParam bizMapRegisterAddParam) {
        BizMapRegister bizMapRegister = BeanUtil.toBean(bizMapRegisterAddParam, BizMapRegister.class);
        String processInstanceId = bizMapRegisterAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizMapRegister.setId(id);
            bizMapRegister.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizMapRegister.setModelId(modelId);

            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            //将流程实例和业务表关联起来
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            formRelationTableApi.save(modelId, BizCommonEnum.BIZ_MAP_REGISTER.getBizTable(),id,processInstanceId,dbName);
        }
        if(bizMapRegisterAddParam.getIsPost().equals("是")){
            bizMapRegister.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }else {
            bizMapRegister.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
        }
        bizMapRegister.setApplyTime(DateUtil.date());
        bizMapRegister.setApplyUser(StpLoginUserUtil.getLoginUser().getName());

        this.save(bizMapRegister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizMapRegisterEditParam bizMapRegisterEditParam) {
        String id = bizMapRegisterEditParam.getId();
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(id)){
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            JSONObject formRelationTable = formRelationTableApi.getInfoByProcessInstanceId(bizMapRegisterEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id=formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizMapRegisterEditParam.setId(id);
            }
        }
        BizMapRegister bizMapRegister = this.queryEntity(id);
        BeanUtil.copyProperties(bizMapRegisterEditParam, bizMapRegister);
        this.updateById(bizMapRegister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizMapRegisterIdParam> bizMapRegisterIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizMapRegisterIdParamList, BizMapRegisterIdParam::getId));
    }

    @Override
    public BizMapRegister detail(BizMapRegisterIdParam bizMapRegisterIdParam) {
        return this.queryEntity(bizMapRegisterIdParam.getId());
    }

    @Override
    public BizMapRegister queryEntity(String id) {
        BizMapRegister bizMapRegister = this.getById(id);
        if(ObjectUtil.isEmpty(bizMapRegister)) {
            throw new CommonException("业务表-出图登记记录表不存在，id值为：{}", id);
        }
        return bizMapRegister;
    }
}
