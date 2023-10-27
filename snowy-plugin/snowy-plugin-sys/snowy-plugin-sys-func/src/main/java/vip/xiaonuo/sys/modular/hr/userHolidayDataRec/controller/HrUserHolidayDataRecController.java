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
package vip.xiaonuo.sys.modular.hr.userHolidayDataRec.controller;

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
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.entity.HrUserHolidayDataRec;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.param.HrUserHolidayDataRecAddParam;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.param.HrUserHolidayDataRecEditParam;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.param.HrUserHolidayDataRecIdParam;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.param.HrUserHolidayDataRecPageParam;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.service.HrUserHolidayDataRecService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 公司员工假期增减详情数据控制器
 *
 * @author ljy
 * @date  2023/10/26 09:21
 */
@Api(tags = "公司员工假期增减详情数据控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrUserHolidayDataRecController {

    @Resource
    private HrUserHolidayDataRecService hrUserHolidayDataRecService;

    /**
     * 获取公司员工假期增减详情数据分页
     *
     * @author ljy
     * @date  2023/10/26 09:21
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取公司员工假期增减详情数据分页")
    //@SaCheckPermission("/sys/userHolidayDataRec/page")
    @GetMapping("/sys/userHolidayDataRec/page")
    public CommonResult<Page<HrUserHolidayDataRec>> page(HrUserHolidayDataRecPageParam hrUserHolidayDataRecPageParam) {
        return CommonResult.data(hrUserHolidayDataRecService.page(hrUserHolidayDataRecPageParam));
    }

    /**
     * 添加公司员工假期增减详情数据
     *
     * @author ljy
     * @date  2023/10/26 09:21
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加公司员工假期增减详情数据")
    @CommonLog("添加公司员工假期增减详情数据")
    //@SaCheckPermission("/sys/userHolidayDataRec/add")
    @PostMapping("/sys/userHolidayDataRec/add")
    public CommonResult<String> add(@RequestBody @Valid HrUserHolidayDataRecAddParam hrUserHolidayDataRecAddParam) {
        hrUserHolidayDataRecService.add(hrUserHolidayDataRecAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑公司员工假期增减详情数据
     *
     * @author ljy
     * @date  2023/10/26 09:21
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑公司员工假期增减详情数据")
    @CommonLog("编辑公司员工假期增减详情数据")
    //@SaCheckPermission("/sys/userHolidayDataRec/edit")
    @PostMapping("/sys/userHolidayDataRec/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrUserHolidayDataRecEditParam hrUserHolidayDataRecEditParam) {
        hrUserHolidayDataRecService.edit(hrUserHolidayDataRecEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除公司员工假期增减详情数据
     *
     * @author ljy
     * @date  2023/10/26 09:21
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除公司员工假期增减详情数据")
    @CommonLog("删除公司员工假期增减详情数据")
    //@SaCheckPermission("/sys/userHolidayDataRec/delete")
    @PostMapping("/sys/userHolidayDataRec/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrUserHolidayDataRecIdParam> hrUserHolidayDataRecIdParamList) {
        hrUserHolidayDataRecService.delete(hrUserHolidayDataRecIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取公司员工假期增减详情数据详情
     *
     * @author ljy
     * @date  2023/10/26 09:21
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取公司员工假期增减详情数据详情")
    //@SaCheckPermission("/sys/userHolidayDataRec/detail")
    @GetMapping("/sys/userHolidayDataRec/detail")
    public CommonResult<HrUserHolidayDataRec> detail(@Valid HrUserHolidayDataRecIdParam hrUserHolidayDataRecIdParam) {
        return CommonResult.data(hrUserHolidayDataRecService.detail(hrUserHolidayDataRecIdParam));
    }
}
