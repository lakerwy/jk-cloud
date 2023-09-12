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
package vip.xiaonuo.pan.modular.userFile.controller;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
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
import vip.xiaonuo.dev.api.DevConfigApi;
import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.pan.modular.file.entity.PanFile;
import vip.xiaonuo.pan.modular.file.service.PanFileService;
import vip.xiaonuo.pan.modular.fileCenter.factory.FileFactory;
import vip.xiaonuo.pan.modular.fileCenter.operation.copy.Copier;
import vip.xiaonuo.pan.modular.fileCenter.operation.copy.domain.CopyFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;
import vip.xiaonuo.pan.modular.userFile.entity.NodeFold;
import vip.xiaonuo.pan.modular.userFile.enums.UserFileEnum;
import vip.xiaonuo.pan.modular.userFile.param.*;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static vip.xiaonuo.pan.core.FileCommonConstant.SNOWY_SYS_DEFAULT_FILE_ENGINE_KEY;

/**
 * 文件主表控制器
 *
 * @author mayike
 * @date  2023/08/28 10:09
 */
@Api(tags = "文件夹控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class UserFileController {

    @Resource
    private UserFileService userFileService;

    @Resource
    private DevConfigApi devConfigApi;

    @Resource
    FileFactory fileFactory;

    @Resource
    PanFileService panFileService;

    private final Integer storageType =1;

    /**
     * 获取文件主表分页
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取文件夹分页")
//    @SaCheckPermission("/pan/fold/page")
    @GetMapping("/pan/fold/page")
    public CommonResult<Page<UserFile>> page(UserFilePageParam userFilePageParam) {
        return CommonResult.data(userFileService.page(userFilePageParam));
    }

    /**
     * 添加文件主表
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加文件夹")
    @CommonLog("添加文件夹")
//    @SaCheckPermission("/pan/fold/add")
    @PostMapping("/pan/fold/add")
    public CommonResult<String> add(@RequestBody @Valid UserFileAddParam userFileAddParam) {
        userFileService.add(userFileAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑文件主表
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑文件夹")
    @CommonLog("编辑文件夹")
//    @SaCheckPermission("/pan/fold/edit")
    @PostMapping("/pan/fold/edit")
    public CommonResult<String> edit(@RequestBody @Valid UserFileEditParam userFileEditParam) {
        userFileService.edit(userFileEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除文件主表
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除文件夹")
    @CommonLog("删除文件夹")
//    @SaCheckPermission("/pan/fold/delete")
    @PostMapping("/pan/fold/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<UserFileIdParam> userFileIdParamList) {
        userFileService.delete(userFileIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取文件主表详情
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取文件夹详情")
//    @SaCheckPermission("/pan/fold/detail")
    @GetMapping("/pan/fold/detail")
    public CommonResult<UserFile> detail(@Valid UserFileIdParam userFileIdParam) {
        return CommonResult.data(userFileService.detail(userFileIdParam));
    }

    /**
     * 本地文件创建文件夹
     *
     **/
    @ApiOperationSupport(order = 6)
    @ApiOperation("本地文件创建文件夹(不允许在根目录下创建文件夹)")
    @CommonLog("本地文件创建文件夹")
    @PostMapping("/pan/fold/createFold")
    public CommonResult<String> createFold(@Valid @RequestBody UserFileAddParam userFileAddParam) {
        //判断文件夹是否存在
        UserFile userFile = BeanUtil.copyProperties(userFileAddParam, UserFile.class);
        Boolean dirExist = userFileService.isDirExist(userFile);
        if(!dirExist){
            return  CommonResult.error("同名文件夹已存在,请修改名称后在操作");
        }
        userFileService.add(userFileAddParam);
        return CommonResult.ok();
    }

    /**
     * 文件夹重命名
     *
     **/
    @ApiOperationSupport(order = 7)
    @ApiOperation("文件夹重命名")
    @CommonLog("文件夹重命名")
    @PostMapping("/pan/fold/reNameFold")
    public CommonResult<String> reNameFold(@Valid @RequestBody UserFileEditParam userFileEditParam) {
        //判断文件夹是否存在
        UserFile userFile = BeanUtil.copyProperties(userFileEditParam, UserFile.class);
        List<UserFile> userFiles =  userFileService.getFolds(userFile);
        if(CollUtil.isNotEmpty(userFiles)){
            //如果修改的名字为自己的原始名称，就直接返回
            if(userFiles.get(0).getId().equals(userFileEditParam.getId())){
                return CommonResult.ok();
            }
            return  CommonResult.error("同名文件夹已存在,请修改名称后在操作");
        }
        userFileService.edit(userFileEditParam);
        return CommonResult.ok();
    }

    /**
     * 文件夹重命名
     *
     **/
    @ApiOperationSupport(order = 7)
    @ApiOperation("文件夹移动")
    @CommonLog("文件夹移动")
    @PostMapping("/pan/fold/moveFold")
    public CommonResult<String> moveFold(@Valid @RequestBody UserFileOperateParam userFileOperateParam) {
        /*
         * 1、个人文件可移动到部门 、 部门文件不能移动到个人 （判断是否可以移动）
         * 2、检查原文件夹下，是否有文件被操作。（如果有文件被操作，是不能移动的） ----文件修改或者预览时都要给文件夹上锁
         * 3、判断目标文件夹是否存在同名文件夹
         */
        userFileService.checkMove(userFileOperateParam);
        userFileService.moveFold(userFileOperateParam);
        return CommonResult.ok();
    }

    /**
     * 文件夹重命名
     *
     **/
    @ApiOperationSupport(order = 7)
    @ApiOperation("文件夹复制")
    @CommonLog("文件夹复制")
    @PostMapping("/pan/fold/copyFold")
    public CommonResult<String> copyFold(@Valid @RequestBody UserFileOperateParam userFileOperateParam) {
        /**
         * 1、复制不受文件夹限制
         * 2、复制文件夹 同时还要复制文件夹下文件，递归处理
         */
        userFileService.checkMove(userFileOperateParam);
        userFileService.userFileCopy(userFileOperateParam);
        return CommonResult.ok();
    }




    //***********************************文件操作**************************************************//
    /**
     * 动态上传文件返回id
     *
     * @author mayike
     * @date 2023/09/08
     **/
    @ApiOperationSupport(order = 7)
    @ApiOperation("动态上传文件返回文件信息")
    @CommonLog("动态上传文件返回文件信息")
    @PostMapping("/pan/file/uploadDynamicReturnBean")
    public CommonResult<List<DevFile>> uploadDynamicReturnId(@RequestPart("file") MultipartFile[] file, @RequestParam(value = "parentId",required = false) String parentId) {
        return CommonResult.data(userFileService.uploadReturnBean(devConfigApi.getValueByKey(SNOWY_SYS_DEFAULT_FILE_ENGINE_KEY), file, parentId));
    }

    /**
     * 获取文件主表分页
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("文件夹列表（个人、部门、分享）")
//    @SaCheckPermission("/pan/fold/page")
    @GetMapping("/pan/fold/pageList")
    public CommonResult<Page<UserFile>> pageList(UserFilePageParam userFilePageParam) {
        return CommonResult.data(userFileService.pageList(userFilePageParam));
    }

    /**
     * 所有个人文件夹列表
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("所有个人文件夹列表")
//    @SaCheckPermission("/pan/fold/page")
    @GetMapping("/pan/fold/selfAllPageList")
    public CommonResult<Page<UserFile>> selfAllPageList(UserFilePageParam userFilePageParam) {
        userFilePageParam.setFoldType(UserFileEnum.SELF_FOLD_TYPE.getLevel());
        return CommonResult.data(userFileService.pageList(userFilePageParam));
    }

    /**
     * 所有群组文件夹列表
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("所有群组文件夹列表")
//    @SaCheckPermission("/pan/fold/page")
    @GetMapping("/pan/fold/groupAllPageList")
    public CommonResult<Page<UserFile>> groupAllPageList(UserFilePageParam userFilePageParam) {
        userFilePageParam.setFoldType(UserFileEnum.GROUP_FOLD_TYPE.getLevel());
        return CommonResult.data(userFileService.pageList(userFilePageParam));
    }

    /**
     * 所有群组文件夹列表
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("获取所见文件夹列表(包含个人和群组)")
//    @SaCheckPermission("/pan/fold/page")
    @GetMapping("/pan/fold/showPageList")
    public CommonResult<Page<UserFile>> showPageList(UserFilePageParam userFilePageParam) {
        //devFoldPageParam.setFoldType(DevFoldEnum.GROUP_FOLD_TYPE.getLevel());
        return CommonResult.data(userFileService.pageList(userFilePageParam));
    }

    /**
     * 获取所在部门的文件夹列表
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("获取所在部门的文件夹列表")
//    @SaCheckPermission("/pan/fold/page")
    @GetMapping("/pan/fold/groupPageList")
    public CommonResult<Page<UserFile>> groupPageList(UserFilePageParam userFilePageParam) {
        //devFoldPageParam.setFoldType(DevFoldEnum.GROUP_FOLD_TYPE.getLevel());
        return CommonResult.data(userFileService.groupPageList(userFilePageParam));
    }

    /**
     * 获取所在部门的文件夹列表
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("获取当前登录人的个人文件夹列表")
//    @SaCheckPermission("/pan/fold/page")
    @GetMapping("/pan/fold/selfPageList")
    public CommonResult<Page<UserFile>> selfPageList(UserFilePageParam userFilePageParam) {
        //devFoldPageParam.setFoldType(DevFoldEnum.GROUP_FOLD_TYPE.getLevel());
        return CommonResult.data(userFileService.selfPageList(userFilePageParam));
    }

    /**
     * 获取所在部门的文件夹列表
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("根据id获取文件树")
//    @SaCheckPermission("/pan/fold/page")
    @GetMapping("/pan/fold/getFoldTree")
    public CommonResult<List<NodeFold>> getFoldTree(@RequestParam(value = "id",required = true) String id) {
        //devFoldPageParam.setFoldType(DevFoldEnum.GROUP_FOLD_TYPE.getLevel());
        return CommonResult.data(userFileService.buildTree(id));
    }

    //***********************************文件操作**************************************************//

    /**
     * 创建文件
     *
     **/
    @ApiOperationSupport(order = 14)
    @ApiOperation("创建文件")
    @CommonLog("创建文件")
    @PostMapping("/pan/fold/createFile")
    public CommonResult<String> createFile(@Valid @RequestBody UserFileAddParam userFileAddParam) {
        //判断文件夹是否存在
        UserFile userFile = BeanUtil.copyProperties(userFileAddParam, UserFile.class);
        Boolean dirExist = userFileService.isDirExist(userFile);
        if(!dirExist){
            return  CommonResult.error("同名文件已存在,请修改名称后在操作");
        }
        userFileService.createFile(userFileAddParam);
        return CommonResult.ok();
    }

}
