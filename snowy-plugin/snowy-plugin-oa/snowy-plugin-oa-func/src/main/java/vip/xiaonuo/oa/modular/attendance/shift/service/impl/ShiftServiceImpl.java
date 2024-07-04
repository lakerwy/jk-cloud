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
package vip.xiaonuo.oa.modular.attendance.shift.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.oa.core.constant.AttCacheConstants;
import vip.xiaonuo.oa.modular.attendance.shift.mapper.ShiftMapper;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftAddParam;
import vip.xiaonuo.oa.modular.attendance.shift.service.ShiftService;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Rule;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Shift;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftEditParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftIdParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftPageParam;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 班次表，用于存储班次的基本信息Service接口实现类
 *
 * @author mayike
 * @date  2024/06/11 09:32
 **/
@Service
public class ShiftServiceImpl extends ServiceImpl<ShiftMapper, Shift> implements ShiftService {

    @Resource
    private CommonCacheOperator cacheOperator;

    @Override
    public Page<Shift> page(ShiftPageParam shiftPageParam) {
        QueryWrapper<Shift> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(shiftPageParam.getShiftName())) {
            queryWrapper.lambda().like(Shift::getShiftName, shiftPageParam.getShiftName());
        }
        if(ObjectUtil.isAllNotEmpty(shiftPageParam.getSortField(), shiftPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(shiftPageParam.getSortOrder());
            queryWrapper.orderBy(true, shiftPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(shiftPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Shift::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ShiftAddParam shiftAddParam) {
        checkShiftName(shiftAddParam.getShiftName());
        List<Rule> rules = getShiftCheckTimes(shiftAddParam.getRules());
        Shift shift = BeanUtil.toBean(shiftAddParam, Shift.class);
        shift.setRules(rules);
        shift.setDefaultFlag(false);
        shift.setCheckNum(rules.size());
        this.save(shift);
        cacheOperator.put(AttCacheConstants.ATT_SHIFT_CACHE_PRE+shift.getId(),shift);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ShiftEditParam shiftEditParam) {
        Shift shift = this.queryEntity(shiftEditParam.getId());
        checkShiftName(shiftEditParam.getShiftName());
        BeanUtil.copyProperties(shiftEditParam, shift);
        if(CollUtil.isNotEmpty(shiftEditParam.getRules())){
            List<Rule> rules = getShiftCheckTimes(shiftEditParam.getRules());
            shift.setRules(rules);
            shift.setCheckNum(rules.size());
        }
        this.updateById(shift);
        cacheOperator.put(AttCacheConstants.ATT_SHIFT_CACHE_PRE+shift.getId(),shift);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ShiftIdParam> shiftIdParamList) {
        //todo
        /**
         * 删除前先判断考勤组中是否已关联了排班
         */
        this.removeByIds(CollStreamUtil.toList(shiftIdParamList, ShiftIdParam::getId));
        List<String> collect = shiftIdParamList.stream().map(ShiftIdParam::getId).collect(Collectors.toList());
        cacheOperator.remove(collect);
    }

    @Override
    public Shift detail(ShiftIdParam shiftIdParam) {
        Object o = cacheOperator.get(AttCacheConstants.ATT_SHIFT_CACHE_PRE + shiftIdParam.getId());
        if(ObjectUtil.isNull(o)){
            Shift shift = this.queryEntity(shiftIdParam.getId());
            cacheOperator.put(AttCacheConstants.ATT_SHIFT_CACHE_PRE+shift.getId(),shift);
            return shift;
        }
        return (Shift) o;
    }

    @Override
    public Shift queryEntity(String id) {
        Shift shift = this.getById(id);
        if(ObjectUtil.isEmpty(shift)) {
            throw new CommonException("班次表，用于存储班次的基本信息不存在，id值为：{}", id);
        }
        return shift;
    }

    @Override
    public void setDefaultShift(ShiftIdParam shiftIdParam) {
        boolean checkDefault = checkDefault(shiftIdParam.getId());
        if(checkDefault){
            Shift shift = this.queryEntity(shiftIdParam.getId());
            update(new LambdaUpdateWrapper<Shift>().eq(Shift::getId,shiftIdParam.getId()).set(Shift::getDefaultFlag,true));
            shift.setDefaultFlag(true);
            cacheOperator.put(AttCacheConstants.ATT_SHIFT_CACHE_PRE+shift.getId(),shift);
        }else {
            throw new CommonException("已有默认班次，请取消默认班次后，重新设置.");
        }

    }

    private void checkShiftName(String shiftName) {
        Shift existShift = this.getOne(new LambdaQueryWrapper<Shift>().eq(Shift::getShiftName, shiftName));
        if(ObjectUtil.isNotEmpty(existShift)){
            throw new CommonException("名称为{}的班次已存在.", shiftName);
        }
    }

    private static @NotNull List<Rule> getShiftCheckTimes(List<Rule> shiftCheckTimes) {
        shiftCheckTimes = shiftCheckTimes
                .stream()
                .peek(Rule::isValid)
                .sorted(Comparator.comparing(Rule::getNormal)).collect(Collectors.toList());
        return shiftCheckTimes;
    }

    /**
     * 判断该班次是否可以被设置成默认班次
     * 默认得有且只有一个
     * 当发现默认得是当前自己得时候，允许再次设置
     * 若没有默认得话，也可以设置
     * @return
     */
    private boolean checkDefault(String shiftId){
        List<Shift> list = list(new LambdaQueryWrapper<Shift>().eq(Shift::getDefaultFlag, true));
        if(CollUtil.isNotEmpty(list)){
            Set<String> collect = list.stream().map(Shift::getId).collect(Collectors.toSet());
            return collect.contains(shiftId);
        }else {
            return true;
        }
    }
}
