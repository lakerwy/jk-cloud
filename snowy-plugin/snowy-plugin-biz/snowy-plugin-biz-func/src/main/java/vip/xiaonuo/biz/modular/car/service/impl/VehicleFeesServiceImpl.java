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
import vip.xiaonuo.biz.modular.car.param.*;
import vip.xiaonuo.biz.modular.car.vo.FeeChartViewModel;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.car.entity.VehicleFees;
import vip.xiaonuo.biz.modular.car.mapper.VehicleFeesMapper;
import vip.xiaonuo.biz.modular.car.service.VehicleFeesService;

import java.util.List;

/**
 * CAR-费用管理Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/08 16:06
 **/

@DS("slave_1")
@Service
public class VehicleFeesServiceImpl extends ServiceImpl<VehicleFeesMapper, VehicleFees> implements VehicleFeesService {

    @Override
    public Page<VehicleFees> page(VehicleFeesPageParam vehicleFeesPageParam) {
        QueryWrapper<VehicleFees> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(vehicleFeesPageParam.getVehicleId())) {
            queryWrapper.lambda().eq(VehicleFees::getVehicleId, vehicleFeesPageParam.getVehicleId());
        }
        if(ObjectUtil.isNotEmpty(vehicleFeesPageParam.getSn())) {
            queryWrapper.lambda().like(VehicleFees::getSn, vehicleFeesPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(vehicleFeesPageParam.getOpt())) {
            queryWrapper.lambda().like(VehicleFees::getOpt, vehicleFeesPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(vehicleFeesPageParam.getLicenseNo())) {
            queryWrapper.lambda().like(VehicleFees::getLicenseNo, vehicleFeesPageParam.getLicenseNo());
        }
        if(ObjectUtil.isNotEmpty(vehicleFeesPageParam.getType())) {
            queryWrapper.lambda().eq(VehicleFees::getType, vehicleFeesPageParam.getType());
        }
        if(ObjectUtil.isAllNotEmpty(vehicleFeesPageParam.getSortField(), vehicleFeesPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(vehicleFeesPageParam.getSortOrder());
            queryWrapper.orderBy(true, vehicleFeesPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(vehicleFeesPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(VehicleFees::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(VehicleFeesAddParam vehicleFeesAddParam) {
        VehicleFees vehicleFees = BeanUtil.toBean(vehicleFeesAddParam, VehicleFees.class);
        this.save(vehicleFees);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(VehicleFeesEditParam vehicleFeesEditParam) {
        VehicleFees vehicleFees = this.queryEntity(vehicleFeesEditParam.getId());
        BeanUtil.copyProperties(vehicleFeesEditParam, vehicleFees);
        this.updateById(vehicleFees);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<VehicleFeesIdParam> vehicleFeesIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(vehicleFeesIdParamList, VehicleFeesIdParam::getId));
    }

    @Override
    public VehicleFees detail(VehicleFeesIdParam vehicleFeesIdParam) {
        return this.queryEntity(vehicleFeesIdParam.getId());
    }

    @Override
    public VehicleFees queryEntity(String id) {
        VehicleFees vehicleFees = this.getById(id);
        if(ObjectUtil.isEmpty(vehicleFees)) {
            throw new CommonException("CAR-费用管理不存在，id值为：{}", id);
        }
        return vehicleFees;
    }

    @Override
    public FeeChartViewModel getFeeChart(FeeChartParam feeChartParam) {
        return null;
    }
}
