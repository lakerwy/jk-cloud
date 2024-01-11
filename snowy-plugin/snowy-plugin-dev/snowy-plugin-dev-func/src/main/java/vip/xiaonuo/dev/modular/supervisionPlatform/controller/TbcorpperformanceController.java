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
package vip.xiaonuo.dev.modular.supervisionPlatform.controller;

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
import vip.xiaonuo.dev.modular.supervisionPlatform.entity.Tbcorpperformance;
import vip.xiaonuo.dev.modular.supervisionPlatform.entity.Tbpersonperformance;
import vip.xiaonuo.dev.modular.supervisionPlatform.entity.Tbprojectsysrelation;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.*;
import vip.xiaonuo.dev.modular.supervisionPlatform.service.TbcorpperformanceService;
import vip.xiaonuo.dev.modular.supervisionPlatform.service.TbpersonperformanceService;
import vip.xiaonuo.dev.modular.supervisionPlatform.service.TbprojectsysrelationService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 市场监管公共服务平台-企业业绩技术指标信息表控制器
 *
 * @author ljy
 * @date  2024/01/11 10:57
 */
@Api(tags = "市场监管公共服务平台-企业业绩技术指标信息表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class TbcorpperformanceController {

    @Resource
    private TbcorpperformanceService tbcorpperformanceService;
    @Resource
    private TbpersonperformanceService tbpersonperformanceService;
    @Resource
    private TbprojectsysrelationService tbprojectsysrelationService;

    /**
     * 获取市场监管公共服务平台-企业业绩技术指标信息表分页
     *
     * @author ljy
     * @date  2024/01/11 10:57
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取市场监管公共服务平台-企业业绩技术指标信息表分页")
    //@SaCheckPermission("/biz/Tbcorpperformance/page")
    @GetMapping("/biz/Tbcorpperformance/page")
    public CommonResult<Page<Tbcorpperformance>> page(TbcorpperformancePageParam tbcorpperformancePageParam) {
        return CommonResult.data(tbcorpperformanceService.page(tbcorpperformancePageParam));
    }

    /**
     * 添加市场监管公共服务平台-企业业绩技术指标信息表
     *
     * @author ljy
     * @date  2024/01/11 10:57
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加市场监管公共服务平台-企业业绩技术指标信息表")
    @CommonLog("添加市场监管公共服务平台-企业业绩技术指标信息表")
    //@SaCheckPermission("/biz/Tbcorpperformance/add")
    @PostMapping("/biz/Tbcorpperformance/add")
    public CommonResult<String> add(@RequestBody @Valid TbcorpperformanceAddParam tbcorpperformanceAddParam) {
        tbcorpperformanceService.add(tbcorpperformanceAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑市场监管公共服务平台-企业业绩技术指标信息表
     *
     * @author ljy
     * @date  2024/01/11 10:57
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑市场监管公共服务平台-企业业绩技术指标信息表")
    @CommonLog("编辑市场监管公共服务平台-企业业绩技术指标信息表")
    //@SaCheckPermission("/biz/Tbcorpperformance/edit")
    @PostMapping("/biz/Tbcorpperformance/edit")
    public CommonResult<String> edit(@RequestBody @Valid TbcorpperformanceEditParam tbcorpperformanceEditParam) {
        tbcorpperformanceService.edit(tbcorpperformanceEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除市场监管公共服务平台-企业业绩技术指标信息表
     *
     * @author ljy
     * @date  2024/01/11 10:57
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除市场监管公共服务平台-企业业绩技术指标信息表")
    @CommonLog("删除市场监管公共服务平台-企业业绩技术指标信息表")
    //@SaCheckPermission("/biz/Tbcorpperformance/delete")
    @PostMapping("/biz/Tbcorpperformance/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<TbcorpperformanceIdParam> tbcorpperformanceIdParamList) {
        tbcorpperformanceService.delete(tbcorpperformanceIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取市场监管公共服务平台-企业业绩技术指标信息表详情
     *
     * @author ljy
     * @date  2024/01/11 10:57
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取市场监管公共服务平台-企业业绩技术指标信息表详情")
    //@SaCheckPermission("/biz/Tbcorpperformance/detail")
    @GetMapping("/biz/Tbcorpperformance/detail")
    public CommonResult<Tbcorpperformance> detail(@Valid TbcorpperformanceIdParam tbcorpperformanceIdParam) {
        return CommonResult.data(tbcorpperformanceService.detail(tbcorpperformanceIdParam));
    }



    /***********
     * *********
     * 市场监管公共服务平台-个人业绩信息表控制器
     * */
    /**
     * 获取市场监管公共服务平台-个人业绩信息表分页
     *
     * @author ljy
     * @date  2024/01/11 10:58
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取市场监管公共服务平台-个人业绩信息表分页")
    @GetMapping("/biz/Tbpersonperformance/personPage")
    public CommonResult<Page<Tbpersonperformance>> personPage(TbpersonperformancePageParam tbpersonperformancePageParam) {
        return CommonResult.data(tbpersonperformanceService.page(tbpersonperformancePageParam));
    }

    /**
     * 添加市场监管公共服务平台-个人业绩信息表
     *
     * @author ljy
     * @date  2024/01/11 10:58
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("添加市场监管公共服务平台-个人业绩信息表")
    @CommonLog("添加市场监管公共服务平台-个人业绩信息表")
    @PostMapping("/biz/Tbpersonperformance/personAdd")
    public CommonResult<String> personAdd(@RequestBody @Valid TbpersonperformanceAddParam tbpersonperformanceAddParam) {
        tbpersonperformanceService.add(tbpersonperformanceAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑市场监管公共服务平台-个人业绩信息表
     *
     * @author ljy
     * @date  2024/01/11 10:58
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("编辑市场监管公共服务平台-个人业绩信息表")
    @CommonLog("编辑市场监管公共服务平台-个人业绩信息表")
    @PostMapping("/biz/Tbpersonperformance/personEdit")
    public CommonResult<String> personEdit(@RequestBody @Valid TbpersonperformanceEditParam tbpersonperformanceEditParam) {
        tbpersonperformanceService.edit(tbpersonperformanceEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除市场监管公共服务平台-个人业绩信息表
     *
     * @author ljy
     * @date  2024/01/11 10:58
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("删除市场监管公共服务平台-个人业绩信息表")
    @CommonLog("删除市场监管公共服务平台-个人业绩信息表")
    @PostMapping("/biz/Tbpersonperformance/personDelete")
    public CommonResult<String> personDelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<TbpersonperformanceIdParam> tbpersonperformanceIdParamList) {
        tbpersonperformanceService.delete(tbpersonperformanceIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取市场监管公共服务平台-个人业绩信息表详情
     *
     * @author ljy
     * @date  2024/01/11 10:58
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("获取市场监管公共服务平台-个人业绩信息表详情")
    @GetMapping("/biz/Tbpersonperformance/personDetail")
    public CommonResult<Tbpersonperformance> personDetail(@Valid TbpersonperformanceIdParam tbpersonperformanceIdParam) {
        return CommonResult.data(tbpersonperformanceService.detail(tbpersonperformanceIdParam));
    }


    /*********
     * *******
     * 市场监管公共服务平台-与其他系统项目关联信息表控制器
     * */

    /**
     * 获取市场监管公共服务平台-与其他系统项目关联信息表分页
     *
     * @author ljy
     * @date  2024/01/11 10:59
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("获取市场监管公共服务平台-与其他系统项目关联信息表分页")
    @GetMapping("/biz/Tbprojectsysrelation/relationPage")
    public CommonResult<Page<Tbprojectsysrelation>> relationPage(TbprojectsysrelationPageParam tbprojectsysrelationPageParam) {
        return CommonResult.data(tbprojectsysrelationService.page(tbprojectsysrelationPageParam));
    }

    /**
     * 添加市场监管公共服务平台-与其他系统项目关联信息表
     *
     * @author ljy
     * @date  2024/01/11 10:59
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("添加市场监管公共服务平台-与其他系统项目关联信息表")
    @CommonLog("添加市场监管公共服务平台-与其他系统项目关联信息表")
    @PostMapping("/biz/Tbprojectsysrelation/relationAdd")
    public CommonResult<String> relationAdd(@RequestBody @Valid TbprojectsysrelationAddParam tbprojectsysrelationAddParam) {
        tbprojectsysrelationService.add(tbprojectsysrelationAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑市场监管公共服务平台-与其他系统项目关联信息表
     *
     * @author ljy
     * @date  2024/01/11 10:59
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("编辑市场监管公共服务平台-与其他系统项目关联信息表")
    @CommonLog("编辑市场监管公共服务平台-与其他系统项目关联信息表")
    @PostMapping("/biz/Tbprojectsysrelation/relationEdit")
    public CommonResult<String> relationEdit(@RequestBody @Valid TbprojectsysrelationEditParam tbprojectsysrelationEditParam) {
        tbprojectsysrelationService.edit(tbprojectsysrelationEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除市场监管公共服务平台-与其他系统项目关联信息表
     *
     * @author ljy
     * @date  2024/01/11 10:59
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("删除市场监管公共服务平台-与其他系统项目关联信息表")
    @CommonLog("删除市场监管公共服务平台-与其他系统项目关联信息表")
    @PostMapping("/biz/Tbprojectsysrelation/relationDelete")
    public CommonResult<String> relationDelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<TbprojectsysrelationIdParam> tbprojectsysrelationIdParamList) {
        tbprojectsysrelationService.delete(tbprojectsysrelationIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取市场监管公共服务平台-与其他系统项目关联信息表详情
     *
     * @author ljy
     * @date  2024/01/11 10:59
     */
    @ApiOperationSupport(order = 15)
    @ApiOperation("获取市场监管公共服务平台-与其他系统项目关联信息表详情")
    @GetMapping("/biz/Tbprojectsysrelation/relationDetail")
    public CommonResult<Tbprojectsysrelation> relationDetail(@Valid TbprojectsysrelationIdParam tbprojectsysrelationIdParam) {
        return CommonResult.data(tbprojectsysrelationService.detail(tbprojectsysrelationIdParam));
    }
}
