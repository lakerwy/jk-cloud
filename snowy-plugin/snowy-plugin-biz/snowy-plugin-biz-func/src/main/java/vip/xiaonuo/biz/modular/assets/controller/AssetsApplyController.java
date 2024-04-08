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
package vip.xiaonuo.biz.modular.assets.controller;

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
import vip.xiaonuo.biz.modular.assets.vo.AssetsApplyVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.assets.param.AssetsApplyAddParam;
import vip.xiaonuo.biz.modular.assets.param.AssetsApplyEditParam;
import vip.xiaonuo.biz.modular.assets.param.AssetsApplyIdParam;
import vip.xiaonuo.biz.modular.assets.param.AssetsApplyPageParam;
import vip.xiaonuo.biz.modular.assets.service.AssetsApplyService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 固资申购 - 申请流程控制器
 *
 * @author yong.ye
 * @date  2024/04/08 16:17
 */
@Api(tags = "固资申购 - 申请流程控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class AssetsApplyController {

    @Resource
    private AssetsApplyService assetsApplyService;

    /**
     * 获取固资申购 - 申请流程分页
     *
     * @author yong.ye
     * @date  2024/04/08 16:17
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取固资申购 - 申请流程分页")
    @SaCheckPermission("/biz/assets/page")
    @GetMapping("/biz/assets/page")
    public CommonResult<Page<AssetsApplyVo>> page(AssetsApplyPageParam assetsApplyPageParam) {
        return CommonResult.data(assetsApplyService.page(assetsApplyPageParam));
    }

    /**
     * 添加固资申购 - 申请流程
     *
     * @author yong.ye
     * @date  2024/04/08 16:17
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加固资申购 - 申请流程")
    @CommonLog("添加固资申购 - 申请流程")
    @SaCheckPermission("/biz/assets/add")
    @PostMapping("/biz/assets/add")
    public CommonResult<String> add(@RequestBody @Valid AssetsApplyAddParam assetsApplyAddParam) {
        assetsApplyService.add(assetsApplyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑固资申购 - 申请流程
     *
     * @author yong.ye
     * @date  2024/04/08 16:17
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑固资申购 - 申请流程")
    @CommonLog("编辑固资申购 - 申请流程")
    @SaCheckPermission("/biz/assets/edit")
    @PostMapping("/biz/assets/edit")
    public CommonResult<String> edit(@RequestBody @Valid AssetsApplyEditParam assetsApplyEditParam) {
        assetsApplyService.edit(assetsApplyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除固资申购 - 申请流程
     *
     * @author yong.ye
     * @date  2024/04/08 16:17
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除固资申购 - 申请流程")
    @CommonLog("删除固资申购 - 申请流程")
    @SaCheckPermission("/biz/assets/delete")
    @PostMapping("/biz/assets/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<AssetsApplyIdParam> assetsApplyIdParamList) {
        assetsApplyService.delete(assetsApplyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取固资申购 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/04/08 16:17
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取固资申购 - 申请流程详情")
    @SaCheckPermission("/biz/assets/detail")
    @GetMapping("/biz/assets/detail")
    public CommonResult<AssetsApplyVo> detail(@Valid AssetsApplyIdParam assetsApplyIdParam) {
        return CommonResult.data(assetsApplyService.detail(assetsApplyIdParam));
    }
}
