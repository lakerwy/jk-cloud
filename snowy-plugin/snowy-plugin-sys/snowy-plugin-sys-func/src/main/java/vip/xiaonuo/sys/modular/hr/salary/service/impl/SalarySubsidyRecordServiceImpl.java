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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryStandard;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalarySubsidyRecord;
import vip.xiaonuo.sys.modular.hr.salary.enums.SalaryTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalarySubsidyRecordMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.SalarySubsidyRecordAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalarySubsidyRecordEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalarySubsidyRecordIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalarySubsidyRecordPageParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.IdsParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryStandardService;
import vip.xiaonuo.sys.modular.hr.salary.service.SalarySubsidyRecordService;
import vip.xiaonuo.sys.modular.hr.salary.util.AesUtil;
import vip.xiaonuo.sys.modular.hr.salary.util.ComputeUtil;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalarySubsidyRecordVo;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

/**
 * 工资系统-各类补贴-明细记录Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:37
 **/
@Service
public class SalarySubsidyRecordServiceImpl extends ServiceImpl<SalarySubsidyRecordMapper, SalarySubsidyRecord> implements SalarySubsidyRecordService {

    @Resource
    private SalarySubsidyRecordMapper salarySubsidyRecordMapper;
    @Resource
    private SalaryStandardService salaryStandardService;

    @Override
    public Page<SalarySubsidyRecordVo> page(SalarySubsidyRecordPageParam salarySubsidyRecordPageParam) {
        QueryWrapper<SalarySubsidyRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getType())){
            queryWrapper.eq("a.TYPE",salarySubsidyRecordPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getYearMonth())) {
            queryWrapper.eq("b.`YEAR_MONTH`", salarySubsidyRecordPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getIdCardNumber())) {
            queryWrapper.like("b.ID_CARD_NUMBER", AesUtil.encrypt(salarySubsidyRecordPageParam.getIdCardNumber()));
        }
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getUserName())) {
            queryWrapper.like("b.USER_NAME", AesUtil.encrypt(salarySubsidyRecordPageParam.getUserName()));
        }
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getFirstOrgName())) {
            queryWrapper.like("b.FIRST_ORG_NAME", AesUtil.encrypt(salarySubsidyRecordPageParam.getFirstOrgName()));
        }
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getOrgName())) {
            queryWrapper.like("b.ORG_NAME", AesUtil.encrypt(salarySubsidyRecordPageParam.getOrgName()));
        }
        if(ObjectUtil.isNotEmpty(salarySubsidyRecordPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("b.ID_CARD_NUMBER",AesUtil.encrypt(salarySubsidyRecordPageParam.getSearchKey()))
                    .or().like("b.USER_NAME",AesUtil.encrypt(salarySubsidyRecordPageParam.getSearchKey()))
                    .or().like("b.FIRST_ORG_NAME",AesUtil.encrypt(salarySubsidyRecordPageParam.getSearchKey()))
                    .or().like("b.ORG_NAME",AesUtil.encrypt(salarySubsidyRecordPageParam.getSearchKey())));
        }
        if(ObjectUtil.isAllNotEmpty(salarySubsidyRecordPageParam.getSortField(), salarySubsidyRecordPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salarySubsidyRecordPageParam.getSortOrder());
            queryWrapper.orderBy(true, salarySubsidyRecordPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salarySubsidyRecordPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("b.`YEAR_MONTH`");
        }
        Page<SalarySubsidyRecordVo> result = salarySubsidyRecordMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
        List<SalarySubsidyRecordVo> records = AesUtil.decryptGZList(result.getRecords());
        records.sort(Comparator.comparing(SalarySubsidyRecordVo::getOrgName,Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(SalarySubsidyRecordVo::getIdCardNumber,Comparator.nullsLast(Comparator.naturalOrder())));
        result.setRecords(records);
        return result;
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
    public SalarySubsidyRecordVo detail(SalarySubsidyRecordIdParam salarySubsidyRecordIdParam) {
        SalarySubsidyRecordVo salarySubsidyRecordVo = salarySubsidyRecordMapper.getDetailById(salarySubsidyRecordIdParam.getId());
        return AesUtil.decryptGZ(salarySubsidyRecordVo);
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
    public void copy(IdsParam idsParam) {
        List<String> copyIds = idsParam.getIds();
        // 当前年月
        String currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String previousYearMonth = ComputeUtil.getPreviousYM(currentYearMonth);

        // 1. 获取待复制的源数据【Copy源数据】
        QueryWrapper<SalarySubsidyRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("a.TYPE", SalaryTypeEnum.REGISTER_SUBSIDY.getValue());
        queryWrapper.eq("b.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("b.`YEAR_MONTH`",previousYearMonth);
        if(CollUtil.isNotEmpty(copyIds)){
            queryWrapper.in("a.ID",copyIds);
        }
        List<SalarySubsidyRecordVo> copyAddData = salarySubsidyRecordMapper.getCopyData(queryWrapper);
        if(CollUtil.isEmpty(copyAddData)){
            throw new CommonException("不存在有效{}注册补贴数据，无法复制，请检查！",previousYearMonth);
        }

        // 2. 获取本月的工资标准数据【Copy目标月工资标准数据】
        List<SalaryStandard> currentStandardList = salaryStandardService.list(new QueryWrapper<SalaryStandard>().lambda().eq(SalaryStandard::getYearMonth,currentYearMonth));
        if(CollUtil.isEmpty(currentStandardList)){
            throw new CommonException("不存在{}月工资标准数据，无法复制，请检查！",currentYearMonth);
        }

        // 3. copy数据匹配月工资标准，并校验【copy数据匹配校验】
        List<SalarySubsidyRecordVo> newCopyAddData = BeanUtil.copyToList(copyAddData,SalarySubsidyRecordVo.class);
        newCopyAddData.forEach(item->{
            SalaryStandard standardTmp = currentStandardList.stream().filter(x->x.getIdCardNumber().equals(item.getIdCardNumber()) && x.getCompany().equals(item.getCompany())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(standardTmp)){
                item.setId(null);         //新主键Id
                item.setStandardId(standardTmp.getId()); //新关联外键Id
                item.setCreateTime(null);
                item.setCreateUser(null);
                item.setUpdateUser(null);
                item.setUpdateTime(null);
            }else{
                throw new CommonException("员工未匹配到{}月工资标准数据，无法复制，请检查！",currentYearMonth);
            }
        });

        // 4. copy数据
        if(CollUtil.isNotEmpty(newCopyAddData)){
            List<SalarySubsidyRecord> addList = BeanUtil.copyToList(newCopyAddData,SalarySubsidyRecord.class);
            this.saveBatch(addList);
        }
    }
}
