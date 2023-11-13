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
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.card.entity.Ecards;
import vip.xiaonuo.biz.modular.card.param.EcardsAddParam;
import vip.xiaonuo.biz.modular.card.param.EcardsEditParam;
import vip.xiaonuo.biz.modular.card.param.EcardsIdParam;
import vip.xiaonuo.biz.modular.card.param.EcardsPageParam;
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

    /**
     * 获取Ecard-电子名片分页
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取Ecard-电子名片分页")
    @SaCheckPermission("/biz/applet/eCard/getEcardsPageList")
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
    @SaCheckPermission("/biz/applet/eCard/addEcards")
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
    @SaCheckPermission("/biz/applet/eCard/updateEcards")
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
    @SaCheckPermission("/biz/applet/eCard/deleteEcards")
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
    @SaCheckPermission("/biz/applet/eCard/getEcardByCardId")
    @GetMapping("/biz/applet/eCard/getEcardByCardId")
    public CommonResult<Ecards> detailByCardId(String cardId) {
        return CommonResult.data(ecardsService.getById(cardId));
    }

    /**
     * 获取Ecard-电子名片详情(通过userId获取)
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取Ecard-电子名片详情(通过userId获取)")
    @SaCheckPermission("/biz/applet/eCard/getEcardByUserId")
    @GetMapping("/biz/applet/eCard/getEcardByUserId")
    public CommonResult<Ecards> detailByUserId(String userId) {
        return CommonResult.data(ecardsService.detailByUserId(userId));
    }

    /**
     * 获取Ecard-电子名片详情(通过openId获取)
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取Ecard-电子名片详情(通过openId获取)")
    @SaCheckPermission("/biz/applet/eCard/getEcardByOpenId")
    @GetMapping("/biz/applet/eCard/getEcardByOpenId")
    public CommonResult<Ecards> detailByOpenId(String openId) {
        return CommonResult.data(ecardsService.detailByOpenId(openId));
    }

    /**
     * 获取电子名片小程序OpenId
     *
     * @author yong.ye
     * @date  2023/11/13 11:22
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取电子名片小程序OpenId")
    @SaCheckPermission("/biz/applet/eCard/getAppletCardOpenIdByCode")
    @GetMapping("/biz/applet/eCard/getAppletCardOpenIdByCode")
    public CommonResult<JSONObject> openIdByCode(String code) {
        return CommonResult.data(ecardsService.openIdByCode(code));
    }
}
