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
package vip.xiaonuo.biz.modular.crm.fpinformations.controller;

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
import vip.xiaonuo.biz.modular.crm.fpinformations.entity.Fpinformations;
import vip.xiaonuo.biz.modular.crm.fpinformations.param.FpinformationsAddParam;
import vip.xiaonuo.biz.modular.crm.fpinformations.param.FpinformationsEditParam;
import vip.xiaonuo.biz.modular.crm.fpinformations.param.FpinformationsIdParam;
import vip.xiaonuo.biz.modular.crm.fpinformations.param.FpinformationsPageParam;
import vip.xiaonuo.biz.modular.crm.fpinformations.service.FpinformationsService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-CRM-合作伙伴人员信息控制器
 *
 * @author yong.ye
 * @date  2023/06/30 16:55
 */
@Api(tags = "业务表-CRM-合作伙伴人员信息控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class FpinformationsController {

    @Resource
    private FpinformationsService fpinformationsService;

    /**
     * 获取业务表-CRM-合作伙伴人员信息分页
     *
     * @author yong.ye
     * @date  2023/06/30 16:55
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-CRM-合作伙伴人员信息分页  【api/crm/partner/GetFPInformationsPage】")
    @GetMapping("/biz/crm/fpinformations/page")
    public CommonResult<Page<Fpinformations>> page(FpinformationsPageParam fpinformationsPageParam) {
        return CommonResult.data(fpinformationsService.page(fpinformationsPageParam));
    }

    /**
     * 添加业务表-CRM-合作伙伴人员信息
     *
     * @author yong.ye
     * @date  2023/06/30 16:55
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-CRM-合作伙伴人员信息  【api/crm/partner/AddFPInformations】")
    @CommonLog("添加业务表-CRM-合作伙伴人员信息")
    @PostMapping("/biz/crm/fpinformations/add")
    public CommonResult<String> add(@RequestBody @Valid FpinformationsAddParam fpinformationsAddParam) {
        fpinformationsService.add(fpinformationsAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-CRM-合作伙伴人员信息
     *
     * @author yong.ye
     * @date  2023/06/30 16:55
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-CRM-合作伙伴人员信息  【api/crm/partner/UpdateFPInformations】")
    @CommonLog("编辑业务表-CRM-合作伙伴人员信息")
    @PostMapping("/biz/crm/fpinformations/edit")
    public CommonResult<String> edit(@RequestBody @Valid FpinformationsEditParam fpinformationsEditParam) {
        fpinformationsService.edit(fpinformationsEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-CRM-合作伙伴人员信息
     *
     * @author yong.ye
     * @date  2023/06/30 16:55
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-CRM-合作伙伴人员信息  【api/crm/partner/DeleteFPInformations】")
    @CommonLog("删除业务表-CRM-合作伙伴人员信息")
    @PostMapping("/biz/crm/fpinformations/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<FpinformationsIdParam> fpinformationsIdParamList) {
        fpinformationsService.delete(fpinformationsIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-CRM-合作伙伴人员信息详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:55
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-CRM-合作伙伴人员信息详情  【api/crm/partner/GetFPInformations】")
    @GetMapping("/biz/crm/fpinformations/detail")
    public CommonResult<Fpinformations> detail(@Valid FpinformationsIdParam fpinformationsIdParam) {
        return CommonResult.data(fpinformationsService.detail(fpinformationsIdParam));
    }
}
