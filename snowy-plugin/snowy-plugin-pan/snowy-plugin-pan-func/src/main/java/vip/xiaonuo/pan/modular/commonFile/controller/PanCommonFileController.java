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
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.pan.modular.commonFile.entity.PanCommonFile;
import vip.xiaonuo.pan.modular.commonFile.param.*;
import vip.xiaonuo.pan.modular.commonFile.service.PanCommonFileService;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonFileUserVo;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonFileVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
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
    @ApiOperationSupport(order = 6)
    @ApiOperation("将文件共享给他人(并设置权限)")
    @CommonLog("将文件共享给他人(并设置权限)")
    @PostMapping("/pan/commonFile/commonFile")
    public CommonResult<String> commonFile(@RequestBody @Valid CommonFileParam commonFileParam) {
        panCommonFileService.commonFile(commonFileParam);
        return CommonResult.ok();
    }

    /**
     * 获取共享空间的全量用户列表
     *
     * 共享文件用户接口(共享给我文件的所有用户集合)
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取共享空间的全量用户列表(共享给我文件的所有用户集合)")
//    @SaCheckPermission("/pan/commonFile/commonFileUser")
    @GetMapping("/pan/commonFile/commonFileUser")
    public CommonResult<List<CommonFileUserVo>> commonFileUser() {
        return CommonResult.data(panCommonFileService.commonFileUser());
    }


    /**
     * 获取共享用户文件列表
     * 收到的共享(某人共享给我的文件列表)
     * 用来做前台列表展示
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("获取共享用户的文件列表，收到的共享(某人共享给我的文件列表)")
//    @SaCheckPermission("/pan/commonFile/commonFileByUser")
    @GetMapping("/pan/commonFile/commonFileByUser")
    public CommonResult<List<CommonFileVo>> commonFileByUser(@Valid String userId) {
        return CommonResult.data(panCommonFileService.commonFileByUser(userId));
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
//    @SaCheckPermission("/pan/commonFile/commonFileList")
    @GetMapping("/pan/commonFile/commonFileList")
    public CommonResult<IPage<CommonFileVo>> commonFileList(PanCommonFilePageParam panCommonFilePageParam) {
        return CommonResult.data(panCommonFileService.commonFileList(panCommonFilePageParam));
    }

}
