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

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.card.entity.Ecards;
import vip.xiaonuo.biz.modular.card.param.*;
import vip.xiaonuo.biz.modular.card.vo.EcardVo;

import java.util.List;

/**
 * Ecard-电子名片Service接口
 *
 * @author yong.ye
 * @date  2023/11/13 11:22
 **/
public interface EcardsService extends IService<Ecards> {

    /**
     * 获取Ecard-电子名片分页
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    Page<Ecards> page(EcardsPageParam ecardsPageParam);

    /**
     * 添加Ecard-电子名片
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    void add(EcardsAddParam ecardsAddParam);

    /**
     * 编辑Ecard-电子名片
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    void edit(EcardsEditParam ecardsEditParam);

    /**
     * 删除Ecard-电子名片
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    void delete(List<EcardsIdParam> ecardsIdParamList);

    /**
     * 获取Ecard-电子名片详情
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    Ecards detail(EcardsIdParam ecardsIdParam);

    /**
     * 获取Ecard-电子名片详情
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     **/
    Ecards queryEntity(String id);

    /**
     * 获取Ecard-电子名片详情,含图片资源(通过userId获取)
     * @param userId
     * @return
     */
    EcardVo detailByUserId(String userId);

    /**
     * 获取Ecard-电子名片详情(通过userId获取)
     * @param openId
     * @return
     */
    EcardVo detailByOpenId(String openId);

    /**
     * 获取电子名片小程序OpenId
     * @param code
     * @return
     */
    JSONObject openIdByCode(String code);


    /**
     * 获取Ecard-电子名片详情,含图片资源(通过CardId获取)
     * @param cardId
     * @return
     */
    EcardVo getByCardId(String cardId);

    /**
     * 获取电子名片小程序 AccessToken
     * @return
     */
    String getAccessToken();

    /**
     * 生成微信电子名片二维码
     * @param qrCodeParam
     * @return
     */
    Boolean createQRCode(QRCodeParam qrCodeParam);
}
