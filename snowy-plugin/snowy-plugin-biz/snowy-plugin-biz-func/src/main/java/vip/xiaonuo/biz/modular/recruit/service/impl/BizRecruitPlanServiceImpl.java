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
package vip.xiaonuo.biz.modular.recruit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.common.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.recruit.entity.BizRecruitFlw;
import vip.xiaonuo.biz.modular.recruit.entity.BizRecruitResume;
import vip.xiaonuo.biz.modular.recruit.param.dto.RecruitPlanFlwAddDto;
import vip.xiaonuo.biz.modular.recruit.param.dto.RecruitPlanFlwDto;
import vip.xiaonuo.biz.modular.recruit.param.dto.RecruitPlanGroupDto;
import vip.xiaonuo.biz.modular.recruit.param.dto.RecruitPlanPageDto;
import vip.xiaonuo.biz.modular.recruit.service.BizRecruitFlwService;
import vip.xiaonuo.biz.modular.recruit.service.BizRecruitResumeService;
import vip.xiaonuo.biz.modular.recruit.utils.ResumeParserUtil;
import vip.xiaonuo.biz.modular.recruit.vo.BizRecruitPlanDetailVo;
import vip.xiaonuo.biz.modular.recruit.vo.BizRecruitPlanVo;
import vip.xiaonuo.biz.modular.task.entity.BizTask;
import vip.xiaonuo.biz.modular.task.entity.BizTaskFlw;
import vip.xiaonuo.biz.modular.task.enums.BizTaskStatusEnum;
import vip.xiaonuo.biz.modular.task.param.FlwParam;
import vip.xiaonuo.biz.modular.task.service.SysOrgService;
import vip.xiaonuo.biz.modular.task.vo.BizSysOrgVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.recruit.entity.BizRecruitPlan;
import vip.xiaonuo.biz.modular.recruit.mapper.BizRecruitPlanMapper;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitPlanAddParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitPlanEditParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitPlanIdParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitPlanPageParam;
import vip.xiaonuo.biz.modular.recruit.service.BizRecruitPlanService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FormRelationTableApi;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 业务表-招聘-部门年度计划表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/23 09:02
 **/

@DS("slave_1")
@Service
public class BizRecruitPlanServiceImpl extends ServiceImpl<BizRecruitPlanMapper, BizRecruitPlan> implements BizRecruitPlanService {

    @Resource
    BizRecruitFlwService bizRecruitFlwService;
    @Resource
    CommonCacheOperator commonCacheOperator;
    @Resource
    SysOrgService sysOrgService;
    @Resource
    BizRecruitResumeService bizRecruitResumeService;

    @Override
    public List<BizRecruitPlanVo> page(RecruitPlanPageDto recruitPlanPageDto) {
        QueryWrapper<BizRecruitPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(BizRecruitPlan::getStatus,"0","1");
        if(ObjectUtil.isNotEmpty(recruitPlanPageDto.getYear())) {
            queryWrapper.lambda().eq(BizRecruitPlan::getYear, recruitPlanPageDto.getYear());
        }
        if(ObjectUtil.isNotEmpty(recruitPlanPageDto.getOrgId())) {
            queryWrapper.lambda().eq(BizRecruitPlan::getOrgId, recruitPlanPageDto.getOrgId());
        }
        queryWrapper.lambda().orderByAsc(BizRecruitPlan::getOrgId).orderByDesc(BizRecruitPlan::getYear).orderByDesc(BizRecruitPlan::getCreateTime);
        List<BizRecruitPlan> recruitPlans = this.list(queryWrapper);
        return planAnalysis(recruitPlans);
    }

