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
package vip.xiaonuo.biz.modular.crm.major.controller;

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
import vip.xiaonuo.biz.modular.crm.major.entity.Major;
import vip.xiaonuo.biz.modular.crm.major.param.MajorAddParam;
import vip.xiaonuo.biz.modular.crm.major.param.MajorEditParam;
import vip.xiaonuo.biz.modular.crm.major.param.MajorIdParam;
import vip.xiaonuo.biz.modular.crm.major.param.MajorPageParam;
import vip.xiaonuo.biz.modular.crm.major.service.MajorService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-CRM-图审专业控制器
 *
 * @author yong.ye
 * @date  2023/06/30 16:42
 */
@Api(tags = "业务表-CRM-图审专业控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class MajorController {

    @Resource
    private MajorService majorService;

    /**
     * 获取业务表-CRM-图审专业分页
     *
     * @author yong.ye
     * @date  2023/06/30 16:42
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-CRM-图审专业分页")
    @GetMapping("/biz/crm/major/page")
    public CommonResult<Page<Major>> page(MajorPageParam majorPageParam) {
        return CommonResult.data(majorService.page(majorPageParam));
    }

    /**
     * 添加业务表-CRM-图审专业
     *
     * @author yong.ye
     * @date  2023/06/30 16:42
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-CRM-图审专业")
    @CommonLog("添加业务表-CRM-图审专业")
    @PostMapping("/biz/crm/major/add")
    public CommonResult<String> add(@RequestBody @Valid MajorAddParam majorAddParam) {
        majorService.add(majorAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-CRM-图审专业
     *
     * @author yong.ye
     * @date  2023/06/30 16:42
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-CRM-图审专业")
    @CommonLog("编辑业务表-CRM-图审专业")
    @PostMapping("/biz/crm/major/edit")
    public CommonResult<String> edit(@RequestBody @Valid MajorEditParam majorEditParam) {
        majorService.edit(majorEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-CRM-图审专业
     *
     * @author yong.ye
     * @date  2023/06/30 16:42
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-CRM-图审专业")
    @CommonLog("删除业务表-CRM-图审专业")
    @PostMapping("/biz/crm/major/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<MajorIdParam> majorIdParamList) {
        majorService.delete(majorIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-CRM-图审专业详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:42
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-CRM-图审专业详情")
    @GetMapping("/biz/crm/major/detail")
    public CommonResult<Major> detail(@Valid MajorIdParam majorIdParam) {
        return CommonResult.data(majorService.detail(majorIdParam));
    }
}
