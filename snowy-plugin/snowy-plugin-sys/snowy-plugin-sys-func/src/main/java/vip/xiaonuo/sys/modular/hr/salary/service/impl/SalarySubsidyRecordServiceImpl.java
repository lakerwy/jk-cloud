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
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalarySubsidyRecord;
import vip.xiaonuo.sys.modular.hr.salary.enums.SalaryTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalarySubsidyRecordMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.SalarySubsidyRecordAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalarySubsidyRecordEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalarySubsidyRecordIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalarySubsidyRecordPageParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalarySubsidyRecordService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 工资系统-各类补贴-明细记录Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:37
 **/
@Service
public class SalarySubsidyRecordServiceImpl extends ServiceImpl<SalarySubsidyRecordMapper, SalarySubsidyRecord> implements SalarySubsidyRecordService {

    @Override
    public Page<SalarySubsidyRecord> page(SalarySubsidyRecordPageParam salarySubsidyRecordPageParam) {
        QueryWrapper<SalarySubsidyRecord> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getType())) {
            queryWrapper.lambda().eq(SalarySubsidyRecord::getType, salarySubsidyRecordPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getYearMonth())) {
            queryWrapper.lambda().eq(SalarySubsidyRecord::getYearMonth, salarySubsidyRecordPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(SalarySubsidyRecord::getIdCardNumber, salarySubsidyRecordPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getUserName())) {
            queryWrapper.lambda().like(SalarySubsidyRecord::getUserName, salarySubsidyRecordPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getFirstOrgName())) {
            queryWrapper.lambda().like(SalarySubsidyRecord::getFirstOrgName, salarySubsidyRecordPageParam.getFirstOrgName());
        }
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getFirstOrgId())) {
            queryWrapper.lambda().eq(SalarySubsidyRecord::getFirstOrgId, salarySubsidyRecordPageParam.getFirstOrgId());
        }
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getOrgName())) {
            queryWrapper.lambda().like(SalarySubsidyRecord::getOrgName, salarySubsidyRecordPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getOrgId())) {
            queryWrapper.lambda().eq(SalarySubsidyRecord::getOrgId, salarySubsidyRecordPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getPosition())) {
            queryWrapper.lambda().like(SalarySubsidyRecord::getPosition, salarySubsidyRecordPageParam.getPosition());
        }
        if(ObjectUtil.isAllNotEmpty(salarySubsidyRecordPageParam.getSortField(), salarySubsidyRecordPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salarySubsidyRecordPageParam.getSortOrder());
            queryWrapper.orderBy(true, salarySubsidyRecordPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salarySubsidyRecordPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SalarySubsidyRecord::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalarySubsidyRecordAddParam salarySubsidyRecordAddParam) {
        SalarySubsidyRecord salarySubsidyRecord = BeanUtil.toBean(salarySubsidyRecordAddParam, SalarySubsidyRecord.class);
        this.save(salarySubsidyRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalarySubsidyRecordEditParam salarySubsidyRecordEditParam) {
        SalarySubsidyRecord salarySubsidyRecord = this.queryEntity(salarySubsidyRecordEditParam.getId());
        BeanUtil.copyProperties(salarySubsidyRecordEditParam, salarySubsidyRecord);
        this.updateById(salarySubsidyRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalarySubsidyRecordIdParam> salarySubsidyRecordIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(salarySubsidyRecordIdParamList, SalarySubsidyRecordIdParam::getId));
    }

    @Override
    public SalarySubsidyRecord detail(SalarySubsidyRecordIdParam salarySubsidyRecordIdParam) {
        return this.queryEntity(salarySubsidyRecordIdParam.getId());
    }

    @Override
    public SalarySubsidyRecord queryEntity(String id) {
        SalarySubsidyRecord salarySubsidyRecord = this.getById(id);
        if(ObjectUtil.isEmpty(salarySubsidyRecord)) {
            throw new CommonException("工资系统-各类补贴-明细记录不存在，id值为：{}", id);
        }
        return salarySubsidyRecord;
    }

    @Override
    public void copy() {
        // 创建格式器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        // 获取当前日期   当前年月 202402
        LocalDate currentDate = LocalDate.now();
        String currentYearMonth = currentDate.format(formatter);
        // 计算上个年月 202401
        YearMonth previousMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonthValue() - 1);
        String previousYearMonth = previousMonth.format(formatter);
        //1. 判断当前年月是否已存在数据（存在，则不可复制）
        List<SalarySubsidyRecord> currentData = this.list(new QueryWrapper<SalarySubsidyRecord>().lambda()
                .eq(SalarySubsidyRecord::getType, SalaryTypeEnum.REGISTER_SUBSIDY.getValue()).eq(SalarySubsidyRecord::getYearMonth,currentYearMonth));
        if(CollUtil.isNotEmpty(currentData)){
            throw new CommonException("已存在{}月注册补贴数据",currentYearMonth);
        }
        //2. 判断上年月是否存在工资标准数据（不存在，则无法复制）
        List<SalarySubsidyRecord> previousData = this.list(new QueryWrapper<SalarySubsidyRecord>().lambda()
                .eq(SalarySubsidyRecord::getType, SalaryTypeEnum.REGISTER_SUBSIDY.getValue()).eq(SalarySubsidyRecord::getYearMonth,previousYearMonth));
        if(CollUtil.isEmpty(previousData)){
            throw new CommonException("不存在{}月注册补贴数据",previousYearMonth);
        }
        //3. 复制数据
        previousData.forEach(item->{
            item.setId(null);
            item.setYearMonth(currentYearMonth);
            item.setCreateTime(null);
            item.setCreateUser(null);
            item.setUpdateTime(null);
            item.setUpdateUser(null);
        });
        this.saveBatch(previousData);
    }
}
