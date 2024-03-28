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
package vip.xiaonuo.sys.modular.hr.registerCert.service.impl;

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
import vip.xiaonuo.sys.modular.hr.registerCert.entity.RegAlwUser;
import vip.xiaonuo.sys.modular.hr.registerCert.mapper.RegAlwUserMapper;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegAlwUserAddParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegAlwUserEditParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegAlwUserIdParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegAlwUserPageParam;
import vip.xiaonuo.sys.modular.hr.registerCert.service.RegAlwUserService;

import java.util.List;

/**
 * 注册补贴-用户基本信息Service接口实现类
 *
 * @author ljy
 * @date  2024/03/27 15:22
 **/
@Service
public class RegAlwUserServiceImpl extends ServiceImpl<RegAlwUserMapper, RegAlwUser> implements RegAlwUserService {

    @Override
    public Page<RegAlwUser> page(RegAlwUserPageParam regAlwUserPageParam) {
        QueryWrapper<RegAlwUser> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(regAlwUserPageParam.getName())) {
            queryWrapper.lambda().eq(RegAlwUser::getName, regAlwUserPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(regAlwUserPageParam.getIdCardNumber())) {
            queryWrapper.lambda().eq(RegAlwUser::getIdCardNumber, regAlwUserPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isAllNotEmpty(regAlwUserPageParam.getSortField(), regAlwUserPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(regAlwUserPageParam.getSortOrder());
            queryWrapper.orderBy(true, regAlwUserPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(regAlwUserPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(RegAlwUser::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(RegAlwUserAddParam regAlwUserAddParam) {
        RegAlwUser regAlwUser = BeanUtil.toBean(regAlwUserAddParam, RegAlwUser.class);
        this.save(regAlwUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(RegAlwUserEditParam regAlwUserEditParam) {
        RegAlwUser regAlwUser = this.queryEntity(regAlwUserEditParam.getId());
        BeanUtil.copyProperties(regAlwUserEditParam, regAlwUser);
        this.updateById(regAlwUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<RegAlwUserIdParam> regAlwUserIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(regAlwUserIdParamList, RegAlwUserIdParam::getId));
    }

    @Override
    public RegAlwUser detail(RegAlwUserIdParam regAlwUserIdParam) {
        return this.queryEntity(regAlwUserIdParam.getId());
    }

    @Override
    public RegAlwUser queryEntity(String id) {
        RegAlwUser regAlwUser = this.getById(id);
        if(ObjectUtil.isEmpty(regAlwUser)) {
            throw new CommonException("注册补贴-用户基本信息不存在，id值为：{}", id);
        }
        return regAlwUser;
    }
}
