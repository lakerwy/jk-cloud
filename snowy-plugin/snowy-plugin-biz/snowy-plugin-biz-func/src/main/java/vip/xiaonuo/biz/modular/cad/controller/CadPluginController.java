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
import vip.xiaonuo.biz.modular.cad.entity.CadFileHistory;
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
    private CadProjectService cadProjectService;
    @Resource
    private CadFileService cadFileService;
    @Resource
    private CadFileHistoryService cadFileHistoryService;
    @Resource
    private CadProductService cadProductService;
    @Resource
    private CadProofreadService cadProofreadService;
    @Resource
    private CadTagService cadTagService;



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
     * 【CAD-插件端】【设计区】根据项目Id,当前用户Id,获取当前项目设计区树状结构
     *
     * @author yong.ye
     * @date  2023/12/01 10:40
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("【CAD-插件端】【设计区】根据项目Id,当前用户Id,获取当前项目设计区树状结构")
    @GetMapping("/biz/cad/plugin/prjDesignTree")
    public CommonResult<List<Tree<String>>> prjDesignTree(MyFileTreeParam myFileTreeParam) {
        return CommonResult.data(cadProjectService.prjDesignTree(myFileTreeParam));
    }

    /**
     * 【CAD-插件端】CAD-文件|文件夹|批注 重命名
     *
     * @author yong.ye
     * @date  2023/12/08 17:40
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("【CAD-插件端】CAD-文件|文件夹|批注 重命名")
    @CommonLog("【CAD-插件端】CAD-文件|文件夹|批注 重命名")
    @PostMapping("/biz/cad/plugin/reName")
    public CommonResult<String> reName(@RequestBody @Valid CadFileReName cadFileReName) {
        cadFileService.reName(cadFileReName);
        return CommonResult.ok();
    }

    /**
     * 【CAD-插件端】CAD- 新建文件|新建文件夹|新建批注
     *
     * @author yong.ye
     * @date  2023/12/08 17:40
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("【CAD-插件端】CAD- 新建文件|新建文件夹|新建批注 ")
    @CommonLog("【CAD-插件端】CAD- 新建文件|新建文件夹|新建批注 ")
    @PostMapping("/biz/cad/plugin/createFile")
    public CommonResult<String> createFile(@RequestBody @Valid CadFileAddParam cadFileAddParam) {
        cadFileService.add(cadFileAddParam);
        return CommonResult.ok();
    }

    /**
     * 【CAD-插件端】CAD- 提交（新增文件历史记录）
     *
     * @author yong.ye
     * @date  2023/12/11 17:40
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("【CAD-插件端】CAD- 添加文件历史记录 ")
    @CommonLog("【CAD-插件端】CAD- 添加文件历史记录 ")
    @PostMapping("/biz/cad/plugin/addFileHis")
    public CommonResult<String> addFileHis(@RequestBody @Valid CadFileHistoryAddParam cadFileHistoryAddParam) {
        cadFileHistoryService.add(cadFileHistoryAddParam);
        return CommonResult.ok();
    }

    /**
     * 【CAD-插件端】根据文件树FileId,获取当前文件的最新版本
     *
     * @author yong.ye
     * @date  2023/12/11 10:40
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("【CAD-插件端】根据文件树FileId,获取当前文件的最新版本")
    @GetMapping("/biz/cad/plugin/lastHisByFileId")
    public CommonResult<CadFileHistory> lastHisByFileId(String fileId) {
        return CommonResult.data(cadFileHistoryService.lastHisByFileId(fileId));
    }

    /**
     * 【CAD-插件端】根据文件树FileId,获取文件历史版本列表
     *
     * @author yong.ye
     * @date  2023/12/11 10:40
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("【CAD-插件端】根据文件树FileId,获取文件历史版本列表")
    @GetMapping("/biz/cad/plugin/listHisByFileId")
    public CommonResult<List<CadFileHistory>> listHisByFileId(String fileId) {
        return CommonResult.data(cadFileHistoryService.listHisByFileId(fileId));
    }

    /**
     * 【CAD-插件端】CAD- 归档
     *
     * 1. 上传文件 （插件端处理）
     * 1.1 并新增一条文件历史记录              -- 接口 处理
     * 2. 创建存放路径（根据设计区文件路径生成）  -- 接口 处理
     * 3. 添加产品成果记录                    -- 接口 处理
     *
     * @author yong.ye
     * @date  2023/12/11 17:40
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("【CAD-插件端】CAD- 归档 ")
    @CommonLog("【CAD-插件端】CAD- 归档 ")
    @PostMapping("/biz/cad/plugin/filing")
    public CommonResult<String> filing(@RequestBody @Valid FilingParam filingParam) {
        cadProductService.filing(filingParam);
        return CommonResult.ok();
    }

    /**
     * 【CAD-插件端】CAD- 发起提资
     *
     * 1. 上传文件 （插件端处理）
     * 1.1 并新增一条文件历史记录              -- 接口 处理
     * 2. 创建存放路径（根据设计区文件路径生成）  -- 接口 处理
     * 3. 添加审批记录（提资给本专业负责人）     -- 接口 处理
     *
     * @author yong.ye
     * @date  2023/12/11 17:40
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("【CAD-插件端】CAD- 发起提资 ")
    @CommonLog("【CAD-插件端】CAD- 发起提资 ")
    @PostMapping("/biz/cad/plugin/supply")
    public CommonResult<String> supply(@RequestBody @Valid StartFlwParam startFlwParam) {
        cadProofreadService.supply(startFlwParam);
        return CommonResult.ok();
    }


    /**
     * 【CAD-插件端】CAD- 发起校审
     *
     * 1. 上传文件 （插件端处理）
     * 1.1 并新增一条文件历史记录              -- 接口 处理
     * 2. 创建存放路径（根据设计区文件路径生成）  -- 接口 处理
     * 3. 添加审批记录（提资给本专业负责人）     -- 接口 处理
     *
     * @author yong.ye
     * @date  2023/12/11 17:40
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("【CAD-插件端】CAD- 发起校审 ")
    @CommonLog("【CAD-插件端】CAD- 发起校审 ")
    @PostMapping("/biz/cad/plugin/proofread")
    public CommonResult<String> proofread(@RequestBody @Valid StartFlwParam startFlwParam) {
        cadProofreadService.proofread(startFlwParam);
        return CommonResult.ok();
    }


    /**
     * 【CAD-插件端】CAD- 审批【通过|退回】
     *
     * 1. 更改审批记录
     * 2. 若审核均通过，自动移动到提资区|成品区
     *
     * @author yong.ye
     * @date  2023/12/11 17:40
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("【CAD-插件端】CAD- 审批【通过|退回】 ")
    @CommonLog("【CAD-插件端】CAD- 审批【通过|退回】 ")
    @PostMapping("/biz/cad/plugin/audit")
    public CommonResult<String> audit(@RequestBody @Valid AuditParam auditParam) {
        cadProofreadService.audit(auditParam);
        return CommonResult.ok();
    }


    /**
     * 【CAD-插件端】【提资区】根据项目Id,当前用户Id,获取当前项目提资文件树状结构
     *
     * @author yong.ye
     * @date  2023/12/01 10:40
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("【CAD-插件端】【提资区】根据项目Id,当前用户Id,获取当前项目提资文件树状结构")
    @GetMapping("/biz/cad/plugin/prjSupplyTree")
    public CommonResult<List<Tree<String>>> prjSupplyTree(MyFileTreeParam myFileTreeParam) {
        return CommonResult.data(cadProjectService.prjSupplyTree(myFileTreeParam));
    }

    /**
     * 【CAD-插件端】【成品区】根据项目Id,当前用户Id,获取当前项目成品文件树状结构
     *
     * @author yong.ye
     * @date  2023/12/01 10:40
     */
    @ApiOperationSupport(order = 15)
    @ApiOperation("【CAD-插件端】【成品区】根据项目Id,当前用户Id,获取当前项目成品文件树状结构")
    @GetMapping("/biz/cad/plugin/prjProductTree")
    public CommonResult<List<Tree<String>>> prjProductTree(MyFileTreeParam myFileTreeParam) {
        return CommonResult.data(cadProjectService.prjProductTree(myFileTreeParam));
    }


    /**
     * 【CAD-插件端】【校审区】根据项目Id,当前用户Id,获取当前项目当前用户校审区数据
     *
     * @author yong.ye
     * @date  2023/12/01 10:40
     */
    @ApiOperationSupport(order = 16)
    @ApiOperation("【CAD-插件端】【校审区】根据项目Id,当前用户Id,获取当前项目当前用户校审区数据")
    @GetMapping("/biz/cad/plugin/prjAuditInfo")
    public CommonResult<CadAuditInfo> prjAuditInfo(MyFileTreeParam myFileTreeParam) {
        return CommonResult.data(cadProjectService.prjAuditInfo(myFileTreeParam));
    }

    /**
     * 【CAD-插件端】CAD- 更新批注状态
     *
     * @author yong.ye
     * @date  2023/12/11 17:40
     */
    @ApiOperationSupport(order = 17)
    @ApiOperation("【CAD-插件端】CAD- 更新批注状态 ")
    @CommonLog("【CAD-插件端】CAD- 更新批注状态 ")
    @PostMapping("/biz/cad/plugin/updateTagStatus")
    public CommonResult<String> updateTagStatus(@RequestBody @Valid UpdateTagStatusParam updateTagStatusParam) {
        cadFileService.updateTagStatus(updateTagStatusParam);
        return CommonResult.ok();
    }

    /**
     * 【CAD-插件端】CAD- 增加批注沟通记录
     *
     * @author yong.ye
     * @date  2023/12/11 17:40
     */
    @ApiOperationSupport(order = 18)
    @ApiOperation("【CAD-插件端】CAD- 增加批注沟通记录 ")
    @CommonLog("【CAD-插件端】CAD- 增加批注沟通记录 ")
    @PostMapping("/biz/cad/plugin/addTag")
    public CommonResult<String> addTag(@RequestBody @Valid CadTagAddParam cadTagAddParam) {
        cadTagService.add(cadTagAddParam);
        return CommonResult.ok();
    }

    /**
     * 【CAD-插件端】根据批注Id,获取聊天记录列表
     *
     * @author yong.ye
     * @date  2023/12/01 10:40
     */
    @ApiOperationSupport(order = 19)
    @ApiOperation("【CAD-插件端】根据批注Id,获取聊天记录列表")
    @GetMapping("/biz/cad/plugin/tagListByFileId")
    public CommonResult<List<CadTagVo>> tagListByFileId(String fileId) {
        return CommonResult.data(cadTagService.tagListByFileId(fileId));
    }


}
