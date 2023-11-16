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
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.org.entity.BizOrg;
import vip.xiaonuo.biz.modular.org.service.BizOrgService;
import vip.xiaonuo.biz.modular.school.entity.ResumeRecord;
import vip.xiaonuo.biz.modular.school.enums.ResumeActionTypeEnum;
import vip.xiaonuo.biz.modular.school.enums.ResumeOprResultEnum;
import vip.xiaonuo.biz.modular.school.enums.ResumeStatusEnum;
import vip.xiaonuo.biz.modular.school.param.*;
import vip.xiaonuo.biz.modular.school.service.ResumeRecordService;
import vip.xiaonuo.biz.modular.school.vo.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.school.entity.Resume;
import vip.xiaonuo.biz.modular.school.mapper.ResumeMapper;
import vip.xiaonuo.biz.modular.school.service.ResumeService;
import vip.xiaonuo.dbs.api.DbsApi;
import vip.xiaonuo.dbs.core.consts.DbsConstant;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    ResumeMapper resumeMapper;

    @Resource
    BizOrgService bizOrgService;

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
        //获取当前部门及其子部门
        DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
        dbsApi.changeDataSource(DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase());
        List<BizOrg> allOrgList = bizOrgService.getAllOrgList();
        List<BizOrg> orgList = bizOrgService.getChildListById(allOrgList, StpLoginUserUtil.getLoginUser().getOrgId(),true);
        dbsApi.changeDataSource(DbsConstant.SLAVE_1_DATASOURCE_NAME);
        //查询
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
        if(CollUtil.isNotEmpty(orgList)){
            queryWrapper.lambda().in(Resume::getBelongsDepts,orgList.stream().map(x->x.getId()).collect(Collectors.toList()));
        }
        //关键字
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

    @Override
    public void cancelAssignDept(AssignDeptsDto assignDept) {
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
            oldResume.setStatus(ResumeStatusEnum.Send.getValue());
            oldResume.setBelongsDepts("");
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
    public ReportModel getReportView(ReportParam reportParam) {
        ReportModel reportModel = new ReportModel();
        QueryWrapper<Resume> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(reportParam.getStartTime())){
            queryWrapper.lambda().ge(Resume::getCreateTime,reportParam.getStartTime());
        }
        if(ObjectUtil.isNotEmpty(reportParam.getEndTime())){
            queryWrapper.lambda().le(Resume::getCreateTime,reportParam.getEndTime());
        }
        //时间区间内的简历
        List<Resume> dbResumes = this.list(queryWrapper);

        //小数位设置
        DecimalFormat df = new DecimalFormat("#.00");

        //1. 简历收集-柱状图(根据简历来源统计)
        Map<String,List<Resume>> infoSourceGroup = dbResumes.stream().collect(Collectors.groupingBy(x->x.getInfoSource()));
        BarChart collect = new BarChart();
        List<String> xAxis = new ArrayList<>();
        List<Integer> firstData = new ArrayList<>();
        for (Map.Entry<String,List<Resume>> entry:infoSourceGroup.entrySet()) {
            xAxis.add(entry.getKey());
            firstData.add(entry.getValue().size());
        }
        collect.setXAxis(xAxis);
        collect.setFirstData(firstData);
        reportModel.setCollect(collect);

        //2. 专业类别分析-饼状图
        List<Resume> positionTypeList = dbResumes.stream().filter(x->ObjectUtil.isNotEmpty(x.getPositionType())).collect(Collectors.toList());
        List<KeyValueObj> keyValueObjList = new ArrayList<>();
        for (Resume item:positionTypeList) {
            List<String> positionTypes = Arrays.asList(item.getPositionType().split(","));
            positionTypes.forEach(x->{
                KeyValueObj obj = new KeyValueObj();
                obj.setKey(x);
                obj.setValue(item.getId());
                keyValueObjList.add(obj);
            });
        }
        Map<String,List<KeyValueObj>> positionTypeGroup = keyValueObjList.stream().collect(Collectors.groupingBy(x->x.getKey()));
        PieChart classification = new PieChart();
        List<Pie> pieList = new ArrayList<>();
        for (Map.Entry<String,List<KeyValueObj>> entry:positionTypeGroup.entrySet()) {
            Pie pie = new Pie();
            pie.setName(entry.getKey());
            pie.setValue(entry.getValue().size());
            pieList.add(pie);
        }
        classification.setData(pieList);
        reportModel.setClassification(classification);

        //所有推荐给部门的简历
        List<Resume> sendDeptList = dbResumes.stream().filter(x->ObjectUtil.isNotEmpty(x.getBelongsDepts())).collect(Collectors.toList());
        //按照部门分组
        Map<String,List<Resume>> deptGroup = sendDeptList.stream().collect(Collectors.groupingBy(x->x.getBelongsDepts()));

        //3. 有效简历学历层次分析-柱状图
        BarChart levels = new BarChart();
        List<String> levels_xAxis = new ArrayList<>();
        List<Integer> levels_firstData = new ArrayList<>();
        List<Integer> levels_secondData = new ArrayList<>();
        List<Integer> levels_thirdData = new ArrayList<>();
        for (Map.Entry<String,List<Resume>> entry:deptGroup.entrySet()) {
            List<Resume> tmpList = entry.getValue().stream().filter(x->ObjectUtil.isNotEmpty(x.getAcademic())).collect(Collectors.toList());
            levels_xAxis.add(entry.getKey());
            levels_firstData.add(tmpList.stream().filter(x->x.getAcademic() == 1).collect(Collectors.toList()).size());
            levels_secondData.add(tmpList.stream().filter(x->x.getAcademic() == 2).collect(Collectors.toList()).size());
            levels_thirdData.add(tmpList.stream().filter(x->x.getAcademic() == 3).collect(Collectors.toList()).size());
        }
        levels.setXAxis(levels_xAxis);
        levels.setFirstData(levels_firstData);
        levels.setSecondData(levels_secondData);
        levels.setThirdData(levels_thirdData);
        reportModel.setLevels(levels);

        //4. 有效简历&初筛合格-柱状图
        BarChart filter = new BarChart();
        List<String> filter_xAxis = new ArrayList<>();
        List<Integer> filter_firstData = new ArrayList<>();
        List<Integer> filter_secondData = new ArrayList<>();
        for (Map.Entry<String,List<Resume>> entry:deptGroup.entrySet()) {
            List<Resume> tmpList = entry.getValue().stream().filter(x-> Integer.parseInt(x.getStatus()) > Integer.parseInt(ResumeStatusEnum.ReSend.getValue())).collect(Collectors.toList());
            filter_xAxis.add(entry.getKey());
            filter_firstData.add(entry.getValue().size());
            filter_secondData.add(tmpList.size());
        }
        filter.setXAxis(filter_xAxis);
        filter.setFirstData(filter_firstData);
        filter.setSecondData(filter_secondData);
        reportModel.setFilter(filter);

        //5. 到面率&通过率-折线图
        LineChart2<String> passing = new LineChart2();
        List<String> passing_xAxis = new ArrayList<>();
        List<String> passing_firstData = new ArrayList<>();
        List<String> passing_secondData = new ArrayList<>();
        for (Map.Entry<String,List<Resume>> entry:deptGroup.entrySet()) {
            //通知面试
            int preFace = entry.getValue().stream().filter(x->Integer.parseInt(x.getStatus()) >= Integer.parseInt(ResumeStatusEnum.Notified.getValue())).collect(Collectors.toList()).size();
            //未到面
            int noFace = entry.getValue().stream().filter(x->x.getStatus().equals(ResumeStatusEnum.NotFace.getValue())).collect(Collectors.toList()).size();
            int addFace = preFace - noFace;    //参加面试数 ！！！
            //面试通过
            int passFace = entry.getValue().stream().filter(x->Integer.parseInt(x.getStatus()) >= Integer.parseInt(ResumeStatusEnum.PreOffer.getValue())).collect(Collectors.toList()).size();
            float arriveRate = preFace == 0 ? 0 : (float)addFace / preFace;   //到面率   参加面试数/所有待参加面试数
            float passRate = addFace == 0 ? 0 : (float)passFace / addFace;    //通过率   通过面试数/参加面试数
            passing_xAxis.add(entry.getKey());
            passing_firstData.add(df.format(arriveRate));
            passing_secondData.add(df.format(passRate));
        }
        passing.setXAxis(passing_xAxis);
        passing.setFirstData(passing_firstData);
        passing.setSecondData(passing_secondData);
        reportModel.setPassing(passing);

        //6. offer拒绝率-折线图
        LineChart<String> refuse = new LineChart<String>();
        List<String> refuse_xAxis = new ArrayList<>();
        List<String> refuse_data = new ArrayList<>();
        for (Map.Entry<String,List<Resume>> entry:deptGroup.entrySet()) {
            //待发Offer数 ！！！
            int preOffer = entry.getValue().stream().filter(x->Integer.parseInt(x.getStatus()) >= Integer.parseInt(ResumeStatusEnum.PreOffer.getValue())).collect(Collectors.toList()).size();
            //拒绝Offer数 ！！！
            int refuseOffer = entry.getValue().stream().filter(x->x.getStatus().equals(ResumeStatusEnum.Refuse.getValue())).collect(Collectors.toList()).size();
            float refuseRate = preOffer == 0 ? 0 : (float)(refuseOffer / preOffer);   //Offer拒绝率   Offer拒绝数/待发Offer数
            refuse_xAxis.add(entry.getKey());
            refuse_data.add(df.format(refuseRate));
        }
        refuse.setXAxis(refuse_xAxis);
        refuse.setData(refuse_data);
        reportModel.setRefuse(refuse);

        //7. 面试通过人数及接受offer人数-柱状图
        BarChart passPerson = new BarChart();
        List<String> passPerson_xAxis = new ArrayList<>();
        List<Integer> passPerson_firstData = new ArrayList<>();
        List<Integer> passPerson_secondData = new ArrayList<>();
        for (Map.Entry<String,List<Resume>> entry:deptGroup.entrySet()) {
            //面试通过 ！！！
            int passFace = entry.getValue().stream().filter(x->Integer.parseInt(x.getStatus()) >= Integer.parseInt(ResumeStatusEnum.PreOffer.getValue())).collect(Collectors.toList()).size();
            //已接收Offer数 ！！！
            int arrivedOffer = entry.getValue().stream().filter(x->x.getStatus().equals(ResumeStatusEnum.Arrived.getValue())).collect(Collectors.toList()).size();
            passPerson_xAxis.add(entry.getKey());
            passPerson_firstData.add(passFace);
            passPerson_secondData.add(arrivedOffer);
        }
        passPerson.setXAxis(passPerson_xAxis);
        passPerson.setFirstData(passPerson_firstData);
        passPerson.setSecondData(passPerson_secondData);
        reportModel.setPassPerson(passPerson);

        //8. offer拒绝原因-饼状图
        PieChart refuseReson = new PieChart();
        List<ResumeRecord> refuseReasonList = resumeMapper.getRecordRefuse(reportParam.getStartTime(),reportParam.getEndTime(),ResumeActionTypeEnum.Offer.getValue(),ResumeOprResultEnum.Refuse.getValue());
        //按照拒绝原因分组
        Map<String,List<ResumeRecord>> reasonGroup = refuseReasonList.stream().collect(Collectors.groupingBy(x->x.getReason()));
        List<Pie> pies = new ArrayList<>();
        for (Map.Entry<String,List<ResumeRecord>> entry:reasonGroup.entrySet()) {
            Pie tmp = new Pie();
            tmp.setName(entry.getKey());
            tmp.setValue(entry.getValue().size());
            pies.add(tmp);
        }
        refuseReson.setData(pies);
        reportModel.setRefuseReson(refuseReson);
        return reportModel;
    }

    @Override
    public ResumeTotalModel getResumeTotalView(ReportParam reportParam) {
        ResumeTotalModel resumeTotalModel = new ResumeTotalModel();
        QueryWrapper<Resume> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(reportParam.getStartTime())){
            queryWrapper.lambda().ge(Resume::getCreateTime,reportParam.getStartTime());
        }
        if(ObjectUtil.isNotEmpty(reportParam.getEndTime())){
            queryWrapper.lambda().le(Resume::getCreateTime,reportParam.getEndTime());
        }
        //时间区间内的简历
        List<Resume> dbResumes = this.list(queryWrapper);
        resumeTotalModel.setCollectNum(dbResumes.size());
        resumeTotalModel.setEffectiveNum(dbResumes.stream().filter(x->Integer.parseInt(x.getStatus()) >= Integer.parseInt(ResumeStatusEnum.ReSend.getValue())).collect(Collectors.toList()).size());
        resumeTotalModel.setFilterNum(dbResumes.stream().filter(x->Integer.parseInt(x.getStatus()) > Integer.parseInt(ResumeStatusEnum.ReSend.getValue())).collect(Collectors.toList()).size());
        resumeTotalModel.setNotifiedNum(dbResumes.stream().filter(x->Integer.parseInt(x.getStatus()) >= Integer.parseInt(ResumeStatusEnum.Notified.getValue())).collect(Collectors.toList()).size());
        resumeTotalModel.setCheckNum(dbResumes.stream().filter(x->Integer.parseInt(x.getStatus()) > Integer.parseInt(ResumeStatusEnum.NotFace.getValue())).collect(Collectors.toList()).size());
        resumeTotalModel.setPassNum(dbResumes.stream().filter(x->Integer.parseInt(x.getStatus()) >= Integer.parseInt(ResumeStatusEnum.PreOffer.getValue())).collect(Collectors.toList()).size());
        resumeTotalModel.setOfferNum(dbResumes.stream().filter(x->x.getStatus().equals(ResumeStatusEnum.Arrived.getValue())).collect(Collectors.toList()).size());
        return resumeTotalModel;
    }

    @Override
    public Page<ResumeReportViewModel> getResumeReportPage(ReportPageParam reportPageParam) {
        //根据页面搜索条件，获取简历信息
        QueryWrapper<Resume> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(reportPageParam.getName())) {
            queryWrapper.lambda().like(Resume::getName, reportPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(reportPageParam.getSex())) {
            queryWrapper.lambda().eq(Resume::getSex, reportPageParam.getSex());
        }
        if(ObjectUtil.isNotEmpty(reportPageParam.getMobile())) {
            queryWrapper.lambda().like(Resume::getMobile, reportPageParam.getMobile());
        }
        if(ObjectUtil.isNotEmpty(reportPageParam.getSchool())) {
            queryWrapper.lambda().like(Resume::getSchool, reportPageParam.getSchool());
        }
        if(ObjectUtil.isNotEmpty(reportPageParam.getSubject())) {
            queryWrapper.lambda().like(Resume::getSubject, reportPageParam.getSubject());
        }
        if(ObjectUtil.isNotEmpty(reportPageParam.getPositionType())) {
            queryWrapper.lambda().eq(Resume::getPositionType, reportPageParam.getPositionType());
        }
        if(ObjectUtil.isNotEmpty(reportPageParam.getInfoSource())) {
            queryWrapper.lambda().eq(Resume::getInfoSource, reportPageParam.getInfoSource());
        }
        if(ObjectUtil.isNotEmpty(reportPageParam.getStatus())) {
            queryWrapper.lambda().eq(Resume::getStatus, reportPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(reportPageParam.getAcademic())) {
            queryWrapper.lambda().eq(Resume::getAcademic, reportPageParam.getAcademic());
        }
        if(ObjectUtil.isNotEmpty(reportPageParam.getStartTime())){
            queryWrapper.lambda().ge(Resume::getCreateTime,reportPageParam.getStartTime());
        }
        if(ObjectUtil.isNotEmpty(reportPageParam.getEndTime())){
            queryWrapper.lambda().le(Resume::getCreateTime,reportPageParam.getEndTime());
        }
        if(ObjectUtil.isNotEmpty(reportPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(Resume::getName,reportPageParam.getSearchKey())
                    .or().like(Resume::getSex,reportPageParam.getSearchKey())
                    .or().like(Resume::getMobile,reportPageParam.getSearchKey())
                    .or().like(Resume::getSchool,reportPageParam.getSearchKey())
                    .or().like(Resume::getSubject,reportPageParam.getSearchKey())
                    .or().like(Resume::getPositionType,reportPageParam.getSearchKey())
                    .or().like(Resume::getInfoSource,reportPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(reportPageParam.getSortField(), reportPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(reportPageParam.getSortOrder());
            queryWrapper.orderBy(true, reportPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(reportPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Resume::getCreateTime);
        }
        Page<Resume> resumePage = this.page(CommonPageRequest.defaultPage(), queryWrapper);

        //2. 获取简历对应的（每类操作类型，最新的一条记录）
        List<ResumeReportViewModel> resumeReportViewModelList = new ArrayList<>();
        if(CollUtil.isNotEmpty(resumePage.getRecords())) {
            List<String> resumeIds = resumePage.getRecords().stream().map(x->x.getId()).collect(Collectors.toList());
            List<ResumeRecord> resumeRecords = resumeMapper.getRecordByAction(resumeIds,
                    Arrays.asList(ResumeActionTypeEnum.InterviewNotice.getValue(),ResumeActionTypeEnum.InterviewRigster.getValue(),ResumeActionTypeEnum.Offer.getValue()));
            for (Resume resume:resumePage.getRecords()) {
                ResumeReportViewModel reportViewModel = BeanUtil.copyProperties(resume,ResumeReportViewModel.class);
                reportViewModel.setResumesId(resume.getId());
                reportViewModel.setIsPassFilter(Integer.parseInt(resume.getStatus()) > Integer.parseInt(ResumeStatusEnum.ReSend.getValue()) ? "是" : "否");
                ResumeRecord tmp = resumeRecords.stream().filter(x->x.getResumesId().equals(resume.getId()) && x.getActionType().equals(ResumeActionTypeEnum.InterviewNotice.getValue())).findFirst().orElse(null);
                reportViewModel.setActionTime(ObjectUtil.isNotEmpty(tmp) ? tmp.getActionTime() : null);
                reportViewModel.setIsFace(Integer.parseInt(resume.getStatus()) > Integer.parseInt(ResumeStatusEnum.NotFace.getValue()) ? "是" : resume.getStatus().equals(ResumeStatusEnum.NotFace.getValue()) ? "否" : "");
                tmp = resumeRecords.stream().filter(x->x.getResumesId().equals(resume.getId()) && x.getActionType().equals(ResumeActionTypeEnum.InterviewRigster.getValue())).findFirst().orElse(null);
                reportViewModel.setFaceResult(ObjectUtil.isNotEmpty(tmp) ? tmp.getResult() : "");
                reportViewModel.setIsOffer(resume.getStatus().equals(ResumeStatusEnum.Arrived.getValue()) ? "是" : resume.getStatus().equals(ResumeStatusEnum.Refuse.getValue()) ? "否" : "");
                tmp = resumeRecords.stream().filter(x->x.getResumesId().equals(resume.getId()) && x.getActionType().equals(ResumeActionTypeEnum.Offer.getValue())).findFirst().orElse(null);
                reportViewModel.setReason(ObjectUtil.isNotEmpty(tmp) ? tmp.getReason() : "");
                reportViewModel.setCreateTime(resume.getCreateTime());
                resumeReportViewModelList.add(reportViewModel);
            }
        }
        Page<ResumeReportViewModel> result = new Page<>();
        result.setCurrent(resumePage.getCurrent());
        result.setPages(resumePage.getPages());
        result.setSize(resumePage.getSize());
        result.setTotal(resumePage.getTotal());
        result.setRecords(resumeReportViewModelList);
        return result;
    }
}
