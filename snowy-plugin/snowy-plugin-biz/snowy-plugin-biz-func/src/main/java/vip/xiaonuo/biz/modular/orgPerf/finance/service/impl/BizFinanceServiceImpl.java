package vip.xiaonuo.biz.modular.orgPerf.finance.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.org.entity.BizOrg;
import vip.xiaonuo.biz.modular.org.service.BizOrgService;
import vip.xiaonuo.biz.modular.orgPerf.finance.entity.BizFinance;
import vip.xiaonuo.biz.modular.orgPerf.finance.param.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.finance.mapper.BizFinanceMapper;
import vip.xiaonuo.biz.modular.orgPerf.finance.service.BizFinanceService;
import vip.xiaonuo.common.pojo.ImportResult;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 财务情况Service接口实现类
 *
 * @author pzh
 * @date  2025/06/17 11:47
 **/
@Service
@DS("slave_1")
public class BizFinanceServiceImpl extends ServiceImpl<BizFinanceMapper, BizFinance> implements BizFinanceService {

    @Resource
    private BizOrgService bizOrgService;

    @Override
    public Page<BizFinance> page(BizFinancePageParam bizFinancePageParam) {
        QueryWrapper<BizFinance> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizFinancePageParam.getSearchKey())){
            queryWrapper.lambda().like(BizFinance::getOrgName, bizFinancePageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(bizFinancePageParam.getOrgName())) {
            queryWrapper.lambda().like(BizFinance::getOrgName, bizFinancePageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(bizFinancePageParam.getOrgId())) {
            queryWrapper.lambda().eq(BizFinance::getOrgId, bizFinancePageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizFinancePageParam.getYear())) {
            queryWrapper.lambda().eq(BizFinance::getYear, bizFinancePageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(bizFinancePageParam.getMonth())) {
            queryWrapper.lambda().eq(BizFinance::getMonth, bizFinancePageParam.getMonth());
        }
        if(ObjectUtil.isAllNotEmpty(bizFinancePageParam.getSortField(), bizFinancePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizFinancePageParam.getSortOrder());
            queryWrapper.orderBy(true, bizFinancePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizFinancePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizFinance::getCreateTime);
        }
        // 校验数据范围
//        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
//        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
//            queryWrapper.lambda().in(BizFinance::getOrgId, loginUserDataScope);
//        } else {
//            queryWrapper.lambda().eq(BizFinance::getCreateUser, StpUtil.getLoginIdAsString());
//        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFinanceAddParam bizFinanceAddParam) {
        //  部门+月份+年度唯一
        long count = this.count(new LambdaQueryWrapper<BizFinance>().eq(BizFinance::getOrgId, bizFinanceAddParam.getOrgId())
                .eq(BizFinance::getYear, bizFinanceAddParam.getYear())
                .eq(BizFinance::getMonth, bizFinanceAddParam.getMonth()));
        if(count > 0){
            throw new CommonException("部门【{}】{}数据已存在！",
                    bizFinanceAddParam.getOrgName(), bizFinanceAddParam.getYear()+"年"+bizFinanceAddParam.getMonth()+"月");
        }
        BizFinance bizFinance = BeanUtil.toBean(bizFinanceAddParam, BizFinance.class);
        this.save(bizFinance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFinanceEditParam bizFinanceEditParam) {
        //  部门+月份+年度唯一
        long count = this.count(new LambdaQueryWrapper<BizFinance>().eq(BizFinance::getOrgId, bizFinanceEditParam.getOrgId())
                .eq(BizFinance::getYear, bizFinanceEditParam.getYear())
                .eq(BizFinance::getMonth, bizFinanceEditParam.getMonth())
                .ne(BizFinance::getId, bizFinanceEditParam.getId()));
        if(count > 0){
            throw new CommonException("部门【{}】{}数据已存在！",
                    bizFinanceEditParam.getOrgName(), bizFinanceEditParam.getYear()+"年"+bizFinanceEditParam.getMonth()+"月");
        }
        BizFinance bizFinance = this.queryEntity(bizFinanceEditParam.getId());
        BeanUtil.copyProperties(bizFinanceEditParam, bizFinance);
        this.updateById(bizFinance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizFinanceIdParam> bizFinanceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizFinanceIdParamList, BizFinanceIdParam::getId));
    }

    @Override
    public BizFinance detail(BizFinanceIdParam bizFinanceIdParam) {
        return this.queryEntity(bizFinanceIdParam.getId());
    }

    @Override
    public BizFinance queryEntity(String id) {
        BizFinance bizFinance = this.getById(id);
        if(ObjectUtil.isEmpty(bizFinance)) {
            throw new CommonException("财务情况不存在，id值为：{}", id);
        }
        return bizFinance;
    }

    @Override
    public ImportResult doImport(ImportFinanceParam importParam) {
        List<ImportFinanceData> importData = importParam.getImportData();
        if(CollUtil.isEmpty(importData)){
            throw new CommonException("无有效导入数据！");
        }
        // 校验格式与必填
        importData.forEach(item->{
            String errMsg = "";
            if(ObjectUtil.isEmpty(item.getOrgName())){
                errMsg += "组织名称必填；";
            }
            if(ObjectUtil.isEmpty(item.getYear())){
                errMsg += "年份必填；";
            }
            if(ObjectUtil.isEmpty(item.getMonth())){
                errMsg += "月份必填；";
            }
//            if(item.getRecAmountAcc() == null){
//                errMsg += "累计回款额必填；";
//            }
//            if(item.getProfitAmountAcc() == null){
//                errMsg += "累计净利润必填；";
//            }
            if(item.getBudgetExecRate() == null){
                errMsg += "预算执行率（成本）必填；";
            }
            item.setErrorMsg(errMsg);
        });
        // 校验Excel内数据是否重复
        List<ImportFinanceData> checkRepeatData = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).collect(Collectors.toList());
        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item -> {
            List<ImportFinanceData> tmpList = checkRepeatData.stream().filter(obj -> item.getOrgName().equals(obj.getOrgName())
                    && item.getYear().equals(obj.getYear()) && item.getMonth().equals(obj.getMonth())).collect(Collectors.toList());
            if(tmpList.size() > 1){
                item.setErrorMsg("导入数据中，组织名称+年份+月份同时唯一存在重复；");
            }
        });
        // 匹配组织ID
        List<BizOrg> orgList = bizOrgService.getAllOrgList();
        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
            List<BizOrg> matchedOrgs = orgList.stream().filter(org -> org.getName().equals(item.getOrgName())).collect(Collectors.toList());
            if(CollUtil.isEmpty(matchedOrgs)){
                item.setErrorMsg(String.format("部门【%s】在系统中未找到", item.getOrgName()));
            }else if(matchedOrgs.size() > 1){
                item.setErrorMsg(String.format("部门【%s】在系统中匹配到多个", item.getOrgName()));
            }else if(matchedOrgs.size() == 1){
                // 匹配到唯一的ORG
                item.setOrgId(matchedOrgs.get(0).getId());
            }
        });
        // 校验数据是否已存在
        List<BizFinance> oldList = this.list();
        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
            List<BizFinance> tmpList = oldList.stream().filter(obj -> item.getOrgId().equals(obj.getOrgId())
                    && item.getYear().equals(obj.getYear()) && item.getMonth().equals(obj.getMonth())).collect(Collectors.toList());
            if(!tmpList.isEmpty()){
                item.setErrorMsg(String.format("部门【%s】%s数据已存在；", item.getOrgName(), item.getYear()+"年"+item.getMonth()+"月"));
            }
        });
        //  导入
        ImportResult result = new ImportResult();
        List<ImportFinanceData> correctData = importData.stream().filter(x -> ObjectUtil.isEmpty(x.getErrorMsg())).collect(Collectors.toList());
        result.setTotal(importData.size());
        result.setErrCount(result.getTotal() - correctData.size());
        result.setSucCount(correctData.size());
        result.setResultList(importData);
        List<BizFinance> addList = BeanUtil.copyToList(correctData, BizFinance.class);
        addList.forEach(x->x.setNote(DateUtil.now()+"：页面导入；"));
        this.saveBatch(addList);
        return result;
    }
}
