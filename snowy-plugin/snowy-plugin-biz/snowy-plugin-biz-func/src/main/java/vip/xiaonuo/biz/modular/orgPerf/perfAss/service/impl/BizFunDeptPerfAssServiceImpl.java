package vip.xiaonuo.biz.modular.orgPerf.perfAss.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.orgPerf.finance.entity.BizFinance;
import vip.xiaonuo.biz.modular.orgPerf.finance.service.BizFinanceService;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizFunDeptGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.enums.BizAssPeriodlEnum;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizGoalIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizCompanyGoalService;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizFunDeptGoalService;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.entity.BizFunDeptPerfAss;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.vo.BizFunDeptPerfAssDataVo;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.vo.BizOrgAssRecordVo;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.CompanySummaryListParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.FunDeptDetailParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.util.SummaryUtil;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.CompanySummaryListVo;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.FunDeptSummaryDetailVo;
import vip.xiaonuo.biz.modular.task.entity.BizTask;
import vip.xiaonuo.biz.modular.task.param.BizDeptAssRecordsPageParam;
import vip.xiaonuo.biz.modular.task.param.BizTaskPageParam;
import vip.xiaonuo.biz.modular.task.service.BizDeptAssRecordsService;
import vip.xiaonuo.biz.modular.task.service.BizTaskService;
import vip.xiaonuo.biz.modular.task.vo.DeptAssRecordVo;
import vip.xiaonuo.biz.modular.task.vo.DeptTaskResult;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.mapper.BizFunDeptPerfAssMapper;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssAddParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssEditParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssIdParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssPageParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.service.BizFunDeptPerfAssService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 职能部门-绩效评估Service接口实现类
 *
 * @author pzh
 * @date  2025/06/30 10:41
 **/
