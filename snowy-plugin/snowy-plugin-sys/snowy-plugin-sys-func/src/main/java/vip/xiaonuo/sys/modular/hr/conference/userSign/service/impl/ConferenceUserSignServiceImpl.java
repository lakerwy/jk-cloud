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
package vip.xiaonuo.sys.modular.hr.conference.userSign.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.conference.userSign.entity.ConferenceUserSign;
import vip.xiaonuo.sys.modular.hr.conference.userSign.mapper.ConferenceUserSignMapper;
import vip.xiaonuo.sys.modular.hr.conference.userSign.param.ConferenceUserSignAddParam;
import vip.xiaonuo.sys.modular.hr.conference.userSign.param.ConferenceUserSignEditParam;
import vip.xiaonuo.sys.modular.hr.conference.userSign.param.ConferenceUserSignIdParam;
import vip.xiaonuo.sys.modular.hr.conference.userSign.param.ConferenceUserSignPageParam;
import vip.xiaonuo.sys.modular.hr.conference.userSign.service.ConferenceUserSignService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会议预约-签到表Service接口实现类
 *
 * @author ljy
 * @date  2023/11/24 09:27
 **/
@Service
public class ConferenceUserSignServiceImpl extends ServiceImpl<ConferenceUserSignMapper, ConferenceUserSign> implements ConferenceUserSignService {

    @Resource
    SysUserService sysUserService;
    @Override
    public Page<ConferenceUserSign> page(ConferenceUserSignPageParam conferenceUserSignPageParam) {
        QueryWrapper<ConferenceUserSign> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(conferenceUserSignPageParam.getConferenceConfigId())) {
            queryWrapper.lambda().eq(ConferenceUserSign::getConferenceConfigId, conferenceUserSignPageParam.getConferenceConfigId());
        }
        if(ObjectUtil.isNotEmpty(conferenceUserSignPageParam.getRezId())) {
            queryWrapper.lambda().eq(ConferenceUserSign::getRezId, conferenceUserSignPageParam.getRezId());
        }
        if(ObjectUtil.isNotEmpty(conferenceUserSignPageParam.getPhone())) {
            queryWrapper.lambda().eq(ConferenceUserSign::getPhone, conferenceUserSignPageParam.getPhone());
        }
        if(ObjectUtil.isAllNotEmpty(conferenceUserSignPageParam.getSortField(), conferenceUserSignPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(conferenceUserSignPageParam.getSortOrder());
            queryWrapper.orderBy(true, conferenceUserSignPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(conferenceUserSignPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ConferenceUserSign::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ConferenceUserSignAddParam conferenceUserSignAddParam) {
        ConferenceUserSign conferenceUserSign = BeanUtil.toBean(conferenceUserSignAddParam, ConferenceUserSign.class);
        List<SysUser>  userList = sysUserService.list(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount,conferenceUserSignAddParam.getPhone()));
        SysUser user = CollectionUtil.isNotEmpty(userList) ? userList.get(0) : new SysUser();
        conferenceUserSign.setUserId(user.getId());
        this.save(conferenceUserSign);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ConferenceUserSignEditParam conferenceUserSignEditParam) {
        ConferenceUserSign conferenceUserSign = this.queryEntity(conferenceUserSignEditParam.getId());
        BeanUtil.copyProperties(conferenceUserSignEditParam, conferenceUserSign);
        this.updateById(conferenceUserSign);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ConferenceUserSignIdParam> conferenceUserSignIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(conferenceUserSignIdParamList, ConferenceUserSignIdParam::getId));
    }

    @Override
    public ConferenceUserSign detail(ConferenceUserSignIdParam conferenceUserSignIdParam) {
        return this.queryEntity(conferenceUserSignIdParam.getId());
    }

    @Override
    public ConferenceUserSign queryEntity(String id) {
        ConferenceUserSign conferenceUserSign = this.getById(id);
        if(ObjectUtil.isEmpty(conferenceUserSign)) {
            throw new CommonException("会议预约-签到表不存在，id值为：{}", id);
        }
        return conferenceUserSign;
    }
}
