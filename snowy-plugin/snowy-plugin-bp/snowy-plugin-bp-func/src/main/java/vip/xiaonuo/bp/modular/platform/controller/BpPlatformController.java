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
package vip.xiaonuo.bp.modular.platform.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
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
import vip.xiaonuo.bp.modular.platform.entity.NoticeTz;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeIdParam;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticePageParam;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeTzIdParam;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeTzPageParam;
import vip.xiaonuo.bp.modular.platform.param.user.*;
import vip.xiaonuo.bp.modular.platform.param.userSign.PayParam;
import vip.xiaonuo.bp.modular.platform.param.userSign.SignParam;
import vip.xiaonuo.bp.modular.platform.param.userSign.UploadParam;
import vip.xiaonuo.bp.modular.platform.param.userSign.UserSignPageParam;
import vip.xiaonuo.bp.modular.platform.service.*;
import vip.xiaonuo.bp.modular.platform.vo.*;
import vip.xiaonuo.bp.modular.recruit.supplier.entity.BpSupplier;
import vip.xiaonuo.bp.modular.platform.vo.NoticeVo;
import vip.xiaonuo.bp.modular.platform.vo.PicValidCodeResult;
import vip.xiaonuo.bp.modular.platform.vo.UserNoticeVo;
import vip.xiaonuo.bp.modular.platform.vo.UserSignVo;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierAddParam;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierPageParam;
import vip.xiaonuo.bp.modular.recruit.supplier.vo.BpSupplierVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 招采平台 - 对外接口
 *
 * @author yong.ye
 * @date  2024/05/09 08:41
 */
