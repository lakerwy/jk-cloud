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
package vip.xiaonuo.bp.modular.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.bp.modular.platform.entity.BpUser;
import vip.xiaonuo.bp.modular.platform.entity.UserSign;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeIdParam;
import vip.xiaonuo.bp.modular.platform.param.user.AccountPasswordLoginParam;
import vip.xiaonuo.bp.modular.platform.param.user.GetPhoneValidCodeParam;
import vip.xiaonuo.bp.modular.platform.param.user.PhoneValidCodeLoginParam;
import vip.xiaonuo.bp.modular.platform.param.user.RegisterParam;
import vip.xiaonuo.bp.modular.platform.param.userSign.*;
import vip.xiaonuo.bp.modular.platform.vo.PicValidCodeResult;
import vip.xiaonuo.bp.modular.platform.vo.UserNoticeVo;
import vip.xiaonuo.bp.modular.platform.vo.UserSignVo;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierAddParam;

/**
 * 招采平台 - 用户 - （招采项目）报名记录表Service接口
 *
 * @author yong.ye
 * @date  2024/05/10 10:49
 **/
public interface BpPlatformService{
    /**
     * 获取图片验证码
     * @return
     */
    PicValidCodeResult getPicCaptcha();

    /**
     * 获取手机验证码
     * @param getPhoneValidCodeParam
     * @return
     */
    String getPhoneValidCode(GetPhoneValidCodeParam getPhoneValidCodeParam);

    /**
     * 账号密码登录
     * @param accountPasswordLoginParam
     * @return
     */
    String doLogin(AccountPasswordLoginParam accountPasswordLoginParam);

    /**
     * 手机验证码登录
     * @param phoneValidCodeLoginParam
     * @return
     */
    String doLoginByPhone(PhoneValidCodeLoginParam phoneValidCodeLoginParam);

    /**
     * 招采平台 - 用户注册
     * @param registerParam
     */
    void register(RegisterParam registerParam);

    /**
     * 获取登录信息
     * @return
     */
    BpUser getLoginUser();


    /**
     * 招采前台 - 报名
     * @param signParam
     * @return
     */
    void sign(SignParam signParam);

    /**
     * 招采前台 - 报名 - 缴费
     * @param payParam
     */
    void signPay(PayParam payParam);

    /**
     * 招采前台 - 上传报名文件
     * @param uploadParam
     */
    void signUpload(UploadParam uploadParam);

    /**
     * 招采前台 - 我的报名
     * @param userSignPageParam
     * @return
     */
    Page<UserSignVo> mySignList(UserSignPageParam userSignPageParam);

    /**
     * 招采前台 - 公告详情（携带个人相关缴费|报名信息）
     * @param noticeIdParam
     * @return
     */
    UserNoticeVo noticeDetail(NoticeIdParam noticeIdParam);

    /**
     * 招采 - 供应商审批
     * @return
     */
    void addSupplier(BpSupplierAddParam bpSupplierAddParam);
}
