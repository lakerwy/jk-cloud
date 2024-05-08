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
package vip.xiaonuo.bp.modular.platform.controller;

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
import vip.xiaonuo.bp.modular.platform.entity.NoticeTz;
import vip.xiaonuo.bp.modular.platform.param.NoticeTzAddParam;
import vip.xiaonuo.bp.modular.platform.param.NoticeTzEditParam;
import vip.xiaonuo.bp.modular.platform.param.NoticeTzIdParam;
import vip.xiaonuo.bp.modular.platform.param.NoticeTzPageParam;
import vip.xiaonuo.bp.modular.platform.service.NoticeTzService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 招采管理 - 通知公告控制器
 *
 * @author yong.ye
 * @date  2024/05/07 16:34
 */
@Api(tags = "招采管理 - 通知公告控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class NoticeTzController {

    @Resource
    private NoticeTzService noticeTzService;

    /**
     * 获取招采管理 - 通知公告分页
     *
     * @author yong.ye
     * @date  2024/05/07 16:34
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取招采管理 - 通知公告分页")
//    @SaCheckPermission("/bp/platform/noticeTz/page")
    @GetMapping("/bp/platform/noticeTz/page")
    public CommonResult<Page<NoticeTz>> page(NoticeTzPageParam noticeTzPageParam) {
        return CommonResult.data(noticeTzService.page(noticeTzPageParam));
    }

    /**
     * 添加招采管理 - 通知公告
     *
     * @author yong.ye
     * @date  2024/05/07 16:34
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加招采管理 - 通知公告")
    @CommonLog("添加招采管理 - 通知公告")
//    @SaCheckPermission("/bp/platform/noticeTz/add")
    @PostMapping("/bp/platform/noticeTz/add")
    public CommonResult<String> add(@RequestBody @Valid NoticeTzAddParam noticeTzAddParam) {
        noticeTzService.add(noticeTzAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑招采管理 - 通知公告
     *
     * @author yong.ye
     * @date  2024/05/07 16:34
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑招采管理 - 通知公告")
    @CommonLog("编辑招采管理 - 通知公告")
//    @SaCheckPermission("/bp/platform/noticeTz/edit")
    @PostMapping("/bp/platform/noticeTz/edit")
    public CommonResult<String> edit(@RequestBody @Valid NoticeTzEditParam noticeTzEditParam) {
        noticeTzService.edit(noticeTzEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除招采管理 - 通知公告
     *
     * @author yong.ye
     * @date  2024/05/07 16:34
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除招采管理 - 通知公告")
    @CommonLog("删除招采管理 - 通知公告")
//    @SaCheckPermission("/bp/platform/noticeTz/delete")
    @PostMapping("/bp/platform/noticeTz/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<NoticeTzIdParam> noticeTzIdParamList) {
        noticeTzService.delete(noticeTzIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取招采管理 - 通知公告详情
     *
     * @author yong.ye
     * @date  2024/05/07 16:34
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取招采管理 - 通知公告详情")
//    @SaCheckPermission("/bp/platform/noticeTz/detail")
    @GetMapping("/bp/platform/noticeTz/detail")
    public CommonResult<NoticeTz> detail(@Valid NoticeTzIdParam noticeTzIdParam) {
        return CommonResult.data(noticeTzService.detail(noticeTzIdParam));
    }
}