@Service
@DS("slave_1")
public class BizFunDeptPerfAssServiceImpl extends ServiceImpl<BizFunDeptPerfAssMapper, BizFunDeptPerfAss> implements BizFunDeptPerfAssService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private FlwCommonApi flwCommonApi;

    @Resource
    private BizFunDeptGoalService bizFunDeptGoalService;

    @Resource
    private BizCompanyGoalService bizCompanyGoalService;

    @Resource
    private BizTaskService bizTaskService;

    @Resource
    private BizDeptAssRecordsService bizDeptAssRecordsService;

    @Resource
    private BizFinanceService bizFinanceService;

    @Override
    public Page<BizFunDeptPerfAss> page(BizFunDeptPerfAssPageParam bizFunDeptPerfAssPageParam) {
        QueryWrapper<BizFunDeptPerfAss> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizFunDeptPerfAssPageParam.getSortField(), bizFunDeptPerfAssPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizFunDeptPerfAssPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizFunDeptPerfAssPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizFunDeptPerfAssPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizFunDeptPerfAss::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFunDeptPerfAssAddParam bizFunDeptPerfAssAddParam) {
        String goalId = bizFunDeptPerfAssAddParam.getGoalId();
        long count = this.count(new QueryWrapper<BizFunDeptPerfAss>().lambda().eq(BizFunDeptPerfAss::getGoalId, goalId)
                .eq(BizFunDeptPerfAss::getStatus, FlwStatusEnum.ACTIVE_STATUS.getValue()));
        if(count > 0) {
            throw new CommonException("存在审批中的绩效评估流程，不能再次发起此流程");
        }
        BizFunDeptPerfAss bizFunDeptPerfAss = BeanUtil.toBean(bizFunDeptPerfAssAddParam, BizFunDeptPerfAss.class);
        //走审批流程
        String processInstanceId = bizFunDeptPerfAssAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizFunDeptPerfAss.setId(id);
            bizFunDeptPerfAss.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizFunDeptPerfAss.setModelId(modelId);
            bizFunDeptPerfAss.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_FUN_DEPT_PERF_ASS.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizFunDeptPerfAss.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bizFunDeptPerfAss);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFunDeptPerfAssEditParam bizFunDeptPerfAssEditParam) {
        String id = bizFunDeptPerfAssEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizFunDeptPerfAssEditParam.getProcessInstanceId())){
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizFunDeptPerfAssEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizFunDeptPerfAssEditParam.setId(id);
            }
        }
        BizFunDeptPerfAss bizFunDeptPerfAss = this.queryEntity(bizFunDeptPerfAssEditParam.getId());
        BeanUtil.copyProperties(bizFunDeptPerfAssEditParam, bizFunDeptPerfAss);
        this.updateById(bizFunDeptPerfAss);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizFunDeptPerfAssIdParam> bizFunDeptPerfAssIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizFunDeptPerfAssIdParamList, BizFunDeptPerfAssIdParam::getId));
    }

    @Override
    public BizFunDeptPerfAss detail(BizFunDeptPerfAssIdParam bizFunDeptPerfAssIdParam) {
        return this.queryEntity(bizFunDeptPerfAssIdParam.getId());
    }

    @Override
    public BizFunDeptPerfAss queryEntity(String id) {
        BizFunDeptPerfAss bizFunDeptPerfAss = this.getById(id);
        if(ObjectUtil.isEmpty(bizFunDeptPerfAss)) {
            throw new CommonException("职能部门-绩效评估不存在，id值为：{}", id);
        }
        return bizFunDeptPerfAss;
    }

    @Override
    public BizFunDeptPerfAssDataVo getData(BizGoalIdParam bizGoalIdParam) {
        BizFunDeptPerfAssDataVo vo = new BizFunDeptPerfAssDataVo();
        BizFunDeptGoal bizFunDeptGoal = bizFunDeptGoalService.queryEntity(bizGoalIdParam.getId());
        // 年度考核(年度目标明细表数据)
        if(BizAssPeriodlEnum.YEAR.getValue().equals(bizFunDeptGoal.getAssType())){
            // kpi指标查询（占比60%）
            FunDeptDetailParam param = new FunDeptDetailParam();
            param.setId(bizFunDeptGoal.getId());
            List<FunDeptSummaryDetailVo> orgSummaryDetailVoList = bizFunDeptGoalService.funDeptDetail(param);
            vo.setKpiList(orgSummaryDetailVoList);
            // 预算执行查询（占比10%）
            LambdaQueryWrapper<BizFinance> qw = new LambdaQueryWrapper<BizFinance>().eq(BizFinance::getOrgId, bizFunDeptGoal.getOrgId())
                    .eq(BizFinance::getYear, bizFunDeptGoal.getAssYear()).eq(BizFinance::getMonth, 12);
            List<BizFinance> financeList = bizFinanceService.list(qw);
            BigDecimal compValueTotal = financeList.stream().findFirst().map(BizFinance::getBudgetExecRate).orElse(null);
            BigDecimal budgetExecScore = SummaryUtil.calcBudgetExecRateScore(compValueTotal, new BigDecimal(100), new BigDecimal(10));
            vo.setBudgetExecRate(compValueTotal);
            vo.setBudgetExecScore(budgetExecScore);
            // 部门履职情况查询（占比15%）
            BizTaskPageParam paramPage = new BizTaskPageParam();
            paramPage.setOrgId(bizFunDeptGoal.getOrgId());
            paramPage.setYear(bizFunDeptGoal.getAssYear().toString());
            List<DeptTaskResult> list = bizTaskService.page(paramPage);
            if(ObjectUtil.isNotEmpty(list)){
                List<BizTask> taskList = list.get(0).getTaskList();
                if(ObjectUtil.isNotEmpty(taskList)){
                    // 完成值：进度100%条数/总条数*100%
                    long finishNum = taskList.stream().filter(x -> x.getFinishStatus() >= 100).count();
                    vo.setOrgDutyFinishValue(new BigDecimal(finishNum).multiply(new BigDecimal(100)).divide(new BigDecimal(taskList.size()), 2, RoundingMode.FLOOR));
                    // 得分=∑[（进度分+成效分）*任务权重]*部门履职情况权重10%
                    BigDecimal orgDutyScore = BigDecimal.ZERO;
                    for(BizTask task : taskList){
                        BigDecimal progressScore = task.getProgressScore() == null?BigDecimal.ZERO: BigDecimal.valueOf(task.getProgressScore());
                        BigDecimal effectScore = task.getEffectScore() == null?BigDecimal.ZERO: BigDecimal.valueOf(task.getEffectScore());
                        BigDecimal weight = task.getRate() == null?BigDecimal.ZERO: BigDecimal.valueOf(task.getRate());
                        orgDutyScore = orgDutyScore.add(progressScore.add(effectScore).multiply(weight));
                    }
                    vo.setOrgDutyScore(orgDutyScore.multiply(new BigDecimal("0.15")).setScale(2, RoundingMode.FLOOR));
                }
            }
            // 集团经营业绩查询（占比15%）
            CompanySummaryListParam companySummaryListParam = new CompanySummaryListParam();
            companySummaryListParam.setAssYear(bizFunDeptGoal.getAssYear());
            List<CompanySummaryListVo> companySummaryListVoList = bizCompanyGoalService.companySummaryList(companySummaryListParam);
            if(ObjectUtil.isNotEmpty(companySummaryListVoList)){
                CompanySummaryListVo companySummaryListVo = companySummaryListVoList.get(0);
                vo.setCompanyComp(companySummaryListVo.getTotalScore());
                vo.setCompanyScore(companySummaryListVo.getTotalScore().multiply(new BigDecimal("0.15")).setScale(2, RoundingMode.FLOOR));
            }
            // 加减分记录查询
            BizDeptAssRecordsPageParam pageParam = new BizDeptAssRecordsPageParam();
            pageParam.setOrgId(bizFunDeptGoal.getOrgId());
            pageParam.setAssYear(bizFunDeptGoal.getAssYear().toString());
            IPage<DeptAssRecordVo> page = bizDeptAssRecordsService.page(pageParam);
            List<BizOrgAssRecordVo> bizOrgAssRecordVoList = BeanUtil.copyToList(page.getRecords(), BizOrgAssRecordVo.class);
            bizOrgAssRecordVoList.forEach(x -> {
                if("1".equals(x.getDictType())){
                    x.setAddScore(x.getDictScore());
                }
                if("2".equals(x.getDictType())){
                    x.setMinusScore(x.getDictScore());
                }
            });
            vo.setBizOrgAssRecordVoList(bizOrgAssRecordVoList);
        }
        // 季度考核(季度考核取对应季度最后一个月份实际完成值)
        if(BizAssPeriodlEnum.SEASON.getValue().equals(bizFunDeptGoal.getAssType())){
            // kpi指标查询（占比90%）
            FunDeptDetailParam param = new FunDeptDetailParam();
            param.setId(bizFunDeptGoal.getId());
            List<FunDeptSummaryDetailVo> orgSummaryDetailVoList = bizFunDeptGoalService.funDeptDetail(param);
            vo.setKpiList(orgSummaryDetailVoList);
            // 预算执行查询（占比10%）
            LambdaQueryWrapper<BizFinance> qw = new LambdaQueryWrapper<BizFinance>().eq(BizFinance::getOrgId, bizFunDeptGoal.getOrgId())
                    .eq(BizFinance::getYear, bizFunDeptGoal.getAssYear()).eq(BizFinance::getMonth, 12);
            List<BizFinance> financeList = bizFinanceService.list(qw);
            BigDecimal compValueTotal = financeList.stream().findFirst().map(BizFinance::getBudgetExecRate).orElse(null);
            BigDecimal budgetExecScore = SummaryUtil.calcBudgetExecRateScore(compValueTotal, new BigDecimal(100), new BigDecimal(10));
            vo.setBudgetExecRate(compValueTotal);
            vo.setBudgetExecScore(budgetExecScore);
        }
        return vo;
    }
}
