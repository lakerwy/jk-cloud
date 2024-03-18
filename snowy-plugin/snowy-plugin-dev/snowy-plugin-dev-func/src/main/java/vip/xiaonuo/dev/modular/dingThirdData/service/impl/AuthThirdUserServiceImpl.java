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
package vip.xiaonuo.dev.modular.dingThirdData.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.modular.dingThirdData.entity.AuthThirdUser;
import vip.xiaonuo.dev.modular.dingThirdData.mapper.AuthThirdUserMapper;
import vip.xiaonuo.dev.modular.dingThirdData.param.AuthThirdUserAddParam;
import vip.xiaonuo.dev.modular.dingThirdData.param.AuthThirdUserEditParam;
import vip.xiaonuo.dev.modular.dingThirdData.param.AuthThirdUserIdParam;
import vip.xiaonuo.dev.modular.dingThirdData.param.AuthThirdUserPageParam;
import vip.xiaonuo.dev.modular.dingThirdData.service.AuthThirdUserService;

import java.util.List;

/**
 * 三方用户Service接口实现类
 *
 * @author ljy
 * @date  2024/03/08 08:39
 **/
@Service
public class AuthThirdUserServiceImpl extends ServiceImpl<AuthThirdUserMapper, AuthThirdUser> implements AuthThirdUserService {

    @Override
    public Page<AuthThirdUser> page(AuthThirdUserPageParam authThirdUserPageParam) {
        QueryWrapper<AuthThirdUser> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(authThirdUserPageParam.getThirdId())) {
            queryWrapper.lambda().eq(AuthThirdUser::getThirdId, authThirdUserPageParam.getThirdId());
        }
        if(ObjectUtil.isNotEmpty(authThirdUserPageParam.getUnionId())) {
            queryWrapper.lambda().eq(AuthThirdUser::getUnionId, authThirdUserPageParam.getUnionId());
        }
        if(ObjectUtil.isNotEmpty(authThirdUserPageParam.getUserId())) {
            queryWrapper.lambda().eq(AuthThirdUser::getUserId, authThirdUserPageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(authThirdUserPageParam.getCategory())) {
            queryWrapper.lambda().eq(AuthThirdUser::getCategory, authThirdUserPageParam.getCategory());
        }
        if(ObjectUtil.isAllNotEmpty(authThirdUserPageParam.getSortField(), authThirdUserPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(authThirdUserPageParam.getSortOrder());
            queryWrapper.orderBy(true, authThirdUserPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(authThirdUserPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(AuthThirdUser::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(AuthThirdUserAddParam authThirdUserAddParam) {
        AuthThirdUser authThirdUser = BeanUtil.toBean(authThirdUserAddParam, AuthThirdUser.class);
        this.save(authThirdUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(AuthThirdUserEditParam authThirdUserEditParam) {
        AuthThirdUser authThirdUser = this.queryEntity(authThirdUserEditParam.getId());
        BeanUtil.copyProperties(authThirdUserEditParam, authThirdUser);
        this.updateById(authThirdUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<AuthThirdUserIdParam> authThirdUserIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(authThirdUserIdParamList, AuthThirdUserIdParam::getId));
    }

    @Override
    public AuthThirdUser detail(AuthThirdUserIdParam authThirdUserIdParam) {
        return this.queryEntity(authThirdUserIdParam.getId());
    }

    @Override
    public AuthThirdUser queryEntity(String id) {
        AuthThirdUser authThirdUser = this.getById(id);
        if(ObjectUtil.isEmpty(authThirdUser)) {
            throw new CommonException("三方用户不存在，id值为：{}", id);
        }
        return authThirdUser;
    }

    /**
     * 当thirdId存在，而unionId不存在，修改
     * 当thirdId和unionId都不存在，新增
     * */
    @Override
    public Boolean updateAuthThird(String userId,String thirdId,String category,String name,String unionId){
        List<AuthThirdUser> oldList = this.list(new QueryWrapper<AuthThirdUser>().lambda().eq(AuthThirdUser::getCategory,category).eq(AuthThirdUser::getUserId,userId).eq(AuthThirdUser::getThirdId,thirdId));
        if(CollectionUtil.isNotEmpty(oldList) && StringUtils.isNotEmpty(oldList.get(0).getUnionId())){ return true; }

        AuthThirdUser thirdUser = CollectionUtil.isNotEmpty(oldList) ? oldList.get(0) : new AuthThirdUser();
        thirdUser.setThirdId(thirdId);
        thirdUser.setUserId(userId);
        thirdUser.setCategory(category);
        thirdUser.setName(name);
        thirdUser.setUnionId(unionId);
        return this.saveOrUpdate(thirdUser);
    }

    @Override
    public String getThirdIdByUserId(String userId, String category) {
        AuthThirdUser thirdUser = this.getOne(new LambdaQueryWrapper<AuthThirdUser>().eq(AuthThirdUser::getUserId, userId)
                .eq(AuthThirdUser::getCategory, category).last(" limit 1 "));
        return ObjectUtil.isEmpty(thirdUser) ? "" : thirdUser.getThirdId();
    }

    @Override
    public String getUnionIdByUserId(String userId,String category){
        AuthThirdUser thirdUser = this.getOne(new LambdaQueryWrapper<AuthThirdUser>().eq(AuthThirdUser::getUserId, userId)
                .eq(AuthThirdUser::getCategory, category).last(" limit 1 "));
        return ObjectUtil.isNotEmpty(thirdUser) && StringUtils.isNotEmpty(thirdUser.getUnionId()) ? "" : thirdUser.getUnionId();
    }
}
