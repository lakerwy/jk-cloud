package vip.xiaonuo.biz.modular.rule.controller;

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
import vip.xiaonuo.biz.modular.rule.entity.BizRuleManage;
import vip.xiaonuo.biz.modular.rule.param.BizRuleMangeAddParam;
import vip.xiaonuo.biz.modular.rule.param.BizRuleMangeEditParam;
import vip.xiaonuo.biz.modular.rule.param.BizRuleMangeIdParam;
import vip.xiaonuo.biz.modular.rule.param.BizRuleMangePageParam;
import vip.xiaonuo.biz.modular.rule.service.BizRuleManageService;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Api(tags = "业务表-行政规章-行政规章制度表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizRuleManageController {
    @Resource
    private BizRuleManageService bizRoleManageService;

    @ApiOperationSupport(order = 1)
    @ApiOperation("规章-规章管理分页")
    @GetMapping("/biz/rule/page")
    public CommonResult<Page<BizRuleManage>> page(BizRuleMangePageParam bizRuleMangePageParam) {
        return CommonResult.data(bizRoleManageService.page(bizRuleMangePageParam));
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation("规章-添加规章管理")
    @PostMapping("/biz/rule/add")
    public CommonResult<String> add(@RequestBody @Valid BizRuleMangeAddParam bizRuleMangeAddParam) {
        bizRoleManageService.add(bizRuleMangeAddParam);
        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 3)
    @ApiOperation("规章-编辑规章管理")
    @PostMapping("/biz/rule/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizRuleMangeEditParam bizRuleMangeEditParam) {
        bizRoleManageService.edit(bizRuleMangeEditParam);
        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 4)
    @ApiOperation("规章-删除规章管理")
    @PostMapping("/biz/rule/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "不得为空") CommonValidList<BizRuleMangeIdParam> bizRuleMangeIdParamCommonValidList) {
        bizRoleManageService.delete(bizRuleMangeIdParamCommonValidList);
        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 5)
    @ApiOperation("规章-查看规章管理")
    @GetMapping("/biz/rule/detail")
    public CommonResult<BizRuleManage> detail(@Valid BizRuleMangeIdParam req) {
        return CommonResult.data(bizRoleManageService.detail(req));

    }
}
