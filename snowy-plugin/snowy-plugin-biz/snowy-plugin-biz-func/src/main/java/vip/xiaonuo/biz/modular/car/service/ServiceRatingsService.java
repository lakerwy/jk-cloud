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
import vip.xiaonuo.biz.modular.car.entity.ServiceRatings;
import vip.xiaonuo.biz.modular.car.param.ServiceRatingsAddParam;
import vip.xiaonuo.biz.modular.car.param.ServiceRatingsEditParam;
import vip.xiaonuo.biz.modular.car.param.ServiceRatingsIdParam;
import vip.xiaonuo.biz.modular.car.param.ServiceRatingsPageParam;
import vip.xiaonuo.biz.modular.car.vo.ServiceRatingsViewModel;

import java.util.List;

/**
 * CAR-用车服务评价Service接口
 *
 * @author yong.ye
 * @date  2023/11/08 16:03
 **/
public interface ServiceRatingsService extends IService<ServiceRatings> {

    /**
     * 获取CAR-用车服务评价分页
     *
     * @author yong.ye
     * @date  2023/11/08 16:03
     */
    Page<ServiceRatings> page(ServiceRatingsPageParam serviceRatingsPageParam);

    /**
     * 添加CAR-用车服务评价
     *
     * @author yong.ye
     * @date  2023/11/08 16:03
     */
    void add(ServiceRatingsAddParam serviceRatingsAddParam);

    /**
     * 编辑CAR-用车服务评价
     *
     * @author yong.ye
     * @date  2023/11/08 16:03
     */
    void edit(ServiceRatingsEditParam serviceRatingsEditParam);

    /**
     * 删除CAR-用车服务评价
     *
     * @author yong.ye
     * @date  2023/11/08 16:03
     */
    void delete(List<ServiceRatingsIdParam> serviceRatingsIdParamList);

    /**
     * 获取CAR-用车服务评价详情
     *
     * @author yong.ye
     * @date  2023/11/08 16:03
     */
    ServiceRatingsViewModel detail(ServiceRatingsIdParam serviceRatingsIdParam);

    /**
     * 获取CAR-用车服务评价详情
     *
     * @author yong.ye
     * @date  2023/11/08 16:03
     **/
    ServiceRatings queryEntity(String id);


    /**
     * 根据申请单Id（派车单VehicleApply.Id）获取本派车单的评论详情
     * @param applyId 用车申请单Id
     * @return
     */
    ServiceRatingsViewModel detailByApplyId(String applyId);


    /**
     * 根据司机用户Id（SysUser.UserId = 派车单VehicleApply.DriverId），获取司机名下所有派车单评论
     * @param driverId 司机用户Id
     * @return
     */
    List<ServiceRatingsViewModel> detailByDriverId(String driverId);

    /**
     * 获取CAR-用车服务评价分页
     *
     * @author yong.ye
     * @date  2023/11/08 16:03
     */
    Page<ServiceRatingsViewModel> pageList(ServiceRatingsPageParam serviceRatingsPageParam);
}
