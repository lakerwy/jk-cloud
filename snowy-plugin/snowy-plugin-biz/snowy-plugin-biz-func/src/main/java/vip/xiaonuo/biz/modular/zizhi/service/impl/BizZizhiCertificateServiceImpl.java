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
package vip.xiaonuo.biz.modular.zizhi.service.impl;

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
import vip.xiaonuo.biz.modular.zizhi.vo.BizZizhiCertificateVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.zizhi.entity.BizZizhiCertificate;
import vip.xiaonuo.biz.modular.zizhi.mapper.BizZizhiCertificateMapper;
import vip.xiaonuo.biz.modular.zizhi.param.BizZizhiCertificateAddParam;
import vip.xiaonuo.biz.modular.zizhi.param.BizZizhiCertificateEditParam;
import vip.xiaonuo.biz.modular.zizhi.param.BizZizhiCertificateIdParam;
import vip.xiaonuo.biz.modular.zizhi.param.BizZizhiCertificatePageParam;
import vip.xiaonuo.biz.modular.zizhi.service.BizZizhiCertificateService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务表-资质-企业资质证书表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/01 09:56
 **/
@DS("slave_1")
@Service
public class BizZizhiCertificateServiceImpl extends ServiceImpl<BizZizhiCertificateMapper, BizZizhiCertificate> implements BizZizhiCertificateService {

    @Resource
    private BizZizhiCertificateMapper bizZizhiCertificateMapper;

    @Override
    public Page<BizZizhiCertificateVo> page(BizZizhiCertificatePageParam bizZizhiCertificatePageParam) {
        QueryWrapper<BizZizhiCertificate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(bizZizhiCertificatePageParam.getSn())) {
            queryWrapper.eq("a.SN", bizZizhiCertificatePageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizZizhiCertificatePageParam.getCompany())) {
            queryWrapper.like("a.COMPANY", bizZizhiCertificatePageParam.getCompany());
        }
        if(ObjectUtil.isNotEmpty(bizZizhiCertificatePageParam.getType())) {
            queryWrapper.eq("a.TYPE", bizZizhiCertificatePageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(bizZizhiCertificatePageParam.getZiZhiName())) {
            queryWrapper.like("a.ZI_ZHI_NAME", bizZizhiCertificatePageParam.getZiZhiName());
        }
        if(ObjectUtil.isNotEmpty(bizZizhiCertificatePageParam.getIssueOrg())) {
            queryWrapper.like("a.ISSUE_ORG", bizZizhiCertificatePageParam.getIssueOrg());
        }
        if(ObjectUtil.isNotEmpty(bizZizhiCertificatePageParam.getCertNo())){
            queryWrapper.like("a.CERT_NO",bizZizhiCertificatePageParam.getCertNo());
        }
        if(ObjectUtil.isNotEmpty(bizZizhiCertificatePageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.COMPANY",bizZizhiCertificatePageParam.getSearchKey())
                    .or().like("a.ISSUE_ORG",bizZizhiCertificatePageParam.getSearchKey())
                    .or().like("a.TYPE",bizZizhiCertificatePageParam.getSearchKey())
                    .or().like("a.ZI_ZHI_NAME",bizZizhiCertificatePageParam.getSearchKey())
                    .or().like("a.CERT_NO",bizZizhiCertificatePageParam.getSearchKey()));
        }
        if(ObjectUtil.isNotEmpty(bizZizhiCertificatePageParam.getCertStage())){
            queryWrapper.eq("a.certStage",bizZizhiCertificatePageParam.getCertStage());
        }
        if(ObjectUtil.isAllNotEmpty(bizZizhiCertificatePageParam.getSortField(), bizZizhiCertificatePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizZizhiCertificatePageParam.getSortOrder());
            queryWrapper.orderBy(true, bizZizhiCertificatePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizZizhiCertificatePageParam.getSortField()));
        } else {
            queryWrapper.orderByAsc("a.COMPANY").orderByAsc("a.TYPE").orderByAsc("a.ZI_ZHI_NAME");
        }
        return bizZizhiCertificateMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizZizhiCertificateAddParam bizZizhiCertificateAddParam) {
        BizZizhiCertificate bizZizhiCertificate = BeanUtil.toBean(bizZizhiCertificateAddParam, BizZizhiCertificate.class);
        Integer sn = this.list().stream().filter(x->x.getSn()!=null).map(x->x.getSn()).max(Integer::compareTo).orElse(0)+1;
        bizZizhiCertificate.setSn(sn);
        this.save(bizZizhiCertificate);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizZizhiCertificateEditParam bizZizhiCertificateEditParam) {
        BizZizhiCertificate bizZizhiCertificate = this.queryEntity(bizZizhiCertificateEditParam.getId());
        BeanUtil.copyProperties(bizZizhiCertificateEditParam, bizZizhiCertificate);
        this.updateById(bizZizhiCertificate);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizZizhiCertificateIdParam> bizZizhiCertificateIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizZizhiCertificateIdParamList, BizZizhiCertificateIdParam::getId));
    }

    @Override
    public BizZizhiCertificate detail(BizZizhiCertificateIdParam bizZizhiCertificateIdParam) {
        return this.queryEntity(bizZizhiCertificateIdParam.getId());
    }

    @Override
    public BizZizhiCertificate queryEntity(String id) {
        BizZizhiCertificate bizZizhiCertificate = this.getById(id);
        if(ObjectUtil.isEmpty(bizZizhiCertificate)) {
            throw new CommonException("业务表-资质-企业资质证书表不存在，id值为：{}", id);
        }
        return bizZizhiCertificate;
    }
}
