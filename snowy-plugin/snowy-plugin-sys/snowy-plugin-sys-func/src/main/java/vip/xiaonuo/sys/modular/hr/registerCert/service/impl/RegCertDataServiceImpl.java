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
import vip.xiaonuo.sys.modular.hr.registerCert.entity.RegCertData;
import vip.xiaonuo.sys.modular.hr.registerCert.mapper.RegCertDataMapper;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegCertDataAddParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegCertDataEditParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegCertDataIdParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.RegCertDataPageParam;
import vip.xiaonuo.sys.modular.hr.registerCert.service.RegCertDataService;

import java.util.List;

/**
 * 注册补贴-已有证书Service接口实现类
 *
 * @author ljy
 * @date  2024/03/27 15:24
 **/
@Service
public class RegCertDataServiceImpl extends ServiceImpl<RegCertDataMapper, RegCertData> implements RegCertDataService {

    @Override
    public Page<RegCertData> page(RegCertDataPageParam regCertDataPageParam) {
        QueryWrapper<RegCertData> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(regCertDataPageParam.getIdCardNumber())) {
            queryWrapper.lambda().eq(RegCertData::getIdCardNumber, regCertDataPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isAllNotEmpty(regCertDataPageParam.getSortField(), regCertDataPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(regCertDataPageParam.getSortOrder());
            queryWrapper.orderBy(true, regCertDataPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(regCertDataPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(RegCertData::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(RegCertDataAddParam regCertDataAddParam) {
        RegCertData regCertData = BeanUtil.toBean(regCertDataAddParam, RegCertData.class);
        this.save(regCertData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(RegCertDataEditParam regCertDataEditParam) {
        RegCertData regCertData = this.queryEntity(regCertDataEditParam.getId());
        BeanUtil.copyProperties(regCertDataEditParam, regCertData);
        this.updateById(regCertData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<RegCertDataIdParam> regCertDataIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(regCertDataIdParamList, RegCertDataIdParam::getId));
    }

    @Override
    public RegCertData detail(RegCertDataIdParam regCertDataIdParam) {
        return this.queryEntity(regCertDataIdParam.getId());
    }

    @Override
    public RegCertData queryEntity(String id) {
        RegCertData regCertData = this.getById(id);
        if(ObjectUtil.isEmpty(regCertData)) {
            throw new CommonException("注册补贴-已有证书不存在，id值为：{}", id);
        }
        return regCertData;
    }
}
