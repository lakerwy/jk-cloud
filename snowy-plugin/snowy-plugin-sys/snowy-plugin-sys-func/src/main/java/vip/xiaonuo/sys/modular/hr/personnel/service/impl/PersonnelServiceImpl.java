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
package vip.xiaonuo.sys.modular.hr.personnel.service.impl;

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
import vip.xiaonuo.sys.modular.hr.fileMoves.service.PersonnelFileMovesService;
import vip.xiaonuo.sys.modular.hr.fileRegister.service.PersonnelFileRegisterService;
import vip.xiaonuo.sys.modular.hr.fileTitle.service.PersonnelFileTitleService;
import vip.xiaonuo.sys.modular.hr.personnel.vo.PersonnelListVo;
import vip.xiaonuo.sys.modular.hr.personnel.mapper.PersonnelMapper;
import vip.xiaonuo.sys.modular.hr.personnel.param.PersonnelAddParam;
import vip.xiaonuo.sys.modular.hr.personnel.param.PersonnelEditParam;
import vip.xiaonuo.sys.modular.hr.personnel.param.PersonnelIdParam;
import vip.xiaonuo.sys.modular.hr.personnel.service.PersonnelService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.personnel.entity.Personnel;
import vip.xiaonuo.sys.modular.hr.personnel.param.PersonnelPageParam;
import vip.xiaonuo.sys.modular.hr.personnel.vo.PersonnelVo;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务表-人事档案Service接口实现类
 *
 * @author mayike
 * @date  2023/07/10 17:28
 **/
@Service
public class PersonnelServiceImpl extends ServiceImpl<PersonnelMapper, Personnel> implements PersonnelService {

    @Resource
    private PersonnelMapper personnelMapper;

    @Resource
    private PersonnelFileCertService personnelFileCertService;

    @Resource
    private PersonnelFileMovesService personnelFileMovesService;

    @Resource
    private PersonnelFileTitleService personnelFileTitleService;

    @Resource
    private PersonnelFileRegisterService personnelFileRegisterService;

    @Override
    public Page<Personnel> page(PersonnelPageParam personnelPageParam) {
        QueryWrapper<Personnel> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(personnelPageParam.getName())) {
            queryWrapper.lambda().like(Personnel::getName, personnelPageParam.getName());
        }
        if(ObjectUtil.isAllNotEmpty(personnelPageParam.getSortField(), personnelPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(personnelPageParam.getSortOrder());
            queryWrapper.orderBy(true, personnelPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(personnelPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Personnel::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PersonnelAddParam personnelAddParam) {
        Personnel personnel = BeanUtil.toBean(personnelAddParam, Personnel.class);
        //新增基本信息
        //签约信息
        //异动信息
        //职称信息
        //证书信息
        this.save(personnel);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PersonnelEditParam personnelEditParam) {
        Personnel personnel = this.queryEntity(personnelEditParam.getId());
        BeanUtil.copyProperties(personnelEditParam, personnel);
        this.updateById(personnel);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PersonnelIdParam> personnelIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(personnelIdParamList, PersonnelIdParam::getId));
    }

    @Override
    public PersonnelVo detail(PersonnelIdParam personnelIdParam) {
        Personnel personnel = this.queryEntity(personnelIdParam.getId());
        PersonnelVo personnelVo = BeanUtil.toBean(personnel, PersonnelVo.class);
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("FILE_ID",personnel.getId());
        columnMap.put("DELETE_FLAG","NOT_DELETE");
        personnelVo.setPersonnelFileCertList(personnelFileCertService.listByMap(columnMap));
        personnelVo.setPersonnelFileMovesList(personnelFileMovesService.listByMap(columnMap));
        personnelVo.setPersonnelFileTitleList(personnelFileTitleService.listByMap(columnMap));
        personnelVo.setPersonnelFileRegisterList(personnelFileRegisterService.listByMap(columnMap));
        return personnelVo;
    }

    @Override
    public Personnel queryEntity(String id) {
        Personnel personnel = this.getById(id);
        if(ObjectUtil.isEmpty(personnel)) {
            throw new CommonException("业务表-人事档案不存在，id值为：{}", id);
        }
        return personnel;
    }

    @Override
    public Page<PersonnelListVo> pageList(PersonnelPageParam personnelPageParam) {
        QueryWrapper<PersonnelListVo> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(personnelPageParam.getName())) {
            queryWrapper.like("a.`NAME", personnelPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(personnelPageParam.getStartHireDate()) && ObjectUtil.isNotEmpty(personnelPageParam.getEndHireDate())) {
            queryWrapper.between("a.HIRE_DATE",personnelPageParam.getStartHireDate(),personnelPageParam.getEndHireDate());
        }
        if(ObjectUtil.isNotEmpty(personnelPageParam.getDept())) {
            queryWrapper.like("b.`NAME`", personnelPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(personnelPageParam.getDuty())) {
            queryWrapper.like("c.`NAME``", personnelPageParam.getDuty());
        }
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("a.`STATUS`","1");
        if(ObjectUtil.isAllNotEmpty(personnelPageParam.getSortField(), personnelPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(personnelPageParam.getSortOrder());
            queryWrapper.orderBy(true, personnelPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(personnelPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.FILE_NO");
        }
        return personnelMapper.getPageList(CommonPageRequest.defaultPage(),queryWrapper);
    }
}
