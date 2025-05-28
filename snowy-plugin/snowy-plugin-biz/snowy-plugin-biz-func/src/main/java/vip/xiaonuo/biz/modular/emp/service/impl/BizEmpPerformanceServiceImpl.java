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
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpAssList;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpTask;
import vip.xiaonuo.biz.modular.emp.mapper.BizEmpAssListMapper;
import vip.xiaonuo.biz.modular.emp.param.*;
import vip.xiaonuo.biz.modular.emp.service.BizEmpTaskService;
import vip.xiaonuo.biz.modular.emp.vo.BizEmpAssListVo;
import vip.xiaonuo.biz.modular.emp.vo.BizEmpPerformanceVo;
import vip.xiaonuo.biz.modular.prj.exp.vo.ExpMonthTotal;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpPerformance;
import vip.xiaonuo.biz.modular.emp.mapper.BizEmpPerformanceMapper;
import vip.xiaonuo.biz.modular.emp.service.BizEmpPerformanceService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.*;


/**
 * 业务表-员工绩效考核-季度评分审批流程Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/19 08:45
 **/

@DS("slave_1")
@Service
public class BizEmpPerformanceServiceImpl extends ServiceImpl<BizEmpPerformanceMapper, BizEmpPerformance> implements BizEmpPerformanceService {
    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private BizEmpTaskService bizEmpTaskService;

    @Resource
    private BizEmpAssListMapper bizEmpAssListMapper;

