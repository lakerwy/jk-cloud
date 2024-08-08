package vip.xiaonuo.iot.modular.topic.controller;

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
import vip.xiaonuo.iot.modular.topic.entity.IotTopic;
import vip.xiaonuo.iot.modular.topic.param.IotTopicAddParam;
import vip.xiaonuo.iot.modular.topic.param.IotTopicEditParam;
import vip.xiaonuo.iot.modular.topic.param.IotTopicIdParam;
import vip.xiaonuo.iot.modular.topic.param.IotTopicPageParam;
import vip.xiaonuo.iot.modular.topic.service.IotTopicService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 物理网-主题topic 表控制器
 *
 * @author mayike
 * @date  2024/08/05 10:26
 */
@Api(tags = "物理网-网关控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class IotTopicController {

    @Resource
    private IotTopicService iotTopicService;

    /**
     * 获取物理网-网关分页
     *
     * @author mayike
     * @date  2024/08/05 10:26
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取物理网-网关分页")
//    @SaCheckPermission("/iot/topic/page")
    @GetMapping("/iot/topic/page")
    public CommonResult<Page<IotTopic>> page(IotTopicPageParam iotTopicPageParam) {
        return CommonResult.data(iotTopicService.page(iotTopicPageParam));
    }

    /**
     * 添加物理网-主题topic 表
     *
     * @author mayike
     * @date  2024/08/05 10:26
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加物理网-网关")
    @CommonLog("添加物理网-网关")
//    @SaCheckPermission("/iot/topic/add")
    @PostMapping("/iot/topic/add")
    public CommonResult<String> add(@RequestBody @Valid IotTopicAddParam iotTopicAddParam) {
        iotTopicService.add(iotTopicAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑物理网-主题topic 表
     *
     * @author mayike
     * @date  2024/08/05 10:26
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑物理网-网关")
    @CommonLog("编辑物理网-网关")
//    @SaCheckPermission("/iot/topic/edit")
    @PostMapping("/iot/topic/edit")
    public CommonResult<String> edit(@RequestBody @Valid IotTopicEditParam iotTopicEditParam) {
        iotTopicService.edit(iotTopicEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除物理网-主题topic 表
     *
     * @author mayike
     * @date  2024/08/05 10:26
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除物理网-网关")
    @CommonLog("删除物理网-网关")
//    @SaCheckPermission("/iot/topic/delete")
    @PostMapping("/iot/topic/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<IotTopicIdParam> iotTopicIdParamList) {
        iotTopicService.delete(iotTopicIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取物理网-主题topic 表详情
     *
     * @author mayike
     * @date  2024/08/05 10:26
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取物理网-网关")
//    @SaCheckPermission("/iot/topic/detail")
    @GetMapping("/iot/topic/detail")
    public CommonResult<IotTopic> detail(@Valid IotTopicIdParam iotTopicIdParam) {
        return CommonResult.data(iotTopicService.detail(iotTopicIdParam));
    }
}
