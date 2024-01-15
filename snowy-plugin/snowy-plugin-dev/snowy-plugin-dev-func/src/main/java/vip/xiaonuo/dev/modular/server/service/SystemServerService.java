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
package vip.xiaonuo.dev.modular.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.dev.modular.server.entity.SystemServer;
import vip.xiaonuo.dev.modular.server.param.*;

import java.util.List;

/**
 * 系统服务表Service接口
 *
 * @author ljy
 * @date  2023/12/29 11:31
 **/
public interface SystemServerService extends IService<SystemServer> {

    /**
     * 获取系统服务表分页
     *
     * @author ljy
     * @date  2023/12/29 11:31
     */
    Page<SystemServer> page(SystemServerPageParam systemServerPageParam);

    /**
     * 添加系统服务表
     *
     * @author ljy
     * @date  2023/12/29 11:31
     */
    void add(SystemServerAddParam systemServerAddParam);

    /**
     * 编辑系统服务表
     *
     * @author ljy
     * @date  2023/12/29 11:31
     */
    void edit(SystemServerEditParam systemServerEditParam);

    /**
     * 删除系统服务表
     *
     * @author ljy
     * @date  2023/12/29 11:31
     */
    void delete(List<SystemServerIdParam> systemServerIdParamList);

    /**
     * 获取系统服务表详情
     *
     * @author ljy
     * @date  2023/12/29 11:31
     */
    SystemServer detail(SystemServerIdParam systemServerIdParam);

    /**
     * 获取系统服务表详情
     *
     * @author ljy
     * @date  2023/12/29 11:31
     **/
    SystemServer queryEntity(String id);

    /**
     * 我的项目
     *
     * @author ljy
     * @date  2024/01/15 15:31
     */
    Page<SystemServer> myServerTask(MyServerParam myServerParam);
}
