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

import cn.hutool.json.JSONObject;
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
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceTimeDataParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRezPeriod;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceWorkArrange;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.*;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezPeriodService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceWorkArrangeService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.RuanLianApiService;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceTimeData;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;

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
    private RuanLianApiService ruanLianApiService;
    @Resource
    private HrUserAttendanceService hrUserAttendanceService;
    @Resource
    ConferenceWorkArrangeService conferenceWorkArrangeService;

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
     * 添加会议模块-预约会议
     *
     * @author ljy
     * @date  2023/12/01 23:35
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加会议模块-预约会议")
    @CommonLog("添加会议模块-预约会议")
    //@SaCheckPermission("/sys/rez/add")
    @PostMapping("/sys/rez/add")
    public CommonResult<String> add(@RequestBody @Valid ConferenceRezAddParam conferenceRezAddParam) {
        conferenceRezService.add(conferenceRezAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑会议模块-预约会议
     *
     * @author ljy
     * @date  2023/12/01 23:35
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑会议模块-预约会议")
    @CommonLog("编辑会议模块-预约会议")
    //@SaCheckPermission("/sys/rez/edit")
    @PostMapping("/sys/rez/edit")
    public CommonResult<String> edit(@RequestBody @Valid ConferenceRezEditParam conferenceRezEditParam) {
        conferenceRezService.edit(conferenceRezEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除会议模块-预约会议
     *
     * @author ljy
     * @date  2023/12/01 23:35
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除会议模块-预约会议")
    @CommonLog("删除会议模块-预约会议")
    //@SaCheckPermission("/sys/rez/delete")
    @PostMapping("/sys/rez/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ConferenceRezIdParam> conferenceRezIdParamList) {
        conferenceRezService.delete(conferenceRezIdParamList);
        return CommonResult.ok();
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

    /**
     * 获取会议预约-时间列表-查看可预约会议数据
     * @author ljy
     * @date  2023/11/05 23:13
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取会议预约-时间列表-查看可预约会议数据")
    //@SaCheckPermission("/sys/rez/conferenceTimeList")
    @GetMapping("/sys/rez/conferenceTimeList")
    public CommonResult<List<ConferenceTimeData>> conferenceList(@Valid ConferenceTimeDataParam conferenceTimeDataParam) {
        return CommonResult.data(conferenceRezService.conferenceList(conferenceTimeDataParam));
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
     * 编辑会议预约-周期预约
     * @author ljy
     * @date  2023/11/09 22:47
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("编辑会议预约-周期预约")
    @CommonLog("编辑会议预约-周期预约")
    //@SaCheckPermission("/sys/rezPeriod/edit")
    @PostMapping("/sys/rezPeriod/edit")
    public CommonResult<String> periodEdit(@RequestBody @Valid ConferenceRezPeriodEditParam conferenceRezPeriodEditParam) {
        conferenceRezPeriodService.edit(conferenceRezPeriodEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除会议预约-周期预约
     * @author ljy
     * @date  2023/11/09 22:47
     */
    /*@ApiOperationSupport(order = 9)
    @ApiOperation("取消会议预约-周期预约")
    @CommonLog("取消会议预约-周期预约")
    //@SaCheckPermission("/sys/rezPeriod/delete")
    @PostMapping("/sys/rezPeriod/delete")
    public CommonResult<String> periodDelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<ConferenceRezPeriodIdParam> conferenceRezPeriodIdParamList) {
        conferenceRezPeriodService.delete(conferenceRezPeriodIdParamList);
        return CommonResult.ok();
    }*/

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

    /**
     * 软联预约会议
     *
     * @author ljy
     * @date  2023/11/20 08:39
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("软联预约会议")
    @CommonLog("软联预约会议")
    //@SaCheckPermission("/sys/conferenceRecord/setmeeting")
    @GetMapping("/sys/rez/setmeeting")
    public CommonResult<JSONObject> setmeeting(@Valid RuanLianParam ruanLianParam) throws IOException {
        return CommonResult.data(ruanLianApiService.setmeeting(ruanLianParam));
    }

    /**
     * 软联预约会议
     *
     * @author ljy
     * @date  2023/11/20 08:39
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("取消软联预约会议")
    @CommonLog("取消软联预约会议")
    //@SaCheckPermission("/sys/conferenceRecord/delmeeting")
    @GetMapping("/sys/rez/delmeeting")
    public CommonResult<String> delmeeting(@Valid String yyid){
        ruanLianApiService.delmeeting(yyid);
        return CommonResult.ok();
    }

    /**
     * 释放会议
     *
     * @author ljy
     * @date  2023/11/20 08:39
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("释放会议")
    @CommonLog("释放会议")
    @GetMapping("/sys/rez/freeRez")
    public CommonResult<String> freeRez(String rezId){
        conferenceRezService.freeRez(rezId);
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
     * 测试：获取钉钉预约会议数据
     * */
    @ApiOperationSupport(order = 18)
    @ApiOperation("会议-获取钉钉预约会议数据")
    @PostMapping("/sys/rez/dingCallBack")
    public CommonResult<String> callBack(
            @RequestParam(value = "msg_signature", required = false) String msg_signature, @RequestParam(value = "timestamp", required = false) String timeStamp,
            @RequestParam(value = "nonce", required = false) String nonce, @RequestBody(required = false) JSONObject json) {
        conferenceRezService.callBack(msg_signature,timeStamp,nonce,json);
        return CommonResult.ok();
    }
}
