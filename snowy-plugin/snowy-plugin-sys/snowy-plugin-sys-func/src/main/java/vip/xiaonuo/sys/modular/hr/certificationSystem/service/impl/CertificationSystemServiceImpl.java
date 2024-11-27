package vip.xiaonuo.sys.modular.hr.certificationSystem.service.impl;

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
import vip.xiaonuo.sys.modular.hr.certificationSystem.entity.CertificationSystem;
import vip.xiaonuo.sys.modular.hr.certificationSystem.mapper.CertificationSystemMapper;
import vip.xiaonuo.sys.modular.hr.certificationSystem.param.CertificationSystemAddParam;
import vip.xiaonuo.sys.modular.hr.certificationSystem.param.CertificationSystemEditParam;
import vip.xiaonuo.sys.modular.hr.certificationSystem.param.CertificationSystemIdParam;
import vip.xiaonuo.sys.modular.hr.certificationSystem.param.CertificationSystemPageParam;
import vip.xiaonuo.sys.modular.hr.certificationSystem.service.CertificationSystemService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 认证体系Service接口实现类
 *
 * @author pzh
 * @date  2024/10/18 10:01
 **/
@Service
public class CertificationSystemServiceImpl extends ServiceImpl<CertificationSystemMapper, CertificationSystem> implements CertificationSystemService {

    @Resource
    private CertificationSystemMapper  certificationSystemMapper;

    @Override
    public Page<CertificationSystem> page(CertificationSystemPageParam certificationSystemPageParam) {
        QueryWrapper<CertificationSystem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(certificationSystemPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.NAME",certificationSystemPageParam.getSearchKey())
                    .or().like("a.CERT_NO",certificationSystemPageParam.getSearchKey())
                    .or().like("a.STANDARD",certificationSystemPageParam.getSearchKey())
                    .or().like("a.SCOPE",certificationSystemPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isAllNotEmpty(certificationSystemPageParam.getSortField(), certificationSystemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(certificationSystemPageParam.getSortOrder());
            queryWrapper.orderBy(true, certificationSystemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(certificationSystemPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return certificationSystemMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CertificationSystemAddParam certificationSystemAddParam) {
        CertificationSystem certificationSystem = BeanUtil.toBean(certificationSystemAddParam, CertificationSystem.class);
        this.save(certificationSystem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CertificationSystemEditParam certificationSystemEditParam) {
        CertificationSystem certificationSystem = this.queryEntity(certificationSystemEditParam.getId());
        BeanUtil.copyProperties(certificationSystemEditParam, certificationSystem);
        this.updateById(certificationSystem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CertificationSystemIdParam> certificationSystemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(certificationSystemIdParamList, CertificationSystemIdParam::getId));
    }

    @Override
    public CertificationSystem detail(CertificationSystemIdParam certificationSystemIdParam) {
        return this.queryEntity(certificationSystemIdParam.getId());
    }

    @Override
    public CertificationSystem queryEntity(String id) {
        CertificationSystem certificationSystem = this.getById(id);
        if(ObjectUtil.isEmpty(certificationSystem)) {
            throw new CommonException("认证体系不存在，id值为：{}", id);
        }
        return certificationSystem;
    }
}
