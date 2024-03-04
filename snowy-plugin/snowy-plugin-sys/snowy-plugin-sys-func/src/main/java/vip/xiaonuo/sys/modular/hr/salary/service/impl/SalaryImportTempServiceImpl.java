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
package vip.xiaonuo.sys.modular.hr.salary.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.salary.entity.*;
import vip.xiaonuo.sys.modular.hr.salary.enums.SalaryTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryImportTempMapper;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryPayMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryImportTempIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryImportTempPageParam;
import vip.xiaonuo.sys.modular.hr.salary.service.*;
import vip.xiaonuo.sys.modular.hr.salary.util.ComputeUtil;
import vip.xiaonuo.sys.modular.hr.salary.vo.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static vip.xiaonuo.common.consts.CacheConstant.ORG_FIRST_CODE_CACHE_KEY;

/**
 * 工资系统-相关数据导入临时表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:31
 **/
@Service
public class SalaryImportTempServiceImpl extends ServiceImpl<SalaryImportTempMapper, SalaryImportTemp> implements SalaryImportTempService {
    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private SalaryStandardService salaryStandardService;
    @Resource
    private SalaryAttendService salaryAttendService;
    @Resource
    private SalarySubsidyRecordService salarySubsidyRecordService;
    @Resource
    private SalaryInsService salaryInsService;
    @Resource
    private SalaryGjjService salaryGjjService;
    @Resource
    private SalaryTaxService salaryTaxService;
    @Resource
    private SalaryPayMapper salaryPayMapper;

