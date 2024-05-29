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
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.bp.modular.platform.entity.BpPoint;
import vip.xiaonuo.bp.modular.platform.mapper.BpPointMapper;
import vip.xiaonuo.bp.modular.platform.param.point.BpPointAddParam;
import vip.xiaonuo.bp.modular.platform.param.point.BpPointEditParam;
import vip.xiaonuo.bp.modular.platform.param.point.BpPointIdParam;
import vip.xiaonuo.bp.modular.platform.param.point.BpPointPageParam;
import vip.xiaonuo.bp.modular.platform.service.BpPointService;

import java.util.List;

/**
 * 招采管理 - 操作指引Service接口实现类
 *
 * @author yong.ye
 * @date  2024/05/29 16:22
 **/
@Service
@DS("slave_1")
public class BpPointServiceImpl extends ServiceImpl<BpPointMapper, BpPoint> implements BpPointService {

    @Override
    public Page<BpPoint> page(BpPointPageParam bpPointPageParam) {
        QueryWrapper<BpPoint> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bpPointPageParam.getTitle())) {
            queryWrapper.lambda().like(BpPoint::getTitle, bpPointPageParam.getTitle());
        }
        if(ObjectUtil.isAllNotEmpty(bpPointPageParam.getSortField(), bpPointPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bpPointPageParam.getSortOrder());
            queryWrapper.orderBy(true, bpPointPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bpPointPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BpPoint::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BpPointAddParam bpPointAddParam) {
        BpPoint bpPoint = BeanUtil.toBean(bpPointAddParam, BpPoint.class);
        this.save(bpPoint);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BpPointEditParam bpPointEditParam) {
        BpPoint bpPoint = this.queryEntity(bpPointEditParam.getId());
        BeanUtil.copyProperties(bpPointEditParam, bpPoint);
        this.updateById(bpPoint);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BpPointIdParam> bpPointIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bpPointIdParamList, BpPointIdParam::getId));
    }

    @Override
    public BpPoint detail(BpPointIdParam bpPointIdParam) {
        return this.queryEntity(bpPointIdParam.getId());
    }

    @Override
    public BpPoint queryEntity(String id) {
        BpPoint bpPoint = this.getById(id);
        if(ObjectUtil.isEmpty(bpPoint)) {
            throw new CommonException("招采管理 - 操作指引不存在，id值为：{}", id);
        }
        return bpPoint;
    }
}
