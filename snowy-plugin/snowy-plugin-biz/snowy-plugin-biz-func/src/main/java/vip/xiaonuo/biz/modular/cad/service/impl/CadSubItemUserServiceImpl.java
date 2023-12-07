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
package vip.xiaonuo.biz.modular.cad.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.cad.param.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cad.entity.CadSubItemUser;
import vip.xiaonuo.biz.modular.cad.mapper.CadSubItemUserMapper;
import vip.xiaonuo.biz.modular.cad.service.CadSubItemUserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CAD-子项-专业-人员关系表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/29 10:39
 **/
@DS("slave_1")
@Service
public class CadSubItemUserServiceImpl extends ServiceImpl<CadSubItemUserMapper, CadSubItemUser> implements CadSubItemUserService {

    @Override
    public Page<CadSubItemUser> page(CadSubItemUserPageParam cadSubItemUserPageParam) {
        QueryWrapper<CadSubItemUser> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(cadSubItemUserPageParam.getSortField(), cadSubItemUserPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cadSubItemUserPageParam.getSortOrder());
            queryWrapper.orderBy(true, cadSubItemUserPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cadSubItemUserPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CadSubItemUser::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CadSubItemUserAddParam cadSubItemUserAddParam) {
        CadSubItemUser cadSubItemUser = BeanUtil.toBean(cadSubItemUserAddParam, CadSubItemUser.class);
        this.save(cadSubItemUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CadSubItemUserEditParam cadSubItemUserEditParam) {
        CadSubItemUser cadSubItemUser = this.queryEntity(cadSubItemUserEditParam.getId());
        BeanUtil.copyProperties(cadSubItemUserEditParam, cadSubItemUser);
        this.updateById(cadSubItemUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CadSubItemUserIdParam> cadSubItemUserIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cadSubItemUserIdParamList, CadSubItemUserIdParam::getId));
    }

    @Override
    public CadSubItemUser detail(CadSubItemUserIdParam cadSubItemUserIdParam) {
        return this.queryEntity(cadSubItemUserIdParam.getId());
    }

    @Override
    public CadSubItemUser queryEntity(String id) {
        CadSubItemUser cadSubItemUser = this.getById(id);
        if(ObjectUtil.isEmpty(cadSubItemUser)) {
            throw new CommonException("CAD-子项-专业-人员关系表不存在，id值为：{}", id);
        }
        return cadSubItemUser;
    }

    @Override
    public void saveSubItemUser(SaveSubItemUserParam saveSubItemUserParam) {
        List<CadSubItemUser> newCadSubItemUserList = BeanUtil.copyToList(saveSubItemUserParam.getSubItemUserList(),CadSubItemUser.class);
        //获取历史数据
        List<CadSubItemUser> oldSubItemUserList = this.list(new QueryWrapper<CadSubItemUser>().lambda().eq(CadSubItemUser::getSubItemId,saveSubItemUserParam.getSubItemId()));
        //历史的主键Id集合
        List<String> oldIds = oldSubItemUserList.stream().map(x->x.getId()).collect(Collectors.toList());
        //新增的
        List<CadSubItemUser> addList = newCadSubItemUserList.stream().filter(x->ObjectUtil.isEmpty(x.getId())).collect(Collectors.toList());
        //更新的
        List<CadSubItemUser> updateList = newCadSubItemUserList.stream().filter(x->ObjectUtil.isNotEmpty(x.getId())).collect(Collectors.toList());
        //删除的
        oldIds.removeAll(updateList.stream().map(x->x.getId()).collect(Collectors.toList()));
        if(CollUtil.isNotEmpty(addList)){
            this.saveBatch(addList);
        }
        if(CollUtil.isNotEmpty(updateList)){
            this.updateBatchById(updateList);
        }
        if(CollUtil.isNotEmpty(oldIds)){
            this.removeByIds(oldIds);
        }
    }


}
