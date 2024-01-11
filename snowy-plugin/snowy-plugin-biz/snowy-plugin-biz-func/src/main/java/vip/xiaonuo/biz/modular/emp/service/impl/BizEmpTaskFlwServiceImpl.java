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
package vip.xiaonuo.biz.modular.emp.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpAssList;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpPerformance;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpTask;
import vip.xiaonuo.biz.modular.emp.param.*;
import vip.xiaonuo.biz.modular.emp.service.BizEmpAssListService;
import vip.xiaonuo.biz.modular.emp.service.BizEmpPerformanceService;
import vip.xiaonuo.biz.modular.emp.service.BizEmpTaskService;
import vip.xiaonuo.biz.modular.emp.vo.BizEmpAssTotalVo;
import vip.xiaonuo.biz.modular.emp.vo.BizEmpAssVo;
import vip.xiaonuo.biz.modular.emp.vo.BizEmpTaskFlwVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpTaskFlw;
import vip.xiaonuo.biz.modular.emp.mapper.BizEmpTaskFlwMapper;
import vip.xiaonuo.biz.modular.emp.service.BizEmpTaskFlwService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 业务表-员工绩效考核-季度任务流程审批单Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/19 08:48
 **/


@DS("slave_1")
@Service
public class BizEmpTaskFlwServiceImpl extends ServiceImpl<BizEmpTaskFlwMapper, BizEmpTaskFlw> implements BizEmpTaskFlwService {
    @Resource
    private BizEmpTaskFlwMapper bizEmpTaskFlwMapper;

    @Resource
    private BizEmpTaskService bizEmpTaskService;

    @Resource
    private BizEmpPerformanceService bizEmpPerformanceService;

    @Resource
    private BizEmpAssListService bizEmpAssListService;

