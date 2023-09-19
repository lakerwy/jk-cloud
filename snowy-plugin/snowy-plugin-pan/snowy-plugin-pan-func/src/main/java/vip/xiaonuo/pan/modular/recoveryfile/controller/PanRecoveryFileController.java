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
package vip.xiaonuo.pan.modular.recoveryfile.controller;

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
import vip.xiaonuo.pan.modular.recoveryfile.entity.PanRecoveryFile;
import vip.xiaonuo.pan.modular.recoveryfile.param.*;
import vip.xiaonuo.pan.modular.recoveryfile.param.dto.BatchDeleteDto;
import vip.xiaonuo.pan.modular.recoveryfile.param.dto.RestoreFileDto;
import vip.xiaonuo.pan.modular.recoveryfile.service.PanRecoveryFileService;
import vip.xiaonuo.pan.modular.recoveryfile.vo.RecoveryFileVo;
import vip.xiaonuo.pan.modular.userFile.param.UserFileIdParam;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 网盘-回收站控制器
 *
 * @author yong.ye
 * @date  2023/09/07 15:54
 */
@Api(tags = "网盘-回收站控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PanRecoveryFileController {

    @Resource
    private PanRecoveryFileService panRecoveryFileService;

    /**
     * 获取网盘-回收站分页 (回收站文件列表)
     *
     * @author yong.ye
     * @date  2023/09/07 15:54
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取网盘-回收站分页 (回收站文件列表)")
    @GetMapping("/pan/recovery/listPage")
    public CommonResult<IPage<RecoveryFileVo>> listPage(PanRecoveryFilePageParam panRecoveryFilePageParam) {
        return CommonResult.data(panRecoveryFileService.listPage(panRecoveryFilePageParam));
    }

    /**
     * 删除网盘-回收站 (支持批量删除)
     *
     * @author yong.ye
     * @date  2023/09/07 15:54
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("删除网盘-回收站(支持批量删除)")
    @CommonLog("删除网盘-回收站(支持批量删除)")
    @PostMapping("/pan/recovery/batchDelete")
    public CommonResult<String> batchDelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                CommonValidList<PanRecoveryFileIdParam> recoveryFileIdParamList) {
        panRecoveryFileService.batchDelete(recoveryFileIdParamList);
        return CommonResult.ok();
    }


    /**
     * 网盘-回收站 (还原回收站文件)
     *
     * @author yong.ye
     * @date  2023/09/07 15:54
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("网盘-回收站(还原回收站文件)")
    @CommonLog("网盘-回收站(还原回收站文件)")
    @PostMapping("/pan/recovery/restoreFile")
    public CommonResult<String> restoreFile(@RequestBody @Valid RestoreFileDto restoreFileDto) {
        panRecoveryFileService.restoreFile(restoreFileDto);
        return CommonResult.ok();
    }


}
