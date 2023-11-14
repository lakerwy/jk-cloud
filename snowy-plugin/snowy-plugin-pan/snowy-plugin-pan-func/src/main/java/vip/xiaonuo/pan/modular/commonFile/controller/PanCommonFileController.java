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
package vip.xiaonuo.pan.modular.commonFile.controller;

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
import vip.xiaonuo.pan.modular.commonFile.param.*;
import vip.xiaonuo.pan.modular.commonFile.service.PanCommonFileService;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonFilePermissionVo;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonFileVo;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonToMeVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 共享文件控制器
 *
 * @author mayike
 * @date  2023/09/06 21:07
 */
@Api(tags = "共享文件控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PanCommonFileController {

    @Resource
    private PanCommonFileService panCommonFileService;

    /**
     * 将文件共享给他人(并设置权限)
     *
     * 共享文件统一接口
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("将文件共享给他人(并设置权限)")
    @CommonLog("将文件共享给他人(并设置权限)")
    @PostMapping("/pan/commonFile/saveCommon")
    public CommonResult<String> saveCommon(@RequestBody @Valid CommonFileParam commonFileParam) {
        panCommonFileService.saveCommon(commonFileParam);
        return CommonResult.ok();
    }

    /**
     * 共享文件，取消某些人的共享权限(支持批量)
     *
     * 权限共享统一接口
     *
     * @author mayike
     * @date  2023/11/14 09:07
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("共享文件，取消某些人的共享权限")
    @CommonLog("共享文件，取消某些人的共享权限")
    @PostMapping("/pan/commonFile/removeCommon")
    public CommonResult<String> removeCommon(@RequestBody @Valid RemoveCommonParam removeCommonParam) {
        panCommonFileService.removeCommon(removeCommonParam);
        return CommonResult.ok();
    }


    /**
     * 获取某个文件，某个人的权限配置详情
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("获取某个文件，某个人的权限配置详情")
    @GetMapping("/pan/commonFile/detailByObjId")
    public CommonResult<CommonFilePermissionVo> detailByObjId(CommonUserParam commonUserParam) {
        return CommonResult.data(panCommonFileService.detailByObjId(commonUserParam));
    }

    /**
     * 获取某个共享文件的权限配置详情
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("获取某个共享文件的权限配置详情")
    @GetMapping("/pan/commonFile/detailByFileId")
    public CommonResult<List<CommonFilePermissionVo>> detailByFileId(String userFileId) {
        return CommonResult.data(panCommonFileService.detailByFileId(userFileId));
    }


    /**
     * 获取共享给我的文件列表(共享给我)
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("获取共享给我的文件列表(共享给我)")
    @GetMapping("/pan/commonFile/commonToMePage")
    public CommonResult<Page<CommonToMeVo>> commonToMePage(PanCommonFilePageParam panCommonFilePageParam) {
        return CommonResult.data(panCommonFileService.commonToMePage(panCommonFilePageParam));
    }

    /**
     * 我的共享
     * 用来做前台列表展示
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("获取共享文件分页（我的共享）")
    @GetMapping("/pan/commonFile/myCommonPage")
    public CommonResult<Page<CommonFileVo>> myCommonPage(PanCommonFilePageParam panCommonFilePageParam) {
        return CommonResult.data(panCommonFileService.myCommonPage(panCommonFilePageParam));
    }

}
