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
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.sys.modular.hr.certManage.entity.CertManage;
import vip.xiaonuo.sys.modular.hr.certManage.enums.CertTypeEnum;
import vip.xiaonuo.sys.modular.hr.certManage.service.CertManageService;
import vip.xiaonuo.sys.modular.hr.hrCenter.entity.PersonnelFileTitle;
import vip.xiaonuo.sys.modular.hr.hrCenter.service.PersonnelFileTitleService;
import vip.xiaonuo.sys.modular.hr.registerCert.entity.ApplyRegCertData;
import vip.xiaonuo.sys.modular.hr.registerCert.entity.ApplyRegCertDataFlow;
import vip.xiaonuo.sys.modular.hr.registerCert.entity.RegAlwUser;
import vip.xiaonuo.sys.modular.hr.registerCert.entity.RegCertData;
import vip.xiaonuo.sys.modular.hr.registerCert.mapper.ApplyRegCertDataMapper;
import vip.xiaonuo.sys.modular.hr.registerCert.param.*;
import vip.xiaonuo.sys.modular.hr.registerCert.service.ApplyRegCertDataFlowService;
import vip.xiaonuo.sys.modular.hr.registerCert.service.ApplyRegCertDataService;
import vip.xiaonuo.sys.modular.hr.registerCert.service.RegAlwUserService;
import vip.xiaonuo.sys.modular.hr.registerCert.service.RegCertDataService;
import vip.xiaonuo.sys.modular.hr.registerCert.vo.RegAlwUserVo;
import vip.xiaonuo.sys.modular.hr.registerCert.vo.RegCertDataVo;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryStandardMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.PersonnelParam;
import vip.xiaonuo.sys.modular.hr.salary.vo.PersonnelRegInfoVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.PersonnelTitleVo;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubPersonnelTitle;
import vip.xiaonuo.sys.modular.hr.sub.service.SubPersonnelTitleService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 注册补贴-申请基本信息Service接口实现类
 *
 * @author ljy
 * @date  2024/03/27 15:28
 **/
@Service
public class ApplyRegCertDataServiceImpl extends ServiceImpl<ApplyRegCertDataMapper, ApplyRegCertData> implements ApplyRegCertDataService {

    @Resource
    CommonCacheOperator commonCacheOperator;
    @Resource
    CertManageService certManageService;
    @Resource
    SalaryStandardMapper salaryStandardMapper;
    @Resource
    PersonnelFileTitleService personnelFileTitleService;
    @Resource
    SubPersonnelTitleService subPersonnelTitleService;
    @Resource
    RegAlwUserService regAlwUserService;
    @Resource
    RegCertDataService regCertDataService;
    @Resource
    ApplyRegCertDataFlowService applyRegCertDataFlowService;

