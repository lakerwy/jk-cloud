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
import vip.xiaonuo.biz.modular.prj.por.vo.PorOwnerSettleVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.por.entity.PorOwnerSettle;
import vip.xiaonuo.biz.modular.prj.por.param.PorOwnerSettleAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorOwnerSettleEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorOwnerSettleIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorOwnerSettlePageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorOwnerSettleService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 项目产值台账 - 项目管理 - 项目业主结算记录控制器
 *
 * @author yy
 * @date  2025/06/03 09:52
 */
@Api(tags = "项目产值台账 - 项目管理 - 项目业主结算记录控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PorOwnerSettleController {

    @Resource
    private PorOwnerSettleService porOwnerSettleService;

    /**
     * 获取项目产值台账 - 项目管理 - 项目业主结算记录分页
     *
     * @author yy
     * @date  2025/06/03 09:52
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目产值台账 - 项目管理 - 项目业主结算记录分页")
//    @SaCheckPermission("/biz/prj/por/owner/settle/page")
    @GetMapping("/biz/prj/por/owner/settle/page")
    public CommonResult<Page<PorOwnerSettleVo>> page(PorOwnerSettlePageParam porOwnerSettlePageParam) {
        return CommonResult.data(porOwnerSettleService.page(porOwnerSettlePageParam));
    }

    /**
     * 添加项目产值台账 - 项目管理 - 项目业主结算记录
     *
     * @author yy
     * @date  2025/06/03 09:52
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目产值台账 - 项目管理 - 项目业主结算记录")
    @CommonLog("添加项目产值台账 - 项目管理 - 项目业主结算记录")
//    @SaCheckPermission("/biz/prj/por/owner/settle/add")
    @PostMapping("/biz/prj/por/owner/settle/add")
    public CommonResult<String> add(@RequestBody @Valid PorOwnerSettleAddParam porOwnerSettleAddParam) {
        porOwnerSettleService.add(porOwnerSettleAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目产值台账 - 项目管理 - 项目业主结算记录
     *
     * @author yy
     * @date  2025/06/03 09:52
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目产值台账 - 项目管理 - 项目业主结算记录")
    @CommonLog("编辑项目产值台账 - 项目管理 - 项目业主结算记录")
//    @SaCheckPermission("/biz/prj/por/owner/settle/edit")
    @PostMapping("/biz/prj/por/owner/settle/edit")
    public CommonResult<String> edit(@RequestBody @Valid PorOwnerSettleEditParam porOwnerSettleEditParam) {
        porOwnerSettleService.edit(porOwnerSettleEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目产值台账 - 项目管理 - 项目业主结算记录
     *
     * @author yy
     * @date  2025/06/03 09:52
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目产值台账 - 项目管理 - 项目业主结算记录")
    @CommonLog("删除项目产值台账 - 项目管理 - 项目业主结算记录")
//    @SaCheckPermission("/biz/prj/por/owner/settle/delete")
    @PostMapping("/biz/prj/por/owner/settle/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PorOwnerSettleIdParam> porOwnerSettleIdParamList) {
        porOwnerSettleService.delete(porOwnerSettleIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目产值台账 - 项目管理 - 项目业主结算记录详情
     *
     * @author yy
     * @date  2025/06/03 09:52
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目产值台账 - 项目管理 - 项目业主结算记录详情")
//    @SaCheckPermission("/biz/prj/por/owner/settle/detail")
    @GetMapping("/biz/prj/por/owner/settle/detail")
    public CommonResult<PorOwnerSettleVo> detail(@Valid PorOwnerSettleIdParam porOwnerSettleIdParam) {
        return CommonResult.data(porOwnerSettleService.detail(porOwnerSettleIdParam));
    }
}
