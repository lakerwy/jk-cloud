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

import cn.dev33.satoken.stp.StpUtil;
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
import vip.xiaonuo.biz.modular.task.entity.BizDeptAss;
import vip.xiaonuo.biz.modular.task.entity.BizDeptAssSynergy;
import vip.xiaonuo.biz.modular.task.entity.BizGroupPerformance;
import vip.xiaonuo.biz.modular.task.mapper.BizDeptAssMapper;
import vip.xiaonuo.biz.modular.task.param.*;
import vip.xiaonuo.biz.modular.task.service.*;
import vip.xiaonuo.biz.modular.task.vo.BizSysOrgVo;
import vip.xiaonuo.biz.modular.task.vo.DeptAssReportVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;


import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 业务表-部门绩效考核-考核表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/09 08:38
 **/
@DS("slave_1")
@Service
public class BizDeptAssServiceImpl extends ServiceImpl<BizDeptAssMapper, BizDeptAss> implements BizDeptAssService {

    @Resource
    private SysOrgService sysOrgService;

    @Resource
    private BizTaskService bizTaskService;

    @Resource
    private BizGroupPerformanceService bizGroupPerformanceService;

    @Resource
    private BizDeptAssSynergyService bizDeptAssSynergyService;

    @Override
    public Page<BizDeptAss> page(BizDeptAssPageParam bizDeptAssPageParam) {
        QueryWrapper<BizDeptAss> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizDeptAssPageParam.getAssYear())) {
            queryWrapper.lambda().eq(BizDeptAss::getAssYear, bizDeptAssPageParam.getAssYear());
        }
        if(ObjectUtil.isNotEmpty(bizDeptAssPageParam.getOrgId())) {
            queryWrapper.lambda().eq(BizDeptAss::getOrgId, bizDeptAssPageParam.getOrgId());
        }
        if(ObjectUtil.isAllNotEmpty(bizDeptAssPageParam.getSortField(), bizDeptAssPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizDeptAssPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizDeptAssPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizDeptAssPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizDeptAss::getAssYear);
        }
        Page<BizDeptAss> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<BizSysOrgVo> orgList = sysOrgService.getOrgList();
        for (BizDeptAss item:result.getRecords()) {
            Optional<BizSysOrgVo> org = orgList.stream().filter(x->x.getOrgId().equals(item.getOrgId())).findFirst();
            String orgName = org.isPresent() ? org.get().getOrgName() : "";
            item.setOrgName(orgName);
            item.setTotalScore(item.getAddScore()-item.getMinusScore());
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizDeptAssAddParam bizDeptAssAddParam) {
        //同一个年度同一个部门只能存在一条有效数据
        BizDeptAss dbBizDeptAss = this.getOne(new QueryWrapper<BizDeptAss>().lambda()
                .eq(BizDeptAss::getAssYear,bizDeptAssAddParam.getAssYear())
                .eq(BizDeptAss::getOrgId,bizDeptAssAddParam.getOrgId()));
        if(ObjectUtil.isNotEmpty(dbBizDeptAss)){
            throw new CommonException("当前部门已存在{}年度数据",bizDeptAssAddParam.getAssYear());
        }
        BizDeptAss bizDeptAss = BeanUtil.toBean(bizDeptAssAddParam, BizDeptAss.class);
        this.save(bizDeptAss);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizDeptAssEditParam bizDeptAssEditParam) {
        //同一个年度同一个部门只能存在一条有效数据
        BizDeptAss dbBizDeptAss = this.getOne(new QueryWrapper<BizDeptAss>().lambda()
                .eq(BizDeptAss::getAssYear,bizDeptAssEditParam.getAssYear())
                .eq(BizDeptAss::getOrgId,bizDeptAssEditParam.getOrgId())
                .ne(BizDeptAss::getId,bizDeptAssEditParam.getId()));
        if(ObjectUtil.isNotEmpty(dbBizDeptAss)){
            throw new CommonException("当前部门已存在{}年度数据",bizDeptAssEditParam.getAssYear());
        }
        BizDeptAss bizDeptAss = this.queryEntity(bizDeptAssEditParam.getId());
        BeanUtil.copyProperties(bizDeptAssEditParam, bizDeptAss);
        this.updateById(bizDeptAss);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizDeptAssIdParam> bizDeptAssIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizDeptAssIdParamList, BizDeptAssIdParam::getId));
    }

    @Override
    public BizDeptAss detail(BizDeptAssIdParam bizDeptAssIdParam) {
        BizDeptAss result = this.queryEntity(bizDeptAssIdParam.getId());
        BizSysOrgVo org = sysOrgService.getOrgById(result.getOrgId());
        result.setOrgName(org.getOrgName());
        result.setTotalScore(result.getAddScore()-result.getMinusScore());
        return result;
    }

    @Override
    public BizDeptAss queryEntity(String id) {
        BizDeptAss bizDeptAss = this.getById(id);
        if(ObjectUtil.isEmpty(bizDeptAss)) {
            throw new CommonException("业务表-部门绩效考核-考核表不存在，id值为：{}", id);
        }
        return bizDeptAss;
    }

    @Override
    public List<DeptAssReportVo> yearReport(DeptAssReportParam deptAssReportParam) {
        //各个部门年度任务分
        List<DeptAssReportVo> ndTask = bizTaskService.getDeptTaskScore(deptAssReportParam.getAssYear());
        //各个部门年度考核分（加减分）
        List<BizDeptAss> ndAssess = this.list(new QueryWrapper<BizDeptAss>().lambda().eq(BizDeptAss::getAssYear,deptAssReportParam.getAssYear()));
        //部门年度任务 涉及的部门
        List<String> taskOrgIds = ndTask.stream().map(DeptAssReportVo::getOrgId).collect(Collectors.toList());
        //构造结果集
        List<DeptAssReportVo> result = ndTask;
//        for (BizDeptAss item:ndAssess) {
//            if(!taskOrgIds.contains(item.getOrgId())){
//                DeptAssReportVo reportVo = new DeptAssReportVo();
//                reportVo.setAssYear(deptAssReportParam.getAssYear());
//                reportVo.setOrgId(item.getOrgId());
//                reportVo.setKhScore(item.getAddScore()-item.getMinusScore());
//                result.add(reportVo);
//            }
//        }

        //获取携带信息
        List<BizSysOrgVo> orgList = sysOrgService.getOrgList();  //部门字典列表
        //集团经营业绩分(一年一条)
        BizGroupPerformance bizGroupPerformance = bizGroupPerformanceService.getOne(new QueryWrapper<BizGroupPerformance>().lambda().eq(BizGroupPerformance::getAssYear,deptAssReportParam.getAssYear()));
        //各部门年度协同分
        List<BizDeptAssSynergy> bizDeptAssSynergyList = bizDeptAssSynergyService.list(new QueryWrapper<BizDeptAssSynergy>().lambda().eq(BizDeptAssSynergy::getAssYear,deptAssReportParam.getAssYear()));
        //拼接结果 校验数据范围（由于本列表是根据组织架构决定的）
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(CollUtil.isNotEmpty(loginUserDataScope)){
            result = result.stream().filter(x->loginUserDataScope.contains(x.getOrgId())).collect(Collectors.toList());
        }else {
            if (StrUtil.isNotEmpty(deptAssReportParam.getOrgId())) {
                result = result.stream().filter(x -> x.getOrgId().equals(deptAssReportParam.getOrgId())).collect(Collectors.toList());
            }
        }
        result.forEach(item->{
            //部门名称
            Optional<BizSysOrgVo> org = orgList.stream().filter(x->x.getOrgId().equals(item.getOrgId())).findFirst();
            if(org.isPresent()) {
                item.setOrgName(org.get().getOrgName());
            }
            //集团经营业绩得分（15%）
            if(ObjectUtil.isNotEmpty(bizGroupPerformance)){
                item.setJtScore(bizGroupPerformance.getGroupScore());
            }
            //部门协同分(5%)
            Optional<BizDeptAssSynergy> bizDeptAssSynergy = bizDeptAssSynergyList.stream().filter(x->x.getOrgId().equals(item.getOrgId())).findFirst();
            if(bizDeptAssSynergy.isPresent()){
                item.setXtScore(bizDeptAssSynergy.get().getSynergyScore());
            }
            //部门综合分（加减分）
            Optional<BizDeptAss> tempAssess = ndAssess.stream().filter(x->x.getOrgId().equals(item.getOrgId())).findFirst();
            if (tempAssess.isPresent()){
               item.setKhScore(tempAssess.get().getAddScore()-tempAssess.get().getMinusScore());
            }
            //合计总分
            double totalScore = (ObjectUtil.isNotEmpty(item.getJtScore())?item.getJtScore():0)
                               +(ObjectUtil.isNotEmpty(item.getNdScore())?item.getNdScore():0)
                               +(ObjectUtil.isNotEmpty(item.getXtScore())?item.getXtScore():0)
                               +(ObjectUtil.isNotEmpty(item.getKhScore())?item.getKhScore():0);
            item.setTotalScore(totalScore);
        });
        return CollUtil.sort(result, new Comparator<DeptAssReportVo>() {
            @Override
            public int compare(DeptAssReportVo o1, DeptAssReportVo o2) {
                return o2.getTotalScore().compareTo(o1.getTotalScore());
            }
        });
    }
}
