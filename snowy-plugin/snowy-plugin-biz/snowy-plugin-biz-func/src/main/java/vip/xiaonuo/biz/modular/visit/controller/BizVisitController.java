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
package vip.xiaonuo.biz.modular.visit.controller;


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
import vip.xiaonuo.biz.modular.visit.vo.BizVisitVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.visit.entity.BizVisit;
import vip.xiaonuo.biz.modular.visit.param.BizVisitAddParam;
import vip.xiaonuo.biz.modular.visit.param.BizVisitEditParam;
import vip.xiaonuo.biz.modular.visit.param.BizVisitIdParam;
import vip.xiaonuo.biz.modular.visit.param.BizVisitPageParam;
import vip.xiaonuo.biz.modular.visit.service.BizVisitService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 外访表控制器
 *
 * @author kaiminglu
 * @date  2023/10/12 10:35
 */
@Api(tags = "外访表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizVisitController {

    @Resource
    private BizVisitService bizVisitService;

    /**
     * 获取外访表分页
     *
     * @author kaiminglu
     * @date  2023/10/12 10:35
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取外访表分页")
    @GetMapping("/biz/visit/page")
    public CommonResult<Page<BizVisitVo>> page(BizVisitPageParam bizVisitPageParam) {
        return CommonResult.data(bizVisitService.page(bizVisitPageParam));
    }

    /**
     * 添加外访表
     *
     * @author kaiminglu
     * @date  2023/10/12 10:35
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加外访表")
    @CommonLog("添加外访表")
    @PostMapping("/biz/visit/add")
    public CommonResult<String> add(@RequestBody @Valid BizVisitAddParam bizVisitAddParam) {
        bizVisitService.add(bizVisitAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑外访表
     *
     * @author kaiminglu
     * @date  2023/10/12 10:35
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑外访表")
    @CommonLog("编辑外访表")
    @PostMapping("/biz/visit/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizVisitEditParam bizVisitEditParam) {
        bizVisitService.edit(bizVisitEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除外访表
     *
     * @author kaiminglu
     * @date  2023/10/12 10:35
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除外访表")
    @CommonLog("删除外访表")
    @PostMapping("/biz/visit/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizVisitIdParam> bizVisitIdParamList) {
        bizVisitService.delete(bizVisitIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取外访表详情
     *
     * @author kaiminglu
     * @date  2023/10/12 10:35
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取外访表详情")
    @GetMapping("/biz/visit/detail")
    public CommonResult<BizVisit> detail(@Valid BizVisitIdParam bizVisitIdParam) {
        return CommonResult.data(bizVisitService.detail(bizVisitIdParam));
    }
}
