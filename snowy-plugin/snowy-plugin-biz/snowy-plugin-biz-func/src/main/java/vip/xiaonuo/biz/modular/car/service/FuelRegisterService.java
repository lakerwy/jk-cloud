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
import vip.xiaonuo.biz.modular.car.entity.FuelRegister;
import vip.xiaonuo.biz.modular.car.param.FuelRegisterAddParam;
import vip.xiaonuo.biz.modular.car.param.FuelRegisterEditParam;
import vip.xiaonuo.biz.modular.car.param.FuelRegisterIdParam;
import vip.xiaonuo.biz.modular.car.param.FuelRegisterPageParam;

import java.util.List;

/**
 * CAR-加油记录Service接口
 *
 * @author yong.ye
 * @date  2023/11/08 16:01
 **/
public interface FuelRegisterService extends IService<FuelRegister> {

    /**
     * 获取CAR-加油记录分页
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    Page<FuelRegister> page(FuelRegisterPageParam fuelRegisterPageParam);

    /**
     * 添加CAR-加油记录
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    void add(FuelRegisterAddParam fuelRegisterAddParam);

    /**
     * 编辑CAR-加油记录
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    void edit(FuelRegisterEditParam fuelRegisterEditParam);

    /**
     * 删除CAR-加油记录
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    void delete(List<FuelRegisterIdParam> fuelRegisterIdParamList);

    /**
     * 获取CAR-加油记录详情
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    FuelRegister detail(FuelRegisterIdParam fuelRegisterIdParam);

    /**
     * 获取CAR-加油记录详情
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     **/
    FuelRegister queryEntity(String id);
}
