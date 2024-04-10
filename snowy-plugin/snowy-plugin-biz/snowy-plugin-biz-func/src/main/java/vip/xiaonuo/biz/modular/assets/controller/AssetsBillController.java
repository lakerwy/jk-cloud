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
package vip.xiaonuo.biz.modular.assets.controller;

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
import vip.xiaonuo.biz.modular.assets.vo.AssetsBillVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.assets.entity.AssetsBill;
import vip.xiaonuo.biz.modular.assets.param.AssetsBillAddParam;
import vip.xiaonuo.biz.modular.assets.param.AssetsBillEditParam;
import vip.xiaonuo.biz.modular.assets.param.AssetsBillIdParam;
import vip.xiaonuo.biz.modular.assets.param.AssetsBillPageParam;
import vip.xiaonuo.biz.modular.assets.service.AssetsBillService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 固资申购 - 申请单 - 申购详情列表控制器
 *
 * @author yong.ye
 * @date  2024/04/08 16:18
 */
@Api(tags = "固资申购 - 申请单 - 申购详情列表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class AssetsBillController {

    @Resource
    private AssetsBillService assetsBillService;

    /**
     * 获取固资申购 - 申请单 - 申购详情列表分页
     *
     * @author yong.ye
     * @date  2024/04/08 16:18
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取固资申购 - 申请单 - 申购详情列表分页")
//    @SaCheckPermission("/biz/assets/bill/page")
    @GetMapping("/biz/assets/bill/page")
    public CommonResult<Page<AssetsBillVo>> page(AssetsBillPageParam assetsBillPageParam) {
        return CommonResult.data(assetsBillService.page(assetsBillPageParam));
    }

    /**
     * 添加固资申购 - 申请单 - 申购详情列表
     *
     * @author yong.ye
     * @date  2024/04/08 16:18
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加固资申购 - 申请单 - 申购详情列表")
    @CommonLog("添加固资申购 - 申请单 - 申购详情列表")
//    @SaCheckPermission("/biz/assets/bill/add")
    @PostMapping("/biz/assets/bill/add")
    public CommonResult<String> add(@RequestBody @Valid AssetsBillAddParam assetsBillAddParam) {
        assetsBillService.add(assetsBillAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑固资申购 - 申请单 - 申购详情列表
     *
     * @author yong.ye
     * @date  2024/04/08 16:18
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑固资申购 - 申请单 - 申购详情列表")
    @CommonLog("编辑固资申购 - 申请单 - 申购详情列表")
//    @SaCheckPermission("/biz/assets/bill/edit")
    @PostMapping("/biz/assets/bill/edit")
    public CommonResult<String> edit(@RequestBody @Valid AssetsBillEditParam assetsBillEditParam) {
        assetsBillService.edit(assetsBillEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除固资申购 - 申请单 - 申购详情列表
     *
     * @author yong.ye
     * @date  2024/04/08 16:18
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除固资申购 - 申请单 - 申购详情列表")
    @CommonLog("删除固资申购 - 申请单 - 申购详情列表")
//    @SaCheckPermission("/biz/assets/bill/delete")
    @PostMapping("/biz/assets/bill/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<AssetsBillIdParam> assetsBillIdParamList) {
        assetsBillService.delete(assetsBillIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取固资申购 - 申请单 - 申购详情列表详情
     *
     * @author yong.ye
     * @date  2024/04/08 16:18
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取固资申购 - 申请单 - 申购详情列表详情")
//    @SaCheckPermission("/biz/assets/bill/detail")
    @GetMapping("/biz/assets/bill/detail")
    public CommonResult<AssetsBill> detail(@Valid AssetsBillIdParam assetsBillIdParam) {
        return CommonResult.data(assetsBillService.detail(assetsBillIdParam));
    }
}