    @Override
    public Page<BizEmpPerformance> page(BizEmpPerformancePageParam bizEmpPerformancePageParam) {
        QueryWrapper<BizEmpPerformance> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getSn())) {
            queryWrapper.lambda().like(BizEmpPerformance::getSn, bizEmpPerformancePageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getUserId())) {
            queryWrapper.lambda().eq(BizEmpPerformance::getUserId, bizEmpPerformancePageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getUserName())) {
            queryWrapper.lambda().like(BizEmpPerformance::getUserName, bizEmpPerformancePageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getOrgId())) {
            queryWrapper.lambda().eq(BizEmpPerformance::getOrgId, bizEmpPerformancePageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getYear())) {
            queryWrapper.lambda().eq(BizEmpPerformance::getYear, bizEmpPerformancePageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getSeason())) {
            queryWrapper.lambda().eq(BizEmpPerformance::getSeason, bizEmpPerformancePageParam.getSeason());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getEmpSeasonLevel())) {
            queryWrapper.lambda().eq(BizEmpPerformance::getEmpSeasonLevel, bizEmpPerformancePageParam.getEmpSeasonLevel());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getStatus())) {
            queryWrapper.lambda().eq(BizEmpPerformance::getStatus, bizEmpPerformancePageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(bizEmpPerformancePageParam.getSortField(), bizEmpPerformancePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizEmpPerformancePageParam.getSortOrder());
            queryWrapper.orderBy(true, bizEmpPerformancePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizEmpPerformancePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizEmpPerformance::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizEmpPerformanceAddParam bizEmpPerformanceAddParam) {
        BizEmpPerformance bizEmpPerformance = BeanUtil.toBean(bizEmpPerformanceAddParam, BizEmpPerformance.class);
        //走合同审批流程
        String processInstanceId = bizEmpPerformanceAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizEmpPerformance.setId(id);
            bizEmpPerformance.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizEmpPerformance.setModelId(modelId);
            bizEmpPerformance.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_EMP_PERFORMANCE.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizEmpPerformance.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bizEmpPerformance);
        //反写流程中的任务信息
        try{
            List<EmpTaskParam> paramList = JSONUtil.toList(bizEmpPerformanceAddParam.getEmpTaskJson(), EmpTaskParam.class);
            List<BizEmpTask> bizEmpTaskList = BeanUtil.copyToList(paramList,BizEmpTask.class);
            bizEmpTaskService.updateBatchById(bizEmpTaskList);
        }catch (Exception ex){
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizEmpPerformanceEditParam bizEmpPerformanceEditParam) {
        String id = bizEmpPerformanceEditParam.getId();
        //判断是否走合同审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizEmpPerformanceEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizEmpPerformanceEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizEmpPerformanceEditParam.setId(id);
            }
        }
        BizEmpPerformance bizEmpPerformance = this.queryEntity(bizEmpPerformanceEditParam.getId());
        BeanUtil.copyProperties(bizEmpPerformanceEditParam, bizEmpPerformance);
        this.updateById(bizEmpPerformance);
        //反写流程中的任务信息
        try{
            List<EmpTaskParam> paramList = JSONUtil.toList(bizEmpPerformanceEditParam.getEmpTaskJson(), EmpTaskParam.class);
            List<BizEmpTask> bizEmpTaskList = BeanUtil.copyToList(paramList,BizEmpTask.class);
            bizEmpTaskService.updateBatchById(bizEmpTaskList);
        }catch (Exception ex){
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizEmpPerformanceIdParam> bizEmpPerformanceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizEmpPerformanceIdParamList, BizEmpPerformanceIdParam::getId));
    }

    @Override
    public BizEmpPerformance detail(BizEmpPerformanceIdParam bizEmpPerformanceIdParam) {
        return this.queryEntity(bizEmpPerformanceIdParam.getId());
    }

    @Override
    public BizEmpPerformance queryEntity(String id) {
        BizEmpPerformance bizEmpPerformance = this.getById(id);
        if(ObjectUtil.isEmpty(bizEmpPerformance)) {
            throw new CommonException("业务表-员工绩效考核-季度评分审批流程不存在，id值为：{}", id);
        }
        return bizEmpPerformance;
    }

    @Override
    public Boolean check(EmpCheckParam empCheckParam) {
        QueryWrapper<BizEmpPerformance> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BizEmpPerformance::getUserId,empCheckParam.getUserId())
                .eq(BizEmpPerformance::getOrgId,empCheckParam.getOrgId())
                .eq(BizEmpPerformance::getYear,empCheckParam.getYear())
                .eq(BizEmpPerformance::getSeason,empCheckParam.getSeason())
                .in(BizEmpPerformance::getStatus,FlwStatusEnum.ACTIVE_STATUS.getValue(),FlwStatusEnum.COMPLETED_STATUS.getValue());
        return this.list(queryWrapper).size() > 0;
    }


    /***
     * 员工绩效统计表查询页面，数据构造
     *
     * 根据需求：需要展示组织架构全员，每年，四个季度的考评，没有考评显示空
     *
     * 例如： 姓名  年份  一季度  二季度  三季度  四季度  是否填报
     *       A    2023  10      20
     *       B    2023  40      12     32
     *
     ***/
    @Override
    public Page<BizEmpPerformanceVo> pageList(BizEmpPerformancePageParam bizEmpPerformancePageParam) {
        if(ObjectUtil.isEmpty(bizEmpPerformancePageParam.getYear())){
            throw new CommonException("考核年度参数必填");
        }
        /* 考核人员列表 */
        QueryWrapper<BizEmpAssList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BizEmpAssList::getDeleteFlag,"NOT_DELETE");
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getYear())) {
            queryWrapper.lambda().eq(BizEmpAssList::getYear, bizEmpPerformancePageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getOrgId())){
            queryWrapper.lambda().eq(BizEmpAssList::getOrgId,bizEmpPerformancePageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getUserId())){
            queryWrapper.lambda().eq(BizEmpAssList::getUserId,bizEmpPerformancePageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getFirstOrgId())){
            queryWrapper.lambda().eq(BizEmpAssList::getFirstOrgId,bizEmpPerformancePageParam.getFirstOrgId());
        }
        //关键字查询
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getSearchKey())){
            queryWrapper.and(q->q.lambda().like(BizEmpAssList::getUserName,bizEmpPerformancePageParam.getSearchKey())
                    .or().like(BizEmpAssList::getOrgName,bizEmpPerformancePageParam.getSearchKey())
                    .or().like(BizEmpAssList::getFirstOrgName,bizEmpPerformancePageParam.getSearchKey()));
        }
        // 校验数据范围（由于本列表是根据组织架构决定的）
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(BizEmpAssList::getOrgId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(BizEmpAssList::getUserId, StpUtil.getLoginIdAsString());
        }
        IPage<BizEmpAssListVo> result = bizEmpAssListMapper.getPageList(CommonPageRequest.defaultPage(),queryWrapper);


        //处理结果（获取绩效评估分记录）
        Page<BizEmpPerformanceVo> pageVo = new Page<>();
        List<BizEmpPerformanceVo> newResult = new ArrayList<>();
        //查询条件
        QueryWrapper<BizEmpPerformance> queryWrapper1 = new QueryWrapper<>();
        //只有 审批中、审批通过 才有效
        queryWrapper1.lambda().in(BizEmpPerformance::getStatus,FlwStatusEnum.ACTIVE_STATUS.getValue(),FlwStatusEnum.COMPLETED_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getUserId())) {
            queryWrapper1.lambda().eq(BizEmpPerformance::getUserId, bizEmpPerformancePageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getOrgId())) {
            queryWrapper1.lambda().eq(BizEmpPerformance::getOrgId, bizEmpPerformancePageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getYear())) {
            queryWrapper1.lambda().eq(BizEmpPerformance::getYear, bizEmpPerformancePageParam.getYear());
        }
        List<BizEmpPerformance> bizEmpPerformanceList = this.list(queryWrapper1);

        //季度
        List<String> seasons = Arrays.asList("1","2","3","4");
        //构造每个人的年度绩效数据
        for (BizEmpAssListVo empAss:result.getRecords()) {
            BizEmpPerformanceVo performanceVo = new BizEmpPerformanceVo();
            performanceVo.setYear(empAss.getYear());
            performanceVo.setOrgId(empAss.getOrgId());
            performanceVo.setOrgName(empAss.getOrgName());
            performanceVo.setUserId(empAss.getUserId());
            performanceVo.setUserName(empAss.getUserName());
            performanceVo.setFirstOrgId(empAss.getFirstOrgId());
            performanceVo.setFirstOrgName(empAss.getFirstOrgName());
            //循环四个季度
            for (String season:seasons)
            {
                Optional<BizEmpPerformance> first = bizEmpPerformanceList.stream()
                        .filter(x -> x.getYear().equals(empAss.getYear()) && x.getSeason().equals(season)
                        && x.getUserId().equals(empAss.getUserId()) && x.getOrgId().equals(empAss.getOrgId())).findFirst();
                if(season.equals("1")){
                    performanceVo.setSeasonLevel1(first.isPresent() ? first.get().getEmpSeasonLevel() : null);
                    performanceVo.setSeasonScore1(first.isPresent() ? first.get().getEmpSeasonScore() : null);
                }
                if(season.equals("2")){
                    performanceVo.setSeasonLevel2(first.isPresent() ? first.get().getEmpSeasonLevel() : null);
                    performanceVo.setSeasonScore2(first.isPresent() ? first.get().getEmpSeasonScore() : null);
                }
                if(season.equals("3")){
                    performanceVo.setSeasonLevel3(first.isPresent() ? first.get().getEmpSeasonLevel() : null);
                    performanceVo.setSeasonScore3(first.isPresent() ? first.get().getEmpSeasonScore() : null);
                }
                if(season.equals("4")){
                    performanceVo.setSeasonLevel4(first.isPresent() ? first.get().getEmpSeasonLevel() : null);
                    performanceVo.setSeasonScore4(first.isPresent() ? first.get().getEmpSeasonScore() : null);
                }
            }
            newResult.add(performanceVo);
        }
        newResult.sort(Comparator.comparing(BizEmpPerformanceVo::getYear,Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(BizEmpPerformanceVo::getFirstOrgName,Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(BizEmpPerformanceVo::getUserName,Comparator.nullsLast(Comparator.naturalOrder())));
        pageVo.setCurrent(result.getCurrent());
        pageVo.setSize(result.getSize());
        pageVo.setPages(result.getPages());
        pageVo.setTotal(result.getTotal());
        pageVo.setRecords(newResult);
        return pageVo;
    }

    @Override
    public void updateCol(EmpAssUpdateParam empAssUpdateParam) {
        //主键Id
        BizEmpPerformance bizEmpPerformance = this.queryEntity(empAssUpdateParam.getEpId());
        String colName = empAssUpdateParam.getColName().toLowerCase();
        //员工季度评级
        if("empseasonlevel".equals(colName)){
            bizEmpPerformance.setEmpSeasonLevel(empAssUpdateParam.getColValue());
        }
        this.updateById(bizEmpPerformance);
    }
}
