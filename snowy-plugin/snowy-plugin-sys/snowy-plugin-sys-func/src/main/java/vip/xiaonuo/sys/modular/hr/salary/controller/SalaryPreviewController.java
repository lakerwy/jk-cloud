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
package vip.xiaonuo.sys.modular.hr.salary.controller;

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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryPreview;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryPreviewAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryPreviewEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryPreviewIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryPreviewPageParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.*;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryPreviewService;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryPreviewVo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 【预览】工资系统-工资预览表控制器
 *
 * @author yong.ye
 * @date  2024/03/22 10:26
 */
@Api(tags = "工资系统-工资预览表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SalaryPreviewController {

    @Resource
    private SalaryPreviewService salaryPreviewService;

    /**
     * 获取工资系统-【未封存的预览数据，已封存不展示】
     *
     * @author yong.ye
     * @date  2024/03/22 10:26
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取工资系统-【未封存的预览数据，已封存不展示】")
    @GetMapping("/sys/hr/salary/preview/page")
    public CommonResult<Page<SalaryPreviewVo>> page(SalaryPreviewPageParam salaryPreviewPageParam) {
        return CommonResult.data(salaryPreviewService.page(salaryPreviewPageParam));
    }

    /**
     * 添加工资系统-工资预览表
     *
     * @author yong.ye
     * @date  2024/03/22 10:26
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加工资系统-工资预览表")
    @CommonLog("添加工资系统-工资预览表")
    @PostMapping("/sys/hr/salary/preview/add")
    public CommonResult<String> add(@RequestBody @Valid SalaryPreviewAddParam salaryPreviewAddParam) {
        salaryPreviewService.add(salaryPreviewAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑工资系统-工资预览表
     *
     * @author yong.ye
     * @date  2024/03/22 10:26
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑工资系统-工资预览表")
    @CommonLog("编辑工资系统-工资预览表")
    @PostMapping("/sys/hr/salary/preview/edit")
    public CommonResult<String> edit(@RequestBody @Valid SalaryPreviewEditParam salaryPreviewEditParam) {
        salaryPreviewService.edit(salaryPreviewEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除工资系统-工资预览表
     *
     * @author yong.ye
     * @date  2024/03/22 10:26
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除工资系统-工资预览表")
    @CommonLog("删除工资系统-工资预览表")
    @PostMapping("/sys/hr/salary/preview/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<SalaryPreviewIdParam> salaryPreviewIdParamList) {
        salaryPreviewService.delete(salaryPreviewIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取工资系统-工资预览表详情
     *
     * @author yong.ye
     * @date  2024/03/22 10:26
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取工资系统-工资预览表详情")
    @GetMapping("/sys/hr/salary/preview/detail")
    public CommonResult<SalaryPreview> detail(@Valid SalaryPreviewIdParam salaryPreviewIdParam) {
        return CommonResult.data(salaryPreviewService.detail(salaryPreviewIdParam));
    }




    /**
     * 【删除-未封存数据】删除某年月的所有预览工资数据
     *  已封存的数据不可删除
     * @author yong.ye
     * @date  2024/03/01 16:50
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("【删除-未封存数据】删除某年月的所有预览工资数据-已封存的数据不可删除")
    @CommonLog("【删除-未封存数据】删除某年月的所有预览工资数据-已封存的数据不可删除")
    @PostMapping("/sys/hr/salary/preview/delByMonth")
    public CommonResult<String> delByMonth(@RequestBody @Valid SearchParam param) {
        salaryPreviewService.delByMonth(param);
        return CommonResult.ok();
    }

    /**
     * 【封存】工资系统-【某年月未封存预览工资数据】
     *
     * @author yong.ye
     * @date  2024/03/01 16:50
     */
    @ApiOperationSupport(order = 22)
    @ApiOperation("【封存】工资系统-【某年月未封存预览工资数据】")
    @CommonLog("【封存】工资系统-【某年月未封存预览工资数据】")
    @PostMapping("/sys/hr/salary/preview/archiveGZ")
    public CommonResult<String> archiveGZ(@RequestBody @Valid SearchParam param) {
        salaryPreviewService.archiveGZ(param);
        return CommonResult.ok();
    }




    /**
     * 【系统自动生成工资数据】- 系统自动生成的工资预览数据
     *
     * @author yong.ye
     * @date  2024/03/01 16:50
     */
    @ApiOperationSupport(order = 21)
    @ApiOperation("【系统自动生成工资数据】- 系统自动生成的工资预览数据")
    @GetMapping("/sys/hr/salary/preview/autoGZPage")
    public CommonResult<List<SalaryPreviewVo>> autoGZPage(AutoParam param) {
        return CommonResult.data(salaryPreviewService.autoGZPage(param));
    }


    /**
     * 【系统自动生成的工资数据 - 确认】
     *
     * @author yong.ye
     * @date  2024/03/01 16:50
     */
    @ApiOperationSupport(order = 22)
    @ApiOperation("【确认生成】工资系统-【系统自动生成的工资预览数据】")
    @CommonLog("【确认生成】工资系统-【系统自动生成的工资预览数据】")
    @PostMapping("/sys/hr/salary/preview/autoGZSure")
    public CommonResult<String> autoGZSure(@RequestBody @Valid SearchParam param) {
        salaryPreviewService.autoGZSure(param);
        return CommonResult.ok();
    }


    /**
     * 标记-字段更新
     *
     * @author yong.ye
     * @date  2024/03/22 10:26
     */
    @ApiOperationSupport(order = 23)
    @ApiOperation("标记-字段更新")
    @CommonLog("标记-字段更新")
    @PostMapping("/sys/hr/salary/preview/updateField")
    public CommonResult<String> updateField(@RequestBody @Valid UpdateFieldParam fieldParam) {
        salaryPreviewService.updateField(fieldParam);
        return CommonResult.ok();
    }


    /**
     * 批量更新-是否缓发
     *
     * @author yong.ye
     * @date  2024/03/22 10:26
     */
    @ApiOperationSupport(order = 24)
    @ApiOperation("批量更新-是否缓发")
    @CommonLog("批量更新-是否缓发")
    @PostMapping("/sys/hr/salary/preview/updateIsSlow")
    public CommonResult<String> updateIsSlow(@RequestBody @Valid UpdateBatch updateBatch) {
        salaryPreviewService.updateIsSlow(updateBatch);
        return CommonResult.ok();
    }

    /**
     * 【工资预览】按照 工资所属年月 导出工资报表 - 打印版
     *
     * @author yong.ye
     * @date  2024/06/06 10:26
     */
    @ApiOperationSupport(order = 25)
    @ApiOperation("【工资预览】按照 工资所属年月 导出工资报表 - 打印版")
    @CommonLog("【工资预览】按照 工资所属年月 导出工资报表 - 打印版")
    @GetMapping("/sys/hr/salary/preview/gzExport")
    public void gzExport(@Valid ExcelParam excelParam, HttpServletResponse response) {
        salaryPreviewService.gzExport(excelParam,response);
    }
}
