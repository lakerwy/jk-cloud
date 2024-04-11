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
package vip.xiaonuo.sys.modular.hr.assetManage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.assetManage.entity.JkAssetManageRelation;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageRelationAddParam;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageRelationEditParam;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageRelationIdParam;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageRelationPageParam;

import java.util.List;

/**
 * 资产管理申请关联表Service接口
 *
 * @author ljy
 * @date  2024/04/11 11:18
 **/
public interface JkAssetManageRelationService extends IService<JkAssetManageRelation> {

    /**
     * 获取资产管理申请关联表分页
     *
     * @author ljy
     * @date  2024/04/11 11:18
     */
    Page<JkAssetManageRelation> page(JkAssetManageRelationPageParam jkAssetManageRelationPageParam);

    /**
     * 添加资产管理申请关联表
     *
     * @author ljy
     * @date  2024/04/11 11:18
     */
    void add(JkAssetManageRelationAddParam jkAssetManageRelationAddParam);

    /**
     * 编辑资产管理申请关联表
     *
     * @author ljy
     * @date  2024/04/11 11:18
     */
    void edit(JkAssetManageRelationEditParam jkAssetManageRelationEditParam);

    /**
     * 删除资产管理申请关联表
     *
     * @author ljy
     * @date  2024/04/11 11:18
     */
    void delete(List<JkAssetManageRelationIdParam> jkAssetManageRelationIdParamList);

    /**
     * 获取资产管理申请关联表详情
     *
     * @author ljy
     * @date  2024/04/11 11:18
     */
    JkAssetManageRelation detail(JkAssetManageRelationIdParam jkAssetManageRelationIdParam);

    /**
     * 获取资产管理申请关联表详情
     *
     * @author ljy
     * @date  2024/04/11 11:18
     **/
    JkAssetManageRelation queryEntity(String id);
}
