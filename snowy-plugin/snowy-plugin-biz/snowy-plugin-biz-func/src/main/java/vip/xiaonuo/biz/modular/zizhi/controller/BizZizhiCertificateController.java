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
package vip.xiaonuo.biz.modular.zizhi.controller;

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
import vip.xiaonuo.biz.modular.zizhi.entity.BizZizhiCertificate;
import vip.xiaonuo.biz.modular.zizhi.param.BizZizhiCertificateAddParam;
import vip.xiaonuo.biz.modular.zizhi.param.BizZizhiCertificateEditParam;
import vip.xiaonuo.biz.modular.zizhi.param.BizZizhiCertificateIdParam;
import vip.xiaonuo.biz.modular.zizhi.param.BizZizhiCertificatePageParam;
import vip.xiaonuo.biz.modular.zizhi.service.BizZizhiCertificateService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-资质-企业资质证书表控制器
 *
 * @author yong.ye
 * @date  2023/09/01 09:56
 */
@Api(tags = "业务表-资质-企业资质证书表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizZizhiCertificateController {

    @Resource
    private BizZizhiCertificateService bizZizhiCertificateService;

    /**
     * 获取业务表-资质-企业资质证书表分页
     *
     * @author yong.ye
     * @date  2023/09/01 09:56
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-资质-企业资质证书表分页")
    @GetMapping("/biz/ziZhi/page")
    public CommonResult<Page<BizZizhiCertificate>> page(BizZizhiCertificatePageParam bizZizhiCertificatePageParam) {
        return CommonResult.data(bizZizhiCertificateService.page(bizZizhiCertificatePageParam));
    }

    /**
     * 添加业务表-资质-企业资质证书表
     *
     * @author yong.ye
     * @date  2023/09/01 09:56
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-资质-企业资质证书表")
    @CommonLog("添加业务表-资质-企业资质证书表")
    @PostMapping("/biz/ziZhi/add")
    public CommonResult<String> add(@RequestBody @Valid BizZizhiCertificateAddParam bizZizhiCertificateAddParam) {
        bizZizhiCertificateService.add(bizZizhiCertificateAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-资质-企业资质证书表
     *
     * @author yong.ye
     * @date  2023/09/01 09:56
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-资质-企业资质证书表")
    @CommonLog("编辑业务表-资质-企业资质证书表")
    @PostMapping("/biz/ziZhi/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizZizhiCertificateEditParam bizZizhiCertificateEditParam) {
        bizZizhiCertificateService.edit(bizZizhiCertificateEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-资质-企业资质证书表
     *
     * @author yong.ye
     * @date  2023/09/01 09:56
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-资质-企业资质证书表")
    @CommonLog("删除业务表-资质-企业资质证书表")
    @PostMapping("/biz/ziZhi/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizZizhiCertificateIdParam> bizZizhiCertificateIdParamList) {
        bizZizhiCertificateService.delete(bizZizhiCertificateIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-资质-企业资质证书表详情
     *
     * @author yong.ye
     * @date  2023/09/01 09:56
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-资质-企业资质证书表详情")
    @GetMapping("/biz/ziZhi/detail")
    public CommonResult<BizZizhiCertificate> detail(@Valid BizZizhiCertificateIdParam bizZizhiCertificateIdParam) {
        return CommonResult.data(bizZizhiCertificateService.detail(bizZizhiCertificateIdParam));
    }
}
