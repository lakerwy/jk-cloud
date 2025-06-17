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
import vip.xiaonuo.biz.modular.crm.hetong.vo.HetongDetailVo;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.Subcontract;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataPpc;
import vip.xiaonuo.biz.modular.prj.por.entity.PorMember;
import vip.xiaonuo.biz.modular.prj.por.entity.PorReport;
import vip.xiaonuo.biz.modular.prj.por.entity.PorTask;
import vip.xiaonuo.biz.modular.prj.por.entity.PorWarn;
import vip.xiaonuo.biz.modular.prj.por.param.*;
import vip.xiaonuo.biz.modular.prj.por.vo.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.por.service.PorProjectService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 项目产值台账 - 项目管理控制器
 *
 * @author yy
 * @date  2025/06/03 09:39
 */
@Api(tags = "项目产值台账 - 项目管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PorProjectController {

    @Resource
    private PorProjectService porProjectService;

    /**
     * 获取项目产值台账 - 项目管理分页
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目产值台账 - 项目管理分页")
//    @SaCheckPermission("/biz/prj/por/page")
    @GetMapping("/biz/prj/por/page")
    public CommonResult<Page<PorProjectVo>> page(PorProjectPageParam porProjectPageParam) {
        return CommonResult.data(porProjectService.page(porProjectPageParam));
    }

    /**
     * 添加项目产值台账 - 项目管理
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目产值台账 - 项目管理")
    @CommonLog("添加项目产值台账 - 项目管理")
//    @SaCheckPermission("/biz/prj/por/add")
    @PostMapping("/biz/prj/por/add")
    public CommonResult<String> add(@RequestBody @Valid PorProjectAddParam porProjectAddParam) {
        porProjectService.add(porProjectAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目产值台账 - 项目管理
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目产值台账 - 项目管理")
    @CommonLog("编辑项目产值台账 - 项目管理")
//    @SaCheckPermission("/biz/prj/por/edit")
    @PostMapping("/biz/prj/por/edit")
    public CommonResult<String> edit(@RequestBody @Valid PorProjectEditParam porProjectEditParam) {
        porProjectService.edit(porProjectEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目产值台账 - 项目管理
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目产值台账 - 项目管理")
    @CommonLog("删除项目产值台账 - 项目管理")
//    @SaCheckPermission("/biz/prj/por/delete")
    @PostMapping("/biz/prj/por/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PorProjectIdParam> porProjectIdParamList) {
        porProjectService.delete(porProjectIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目产值台账 - 项目管理详情
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目产值台账 - 项目管理详情")
//    @SaCheckPermission("/biz/prj/por/detail")
    @GetMapping("/biz/prj/por/detail")
    public CommonResult<PorProjectVo> detail(@Valid PorProjectIdParam porProjectIdParam) {
        return CommonResult.data(porProjectService.detail(porProjectIdParam));
    }




    /**
     * 获取项目产值台账 - 项目管理分页（含权限控制）
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("获取项目产值台账 - 项目管理分页（含权限控制）")
    @SaCheckPermission("/biz/prj/por/pageList")
    @GetMapping("/biz/prj/por/pageList")
    public CommonResult<Page<PorProjectVo>> pageList(PorProjectPageParam porProjectPageParam) {
        return CommonResult.data(porProjectService.pageList(porProjectPageParam));
    }




    /**
     * 根据项目id,获取项目计划
     */
    @ApiOperationSupport(order = 21)
    @ApiOperation("根据项目id,获取项目计划")
    @GetMapping("/biz/prj/por/task/getTaskListByPrjId")
    public CommonResult<List<PorTask>> getTaskListByPrjId(@Valid PorProjectParam param) {
        return CommonResult.data(porProjectService.getTaskListByPrjId(param));
    }


    /**
     * 根据项目id,获取项目周报|月报
     */
    @ApiOperationSupport(order = 22)
    @ApiOperation("根据项目id,获取项目周报|月报")
    @GetMapping("/biz/prj/por/report/getReportListByPrjId")
    public CommonResult<List<PorReport>> getReportListByPrjId(@Valid PorProjectParam param) {
        return CommonResult.data(porProjectService.getReportListByPrjId(param));
    }

    /**
     * 根据项目id,获取收入合同
     */
    @ApiOperationSupport(order = 23)
    @ApiOperation("根据项目id,获取收入合同")
    @GetMapping("/biz/prj/por/getHtListByPrjId")
    public CommonResult<List<HetongDetailVo>> getHtListByPrjId(@Valid PorProjectParam param) {
        return CommonResult.data(porProjectService.getHtListByPrjId(param));
    }



    /**
     * 根据项目id,获取收入合同
     */
    @ApiOperationSupport(order = 24)
    @ApiOperation("根据项目id,获取回款计划")
    @GetMapping("/biz/prj/por/getRecListByPrjId")
    public CommonResult<List<RecData>> getRecListByPrjId(@Valid PorProjectParam param) {
        return CommonResult.data(porProjectService.getRecListByPrjId(param));
    }


    /**
     * 根据项目id,获取业主结算
     */
    @ApiOperationSupport(order = 25)
    @ApiOperation("根据项目id,获取业主结算")
    @GetMapping("/biz/prj/por/owner/settle/getOwnerListByPrjId")
    public CommonResult<List<PorOwnerSettleVo>> getOwnerListByPrjId(@Valid PorProjectParam param) {
        return CommonResult.data(porProjectService.getOwnerListByPrjId(param));
    }


    /**
     * 根据项目id,获取项目回款
     */
    @ApiOperationSupport(order = 26)
    @ApiOperation("根据项目id,获取项目回款")
    @GetMapping("/biz/prj/por/getPpcListByPrjId")
    public CommonResult<List<FinDataPpc>> getPpcListByPrjId(@Valid PorProjectParam param) {
        return CommonResult.data(porProjectService.getPpcListByPrjId(param));
    }

    /**
     * 根据项目id,获取分包合同
     */
    @ApiOperationSupport(order = 27)
    @ApiOperation("根据项目id,获取分包合同")
    @GetMapping("/biz/prj/por/getSubListByPrjId")
    public CommonResult<List<Subcontract>> getSubListByPrjId(@Valid PorProjectParam param) {
        return CommonResult.data(porProjectService.getSubListByPrjId(param));
    }


    /**
     * 根据项目id,获取分包结算
     */
    @ApiOperationSupport(order = 28)
    @ApiOperation("根据项目id,获取分包结算")
    @GetMapping("/biz/prj/por/getSubSettleListByPrjId")
    public CommonResult<List<PorSubSettleVo>> getSubSettleListByPrjId(@Valid PorProjectParam param) {
        return CommonResult.data(porProjectService.getSubSettleListByPrjId(param));
    }



    /**
     * 根据项目id,获取项目拨付明细
     */
    @ApiOperationSupport(order = 29)
    @ApiOperation("根据项目id,获取项目拨付明细")
    @GetMapping("/biz/prj/por/getPisListByPrjId")
    public CommonResult<List<PisData>> getPisListByPrjId(@Valid PorProjectParam param) {
        return CommonResult.data(porProjectService.getPisListByPrjId(param));
    }


    /**
     * 根据项目id,获取责任上缴
     */
    @ApiOperationSupport(order = 30)
    @ApiOperation("根据项目id,获取责任上缴")
    @GetMapping("/biz/prj/por/getRpmListByPrjId")
    public CommonResult<List<PorRpmVo>> getRpmListByPrjId(@Valid PorProjectParam param) {
        return CommonResult.data(porProjectService.getRpmListByPrjId(param));
    }


    /**
     * 根据项目id,获取签字费
     */
    @ApiOperationSupport(order = 31)
    @ApiOperation("根据项目id,获取签字费")
    @GetMapping("/biz/prj/por/getSignListByPrjId")
    public CommonResult<List<PorSignVo>> getSignListByPrjId(@Valid PorProjectParam param) {
        return CommonResult.data(porProjectService.getSignListByPrjId(param));
    }


    /**
     * 根据项目id,获取签字费
     */
    @ApiOperationSupport(order = 32)
    @ApiOperation("根据项目id,获取项目现场管理费")
    @GetMapping("/biz/prj/por/getSiteListByPrjId")
    public CommonResult<List<PorSiteFeeVo>> getSiteListByPrjId(@Valid PorProjectParam param) {
        return CommonResult.data(porProjectService.getSiteListByPrjId(param));
    }


    /**
     * 根据项目id，获取项目成员
     *
     * @author yy
     * @date  2025/06/03 09:40
     */
    @ApiOperationSupport(order = 33)
    @ApiOperation("根据项目id，获取项目成员")
    @GetMapping("/biz/prj/por/getMemberListByPrjId")
    public CommonResult<List<PorMember>> getMemberListByPrjId(@Valid PorPrjParam param) {
        return CommonResult.data(porProjectService.getMemberListByPrjId(param));
    }


    /**
     * 根据项目id,获取预警记录
     *
     * @author yy
     * @date  2025/06/03 09:40
     */
    @ApiOperationSupport(order = 33)
    @ApiOperation("根据项目id,获取预警记录")
    @GetMapping("/biz/prj/por/getWarnListByPrjId")
    public CommonResult<List<PorWarn>> getWarnListByPrjId(@Valid PorPrjParam param) {
        return CommonResult.data(porProjectService.getWarnListByPrjId(param));
    }


    /**
     * 回款计划 - 更新回款情况
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    @ApiOperationSupport(order = 34)
    @ApiOperation("回款计划 - 更新回款情况")
    @CommonLog("回款计划 - 更新回款情况")
    @PostMapping("/biz/prj/por/updateRec")
    public CommonResult<String> updateRec(@RequestBody @Valid RecParam param) {
        porProjectService.updateRec(param);
        return CommonResult.ok();
    }


    /**
     * 预警记录 - 更新预警记录信息
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    @ApiOperationSupport(order = 35)
    @ApiOperation("预警记录 - 更新预警记录信息")
    @CommonLog("预警记录 - 更新预警记录信息")
    @PostMapping("/biz/prj/por/updatePorWarn")
    public CommonResult<String> updatePorWarn(@RequestBody @Valid RecParam param) {
        porProjectService.updatePorWarn(param);
        return CommonResult.ok();
    }

}
