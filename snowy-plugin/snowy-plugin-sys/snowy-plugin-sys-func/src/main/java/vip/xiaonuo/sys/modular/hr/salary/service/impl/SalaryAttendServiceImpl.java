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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.emp.entity.HrEmpAttend;
import vip.xiaonuo.sys.modular.hr.emp.service.HrEmpAttendService;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryAttend;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryAttendMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAttendAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAttendEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAttendIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAttendPageParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.SearchParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryAttendService;
import vip.xiaonuo.sys.modular.hr.salary.util.AesUtil;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryAttendVo;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工资系统-月度考勤表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:26
 **/
@Service
public class SalaryAttendServiceImpl extends ServiceImpl<SalaryAttendMapper, SalaryAttend> implements SalaryAttendService {

    @Resource
    private SalaryAttendMapper salaryAttendMapper;

    @Resource
    private HrEmpAttendService hrEmpAttendService;

    @Override
    public Page<SalaryAttendVo> page(SalaryAttendPageParam salaryAttendPageParam) {
        QueryWrapper<SalaryAttend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(salaryAttendPageParam.getYearMonth())) {
            queryWrapper.eq("b.`YEAR_MONTH`", salaryAttendPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryAttendPageParam.getIdCardNumber())) {
            queryWrapper.like("b.ID_CARD_NUMBER", AesUtil.encrypt(salaryAttendPageParam.getIdCardNumber()));
        }
        if(ObjectUtil.isNotEmpty(salaryAttendPageParam.getUserName())) {
            queryWrapper.like("b.USER_NAME", AesUtil.encrypt(salaryAttendPageParam.getUserName()));
        }
        if(ObjectUtil.isNotEmpty(salaryAttendPageParam.getFirstOrgName())) {
            queryWrapper.like("b.FIRST_ORG_NAME", AesUtil.encrypt(salaryAttendPageParam.getFirstOrgName()));
        }
        if(ObjectUtil.isNotEmpty(salaryAttendPageParam.getOrgName())) {
            queryWrapper.like("b.ORG_NAME", AesUtil.encrypt(salaryAttendPageParam.getOrgName()));
        }
        if(ObjectUtil.isNotEmpty(salaryAttendPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("b.ID_CARD_NUMBER",AesUtil.encrypt(salaryAttendPageParam.getSearchKey()))
                    .or().like("b.USER_NAME",AesUtil.encrypt(salaryAttendPageParam.getSearchKey()))
                    .or().like("b.FIRST_ORG_NAME",AesUtil.encrypt(salaryAttendPageParam.getSearchKey()))
                    .or().like("b.ORG_NAME",AesUtil.encrypt(salaryAttendPageParam.getSearchKey())));
        }
        if(ObjectUtil.isAllNotEmpty(salaryAttendPageParam.getSortField(), salaryAttendPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryAttendPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryAttendPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryAttendPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("b.`YEAR_MONTH`");
        }
        Page<SalaryAttendVo> result = salaryAttendMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
        List<SalaryAttendVo> records = AesUtil.decryptGZList(result.getRecords());
        records.sort(Comparator.comparing(SalaryAttendVo::getOrgName,Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(SalaryAttendVo::getIdCardNumber,Comparator.nullsLast(Comparator.naturalOrder())));
        result.setRecords(records);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalaryAttendAddParam salaryAttendAddParam) {
        //同一年月，同一员工，只存在一条记录
        List<SalaryAttend> oldList = this.list(new QueryWrapper<SalaryAttend>().lambda()
                .eq(SalaryAttend::getStandardId,salaryAttendAddParam.getStandardId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("当前员工本年月已存在记录");
        }
        SalaryAttend salaryAttend = BeanUtil.toBean(salaryAttendAddParam, SalaryAttend.class);
        this.save(salaryAttend);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalaryAttendEditParam salaryAttendEditParam) {
        //同一年月，同一员工，只存在一条考核记录
        List<SalaryAttend> oldList = this.list(new QueryWrapper<SalaryAttend>().lambda()
                .eq(SalaryAttend::getStandardId,salaryAttendEditParam.getStandardId())
                .ne(SalaryAttend::getId,salaryAttendEditParam.getId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("当前员工本年月已存在记录");
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
    public SalaryAttendVo detail(SalaryAttendIdParam salaryAttendIdParam) {
        SalaryAttendVo salaryAttendVo = salaryAttendMapper.getDetailById(salaryAttendIdParam.getId());
        return AesUtil.decryptGZ(salaryAttendVo);
    }

    @Override
    public SalaryAttend queryEntity(String id) {
        SalaryAttend salaryAttend = this.getById(id);
        if(ObjectUtil.isEmpty(salaryAttend)) {
            throw new CommonException("工资系统-月度考勤表不存在，id值为：{}", id);
        }
        return salaryAttend;
    }

    @Override
    public List<SalaryAttendVo> empAttend(SearchParam param) {
        String yearMonth = param.getYearMonth();
        String encryptUserId = AesUtil.encrypt(StpLoginUserUtil.getLoginUser().getId());
        List<SalaryAttendVo> salaryAttendVos = salaryAttendMapper.getEmpAttendList(yearMonth,encryptUserId);
        List<SalaryAttendVo> result = AesUtil.decryptGZList(salaryAttendVos);
        //员工考勤部分
        List<HrEmpAttend> empAttendList = hrEmpAttendService.list(new LambdaQueryWrapper<HrEmpAttend>().eq(HrEmpAttend::getUserId,StpLoginUserUtil.getLoginUser().getId()).likeRight(HrEmpAttend::getYearMonth,yearMonth));
        List<SalaryAttendVo> empList = BeanUtil.copyToList(empAttendList,SalaryAttendVo.class);
        result.addAll(empList);
        return result.stream().sorted(Comparator.comparing(x->x.getYearMonth())).collect(Collectors.toList());
    }

}
