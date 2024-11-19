package vip.xiaonuo.sys.modular.hr.techAchive.controller;

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
import vip.xiaonuo.sys.modular.hr.techAchive.entity.OtherTechAchive;
import vip.xiaonuo.sys.modular.hr.techAchive.param.OtherTechAchiveAddParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.OtherTechAchiveEditParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.OtherTechAchiveIdParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.OtherTechAchivePageParam;
import vip.xiaonuo.sys.modular.hr.techAchive.service.OtherTechAchiveService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 其他（科技成果）控制器
 *
 * @author pzh
 * @date  2024/10/18 09:10
 */
@Api(tags = "其他（科技成果）控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class OtherTechAchiveController {

    @Resource
    private OtherTechAchiveService otherTechAchiveService;

    /**
     * 获取其他（科技成果）分页
     *
     * @author pzh
     * @date  2024/10/18 09:10
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取其他（科技成果）分页")
    // @SaCheckPermission("/sys/techAchive/otherTechAchive/page")
    @GetMapping("/sys/techAchive/otherTechAchive/page")
    public CommonResult<Page<OtherTechAchive>> page(OtherTechAchivePageParam otherTechAchivePageParam) {
        return CommonResult.data(otherTechAchiveService.page(otherTechAchivePageParam));
    }

    /**
     * 添加其他（科技成果）
     *
     * @author pzh
     * @date  2024/10/18 09:10
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加其他（科技成果）")
    @CommonLog("添加其他（科技成果）")
    // @SaCheckPermission("/sys/techAchive/otherTechAchive/add")
    @PostMapping("/sys/techAchive/otherTechAchive/add")
    public CommonResult<String> add(@RequestBody @Valid OtherTechAchiveAddParam otherTechAchiveAddParam) {
        otherTechAchiveService.add(otherTechAchiveAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑其他（科技成果）
     *
     * @author pzh
     * @date  2024/10/18 09:10
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑其他（科技成果）")
    @CommonLog("编辑其他（科技成果）")
    // @SaCheckPermission("/sys/techAchive/otherTechAchive/edit")
    @PostMapping("/sys/techAchive/otherTechAchive/edit")
    public CommonResult<String> edit(@RequestBody @Valid OtherTechAchiveEditParam otherTechAchiveEditParam) {
        otherTechAchiveService.edit(otherTechAchiveEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除其他（科技成果）
     *
     * @author pzh
     * @date  2024/10/18 09:10
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除其他（科技成果）")
    @CommonLog("删除其他（科技成果）")
    // @SaCheckPermission("/sys/techAchive/otherTechAchive/delete")
    @PostMapping("/sys/techAchive/otherTechAchive/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<OtherTechAchiveIdParam> otherTechAchiveIdParamList) {
        otherTechAchiveService.delete(otherTechAchiveIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取其他（科技成果）详情
     *
     * @author pzh
     * @date  2024/10/18 09:10
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取其他（科技成果）详情")
    // @SaCheckPermission("/sys/techAchive/otherTechAchive/detail")
    @GetMapping("/sys/techAchive/otherTechAchive/detail")
    public CommonResult<OtherTechAchive> detail(@Valid OtherTechAchiveIdParam otherTechAchiveIdParam) {
        return CommonResult.data(otherTechAchiveService.detail(otherTechAchiveIdParam));
    }
}
