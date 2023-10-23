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
package vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.entity.HrMagicUserRelation;
import vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.param.HrMagicUserRelationAddParam;
import vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.param.HrMagicUserRelationEditParam;
import vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.param.HrMagicUserRelationIdParam;
import vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.param.HrMagicUserRelationPageParam;

import java.util.List;

/**
 * 魔点-建科人员关联表Service接口
 *
 * @author ljy
 * @date  2023/10/20 10:39
 **/
public interface HrMagicUserRelationService extends IService<HrMagicUserRelation> {

    /**
     * 获取魔点-建科人员关联表分页
     *
     * @author ljy
     * @date  2023/10/20 10:39
     */
    Page<HrMagicUserRelation> page(HrMagicUserRelationPageParam hrMagicUserRelationPageParam);

    /**
     * 添加魔点-建科人员关联表
     *
     * @author ljy
     * @date  2023/10/20 10:39
     */
    void add(HrMagicUserRelationAddParam hrMagicUserRelationAddParam);

    /**
     * 编辑魔点-建科人员关联表
     *
     * @author ljy
     * @date  2023/10/20 10:39
     */
    void edit(HrMagicUserRelationEditParam hrMagicUserRelationEditParam);

    /**
     * 删除魔点-建科人员关联表
     *
     * @author ljy
     * @date  2023/10/20 10:39
     */
    void delete(List<HrMagicUserRelationIdParam> hrMagicUserRelationIdParamList);

    /**
     * 获取魔点-建科人员关联表详情
     *
     * @author ljy
     * @date  2023/10/20 10:39
     */
    HrMagicUserRelation detail(HrMagicUserRelationIdParam hrMagicUserRelationIdParam);

    /**
     * 获取魔点-建科人员关联表详情
     *
     * @author ljy
     * @date  2023/10/20 10:39
     **/
    HrMagicUserRelation queryEntity(String id);
}
