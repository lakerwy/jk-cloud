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

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.json.JSONObject;
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
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceTimeDataParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRezPeriod;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.*;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezPeriodService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.RuanLianApiService;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceTimeData;

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
public class ConferenceRezController {

    @Resource
    private ConferenceRezService conferenceRezService;
    @Resource
    private ConferenceRezPeriodService conferenceRezPeriodService;
    @Resource
    private RuanLianApiService ruanLianApiService;

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
}
