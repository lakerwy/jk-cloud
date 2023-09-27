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
import vip.xiaonuo.pan.modular.fileCenter.entity.PanFileClassification;
import vip.xiaonuo.pan.modular.fileCenter.param.PanFileClassificationAddParam;
import vip.xiaonuo.pan.modular.fileCenter.param.PanFileClassificationEditParam;
import vip.xiaonuo.pan.modular.fileCenter.param.PanFileClassificationIdParam;
import vip.xiaonuo.pan.modular.fileCenter.param.PanFileClassificationPageParam;

import java.util.List;

/**
 * 文件类型拓展表Service接口
 *
 * @author mayike
 * @date  2023/09/06 21:16
 **/
public interface PanFileClassificationService extends IService<PanFileClassification> {

    /**
     * 获取文件类型拓展表分页
     *
     * @author mayike
     * @date  2023/09/06 21:16
     */
    Page<PanFileClassification> page(PanFileClassificationPageParam panFileClassificationPageParam);

    /**
     * 添加文件类型拓展表
     *
     * @author mayike
     * @date  2023/09/06 21:16
     */
    void add(PanFileClassificationAddParam panFileClassificationAddParam);

    /**
     * 编辑文件类型拓展表
     *
     * @author mayike
     * @date  2023/09/06 21:16
     */
    void edit(PanFileClassificationEditParam panFileClassificationEditParam);

    /**
     * 删除文件类型拓展表
     *
     * @author mayike
     * @date  2023/09/06 21:16
     */
    void delete(List<PanFileClassificationIdParam> panFileClassificationIdParamList);

    /**
     * 获取文件类型拓展表详情
     *
     * @author mayike
     * @date  2023/09/06 21:16
     */
    PanFileClassification detail(PanFileClassificationIdParam panFileClassificationIdParam);

    /**
     * 获取文件类型拓展表详情
     *
     * @author mayike
     * @date  2023/09/06 21:16
     **/
    PanFileClassification queryEntity(String id);
}
