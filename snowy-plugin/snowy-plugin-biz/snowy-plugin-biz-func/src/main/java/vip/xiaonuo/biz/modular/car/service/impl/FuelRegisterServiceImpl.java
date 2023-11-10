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
package vip.xiaonuo.biz.modular.car.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.car.entity.FuelRegister;
import vip.xiaonuo.biz.modular.car.mapper.FuelRegisterMapper;
import vip.xiaonuo.biz.modular.car.param.FuelRegisterAddParam;
import vip.xiaonuo.biz.modular.car.param.FuelRegisterEditParam;
import vip.xiaonuo.biz.modular.car.param.FuelRegisterIdParam;
import vip.xiaonuo.biz.modular.car.param.FuelRegisterPageParam;
import vip.xiaonuo.biz.modular.car.service.FuelRegisterService;

import java.util.List;

/**
 * CAR-加油记录Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/08 16:01
 **/

@DS("slave_1")
@Service
public class FuelRegisterServiceImpl extends ServiceImpl<FuelRegisterMapper, FuelRegister> implements FuelRegisterService {

    @Override
    public Page<FuelRegister> page(FuelRegisterPageParam fuelRegisterPageParam) {
        QueryWrapper<FuelRegister> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(fuelRegisterPageParam.getSn())) {
            queryWrapper.lambda().like(FuelRegister::getSn, fuelRegisterPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(fuelRegisterPageParam.getOpt())) {
            queryWrapper.lambda().like(FuelRegister::getOpt, fuelRegisterPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(fuelRegisterPageParam.getStartOptDate()) && ObjectUtil.isNotEmpty(fuelRegisterPageParam.getEndOptDate())) {
            queryWrapper.lambda().between(FuelRegister::getOptDate, fuelRegisterPageParam.getStartOptDate(), fuelRegisterPageParam.getEndOptDate());
        }
        if(ObjectUtil.isNotEmpty(fuelRegisterPageParam.getLicenseNo())) {
            queryWrapper.lambda().like(FuelRegister::getLicenseNo, fuelRegisterPageParam.getLicenseNo());
        }
        if(ObjectUtil.isNotEmpty(fuelRegisterPageParam.getOilCard())) {
            queryWrapper.lambda().like(FuelRegister::getOilCard, fuelRegisterPageParam.getOilCard());
        }
        if(ObjectUtil.isNotEmpty(fuelRegisterPageParam.getFuelLable())) {
            queryWrapper.lambda().eq(FuelRegister::getFuelLable, fuelRegisterPageParam.getFuelLable());
        }
        if(ObjectUtil.isAllNotEmpty(fuelRegisterPageParam.getSortField(), fuelRegisterPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(fuelRegisterPageParam.getSortOrder());
            queryWrapper.orderBy(true, fuelRegisterPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(fuelRegisterPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(FuelRegister::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(FuelRegisterAddParam fuelRegisterAddParam) {
        FuelRegister fuelRegister = BeanUtil.toBean(fuelRegisterAddParam, FuelRegister.class);
        this.save(fuelRegister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FuelRegisterEditParam fuelRegisterEditParam) {
        FuelRegister fuelRegister = this.queryEntity(fuelRegisterEditParam.getId());
        BeanUtil.copyProperties(fuelRegisterEditParam, fuelRegister);
        this.updateById(fuelRegister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FuelRegisterIdParam> fuelRegisterIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(fuelRegisterIdParamList, FuelRegisterIdParam::getId));
    }

    @Override
    public FuelRegister detail(FuelRegisterIdParam fuelRegisterIdParam) {
        return this.queryEntity(fuelRegisterIdParam.getId());
    }

    @Override
    public FuelRegister queryEntity(String id) {
        FuelRegister fuelRegister = this.getById(id);
        if(ObjectUtil.isEmpty(fuelRegister)) {
            throw new CommonException("CAR-加油记录不存在，id值为：{}", id);
        }
        return fuelRegister;
    }
}
