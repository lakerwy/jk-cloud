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
package vip.xiaonuo.dev.modular.dingThirdData.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.dev.modular.dingThirdData.entity.AuthThirdUser;
import vip.xiaonuo.dev.modular.dingThirdData.param.AuthThirdUserAddParam;
import vip.xiaonuo.dev.modular.dingThirdData.param.AuthThirdUserEditParam;
import vip.xiaonuo.dev.modular.dingThirdData.param.AuthThirdUserIdParam;
import vip.xiaonuo.dev.modular.dingThirdData.param.AuthThirdUserPageParam;

import java.util.List;

/**
 * 三方用户Service接口
 *
 * @author ljy
 * @date  2024/03/08 08:39
 **/
public interface AuthThirdUserService extends IService<AuthThirdUser> {

    /**
     * 获取三方用户分页
     *
     * @author ljy
     * @date  2024/03/08 08:39
     */
    Page<AuthThirdUser> page(AuthThirdUserPageParam authThirdUserPageParam);

    /**
     * 添加三方用户
     *
     * @author ljy
     * @date  2024/03/08 08:39
     */
    void add(AuthThirdUserAddParam authThirdUserAddParam);

    /**
     * 编辑三方用户
     *
     * @author ljy
     * @date  2024/03/08 08:39
     */
    void edit(AuthThirdUserEditParam authThirdUserEditParam);

    /**
     * 删除三方用户
     *
     * @author ljy
     * @date  2024/03/08 08:39
     */
    void delete(List<AuthThirdUserIdParam> authThirdUserIdParamList);

    /**
     * 获取三方用户详情
     *
     * @author ljy
     * @date  2024/03/08 08:39
     */
    AuthThirdUser detail(AuthThirdUserIdParam authThirdUserIdParam);

    /**
     * 获取三方用户详情
     *
     * @author ljy
     * @date  2024/03/08 08:39
     **/
    AuthThirdUser queryEntity(String id);


    /**
     * 通过系统用户Id,获取三方账号
     * @param userId 系统用户Id
     * @param category 类型
     * @return
     */
    String getUnionIdByUserId(String userId,String category);

    /**
     * 通过系统用户Id,获取三方账号
     * @param userId 系统用户Id
     * @param category 类型
     * @return
     */
    String getThirdIdByUserId(String userId,String category);

    /**
     * 新增第三方用户记录
     * @param userId 系统用户Id
     * @param thirdId 第三方用户Id
     * @param category 类型
     * @param name 用户名
     * @param unionId 钉钉唯一第三方ID
     * @return
     */
    Boolean updateAuthThird(String userId,String thirdId,String category,String name,String unionId);
}
