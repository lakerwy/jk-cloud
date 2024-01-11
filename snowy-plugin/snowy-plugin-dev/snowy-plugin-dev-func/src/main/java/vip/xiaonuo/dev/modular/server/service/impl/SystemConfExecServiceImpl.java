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
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.modular.server.consts.InitConfConstant;
import vip.xiaonuo.dev.modular.server.entity.SystemConfExec;
import vip.xiaonuo.dev.modular.server.mapper.SystemConfExecMapper;
import vip.xiaonuo.dev.modular.server.param.SystemConfExecAddParam;
import vip.xiaonuo.dev.modular.server.param.SystemConfExecEditParam;
import vip.xiaonuo.dev.modular.server.param.SystemConfExecIdParam;
import vip.xiaonuo.dev.modular.server.param.SystemConfExecPageParam;
import vip.xiaonuo.dev.modular.server.service.SystemConfExecService;


import javax.annotation.Resource;
import java.util.List;

/**
 * 系统服务操作记录表Service接口实现类
 *
 * @author ljy
 * @date  2024/01/03 16:07
 **/
@Service
public class SystemConfExecServiceImpl extends ServiceImpl<SystemConfExecMapper, SystemConfExec> implements SystemConfExecService {

    @Resource
    CommonCacheOperator commonCacheOperator;

    @Override
    public Page<SystemConfExec> page(SystemConfExecPageParam systemConfExecPageParam) {
        QueryWrapper<SystemConfExec> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(systemConfExecPageParam.getSystemId())) {
            queryWrapper.lambda().eq(SystemConfExec::getSystemId, systemConfExecPageParam.getSystemId());
        }
        if(ObjectUtil.isAllNotEmpty(systemConfExecPageParam.getSortField(), systemConfExecPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(systemConfExecPageParam.getSortOrder());
            queryWrapper.orderBy(true, systemConfExecPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(systemConfExecPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SystemConfExec::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SystemConfExecAddParam systemConfExecAddParam) {
        SystemConfExec systemConfExec = BeanUtil.toBean(systemConfExecAddParam, SystemConfExec.class);
        systemConfExec.setSystemServerNo(generateNo());
        this.save(systemConfExec);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SystemConfExecEditParam systemConfExecEditParam) {
        SystemConfExec systemConfExec = this.queryEntity(systemConfExecEditParam.getId());
        BeanUtil.copyProperties(systemConfExecEditParam, systemConfExec);
        this.updateById(systemConfExec);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SystemConfExecIdParam> systemConfExecIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(systemConfExecIdParamList, SystemConfExecIdParam::getId));
    }

    @Override
    public SystemConfExec detail(SystemConfExecIdParam systemConfExecIdParam) {
        return this.queryEntity(systemConfExecIdParam.getId());
    }

    @Override
    public SystemConfExec queryEntity(String id) {
        SystemConfExec systemConfExec = this.getById(id);
        if(ObjectUtil.isEmpty(systemConfExec)) {
            throw new CommonException("系统服务操作记录表不存在，id值为：{}", id);
        }
        return systemConfExec;
    }

    public Integer generateNo(){
        Object o = commonCacheOperator.get(InitConfConstant.SYSTEM_NO_KEY);
        int sysNo = 1000;
        if(ObjectUtil.isNotEmpty(o)){
            sysNo = Integer.parseInt(String.valueOf(commonCacheOperator.get(InitConfConstant.SYSTEM_NO_KEY))) + 1;
            commonCacheOperator.put(InitConfConstant.SYSTEM_NO_KEY,sysNo);
        }else {
            commonCacheOperator.put(InitConfConstant.SYSTEM_NO_KEY,1000);
        }
        return sysNo;
    }
}
