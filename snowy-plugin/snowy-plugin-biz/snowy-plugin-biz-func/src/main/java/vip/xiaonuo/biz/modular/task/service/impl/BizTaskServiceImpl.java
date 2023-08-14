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
package vip.xiaonuo.biz.modular.task.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.common.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.task.service.SysOrgService;
import vip.xiaonuo.biz.modular.task.vo.BizSysOrgVo;
import vip.xiaonuo.biz.modular.task.vo.DeptAssReportVo;
import vip.xiaonuo.biz.modular.task.vo.DeptTaskResult;
import vip.xiaonuo.biz.modular.task.entity.BizTaskFlw;
import vip.xiaonuo.biz.modular.task.enums.BizTaskStatusEnum;
import vip.xiaonuo.biz.modular.task.param.*;
import vip.xiaonuo.biz.modular.task.service.BizTaskFlwService;
import vip.xiaonuo.biz.modular.task.vo.DeptTaskScore;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.task.entity.BizTask;
import vip.xiaonuo.biz.modular.task.mapper.BizTaskMapper;
import vip.xiaonuo.biz.modular.task.service.BizTaskService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FormRelationTableApi;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 业务表-绩效考核-任务表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/04 14:20
 **/

@DS("slave_1")
@Service
public class BizTaskServiceImpl extends ServiceImpl<BizTaskMapper, BizTask> implements BizTaskService {

    @Resource
    private BizTaskFlwService bizTaskFlwService;
    @Resource
    CommonCacheOperator commonCacheOperator;
    @Resource
    private SysOrgService sysOrgService;

    @Resource
    private BizTaskMapper bizTaskMapper;


    @Override
    public List<DeptTaskResult> page(BizTaskPageParam bizTaskPageParam) {
        QueryWrapper<BizTask> queryWrapper = new QueryWrapper<>();
        //只看 审核中 + 审核完成
        queryWrapper.lambda().in(BizTask::getStatus,"1","0");
        if(ObjectUtil.isNotEmpty(bizTaskPageParam.getOrgId())) {
            queryWrapper.lambda().eq(BizTask::getOrgId, bizTaskPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizTaskPageParam.getTaskName())) {
            queryWrapper.lambda().like(BizTask::getTaskName, bizTaskPageParam.getTaskName());
        }
        if(ObjectUtil.isNotEmpty(bizTaskPageParam.getYear())) {
            queryWrapper.lambda().eq(BizTask::getYear, bizTaskPageParam.getYear());
        }
        if(ObjectUtil.isAllNotEmpty(bizTaskPageParam.getSortField(), bizTaskPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTaskPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTaskPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTaskPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizTask::getCreateTime);
        }
        //直接查询所有
        bizTaskPageParam.setCurrent(1);
        bizTaskPageParam.setSize(Integer.MAX_VALUE);
        List<BizTask> result = this.page(CommonPageRequest.defaultPage(), queryWrapper).getRecords();
        //构造结构
        List<DeptTaskResult> deptTaskResultList = new ArrayList<>();
        if(CollUtil.isNotEmpty(result)) {
            List<BizSysOrgVo> orgList = sysOrgService.getOrgList();
            List<String> orgResult = result.stream().map(x -> x.getOrgId()).distinct().collect(Collectors.toList());
            for (String orgId:orgResult) {
                DeptTaskResult deptTaskResult = new DeptTaskResult();
                deptTaskResult.setOrgId(orgId);
                Optional<BizSysOrgVo> org = orgList.stream().filter(x->x.getOrgId().equals(orgId)).findFirst();
                String orgName = org.isPresent() ? org.get().getOrgName() : "";
                deptTaskResult.setOrgName(orgName);
                List<BizTask> listTask = result.stream().filter(x->x.getOrgId().equals(orgId)).collect(Collectors.toList());
                listTask.forEach(x->x.setOrgName(orgName));
                DeptTaskScore deptTaskScore = getDeptTotalScore(listTask);
                deptTaskResult.setTaskList(deptTaskScore.getBizTaskList());
                deptTaskResult.setTotalScore(deptTaskScore.getTotalScore());
                deptTaskResultList.add(deptTaskResult);
            }
        }
        return deptTaskResultList;
    }

