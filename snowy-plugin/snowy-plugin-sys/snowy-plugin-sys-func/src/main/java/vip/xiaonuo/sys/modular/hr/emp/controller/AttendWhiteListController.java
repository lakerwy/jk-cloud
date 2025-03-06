package vip.xiaonuo.sys.modular.hr.emp.controller;

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
import vip.xiaonuo.sys.modular.hr.emp.entity.AttendWhiteList;
import vip.xiaonuo.sys.modular.hr.emp.param.AttendWhiteListAddParam;
import vip.xiaonuo.sys.modular.hr.emp.param.AttendWhiteListEditParam;
import vip.xiaonuo.sys.modular.hr.emp.param.AttendWhiteListIdParam;
import vip.xiaonuo.sys.modular.hr.emp.param.AttendWhiteListPageParam;
import vip.xiaonuo.sys.modular.hr.emp.service.AttendWhiteListService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 钉钉考勤-员工白名单控制器
 *
 * @author pzh
 * @date  2025/03/06 11:34
 */
@Api(tags = "钉钉考勤-员工白名单控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class AttendWhiteListController {

    @Resource
    private AttendWhiteListService attendWhiteListService;

    /**
     * 获取钉钉考勤-员工白名单分页
     *
     * @author pzh
     * @date  2025/03/06 11:34
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取钉钉考勤-员工白名单分页")
//    @SaCheckPermission("/sys/emp/whiteList/page")
    @GetMapping("/sys/emp/whiteList/page")
    public CommonResult<Page<AttendWhiteList>> page(AttendWhiteListPageParam attendWhiteListPageParam) {
        return CommonResult.data(attendWhiteListService.page(attendWhiteListPageParam));
    }

    /**
     * 添加钉钉考勤-员工白名单
     *
     * @author pzh
     * @date  2025/03/06 11:34
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加钉钉考勤-员工白名单")
    @CommonLog("添加钉钉考勤-员工白名单")
//    @SaCheckPermission("/sys/emp/whiteList/add")
    @PostMapping("//sys/emp/whiteList/add")
    public CommonResult<String> add(@RequestBody @Valid AttendWhiteListAddParam attendWhiteListAddParam) {
        attendWhiteListService.add(attendWhiteListAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑钉钉考勤-员工白名单
     *
     * @author pzh
     * @date  2025/03/06 11:34
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑钉钉考勤-员工白名单")
    @CommonLog("编辑钉钉考勤-员工白名单")
//    @SaCheckPermission("/sys/emp/whiteList/edit")
    @PostMapping("/sys/emp/whiteList/edit")
    public CommonResult<String> edit(@RequestBody @Valid AttendWhiteListEditParam attendWhiteListEditParam) {
        attendWhiteListService.edit(attendWhiteListEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除钉钉考勤-员工白名单
     *
     * @author pzh
     * @date  2025/03/06 11:34
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除钉钉考勤-员工白名单")
    @CommonLog("删除钉钉考勤-员工白名单")
//    @SaCheckPermission("/sys/emp/whiteList/delete")
    @PostMapping("/sys/emp/whiteList/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<AttendWhiteListIdParam> attendWhiteListIdParamList) {
        attendWhiteListService.delete(attendWhiteListIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取钉钉考勤-员工白名单详情
     *
     * @author pzh
     * @date  2025/03/06 11:34
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取钉钉考勤-员工白名单详情")
//    @SaCheckPermission("/sys/emp/whiteList/detail")
    @GetMapping("/sys/emp/whiteList/detail")
    public CommonResult<AttendWhiteList> detail(@Valid AttendWhiteListIdParam attendWhiteListIdParam) {
        return CommonResult.data(attendWhiteListService.detail(attendWhiteListIdParam));
    }
}
