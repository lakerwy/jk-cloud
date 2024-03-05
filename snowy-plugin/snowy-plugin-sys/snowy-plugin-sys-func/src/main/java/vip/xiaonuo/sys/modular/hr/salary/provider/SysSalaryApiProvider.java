package vip.xiaonuo.sys.modular.hr.salary.provider;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Service;
import vip.xiaonuo.sys.api.SysSalaryApi;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryPay;
import vip.xiaonuo.sys.modular.hr.salary.enums.SalaryTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryPayMapper;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryPayService;
import vip.xiaonuo.sys.modular.hr.salary.util.ComputeUtil;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalarySeniorityVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryStandardVo;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * 工资系统 对外暴露接口 实现方法
 */

@Service
public class SysSalaryApiProvider implements SysSalaryApi {
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
        List<SalaryStandardVo> personnelList = salaryPayMapper.getSeniorityPersonnel(type, yearMonth);
        if(CollUtil.isNotEmpty(personnelList)){
            List<SalaryPay> addList = new ArrayList<>();
            personnelList.forEach(item->{
                SalaryPay addParam = new SalaryPay();
                addParam.setType(type);
                addParam.setStandardId(item.getId());
                SalarySeniorityVo salarySeniorityVo = ComputeUtil.seniorityGZ(item.getPositiveTime());
                addParam.setAmount(salarySeniorityVo.getAmount());
                addParam.setPositiveAge(salarySeniorityVo.getPositiveAge());
                addParam.setMemo("记录自动生成时间："+ DateUtil.now()+"，"+salarySeniorityVo.getMemo()+"，转正日期："+ DateUtil.formatDate(item.getPositiveTime()));
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
        List<SalaryStandardVo> personnelList = salaryPayMapper.getBirthdayPersonnel(type,itemType, yearMonth,month);
        if(CollUtil.isNotEmpty(personnelList)){
            List<SalaryPay> addList = new ArrayList<>();
            personnelList.forEach(item->{
                SalaryPay addParam = new SalaryPay();
                addParam.setType(type);
                addParam.setItemType(itemType);
                addParam.setStandardId(item.getId());
                addParam.setAmount(ComputeUtil.birthdayGZ(item.getBirthDay()));
                addParam.setMemo("记录自动生成时间："+DateUtil.now()+"，生日日期是："+DateUtil.formatDate(item.getBirthDay())+"，是否有生日福利标记："+item.getHaveBirthdayPay());
                addList.add(addParam);
            });
            salaryPayService.saveBatch(addList);
        }
    }
}
