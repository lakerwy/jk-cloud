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
package vip.xiaonuo.sys.modular.hr.conference.rezPeriod.controller;

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
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.entity.ConferenceRezPeriod;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.param.ConferenceRezPeriodAddParam;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.param.ConferenceRezPeriodEditParam;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.param.ConferenceRezPeriodIdParam;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.param.ConferenceRezPeriodPageParam;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.service.ConferenceRezPeriodService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 会议预约-周期预约控制器
 *
 * @author ljy
 * @date  2023/11/09 22:47
 */
@Api(tags = "会议预约-周期预约控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ConferenceRezPeriodController {

    @Resource
    private ConferenceRezPeriodService conferenceRezPeriodService;

    /**
     * 获取会议预约-周期预约分页
     *
     * @author ljy
     * @date  2023/11/09 22:47
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取会议预约-周期预约分页")
    @SaCheckPermission("/sys/rezPeriod/page")
    @GetMapping("/sys/rezPeriod/page")
    public CommonResult<Page<ConferenceRezPeriod>> page(ConferenceRezPeriodPageParam conferenceRezPeriodPageParam) {
        return CommonResult.data(conferenceRezPeriodService.page(conferenceRezPeriodPageParam));
    }

    /**
     * 添加会议预约-周期预约
     *
     * @author ljy
     * @date  2023/11/09 22:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加会议预约-周期预约")
    @CommonLog("添加会议预约-周期预约")
    @SaCheckPermission("/sys/rezPeriod/add")
    @PostMapping("/sys/rezPeriod/add")
    public CommonResult<String> add(@RequestBody @Valid ConferenceRezPeriodAddParam conferenceRezPeriodAddParam) {
        conferenceRezPeriodService.add(conferenceRezPeriodAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑会议预约-周期预约
     *
     * @author ljy
     * @date  2023/11/09 22:47
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑会议预约-周期预约")
    @CommonLog("编辑会议预约-周期预约")
    @SaCheckPermission("/sys/rezPeriod/edit")
    @PostMapping("/sys/rezPeriod/edit")
    public CommonResult<String> edit(@RequestBody @Valid ConferenceRezPeriodEditParam conferenceRezPeriodEditParam) {
        conferenceRezPeriodService.edit(conferenceRezPeriodEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除会议预约-周期预约
     *
     * @author ljy
     * @date  2023/11/09 22:47
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除会议预约-周期预约")
    @CommonLog("删除会议预约-周期预约")
    @SaCheckPermission("/sys/rezPeriod/delete")
    @PostMapping("/sys/rezPeriod/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ConferenceRezPeriodIdParam> conferenceRezPeriodIdParamList) {
        conferenceRezPeriodService.delete(conferenceRezPeriodIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取会议预约-周期预约详情
     *
     * @author ljy
     * @date  2023/11/09 22:47
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取会议预约-周期预约详情")
    @SaCheckPermission("/sys/rezPeriod/detail")
    @GetMapping("/sys/rezPeriod/detail")
    public CommonResult<ConferenceRezPeriod> detail(@Valid ConferenceRezPeriodIdParam conferenceRezPeriodIdParam) {
        return CommonResult.data(conferenceRezPeriodService.detail(conferenceRezPeriodIdParam));
    }
}
