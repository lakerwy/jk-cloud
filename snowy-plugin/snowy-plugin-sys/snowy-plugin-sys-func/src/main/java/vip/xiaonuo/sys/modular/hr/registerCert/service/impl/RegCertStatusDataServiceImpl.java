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
package vip.xiaonuo.sys.modular.hr.registerCert.service.impl;

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
import vip.xiaonuo.sys.modular.hr.registerCert.entity.RegCertStatusData;
import vip.xiaonuo.sys.modular.hr.registerCert.mapper.RegCertStatusDataMapper;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegCertStatusDataAddParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegCertStatusDataEditParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegCertStatusDataIdParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegCertStatusDataPageParam;
import vip.xiaonuo.sys.modular.hr.registerCert.service.RegCertStatusDataService;

import java.util.List;

/**
 * 注册补贴-人力资源-注册执业资格证书Service接口实现类
 *
 * @author ljy
 * @date  2024/03/27 15:30
 **/
@Service
public class RegCertStatusDataServiceImpl extends ServiceImpl<RegCertStatusDataMapper, RegCertStatusData> implements RegCertStatusDataService {

    @Override
    public Page<RegCertStatusData> page(RegCertStatusDataPageParam regCertStatusDataPageParam) {
        QueryWrapper<RegCertStatusData> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(regCertStatusDataPageParam.getApplyRegCertdId())) {
            queryWrapper.lambda().eq(RegCertStatusData::getApplyRegCertdId, regCertStatusDataPageParam.getApplyRegCertdId());
        }
        if(ObjectUtil.isNotEmpty(regCertStatusDataPageParam.getIsPass())) {
            queryWrapper.lambda().eq(RegCertStatusData::getIsPass, regCertStatusDataPageParam.getIsPass());
        }
        if(ObjectUtil.isNotEmpty(regCertStatusDataPageParam.getIdCardNumber())) {
            queryWrapper.lambda().eq(RegCertStatusData::getIdCardNumber, regCertStatusDataPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isAllNotEmpty(regCertStatusDataPageParam.getSortField(), regCertStatusDataPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(regCertStatusDataPageParam.getSortOrder());
            queryWrapper.orderBy(true, regCertStatusDataPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(regCertStatusDataPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(RegCertStatusData::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(RegCertStatusDataAddParam regCertStatusDataAddParam) {
        RegCertStatusData regCertStatusData = BeanUtil.toBean(regCertStatusDataAddParam, RegCertStatusData.class);
        this.save(regCertStatusData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(RegCertStatusDataEditParam regCertStatusDataEditParam) {
        RegCertStatusData regCertStatusData = this.queryEntity(regCertStatusDataEditParam.getId());
        BeanUtil.copyProperties(regCertStatusDataEditParam, regCertStatusData);
        this.updateById(regCertStatusData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<RegCertStatusDataIdParam> regCertStatusDataIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(regCertStatusDataIdParamList, RegCertStatusDataIdParam::getId));
    }

    @Override
    public RegCertStatusData detail(RegCertStatusDataIdParam regCertStatusDataIdParam) {
        return this.queryEntity(regCertStatusDataIdParam.getId());
    }

    @Override
    public RegCertStatusData queryEntity(String id) {
        RegCertStatusData regCertStatusData = this.getById(id);
        if(ObjectUtil.isEmpty(regCertStatusData)) {
            throw new CommonException("注册补贴-人力资源中心证书不存在，id值为：{}", id);
        }
        return regCertStatusData;
    }
}
