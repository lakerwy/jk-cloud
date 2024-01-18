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
package vip.xiaonuo.biz.modular.sysLive.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.sysLive.entity.Live;
import vip.xiaonuo.biz.modular.sysLive.param.LiveAddParam;
import vip.xiaonuo.biz.modular.sysLive.param.LiveEditParam;
import vip.xiaonuo.biz.modular.sysLive.param.LiveIdParam;
import vip.xiaonuo.biz.modular.sysLive.param.LivePageParam;
import vip.xiaonuo.biz.modular.sysLive.service.LiveService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 直播地址表控制器
 *
 * @author mayike
 * @date  2024/01/17 14:10
 */
@Api(tags = "直播地址表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class LiveController {

    @Resource
    private LiveService liveService;

    /**
     * 获取直播地址表分页
     *
     * @author mayike
     * @date  2024/01/17 14:10
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取直播地址表分页")
//    @SaCheckPermission("/biz/sysLive/page")
    @GetMapping("/biz/sysLive/page")
    public CommonResult<Page<Live>> page(LivePageParam livePageParam) {
        return CommonResult.data(liveService.page(livePageParam));
    }

    /**
     * 添加直播地址表
     *
     * @author mayike
     * @date  2024/01/17 14:10
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加直播地址表")
    @CommonLog("添加直播地址表")
//    @SaCheckPermission("/biz/sysLive/add")
    @PostMapping("/biz/sysLive/add")
    public CommonResult<String> add(@RequestBody @Valid LiveAddParam liveAddParam) {
        liveService.add(liveAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑直播地址表
     *
     * @author mayike
     * @date  2024/01/17 14:10
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑直播地址表")
    @CommonLog("编辑直播地址表")
//    @SaCheckPermission("/biz/sysLive/edit")
    @PostMapping("/biz/sysLive/edit")
    public CommonResult<String> edit(@RequestBody @Valid LiveEditParam liveEditParam) {
        liveService.edit(liveEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除直播地址表
     *
     * @author mayike
     * @date  2024/01/17 14:10
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除直播地址表")
    @CommonLog("删除直播地址表")
//    @SaCheckPermission("/biz/sysLive/delete")
    @PostMapping("/biz/sysLive/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<LiveIdParam> liveIdParamList) {
        liveService.delete(liveIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取直播地址表详情
     *
     * @author mayike
     * @date  2024/01/17 14:10
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取直播地址表详情")
//    @SaCheckPermission("/biz/sysLive/detail")
    @GetMapping("/biz/sysLive/detail")
    public CommonResult<Live> detail(@Valid LiveIdParam liveIdParam) {
        return CommonResult.data(liveService.detail(liveIdParam));
    }
}
