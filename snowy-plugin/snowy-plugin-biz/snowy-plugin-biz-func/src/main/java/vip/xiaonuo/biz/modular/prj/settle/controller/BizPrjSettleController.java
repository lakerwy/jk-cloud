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
package vip.xiaonuo.biz.modular.prj.settle.controller;

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
import vip.xiaonuo.biz.modular.prj.settle.param.*;
import vip.xiaonuo.biz.modular.prj.settle.vo.BizPrjSettleVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.settle.entity.BizPrjSettle;
import vip.xiaonuo.biz.modular.prj.settle.service.BizPrjSettleService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-项目计划付款单控制器
 *
 * @author yong.ye
 * @date  2023/09/28 11:36
 */
@Api(tags = "业务表-项目款结算单控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizPrjSettleController {

    @Resource
    private BizPrjSettleService bizPrjSettleService;

    /**
     * 获取业务表-项目款结算单分页
     *
     * @author yong.ye
     * @date  2023/09/28 11:36
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-项目款结算单分页")
//    @SaCheckPermission("/biz/prj/settle/page")
    @GetMapping("/biz/prj/settle/page")
    public CommonResult<Page<BizPrjSettleVo>> page(BizPrjSettlePageParam bizPrjSettlePageParam) {
        return CommonResult.data(bizPrjSettleService.page(bizPrjSettlePageParam));
    }

    /**
     * 添加业务表-项目款结算单
     *
     * @author yong.ye
     * @date  2023/09/28 11:36
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-项目款结算单")
    @CommonLog("添加业务表-项目款结算单")
//    @SaCheckPermission("/biz/prj/settle/add")
    @PostMapping("/biz/prj/settle/add")
    public CommonResult<String> add(@RequestBody @Valid PrjSettleParam prjSettleParam) {
        bizPrjSettleService.add(prjSettleParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-项目款结算单
     *
     * @author yong.ye
     * @date  2023/09/28 11:36
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-项目款结算单")
    @CommonLog("编辑业务表-项目款结算单")
//    @SaCheckPermission("/biz/prj/settle/edit")
    @PostMapping("/biz/prj/settle/edit")
    public CommonResult<String> edit(@RequestBody @Valid PrjSettleParam prjSettleParam) {
        bizPrjSettleService.edit(prjSettleParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-项目款结算单
     *
     * @author yong.ye
     * @date  2023/09/28 11:36
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-项目款结算单")
    @CommonLog("删除业务表-项目款结算单")
//    @SaCheckPermission("/biz/prj/settle/delete")
    @PostMapping("/biz/prj/settle/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizPrjSettleIdParam> bizPrjSettleIdParamList) {
        bizPrjSettleService.delete(bizPrjSettleIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-项目款结算单
     *
     * @author yong.ye
     * @date  2023/09/28 11:36
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-项目款结算单详情")
//    @SaCheckPermission("/biz/prj/settle/detail")
    @GetMapping("/biz/prj/settle/detail")
    public CommonResult<BizPrjSettle> detail(@Valid BizPrjSettleIdParam bizPrjSettleIdParam) {
        return CommonResult.data(bizPrjSettleService.detail(bizPrjSettleIdParam));
    }
}
