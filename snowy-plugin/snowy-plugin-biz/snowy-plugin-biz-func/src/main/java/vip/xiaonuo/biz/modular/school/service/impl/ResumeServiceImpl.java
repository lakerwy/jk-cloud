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
package vip.xiaonuo.biz.modular.school.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.school.entity.ResumeRecord;
import vip.xiaonuo.biz.modular.school.enums.ResumeActionTypeEnum;
import vip.xiaonuo.biz.modular.school.enums.ResumeOprResultEnum;
import vip.xiaonuo.biz.modular.school.enums.ResumeStatusEnum;
import vip.xiaonuo.biz.modular.school.param.*;
import vip.xiaonuo.biz.modular.school.service.ResumeRecordService;
import vip.xiaonuo.biz.modular.school.vo.ResumeVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.school.entity.Resume;
import vip.xiaonuo.biz.modular.school.mapper.ResumeMapper;
import vip.xiaonuo.biz.modular.school.service.ResumeService;
import vip.xiaonuo.sys.api.SysOrgApi;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 校招-简历Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/15 08:54
 **/

@DS("slave_1")
@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {

    @Resource
    private ResumeRecordService resumeRecordService;

//    @Resource
//    SysOrgApi sysOrgApi;

    @Override
    public Page<Resume> page(ResumePageParam resumePageParam) {
        QueryWrapper<Resume> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(resumePageParam.getName())) {
            queryWrapper.lambda().like(Resume::getName, resumePageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getSex())) {
            queryWrapper.lambda().eq(Resume::getSex, resumePageParam.getSex());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getMobile())) {
            queryWrapper.lambda().like(Resume::getMobile, resumePageParam.getMobile());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getSchool())) {
            queryWrapper.lambda().like(Resume::getSchool, resumePageParam.getSchool());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getSubject())) {
            queryWrapper.lambda().like(Resume::getSubject, resumePageParam.getSubject());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getPositionType())) {
            queryWrapper.lambda().eq(Resume::getPositionType, resumePageParam.getPositionType());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getInfoSource())) {
            queryWrapper.lambda().eq(Resume::getInfoSource, resumePageParam.getInfoSource());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getStatus())) {
            queryWrapper.lambda().eq(Resume::getStatus, resumePageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getAcademic())) {
            queryWrapper.lambda().eq(Resume::getAcademic, resumePageParam.getAcademic());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getStartCreateTime()) && ObjectUtil.isNotEmpty(resumePageParam.getEndCreateTime())){
            queryWrapper.lambda().between(Resume::getCreateTime,resumePageParam.getStartCreateTime(),resumePageParam.getEndCreateTime());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getFilterStatusIn())){
            queryWrapper.lambda().in(Resume::getStatus,resumePageParam.getFilterStatusIn());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getFilterStatusNotIn())){
            queryWrapper.lambda().notIn(Resume::getStatus,resumePageParam.getFilterStatusNotIn());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(Resume::getName,resumePageParam.getSearchKey())
                    .or().like(Resume::getSex,resumePageParam.getSearchKey())
                    .or().like(Resume::getMobile,resumePageParam.getSearchKey())
                    .or().like(Resume::getSchool,resumePageParam.getSearchKey())
                    .or().like(Resume::getSubject,resumePageParam.getSearchKey())
                    .or().like(Resume::getPositionType,resumePageParam.getSearchKey())
                    .or().like(Resume::getInfoSource,resumePageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(resumePageParam.getSortField(), resumePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(resumePageParam.getSortOrder());
            queryWrapper.orderBy(true, resumePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(resumePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Resume::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ResumeAddParam resumeAddParam) {
        Resume resume = BeanUtil.toBean(resumeAddParam, Resume.class);
        this.save(resume);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ResumeEditParam resumeEditParam) {
        Resume resume = this.queryEntity(resumeEditParam.getId());
        BeanUtil.copyProperties(resumeEditParam, resume);
        this.updateById(resume);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ResumeIdParam> resumeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(resumeIdParamList, ResumeIdParam::getId));
    }

    @Override
    public ResumeVo detail(ResumeIdParam resumeIdParam) {
        Resume resume = this.queryEntity(resumeIdParam.getId());
        ResumeVo resumeVo = BeanUtil.copyProperties(resume, ResumeVo.class);
        resumeVo.setResumeRecords(resumeRecordService.list(new QueryWrapper<ResumeRecord>().lambda()
                .eq(ResumeRecord::getResumesId,resumeIdParam.getId()).orderByDesc(ResumeRecord::getCreateTime)));
        return resumeVo;
    }

    @Override
    public Resume queryEntity(String id) {
        Resume resume = this.getById(id);
        if(ObjectUtil.isEmpty(resume)) {
            throw new CommonException("校招-简历不存在，id值为：{}", id);
        }
        return resume;
    }

    @Override
    public void doResumeAction(ResumeRecordAddParam resumeRecordAddParam) {
        //根据简历Id,获取简历信息
        Resume resume = this.queryEntity(resumeRecordAddParam.getResumesId());
        //添加操作记录
        ResumeRecord resumeRecord = BeanUtil.copyProperties(resumeRecordAddParam, ResumeRecord.class);
        resumeRecordService.save(resumeRecord);
        //更新简历状态
        if(resumeRecordAddParam.getActionType().equals(ResumeActionTypeEnum.HRFilter.getValue())){
            if(resumeRecordAddParam.getResult().equals(ResumeOprResultEnum.NotPass.getValue())){
                resume.setStatus(ResumeStatusEnum.NotPass.getValue());
            }else if(resumeRecordAddParam.getResult().equals(ResumeOprResultEnum.ReSend.getValue())){
                resume.setStatus(ResumeStatusEnum.ReSend.getValue());
                resume.setBelongsDepts(resumeRecordAddParam.getDeptId());
            }
        } else if (resumeRecordAddParam.getActionType().equals(ResumeActionTypeEnum.DeptFilter.getValue())) {
            if(resumeRecordAddParam.getResult().equals(ResumeOprResultEnum.NotPass.getValue())){
                resume.setStatus(ResumeStatusEnum.NotPass.getValue());
            } else if (resumeRecordAddParam.getResult().equals(ResumeOprResultEnum.Pass.getValue())) {
                resume.setStatus(ResumeStatusEnum.PreInterview.getValue());
            }
        } else if (resumeRecordAddParam.getActionType().equals(ResumeActionTypeEnum.InterviewNotice.getValue())) {
            resume.setStatus(ResumeStatusEnum.Notified.getValue());
        } else if (resumeRecordAddParam.getActionType().equals(ResumeActionTypeEnum.InterviewRigster.getValue())) {
            if(resumeRecordAddParam.getResult().equals(ResumeOprResultEnum.NotPass.getValue())){
                resume.setStatus(ResumeStatusEnum.Break.getValue());
            } else if (resumeRecordAddParam.getResult().equals(ResumeOprResultEnum.Pass.getValue())) {
                resume.setStatus(ResumeStatusEnum.PreOffer.getValue());
            } else if (resumeRecordAddParam.getResult().equals(ResumeOprResultEnum.NotFace.getValue())) {
                resume.setStatus(ResumeStatusEnum.NotFace.getValue());
            }
        } else if (resumeRecordAddParam.getActionType().equals(ResumeActionTypeEnum.Offer.getValue())) {
            if(resumeRecordAddParam.getResult().equals(ResumeOprResultEnum.Arrived.getValue())){
                resume.setStatus(ResumeStatusEnum.Arrived.getValue());
            } else if (resumeRecordAddParam.getResult().equals(ResumeOprResultEnum.Refuse.getValue())) {
                resume.setStatus(ResumeStatusEnum.Refuse.getValue());
            }
        }
        this.updateById(resume);
    }

    @Override
    public void doResumeActionBatch(BatchResumeRecord batchResumeRecord) {
        for (ResumeRecordAddParam record:batchResumeRecord.getResumeRecordList()) {
            doResumeAction(record);
        }
    }

    @Override
    public void saveResume(ResumeAddParam resumeAddParam) {
        //判断是否投递过,且未进入面试流程
        Resume oldResume = this.getOne(new QueryWrapper<Resume>().lambda()
                .eq(Resume::getName,resumeAddParam.getName())
                .eq(Resume::getMobile,resumeAddParam.getMobile())
                .in(Resume::getStatus,ResumeStatusEnum.Send.getValue())
                .orderByDesc(Resume::getCreateTime)
                .last("limit 1"));
        //更新简历
        if(ObjectUtil.isNotEmpty(oldResume)){
            BeanUtil.copyProperties(resumeAddParam,oldResume);
            oldResume.setPostTimes(oldResume.getPostTimes()+1);
            oldResume.setIsOld(0);
            this.updateById(oldResume);
        }else {
            Resume resume = BeanUtil.copyProperties(resumeAddParam,Resume.class);
            resume.setPostTimes(1);
            resume.setStatus(ResumeStatusEnum.Send.getValue());
            resume.setIsOld(0);
            this.save(resume);
        }
    }

    @Override
    public void assignDept(AssignDeptsDto assignDept) {
        List<Resume> updateList = new ArrayList<>();
        List<ResumeRecord> addList = new ArrayList<>();
        for (String id :assignDept.getIds()) {
            //获取简历
            Resume oldResume = this.getById(id);
            //增加记录
            ResumeRecord resumeRecord = new ResumeRecord();
            resumeRecord.setResumesId(id);
            resumeRecord.setActionType(assignDept.getDeptRecord().getActionType());
            resumeRecord.setOperation(assignDept.getDeptRecord().getOperation());
            resumeRecord.setOprTime(new Date());
            resumeRecord.setOprName(assignDept.getDeptRecord().getOprName());
            resumeRecord.setMemo(assignDept.getDeptRecord().getMemo());
            addList.add(resumeRecord);
            //更新简历信息
            oldResume.setStatus(ResumeStatusEnum.ReSend.getValue());
            oldResume.setBelongsDepts(assignDept.getDeptRecord().getDeptId());
            updateList.add(oldResume);
        }
        if(CollUtil.isNotEmpty(updateList)){
            this.updateBatchById(updateList);
        }
        if(CollUtil.isNotEmpty(addList)){
            resumeRecordService.saveBatch(addList);
        }
    }

    @Override
    public Page<Resume> pageByUserId(ResumePageParam resumePageParam) {
        QueryWrapper<Resume> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(resumePageParam.getName())) {
            queryWrapper.lambda().like(Resume::getName, resumePageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getSex())) {
            queryWrapper.lambda().eq(Resume::getSex, resumePageParam.getSex());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getMobile())) {
            queryWrapper.lambda().like(Resume::getMobile, resumePageParam.getMobile());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getSchool())) {
            queryWrapper.lambda().like(Resume::getSchool, resumePageParam.getSchool());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getSubject())) {
            queryWrapper.lambda().like(Resume::getSubject, resumePageParam.getSubject());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getPositionType())) {
            queryWrapper.lambda().eq(Resume::getPositionType, resumePageParam.getPositionType());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getInfoSource())) {
            queryWrapper.lambda().eq(Resume::getInfoSource, resumePageParam.getInfoSource());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getStatus())) {
            queryWrapper.lambda().eq(Resume::getStatus, resumePageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getAcademic())) {
            queryWrapper.lambda().eq(Resume::getAcademic, resumePageParam.getAcademic());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getStartCreateTime()) && ObjectUtil.isNotEmpty(resumePageParam.getEndCreateTime())){
            queryWrapper.lambda().between(Resume::getCreateTime,resumePageParam.getStartCreateTime(),resumePageParam.getEndCreateTime());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getFilterStatusIn())){
            queryWrapper.lambda().in(Resume::getStatus,resumePageParam.getFilterStatusIn());
        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getFilterStatusNotIn())){
            queryWrapper.lambda().notIn(Resume::getStatus,resumePageParam.getFilterStatusNotIn());
        }
        //获取当前部门及其子部门
//        List<String> orgIds = sysOrgApi.getChildrenIds(StpLoginUserUtil.getLoginUser().getOrgId());
//        if(CollUtil.isNotEmpty(orgIds)){
//            queryWrapper.lambda().in(Resume::getBelongsDepts,orgIds);
//        }
        if(ObjectUtil.isNotEmpty(resumePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(Resume::getName,resumePageParam.getSearchKey())
                    .or().like(Resume::getSex,resumePageParam.getSearchKey())
                    .or().like(Resume::getMobile,resumePageParam.getSearchKey())
                    .or().like(Resume::getSchool,resumePageParam.getSearchKey())
                    .or().like(Resume::getSubject,resumePageParam.getSearchKey())
                    .or().like(Resume::getPositionType,resumePageParam.getSearchKey())
                    .or().like(Resume::getInfoSource,resumePageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(resumePageParam.getSortField(), resumePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(resumePageParam.getSortOrder());
            queryWrapper.orderBy(true, resumePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(resumePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Resume::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }
}
