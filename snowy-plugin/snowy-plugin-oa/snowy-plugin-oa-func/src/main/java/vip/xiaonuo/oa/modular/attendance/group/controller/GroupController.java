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
package vip.xiaonuo.oa.modular.attendance.group.controller;

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
import vip.xiaonuo.oa.modular.attendance.group.param.*;
import vip.xiaonuo.oa.modular.attendance.client.WebServerClient;
import vip.xiaonuo.oa.modular.attendance.group.entity.Group;
import vip.xiaonuo.oa.modular.attendance.group.param.GroupIdParam;
import vip.xiaonuo.oa.modular.attendance.group.service.GroupService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 考勤-考勤组控制器
 *
 * @author mayike
 * @date  2024/06/05 15:51
 */
@Api(tags = "考勤-考勤组控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class GroupController {

    @Resource
    private GroupService groupService;

    @Resource
    WebServerClient webServerClient;

    /**
     * 获取考勤-考勤组分页
     *
     * @author mayike
     * @date  2024/06/05 15:51
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取考勤-考勤组分页")
//    @SaCheckPermission("/oa/group/page")
    @GetMapping("/oa/group/page")
    public CommonResult<Page<Group>> page(GroupPageParam groupPageParam) {
        return CommonResult.data(groupService.page(groupPageParam));
    }

    /**
     * 添加考勤-考勤组
     *
     * @author mayike
     * @date  2024/06/05 15:51
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加考勤-考勤组")
    @CommonLog("添加考勤-考勤组")
//    @SaCheckPermission("/oa/group/add")
    @PostMapping("/oa/group/add")
    public CommonResult<String> add(@RequestBody @Valid GroupAddParam groupAddParam) {
        groupService.add(groupAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑考勤-考勤组
     *
     * @author mayike
     * @date  2024/06/05 15:51
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑考勤-考勤组")
    @CommonLog("编辑考勤-考勤组")
//    @SaCheckPermission("/oa/group/edit")
    @PostMapping("/oa/group/edit")
    public CommonResult<String> edit(@RequestBody @Valid GroupEditParam groupEditParam) {
        groupService.edit(groupEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除考勤-考勤组
     *
     * @author mayike
     * @date  2024/06/05 15:51
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除考勤-考勤组")
    @CommonLog("删除考勤-考勤组")
//    @SaCheckPermission("/oa/group/delete")
    @PostMapping("/oa/group/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<GroupIdParam> groupIdParamList) {
        groupService.delete(groupIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取考勤-考勤组详情
     *
     * @author mayike
     * @date  2024/06/05 15:51
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取考勤-考勤组详情")
//    @SaCheckPermission("/oa/group/detail")
    @GetMapping("/oa/group/detail")
    public CommonResult<Group> detail(@Valid GroupIdParam groupIdParam) {
        return CommonResult.data(groupService.detail(groupIdParam));
    }

    @ApiOperationSupport(order = 5)
    @ApiOperation("获取考勤-测试")
    @GetMapping("/oa/group/test")
    public CommonResult<String> test(){
        String childrenUser = webServerClient.getChildrenUser("1666651344023891970");
        System.out.println(childrenUser);
        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 3)
    @ApiOperation("给公司/部门/角色/用户 设置考勤组-考勤")
    @CommonLog("给公司/部门/角色/用户 设置考勤组-考勤")
//    @SaCheckPermission("/oa/group/edit")
    @PostMapping("/oa/group/edit")
    public CommonResult<String> setGroup(@RequestBody @Valid GroupAttObjectParam groupAttObjectParam) {
        groupService.setGroup(groupAttObjectParam);
        return CommonResult.ok();
    }
}
