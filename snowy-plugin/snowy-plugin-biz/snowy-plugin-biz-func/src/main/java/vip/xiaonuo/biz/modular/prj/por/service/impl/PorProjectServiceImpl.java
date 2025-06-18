package vip.xiaonuo.biz.modular.prj.por.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.crm.parameters.entity.BizCrmRecPlan;
import vip.xiaonuo.biz.modular.crm.parameters.service.BizCrmRecPlanService;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.Subcontract;
import vip.xiaonuo.biz.modular.crm.subcontract.service.SubcontractService;
import vip.xiaonuo.biz.modular.invoice.vo.InvoiceSaleBillVo;
import vip.xiaonuo.biz.modular.org.service.BizOrgService;
import vip.xiaonuo.biz.modular.pis.entity.PisAllotment;
import vip.xiaonuo.biz.modular.pis.service.PisAllotmentService;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataPpc;
import vip.xiaonuo.biz.modular.prj.por.entity.*;
import vip.xiaonuo.biz.modular.prj.por.mapper.*;
import vip.xiaonuo.biz.modular.prj.por.param.*;
import vip.xiaonuo.biz.modular.prj.por.service.*;
import vip.xiaonuo.biz.modular.prj.por.vo.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 项目产值台账 - 项目管理Service接口实现类
 *
 * @author yy
 * @date  2025/06/03 09:39
 **/
@Service
@DS("slave_1")
public class PorProjectServiceImpl extends ServiceImpl<PorProjectMapper, PorProject> implements PorProjectService {

    @Resource
    private PorReportService porReportService;

    @Resource
    private PorTaskService porTaskService;

    @Resource
    private PorMemberService porMemberService;

    @Resource
    private PorWarnService porWarnService;

    @Resource
    private PisAllotmentService pisAllotmentService;

    @Resource
    private SubcontractService subcontractService;

    @Resource
    private BizOrgService bizOrgService;

    @Resource
    private BizCrmRecPlanService bizCrmRecPlanService;

    @Resource
    private PorProjectMapper porProjectMapper;

    @Resource
    private PorOwnerSettleMapper porOwnerSettleMapper;

    @Resource
    private PorSubSettleMapper porSubSettleMapper;

    @Resource
    private PorRpmMapper porRpmMapper;

    @Resource
    private PorSignMapper porSignMapper;

    @Resource
    private PorSiteFeeMapper porSiteFeeMapper;

    @Resource
    private PorOemMapper porOemMapper;

