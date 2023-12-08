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
package vip.xiaonuo.flw.modular.model.service.impl;

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
import vip.xiaonuo.flw.modular.model.entity.FlwListener;
import vip.xiaonuo.flw.modular.model.mapper.FlwListenerMapper;
import vip.xiaonuo.flw.modular.model.param.FlwListenerAddParam;
import vip.xiaonuo.flw.modular.model.param.FlwListenerEditParam;
import vip.xiaonuo.flw.modular.model.param.FlwListenerIdParam;
import vip.xiaonuo.flw.modular.model.param.FlwListenerPageParam;
import vip.xiaonuo.flw.modular.model.service.FlwListenerService;

import java.util.List;

/**
 * 监听器类Service接口实现类
 *
 * @author mayike
 * @date  2023/12/08 10:24
 **/
@Service
public class FlwListenerServiceImpl extends ServiceImpl<FlwListenerMapper, FlwListener> implements FlwListenerService {

    @Override
    public Page<FlwListener> page(FlwListenerPageParam flwListenerPageParam) {
        QueryWrapper<FlwListener> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotEmpty(flwListenerPageParam.getListenerType())){
            queryWrapper.lambda().eq(FlwListener::getListenerType,flwListenerPageParam.getListenerType());
        }
        if(StrUtil.isNotEmpty(flwListenerPageParam.getSearchKey())){
            queryWrapper.lambda().like(FlwListener::getListenerRemark,flwListenerPageParam.getSearchKey()).or().like(FlwListener::getListenerClassName,flwListenerPageParam.getSearchKey())
                    .or().like(FlwListener::getListenerClass,flwListenerPageParam.getSearchKey());
        }
        if(ObjectUtil.isAllNotEmpty(flwListenerPageParam.getSortField(), flwListenerPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(flwListenerPageParam.getSortOrder());
            queryWrapper.orderBy(true, flwListenerPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(flwListenerPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(FlwListener::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(FlwListenerAddParam flwListenerAddParam) {
        FlwListener flwListener = BeanUtil.toBean(flwListenerAddParam, FlwListener.class);
        this.save(flwListener);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FlwListenerEditParam flwListenerEditParam) {
        FlwListener flwListener = this.queryEntity(flwListenerEditParam.getId());
        BeanUtil.copyProperties(flwListenerEditParam, flwListener);
        this.updateById(flwListener);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FlwListenerIdParam> flwListenerIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(flwListenerIdParamList, FlwListenerIdParam::getId));
    }

    @Override
    public FlwListener detail(FlwListenerIdParam flwListenerIdParam) {
        return this.queryEntity(flwListenerIdParam.getId());
    }

    @Override
    public FlwListener queryEntity(String id) {
        FlwListener flwListener = this.getById(id);
        if(ObjectUtil.isEmpty(flwListener)) {
            throw new CommonException("监听器类不存在，id值为：{}", id);
        }
        return flwListener;
    }
}
