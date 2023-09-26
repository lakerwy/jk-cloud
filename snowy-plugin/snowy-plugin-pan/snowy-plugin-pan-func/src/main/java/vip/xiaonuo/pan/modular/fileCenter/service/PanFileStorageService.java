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
package vip.xiaonuo.pan.modular.fileCenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.pan.modular.fileCenter.entity.PanFileStorage;
import vip.xiaonuo.pan.modular.fileCenter.param.PanFileStorageAddParam;
import vip.xiaonuo.pan.modular.fileCenter.param.PanFileStorageEditParam;
import vip.xiaonuo.pan.modular.fileCenter.param.PanFileStorageIdParam;
import vip.xiaonuo.pan.modular.fileCenter.param.PanFileStoragePageParam;

import java.util.List;

/**
 * 存储实体类Service接口
 *
 * @author mayike
 * @date  2023/09/07 14:57
 **/
public interface PanFileStorageService extends IService<PanFileStorage> {

    /**
     * 获取存储实体类分页
     *
     * @author mayike
     * @date  2023/09/07 14:57
     */
    Page<PanFileStorage> page(PanFileStoragePageParam panFileStoragePageParam);

    /**
     * 添加存储实体类
     *
     * @author mayike
     * @date  2023/09/07 14:57
     */
    void add(PanFileStorageAddParam panFileStorageAddParam);

    /**
     * 编辑存储实体类
     *
     * @author mayike
     * @date  2023/09/07 14:57
     */
    void edit(PanFileStorageEditParam panFileStorageEditParam);

    /**
     * 删除存储实体类
     *
     * @author mayike
     * @date  2023/09/07 14:57
     */
    void delete(List<PanFileStorageIdParam> panFileStorageIdParamList);

    /**
     * 获取存储实体类详情
     *
     * @author mayike
     * @date  2023/09/07 14:57
     */
    PanFileStorage detail(PanFileStorageIdParam panFileStorageIdParam);

    /**
     * 获取存储实体类详情
     *
     * @author mayike
     * @date  2023/09/07 14:57
     **/
    PanFileStorage queryEntity(String id);

    Boolean checkStorage(long totalSize,int type);

    Long getTotalStorageSize(String userId);
}
