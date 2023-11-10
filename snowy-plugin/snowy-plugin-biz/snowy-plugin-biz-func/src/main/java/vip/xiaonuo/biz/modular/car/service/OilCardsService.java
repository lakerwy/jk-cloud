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
import vip.xiaonuo.biz.modular.car.entity.OilCards;
import vip.xiaonuo.biz.modular.car.param.OilCardsAddParam;
import vip.xiaonuo.biz.modular.car.param.OilCardsEditParam;
import vip.xiaonuo.biz.modular.car.param.OilCardsIdParam;
import vip.xiaonuo.biz.modular.car.param.OilCardsPageParam;

import java.util.List;

/**
 * CAR-油卡信息Service接口
 *
 * @author yong.ye
 * @date  2023/11/08 16:01
 **/
public interface OilCardsService extends IService<OilCards> {

    /**
     * 获取CAR-油卡信息分页
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    Page<OilCards> page(OilCardsPageParam oilCardsPageParam);

    /**
     * 添加CAR-油卡信息
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    void add(OilCardsAddParam oilCardsAddParam);

    /**
     * 编辑CAR-油卡信息
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    void edit(OilCardsEditParam oilCardsEditParam);

    /**
     * 删除CAR-油卡信息
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    void delete(List<OilCardsIdParam> oilCardsIdParamList);

    /**
     * 获取CAR-油卡信息详情
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    OilCards detail(OilCardsIdParam oilCardsIdParam);

    /**
     * 获取CAR-油卡信息详情
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     **/
    OilCards queryEntity(String id);
}
