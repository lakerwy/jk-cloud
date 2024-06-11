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
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryAdd;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryAddRecord;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryStandard;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryAddMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddPageParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.CopyParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryAddRecordService;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryAddService;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryStandardService;
import vip.xiaonuo.sys.modular.hr.salary.util.AesUtil;
import vip.xiaonuo.sys.modular.hr.salary.util.ComputeUtil;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryAddVo;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工资系统-其他费用扣加Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:23
 **/
@Service
public class SalaryAddServiceImpl extends ServiceImpl<SalaryAddMapper, SalaryAdd> implements SalaryAddService {

    @Resource
    private SalaryStandardService salaryStandardService;
    @Resource
    private SalaryAddMapper salaryAddMapper;
    @Resource
    private SalaryAddRecordService salaryAddRecordService;
    @Override
    public Page<SalaryAddVo> page(SalaryAddPageParam salaryAddPageParam) {
        QueryWrapper<SalaryAdd> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(salaryAddPageParam.getYearMonth())) {
            queryWrapper.eq("b.`YEAR_MONTH`", salaryAddPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryAddPageParam.getIdCardNumber())) {
            queryWrapper.like("b.ID_CARD_NUMBER", AesUtil.encrypt(salaryAddPageParam.getIdCardNumber()));
        }
        if(ObjectUtil.isNotEmpty(salaryAddPageParam.getUserName())) {
            queryWrapper.like("b.USER_NAME", AesUtil.encrypt(salaryAddPageParam.getUserName()));
        }
        if(ObjectUtil.isNotEmpty(salaryAddPageParam.getFirstOrgName())) {
            queryWrapper.like("b.FIRST_ORG_NAME", AesUtil.encrypt(salaryAddPageParam.getFirstOrgName()));
        }
        if(ObjectUtil.isNotEmpty(salaryAddPageParam.getOrgName())) {
            queryWrapper.like("b.ORG_NAME", AesUtil.encrypt(salaryAddPageParam.getOrgName()));
        }
        if(ObjectUtil.isNotEmpty(salaryAddPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("b.FIRST_ORG_NAME",AesUtil.encrypt(salaryAddPageParam.getSearchKey()))
                    .or().like("b.ORG_NAME",AesUtil.encrypt(salaryAddPageParam.getSearchKey()))
                    .or().like("b.USER_NAME",AesUtil.encrypt(salaryAddPageParam.getSearchKey()))
                    .or().like("b.ID_CARD_NUMBER",AesUtil.encrypt(salaryAddPageParam.getSearchKey())));
        }
        if(ObjectUtil.isAllNotEmpty(salaryAddPageParam.getSortField(), salaryAddPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryAddPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryAddPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryAddPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("b.`YEAR_MONTH`");
        }
        Page<SalaryAddVo> result = salaryAddMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
        List<SalaryAddVo> records = AesUtil.decryptGZList(result.getRecords());
        records.sort(Comparator.comparing(SalaryAddVo::getOrgName,Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(SalaryAddVo::getIdCardNumber,Comparator.nullsLast(Comparator.naturalOrder())));
        result.setRecords(records);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalaryAddAddParam salaryAddAddParam) {
        //同一用户，同一年度月份，只能存在一条有效记录
        List<SalaryAdd> oldList = this.list(new QueryWrapper<SalaryAdd>().lambda()
                .eq(SalaryAdd::getStandardId,salaryAddAddParam.getStandardId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("当前员工本年月已存在记录");
        }
        SalaryAdd salaryAdd = BeanUtil.toBean(salaryAddAddParam, SalaryAdd.class);
        this.save(salaryAdd);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalaryAddEditParam salaryAddEditParam) {
        //同一用户，同一年度月份，只能存在一条有效记录
        List<SalaryAdd> oldList = this.list(new QueryWrapper<SalaryAdd>().lambda()
                .eq(SalaryAdd::getStandardId,salaryAddEditParam.getStandardId())
                .ne(SalaryAdd::getId,salaryAddEditParam.getId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("当前员工本年月已存在记录");
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
    public SalaryAddVo detail(SalaryAddIdParam salaryAddIdParam) {
        SalaryAddVo salaryAddVo = salaryAddMapper.getDetailById(salaryAddIdParam.getId());
        return AesUtil.decryptGZ(salaryAddVo);
    }

    @Override
    public SalaryAdd queryEntity(String id) {
        SalaryAdd salaryAdd = this.getById(id);
        if(ObjectUtil.isEmpty(salaryAdd)) {
            throw new CommonException("工资系统-其他费用扣加不存在，id值为：{}", id);
        }
        return salaryAdd;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void copy(CopyParam copyParam) {
        List<String> copyIds = copyParam.getIds();

        // 当前年月【数据复制 from - to】
        String currentYearMonth = copyParam.getToYearMonth();
        if(ObjectUtil.isEmpty(currentYearMonth)) {
            currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        }
        String previousYearMonth = copyParam.getFromYearMonth();
        if(ObjectUtil.isEmpty(previousYearMonth)) {
            previousYearMonth = ComputeUtil.getPreviousYM(currentYearMonth);
        }

        // 1. 获取待复制的源数据【Copy源数据】
        QueryWrapper<SalaryAdd> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("b.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("b.`YEAR_MONTH`",previousYearMonth);
        if(CollUtil.isNotEmpty(copyIds)){
            queryWrapper.in("a.ID",copyIds);
        }
        List<SalaryAddVo> copyAddData = salaryAddMapper.getAddList(queryWrapper);
        if(CollUtil.isEmpty(copyAddData)){
            throw new CommonException("不存在有效{}其他费用数据，无法复制，请检查！",previousYearMonth);
        }

        // 2. 获取本月的工资标准数据【Copy目标月工资标准数据】
        List<SalaryStandard> currentStandardList = salaryStandardService.list(new QueryWrapper<SalaryStandard>().lambda().eq(SalaryStandard::getYearMonth,currentYearMonth));
        if(CollUtil.isEmpty(currentStandardList)){
            throw new CommonException("不存在{}月工资标准数据，无法复制，请检查！",currentYearMonth);
        }

        // 3. copy数据匹配月工资标准，并校验【copy数据匹配校验】
        List<SalaryAddVo> newCopyAddData = BeanUtil.copyToList(copyAddData,SalaryAddVo.class);
        newCopyAddData.forEach(item->{
            SalaryStandard standardTmp = currentStandardList.stream().filter(x->x.getIdCardNumber().equals(item.getIdCardNumber()) && x.getCompany().equals(item.getCompany())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(standardTmp)){
                item.setStandardId(standardTmp.getId()); //新关联外键Id
            }else{
                item.setStandardId(null);  //没有匹配到
            }
        });

        // 4. copy数据校验是否已存在【copy数据唯一性校验】
        QueryWrapper<SalaryAdd> oldQueryWrapper = new QueryWrapper<>();
        oldQueryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        oldQueryWrapper.eq("b.DELETE_FLAG","NOT_DELETE");
        oldQueryWrapper.eq("b.`YEAR_MONTH`",currentYearMonth);
        List<SalaryAddVo> oldAddData = salaryAddMapper.getAddList(oldQueryWrapper);
        //真正要添加的其他费用数据
        List<SalaryAddVo> tmpAddList = new ArrayList<>();
        newCopyAddData.stream().filter(x->ObjectUtil.isNotEmpty(x.getStandardId())).forEach(item->{
            SalaryAddVo oldAddTmp = oldAddData.stream().filter(x->x.getStandardId().equals(item.getStandardId())).findFirst().orElse(null);
            if(ObjectUtil.isEmpty(oldAddTmp)){
                tmpAddList.add(item);
            }
        });

        // 复制的时候，202403 - 202411 工资标准人员不匹配
        if(CollUtil.isEmpty(tmpAddList)){
            throw new CommonException("无有效复制数据");
        }

        //根据tmpAddList获取扣减详情记录 tmpAddList 中旧主键id没变
        List<String> copyAddIds = tmpAddList.stream().map(x->x.getId()).collect(Collectors.toList());
        List<SalaryAddRecord> copyAddRecordData = new ArrayList<>();
        if(CollUtil.isNotEmpty(copyAddIds)){
            copyAddRecordData = salaryAddRecordService.list(new QueryWrapper<SalaryAddRecord>().lambda().in(SalaryAddRecord::getAddId,copyAddIds));
        }

        //赋值新的主键
        tmpAddList.forEach(item->{
            item.setId(IdWorker.getIdStr());         //新主键Id
            item.setCreateTime(null);
            item.setCreateUser(null);
            item.setUpdateUser(null);
            item.setUpdateTime(null);
        });

        //处理记录详情
        copyAddRecordData.forEach(item->{
            SalaryAddVo newAddTmp = tmpAddList.stream().filter(x->x.getOldAddId().equals(item.getAddId())).findFirst().orElse(null);
            item.setAddId(newAddTmp.getId());  //复制数据，赋予新外键Id
            item.setId(null);
            item.setCreateTime(null);
            item.setCreateUser(null);
            item.setUpdateUser(null);
            item.setUpdateTime(null);
        });

        if(CollUtil.isNotEmpty(tmpAddList)){
            List<SalaryAdd> addList = BeanUtil.copyToList(tmpAddList,SalaryAdd.class);
            this.saveBatch(addList);
        }
        if(CollUtil.isNotEmpty(copyAddRecordData)){
            salaryAddRecordService.saveBatch(copyAddRecordData);
        }
    }
}

