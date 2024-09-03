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
import vip.xiaonuo.sys.modular.hr.school.entity.SchoolInterview;
import vip.xiaonuo.sys.modular.hr.school.mapper.SchoolInterviewMapper;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolInterviewAddParam;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolInterviewEditParam;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolInterviewIdParam;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolInterviewPageParam;
import vip.xiaonuo.sys.modular.hr.school.service.SchoolInterviewService;
import vip.xiaonuo.sys.modular.hr.school.vo.SchoolInterviewVo;

import javax.annotation.Resource;
import java.util.List;

/**
 * 校招 - （应聘者）- 面试入职流程Service接口实现类
 *
 * @author yong.ye
 * @date  2024/09/03 10:46
 **/
@Service
public class SchoolInterviewServiceImpl extends ServiceImpl<SchoolInterviewMapper, SchoolInterview> implements SchoolInterviewService {

    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private SchoolInterviewMapper schoolInterviewMapper;
    @Override
    public Page<SchoolInterviewVo> page(SchoolInterviewPageParam schoolInterviewPageParam) {
        QueryWrapper<SchoolInterview> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.in("a.STATUS",FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(schoolInterviewPageParam.getDeptId())) {
            queryWrapper.eq("a.DEPT_ID", schoolInterviewPageParam.getDeptId());
        }
        if(ObjectUtil.isNotEmpty(schoolInterviewPageParam.getDept())) {
            queryWrapper.like("a.DEPT", schoolInterviewPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(schoolInterviewPageParam.getResumeId())) {
            queryWrapper.eq("a.RESUME_ID", schoolInterviewPageParam.getResumeId());
        }
        if(ObjectUtil.isNotEmpty(schoolInterviewPageParam.getSn())) {
            queryWrapper.like("a.SN", schoolInterviewPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(schoolInterviewPageParam.getStatus())) {
            queryWrapper.eq("a.STATUS", schoolInterviewPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(schoolInterviewPageParam.getYear())) {
            queryWrapper.eq("b.YEAR", schoolInterviewPageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(schoolInterviewPageParam.getType())) {
            queryWrapper.eq("b.TYPE", schoolInterviewPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(schoolInterviewPageParam.getSchool())) {
            queryWrapper.like("b.SCHOOL", schoolInterviewPageParam.getSchool());
        }
        if(ObjectUtil.isNotEmpty(schoolInterviewPageParam.getPostType())) {
            queryWrapper.eq("b.POST_TYPE", schoolInterviewPageParam.getPostType());
        }
        if(ObjectUtil.isNotEmpty(schoolInterviewPageParam.getEducation())) {
            queryWrapper.eq("b.EDUCATION", schoolInterviewPageParam.getEducation());
        }
        if(ObjectUtil.isNotEmpty(schoolInterviewPageParam.getInfoResource())) {
            queryWrapper.eq("b.INFO_RESOURCE", schoolInterviewPageParam.getInfoResource());
        }
        if(ObjectUtil.isNotEmpty(schoolInterviewPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.SN",schoolInterviewPageParam.getSearchKey())
                    .or().like("b.NAME",schoolInterviewPageParam.getSearchKey())
                    .or().like("b.SCHOOL",schoolInterviewPageParam.getSearchKey())
                    .or().like("b.PHONE",schoolInterviewPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(schoolInterviewPageParam.getSortField(), schoolInterviewPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(schoolInterviewPageParam.getSortOrder());
            queryWrapper.orderBy(true, schoolInterviewPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(schoolInterviewPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(SchoolInterview::getSn);
        }
        return schoolInterviewMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SchoolInterviewAddParam schoolInterviewAddParam) {
        SchoolInterview schoolInterview = BeanUtil.toBean(schoolInterviewAddParam, SchoolInterview.class);
        //走审批流程
        String processInstanceId = schoolInterviewAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            schoolInterview.setId(id);
            schoolInterview.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            schoolInterview.setModelId(modelId);
            schoolInterview.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.HR_SCHOOL_INTERVIEW.getBizTable(),id,processInstanceId,dbName);
        }else{
            schoolInterview.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(schoolInterview);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SchoolInterviewEditParam schoolInterviewEditParam) {
        String id = schoolInterviewEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(schoolInterviewEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(schoolInterviewEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                schoolInterviewEditParam.setId(id);
            }
        }
        SchoolInterview schoolInterview = this.queryEntity(schoolInterviewEditParam.getId());
        BeanUtil.copyProperties(schoolInterviewEditParam, schoolInterview);
        this.updateById(schoolInterview);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SchoolInterviewIdParam> schoolInterviewIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(schoolInterviewIdParamList, SchoolInterviewIdParam::getId));
    }

    @Override
    public SchoolInterviewVo detail(SchoolInterviewIdParam schoolInterviewIdParam) {
        return schoolInterviewMapper.getDetail(schoolInterviewIdParam.getId());
    }

    @Override
    public SchoolInterview queryEntity(String id) {
        SchoolInterview schoolInterview = this.getById(id);
        if(ObjectUtil.isEmpty(schoolInterview)) {
            throw new CommonException("校招 - （应聘者）- 面试入职流程不存在，id值为：{}", id);
        }
        return schoolInterview;
    }
}
