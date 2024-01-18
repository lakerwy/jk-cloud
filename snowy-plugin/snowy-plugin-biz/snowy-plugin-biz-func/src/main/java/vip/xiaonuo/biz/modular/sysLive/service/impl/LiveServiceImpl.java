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
package vip.xiaonuo.biz.modular.sysLive.service.impl;

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
import vip.xiaonuo.biz.modular.sysLive.entity.Live;
import vip.xiaonuo.biz.modular.sysLive.mapper.LiveMapper;
import vip.xiaonuo.biz.modular.sysLive.param.LiveAddParam;
import vip.xiaonuo.biz.modular.sysLive.param.LiveEditParam;
import vip.xiaonuo.biz.modular.sysLive.param.LiveIdParam;
import vip.xiaonuo.biz.modular.sysLive.param.LivePageParam;
import vip.xiaonuo.biz.modular.sysLive.service.LiveService;

import java.util.List;

/**
 * 直播地址表Service接口实现类
 *
 * @author mayike
 * @date  2024/01/17 14:10
 **/
@Service
public class LiveServiceImpl extends ServiceImpl<LiveMapper, Live> implements LiveService {

    @Override
    public Page<Live> page(LivePageParam livePageParam) {
        QueryWrapper<Live> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(livePageParam.getSortField(), livePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(livePageParam.getSortOrder());
            queryWrapper.orderBy(true, livePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(livePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Live::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(LiveAddParam liveAddParam) {
        Live live = BeanUtil.toBean(liveAddParam, Live.class);
        this.save(live);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(LiveEditParam liveEditParam) {
        Live live = this.queryEntity(liveEditParam.getId());
        BeanUtil.copyProperties(liveEditParam, live);
        this.updateById(live);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<LiveIdParam> liveIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(liveIdParamList, LiveIdParam::getId));
    }

    @Override
    public Live detail(LiveIdParam liveIdParam) {
        return this.queryEntity(liveIdParam.getId());
    }

    @Override
    public Live queryEntity(String id) {
        Live live = this.getById(id);
        if(ObjectUtil.isEmpty(live)) {
            throw new CommonException("直播地址表不存在，id值为：{}", id);
        }
        return live;
    }
}
