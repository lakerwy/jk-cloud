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
package vip.xiaonuo.sys.modular.hr.hrCenter.service.impl;

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
import vip.xiaonuo.sys.modular.hr.hrCenter.entity.PersonnelFileRegister;
import vip.xiaonuo.sys.modular.hr.hrCenter.mapper.PersonnelFileRegisterMapper;
import vip.xiaonuo.sys.modular.hr.hrCenter.param.PersonnelFileRegisterAddParam;
import vip.xiaonuo.sys.modular.hr.hrCenter.param.PersonnelFileRegisterEditParam;
import vip.xiaonuo.sys.modular.hr.hrCenter.param.PersonnelFileRegisterIdParam;
import vip.xiaonuo.sys.modular.hr.hrCenter.param.PersonnelFileRegisterPageParam;
import vip.xiaonuo.sys.modular.hr.hrCenter.service.PersonnelFileRegisterService;

import java.util.List;

/**
 * 业务表- 签约信息Service接口实现类
 *
 * @author mayike
 * @date  2023/07/12 10:55
 **/
@Service
public class PersonnelFileRegisterServiceImpl extends ServiceImpl<PersonnelFileRegisterMapper, PersonnelFileRegister> implements PersonnelFileRegisterService {

    @Override
    public Page<PersonnelFileRegister> page(PersonnelFileRegisterPageParam personnelFileRegisterPageParam) {
        QueryWrapper<PersonnelFileRegister> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(personnelFileRegisterPageParam.getSortField(), personnelFileRegisterPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(personnelFileRegisterPageParam.getSortOrder());
            queryWrapper.orderBy(true, personnelFileRegisterPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(personnelFileRegisterPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PersonnelFileRegister::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PersonnelFileRegisterAddParam personnelFileRegisterAddParam) {
        PersonnelFileRegister personnelFileRegister = BeanUtil.toBean(personnelFileRegisterAddParam, PersonnelFileRegister.class);
        this.save(personnelFileRegister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PersonnelFileRegisterEditParam personnelFileRegisterEditParam) {
        PersonnelFileRegister personnelFileRegister = this.queryEntity(personnelFileRegisterEditParam.getId());
        BeanUtil.copyProperties(personnelFileRegisterEditParam, personnelFileRegister);
        this.updateById(personnelFileRegister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PersonnelFileRegisterIdParam> personnelFileRegisterIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(personnelFileRegisterIdParamList, PersonnelFileRegisterIdParam::getId));
    }

    @Override
    public PersonnelFileRegister detail(PersonnelFileRegisterIdParam personnelFileRegisterIdParam) {
        return this.queryEntity(personnelFileRegisterIdParam.getId());
    }

    @Override
    public PersonnelFileRegister queryEntity(String id) {
        PersonnelFileRegister personnelFileRegister = this.getById(id);
        if(ObjectUtil.isEmpty(personnelFileRegister)) {
            throw new CommonException("业务表- 签约信息不存在，id值为：{}", id);
        }
        return personnelFileRegister;
    }

    @Override
    public void deleteByFileId(String fileId){
//        LambdaQueryWrapper<PersonnelFileRegister> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(PersonnelFileRegister::getFileId,fileId);
        baseMapper.deleteByFileId(fileId);
    }

    @Override
    public void deleteNewFileId(List<String> ids) {
        QueryWrapper<PersonnelFileRegister> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(PersonnelFileRegister::getFileId,ids);
        baseMapper.delete(queryWrapper);
    }
}
