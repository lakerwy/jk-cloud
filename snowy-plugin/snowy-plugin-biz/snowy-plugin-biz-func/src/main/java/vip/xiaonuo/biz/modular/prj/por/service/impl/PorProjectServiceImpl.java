package vip.xiaonuo.biz.modular.prj.por.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
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
import java.util.ArrayList;
import java.util.List;
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
        return porProjectMapper.getDetail(porProjectIdParam.getId());
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
        return porProjectMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
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
        List<PisData> result = new ArrayList<>();
        PorProject porProject = this.queryEntity(param.getPorProjectId());
        List<HtData> htList = porProjectMapper.getHtListByPrjId(porProject.getProjectId());
        if(CollUtil.isEmpty(htList)) return result;
        List<String> htIds = htList.stream().map(x->x.getId()).collect(Collectors.toList());

        LambdaQueryWrapper<PisAllotment> pisAllotmentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pisAllotmentLambdaQueryWrapper.in(PisAllotment::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        pisAllotmentLambdaQueryWrapper.in(PisAllotment::getHtId,htIds);
        List<PisAllotment> pisAllotmentList = pisAllotmentService.list(pisAllotmentLambdaQueryWrapper);
        if(CollUtil.isEmpty(pisAllotmentList)) return result;

        LambdaQueryWrapper<Subcontract> subcontractLambdaQueryWrapper = new LambdaQueryWrapper<>();
        subcontractLambdaQueryWrapper.in(Subcontract::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        subcontractLambdaQueryWrapper.in(Subcontract::getHetongId,htIds);
        List<Subcontract> subcontractList = subcontractService.list(subcontractLambdaQueryWrapper);

        return getPisData(result, pisAllotmentList, subcontractList);
    }


    /**
     * 构造结果
     * @param result
     * @param pisAllotmentList
     * @param subcontractList
     * @return
     */
    @Override
    public List<PisData> getPisData(List<PisData> result, List<PisAllotment> pisAllotmentList, List<Subcontract> subcontractList) {
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
