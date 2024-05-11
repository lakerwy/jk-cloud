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
package vip.xiaonuo.bp.modular.platform.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.bp.modular.platform.param.user.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.bp.modular.platform.entity.BpUser;
import vip.xiaonuo.bp.modular.platform.mapper.BpUserMapper;
import vip.xiaonuo.bp.modular.platform.service.BpUserService;
import java.util.List;

/**
 * 招采平台 - 用户表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/05/09 08:41
 **/
@Service
@DS("slave_1")
public class BpUserServiceImpl extends ServiceImpl<BpUserMapper, BpUser> implements BpUserService {
    @Override
    public Page<BpUser> page(BpUserPageParam bpUserPageParam) {
        QueryWrapper<BpUser> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bpUserPageParam.getName())) {
            queryWrapper.lambda().like(BpUser::getName, bpUserPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(bpUserPageParam.getAccount())) {
            queryWrapper.lambda().like(BpUser::getAccount, bpUserPageParam.getAccount());
        }
        if(ObjectUtil.isNotEmpty(bpUserPageParam.getUserStatus())) {
            queryWrapper.lambda().eq(BpUser::getUserStatus, bpUserPageParam.getUserStatus());
        }
        if(ObjectUtil.isNotEmpty(bpUserPageParam.getEmail())) {
            queryWrapper.lambda().like(BpUser::getEmail, bpUserPageParam.getEmail());
        }
        if(ObjectUtil.isNotEmpty(bpUserPageParam.getPhone())) {
            queryWrapper.lambda().like(BpUser::getPhone, bpUserPageParam.getPhone());
        }
        if(ObjectUtil.isAllNotEmpty(bpUserPageParam.getSortField(), bpUserPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bpUserPageParam.getSortOrder());
            queryWrapper.orderBy(true, bpUserPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bpUserPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BpUser::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BpUserAddParam bpUserAddParam) {
        BpUser bpUser = BeanUtil.toBean(bpUserAddParam, BpUser.class);
        this.save(bpUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BpUserEditParam bpUserEditParam) {
        BpUser bpUser = this.queryEntity(bpUserEditParam.getId());
        BeanUtil.copyProperties(bpUserEditParam, bpUser);
        this.updateById(bpUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BpUserIdParam> bpUserIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bpUserIdParamList, BpUserIdParam::getId));
    }

    @Override
    public BpUser detail(BpUserIdParam bpUserIdParam) {
        return this.queryEntity(bpUserIdParam.getId());
    }

    @Override
    public BpUser queryEntity(String id) {
        BpUser bpUser = this.getById(id);
        if(ObjectUtil.isEmpty(bpUser)) {
            throw new CommonException("招采平台 - 用户表不存在，id值为：{}", id);
        }
        return bpUser;
    }
}
