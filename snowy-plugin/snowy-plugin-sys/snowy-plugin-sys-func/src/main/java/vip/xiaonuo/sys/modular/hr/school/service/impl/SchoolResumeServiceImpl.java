package vip.xiaonuo.sys.modular.hr.school.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.school.entity.SchoolDept;
import vip.xiaonuo.sys.modular.hr.school.entity.SchoolInterview;
import vip.xiaonuo.sys.modular.hr.school.entity.SchoolResume;
import vip.xiaonuo.sys.modular.hr.school.mapper.SchoolResumeMapper;
import vip.xiaonuo.sys.modular.hr.school.param.*;
import vip.xiaonuo.sys.modular.hr.school.service.SchoolDeptService;
import vip.xiaonuo.sys.modular.hr.school.service.SchoolInterviewService;
import vip.xiaonuo.sys.modular.hr.school.service.SchoolResumeService;
import vip.xiaonuo.sys.modular.hr.school.util.SchoolUtils;
import vip.xiaonuo.sys.modular.hr.school.vo.DeptInfo;
import vip.xiaonuo.sys.modular.hr.school.vo.SchoolDeptInfo;
import vip.xiaonuo.sys.modular.hr.school.vo.SchoolInterviewInfo;
import vip.xiaonuo.sys.modular.hr.school.vo.SchoolResumeVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 校招 - （应聘者）简历库信息Service接口实现类
 *
 * @author yong.ye
 * @date  2024/09/03 10:44
 **/
@Service
public class SchoolResumeServiceImpl extends ServiceImpl<SchoolResumeMapper, SchoolResume> implements SchoolResumeService {

    @Resource
    private SchoolInterviewService schoolInterviewService;
    @Resource
    private SchoolDeptService schoolDeptService;
    @Resource
    private SchoolResumeMapper schoolResumeMapper;

