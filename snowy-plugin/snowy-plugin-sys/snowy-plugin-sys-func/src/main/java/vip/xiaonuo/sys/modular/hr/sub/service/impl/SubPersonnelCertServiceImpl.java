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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubPersonnelCert;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubPersonnelCertMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelCertAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelCertEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelCertIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelCertPageParam;
import vip.xiaonuo.sys.modular.hr.sub.service.SubPersonnelCertService;

import java.util.List;

/**
 * HR-分公司-证书信息Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/21 11:16
 **/
@Service
public class SubPersonnelCertServiceImpl extends ServiceImpl<SubPersonnelCertMapper, SubPersonnelCert> implements SubPersonnelCertService {

    @Override
    public Page<SubPersonnelCert> page(SubPersonnelCertPageParam subPersonnelCertPageParam) {
        QueryWrapper<SubPersonnelCert> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(subPersonnelCertPageParam.getCertType())) {
            queryWrapper.lambda().eq(SubPersonnelCert::getCertType, subPersonnelCertPageParam.getCertType());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelCertPageParam.getName())) {
            queryWrapper.lambda().like(SubPersonnelCert::getName, subPersonnelCertPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelCertPageParam.getMajor())) {
            queryWrapper.lambda().like(SubPersonnelCert::getMajor, subPersonnelCertPageParam.getMajor());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelCertPageParam.getCertNo())) {
            queryWrapper.lambda().like(SubPersonnelCert::getCertNo, subPersonnelCertPageParam.getCertNo());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelCertPageParam.getStampNo())) {
            queryWrapper.lambda().like(SubPersonnelCert::getStampNo, subPersonnelCertPageParam.getStampNo());
        }
        if(ObjectUtil.isAllNotEmpty(subPersonnelCertPageParam.getSortField(), subPersonnelCertPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subPersonnelCertPageParam.getSortOrder());
            queryWrapper.orderBy(true, subPersonnelCertPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subPersonnelCertPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SubPersonnelCert::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubPersonnelCertAddParam subPersonnelCertAddParam) {
        SubPersonnelCert subPersonnelCert = BeanUtil.toBean(subPersonnelCertAddParam, SubPersonnelCert.class);
        this.save(subPersonnelCert);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubPersonnelCertEditParam subPersonnelCertEditParam) {
        SubPersonnelCert subPersonnelCert = this.queryEntity(subPersonnelCertEditParam.getId());
        BeanUtil.copyProperties(subPersonnelCertEditParam, subPersonnelCert);
        this.updateById(subPersonnelCert);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubPersonnelCertIdParam> subPersonnelCertIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(subPersonnelCertIdParamList, SubPersonnelCertIdParam::getId));
    }

    @Override
    public SubPersonnelCert detail(SubPersonnelCertIdParam subPersonnelCertIdParam) {
        return this.queryEntity(subPersonnelCertIdParam.getId());
    }

    @Override
    public SubPersonnelCert queryEntity(String id) {
        SubPersonnelCert subPersonnelCert = this.getById(id);
        if(ObjectUtil.isEmpty(subPersonnelCert)) {
            throw new CommonException("HR-分公司-证书信息不存在，id值为：{}", id);
        }
        return subPersonnelCert;
    }
}
