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
package vip.xiaonuo.biz.modular.car.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.car.entity.VehicleInformation;
import vip.xiaonuo.biz.modular.car.param.VehicleInformationAddParam;
import vip.xiaonuo.biz.modular.car.param.VehicleInformationEditParam;
import vip.xiaonuo.biz.modular.car.param.VehicleInformationIdParam;
import vip.xiaonuo.biz.modular.car.param.VehicleInformationPageParam;
import vip.xiaonuo.biz.modular.car.vo.H5CarDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * BIZ_CAR_VEHICLE_INFORMATIONService接口
 *
 * @author yong.ye
 * @date  2023/11/08 17:17
 **/
public interface VehicleInformationService extends IService<VehicleInformation> {

    /**
     * 获取BIZ_CAR_VEHICLE_INFORMATION分页
     *
     * @author yong.ye
     * @date  2023/11/08 17:17
     */
    Page<VehicleInformation> page(VehicleInformationPageParam vehicleInformationPageParam);

    /**
     * 添加BIZ_CAR_VEHICLE_INFORMATION
     *
     * @author yong.ye
     * @date  2023/11/08 17:17
     */
    void add(VehicleInformationAddParam vehicleInformationAddParam);

    /**
     * 编辑BIZ_CAR_VEHICLE_INFORMATION
     *
     * @author yong.ye
     * @date  2023/11/08 17:17
     */
    void edit(VehicleInformationEditParam vehicleInformationEditParam);

    /**
     * 删除BIZ_CAR_VEHICLE_INFORMATION
     *
     * @author yong.ye
     * @date  2023/11/08 17:17
     */
    void delete(List<VehicleInformationIdParam> vehicleInformationIdParamList);

    /**
     * 获取BIZ_CAR_VEHICLE_INFORMATION详情
     *
     * @author yong.ye
     * @date  2023/11/08 17:17
     */
    VehicleInformation detail(VehicleInformationIdParam vehicleInformationIdParam);

    /**
     * 获取BIZ_CAR_VEHICLE_INFORMATION详情
     *
     * @author yong.ye
     * @date  2023/11/08 17:17
     **/
    VehicleInformation queryEntity(String id);

    /**
     * H5端 - 首页 - 获取所有车辆信息列表
     * @return
     */
    List<H5CarDto> getH5VehicleList();

    /**
     * 里程补登：新增里程补登（总里程数+本次补登里程）  编辑里程补登记录（总里程数-旧补登里程+新补登里程）
     * @param vehicleId 车辆Id
     * @param isEdit 是否为编辑操作
     * @param mileages 补登里程：本次补登里程数
     * @param oldMileages 旧补登里程：修改前的补登里程数
     */
    void updateVehicleTotalMileage(String vehicleId, Boolean isEdit, BigDecimal mileages, BigDecimal oldMileages);

    /**
     * 车辆加油：新增加油记录（总加油量+本次加油量）  编辑充值记录（总加油量-旧加油量+新加油量）
     * @param vehicleId 车辆Id
     * @param isEdit 是否为编辑操作
     * @param fuelCharge 加油量：本次加油量
     * @param oldFuelCharge 旧加油量：修改前的加油量
     */
    void updateVehicleTotalOilFuel(String vehicleId, Boolean isEdit, BigDecimal fuelCharge, BigDecimal oldFuelCharge);

}
