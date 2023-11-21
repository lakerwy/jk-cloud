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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.sys.modular.hr.conference.config.entity.ConferenceConfig;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceTimeDataParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.*;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceTimeData;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 会议预约-预约会议表数据控制器
 *
 * @author ljy
 * @date  2023/11/05 23:13
 */
@Api(tags = "会议预约-预约会议表数据控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ConferenceRezController {

    @Resource
    private ConferenceRezService conferenceRezService;

    /**
     * 获取会议预约-预约会议表数据分页
     *
     * @author ljy
     * @date  2023/11/05 23:13
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取会议预约-预约会议表数据分页")
    //@SaCheckPermission("/sys/rez/page")
    @GetMapping("/sys/rez/page")
    public CommonResult<Page<ConferenceRez>> page(ConferenceRezPageParam conferenceRezPageParam) {
        return CommonResult.data(conferenceRezService.page(conferenceRezPageParam));
    }

    /**
     * 添加会议预约-预约会议表数据
     *
     * @author ljy
     * @date  2023/11/05 23:13
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加会议预约-预约会议表数据")
    @CommonLog("添加会议预约-预约会议表数据")
    //@SaCheckPermission("/sys/rez/add")
    @PostMapping("/sys/rez/add")
    public CommonResult<String> add(@RequestBody @Valid ConferenceRezAddParam conferenceRezAddParam) {
        conferenceRezService.add(conferenceRezAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑会议预约-预约会议表数据
     *
     * @author ljy
     * @date  2023/11/05 23:13
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("会议审核-预约会议表数据")
    @CommonLog("会议审核-预约会议表数据")
    //@SaCheckPermission("/sys/rez/edit")
    @PostMapping("/sys/rez/edit")
    public CommonResult<String> edit(@RequestBody @Valid ConferenceRezEditParam conferenceRezEditParam) {
        conferenceRezService.edit(conferenceRezEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除会议预约-预约会议表数据
     *
     * @author ljy
     * @date  2023/11/05 23:13
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除会议预约-预约会议表数据")
    @CommonLog("删除会议预约-预约会议表数据")
    //@SaCheckPermission("/sys/rez/delete")
    @PostMapping("/sys/rez/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ConferenceRezIdParam> conferenceRezIdParamList) {
        conferenceRezService.delete(conferenceRezIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取会议预约-预约会议表数据详情
     *
     * @author ljy
     * @date  2023/11/05 23:13
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取会议预约-预约会议表数据详情")
    //@SaCheckPermission("/sys/rez/detail")
    @GetMapping("/sys/rez/detail")
    public CommonResult<ConferenceRez> detail(@Valid ConferenceRezIdParam conferenceRezIdParam) {
        return CommonResult.data(conferenceRezService.detail(conferenceRezIdParam));
    }

    /**
     * 获取会议预约-预约会议表数据详情
     *
     * @author ljy
     * @date  2023/11/05 23:13
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取会议预约-查看可预约会议数据")
    //@SaCheckPermission("/sys/rez/conferenceRoom")
    @GetMapping("/sys/rez/conferenceRoom")
    public CommonResult<List<ConferenceConfig>> conferenceRoom(@Valid ConferenceRoomParam conferenceRoomParam) {
        return CommonResult.data(conferenceRezService.conferenceRoom(conferenceRoomParam));
    }

    /**
     * 获取会议预约-时间列表-查看可预约会议数据
     *
     * @author ljy
     * @date  2023/11/05 23:13
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取会议预约-时间列表-查看可预约会议数据")
    //@SaCheckPermission("/sys/rez/conferenceTimeList")
    @GetMapping("/sys/rez/conferenceTimeList")
    public CommonResult<List<ConferenceTimeData>> conferenceList(@Valid ConferenceTimeDataParam conferenceTimeDataParam) {
        return CommonResult.data(conferenceRezService.conferenceList(conferenceTimeDataParam));
    }

}
