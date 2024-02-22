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
package vip.xiaonuo.biz.modular.card.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.card.entity.CardImages;
import vip.xiaonuo.biz.modular.card.entity.CardQrCode;
import vip.xiaonuo.biz.modular.card.param.*;
import vip.xiaonuo.biz.modular.card.service.CardImagesService;
import vip.xiaonuo.biz.modular.card.service.CardQrCodeService;
import vip.xiaonuo.biz.modular.card.vo.EcardVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.card.entity.Ecards;
import vip.xiaonuo.biz.modular.card.service.EcardsService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Ecard-电子名片控制器
 *
 * @author yong.ye
 * @date  2023/11/13 11:22
 */
@Api(tags = "Ecard-电子名片控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EcardsController {

    @Resource
    private EcardsService ecardsService;


    @Resource
    private CardImagesService cardImagesService;

    @Resource
    private CardQrCodeService cardQrCodeService;

    /**
     * 获取Ecard-电子名片分页
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取Ecard-电子名片分页")
//    @SaCheckPermission("/biz/applet/eCard/getEcardsPageList")
    @GetMapping("/biz/applet/eCard/getEcardsPageList")
    public CommonResult<Page<Ecards>> page(EcardsPageParam ecardsPageParam) {
        return CommonResult.data(ecardsService.page(ecardsPageParam));
    }

    /**
     * 添加Ecard-电子名片
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加Ecard-电子名片")
    @CommonLog("添加Ecard-电子名片")
//    @SaCheckPermission("/biz/applet/eCard/addEcards")
    @PostMapping("/biz/applet/eCard/addEcards")
    public CommonResult<String> add(@RequestBody @Valid EcardsAddParam ecardsAddParam) {
        ecardsService.add(ecardsAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑Ecard-电子名片
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑Ecard-电子名片")
    @CommonLog("编辑Ecard-电子名片")
//    @SaCheckPermission("/biz/applet/eCard/updateEcards")
    @PostMapping("/biz/applet/eCard/updateEcards")
    public CommonResult<String> edit(@RequestBody @Valid EcardsEditParam ecardsEditParam) {
        ecardsService.edit(ecardsEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除Ecard-电子名片
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除Ecard-电子名片")
    @CommonLog("删除Ecard-电子名片")
//    @SaCheckPermission("/biz/applet/eCard/deleteEcards")
    @PostMapping("/biz/applet/eCard/deleteEcards")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<EcardsIdParam> ecardsIdParamList) {
        ecardsService.delete(ecardsIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取Ecard-电子名片详情(通过cardId主键获取)
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取Ecard-电子名片详情(通过cardId主键获取)")
//    @SaCheckPermission("/biz/applet/eCard/getEcardByCardId")
    @GetMapping("/biz/applet/eCard/getEcardByCardId")
    public CommonResult<EcardVo> detailByCardId(String cardId) {
        return CommonResult.data(ecardsService.getByCardId(cardId));
    }

    /**
     * 获取Ecard-电子名片详情(通过userId获取)
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取Ecard-电子名片详情(通过userId获取)")
//    @SaCheckPermission("/biz/applet/eCard/getEcardByUserId")
    @GetMapping("/biz/applet/eCard/getEcardByUserId")
    public CommonResult<EcardVo> detailByUserId(String userId) {
        return CommonResult.data(ecardsService.detailByUserId(userId));
    }

    /**
     * 获取Ecard-电子名片详情(通过openId获取)【未用到】
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取Ecard-电子名片详情(通过openId获取)【未用到】")
//    @SaCheckPermission("/biz/applet/eCard/getEcardByOpenId")
    @GetMapping("/biz/applet/eCard/getEcardByOpenId")
    public CommonResult<EcardVo> detailByOpenId(String openId) {
        return CommonResult.data(ecardsService.detailByOpenId(openId));
    }

    /**
     * 获取电子名片小程序OpenId
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("获取电子名片小程序OpenId")
//    @SaCheckPermission("/biz/applet/eCard/getAppletCardOpenIdByCode")
    @GetMapping("/biz/applet/eCard/getAppletCardOpenIdByCode")
    public CommonResult<JSONObject> openIdByCode(String code) {
        return CommonResult.data(ecardsService.openIdByCode(code));
    }








    /**
     * 获取Ecard-电子名片-图片分页
     *
     * @author yong.ye
     * @date  2024/02/18 15:40
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("获取Ecard-电子名片-图片分页")
//    @SaCheckPermission("/biz/applet/eCard/image/page")
    @GetMapping("/biz/applet/eCard/image/page")
    public CommonResult<Page<CardImages>> imgPage(CardImagesPageParam cardImagesPageParam) {
        return CommonResult.data(cardImagesService.page(cardImagesPageParam));
    }

    /**
     * 添加Ecard-电子名片-图片
     *
     * @author yong.ye
     * @date  2024/02/18 15:40
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("添加Ecard-电子名片-图片")
    @CommonLog("添加Ecard-电子名片-图片")
//    @SaCheckPermission("/biz/applet/eCard/image/add")
    @PostMapping("/biz/applet/eCard/image/add")
    public CommonResult<String> imgAdd(@RequestBody @Valid CardImagesAddParam cardImagesAddParam) {
        cardImagesService.add(cardImagesAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑Ecard-电子名片-图片
     *
     * @author yong.ye
     * @date  2024/02/18 15:40
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("编辑Ecard-电子名片-图片")
    @CommonLog("编辑Ecard-电子名片-图片")
//    @SaCheckPermission("/biz/applet/eCard/image/edit")
    @PostMapping("/biz/applet/eCard/image/edit")
    public CommonResult<String> imgEdit(@RequestBody @Valid CardImagesEditParam cardImagesEditParam) {
        cardImagesService.edit(cardImagesEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除Ecard-电子名片-图片
     *
     * @author yong.ye
     * @date  2024/02/18 15:40
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("删除Ecard-电子名片-图片")
    @CommonLog("删除Ecard-电子名片-图片")
//    @SaCheckPermission("/biz/applet/eCard/image/delete")
    @PostMapping("/biz/applet/eCard/image/delete")
    public CommonResult<String> imgDelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<CardImagesIdParam> cardImagesIdParamList) {
        cardImagesService.delete(cardImagesIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取Ecard-电子名片-图片详情
     *
     * @author yong.ye
     * @date  2024/02/18 15:40
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("获取Ecard-电子名片-图片详情")
//    @SaCheckPermission("/biz/applet/eCard/image/detail")
    @GetMapping("/biz/applet/eCard/image/detail")
    public CommonResult<CardImages> imgDetail(@Valid CardImagesIdParam cardImagesIdParam) {
        return CommonResult.data(cardImagesService.detail(cardImagesIdParam));
    }




    /**
     * 获取电子名片小程序,AccessToken
     *
     * @author yong.ye
     * @date  2024/02/21 11:22
     */
    @ApiOperationSupport(order = 20)
    @ApiOperation("获取电子名片小程序,AccessToken")
    @GetMapping("/biz/applet/eCard/getAccessToken")
    public CommonResult<String> getAccessToken() {
        return CommonResult.data(ecardsService.getAccessToken());
    }

    /**
     * 电子名片小程序,生成二维码
     *
     * @author yong.ye
     * @date  2024/02/21 11:22
     */
    @ApiOperationSupport(order = 21)
    @ApiOperation("电子名片小程序,生成二维码")
    @CommonLog("电子名片小程序,生成二维码")
    @PostMapping("/biz/applet/eCard/createQRCode")
    public CommonResult<Boolean> createQRCode(@RequestBody @Valid QRCodeParam qrCodeParam) {
        return CommonResult.data(ecardsService.createQRCode(qrCodeParam));
    }


    /**
     * 获取Ecard-电子名片-二维码分页
     *
     * @author yong.ye
     * @date  2024/02/21 15:52
     */
    @ApiOperationSupport(order = 22)
    @ApiOperation("获取Ecard-电子名片-二维码分页")
    @GetMapping("/biz/applet/eCard/qrCode/page")
    public CommonResult<Page<CardQrCode>> qrCodePage(CardQrCodePageParam cardQrCodePageParam) {
        return CommonResult.data(cardQrCodeService.page(cardQrCodePageParam));
    }

    /**
     * 添加Ecard-电子名片-二维码
     *
     * @author yong.ye
     * @date  2024/02/21 15:52
     */
    @ApiOperationSupport(order = 23)
    @ApiOperation("添加Ecard-电子名片-二维码")
    @CommonLog("添加Ecard-电子名片-二维码")
    @PostMapping("/biz/applet/eCard/qrCode/add")
    public CommonResult<String> qrCodeAdd(@RequestBody @Valid CardQrCodeAddParam cardQrCodeAddParam) {
        cardQrCodeService.add(cardQrCodeAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑Ecard-电子名片-二维码
     *
     * @author yong.ye
     * @date  2024/02/21 15:52
     */
    @ApiOperationSupport(order = 24)
    @ApiOperation("编辑Ecard-电子名片-二维码")
    @CommonLog("编辑Ecard-电子名片-二维码")
    @PostMapping("/biz/applet/eCard/qrCode/edit")
    public CommonResult<String> qrCodeEdit(@RequestBody @Valid CardQrCodeEditParam cardQrCodeEditParam) {
        cardQrCodeService.edit(cardQrCodeEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除Ecard-电子名片-二维码
     *
     * @author yong.ye
     * @date  2024/02/21 15:52
     */
    @ApiOperationSupport(order = 25)
    @ApiOperation("删除Ecard-电子名片-二维码")
    @CommonLog("删除Ecard-电子名片-二维码")
    @PostMapping("/biz/applet/eCard/qrCode/delete")
    public CommonResult<String> qrCodeDelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<CardQrCodeIdParam> cardQrCodeIdParamList) {
        cardQrCodeService.delete(cardQrCodeIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取Ecard-电子名片-二维码详情
     *
     * @author yong.ye
     * @date  2024/02/21 15:52
     */
    @ApiOperationSupport(order = 26)
    @ApiOperation("获取Ecard-电子名片-二维码详情")
    @GetMapping("/biz/applet/eCard/qrCode/detail")
    public CommonResult<CardQrCode> qrCodeDetail(@Valid CardQrCodeIdParam cardQrCodeIdParam) {
        return CommonResult.data(cardQrCodeService.detail(cardQrCodeIdParam));
    }

}
