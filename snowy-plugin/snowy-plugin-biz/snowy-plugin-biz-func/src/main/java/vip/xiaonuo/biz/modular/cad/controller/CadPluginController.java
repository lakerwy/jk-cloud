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
import vip.xiaonuo.biz.modular.cad.entity.CadProfession;
import vip.xiaonuo.biz.modular.cad.entity.CadStage;
import vip.xiaonuo.biz.modular.cad.entity.CadSubItem;
import vip.xiaonuo.biz.modular.cad.entity.CadUser;
import vip.xiaonuo.biz.modular.cad.param.*;
import vip.xiaonuo.biz.modular.cad.service.*;
import vip.xiaonuo.biz.modular.cad.vo.CadProjectVo;
import vip.xiaonuo.biz.modular.cad.vo.CadUserVo;
import vip.xiaonuo.biz.modular.cad.vo.StageSubItemVo;
import vip.xiaonuo.biz.modular.cad.vo.SubItemUserVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * CAD-插件端控制器-cad插件端接口集合
 *
 * @author yong.ye
 * @date  2023/11/29 10:35
 */
@Api(tags = "CAD-插件端控制器-cad插件端接口集合")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class CadPluginController {

    @Resource
    private CadProfessionService cadProfessionService;

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
     * 【用户】修改CAD插件端用户密码
     *
     * @author yong.ye
     * @date  2023/11/29 10:40
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("【用户】修改CAD插件端用户密码")
//    @SaCheckPermission("/biz/cad/plugin/user/changePassword")
    @GetMapping("/biz/cad/plugin/user/changePassword")
    public CommonResult<String> changePassword(CadUserPageParam cadUserPageParam) {
        return CommonResult.ok();
    }

}