@Api(tags = "【前台】招采平台")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BpPlatformController {

    @Resource
    private BpPlatformService bpPlatformService;

    /**
     * 【前台】招采平台 - 注册用户
     *
     * @author yong.ye
     * @date  2024/05/09 08:41
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("【前台】招采平台 - 注册用户")
    @CommonLog("【前台】招采平台 - 注册用户")
    @PostMapping("/bp/platform/frontend/public/register")
    public CommonResult<String> register(@RequestBody @Valid RegisterParam registerParam) {
        bpPlatformService.register(registerParam);
        return CommonResult.ok();
    }


    /**
     * 【前台】招采平台 - 注册|登录 获取图片验证码
     *
     * @author xuyuxiang
     * @date 2022/7/8 9:26
     **/
    @ApiOperationSupport(order = 2)
    @ApiOperation("【前台】招采平台 - 注册|登录 获取图片验证码")
    @GetMapping("/bp/platform/frontend/public/getPicCaptcha")
    public CommonResult<PicValidCodeResult> getPicCaptcha() {
        return CommonResult.data(bpPlatformService.getPicCaptcha());
    }

    /**
     * 【前台】招采平台 - 注册|登录 获取手机验证码
     *
     * @author xuyuxiang
     * @date 2022/7/8 9:26
     **/
    @ApiOperationSupport(order = 3)
    @ApiOperation("【前台】招采平台 - 注册|登录 获取手机验证码")
    @GetMapping("/bp/platform/frontend/public/getPhoneValidCode")
    public CommonResult<String> getPhoneValidCode(@Valid GetPhoneValidCodeParam getPhoneValidCodeParam) {
        return CommonResult.data(bpPlatformService.getPhoneValidCode(getPhoneValidCodeParam));
    }

    /**
     * 【前台】招采平台 - 登录 账号密码登录
     *
     * @author xuyuxiang
     * @date 2021/10/15 13:12
     **/
    @ApiOperationSupport(order = 4)
    @ApiOperation("【前台】招采平台 - 登录 账号密码登录")
    @CommonLog("【前台】招采平台 - 登录 账号密码登录")
    @PostMapping("/bp/platform/frontend/public/doLogin")
    public CommonResult<String> doLogin(@RequestBody @Valid AccountPasswordLoginParam accountPasswordLoginParam) {
        return CommonResult.data(bpPlatformService.doLogin(accountPasswordLoginParam));
    }

    /**
     * 【前台】招采平台 - 登录 手机验证码登录
     *
     * @author xuyuxiang
     * @date 2021/10/15 13:12
     **/
    @ApiOperationSupport(order = 5)
    @ApiOperation("【前台】招采平台 - 登录 手机验证码登录")
    @CommonLog("【前台】招采平台 - 登录 手机验证码登录")
    @PostMapping("/bp/platform/frontend/public/doLoginByPhone")
    public CommonResult<String> doLoginByPhone(@RequestBody @Valid PhoneValidCodeLoginParam phoneValidCodeLoginParam) {
        return CommonResult.data(bpPlatformService.doLoginByPhone(phoneValidCodeLoginParam));
    }


    /**
     * 【前台】获取招采平台-前台用户登录信息
     *
     **/
    @ApiOperationSupport(order = 6)
    @ApiOperation("【前台】获取招采平台-前台用户登录信息")
    @SaCheckLogin
    @GetMapping("/bp/platform/frontend/self/getLoginUser")
    public CommonResult<BpLoginUser> getLoginUser() {
        return CommonResult.data(bpPlatformService.getLoginUser());
    }


    /**
     * 【前台】招采平台-前台用户登录退出
     *
     **/
    @ApiOperationSupport(order = 7)
    @ApiOperation("【前台】招采平台-前台用户登录退出")
    @SaCheckLogin
    @GetMapping("/bp/platform/frontend/self/doLogout")
    public CommonResult<String> doLogout() {
        StpUtil.logout();
        return CommonResult.ok();
    }


    /**
     * 【前台】招采平台 - 登录用户，获取对应供应商入库信息【审核中、审核通过】
     *
     **/
    @ApiOperationSupport(order = 8)
    @ApiOperation("【前台】招采平台 - 登录用户，获取对应供应商入库信息【审核中、审核通过】")
    @GetMapping("/bp/platform/frontend/self/getSupplierInfo")
    public CommonResult<BpSupplier> getSupplierInfo() {
        return CommonResult.data(bpPlatformService.getSupplierInfo());
    }




    /**
     * 【前台】获取招采管理 - 通知公告分页
     *
     * @author yong.ye
     * @date  2024/05/07 16:34
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("【前台】获取招采管理 - 通知公告分页")
    @GetMapping("/bp/platform/frontend/public/noticeTzPage")
    public CommonResult<Page<NoticeTz>> noticeTzPage(NoticeTzPageParam noticeTzPageParam) {
        return CommonResult.data(bpPlatformService.noticeTzPage(noticeTzPageParam));
    }

    /**
     * 【前台】获取招采管理 - 通知公告详情
     *
     * @author yong.ye
     * @date  2024/05/07 16:34
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("【前台】获取招采管理 - 通知公告详情")
    @GetMapping("/bp/platform/frontend/public/noticeTzDetail")
    public CommonResult<NoticeTz> noticeTzDetail(@Valid NoticeTzIdParam noticeTzIdParam) {
        return CommonResult.data(bpPlatformService.noticeTzDetail(noticeTzIdParam));
    }

    /**
     * 【前台】获取招采管理 - 竞价公告|询价公告|招标公告分页
     *
     * @author yong.ye
     * @date  2024/05/07 16:36
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("【前台】获取招采管理 - 竞价公告|询价公告|招标公告分页")
    @PostMapping("/bp/platform/frontend/public/noticePage")
    public CommonResult<Page<NoticeVo>> noticePage(@RequestBody @Valid NoticePageParam noticePageParam) {
        return CommonResult.data(bpPlatformService.noticePage(noticePageParam));
    }

    /**
     * 【前台】获取招采管理 - 竞价公告|询价公告|招标公告详情【本人是否已报名|是否已缴附件查看费】
     *
     * @author yong.ye
     * @date  2024/05/07 16:36
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("【前台】获取招采管理 - 竞价公告|询价公告|招标公告详情")
    @GetMapping("/bp/platform/frontend/public/noticeDetail")
    public CommonResult<UserNoticeVo> noticeDetail(@Valid NoticeIdParam noticeIdParam) {
        return CommonResult.data(bpPlatformService.noticeDetail(noticeIdParam));
    }


    /**
     * 【前台】招采 - 竞价公告|询价公告|招标公告 - 报名
     *
     * 1. 校验供应商是否入库，且审批通过
     * 2. 校验是否已缴纳附件费用
     * 3. 校验是否已报名
     *
     * @author yong.ye
     * @date  2024/05/10 16:36
     */
    @ApiOperationSupport(order = 15)
    @ApiOperation("【前台】招采 - 竞价公告|询价公告|招标公告 - 报名")
    @CommonLog("【前台】招采 - 竞价公告|询价公告|招标公告 - 报名")
    @PostMapping("/bp/platform/frontend/self/sign")
    public CommonResult<String> sign(@RequestBody @Valid SignParam signParam) {
        bpPlatformService.sign(signParam);
        return CommonResult.ok();
    }


    /**
     * 【前台】招采 - 用户报名 - 缴费
     *
     * 1. 校验是否已缴纳
     *
     * @author yong.ye
     * @date  2024/05/10 16:36
     */
    @ApiOperationSupport(order = 16)
    @ApiOperation("【前台】招采 - 用户报名 - 缴费")
    @CommonLog("【前台】招采 - 用户报名 - 缴费")
    @PostMapping("/bp/platform/frontend/self/signPay")
    public CommonResult<String> signPay(@RequestBody @Valid PayParam payParam) {
        bpPlatformService.signPay(payParam);
        return CommonResult.ok();
    }


    /**
     * 【前台】招采 - 用户报名 - 上传报名文件
     *
     * @author yong.ye
     * @date  2024/05/10 16:36
     */
    @ApiOperationSupport(order = 17)
    @ApiOperation("【前台】招采 - 用户报名 - 上传报名文件")
    @CommonLog("【前台】招采 - 用户报名 - 上传报名文件")
    @PostMapping("/bp/platform/frontend/self/signUpload")
    public CommonResult<String> signUpload(@RequestBody @Valid UploadParam uploadParam) {
        bpPlatformService.signUpload(uploadParam);
        return CommonResult.ok();
    }


    /**
     * 【前台】招采 - 我的报名
     *
     * @author yong.ye
     * @date  2024/05/10 16:36
     */
    @ApiOperationSupport(order = 18)
    @ApiOperation("【前台】招采 - 我的报名")
    @GetMapping("/bp/platform/frontend/self/mySignList")
    public CommonResult<Page<UserSignVo>> mySignList(@Valid UserSignPageParam userSignPageParam) {
        return CommonResult.data(bpPlatformService.mySignList(userSignPageParam));
    }

    /**
     * 【前台】招采 - 供应商入库
     *
     * @author yong.ye
     * @date  2024/05/10 16:36
     */
    @ApiOperationSupport(order = 19)
    @ApiOperation("【前台】招采 - 供应商入库")
    @CommonLog("【前台】招采 - 供应商入库")
    @PostMapping("/bp/platform/frontend/self/addSupplier")
    public CommonResult<String> addSupplier(@RequestBody @Valid BpSupplierAddParam bpSupplierAddParam) {
        bpPlatformService.addSupplier(bpSupplierAddParam);
        return CommonResult.ok();
    }

    /**
     * 【前台】招采 -供应商审批入库查询
     *
     * @author hxw
     * @date 2024/5/14 11:10
     */
    @ApiOperationSupport(order = 20)
    @ApiOperation("【前台】招采 -供应商审批入库查询")
    @CommonLog("【前台】招采 -供应商审批入库查询")
    @GetMapping("/bp/platform/frontend/public/getBpSupplierPageList")
    public CommonResult<Page<BpSupplierVo>> getBpSupplierPageList(@Valid BpSupplierPageParam bpSupplierPageParam) {
        return CommonResult.data(bpPlatformService.getBpSupplierPageList(bpSupplierPageParam));
    }


    /**
     * 【前台】获取招采管理 - 澄清公告详情(id)
     *
     * @author yong.ye
     * @date  2024/05/20 16:36
     */
    @ApiOperationSupport(order = 21)
    @ApiOperation("【前台】获取招采管理 - 澄清公告详情(id)")
    @GetMapping("/bp/platform/frontend/public/noticeClarify")
    public CommonResult<List<ClarifyVo>> noticeClarify(String noticeId) {
        return CommonResult.data(bpPlatformService.noticeClarify(noticeId));
    }
}
