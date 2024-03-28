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
package vip.xiaonuo.sys.modular.hr.registerCert.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.registerCert.entity.RegAlwUser;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegAlwUserAddParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegAlwUserEditParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegAlwUserIdParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegAlwUserPageParam;

import java.util.List;

/**
 * 注册补贴-用户基本信息Service接口
 *
 * @author ljy
 * @date  2024/03/27 15:22
 **/
public interface RegAlwUserService extends IService<RegAlwUser> {

    /**
     * 获取注册补贴-用户基本信息分页
     *
     * @author ljy
     * @date  2024/03/27 15:22
     */
    Page<RegAlwUser> page(RegAlwUserPageParam regAlwUserPageParam);

    /**
     * 添加注册补贴-用户基本信息
     *
     * @author ljy
     * @date  2024/03/27 15:22
     */
    void add(RegAlwUserAddParam regAlwUserAddParam);

    /**
     * 编辑注册补贴-用户基本信息
     *
     * @author ljy
     * @date  2024/03/27 15:22
     */
    void edit(RegAlwUserEditParam regAlwUserEditParam);

    /**
     * 删除注册补贴-用户基本信息
     *
     * @author ljy
     * @date  2024/03/27 15:22
     */
    void delete(List<RegAlwUserIdParam> regAlwUserIdParamList);

    /**
     * 获取注册补贴-用户基本信息详情
     *
     * @author ljy
     * @date  2024/03/27 15:22
     */
    RegAlwUser detail(RegAlwUserIdParam regAlwUserIdParam);

    /**
     * 获取注册补贴-用户基本信息详情
     *
     * @author ljy
     * @date  2024/03/27 15:22
     **/
    RegAlwUser queryEntity(String id);
}
