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
import vip.xiaonuo.biz.modular.car.entity.VehicleApply;
import vip.xiaonuo.biz.modular.car.param.*;
import vip.xiaonuo.biz.modular.car.vo.*;

import javax.validation.Valid;
import java.util.List;

/**
 * CAR-用车申请单Service接口
 *
 * @author yong.ye
 * @date  2023/11/08 16:04
 **/
public interface VehicleApplyService extends IService<VehicleApply> {

    /**
     * 获取CAR-用车申请单分页
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    Page<VehicleApply> page(VehicleApplyPageParam vehicleApplyPageParam);

    /**
     * 添加CAR-用车申请单
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    void add(VehicleApplyAddParam vehicleApplyAddParam);

    void add2(VehicleApplyAddParam vehicleApplyAddParam);

    /**
     * 编辑CAR-用车申请单
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    void edit(VehicleApplyEditParam vehicleApplyEditParam);

    /**
     * 删除CAR-用车申请单
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    void delete(List<VehicleApplyIdParam> vehicleApplyIdParamList);

    /**
     * 获取CAR-用车申请单详情
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    VehicleApplyDetailDto detail(VehicleApplyIdParam vehicleApplyIdParam);

    /**
     * 获取CAR-用车申请单详情
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     **/
    VehicleApply queryEntity(String id);

    /**
     * 调整用车申请单（派车单）的状态
     * @param updateApplyStatusParam
     */
    void updateStatus(UpdateApplyStatusParam updateApplyStatusParam);

    /**
     * 给申请单（用车申请单），分派车辆与驾驶员（车辆调度员分配驾驶员和车辆）
     * @param dispatchCarParam
     */
    void dispatchCar(DispatchCarParam dispatchCarParam);

    /**
     * 根据用户UserId，获取用车申请列表【筛选+分页】
     * @param vehicleApplyPageParam
     * @return
     */
    Page<VehicleApply> pageByUserId(VehicleApplyPageParam vehicleApplyPageParam);

    /**
     * 根据用户UserId，获取派车单待评价列表【筛选+分页】
     * @param vehicleApplyPageParam
     * @return
     */
    Page<VehicleApply> ratePageByUserId(VehicleApplyPageParam vehicleApplyPageParam);

    /**
     * 用车统计表
     * @param vehicleChartParam
     * @return
     */
    VehicleChartViewModel getVehicleChart(VehicleChartParam vehicleChartParam);

    /**
     * 油耗分摊
     * @param oilChartParam
     * @return
     */
    AvgOilChartViewModel getAvgOilChart(OilChartParam oilChartParam);

    /**
     * 用车预定列表
     * @param carPreParam
     * @return
     */
    Page<CarPreViewModel> getCarPrePageList(CarPreParam carPreParam);

    /**
     * 获取当前申请时间段，可用的车辆和司机信息
     * @param availableParam
     * @return
     */
    AvailableInfo getAvailableInfo(AvailableParam availableParam);

    /**
     * 首页 - 根据车辆Id，获取H5端车辆详情
     * @param id
     * @return
     */
    H5VehicleDto getH5VehicleDetail(String id);

    /**
     * 派车单 - 获取当前司机名下的所有派车单
     * @param driverUserId
     * @param isShowAll
     * @return
     */
    List<H5CarOrderDto> getH5CarOrderList(String driverUserId, Boolean isShowAll);

    /**
     *  派车单 - 发车登记
     * @param h5CarRegisterFromDto
     */
    void drivingRegistrationStart(H5CarRegisterFromDto h5CarRegisterFromDto);

    /**
     * 派车单 - 还车登记
     * @param h5CarRegisterEndDto
     */
    void drivingRegistrationEnd(H5CarRegisterEndDto h5CarRegisterEndDto);

    /**
     * 个人设置 - 获取司机（用户Id）详情
     * @param id
     * @return
     */
    H5CarUserInfoDto getH5UserInfo(String id);

}
