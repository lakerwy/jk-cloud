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
package vip.xiaonuo.biz.modular.keyProject.controller;

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
import vip.xiaonuo.biz.modular.keyProject.entity.BizKeyProject;
import vip.xiaonuo.biz.modular.keyProject.param.BizKeyProjectAddParam;
import vip.xiaonuo.biz.modular.keyProject.param.BizKeyProjectEditParam;
import vip.xiaonuo.biz.modular.keyProject.param.BizKeyProjectIdParam;
import vip.xiaonuo.biz.modular.keyProject.param.BizKeyProjectPageParam;
import vip.xiaonuo.biz.modular.keyProject.service.BizKeyProjectService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * BIZ-重点项目信息控制器
 *
 * @author yong.ye
 * @date  2023/12/18 15:29
 */
@Api(tags = "BIZ-重点项目信息控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizKeyProjectController {

    @Resource
    private BizKeyProjectService bizKeyProjectService;

    /**
     * 获取BIZ-重点项目信息分页
     *
     * @author yong.ye
     * @date  2023/12/18 15:29
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取BIZ-重点项目信息分页")
//    @SaCheckPermission("/biz/keyProject/page")
    @GetMapping("/biz/keyProject/page")
    public CommonResult<Page<BizKeyProject>> page(BizKeyProjectPageParam bizKeyProjectPageParam) {
        return CommonResult.data(bizKeyProjectService.page(bizKeyProjectPageParam));
    }

    /**
     * 添加BIZ-重点项目信息
     *
     * @author yong.ye
     * @date  2023/12/18 15:29
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加BIZ-重点项目信息")
    @CommonLog("添加BIZ-重点项目信息")
//    @SaCheckPermission("/biz/keyProject/add")
    @PostMapping("/biz/keyProject/add")
    public CommonResult<String> add(@RequestBody @Valid BizKeyProjectAddParam bizKeyProjectAddParam) {
        bizKeyProjectService.add(bizKeyProjectAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑BIZ-重点项目信息
     *
     * @author yong.ye
     * @date  2023/12/18 15:29
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑BIZ-重点项目信息")
    @CommonLog("编辑BIZ-重点项目信息")
//    @SaCheckPermission("/biz/keyProject/edit")
    @PostMapping("/biz/keyProject/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizKeyProjectEditParam bizKeyProjectEditParam) {
        bizKeyProjectService.edit(bizKeyProjectEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除BIZ-重点项目信息
     *
     * @author yong.ye
     * @date  2023/12/18 15:29
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除BIZ-重点项目信息")
    @CommonLog("删除BIZ-重点项目信息")
//    @SaCheckPermission("/biz/keyProject/delete")
    @PostMapping("/biz/keyProject/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizKeyProjectIdParam> bizKeyProjectIdParamList) {
        bizKeyProjectService.delete(bizKeyProjectIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取BIZ-重点项目信息详情
     *
     * @author yong.ye
     * @date  2023/12/18 15:29
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取BIZ-重点项目信息详情")
//    @SaCheckPermission("/biz/keyProject/detail")
    @GetMapping("/biz/keyProject/detail")
    public CommonResult<BizKeyProject> detail(@Valid BizKeyProjectIdParam bizKeyProjectIdParam) {
        return CommonResult.data(bizKeyProjectService.detail(bizKeyProjectIdParam));
    }
}
