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
import vip.xiaonuo.bp.modular.platform.entity.NoticeTz;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeIdParam;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticePageParam;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeTzIdParam;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeTzPageParam;
import vip.xiaonuo.bp.modular.platform.param.user.AccountPasswordLoginParam;
import vip.xiaonuo.bp.modular.platform.param.user.GetPhoneValidCodeParam;
import vip.xiaonuo.bp.modular.platform.param.user.PhoneValidCodeLoginParam;
import vip.xiaonuo.bp.modular.platform.param.user.RegisterParam;
import vip.xiaonuo.bp.modular.platform.param.userSign.*;
import vip.xiaonuo.bp.modular.platform.vo.*;
import vip.xiaonuo.bp.modular.recruit.supplier.entity.BpSupplier;

import vip.xiaonuo.bp.modular.platform.vo.PicValidCodeResult;
import vip.xiaonuo.bp.modular.platform.vo.UserNoticeVo;
import vip.xiaonuo.bp.modular.platform.vo.UserSignVo;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierAddParam;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierPageParam;
import vip.xiaonuo.bp.modular.recruit.supplier.vo.BpSupplierVo;

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
    BpLoginUser getLoginUser();


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
     * 招采前台 - 根据当前登录用户，获取供应商入库信息【审核中、审核通过】
     * @return
     */
    BpSupplier getSupplierInfo();

    /**
     * 招采前台 - 获取首页公告
     * @param noticeTzPageParam
     * @return
     */
    Page<NoticeTz> noticeTzPage(NoticeTzPageParam noticeTzPageParam);

    /**
     * 招采前台 - 获取首页公告详情
     * @param noticeTzIdParam
     * @return
     */
    NoticeTz noticeTzDetail(NoticeTzIdParam noticeTzIdParam);

    /**
     * 招采前台 - 获取竞价|询价|招标公告
     * @param noticePageParam
     * @return
     */
    Page<NoticeVo> noticePage(NoticePageParam noticePageParam);

    /**
     * 招采前台 - 供应商审批
     * @param bpSupplierAddParam
     * @return
     */
    void addSupplier(BpSupplierAddParam bpSupplierAddParam);

    /**
     * 招采前台 - 供应商审批入库查询
     * @param bpSupplierPageParam
     * @return
     */
    Page<BpSupplierVo> getBpSupplierPageList(BpSupplierPageParam bpSupplierPageParam);
}
