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
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hpsf.Decimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.car.config.DriversProperties;
import vip.xiaonuo.biz.modular.car.config.UsersProperties;
import vip.xiaonuo.biz.modular.car.entity.*;
import vip.xiaonuo.biz.modular.car.enums.CarApplyStatusEnum;
import vip.xiaonuo.biz.modular.car.enums.CarStatusEnum;
import vip.xiaonuo.biz.modular.car.enums.DriverStatusEnum;
import vip.xiaonuo.biz.modular.car.param.*;
import vip.xiaonuo.biz.modular.car.service.*;
import vip.xiaonuo.biz.modular.car.vo.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.car.mapper.VehicleApplyMapper;
import vip.xiaonuo.dev.api.IDingDingService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Driver;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CAR-用车申请单Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/08 16:04
 **/

@DS("slave_1")
@Service
public class VehicleApplyServiceImpl extends ServiceImpl<VehicleApplyMapper, VehicleApply> implements VehicleApplyService {

    @Resource
    private VehicleInformationService vehicleInformationService;
    @Resource
    private DriversService driversService;

    @Resource
    UsersProperties usersProperties;

    @Resource
    DriversProperties driversProperties;

    @Resource
    IDingDingService iDingDingService;

    @Resource
    VehicleApplyMapper vehicleApplyMapper;

    @Resource
    EnterMileageService enterMileageService;

    @Resource
    FuelRegisterService fuelRegisterService;

    @Resource
    ServiceRatingsService serviceRatingsService;

    @Resource
    DriverBreaksService driverBreaksService;

    @Resource
    DangerRegisterService dangerRegisterService;

