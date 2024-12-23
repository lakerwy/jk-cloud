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
package vip.xiaonuo.biz.modular.bid.controller;

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
import vip.xiaonuo.biz.modular.bid.entity.BidDecision;
import vip.xiaonuo.biz.modular.bid.param.BidDecisionAddParam;
import vip.xiaonuo.biz.modular.bid.param.BidDecisionEditParam;
import vip.xiaonuo.biz.modular.bid.param.BidDecisionIdParam;
import vip.xiaonuo.biz.modular.bid.param.BidDecisionPageParam;
import vip.xiaonuo.biz.modular.bid.service.BidDecisionService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 投标决策【调整为合约规划】 - 申请流程控制器
 *
 * @author yong.ye
 * @date  2024/06/21 10:30
 */
@Api(tags = "投标决策【调整为合约规划】 - 申请流程控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BidDecisionController {

    @Resource
    private BidDecisionService bidDecisionService;

    /**
     * 获取投标决策【调整为合约规划】 - 申请流程分页
     *
     * @author yong.ye
     * @date  2024/06/21 10:30
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取投标决策【调整为合约规划】 - 申请流程分页")
//    @SaCheckPermission("/biz/bid/decision/page")
    @GetMapping("/biz/bid/decision/page")
    public CommonResult<Page<BidDecision>> page(BidDecisionPageParam bidDecisionPageParam) {
        return CommonResult.data(bidDecisionService.page(bidDecisionPageParam));
    }

    /**
     * 添加投标决策【调整为合约规划】 - 申请流程
     *
     * @author yong.ye
     * @date  2024/06/21 10:30
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加投标决策【调整为合约规划】 - 申请流程")
    @CommonLog("添加投标决策【调整为合约规划】 - 申请流程")
//    @SaCheckPermission("/biz/bid/decision/add")
    @PostMapping("/biz/bid/decision/add")
    public CommonResult<String> add(@RequestBody @Valid BidDecisionAddParam bidDecisionAddParam) {
        bidDecisionService.add(bidDecisionAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑投标决策 - 申请流程
     *
     * @author yong.ye
     * @date  2024/06/21 10:30
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑投标决策【调整为合约规划】 - 申请流程")
    @CommonLog("编辑投标决策【调整为合约规划】 - 申请流程")
//    @SaCheckPermission("/biz/bid/decision/edit")
    @PostMapping("/biz/bid/decision/edit")
    public CommonResult<String> edit(@RequestBody @Valid BidDecisionEditParam bidDecisionEditParam) {
        bidDecisionService.edit(bidDecisionEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除投标决策 - 申请流程
     *
     * @author yong.ye
     * @date  2024/06/21 10:30
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除投标决策【调整为合约规划】 - 申请流程")
    @CommonLog("删除投标决策【调整为合约规划】 - 申请流程")
//    @SaCheckPermission("/biz/bid/decision/delete")
    @PostMapping("/biz/bid/decision/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BidDecisionIdParam> bidDecisionIdParamList) {
        bidDecisionService.delete(bidDecisionIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取投标决策【调整为合约规划】 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/06/21 10:30
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取投标决策【调整为合约规划】 - 申请流程详情")
//    @SaCheckPermission("/biz/bid/decision/detail")
    @GetMapping("/biz/bid/decision/detail")
    public CommonResult<BidDecision> detail(@Valid BidDecisionIdParam bidDecisionIdParam) {
        return CommonResult.data(bidDecisionService.detail(bidDecisionIdParam));
    }
}
