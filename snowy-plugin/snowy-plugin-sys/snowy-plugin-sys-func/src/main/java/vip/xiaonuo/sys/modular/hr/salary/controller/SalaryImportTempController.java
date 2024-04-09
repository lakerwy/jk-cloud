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

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.*;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryImportTempService;
import vip.xiaonuo.sys.modular.hr.salary.vo.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 工资系统-相关数据导入临时表控制器
 *
 * @author yong.ye
 * @date  2024/02/26 09:31
 */
@Api(tags = "工资系统-相关数据导入临时表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SalaryImportTempController {

    @Resource
    private SalaryImportTempService salaryImportTempService;


    /**
     * 【导入】- 员工月工资标准
     *
     * @author yong.ye
     * @date  2024/02/28 09:31
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("【导入】- 员工月工资标准 ")
    @CommonLog("【导入】- 员工月工资标准 ")
    @PostMapping("/sys/hr/salary/import/standard")
    public CommonResult<ImportResult> importStandard(@RequestBody @Valid ImportStandard input) {
        return CommonResult.data(salaryImportTempService.importStandard(input));
    }

    /**
     * 【导入】- 考勤数据
     *
     * @author yong.ye
     * @date  2024/02/28 09:31
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("【导入】- 考勤数据 ")
    @CommonLog("【导入】- 考勤数据 ")
    @PostMapping("/sys/hr/salary/import/attend")
    public CommonResult<ImportResult> importAttend(@RequestBody @Valid ImportAttend input) {
        return CommonResult.data(salaryImportTempService.importAttend(input));
    }


    /**
     * 【导入】- 补贴数据
     *
     * @author yong.ye
     * @date  2024/02/28 09:31
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("【导入】- 补贴数据 ")
    @CommonLog("【导入】- 补贴数据 ")
    @PostMapping("/sys/hr/salary/import/subsidy")
    public CommonResult<ImportResult> importSubsidy(@RequestBody @Valid ImportSubsidy input) {
        return CommonResult.data(salaryImportTempService.importSubsidy(input));
    }

    /**
     * 【导入】- 社保|公积金|专项扣除 数据
     *
     * @author yong.ye
     * @date  2024/02/28 09:31
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("【导入】- 社保|公积金|专项扣除 数据 ")
    @CommonLog("【导入】- 社保|公积金|专项扣除 数据 ")
    @PostMapping("/sys/hr/salary/import/insGjjTax")
    public CommonResult<ImportResult> importInsGjjTax(@RequestBody @Valid ImportInsGjjTax input) {
        return CommonResult.data(salaryImportTempService.importInsGjjTax(input));
    }


    /**
     * 【导入】- 员工福利（除生日福利） 数据
     *
     * @author yong.ye
     * @date  2024/02/28 09:31
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("【导入】- 员工福利（除生日福利） 数据 ")
    @CommonLog("【导入】- 员工福利（除生日福利） 数据 ")
    @PostMapping("/sys/hr/salary/import/importPay")
    public CommonResult<ImportResult> importPay(@RequestBody @Valid ImportPay input) {
        return CommonResult.data(salaryImportTempService.importPay(input));
    }

    /**
     * 【导入】- 已确认的 员工工资 数据  HR_SALARY
     *
     * @author yong.ye
     * @date  2024/02/28 09:31
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("【导入】- 已确认的 员工工资 数据  HR_SALARY ")
    @CommonLog("【导入】- 已确认的 员工工资 数据  HR_SALARY ")
    @PostMapping("/sys/hr/salary/import/importSalary")
    public CommonResult<ImportResult> importSalary(@RequestBody @Valid ImportSalary input) {
        return CommonResult.data(salaryImportTempService.importSalary(input));
    }


    /**
     * 【导入】- 确认导入，数据正式入库
     *
     * @author yong.ye
     * @date  2024/02/28 09:31
     */
    @ApiOperationSupport(order = 20)
    @ApiOperation("【导入】- 确认导入，数据正式入库 ")
    @CommonLog("【导入】- 确认导入，数据正式入库 ")
    @PostMapping("/sys/hr/salary/import/doImport")
    public CommonResult<Boolean> doImport(@RequestBody @Valid DoImport input) {
        return CommonResult.data(salaryImportTempService.doImport(input));
    }

//    /**
//     * 【导入】- 其他费用明细 数据
//     *
//     * @author yong.ye
//     * @date  2024/02/28 09:31
//     */
//    @ApiOperationSupport(order = 8)
//    @ApiOperation("【导入】- 其他费用明细 数据 ")
//    @CommonLog("【导入】- 其他费用明细 数据 ")
//    @PostMapping("/sys/hr/salary/import/importAdd")
//    public CommonResult<ImportResult> importAdd(@RequestBody @Valid ImportAdd input) {
//        return CommonResult.data(salaryImportTempService.importAdd(input));
//    }

}
