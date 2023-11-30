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
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.cad.vo.SubItemUserVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cad.entity.CadSubItem;
import vip.xiaonuo.biz.modular.cad.mapper.CadSubItemMapper;
import vip.xiaonuo.biz.modular.cad.param.CadSubItemAddParam;
import vip.xiaonuo.biz.modular.cad.param.CadSubItemEditParam;
import vip.xiaonuo.biz.modular.cad.param.CadSubItemIdParam;
import vip.xiaonuo.biz.modular.cad.param.CadSubItemPageParam;
import vip.xiaonuo.biz.modular.cad.service.CadSubItemService;

import javax.annotation.Resource;
import java.util.List;

/**
 * CAD-项目子项表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/29 10:39
 **/
@DS("slave_1")
@Service
public class CadSubItemServiceImpl extends ServiceImpl<CadSubItemMapper, CadSubItem> implements CadSubItemService {

    @Resource
    private CadSubItemMapper cadSubItemMapper;

    @Override
    public Page<CadSubItem> page(CadSubItemPageParam cadSubItemPageParam) {
        QueryWrapper<CadSubItem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(cadSubItemPageParam.getName())) {
            queryWrapper.lambda().like(CadSubItem::getName, cadSubItemPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(cadSubItemPageParam.getStatus())) {
            queryWrapper.lambda().eq(CadSubItem::getStatus, cadSubItemPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(cadSubItemPageParam.getSortField(), cadSubItemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cadSubItemPageParam.getSortOrder());
            queryWrapper.orderBy(true, cadSubItemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cadSubItemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CadSubItem::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CadSubItemAddParam cadSubItemAddParam) {
        CadSubItem cadSubItem = BeanUtil.toBean(cadSubItemAddParam, CadSubItem.class);
        this.save(cadSubItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CadSubItemEditParam cadSubItemEditParam) {
        CadSubItem cadSubItem = this.queryEntity(cadSubItemEditParam.getId());
        BeanUtil.copyProperties(cadSubItemEditParam, cadSubItem);
        this.updateById(cadSubItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CadSubItemIdParam> cadSubItemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cadSubItemIdParamList, CadSubItemIdParam::getId));
    }

    @Override
    public CadSubItem detail(CadSubItemIdParam cadSubItemIdParam) {
        return this.queryEntity(cadSubItemIdParam.getId());
    }

    @Override
    public CadSubItem queryEntity(String id) {
        CadSubItem cadSubItem = this.getById(id);
        if(ObjectUtil.isEmpty(cadSubItem)) {
            throw new CommonException("CAD-项目子项表不存在，id值为：{}", id);
        }
        return cadSubItem;
    }

    @Override
    public List<SubItemUserVo> subItemUser(String subItemId) {
        return cadSubItemMapper.getSubItemUser(subItemId);
    }
}
