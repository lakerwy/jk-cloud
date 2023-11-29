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
import vip.xiaonuo.biz.modular.cad.entity.*;
import vip.xiaonuo.biz.modular.cad.param.*;
import vip.xiaonuo.biz.modular.cad.service.*;
import vip.xiaonuo.biz.modular.cad.vo.CadProjectVo;
import vip.xiaonuo.biz.modular.cad.vo.CadUserVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * CAD-Web端控制器-web端接口集合
 *
 * @author yong.ye
 * @date  2023/11/29 10:35
 */
@Api(tags = "CAD-Web端控制器-web端接口集合")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class CadWebController {

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

    /**
     * 获取CAD-专业表分页
     *
     * @author yong.ye
     * @date  2023/11/29 10:35
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取CAD-专业表分页")
//    @SaCheckPermission("/biz/cad/web/profession/page")
    @GetMapping("/biz/cad/web/profession/page")
    public CommonResult<Page<CadProfession>> pageProfession(CadProfessionPageParam cadProfessionPageParam) {
        return CommonResult.data(cadProfessionService.page(cadProfessionPageParam));
    }

    /**
     * 添加CAD-专业表
     *
     * @author yong.ye
     * @date  2023/11/29 10:35
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加CAD-专业表")
    @CommonLog("添加CAD-专业表")
//    @SaCheckPermission("/biz/cad/web/profession/add")
    @PostMapping("/biz/cad/web/profession/add")
    public CommonResult<String> addProfession(@RequestBody @Valid CadProfessionAddParam cadProfessionAddParam) {
        cadProfessionService.add(cadProfessionAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑CAD-专业表
     *
     * @author yong.ye
     * @date  2023/11/29 10:35
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑CAD-专业表")
    @CommonLog("编辑CAD-专业表")
//    @SaCheckPermission("/biz/cad/web/profession/edit")
    @PostMapping("/biz/cad/web/profession/edit")
    public CommonResult<String> editProfession(@RequestBody @Valid CadProfessionEditParam cadProfessionEditParam) {
        cadProfessionService.edit(cadProfessionEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除CAD-专业表
     *
     * @author yong.ye
     * @date  2023/11/29 10:35
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除CAD-专业表")
    @CommonLog("删除CAD-专业表")
//    @SaCheckPermission("/biz/cad/web/profession/delete")
    @PostMapping("/biz/cad/web/profession/delete")
    public CommonResult<String> deleteProfession(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<CadProfessionIdParam> cadProfessionIdParamList) {
        cadProfessionService.delete(cadProfessionIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取CAD-专业表详情
     *
     * @author yong.ye
     * @date  2023/11/29 10:35
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取CAD-专业表详情")
//    @SaCheckPermission("/biz/cad/web/profession/detail")
    @GetMapping("/biz/cad/web/profession/detail")
    public CommonResult<CadProfession> detailProfession(@Valid CadProfessionIdParam cadProfessionIdParam) {
        return CommonResult.data(cadProfessionService.detail(cadProfessionIdParam));
    }

    /**
     * 获取全部cad专业集合
     *
     * @author yong.ye
     * @date  2023/11/29 10:35
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取全部cad专业集合")
//    @SaCheckPermission("/biz/cad/web/profession/all")
    @GetMapping("/biz/cad/web/profession/all")
    public CommonResult<List<CadProfession>> allProfession() {
        return CommonResult.data(cadProfessionService.list());
    }


    /**
     * 获取CAD-用户表分页
     *
     * @author yong.ye
     * @date  2023/11/29 10:40
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("获取CAD-用户表分页")
//    @SaCheckPermission("/biz/cad/web/user/page")
    @GetMapping("/biz/cad/web/user/page")
    public CommonResult<Page<CadUserVo>> pageUser(CadUserPageParam cadUserPageParam) {
        return CommonResult.data(cadUserService.page(cadUserPageParam));
    }

    /**
     * 添加CAD-用户表
     *
     * @author yong.ye
     * @date  2023/11/29 10:40
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("添加CAD-用户表")
    @CommonLog("添加CAD-用户表")
//    @SaCheckPermission("/biz/cad/web/user/add")
    @PostMapping("/biz/cad/web/user/add")
    public CommonResult<String> addUser(@RequestBody @Valid CadUserAddParam cadUserAddParam) {
        cadUserService.add(cadUserAddParam);
        return CommonResult.ok();
    }

    /**
     * 添加CAD-用户表【批量添加】
     *
     * @author yong.ye
     * @date  2023/11/29 10:40
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("添加CAD-用户表【支持批量添加】")
    @CommonLog("添加CAD-用户表【支持批量添加】")
//    @SaCheckPermission("/biz/cad/web/user/addBatch")
    @PostMapping("/biz/cad/web/user/addBatch")
    public CommonResult<String> addBatchUser(@RequestBody @Valid CadUserBatchAddParam cadUserBatchAddParam) {
        cadUserService.addBatchUser(cadUserBatchAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑CAD-用户表
     *
     * @author yong.ye
     * @date  2023/11/29 10:40
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("编辑CAD-用户表")
    @CommonLog("编辑CAD-用户表")
//    @SaCheckPermission("/biz/cad/web/user/edit")
    @PostMapping("/biz/cad/web/user/edit")
    public CommonResult<String> editUser(@RequestBody @Valid CadUserEditParam cadUserEditParam) {
        cadUserService.edit(cadUserEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除CAD-用户表
     *
     * @author yong.ye
     * @date  2023/11/29 10:40
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("删除CAD-用户表")
    @CommonLog("删除CAD-用户表")
//    @SaCheckPermission("/biz/cad/web/user/delete")
    @PostMapping("/biz/cad/web/user/delete")
    public CommonResult<String> deleteUser(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<CadUserIdParam> cadUserIdParamList) {
        cadUserService.delete(cadUserIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取CAD-用户表详情
     *
     * @author yong.ye
     * @date  2023/11/29 10:40
     */
    @ApiOperationSupport(order = 15)
    @ApiOperation("获取CAD-用户表详情")
//    @SaCheckPermission("/biz/cad/web/user/detail")
    @GetMapping("/biz/cad/web/user/detail")
    public CommonResult<CadUserVo> detailUser(@Valid CadUserIdParam cadUserIdParam) {
        return CommonResult.data(cadUserService.detail(cadUserIdParam));
    }

    /**
     * 设置 CAD-用户表 所属专业【支持批量处理】
     *
     * @author yong.ye
     * @date  2023/11/29 10:40
     */
    @ApiOperationSupport(order = 16)
    @ApiOperation("设置 CAD-用户表 所属专业【支持批量处理】")
    @CommonLog("设置 CAD-用户表 所属专业【支持批量处理】")
//    @SaCheckPermission("/biz/cad/web/user/setProfession")
    @PostMapping("/biz/cad/web/user/setProfession")
    public CommonResult<String> setProfession(@RequestBody @Valid CadUserProfessionParam cadUserProfessionParam) {
        cadUserService.setProfession(cadUserProfessionParam);
        return CommonResult.ok();
    }

    /**
     * 根据专业获取该专业的用户集合
     *
     * @author yong.ye
     * @date  2023/11/29 10:40
     */
    @ApiOperationSupport(order = 17)
    @ApiOperation("根据专业获取该专业的用户集合")
//    @SaCheckPermission("/biz/cad/web/user/userByProfession")
    @GetMapping("/biz/cad/web/user/userByProfession")
    public CommonResult<List<CadUser>> userByProfession(String professionId) {
        return CommonResult.data(cadUserService.userByProfession(professionId));
    }


    /**
     * 获取CAD项目阶段分页
     *
     * @author yong.ye
     * @date  2023/11/29 10:38
     */
    @ApiOperationSupport(order = 21)
    @ApiOperation("获取CAD项目阶段分页")
//    @SaCheckPermission("/biz/cad/web/stage/page")
    @GetMapping("/biz/cad/web/stage/page")
    public CommonResult<Page<CadStage>> pageStage(CadStagePageParam cadStagePageParam) {
        return CommonResult.data(cadStageService.page(cadStagePageParam));
    }

    /**
     * 添加CAD项目阶段
     *
     * @author yong.ye
     * @date  2023/11/29 10:38
     */
    @ApiOperationSupport(order = 22)
    @ApiOperation("添加CAD项目阶段")
    @CommonLog("添加CAD项目阶段")
//    @SaCheckPermission("/biz/cad/web/stage/add")
    @PostMapping("/biz/cad/web/stage/add")
    public CommonResult<String> addStage(@RequestBody @Valid CadStageAddParam cadStageAddParam) {
        cadStageService.add(cadStageAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑CAD项目阶段
     *
     * @author yong.ye
     * @date  2023/11/29 10:38
     */
    @ApiOperationSupport(order = 23)
    @ApiOperation("编辑CAD项目阶段")
    @CommonLog("编辑CAD项目阶段")
//    @SaCheckPermission("/biz/cad/web/stage/edit")
    @PostMapping("/biz/cad/web/stage/edit")
    public CommonResult<String> editStage(@RequestBody @Valid CadStageEditParam cadStageEditParam) {
        cadStageService.edit(cadStageEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除CAD项目阶段
     *
     * @author yong.ye
     * @date  2023/11/29 10:38
     */
    @ApiOperationSupport(order = 24)
    @ApiOperation("删除CAD项目阶段")
    @CommonLog("删除CAD项目阶段")
//    @SaCheckPermission("/biz/cad/web/stage/delete")
    @PostMapping("/biz/cad/web/stage/delete")
    public CommonResult<String> deleteStage(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<CadStageIdParam> cadStageIdParamList) {
        cadStageService.delete(cadStageIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取CAD项目阶段详情
     *
     * @author yong.ye
     * @date  2023/11/29 10:38
     */
    @ApiOperationSupport(order = 25)
    @ApiOperation("获取CAD项目阶段详情")
//    @SaCheckPermission("/biz/cad/web/stage/detail")
    @GetMapping("/biz/cad/web/stage/detail")
    public CommonResult<CadStage> detailStage(@Valid CadStageIdParam cadStageIdParam) {
        return CommonResult.data(cadStageService.detail(cadStageIdParam));
    }

    /**
     * 获取CAD所有项目阶段集合
     *
     * @author yong.ye
     * @date  2023/11/29 10:38
     */
    @ApiOperationSupport(order = 26)
    @ApiOperation("获取CAD所有项目阶段集合")
//    @SaCheckPermission("/biz/cad/web/stage/all")
    @GetMapping("/biz/cad/web/stage/all")
    public CommonResult<List<CadStage>> allStage() {
        return CommonResult.data(cadStageService.list());
    }



    /**
     * 获取CAD-项目表分页
     *
     * @author yong.ye
     * @date  2023/11/29 10:36
     */
    @ApiOperationSupport(order = 31)
    @ApiOperation("获取CAD-项目表分页")
//    @SaCheckPermission("/biz/cad/web/project/page")
    @GetMapping("/biz/cad/web/project/page")
    public CommonResult<Page<CadProjectVo>> pageProject(CadProjectPageParam cadProjectPageParam) {
        return CommonResult.data(cadProjectService.page(cadProjectPageParam));
    }

    /**
     * 添加CAD-项目表
     *
     * @author yong.ye
     * @date  2023/11/29 10:36
     */
    @ApiOperationSupport(order = 32)
    @ApiOperation("添加CAD-项目表")
    @CommonLog("添加CAD-项目表")
//    @SaCheckPermission("/biz/cad/web/project/add")
    @PostMapping("/biz/cad/web/project/add")
    public CommonResult<String> addProject(@RequestBody @Valid CadProjectAddParam cadProjectAddParam) {
        cadProjectService.add(cadProjectAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑CAD-项目表
     *
     * @author yong.ye
     * @date  2023/11/29 10:36
     */
    @ApiOperationSupport(order = 33)
    @ApiOperation("编辑CAD-项目表")
    @CommonLog("编辑CAD-项目表")
//    @SaCheckPermission("/biz/cad/web/project/edit")
    @PostMapping("/biz/cad/web/project/edit")
    public CommonResult<String> editProject(@RequestBody @Valid CadProjectEditParam cadProjectEditParam) {
        cadProjectService.edit(cadProjectEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除CAD-项目表
     *
     * @author yong.ye
     * @date  2023/11/29 10:36
     */
    @ApiOperationSupport(order = 34)
    @ApiOperation("删除CAD-项目表")
    @CommonLog("删除CAD-项目表")
//    @SaCheckPermission("/biz/cad/web/project/delete")
    @PostMapping("/biz/cad/web/project/delete")
    public CommonResult<String> deleteProject(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<CadProjectIdParam> cadProjectIdParamList) {
        cadProjectService.delete(cadProjectIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取CAD-项目表详情
     *
     * @author yong.ye
     * @date  2023/11/29 10:36
     */
    @ApiOperationSupport(order = 35)
    @ApiOperation("获取CAD-项目表详情")
//    @SaCheckPermission("/biz/cad/web/project/detail")
    @GetMapping("/biz/cad/web/project/detail")
    public CommonResult<CadProject> detailProject(@Valid CadProjectIdParam cadProjectIdParam) {
        return CommonResult.data(cadProjectService.detail(cadProjectIdParam));
    }


    /**
     * 获取CAD-项目子项表分页
     *
     * @author yong.ye
     * @date  2023/11/29 10:39
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取CAD-项目子项表分页")
//    @SaCheckPermission("/biz/cad/web/subItem/page")
    @GetMapping("/biz/cad/web/subItem/page")
    public CommonResult<Page<CadSubItem>> pageSubItem(CadSubItemPageParam cadSubItemPageParam) {
        return CommonResult.data(cadSubItemService.page(cadSubItemPageParam));
    }

    /**
     * 添加CAD-项目子项表
     *
     * @author yong.ye
     * @date  2023/11/29 10:39
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加CAD-项目子项表")
    @CommonLog("添加CAD-项目子项表")
//    @SaCheckPermission("/biz/cad/web/subItem/add")
    @PostMapping("/biz/cad/web/subItem/add")
    public CommonResult<String> addSubItem(@RequestBody @Valid CadSubItemAddParam cadSubItemAddParam) {
        cadSubItemService.add(cadSubItemAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑CAD-项目子项表
     *
     * @author yong.ye
     * @date  2023/11/29 10:39
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑CAD-项目子项表")
    @CommonLog("编辑CAD-项目子项表")
//    @SaCheckPermission("/biz/cad/web/subItem/edit")
    @PostMapping("/biz/cad/web/subItem/edit")
    public CommonResult<String> editSubItem(@RequestBody @Valid CadSubItemEditParam cadSubItemEditParam) {
        cadSubItemService.edit(cadSubItemEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除CAD-项目子项表
     *
     * @author yong.ye
     * @date  2023/11/29 10:39
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除CAD-项目子项表")
    @CommonLog("删除CAD-项目子项表")
//    @SaCheckPermission("/biz/cad/web/subItem/delete")
    @PostMapping("/biz/cad/web/subItem/delete")
    public CommonResult<String> deleteSubItem(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<CadSubItemIdParam> cadSubItemIdParamList) {
        cadSubItemService.delete(cadSubItemIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取CAD-项目子项表详情
     *
     * @author yong.ye
     * @date  2023/11/29 10:39
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取CAD-项目子项表详情")
//    @SaCheckPermission("/biz/cad/web/subItem/detail")
    @GetMapping("/biz/cad/web/subItem/detail")
    public CommonResult<CadSubItem> detailSubItem(@Valid CadSubItemIdParam cadSubItemIdParam) {
        return CommonResult.data(cadSubItemService.detail(cadSubItemIdParam));
    }
}
