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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryAttend;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryAttendMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAttendAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAttendEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAttendIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAttendPageParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryAttendService;

import java.util.List;

/**
 * 工资系统-月度考勤表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:26
 **/
@Service
public class SalaryAttendServiceImpl extends ServiceImpl<SalaryAttendMapper, SalaryAttend> implements SalaryAttendService {
    @Override
    public Page<SalaryAttend> page(SalaryAttendPageParam salaryAttendPageParam) {
        QueryWrapper<SalaryAttend> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(salaryAttendPageParam.getYearMonth())) {
            queryWrapper.lambda().eq(SalaryAttend::getYearMonth, salaryAttendPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryAttendPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(SalaryAttend::getIdCardNumber, salaryAttendPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(salaryAttendPageParam.getUserName())) {
            queryWrapper.lambda().like(SalaryAttend::getUserName, salaryAttendPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(salaryAttendPageParam.getFirstOrgName())) {
            queryWrapper.lambda().like(SalaryAttend::getFirstOrgName, salaryAttendPageParam.getFirstOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryAttendPageParam.getFirstOrgId())) {
            queryWrapper.lambda().eq(SalaryAttend::getFirstOrgId, salaryAttendPageParam.getFirstOrgId());
        }
        if(ObjectUtil.isNotEmpty(salaryAttendPageParam.getOrgName())) {
            queryWrapper.lambda().like(SalaryAttend::getOrgName, salaryAttendPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryAttendPageParam.getOrgId())) {
            queryWrapper.lambda().eq(SalaryAttend::getOrgId, salaryAttendPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(salaryAttendPageParam.getPosition())) {
            queryWrapper.lambda().like(SalaryAttend::getPosition, salaryAttendPageParam.getPosition());
        }
        if(ObjectUtil.isNotEmpty(salaryAttendPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(SalaryAttend::getIdCardNumber,salaryAttendPageParam.getSearchKey())
                    .or().like(SalaryAttend::getUserName,salaryAttendPageParam.getSearchKey())
                    .or().like(SalaryAttend::getFirstOrgName,salaryAttendPageParam.getSearchKey())
                    .or().like(SalaryAttend::getOrgName,salaryAttendPageParam.getSearchKey())
                    .or().like(SalaryAttend::getPosition,salaryAttendPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(salaryAttendPageParam.getSortField(), salaryAttendPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryAttendPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryAttendPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryAttendPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(SalaryAttend::getYearMonth).orderByAsc(SalaryAttend::getFirstOrgName).orderByAsc(SalaryAttend::getOrgName).orderByAsc(SalaryAttend::getUserName);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalaryAttendAddParam salaryAttendAddParam) {
        //同一年月，同一花名册Id，只存在一条考核记录
        List<SalaryAttend> oldList = this.list(new QueryWrapper<SalaryAttend>().lambda()
                .eq(SalaryAttend::getYearMonth,salaryAttendAddParam.getYearMonth())
                .eq(SalaryAttend::getPersonnelId,salaryAttendAddParam.getPersonnelId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("{}年月{}已存在记录",salaryAttendAddParam.getYearMonth(),salaryAttendAddParam.getUserName());
        }
        SalaryAttend salaryAttend = BeanUtil.toBean(salaryAttendAddParam, SalaryAttend.class);
        this.save(salaryAttend);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalaryAttendEditParam salaryAttendEditParam) {
        //同一年月，同一花名册Id，只存在一条考核记录
        List<SalaryAttend> oldList = this.list(new QueryWrapper<SalaryAttend>().lambda()
                .eq(SalaryAttend::getYearMonth,salaryAttendEditParam.getYearMonth())
                .eq(SalaryAttend::getPersonnelId,salaryAttendEditParam.getPersonnelId())
                .ne(SalaryAttend::getId,salaryAttendEditParam.getId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("{}年月{}已存在记录",salaryAttendEditParam.getYearMonth(),salaryAttendEditParam.getUserName());
        }
        SalaryAttend salaryAttend = this.queryEntity(salaryAttendEditParam.getId());
        BeanUtil.copyProperties(salaryAttendEditParam, salaryAttend);
        this.updateById(salaryAttend);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalaryAttendIdParam> salaryAttendIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(salaryAttendIdParamList, SalaryAttendIdParam::getId));
    }

    @Override
    public SalaryAttend detail(SalaryAttendIdParam salaryAttendIdParam) {
        return this.queryEntity(salaryAttendIdParam.getId());
    }

    @Override
    public SalaryAttend queryEntity(String id) {
        SalaryAttend salaryAttend = this.getById(id);
        if(ObjectUtil.isEmpty(salaryAttend)) {
            throw new CommonException("工资系统-月度考勤表不存在，id值为：{}", id);
        }
        return salaryAttend;
    }

}
