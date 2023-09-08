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
package vip.xiaonuo.pan.modular.music.controller;

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
import vip.xiaonuo.pan.modular.music.entity.PanMusic;
import vip.xiaonuo.pan.modular.music.param.PanMusicAddParam;
import vip.xiaonuo.pan.modular.music.param.PanMusicEditParam;
import vip.xiaonuo.pan.modular.music.param.PanMusicIdParam;
import vip.xiaonuo.pan.modular.music.param.PanMusicPageParam;
import vip.xiaonuo.pan.modular.music.service.PanMusicService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 云盘-音乐实体类控制器
 *
 * @author mayike
 * @date  2023/09/08 14:13
 */
@Api(tags = "云盘-音乐实体类控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PanMusicController {

    @Resource
    private PanMusicService panMusicService;

    /**
     * 获取云盘-音乐实体类分页
     *
     * @author mayike
     * @date  2023/09/08 14:13
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取云盘-音乐实体类分页")
    @SaCheckPermission("/pan/music/page")
    @GetMapping("/pan/music/page")
    public CommonResult<Page<PanMusic>> page(PanMusicPageParam panMusicPageParam) {
        return CommonResult.data(panMusicService.page(panMusicPageParam));
    }

    /**
     * 添加云盘-音乐实体类
     *
     * @author mayike
     * @date  2023/09/08 14:13
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加云盘-音乐实体类")
    @CommonLog("添加云盘-音乐实体类")
    @SaCheckPermission("/pan/music/add")
    @PostMapping("/pan/music/add")
    public CommonResult<String> add(@RequestBody @Valid PanMusicAddParam panMusicAddParam) {
        panMusicService.add(panMusicAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑云盘-音乐实体类
     *
     * @author mayike
     * @date  2023/09/08 14:13
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑云盘-音乐实体类")
    @CommonLog("编辑云盘-音乐实体类")
    @SaCheckPermission("/pan/music/edit")
    @PostMapping("/pan/music/edit")
    public CommonResult<String> edit(@RequestBody @Valid PanMusicEditParam panMusicEditParam) {
        panMusicService.edit(panMusicEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除云盘-音乐实体类
     *
     * @author mayike
     * @date  2023/09/08 14:13
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除云盘-音乐实体类")
    @CommonLog("删除云盘-音乐实体类")
    @SaCheckPermission("/pan/music/delete")
    @PostMapping("/pan/music/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PanMusicIdParam> panMusicIdParamList) {
        panMusicService.delete(panMusicIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取云盘-音乐实体类详情
     *
     * @author mayike
     * @date  2023/09/08 14:13
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取云盘-音乐实体类详情")
    @SaCheckPermission("/pan/music/detail")
    @GetMapping("/pan/music/detail")
    public CommonResult<PanMusic> detail(@Valid PanMusicIdParam panMusicIdParam) {
        return CommonResult.data(panMusicService.detail(panMusicIdParam));
    }
}
