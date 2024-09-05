package vip.xiaonuo.sys.modular.hr.school.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
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
import vip.xiaonuo.sys.modular.hr.school.vo.SchoolDeptInfo;
import vip.xiaonuo.sys.modular.hr.school.vo.SchoolInterviewInfo;
import vip.xiaonuo.sys.modular.hr.school.vo.SchoolResumeVo;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        if(ObjectUtil.isAllNotEmpty(schoolResumePageParam.getSortField(), schoolResumePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(schoolResumePageParam.getSortOrder());
            queryWrapper.orderBy(true, schoolResumePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(schoolResumePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SchoolResume::getId);
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
        SchoolResume schoolResume = BeanUtil.toBean(schoolResumeAddParam, SchoolResume.class);
        schoolResume.setYear(SchoolUtils.getCurrentYear());
        schoolResume.setType(SchoolUtils.getTypeEnum());
        SchoolResume newSchoolResume = saveOrUpdateResume(schoolResume);
        this.saveOrUpdate(newSchoolResume);
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
            BeanUtil.copyProperties(resumeParam,schoolResume);
            schoolResume.setYear(SchoolUtils.getCurrentYear());
            schoolResume.setType(SchoolUtils.getTypeEnum());
            schoolResume = saveOrUpdateResume(schoolResume);
        }
        this.saveOrUpdate(schoolResume);
    }

    @Override
    public void batchAdd(List<SchoolResumeAddParam> schoolResumeAddParamList) {
        List<SchoolResume> addList = BeanUtil.copyToList(schoolResumeAddParamList,SchoolResume.class);
        String year = SchoolUtils.getCurrentYear();
        String type = SchoolUtils.getTypeEnum();
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
        List<SchoolResume> addList = BeanUtil.copyToList(schoolResumeAddParamList,SchoolResume.class);
        String year = SchoolUtils.getCurrentYear();
        String type = SchoolUtils.getTypeEnum();
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
    public void upload(ResumeUploadParam resumeUploadParam) {
        SchoolResume schoolResume = this.queryEntity(resumeUploadParam.getId());
        schoolResume.setResumeAttach(resumeUploadParam.getResumeAttach());
        this.updateById(schoolResume);
    }

    /**
     * 获取 面试信息|部门推荐信息
     * @param schoolResume
     * @return
     */
    public SchoolResumeVo getSchoolResumeVo(SchoolResume schoolResume){
        SchoolResumeVo schoolResumeVo = BeanUtil.copyProperties(schoolResume,SchoolResumeVo.class);
        LambdaQueryWrapper<SchoolInterview> interviewQueryWrapper = new QueryWrapper<SchoolInterview>().lambda()
                .eq(SchoolInterview::getResumeId,schoolResume.getId())
                .orderByDesc(SchoolInterview::getSn);
        schoolResumeVo.setInterviewList(BeanUtil.copyToList(schoolInterviewService.list(interviewQueryWrapper), SchoolInterviewInfo.class));

        LambdaQueryWrapper<SchoolDept> deptQueryWrapper = new QueryWrapper<SchoolDept>().lambda()
                .apply(" JSON_VALID(RESUME_JSON) AND JSON_CONTAINS(RESUME_JSON, JSON_OBJECT('resumeId',{0})) ",schoolResume.getId())
                .orderByDesc(SchoolDept::getSn);
        schoolResumeVo.setDeptList(BeanUtil.copyToList(schoolDeptService.list(deptQueryWrapper), SchoolDeptInfo.class));
        return schoolResumeVo;
    }


    /**
     * 同一年同一期，同名+同手机号+岗位 历史简历（覆盖），否则新增
     * @param source 源数据
     * @return
     */
    public SchoolResume saveOrUpdateResume(SchoolResume source){
        //同一年同一期，同名+同手机号+岗位 是否存在，存在则覆盖
        SchoolResume target = this.getOne(new QueryWrapper<SchoolResume>().lambda()
                .eq(SchoolResume::getYear,source.getYear())
                .eq(SchoolResume::getType,source.getType())
                .eq(SchoolResume::getName,source.getName())
                .eq(SchoolResume::getPhone,source.getPhone())
                .eq(SchoolResume::getApplyPost,source.getApplyPost())
                .last(" limit 1 "));
        if(ObjectUtil.isEmpty(target)){
            target = new SchoolResume();
        }
        BeanUtil.copyProperties(source,target);
        return target;
    }
}
