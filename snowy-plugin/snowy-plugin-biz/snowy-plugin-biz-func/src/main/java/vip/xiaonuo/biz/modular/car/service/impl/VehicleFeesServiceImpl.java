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
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.car.entity.EnterFees;
import vip.xiaonuo.biz.modular.car.entity.VehicleInformation;
import vip.xiaonuo.biz.modular.car.enums.FeeTypeEnum;
import vip.xiaonuo.biz.modular.car.param.*;
import vip.xiaonuo.biz.modular.car.service.EnterFeesService;
import vip.xiaonuo.biz.modular.car.service.VehicleInformationService;
import vip.xiaonuo.biz.modular.car.vo.FeeChartViewModel;
import vip.xiaonuo.biz.modular.car.vo.PieChartData;
import vip.xiaonuo.biz.modular.car.vo.TabTotalData;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.car.entity.VehicleFees;
import vip.xiaonuo.biz.modular.car.mapper.VehicleFeesMapper;
import vip.xiaonuo.biz.modular.car.service.VehicleFeesService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CAR-费用管理Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/08 16:06
 **/

@DS("slave_1")
@Service
public class VehicleFeesServiceImpl extends ServiceImpl<VehicleFeesMapper, VehicleFees> implements VehicleFeesService {

    @Resource
    VehicleInformationService vehicleInformationService;
    @Resource
    EnterFeesService enterFeesService;
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
        if(ObjectUtil.isNotEmpty(vehicleFeesPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(VehicleFees::getLicenseNo,vehicleFeesPageParam.getSearchKey())
                    .or().like(VehicleFees::getSn,vehicleFeesPageParam.getSearchKey())
                    .or().like(VehicleFees::getOpt,vehicleFeesPageParam.getSearchKey())
                    .or().like(VehicleFees::getType,vehicleFeesPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(vehicleFeesPageParam.getSortField(), vehicleFeesPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(vehicleFeesPageParam.getSortOrder());
            queryWrapper.orderBy(true, vehicleFeesPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(vehicleFeesPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(VehicleFees::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(VehicleFeesAddParam vehicleFeesAddParam) {
        VehicleFees vehicleFees = BeanUtil.toBean(vehicleFeesAddParam, VehicleFees.class);
        this.save(vehicleFees);
        //司机端新增【维保】信息时，反更新 上次维保里程，上次维保日期 到车辆上
        if (vehicleFeesAddParam.getType().equals(FeeTypeEnum.Maintanin.getValue()))
        {
            VehicleInformation vehicle = vehicleInformationService.getById(vehicleFeesAddParam.getVehicleId());
            vehicle.setLastMileage(new BigDecimal(vehicleFeesAddParam.getCheckCode()));
            vehicle.setMaintenanceDate(vehicleFeesAddParam.getChargeDate());
            vehicleInformationService.updateById(vehicle);
        }
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
        //【费用统计表】
        QueryWrapper<VehicleFees> vehicleFeesQueryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(feeChartParam.getStartDate())){
            vehicleFeesQueryWrapper.lambda().ge(VehicleFees::getOptDate,feeChartParam.getStartDate());
        }
        if(ObjectUtil.isNotEmpty(feeChartParam.getEndDate())){
            vehicleFeesQueryWrapper.lambda().le(VehicleFees::getOptDate,feeChartParam.getEndDate());
        }
        if(ObjectUtil.isNotEmpty(feeChartParam.getFeeTypes())){
            vehicleFeesQueryWrapper.lambda().eq(VehicleFees::getType,feeChartParam.getFeeTypes());
        }
        if(ObjectUtil.isNotEmpty(feeChartParam.getVehicleId())){
            vehicleFeesQueryWrapper.lambda().eq(VehicleFees::getVehicleId,feeChartParam.getVehicleId());
        }
        vehicleFeesQueryWrapper.lambda().orderByDesc(VehicleFees::getCreateTime);
        List<VehicleFees> vehicleFeesList = this.list(vehicleFeesQueryWrapper);

        //【费用补登统计表】
        QueryWrapper<EnterFees> enterFeesQueryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(feeChartParam.getStartDate())){
            enterFeesQueryWrapper.lambda().ge(EnterFees::getOptDate,feeChartParam.getStartDate());
        }
        if(ObjectUtil.isNotEmpty(feeChartParam.getEndDate())){
            enterFeesQueryWrapper.lambda().le(EnterFees::getOptDate,feeChartParam.getEndDate());
        }
        if(ObjectUtil.isNotEmpty(feeChartParam.getVehicleId())){
            enterFeesQueryWrapper.lambda().eq(EnterFees::getVehicleId,feeChartParam.getVehicleId());
        }
        enterFeesQueryWrapper.lambda().orderByDesc(EnterFees::getCreateTime);
        List<EnterFees> enterFeesList = enterFeesService.list(enterFeesQueryWrapper);

        //【按照车辆分组，统计各车费用（正常费用+补登费用）】
        Map<String,List<VehicleFees>> feeGroup = vehicleFeesList.stream().collect(Collectors.groupingBy(x->x.getVehicleId()));
        List<PieChartData<BigDecimal>> feePie = new ArrayList<>();
        for (Map.Entry<String,List<VehicleFees>> entry:feeGroup.entrySet()) {
            PieChartData<BigDecimal> tmpFee = new PieChartData<>();
            tmpFee.setName(entry.getKey());
            tmpFee.setValue(feeTypeSum(entry.getValue()));
            feePie.add(tmpFee);
        }
        Map<String,List<EnterFees>> enterFeeGroup = enterFeesList.stream().collect(Collectors.groupingBy(x->x.getVehicleId()));
        for (Map.Entry<String,List<EnterFees>> entry:enterFeeGroup.entrySet()) {
            PieChartData<BigDecimal> tmpFee = new PieChartData<>();
            tmpFee.setName(entry.getKey());
            tmpFee.setValue(sumValue(entry.getValue().stream().filter(x->ObjectUtil.isNotEmpty(x.getAmt())).map(x->x.getAmt()).collect(Collectors.toList())));
            feePie.add(tmpFee);
        }
        //按照车辆统计费用： 正常费用+补登费用
        Map<String,List<PieChartData<BigDecimal>>> tmpPieGroup = feePie.stream().collect(Collectors.groupingBy(x->x.getName()));
        List<PieChartData<BigDecimal>> feeTotalPie = new ArrayList<>();
        for (Map.Entry<String,List<PieChartData<BigDecimal>>> entry:tmpPieGroup.entrySet()) {
            PieChartData<BigDecimal> tmpFee = new PieChartData<>();
            tmpFee.setName(entry.getKey());
            tmpFee.setValue(sumValue(entry.getValue().stream().map(x->x.getValue()).collect(Collectors.toList())));
            feeTotalPie.add(tmpFee);
        }

        //按照费用类别统计费用
        Map<String,List<VehicleFees>> typeGroup = vehicleFeesList.stream().collect(Collectors.groupingBy(x->x.getType()));
        List<PieChartData<BigDecimal>> typePie = new ArrayList<>();
        for (Map.Entry<String,List<VehicleFees>> entry:typeGroup.entrySet()) {
            PieChartData<BigDecimal> tmpFee = new PieChartData<>();
            tmpFee.setName(entry.getKey());
            tmpFee.setValue(feeTypeSum(entry.getValue()));
            typePie.add(tmpFee);
        }

        //合计
        TabTotalData<BigDecimal> tabTotalData = new TabTotalData<>();
        tabTotalData.setValue1(feeTypeSum(vehicleFeesList));
        tabTotalData.setValue2(sumValue(enterFeesList.stream().filter(x->ObjectUtil.isNotEmpty(x.getAmt())).map(x->x.getAmt()).collect(Collectors.toList())));
        tabTotalData.setSumValue(tabTotalData.getValue1().add(tabTotalData.getValue2()));

        FeeChartViewModel feeChartViewModel = new FeeChartViewModel();
        feeChartViewModel.setFeeResultDto(vehicleFeesList);
        feeChartViewModel.setEnterResultDto(enterFeesList);
        feeChartViewModel.setPieChartCarDatas(feeTotalPie);
        feeChartViewModel.setPieChartFeeDatas(typePie);
        feeChartViewModel.setTabTotalData(tabTotalData);
        return feeChartViewModel;
    }

    /**
     * 合计各类费用
     * @param vehicleFeesList
     * @return
     */
    public BigDecimal feeTypeSum(List<VehicleFees> vehicleFeesList){
        if(CollUtil.isEmpty(vehicleFeesList)) return BigDecimal.ZERO;
        BigDecimal feeValue = sumValue(vehicleFeesList.stream().filter(x->ObjectUtil.isNotEmpty(x.getFee())).map(x->x.getFee()).collect(Collectors.toList()));
        BigDecimal unKeepFeeValue = sumValue(vehicleFeesList.stream().filter(x->ObjectUtil.isNotEmpty(x.getUnkeepFee())).map(x->x.getUnkeepFee()).collect(Collectors.toList()));
        BigDecimal insuranceFeeValue = sumValue(vehicleFeesList.stream().filter(x->ObjectUtil.isNotEmpty(x.getInsuranceFee())).map(x->x.getInsuranceFee()).collect(Collectors.toList()));
        BigDecimal annualFeeValue = sumValue(vehicleFeesList.stream().filter(x->ObjectUtil.isNotEmpty(x.getAnnualFee())).map(x->x.getAnnualFee()).collect(Collectors.toList()));
        BigDecimal parkFeeValue = sumValue(vehicleFeesList.stream().filter(x->ObjectUtil.isNotEmpty(x.getParkingFee())).map(x->x.getParkingFee()).collect(Collectors.toList()));
        BigDecimal roadFeeValue = sumValue(vehicleFeesList.stream().filter(x->ObjectUtil.isNotEmpty(x.getRoadFee())).map(x->x.getRoadFee()).collect(Collectors.toList()));
        BigDecimal amountValue = sumValue(vehicleFeesList.stream().filter(x->ObjectUtil.isNotEmpty(x.getAmount())).map(x->x.getAmount()).collect(Collectors.toList()));
        return feeValue.add(unKeepFeeValue).add(insuranceFeeValue).add(annualFeeValue).add(parkFeeValue).add(roadFeeValue).add(amountValue);
    }



    /**
     * BigDecimal 数值处理
     * @param value 入参值
     * @return
     */
    public BigDecimal toValue(BigDecimal value){
        return value == null ? BigDecimal.ZERO : value;
    }

    /**
     * BigDecimal 集合相加
     * @param list
     * @return
     */
    public BigDecimal sumValue(List<BigDecimal> list){
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal value:list) {
            total = total.add(toValue(value));
        }
        return total;
    }
}