    /***
     * 统计对象：某部门，某年度，年度任务分
     * 统计规则：满分80分制
     * 董事办打分规则：满分5分制
     *
     * 权重比，比如：两个任务70%，30%，总分就是56，24
     * 进度比（逾期0，晚一点50%，正常100%）
     * 董事办比（5星100%，4星80%以此类推）
     * 56=70%（比例固定）进度分+30%（比例固定）董事长审核
     *
     * 返回结果：统计某部门某年度的 年度任务得分
    ***/
    public DeptTaskScore getDeptTotalScore(List<BizTask> bizTaskList){
        //返回结果
        DeptTaskScore deptTaskScore = new DeptTaskScore();
        //统计参数
        double fullScore = 80; //部门年度任务总分 80 分
        double bossScore = 5;  //董事办任务评价总分 5 分
        double progress = 0.7; //任务进度分占比 70%
        double boss = 0.3;     //董事办成效评分占比 30%
        double total = 0;      //部门年度任务最后得分
        double totalRate = 0;  //部门任务总占比（因为合计可能超过100%）
        //统计逻辑
        if(CollUtil.isNotEmpty(bizTaskList)){
            totalRate = bizTaskList.stream().filter(x->ObjectUtil.isNotEmpty(x.getRate())).mapToDouble(BizTask::getRate).sum();
            //循环计算
            for (BizTask item:bizTaskList) {
                double progressScore = 0;  //任务进度分
                double effectScore = 0;    //任务成效分
                double adjScore = ObjectUtil.isNotEmpty(item.getAdjScore()) ? item.getAdjScore() : 0;       //人工修正分
                //任务权重，进度分系数
                if(ObjectUtil.isNotEmpty(item.getRate()) && ObjectUtil.isNotEmpty(item.getProgressRate()) ) {
                    progressScore = fullScore * (item.getRate() / totalRate) * progress * item.getProgressRate();
                }
                if(ObjectUtil.isNotEmpty(item.getRate()) && ObjectUtil.isNotEmpty(item.getBoosScore())) {
                    effectScore = fullScore * (item.getRate() / totalRate) * boss * (item.getBoosScore() / bossScore);
                }
                item.setProgressScore(progressScore);
                item.setEffectScore(effectScore);

                total = total + progressScore + effectScore + adjScore;
            }
        }
        deptTaskScore.setTotalScore(total);
        deptTaskScore.setBizTaskList(bizTaskList);
        return deptTaskScore;
    }

