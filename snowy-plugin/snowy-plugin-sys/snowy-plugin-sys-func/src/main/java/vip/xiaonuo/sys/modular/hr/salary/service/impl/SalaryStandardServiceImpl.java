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
import vip.xiaonuo.sys.modular.hr.salary.enums.SalaryTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.enums.UserTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryStandardMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.*;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryStandardService;
import vip.xiaonuo.sys.modular.hr.salary.vo.PersonnelInfoVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryStandardVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.ThreeSalaryStandardVo;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工资系统-月度工资标准Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:36
 **/
@Service
public class SalaryStandardServiceImpl extends ServiceImpl<SalaryStandardMapper, SalaryStandard> implements SalaryStandardService {

    @Resource
    private SalaryStandardMapper salaryStandardMapper;

    @Override
    public Page<SalaryStandard> page(SalaryStandardPageParam salaryStandardPageParam) {
        QueryWrapper<SalaryStandard> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getType())) {
            queryWrapper.lambda().eq(SalaryStandard::getType, salaryStandardPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getUserType())){
            queryWrapper.lambda().eq(SalaryStandard::getUserType,salaryStandardPageParam.getUserType());
        }
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getYearMonth())) {
            queryWrapper.lambda().eq(SalaryStandard::getYearMonth, salaryStandardPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(SalaryStandard::getIdCardNumber, salaryStandardPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getUserName())) {
            queryWrapper.lambda().like(SalaryStandard::getUserName, salaryStandardPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getFirstOrgName())) {
            queryWrapper.lambda().like(SalaryStandard::getFirstOrgName, salaryStandardPageParam.getFirstOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getOrgName())) {
            queryWrapper.lambda().like(SalaryStandard::getOrgName, salaryStandardPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getPosition())) {
            queryWrapper.lambda().like(SalaryStandard::getPosition, salaryStandardPageParam.getPosition());
        }
        if(ObjectUtil.isAllNotEmpty(salaryStandardPageParam.getSortField(), salaryStandardPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryStandardPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryStandardPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryStandardPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(SalaryStandard::getYearMonth).orderByAsc(SalaryStandard::getIdCardNumber);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalaryStandardAddParam salaryStandardAddParam) {
        //同一工资类别，同一年月，同一身份证，只存在一条记录
        List<SalaryStandard> oldList = this.list(new QueryWrapper<SalaryStandard>().lambda()
                .eq(SalaryStandard::getType,salaryStandardAddParam.getType())
                .eq(SalaryStandard::getYearMonth,salaryStandardAddParam.getYearMonth())
                .eq(SalaryStandard::getIdCardNumber,salaryStandardAddParam.getIdCardNumber()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("{}年月{}已存在记录",salaryStandardAddParam.getYearMonth(),salaryStandardAddParam.getUserName());
        }
        SalaryStandard salaryStandard = BeanUtil.toBean(salaryStandardAddParam, SalaryStandard.class);
        this.save(salaryStandard);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalaryStandardEditParam salaryStandardEditParam) {
        //同一工资类别，同一年月，同一身份证，只存在一条记录
        List<SalaryStandard> oldList = this.list(new QueryWrapper<SalaryStandard>().lambda()
                .eq(SalaryStandard::getType,salaryStandardEditParam.getType())
                .eq(SalaryStandard::getYearMonth,salaryStandardEditParam.getYearMonth())
                .eq(SalaryStandard::getIdCardNumber,salaryStandardEditParam.getIdCardNumber())
                .ne(SalaryStandard::getId,salaryStandardEditParam.getId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("{}年月{}已存在记录",salaryStandardEditParam.getYearMonth(),salaryStandardEditParam.getUserName());
        }
        SalaryStandard salaryStandard = this.queryEntity(salaryStandardEditParam.getId());
        BeanUtil.copyProperties(salaryStandardEditParam, salaryStandard);
        this.updateById(salaryStandard);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalaryStandardIdParam> salaryStandardIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(salaryStandardIdParamList, SalaryStandardIdParam::getId));
    }

    @Override
    public SalaryStandard detail(SalaryStandardIdParam salaryStandardIdParam) {
        return this.queryEntity(salaryStandardIdParam.getId());
    }

    @Override
    public SalaryStandard queryEntity(String id) {
        SalaryStandard salaryStandard = this.getById(id);
        if(ObjectUtil.isEmpty(salaryStandard)) {
            throw new CommonException("工资系统-月度工资标准不存在，id值为：{}", id);
        }
        return salaryStandard;
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
        //1. 判断当前年月是否已存在工资标准数据（存在，则不可复制）
        List<SalaryStandard> currentData = this.list(new QueryWrapper<SalaryStandard>().lambda()
                .eq(SalaryStandard::getType, SalaryTypeEnum.STANDARD.getValue()).eq(SalaryStandard::getYearMonth,currentYearMonth));
        if(CollUtil.isNotEmpty(currentData)){
            throw new CommonException("已存在{}月工资标准数据",currentYearMonth);
        }
        //2. 判断上年月是否存在工资标准数据（不存在，则无法复制）
        List<SalaryStandard> previousData = this.list(new QueryWrapper<SalaryStandard>().lambda()
                .eq(SalaryStandard::getType, SalaryTypeEnum.STANDARD.getValue()).eq(SalaryStandard::getYearMonth,previousYearMonth));
        if(CollUtil.isEmpty(previousData)){
            throw new CommonException("不存在{}月工资标准数据",previousYearMonth);
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

    @Override
    public List<PersonnelInfoVo> personnelList(PersonnelParam personnelParam) {
        return salaryStandardMapper.getPersonnelList(personnelParam);
    }

    @Override
    public List<SalaryStandardVo> listByYearMonth(StandardParam standardParam) {
        if(ObjectUtil.isEmpty(standardParam.getYearMonth())){
            throw new CommonException("所属年月必传参数");
        }
        QueryWrapper<SalaryStandard> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SalaryStandard::getType,SalaryTypeEnum.STANDARD.getValue());
        queryWrapper.lambda().eq(SalaryStandard::getYearMonth,standardParam.getYearMonth());

        List<SalaryStandard> result = this.list(queryWrapper);
        return BeanUtil.copyToList(result,SalaryStandardVo.class);
    }

    @Override
    public List<SalaryStandardVo> getStandardAllList(String type, List<String> yearMonths) {
        List<SalaryStandardVo> result = new ArrayList<>();
        if(CollUtil.isEmpty(yearMonths)){
            return result;
        }
        List<SalaryStandard> standardList = this.list(new QueryWrapper<SalaryStandard>().lambda()
                .in(SalaryStandard::getYearMonth,yearMonths)
                .eq(SalaryStandard::getType,type));
        result = BeanUtil.copyToList(standardList,SalaryStandardVo.class);
        return result;
    }

    @Override
    public ThreeSalaryStandardVo threePageList(ThreePageParam threePageParam) {
        ThreeSalaryStandardVo result = new ThreeSalaryStandardVo();
        if(ObjectUtil.isEmpty(threePageParam.getYearMonth())){
            throw new CommonException("所属年月必传");
        }
        QueryWrapper<SalaryStandard> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SalaryStandard::getYearMonth,threePageParam.getYearMonth());
        if(ObjectUtil.isNotEmpty(threePageParam.getType())){
            queryWrapper.lambda().eq(SalaryStandard::getType,threePageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(threePageParam.getUserType())){
            queryWrapper.lambda().eq(SalaryStandard::getUserType,threePageParam.getUserType());
        }
        if(ObjectUtil.isNotEmpty(threePageParam.getIdCardNumber())){
            queryWrapper.lambda().like(SalaryStandard::getIdCardNumber,threePageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(threePageParam.getOrgName())){
            queryWrapper.lambda().like(SalaryStandard::getOrgName,threePageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(threePageParam.getFirstOrgName())){
            queryWrapper.lambda().like(SalaryStandard::getFirstOrgName,threePageParam.getFirstOrgName());
        }
        if(ObjectUtil.isNotEmpty(threePageParam.getUserName())){
            queryWrapper.lambda().like(SalaryStandard::getUserName,threePageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(threePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(SalaryStandard::getUserName,threePageParam.getSearchKey())
                    .or().like(SalaryStandard::getIdCardNumber,threePageParam.getSearchKey())
                    .or().like(SalaryStandard::getOrgName,threePageParam.getSearchKey())
                    .or().like(SalaryStandard::getFirstOrgName,threePageParam.getSearchKey()));
        }
        queryWrapper.lambda().orderByDesc(SalaryStandard::getYearMonth).orderByAsc(SalaryStandard::getIdCardNumber);
        List<SalaryStandard> standardList = this.list(queryWrapper);
        result.setZbList(standardList.stream().filter(x-> UserTypeEnum.ZB.getValue().equals(x.getUserType())).collect(Collectors.toList()));
        result.setFgsList(standardList.stream().filter(x-> UserTypeEnum.FGS.getValue().equals(x.getUserType())).collect(Collectors.toList()));
        result.setWpList(standardList.stream().filter(x-> UserTypeEnum.WP.getValue().equals(x.getUserType())).collect(Collectors.toList()));
        return result;
    }
}
