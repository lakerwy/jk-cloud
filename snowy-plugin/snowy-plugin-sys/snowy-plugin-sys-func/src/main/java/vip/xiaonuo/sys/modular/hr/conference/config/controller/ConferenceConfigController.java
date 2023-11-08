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
package vip.xiaonuo.sys.modular.hr.conference.config.controller;

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
import vip.xiaonuo.sys.modular.hr.conference.config.entity.ConferenceConfig;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceConfigAddParam;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceConfigEditParam;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceConfigIdParam;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceConfigPageParam;
import vip.xiaonuo.sys.modular.hr.conference.config.service.ConferenceConfigService;


import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 会议预约-会议室数据控制器
 *
 * @author ljy
 * @date  2023/11/05 23:12
 */
@Api(tags = "会议预约-会议室数据控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ConferenceConfigController {

    @Resource
    private ConferenceConfigService conferenceConfigService;

    /**
     * 获取会议预约-会议室数据分页
     *
     * @author ljy
     * @date  2023/11/05 23:12
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取会议预约-会议室数据分页")
    //@SaCheckPermission("/sys/config/page")
    @GetMapping("/sys/config/page")
    public CommonResult<Page<ConferenceConfig>> page(ConferenceConfigPageParam conferenceConfigPageParam) {
        return CommonResult.data(conferenceConfigService.page(conferenceConfigPageParam));
    }

    /**
     * 添加会议预约-会议室数据
     *
     * @author ljy
     * @date  2023/11/05 23:12
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加会议预约-会议室数据")
    @CommonLog("添加会议预约-会议室数据")
    //@SaCheckPermission("/sys/config/add")
    @PostMapping("/sys/config/add")
    public CommonResult<String> add(@RequestBody @Valid ConferenceConfigAddParam conferenceConfigAddParam) {
        conferenceConfigService.add(conferenceConfigAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑会议预约-会议室数据
     *
     * @author ljy
     * @date  2023/11/05 23:12
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑会议预约-会议室数据")
    @CommonLog("编辑会议预约-会议室数据")
    //@SaCheckPermission("/sys/config/edit")
    @PostMapping("/sys/config/edit")
    public CommonResult<String> edit(@RequestBody @Valid ConferenceConfigEditParam conferenceConfigEditParam) {
        conferenceConfigService.edit(conferenceConfigEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除会议预约-会议室数据
     *
     * @author ljy
     * @date  2023/11/05 23:12
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除会议预约-会议室数据")
    @CommonLog("删除会议预约-会议室数据")
    //@SaCheckPermission("/sys/config/delete")
    @PostMapping("/sys/config/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ConferenceConfigIdParam> conferenceConfigIdParamList) {
        conferenceConfigService.delete(conferenceConfigIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取会议预约-会议室数据详情
     *
     * @author ljy
     * @date  2023/11/05 23:12
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取会议预约-会议室数据详情")
    //@SaCheckPermission("/sys/config/detail")
    @GetMapping("/sys/config/detail")
    public CommonResult<ConferenceConfig> detail(@Valid ConferenceConfigIdParam conferenceConfigIdParam) {
        return CommonResult.data(conferenceConfigService.detail(conferenceConfigIdParam));
    }
}
