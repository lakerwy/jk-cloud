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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubGzItem;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubGzItemMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.SubGzItemAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubGzItemEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubGzItemIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubGzItemPageParam;
import vip.xiaonuo.sys.modular.hr.sub.service.SubGzItemService;

import java.util.List;

/**
 * 分公司-工资发放 - 工资|奖金|经济补偿金明细Service接口实现类
 *
 * @author yong.ye
 * @date  2025/02/06 09:11
 **/
@Service
public class SubGzItemServiceImpl extends ServiceImpl<SubGzItemMapper, SubGzItem> implements SubGzItemService {

    @Override
    public Page<SubGzItem> page(SubGzItemPageParam subGzItemPageParam) {
        QueryWrapper<SubGzItem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(subGzItemPageParam.getType())) {
            queryWrapper.lambda().eq(SubGzItem::getType, subGzItemPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(subGzItemPageParam.getYM())) {
            queryWrapper.lambda().eq(SubGzItem::getYM, subGzItemPageParam.getYM());
        }
        if(ObjectUtil.isNotEmpty(subGzItemPageParam.getUserName())) {
            queryWrapper.lambda().like(SubGzItem::getUserName, subGzItemPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(subGzItemPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(SubGzItem::getIdCardNumber, subGzItemPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(subGzItemPageParam.getOrgName())) {
            queryWrapper.lambda().like(SubGzItem::getOrgName, subGzItemPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(subGzItemPageParam.getDuty())) {
            queryWrapper.lambda().like(SubGzItem::getDuty, subGzItemPageParam.getDuty());
        }
        if(ObjectUtil.isNotEmpty(subGzItemPageParam.getNature())) {
            queryWrapper.lambda().eq(SubGzItem::getNature, subGzItemPageParam.getNature());
        }
        if(ObjectUtil.isAllNotEmpty(subGzItemPageParam.getSortField(), subGzItemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subGzItemPageParam.getSortOrder());
            queryWrapper.orderBy(true, subGzItemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subGzItemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SubGzItem::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubGzItemAddParam subGzItemAddParam) {
        SubGzItem subGzItem = BeanUtil.toBean(subGzItemAddParam, SubGzItem.class);
        this.save(subGzItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubGzItemEditParam subGzItemEditParam) {
        SubGzItem subGzItem = this.queryEntity(subGzItemEditParam.getId());
        BeanUtil.copyProperties(subGzItemEditParam, subGzItem);
        this.updateById(subGzItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubGzItemIdParam> subGzItemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(subGzItemIdParamList, SubGzItemIdParam::getId));
    }

    @Override
    public SubGzItem detail(SubGzItemIdParam subGzItemIdParam) {
        return this.queryEntity(subGzItemIdParam.getId());
    }

    @Override
    public SubGzItem queryEntity(String id) {
        SubGzItem subGzItem = this.getById(id);
        if(ObjectUtil.isEmpty(subGzItem)) {
            throw new CommonException("分公司-工资发放 - 工资|奖金|经济补偿金明细不存在，id值为：{}", id);
        }
        return subGzItem;
    }
}