    @Override
    public Page<ApplyRegCertData> page(ApplyRegCertDataPageParam applyRegCertDataPageParam) {
        QueryWrapper<ApplyRegCertData> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(applyRegCertDataPageParam.getRegisterLever())) {
            queryWrapper.lambda().eq(ApplyRegCertData::getRegisterLever, applyRegCertDataPageParam.getRegisterLever());
        }
        if(ObjectUtil.isNotEmpty(applyRegCertDataPageParam.getRegisterType())) {
            queryWrapper.lambda().eq(ApplyRegCertData::getRegisterType, applyRegCertDataPageParam.getRegisterType());
        }
        if(ObjectUtil.isNotEmpty(applyRegCertDataPageParam.getRegisterSubject())) {
            queryWrapper.lambda().eq(ApplyRegCertData::getRegisterSubject, applyRegCertDataPageParam.getRegisterSubject());
        }
        if(ObjectUtil.isNotEmpty(applyRegCertDataPageParam.getAplyItem())) {
            queryWrapper.lambda().like(ApplyRegCertData::getAplyItem, applyRegCertDataPageParam.getAplyItem());
        }
        if(ObjectUtil.isNotEmpty(applyRegCertDataPageParam.getIsFlag())) {
            queryWrapper.lambda().eq(ApplyRegCertData::getIsFlag, applyRegCertDataPageParam.getIsFlag());
        }
        if(ObjectUtil.isNotEmpty(applyRegCertDataPageParam.getJoinPrjName())) {
            queryWrapper.lambda().like(ApplyRegCertData::getJoinPrjName, applyRegCertDataPageParam.getJoinPrjName());
        }
        if(ObjectUtil.isAllNotEmpty(applyRegCertDataPageParam.getSortField(), applyRegCertDataPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(applyRegCertDataPageParam.getSortOrder());
            queryWrapper.orderBy(true, applyRegCertDataPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(applyRegCertDataPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ApplyRegCertData::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ApplyRegCertDataAddParam applyRegCertDataAddParam) {
         ApplyRegCertData applyRegCertData = BeanUtil.toBean(applyRegCertDataAddParam, ApplyRegCertData.class);
        this.save(applyRegCertData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ApplyRegCertDataEditParam applyRegCertDataEditParam) {
        ApplyRegCertData applyRegCertData = this.queryEntity(applyRegCertDataEditParam.getId());
        BeanUtil.copyProperties(applyRegCertDataEditParam, applyRegCertData);
        this.updateById(applyRegCertData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ApplyRegCertDataIdParam> applyRegCertDataIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(applyRegCertDataIdParamList, ApplyRegCertDataIdParam::getId));
    }

    @Override
    public ApplyRegCertData detail(ApplyRegCertDataIdParam applyRegCertDataIdParam) {
        return this.queryEntity(applyRegCertDataIdParam.getId());
    }

    @Override
    public ApplyRegCertData queryEntity(String id) {
        ApplyRegCertData applyRegCertData = this.getById(id);
        if(ObjectUtil.isEmpty(applyRegCertData)) {
            throw new CommonException("注册补贴-申请基本信息不存在，id值为：{}", id);
        }
        return applyRegCertData;
    }

    @Override
    public List<PersonnelRegInfoVo> alwAndCertData(AlwAndCertParam alwAndCertParam){
        SaBaseLoginUser saBaseLoginUser = StpLoginUserUtil.getLoginUser();

        PersonnelParam personnelParam = new PersonnelParam();
        personnelParam.setName(saBaseLoginUser.getName());
        if(StringUtils.isNotEmpty(alwAndCertParam.getUserName())){
            personnelParam.setName(alwAndCertParam.getUserName());
        }
        if(StringUtils.isNotEmpty(alwAndCertParam.getUserType())){
            personnelParam.setUserType(alwAndCertParam.getUserType());
        }
        if(StringUtils.isNotEmpty(alwAndCertParam.getStatus())){
            personnelParam.setStatus(alwAndCertParam.getStatus());
        }
        List<PersonnelRegInfoVo> personnelRegInfoList = salaryStandardMapper.getPersonnelRegList(personnelParam);
        if(CollectionUtil.isEmpty(personnelRegInfoList)){return personnelRegInfoList;}

        List<String> perIds = personnelRegInfoList.stream().map(person -> person.getId()).collect(Collectors.toList());
        getPersonnelTitle(perIds,personnelRegInfoList);
        getPersonnelCert(personnelRegInfoList);
        return personnelRegInfoList;
    }

    /**  获取用户职称信息 */
    private void getPersonnelTitle(List<String> perIds,List<PersonnelRegInfoVo> personnelRegInfoList){
        List<PersonnelFileTitle> personnelFileTitleList = personnelFileTitleService.list(new LambdaQueryWrapper<PersonnelFileTitle>().in(PersonnelFileTitle::getFileId,perIds));
        List<SubPersonnelTitle>  subPersonnelTitleList = subPersonnelTitleService.list(new LambdaQueryWrapper<SubPersonnelTitle>().in(SubPersonnelTitle::getFileId,perIds));

        List<PersonnelTitleVo> personnelTitleVoList = new ArrayList<>();
        personnelFileTitleList.forEach(person -> {
            PersonnelTitleVo personnelTitleVo = BeanUtil.toBean(person,PersonnelTitleVo.class);
            personnelTitleVoList.add(personnelTitleVo);
        });
        subPersonnelTitleList.forEach(person -> {
            PersonnelTitleVo personnelTitleVo = BeanUtil.toBean(person,PersonnelTitleVo.class);
            personnelTitleVoList.add(personnelTitleVo);
        });
        Map<String,List<PersonnelTitleVo>> map = new HashMap<>();
        personnelTitleVoList.forEach(person -> {
            List<PersonnelTitleVo> voList = ObjectUtil.isNotEmpty(map.get(person.getFileId()))? map.get(person.getFileId()) : new ArrayList<>();
            voList.add(person);
            map.put(person.getFileId(),voList);
        });
        personnelRegInfoList.forEach(reg -> {
            if(ObjectUtil.isNotEmpty(map.get(reg.getId()))){reg.setPersonnelTitleVoList(map.get(reg.getId()));}
        });
    }

    /**  获取用户注册证书信息 */
    private void getPersonnelCert(List<PersonnelRegInfoVo> personnelRegInfoList){
        List<String> idCards = personnelRegInfoList.stream().map(reg -> reg.getIdCardNumber()).collect(Collectors.toList());;
        QueryWrapper<CertManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(CertManage::getIdNumber,idCards);
        queryWrapper.lambda().eq(CertManage::getCertStatus,"0");
        queryWrapper.lambda().eq(CertManage::getType, CertTypeEnum.REGISTER.getValue());
        queryWrapper.lambda().orderByAsc(CertManage::getCertTypeId).orderByAsc(CertManage::getLevelId).orderByAsc(CertManage::getMajor);
        List<CertManage> certManageList = certManageService.list(queryWrapper);
        Map<String,List<CertManage>> map = new HashMap<>();
        certManageList.forEach(cert -> {
            List<CertManage> certManages = ObjectUtil.isNotEmpty(map.get(cert.getIdNumber())) ? map.get(cert.getIdNumber()) : new ArrayList<>();
            certManages.add(cert);
            map.put(cert.getIdNumber(),certManages);
        });
        personnelRegInfoList.forEach(reg -> {
            if(ObjectUtil.isNotEmpty(map.get(reg.getIdCardNumber()))){reg.setCertManages(map.get(reg.getIdCardNumber()));}
        });
    }
}
