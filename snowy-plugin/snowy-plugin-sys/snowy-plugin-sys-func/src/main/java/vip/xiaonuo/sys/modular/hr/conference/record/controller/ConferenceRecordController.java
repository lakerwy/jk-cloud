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
package vip.xiaonuo.sys.modular.hr.conference.record.controller;

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
import vip.xiaonuo.sys.modular.hr.conference.record.entity.ConferenceConfirmUser;
import vip.xiaonuo.sys.modular.hr.conference.record.entity.ConferenceRecord;
import vip.xiaonuo.sys.modular.hr.conference.record.param.*;
import vip.xiaonuo.sys.modular.hr.conference.record.service.ConferenceConfirmUserService;
import vip.xiaonuo.sys.modular.hr.conference.record.service.ConferenceRecordService;
import vip.xiaonuo.sys.modular.hr.conference.record.service.ConferenceWorkArrangeService;
import vip.xiaonuo.sys.modular.hr.conference.record.vo.RecordDetailVo;
import vip.xiaonuo.sys.modular.hr.conference.record.entity.ConferenceWorkArrange;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceWorkArrangeAddParam;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceWorkArrangeEditParam;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceWorkArrangeIdParam;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceWorkArrangePageParam;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 会议预约-会议记录控制器
 *
 * @author ljy
 * @date  2023/11/20 08:39
 */
