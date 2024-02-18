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
package vip.xiaonuo.biz.modular.card.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.card.entity.CardImages;
import vip.xiaonuo.biz.modular.card.param.CardImagesAddParam;
import vip.xiaonuo.biz.modular.card.param.CardImagesEditParam;
import vip.xiaonuo.biz.modular.card.param.CardImagesIdParam;
import vip.xiaonuo.biz.modular.card.param.CardImagesPageParam;

import java.util.List;

/**
 * Ecard-电子名片-图片Service接口
 *
 * @author yong.ye
 * @date  2024/02/18 15:40
 **/
public interface CardImagesService extends IService<CardImages> {

    /**
     * 获取Ecard-电子名片-图片分页
     *
     * @author yong.ye
     * @date  2024/02/18 15:40
     */
    Page<CardImages> page(CardImagesPageParam cardImagesPageParam);

    /**
     * 添加Ecard-电子名片-图片
     *
     * @author yong.ye
     * @date  2024/02/18 15:40
     */
    void add(CardImagesAddParam cardImagesAddParam);

    /**
     * 编辑Ecard-电子名片-图片
     *
     * @author yong.ye
     * @date  2024/02/18 15:40
     */
    void edit(CardImagesEditParam cardImagesEditParam);

    /**
     * 删除Ecard-电子名片-图片
     *
     * @author yong.ye
     * @date  2024/02/18 15:40
     */
    void delete(List<CardImagesIdParam> cardImagesIdParamList);

    /**
     * 获取Ecard-电子名片-图片详情
     *
     * @author yong.ye
     * @date  2024/02/18 15:40
     */
    CardImages detail(CardImagesIdParam cardImagesIdParam);

    /**
     * 获取Ecard-电子名片-图片详情
     *
     * @author yong.ye
     * @date  2024/02/18 15:40
     **/
    CardImages queryEntity(String id);

    /**
     * 根据外键Id，获取 公用资源+私有资源
     * @return
     */
    List<CardImages> getImagesById(String cardId);
}
