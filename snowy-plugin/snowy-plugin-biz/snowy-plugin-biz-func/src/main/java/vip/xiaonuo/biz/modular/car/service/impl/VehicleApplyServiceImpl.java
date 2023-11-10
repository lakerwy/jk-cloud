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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.car.config.DriversProperties;
import vip.xiaonuo.biz.modular.car.config.UsersProperties;
import vip.xiaonuo.biz.modular.car.entity.Drivers;
import vip.xiaonuo.biz.modular.car.entity.VehicleInformation;
import vip.xiaonuo.biz.modular.car.enums.CarApplyStatusEnum;
import vip.xiaonuo.biz.modular.car.param.*;
import vip.xiaonuo.biz.modular.car.service.DriversService;
import vip.xiaonuo.biz.modular.car.service.VehicleInformationService;
import vip.xiaonuo.biz.modular.car.vo.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.car.entity.VehicleApply;
import vip.xiaonuo.biz.modular.car.mapper.VehicleApplyMapper;
import vip.xiaonuo.biz.modular.car.service.VehicleApplyService;
import vip.xiaonuo.dev.api.IDingDingService;

import javax.annotation.Resource;
import java.util.List;
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
        return vehicleApplyMapper.getRatePageByUserId(vehicleApplyPageParam.getUserId(), CarApplyStatusEnum.Finish.getValue());
    }

    @Override
    public VehicleChartViewModel getVehicleChart(VehicleChartParam vehicleChartParam) {
        return null;
    }

    @Override
    public AvgOilChartViewModel getAvgOilChart(OilChartParam oilChartParam) {
        return null;
    }

    @Override
    public Page<CarPreViewModel> getCarPrePageList(CarPreParam carPreParam) {
        return null;
    }

    @Override
    public AvailableInfo getAvailableInfo(AvailableParam availableParam) {
        return null;
    }
}