    @Override
    public Page<PorProjectVo> page(PorProjectPageParam porProjectPageParam) {
        QueryWrapper<PorProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(porProjectPageParam.getProjectId())) {
            queryWrapper.eq("a.PROJECT_ID", porProjectPageParam.getProjectId());
        }
        if(ObjectUtil.isNotEmpty(porProjectPageParam.getProjectStatus())) {
            queryWrapper.eq("a.PROJECT_STATUS", porProjectPageParam.getProjectStatus());
        }
        if(ObjectUtil.isAllNotEmpty(porProjectPageParam.getSortField(), porProjectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porProjectPageParam.getSortOrder());
            queryWrapper.orderBy(true, porProjectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porProjectPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return porProjectMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PorProjectAddParam porProjectAddParam) {
        PorProject porProject = BeanUtil.toBean(porProjectAddParam, PorProject.class);
        this.save(porProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PorProjectEditParam porProjectEditParam) {
        PorProject porProject = this.queryEntity(porProjectEditParam.getId());
        BeanUtil.copyProperties(porProjectEditParam, porProject);
        this.updateById(porProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PorProjectIdParam> porProjectIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(porProjectIdParamList, PorProjectIdParam::getId));
    }

    @Override
    public PorProjectVo detail(PorProjectIdParam porProjectIdParam) {
        PorProjectVo result = porProjectMapper.getDetail(porProjectIdParam.getId());
        List<PorProjectVo> list = new ArrayList<>();
        list.add(result);
        return getPorProjectData(list).get(0);
    }

    @Override
    public PorProject queryEntity(String id) {
        PorProject porProject = this.getById(id);
        if(ObjectUtil.isEmpty(porProject)) {
            throw new CommonException("项目产值台账 - 项目管理不存在，id值为：{}", id);
        }
        return porProject;
    }

    @Override
    public Page<PorProjectVo> pageList(PorProjectPageParam porProjectPageParam) {
        QueryWrapper<PorProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(porProjectPageParam.getProjectId())) {
            queryWrapper.eq("a.PROJECT_ID", porProjectPageParam.getProjectId());
        }
        if(ObjectUtil.isNotEmpty(porProjectPageParam.getProjectStatus())) {
            queryWrapper.eq("a.PROJECT_STATUS", porProjectPageParam.getProjectStatus());
        }
        if(ObjectUtil.isNotEmpty(porProjectPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("b.NAME",porProjectPageParam.getSearchKey()));
        }

        // 校验数据范围
        // 责任人
        String userId = StpUtil.getLoginIdAsString();
        // 所属部门
        String dutyDeptId = StpLoginUserUtil.getLoginUser().getOrgId();
        // 所属部门级以下
        List<String> dutyDeptList = bizOrgService.getChildListById(bizOrgService.getAllOrgList(), dutyDeptId, true).stream().map(x->x.getId()).collect(Collectors.toList());
        // 接口数据权限设置
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();

        /**
         * 公司领导层-查看所有项目数据
         * 项目责任人-查看、编辑自己责任项目
         * 责任部门负责人-查看归属部门的项目数据
         * 项目主要成员-查看参与项目数据
         */
        queryWrapper.and(q->{
            q.eq("a.DUTY_ID",userId);   //责任人
            q.or().apply("a.ID in (select member.POR_PROJECT_ID from BIZ_POR_MEMBER member where member.DELETE_FLAG='NOT_DELETE' and member.USER_ID = {0}) ",userId); //项目成员
            if(ObjectUtil.isNotEmpty(loginUserDataScope)){
                q.or().in("a.DUTY_DEPT_ID",loginUserDataScope); // 权限设置
            }
            if(CollUtil.isNotEmpty(dutyDeptList)){
                q.or().in("a.DUTY_DEPT_ID",dutyDeptList); //责任部门及以下
            }
        });

        if(ObjectUtil.isAllNotEmpty(porProjectPageParam.getSortField(), porProjectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porProjectPageParam.getSortOrder());
            queryWrapper.orderBy(true, porProjectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porProjectPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        Page<PorProjectVo> result = porProjectMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
        result.setRecords(getPorProjectData(result.getRecords()));
        return result;
    }

    @Override
    public List<HtData> getHtListByPrjId(PorProjectParam param) {
        PorProject porProject = this.queryEntity(param.getPorProjectId());
        return porProjectMapper.getHtListByPrjId(porProject.getProjectId());
    }

    @Override
    public List<RecData> getRecListByPrjId(PorProjectParam param) {
        List<RecData> result = new ArrayList<>();
        PorProject porProject = this.queryEntity(param.getPorProjectId());
        List<HtData> htList = porProjectMapper.getHtListByPrjId(porProject.getProjectId());
        for (HtData ht : htList) {
            RecData porRecData = new RecData();
            porRecData.setHtId(ht.getId());
            porRecData.setHtProjectName(ht.getProjectName());
            porRecData.setHtContractNo(ht.getContractNo());
            porRecData.setProcessInstanceId(ht.getProcessInstanceId());
            porRecData.setStatus(ht.getStatus());
            LambdaQueryWrapper<BizCrmRecPlan> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BizCrmRecPlan::getHetongId,ht.getId());
            List<BizCrmRecPlan> recPlanList = bizCrmRecPlanService.list(queryWrapper);
            porRecData.setPlanList(recPlanList);
            result.add(porRecData);
        }
        return result;
    }

    @Override
    public List<PorOwnerSettleVo> getOwnerListByPrjId(PorProjectParam param) {
        PorProject porProject = this.queryEntity(param.getPorProjectId());
        return porOwnerSettleMapper.getOwnerSettleListById(porProject.getProjectId());
    }

    @Override
    public List<FinDataPpc> getPpcListByPrjId(PorProjectParam param) {
        PorProject porProject = this.queryEntity(param.getPorProjectId());
        return porProjectMapper.getPpcListByPrjId(porProject.getProjectId());
    }

    @Override
    public List<Subcontract> getSubListByPrjId(PorProjectParam param) {
        PorProject porProject = this.queryEntity(param.getPorProjectId());
        return porProjectMapper.getSubListByPrjId(porProject.getProjectId());
    }

    @Override
    public List<PorReport> getReportListByPrjId(PorProjectParam param) {
        LambdaQueryWrapper<PorReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PorReport::getPorProjectId,param.getPorProjectId());
        if(ObjectUtil.isNotEmpty(param.getReportType())){
            queryWrapper.eq(PorReport::getReportType,param.getReportType());
        }
        return porReportService.list(queryWrapper);
    }

    @Override
    public List<PorTask> getTaskListByPrjId(PorProjectParam param) {
        LambdaQueryWrapper<PorTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PorTask::getPorProjectId,param.getPorProjectId());
        return porTaskService.list(queryWrapper);
    }

