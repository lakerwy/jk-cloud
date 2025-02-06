package vip.xiaonuo.sys.modular.hr.sub.service.impl;

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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFundItem;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubFundItemMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFundItemAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFundItemEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFundItemIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFundItemPageParam;
import vip.xiaonuo.sys.modular.hr.sub.service.SubFundItemService;

import java.util.List;

/**
 * 分公司-五险一金 - 社保|公积金明细Service接口实现类
 *
 * @author yong.ye
 * @date  2025/02/06 09:13
 **/
@Service
public class SubFundItemServiceImpl extends ServiceImpl<SubFundItemMapper, SubFundItem> implements SubFundItemService {

    @Override
    public Page<SubFundItem> page(SubFundItemPageParam subFundItemPageParam) {
        QueryWrapper<SubFundItem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(subFundItemPageParam.getType())) {
            queryWrapper.lambda().eq(SubFundItem::getType, subFundItemPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(subFundItemPageParam.getYM())) {
            queryWrapper.lambda().eq(SubFundItem::getYM, subFundItemPageParam.getYM());
        }
        if(ObjectUtil.isNotEmpty(subFundItemPageParam.getUserName())) {
            queryWrapper.lambda().like(SubFundItem::getUserName, subFundItemPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(subFundItemPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(SubFundItem::getIdCardNumber, subFundItemPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(subFundItemPageParam.getOrgName())) {
            queryWrapper.lambda().like(SubFundItem::getOrgName, subFundItemPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(subFundItemPageParam.getDuty())) {
            queryWrapper.lambda().like(SubFundItem::getDuty, subFundItemPageParam.getDuty());
        }
        if(ObjectUtil.isNotEmpty(subFundItemPageParam.getNature())) {
            queryWrapper.lambda().eq(SubFundItem::getNature, subFundItemPageParam.getNature());
        }
        if(ObjectUtil.isAllNotEmpty(subFundItemPageParam.getSortField(), subFundItemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subFundItemPageParam.getSortOrder());
            queryWrapper.orderBy(true, subFundItemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subFundItemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SubFundItem::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubFundItemAddParam subFundItemAddParam) {
        SubFundItem subFundItem = BeanUtil.toBean(subFundItemAddParam, SubFundItem.class);
        this.save(subFundItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubFundItemEditParam subFundItemEditParam) {
        SubFundItem subFundItem = this.queryEntity(subFundItemEditParam.getId());
        BeanUtil.copyProperties(subFundItemEditParam, subFundItem);
        this.updateById(subFundItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubFundItemIdParam> subFundItemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(subFundItemIdParamList, SubFundItemIdParam::getId));
    }

    @Override
    public SubFundItem detail(SubFundItemIdParam subFundItemIdParam) {
        return this.queryEntity(subFundItemIdParam.getId());
    }

    @Override
    public SubFundItem queryEntity(String id) {
        SubFundItem subFundItem = this.getById(id);
        if(ObjectUtil.isEmpty(subFundItem)) {
            throw new CommonException("分公司-五险一金 - 社保|公积金明细不存在，id值为：{}", id);
        }
        return subFundItem;
    }
}
