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
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryAddRecordMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddRecordAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddRecordEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddRecordIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddRecordPageParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryAddRecordService;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryAddService;
import vip.xiaonuo.sys.modular.hr.salary.util.AesUtil;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryAddRecordVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryAddTotal;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工资系统-其他费用扣加-记录Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:24
 **/
@Service
public class SalaryAddRecordServiceImpl extends ServiceImpl<SalaryAddRecordMapper, SalaryAddRecord> implements SalaryAddRecordService {

    @Resource
    private SalaryAddRecordMapper salaryAddRecordMapper;
    @Resource
    private SalaryAddService salaryAddService;
    @Override
    public Page<SalaryAddRecordVo> page(SalaryAddRecordPageParam salaryAddRecordPageParam) {
        QueryWrapper<SalaryAddRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(salaryAddRecordPageParam.getDictType())) {
            queryWrapper.eq("a.DICT_TYPE", salaryAddRecordPageParam.getDictType());
        }
        if(ObjectUtil.isNotEmpty(salaryAddRecordPageParam.getDictName())) {
            queryWrapper.like("a.DICT_NAME", salaryAddRecordPageParam.getDictName());
        }
        if(ObjectUtil.isNotEmpty(salaryAddRecordPageParam.getYearMonth())){
            queryWrapper.eq("c.`YEAR_MONTH`",salaryAddRecordPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryAddRecordPageParam.getIdCardNumber())){
            queryWrapper.like("c.ID_CARD_NUMBER", AesUtil.encrypt(salaryAddRecordPageParam.getIdCardNumber()));
        }
        if(ObjectUtil.isNotEmpty(salaryAddRecordPageParam.getUserName())){
            queryWrapper.like("c.USER_NAME",AesUtil.encrypt(salaryAddRecordPageParam.getUserName()));
        }
        if(ObjectUtil.isNotEmpty(salaryAddRecordPageParam.getItemType())){
            queryWrapper.eq("a.ITEM_TYPE",salaryAddRecordPageParam.getItemType());
        }
        if(ObjectUtil.isNotEmpty(salaryAddRecordPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("c.ID_CARD_NUMBER",AesUtil.encrypt(salaryAddRecordPageParam.getSearchKey()))
                    .or().like("c.USER_NAME",AesUtil.encrypt(salaryAddRecordPageParam.getSearchKey()))
                    .or().like("c.FIRST_ORG_NAME",AesUtil.encrypt(salaryAddRecordPageParam.getSearchKey()))
                    .or().like("c.ORG_NAME",AesUtil.encrypt(salaryAddRecordPageParam.getSearchKey()))
                    .or().like("a.DICT_NAME",salaryAddRecordPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(salaryAddRecordPageParam.getSortField(), salaryAddRecordPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryAddRecordPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryAddRecordPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryAddRecordPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("c.`YEAR_MONTH`");
        }
        Page<SalaryAddRecordVo> result = salaryAddRecordMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
        List<SalaryAddRecordVo> records = AesUtil.decryptGZList(result.getRecords());
        records.sort(Comparator.comparing(SalaryAddRecordVo::getOrgName,Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(SalaryAddRecordVo::getIdCardNumber,Comparator.nullsLast(Comparator.naturalOrder())));
        result.setRecords(records);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalaryAddRecordAddParam salaryAddRecordAddParam) {
        SalaryAddRecord salaryAddRecord = BeanUtil.toBean(salaryAddRecordAddParam, SalaryAddRecord.class);
        this.save(salaryAddRecord);
        //统计嘉奖金额|扣减金额，反更新到员工嘉奖扣除记录中
        String addId = salaryAddRecordAddParam.getAddId();
        if(ObjectUtil.isNotEmpty(addId)) {
            SalaryAddTotal salaryAddTotal = salaryAddRecordMapper.getTotalByAddId(addId);
            SalaryAdd salaryAdd = salaryAddService.queryEntity(addId);
            salaryAdd.setAddAmount(ObjectUtil.isNotEmpty(salaryAddTotal) ? salaryAddTotal.getAddAmount(): BigDecimal.ZERO);
            salaryAdd.setMinusAmount(ObjectUtil.isNotEmpty(salaryAddTotal) ?salaryAddTotal.getMinusAmount(): BigDecimal.ZERO);
            salaryAdd.setLastAddAmount(ObjectUtil.isNotEmpty(salaryAddTotal) ?salaryAddTotal.getLastAddAmount(): BigDecimal.ZERO);
            salaryAdd.setLastMinusAmount(ObjectUtil.isNotEmpty(salaryAddTotal) ?salaryAddTotal.getLastMinusAmount(): BigDecimal.ZERO);
            salaryAddService.updateById(salaryAdd);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalaryAddRecordEditParam salaryAddRecordEditParam) {
        SalaryAddRecord salaryAddRecord = this.queryEntity(salaryAddRecordEditParam.getId());
        BeanUtil.copyProperties(salaryAddRecordEditParam, salaryAddRecord);
        this.updateById(salaryAddRecord);
        //统计嘉奖金额|扣减金额，反更新到员工嘉奖扣除记录中
        String addId = salaryAddRecordEditParam.getAddId();
        if(ObjectUtil.isNotEmpty(addId)) {
            SalaryAddTotal salaryAddTotal = salaryAddRecordMapper.getTotalByAddId(addId);
            SalaryAdd salaryAdd = salaryAddService.queryEntity(addId);
            salaryAdd.setAddAmount(ObjectUtil.isNotEmpty(salaryAddTotal) ? salaryAddTotal.getAddAmount(): BigDecimal.ZERO);
            salaryAdd.setMinusAmount(ObjectUtil.isNotEmpty(salaryAddTotal) ?salaryAddTotal.getMinusAmount(): BigDecimal.ZERO);
            salaryAdd.setLastAddAmount(ObjectUtil.isNotEmpty(salaryAddTotal) ?salaryAddTotal.getLastAddAmount(): BigDecimal.ZERO);
            salaryAdd.setLastMinusAmount(ObjectUtil.isNotEmpty(salaryAddTotal) ?salaryAddTotal.getLastMinusAmount(): BigDecimal.ZERO);
            salaryAddService.updateById(salaryAdd);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalaryAddRecordIdParam> salaryAddRecordIdParamList) {
        // 执行删除
        List<String> delIds = CollStreamUtil.toList(salaryAddRecordIdParamList, SalaryAddRecordIdParam::getId);
        // 删除集合的外键 - 嘉奖扣除记录
        List<String> addIds = this.listByIds(delIds).stream().map(x->x.getAddId()).collect(Collectors.toList());
        // 删除记录
        this.removeByIds(delIds);
        //统计嘉奖金额|扣减金额，反更新到员工嘉奖扣除记录中
        if(CollUtil.isNotEmpty(addIds)) {
            List<SalaryAdd> salaryAddList = new ArrayList<>();
            for (String addId:addIds) {
                SalaryAddTotal salaryAddTotal = salaryAddRecordMapper.getTotalByAddId(addId);
                SalaryAdd salaryAdd = salaryAddService.queryEntity(addId);
                salaryAdd.setAddAmount(ObjectUtil.isNotEmpty(salaryAddTotal) ? salaryAddTotal.getAddAmount(): BigDecimal.ZERO);
                salaryAdd.setMinusAmount(ObjectUtil.isNotEmpty(salaryAddTotal) ?salaryAddTotal.getMinusAmount(): BigDecimal.ZERO);
                salaryAdd.setLastAddAmount(ObjectUtil.isNotEmpty(salaryAddTotal) ?salaryAddTotal.getLastAddAmount(): BigDecimal.ZERO);
                salaryAdd.setLastMinusAmount(ObjectUtil.isNotEmpty(salaryAddTotal) ?salaryAddTotal.getLastMinusAmount(): BigDecimal.ZERO);
                salaryAddList.add(salaryAdd);
            }
            salaryAddService.updateBatchById(salaryAddList);
        }
    }

    @Override
    public SalaryAddRecordVo detail(SalaryAddRecordIdParam salaryAddRecordIdParam) {
        SalaryAddRecordVo salaryAddRecordVo = salaryAddRecordMapper.getDetailById(salaryAddRecordIdParam.getId());
        return AesUtil.decryptGZ(salaryAddRecordVo);
    }

    @Override
    public SalaryAddRecord queryEntity(String id) {
        SalaryAddRecord salaryAddRecord = this.getById(id);
        if(ObjectUtil.isEmpty(salaryAddRecord)) {
            throw new CommonException("工资系统-其他费用扣加-记录不存在，id值为：{}", id);
        }
        return salaryAddRecord;
    }
}
