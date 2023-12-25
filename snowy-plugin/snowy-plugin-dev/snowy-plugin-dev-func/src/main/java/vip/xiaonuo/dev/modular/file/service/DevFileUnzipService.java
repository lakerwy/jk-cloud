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
package vip.xiaonuo.dev.modular.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.dev.modular.file.entity.DevFileUnzip;
import vip.xiaonuo.dev.modular.file.param.DevFileUnzipAddParam;
import vip.xiaonuo.dev.modular.file.param.DevFileUnzipEditParam;
import vip.xiaonuo.dev.modular.file.param.DevFileUnzipIdParam;
import vip.xiaonuo.dev.modular.file.param.DevFileUnzipPageParam;

import java.util.List;

/**
 * 文件-附件压缩包解压列表Service接口
 *
 * @author ljy
 * @date  2023/12/24 20:09
 **/
public interface DevFileUnzipService extends IService<DevFileUnzip> {

    /**
     * 获取文件-附件压缩包解压列表分页
     *
     * @author ljy
     * @date  2023/12/24 20:09
     */
    Page<DevFileUnzip> page(DevFileUnzipPageParam devFileUnzipPageParam);

    /**
     * 添加文件-附件压缩包解压列表
     *
     * @author ljy
     * @date  2023/12/24 20:09
     */
    void add(DevFileUnzipAddParam devFileUnzipAddParam);

    /**
     * 编辑文件-附件压缩包解压列表
     *
     * @author ljy
     * @date  2023/12/24 20:09
     */
    void edit(DevFileUnzipEditParam devFileUnzipEditParam);

    /**
     * 删除文件-附件压缩包解压列表
     *
     * @author ljy
     * @date  2023/12/24 20:09
     */
    void delete(List<DevFileUnzipIdParam> devFileUnzipIdParamList);

    /**
     * 获取文件-附件压缩包解压列表详情
     *
     * @author ljy
     * @date  2023/12/24 20:09
     */
    DevFileUnzip detail(DevFileUnzipIdParam devFileUnzipIdParam);

    /**
     * 获取文件-附件压缩包解压列表详情
     *
     * @author ljy
     * @date  2023/12/24 20:09
     **/
    DevFileUnzip queryEntity(String id);
}
