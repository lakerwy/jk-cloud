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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryAdd;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryAddRecord;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryAddMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddPageParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryAddRecordService;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryAddService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工资系统-其他费用扣加Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:23
 **/
@Service
public class SalaryAddServiceImpl extends ServiceImpl<SalaryAddMapper, SalaryAdd> implements SalaryAddService {

    @Resource
    private SalaryAddRecordService salaryAddRecordService;
    @Override
    public Page<SalaryAdd> page(SalaryAddPageParam salaryAddPageParam) {
        QueryWrapper<SalaryAdd> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(salaryAddPageParam.getYearMonth())) {
            queryWrapper.lambda().eq(SalaryAdd::getYearMonth, salaryAddPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryAddPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(SalaryAdd::getIdCardNumber, salaryAddPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(salaryAddPageParam.getUserName())) {
            queryWrapper.lambda().like(SalaryAdd::getUserName, salaryAddPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(salaryAddPageParam.getFirstOrgName())) {
            queryWrapper.lambda().like(SalaryAdd::getFirstOrgName, salaryAddPageParam.getFirstOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryAddPageParam.getFirstOrgId())) {
            queryWrapper.lambda().eq(SalaryAdd::getFirstOrgId, salaryAddPageParam.getFirstOrgId());
        }
        if(ObjectUtil.isNotEmpty(salaryAddPageParam.getOrgName())) {
            queryWrapper.lambda().like(SalaryAdd::getOrgName, salaryAddPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryAddPageParam.getOrgId())) {
            queryWrapper.lambda().eq(SalaryAdd::getOrgId, salaryAddPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(salaryAddPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(SalaryAdd::getFirstOrgName,salaryAddPageParam.getSearchKey())
                    .or().like(SalaryAdd::getOrgName,salaryAddPageParam.getSearchKey())
                    .or().like(SalaryAdd::getUserName,salaryAddPageParam.getSearchKey())
                    .or().like(SalaryAdd::getIdCardNumber,salaryAddPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(salaryAddPageParam.getSortField(), salaryAddPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryAddPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryAddPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryAddPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(SalaryAdd::getYearMonth).orderByAsc(SalaryAdd::getFirstOrgName).orderByAsc(SalaryAdd::getOrgName).orderByAsc(SalaryAdd::getUserName);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalaryAddAddParam salaryAddAddParam) {
        //同一用户，同一年度月份，只能存在一条有效记录（yearMonth+personnelId）
        List<SalaryAdd> oldList = this.list(new QueryWrapper<SalaryAdd>().lambda()
                .eq(SalaryAdd::getYearMonth,salaryAddAddParam.getYearMonth())
                .eq(SalaryAdd::getPersonnelId,salaryAddAddParam.getPersonnelId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("{}年月{}已存在记录",salaryAddAddParam.getYearMonth(),salaryAddAddParam.getUserName());
        }
        SalaryAdd salaryAdd = BeanUtil.toBean(salaryAddAddParam, SalaryAdd.class);
        this.save(salaryAdd);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalaryAddEditParam salaryAddEditParam) {
        //同一用户，同一年度月份，只能存在一条有效记录（yearMonth+personnelId）
        List<SalaryAdd> oldList = this.list(new QueryWrapper<SalaryAdd>().lambda()
                .eq(SalaryAdd::getYearMonth,salaryAddEditParam.getYearMonth())
                .eq(SalaryAdd::getPersonnelId,salaryAddEditParam.getPersonnelId())
                .ne(SalaryAdd::getId,salaryAddEditParam.getId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("{}年月{}已存在记录",salaryAddEditParam.getYearMonth(),salaryAddEditParam.getUserName());
        }
        SalaryAdd salaryAdd = this.queryEntity(salaryAddEditParam.getId());
        BeanUtil.copyProperties(salaryAddEditParam, salaryAdd);
        this.updateById(salaryAdd);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalaryAddIdParam> salaryAddIdParamList) {
        List<String> delIds = CollStreamUtil.toList(salaryAddIdParamList, SalaryAddIdParam::getId);
        // 执行删除
        this.removeByIds(delIds);
        // 删除加扣人员，同时删除加扣记录
        if(CollUtil.isNotEmpty(delIds)){
            salaryAddRecordService.remove(new QueryWrapper<SalaryAddRecord>().lambda().in(SalaryAddRecord::getAddId,delIds));
        }
    }

    @Override
    public SalaryAdd detail(SalaryAddIdParam salaryAddIdParam) {
        return this.queryEntity(salaryAddIdParam.getId());
    }

    @Override
    public SalaryAdd queryEntity(String id) {
        SalaryAdd salaryAdd = this.getById(id);
        if(ObjectUtil.isEmpty(salaryAdd)) {
            throw new CommonException("工资系统-其他费用扣加不存在，id值为：{}", id);
        }
        return salaryAdd;
    }
}
