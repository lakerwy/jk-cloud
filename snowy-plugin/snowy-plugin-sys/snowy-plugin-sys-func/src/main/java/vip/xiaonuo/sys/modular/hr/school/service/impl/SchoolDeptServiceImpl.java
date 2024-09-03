package vip.xiaonuo.sys.modular.hr.school.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
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
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.sys.modular.hr.school.entity.SchoolDept;
import vip.xiaonuo.sys.modular.hr.school.mapper.SchoolDeptMapper;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolDeptAddParam;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolDeptEditParam;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolDeptIdParam;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolDeptPageParam;
import vip.xiaonuo.sys.modular.hr.school.service.SchoolDeptService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 校招 - （应聘者）- 推送部门流程Service接口实现类
 *
 * @author yong.ye
 * @date  2024/09/03 10:47
 **/
@Service
public class SchoolDeptServiceImpl extends ServiceImpl<SchoolDeptMapper, SchoolDept> implements SchoolDeptService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<SchoolDept> page(SchoolDeptPageParam schoolDeptPageParam) {
        QueryWrapper<SchoolDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(SchoolDept::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(schoolDeptPageParam.getDeptId())) {
            queryWrapper.lambda().eq(SchoolDept::getDeptId, schoolDeptPageParam.getDeptId());
        }
        if(ObjectUtil.isNotEmpty(schoolDeptPageParam.getDept())) {
            queryWrapper.lambda().like(SchoolDept::getDept, schoolDeptPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(schoolDeptPageParam.getAuditId())) {
            queryWrapper.lambda().eq(SchoolDept::getAuditId, schoolDeptPageParam.getAuditId());
        }
        if(ObjectUtil.isNotEmpty(schoolDeptPageParam.getAudit())) {
            queryWrapper.lambda().like(SchoolDept::getAudit, schoolDeptPageParam.getAudit());
        }
        if(ObjectUtil.isNotEmpty(schoolDeptPageParam.getSn())) {
            queryWrapper.lambda().like(SchoolDept::getSn, schoolDeptPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(schoolDeptPageParam.getStatus())) {
            queryWrapper.lambda().eq(SchoolDept::getStatus, schoolDeptPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(schoolDeptPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.apply(" JSON_CONTAINS(RESUME_JSON, JSON_OBJECT('name',{0}))>0 ",schoolDeptPageParam.getSearchKey())
                    .or().like(SchoolDept::getSn,schoolDeptPageParam.getSearchKey())
                    .or().like(SchoolDept::getDept,schoolDeptPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(schoolDeptPageParam.getSortField(), schoolDeptPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(schoolDeptPageParam.getSortOrder());
            queryWrapper.orderBy(true, schoolDeptPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(schoolDeptPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(SchoolDept::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SchoolDeptAddParam schoolDeptAddParam) {
        SchoolDept schoolDept = BeanUtil.toBean(schoolDeptAddParam, SchoolDept.class);
        //走审批流程
        String processInstanceId = schoolDeptAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            schoolDept.setId(id);
            schoolDept.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            schoolDept.setModelId(modelId);
            schoolDept.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.HR_SCHOOL_DEPT.getBizTable(),id,processInstanceId,dbName);
        }else{
            schoolDept.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(schoolDept);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SchoolDeptEditParam schoolDeptEditParam) {
        String id = schoolDeptEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(schoolDeptEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(schoolDeptEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                schoolDeptEditParam.setId(id);
            }
        }
        SchoolDept schoolDept = this.queryEntity(schoolDeptEditParam.getId());
        BeanUtil.copyProperties(schoolDeptEditParam, schoolDept);
        this.updateById(schoolDept);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SchoolDeptIdParam> schoolDeptIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(schoolDeptIdParamList, SchoolDeptIdParam::getId));
    }

    @Override
    public SchoolDept detail(SchoolDeptIdParam schoolDeptIdParam) {
        return this.queryEntity(schoolDeptIdParam.getId());
    }

    @Override
    public SchoolDept queryEntity(String id) {
        SchoolDept schoolDept = this.getById(id);
        if(ObjectUtil.isEmpty(schoolDept)) {
            throw new CommonException("校招 - （应聘者）- 推送部门流程不存在，id值为：{}", id);
        }
        return schoolDept;
    }
}
