/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.sys.modular.hr.salary.controller;

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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryStandard;
import vip.xiaonuo.sys.modular.hr.salary.param.*;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.PersonnelParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.StandardParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.ThreePageParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryStandardService;
import vip.xiaonuo.sys.modular.hr.salary.vo.PersonnelInfoVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryStandardVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.ThreeSalaryStandardVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 工资系统-月度工资标准控制器
 *
 * @author yong.ye
 * @date  2024/02/26 09:36
 */
@Api(tags = "工资系统-月度工资标准控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SalaryStandardController {

    @Resource
    private SalaryStandardService salaryStandardService;

    /**
     * 获取工资系统-月度工资标准分页
     *
     * @author yong.ye
     * @date  2024/02/26 09:36
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取工资系统-月度工资标准分页")
    @GetMapping("/sys/hr/salary/standard/page")
    public CommonResult<Page<SalaryStandard>> page(SalaryStandardPageParam salaryStandardPageParam) {
        return CommonResult.data(salaryStandardService.page(salaryStandardPageParam));
    }

    /**
     * 添加工资系统-月度工资标准
     *
     * @author yong.ye
     * @date  2024/02/26 09:36
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加工资系统-月度工资标准")
    @CommonLog("添加工资系统-月度工资标准")
    @PostMapping("/sys/hr/salary/standard/add")
    public CommonResult<String> add(@RequestBody @Valid SalaryStandardAddParam salaryStandardAddParam) {
        salaryStandardService.add(salaryStandardAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑工资系统-月度工资标准
     *
     * @author yong.ye
     * @date  2024/02/26 09:36
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑工资系统-月度工资标准")
    @CommonLog("编辑工资系统-月度工资标准")
    @PostMapping("/sys/hr/salary/standard/edit")
    public CommonResult<String> edit(@RequestBody @Valid SalaryStandardEditParam salaryStandardEditParam) {
        salaryStandardService.edit(salaryStandardEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除工资系统-月度工资标准
     *
     * @author yong.ye
     * @date  2024/02/26 09:36
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除工资系统-月度工资标准")
    @CommonLog("删除工资系统-月度工资标准")
    @PostMapping("/sys/hr/salary/standard/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SalaryStandardIdParam> salaryStandardIdParamList) {
        salaryStandardService.delete(salaryStandardIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取工资系统-月度工资标准详情
     *
     * @author yong.ye
     * @date  2024/02/26 09:36
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取工资系统-月度工资标准详情")
    @GetMapping("/sys/hr/salary/standard/detail")
    public CommonResult<SalaryStandard> detail(@Valid SalaryStandardIdParam salaryStandardIdParam) {
        return CommonResult.data(salaryStandardService.detail(salaryStandardIdParam));
    }


    /**
     * 工资系统-月度工资标准 - 【复制上月工资标准】
     *
     * @author yong.ye
     * @date  2024/02/26 09:36
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("工资系统-月度工资标准 - 【复制上月工资标准】")
    @CommonLog("工资系统-月度工资标准 - 【复制上月工资标准】")
    @PostMapping("/sys/hr/salary/standard/copy")
    public CommonResult<String> copy() {
        salaryStandardService.copy();
        return CommonResult.ok();
    }



    /**
     *  花名册（总部|分公司） -- 全部返回
     *
     * @author yong.ye
     * @date  2024/03/05 09:36
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("花名册（总部|分公司）")
    @GetMapping("/sys/hr/salary/standard/personnelList")
    public CommonResult<List<PersonnelInfoVo>> personnelList(PersonnelParam personnelParam) {
        return CommonResult.data(salaryStandardService.personnelList(personnelParam));
    }

    /**
     *  根据 所属年月 获取当前年月的工资标准名单（即：待发工资人员名单）
     *
     * @author yong.ye
     * @date  2024/03/05 09:36
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("根据 所属年月 获取当前年月的工资标准名单（即：待发工资人员名单）")
    @GetMapping("/sys/hr/salary/standard/listByYearMonth")
    public CommonResult<List<SalaryStandardVo>> listByYearMonth(StandardParam standardParam) {
        return CommonResult.data(salaryStandardService.listByYearMonth(standardParam));
    }



    /**
     * 获取工资系统-月度工资标准 - 【分为3个列表】
     *
     * @author yong.ye
     * @date  2024/03/06 09:36
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("获取工资系统-月度工资标准 - 【分为3个列表】")
    @GetMapping("/sys/hr/salary/standard/threePageList")
    public CommonResult<ThreeSalaryStandardVo> threePageList(ThreePageParam threePageParam) {
        return CommonResult.data(salaryStandardService.threePageList(threePageParam));
    }

}
