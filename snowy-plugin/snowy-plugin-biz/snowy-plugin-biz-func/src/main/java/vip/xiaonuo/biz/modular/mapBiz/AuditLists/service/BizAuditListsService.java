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
package vip.xiaonuo.biz.modular.mapBiz.AuditLists.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.entity.BizAuditLists;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.param.BizAuditListsAddParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.param.BizAuditListsEditParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.param.BizAuditListsIdParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.param.BizAuditListsPageParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.vo.BizAuditInfoVo;

import java.util.List;

/**
 * 业务表-设计校审主表Service接口
 *
 * @author mayike
 * @date  2023/06/30 16:43
 **/
public interface BizAuditListsService extends IService<BizAuditLists> {

    /**
     * 获取业务表-设计校审主表分页
     *
     * @author mayike
     * @date  2023/06/30 16:43
     */
    Page<BizAuditLists> page(BizAuditListsPageParam bizAuditListsPageParam);

    /**
     * 添加业务表-设计校审主表
     *
     * @author mayike
     * @date  2023/06/30 16:43
     */
    void add(BizAuditListsAddParam bizAuditListsAddParam);

    /**
     * 编辑业务表-设计校审主表
     *
     * @author mayike
     * @date  2023/06/30 16:43
     */
    void edit(BizAuditListsEditParam bizAuditListsEditParam);

    /**
     * 删除业务表-设计校审主表
     *
     * @author mayike
     * @date  2023/06/30 16:43
     */
    void delete(List<BizAuditListsIdParam> bizAuditListsIdParamList);

    /**
     * 获取业务表-设计校审主表详情
     *
     * @author mayike
     * @date  2023/06/30 16:43
     */
    BizAuditLists detail(BizAuditListsIdParam bizAuditListsIdParam);

    /**
     * 获取业务表-设计校审主表详情
     *
     * @author mayike
     * @date  2023/06/30 16:43
     **/
    BizAuditLists queryEntity(String id);

    /**
     * 获取业务表-设计校审主表详情
     *
     * @author mayike
     * @date  2023/06/30 16:43
     */
    BizAuditInfoVo info(BizAuditListsIdParam bizAuditListsIdParam);
}
