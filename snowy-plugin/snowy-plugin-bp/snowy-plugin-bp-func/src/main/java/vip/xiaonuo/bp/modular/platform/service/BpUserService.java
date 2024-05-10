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
import vip.xiaonuo.bp.modular.platform.param.user.*;
import vip.xiaonuo.bp.modular.platform.vo.PicValidCodeResult;

import java.util.List;

/**
 * 招采平台 - 用户表Service接口
 *
 * @author yong.ye
 * @date  2024/05/09 08:41
 **/
public interface BpUserService extends IService<BpUser> {

    /**
     * 获取招采平台 - 用户表分页
     *
     * @author yong.ye
     * @date  2024/05/09 08:41
     */
    Page<BpUser> page(BpUserPageParam bpUserPageParam);

    /**
     * 添加招采平台 - 用户表
     *
     * @author yong.ye
     * @date  2024/05/09 08:41
     */
    void add(BpUserAddParam bpUserAddParam);

    /**
     * 编辑招采平台 - 用户表
     *
     * @author yong.ye
     * @date  2024/05/09 08:41
     */
    void edit(BpUserEditParam bpUserEditParam);

    /**
     * 删除招采平台 - 用户表
     *
     * @author yong.ye
     * @date  2024/05/09 08:41
     */
    void delete(List<BpUserIdParam> bpUserIdParamList);

    /**
     * 获取招采平台 - 用户表详情
     *
     * @author yong.ye
     * @date  2024/05/09 08:41
     */
    BpUser detail(BpUserIdParam bpUserIdParam);

    /**
     * 获取招采平台 - 用户表详情
     *
     * @author yong.ye
     * @date  2024/05/09 08:41
     **/
    BpUser queryEntity(String id);

    /**
     * 招采平台 - 用户注册
     * @param registerParam
     */
    void register(RegisterParam registerParam);

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
    Object doLogin(AccountPasswordLoginParam accountPasswordLoginParam);

    /**
     * 手机验证码登录
     * @param phoneValidCodeLoginParam
     * @return
     */
    Object doLoginByPhone(PhoneValidCodeLoginParam phoneValidCodeLoginParam);
}
