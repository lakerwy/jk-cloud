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
import vip.xiaonuo.sys.modular.hr.school.entity.SchoolResume;
import vip.xiaonuo.sys.modular.hr.school.param.*;
import vip.xiaonuo.sys.modular.hr.school.service.SchoolResumeService;
import vip.xiaonuo.sys.modular.hr.school.vo.SchoolResumeVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 校招 - （应聘者）简历库信息控制器
 *
 * @author yong.ye
 * @date  2024/09/03 10:44
 */
@Api(tags = "校招 - （应聘者）简历库信息控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SchoolResumeController {

    @Resource
    private SchoolResumeService schoolResumeService;

    /**
     * 获取校招 - （应聘者）简历库信息分页
     *
     * @author yong.ye
     * @date  2024/09/03 10:44
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取校招 - （应聘者）简历库信息分页")
    @GetMapping("/sys/hr/school/resume/page")
    public CommonResult<Page<SchoolResumeVo>> page(SchoolResumePageParam schoolResumePageParam) {
        return CommonResult.data(schoolResumeService.page(schoolResumePageParam));
    }

    /**
     * 添加校招 - （应聘者）简历库信息
     *
     * @author yong.ye
     * @date  2024/09/03 10:44
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加校招 - （应聘者）简历库信息")
    @CommonLog("添加校招 - （应聘者）简历库信息")
    @PostMapping("/sys/hr/school/resume/add")
    public CommonResult<String> add(@RequestBody @Valid SchoolResumeAddParam schoolResumeAddParam) {
        schoolResumeService.add(schoolResumeAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑校招 - （应聘者）简历库信息
     *
     * @author yong.ye
     * @date  2024/09/03 10:44
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑校招 - （应聘者）简历库信息")
    @CommonLog("编辑校招 - （应聘者）简历库信息")
    @PostMapping("/sys/hr/school/resume/edit")
    public CommonResult<String> edit(@RequestBody @Valid SchoolResumeEditParam schoolResumeEditParam) {
        schoolResumeService.edit(schoolResumeEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除校招 - （应聘者）简历库信息
     *
     * @author yong.ye
     * @date  2024/09/03 10:44
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除校招 - （应聘者）简历库信息")
    @CommonLog("删除校招 - （应聘者）简历库信息")
    @PostMapping("/sys/hr/school/resume/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SchoolResumeIdParam> schoolResumeIdParamList) {
        schoolResumeService.delete(schoolResumeIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取校招 - （应聘者）简历库信息详情
     *
     * @author yong.ye
     * @date  2024/09/03 10:44
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取校招 - （应聘者）简历库信息详情")
    @GetMapping("/sys/hr/school/resume/detail")
    public CommonResult<SchoolResume> detail(@Valid SchoolResumeIdParam schoolResumeIdParam) {
        return CommonResult.data(schoolResumeService.detail(schoolResumeIdParam));
    }


    /**
     * 校招 导入 简历
     * @author yong.ye
     * @date  2024/09/03 10:44
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("校招 导入 简历")
    @CommonLog("校招 导入 简历")
    @PostMapping("/sys/hr/school/resume/batchImport")
    public CommonResult<String> batchImport(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                            CommonValidList<SchoolResumeAddParam> schoolResumeAddParamList) {
        schoolResumeService.batchImport(schoolResumeAddParamList);
        return CommonResult.ok();
    }


    /**
     * 校招简历 - 上传
     * @author yong.ye
     * @date  2024/09/03 10:44
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("校招简历 - 上传")
    @CommonLog("校招简历 - 上传")
    @PostMapping("/sys/hr/school/resume/upload")
    public CommonResult<String> upload(@RequestBody @Valid ResumeUploadParam resumeUploadParam) {
        schoolResumeService.upload(resumeUploadParam);
        return CommonResult.ok();
    }


    /**
     * 校招 批量新增 简历
     * @author yong.ye
     * @date  2024/09/03 10:44
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("校招 批量新增 简历")
    @CommonLog("校招 批量新增 简历")
    @PostMapping("/sys/hr/school/resume/batchAdd")
    public CommonResult<String> batchAdd(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                             CommonValidList<SchoolResumeAddParam> schoolResumeAddParamList) {
        schoolResumeService.batchAdd(schoolResumeAddParamList);
        return CommonResult.ok();
    }


    /**
     * 校招应聘者填写个人信息，提交
     * @author yong.ye
     * @date  2024/09/03 10:44
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("应聘者填写个人信息，提交")
    @CommonLog("应聘者填写个人信息，提交")
    @PostMapping("/sys/hr/school/resume/submit")
    public CommonResult<String> submit(@RequestBody @Valid ResumeParam resumeParam) {
        schoolResumeService.submit(resumeParam);
        return CommonResult.ok();
    }


    /**
     * 校招,人资初审
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("校招,人资初审")
    @CommonLog("校招,人资初审")
    @PostMapping("/sys/hr/school/resume/status")
    public CommonResult<String> status(@RequestBody @Valid ResumeStatusParam resumeStatusParam) {
        schoolResumeService.status(resumeStatusParam);
        return CommonResult.ok();
    }
}