    @Override
    public Page<SalaryImportTemp> page(SalaryImportTempPageParam salaryImportTempPageParam) {
        QueryWrapper<SalaryImportTemp> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(salaryImportTempPageParam.getBatchId())) {
            queryWrapper.lambda().eq(SalaryImportTemp::getBatchId, salaryImportTempPageParam.getBatchId());
        }
        if(ObjectUtil.isNotEmpty(salaryImportTempPageParam.getType())) {
            queryWrapper.lambda().eq(SalaryImportTemp::getType, salaryImportTempPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(salaryImportTempPageParam.getYearMonth())) {
            queryWrapper.lambda().eq(SalaryImportTemp::getYearMonth, salaryImportTempPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryImportTempPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(SalaryImportTemp::getIdCardNumber, salaryImportTempPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(salaryImportTempPageParam.getUserName())) {
            queryWrapper.lambda().like(SalaryImportTemp::getUserName, salaryImportTempPageParam.getUserName());
        }
        if(ObjectUtil.isAllNotEmpty(salaryImportTempPageParam.getSortField(), salaryImportTempPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryImportTempPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryImportTempPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryImportTempPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SalaryImportTemp::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalaryImportTempIdParam> salaryImportTempIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(salaryImportTempIdParamList, SalaryImportTempIdParam::getId));
    }

    @Override
    public SalaryImportTemp detail(SalaryImportTempIdParam salaryImportTempIdParam) {
        return this.queryEntity(salaryImportTempIdParam.getId());
    }

    @Override
    public SalaryImportTemp queryEntity(String id) {
        SalaryImportTemp salaryImportTemp = this.getById(id);
        if(ObjectUtil.isEmpty(salaryImportTemp)) {
            throw new CommonException("工资系统-相关数据导入临时表不存在，id值为：{}", id);
        }
        return salaryImportTemp;
    }

    @Override
    public ImportResult importStandard(ImportStandard input) {
        //1.定义一个导入批次号
        String batchId = IdWorker.getTimeId();
        //2.导入类型
        String type = SalaryTypeEnum.STANDARD.getValue();
        //3.数据转换，数据初始值
        List<SalaryImportTemp> importData = BeanUtil.copyToList(input.getImportList(), SalaryImportTemp.class);
        importData.forEach(x->{
            x.setBatchId(batchId);
            x.setType(type);
        });
        //4.数据校验（必填+匹配）
        checkData(importData);
        //5.校验是否已存在数据（每人每月一条导入记录）
        List<String> yearMonths = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).map(x->x.getYearMonth()).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(yearMonths)) {
            List<SalaryStandard> oldList = salaryStandardService.list(new QueryWrapper<SalaryStandard>().lambda().eq(SalaryStandard::getType,type).in(SalaryStandard::getYearMonth,yearMonths));
            if(CollUtil.isNotEmpty(oldList)){
                importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
                    List<SalaryStandard> tmpList = oldList.stream().filter(obj->obj.getYearMonth().equals(item.getYearMonth()) && obj.getPersonnelId().equals(item.getPersonnelId())).collect(Collectors.toList());
                    if(CollUtil.isNotEmpty(tmpList)){
                        item.setErrorMsg("员工已存在当月数据；");
                    }
                });
            }
        }
        //6.将数据存放在导入临时表
        this.saveBatch(importData);
        //7.返回结果
        return importResult(batchId,type,importData);
    }

    @Override
    public ImportResult importAttend(ImportAttend input) {
        //1.定义一个导入批次号
        String batchId = IdWorker.getTimeId();
        //2.导入类型
        String type = SalaryTypeEnum.ATTEND.getValue();
        //3.数据转换，数据初始值
        List<SalaryImportTemp> importData = BeanUtil.copyToList(input.getImportList(), SalaryImportTemp.class);
        importData.forEach(x->{
            x.setBatchId(batchId);
            x.setType(type);
        });
        //4.数据校验（必填+匹配）
        checkData(importData);
        //5.校验是否已存在数据（每人每月一条导入记录）
        List<String> yearMonths = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).map(x->x.getYearMonth()).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(yearMonths)) {
            List<SalaryAttend> oldList = salaryAttendService.list(new QueryWrapper<SalaryAttend>().lambda().in(SalaryAttend::getYearMonth,yearMonths));
            if(CollUtil.isNotEmpty(oldList)){
                importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
                    List<SalaryAttend> tmpList = oldList.stream().filter(obj->obj.getYearMonth().equals(item.getYearMonth()) && obj.getPersonnelId().equals(item.getPersonnelId())).collect(Collectors.toList());
                    if(CollUtil.isNotEmpty(tmpList)){
                        item.setErrorMsg("员工已存在当月数据；");
                    }
                });
            }
        }
        //6.将数据存放在导入临时表
        this.saveBatch(importData);
        //7.返回结果
        return importResult(batchId,type,importData);
    }

    @Override
    public ImportResult importSubsidy(ImportSubsidy input) {
        //1.定义一个导入批次号
        String batchId = IdWorker.getTimeId();
        //2.导入类型
        String type = input.getType();
        //3.数据转换，数据初始值
        List<SalaryImportTemp> importData = BeanUtil.copyToList(input.getImportList(), SalaryImportTemp.class);
        importData.forEach(x->{
            x.setBatchId(batchId);
            x.setType(type);
        });
        //4.数据校验（必填+匹配）
        checkData(importData);
        //6.将数据存放在导入临时表
        this.saveBatch(importData);
        //7.返回结果
        return importResult(batchId,type,importData);
    }

    @Override
    public ImportResult importInsGjjTax(ImportInsGjjTax input) {
        //1.定义一个导入批次号
        String batchId = IdWorker.getTimeId();
        //2.导入类型
        String type = input.getType();
        //3.数据转换，数据初始值
        List<SalaryImportTemp> importData = BeanUtil.copyToList(input.getImportList(), SalaryImportTemp.class);
        importData.forEach(x->{
            x.setBatchId(batchId);
            x.setType(type);
        });
        //4.数据校验（必填+匹配）
        checkData(importData);
        //5.校验是否已存在数据（每人每月一条导入记录）
        List<String> yearMonths = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).map(x->x.getYearMonth()).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(yearMonths)) {
            if (SalaryTypeEnum.INS.getValue().equals(type)) {
                List<SalaryIns> oldList = salaryInsService.list(new QueryWrapper<SalaryIns>().lambda().in(SalaryIns::getYearMonth,yearMonths));
                if(CollUtil.isNotEmpty(oldList)){
                    importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
                        List<SalaryIns> tmpList = oldList.stream().filter(obj->obj.getYearMonth().equals(item.getYearMonth()) && obj.getPersonnelId().equals(item.getPersonnelId())).collect(Collectors.toList());
                        if(CollUtil.isNotEmpty(tmpList)){
                            item.setErrorMsg("员工已存在当月数据；");
                        }
                    });
                }
            } else if (SalaryTypeEnum.GJJ.getValue().equals(type)) {
                List<SalaryGjj> oldList = salaryGjjService.list(new QueryWrapper<SalaryGjj>().lambda().in(SalaryGjj::getYearMonth,yearMonths));
                if(CollUtil.isNotEmpty(oldList)){
                    importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
                        List<SalaryGjj> tmpList = oldList.stream().filter(obj->obj.getYearMonth().equals(item.getYearMonth()) && obj.getPersonnelId().equals(item.getPersonnelId())).collect(Collectors.toList());
                        if(CollUtil.isNotEmpty(tmpList)){
                            item.setErrorMsg("员工已存在当月数据；");
                        }
                    });
                }
            } else if (SalaryTypeEnum.TAX.getValue().equals(type)) {
                List<SalaryTax> oldList = salaryTaxService.list(new QueryWrapper<SalaryTax>().lambda().in(SalaryTax::getYearMonth,yearMonths));
                if(CollUtil.isNotEmpty(oldList)){
                    importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
                        List<SalaryTax> tmpList = oldList.stream().filter(obj->obj.getYearMonth().equals(item.getYearMonth()) && obj.getPersonnelId().equals(item.getPersonnelId())).collect(Collectors.toList());
                        if(CollUtil.isNotEmpty(tmpList)){
                            item.setErrorMsg("员工已存在当月数据；");
                        }
                    });
                }
            }
        }
        //6.将数据存放在导入临时表
        this.saveBatch(importData);
        //7.返回结果
        return importResult(batchId,type,importData);
    }


    @Override
    public Boolean doImport(DoImport input) {
        //校验请求参数
        if(ObjectUtil.isEmpty(input.getBatchId()) || ObjectUtil.isEmpty(input.getBatchId())){
            throw new CommonException("参数异常");
        }
        //判断本次导入是否有无误数据
        List<SalaryImportTemp> importTempList = this.list(new QueryWrapper<SalaryImportTemp>().lambda()
                .eq(SalaryImportTemp::getBatchId,input.getBatchId()).eq(SalaryImportTemp::getErrorMsg,""));
        if(CollUtil.isEmpty(importTempList)) return true;
        //为导入数据，默认值处理
        List<SalaryImportTemp> importTempData = ComputeUtil.initImportData(importTempList);
        //月工资标准
        if(SalaryTypeEnum.STANDARD.getValue().equals(input.getType())){
            List<SalaryStandard> importData = BeanUtil.copyToList(importTempData,SalaryStandard.class);
            List<SalaryStandard> newImportData = ComputeUtil.computeStandardGZ(importData);
            salaryStandardService.saveBatch(newImportData);
        }else if(SalaryTypeEnum.ATTEND.getValue().equals(input.getType())){
            List<SalaryAttend> importData = BeanUtil.copyToList(importTempData,SalaryAttend.class);
            //获取 本次 涉及到的 工资年月
            List<String> yearMonths = importData.stream().map(x->x.getYearMonth()).distinct().collect(Collectors.toList());
            //获取 工资标准 数据
            List<SalaryStandardInfoVo> standardList = salaryStandardService.getSalaryStandardList(SalaryTypeEnum.STANDARD.getValue(), yearMonths);
            List<SalaryAttend> newImportData = ComputeUtil.computeAttendGZ(importData,standardList);
            salaryAttendService.saveBatch(newImportData);
        }else if(SalaryTypeEnum.REGISTER_SUBSIDY.getValue().equals(input.getType())
                || SalaryTypeEnum.DUTY_SUBSIDY.getValue().equals(input.getType())
                || SalaryTypeEnum.TEACHER_SUBSIDY.getValue().equals(input.getType())
                || SalaryTypeEnum.OUTER_SUBSIDY.getValue().equals(input.getType())){
            List<SalarySubsidyRecord> importData = BeanUtil.copyToList(importTempData,SalarySubsidyRecord.class);
            salarySubsidyRecordService.saveBatch(importData);
        }else if(SalaryTypeEnum.INS.getValue().equals(input.getType())){
            List<SalaryIns> importData = BeanUtil.copyToList(importTempData,SalaryIns.class);
            salaryInsService.saveBatch(importData);
        }else if(SalaryTypeEnum.GJJ.getValue().equals(input.getType())){
            List<SalaryGjj> importData = BeanUtil.copyToList(importTempData,SalaryGjj.class);
            salaryGjjService.saveBatch(importData);
        }else if(SalaryTypeEnum.TAX.getValue().equals(input.getType())){
            List<SalaryTax> importData = BeanUtil.copyToList(importTempData,SalaryTax.class);
            salaryTaxService.saveBatch(importData);
        }
        return true;
    }


    /**
     * 通用校验（必填，与花名册匹配）
     * @param importData
     * @return
     */
    public void checkData(List<SalaryImportTemp> importData){
        //第一步：校验必填
        importData.forEach(item->{
            String errMsg = "";
            if(ObjectUtil.isEmpty(item.getIdCardNumber())){
                errMsg += "身份证号必填；";
            }
            if(ObjectUtil.isEmpty(item.getYearMonth())){
                errMsg += "所属年月必填；";
            }
            if(ObjectUtil.isEmpty(item.getUserName())){
                errMsg += "姓名必填；";
            }
            if(SalaryTypeEnum.STANDARD.getValue().equals(item.getType())){
                if(ObjectUtil.isEmpty(item.getStandardAmount())){
                    errMsg += "月度标准工资必填；";
                }
            }
            item.setErrorMsg(errMsg);
        });

        //第二步：逻辑校验  经过 必填校验，是否存在无错误记录
        long filterImportData = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).count();
        if(filterImportData <= 0) {
            return ;
        }
        // 与花名册匹配，反更新基础信息
        List<PersonnelInfoVo> personnelList = salaryPayMapper.getPersonnelList();
        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
                //根据身份证号匹配 花名册 人员
                List<PersonnelInfoVo> personnels = personnelList.stream().filter(x->item.getIdCardNumber().equals(x.getIdNumber())).collect(Collectors.toList());
                if(CollUtil.isNotEmpty(personnels)){
                    if(personnels.size() == 1){
                        PersonnelInfoVo personnel = personnels.get(0);
                        item.setUserId(personnel.getUserId());
                        item.setPersonnelId(personnel.getId());
                        item.setOrgName(personnel.getDeptName());
                        item.setOrgId(personnel.getDeptId());
                        item.setPosition(personnel.getDutyName());
                        item.setPositionId(personnel.getDutyId());
                        item.setHireDate(DateUtil.formatDate(personnel.getHireDate()));
                        item.setLeaveDate(personnel.getLeaveDate());  //离职日期
                        item.setFirstOrgId(ComputeUtil.objToString(commonCacheOperator.get(ORG_FIRST_CODE_CACHE_KEY + "orgId" + item.getOrgId())));
                        item.setFirstOrgName(ComputeUtil.objToString(commonCacheOperator.get(ORG_FIRST_CODE_CACHE_KEY + "orgName" + item.getOrgId())));
                    }else{
                        item.setErrorMsg("花名册未能唯一匹配用户；");
                    }
                }else{
                    item.setErrorMsg("花名册未能匹配到用户");
                }
        });
    }

    /**
     * 生成 导入结果
     * @param batchId 导入批次号
     * @param type 导入类型
     * @param importData 导入数据集
     * @return
     */
    public ImportResult importResult(String batchId,String type,List<SalaryImportTemp> importData){
        ImportResult result = new ImportResult();
        result.setBatchId(batchId);
        result.setType(type);
        result.setTotal(importData.size());
        List<SalaryImportTemp> errList = importData.stream().filter(x->ObjectUtil.isNotEmpty(x.getErrorMsg())).collect(Collectors.toList());
        result.setErrCount(errList.size());
        result.setResultList(importData);
        result.setSucCount(result.getTotal() - result.getErrCount());
        return result;
    }
}
