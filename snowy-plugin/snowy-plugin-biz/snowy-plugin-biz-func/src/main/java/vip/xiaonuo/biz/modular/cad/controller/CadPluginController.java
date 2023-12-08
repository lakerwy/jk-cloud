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
package vip.xiaonuo.biz.modular.cad.controller;

import cn.hutool.core.lang.tree.Tree;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.cad.entity.CadUser;
import vip.xiaonuo.biz.modular.cad.param.*;
import vip.xiaonuo.biz.modular.cad.service.*;
import vip.xiaonuo.biz.modular.cad.vo.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 【CAD插件端】控制器-cad插件端接口集合
 *
 * @author yong.ye
 * @date  2023/11/29 10:35
 */
@Api(tags = "【CAD插件端】控制器-cad插件端接口集合")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class CadPluginController {
    @Resource
    private CadUserService cadUserService;

    @Resource
    private CadStageService cadStageService;

    @Resource
    private CadProjectService cadProjectService;

    @Resource
    private CadSubItemService cadSubItemService;

    @Resource
    private CadSubItemUserService cadSubItemUserService;

    /**
     * 【CAD-插件端】登录
     *
     * @author yong.ye
     * @date  2023/12/01 10:35
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("【CAD-插件端】登录")
    @CommonLog("【CAD-插件端】登录")
    @PostMapping("/biz/cad/plugin/login")
    public CommonResult<CadUser> login(@RequestBody @Valid CadLoginParam cadLoginParam) {
        return CommonResult.data(cadUserService.login(cadLoginParam));
    }

    /**
     * 【CAD-插件端】CAD-插件端修改用户密码
     *
     * @author yong.ye
     * @date  2023/12/01 10:40
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("【CAD-插件端】修改用户密码")
    @CommonLog("【CAD-插件端】修改用户密码")
    @PostMapping("/biz/cad/plugin/changePassword")
    public CommonResult<String> changePassword(@RequestBody @Valid CadChangePasswordParam cadChangePasswordParam) {
        cadUserService.changePassword(cadChangePasswordParam);
        return CommonResult.ok();
    }

    /**
     * 【CAD-插件端】根据登录用户Id,获取当前用户所参与的所有项目列表（包括项目管理者）
     *
     * @author yong.ye
     * @date  2023/12/01 10:40
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("【CAD-插件端】根据登录用户Id,获取当前用户所参与的所有项目列表（包括项目管理者）")
    @GetMapping("/biz/cad/plugin/myProjects")
    public CommonResult<List<CadProjectVo>> myProjects(String cadUserId) {
        return CommonResult.data(cadProjectService.projectsByUserId(cadUserId));
    }

    /**
     * 【CAD-插件端】根据项目Id,当前用户Id,获取当前项目当前人树状结构
     *
     * @author yong.ye
     * @date  2023/12/01 10:40
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("【CAD-插件端】根据项目Id,当前用户Id,获取当前项目当前人树状结构")
    @GetMapping("/biz/cad/plugin/myFileTree")
    public CommonResult<List<Tree<String>>> myFileTree(MyFileTreeParam myFileTreeParam) {
        return CommonResult.data(cadProjectService.myFileTree(myFileTreeParam));
    }

}
