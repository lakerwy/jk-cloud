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
package vip.xiaonuo.dev.modular.epcCenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.dev.modular.epcCenter.entity.SysProjectUser;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectUserAddParam;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectUserEditParam;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectUserIdParam;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectUserPageParam;

import java.util.List;

/**
 * 系统项目-用户关系表Service接口
 *
 * @author ljy
 * @date  2024/01/31 11:42
 **/
public interface SysProjectUserService extends IService<SysProjectUser> {

    /**
     * 获取系统项目-用户关系表分页
     *
     * @author ljy
     * @date  2024/01/31 11:42
     */
    Page<SysProjectUser> page(SysProjectUserPageParam sysProjectUserPageParam);

    /**
     * 添加系统项目-用户关系表
     *
     * @author ljy
     * @date  2024/01/31 11:42
     */
    void add(SysProjectUserAddParam sysProjectUserAddParam);

    /**
     * 编辑系统项目-用户关系表
     *
     * @author ljy
     * @date  2024/01/31 11:42
     */
    void edit(SysProjectUserEditParam sysProjectUserEditParam);

    /**
     * 删除系统项目-用户关系表
     *
     * @author ljy
     * @date  2024/01/31 11:42
     */
    void delete(List<SysProjectUserIdParam> sysProjectUserIdParamList);

    /**
     * 获取系统项目-用户关系表详情
     *
     * @author ljy
     * @date  2024/01/31 11:42
     */
    SysProjectUser detail(SysProjectUserIdParam sysProjectUserIdParam);

    /**
     * 获取系统项目-用户关系表详情
     *
     * @author ljy
     * @date  2024/01/31 11:42
     **/
    SysProjectUser queryEntity(String id);
}
