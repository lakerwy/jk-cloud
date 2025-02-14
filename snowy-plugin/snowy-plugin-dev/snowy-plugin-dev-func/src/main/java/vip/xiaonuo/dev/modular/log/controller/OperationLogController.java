package vip.xiaonuo.dev.modular.log.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.util.modular.log.entity.OperationLogEntity;
import vip.xiaonuo.util.modular.log.param.OperationLogPageParam;
import vip.xiaonuo.util.modular.log.service.OperationLogService;

import javax.annotation.Resource;

/**
 * 操作日志控制器
 *
 * @author pzh
 * @date  2025/02/13 16:48
 */
@Api(tags = "操作日志控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class OperationLogController {

    @Resource
    private OperationLogService operationLogService;

    /**
     * 获取操作日志分页
     *
     * @author pzh
     * @date  2025/02/13 16:48
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取操作日志分页")
    @GetMapping("/dev/operationLog/page")
    public CommonResult<Page<OperationLogEntity>> page(OperationLogPageParam operationLogPageParam) {
        return CommonResult.data(operationLogService.page(operationLogPageParam));
    }

//    /**
//     * 添加操作日志
//     *
//     * @author pzh
//     * @date  2025/02/13 16:48
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("添加操作日志")
//    @CommonLog("添加操作日志")
//    @PostMapping("/dev/operationLog/add")
//    public CommonResult<String> add(@RequestBody @Valid OperationLogAddParam operationLogAddParam) {
//        operationLogService.add(operationLogAddParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 编辑操作日志
//     *
//     * @author pzh
//     * @date  2025/02/13 16:48
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("编辑操作日志")
//    @CommonLog("编辑操作日志")
//    @PostMapping("/dev/operationLog/edit")
//    public CommonResult<String> edit(@RequestBody @Valid OperationLogEditParam operationLogEditParam) {
//        operationLogService.edit(operationLogEditParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 删除操作日志
//     *
//     * @author pzh
//     * @date  2025/02/13 16:48
//     */
//    @ApiOperationSupport(order = 4)
//    @ApiOperation("删除操作日志")
//    @CommonLog("删除操作日志")
//    @PostMapping("/dev/operationLog/delete")
//    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
//                                       CommonValidList<OperationLogIdParam> operationLogIdParamList) {
//        operationLogService.delete(operationLogIdParamList);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 获取操作日志详情
//     *
//     * @author pzh
//     * @date  2025/02/13 16:48
//     */
//    @ApiOperationSupport(order = 5)
//    @ApiOperation("获取操作日志详情")
//    @GetMapping("/dev/operationLog/detail")
//    public CommonResult<OperationLogEntity> detail(@Valid OperationLogIdParam operationLogIdParam) {
//        return CommonResult.data(operationLogService.detail(operationLogIdParam));
//    }
}
