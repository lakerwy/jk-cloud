package vip.xiaonuo.biz.modular.prj.por.controller;

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
import vip.xiaonuo.biz.modular.prj.por.param.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.por.entity.PorMember;
import vip.xiaonuo.biz.modular.prj.por.service.PorMemberService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目成员管理控制器
 *
 * @author yy
 * @date  2025/06/03 09:40
 */
@Api(tags = "项目产值台账 - 项目管理 - 项目成员管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PorMemberController {

    @Resource
    private PorMemberService porMemberService;

    /**
     * 获取项目产值台账 - 项目管理 - 项目成员管理分页
     *
     * @author yy
     * @date  2025/06/03 09:40
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目产值台账 - 项目管理 - 项目成员管理分页")
//    @SaCheckPermission("/biz/prj/por/member/page")
    @GetMapping("/biz/prj/por/member/page")
    public CommonResult<Page<PorMember>> page(PorMemberPageParam porMemberPageParam) {
        return CommonResult.data(porMemberService.page(porMemberPageParam));
    }

    /**
     * 添加项目产值台账 - 项目管理 - 项目成员管理
     *
     * @author yy
     * @date  2025/06/03 09:40
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目产值台账 - 项目管理 - 项目成员管理")
    @CommonLog("添加项目产值台账 - 项目管理 - 项目成员管理")
//    @SaCheckPermission("/biz/prj/por/member/add")
    @PostMapping("/biz/prj/por/member/add")
    public CommonResult<String> add(@RequestBody @Valid PorMemberAddParam porMemberAddParam) {
        porMemberService.add(porMemberAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目产值台账 - 项目管理 - 项目成员管理
     *
     * @author yy
     * @date  2025/06/03 09:40
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目产值台账 - 项目管理 - 项目成员管理")
    @CommonLog("编辑项目产值台账 - 项目管理 - 项目成员管理")
//    @SaCheckPermission("/biz/prj/por/member/edit")
    @PostMapping("/biz/prj/por/member/edit")
    public CommonResult<String> edit(@RequestBody @Valid PorMemberEditParam porMemberEditParam) {
        porMemberService.edit(porMemberEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目产值台账 - 项目管理 - 项目成员管理
     *
     * @author yy
     * @date  2025/06/03 09:40
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目产值台账 - 项目管理 - 项目成员管理")
    @CommonLog("删除项目产值台账 - 项目管理 - 项目成员管理")
//    @SaCheckPermission("/biz/prj/por/member/delete")
    @PostMapping("/biz/prj/por/member/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PorMemberIdParam> porMemberIdParamList) {
        porMemberService.delete(porMemberIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目产值台账 - 项目管理 - 项目成员管理详情
     *
     * @author yy
     * @date  2025/06/03 09:40
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目产值台账 - 项目管理 - 项目成员管理详情")
//    @SaCheckPermission("/biz/prj/por/member/detail")
    @GetMapping("/biz/prj/por/member/detail")
    public CommonResult<PorMember> detail(@Valid PorMemberIdParam porMemberIdParam) {
        return CommonResult.data(porMemberService.detail(porMemberIdParam));
    }



    /**
     * 获取项目产值台账 - 项目管理 - 项目成员（根据项目id，获取项目成员列表）
     *
     * @author yy
     * @date  2025/06/03 09:40
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("获取项目产值台账 - 项目管理 - 项目成员（根据项目id，获取项目成员列表）")
    @GetMapping("/biz/prj/por/member/getListById")
    public CommonResult<List<PorMember>> getListById(@Valid PorPrjParam param) {
        return CommonResult.data(porMemberService.getListById(param));
    }
}
