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
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.sys.modular.hr.fileCert.entity.PersonnelFileCert;
import vip.xiaonuo.sys.modular.hr.fileCert.param.PersonnelFileCertAddParam;
import vip.xiaonuo.sys.modular.hr.fileCert.param.PersonnelFileCertEditParam;
import vip.xiaonuo.sys.modular.hr.fileCert.service.PersonnelFileCertService;
import vip.xiaonuo.sys.modular.hr.fileMoves.entity.PersonnelFileMoves;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesAddParam;
import vip.xiaonuo.sys.modular.hr.fileMoves.service.PersonnelFileMovesService;
import vip.xiaonuo.sys.modular.hr.fileRegister.entity.PersonnelFileRegister;
import vip.xiaonuo.sys.modular.hr.fileRegister.service.PersonnelFileRegisterService;
import vip.xiaonuo.sys.modular.hr.fileTitle.entity.PersonnelFileTitle;
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
import vip.xiaonuo.sys.modular.info.entity.PartnerFee;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        //新增基本信息
        //签约信息
        //异动信息
        //职称信息
        //证书信息
        Personnel personnel = BeanUtil.toBean(personnelAddParam, Personnel.class);
        this.save(personnel);
        if(CollUtil.isNotEmpty(personnelAddParam.getPersonnelFileCertList())){
            List<PersonnelFileCert> collect = personnelAddParam.getPersonnelFileCertList().stream().map(personnelFileCertAddParam -> BeanUtil.toBean(personnelFileCertAddParam, PersonnelFileCert.class)).collect(Collectors.toList());
            collect.forEach(personnelFileCert -> personnelFileCert.setFileId(personnel.getId()));
            personnelFileCertService.saveBatch(collect);
        }
        if(CollUtil.isNotEmpty(personnelAddParam.getPersonnelFileMovesList())){
            List<PersonnelFileMoves> collect = personnelAddParam.getPersonnelFileMovesList().stream().map(personnelFileMovesAddParam -> BeanUtil.toBean(personnelFileMovesAddParam, PersonnelFileMoves.class)).collect(Collectors.toList());
            collect.forEach(personnelFileMoves -> personnelFileMoves.setFileId(personnel.getId()));
            personnelFileMovesService.saveBatch(collect);
        }
        if(CollUtil.isNotEmpty(personnelAddParam.getPersonnelFileTitleList())){
            List<PersonnelFileTitle> collect = personnelAddParam.getPersonnelFileTitleList().stream().map(personnelFileTitleAddParam -> BeanUtil.toBean(personnelFileTitleAddParam, PersonnelFileTitle.class)).collect(Collectors.toList());
            collect.forEach(personnelFileTitle -> personnelFileTitle.setFileId(personnel.getId()));
            personnelFileTitleService.saveBatch(collect);
        }
        if(CollUtil.isNotEmpty(personnelAddParam.getPersonnelFileRegisterList())){
            List<PersonnelFileRegister> collect = personnelAddParam.getPersonnelFileRegisterList().stream().map(personnelFileRegisterAddParam -> BeanUtil.toBean(personnelFileRegisterAddParam, PersonnelFileRegister.class)).collect(Collectors.toList());
            collect.forEach(personnelFileRegister -> personnelFileRegister.setFileId(personnel.getId()));
            personnelFileRegisterService.saveBatch(collect);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PersonnelEditParam personnelEditParam) {
        Personnel personnel = this.queryEntity(personnelEditParam.getId());
        BeanUtil.copyProperties(personnelEditParam, personnel);
        personnelFileRegisterService.deleteByFileId(personnel.getId());
        personnelFileMovesService.deleteByFileId(personnel.getId());
        personnelFileTitleService.deleteByFileId(personnel.getId());
        personnelFileCertService.deleteByFileId(personnel.getId());
        this.updateById(personnel);
        if(CollUtil.isNotEmpty(personnelEditParam.getPersonnelFileCertList())){
            List<PersonnelFileCert> collect = personnelEditParam.getPersonnelFileCertList().stream().map(param ->  BeanUtil.toBean(param, PersonnelFileCert.class)).collect(Collectors.toList());
            collect.forEach(personnelFileCert -> personnelFileCert.setFileId(personnel.getId()));
            personnelFileCertService.saveBatch(collect);
        }
        if(CollUtil.isNotEmpty(personnelEditParam.getPersonnelFileMovesList())){
            List<PersonnelFileMoves> collect = personnelEditParam.getPersonnelFileMovesList().stream().map(param -> BeanUtil.toBean(param, PersonnelFileMoves.class)).collect(Collectors.toList());
            collect.forEach(personnelFileMoves -> personnelFileMoves.setFileId(personnel.getId()));
            personnelFileMovesService.saveBatch(collect);
        }
        if(CollUtil.isNotEmpty(personnelEditParam.getPersonnelFileTitleList())){
            List<PersonnelFileTitle> collect = personnelEditParam.getPersonnelFileTitleList().stream().map(param -> BeanUtil.toBean(param, PersonnelFileTitle.class)).collect(Collectors.toList());
            collect.forEach(personnelFileTitle -> personnelFileTitle.setFileId(personnel.getId()));
            personnelFileTitleService.saveBatch(collect);
        }
        if(CollUtil.isNotEmpty(personnelEditParam.getPersonnelFileRegisterList())){
            List<PersonnelFileRegister> collect = personnelEditParam.getPersonnelFileRegisterList().stream().map(param -> BeanUtil.toBean(param, PersonnelFileRegister.class)).collect(Collectors.toList());
            collect.forEach(personnelFileRegister -> personnelFileRegister.setFileId(personnel.getId()));
            personnelFileRegisterService.saveBatch(collect);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PersonnelIdParam> personnelIdParamList) {
        // 执行删除
        List<String> ids = CollStreamUtil.toList(personnelIdParamList, PersonnelIdParam::getId);
        this.removeByIds(ids);
        personnelFileCertService.deleteNewFileId(ids);
        personnelFileTitleService.deleteNewFileId(ids);
        personnelFileMovesService.deleteNewFileId(ids);
        personnelFileRegisterService.deleteNewFileId(ids);
    }

    @Override
    public PersonnelVo detail(PersonnelIdParam personnelIdParam) {
        Personnel personnel = this.queryEntity(personnelIdParam.getId());
        PersonnelVo personnelVo = BeanUtil.toBean(personnel, PersonnelVo.class);
        //入职司龄
        personnelVo.setEnterage(dayComparePrecise(new Date(),personnelVo.getHireDate()));
        //转正司龄
        personnelVo.setPositiveAge(dayComparePrecise(new Date(),personnelVo.getPositiveTime()));
        //年纪
        personnelVo.setAge(Period.between(LocalDate.parse(DateUtil.formatDate(new Date())), LocalDate.parse(DateUtil.formatDate(personnelVo.getBirth()))).getYears());

        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("FILE_ID",personnel.getId());
        columnMap.put("DELETE_FLAG","NOT_DELETE");
        personnelVo.setPersonnelFileCertList(personnelFileCertService.listByMap(columnMap));
        personnelVo.setPersonnelFileMovesList(personnelFileMovesService.listByMap(columnMap));
        personnelVo.setPersonnelFileTitleList(personnelFileTitleService.listByMap(columnMap));
        List<PersonnelFileRegister> personnelFileRegisters = personnelFileRegisterService.listByMap(columnMap);
        personnelVo.setPersonnelFileRegisterList(personnelFileRegisters);
        //合同续签次数
        personnelVo.setAge(personnelFileRegisters.size());
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
        Page<PersonnelListVo> pageList = personnelMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
        List<PersonnelListVo> records = pageList.getRecords();
        records.forEach(personnelListVo -> personnelListVo.setEnterage(dayComparePrecise(new Date(),personnelListVo.getHireDate())));
        return personnelMapper.getPageList(CommonPageRequest.defaultPage(),queryWrapper);
    }
    private static String dayComparePrecise(Date date1, Date date2){
        Period period = Period.between(LocalDate.parse(DateUtil.formatDate(date1)), LocalDate.parse(DateUtil.formatDate(date2)));

        return period.getYears() + "年" +
                period.getMonths() + "月" +
                period.getDays() + "日";
    }
}
