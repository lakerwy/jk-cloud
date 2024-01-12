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

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.dev.modular.supervisionPlatform.entity.TBCorpperFormance;
import vip.xiaonuo.dev.modular.supervisionPlatform.entity.TBPersonPerFormance;
import vip.xiaonuo.dev.modular.supervisionPlatform.entity.TBProjectSysRelation;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.*;
import vip.xiaonuo.dev.modular.supervisionPlatform.service.TBCorpperFormanceService;
import vip.xiaonuo.dev.modular.supervisionPlatform.service.TBPersonPerFormanceService;
import vip.xiaonuo.dev.modular.supervisionPlatform.service.TBProjectSysRelationService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;

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
public class TBCorpPerformanceController {

    @Resource
    private TBCorpperFormanceService tbcorpperformanceService;
    @Resource
    private TBPersonPerFormanceService tbpersonperformanceService;
    @Resource
    private TBProjectSysRelationService tbprojectsysrelationService;

    /**
     * 企业业绩技术指标信息表分页
     *
     * @author ljy
     * @date  2024/01/11 10:57
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取市场监管公共服务平台-企业业绩技术指标信息表分页")
    @GetMapping("/rural/Tbcorpperformance/page")
    public CommonResult<Page<TBCorpperFormance>> page(TBCorpperFormancePageParam tbcorpperformancePageParam) {
        return CommonResult.data(tbcorpperformanceService.page(tbcorpperformancePageParam));
    }

    /**
     * 企业业绩技术指标信息表
     *
     * @author ljy
     * @date  2024/01/11 10:57
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加企业业绩技术指标信息表")
    @CommonLog("添加企业业绩技术指标信息表")
    @PostMapping("/rural/Tbcorpperformance/add")
    public CommonResult<String> add(@RequestBody @Valid TBCorpperFormanceAddParam tbcorpperformanceAddParam) {
        tbcorpperformanceService.add(tbcorpperformanceAddParam);
        return CommonResult.ok();
    }

    /**
     * 批量导入企业业绩技术指标信息数据
     *
     * @author ljy
     * @date  2024/01/11 10:57
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("批量添加企业业绩技术指标信息数据")
    @CommonLog("批量添加企业业绩技术指标信息数据")
    @PostMapping("/rural/Tbcorpperformance/addBatch")
    public CommonResult<String> addBatch(@RequestBody @Valid List<TBCorpperFormanceAddParam> tbcorpperformanceAddParamList) {
        tbcorpperformanceService.addBatch(tbcorpperformanceAddParamList);
        return CommonResult.ok();
    }

    /**
     * 编辑市场监管公共服务平台-企业业绩技术指标信息表
     *
     * @author ljy
     * @date  2024/01/11 10:57
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑企业业绩技术指标信息表")
    @CommonLog("编辑企业业绩技术指标信息表")
    @PostMapping("/rural/Tbcorpperformance/edit")
    public CommonResult<String> edit(@RequestBody @Valid TBCorpperFormanceEditParam tbcorpperformanceEditParam) {
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
    @ApiOperation("删除企业业绩技术指标信息表")
    @CommonLog("删除企业业绩技术指标信息表")
    @PostMapping("/rural/Tbcorpperformance/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<TBCorpperFormanceIdParam> TBCorpperFormanceIdParamList) {
        tbcorpperformanceService.delete(TBCorpperFormanceIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取市场监管公共服务平台-企业业绩技术指标信息表详情
     *
     * @author ljy
     * @date  2024/01/11 10:57
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取企业业绩技术指标信息表详情")
    @GetMapping("/rural/Tbcorpperformance/detail")
    public CommonResult<TBCorpperFormance> detail(@Valid TBCorpperFormanceIdParam tbcorpperformanceIdParam) {
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
    @ApiOperation("获取个人业绩信息表分页")
    @GetMapping("/rural/Tbpersonperformance/personPage")
    public CommonResult<Page<TBPersonPerFormance>> personPage(TBPersonPerFormancePageParam tbpersonperformancePageParam) {
        return CommonResult.data(tbpersonperformanceService.page(tbpersonperformancePageParam));
    }

    /**
     * 添加市场监管公共服务平台-个人业绩信息表
     *
     * @author ljy
     * @date  2024/01/11 10:58
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("添加个人业绩信息表")
    @CommonLog("添加个人业绩信息表")
    @PostMapping("/rural/Tbpersonperformance/personAdd")
    public CommonResult<String> personAdd(@RequestBody @Valid TBPersonPerFormanceAddParam tbpersonperformanceAddParam) {
        tbpersonperformanceService.add(tbpersonperformanceAddParam);
        return CommonResult.ok();
    }

    /**
     * 添加市场监管公共服务平台-个人业绩信息表
     *
     * @author ljy
     * @date  2024/01/11 10:58
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("批量添加个人业绩信息表")
    @CommonLog("批量添加个人业绩信息表")
    @PostMapping("/rural/Tbpersonperformance/personAddBatch")
    public CommonResult<String> personAddBatch(@RequestBody @Valid List<TBPersonPerFormanceAddParam> tbPersonPerFormanceAddParamList) {
        tbpersonperformanceService.personAddBatch(tbPersonPerFormanceAddParamList);
        return CommonResult.ok();
    }

    /**
     * 编辑市场监管公共服务平台-个人业绩信息表
     *
     * @author ljy
     * @date  2024/01/11 10:58
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("编辑个人业绩信息表")
    @CommonLog("编辑个人业绩信息表")
    @PostMapping("/rural/Tbpersonperformance/personEdit")
    public CommonResult<String> personEdit(@RequestBody @Valid TBPersonPerFormanceEditParam tbpersonperformanceEditParam) {
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
    @ApiOperation("删除个人业绩信息表")
    @CommonLog("删除个人业绩信息表")
    @PostMapping("/rural/Tbpersonperformance/personDelete")
    public CommonResult<String> personDelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<TBPersonPerFormanceIdParam> TBPersonPerFormanceIdParamList) {
        tbpersonperformanceService.delete(TBPersonPerFormanceIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取市场监管公共服务平台-个人业绩信息表详情
     *
     * @author ljy
     * @date  2024/01/11 10:58
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("获取个人业绩信息表详情")
    @GetMapping("/rural/Tbpersonperformance/personDetail")
    public CommonResult<TBPersonPerFormance> personDetail(@Valid TBPersonPerFormanceIdParam tbpersonperformanceIdParam) {
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
    @ApiOperation("获取与其他系统项目关联信息表分页")
    @GetMapping("/rural/Tbprojectsysrelation/relationPage")
    public CommonResult<Page<TBProjectSysRelation>> relationPage(TBProjectSysRelationPageParam tbprojectsysrelationPageParam) {
        return CommonResult.data(tbprojectsysrelationService.page(tbprojectsysrelationPageParam));
    }

    /**
     * 添加市场监管公共服务平台-与其他系统项目关联信息表
     *
     * @author ljy
     * @date  2024/01/11 10:59
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("添加与其他系统项目关联信息表")
    @CommonLog("添加与其他系统项目关联信息表")
    @PostMapping("/rural/Tbprojectsysrelation/relationAdd")
    public CommonResult<String> relationAdd(@RequestBody @Valid TBProjectSysRelationAddParam tbprojectsysrelationAddParam) {
        tbprojectsysrelationService.add(tbprojectsysrelationAddParam);
        return CommonResult.ok();
    }

    /**
     * 添加市场监管公共服务平台-与其他系统项目关联信息表
     *
     * @author ljy
     * @date  2024/01/11 10:59
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("批量添加与其他系统项目关联信息表")
    @CommonLog("批量添加与其他系统项目关联信息表")
    @PostMapping("/rural/Tbprojectsysrelation/relationAddBatch")
    public CommonResult<String> relationAddBatch(@RequestBody @Valid List<TBProjectSysRelationAddParam> tbProjectSysRelationAddParamList) {
        tbprojectsysrelationService.relationAddBatch(tbProjectSysRelationAddParamList);
        return CommonResult.ok();
    }

    /**
     * 编辑市场监管公共服务平台-与其他系统项目关联信息表
     *
     * @author ljy
     * @date  2024/01/11 10:59
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("编辑与其他系统项目关联信息表")
    @CommonLog("编辑与其他系统项目关联信息表")
    @PostMapping("/rural/Tbprojectsysrelation/relationEdit")
    public CommonResult<String> relationEdit(@RequestBody @Valid TBProjectSysRelationEditParam tbprojectsysrelationEditParam) {
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
    @ApiOperation("删除与其他系统项目关联信息表")
    @CommonLog("删除与其他系统项目关联信息表")
    @PostMapping("/rural/Tbprojectsysrelation/relationDelete")
    public CommonResult<String> relationDelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<TBProjectSysRelationIdParam> TBProjectSysRelationIdParamList) {
        tbprojectsysrelationService.delete(TBProjectSysRelationIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取市场监管公共服务平台-与其他系统项目关联信息表详情
     *
     * @author ljy
     * @date  2024/01/11 10:59
     */
    @ApiOperationSupport(order = 15)
    @ApiOperation("获取与其他系统项目关联信息表详情")
    @GetMapping("/rural/Tbprojectsysrelation/relationDetail")
    public CommonResult<TBProjectSysRelation> relationDetail(@Valid TBProjectSysRelationIdParam tbprojectsysrelationIdParam) {
        return CommonResult.data(tbprojectsysrelationService.detail(tbprojectsysrelationIdParam));
    }

    /**
     * 下载用户导入模板
     *
     * @author ljy
     * @date 2024/01/12 10:30
     */
    @ApiOperationSupport(order = 16)
    @ApiOperation("下载建筑监管市场导入模板")
    @CommonLog("下载建筑监管市场导入模板")
    @GetMapping(value = "/rural/Tbcorpperformance/downloadImportUserTemplate", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public CommonResult<String> downloadImportUserTemplate(HttpServletResponse response,Integer excelType) throws IOException {
        tbcorpperformanceService.downloadImportUserTemplate(response,excelType);
        return CommonResult.ok();
    }

    /**
     * 建筑监管市场数据导入
     *
     * @author ljy
     * @date 2024/01/12 16:50
     */
    @ApiOperationSupport(order = 17)
    @ApiOperation("建筑监管市场数据导入")
    @CommonLog("建筑监管市场数据导入")
    @PostMapping("/rural/Tbcorpperformance/importRuralData")
    public CommonResult<String> importRuralData(@RequestPart("file") @ApiParam(value="文件", required = true) MultipartFile file,Integer excelType) {
        tbcorpperformanceService.importRuralData(file,excelType);
        return CommonResult.ok();
    }
}
