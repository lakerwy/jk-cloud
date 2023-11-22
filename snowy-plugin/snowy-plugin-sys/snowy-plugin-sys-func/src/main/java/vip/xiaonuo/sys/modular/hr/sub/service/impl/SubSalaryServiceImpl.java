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
package vip.xiaonuo.sys.modular.hr.sub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.common.utils.JsonUtils;
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
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FormRelationTableApi;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubSalary;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubSalaryMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryPageParam;
import vip.xiaonuo.sys.modular.hr.sub.service.SubSalaryService;

import javax.annotation.Resource;
import java.util.List;

/**
 * HR-分公司-工资条Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/22 11:20
 **/
@Service
public class SubSalaryServiceImpl extends ServiceImpl<SubSalaryMapper, SubSalary> implements SubSalaryService {

    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Override
    public Page<SubSalary> page(SubSalaryPageParam subSalaryPageParam) {
        QueryWrapper<SubSalary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(SubSalary::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(subSalaryPageParam.getOrgId())){
            queryWrapper.lambda().eq(SubSalary::getOrgId,subSalaryPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(subSalaryPageParam.getOrgName())) {
            queryWrapper.lambda().like(SubSalary::getOrgName, subSalaryPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(subSalaryPageParam.getYear())) {
            queryWrapper.lambda().eq(SubSalary::getYear, subSalaryPageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(subSalaryPageParam.getMonth())) {
            queryWrapper.lambda().eq(SubSalary::getMonth, subSalaryPageParam.getMonth());
        }
        if(ObjectUtil.isNotEmpty(subSalaryPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(SubSalary::getOrgName,subSalaryPageParam.getSearchKey()));
        }
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(SubSalary::getDataScopeId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(SubSalary::getCreateUser, StpUtil.getLoginIdAsString());
        }
        if(ObjectUtil.isAllNotEmpty(subSalaryPageParam.getSortField(), subSalaryPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subSalaryPageParam.getSortOrder());
            queryWrapper.orderBy(true, subSalaryPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subSalaryPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SubSalary::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubSalaryAddParam subSalaryAddParam) {
        SubSalary subSalary = BeanUtil.toBean(subSalaryAddParam, SubSalary.class);
        //走审批流程
        String processInstanceId = subSalaryAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            subSalary.setId(id);
            subSalary.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            subSalary.setModelId(modelId);
            subSalary.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            formRelationTableApi.save(modelId, BizCommonEnum.HR_SUB_SALARY.getBizTable(),id,processInstanceId,dbName);
        }else{
            subSalary.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        subSalary.setExtJson(JsonUtils.object2json(subSalaryAddParam));
        this.save(subSalary);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubSalaryEditParam subSalaryEditParam) {
        String id = subSalaryEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(subSalaryEditParam.getProcessInstanceId())){
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            JSONObject formRelationTable = formRelationTableApi.getInfoByProcessInstanceId(subSalaryEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                subSalaryEditParam.setId(id);
            }
        }
        SubSalary subSalary = this.queryEntity(subSalaryEditParam.getId());
        BeanUtil.copyProperties(subSalaryEditParam, subSalary);
        this.updateById(subSalary);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubSalaryIdParam> subSalaryIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(subSalaryIdParamList, SubSalaryIdParam::getId));
    }

    @Override
    public SubSalary detail(SubSalaryIdParam subSalaryIdParam) {
        return this.queryEntity(subSalaryIdParam.getId());
    }

    @Override
    public SubSalary queryEntity(String id) {
        SubSalary subSalary = this.getById(id);
        if(ObjectUtil.isEmpty(subSalary)) {
            throw new CommonException("HR-分公司-工资条不存在，id值为：{}", id);
        }
        return subSalary;
    }
}
