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
package vip.xiaonuo.biz.modular.pis.controller;

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
import vip.xiaonuo.biz.modular.pis.entity.PisAllotment;
import vip.xiaonuo.biz.modular.pis.param.PisAllotmentAddParam;
import vip.xiaonuo.biz.modular.pis.param.PisAllotmentEditParam;
import vip.xiaonuo.biz.modular.pis.param.PisAllotmentIdParam;
import vip.xiaonuo.biz.modular.pis.param.PisAllotmentPageParam;
import vip.xiaonuo.biz.modular.pis.service.PisAllotmentService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 生产院及子公司项目拨付款 - 申请流程控制器
 *
 * @author yong.ye
 * @date  2024/07/03 15:29
 */
@Api(tags = "生产院及子公司项目拨付款 - 申请流程控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PisAllotmentController {

    @Resource
    private PisAllotmentService pisAllotmentService;

    /**
     * 获取生产院及子公司项目拨付款 - 申请流程分页
     *
     * @author yong.ye
     * @date  2024/07/03 15:29
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取生产院及子公司项目拨付款 - 申请流程分页")
//    @SaCheckPermission("/biz/pis/page")
    @GetMapping("/biz/pis/page")
    public CommonResult<Page<PisAllotment>> page(PisAllotmentPageParam pisAllotmentPageParam) {
        return CommonResult.data(pisAllotmentService.page(pisAllotmentPageParam));
    }

    /**
     * 添加生产院及子公司项目拨付款 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/03 15:29
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加生产院及子公司项目拨付款 - 申请流程")
    @CommonLog("添加生产院及子公司项目拨付款 - 申请流程")
//    @SaCheckPermission("/biz/pis/add")
    @PostMapping("/biz/pis/add")
    public CommonResult<String> add(@RequestBody @Valid PisAllotmentAddParam pisAllotmentAddParam) {
        pisAllotmentService.add(pisAllotmentAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑生产院及子公司项目拨付款 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/03 15:29
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑生产院及子公司项目拨付款 - 申请流程")
    @CommonLog("编辑生产院及子公司项目拨付款 - 申请流程")
//    @SaCheckPermission("/biz/pis/edit")
    @PostMapping("/biz/pis/edit")
    public CommonResult<String> edit(@RequestBody @Valid PisAllotmentEditParam pisAllotmentEditParam) {
        pisAllotmentService.edit(pisAllotmentEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除生产院及子公司项目拨付款 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/03 15:29
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除生产院及子公司项目拨付款 - 申请流程")
    @CommonLog("删除生产院及子公司项目拨付款 - 申请流程")
//    @SaCheckPermission("/biz/pis/delete")
    @PostMapping("/biz/pis/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PisAllotmentIdParam> pisAllotmentIdParamList) {
        pisAllotmentService.delete(pisAllotmentIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取生产院及子公司项目拨付款 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/07/03 15:29
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取生产院及子公司项目拨付款 - 申请流程详情")
//    @SaCheckPermission("/biz/pis/detail")
    @GetMapping("/biz/pis/detail")
    public CommonResult<PisAllotment> detail(@Valid PisAllotmentIdParam pisAllotmentIdParam) {
        return CommonResult.data(pisAllotmentService.detail(pisAllotmentIdParam));
    }

    /**
     * 根据总包合同Id,获取前一次审批通过的拨付款单信息
     *
     * @author yong.ye
     * @date  2024/07/03 15:29
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("根据总包合同Id,获取前一次审批通过的拨付款单信息")
//    @SaCheckPermission("/biz/pis/getByHtId")
    @GetMapping("/biz/pis/getByHtId")
    public CommonResult<PisAllotment> getByHtId(String htId) {
        return CommonResult.data(pisAllotmentService.getByHtId(htId));
    }
}
