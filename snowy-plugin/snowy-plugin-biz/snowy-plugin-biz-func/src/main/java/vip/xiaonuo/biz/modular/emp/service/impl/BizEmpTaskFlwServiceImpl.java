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
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
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
import vip.xiaonuo.biz.modular.emp.service.BizEmpAssListService;
import vip.xiaonuo.biz.modular.emp.service.BizEmpPerformanceService;
import vip.xiaonuo.biz.modular.emp.service.BizEmpTaskService;
import vip.xiaonuo.biz.modular.emp.vo.BizEmpTaskFlwVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpTaskFlw;
import vip.xiaonuo.biz.modular.emp.mapper.BizEmpTaskFlwMapper;
import vip.xiaonuo.biz.modular.emp.param.BizEmpTaskFlwAddParam;
import vip.xiaonuo.biz.modular.emp.param.BizEmpTaskFlwEditParam;
import vip.xiaonuo.biz.modular.emp.param.BizEmpTaskFlwIdParam;
import vip.xiaonuo.biz.modular.emp.param.BizEmpTaskFlwPageParam;
import vip.xiaonuo.biz.modular.emp.service.BizEmpTaskFlwService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
                    .eq(BizEmpTask::getUserId,empTaskFlw.getUserId()));

            bizEmpPerformanceService.remove(new QueryWrapper<BizEmpPerformance>().lambda()
                    .eq(BizEmpPerformance::getYear,empTaskFlw.getYear())
                    .eq(BizEmpPerformance::getSeason,empTaskFlw.getSeason())
                    .eq(BizEmpPerformance::getUserId,empTaskFlw.getUserId()));

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

    @Override
    public IPage<BizEmpTaskFlwVo> pageList(BizEmpTaskFlwPageParam bizEmpTaskFlwPageParam) {
        if(ObjectUtil.isEmpty(bizEmpTaskFlwPageParam.getYear()) || ObjectUtil.isEmpty(bizEmpTaskFlwPageParam.getSeason())){
            throw new CommonException("考核年份、季度参数必填");
        }
        //筛选业务数据
        QueryWrapper<BizEmpTaskFlw> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.in("a.STATUS", FlwStatusEnum.ACTIVE_STATUS.getValue(),FlwStatusEnum.COMPLETED_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getUserId())) {
            queryWrapper.eq("a.USER_ID", bizEmpTaskFlwPageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getOrgId())) {
            queryWrapper.eq("a.ORG_ID", bizEmpTaskFlwPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getYear())) {
            queryWrapper.eq("a.YEAR", bizEmpTaskFlwPageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getSeason())) {
            queryWrapper.eq("a.SEASON", bizEmpTaskFlwPageParam.getSeason());
        }
        if(ObjectUtil.isAllNotEmpty(bizEmpTaskFlwPageParam.getSortField(), bizEmpTaskFlwPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizEmpTaskFlwPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizEmpTaskFlwPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizEmpTaskFlwPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.YEAR").orderByDesc("a.SEASON").orderByDesc("a.ORG_ID").orderByAsc("a.USER_ID");
        }
        List<BizEmpTaskFlwVo> result = bizEmpTaskFlwMapper.getList(queryWrapper);
        return completeResult(result,bizEmpTaskFlwPageParam);
    }


    /***
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
     * @param result       员工绩效目标任务记录查询 结果
     * @param bizEmpTaskFlwPageParam  查询参数
     * @return
     */
    public Page<BizEmpTaskFlwVo> completeResult(List<BizEmpTaskFlwVo> result,BizEmpTaskFlwPageParam bizEmpTaskFlwPageParam){
        //最终返回结果
        Page<BizEmpTaskFlwVo> pageVo = new Page<>();
        List<BizEmpTaskFlwVo> newResult = new ArrayList<>();

        //统计年度
        String year = bizEmpTaskFlwPageParam.getYear();
        //统计季度
        String season = bizEmpTaskFlwPageParam.getSeason();

        //需要补充的组织架构人员
        QueryWrapper<BizEmpAssList> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getOrgId())){
            queryWrapper.lambda().eq(BizEmpAssList::getOrgId,bizEmpTaskFlwPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getUserId())){
            queryWrapper.lambda().eq(BizEmpAssList::getUserId,bizEmpTaskFlwPageParam.getUserId());
        }

        //若筛选（已填报|未填报）
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getIsFill())){
            //已经填报过的员工id集合
            List<String> haveFillUserIds = result.stream().map(x->x.getUserId()).distinct().collect(Collectors.toList());
            //若没有填报记录，则置为空
            if(CollUtil.isEmpty(haveFillUserIds)) haveFillUserIds = Arrays.asList("");
            //筛选是否填报
            if("1".equals(bizEmpTaskFlwPageParam.getIsFill())){
                queryWrapper.lambda().in(BizEmpAssList::getUserId,haveFillUserIds);
            }else{
                queryWrapper.lambda().notIn(BizEmpAssList::getUserId,haveFillUserIds);
            }
        }

        //若筛选流程审批状态
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getStatus())){
            //根据流程状态筛选 员工id集合
            List<String> statusUserIds = result.stream().filter(x->x.getStatus().equals(bizEmpTaskFlwPageParam.getStatus())).map(x->x.getUserId()).distinct().collect(Collectors.toList());
            if(CollUtil.isEmpty(statusUserIds)) statusUserIds = Arrays.asList("");
            queryWrapper.lambda().in(BizEmpAssList::getUserId,statusUserIds);
        }

        //关键字
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getSearchKey())){
            queryWrapper.and(q->q.lambda().like(BizEmpAssList::getUserName,bizEmpTaskFlwPageParam.getSearchKey())
                    .like(BizEmpAssList::getOrgName,bizEmpTaskFlwPageParam.getSearchKey()));
        }

        // 校验数据范围（由于本列表是根据组织架构决定的）
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(BizEmpAssList::getOrgId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(BizEmpAssList::getUserId, StpUtil.getLoginIdAsString());
        }
        queryWrapper.lambda().orderByAsc(BizEmpAssList::getOrgId).orderByAsc(BizEmpAssList::getUserId);

        //构造分页
        long current = ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getCurrent())?bizEmpTaskFlwPageParam.getCurrent():1;
        long size =  ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getSize())?bizEmpTaskFlwPageParam.getSize():Integer.MAX_VALUE;
        Page<BizEmpAssList> objectPage = new Page<>(current, size);

        //查询考核人员列表
        Page<BizEmpAssList> needUserList = bizEmpAssListService.page(objectPage,queryWrapper);

        //构造每个人的年度绩效数据（组织架构中每个人均有4条本年度四个季度的目标填报记录）
        for (BizEmpAssList user:needUserList.getRecords()) {
            //判断 当前年 当前季度 当前员工  是否存在记录，若不存在，则生产一条记录
            Optional<BizEmpTaskFlwVo> first = result.stream().filter(x -> x.getUserId().equals(user.getId())
                    && x.getYear().equals(year) && x.getSeason().equals(season)).findFirst();
            if(first.isPresent()){
                BizEmpTaskFlwVo bizEmpTaskFlwVo = first.get();
                bizEmpTaskFlwVo.setOrgName(user.getOrgName());
                newResult.add(bizEmpTaskFlwVo);
            }else{
                BizEmpTaskFlwVo addVo = new BizEmpTaskFlwVo();
                addVo.setYear(year);
                addVo.setSeason(season);
                addVo.setUserId(user.getId());
                addVo.setOrgId(user.getOrgId());
                addVo.setUserName(user.getUserName());
                addVo.setOrgName(user.getOrgName());
                newResult.add(addVo);
           }
        }
        //构造返回体
        pageVo.setTotal(needUserList.getTotal());
        pageVo.setRecords(newResult);
        pageVo.setPages(needUserList.getPages());
        pageVo.setCurrent(current);
        pageVo.setSize(size);
        return pageVo;
    }
}
