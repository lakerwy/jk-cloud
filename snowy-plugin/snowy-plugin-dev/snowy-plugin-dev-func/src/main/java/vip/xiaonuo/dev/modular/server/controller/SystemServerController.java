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
package vip.xiaonuo.dev.modular.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.dev.modular.server.entity.SystemServer;
import vip.xiaonuo.dev.modular.server.entity.TemplateInfo;
import vip.xiaonuo.dev.modular.server.message.MessageService;
import vip.xiaonuo.dev.modular.server.param.*;
import vip.xiaonuo.dev.modular.server.service.SystemServerService;
import vip.xiaonuo.dev.modular.server.util.TemplateUtil;
import vip.xiaonuo.dev.modular.server.vo.SystemRoleVo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 系统服务表控制器
 *
 * @author ljy
 * @date  2023/12/29 11:31
 */
@Api(tags = "系统服务表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SystemServerController {

    @Resource
    private SystemServerService systemServerService;

    @Resource
    private MessageService messageService;

    /**
     * 获取系统服务表分页
     *
     * @author ljy
     * @date  2023/12/29 11:31
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取系统服务表分页")
    //@SaCheckPermission("/sys/server/page")
    @GetMapping("/sys/server/page")
    public CommonResult<Page<SystemServer>> page(SystemServerPageParam systemServerPageParam) {
        return CommonResult.data(systemServerService.page(systemServerPageParam));
    }

    /**
     * 添加系统服务表
     *
     * @author ljy
     * @date  2023/12/29 11:31
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加系统服务表")
    @CommonLog("添加系统服务表")
    //@SaCheckPermission("/sys/server/add")
    @PostMapping("/sys/server/add")
    public CommonResult<String> add(@RequestBody @Valid SystemServerAddParam systemServerAddParam) {
        systemServerService.add(systemServerAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑系统服务表
     *
     * @author ljy
     * @date  2023/12/29 11:31
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑系统服务表")
    @CommonLog("编辑系统服务表")
    //@SaCheckPermission("/sys/server/edit")
    @PostMapping("/sys/server/edit")
    public CommonResult<String> edit(@RequestBody @Valid SystemServerEditParam systemServerEditParam) {
        systemServerService.edit(systemServerEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除系统服务表
     *
     * @author ljy
     * @date  2023/12/29 11:31
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除系统服务表")
    @CommonLog("删除系统服务表")
    //@SaCheckPermission("/sys/server/delete")
    @PostMapping("/sys/server/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SystemServerIdParam> systemServerIdParamList) {
        systemServerService.delete(systemServerIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取系统服务表详情
     *
     * @author ljy
     * @date  2023/12/29 11:31
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取系统服务表详情")
    //@SaCheckPermission("/sys/server/detail")
    @GetMapping("/sys/server/detail")
    public CommonResult<SystemServer> detail(@Valid SystemServerIdParam systemServerIdParam) {
        return CommonResult.data(systemServerService.detail(systemServerIdParam));
    }

    /**
     * 我的项目
     *
     * @author ljy
     * @date  2024/01/15 15:31
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("我的项目")
    @GetMapping("/sys/server/myServerTask")
    @Deprecated
    public CommonResult<Page<SystemServer>> myServerTask(MyServerParam myServerParam) {
        return CommonResult.data(systemServerService.myServerTask(myServerParam));
    }

//    @ApiOperationSupport(order = 6)
//    @ApiOperation("测试消息队列")
//    @GetMapping("/sys/server/test")
//    public CommonResult<List<TemplateInfo> > test() {
//        List<TemplateInfo> templateInfoList = TemplateUtil.parseExcel(new File("系统初始化模板.xlsx"));
//        return CommonResult.data(templateInfoList);
//    }

    @ApiOperationSupport(order = 7)
    @ApiOperation("获取项目初始化模板下载")
    @GetMapping(value = "/sys/server/downloadTemplate",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getTemplate(HttpServletResponse response) throws IOException {
        systemServerService.downloadTemplate(response);
    }

    @ApiOperationSupport(order = 8)
    @ApiOperation("系统模板初始化上传")
    @CommonLog("系统模板初始化上传")
    @PostMapping("/sys/user/initTemplate")
    public CommonResult<Void> initTemplate(@RequestPart("file") @ApiParam(value="文件", required = true) MultipartFile file,@RequestParam("systemType") String systemType) {
        systemServerService.parseExcel(file,systemType);
        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 9)
    @ApiOperation("系统角色列表")
    @GetMapping("/sys/user/getSystemRoles")
    public CommonResult<List<SystemRoleVo>> getSystemRoles(@RequestParam(value = "systemType") String systemType){
        return CommonResult.data(systemServerService.getSystemRoles(systemType));
    }
}
