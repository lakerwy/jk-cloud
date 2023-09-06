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
package vip.xiaonuo.pan.modular.fileType.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.pan.modular.fileType.entity.PanFileType;
import vip.xiaonuo.pan.modular.fileType.param.PanFileTypeAddParam;
import vip.xiaonuo.pan.modular.fileType.param.PanFileTypeEditParam;
import vip.xiaonuo.pan.modular.fileType.param.PanFileTypeIdParam;
import vip.xiaonuo.pan.modular.fileType.param.PanFileTypePageParam;

import java.util.List;

/**
 * 文件类型Service接口
 *
 * @author mayike
 * @date  2023/09/06 21:18
 **/
public interface PanFileTypeService extends IService<PanFileType> {

    /**
     * 获取文件类型分页
     *
     * @author mayike
     * @date  2023/09/06 21:18
     */
    Page<PanFileType> page(PanFileTypePageParam panFileTypePageParam);

    /**
     * 添加文件类型
     *
     * @author mayike
     * @date  2023/09/06 21:18
     */
    void add(PanFileTypeAddParam panFileTypeAddParam);

    /**
     * 编辑文件类型
     *
     * @author mayike
     * @date  2023/09/06 21:18
     */
    void edit(PanFileTypeEditParam panFileTypeEditParam);

    /**
     * 删除文件类型
     *
     * @author mayike
     * @date  2023/09/06 21:18
     */
    void delete(List<PanFileTypeIdParam> panFileTypeIdParamList);

    /**
     * 获取文件类型详情
     *
     * @author mayike
     * @date  2023/09/06 21:18
     */
    PanFileType detail(PanFileTypeIdParam panFileTypeIdParam);

    /**
     * 获取文件类型详情
     *
     * @author mayike
     * @date  2023/09/06 21:18
     **/
    PanFileType queryEntity(String id);
}
