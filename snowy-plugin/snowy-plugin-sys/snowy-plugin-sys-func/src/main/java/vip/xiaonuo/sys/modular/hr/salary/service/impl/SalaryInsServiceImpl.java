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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryIns;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryInsMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryInsAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryInsEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryInsIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryInsPageParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryInsService;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryInsVo;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工资系统-社保明细Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:33
 **/
@Service
public class SalaryInsServiceImpl extends ServiceImpl<SalaryInsMapper, SalaryIns> implements SalaryInsService {

    @Resource
    private SalaryInsMapper salaryInsMapper;

    @Override
    public Page<SalaryInsVo> page(SalaryInsPageParam salaryInsPageParam) {
        QueryWrapper<SalaryIns> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(salaryInsPageParam.getYearMonth())) {
            queryWrapper.eq("b.`YEAR_MONTH`", salaryInsPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryInsPageParam.getIdCardNumber())) {
            queryWrapper.like("b.ID_CARD_NUMBER", salaryInsPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(salaryInsPageParam.getUserName())) {
            queryWrapper.like("b.USER_NAME", salaryInsPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(salaryInsPageParam.getFirstOrgName())) {
            queryWrapper.like("b.FIRST_ORG_NAME", salaryInsPageParam.getFirstOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryInsPageParam.getOrgName())) {
            queryWrapper.like("b.ORG_NAME", salaryInsPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryInsPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("b.ID_CARD_NUMBER",salaryInsPageParam.getSearchKey())
                    .or().like("b.USER_NAME",salaryInsPageParam.getSearchKey())
                    .or().like("b.FIRST_ORG_NAME",salaryInsPageParam.getSearchKey())
                    .or().like("b.ORG_NAME",salaryInsPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(salaryInsPageParam.getSortField(), salaryInsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryInsPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryInsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryInsPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("b.`YEAR_MONTH`").orderByAsc("b.ID_CARD_NUMBER");
        }
        return salaryInsMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalaryInsAddParam salaryInsAddParam) {
        //同一年月，同一员工，只存在一条记录
        List<SalaryIns> oldList = this.list(new QueryWrapper<SalaryIns>().lambda()
                .eq(SalaryIns::getStandardId,salaryInsAddParam.getStandardId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("当前员工本年月已存在记录");
        }
        SalaryIns salaryIns = BeanUtil.toBean(salaryInsAddParam, SalaryIns.class);
        this.save(salaryIns);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalaryInsEditParam salaryInsEditParam) {
        //同一年月，同一花名册Id，只存在一条记录
        List<SalaryIns> oldList = this.list(new QueryWrapper<SalaryIns>().lambda()
                .eq(SalaryIns::getStandardId,salaryInsEditParam.getStandardId())
                .ne(SalaryIns::getId,salaryInsEditParam.getId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("当前员工本年月已存在记录");
        }
        SalaryIns salaryIns = this.queryEntity(salaryInsEditParam.getId());
        BeanUtil.copyProperties(salaryInsEditParam, salaryIns);
        this.updateById(salaryIns);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalaryInsIdParam> salaryInsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(salaryInsIdParamList, SalaryInsIdParam::getId));
    }

    @Override
    public SalaryInsVo detail(SalaryInsIdParam salaryInsIdParam) {
        return salaryInsMapper.getDetailById(salaryInsIdParam.getId());
    }

    @Override
    public SalaryIns queryEntity(String id) {
        SalaryIns salaryIns = this.getById(id);
        if(ObjectUtil.isEmpty(salaryIns)) {
            throw new CommonException("工资系统-社保明细不存在，id值为：{}", id);
        }
        return salaryIns;
    }
}
