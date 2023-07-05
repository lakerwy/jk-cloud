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
package vip.xiaonuo.biz.modular.crm.rollinout.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
import vip.xiaonuo.biz.modular.crm.parameters.param.ParametersIdParam;
import vip.xiaonuo.biz.modular.crm.parameters.param.ParametersInfoPageParam;
import vip.xiaonuo.biz.modular.crm.parameters.vo.ParametersInfoVo;
import vip.xiaonuo.biz.modular.crm.rollinout.param.*;
import vip.xiaonuo.biz.modular.crm.rollinout.vo.RollinoutInfoVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.crm.rollinout.entity.Rollinout;
import vip.xiaonuo.biz.modular.crm.rollinout.service.RollinoutService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-CRM-合作伙伴人员转入转出控制器
 *
 * @author yong.ye
 * @date  2023/06/30 16:56
 */
@Api(tags = "业务表-CRM-合作伙伴人员转入转出控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class RollinoutController {

    @Resource
    private RollinoutService rollinoutService;

    /**
     * 获取业务表-CRM-合作伙伴人员转入转出分页
     *
     * @author yong.ye
     * @date  2023/06/30 16:56
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-CRM-合作伙伴人员转入转出分页")
    @GetMapping("/biz/crm/rollinout/page")
    public CommonResult<Page<Rollinout>> page(RollinoutPageParam rollinoutPageParam) {
        return CommonResult.data(rollinoutService.page(rollinoutPageParam));
    }

    /**
     * 添加业务表-CRM-合作伙伴人员转入转出
     *
     * @author yong.ye
     * @date  2023/06/30 16:56
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-CRM-合作伙伴人员转入转出  【api/crm/partner/AddRollInOut】")
    @CommonLog("添加业务表-CRM-合作伙伴人员转入转出")
    @PostMapping("/biz/crm/rollinout/add")
    public CommonResult<String> add(@RequestBody @Valid RollinoutAddParam rollinoutAddParam) {
        rollinoutService.add(rollinoutAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-CRM-合作伙伴人员转入转出
     *
     * @author yong.ye
     * @date  2023/06/30 16:56
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-CRM-合作伙伴人员转入转出  【api/crm/partner/UpdateRollInOut】")
    @CommonLog("编辑业务表-CRM-合作伙伴人员转入转出")
    @PostMapping("/biz/crm/rollinout/edit")
    public CommonResult<String> edit(@RequestBody @Valid RollinoutEditParam rollinoutEditParam) {
        rollinoutService.edit(rollinoutEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-CRM-合作伙伴人员转入转出
     *
     * @author yong.ye
     * @date  2023/06/30 16:56
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-CRM-合作伙伴人员转入转出  【api/crm/partner/DeleteRollInOut】")
    @CommonLog("删除业务表-CRM-合作伙伴人员转入转出")
    @PostMapping("/biz/crm/rollinout/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<RollinoutIdParam> rollinoutIdParamList) {
        rollinoutService.delete(rollinoutIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-CRM-合作伙伴人员转入转出详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:56
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-CRM-合作伙伴人员转入转出详情")
    @GetMapping("/biz/crm/rollinout/detail")
    public CommonResult<Rollinout> detail(@Valid RollinoutIdParam rollinoutIdParam) {
        return CommonResult.data(rollinoutService.detail(rollinoutIdParam));
    }

    /**
     * 获取人员转入转出列表（分页）-携带人员信息
     *
     * @author yong.ye
     * @date  2023/07/03 14:04
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取人员转入转出列表（分页）-携带人员信息  【api/crm/partner/GetRollInOut】")
    @GetMapping("/biz/crm/rollinout/listPage")
    public CommonResult<IPage<RollinoutInfoVo>> listPage(@Valid RollinoutInfoPageParam rollinoutInfoPageParam) {
        return CommonResult.data(rollinoutService.pageList(rollinoutInfoPageParam));
    }

    /**
     * 根据主键ID获取人员转入转出列表（分页）-携带人员信息
     *
     * @author yong.ye
     * @date  2023/07/03 14:04
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("根据ID获取合同参数详情（携带合同信息）  【api/crm/partner/GetRollInOut】")
    @GetMapping("/biz/crm/rollinout/detailById")
    public CommonResult<RollinoutInfoVo> detailById(@Valid RollinoutIdParam rollinoutIdParam) {
        return CommonResult.data(rollinoutService.detailById(rollinoutIdParam));
    }
}
