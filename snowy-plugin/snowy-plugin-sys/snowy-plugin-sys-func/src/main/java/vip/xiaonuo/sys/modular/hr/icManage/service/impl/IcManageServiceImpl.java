package vip.xiaonuo.sys.modular.hr.icManage.service.impl;

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
import vip.xiaonuo.sys.modular.hr.icManage.entity.IcManage;
import vip.xiaonuo.sys.modular.hr.icManage.mapper.IcManageMapper;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageAddParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageEditParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageIdParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManagePageParam;
import vip.xiaonuo.sys.modular.hr.icManage.service.IcManageService;

import java.util.List;

/**
 * 工商管理Service接口实现类
 *
 * @author pzh
 * @date  2025/02/25 16:33
 **/
@Service
public class IcManageServiceImpl extends ServiceImpl<IcManageMapper, IcManage> implements IcManageService {

    @Override
    public Page<IcManage> page(IcManagePageParam icManagePageParam) {
        QueryWrapper<IcManage> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(icManagePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(IcManage::getCompanyName,icManagePageParam.getSearchKey())
                    .or().like(IcManage::getBusinessScope,icManagePageParam.getSearchKey())
                    .or().like(IcManage::getLegalRepresentative,icManagePageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isNotEmpty(icManagePageParam.getType())){
            queryWrapper.lambda().eq(IcManage::getType,icManagePageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(icManagePageParam.getStartEstablishTime()) && ObjectUtil.isNotEmpty(icManagePageParam.getEndEstablishTime())) {
            queryWrapper.lambda().between(IcManage::getEstablishTime, icManagePageParam.getStartEstablishTime(), icManagePageParam.getEndEstablishTime());
        }
        if(ObjectUtil.isAllNotEmpty(icManagePageParam.getSortField(), icManagePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(icManagePageParam.getSortOrder());
            queryWrapper.orderBy(true, icManagePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(icManagePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(IcManage::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(IcManageAddParam icManageAddParam) {
        IcManage icManage = BeanUtil.toBean(icManageAddParam, IcManage.class);
        this.save(icManage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(IcManageEditParam icManageEditParam) {
        IcManage icManage = this.queryEntity(icManageEditParam.getId());
        BeanUtil.copyProperties(icManageEditParam, icManage);
        this.updateById(icManage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<IcManageIdParam> icManageIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(icManageIdParamList, IcManageIdParam::getId));
    }

    @Override
    public IcManage detail(IcManageIdParam icManageIdParam) {
        return this.queryEntity(icManageIdParam.getId());
    }

    @Override
    public IcManage queryEntity(String id) {
        IcManage icManage = this.getById(id);
        if(ObjectUtil.isEmpty(icManage)) {
            throw new CommonException("工商管理不存在，id值为：{}", id);
        }
        return icManage;
    }
}
