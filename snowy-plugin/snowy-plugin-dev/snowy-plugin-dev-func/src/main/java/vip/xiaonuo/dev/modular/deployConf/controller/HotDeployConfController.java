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
package vip.xiaonuo.dev.modular.deployConf.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.dev.modular.deployConf.entity.HotDeployConf;
import vip.xiaonuo.dev.modular.deployConf.param.HotDeployConfAddParam;
import vip.xiaonuo.dev.modular.deployConf.param.HotDeployConfEditParam;
import vip.xiaonuo.dev.modular.deployConf.param.HotDeployConfIdParam;
import vip.xiaonuo.dev.modular.deployConf.param.HotDeployConfPageParam;
import vip.xiaonuo.dev.modular.deployConf.service.HotDeployConfService;
import vip.xiaonuo.dev.modular.file.enums.DevFileEngineTypeEnum;
import vip.xiaonuo.dev.modular.file.service.DevFileService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * 热部署配置控制器
 *
 * @author mayike
 * @date  2023/08/10 16:14
 */
@Api(tags = "热部署配置控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HotDeployConfController {

    @Resource
    private HotDeployConfService hotDeployConfService;

    /**
     * 获取热部署配置分页
     *
     * @author mayike
     * @date  2023/08/10 16:14
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取热部署配置分页")
//    @SaCheckPermission("/dev/deployConf/page")
    @GetMapping("/dev/deployConf/page")
    public CommonResult<Page<HotDeployConf>> page(HotDeployConfPageParam hotDeployConfPageParam) {
        return CommonResult.data(hotDeployConfService.page(hotDeployConfPageParam));
    }

    /**
     * 添加热部署配置
     *
     * @author mayike
     * @date  2023/08/10 16:14
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加热部署配置")
    @CommonLog("添加热部署配置")
//    @SaCheckPermission("/dev/deployConf/add")
    @PostMapping("/dev/deployConf/add")
    public CommonResult<String> add(@RequestBody @Valid HotDeployConfAddParam hotDeployConfAddParam) {
        hotDeployConfService.add(hotDeployConfAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑热部署配置
     *
     * @author mayike
     * @date  2023/08/10 16:14
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑热部署配置")
    @CommonLog("编辑热部署配置")
//    @SaCheckPermission("/dev/deployConf/edit")
    @PostMapping("/dev/deployConf/edit")
    public CommonResult<String> edit(@RequestBody @Valid HotDeployConfEditParam hotDeployConfEditParam) {
        hotDeployConfService.edit(hotDeployConfEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除热部署配置
     *
     * @author mayike
     * @date  2023/08/10 16:14
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除热部署配置")
    @CommonLog("删除热部署配置")
//    @SaCheckPermission("/dev/deployConf/delete")
    @PostMapping("/dev/deployConf/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HotDeployConfIdParam> hotDeployConfIdParamList) {
        hotDeployConfService.delete(hotDeployConfIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取热部署配置详情
     *
     * @author mayike
     * @date  2023/08/10 16:14
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取热部署配置详情")
//    @SaCheckPermission("/dev/deployConf/detail")
    @GetMapping("/dev/deployConf/detail")
    public CommonResult<HotDeployConf> detail(@Valid HotDeployConfIdParam hotDeployConfIdParam) {
        return CommonResult.data(hotDeployConfService.detail(hotDeployConfIdParam));
    }

    /**
     * 本地文件上传，返回文件Url
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 4)
    @ApiOperation("上传移动端安装包返回url")
    @CommonLog("上传移动端安装包返回url")
    @PostMapping("/dev/deployConf/uploadPackage")
    public CommonResult<Map<String,String>> uploadPackageReturnUrl(@RequestPart("file") MultipartFile file, @RequestPart(value = "version") String version) {
        return  CommonResult.data(hotDeployConfService.save(version,file));
    }

    /**
     * 获取热部署配置详情
     *
     * @author mayike
     * @date  2023/08/10 16:14
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取最新安装包信息")
//    @SaCheckPermission("/dev/deployConf/getInfo")
    @GetMapping("/dev/deployConf/getInfo")
    public CommonResult<HotDeployConf> getInfo() {
        return CommonResult.data(hotDeployConfService.getInfo());
    }
}
