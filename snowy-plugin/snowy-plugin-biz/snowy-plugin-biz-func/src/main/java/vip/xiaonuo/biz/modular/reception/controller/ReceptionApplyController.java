package vip.xiaonuo.biz.modular.reception.controller;

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
import vip.xiaonuo.biz.modular.reception.entity.ReceptionApply;
import vip.xiaonuo.biz.modular.reception.param.ReceptionApplyAddParam;
import vip.xiaonuo.biz.modular.reception.param.ReceptionApplyEditParam;
import vip.xiaonuo.biz.modular.reception.param.ReceptionApplyIdParam;
import vip.xiaonuo.biz.modular.reception.param.ReceptionApplyPageParam;
import vip.xiaonuo.biz.modular.reception.service.ReceptionApplyService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 接待申请控制器
 *
 * @author pzh
 * @date  2025/05/28 10:15
 */
@Api(tags = "接待申请控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ReceptionApplyController {

    @Resource
    private ReceptionApplyService receptionApplyService;

    /**
     * 获取接待申请分页
     *
     * @author pzh
     * @date  2025/05/28 10:15
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取接待申请分页")
    @SaCheckPermission("/biz/reception/page")
    @GetMapping("/biz/reception/page")
    public CommonResult<Page<ReceptionApply>> page(ReceptionApplyPageParam receptionApplyPageParam) {
        return CommonResult.data(receptionApplyService.page(receptionApplyPageParam));
    }

    /**
     * 添加接待申请
     *
     * @author pzh
     * @date  2025/05/28 10:15
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加接待申请")
    @CommonLog("添加接待申请")
    // @SaCheckPermission("/biz/reception/add")
    @PostMapping("/biz/reception/add")
    public CommonResult<String> add(@RequestBody @Valid ReceptionApplyAddParam receptionApplyAddParam) {
        receptionApplyService.add(receptionApplyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑接待申请
     *
     * @author pzh
     * @date  2025/05/28 10:15
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑接待申请")
    @CommonLog("编辑接待申请")
    // @SaCheckPermission("/biz/reception/edit")
    @PostMapping("/biz/reception/edit")
    public CommonResult<String> edit(@RequestBody @Valid ReceptionApplyEditParam receptionApplyEditParam) {
        receptionApplyService.edit(receptionApplyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除接待申请
     *
     * @author pzh
     * @date  2025/05/28 10:15
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除接待申请")
    @CommonLog("删除接待申请")
    // @SaCheckPermission("/biz/reception/delete")
    @PostMapping("/biz/reception/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ReceptionApplyIdParam> receptionApplyIdParamList) {
        receptionApplyService.delete(receptionApplyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取接待申请详情
     *
     * @author pzh
     * @date  2025/05/28 10:15
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取接待申请详情")
    // @SaCheckPermission("/biz/reception/detail")
    @GetMapping("/biz/reception/detail")
    public CommonResult<ReceptionApply> detail(@Valid ReceptionApplyIdParam receptionApplyIdParam) {
        return CommonResult.data(receptionApplyService.detail(receptionApplyIdParam));
    }
}
