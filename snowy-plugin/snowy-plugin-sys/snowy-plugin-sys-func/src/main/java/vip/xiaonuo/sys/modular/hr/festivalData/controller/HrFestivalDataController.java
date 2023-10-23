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
package vip.xiaonuo.sys.modular.hr.festivalData.controller;

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
import vip.xiaonuo.sys.modular.hr.festivalData.entity.HrFestivalData;
import vip.xiaonuo.sys.modular.hr.festivalData.param.HrFestivalDataAddParam;
import vip.xiaonuo.sys.modular.hr.festivalData.param.HrFestivalDataEditParam;
import vip.xiaonuo.sys.modular.hr.festivalData.param.HrFestivalDataIdParam;
import vip.xiaonuo.sys.modular.hr.festivalData.param.HrFestivalDataPageParam;
import vip.xiaonuo.sys.modular.hr.festivalData.service.HrFestivalDataService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 法定节假日数据控制器
 *
 * @author ljy
 * @date  2023/10/23 17:55
 */
@Api(tags = "法定节假日数据控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrFestivalDataController {

    @Resource
    private HrFestivalDataService hrFestivalDataService;

    /**
     * 获取法定节假日数据分页
     *
     * @author ljy
     * @date  2023/10/23 17:55
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取法定节假日数据分页")
    @SaCheckPermission("/sys/festivalData/page")
    @GetMapping("/sys/festivalData/page")
    public CommonResult<Page<HrFestivalData>> page(HrFestivalDataPageParam hrFestivalDataPageParam) {
        return CommonResult.data(hrFestivalDataService.page(hrFestivalDataPageParam));
    }

    /**
     * 添加法定节假日数据
     *
     * @author ljy
     * @date  2023/10/23 17:55
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加法定节假日数据")
    @CommonLog("添加法定节假日数据")
    @SaCheckPermission("/sys/festivalData/add")
    @PostMapping("/sys/festivalData/add")
    public CommonResult<String> add(@RequestBody @Valid HrFestivalDataAddParam hrFestivalDataAddParam) {
        hrFestivalDataService.add(hrFestivalDataAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑法定节假日数据
     *
     * @author ljy
     * @date  2023/10/23 17:55
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑法定节假日数据")
    @CommonLog("编辑法定节假日数据")
    @SaCheckPermission("/sys/festivalData/edit")
    @PostMapping("/sys/festivalData/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrFestivalDataEditParam hrFestivalDataEditParam) {
        hrFestivalDataService.edit(hrFestivalDataEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除法定节假日数据
     *
     * @author ljy
     * @date  2023/10/23 17:55
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除法定节假日数据")
    @CommonLog("删除法定节假日数据")
    @SaCheckPermission("/sys/festivalData/delete")
    @PostMapping("/sys/festivalData/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrFestivalDataIdParam> hrFestivalDataIdParamList) {
        hrFestivalDataService.delete(hrFestivalDataIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取法定节假日数据详情
     *
     * @author ljy
     * @date  2023/10/23 17:55
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取法定节假日数据详情")
    @SaCheckPermission("/sys/festivalData/detail")
    @GetMapping("/sys/festivalData/detail")
    public CommonResult<HrFestivalData> detail(@Valid HrFestivalDataIdParam hrFestivalDataIdParam) {
        return CommonResult.data(hrFestivalDataService.detail(hrFestivalDataIdParam));
    }
}
