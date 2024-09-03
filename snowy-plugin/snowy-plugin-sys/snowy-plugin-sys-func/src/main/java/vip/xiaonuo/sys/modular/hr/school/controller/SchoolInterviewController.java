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
import vip.xiaonuo.sys.modular.hr.school.entity.SchoolInterview;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolInterviewAddParam;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolInterviewEditParam;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolInterviewIdParam;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolInterviewPageParam;
import vip.xiaonuo.sys.modular.hr.school.service.SchoolInterviewService;
import vip.xiaonuo.sys.modular.hr.school.vo.SchoolInterviewVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 校招 - （应聘者）- 面试入职流程控制器
 *
 * @author yong.ye
 * @date  2024/09/03 10:46
 */
@Api(tags = "校招 - （应聘者）- 面试入职流程控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SchoolInterviewController {

    @Resource
    private SchoolInterviewService schoolInterviewService;

    /**
     * 获取校招 - （应聘者）- 面试入职流程分页
     *
     * @author yong.ye
     * @date  2024/09/03 10:46
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取校招 - （应聘者）- 面试入职流程分页")
    @GetMapping("/sys/hr/school/interview/page")
    public CommonResult<Page<SchoolInterviewVo>> page(SchoolInterviewPageParam schoolInterviewPageParam) {
        return CommonResult.data(schoolInterviewService.page(schoolInterviewPageParam));
    }

    /**
     * 添加校招 - （应聘者）- 面试入职流程
     *
     * @author yong.ye
     * @date  2024/09/03 10:46
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加校招 - （应聘者）- 面试入职流程")
    @CommonLog("添加校招 - （应聘者）- 面试入职流程")
    @PostMapping("/sys/hr/school/interview/add")
    public CommonResult<String> add(@RequestBody @Valid SchoolInterviewAddParam schoolInterviewAddParam) {
        schoolInterviewService.add(schoolInterviewAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑校招 - （应聘者）- 面试入职流程
     *
     * @author yong.ye
     * @date  2024/09/03 10:46
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑校招 - （应聘者）- 面试入职流程")
    @CommonLog("编辑校招 - （应聘者）- 面试入职流程")
    @PostMapping("/sys/hr/school/interview/edit")
    public CommonResult<String> edit(@RequestBody @Valid SchoolInterviewEditParam schoolInterviewEditParam) {
        schoolInterviewService.edit(schoolInterviewEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除校招 - （应聘者）- 面试入职流程
     *
     * @author yong.ye
     * @date  2024/09/03 10:46
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除校招 - （应聘者）- 面试入职流程")
    @CommonLog("删除校招 - （应聘者）- 面试入职流程")
    @PostMapping("/sys/hr/school/interview/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SchoolInterviewIdParam> schoolInterviewIdParamList) {
        schoolInterviewService.delete(schoolInterviewIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取校招 - （应聘者）- 面试入职流程详情
     *
     * @author yong.ye
     * @date  2024/09/03 10:46
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取校招 - （应聘者）- 面试入职流程详情")
    @GetMapping("/sys/hr/school/interview/detail")
    public CommonResult<SchoolInterviewVo> detail(@Valid SchoolInterviewIdParam schoolInterviewIdParam) {
        return CommonResult.data(schoolInterviewService.detail(schoolInterviewIdParam));
    }
}
