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
package vip.xiaonuo.sys.modular.hr.fileCert.service.impl;

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
import vip.xiaonuo.sys.modular.hr.fileCert.service.PersonnelFileCertService;
import vip.xiaonuo.sys.modular.hr.fileCert.param.PersonnelFileCertAddParam;
import vip.xiaonuo.sys.modular.hr.fileCert.param.PersonnelFileCertIdParam;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.fileCert.entity.PersonnelFileCert;
import vip.xiaonuo.sys.modular.hr.fileCert.mapper.PersonnelFileCertMapper;
import vip.xiaonuo.sys.modular.hr.fileCert.param.PersonnelFileCertEditParam;
import vip.xiaonuo.sys.modular.hr.fileCert.param.PersonnelFileCertPageParam;

import java.util.List;

/**
 * 业务表-证书信息Service接口实现类
 *
 * @author mayike
 * @date  2023/07/10 16:53
 **/
@Service
public class PersonnelFileCertServiceImpl extends ServiceImpl<PersonnelFileCertMapper, PersonnelFileCert> implements PersonnelFileCertService {

    @Override
    public Page<PersonnelFileCert> page(PersonnelFileCertPageParam personnelFileCertPageParam) {
        QueryWrapper<PersonnelFileCert> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(personnelFileCertPageParam.getSortField(), personnelFileCertPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(personnelFileCertPageParam.getSortOrder());
            queryWrapper.orderBy(true, personnelFileCertPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(personnelFileCertPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PersonnelFileCert::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PersonnelFileCertAddParam personnelFileCertAddParam) {
        PersonnelFileCert personnelFileCert = BeanUtil.toBean(personnelFileCertAddParam, PersonnelFileCert.class);
        this.save(personnelFileCert);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PersonnelFileCertEditParam personnelFileCertEditParam) {
        PersonnelFileCert personnelFileCert = this.queryEntity(personnelFileCertEditParam.getId());
        BeanUtil.copyProperties(personnelFileCertEditParam, personnelFileCert);
        this.updateById(personnelFileCert);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PersonnelFileCertIdParam> personnelFileCertIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(personnelFileCertIdParamList, PersonnelFileCertIdParam::getId));
    }

    @Override
    public PersonnelFileCert detail(PersonnelFileCertIdParam personnelFileCertIdParam) {
        return this.queryEntity(personnelFileCertIdParam.getId());
    }

    @Override
    public PersonnelFileCert queryEntity(String id) {
        PersonnelFileCert personnelFileCert = this.getById(id);
        if(ObjectUtil.isEmpty(personnelFileCert)) {
            throw new CommonException("业务表-证书信息不存在，id值为：{}", id);
        }
        return personnelFileCert;
    }
}
