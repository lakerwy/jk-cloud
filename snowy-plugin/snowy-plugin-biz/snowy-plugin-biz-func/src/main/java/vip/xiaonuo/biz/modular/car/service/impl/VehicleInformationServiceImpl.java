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
import vip.xiaonuo.biz.modular.car.vo.H5CarDto;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.car.entity.VehicleInformation;
import vip.xiaonuo.biz.modular.car.mapper.VehicleInformationMapper;
import vip.xiaonuo.biz.modular.car.param.VehicleInformationAddParam;
import vip.xiaonuo.biz.modular.car.param.VehicleInformationEditParam;
import vip.xiaonuo.biz.modular.car.param.VehicleInformationIdParam;
import vip.xiaonuo.biz.modular.car.param.VehicleInformationPageParam;
import vip.xiaonuo.biz.modular.car.service.VehicleInformationService;

import java.math.BigDecimal;
import java.util.List;

/**
 * BIZ_CAR_VEHICLE_INFORMATIONService接口实现类
 *
 * @author yong.ye
 * @date  2023/11/08 17:17
 **/

@DS("slave_1")
@Service
public class VehicleInformationServiceImpl extends ServiceImpl<VehicleInformationMapper, VehicleInformation> implements VehicleInformationService {

    @Override
    public Page<VehicleInformation> page(VehicleInformationPageParam vehicleInformationPageParam) {
        QueryWrapper<VehicleInformation> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(vehicleInformationPageParam.getSn())) {
            queryWrapper.lambda().like(VehicleInformation::getSn, vehicleInformationPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(vehicleInformationPageParam.getOpt())) {
            queryWrapper.lambda().like(VehicleInformation::getOpt, vehicleInformationPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(vehicleInformationPageParam.getLicenseNo())) {
            queryWrapper.lambda().like(VehicleInformation::getLicenseNo, vehicleInformationPageParam.getLicenseNo());
        }
        if(ObjectUtil.isNotEmpty(vehicleInformationPageParam.getVehicleBrand())) {
            queryWrapper.lambda().like(VehicleInformation::getVehicleBrand, vehicleInformationPageParam.getVehicleBrand());
        }
        if(ObjectUtil.isNotEmpty(vehicleInformationPageParam.getVehicleType())) {
            queryWrapper.lambda().eq(VehicleInformation::getVehicleType, vehicleInformationPageParam.getVehicleType());
        }
        if(ObjectUtil.isNotEmpty(vehicleInformationPageParam.getOilCardNumber())) {
            queryWrapper.lambda().like(VehicleInformation::getOilCardNumber, vehicleInformationPageParam.getOilCardNumber());
        }
        if(ObjectUtil.isNotEmpty(vehicleInformationPageParam.getStatus())) {
            queryWrapper.lambda().eq(VehicleInformation::getStatus, vehicleInformationPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(vehicleInformationPageParam.getSortField(), vehicleInformationPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(vehicleInformationPageParam.getSortOrder());
            queryWrapper.orderBy(true, vehicleInformationPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(vehicleInformationPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(VehicleInformation::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(VehicleInformationAddParam vehicleInformationAddParam) {
        VehicleInformation vehicleInformation = BeanUtil.toBean(vehicleInformationAddParam, VehicleInformation.class);
        this.save(vehicleInformation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(VehicleInformationEditParam vehicleInformationEditParam) {
        VehicleInformation vehicleInformation = this.queryEntity(vehicleInformationEditParam.getId());
        BeanUtil.copyProperties(vehicleInformationEditParam, vehicleInformation);
        this.updateById(vehicleInformation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<VehicleInformationIdParam> vehicleInformationIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(vehicleInformationIdParamList, VehicleInformationIdParam::getId));
    }

    @Override
    public VehicleInformation detail(VehicleInformationIdParam vehicleInformationIdParam) {
        return this.queryEntity(vehicleInformationIdParam.getId());
    }

    @Override
    public VehicleInformation queryEntity(String id) {
        VehicleInformation vehicleInformation = this.getById(id);
        if(ObjectUtil.isEmpty(vehicleInformation)) {
            throw new CommonException("BIZ_CAR_VEHICLE_INFORMATION不存在，id值为：{}", id);
        }
        return vehicleInformation;
    }

    @Override
    public List<H5CarDto> getH5VehicleList() {
        return BeanUtil.copyToList(this.list(), H5CarDto.class);
    }

    @Override
    public void updateVehicleTotalMileage(String vehicleId, Boolean isEdit, BigDecimal mileages, BigDecimal oldMileages) {
        VehicleInformation vehicleInformation = this.getById(vehicleId);
        if(ObjectUtil.isEmpty(vehicleInformation)) return;
        if(isEdit){
            //编辑
            vehicleInformation.setTotalMileage(toValue(vehicleInformation.getTotalMileage()).add(toValue(mileages)).subtract(toValue(oldMileages)));
        }else{
            vehicleInformation.setTotalMileage(toValue(vehicleInformation.getTotalMileage()).add(toValue(mileages)));
        }
        this.updateById(vehicleInformation);
    }

    @Override
    public void updateVehicleTotalOilFuel(String vehicleId, Boolean isEdit, BigDecimal fuelCharge, BigDecimal oldFuelCharge) {
        VehicleInformation vehicleInformation = this.getById(vehicleId);
        if(ObjectUtil.isEmpty(vehicleInformation)) return;
        if(isEdit){
            //编辑
            vehicleInformation.setTotalOilFuel(toValue(vehicleInformation.getTotalOilFuel()).add(toValue(fuelCharge)).subtract(toValue(oldFuelCharge)));
        }else{
            vehicleInformation.setTotalOilFuel(toValue(vehicleInformation.getTotalOilFuel()).add(toValue(fuelCharge)));
        }
        this.updateById(vehicleInformation);
    }

    /**
     * 数值处理
     * @param value 入参值
     * @return
     */
    public BigDecimal toValue(BigDecimal value){
        return value == null ? BigDecimal.ZERO : value;
    }
}
