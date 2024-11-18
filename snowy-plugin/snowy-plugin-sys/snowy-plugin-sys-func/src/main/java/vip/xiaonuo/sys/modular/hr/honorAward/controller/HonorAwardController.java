package vip.xiaonuo.sys.modular.hr.honorAward.controller;

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
import vip.xiaonuo.sys.modular.hr.honorAward.entity.HonorAward;
import vip.xiaonuo.sys.modular.hr.honorAward.param.HonorAwardAddParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.HonorAwardEditParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.HonorAwardIdParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.HonorAwardPageParam;
import vip.xiaonuo.sys.modular.hr.honorAward.service.HonorAwardService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 荣誉奖项控制器
 *
 * @author pzh
 * @date  2024/10/16 15:18
 */
@Api(tags = "荣誉奖项控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HonorAwardController {

    @Resource
    private HonorAwardService honorAwardService;

    /**
     * 获取荣誉奖项分页
     *
     * @author pzh
     * @date  2024/10/16 15:18
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取荣誉奖项分页")
    // @SaCheckPermission("/sys/honorAward/page")
    @GetMapping("/sys/honorAward/page")
    public CommonResult<Page<HonorAward>> page(HonorAwardPageParam honorAwardPageParam) {
        return CommonResult.data(honorAwardService.page(honorAwardPageParam));
    }

    /**
     * 添加荣誉奖项
     *
     * @author pzh
     * @date  2024/10/16 15:18
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加荣誉奖项")
    @CommonLog("添加荣誉奖项")
    // @SaCheckPermission("/sys/honorAward/add")
    @PostMapping("/sys/honorAward/add")
    public CommonResult<String> add(@RequestBody @Valid HonorAwardAddParam honorAwardAddParam) {
        honorAwardService.add(honorAwardAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑荣誉奖项
     *
     * @author pzh
     * @date  2024/10/16 15:18
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑荣誉奖项")
    @CommonLog("编辑荣誉奖项")
    // @SaCheckPermission("/sys/honorAward/edit")
    @PostMapping("/sys/honorAward/edit")
    public CommonResult<String> edit(@RequestBody @Valid HonorAwardEditParam honorAwardEditParam) {
        honorAwardService.edit(honorAwardEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除荣誉奖项
     *
     * @author pzh
     * @date  2024/10/16 15:18
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除荣誉奖项")
    @CommonLog("删除荣誉奖项")
    // @SaCheckPermission("/sys/honorAward/delete")
    @PostMapping("/sys/honorAward/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<HonorAwardIdParam> honorAwardIdParamList) {
        honorAwardService.delete(honorAwardIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取荣誉奖项详情
     *
     * @author pzh
     * @date  2024/10/16 15:18
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取荣誉奖项详情")
    // @SaCheckPermission("/sys/honorAward/detail")
    @GetMapping("/sys/honorAward/detail")
    public CommonResult<HonorAward> detail(@Valid HonorAwardIdParam honorAwardIdParam) {
        return CommonResult.data(honorAwardService.detail(honorAwardIdParam));
    }
}