    @Override
    public Page<BizEmpTaskFlw> page(BizEmpTaskFlwPageParam bizEmpTaskFlwPageParam) {
        QueryWrapper<BizEmpTaskFlw> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getStatus())) {
            queryWrapper.lambda().eq(BizEmpTaskFlw::getStatus, bizEmpTaskFlwPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(bizEmpTaskFlwPageParam.getSortField(), bizEmpTaskFlwPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizEmpTaskFlwPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizEmpTaskFlwPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizEmpTaskFlwPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizEmpTaskFlw::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizEmpTaskFlwAddParam bizEmpTaskFlwAddParam) {
        BizEmpTaskFlw bizEmpTaskFlw = BeanUtil.toBean(bizEmpTaskFlwAddParam, BizEmpTaskFlw.class);
        this.save(bizEmpTaskFlw);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizEmpTaskFlwEditParam bizEmpTaskFlwEditParam) {
        BizEmpTaskFlw bizEmpTaskFlw = this.queryEntity(bizEmpTaskFlwEditParam.getId());
        BeanUtil.copyProperties(bizEmpTaskFlwEditParam, bizEmpTaskFlw);
        this.updateById(bizEmpTaskFlw);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizEmpTaskFlwIdParam> bizEmpTaskFlwIdParamList) {
        // 执行删除（逻辑，删除 绩效目标流程记录，同时删除 BizEmpTask绩效目标任务记录 ,删除逻辑(已有绩效评分的流程不能删除)）
        List<String> ids = CollStreamUtil.toList(bizEmpTaskFlwIdParamList, BizEmpTaskFlwIdParam::getId);
        List<BizEmpTaskFlw> bizEmpTaskFlwList = this.list(new QueryWrapper<BizEmpTaskFlw>().lambda().in(BizEmpTaskFlw::getId,ids));
        for (BizEmpTaskFlw empTaskFlw : bizEmpTaskFlwList) {
            bizEmpTaskService.remove(new QueryWrapper<BizEmpTask>().lambda()
                    .eq(BizEmpTask::getYear,empTaskFlw.getYear())
                    .eq(BizEmpTask::getSeason,empTaskFlw.getSeason())
                    .eq(BizEmpTask::getUserId,empTaskFlw.getUserId())
                    .eq(BizEmpTask::getOrgId,empTaskFlw.getOrgId()));

            bizEmpPerformanceService.remove(new QueryWrapper<BizEmpPerformance>().lambda()
                    .eq(BizEmpPerformance::getYear,empTaskFlw.getYear())
                    .eq(BizEmpPerformance::getSeason,empTaskFlw.getSeason())
                    .eq(BizEmpPerformance::getUserId,empTaskFlw.getUserId())
                    .eq(BizEmpPerformance::getOrgId,empTaskFlw.getOrgId()));

            this.removeById(empTaskFlw);
        }
    }

    @Override
    public BizEmpTaskFlw detail(BizEmpTaskFlwIdParam bizEmpTaskFlwIdParam) {
        return this.queryEntity(bizEmpTaskFlwIdParam.getId());
    }

    @Override
    public BizEmpTaskFlw queryEntity(String id) {
        BizEmpTaskFlw bizEmpTaskFlw = this.getById(id);
        if(ObjectUtil.isEmpty(bizEmpTaskFlw)) {
            throw new CommonException("业务表-员工绩效考核-季度任务流程审批单不存在，id值为：{}", id);
        }
        return bizEmpTaskFlw;
    }

    /**
     * 员工绩效查询页面，数据构造（必须输入年度、季度）
     *
     * 根据需求：需要展示组织架构全员，每年，每季度的目标信息，就算未提交也要有一条记录展示
     *
     * 例如： 姓名  年份  季度   是否填报
     *       A    2023  1       是
     *       A    2023  2       否
     *       A    2023  3       是
     *       A    2023  4       否
     *       B    2023  1       是
     *       B    2023  2       是
     *       B    2023  3       是
     *       B    2023  4       否
     *
     ***/
    @Override
    public IPage<BizEmpTaskFlwVo> pageList(BizEmpTaskFlwPageParam bizEmpTaskFlwPageParam) {
        if(ObjectUtil.isEmpty(bizEmpTaskFlwPageParam.getYear()) || ObjectUtil.isEmpty(bizEmpTaskFlwPageParam.getSeason())){
            throw new CommonException("考核年份、季度参数必填");
        }
        //筛选业务数据
        QueryWrapper<BizEmpTaskFlw> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("emp.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getUserId())) {
            queryWrapper.eq("emp.USER_ID", bizEmpTaskFlwPageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getOrgId())) {
            queryWrapper.eq("emp.ORG_ID", bizEmpTaskFlwPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getYear())) {
            queryWrapper.eq("emp.YEAR", bizEmpTaskFlwPageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getSeason())) {
            queryWrapper.eq("emp.SEASON", bizEmpTaskFlwPageParam.getSeason());
        }
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getFirstOrgId())){
            queryWrapper.eq("emp.FIRST_ORG_ID",bizEmpTaskFlwPageParam.getFirstOrgId());
        }
        //若筛选（已填报|未填报）
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getIsFill())){
            //筛选是否填报
            if("1".equals(bizEmpTaskFlwPageParam.getIsFill())){
                queryWrapper.isNotNull("a.ID");
            }else{
                queryWrapper.isNull("a.ID");
            }
        }
        //若筛选流程审批状态
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getStatus())){
            queryWrapper.eq("a.STATUS",bizEmpTaskFlwPageParam.getStatus());
        }
        //关键字
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("emp.USER_NAME",bizEmpTaskFlwPageParam.getSearchKey())
                    .or().like("emp.ORG_NAME",bizEmpTaskFlwPageParam.getSearchKey())
                    .or().like("emp.FIRST_ORG_NAME",bizEmpTaskFlwPageParam.getSearchKey()));
        }
        // 校验数据范围（由于本列表是根据组织架构决定的）
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.in("emp.ORG_ID", loginUserDataScope);
        } else {
            queryWrapper.eq("emp.USER_ID", StpUtil.getLoginIdAsString());
        }
        if(ObjectUtil.isAllNotEmpty(bizEmpTaskFlwPageParam.getSortField(), bizEmpTaskFlwPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizEmpTaskFlwPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizEmpTaskFlwPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizEmpTaskFlwPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("emp.YEAR").orderByDesc("emp.SEASON").orderByDesc("emp.ORG_ID").orderByDesc("b.EMP_SEASON_SCORE");
        }
        return bizEmpTaskFlwMapper.getList(CommonPageRequest.defaultPage(),queryWrapper);
    }

    @Override
    public BizEmpAssTotalVo total(EmpAssTotalParam empAssTotalParam) {
        // 数据权限 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(loginUserDataScope.size() < 1) return null;

        //权限组织
        List<String> dataScopeOrgIds = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            dataScopeOrgIds = loginUserDataScope;
        } else {
            dataScopeOrgIds = Arrays.asList(StpLoginUserUtil.getLoginUser().getOrgId());
        }
        //统计年度
        String year = empAssTotalParam.getYear();
        //统计季度
        String season = empAssTotalParam.getSeason();
        //统计 年度+季度+组织+考核等级
        BizEmpAssTotalVo bizEmpAssTotalVo = new BizEmpAssTotalVo();
        bizEmpAssTotalVo.setYear(year);
        bizEmpAssTotalVo.setSeason(season);
        //考核 名单
        List<BizEmpAssList> assListList = bizEmpAssListService.list(new QueryWrapper<BizEmpAssList>().lambda()
                .eq(BizEmpAssList::getYear,year)
                .eq(BizEmpAssList::getSeason,season)
                .in(BizEmpAssList::getOrgId,dataScopeOrgIds));
        //考核 记录信息
        List<BizEmpPerformance> assEmpPerformanceList = bizEmpPerformanceService.list(new QueryWrapper<BizEmpPerformance>().lambda()
                .eq(BizEmpPerformance::getYear,year)
                .eq(BizEmpPerformance::getSeason,season)
                .in(BizEmpPerformance::getOrgId,dataScopeOrgIds)
                .in(BizEmpPerformance::getStatus,FlwStatusEnum.ACTIVE_STATUS.getValue(),FlwStatusEnum.COMPLETED_STATUS.getValue()));
        //统计 各年 各季度 各等级
        List<String> assLevels = assEmpPerformanceList.stream()
                .filter(x->ObjectUtil.isNotEmpty(x.getEmpSeasonLevel()))
                .map(x->x.getEmpSeasonLevel()).distinct().collect(Collectors.toList());
        //该部门参加考核人数
        Integer empAssNums = assListList.size();
        bizEmpAssTotalVo.setEmpAssNums(empAssNums);
        //循环
        List<BizEmpAssVo> empAssVoList = new ArrayList<>();
        for (String level : assLevels){
            BizEmpAssVo empAssVo = new BizEmpAssVo();
            empAssVo.setEmpSeasonLevel(level);
            empAssVo.setEmpSeasonLevelNums(assEmpPerformanceList.stream().filter(x-> ObjectUtil.isNotEmpty(x.getEmpSeasonLevel()) && x.getEmpSeasonLevel().equals(level)).collect(Collectors.toList()).size());
            empAssVoList.add(empAssVo);
        }
        bizEmpAssTotalVo.setEmpAssVoList(empAssVoList);
        return bizEmpAssTotalVo;
    }
}
