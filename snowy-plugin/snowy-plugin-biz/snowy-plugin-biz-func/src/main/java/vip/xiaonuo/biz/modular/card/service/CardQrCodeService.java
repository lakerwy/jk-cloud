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
import vip.xiaonuo.biz.modular.card.entity.CardQrCode;
import vip.xiaonuo.biz.modular.card.param.CardQrCodeAddParam;
import vip.xiaonuo.biz.modular.card.param.CardQrCodeEditParam;
import vip.xiaonuo.biz.modular.card.param.CardQrCodeIdParam;
import vip.xiaonuo.biz.modular.card.param.CardQrCodePageParam;

import java.util.List;

/**
 * Ecard-电子名片-二维码Service接口
 *
 * @author yong.ye
 * @date  2024/02/21 15:52
 **/
public interface CardQrCodeService extends IService<CardQrCode> {

    /**
     * 获取Ecard-电子名片-二维码分页
     *
     * @author yong.ye
     * @date  2024/02/21 15:52
     */
    Page<CardQrCode> page(CardQrCodePageParam cardQrCodePageParam);

    /**
     * 添加Ecard-电子名片-二维码
     *
     * @author yong.ye
     * @date  2024/02/21 15:52
     */
    void add(CardQrCodeAddParam cardQrCodeAddParam);

    /**
     * 编辑Ecard-电子名片-二维码
     *
     * @author yong.ye
     * @date  2024/02/21 15:52
     */
    void edit(CardQrCodeEditParam cardQrCodeEditParam);

    /**
     * 删除Ecard-电子名片-二维码
     *
     * @author yong.ye
     * @date  2024/02/21 15:52
     */
    void delete(List<CardQrCodeIdParam> cardQrCodeIdParamList);

    /**
     * 获取Ecard-电子名片-二维码详情
     *
     * @author yong.ye
     * @date  2024/02/21 15:52
     */
    CardQrCode detail(CardQrCodeIdParam cardQrCodeIdParam);

    /**
     * 获取Ecard-电子名片-二维码详情
     *
     * @author yong.ye
     * @date  2024/02/21 15:52
     **/
    CardQrCode queryEntity(String id);
}