    /***
     * 根据考核年度，获取各部门的 年度任务分
     * @param assYear
     * @return
     */
    public List<DeptAssReportVo> getDeptTaskScore(String assYear){
        //获取本年度任务数据(只看审核中+审核完成)
        List<BizTask> bizTaskList = this.list(new QueryWrapper<BizTask>().lambda().eq(BizTask::getYear,assYear).in(BizTask::getStatus,"1","0"));
        //获取涉及的部门
        List<String> orgIds = bizTaskList.stream().map(x -> x.getOrgId()).distinct().collect(Collectors.toList());
        //返回的结果集
        List<DeptAssReportVo> deptAssReportVoList = new ArrayList<>();
        if(CollUtil.isNotEmpty(orgIds)){
            for (String orgId:orgIds) {
                DeptAssReportVo reportVo = new DeptAssReportVo();
                List<BizTask> listTask = bizTaskList.stream().filter(x->x.getOrgId().equals(orgId)).collect(Collectors.toList());
                reportVo.setAssYear(assYear);
                reportVo.setOrgId(orgId);
                reportVo.setNdScore(getDeptTotalScore(listTask).getTotalScore());
                deptAssReportVoList.add(reportVo);
            }
        }
        return deptAssReportVoList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTaskAddParam bizTaskAddParam) {
        BizTask bizTask = BeanUtil.toBean(bizTaskAddParam, BizTask.class);
        this.save(bizTask);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTaskEditParam bizTaskEditParam) {
        BizTask bizTask = this.queryEntity(bizTaskEditParam.getId());
        BeanUtil.copyProperties(bizTaskEditParam, bizTask);
        this.updateById(bizTask);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTaskIdParam> bizTaskIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizTaskIdParamList, BizTaskIdParam::getId));
    }

    @Override
    public BizTask detail(BizTaskIdParam bizTaskIdParam) {
        BizTask bizTask = this.queryEntity(bizTaskIdParam.getId());
        BizSysOrgVo org = sysOrgService.getOrgById(bizTask.getOrgId());
        bizTask.setOrgName(org.getOrgName());
        return bizTask;
    }

    @Override
    public BizTask queryEntity(String id) {
        BizTask bizTask = this.getById(id);
        if(ObjectUtil.isEmpty(bizTask)) {
            throw new CommonException("业务表-绩效考核-任务表不存在，id值为：{}", id);
        }
        return bizTask;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void flwAdd(TaskFlwParam taskFlwParam) {
        //部门年度重点任务审批流程
        String processInstanceId = taskFlwParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            //第一步： 任务流程单（类似与其他的单一流程业务表）
            BizTaskFlw bizTaskFlw = new BizTaskFlw();
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizTaskFlw.setId(id);
            bizTaskFlw.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizTaskFlw.setModelId(modelId);
            bizTaskFlw.setProcessInstanceId(processInstanceId);
            bizTaskFlw.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            bizTaskFlw.setExtJson(JsonUtils.object2json(taskFlwParam));
            bizTaskFlwService.save(bizTaskFlw);

            //第二步： 存储流程表对应关系
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            //将流程实例和业务表关联起来(任务流程单表)
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            formRelationTableApi.save2(modelId, BizCommonEnum.BIZ_TASK_FLW.getBizTable(),id,processInstanceId,dbName,BizCommonEnum.BIZ_TASK.getBizTable());

            //第三步： 处理绩效考核年度重要任务记录表（审批流程中一次提交多条年度重要任务）
            List<BizTask> bizTaskList = new ArrayList<>();
            for (FlwParam item : taskFlwParam.getFlwParamList()) {
                BizTask bizTask = new BizTask();
                BeanUtil.copyProperties(item, bizTask);
                bizTask.setFlwId(bizTaskFlw.getId());
                bizTask.setOrgId(taskFlwParam.getOrgId());
                bizTask.setOwer(taskFlwParam.getOwer());
                bizTask.setOwerId(taskFlwParam.getOwerId());
                bizTask.setYear(taskFlwParam.getYear());
                bizTask.setStatus(BizTaskStatusEnum.ACTIVE_STATUS.getValue());
                bizTaskList.add(bizTask);
            }
            if(CollUtil.isNotEmpty(bizTaskList)) {
                this.saveBatch(bizTaskList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void flwEdit(TaskFlwParam taskFlwParam) {
        //任务流程清单主键Id，与流程表单对应
        String taskFlwId = "";
        //判断审批流程内的修改
        if(ObjectUtil.isNotEmpty(taskFlwParam.getProcessInstanceId())){
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            JSONObject formRelationTable = formRelationTableApi.getInfoByProcessInstanceId(taskFlwParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                taskFlwId = formRelationTable.getStr("id");
            }
        }
        if(StrUtil.isNotEmpty(taskFlwId)){
            BizTaskFlw bizTaskFlw = bizTaskFlwService.queryEntity(taskFlwId);
            bizTaskFlw.setExtJson(JsonUtils.object2json(taskFlwParam));
            bizTaskFlwService.updateById(bizTaskFlw);
            //处理该流程的业务数据(业务数据主键由前端提供)
            //格式化前端数据
            List<BizTask> tmpBizTaskList = new ArrayList<>();
            for (FlwParam item : taskFlwParam.getFlwParamList()) {
                BizTask bizTask = new BizTask();
                BeanUtil.copyProperties(item, bizTask);
                bizTask.setFlwId(bizTaskFlw.getId());
                bizTask.setOrgId(taskFlwParam.getOrgId());
                bizTask.setOwer(taskFlwParam.getOwer());
                bizTask.setOwerId(taskFlwParam.getOwerId());
                bizTask.setYear(taskFlwParam.getYear());
                tmpBizTaskList.add(bizTask);
            }
            if(CollUtil.isNotEmpty(tmpBizTaskList)) {
                //历史数据
                List<BizTask> oldBizTaskList = this.list(new QueryWrapper<BizTask>().lambda().eq(BizTask::getFlwId, taskFlwId));
                List<String> oldIds = oldBizTaskList.stream().map(BizTask::getId).collect(Collectors.toList());
                //待更新的
                List<BizTask> updateList = tmpBizTaskList.stream().filter(x -> oldIds.contains(x.getId())).collect(Collectors.toList());
                //待新增的
                List<BizTask> addList = tmpBizTaskList.stream().filter(x -> !oldIds.contains(x.getId())).collect(Collectors.toList());
                //待删除的
                List<String> updateIds = updateList.stream().map(BizTask::getId).collect(Collectors.toList());
                List<BizTask> removeList = oldBizTaskList.stream().filter(x -> !updateIds.contains(x.getId())).collect(Collectors.toList());

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
                List<BizTask> oldBizTaskList = this.list(new QueryWrapper<BizTask>().lambda().eq(BizTask::getFlwId, taskFlwId));
                if (CollUtil.isNotEmpty(oldBizTaskList)) {
                    this.removeByIds(oldBizTaskList);
                }
            }
        }
    }

    @Override
    public void update(BizTaskUpdateParam bizTaskUpdateParam) {
        BizTask bizTask = this.queryEntity(bizTaskUpdateParam.getId());
        String colName = bizTaskUpdateParam.getColName().toLowerCase();
        //完成进度
        if("finishstatus".equals(colName)){
            bizTask.setFinishStatus(Double.valueOf(bizTaskUpdateParam.getColValue()));
        }//证明文件
        else if("attach".equals(colName)){
            bizTask.setAttach(bizTaskUpdateParam.getColValue());
        }//任务权重
        else if("rate".equals(colName)){
            bizTask.setRate(Double.valueOf(bizTaskUpdateParam.getColValue()));
        }//进度系数
        else if("progressrate".equals(colName)){
            bizTask.setProgressRate(Double.valueOf(bizTaskUpdateParam.getColValue()));
        }//完成情况
        else if("memo".equals(colName)){
            bizTask.setMemo(bizTaskUpdateParam.getColValue());
        }//人工修正分
        else if("adjscore".equals(colName)){
            bizTask.setAdjScore(Double.valueOf(bizTaskUpdateParam.getColValue()));
        }
        this.updateById(bizTask);
    }


    @Override
    public void autoUpdateProgressRate() {
         bizTaskMapper.firstAuto();
         bizTaskMapper.secondAuto();
         bizTaskMapper.thirdAuto();
    }
}
