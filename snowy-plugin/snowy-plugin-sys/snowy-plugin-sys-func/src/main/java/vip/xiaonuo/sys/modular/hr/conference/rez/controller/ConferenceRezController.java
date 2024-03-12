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
package vip.xiaonuo.sys.modular.hr.conference.rez.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.sys.modular.hr.conference.DingDingRec.DingdingCalendarUtil;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRezPeriod;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceWorkArrange;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.*;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezPeriodService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceWorkArrangeService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 会议模块-预约会议控制器
 *
 * @author ljy
 * @date  2023/12/01 23:35
 */
@Api(tags = "会议模块-预约会议控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
@Slf4j
public class ConferenceRezController {
    @Resource
    private ConferenceRezService conferenceRezService;
    @Resource
    private ConferenceRezPeriodService conferenceRezPeriodService;
    @Resource
    ConferenceWorkArrangeService conferenceWorkArrangeService;
    @Resource
    DingdingCalendarUtil dingdingCalendarUtil;

    /**
     * 获取会议模块-预约会议分页
     *
     * @author ljy
     * @date  2023/12/01 23:35
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取会议模块-预约会议分页")
    //@SaCheckPermission("/sys/rez/page")
    @GetMapping("/sys/rez/page")
    public CommonResult<Page<ConferenceRez>> page(ConferenceRezPageParam conferenceRezPageParam) {
        return CommonResult.data(conferenceRezService.page(conferenceRezPageParam));
    }

    /**
     * 获取会议模块-预约会议详情
     *
     * @author ljy
     * @date  2023/12/01 23:35
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取会议模块-预约会议详情")
    //@SaCheckPermission("/sys/rez/detail")
    @GetMapping("/sys/rez/detail")
    public CommonResult<ConferenceRez> detail(@Valid ConferenceRezIdParam conferenceRezIdParam) {
        return CommonResult.data(conferenceRezService.detail(conferenceRezIdParam));
    }

    /** 周期预约*/
    /**
     * 获取会议预约-周期预约分页
     * @author ljy
     * @date  2023/11/09 22:47
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取会议预约-周期预约分页")
    //@SaCheckPermission("/sys/rezPeriod/page")
    @GetMapping("/sys/rezPeriod/page")
    public CommonResult<Page<ConferenceRezPeriod>> periodPage(ConferenceRezPeriodPageParam conferenceRezPeriodPageParam) {
        return CommonResult.data(conferenceRezPeriodService.page(conferenceRezPeriodPageParam));
    }

    /**
     * 会议记录确认通知
     *
     * @author ljy
     * @date  2023/11/20 08:39
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("会议审核确认")
    @CommonLog("会议审核确认")
    //@SaCheckPermission("/sys/conferenceRecord/recordNotice")
    @GetMapping("/sys/rez/recordNotice")
    public CommonResult<String> recordNotice(@Valid String rezId) {
        conferenceRezService.recordNotice(rezId);
        return CommonResult.ok();
    }

    /** 工作部署模块接口 */
    /**
     * 获取会议模块-工作部署分页
     *
     * @author ljy
     * @date  2023/12/07 17:43
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("获取会议模块-工作部署分页")
    @GetMapping("/sys/workArrange/page")
    public CommonResult<Page<ConferenceWorkArrange>> workArrangePage(ConferenceWorkArrangePageParam conferenceWorkArrangePageParam) {
        return CommonResult.data(conferenceWorkArrangeService.page(conferenceWorkArrangePageParam));
    }

    /**
     * 添加会议模块-工作部署
     *
     * @author ljy
     * @date  2023/12/07 17:43
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("添加会议模块-工作部署")
    @CommonLog("添加会议模块-工作部署")
    @PostMapping("/sys/workArrange/add")
    public CommonResult<String> workArrangeAdd(@RequestBody @Valid ConferenceWorkArrangeAddParam conferenceWorkArrangeAddParam) {
        conferenceWorkArrangeService.add(conferenceWorkArrangeAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑会议模块-工作部署
     *
     * @author ljy
     * @date  2023/12/07 17:43
     */
    @ApiOperationSupport(order = 15)
    @ApiOperation("编辑会议模块-工作部署")
    @CommonLog("编辑会议模块-工作部署")
    @PostMapping("/sys/workArrange/edit")
    public CommonResult<String> workArrangeEdit(@RequestBody @Valid ConferenceWorkArrangeEditParam conferenceWorkArrangeEditParam) {
        conferenceWorkArrangeService.edit(conferenceWorkArrangeEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除会议模块-工作部署
     *
     * @author ljy
     * @date  2023/12/07 17:43
     */
    @ApiOperationSupport(order = 16)
    @ApiOperation("删除会议模块-工作部署")
    @CommonLog("删除会议模块-工作部署")
    @PostMapping("/sys/workArrange/delete")
    public CommonResult<String> workArrangeDelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<ConferenceWorkArrangeIdParam> conferenceWorkArrangeIdParamList) {
        conferenceWorkArrangeService.delete(conferenceWorkArrangeIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取会议模块-工作部署详情
     *
     * @author ljy
     * @date  2023/12/07 17:43
     */
    @ApiOperationSupport(order = 17)
    @ApiOperation("获取会议模块-工作部署详情")
    @GetMapping("/sys/workArrange/detail")
    public CommonResult<ConferenceWorkArrange> workArrangeDetail(@Valid ConferenceWorkArrangeIdParam conferenceWorkArrangeIdParam) {
        return CommonResult.data(conferenceWorkArrangeService.detail(conferenceWorkArrangeIdParam));
    }

    /**
     * 获取日程参与人列表接口
     *
     * @author ljy
     * @date  2024/03/12 15:43
     */
    @ApiOperationSupport(order = 18)
    @ApiOperation("获取日程参与人列表接口")
    @GetMapping("/sys/workArrange/getCalendarPart")
    public CommonResult<String > dingdingCalendarUtil(String unionId,String eventId,String xAcsDingtalkAccessToken) throws Exception{
        dingdingCalendarUtil.getCalendarPart(unionId,eventId,xAcsDingtalkAccessToken);
        return CommonResult.ok();
    }
}
