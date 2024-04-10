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
import vip.xiaonuo.sys.modular.hr.assetManage.entity.JkAssetManage;
import vip.xiaonuo.sys.modular.hr.assetManage.mapper.JkAssetManageMapper;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageAddParam;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageEditParam;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageIdParam;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManagePageParam;
import vip.xiaonuo.sys.modular.hr.assetManage.service.JkAssetManageService;


import java.util.List;

/**
 * 建科资产管理表Service接口实现类
 *
 * @author ljy
 * @date  2024/04/10 08:47
 **/
@Service
public class JkAssetManageServiceImpl extends ServiceImpl<JkAssetManageMapper, JkAssetManage> implements JkAssetManageService {

    @Override
    public Page<JkAssetManage> page(JkAssetManagePageParam jkAssetManagePageParam) {
        QueryWrapper<JkAssetManage> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(jkAssetManagePageParam.getAssetTitle())) {
            queryWrapper.lambda().like(JkAssetManage::getAssetTitle, jkAssetManagePageParam.getAssetTitle());
        }
        if(ObjectUtil.isNotEmpty(jkAssetManagePageParam.getAssetType())) {
            queryWrapper.lambda().eq(JkAssetManage::getAssetType, jkAssetManagePageParam.getAssetType());
        }
        if(ObjectUtil.isAllNotEmpty(jkAssetManagePageParam.getSortField(), jkAssetManagePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(jkAssetManagePageParam.getSortOrder());
            queryWrapper.orderBy(true, jkAssetManagePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(jkAssetManagePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(JkAssetManage::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(JkAssetManageAddParam jkAssetManageAddParam) {
        JkAssetManage jkAssetManage = BeanUtil.toBean(jkAssetManageAddParam, JkAssetManage.class);
        this.save(jkAssetManage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(JkAssetManageEditParam jkAssetManageEditParam) {
        JkAssetManage jkAssetManage = this.queryEntity(jkAssetManageEditParam.getId());
        BeanUtil.copyProperties(jkAssetManageEditParam, jkAssetManage);
        this.updateById(jkAssetManage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<JkAssetManageIdParam> jkAssetManageIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(jkAssetManageIdParamList, JkAssetManageIdParam::getId));
    }

    @Override
    public JkAssetManage detail(JkAssetManageIdParam jkAssetManageIdParam) {
        return this.queryEntity(jkAssetManageIdParam.getId());
    }

    @Override
    public JkAssetManage queryEntity(String id) {
        JkAssetManage jkAssetManage = this.getById(id);
        if(ObjectUtil.isEmpty(jkAssetManage)) {
            throw new CommonException("建科资产管理表不存在，id值为：{}", id);
        }
        return jkAssetManage;
    }
}
