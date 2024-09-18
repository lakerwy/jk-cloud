package vip.xiaonuo.sys.modular.hr.enterpriseMaterial.service.impl;

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
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.entity.EnterpriseMaterial;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.mapper.EnterpriseMaterialMapper;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.param.EnterpriseMaterialAddParam;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.param.EnterpriseMaterialEditParam;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.param.EnterpriseMaterialIdParam;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.param.EnterpriseMaterialPageParam;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.service.EnterpriseMaterialService;
import vip.xiaonuo.sys.modular.hr.salary.enums.YesOrNoEnum;

import java.util.Date;
import java.util.List;

/**
 * 企业宣传资料Service接口实现类
 *
 * @author pzh
 * @date  2024/09/18 14:57
 **/
@Service
public class EnterpriseMaterialServiceImpl extends ServiceImpl<EnterpriseMaterialMapper, EnterpriseMaterial> implements EnterpriseMaterialService {

    @Override
    public Page<EnterpriseMaterial> page(EnterpriseMaterialPageParam enterpriseMaterialPageParam) {
        QueryWrapper<EnterpriseMaterial> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(enterpriseMaterialPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(EnterpriseMaterial::getName,enterpriseMaterialPageParam.getSearchKey())
                    .or().like(EnterpriseMaterial::getMemo,enterpriseMaterialPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(enterpriseMaterialPageParam.getSortField(), enterpriseMaterialPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(enterpriseMaterialPageParam.getSortOrder());
            queryWrapper.orderBy(true, enterpriseMaterialPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(enterpriseMaterialPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(EnterpriseMaterial::getUploadTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EnterpriseMaterialAddParam enterpriseMaterialAddParam) {
        EnterpriseMaterial enterpriseMaterial = BeanUtil.toBean(enterpriseMaterialAddParam, EnterpriseMaterial.class);
        Date now = new Date();
        enterpriseMaterial.setUploadTime(now);
        enterpriseMaterial.setCreateTime(now);
        this.save(enterpriseMaterial);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EnterpriseMaterialEditParam enterpriseMaterialEditParam) {
        EnterpriseMaterial enterpriseMaterial = this.queryEntity(enterpriseMaterialEditParam.getId());
        BeanUtil.copyProperties(enterpriseMaterialEditParam, enterpriseMaterial);
        if(YesOrNoEnum.YES.getValue().equals(enterpriseMaterialEditParam.getIsReUpload())){
            Date now = new Date();
            enterpriseMaterial.setUploadTime(now);
            enterpriseMaterial.setUpdateTime(now);
        }
        this.updateById(enterpriseMaterial);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EnterpriseMaterialIdParam> enterpriseMaterialIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(enterpriseMaterialIdParamList, EnterpriseMaterialIdParam::getId));
    }

    @Override
    public EnterpriseMaterial detail(EnterpriseMaterialIdParam enterpriseMaterialIdParam) {
        return this.queryEntity(enterpriseMaterialIdParam.getId());
    }

    @Override
    public EnterpriseMaterial queryEntity(String id) {
        EnterpriseMaterial enterpriseMaterial = this.getById(id);
        if(ObjectUtil.isEmpty(enterpriseMaterial)) {
            throw new CommonException("企业宣传资料不存在，id值为：{}", id);
        }
        return enterpriseMaterial;
    }
}
