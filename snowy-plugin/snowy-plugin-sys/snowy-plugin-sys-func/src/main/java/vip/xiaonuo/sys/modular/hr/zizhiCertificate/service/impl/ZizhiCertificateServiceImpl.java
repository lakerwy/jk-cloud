package vip.xiaonuo.sys.modular.hr.zizhiCertificate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.entity.ZizhiCertificate;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.mapper.ZizhiCertificateMapper;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.param.ZizhiCertificateAddParam;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.param.ZizhiCertificateEditParam;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.param.ZizhiCertificateIdParam;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.param.ZizhiCertificatePageParam;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.service.ZizhiCertificateService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资质证照Service接口实现类
 *
 * @author pzh
 * @date  2024/10/16 16:39
 **/
@Service
public class ZizhiCertificateServiceImpl extends ServiceImpl<ZizhiCertificateMapper, ZizhiCertificate> implements ZizhiCertificateService {

    @Resource
    private ZizhiCertificateMapper zizhiCertificateMapper;

    @Override
    public Page<ZizhiCertificate> page(ZizhiCertificatePageParam zizhiCertificatePageParam) {
        QueryWrapper<ZizhiCertificate> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(zizhiCertificatePageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.ZI_ZHI_NAME",zizhiCertificatePageParam.getSearchKey())
                    .or().like("a.TYPE",zizhiCertificatePageParam.getSearchKey())
                    .or().like("a.CERT_NO",zizhiCertificatePageParam.getSearchKey())
                    .or().like("a.ISSUE_ORG",zizhiCertificatePageParam.getSearchKey())
                    .or().like("a.BUSINESS_SCOPE",zizhiCertificatePageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isAllNotEmpty(zizhiCertificatePageParam.getSortField(), zizhiCertificatePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(zizhiCertificatePageParam.getSortOrder());
            queryWrapper.orderBy(true, zizhiCertificatePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(zizhiCertificatePageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return zizhiCertificateMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ZizhiCertificateAddParam zizhiCertificateAddParam) {
        ZizhiCertificate zizhiCertificate = BeanUtil.toBean(zizhiCertificateAddParam, ZizhiCertificate.class);
        this.save(zizhiCertificate);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ZizhiCertificateEditParam zizhiCertificateEditParam) {
        ZizhiCertificate zizhiCertificate = this.queryEntity(zizhiCertificateEditParam.getId());
        BeanUtil.copyProperties(zizhiCertificateEditParam, zizhiCertificate);
        this.updateById(zizhiCertificate);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ZizhiCertificateIdParam> zizhiCertificateIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(zizhiCertificateIdParamList, ZizhiCertificateIdParam::getId));
    }

    @Override
    public ZizhiCertificate detail(ZizhiCertificateIdParam zizhiCertificateIdParam) {
        return this.queryEntity(zizhiCertificateIdParam.getId());
    }

    @Override
    public ZizhiCertificate queryEntity(String id) {
        ZizhiCertificate zizhiCertificate = this.getById(id);
        if(ObjectUtil.isEmpty(zizhiCertificate)) {
            throw new CommonException("资质证照不存在，id值为：{}", id);
        }
        return zizhiCertificate;
    }

}
