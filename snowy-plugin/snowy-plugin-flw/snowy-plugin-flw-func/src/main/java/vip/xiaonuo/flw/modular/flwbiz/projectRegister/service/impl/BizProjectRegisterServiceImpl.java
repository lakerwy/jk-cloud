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
package vip.xiaonuo.flw.modular.flwbiz.projectRegister.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.flw.modular.flwbiz.project.entity.BizProject;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.entity.BizProjectRegister;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.mapper.BizProjectRegisterMapper;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.param.*;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.service.BizProjectRegisterService;
import vip.xiaonuo.flw.modular.relationtable.entity.FormRelationTable;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务表-立项报备Service接口实现类
 *
 * @author mayike
 * @date  2023/06/19 15:26
 **/
@DS("slave_1")
@Service
public class BizProjectRegisterServiceImpl extends ServiceImpl<BizProjectRegisterMapper, BizProjectRegister> implements BizProjectRegisterService {

    @Resource
    FormRelationTableService formRelationTableService;

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<BizProjectRegister> page(BizProjectRegisterPageParam bizProjectRegisterPageParam) {
        QueryWrapper<BizProjectRegister> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizProjectRegisterPageParam.getStartApplyTime()) && ObjectUtil.isNotEmpty(bizProjectRegisterPageParam.getEndApplyTime())) {
            queryWrapper.lambda().between(BizProjectRegister::getApplyTime, bizProjectRegisterPageParam.getStartApplyTime(), bizProjectRegisterPageParam.getEndApplyTime());
        }
        if(ObjectUtil.isNotEmpty(bizProjectRegisterPageParam.getSn())) {
            queryWrapper.lambda().like(BizProjectRegister::getSn, bizProjectRegisterPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizProjectRegisterPageParam.getEntryName())) {
            queryWrapper.lambda().like(BizProjectRegister::getEntryName, bizProjectRegisterPageParam.getEntryName());
        }
        if(ObjectUtil.isNotEmpty(bizProjectRegisterPageParam.getLocation())) {
            queryWrapper.lambda().like(BizProjectRegister::getLocation, bizProjectRegisterPageParam.getLocation());
        }
        if(ObjectUtil.isNotEmpty(bizProjectRegisterPageParam.getScale())) {
            queryWrapper.lambda().like(BizProjectRegister::getScale, bizProjectRegisterPageParam.getScale());
        }
        if(ObjectUtil.isNotEmpty(bizProjectRegisterPageParam.getSearchKey())){
            queryWrapper.lambda().or(wrapper ->wrapper.like(BizProjectRegister::getEntryName,bizProjectRegisterPageParam.getSearchKey())
                    .or().like(BizProjectRegister::getSn,bizProjectRegisterPageParam.getSearchKey())
                    .or().like(BizProjectRegister::getCustomerName,bizProjectRegisterPageParam.getSearchKey()));
        }

        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(BizProjectRegister::getDataScopeId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(BizProjectRegister::getCreateUser,StpUtil.getLoginIdAsString());
        }
        if(ObjectUtil.isNotEmpty(bizProjectRegisterPageParam.getFilterFlag()) && bizProjectRegisterPageParam.getFilterFlag()){
            queryWrapper.lambda().in(BizProjectRegister::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue());
        }else {
            queryWrapper.lambda().in(BizProjectRegister::getStatus,FlwStatusEnum.ACTIVE_STATUS.getValue(),FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        if(ObjectUtil.isAllNotEmpty(bizProjectRegisterPageParam.getSortField(), bizProjectRegisterPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizProjectRegisterPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizProjectRegisterPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizProjectRegisterPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizProjectRegister::getApplyTime,BizProjectRegister::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

   // @Transactional(rollbackFor = Exception.class)
    @Override
    @DSTransactional
    public void add(BizProjectRegisterAddParam bizProjectRegisterAddParam) {
        BizProjectRegister bizProjectRegister = BeanUtil.toBean(bizProjectRegisterAddParam, BizProjectRegister.class);
        //bizProjectRegister.setSn(HandleExecutionUtil.getTableSn(BizCommon.PROJECT_REGISTER.getBizTable()));
        String processInstanceId = bizProjectRegisterAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizProjectRegister.setId(id);
            bizProjectRegister.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizProjectRegister.setModelId(modelId);
            bizProjectRegister.setApplyTime(DateUtil.now());
            bizProjectRegister.setApplyUser(StpLoginUserUtil.getLoginUser().getName());
            //将流程实例和业务表关联起来
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            formRelationTableService.save(modelId, BizCommonEnum.PROJECT_REGISTER.getBizTable(),id,processInstanceId,dbName);
        }
        bizProjectRegister.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
        this.save(bizProjectRegister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizProjectRegisterEditParam bizProjectRegisterEditParam) {
        String id = bizProjectRegisterEditParam.getId();
        if(ObjectUtil.isEmpty(bizProjectRegisterEditParam.getId()) && ObjectUtil.isNotEmpty(bizProjectRegisterEditParam.getProcessInstanceId())){
            FormRelationTable formRelationTable = formRelationTableService.getInfoByProcessInstanceId(bizProjectRegisterEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id=formRelationTable.getId();
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizProjectRegisterEditParam.setId(id);
            }
        }
        BizProjectRegister bizProjectRegister = this.queryEntity(id);
        checkRight(null,bizProjectRegister.getCreateUser());
        BeanUtil.copyProperties(bizProjectRegisterEditParam, bizProjectRegister);
        this.updateById(bizProjectRegister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizProjectRegisterIdParam> bizProjectRegisterIdParamList) {
        bizProjectRegisterIdParamList.forEach(bizProjectRegisterIdParam -> checkRight(bizProjectRegisterIdParam.getId(),null));
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizProjectRegisterIdParamList, BizProjectRegisterIdParam::getId));
    }

    @Override
    public BizProjectRegister detail(BizProjectRegisterIdParam bizProjectRegisterIdParam) {
        return this.queryEntity(bizProjectRegisterIdParam.getId());
    }

    public void checkRight(String id,String createUser){
        if(StrUtil.isBlank(createUser)){
            BizProjectRegister bizProjectRegister= this.queryEntity(id);
            createUser = bizProjectRegister.getCreateUser();
        }
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        //仅可编辑自己的
        if(CollUtil.isEmpty(loginUserDataScope)){
            if(!StpLoginUserUtil.getLoginUser().getAccount().equals(createUser)){
                throw new CommonException("您没有权限编辑该条记录");
            }
        }else {
            if(!StpLoginUserUtil.checkRight()){
                throw new CommonException("您没有权限编辑该条记录");
            }
        }
    }

    @Override
    public BizProjectRegister queryEntity(String id) {
        BizProjectRegister bizProjectRegister = this.getById(id);
        if(ObjectUtil.isEmpty(bizProjectRegister)) {
            throw new CommonException("业务表-立项报备不存在，id值为：{}", id);
        }
        return bizProjectRegister;
    }

    @Override
    public void updateStatus(BizProjectRegisterCommonEditParam bizProjectRegisterCommonEditParam) {
        UpdateWrapper<BizProjectRegister> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(BizProjectRegister::getId,bizProjectRegisterCommonEditParam.getId());
        if(ObjectUtil.isNotEmpty(bizProjectRegisterCommonEditParam.getBusinessStatus())){
            updateWrapper.lambda().set(BizProjectRegister::getBusinessStatus,bizProjectRegisterCommonEditParam.getBusinessStatus());
        }
        if(ObjectUtil.isNotEmpty(bizProjectRegisterCommonEditParam.getStatus())){
            updateWrapper.lambda().set(BizProjectRegister::getStatus,bizProjectRegisterCommonEditParam.getStatus());
        }
        this.update(updateWrapper);
    }

}