    @Override
    public Page<VehicleApply> page(VehicleApplyPageParam vehicleApplyPageParam) {
        QueryWrapper<VehicleApply> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(vehicleApplyPageParam.getSn())) {
            queryWrapper.lambda().like(VehicleApply::getSn, vehicleApplyPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(vehicleApplyPageParam.getOpt())) {
            queryWrapper.lambda().like(VehicleApply::getOpt, vehicleApplyPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(vehicleApplyPageParam.getUsers())) {
            queryWrapper.lambda().like(VehicleApply::getUsers, vehicleApplyPageParam.getUsers());
        }
        if(ObjectUtil.isNotEmpty(vehicleApplyPageParam.getDept())) {
            queryWrapper.lambda().like(VehicleApply::getDept, vehicleApplyPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(vehicleApplyPageParam.getVehicle())) {
            queryWrapper.lambda().like(VehicleApply::getVehicle, vehicleApplyPageParam.getVehicle());
        }
        if(ObjectUtil.isNotEmpty(vehicleApplyPageParam.getDriver())) {
            queryWrapper.lambda().like(VehicleApply::getDriver, vehicleApplyPageParam.getDriver());
        }
        if(ObjectUtil.isAllNotEmpty(vehicleApplyPageParam.getSortField(), vehicleApplyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(vehicleApplyPageParam.getSortOrder());
            queryWrapper.orderBy(true, vehicleApplyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(vehicleApplyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(VehicleApply::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(VehicleApplyAddParam vehicleApplyAddParam) {
        VehicleApply vehicleApply = BeanUtil.toBean(vehicleApplyAddParam, VehicleApply.class);
        vehicleApply.setOptDate(DateTime.now());
        vehicleApply.setStatus(vehicleApplyAddParam.getUserId().equals(vehicleApplyAddParam.getSeniorManagerUserId())?CarApplyStatusEnum.Dispatching.getValue():CarApplyStatusEnum.Auditing.getValue());
        this.save(vehicleApply);

        //派车单申请，发送通知给部门直属主管审批（申请单详情页）
        String title = "用车申请";
        String url = usersProperties.getLinkUrl() + "yongche/dispatchCar?applyid=" + vehicleApply.getId();
        iDingDingService.sendCarMsg(usersProperties.getAgentId(), usersProperties.getAppKey(), usersProperties.getAppSecret(),
                JSONUtil.parseObj(vehicleApply),title,url,vehicleApplyAddParam.getSeniorManagerDingDingUserId());
        //若审批通过【由 待审核--待派车】，发送通知给车辆调度员分派车辆
        if (vehicleApply.getStatus().equals(CarApplyStatusEnum.Dispatching.getValue()))
        {
            title = "分派车辆";
            url = usersProperties.getLinkUrl() + "yongche/dispatchCar?applyid=" + vehicleApply.getId();
            iDingDingService.sendCarMsg(usersProperties.getAgentId(), usersProperties.getAppKey(), usersProperties.getAppSecret(),
                    JSONUtil.parseObj(vehicleApply),title,url,usersProperties.getCarAdminDingDingUserId());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(VehicleApplyEditParam vehicleApplyEditParam) {
        VehicleApply vehicleApply = this.queryEntity(vehicleApplyEditParam.getId());
        BeanUtil.copyProperties(vehicleApplyEditParam, vehicleApply);
        this.updateById(vehicleApply);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<VehicleApplyIdParam> vehicleApplyIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(vehicleApplyIdParamList, VehicleApplyIdParam::getId));
    }

    @Override
    public VehicleApplyDetailDto detail(VehicleApplyIdParam vehicleApplyIdParam) {
        VehicleApply vehicleApply = this.queryEntity(vehicleApplyIdParam.getId());
        VehicleApplyDetailDto result = BeanUtil.copyProperties(vehicleApply,VehicleApplyDetailDto.class);
        if(ObjectUtil.isNotEmpty(result)){
            QueryWrapper<VehicleApply> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(VehicleApply::getStatus, CarApplyStatusEnum.Dispatched.getValue());
            queryWrapper.lambda().gt(VehicleApply::getPredictCarTime,result.getPredictTransportTime());
            queryWrapper.lambda().lt(VehicleApply::getPredictTransportTime,result.getPredictCarTime());
            //判断当前预定时间下，可用的车辆信息
            List<VehicleApply> dbVehicleApply = this.list(queryWrapper);
            //该区间不可用车辆
            List<String> haveUsedVehicle = dbVehicleApply.stream().map(x->x.getVehicleId()).distinct().collect(Collectors.toList());
            //该区间不可用司机
            List<String> haveUsedDriver = dbVehicleApply.stream().map(x->x.getDriverUserId()).distinct().collect(Collectors.toList());
            //可用车辆
            QueryWrapper<VehicleInformation> queryWrapper1 = new QueryWrapper<>();
            if(CollUtil.isNotEmpty(haveUsedVehicle))
            {
                queryWrapper1.lambda().notIn(VehicleInformation::getId,haveUsedVehicle);
            }
            List<VehicleInformation> dbVehicleInfo = vehicleInformationService.list(queryWrapper1);
            result.setAvailableVehicles(BeanUtil.copyToList(dbVehicleInfo, VehicleInfoDto.class));
            //可用司机
            QueryWrapper<Drivers> queryWrapper2 = new QueryWrapper<>();
            if(CollUtil.isNotEmpty(haveUsedDriver))
            {
                queryWrapper2.lambda().notIn(Drivers::getDriverUserId,haveUsedDriver);
            }
            List<Drivers> dbDriverInfo = driversService.list(queryWrapper2);
            result.setAvailableDrivers(BeanUtil.copyToList(dbDriverInfo, DriverInfoDto.class));
        }
        return result;
    }

    @Override
    public VehicleApply queryEntity(String id) {
        VehicleApply vehicleApply = this.getById(id);
        if(ObjectUtil.isEmpty(vehicleApply)) {
            throw new CommonException("CAR-用车申请单不存在，id值为：{}", id);
        }
        return vehicleApply;
    }

    @Override
    public void updateStatus(UpdateApplyStatusParam updateApplyStatusParam) {
        VehicleApply vehicleApply = queryEntity(updateApplyStatusParam.getVehicleApplyId());
        vehicleApply.setStatus(updateApplyStatusParam.getStatus());
        this.updateById(vehicleApply);
        //若审批通过【由 待审核--待派车】，发送通知给车辆调度员分派车辆
        if(vehicleApply.getStatus().equals(CarApplyStatusEnum.Dispatching.getValue()))
        {
            //派车单申请，发送通知
            String title = "分派车辆";
            String url = usersProperties.getLinkUrl() + "yongche/dispatchCar?applyid=" + vehicleApply.getId();
            iDingDingService.sendCarMsg(usersProperties.getAgentId(),usersProperties.getAppKey(),usersProperties.getAppSecret(),
                    JSONUtil.parseObj(vehicleApply), title, url, updateApplyStatusParam.getCarAdminDingDingUserId());
        }
    }

    @Override
    public void dispatchCar(DispatchCarParam dispatchCarParam) {
        VehicleApply vehicleApply = this.queryEntity(dispatchCarParam.getVehicleApplyId());
        vehicleApply.setStatus(CarApplyStatusEnum.Dispatched.getValue());
        vehicleApply.setVehicle(dispatchCarParam.getVehicle());
        vehicleApply.setVehicleId(dispatchCarParam.getVehicleId());
        vehicleApply.setDriver(dispatchCarParam.getDriver());
        vehicleApply.setDriverUserId(dispatchCarParam.getDriverUserId());
        this.updateById(vehicleApply);
        //车辆调度后，发送通知给司机，派车单详情页
        String title = "派车单";
        String url = usersProperties.getLinkUrl() + "Activing/activingInfo?applyid=" + vehicleApply.getId();
        String userlist = String.join(",", dispatchCarParam.getDingDingUserIds());
        iDingDingService.sendCarMsg(usersProperties.getAgentId(),usersProperties.getAppKey(),usersProperties.getAppSecret(),
                JSONUtil.parseObj(vehicleApply),title,url,userlist);
    }

    @Override
    public Page<VehicleApply> pageByUserId(VehicleApplyPageParam vehicleApplyPageParam) {
        QueryWrapper<VehicleApply> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(vehicleApplyPageParam.getStatus())){
            queryWrapper.lambda().eq(VehicleApply::getStatus,vehicleApplyPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(vehicleApplyPageParam.getUserId())){
            queryWrapper.lambda().and(q->q.eq(VehicleApply::getUserId,vehicleApplyPageParam.getUserId())
                    .or().eq(VehicleApply::getSeniorManagerUserId,vehicleApplyPageParam.getUserId())
                    .or().eq(VehicleApply::getCarAdminUserId,vehicleApplyPageParam.getUserId()));
        }
        if(CollUtil.isNotEmpty(vehicleApplyPageParam.getFilterStatusIn())){
            queryWrapper.lambda().in(VehicleApply::getStatus,vehicleApplyPageParam.getFilterStatusIn());
        }
        if(CollUtil.isNotEmpty(vehicleApplyPageParam.getFilterStatusNotIn())){
            queryWrapper.lambda().notIn(VehicleApply::getStatus,vehicleApplyPageParam.getFilterStatusNotIn());
        }
        if(ObjectUtil.isNotEmpty(vehicleApplyPageParam.getApplyUserId())){
            queryWrapper.lambda().eq(VehicleApply::getUserId,vehicleApplyPageParam.getApplyUserId());
        }
        if(ObjectUtil.isAllNotEmpty(vehicleApplyPageParam.getSortField(), vehicleApplyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(vehicleApplyPageParam.getSortOrder());
            queryWrapper.orderBy(true, vehicleApplyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(vehicleApplyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(VehicleApply::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public Page<VehicleApply> ratePageByUserId(VehicleApplyPageParam vehicleApplyPageParam) {
        QueryWrapper<VehicleApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("a.STATUS",CarApplyStatusEnum.Finish.getValue());
        if(ObjectUtil.isNotEmpty(vehicleApplyPageParam.getUserId())){
            queryWrapper.eq("a.USER_ID",vehicleApplyPageParam.getUserId());
        }
        queryWrapper.apply(" b.ID is null ");
        if(ObjectUtil.isAllNotEmpty(vehicleApplyPageParam.getSortField(), vehicleApplyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(vehicleApplyPageParam.getSortOrder());
            queryWrapper.orderBy(true, vehicleApplyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(vehicleApplyPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return vehicleApplyMapper.getRatePageByUserId(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public VehicleChartViewModel getVehicleChart(VehicleChartParam vehicleChartParam) {
        //派车单信息 【已完成  + 区间】
        QueryWrapper<VehicleApply> vehicleApplyQueryWrapper = new QueryWrapper<>();
        vehicleApplyQueryWrapper.lambda().eq(VehicleApply::getStatus,CarApplyStatusEnum.Finish.getValue());
        if(ObjectUtil.isNotEmpty(vehicleChartParam.getStartDate())){
            vehicleApplyQueryWrapper.lambda().ge(VehicleApply::getOptDate,vehicleChartParam.getStartDate());
        }
        if(ObjectUtil.isNotEmpty(vehicleChartParam.getEndDate())){
            vehicleApplyQueryWrapper.lambda().le(VehicleApply::getOptDate,vehicleChartParam.getEndDate());
        }
        if(ObjectUtil.isNotEmpty(vehicleChartParam.getDepts())){
            vehicleApplyQueryWrapper.lambda().eq(VehicleApply::getDept,vehicleChartParam.getDepts());
        }
        if(ObjectUtil.isNotEmpty(vehicleChartParam.getVehicleId())){
            vehicleApplyQueryWrapper.lambda().eq(VehicleApply::getVehicleId,vehicleChartParam.getVehicleId());
        }
        if(ObjectUtil.isNotEmpty(vehicleChartParam.getDriverUserId())){
            vehicleApplyQueryWrapper.lambda().eq(VehicleApply::getDriverUserId,vehicleChartParam.getDriverUserId());
        }
        vehicleApplyQueryWrapper.lambda().orderByDesc(VehicleApply::getCreateTime);
        List<VehicleApply> applylistRes = this.list(vehicleApplyQueryWrapper);

        //【按照部门分组，分别统计各个部门用车次数、用车里程】
        Map<String,List<VehicleApply>> deptGroup = applylistRes.stream().collect(Collectors.groupingBy(x->x.getDept()));
        List<PieChartData<Long>> pieChartTimesDatas = new ArrayList<>();
        List<PieChartData<BigDecimal>> pieChartMileagesDatas = new ArrayList<>();
        for (Map.Entry<String,List<VehicleApply>> entry:deptGroup.entrySet()) {
            //用车次数
            PieChartData<Long> times = new PieChartData<>();
            times.setName(entry.getKey());
            times.setValue((long)entry.getValue().size());
            pieChartTimesDatas.add(times);
            //用车里程
            PieChartData<BigDecimal> mileage = new PieChartData<>();
            mileage.setName(entry.getKey());
            mileage.setValue(sumValue(entry.getValue().stream().map(x->x.getSubTotal()).collect(Collectors.toList())));
            pieChartMileagesDatas.add(mileage);
        }

        //【里程补登统计】
        QueryWrapper<EnterMileage> enterMileageQueryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(vehicleChartParam.getStartDate())){
            enterMileageQueryWrapper.lambda().ge(EnterMileage::getOptDate,vehicleChartParam.getStartDate());
        }
        if(ObjectUtil.isNotEmpty(vehicleChartParam.getEndDate())){
            enterMileageQueryWrapper.lambda().le(EnterMileage::getOptDate,vehicleChartParam.getEndDate());
        }
        if(ObjectUtil.isNotEmpty(vehicleChartParam.getVehicleId())){
            enterMileageQueryWrapper.lambda().eq(EnterMileage::getVehicleId,vehicleChartParam.getVehicleId());
        }
        if(ObjectUtil.isNotEmpty(vehicleChartParam.getDriverUserId())){
            enterMileageQueryWrapper.lambda().eq(EnterMileage::getOptUserId,vehicleChartParam.getDriverUserId());
        }
        enterMileageQueryWrapper.lambda().orderByDesc(EnterMileage::getCreateTime);
        List<EnterMileage> enterMileageList = enterMileageService.list(enterMileageQueryWrapper);

        //【合计统计】
        TabTotalData<BigDecimal> tabTotalData = new TabTotalData<>();
        tabTotalData.setValue1(sumValue(applylistRes.stream().filter(x->ObjectUtil.isNotEmpty(x.getSubTotal())).map(x->x.getSubTotal()).collect(Collectors.toList())));
        tabTotalData.setValue2(sumValue(enterMileageList.stream().filter(x->ObjectUtil.isNotEmpty(x.getMileages())).map(x->x.getMileages()).collect(Collectors.toList())));
        tabTotalData.setSumValue(tabTotalData.getValue1().add(tabTotalData.getValue2()));

        VehicleChartViewModel vehicleChartViewModel = new VehicleChartViewModel();
        vehicleChartViewModel.setApplyResultDto(applylistRes);
        vehicleChartViewModel.setEnterResultDto(enterMileageList);
        vehicleChartViewModel.setPieChartMileagesDatas(pieChartMileagesDatas);
        vehicleChartViewModel.setPieChartTimesDatas(pieChartTimesDatas);
        vehicleChartViewModel.setTabTotalData(tabTotalData);

        return vehicleChartViewModel;
    }

    @Override
    public AvgOilChartViewModel getAvgOilChart(OilChartParam oilChartParam) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String quJian = (ObjectUtil.isNotEmpty(oilChartParam.getStartDate()) ? sdf.format(oilChartParam.getStartDate()) + " - " : "")
                + (ObjectUtil.isNotEmpty(oilChartParam.getEndDate()) ? sdf.format(oilChartParam.getEndDate()) : "至今");

        //派车单信息 【已完成  + 区间】
        QueryWrapper<VehicleApply> vehicleApplyQueryWrapper = new QueryWrapper<>();
        vehicleApplyQueryWrapper.lambda().eq(VehicleApply::getStatus,CarApplyStatusEnum.Finish.getValue());
        if(ObjectUtil.isNotEmpty(oilChartParam.getStartDate())){
            vehicleApplyQueryWrapper.lambda().ge(VehicleApply::getOptDate,oilChartParam.getStartDate());
        }
        if(ObjectUtil.isNotEmpty(oilChartParam.getEndDate())){
            vehicleApplyQueryWrapper.lambda().le(VehicleApply::getOptDate,oilChartParam.getEndDate());
        }
        vehicleApplyQueryWrapper.lambda().orderByDesc(VehicleApply::getCreateTime);
        List<VehicleApply> applylistRes = this.list(vehicleApplyQueryWrapper);

        //获取补登里程信息 【区间】
        QueryWrapper<EnterMileage> enterMileageQueryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(oilChartParam.getStartDate())){
            enterMileageQueryWrapper.lambda().ge(EnterMileage::getOptDate,oilChartParam.getStartDate());
        }
        if(ObjectUtil.isNotEmpty(oilChartParam.getEndDate())){
            enterMileageQueryWrapper.lambda().le(EnterMileage::getOptDate,oilChartParam.getEndDate());
        }
        enterMileageQueryWrapper.lambda().orderByDesc(EnterMileage::getCreateTime);
        List<EnterMileage> enterlistRes = enterMileageService.list(enterMileageQueryWrapper);

        //获取车辆加油信息 【区间】
        QueryWrapper<FuelRegister> fuelRegisterQueryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(oilChartParam.getStartDate())){
            fuelRegisterQueryWrapper.lambda().ge(FuelRegister::getOptDate,oilChartParam.getStartDate());
        }
        if(ObjectUtil.isNotEmpty(oilChartParam.getEndDate())){
            fuelRegisterQueryWrapper.lambda().le(FuelRegister::getOptDate,oilChartParam.getEndDate());
        }
        fuelRegisterQueryWrapper.lambda().orderByDesc(FuelRegister::getCreateTime);
        List<FuelRegister> fuellistRes = fuelRegisterService.list(fuelRegisterQueryWrapper);

        //获取车辆信息
        List<VehicleInformation> vehicleInfos = vehicleInformationService.list(new QueryWrapper<VehicleInformation>().lambda().orderByAsc(VehicleInformation::getLicenseNo));

        //按照车辆，计算平均油耗
        List<AvgVehicleOilViewModel> avgVehicleOilViewModelList = new ArrayList<>();
        for (VehicleInformation vehicle:vehicleInfos) {
            AvgVehicleOilViewModel avgVehicleOilViewModel = new AvgVehicleOilViewModel();
            //用车里程
            avgVehicleOilViewModel.setSubApplyMileages(sumValue(applylistRes.stream().filter(x->ObjectUtil.isNotEmpty(x.getVehicleId()) && x.getVehicleId().equals(vehicle.getId())).map(x->x.getSubTotal()).collect(Collectors.toList())));
            //补登里程
            avgVehicleOilViewModel.setSubEnterMileages(sumValue(enterlistRes.stream().filter(x->ObjectUtil.isNotEmpty(x.getVehicleId()) && x.getVehicleId().equals(vehicle.getId())).map(x->x.getMileages()).collect(Collectors.toList())));
            //总里程
            avgVehicleOilViewModel.setTotalMileages(avgVehicleOilViewModel.getSubApplyMileages().add(avgVehicleOilViewModel.getSubEnterMileages()));
            //按照车辆、加油登记统计加油金额 【总加油金额 = 加油登记的加油金额】
            avgVehicleOilViewModel.setSubAmount(sumValue(fuellistRes.stream().filter(x->ObjectUtil.isNotEmpty(x.getVehicleId()) && x.getVehicleId().equals(vehicle.getId())).map(x->x.getAmount()).collect(Collectors.toList())));
            //油卡
            avgVehicleOilViewModel.setOilCardNumber(fuellistRes.stream().filter(x->ObjectUtil.isNotEmpty(x.getVehicleId()) && x.getVehicleId().equals(vehicle.getId())).findFirst().orElse(null).getOilCard());
            avgVehicleOilViewModel.setQuJian(quJian);
            avgVehicleOilViewModel.setVehicleId(vehicle.getId());
            avgVehicleOilViewModel.setLicenseNo(vehicle.getLicenseNo());
            avgVehicleOilViewModel.setAvgOil(divideValue(avgVehicleOilViewModel.getSubAmount(),avgVehicleOilViewModel.getTotalMileages()));
            avgVehicleOilViewModelList.add(avgVehicleOilViewModel);
        }

        //按照部门，计算油耗分摊
        Map<String,List<VehicleApply>> deptGroup = applylistRes.stream().sorted(Comparator.comparing(VehicleApply::getVehicleId)).collect(Collectors.groupingBy(x->x.getDept()));
        List<AvgDeptOilViewModel> avgDeptOilViewModelList = new ArrayList<>();
        for (Map.Entry<String,List<VehicleApply>> entry:deptGroup.entrySet()) {
            List<VehicleApply> applyList = entry.getValue();
            List<String> vehicleIds = applyList.stream().filter(x->ObjectUtil.isNotEmpty(x.getVehicleId())).map(x->x.getVehicleId()).distinct().collect(Collectors.toList());
            for (String vehicleId:vehicleIds) {
                AvgDeptOilViewModel avgDeptOilViewModel = new AvgDeptOilViewModel();
                avgDeptOilViewModel.setQuJian(quJian);
                avgDeptOilViewModel.setDept(entry.getKey());
                avgDeptOilViewModel.setVehicleId(vehicleId);
                avgDeptOilViewModel.setSubApplyMileages(sumValue(applyList.stream().filter(x->ObjectUtil.isNotEmpty(x.getVehicleId()) && x.getVehicleId().equals(vehicleId)).map(x->x.getSubTotal()).collect(Collectors.toList())));
                AvgVehicleOilViewModel tmp = avgVehicleOilViewModelList.stream().filter(x->x.getVehicleId().equals(vehicleId)).findFirst().orElse(null);
                avgDeptOilViewModel.setOilCardNumber(tmp.getOilCardNumber());
                avgDeptOilViewModel.setLicenseNo(tmp.getLicenseNo());
                avgDeptOilViewModel.setAvgOil(tmp.getAvgOil());
                avgDeptOilViewModel.setSubAmount(tmp.getAvgOil().multiply(avgDeptOilViewModel.getSubApplyMileages()).setScale(2,BigDecimal.ROUND_HALF_UP));
                avgDeptOilViewModelList.add(avgDeptOilViewModel);
            }
        }
        AvgOilChartViewModel avgOilChartViewModel = new AvgOilChartViewModel();
        avgOilChartViewModel.setAvgOilCarResultDto(avgVehicleOilViewModelList);
        avgOilChartViewModel.setAvgOilDeptResultDto(avgDeptOilViewModelList);
        return avgOilChartViewModel;
    }

    @Override
    public Page<CarPreViewModel> getCarPrePageList(CarPreParam carPreParam) {
        Page<CarPreViewModel> carPreViewModel = new Page<>();
        //分页获取车辆信息
        QueryWrapper<VehicleInformation> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(carPreParam.getLicenseNo())){
            queryWrapper.lambda().eq(VehicleInformation::getLicenseNo,carPreParam.getLicenseNo());
        }
        Page<VehicleInformation> vehiclePage = vehicleInformationService.page(CommonPageRequest.defaultPage(), queryWrapper);
        //有返回，构造结果集
        List<CarPreViewModel> newRecords = BeanUtil.copyToList(vehiclePage.getRecords(),CarPreViewModel.class);
        if(CollUtil.isNotEmpty(newRecords)){
            List<String> vehicleIds = newRecords.stream().map(x->x.getId()).distinct().collect(Collectors.toList());
            QueryWrapper<VehicleApply> vehicleApplyQueryWrapper = new QueryWrapper<>();
            if(CollUtil.isNotEmpty(vehicleIds)){
                vehicleApplyQueryWrapper.lambda().in(VehicleApply::getVehicleId,vehicleIds);
            }
            if(ObjectUtil.isNotEmpty(carPreParam.getPreDate())){
                vehicleApplyQueryWrapper.lambda().gt(VehicleApply::getPredictCarTime,carPreParam.getPreDate());
                //日期控制
                Calendar c = Calendar.getInstance();
                c.setTime(carPreParam.getPreDate());
                c.add(Calendar.DATE, 1);
                vehicleApplyQueryWrapper.lambda().lt(VehicleApply::getPredictTransportTime,c.getTime());
            }
            vehicleApplyQueryWrapper.lambda().eq(VehicleApply::getStatus,CarApplyStatusEnum.Dispatched.getValue());
            vehicleApplyQueryWrapper.lambda().orderByDesc(VehicleApply::getPredictTransportTime);
            List<VehicleApply> vehicleApplies = this.list(vehicleApplyQueryWrapper);
            newRecords.forEach(x->x.setPreVehicleApplyData(vehicleApplies.stream().filter(y->y.getVehicleId().equals(x.getId())).collect(Collectors.toList())));
        }
        carPreViewModel.setCurrent(vehiclePage.getCurrent());
        carPreViewModel.setPages(vehiclePage.getPages());
        carPreViewModel.setSize(vehiclePage.getSize());
        carPreViewModel.setTotal(vehiclePage.getTotal());
        carPreViewModel.setRecords(newRecords);
        return carPreViewModel;
    }

    @Override
    public AvailableInfo getAvailableInfo(AvailableParam availableParam) {
        AvailableInfo result = new AvailableInfo();
        if(ObjectUtil.isNotEmpty(availableParam.getPredictCarTime()) && ObjectUtil.isNotEmpty(availableParam.getPredictTransportTime()))
        {
            QueryWrapper<VehicleApply> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(VehicleApply::getStatus, CarApplyStatusEnum.Dispatched.getValue());
            queryWrapper.lambda().gt(VehicleApply::getPredictCarTime,availableParam.getPredictTransportTime());
            queryWrapper.lambda().lt(VehicleApply::getPredictTransportTime,availableParam.getPredictCarTime());
            //判断当前预定时间下，可用的车辆信息
            List<VehicleApply> dbVehicleApply = this.list(queryWrapper);
            //该区间不可用车辆
            List<String> haveUsedVehicle = dbVehicleApply.stream().map(x->x.getVehicleId()).distinct().collect(Collectors.toList());
            //该区间不可用司机
            List<String> haveUsedDriver = dbVehicleApply.stream().map(x->x.getDriverUserId()).distinct().collect(Collectors.toList());
            //可用车辆
            QueryWrapper<VehicleInformation> queryWrapper1 = new QueryWrapper<>();
            if(CollUtil.isNotEmpty(haveUsedVehicle))
            {
                queryWrapper1.lambda().notIn(VehicleInformation::getId,haveUsedVehicle);
            }
            List<VehicleInformation> dbVehicleInfo = vehicleInformationService.list(queryWrapper1);
            result.setAvailableVehicles(BeanUtil.copyToList(dbVehicleInfo, VehicleInfoDto.class));
            //可用司机
            QueryWrapper<Drivers> queryWrapper2 = new QueryWrapper<>();
            if(CollUtil.isNotEmpty(haveUsedDriver))
            {
                queryWrapper2.lambda().notIn(Drivers::getDriverUserId,haveUsedDriver);
            }
            List<Drivers> dbDriverInfo = driversService.list(queryWrapper2);
            result.setAvailableDrivers(BeanUtil.copyToList(dbDriverInfo, DriverInfoDto.class));
        }
        return result;
    }

    @Override
    public H5VehicleDto getH5VehicleDetail(String id) {
        VehicleInformation vehicleInformation = vehicleInformationService.getById(id);
        H5VehicleDto h5VehicleDto = BeanUtil.copyProperties(vehicleInformation,H5VehicleDto.class);
        //处理特殊信息字段
        if(ObjectUtil.isNotEmpty(h5VehicleDto)){
            h5VehicleDto.setToReferenceMileage(toValue(h5VehicleDto.getReferenceMileage()).add(toValue(h5VehicleDto.getLastMileage())).subtract(toValue(h5VehicleDto.getTotalMileage())).setScale(2));
            //用车申请，统计各车（车辆Id）出车次数，行驶里程数 【只统计已完成的订单】
            //里程补登，统计各车（车辆Id）补登总里程
            List<VehicleApply> vehicleApplies = this.list(new QueryWrapper<VehicleApply>().lambda().eq(VehicleApply::getVehicleId,id).eq(VehicleApply::getStatus,CarApplyStatusEnum.Finish.getValue()));
            int thisCount = vehicleApplies.size();
            BigDecimal thisTotalMileage = BigDecimal.ZERO;
            vehicleApplies.stream().filter(x->ObjectUtil.isNotEmpty(x.getSubTotal())).forEach(x->thisTotalMileage.add(x.getSubTotal()));
            //里程补登统计
            List<EnterMileage> enterMileages = enterMileageService.list(new QueryWrapper<EnterMileage>().lambda().eq(EnterMileage::getVehicleId,id));
            BigDecimal thisTotalEnterMileage = BigDecimal.ZERO;
            enterMileages.stream().filter(x->ObjectUtil.isNotEmpty(x.getMileages())).forEach(x->thisTotalEnterMileage.add(x.getMileages()));
            //加油量统计
            List<FuelRegister> fuelRegisters = fuelRegisterService.list(new QueryWrapper<FuelRegister>().lambda().eq(FuelRegister::getVehicleId,id));
            BigDecimal thisTotalOilsFuel = BigDecimal.ZERO;
            fuelRegisters.stream().filter(x->ObjectUtil.isNotEmpty(x.getFuelCharge())).forEach(x->thisTotalOilsFuel.add(x.getFuelCharge()));

            h5VehicleDto.setAvgMileage(thisCount == 0 ? null: thisTotalMileage.divide(BigDecimal.valueOf(thisCount),2, RoundingMode.HALF_UP));
            h5VehicleDto.setAvgOil(thisTotalMileage.add(thisTotalEnterMileage).compareTo(BigDecimal.ZERO) == 0 ? null : thisTotalOilsFuel.divide(thisTotalMileage.add(thisTotalEnterMileage),2,RoundingMode.HALF_UP));
        }
        return h5VehicleDto;
    }

    @Override
    public List<H5CarOrderDto> getH5CarOrderList(String driverUserId, Boolean IsShowAll) {
        QueryWrapper<VehicleApply> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(driverUserId)){
            queryWrapper.lambda().eq(VehicleApply::getDriverUserId,driverUserId);
        }
        if(ObjectUtil.isNotEmpty(IsShowAll) && !IsShowAll){
            queryWrapper.lambda().eq(VehicleApply::getStatus,CarApplyStatusEnum.Dispatched.getValue());
        }
        queryWrapper.lambda().orderByDesc(VehicleApply::getCreateTime);
        List<VehicleApply> vehicleApplies = this.list(queryWrapper);
        return BeanUtil.copyToList(vehicleApplies,H5CarOrderDto.class);
    }

    @Override
    public void drivingRegistrationStart(H5CarRegisterFromDto h5CarRegisterFromDto) {
        VehicleApply vehicleApply = this.getById(h5CarRegisterFromDto.getId());
        vehicleApply.setStartTime(h5CarRegisterFromDto.getStartTime());
        vehicleApply.setStartMileage(h5CarRegisterFromDto.getStartMileage());
        this.updateById(vehicleApply);
        VehicleInformation vehicleInformation = vehicleInformationService.getById(vehicleApply.getVehicleId());
        vehicleInformation.setStatus(CarStatusEnum.Out.getValue());
        vehicleInformationService.updateById(vehicleInformation);
        QueryWrapper<Drivers> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Drivers::getDriverUserId,vehicleApply.getDriverUserId());
        queryWrapper.lambda().last("limit 1");
        Drivers driver = driversService.getOne(queryWrapper);
        driver.setStatus(DriverStatusEnum.Out.getValue());
        driversService.updateById(driver);

    }

    @Override
    public void drivingRegistrationEnd(H5CarRegisterEndDto h5CarRegisterEndDto) {
        VehicleApply vehicleApply = this.getById(h5CarRegisterEndDto.getId());
        if(ObjectUtil.isEmpty(vehicleApply) || ObjectUtil.isEmpty(vehicleApply.getStartMileage())) {
            throw new CommonException("请先进行发车登记");
        }
        vehicleApply.setEndTime(h5CarRegisterEndDto.getEndTime());
        vehicleApply.setEndMileage(toValue(h5CarRegisterEndDto.getEndMileage()));
        vehicleApply.setSubTotal(vehicleApply.getEndMileage().subtract(vehicleApply.getStartMileage()));
        vehicleApply.setStatus(CarApplyStatusEnum.Finish.getValue());
        vehicleApply.setDriverMemo(h5CarRegisterEndDto.getDriverMemo());
        this.updateById(vehicleApply);

        //还车登记，更新车辆总行驶里程，车辆状态
        VehicleInformation vehicleInformation = vehicleInformationService.getById(vehicleApply.getVehicleId());
        vehicleInformation.setTotalMileage(vehicleApply.getEndMileage());
        vehicleInformation.setStatus(CarStatusEnum.Useable.getValue());
        vehicleInformationService.updateById(vehicleInformation);

        //还车登记时，更新司机状态
        QueryWrapper<Drivers> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Drivers::getDriverUserId,vehicleApply.getDriverUserId());
        queryWrapper.lambda().last("limit 1");
        Drivers driver = driversService.getOne(queryWrapper);
        driver.setStatus(DriverStatusEnum.Free.getValue());
        driversService.updateById(driver);

        //派车单结束后，发送评价给用户（先判断是否已评价过）
        List<ServiceRatings> serviceRates = serviceRatingsService.list(new QueryWrapper<ServiceRatings>().lambda().eq(ServiceRatings::getVehicleApplyId,vehicleApply.getId()));
        if (CollUtil.isEmpty(serviceRates))
        {
            String title = "用车评价";
            String url = driversProperties.getLinkUrl()+ "appraise/userAppraise?vehicleapplyId=" + vehicleApply.getId()+ "&drivername=" + vehicleApply.getDriver();
            iDingDingService.sendCarMsg(driversProperties.getAgentId(),driversProperties.getAppKey(),driversProperties.getAppSecret(),
                    JSONUtil.parseObj(vehicleApply),title,url,h5CarRegisterEndDto.getUserDingDingUserId());
        }

        //派车单完成，发送通知给老板娘
        if (ObjectUtil.isNotEmpty(h5CarRegisterEndDto.getBossDingDingUserId()))
        {
            String title = "派车单";
            String url = driversProperties.getLinkUrl() + "Activing/activingInfo?applyid=" + vehicleApply.getId();
            iDingDingService.sendCarMsg(driversProperties.getAgentId(),driversProperties.getAppKey(),driversProperties.getAppSecret(),
                    JSONUtil.parseObj(vehicleApply),title,url,h5CarRegisterEndDto.getBossDingDingUserId());
        }
    }

    @Override
    public H5CarUserInfoDto getH5UserInfo(String id) {
        QueryWrapper<Drivers> driversQueryWrapper = new QueryWrapper<>();
        driversQueryWrapper.lambda().eq(Drivers::getDriverUserId,id);
        driversQueryWrapper.lambda().last("limit 1");
        Drivers drivers = driversService.getOne(driversQueryWrapper);
        H5CarUserInfoDto h5CarUserInfoDto = BeanUtil.copyProperties(drivers,H5CarUserInfoDto.class);
        //获取司机的派车单
        List<VehicleApply> dbApply = this.list(new QueryWrapper<VehicleApply>().lambda().eq(VehicleApply::getDriverUserId,id));
        h5CarUserInfoDto.setOutCarCounts(dbApply.size());
        //获取司机的违章
        List<DriverBreaks> dbBreak = driverBreaksService.list(new QueryWrapper<DriverBreaks>().lambda().eq(DriverBreaks::getDriverUserId,id));
        h5CarUserInfoDto.setBreaksCounts(dbBreak.size());
        //获取司机的出险
        List<DangerRegister> dbDanger = dangerRegisterService.list(new QueryWrapper<DangerRegister>().lambda().eq(DangerRegister::getDriverUserId,id));
        h5CarUserInfoDto.setDangerCounts(dbDanger.size());
        return h5CarUserInfoDto;
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

    /**
     * BigDecimal 两个数相除
     * @param value1
     * @param value2
     * @return
     */
    public BigDecimal divideValue(BigDecimal value1,BigDecimal value2){
        if(value2 == null || value2.compareTo(BigDecimal.ZERO) == 1){
            return null;
        }
        return value1.divide(value2,2,BigDecimal.ROUND_HALF_UP);
    }


}
