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
package vip.xiaonuo.biz.modular.jzscmohurd.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.jzscmohurd.entity.BizJzscMohurd;
import vip.xiaonuo.biz.modular.jzscmohurd.param.BizJzscMohurdAddParam;
import vip.xiaonuo.biz.modular.jzscmohurd.param.BizJzscMohurdEditParam;
import vip.xiaonuo.biz.modular.jzscmohurd.param.BizJzscMohurdIdParam;
import vip.xiaonuo.biz.modular.jzscmohurd.param.BizJzscMohurdPageParam;

import java.util.List;

/**
 * BIZ_JZSC_MOHURDService接口
 *
 * @author mayike
 * @date  2024/05/20 17:18
 **/
public interface BizJzscMohurdService extends IService<BizJzscMohurd> {

    /**
     * 获取BIZ_JZSC_MOHURD分页
     *
     * @author mayike
     * @date  2024/05/20 17:18
     */
    Page<BizJzscMohurd> page(BizJzscMohurdPageParam bizJzscMohurdPageParam);

    /**
     * 添加BIZ_JZSC_MOHURD
     *
     * @author mayike
     * @date  2024/05/20 17:18
     */
    void add(BizJzscMohurdAddParam bizJzscMohurdAddParam);

    /**
     * 编辑BIZ_JZSC_MOHURD
     *
     * @author mayike
     * @date  2024/05/20 17:18
     */
    void edit(BizJzscMohurdEditParam bizJzscMohurdEditParam);

    /**
     * 删除BIZ_JZSC_MOHURD
     *
     * @author mayike
     * @date  2024/05/20 17:18
     */
    void delete(List<BizJzscMohurdIdParam> bizJzscMohurdIdParamList);

    /**
     * 获取BIZ_JZSC_MOHURD详情
     *
     * @author mayike
     * @date  2024/05/20 17:18
     */
    BizJzscMohurd detail(BizJzscMohurdIdParam bizJzscMohurdIdParam);

    /**
     * 获取BIZ_JZSC_MOHURD详情
     *
     * @author mayike
     * @date  2024/05/20 17:18
     **/
    BizJzscMohurd queryEntity(String id);
}
