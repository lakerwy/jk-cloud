package vip.xiaonuo.biz.modular.orgPerf.business.service.impl;

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
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.org.entity.BizOrg;
import vip.xiaonuo.biz.modular.org.service.BizOrgService;
import vip.xiaonuo.biz.modular.orgPerf.business.param.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.business.entity.BizBusiness;
import vip.xiaonuo.biz.modular.orgPerf.business.mapper.BizBusinessMapper;
import vip.xiaonuo.biz.modular.orgPerf.business.service.BizBusinessService;
import vip.xiaonuo.common.pojo.ImportResult;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务情况Service接口实现类
 *
 * @author pzh
 * @date  2025/06/17 14:48
 **/
@Service
@DS("slave_1")
public class BizBusinessServiceImpl extends ServiceImpl<BizBusinessMapper, BizBusiness> implements BizBusinessService {

    @Resource
    private BizOrgService bizOrgService;

    @Override
    public Page<BizBusiness> page(BizBusinessPageParam bizBusinessPageParam) {
        QueryWrapper<BizBusiness> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizBusinessPageParam.getSearchKey())){
            queryWrapper.lambda().like(BizBusiness::getOrgName, bizBusinessPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(bizBusinessPageParam.getOrgName())) {
            queryWrapper.lambda().like(BizBusiness::getOrgName, bizBusinessPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(bizBusinessPageParam.getOrgId())) {
            queryWrapper.lambda().eq(BizBusiness::getOrgId, bizBusinessPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizBusinessPageParam.getYear())) {
            queryWrapper.lambda().eq(BizBusiness::getYear, bizBusinessPageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(bizBusinessPageParam.getMonth())) {
            queryWrapper.lambda().eq(BizBusiness::getMonth, bizBusinessPageParam.getMonth());
        }
        if(ObjectUtil.isAllNotEmpty(bizBusinessPageParam.getSortField(), bizBusinessPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizBusinessPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizBusinessPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizBusinessPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizBusiness::getCreateTime);
        }
        // 校验数据范围
//        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
//        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
//            queryWrapper.lambda().in(BizBusiness::getOrgId, loginUserDataScope);
//        } else {
//            queryWrapper.lambda().eq(BizBusiness::getCreateUser, StpUtil.getLoginIdAsString());
//        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizBusinessAddParam bizBusinessAddParam) {
        //  部门+月份+年度唯一
        long count = this.count(new LambdaQueryWrapper<BizBusiness>().eq(BizBusiness::getOrgId, bizBusinessAddParam.getOrgId())
                .eq(BizBusiness::getYear, bizBusinessAddParam.getYear())
                .eq(BizBusiness::getMonth, bizBusinessAddParam.getMonth()));
        if(count > 0){
            throw new CommonException("部门【{}】{}数据已存在！",
                    bizBusinessAddParam.getOrgName(), bizBusinessAddParam.getYear()+"年"+bizBusinessAddParam.getMonth()+"月");
        }
        BizBusiness bizBusiness = BeanUtil.toBean(bizBusinessAddParam, BizBusiness.class);
        this.save(bizBusiness);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizBusinessEditParam bizBusinessEditParam) {
        //  部门+月份+年度唯一
        long count = this.count(new LambdaQueryWrapper<BizBusiness>().eq(BizBusiness::getOrgId, bizBusinessEditParam.getOrgId())
                .eq(BizBusiness::getYear, bizBusinessEditParam.getYear())
                .eq(BizBusiness::getMonth, bizBusinessEditParam.getMonth())
                .ne(BizBusiness::getId, bizBusinessEditParam.getId()));
        if(count > 0){
            throw new CommonException("部门【{}】{}数据已存在！",
                    bizBusinessEditParam.getOrgName(), bizBusinessEditParam.getYear()+"年"+bizBusinessEditParam.getMonth()+"月");
        }
        BizBusiness bizBusiness = this.queryEntity(bizBusinessEditParam.getId());
        BeanUtil.copyProperties(bizBusinessEditParam, bizBusiness);
        this.updateById(bizBusiness);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizBusinessIdParam> bizBusinessIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizBusinessIdParamList, BizBusinessIdParam::getId));
    }

    @Override
    public BizBusiness detail(BizBusinessIdParam bizBusinessIdParam) {
        return this.queryEntity(bizBusinessIdParam.getId());
    }

    @Override
    public BizBusiness queryEntity(String id) {
        BizBusiness bizBusiness = this.getById(id);
        if(ObjectUtil.isEmpty(bizBusiness)) {
            throw new CommonException("业务情况不存在，id值为：{}", id);
        }
        return bizBusiness;
    }

    @Override
    public ImportResult doImport(ImportBusinessParam importParam) {
        List<ImportBusinessData> importData = importParam.getImportData();
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
//            if(item.getNewContractAmount() == null){
//                errMsg += "累计新签合同额必填；";
//            }
            item.setErrorMsg(errMsg);
        });
        List<ImportBusinessData> checkRepeatData = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).collect(Collectors.toList());
        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item -> {
            List<ImportBusinessData> tmpList = checkRepeatData.stream().filter(obj -> item.getOrgName().equals(obj.getOrgName())
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

        List<BizBusiness> oldList = this.list();
        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
            List<BizBusiness> tmpList = oldList.stream().filter(obj -> item.getOrgId().equals(obj.getOrgId())
                    && item.getYear().equals(obj.getYear()) && item.getMonth().equals(obj.getMonth())).collect(Collectors.toList());
            if(!tmpList.isEmpty()){
                item.setErrorMsg(String.format("部门【%s】%s数据已存在；", item.getOrgName(), item.getYear()+"年"+item.getMonth()+"月"));
            }
        });
        //  导入
        ImportResult result = new ImportResult();
        List<ImportBusinessData> correctData = importData.stream().filter(x -> ObjectUtil.isEmpty(x.getErrorMsg())).collect(Collectors.toList());
        result.setTotal(importData.size());
        result.setErrCount(result.getTotal() - correctData.size());
        result.setSucCount(correctData.size());
        result.setResultList(importData);
        List<BizBusiness> addList = BeanUtil.copyToList(correctData, BizBusiness.class);
        addList.forEach(x->x.setNote(DateUtil.now()+"：页面导入；"));
        this.saveBatch(addList);
        return result;
    }
}
