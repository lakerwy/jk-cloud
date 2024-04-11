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
package vip.xiaonuo.sys.modular.hr.assetManage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.assetManage.entity.JkAssetManageRelation;
import vip.xiaonuo.sys.modular.hr.assetManage.mapper.JkAssetManageRelationMapper;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageRelationAddParam;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageRelationEditParam;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageRelationIdParam;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageRelationPageParam;
import vip.xiaonuo.sys.modular.hr.assetManage.service.JkAssetManageRelationService;

import java.util.List;

/**
 * 资产管理申请关联表Service接口实现类
 *
 * @author ljy
 * @date  2024/04/11 11:18
 **/
@Service
public class JkAssetManageRelationServiceImpl extends ServiceImpl<JkAssetManageRelationMapper, JkAssetManageRelation> implements JkAssetManageRelationService {

    @Override
    public Page<JkAssetManageRelation> page(JkAssetManageRelationPageParam jkAssetManageRelationPageParam) {
        QueryWrapper<JkAssetManageRelation> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(jkAssetManageRelationPageParam.getUserId())) {
            queryWrapper.lambda().eq(JkAssetManageRelation::getUserId, jkAssetManageRelationPageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(jkAssetManageRelationPageParam.getAssetId())) {
            queryWrapper.lambda().eq(JkAssetManageRelation::getAssetId, jkAssetManageRelationPageParam.getAssetId());
        }
        if(ObjectUtil.isNotEmpty(jkAssetManageRelationPageParam.getIsPass())) {
            queryWrapper.lambda().eq(JkAssetManageRelation::getIsPass, jkAssetManageRelationPageParam.getIsPass());
        }
        if(ObjectUtil.isAllNotEmpty(jkAssetManageRelationPageParam.getSortField(), jkAssetManageRelationPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(jkAssetManageRelationPageParam.getSortOrder());
            queryWrapper.orderBy(true, jkAssetManageRelationPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(jkAssetManageRelationPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(JkAssetManageRelation::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(JkAssetManageRelationAddParam jkAssetManageRelationAddParam) {
        JkAssetManageRelation jkAssetManageRelation = BeanUtil.toBean(jkAssetManageRelationAddParam, JkAssetManageRelation.class);
        this.save(jkAssetManageRelation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(JkAssetManageRelationEditParam jkAssetManageRelationEditParam) {
        JkAssetManageRelation jkAssetManageRelation = this.queryEntity(jkAssetManageRelationEditParam.getId());
        BeanUtil.copyProperties(jkAssetManageRelationEditParam, jkAssetManageRelation);
        this.updateById(jkAssetManageRelation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<JkAssetManageRelationIdParam> jkAssetManageRelationIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(jkAssetManageRelationIdParamList, JkAssetManageRelationIdParam::getId));
    }

    @Override
    public JkAssetManageRelation detail(JkAssetManageRelationIdParam jkAssetManageRelationIdParam) {
        return this.queryEntity(jkAssetManageRelationIdParam.getId());
    }

    @Override
    public JkAssetManageRelation queryEntity(String id) {
        JkAssetManageRelation jkAssetManageRelation = this.getById(id);
        if(ObjectUtil.isEmpty(jkAssetManageRelation)) {
            throw new CommonException("资产管理申请关联表不存在，id值为：{}", id);
        }
        return jkAssetManageRelation;
    }
}
