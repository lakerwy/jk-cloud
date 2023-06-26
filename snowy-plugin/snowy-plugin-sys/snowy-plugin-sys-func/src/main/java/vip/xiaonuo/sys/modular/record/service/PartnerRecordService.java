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
package vip.xiaonuo.sys.modular.record.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.record.entity.PartnerRecord;
import vip.xiaonuo.sys.modular.record.param.PartnerRecordAddParam;
import vip.xiaonuo.sys.modular.record.param.PartnerRecordEditParam;
import vip.xiaonuo.sys.modular.record.param.PartnerRecordIdParam;
import vip.xiaonuo.sys.modular.record.param.PartnerRecordPageParam;

import java.util.List;

/**
 * 合作伙伴-维护记录Service接口
 *
 * @author mayike
 * @date  2023/06/26 14:08
 **/
public interface PartnerRecordService extends IService<PartnerRecord> {

    /**
     * 获取合作伙伴-维护记录分页
     *
     * @author mayike
     * @date  2023/06/26 14:08
     */
    Page<PartnerRecord> page(PartnerRecordPageParam partnerRecordPageParam);

    /**
     * 添加合作伙伴-维护记录
     *
     * @author mayike
     * @date  2023/06/26 14:08
     */
    void add(PartnerRecordAddParam partnerRecordAddParam);

    /**
     * 编辑合作伙伴-维护记录
     *
     * @author mayike
     * @date  2023/06/26 14:08
     */
    void edit(PartnerRecordEditParam partnerRecordEditParam);

    /**
     * 删除合作伙伴-维护记录
     *
     * @author mayike
     * @date  2023/06/26 14:08
     */
    void delete(List<PartnerRecordIdParam> partnerRecordIdParamList);

    /**
     * 获取合作伙伴-维护记录详情
     *
     * @author mayike
     * @date  2023/06/26 14:08
     */
    PartnerRecord detail(PartnerRecordIdParam partnerRecordIdParam);

    /**
     * 获取合作伙伴-维护记录详情
     *
     * @author mayike
     * @date  2023/06/26 14:08
     **/
    PartnerRecord queryEntity(String id);
}
