package vip.xiaonuo.biz.modular.deptFee.service.impl;

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
import vip.xiaonuo.biz.modular.deptFee.param.*;
import vip.xiaonuo.biz.modular.deptFee.service.DeptFeeService;
import vip.xiaonuo.biz.modular.deptFee.vo.ImportResult;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.deptFee.entity.DeptFee;
import vip.xiaonuo.biz.modular.deptFee.mapper.DeptFeeMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门费用管理Service接口实现类
 *
 * @author pzh
 * @date  2025/06/05 14:56
 **/
@Service
@DS("slave_1")
public class DeptFeeServiceImpl extends ServiceImpl<DeptFeeMapper, DeptFee> implements DeptFeeService {

    @Override
    public Page<DeptFee> page(DeptFeePageParam deptFeePageParam) {
        QueryWrapper<DeptFee> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(deptFeePageParam.getSearchKey())){
            queryWrapper.lambda().like(DeptFee::getDeptName, deptFeePageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(deptFeePageParam.getYear())){
            queryWrapper.lambda().eq(DeptFee::getYear, deptFeePageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(deptFeePageParam.getSeason())){
            queryWrapper.lambda().eq(DeptFee::getSeason, deptFeePageParam.getSeason());
        }
        if(ObjectUtil.isAllNotEmpty(deptFeePageParam.getSortField(), deptFeePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(deptFeePageParam.getSortOrder());
            queryWrapper.orderBy(true, deptFeePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(deptFeePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(DeptFee::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(DeptFeeAddParam deptFeeAddParam) {
        long count = this.count(new LambdaQueryWrapper<DeptFee>().eq(DeptFee::getDeptName, deptFeeAddParam.getDeptName())
                .eq(DeptFee::getYear, deptFeeAddParam.getYear())
                .eq(DeptFee::getSeason, deptFeeAddParam.getSeason()));
        if(count > 0){
            throw new CommonException("部门【{}】{}数据已存在！",
                    deptFeeAddParam.getDeptName(), deptFeeAddParam.getYear()+"-Q"+deptFeeAddParam.getSeason());
        }
        DeptFee deptFee = BeanUtil.toBean(deptFeeAddParam, DeptFee.class);
        this.save(deptFee);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(DeptFeeEditParam deptFeeEditParam) {
        long count = this.count(new LambdaQueryWrapper<DeptFee>().eq(DeptFee::getDeptName, deptFeeEditParam.getDeptName())
                .eq(DeptFee::getYear, deptFeeEditParam.getYear())
                .eq(DeptFee::getSeason, deptFeeEditParam.getSeason())
                .ne(DeptFee::getId, deptFeeEditParam.getId()));
        if(count > 0){
            throw new CommonException("部门【{}】{}数据已存在！",
                    deptFeeEditParam.getDeptName(), deptFeeEditParam.getYear()+"-Q"+deptFeeEditParam.getSeason());
        }
        DeptFee deptFee = this.queryEntity(deptFeeEditParam.getId());
        BeanUtil.copyProperties(deptFeeEditParam, deptFee);
        this.updateById(deptFee);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<DeptFeeIdParam> deptFeeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(deptFeeIdParamList, DeptFeeIdParam::getId));
    }

    @Override
    public DeptFee detail(DeptFeeIdParam deptFeeIdParam) {
        return this.queryEntity(deptFeeIdParam.getId());
    }

    @Override
    public DeptFee queryEntity(String id) {
        DeptFee deptFee = this.getById(id);
        if(ObjectUtil.isEmpty(deptFee)) {
            throw new CommonException("部门费用管理不存在，id值为：{}", id);
        }
        return deptFee;
    }

//    @Override
//    public ImportResult importCheck(ImportParam importParam) {
//        List<DeptFeeImportParam> importData = importParam.getImportData();
//        checkData(importData);
//        ImportResult result = new ImportResult();
//        result.setTotal(importData.size());
//        List<DeptFeeImportParam> errList = importData.stream().filter(x->ObjectUtil.isNotEmpty(x.getErrorMsg())).collect(Collectors.toList());
//        result.setErrCount(errList.size());
//        result.setResultList(importData);
//        result.setSucCount(result.getTotal() - result.getErrCount());
//        return result;
//    }
//
//    private void checkData(List<DeptFeeImportParam> importData) {
//        //校验格式与必填
//        importData.forEach(item->{
//            String errMsg = "";
//            if(ObjectUtil.isEmpty(item.getDeptName())){
//                errMsg += "部门名称必填；";
//            }
//            if(ObjectUtil.isEmpty(item.getYear())){
//                errMsg += "年份必填；";
//            }
//            if(ObjectUtil.isEmpty(item.getSeason())){
//                errMsg += "季度必填；";
//            }
//            if(item.getDeptFee() == null){
//                errMsg += "部门费用必填；";
//            }
//            item.setErrorMsg(errMsg);
//        });
//        List<DeptFeeImportParam> checkRepeatData = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).collect(Collectors.toList());
//        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item -> {
//            List<DeptFeeImportParam> tmpList = checkRepeatData.stream().filter(obj -> item.getDeptName().equals(obj.getDeptName())
//                            && item.getYear().equals(obj.getYear()) && item.getSeason().equals(obj.getSeason())).collect(Collectors.toList());
//            if(tmpList.size() > 1){
//                item.setErrorMsg("导入数据中，部门名称+年份季度同时唯一存在重复；");
//            }
//        });
//        List<DeptFee> oldList = this.list();
//        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
//            List<DeptFee> tmpList = oldList.stream().filter(obj -> item.getDeptName().equals(obj.getDeptName())
//                && item.getYear().equals(obj.getYear()) && item.getSeason().equals(obj.getSeason())).collect(Collectors.toList());
//            if(tmpList.size() > 0){
//                item.setErrorMsg(String.format("部门【%s】%s数据已存在；", item.getDeptName(), item.getYear()+"-Q"+item.getSeason()));
//            }
//        });
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public Boolean doImport(ImportParam importParam) {
//        List<DeptFeeImportParam> importData = importParam.getImportData().stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).collect(Collectors.toList());
//        if(CollUtil.isEmpty(importData)){
//            throw new CommonException("无有效导入数据");
//        }
//        List<DeptFee> deptFeeList = BeanUtil.copyToList(importData, DeptFee.class);
//        deptFeeList.forEach(x->x.setNote(DateUtil.now()+"：页面导入；"));
//        this.saveBatch(deptFeeList);
//        return true;
//    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ImportResult doImport(ImportParam importParam) {
        List<DeptFeeImportParam> importData = importParam.getImportData();
        if(CollUtil.isEmpty(importData)){
            throw new CommonException("无有效导入数据！");
        }
        // 校验格式与必填
        importData.forEach(item->{
            String errMsg = "";
            if(ObjectUtil.isEmpty(item.getDeptName())){
                errMsg += "部门名称必填；";
            }
            if(ObjectUtil.isEmpty(item.getYear())){
                errMsg += "年份必填；";
            }
            if(ObjectUtil.isEmpty(item.getSeason())){
                errMsg += "季度必填；";
            }
            if(item.getDeptFee() == null){
                errMsg += "部门费用必填；";
            }
            item.setErrorMsg(errMsg);
        });
        List<DeptFeeImportParam> checkRepeatData = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).collect(Collectors.toList());
        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item -> {
            List<DeptFeeImportParam> tmpList = checkRepeatData.stream().filter(obj -> item.getDeptName().equals(obj.getDeptName())
                            && item.getYear().equals(obj.getYear()) && item.getSeason().equals(obj.getSeason())).collect(Collectors.toList());
            if(tmpList.size() > 1){
                item.setErrorMsg("导入数据中，部门名称+年份+季度同时唯一存在重复；");
            }
        });
        List<DeptFee> oldList = this.list();
        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
            List<DeptFee> tmpList = oldList.stream().filter(obj -> item.getDeptName().equals(obj.getDeptName())
                && item.getYear().equals(obj.getYear()) && item.getSeason().equals(obj.getSeason())).collect(Collectors.toList());
            if(!tmpList.isEmpty()){
                item.setErrorMsg(String.format("部门【%s】%s数据已存在；", item.getDeptName(), item.getYear()+"-Q"+item.getSeason()));
            }
        });
        //  导入
        ImportResult result = new ImportResult();
        List<DeptFeeImportParam> correctData = importData.stream().filter(x -> ObjectUtil.isEmpty(x.getErrorMsg())).collect(Collectors.toList());
        result.setTotal(importData.size());
        result.setErrCount(result.getTotal() - correctData.size());
        result.setSucCount(correctData.size());
        result.setResultList(importData);
        List<DeptFee> addList = BeanUtil.copyToList(correctData, DeptFee.class);
        addList.forEach(x->x.setNote(DateUtil.now()+"：页面导入；"));
        this.saveBatch(addList);
        return result;
    }

}
