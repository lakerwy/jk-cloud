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
package vip.xiaonuo.pan.modular.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.pan.modular.file.entity.PanFile;
import vip.xiaonuo.pan.modular.file.param.PanFileAddParam;
import vip.xiaonuo.pan.modular.file.param.PanFileEditParam;
import vip.xiaonuo.pan.modular.file.param.PanFileIdParam;
import vip.xiaonuo.pan.modular.file.param.PanFilePageParam;

import java.util.List;

/**
 * 文件Service接口
 *
 * @author mayike
 * @date  2023/09/06 21:15
 **/
public interface PanFileService extends IService<PanFile> {

    /**
     * 获取文件分页
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    Page<PanFile> page(PanFilePageParam panFilePageParam);

    /**
     * 添加文件
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    void add(PanFileAddParam panFileAddParam);

    /**
     * 编辑文件
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    void edit(PanFileEditParam panFileEditParam);

    /**
     * 删除文件
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    void delete(List<PanFileIdParam> panFileIdParamList);

    /**
     * 获取文件详情
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    PanFile detail(PanFileIdParam panFileIdParam);

    /**
     * 获取文件详情
     *
     * @author mayike
     * @date  2023/09/06 21:15
     **/
    PanFile queryEntity(String id);
}