@Api(tags = "会议预约-会议记录控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ConferenceRecordController {

    @Resource
    private ConferenceRecordService conferenceRecordService;
    @Resource
    private ConferenceWorkArrangeService conferenceWorkArrangeService;
    @Resource
    private ConferenceConfirmUserService conferenceConfirmUserService;

    /**
     * 获取会议预约-会议记录分页
     *
     * @author ljy
     * @date  2023/11/20 08:39
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取会议预约-会议记录分页")
    //@SaCheckPermission("/sys/conferenceRecord/page")
    @GetMapping("/sys/conferenceRecord/page")
    public CommonResult<Page<ConferenceRecord>> page(ConferenceRecordPageParam conferenceRecordPageParam) {
        return CommonResult.data(conferenceRecordService.page(conferenceRecordPageParam));
    }

    /**
     * 添加会议预约-会议记录
     *
     * @author ljy
     * @date  2023/11/20 08:39
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加会议预约-会议记录")
    @CommonLog("添加会议预约-会议记录")
    //@SaCheckPermission("/sys/conferenceRecord/add")
    @PostMapping("/sys/conferenceRecord/add")
    public CommonResult<String> add(@RequestBody @Valid ConferenceRecordAddParam conferenceRecordAddParam) {
        conferenceRecordService.add(conferenceRecordAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑会议预约-会议记录
     *
     * @author ljy
     * @date  2023/11/20 08:39
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑会议预约-会议记录")
    @CommonLog("编辑会议预约-会议记录")
    //@SaCheckPermission("/sys/conferenceRecord/edit")
    @PostMapping("/sys/conferenceRecord/edit")
    public CommonResult<String> edit(@RequestBody @Valid ConferenceRecordEditParam conferenceRecordEditParam) {
        conferenceRecordService.edit(conferenceRecordEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除会议预约-会议记录
     *
     * @author ljy
     * @date  2023/11/20 08:39
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除会议预约-会议记录")
    @CommonLog("删除会议预约-会议记录")
    //@SaCheckPermission("/sys/conferenceRecord/delete")
    @PostMapping("/sys/conferenceRecord/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<DeleteRecordParam> deleteRecordParamCommonValidList) {
        conferenceRecordService.delete(deleteRecordParamCommonValidList);
        return CommonResult.ok();
    }

    /**
     * 获取会议预约-会议记录详情
     *
     * @author ljy
     * @date  2023/11/20 08:39
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取会议预约-会议记录详情")
    //@SaCheckPermission("/sys/conferenceRecord/detail")
    @GetMapping("/sys/conferenceRecord/detail")
    public CommonResult<RecordDetailVo> detail(@Valid ConferenceRecordIdParam conferenceRecordIdParam) {
        return CommonResult.data(conferenceRecordService.detail(conferenceRecordIdParam));
    }

    /**
     * 添加会议预约-会议记录
     *
     * @author ljy
     * @date  2023/11/20 08:39
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("添加会议纪要")
    @CommonLog("添加会议纪要")
    //@SaCheckPermission("/sys/conferenceRecord/add")
    @PostMapping("/sys/conferenceRecord/addSummary")
    public CommonResult<String> addSummary(@RequestBody @Valid ConfSummaryAddParam confSummaryAddParam) {
        conferenceRecordService.addSummary(confSummaryAddParam);
        return CommonResult.ok();
    }


    /**
     * 获取工作部署分页
     *
     * @author ljy
     * @date  2023/11/28 16:31
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取工作部署分页")
    //@SaCheckPermission("/sys/workArrange/page")
    @GetMapping("/sys/workArrange/page")
    public CommonResult<Page<ConferenceWorkArrange>> workArrangepage(ConferenceWorkArrangePageParam conferenceWorkArrangePageParam) {
        return CommonResult.data(conferenceWorkArrangeService.page(conferenceWorkArrangePageParam));
    }

    /**
     * 添加工作部署
     *
     * @author ljy
     * @date  2023/11/28 16:31
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("添加工作部署")
    @CommonLog("添加工作部署")
    //@SaCheckPermission("/sys/workArrange/add")
    @PostMapping("/sys/workArrange/add")
    public CommonResult<String> workArrangeadd(@RequestBody @Valid ConferenceWorkArrangeAddParam conferenceWorkArrangeAddParam) {
        conferenceWorkArrangeService.add(conferenceWorkArrangeAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑工作部署
     *
     * @author ljy
     * @date  2023/11/28 16:31
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("编辑工作部署")
    @CommonLog("编辑工作部署")
    //@SaCheckPermission("/sys/workArrange/edit")
    @PostMapping("/sys/workArrange/edit")
    public CommonResult<String> workArrangeedit(@RequestBody @Valid ConferenceWorkArrangeEditParam conferenceWorkArrangeEditParam) {
        conferenceWorkArrangeService.edit(conferenceWorkArrangeEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除工作部署
     *
     * @author ljy
     * @date  2023/11/28 16:31
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("删除工作部署")
    @CommonLog("删除工作部署")
    //@SaCheckPermission("/sys/workArrange/delete")
    @PostMapping("/sys/workArrange/delete")
    public CommonResult<String> workArrangedelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<ConferenceWorkArrangeIdParam> conferenceWorkArrangeIdParamList) {
        conferenceWorkArrangeService.delete(conferenceWorkArrangeIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取工作部署详情
     *
     * @author ljy
     * @date  2023/11/28 16:31
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("获取工作部署详情")
    //@SaCheckPermission("/sys/workArrange/detail")
    @GetMapping("/sys/workArrange/detail")
    public CommonResult<ConferenceWorkArrange> workArrangedetail(@Valid ConferenceWorkArrangeIdParam conferenceWorkArrangeIdParam) {
        return CommonResult.data(conferenceWorkArrangeService.detail(conferenceWorkArrangeIdParam));
    }


    /**
     * 会议记录确认通知
     *
     * @author ljy
     * @date  2023/11/20 08:39
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("会议记录确认通知")
    @CommonLog("会议记录确认通知")
    //@SaCheckPermission("/sys/conferenceRecord/recordNotice")
    @GetMapping("/sys/conferenceRecord/recordNotice")
    public CommonResult<String> recordNotice(@Valid String recordId) {
        conferenceRecordService.recordNotice(recordId);
        return CommonResult.ok();
    }


    /** 会议记录确认接口 */
    /**
     * 获取会议记录确认表分页
     *
     * @author ljy
     * @date  2023/11/28 22:28
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("获取会议记录确认表分页")
    //@SaCheckPermission("/sys/confirmUser/page")
    @GetMapping("/sys/confirmUser/page")
    public CommonResult<Page<ConferenceConfirmUser>> confirmPage(ConferenceConfirmPageParam conferenceConfirmUserPageParam) {
        return CommonResult.data(conferenceConfirmUserService.page(conferenceConfirmUserPageParam));
    }

    /**
     * 添加会议记录确认表
     *
     * @author ljy
     * @date  2023/11/28 22:28
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("添加会议记录确认表")
    @CommonLog("添加会议记录确认表")
    //@SaCheckPermission("/sys/confirmUser/add")
    @PostMapping("/sys/confirmUser/add")
    public CommonResult<String> confirmAdd(@RequestBody @Valid ConferenceConfirmUserAddParam conferenceConfirmUserAddParam) {
        conferenceConfirmUserService.add(conferenceConfirmUserAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑会议记录确认表
     *
     * @author ljy
     * @date  2023/11/28 22:28
     */
    @ApiOperationSupport(order = 15)
    @ApiOperation("编辑会议记录确认表")
    @CommonLog("编辑会议记录确认表")
    //@SaCheckPermission("/sys/confirmUser/edit")
    @PostMapping("/sys/confirmUser/edit")
    public CommonResult<String> confirmEdit(@RequestBody @Valid ConferenceConfirmUserEditParam conferenceConfirmUserEditParam) {
        conferenceConfirmUserService.edit(conferenceConfirmUserEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除会议记录确认表
     *
     * @author ljy
     * @date  2023/11/28 22:28
     */
    @ApiOperationSupport(order = 16)
    @ApiOperation("删除会议记录确认表")
    @CommonLog("删除会议记录确认表")
    //@SaCheckPermission("/sys/confirmUser/delete")
    @PostMapping("/sys/confirmUser/delete")
    public CommonResult<String> confirmDelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                      CommonValidList<ConferenceConfirmUserIdParam> conferenceConfirmUserIdParamList) {
        conferenceConfirmUserService.delete(conferenceConfirmUserIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取会议记录确认表详情
     *
     * @author ljy
     * @date  2023/11/28 22:28
     */
    @ApiOperationSupport(order = 17)
    @ApiOperation("获取会议记录确认表详情")
    //@SaCheckPermission("/sys/confirmUser/detail")
    @GetMapping("/sys/confirmUser/detail")
    public CommonResult<ConferenceConfirmUser> confirmDetail(@Valid ConferenceConfirmUserIdParam conferenceConfirmUserIdParam) {
        return CommonResult.data(conferenceConfirmUserService.detail(conferenceConfirmUserIdParam));
    }

}