    @Override
    public Page<SchoolResumeVo> page(SchoolResumePageParam schoolResumePageParam) {
        QueryWrapper<SchoolResume> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getYear())) {
            queryWrapper.lambda().eq(SchoolResume::getYear, schoolResumePageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getType())) {
            queryWrapper.lambda().eq(SchoolResume::getType, schoolResumePageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getName())) {
            queryWrapper.lambda().like(SchoolResume::getName, schoolResumePageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getPhone())) {
            queryWrapper.lambda().like(SchoolResume::getPhone, schoolResumePageParam.getPhone());
        }
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getSchool())) {
            queryWrapper.lambda().like(SchoolResume::getSchool, schoolResumePageParam.getSchool());
        }
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getEducation())) {
            queryWrapper.lambda().eq(SchoolResume::getEducation, schoolResumePageParam.getEducation());
        }
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getMajor())) {
            queryWrapper.lambda().like(SchoolResume::getMajor, schoolResumePageParam.getMajor());
        }
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getInfoResource())) {
            queryWrapper.lambda().eq(SchoolResume::getInfoResource, schoolResumePageParam.getInfoResource());
        }
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getPostType())) {
            queryWrapper.lambda().eq(SchoolResume::getPostType, schoolResumePageParam.getPostType());
        }
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getIdNumber())) {
            queryWrapper.lambda().like(SchoolResume::getIdNumber, schoolResumePageParam.getIdNumber());
        }
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getSex())) {
            queryWrapper.lambda().eq(SchoolResume::getSex, schoolResumePageParam.getSex());
        }
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getResumeStage())) {
            queryWrapper.lambda().eq(SchoolResume::getResumeStage, schoolResumePageParam.getResumeStage());
        }
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getIsSend())) {
            queryWrapper.lambda().eq(SchoolResume::getIsSend, schoolResumePageParam.getIsSend());
        }
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getResumeStatus())){
            queryWrapper.lambda().eq(SchoolResume::getResumeStatus,schoolResumePageParam.getResumeStatus());
        }
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getStartDate()) && ObjectUtil.isNotEmpty(schoolResumePageParam.getEndDate())) {
            queryWrapper.lambda().between(SchoolResume::getCreateTime, schoolResumePageParam.getStartDate(), schoolResumePageParam.getEndDate());
        }
        if(ObjectUtil.isNotEmpty(schoolResumePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(SchoolResume::getName,schoolResumePageParam.getSearchKey())
                    .or().like(SchoolResume::getSchool,schoolResumePageParam.getSearchKey())
                    .or().like(SchoolResume::getMajor,schoolResumePageParam.getSearchKey())
                    .or().like(SchoolResume::getMemo,schoolResumePageParam.getSearchKey())
                    .or().like(SchoolResume::getPhone,schoolResumePageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(schoolResumePageParam.getSortField(), schoolResumePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(schoolResumePageParam.getSortOrder());
            queryWrapper.orderBy(true, schoolResumePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(schoolResumePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(SchoolResume::getCreateTime);
        }
        Page<SchoolResume> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<SchoolResumeVo> records = new ArrayList<>();
        if(CollUtil.isNotEmpty(result.getRecords())){
            result.getRecords().forEach(item->records.add(getSchoolResumeVo(item)));
        }
        Page<SchoolResumeVo> newResult = new Page<>();
        newResult.setRecords(records);
        newResult.setPages(result.getPages());
        newResult.setSize(result.getSize());
        newResult.setTotal(result.getTotal());
        newResult.setCurrent(result.getCurrent());
        return newResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SchoolResumeAddParam schoolResumeAddParam) {
        //判断是否存在历史简历
        schoolResumeAddParam.setYear(SchoolUtils.getCurrentYear());
        schoolResumeAddParam.setType(SchoolUtils.getTypeEnum());
        SchoolResume tmp = BeanUtil.copyProperties(schoolResumeAddParam,SchoolResume.class);
        SchoolResume saveOrUpdate = saveOrUpdateResume(tmp);
        this.saveOrUpdate(saveOrUpdate);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SchoolResumeEditParam schoolResumeEditParam) {
        SchoolResume schoolResume = this.queryEntity(schoolResumeEditParam.getId());
        BeanUtil.copyProperties(schoolResumeEditParam, schoolResume);
        this.updateById(schoolResume);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SchoolResumeIdParam> schoolResumeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(schoolResumeIdParamList, SchoolResumeIdParam::getId));
    }

    @Override
    public SchoolResumeVo detail(SchoolResumeIdParam schoolResumeIdParam) {
        SchoolResume schoolResume = this.queryEntity(schoolResumeIdParam.getId());
        return getSchoolResumeVo(schoolResume);
    }

    @Override
    public SchoolResume queryEntity(String id) {
        SchoolResume schoolResume = this.getById(id);
        if(ObjectUtil.isEmpty(schoolResume)) {
            throw new CommonException("校招 - （应聘者）简历库信息不存在，id值为：{}", id);
        }
        return schoolResume;
    }

    @Override
    public void submit(ResumeParam resumeParam) {
        SchoolResume schoolResume = new SchoolResume();
        if(ObjectUtil.isNotEmpty(resumeParam.getId())){
            schoolResume = this.queryEntity(resumeParam.getId());
            BeanUtil.copyProperties(resumeParam,schoolResume);
        }else{
            resumeParam.setYear(SchoolUtils.getCurrentYear());
            resumeParam.setType(SchoolUtils.getTypeEnum());
            BeanUtil.copyProperties(resumeParam,schoolResume);
            schoolResume = saveOrUpdateResume(schoolResume);
        }
        this.saveOrUpdate(schoolResume);
    }

    @Override
    public void batchAdd(List<SchoolResumeAddParam> schoolResumeAddParamList) {
        String year = SchoolUtils.getCurrentYear();
        String type = SchoolUtils.getTypeEnum();
        List<SchoolResume> addList = BeanUtil.copyToList(schoolResumeAddParamList,SchoolResume.class);
        List<SchoolResume> saveOrUpdateList = new ArrayList<>();
        addList.forEach(item->{
            item.setYear(year);
            item.setType(type);
            SchoolResume tmp = saveOrUpdateResume(item);
            saveOrUpdateList.add(tmp);
        });
        this.saveOrUpdateBatch(saveOrUpdateList);
    }

    @Override
    public void batchImport(List<SchoolResumeAddParam> schoolResumeAddParamList) {
        batchAdd(schoolResumeAddParamList);
    }

    @Override
    public void upload(ResumeUploadParam resumeUploadParam) {
        SchoolResume schoolResume = this.queryEntity(resumeUploadParam.getId());
        schoolResume.setResumeAttach(resumeUploadParam.getResumeAttach());
        this.updateById(schoolResume);
    }

    @Override
    public void status(ResumeStatusParam resumeStatusParam) {
        if(CollUtil.isNotEmpty(resumeStatusParam.getIds())){
            List<SchoolResume> schoolResumes = this.list(new QueryWrapper<SchoolResume>().lambda().in(SchoolResume::getId,resumeStatusParam.getIds()));
            schoolResumes.forEach(item-> {
                item.setResumeStatus(resumeStatusParam.getResumeStatus());
                item.setAuditTime(new Date());
            });
            this.updateBatchById(schoolResumes);
        }
    }

    /**
     * 获取 面试信息|部门推荐信息
     * @param schoolResume
     * @return
     */
    public SchoolResumeVo getSchoolResumeVo(SchoolResume schoolResume){
        SchoolResumeVo schoolResumeVo = BeanUtil.copyProperties(schoolResume,SchoolResumeVo.class);
        List<SchoolInterview> interviewList = getInterviewList(schoolResume.getId());
        if(CollUtil.isNotEmpty(interviewList)) {
            schoolResumeVo.setInterviewList(BeanUtil.copyToList(interviewList, SchoolInterviewInfo.class));
        }
        List<SchoolDept> deptList = getDeptList(schoolResume.getId());
        if(CollUtil.isNotEmpty(deptList)){
            List<SchoolDeptInfo> deptInfoList = new ArrayList<>();
            deptList.forEach(item->{
                SchoolDeptInfo tmp = BeanUtil.copyProperties(item,SchoolDeptInfo.class);
                try {
                    DeptInfo deptInfo = JSONUtil.toList(item.getResumeJson(), DeptInfo.class).stream().filter(x -> x.getResumeId().equals(schoolResume.getId())).findFirst().orElse(null);
                    if (ObjectUtil.isNotEmpty(deptInfo)) {
                        tmp.setIsPass(deptInfo.getIsPass());
                        deptInfoList.add(tmp);
                    }
                }catch (Exception e){}
            });
            schoolResumeVo.setDeptList(deptInfoList);
        }
        return schoolResumeVo;
    }


    /**
     * 根据简历id，获取面试流程记录
     * @param resumeId
     * @return
     */
    public List<SchoolInterview> getInterviewList(String resumeId){
        LambdaQueryWrapper<SchoolInterview> interviewQueryWrapper = new QueryWrapper<SchoolInterview>().lambda()
                .eq(SchoolInterview::getResumeId,resumeId)
                .orderByDesc(SchoolInterview::getCreateTime);
        return schoolInterviewService.list(interviewQueryWrapper);
    }


    /**
     * 根据简历id，获取部门推送流程记录
     * @param resumeId
     * @return
     */
    public List<SchoolDept> getDeptList(String resumeId){
        LambdaQueryWrapper<SchoolDept> deptQueryWrapper = new QueryWrapper<SchoolDept>().lambda()
                .apply(" JSON_VALID(RESUME_JSON) AND JSON_CONTAINS(RESUME_JSON, JSON_OBJECT('resumeId',{0})) ",resumeId)
                .orderByDesc(SchoolDept::getCreateTime);
        return schoolDeptService.list(deptQueryWrapper);
    }

    /**
     * 同一年同一期，同名+同手机号+岗位类别 历史简历(若无推荐入职流程记录，覆盖，否则不覆盖)
     * @param source
     * @return
     */
    public SchoolResume saveOrUpdateResume(SchoolResume source){
        //同一年同一期，同名+同手机号+岗位类别
        SchoolResume resume = this.getOne(new QueryWrapper<SchoolResume>().lambda()
                .eq(SchoolResume::getYear,source.getYear()).eq(SchoolResume::getType,source.getType())
                .eq(SchoolResume::getName,source.getName()).eq(SchoolResume::getPhone,source.getPhone())
                .eq(SchoolResume::getPostType,source.getPostType())
                .orderByDesc(SchoolResume::getCreateTime)
                .last(" limit 1 "));
        List<SchoolInterview> interviewList = new ArrayList<>();
        List<SchoolDept> deptList = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(resume)) {
            interviewList = getInterviewList(resume.getId());
            deptList = getDeptList(resume.getId());
        }
        //存在历史简历且无流程记录，则覆盖，否则新增简历
        SchoolResume newResume = new SchoolResume();
        if(ObjectUtil.isNotEmpty(resume) && CollUtil.isEmpty(interviewList) && CollUtil.isEmpty(deptList)){
            BeanUtil.copyProperties(resume,newResume);
            BeanUtil.copyProperties(source,newResume,"id");
        }else{
            BeanUtil.copyProperties(source,newResume);
        }
        return newResume;
    }

    @Override
    public List<SchoolResume> getApprovedResumeList(String name) {
        return schoolResumeMapper.getApprovedResumeList(name);
    }

}
