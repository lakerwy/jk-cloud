package vip.xiaonuo.biz.modular.orgPerf.product.service.impl;

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
import vip.xiaonuo.biz.modular.orgPerf.product.param.*;
import vip.xiaonuo.biz.modular.orgPerf.product.service.BizProductService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.product.entity.BizProduct;
import vip.xiaonuo.biz.modular.orgPerf.product.mapper.BizProductMapper;
import vip.xiaonuo.common.pojo.ImportResult;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 产值情况Service接口实现类
 *
 * @author pzh
 * @date  2025/06/17 09:03
 **/
@Service
@DS("slave_1")
public class BizProductServiceImpl extends ServiceImpl<BizProductMapper, BizProduct> implements BizProductService {

    @Resource
    private BizOrgService bizOrgService;

    @Override
    public Page<BizProduct> page(BizProductPageParam bizProductPageParam) {
        QueryWrapper<BizProduct> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizProductPageParam.getSearchKey())){
            queryWrapper.lambda().like(BizProduct::getOrgName, bizProductPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(bizProductPageParam.getYear())){
            queryWrapper.lambda().eq(BizProduct::getYear, bizProductPageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(bizProductPageParam.getSeason())){
            queryWrapper.lambda().eq(BizProduct::getSeason, bizProductPageParam.getSeason());
        }
        if(ObjectUtil.isAllNotEmpty(bizProductPageParam.getSortField(), bizProductPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizProductPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizProductPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizProductPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizProduct::getId);
        }
        // 校验数据范围
//        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
//        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
//            queryWrapper.lambda().in(BizProduct::getOrgId, loginUserDataScope);
//        } else {
//            queryWrapper.lambda().eq(BizProduct::getCreateUser, StpUtil.getLoginIdAsString());
//        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizProductAddParam bizProductAddParam) {
        //  季度+部门唯一
        long count = this.count(new LambdaQueryWrapper<BizProduct>().eq(BizProduct::getOrgId, bizProductAddParam.getOrgId())
                .eq(BizProduct::getYear, bizProductAddParam.getYear())
                .eq(BizProduct::getSeason, bizProductAddParam.getSeason()));
        if(count > 0){
            throw new CommonException("部门【{}】{}数据已存在！",
                    bizProductAddParam.getOrgName(), bizProductAddParam.getYear()+"-Q"+ bizProductAddParam.getSeason());
        }
        BizProduct bizProduct = BeanUtil.toBean(bizProductAddParam, BizProduct.class);
        this.save(bizProduct);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizProductEditParam bizProductEditParam) {
        //  季度+部门唯一
        long count = this.count(new LambdaQueryWrapper<BizProduct>().eq(BizProduct::getOrgId, bizProductEditParam.getOrgId())
                .eq(BizProduct::getYear, bizProductEditParam.getYear())
                .eq(BizProduct::getSeason, bizProductEditParam.getSeason())
                .ne(BizProduct::getId, bizProductEditParam.getId()));
        if(count > 0){
            throw new CommonException("部门【{}】{}数据已存在！",
                    bizProductEditParam.getOrgName(), bizProductEditParam.getYear()+"-Q"+ bizProductEditParam.getSeason());
        }
        BizProduct bizProduct = this.queryEntity(bizProductEditParam.getId());
        BeanUtil.copyProperties(bizProductEditParam, bizProduct);
        this.updateById(bizProduct);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizProductIdParam> bizProductIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizProductIdParamList, BizProductIdParam::getId));
    }

    @Override
    public BizProduct detail(BizProductIdParam bizProductIdParam) {
        return this.queryEntity(bizProductIdParam.getId());
    }

    @Override
    public BizProduct queryEntity(String id) {
        BizProduct bizProduct = this.getById(id);
        if(ObjectUtil.isEmpty(bizProduct)) {
            throw new CommonException("产值情况不存在，id值为：{}", id);
        }
        return bizProduct;
    }

    @Override
    public ImportResult doImport(ImportProductParam importParam) {
        List<ImportProductData> importData = importParam.getImportData();
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
            if(ObjectUtil.isEmpty(item.getSeason())){
                errMsg += "季度必填；";
            }
            if(item.getProductAmount() == null){
                errMsg += "生产产值必填；";
            }
            item.setErrorMsg(errMsg);
        });
        List<ImportProductData> checkRepeatData = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).collect(Collectors.toList());
        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item -> {
            List<ImportProductData> tmpList = checkRepeatData.stream().filter(obj -> item.getOrgName().equals(obj.getOrgName())
                    && item.getYear().equals(obj.getYear()) && item.getSeason().equals(obj.getSeason())).collect(Collectors.toList());
            if(tmpList.size() > 1){
                item.setErrorMsg("导入数据中，组织名称+年份+季度同时唯一存在重复；");
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

        List<BizProduct> oldList = this.list();
        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
            List<BizProduct> tmpList = oldList.stream().filter(obj -> item.getOrgName().equals(obj.getOrgName())
                    && item.getYear().equals(obj.getYear()) && item.getSeason().equals(obj.getSeason())).collect(Collectors.toList());
            if(!tmpList.isEmpty()){
                item.setErrorMsg(String.format("部门【%s】%s数据已存在；", item.getOrgName(), item.getYear()+"-Q"+item.getSeason()));
            }
        });
        //  导入
        ImportResult result = new ImportResult();
        List<ImportProductData> correctData = importData.stream().filter(x -> ObjectUtil.isEmpty(x.getErrorMsg())).collect(Collectors.toList());
        result.setTotal(importData.size());
        result.setErrCount(result.getTotal() - correctData.size());
        result.setSucCount(correctData.size());
        result.setResultList(importData);
        List<BizProduct> addList = BeanUtil.copyToList(correctData, BizProduct.class);
        addList.forEach(x->x.setNote(DateUtil.now()+"：页面导入；"));
        this.saveBatch(addList);
        return result;
    }
}
