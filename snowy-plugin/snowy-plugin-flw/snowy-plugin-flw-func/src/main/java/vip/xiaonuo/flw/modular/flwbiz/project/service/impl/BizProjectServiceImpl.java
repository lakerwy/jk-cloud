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
package vip.xiaonuo.flw.modular.flwbiz.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
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
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.flw.modular.flwbiz.project.entity.BizProject;
import vip.xiaonuo.flw.modular.flwbiz.project.mapper.BizProjectMapper;
import vip.xiaonuo.flw.modular.flwbiz.project.param.BizProjectAddParam;
import vip.xiaonuo.flw.modular.flwbiz.project.param.BizProjectEditParam;
import vip.xiaonuo.flw.modular.flwbiz.project.param.BizProjectIdParam;
import vip.xiaonuo.flw.modular.flwbiz.project.param.BizProjectPageParam;
import vip.xiaonuo.flw.modular.flwbiz.project.service.BizProjectService;
import vip.xiaonuo.flw.modular.relationtable.entity.FormRelationTable;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务表-立项申请Service接口实现类
 *
 * @author mayike
 * @date  2023/06/20 14:26
 **/
@DS("slave_1")
@Service
public class BizProjectServiceImpl extends ServiceImpl<BizProjectMapper, BizProject> implements BizProjectService {

    @Resource
    FormRelationTableService formRelationTableService;

    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Override
    public Page<BizProject> page(BizProjectPageParam bizProjectPageParam) {
        QueryWrapper<BizProject> queryWrapper = new QueryWrapper<>();

        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getStartApplyTime()) && ObjectUtil.isNotEmpty(bizProjectPageParam.getEndApplyTime())) {
            queryWrapper.lambda().between(BizProject::getApplyTime, bizProjectPageParam.getStartApplyTime(), bizProjectPageParam.getEndApplyTime());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getSn())) {
            queryWrapper.lambda().like(BizProject::getSn, bizProjectPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getName())) {
            queryWrapper.lambda().like(BizProject::getName, bizProjectPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getSource())) {
            queryWrapper.lambda().like(BizProject::getSource, bizProjectPageParam.getSource());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getSearchKey())){
            queryWrapper.lambda().or(wrapper ->wrapper.like(BizProject::getName,bizProjectPageParam.getSearchKey()).or().like(BizProject::getProjectSn,bizProjectPageParam.getSearchKey()));
        }
        //queryWrapper.lambda().eq(BizProject::getStatus,"1");
        queryWrapper.lambda().in(BizProject::getStatus,FlwStatusEnum.ACTIVE_STATUS.getValue(),FlwStatusEnum.COMPLETED_STATUS.getValue());
        if(ObjectUtil.isAllNotEmpty(bizProjectPageParam.getSortField(), bizProjectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizProjectPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizProjectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizProjectPageParam.getSortField()));
        } else {
            //queryWrapper.lambda().orderByAsc(BizProject::getId);
            queryWrapper.lambda().orderByDesc(BizProject::getApplyTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    //@Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizProjectAddParam bizProjectAddParam) {
        BizProject bizProject = BeanUtil.toBean(bizProjectAddParam, BizProject.class);
        bizProject.setLocation(JSONUtil.toJsonStr(bizProjectAddParam.getLocationCheckbox()));
        String processInstanceId = bizProjectAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizProject.setId(id);
            bizProject.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizProject.setModelId(modelId);
            bizProject.setApplyTime(DateUtil.now());
            bizProject.setApplyUser(StpLoginUserUtil.getLoginUser().getName());
            //将流程实例和业务表关联起来
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            formRelationTableService.save(modelId, BizCommonEnum.PROJECT.getBizTable(),id,processInstanceId,dbName);
        }
        bizProject.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
        this.save(bizProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizProjectEditParam bizProjectEditParam) {
        String id = bizProjectEditParam.getId();
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizProjectEditParam.getProcessInstanceId())){
            FormRelationTable formRelationTable = formRelationTableService.getInfoByProcessInstanceId(bizProjectEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id=formRelationTable.getId();
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizProjectEditParam.setId(id);
            }
        }
        BizProject bizProject = this.queryEntity(id);
        BeanUtil.copyProperties(bizProjectEditParam, bizProject);
        bizProject.setLocation(JSONUtil.toJsonStr(bizProjectEditParam.getLocationCheckbox()));
        this.updateById(bizProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizProjectIdParam> bizProjectIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizProjectIdParamList, BizProjectIdParam::getId));
    }

    @Override
    public BizProject detail(BizProjectIdParam bizProjectIdParam) {
        return this.queryEntity(bizProjectIdParam.getId());
    }

    @Override
    public BizProject queryEntity(String id) {
        BizProject bizProject = this.getById(id);
        if(ObjectUtil.isEmpty(bizProject)) {
            throw new CommonException("业务表-立项申请不存在，id值为：{}", id);
        }
        return bizProject;
    }
}
