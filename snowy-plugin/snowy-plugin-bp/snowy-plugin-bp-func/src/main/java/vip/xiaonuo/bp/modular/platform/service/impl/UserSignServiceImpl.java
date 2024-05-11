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
import vip.xiaonuo.bp.modular.platform.entity.*;
import vip.xiaonuo.bp.modular.platform.param.userSign.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.bp.modular.platform.mapper.UserSignMapper;
import vip.xiaonuo.bp.modular.platform.service.UserSignService;

import java.util.List;

/**
 * 招采平台 - 用户 - （招采项目）报名记录表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/05/10 10:49
 **/
@Service
@DS("slave_1")
public class UserSignServiceImpl extends ServiceImpl<UserSignMapper, UserSign> implements UserSignService {

    @Override
    public Page<UserSign> page(UserSignPageParam userSignPageParam) {
        QueryWrapper<UserSign> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(userSignPageParam.getBmStatus())) {
            queryWrapper.lambda().eq(UserSign::getBmStatus, userSignPageParam.getBmStatus());
        }
        if(ObjectUtil.isAllNotEmpty(userSignPageParam.getSortField(), userSignPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(userSignPageParam.getSortOrder());
            queryWrapper.orderBy(true, userSignPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(userSignPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(UserSign::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(UserSignAddParam userSignAddParam) {
        UserSign userSign = BeanUtil.toBean(userSignAddParam, UserSign.class);
        this.save(userSign);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(UserSignEditParam userSignEditParam) {
        UserSign userSign = this.queryEntity(userSignEditParam.getId());
        BeanUtil.copyProperties(userSignEditParam, userSign);
        this.updateById(userSign);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<UserSignIdParam> userSignIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(userSignIdParamList, UserSignIdParam::getId));
    }

    @Override
    public UserSign detail(UserSignIdParam userSignIdParam) {
        return this.queryEntity(userSignIdParam.getId());
    }

    @Override
    public UserSign queryEntity(String id) {
        UserSign userSign = this.getById(id);
        if(ObjectUtil.isEmpty(userSign)) {
            throw new CommonException("招采平台 - 用户 - （招采项目）报名记录表不存在，id值为：{}", id);
        }
        return userSign;
    }
}
