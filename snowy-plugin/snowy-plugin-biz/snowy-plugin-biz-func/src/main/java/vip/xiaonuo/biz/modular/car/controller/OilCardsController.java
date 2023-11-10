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
package vip.xiaonuo.biz.modular.car.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
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
import vip.xiaonuo.biz.modular.car.entity.OilCards;
import vip.xiaonuo.biz.modular.car.param.OilCardsAddParam;
import vip.xiaonuo.biz.modular.car.param.OilCardsEditParam;
import vip.xiaonuo.biz.modular.car.param.OilCardsIdParam;
import vip.xiaonuo.biz.modular.car.param.OilCardsPageParam;
import vip.xiaonuo.biz.modular.car.service.OilCardsService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * CAR-油卡信息控制器
 *
 * @author yong.ye
 * @date  2023/11/08 16:01
 */
@Api(tags = "CAR-油卡信息控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class OilCardsController {

    @Resource
    private OilCardsService oilCardsService;

    /**
     * 获取CAR-油卡信息分页
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取CAR-油卡信息分页")
//    @SaCheckPermission("/biz/car/oilCards/getOilCardsPageList")
    @GetMapping("/biz/car/oilCards/getOilCardsPageList")
    public CommonResult<Page<OilCards>> page(OilCardsPageParam oilCardsPageParam) {
        return CommonResult.data(oilCardsService.page(oilCardsPageParam));
    }

    /**
     * 添加CAR-油卡信息
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加CAR-油卡信息")
    @CommonLog("添加CAR-油卡信息")
//    @SaCheckPermission("/biz/car/oilCards/addOilCards")
    @PostMapping("/biz/car/oilCards/addOilCards")
    public CommonResult<String> add(@RequestBody @Valid OilCardsAddParam oilCardsAddParam) {
        oilCardsService.add(oilCardsAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑CAR-油卡信息
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑CAR-油卡信息")
    @CommonLog("编辑CAR-油卡信息")
//    @SaCheckPermission("/biz/car/oilCards/updateOilCards")
    @PostMapping("/biz/car/oilCards/updateOilCards")
    public CommonResult<String> edit(@RequestBody @Valid OilCardsEditParam oilCardsEditParam) {
        oilCardsService.edit(oilCardsEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除CAR-油卡信息
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除CAR-油卡信息")
    @CommonLog("删除CAR-油卡信息")
//    @SaCheckPermission("/biz/car/oilCards/deleteOilCards")
    @PostMapping("/biz/car/oilCards/deleteOilCards")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<OilCardsIdParam> oilCardsIdParamList) {
        oilCardsService.delete(oilCardsIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取CAR-油卡信息详情
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取CAR-油卡信息详情")
//    @SaCheckPermission("/biz/car/oilCards/getOilCardsDetail")
    @GetMapping("/biz/car/oilCards/getOilCardsDetail")
    public CommonResult<OilCards> detail(@Valid OilCardsIdParam oilCardsIdParam) {
        return CommonResult.data(oilCardsService.detail(oilCardsIdParam));
    }


    /**
     * 获取CAR-油卡信息列表
     *
     * @author yong.ye
     * @date  2023/11/08 16:01
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取CAR-油卡信息列表")
//    @SaCheckPermission("/biz/car/oilCards/getOilCardsList")
    @GetMapping("/biz/car/oilCards/getOilCardsList")
    public CommonResult<List<OilCards>> list() {
        return CommonResult.data(oilCardsService.list());
    }
}