    /**
     * 招聘计划，按部门+年度统计
     * @param recruitPlans
     * @return
     */
    public List<BizRecruitPlanVo> planAnalysis(List<BizRecruitPlan> recruitPlans){
        if(CollUtil.isEmpty(recruitPlans)) return null;
        //返回结构体
        List<BizRecruitPlanVo> bizRecruitPlanVoList = new ArrayList<>();
        //多条件分组
        Map<RecruitPlanGroupDto, List<BizRecruitPlan>> groupPlans = recruitPlans.stream().collect(Collectors.groupingBy(
                new Function<BizRecruitPlan, RecruitPlanGroupDto>() {
                    @Override
                    public RecruitPlanGroupDto apply(BizRecruitPlan bizRecruitPlan) {
                        return new RecruitPlanGroupDto(bizRecruitPlan.getYear(), bizRecruitPlan.getOrgId());
                    }
                }
        ));
        //循环
        List<BizSysOrgVo> orgList = sysOrgService.getOrgList();
        for (Map.Entry<RecruitPlanGroupDto, List<BizRecruitPlan>> item: groupPlans.entrySet()) {
            //获取组织名称
            Optional<BizSysOrgVo> org = orgList.stream().filter(x->x.getOrgId().equals(item.getKey().getOrgId())).findFirst();
            String orgName = org.isPresent()?org.get().getOrgName():"";
            //拼接结果
            BizRecruitPlanVo bizRecruitPlanVo = new BizRecruitPlanVo();
            bizRecruitPlanVo.setYear(item.getKey().getYear());
            bizRecruitPlanVo.setOrgId(item.getKey().getOrgId());
            bizRecruitPlanVo.setOrgName(orgName);
            List<BizRecruitPlanDetailVo> bizRecruitPlanDetailVoList = BeanUtil.copyToList(item.getValue(), BizRecruitPlanDetailVo.class);
            //统计社招人数\校招人数
            List<BizRecruitResume> bizRecruitResumeList = bizRecruitResumeService.list(
                    new QueryWrapper<BizRecruitResume>().lambda()
                            .in(BizRecruitResume::getPlanId,bizRecruitPlanDetailVoList.stream().map(x->x.getId()).collect(Collectors.toList()))
                            .in(BizRecruitResume::getStatus,"1"));
            bizRecruitPlanDetailVoList.forEach(x->{
                x.setSchoolNums(0);
                x.setSocialNums(bizRecruitResumeList.stream().filter(m->m.getPlanId().equals(x.getId())).collect(Collectors.toList()).size());
            });
            bizRecruitPlanVo.setPlanList(bizRecruitPlanDetailVoList);
            bizRecruitPlanVo.setNewPlanNums(bizRecruitPlanDetailVoList.get(0).getPlanNums());  //取最新
            bizRecruitPlanVo.setNewRealNums(bizRecruitPlanDetailVoList.get(0).getRealNums());  //取最新
            bizRecruitPlanVoList.add(bizRecruitPlanVo);
        }
        return bizRecruitPlanVoList;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(RecruitPlanFlwDto recruitPlanFlwDto) {
        List<BizRecruitPlan> bizRecruitPlanList = BeanUtil.copyToList(recruitPlanFlwDto.getRecruitPlanList(), BizRecruitPlan.class);
        this.saveBatch(bizRecruitPlanList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizRecruitPlanEditParam bizRecruitPlanEditParam) {
        BizRecruitPlan bizRecruitPlan = this.queryEntity(bizRecruitPlanEditParam.getId());
        BeanUtil.copyProperties(bizRecruitPlanEditParam, bizRecruitPlan);
        this.updateById(bizRecruitPlan);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizRecruitPlanIdParam> bizRecruitPlanIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizRecruitPlanIdParamList, BizRecruitPlanIdParam::getId));
    }

    @Override
    public BizRecruitPlan detail(BizRecruitPlanIdParam bizRecruitPlanIdParam) {
        return this.queryEntity(bizRecruitPlanIdParam.getId());
    }

