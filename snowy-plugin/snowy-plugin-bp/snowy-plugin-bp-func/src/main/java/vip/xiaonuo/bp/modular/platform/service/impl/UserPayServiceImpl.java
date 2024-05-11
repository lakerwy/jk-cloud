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
import vip.xiaonuo.bp.modular.platform.param.userSign.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.bp.modular.platform.entity.UserPay;
import vip.xiaonuo.bp.modular.platform.mapper.UserPayMapper;
import vip.xiaonuo.bp.modular.platform.service.UserPayService;

import java.util.List;

/**
 * 招采平台 - 用户 - （招采项目）费用缴纳信息表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/05/10 10:50
 **/
@Service
@DS("slave_1")
public class UserPayServiceImpl extends ServiceImpl<UserPayMapper, UserPay> implements UserPayService {

    @Override
    public Page<UserPay> page(UserPayPageParam userPayPageParam) {
        QueryWrapper<UserPay> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(userPayPageParam.getType())) {
            queryWrapper.lambda().eq(UserPay::getType, userPayPageParam.getType());
        }
        if(ObjectUtil.isAllNotEmpty(userPayPageParam.getSortField(), userPayPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(userPayPageParam.getSortOrder());
            queryWrapper.orderBy(true, userPayPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(userPayPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(UserPay::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(UserPayAddParam userPayAddParam) {
        UserPay userPay = BeanUtil.toBean(userPayAddParam, UserPay.class);
        this.save(userPay);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(UserPayEditParam userPayEditParam) {
        UserPay userPay = this.queryEntity(userPayEditParam.getId());
        BeanUtil.copyProperties(userPayEditParam, userPay);
        this.updateById(userPay);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<UserPayIdParam> userPayIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(userPayIdParamList, UserPayIdParam::getId));
    }

    @Override
    public UserPay detail(UserPayIdParam userPayIdParam) {
        return this.queryEntity(userPayIdParam.getId());
    }

    @Override
    public UserPay queryEntity(String id) {
        UserPay userPay = this.getById(id);
        if(ObjectUtil.isEmpty(userPay)) {
            throw new CommonException("招采平台 - 用户 - （招采项目）费用缴纳信息表不存在，id值为：{}", id);
        }
        return userPay;
    }
}
