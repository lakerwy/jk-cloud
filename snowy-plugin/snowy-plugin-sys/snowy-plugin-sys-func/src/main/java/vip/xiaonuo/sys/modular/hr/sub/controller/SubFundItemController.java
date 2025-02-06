package vip.xiaonuo.sys.modular.hr.sub.controller;

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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFundItem;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFundItemAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFundItemEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFundItemIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFundItemPageParam;
import vip.xiaonuo.sys.modular.hr.sub.service.SubFundItemService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 分公司-五险一金 - 社保|公积金明细控制器
 *
 * @author yong.ye
 * @date  2025/02/06 09:13
 */
@Api(tags = "分公司-五险一金 - 社保|公积金明细控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SubFundItemController {

    @Resource
    private SubFundItemService subFundItemService;

    /**
     * 获取分公司-五险一金 - 社保|公积金明细分页
     *
     * @author yong.ye
     * @date  2025/02/06 09:13
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取分公司-五险一金 - 社保|公积金明细分页")
//    @SaCheckPermission("/sys/hr/sub/fundItem/page")
    @GetMapping("/sys/hr/sub/fundItem/page")
    public CommonResult<Page<SubFundItem>> page(SubFundItemPageParam subFundItemPageParam) {
        return CommonResult.data(subFundItemService.page(subFundItemPageParam));
    }

    /**
     * 添加分公司-五险一金 - 社保|公积金明细
     *
     * @author yong.ye
     * @date  2025/02/06 09:13
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加分公司-五险一金 - 社保|公积金明细")
    @CommonLog("添加分公司-五险一金 - 社保|公积金明细")
//    @SaCheckPermission("/sys/hr/sub/fundItem/add")
    @PostMapping("/sys/hr/sub/fundItem/add")
    public CommonResult<String> add(@RequestBody @Valid SubFundItemAddParam subFundItemAddParam) {
        subFundItemService.add(subFundItemAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑分公司-五险一金 - 社保|公积金明细
     *
     * @author yong.ye
     * @date  2025/02/06 09:13
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑分公司-五险一金 - 社保|公积金明细")
    @CommonLog("编辑分公司-五险一金 - 社保|公积金明细")
//    @SaCheckPermission("/sys/hr/sub/fundItem/edit")
    @PostMapping("/sys/hr/sub/fundItem/edit")
    public CommonResult<String> edit(@RequestBody @Valid SubFundItemEditParam subFundItemEditParam) {
        subFundItemService.edit(subFundItemEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除分公司-五险一金 - 社保|公积金明细
     *
     * @author yong.ye
     * @date  2025/02/06 09:13
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除分公司-五险一金 - 社保|公积金明细")
    @CommonLog("删除分公司-五险一金 - 社保|公积金明细")
//    @SaCheckPermission("/sys/hr/sub/fundItem/delete")
    @PostMapping("/sys/hr/sub/fundItem/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SubFundItemIdParam> subFundItemIdParamList) {
        subFundItemService.delete(subFundItemIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取分公司-五险一金 - 社保|公积金明细详情
     *
     * @author yong.ye
     * @date  2025/02/06 09:13
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取分公司-五险一金 - 社保|公积金明细详情")
//    @SaCheckPermission("/sys/hr/sub/fundItem/detail")
    @GetMapping("/sys/hr/sub/fundItem/detail")
    public CommonResult<SubFundItem> detail(@Valid SubFundItemIdParam subFundItemIdParam) {
        return CommonResult.data(subFundItemService.detail(subFundItemIdParam));
    }
}
