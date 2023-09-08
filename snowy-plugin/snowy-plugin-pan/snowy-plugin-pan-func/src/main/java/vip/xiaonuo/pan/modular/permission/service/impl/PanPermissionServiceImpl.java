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
package vip.xiaonuo.pan.modular.permission.service.impl;

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
import vip.xiaonuo.pan.modular.permission.entity.PanPermission;
import vip.xiaonuo.pan.modular.permission.mapper.PanPermissionMapper;
import vip.xiaonuo.pan.modular.permission.param.PanPermissionAddParam;
import vip.xiaonuo.pan.modular.permission.param.PanPermissionEditParam;
import vip.xiaonuo.pan.modular.permission.param.PanPermissionIdParam;
import vip.xiaonuo.pan.modular.permission.param.PanPermissionPageParam;
import vip.xiaonuo.pan.modular.permission.service.PanPermissionService;

import java.util.List;

/**
 * 云盘-权限表Service接口实现类
 *
 * @author mayike
 * @date  2023/09/08 16:07
 **/
@Service
public class PanPermissionServiceImpl extends ServiceImpl<PanPermissionMapper, PanPermission> implements PanPermissionService {

    @Override
    public Page<PanPermission> page(PanPermissionPageParam panPermissionPageParam) {
        QueryWrapper<PanPermission> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(panPermissionPageParam.getSortField(), panPermissionPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panPermissionPageParam.getSortOrder());
            queryWrapper.orderBy(true, panPermissionPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panPermissionPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanPermission::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanPermissionAddParam panPermissionAddParam) {
        PanPermission panPermission = BeanUtil.toBean(panPermissionAddParam, PanPermission.class);
        this.save(panPermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanPermissionEditParam panPermissionEditParam) {
        PanPermission panPermission = this.queryEntity(panPermissionEditParam.getId());
        BeanUtil.copyProperties(panPermissionEditParam, panPermission);
        this.updateById(panPermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanPermissionIdParam> panPermissionIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panPermissionIdParamList, PanPermissionIdParam::getId));
    }

    @Override
    public PanPermission detail(PanPermissionIdParam panPermissionIdParam) {
        return this.queryEntity(panPermissionIdParam.getId());
    }

    @Override
    public PanPermission queryEntity(String id) {
        PanPermission panPermission = this.getById(id);
        if(ObjectUtil.isEmpty(panPermission)) {
            throw new CommonException("云盘-权限表不存在，id值为：{}", id);
        }
        return panPermission;
    }
}
