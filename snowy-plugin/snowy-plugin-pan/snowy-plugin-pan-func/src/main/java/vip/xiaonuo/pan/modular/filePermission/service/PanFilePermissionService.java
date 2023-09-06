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
package vip.xiaonuo.pan.modular.filePermission.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.pan.modular.filePermission.entity.PanFilePermission;
import vip.xiaonuo.pan.modular.filePermission.param.PanFilePermissionAddParam;
import vip.xiaonuo.pan.modular.filePermission.param.PanFilePermissionEditParam;
import vip.xiaonuo.pan.modular.filePermission.param.PanFilePermissionIdParam;
import vip.xiaonuo.pan.modular.filePermission.param.PanFilePermissionPageParam;

import java.util.List;

/**
 * 文件权限Service接口
 *
 * @author mayike
 * @date  2023/09/06 21:15
 **/
public interface PanFilePermissionService extends IService<PanFilePermission> {

    /**
     * 获取文件权限分页
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    Page<PanFilePermission> page(PanFilePermissionPageParam panFilePermissionPageParam);

    /**
     * 添加文件权限
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    void add(PanFilePermissionAddParam panFilePermissionAddParam);

    /**
     * 编辑文件权限
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    void edit(PanFilePermissionEditParam panFilePermissionEditParam);

    /**
     * 删除文件权限
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    void delete(List<PanFilePermissionIdParam> panFilePermissionIdParamList);

    /**
     * 获取文件权限详情
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    PanFilePermission detail(PanFilePermissionIdParam panFilePermissionIdParam);

    /**
     * 获取文件权限详情
     *
     * @author mayike
     * @date  2023/09/06 21:15
     **/
    PanFilePermission queryEntity(String id);
}
