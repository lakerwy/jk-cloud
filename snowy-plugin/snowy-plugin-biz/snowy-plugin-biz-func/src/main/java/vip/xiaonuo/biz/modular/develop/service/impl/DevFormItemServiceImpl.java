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
package vip.xiaonuo.biz.modular.develop.service.impl;

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
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.develop.entity.DevFormItem;
import vip.xiaonuo.biz.modular.develop.mapper.DevFormItemMapper;
import vip.xiaonuo.biz.modular.develop.param.DevFormItemAddParam;
import vip.xiaonuo.biz.modular.develop.param.DevFormItemEditParam;
import vip.xiaonuo.biz.modular.develop.param.DevFormItemIdParam;
import vip.xiaonuo.biz.modular.develop.param.DevFormItemPageParam;
import vip.xiaonuo.biz.modular.develop.service.DevFormItemService;

import java.util.List;

/**
 * 需求单 - 需求拆解Service接口实现类
 *
 * @author yong.ye
 * @date  2024/05/27 16:08
 **/
@Service
@DS("slave_1")
public class DevFormItemServiceImpl extends ServiceImpl<DevFormItemMapper, DevFormItem> implements DevFormItemService {

    @Override
    public Page<DevFormItem> page(DevFormItemPageParam devFormItemPageParam) {
        QueryWrapper<DevFormItem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(devFormItemPageParam.getPoint())) {
            queryWrapper.lambda().like(DevFormItem::getPoint, devFormItemPageParam.getPoint());
        }
        if(ObjectUtil.isAllNotEmpty(devFormItemPageParam.getSortField(), devFormItemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(devFormItemPageParam.getSortOrder());
            queryWrapper.orderBy(true, devFormItemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(devFormItemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(DevFormItem::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(DevFormItemAddParam devFormItemAddParam) {
        DevFormItem devFormItem = BeanUtil.toBean(devFormItemAddParam, DevFormItem.class);
        this.save(devFormItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(DevFormItemEditParam devFormItemEditParam) {
        DevFormItem devFormItem = this.queryEntity(devFormItemEditParam.getId());
        BeanUtil.copyProperties(devFormItemEditParam, devFormItem);
        this.updateById(devFormItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<DevFormItemIdParam> devFormItemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(devFormItemIdParamList, DevFormItemIdParam::getId));
    }

    @Override
    public DevFormItem detail(DevFormItemIdParam devFormItemIdParam) {
        return this.queryEntity(devFormItemIdParam.getId());
    }

    @Override
    public DevFormItem queryEntity(String id) {
        DevFormItem devFormItem = this.getById(id);
        if(ObjectUtil.isEmpty(devFormItem)) {
            throw new CommonException("需求单 - 需求拆解不存在，id值为：{}", id);
        }
        return devFormItem;
    }
}