    @Override
    public BizRecruitPlan queryEntity(String id) {
        BizRecruitPlan bizRecruitPlan = this.getById(id);
        if(ObjectUtil.isEmpty(bizRecruitPlan)) {
            throw new CommonException("业务表-招聘-部门年度计划表不存在，id值为：{}", id);
        }
        return bizRecruitPlan;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void flwAdd(RecruitPlanFlwDto recruitPlanFlwDto) {
        //招聘计划审批流程
        String processInstanceId = recruitPlanFlwDto.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            //第一步： 任务流程单（类似与其他的单一流程业务表）
            BizRecruitFlw bizRecruitFlw = new BizRecruitFlw();
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizRecruitFlw.setId(id);
            bizRecruitFlw.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizRecruitFlw.setModelId(modelId);
            bizRecruitFlw.setProcessInstanceId(processInstanceId);
            bizRecruitFlw.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            bizRecruitFlw.setExtJson(JsonUtils.object2json(recruitPlanFlwDto));
            bizRecruitFlwService.save(bizRecruitFlw);

            //第二步： 存储流程表对应关系
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            //将流程实例和业务表关联起来(任务流程单表)
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            formRelationTableApi.save2(modelId, BizCommonEnum.BIZ_RECRUIT_FLW.getBizTable(),id,processInstanceId,dbName,BizCommonEnum.BIZ_RECRUIT_PLAN.getBizTable());

            //第三步： 处理招聘部门计划表（审批流程中一次提交多条计划）
            List<BizRecruitPlan> bizRecruitPlanList = BeanUtil.copyToList(recruitPlanFlwDto.getRecruitPlanList(), BizRecruitPlan.class);
            bizRecruitPlanList.forEach(x->{
                x.setFlwId(bizRecruitFlw.getId());
                x.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            });
            if(CollUtil.isNotEmpty(bizRecruitPlanList)) {
                this.saveBatch(bizRecruitPlanList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void flwEdit(RecruitPlanFlwDto recruitPlanFlwDto) {
        //任务流程清单主键Id，与流程表单对应
        String flwId = "";
        //判断审批流程内的修改
        if(ObjectUtil.isNotEmpty(recruitPlanFlwDto.getProcessInstanceId())){
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            JSONObject formRelationTable = formRelationTableApi.getInfoByProcessInstanceId(recruitPlanFlwDto.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                flwId = formRelationTable.getStr("id");
            }
        }
        if(StrUtil.isNotEmpty(flwId)){
            BizRecruitFlw bizRecruitFlw = bizRecruitFlwService.queryEntity(flwId);
            bizRecruitFlw.setExtJson(JsonUtils.object2json(recruitPlanFlwDto));
            bizRecruitFlwService.updateById(bizRecruitFlw);
            //处理该流程的业务数据(业务数据主键由前端提供)
            //格式化前端数据
            List<BizRecruitPlan> bizRecruitPlanList = BeanUtil.copyToList(recruitPlanFlwDto.getRecruitPlanList(), BizRecruitPlan.class);
            bizRecruitPlanList.forEach(x->{
                x.setFlwId(bizRecruitFlw.getId());
            });
            if(CollUtil.isNotEmpty(bizRecruitPlanList)) {
                //历史数据
                List<BizRecruitPlan> oldBizRecruitPlans = this.list(new QueryWrapper<BizRecruitPlan>().lambda().eq(BizRecruitPlan::getFlwId, flwId));
                List<String> oldIds = oldBizRecruitPlans.stream().map(BizRecruitPlan::getId).collect(Collectors.toList());
                //待更新的
                List<BizRecruitPlan> updateList = bizRecruitPlanList.stream().filter(x -> oldIds.contains(x.getId())).collect(Collectors.toList());
                //待新增的
                List<BizRecruitPlan> addList = bizRecruitPlanList.stream().filter(x -> !oldIds.contains(x.getId())).collect(Collectors.toList());
                //待删除的
                List<String> updateIds = updateList.stream().map(BizRecruitPlan::getId).collect(Collectors.toList());
                List<BizRecruitPlan> removeList = oldBizRecruitPlans.stream().filter(x -> !updateIds.contains(x.getId())).collect(Collectors.toList());
                //更新数据库
                if (CollUtil.isNotEmpty(addList)) {
                    this.saveBatch(addList);
                }
                if (CollUtil.isNotEmpty(updateList)) {
                    this.updateBatchById(updateList);
                }
                if (CollUtil.isNotEmpty(removeList)) {
                    this.removeByIds(removeList);
                }
            }else {
                //历史数据
                List<BizRecruitPlan> oldBizRecruitPlanList = this.list(new QueryWrapper<BizRecruitPlan>().lambda().eq(BizRecruitPlan::getFlwId, flwId));
                if (CollUtil.isNotEmpty(oldBizRecruitPlanList)) {
                    this.removeByIds(oldBizRecruitPlanList);
                }
            }
        }
    }

    @Override
    public Page<BizRecruitPlan> planSelect(BizRecruitPlanPageParam bizRecruitPlanPageParam) {
        QueryWrapper<BizRecruitPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(BizRecruitPlan::getStatus,"1");
        if(ObjectUtil.isNotEmpty(bizRecruitPlanPageParam.getYear())) {
            queryWrapper.lambda().eq(BizRecruitPlan::getYear, bizRecruitPlanPageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitPlanPageParam.getSn())) {
            queryWrapper.lambda().eq(BizRecruitPlan::getSn, bizRecruitPlanPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitPlanPageParam.getOrgId())) {
            queryWrapper.lambda().eq(BizRecruitPlan::getOrgId, bizRecruitPlanPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitPlanPageParam.getStatus())) {
            queryWrapper.lambda().eq(BizRecruitPlan::getStatus, bizRecruitPlanPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(bizRecruitPlanPageParam.getSortField(), bizRecruitPlanPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizRecruitPlanPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizRecruitPlanPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizRecruitPlanPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizRecruitPlan::getId);
        }
        Page<BizRecruitPlan> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);

        if(CollUtil.isNotEmpty(page.getRecords())){
            List<BizSysOrgVo> orgVoList = sysOrgService.getOrgList();
            page.getRecords().forEach(x->{
                Optional<BizSysOrgVo> org = orgVoList.stream().filter(m->m.getOrgId().equals(x.getOrgId())).findFirst();
                x.setOrgName(org.isPresent()?org.get().getOrgName():"");
            });
        }
        return page;
    }
}
