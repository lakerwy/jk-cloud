package vip.xiaonuo.sys.modular.hr.salary.provider;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.sys.api.SysSalaryApi;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryPay;
import vip.xiaonuo.sys.modular.hr.salary.enums.SalaryTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryPayMapper;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryPayService;
import vip.xiaonuo.sys.modular.hr.salary.util.ComputeUtil;
import vip.xiaonuo.sys.modular.hr.salary.vo.PersonnelInfoVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalarySeniorityVo;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static vip.xiaonuo.common.consts.CacheConstant.ORG_FIRST_CODE_CACHE_KEY;

/**
 * 工资系统 对外暴露接口 实现方法
 */

@Service
public class SysSalaryApiProvider implements SysSalaryApi {
    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private SalaryPayMapper salaryPayMapper;

    @Resource
    private SalaryPayService salaryPayService;

    @Override
    public void computeSeniorityGZ() {
        //获取当前年月  格式化当前日期的年月部分
        String type = SalaryTypeEnum.SENIORITY.getValue();
        String yearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        //条件：在职、转正人员(框定计算工龄工资的人员范围),且当月无记录
        List<PersonnelInfoVo> personnelList = salaryPayMapper.getSeniorityPersonnel(type, yearMonth);
        if(CollUtil.isNotEmpty(personnelList)){
            List<SalaryPay> addList = new ArrayList<>();
            personnelList.forEach(item->{
                SalaryPay addParam = new SalaryPay();
                addParam.setType(type);
                addParam.setYearMonth(yearMonth);
                addParam.setIdCardNumber(item.getIdNumber());
                addParam.setUserName(item.getName());
                addParam.setUserId(item.getUserId());
                addParam.setPersonnelId(item.getId());
                addParam.setHireDate(DateUtil.formatDate(item.getHireDate()));
                addParam.setPositiveTime(DateUtil.formatDate(item.getPositiveTime()));
                addParam.setBirthDay(DateUtil.formatDate(item.getBirth()));
                SalarySeniorityVo salarySeniorityVo = ComputeUtil.seniorityGZ(item.getPositiveTime());
                addParam.setAmount(salarySeniorityVo.getAmount());
                addParam.setPositiveAge(salarySeniorityVo.getPositiveAge());
                addParam.setMemo(salarySeniorityVo.getMemo());
                addParam.setPosition(item.getDutyName());
                addParam.setPositionId(item.getDutyId());
                addParam.setOrgName(item.getDeptName());
                addParam.setOrgId(item.getDeptId());
                addParam.setFirstOrgId(ComputeUtil.objToString(commonCacheOperator.get(ORG_FIRST_CODE_CACHE_KEY + "orgId" + addParam.getOrgId())));
                addParam.setFirstOrgName(ComputeUtil.objToString(commonCacheOperator.get(ORG_FIRST_CODE_CACHE_KEY + "orgName" + addParam.getOrgId())));
                addList.add(addParam);
            });
            salaryPayService.saveBatch(addList);
        }
    }

    @Override
    public void computeBirthdayGZ() {
        //获取当前年月  格式化当前日期的年月部分
        String type = SalaryTypeEnum.EMP_BONUS.getValue();
        String itemType = SalaryTypeEnum.BIRTHDAY.getValue();
        String yearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        int month = LocalDate.now().getMonthValue();
        //条件：在职、出生日期为当前月(框定计算工龄工资的人员范围),且当月无记录
        List<PersonnelInfoVo> personnelList = salaryPayMapper.getBirthdayPersonnel(type,itemType, yearMonth,month);
        if(CollUtil.isNotEmpty(personnelList)){
            List<SalaryPay> addList = new ArrayList<>();
            personnelList.forEach(item->{
                SalaryPay addParam = new SalaryPay();
                addParam.setType(type);
                addParam.setItemType(itemType);
                addParam.setYearMonth(yearMonth);
                addParam.setIdCardNumber(item.getIdNumber());
                addParam.setUserName(item.getName());
                addParam.setUserId(item.getUserId());
                addParam.setPersonnelId(item.getId());
                addParam.setHireDate(DateUtil.formatDate(item.getHireDate()));
                addParam.setPositiveTime(DateUtil.formatDate(item.getPositiveTime()));
                addParam.setBirthDay(DateUtil.formatDate(item.getBirth()));
                addParam.setAmount(ComputeUtil.birthdayGZ(item.getBirth()));
                addParam.setPosition(item.getDutyName());
                addParam.setPositionId(item.getDutyId());
                addParam.setOrgName(item.getDeptName());
                addParam.setOrgId(item.getDeptId());
                addParam.setFirstOrgId(ComputeUtil.objToString(commonCacheOperator.get(ORG_FIRST_CODE_CACHE_KEY + "orgId" + addParam.getOrgId())));
                addParam.setFirstOrgName(ComputeUtil.objToString(commonCacheOperator.get(ORG_FIRST_CODE_CACHE_KEY + "orgName" + addParam.getOrgId())));
                addList.add(addParam);
            });
            salaryPayService.saveBatch(addList);
        }
    }
}
