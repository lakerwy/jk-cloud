package vip.xiaonuo.sys.modular.hr.school.controller;

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
import vip.xiaonuo.sys.modular.hr.school.entity.SchoolDept;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolDeptAddParam;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolDeptEditParam;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolDeptIdParam;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolDeptPageParam;
import vip.xiaonuo.sys.modular.hr.school.service.SchoolDeptService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 校招 - （应聘者）- 推送部门流程控制器
 *
 * @author yong.ye
 * @date  2024/09/03 10:47
 */
@Api(tags = "校招 - （应聘者）- 推送部门流程控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SchoolDeptController {

    @Resource
    private SchoolDeptService schoolDeptService;

    /**
     * 获取校招 - （应聘者）- 推送部门流程分页
     *
     * @author yong.ye
     * @date  2024/09/03 10:47
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取校招 - （应聘者）- 推送部门流程分页")
    @GetMapping("/sys/hr/school/dept/page")
    public CommonResult<Page<SchoolDept>> page(SchoolDeptPageParam schoolDeptPageParam) {
        return CommonResult.data(schoolDeptService.page(schoolDeptPageParam));
    }

    /**
     * 添加校招 - （应聘者）- 推送部门流程
     *
     * @author yong.ye
     * @date  2024/09/03 10:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加校招 - （应聘者）- 推送部门流程")
    @CommonLog("添加校招 - （应聘者）- 推送部门流程")
    @PostMapping("/sys/hr/school/dept/add")
    public CommonResult<String> add(@RequestBody @Valid SchoolDeptAddParam schoolDeptAddParam) {
        schoolDeptService.add(schoolDeptAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑校招 - （应聘者）- 推送部门流程
     *
     * @author yong.ye
     * @date  2024/09/03 10:47
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑校招 - （应聘者）- 推送部门流程")
    @CommonLog("编辑校招 - （应聘者）- 推送部门流程")
    @PostMapping("/sys/hr/school/dept/edit")
    public CommonResult<String> edit(@RequestBody @Valid SchoolDeptEditParam schoolDeptEditParam) {
        schoolDeptService.edit(schoolDeptEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除校招 - （应聘者）- 推送部门流程
     *
     * @author yong.ye
     * @date  2024/09/03 10:47
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除校招 - （应聘者）- 推送部门流程")
    @CommonLog("删除校招 - （应聘者）- 推送部门流程")
    @PostMapping("/sys/hr/school/dept/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SchoolDeptIdParam> schoolDeptIdParamList) {
        schoolDeptService.delete(schoolDeptIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取校招 - （应聘者）- 推送部门流程详情
     *
     * @author yong.ye
     * @date  2024/09/03 10:47
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取校招 - （应聘者）- 推送部门流程详情")
    @GetMapping("/sys/hr/school/dept/detail")
    public CommonResult<SchoolDept> detail(@Valid SchoolDeptIdParam schoolDeptIdParam) {
        return CommonResult.data(schoolDeptService.detail(schoolDeptIdParam));
    }
}
