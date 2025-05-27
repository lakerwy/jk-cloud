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
package vip.xiaonuo.flw.modular.flwbiz.project.controller;

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
import vip.xiaonuo.flw.modular.flwbiz.project.entity.BizProject;
import vip.xiaonuo.flw.modular.flwbiz.project.param.*;
import vip.xiaonuo.flw.modular.flwbiz.project.service.BizProjectService;
import vip.xiaonuo.flw.modular.flwbiz.project.vo.BizProjectLikeVo;
import vip.xiaonuo.flw.modular.flwbiz.project.vo.BizProjectVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 业务表-立项申请控制器
 *
 * @author mayike
 * @date  2023/06/20 14:26
 */
@Api(tags = "业务表-立项申请控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizProjectController {

    @Resource
    private BizProjectService bizProjectService;

    /**
     * 获取业务表-立项申请分页
     *
     * @author mayike
     * @date  2023/06/20 14:26
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-立项申请分页")
    @SaCheckPermission("/flw/project/page")
    @GetMapping("/flw/project/page")
    public CommonResult<Page<BizProject>> page(BizProjectPageParam bizProjectPageParam) {
        return CommonResult.data(bizProjectService.page(bizProjectPageParam));
    }

    /**
     * 添加业务表-立项申请
     *
     * @author mayike
     * @date  2023/06/20 14:26
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-立项申请")
    @CommonLog("添加业务表-立项申请")
//    @SaCheckPermission("/flw/project/add")
    @PostMapping("/flw/project/add")
    public CommonResult<String> add(@RequestBody @Valid BizProjectAddParam bizProjectAddParam) {
        bizProjectService.add(bizProjectAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-立项申请
     *
     * @author mayike
     * @date  2023/06/20 14:26
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-立项申请")
    @CommonLog("编辑业务表-立项申请")
    @SaCheckPermission("/flw/project/edit")
    @PostMapping("/flw/project/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizProjectEditParam bizProjectEditParam) {
        bizProjectService.edit(bizProjectEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-立项申请
     *
     * @author mayike
     * @date  2023/06/20 14:26
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-立项申请")
    @CommonLog("删除业务表-立项申请")
    @SaCheckPermission("/flw/project/delete")
    @PostMapping("/flw/project/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizProjectIdParam> bizProjectIdParamList) {
        bizProjectService.delete(bizProjectIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-立项申请详情
     *
     * @author mayike
     * @date  2023/06/20 14:26
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-立项申请详情")
//    @SaCheckPermission("/flw/project/detail")
    @GetMapping("/flw/project/detail")
    public CommonResult<BizProject> detail(@Valid BizProjectIdParam bizProjectIdParam) {
        return CommonResult.data(bizProjectService.detail(bizProjectIdParam));
    }


    /**
     * 立项申请，重名校验（去掉空格，特殊字符，大小写）
     */
    @ApiOperationSupport(order = 100)
    @ApiOperation("立项申请，重名校验（去掉空格，特殊字符，大小写）")
    @GetMapping("/flw/project/checkName")
    public CommonResult<Boolean> checkName(@Valid CheckNameParam checkNameParam) {
        return CommonResult.data(bizProjectService.checkName(checkNameParam));
    }



    /**
     * 立项申请，相似性校验（去掉空格，特殊字符，大小写），取70%，模糊匹配
     */
    @ApiOperationSupport(order = 100)
    @ApiOperation("立项申请，相似性校验（去掉空格，特殊字符，大小写），取70%，模糊匹配")
    @GetMapping("/flw/project/checkNameLike")
    public CommonResult<List<BizProjectLikeVo>> checkNameLike(@Valid CheckNameParam checkNameParam) {
        return CommonResult.data(bizProjectService.checkName2(checkNameParam));
    }




    /**
     * 获取业务表-立项申请分页 【epcMp中台】
     *
     * @author mayike
     * @date  2023/06/20 14:26
     */
    @ApiOperationSupport(order = 100)
    @ApiOperation("获取业务表-立项申请分页 【epcMp中台】")
    @GetMapping("/openApi/epcMp/projectPage")
    public CommonResult<Page<BizProjectVo>> projectPage(BizProjectPageParam bizProjectPageParam) {
        return CommonResult.data(bizProjectService.epcMpPage(bizProjectPageParam));
    }




    /**
     * 获取业务表-立项申请分页(无数据权限控制)
     *
     * @author mayike
     * @date  2023/06/20 14:26
     */
    @ApiOperationSupport(order = 100)
    @ApiOperation("获取业务表-立项申请分页(无数据权限控制)")
    @GetMapping("/flw/project/pageList")
    public CommonResult<Page<BizProject>> pageList(BizProjectPageParam bizProjectPageParam) {
        return CommonResult.data(bizProjectService.pageList(bizProjectPageParam));
    }
}
