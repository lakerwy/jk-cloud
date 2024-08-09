package vip.xiaonuo.iot.modular.project.controller;

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
import vip.xiaonuo.iot.modular.project.entity.IotProject;
import vip.xiaonuo.iot.modular.project.param.*;
import vip.xiaonuo.iot.modular.project.service.IotProjectService;
import vip.xiaonuo.iot.modular.project.vo.NodeVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 物联网-项目控制器
 *
 * @author mayike
 * @date  2024/08/08 14:03
 */
@Api(tags = "物联网-项目控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class IotProjectController {

    @Resource
    private IotProjectService iotProjectService;

    /**
     * 获取物联网-项目分页
     *
     * @author mayike
     * @date  2024/08/08 14:03
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取物联网-项目分页")
//    @SaCheckPermission("/iot/project/page")
    @GetMapping("/iot/project/page")
    public CommonResult<Page<IotProject>> page(IotProjectPageParam iotProjectPageParam) {
        return CommonResult.data(iotProjectService.page(iotProjectPageParam));
    }

    /**
     * 添加物联网-项目
     *
     * @author mayike
     * @date  2024/08/08 14:03
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加物联网-项目")
    @CommonLog("添加物联网-项目")
//    @SaCheckPermission("/iot/project/add")
    @PostMapping("/iot/project/add")
    public CommonResult<String> add(@RequestBody @Valid IotProjectAddParam iotProjectAddParam) {
        iotProjectService.add(iotProjectAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑物联网-项目
     *
     * @author mayike
     * @date  2024/08/08 14:03
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑物联网-项目")
    @CommonLog("编辑物联网-项目")
//    @SaCheckPermission("/iot/project/edit")
    @PostMapping("/iot/project/edit")
    public CommonResult<String> edit(@RequestBody @Valid IotProjectEditParam iotProjectEditParam) {
        iotProjectService.edit(iotProjectEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除物联网-项目
     *
     * @author mayike
     * @date  2024/08/08 14:03
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除物联网-项目")
    @CommonLog("删除物联网-项目")
//    @SaCheckPermission("/iot/project/delete")
    @PostMapping("/iot/project/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<IotProjectIdParam> iotProjectIdParamList) {
        iotProjectService.delete(iotProjectIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取物联网-项目详情
     *
     * @author mayike
     * @date  2024/08/08 14:03
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取物联网-项目详情")
//    @SaCheckPermission("/iot/project/detail")
    @GetMapping("/iot/project/detail")
    public CommonResult<IotProject> detail(@Valid IotProjectIdParam iotProjectIdParam) {
        return CommonResult.data(iotProjectService.detail(iotProjectIdParam));
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("获取物理网拓扑数")
    @GetMapping("/iot/project/getIotTrees")
    public CommonResult<NodeVo> getIotTrees(@Valid IotProjectListParam IotProjectListParam) {
        NodeVo nodeVo = iotProjectService.getIotTrees(IotProjectListParam);
        return CommonResult.data(nodeVo);
    }
}
