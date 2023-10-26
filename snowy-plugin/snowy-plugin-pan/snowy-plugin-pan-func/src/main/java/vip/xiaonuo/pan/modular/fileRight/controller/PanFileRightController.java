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
package vip.xiaonuo.pan.modular.fileRight.controller;

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
import vip.xiaonuo.pan.modular.fileRight.param.PanFileRightAddParam;
import vip.xiaonuo.pan.modular.fileRight.param.PanFileRightEditParam;
import vip.xiaonuo.pan.modular.fileRight.param.PanFileRightIdParam;
import vip.xiaonuo.pan.modular.fileRight.service.PanFileRightService;
import vip.xiaonuo.pan.modular.fileRight.vo.PanFileRightVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 网盘权限配置表控制器
 *
 * @author yong.ye
 * @date  2023/10/25 14:32
 */
@Api(tags = "网盘权限配置表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PanFileRightController {

    @Resource
    private PanFileRightService panFileRightService;

//    /**
//     * 获取网盘权限配置表分页
//     *
//     * @author yong.ye
//     * @date  2023/10/25 14:32
//     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取网盘权限配置表分页")
//    @SaCheckPermission("/pan/fileRight/page")
//    @GetMapping("/pan/fileRight/page")
//    public CommonResult<Page<PanFileRight>> page(PanFileRightPageParam panFileRightPageParam) {
//        return CommonResult.data(panFileRightService.page(panFileRightPageParam));
//    }

    /**
     * 添加网盘权限配置表 - 文件夹|文件设置配置权限
     *
     * @author yong.ye
     * @date  2023/10/25 14:32
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加网盘权限配置表 - 文件夹|文件设置配置权限")
    @CommonLog("添加网盘权限配置表 - 文件夹|文件设置配置权限")
//    @SaCheckPermission("/pan/fileRight/add")
    @PostMapping("/pan/fileRight/add")
    public CommonResult<String> add(@RequestBody @Valid PanFileRightAddParam panFileRightAddParam) {
        panFileRightService.add(panFileRightAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑网盘权限配置表 -  修改 文件夹|文件设置配置权限
     *
     * @author yong.ye
     * @date  2023/10/25 14:32
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑网盘权限配置表 -  修改 文件夹|文件设置配置权限")
    @CommonLog("编辑网盘权限配置表 -  修改 文件夹|文件设置配置权限")
//    @SaCheckPermission("/pan/fileRight/edit")
    @PostMapping("/pan/fileRight/edit")
    public CommonResult<String> edit(@RequestBody @Valid PanFileRightEditParam panFileRightEditParam) {
        panFileRightService.edit(panFileRightEditParam);
        return CommonResult.ok();
    }

//    /**
//     * 删除网盘权限配置表
//     *
//     * @author yong.ye
//     * @date  2023/10/25 14:32
//     */
//    @ApiOperationSupport(order = 4)
//    @ApiOperation("删除网盘权限配置表")
//    @CommonLog("删除网盘权限配置表")
//    @SaCheckPermission("/pan/fileRight/delete")
//    @PostMapping("/pan/fileRight/delete")
//    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
//                                                   CommonValidList<PanFileRightIdParam> panFileRightIdParamList) {
//        panFileRightService.delete(panFileRightIdParamList);
//        return CommonResult.ok();
//    }

    /**
     * 获取网盘权限配置表详情 - 根据 文件夹|文件 Id 获取权限管理详情
     *
     * @author yong.ye
     * @date  2023/10/25 14:32
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取网盘权限配置表详情 - 根据 文件夹|文件 Id 获取权限管理详情")
//    @SaCheckPermission("/pan/fileRight/detail")
    @GetMapping("/pan/fileRight/detail")
    public CommonResult<List<PanFileRightVo>> detail(@Valid PanFileRightIdParam panFileRightIdParam) {
        return CommonResult.data(panFileRightService.detail(panFileRightIdParam));
    }
}
