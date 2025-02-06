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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubGzItem;
import vip.xiaonuo.sys.modular.hr.sub.param.SubGzItemAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubGzItemEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubGzItemIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubGzItemPageParam;
import vip.xiaonuo.sys.modular.hr.sub.service.SubGzItemService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 分公司-工资发放 - 工资|奖金明细控制器
 *
 * @author yong.ye
 * @date  2025/02/06 09:11
 */
@Api(tags = "分公司-工资发放 - 工资|奖金明细控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SubGzItemController {

    @Resource
    private SubGzItemService subGzItemService;

    /**
     * 获取分公司-工资发放 - 工资|奖金明细分页
     *
     * @author yong.ye
     * @date  2025/02/06 09:11
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取分公司-工资发放 - 工资|奖金明细分页")
//    @SaCheckPermission("/sys/hr/sub/gzItem/page")
    @GetMapping("/sys/hr/sub/gzItem/page")
    public CommonResult<Page<SubGzItem>> page(SubGzItemPageParam subGzItemPageParam) {
        return CommonResult.data(subGzItemService.page(subGzItemPageParam));
    }

    /**
     * 添加分公司-工资发放 - 工资|奖金明细
     *
     * @author yong.ye
     * @date  2025/02/06 09:11
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加分公司-工资发放 - 工资|奖金明细")
    @CommonLog("添加分公司-工资发放 - 工资|奖金明细")
//    @SaCheckPermission("/sys/hr/sub/gzItem/add")
    @PostMapping("/sys/hr/sub/gzItem/add")
    public CommonResult<String> add(@RequestBody @Valid SubGzItemAddParam subGzItemAddParam) {
        subGzItemService.add(subGzItemAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑分公司-工资发放 - 工资|奖金明细
     *
     * @author yong.ye
     * @date  2025/02/06 09:11
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑分公司-工资发放 - 工资|奖金明细")
    @CommonLog("编辑分公司-工资发放 - 工资|奖金明细")
//    @SaCheckPermission("/sys/hr/sub/gzItem/edit")
    @PostMapping("/sys/hr/sub/gzItem/edit")
    public CommonResult<String> edit(@RequestBody @Valid SubGzItemEditParam subGzItemEditParam) {
        subGzItemService.edit(subGzItemEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除分公司-工资发放 - 工资|奖金明细
     *
     * @author yong.ye
     * @date  2025/02/06 09:11
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除分公司-工资发放 - 工资|奖金明细")
    @CommonLog("删除分公司-工资发放 - 工资|奖金明细")
//    @SaCheckPermission("/sys/hr/sub/gzItem/delete")
    @PostMapping("/sys/hr/sub/gzItem/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SubGzItemIdParam> subGzItemIdParamList) {
        subGzItemService.delete(subGzItemIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取分公司-工资发放 - 工资|奖金明细详情
     *
     * @author yong.ye
     * @date  2025/02/06 09:11
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取分公司-工资发放 - 工资|奖金明细详情")
//    @SaCheckPermission("/sys/hr/sub/gzItem/detail")
    @GetMapping("/sys/hr/sub/gzItem/detail")
    public CommonResult<SubGzItem> detail(@Valid SubGzItemIdParam subGzItemIdParam) {
        return CommonResult.data(subGzItemService.detail(subGzItemIdParam));
    }
}
