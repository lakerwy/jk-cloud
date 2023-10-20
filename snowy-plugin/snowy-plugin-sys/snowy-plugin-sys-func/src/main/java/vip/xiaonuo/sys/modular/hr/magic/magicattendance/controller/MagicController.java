package vip.xiaonuo.sys.modular.hr.magic.magicattendance.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.moredian.oapi.model.member.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicCallback;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicMemberParam;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicResult;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicTokenParam;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.service.MagicService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;

/**
 * 魔点token
 *
 * @author ljy
 * @date  2023/10/18 10:28
 */
@Api(tags = "魔点考勤")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class MagicController {

    @Resource
    private MagicService magicService;


    @ApiOperationSupport(order = 1)
    @ApiOperation("获取token")
    @CommonLog("获取token")
    @GetMapping("/sys/magic/getToken")
    public CommonResult<String> getToken() {
        return CommonResult.data(magicService.getAppToken());
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation("打卡回调接口(免登录)")
    @CommonLog("打卡回调接口(免登录)")
    @PostMapping("/sys/magic/third/callback")
    public CommonResult<MagicResult> attendanceCallback(@RequestBody @Valid MagicCallback magicCallback) {
        return CommonResult.data(magicService.attendanceCallback(magicCallback));
    }

    @ApiOperationSupport(order = 3)
    @ApiOperation("魔点考勤创建成员")
    @CommonLog("魔点考勤创建成员")
    @PostMapping("/sys/magic/member/create")
    public CommonResult<CreateMemberResponse> magicMemberCreate(@Valid MagicMemberParam magicMemberParam) {
        return CommonResult.data(magicService.magicMemberCreate(magicMemberParam));
    }

    @ApiOperationSupport(order = 4)
    @ApiOperation("魔点考勤删除成员")
    @CommonLog("魔点考勤删除成员")
    @GetMapping("/sys/magic/member/deleteVerifyImg")
    public CommonResult<DeleteMemberResponse> deleteVerifyImg(@Valid Long memberId) {
        return CommonResult.data(magicService.deleteVerifyImg(memberId));
    }

    @ApiOperationSupport(order = 5)
    @ApiOperation("魔点考勤更新成员")
    @CommonLog("魔点考勤更新成员")
    @GetMapping("/sys/magic/member/update")
    public CommonResult<UpdateMemberResponse> magicMemberUpdate(@Valid MagicMemberParam magicMemberParam) {
        return CommonResult.data(magicService.magicMemberUpdate(magicMemberParam));
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("魔点考勤更新识别人脸图片")
    @CommonLog("魔点考勤更新识别人脸图片")
    @GetMapping("/sys/magic/member/updateVerifyImage")
    public CommonResult<UpdateMemberVerifyImageResponse> updateVerifyImage(@Valid File face, Long memberId) {
        return CommonResult.data(magicService.updateVerifyImage(face,memberId));
    }

    @ApiOperationSupport(order = 7)
    @ApiOperation("魔点考勤查询成员信息列表")
    @CommonLog("魔点考勤查询成员信息列表")
    @GetMapping("/sys/magic/member/list")
    public CommonResult<ListMemberResponse> magicMemberList(@Valid Integer offset, Integer size) {
        return CommonResult.data(magicService.magicMemberList(offset,size));
    }

    @ApiOperationSupport(order = 8)
    @ApiOperation("批量导入建科人员信息到魔点考勤--暂勿使用")
    @CommonLog("批量导入建科人员信息到魔点考勤--暂勿使用")
    @GetMapping("/sys/magic/member/jkUserToMagic")
    public CommonResult<String> jkUserToMagicBatch() {
        return CommonResult.data(magicService.jkUserToMagic());
    }


}
