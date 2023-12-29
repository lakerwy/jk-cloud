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
package vip.xiaonuo.dev.modular.server.service.impl;

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
import vip.xiaonuo.dev.modular.server.entity.SystemServer;
import vip.xiaonuo.dev.modular.server.mapper.SystemServerMapper;
import vip.xiaonuo.dev.modular.server.param.SystemServerAddParam;
import vip.xiaonuo.dev.modular.server.param.SystemServerEditParam;
import vip.xiaonuo.dev.modular.server.param.SystemServerIdParam;
import vip.xiaonuo.dev.modular.server.param.SystemServerPageParam;
import vip.xiaonuo.dev.modular.server.service.SystemServerService;

import java.util.List;

/**
 * 系统服务表Service接口实现类
 *
 * @author ljy
 * @date  2023/12/29 11:31
 **/
@Service
public class SystemServerServiceImpl extends ServiceImpl<SystemServerMapper, SystemServer> implements SystemServerService {

    @Override
    public Page<SystemServer> page(SystemServerPageParam systemServerPageParam) {
        QueryWrapper<SystemServer> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(systemServerPageParam.getSystemName())) {
            queryWrapper.lambda().like(SystemServer::getSystemName, systemServerPageParam.getSystemName());
        }
        if(ObjectUtil.isNotEmpty(systemServerPageParam.getSystemType())) {
            queryWrapper.lambda().eq(SystemServer::getSystemType, systemServerPageParam.getSystemType());
        }
        if(ObjectUtil.isNotEmpty(systemServerPageParam.getSystemStatus())) {
            queryWrapper.lambda().eq(SystemServer::getSystemStatus, systemServerPageParam.getSystemStatus());
        }
        if(ObjectUtil.isAllNotEmpty(systemServerPageParam.getSortField(), systemServerPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(systemServerPageParam.getSortOrder());
            queryWrapper.orderBy(true, systemServerPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(systemServerPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SystemServer::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SystemServerAddParam systemServerAddParam) {
        SystemServer systemServer = BeanUtil.toBean(systemServerAddParam, SystemServer.class);
        this.save(systemServer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SystemServerEditParam systemServerEditParam) {
        SystemServer systemServer = this.queryEntity(systemServerEditParam.getId());
        BeanUtil.copyProperties(systemServerEditParam, systemServer);
        this.updateById(systemServer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SystemServerIdParam> systemServerIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(systemServerIdParamList, SystemServerIdParam::getId));
    }

    @Override
    public SystemServer detail(SystemServerIdParam systemServerIdParam) {
        return this.queryEntity(systemServerIdParam.getId());
    }

    @Override
    public SystemServer queryEntity(String id) {
        SystemServer systemServer = this.getById(id);
        if(ObjectUtil.isEmpty(systemServer)) {
            throw new CommonException("系统服务表不存在，id值为：{}", id);
        }
        return systemServer;
    }
}
