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
package vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.entity.BizAuditListsDetails;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.param.BizAuditListsDetailsAddParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.param.BizAuditListsDetailsEditParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.param.BizAuditListsDetailsIdParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.param.BizAuditListsDetailsPageParam;

import java.util.List;

/**
 * 业务表-设计校审记录表Service接口
 *
 * @author mayike
 * @date  2023/07/03 11:33
 **/
public interface BizAuditListsDetailsService extends IService<BizAuditListsDetails> {

    /**
     * 获取业务表-设计校审记录表分页
     *
     * @author mayike
     * @date  2023/07/03 11:33
     */
    Page<BizAuditListsDetails> page(BizAuditListsDetailsPageParam bizAuditListsDetailsPageParam);

    /**
     * 添加业务表-设计校审记录表
     *
     * @author mayike
     * @date  2023/07/03 11:33
     */
    void add(BizAuditListsDetailsAddParam bizAuditListsDetailsAddParam);

    /**
     * 编辑业务表-设计校审记录表
     *
     * @author mayike
     * @date  2023/07/03 11:33
     */
    void edit(BizAuditListsDetailsEditParam bizAuditListsDetailsEditParam);

    /**
     * 删除业务表-设计校审记录表
     *
     * @author mayike
     * @date  2023/07/03 11:33
     */
    void delete(List<BizAuditListsDetailsIdParam> bizAuditListsDetailsIdParamList);

    /**
     * 获取业务表-设计校审记录表详情
     *
     * @author mayike
     * @date  2023/07/03 11:33
     */
    BizAuditListsDetails detail(BizAuditListsDetailsIdParam bizAuditListsDetailsIdParam);

    /**
     * 获取业务表-设计校审记录表详情
     *
     * @author mayike
     * @date  2023/07/03 11:33
     **/
    BizAuditListsDetails queryEntity(String id);
}
