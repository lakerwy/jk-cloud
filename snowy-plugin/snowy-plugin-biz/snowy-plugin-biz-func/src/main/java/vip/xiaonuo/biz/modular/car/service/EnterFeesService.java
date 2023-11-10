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
import vip.xiaonuo.biz.modular.car.entity.EnterFees;
import vip.xiaonuo.biz.modular.car.param.EnterFeesAddParam;
import vip.xiaonuo.biz.modular.car.param.EnterFeesEditParam;
import vip.xiaonuo.biz.modular.car.param.EnterFeesIdParam;
import vip.xiaonuo.biz.modular.car.param.EnterFeesPageParam;

import java.util.List;

/**
 * CAR-费用补登Service接口
 *
 * @author yong.ye
 * @date  2023/11/08 15:59
 **/
public interface EnterFeesService extends IService<EnterFees> {

    /**
     * 获取CAR-费用补登分页
     *
     * @author yong.ye
     * @date  2023/11/08 15:59
     */
    Page<EnterFees> page(EnterFeesPageParam enterFeesPageParam);

    /**
     * 添加CAR-费用补登
     *
     * @author yong.ye
     * @date  2023/11/08 15:59
     */
    void add(EnterFeesAddParam enterFeesAddParam);

    /**
     * 编辑CAR-费用补登
     *
     * @author yong.ye
     * @date  2023/11/08 15:59
     */
    void edit(EnterFeesEditParam enterFeesEditParam);

    /**
     * 删除CAR-费用补登
     *
     * @author yong.ye
     * @date  2023/11/08 15:59
     */
    void delete(List<EnterFeesIdParam> enterFeesIdParamList);

    /**
     * 获取CAR-费用补登详情
     *
     * @author yong.ye
     * @date  2023/11/08 15:59
     */
    EnterFees detail(EnterFeesIdParam enterFeesIdParam);

    /**
     * 获取CAR-费用补登详情
     *
     * @author yong.ye
     * @date  2023/11/08 15:59
     **/
    EnterFees queryEntity(String id);
}
