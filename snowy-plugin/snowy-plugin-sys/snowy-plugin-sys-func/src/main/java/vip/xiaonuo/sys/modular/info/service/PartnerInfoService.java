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
package vip.xiaonuo.sys.modular.info.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.info.entity.PartnerInfo;
import vip.xiaonuo.sys.modular.info.param.*;
import vip.xiaonuo.sys.modular.info.vo.PartnerGroupVo;
import vip.xiaonuo.sys.modular.info.vo.PartnerInfoVo;

import java.util.List;

/**
 * 合作伙伴-基本信息Service接口
 *
 * @author mayike
 * @date  2023/06/26 14:04
 **/
public interface PartnerInfoService extends IService<PartnerInfo> {

    /**
     * 获取合作伙伴-基本信息分页
     *
     * @author mayike
     * @date  2023/06/26 14:04
     */
    Page<PartnerInfo> page(PartnerInfoPageParam partnerInfoPageParam);

    /**
     * 添加合作伙伴-基本信息
     *
     * @author mayike
     * @date  2023/06/26 14:04
     */
    void add(PartnerInfoAddParam partnerInfoAddParam);

    /**
     * 编辑合作伙伴-基本信息
     *
     * @author mayike
     * @date  2023/06/26 14:04
     */
    void edit(PartnerInfoEditParam partnerInfoEditParam);

    /**
     * 删除合作伙伴-基本信息
     *
     * @author mayike
     * @date  2023/06/26 14:04
     */
    void delete(List<PartnerInfoIdParam> partnerInfoIdParamList);

    /**
     * 获取合作伙伴-基本信息详情
     *
     * @author mayike
     * @date  2023/06/26 14:04
     */
    PartnerInfoVo detail(PartnerInfoIdParam partnerInfoIdParam);

    /**
     * 获取合作伙伴-基本信息详情
     *
     * @author mayike
     * @date  2023/06/26 14:04
     **/
    PartnerInfo queryEntity(String id);

    PartnerInfoVo getInfoById(PartnerInfoIdParam partnerInfoIdParam);

    IPage<PartnerInfoVo> pageList(PartnerInfoPageParam partnerInfoPageParam);

    PartnerInfoVo getInfoByOrgId(PartnerInfoOrgIdParam partnerInfoOrgIdParam);

    /**
     * 获取合作伙伴-APP端列表（按大区分组）
     * @param partnerGroupParam
     * @return
     */
    List<PartnerGroupVo> listGroup(PartnerGroupParam partnerGroupParam);
}