    @Override
    public List<PorSubSettleVo> getSubSettleListByPrjId(PorProjectParam param) {
        PorProject porProject = this.queryEntity(param.getPorProjectId());
        return porSubSettleMapper.getSubSettleListByPrjId(porProject.getProjectId());
    }

    @Override
    public List<PisData> getPisListByPrjId(PorProjectParam param) {
        PorProject porProject = this.queryEntity(param.getPorProjectId());
        List<HtData> htList = porProjectMapper.getHtListByPrjId(porProject.getProjectId());
        List<String> htIds = htList.stream().map(x->x.getId()).collect(Collectors.toList());
        return getPisData(htIds,null);
    }



    /**
     * 构造结果[项目拨付-按照分包合同维度加工]
     * @param htIds      收入合同id集合
     * @param natureList 分包合同款项性质集合
     * @return
     */
    @Override
    public List<PisData> getPisData(List<String> htIds, List<String> natureList) {
        List<PisData> result = new ArrayList<>();
        if(CollUtil.isEmpty(htIds)) return result;

        LambdaQueryWrapper<PisAllotment> pisAllotmentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pisAllotmentLambdaQueryWrapper.in(PisAllotment::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        pisAllotmentLambdaQueryWrapper.in(PisAllotment::getHtId,htIds);
        List<PisAllotment> pisAllotmentList = pisAllotmentService.list(pisAllotmentLambdaQueryWrapper);
        if(CollUtil.isEmpty(pisAllotmentList)) return result;

        LambdaQueryWrapper<Subcontract> subcontractLambdaQueryWrapper = new LambdaQueryWrapper<>();
        subcontractLambdaQueryWrapper.in(Subcontract::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        subcontractLambdaQueryWrapper.in(Subcontract::getHetongId,htIds);
        if(CollUtil.isNotEmpty(natureList)){
            subcontractLambdaQueryWrapper.in(Subcontract::getExpenseNature,natureList.toArray(new String[0]));
        }
        List<Subcontract> subcontractList = subcontractService.list(subcontractLambdaQueryWrapper);

        //构建结果
        for (PisAllotment pis : pisAllotmentList) {
            List<PisData> hJsonList = JSONUtil.toList(pis.getHJson(), PisData.class);
            for (PisData hJson : hJsonList) {
                PisData tmpObj = BeanUtil.copyProperties(hJson, PisData.class);
                Subcontract tmpSubcontract = subcontractList.stream().filter(x -> x.getContractNo().equals(hJson.getSubcontractNumber())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(tmpSubcontract)){
                    tmpObj.setSn(pis.getSn());
                    tmpObj.setProcessInstanceId(pis.getProcessInstanceId());
                    tmpObj.setStatus(pis.getStatus());
                    tmpObj.setHtId(pis.getHtId());
                    tmpObj.setHtProjectName(pis.getHtProjectName());
                    tmpObj.setHtContractNo(pis.getHtContractNo());
                    tmpObj.setSubId(tmpSubcontract.getId());
                    tmpObj.setSubProjectName(tmpSubcontract.getProjectName());
                    tmpObj.setSubContractNo(tmpSubcontract.getContractNo());
                    tmpObj.setSubContractAmount(tmpSubcontract.getContractAmount());
                    result.add(tmpObj);
                }
            }
        }
        return result;
    }

    /**
     * 计算百分比：(part / total) × 100%
     * @param part
     * @param total
     * @return
     */
    public Double calculatePercentage(BigDecimal part, BigDecimal total) {
        if (total == null || total.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        return part.divide(total, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 计算金额合计
     * @param dataList
     * @return
     */
    public BigDecimal calculateSum(List<String> dataList){
        BigDecimal sumTotal = BigDecimal.ZERO;
        for (String s : dataList) {
            BigDecimal tmpValue = BigDecimal.ZERO;
            try {
                tmpValue = new BigDecimal(s);
            }catch (Exception e){

            }
            sumTotal = sumTotal.add(tmpValue);
        }
        return sumTotal;
    }

    /**
     * 统计信息[补充统计信息]
     * @param dataList
     * @return
     */
    public List<PorProjectVo> getPorProjectData(List<PorProjectVo> dataList){
        for (PorProjectVo prj : dataList) {
            //基础信息
            List<String> prjTypeList = Arrays.asList(prj.getBizPrjType().replace("，", ",").split(","));

            List<HtData> htData = porProjectMapper.getHtListByPrjId(prj.getProjectId());
            List<String> htIds = htData.stream().map(x->x.getId()).collect(Collectors.toList());
            //[收入合同 - 审批通过]
            List<HtData> htList = htData.stream().filter(x->FlwStatusEnum.COMPLETED_STATUS.getValue().equals(x.getStatus())).collect(Collectors.toList());

            //1. [收入合同]
            BigDecimal prjScaleSum = calculateSum(htList.stream().filter(x->ObjectUtil.isNotEmpty(x.getInvestScale())).map(x->x.getInvestScale()).collect(Collectors.toList()));
            BigDecimal prjHtSum = htList.stream().map(x -> x.getSumAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            Long prjHtNum = htList.stream().count();
            BigDecimal prjJkSum = htList.stream().map(x -> x.getContractAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            Double prjJkRate = calculatePercentage(prjJkSum,prjHtSum);

            //2. [应收款金额 ]
            BigDecimal prjYsSum = BigDecimal.ZERO;
            if(prjTypeList.contains("施工") || prjTypeList.contains("EPC") || prjTypeList.contains("PPP") || prjTypeList.contains("EPC+F")){
                List<PorOwnerSettleVo> ownerSettleData = porOwnerSettleMapper.getOwnerSettleListById(prj.getProjectId());
                prjYsSum = ownerSettleData.stream().map(x -> x.getSettleAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            }else{
                prjYsSum = prjHtSum;
            }

            List<FinDataPpc> ppcData = porProjectMapper.getPpcListByPrjId(prj.getProjectId());

            BigDecimal prjHsSum = ppcData.stream().map(x -> x.getHkAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            Double prjHsRate = calculatePercentage(prjHsSum,prjYsSum);
            BigDecimal prjDsSum = prjYsSum.subtract(prjHsSum);
            Double prjDsRate = 1-prjHsRate;


            //10. [项目回款进度]
            Double prjRcmRate = calculatePercentage(prjHsSum,prjHtSum);

            //11. [项目回款进度缺口]
            BigDecimal recTotal = BigDecimal.ZERO;
            if(CollUtil.isNotEmpty(htList)) {
                LambdaQueryWrapper<BizCrmRecPlan> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.in(BizCrmRecPlan::getHetongId,htList.stream().map(x->x.getId()).collect(Collectors.toList()));
                queryWrapper.lt(BizCrmRecPlan::getPlannedRecDate,DateTime.now());
                queryWrapper.isNotNull(BizCrmRecPlan::getPlannedRecAmount);
                List<BizCrmRecPlan> tmpRecList = bizCrmRecPlanService.list(queryWrapper);
                recTotal = tmpRecList.stream().map(x -> x.getPlannedRecAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            Double prjRcmLostRate = calculatePercentage(recTotal.subtract(prjHsSum),prjHtSum);


            //3. [分包合同]
            List<Subcontract> subContractData = porProjectMapper.getSubListByPrjId(prj.getProjectId());
            //[分包合同 - 审批通过]
            List<Subcontract> subcontractList = subContractData.stream().filter(x->FlwStatusEnum.COMPLETED_STATUS.getValue().equals(x.getStatus())).collect(Collectors.toList());

            BigDecimal prjSubSum = subcontractList.stream().map(x->x.getContractAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            Long prjSubNum = subcontractList.stream().count();
            BigDecimal prjYfSum = prjSubSum;
            List<PisData> pisData = getPisData(htIds,null);
            //[拨付- 审批通过]
            List<PisData> pisDataList = pisData.stream().filter(x -> FlwStatusEnum.COMPLETED_STATUS.getValue().equals(x.getStatus())).collect(Collectors.toList());
            BigDecimal prjHfSum = pisDataList.stream().map(x->x.getPayable()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            Double prjHfRate = calculatePercentage(prjHfSum,prjYfSum);

            //4. [责任上缴费用]
            List<PorRpmVo> rpmData = porRpmMapper.getRpmListByPrjId(prj.getProjectId());
            BigDecimal prjRpmSum = rpmData.stream().map(x->x.getRpmTotalAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

            //5. [经营费]
            List<PorOemVo> oemData = porOemMapper.getOemListByPrjId(prj.getProjectId());
            List<PisData> oemPisData = getPisData(htIds, CollectionUtil.toList("经营费","居间费"));
            //[拨付- 审批通过]
            List<PisData> oemPisDataList = oemPisData.stream().filter(x -> FlwStatusEnum.COMPLETED_STATUS.getValue().equals(x.getStatus())).collect(Collectors.toList());
            BigDecimal prjOemYfSum = oemData.stream().map(x->x.getOemTotalAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal prjOemHfSum = oemPisDataList.stream().map(x->x.getPayable()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            Double prjOemHfRate = calculatePercentage(prjOemHfSum,prjOemYfSum);

            //6. [签字费]
            List<PorSignVo> signData = porSignMapper.getSignListByPrjId(prj.getProjectId());
            BigDecimal prjSignYfSum = signData.stream().map(x->x.getSignTotalAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal prjSignHfSum = pisDataList.stream().map(x->x.getDeductSignature()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            Double prjSignHfRate = calculatePercentage(prjSignHfSum,prjSignYfSum);

            //7. [分包]
            BigDecimal prjSubYfSum = BigDecimal.ZERO;
            if(prjTypeList.contains("施工") || prjTypeList.contains("EPC") || prjTypeList.contains("PPP") || prjTypeList.contains("EPC+F")) {
                List<PorSubSettleVo> subSettleData = porSubSettleMapper.getSubSettleListByPrjId(prj.getProjectId());
                List<PorSubSettleVo> subSettleDataList = subSettleData.stream().filter(x->CollectionUtil.toList("分包","其他").contains(x.getSubContractExpenseNature())).collect(Collectors.toList());
                prjSubYfSum = subSettleDataList.stream().map(x -> x.getReviewAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            }else{
                List<Subcontract> subSettleSubContractList = subcontractList.stream().filter(x->CollectionUtil.toList("分包","其他").contains(x.getExpenseNature())).collect(Collectors.toList());
                prjSubYfSum = subSettleSubContractList.stream().map(x -> x.getContractAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            List<PisData> subSettlePisData = getPisData(htIds, CollectionUtil.toList("分包","其他"));
            List<PisData> subSettlePisDataList = subSettlePisData.stream().filter(x -> FlwStatusEnum.COMPLETED_STATUS.getValue().equals(x.getStatus())).collect(Collectors.toList());
            BigDecimal prjSubHfSum = subSettlePisDataList.stream().map(x -> x.getPayable()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            Double prjSubHfRate = calculatePercentage(prjSubHfSum,prjSubYfSum);

            //8. [现场管理费]
            List<PorSiteFeeVo> siteData = porSiteFeeMapper.getSiteListByPrjId(prj.getProjectId());
            BigDecimal prjSiteSum = siteData.stream().map(x->x.getFeeMonthTotal()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

            //9. [税费]
            List<InvoiceSaleBillVo> invoiceBillData = porProjectMapper.getInvoiceBillListByPrjId(prj.getProjectId());
            BigDecimal prjTaxSum = invoiceBillData.stream().map(x->x.getTaxAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

            //10. [周报|日报 最新]
            PorReport tmpPorReport = porReportService.getOne(new LambdaQueryWrapper<PorReport>().eq(PorReport::getPorProjectId,prj.getId()).orderByDesc(PorReport::getCreateTime).last("limit 1"));

            prj.setReportRate(tmpPorReport.getReportRate());
            prj.setReportPreEndDate(tmpPorReport.getReportPreEndDate());
            prj.setPrjScaleSum(prjScaleSum);
            prj.setPrjHtSum(prjHtSum);
            prj.setPrjHtNum(prjHtNum);
            prj.setPrjJkSum(prjJkSum);
            prj.setPrjJkRate(prjJkRate);
            prj.setPrjYsSum(prjYsSum);
            prj.setPrjHsSum(prjHsSum);
            prj.setPrjHsRate(prjHsRate);
            prj.setPrjDsSum(prjDsSum);
            prj.setPrjDsRate(prjDsRate);
            prj.setPrjSubSum(prjSubSum);
            prj.setPrjSubNum(prjSubNum);
            prj.setPrjYfSum(prjYfSum);
            prj.setPrjHfSum(prjHfSum);
            prj.setPrjHfRate(prjHfRate);
            prj.setPrjRpmSum(prjRpmSum);
            prj.setPrjSignYfSum(prjSignYfSum);
            prj.setPrjSignHfSum(prjSignHfSum);
            prj.setPrjSignHfRate(prjSignHfRate);
            prj.setPrjSubYfSum(prjSubYfSum);
            prj.setPrjSubHfSum(prjSubHfSum);
            prj.setPrjSubHfRate(prjSubHfRate);
            prj.setPrjOemYfSum(prjOemYfSum);
            prj.setPrjOemHfSum(prjOemHfSum);
            prj.setPrjOemHfRate(prjOemHfRate);
            prj.setPrjSiteSum(prjSiteSum);
            prj.setPrjTaxSum(prjTaxSum);
            prj.setPrjRcmRate(prjRcmRate);
            prj.setPrjRcmLostRate(prjRcmLostRate);
        }
        return dataList;
    }


    @Override
    public List<PorRpmVo> getRpmListByPrjId(PorProjectParam param) {
        PorProject porProject = this.queryEntity(param.getPorProjectId());
        return porRpmMapper.getRpmListByPrjId(porProject.getProjectId());
    }

    @Override
    public List<PorSignVo> getSignListByPrjId(PorProjectParam param) {
        PorProject porProject = this.queryEntity(param.getPorProjectId());
        return porSignMapper.getSignListByPrjId(porProject.getProjectId());
    }

    @Override
    public List<PorSiteFeeVo> getSiteListByPrjId(PorProjectParam param) {
        PorProject porProject = this.queryEntity(param.getPorProjectId());
        return porSiteFeeMapper.getSiteListByPrjId(porProject.getProjectId());
    }

    @Override
    public List<PorMember> getMemberListByPrjId(PorPrjParam param) {
        LambdaQueryWrapper<PorMember> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PorMember::getPorProjectId,param.getPorProjectId());
        return porMemberService.list(queryWrapper);
    }

    @Override
    public List<PorWarn> getWarnListByPrjId(PorPrjParam param) {
        LambdaQueryWrapper<PorWarn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PorWarn::getPorProjectId,param.getPorProjectId());
        return porWarnService.list(queryWrapper);
    }

    @Override
    public void updateRec(RecParam param) {
        BizCrmRecPlan bizCrmRecPlan = bizCrmRecPlanService.getById(param.getId());
        bizCrmRecPlan.setRecStatus(param.getRecStatus());
        bizCrmRecPlanService.updateById(bizCrmRecPlan);
    }

    @Override
    public void updatePorWarn(RecParam param) {
        PorWarn porWarn = porWarnService.getById(param.getId());
        porWarn.setDealStatus(porWarn.getDealStatus());
        porWarn.setDealTime(DateTime.now());
        porWarnService.updateById(porWarn);
    }
}
