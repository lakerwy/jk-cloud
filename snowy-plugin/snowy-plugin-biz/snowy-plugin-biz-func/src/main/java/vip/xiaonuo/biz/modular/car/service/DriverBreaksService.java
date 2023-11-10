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
import vip.xiaonuo.biz.modular.car.entity.DriverBreaks;
import vip.xiaonuo.biz.modular.car.param.DriverBreaksAddParam;
import vip.xiaonuo.biz.modular.car.param.DriverBreaksEditParam;
import vip.xiaonuo.biz.modular.car.param.DriverBreaksIdParam;
import vip.xiaonuo.biz.modular.car.param.DriverBreaksPageParam;

import java.util.List;

/**
 * CAR-违章记录Service接口
 *
 * @author yong.ye
 * @date  2023/11/08 15:58
 **/
public interface DriverBreaksService extends IService<DriverBreaks> {

    /**
     * 获取CAR-违章记录分页
     *
     * @author yong.ye
     * @date  2023/11/08 15:58
     */
    Page<DriverBreaks> page(DriverBreaksPageParam driverBreaksPageParam);

    /**
     * 添加CAR-违章记录
     *
     * @author yong.ye
     * @date  2023/11/08 15:58
     */
    void add(DriverBreaksAddParam driverBreaksAddParam);

    /**
     * 编辑CAR-违章记录
     *
     * @author yong.ye
     * @date  2023/11/08 15:58
     */
    void edit(DriverBreaksEditParam driverBreaksEditParam);

    /**
     * 删除CAR-违章记录
     *
     * @author yong.ye
     * @date  2023/11/08 15:58
     */
    void delete(List<DriverBreaksIdParam> driverBreaksIdParamList);

    /**
     * 获取CAR-违章记录详情
     *
     * @author yong.ye
     * @date  2023/11/08 15:58
     */
    DriverBreaks detail(DriverBreaksIdParam driverBreaksIdParam);

    /**
     * 获取CAR-违章记录详情
     *
     * @author yong.ye
     * @date  2023/11/08 15:58
     **/
    DriverBreaks queryEntity(String id);
}
