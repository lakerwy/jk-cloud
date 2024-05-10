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
import vip.xiaonuo.bp.modular.platform.entity.UserSign;
import vip.xiaonuo.bp.modular.platform.param.userSign.*;
import vip.xiaonuo.bp.modular.platform.vo.UserSignVo;

import java.util.List;

/**
 * 招采平台 - 用户 - （招采项目）报名记录表Service接口
 *
 * @author yong.ye
 * @date  2024/05/10 10:49
 **/
public interface UserSignService extends IService<UserSign> {

    /**
     * 获取招采平台 - 用户 - （招采项目）报名记录表分页
     *
     * @author yong.ye
     * @date  2024/05/10 10:49
     */
    Page<UserSign> page(UserSignPageParam userSignPageParam);

    /**
     * 添加招采平台 - 用户 - （招采项目）报名记录表
     *
     * @author yong.ye
     * @date  2024/05/10 10:49
     */
    void add(UserSignAddParam userSignAddParam);

    /**
     * 编辑招采平台 - 用户 - （招采项目）报名记录表
     *
     * @author yong.ye
     * @date  2024/05/10 10:49
     */
    void edit(UserSignEditParam userSignEditParam);

    /**
     * 删除招采平台 - 用户 - （招采项目）报名记录表
     *
     * @author yong.ye
     * @date  2024/05/10 10:49
     */
    void delete(List<UserSignIdParam> userSignIdParamList);

    /**
     * 获取招采平台 - 用户 - （招采项目）报名记录表详情
     *
     * @author yong.ye
     * @date  2024/05/10 10:49
     */
    UserSign detail(UserSignIdParam userSignIdParam);

    /**
     * 获取招采平台 - 用户 - （招采项目）报名记录表详情
     *
     * @author yong.ye
     * @date  2024/05/10 10:49
     **/
    UserSign queryEntity(String id);

    /**
     * 报名
     * @param signParam
     * @return
     */
    void sign(SignParam signParam);

    /**
     * 上传报名文件
     * @param uploadParam
     */
    void upload(UploadParam uploadParam);

    /**
     * 我的报名
     * @param userSignPageParam
     * @return
     */
    Page<UserSignVo> mySignList(UserSignPageParam userSignPageParam);
}
