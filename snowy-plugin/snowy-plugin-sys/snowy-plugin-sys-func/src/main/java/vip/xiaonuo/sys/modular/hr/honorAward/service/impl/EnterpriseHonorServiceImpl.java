package vip.xiaonuo.sys.modular.hr.honorAward.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.sys.modular.hr.honorAward.entity.EnterpriseHonor;
import vip.xiaonuo.sys.modular.hr.honorAward.entity.HonorAward;
import vip.xiaonuo.sys.modular.hr.honorAward.mapper.EnterpriseHonorMapper;
import vip.xiaonuo.sys.modular.hr.honorAward.param.EnterpriseHonorAddParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.EnterpriseHonorEditParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.EnterpriseHonorIdParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.EnterpriseHonorPageParam;
import vip.xiaonuo.sys.modular.hr.honorAward.service.EnterpriseHonorService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 企业荣誉Service接口实现类
 *
 * @author pzh
 * @date  2024/10/16 11:17
 **/
@Service
public class EnterpriseHonorServiceImpl extends ServiceImpl<EnterpriseHonorMapper, EnterpriseHonor> implements EnterpriseHonorService {

    @Resource
    private EnterpriseHonorMapper enterpriseHonorMapper;

    @Override
    public Page<EnterpriseHonor> page(EnterpriseHonorPageParam enterpriseHonorPageParam) {
        QueryWrapper<EnterpriseHonor> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(enterpriseHonorPageParam.getType())){
            queryWrapper.eq("a.TYPE",enterpriseHonorPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(enterpriseHonorPageParam.getName())){
            queryWrapper.like("a.NAME", enterpriseHonorPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(enterpriseHonorPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.NAME",enterpriseHonorPageParam.getSearchKey())
                    .or().like("a.CERT_NO",enterpriseHonorPageParam.getSearchKey())
                    .or().like("a.AWARDING_ORG",enterpriseHonorPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isAllNotEmpty(enterpriseHonorPageParam.getSortField(), enterpriseHonorPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(enterpriseHonorPageParam.getSortOrder());
            queryWrapper.orderBy(true, enterpriseHonorPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(enterpriseHonorPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return enterpriseHonorMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EnterpriseHonorAddParam enterpriseHonorAddParam) {
        EnterpriseHonor enterpriseHonor = BeanUtil.toBean(enterpriseHonorAddParam, EnterpriseHonor.class);
        this.save(enterpriseHonor);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EnterpriseHonorEditParam enterpriseHonorEditParam) {
        EnterpriseHonor enterpriseHonor = this.queryEntity(enterpriseHonorEditParam.getId());
        BeanUtil.copyProperties(enterpriseHonorEditParam, enterpriseHonor);
        this.updateById(enterpriseHonor);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EnterpriseHonorIdParam> enterpriseHonorIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(enterpriseHonorIdParamList, EnterpriseHonorIdParam::getId));
    }

    @Override
    public EnterpriseHonor detail(EnterpriseHonorIdParam enterpriseHonorIdParam) {
        return this.queryEntity(enterpriseHonorIdParam.getId());
    }

    @Override
    public EnterpriseHonor queryEntity(String id) {
        EnterpriseHonor enterpriseHonor = enterpriseHonorMapper.getDetailById(id);
        if(ObjectUtil.isEmpty(enterpriseHonor)) {
            throw new CommonException("企业荣誉不存在，id值为：{}", id);
        }
        return enterpriseHonor;
    }
}
