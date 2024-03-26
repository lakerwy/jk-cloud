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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryGjj;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryGjjMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryGjjAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryGjjEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryGjjIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryGjjPageParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryGjjService;
import vip.xiaonuo.sys.modular.hr.salary.util.AesUtil;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryAttendVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryGjjVo;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工资系统-公积金明细Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:28
 **/
@Service
public class SalaryGjjServiceImpl extends ServiceImpl<SalaryGjjMapper, SalaryGjj> implements SalaryGjjService {

    @Resource
    private SalaryGjjMapper salaryGjjMapper;

    @Override
    public Page<SalaryGjjVo> page(SalaryGjjPageParam salaryGjjPageParam) {
        QueryWrapper<SalaryGjj> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(salaryGjjPageParam.getYearMonth())) {
            queryWrapper.eq("b.`YEAR_MONTH`", salaryGjjPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryGjjPageParam.getIdCardNumber())) {
            queryWrapper.like("b.ID_CARD_NUMBER", AesUtil.encrypt(salaryGjjPageParam.getIdCardNumber()));
        }
        if(ObjectUtil.isNotEmpty(salaryGjjPageParam.getUserName())) {
            queryWrapper.like("b.USER_NAME", AesUtil.encrypt(salaryGjjPageParam.getUserName()));
        }
        if(ObjectUtil.isNotEmpty(salaryGjjPageParam.getFirstOrgName())) {
            queryWrapper.like("b.FIRST_ORG_NAME", AesUtil.encrypt(salaryGjjPageParam.getFirstOrgName()));
        }
        if(ObjectUtil.isNotEmpty(salaryGjjPageParam.getOrgName())) {
            queryWrapper.like("b.ORG_NAME", AesUtil.encrypt(salaryGjjPageParam.getOrgName()));
        }
        if(ObjectUtil.isNotEmpty(salaryGjjPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("b.ID_CARD_NUMBER",AesUtil.encrypt(salaryGjjPageParam.getSearchKey()))
                    .or().like("b.USER_NAME",AesUtil.encrypt(salaryGjjPageParam.getSearchKey()))
                    .or().like("b.FIRST_ORG_NAME",AesUtil.encrypt(salaryGjjPageParam.getSearchKey()))
                    .or().like("b.ORG_NAME",AesUtil.encrypt(salaryGjjPageParam.getSearchKey())));
        }
        if(ObjectUtil.isAllNotEmpty(salaryGjjPageParam.getSortField(), salaryGjjPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryGjjPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryGjjPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryGjjPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("b.`YEAR_MONTH`");
        }
        Page<SalaryGjjVo> result = salaryGjjMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
        List<SalaryGjjVo> records = AesUtil.decryptGZList(result.getRecords());
        records.sort(Comparator.comparing(SalaryGjjVo::getOrgName,Comparator.nullsLast(Comparator.naturalOrder())));
        result.setRecords(records);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalaryGjjAddParam salaryGjjAddParam) {
        //同一年月，同一员工，同一缴纳单位，只存在一条记录
        List<SalaryGjj> oldList = this.list(new QueryWrapper<SalaryGjj>().lambda()
                        .eq(SalaryGjj::getUnitName,salaryGjjAddParam.getUnitName())
                        .eq(SalaryGjj::getStandardId,salaryGjjAddParam.getStandardId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("当前员工本年月本公司已存在记录");
        }
        SalaryGjj salaryGjj = BeanUtil.toBean(salaryGjjAddParam, SalaryGjj.class);
        this.save(salaryGjj);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalaryGjjEditParam salaryGjjEditParam) {
        //同一年月，同一员工，同一缴纳单位，只存在一条记录
        List<SalaryGjj> oldList = this.list(new QueryWrapper<SalaryGjj>().lambda()
                .eq(SalaryGjj::getUnitName,salaryGjjEditParam.getUnitName())
                .eq(SalaryGjj::getStandardId,salaryGjjEditParam.getStandardId())
                .ne(SalaryGjj::getId,salaryGjjEditParam.getId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("当前员工本年月本公司已存在记录");
        }
        SalaryGjj salaryGjj = this.queryEntity(salaryGjjEditParam.getId());
        BeanUtil.copyProperties(salaryGjjEditParam, salaryGjj);
        this.updateById(salaryGjj);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalaryGjjIdParam> salaryGjjIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(salaryGjjIdParamList, SalaryGjjIdParam::getId));
    }

    @Override
    public SalaryGjjVo detail(SalaryGjjIdParam salaryGjjIdParam) {
        SalaryGjjVo salaryGjjVo = salaryGjjMapper.getDetailById(salaryGjjIdParam.getId());
        return AesUtil.decryptGZ(salaryGjjVo);
    }

    @Override
    public SalaryGjj queryEntity(String id) {
        SalaryGjj salaryGjj = this.getById(id);
        if(ObjectUtil.isEmpty(salaryGjj)) {
            throw new CommonException("工资系统-公积金明细不存在，id值为：{}", id);
        }
        return salaryGjj;
    }
}
