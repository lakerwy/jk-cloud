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
package vip.xiaonuo.sys.modular.hr.certManage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
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
import vip.xiaonuo.sys.modular.hr.certManage.entity.CertManage;
import vip.xiaonuo.sys.modular.hr.certManage.enums.CertTypeEnum;
import vip.xiaonuo.sys.modular.hr.certManage.enums.PersonTypeEnum;
import vip.xiaonuo.sys.modular.hr.certManage.mapper.CertManageMapper;
import vip.xiaonuo.sys.modular.hr.certManage.param.*;
import vip.xiaonuo.sys.modular.hr.certManage.service.CertManageService;
import vip.xiaonuo.sys.modular.hr.certManage.vo.*;
import vip.xiaonuo.sys.modular.hr.personnel.entity.Personnel;
import vip.xiaonuo.sys.modular.hr.personnel.service.PersonnelService;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubPersonnel;
import vip.xiaonuo.sys.modular.hr.sub.service.SubPersonnelService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 人员资质管理Service接口实现类
 *
 * @author yong.ye
 * @date  2024/01/23 17:23
 **/
@Service
public class CertManageServiceImpl extends ServiceImpl<CertManageMapper, CertManage> implements CertManageService {

    @Resource
    private CertManageMapper certManageMapper;
    @Resource
    private PersonnelService personnelService;
    @Resource
    private SubPersonnelService subPersonnelService;

    @Override
    public Page<CertManage> page(CertManagePageParam certManagePageParam) {
        QueryWrapper<CertManage> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(certManagePageParam.getType())) {
            queryWrapper.eq("a.TYPE", certManagePageParam.getType());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getCertType())) {
            queryWrapper.eq("a.CERT_TYPE", certManagePageParam.getCertType());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getLevel())){
            queryWrapper.eq("a.LEVEL",certManagePageParam.getLevel());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getDept())){
            queryWrapper.like("a.DEPT",certManagePageParam.getDept());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getName())) {
            queryWrapper.like("a.NAME", certManagePageParam.getName());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getMajor())) {
            queryWrapper.and(q->q.like("a.MAJOR", certManagePageParam.getMajor())
                    .or().like("a.MAJOR2",certManagePageParam.getMajor()));
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getIdNumber())) {
            queryWrapper.like("a.ID_NUMBER", certManagePageParam.getIdNumber());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getCertNo())) {
            queryWrapper.like("a.CERT_NO", certManagePageParam.getCertNo());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getCertSealNo())) {
            queryWrapper.like("a.CERT_SEAL_NO", certManagePageParam.getCertSealNo());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getStartIssueDate()) && ObjectUtil.isNotEmpty(certManagePageParam.getEndIssueDate())) {
            queryWrapper.between("a.ISSUE_DATE", certManagePageParam.getStartIssueDate(), certManagePageParam.getEndIssueDate());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getStartValidity()) && ObjectUtil.isNotEmpty(certManagePageParam.getEndValidity())) {
            queryWrapper.between("a.VALIDITY", certManagePageParam.getStartValidity(), certManagePageParam.getEndValidity());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getDepositAddress())) {
            queryWrapper.like("a.DEPOSIT_ADDRESS", certManagePageParam.getDepositAddress());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getUnit())) {
            queryWrapper.like("a.UNIT", certManagePageParam.getUnit());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getCertName())) {
            queryWrapper.like("a.CERT_NAME", certManagePageParam.getCertName());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getJobType())) {
            queryWrapper.like("a.JOB_TYPE", certManagePageParam.getJobType());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getTechnicalTitle())) {
            queryWrapper.like("a.TECHNICAL_TITLE", certManagePageParam.getTechnicalTitle());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getCompanyName())) {
            queryWrapper.like("a.COMPANY_NAME", certManagePageParam.getCompanyName());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getCertStatus())) {
            queryWrapper.eq("a.CERT_STATUS", certManagePageParam.getCertStatus());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getUserType())) {
            queryWrapper.eq("a.USER_TYPE", certManagePageParam.getUserType());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getCertStage())){
            queryWrapper.eq("a.certStage", certManagePageParam.getCertStage());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getSearchKey())) {
            queryWrapper.and(q -> q.like("a.CERT_NO", certManagePageParam.getSearchKey())
                    .or().like("a.CERT_NAME", certManagePageParam.getSearchKey())
                    .or().like("a.CERT_SEAL_NO", certManagePageParam.getSearchKey())
                    .or().like("a.CERT_TYPE", certManagePageParam.getSearchKey())
                    .or().like("a.COMPANY_NAME", certManagePageParam.getSearchKey())
                    .or().like("a.ID_NUMBER", certManagePageParam.getSearchKey())
                    .or().like("a.MAJOR", certManagePageParam.getSearchKey())
                    .or().like("a.MAJOR2", certManagePageParam.getSearchKey())
                    .or().like("a.UNIT", certManagePageParam.getSearchKey())
                    .or().like("a.TECHNICAL_TITLE", certManagePageParam.getSearchKey())
                    .or().like("a.NAME", certManagePageParam.getSearchKey())
                    .or().like("a.JOB_TYPE", certManagePageParam.getSearchKey())
            );
        }
        if (ObjectUtil.isAllNotEmpty(certManagePageParam.getSortField(), certManagePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(certManagePageParam.getSortOrder());
            queryWrapper.orderBy(true, certManagePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(certManagePageParam.getSortField()));
        } else {
            queryWrapper.orderByAsc("a.ID_NUMBER").orderByAsc("a.TYPE").orderByAsc("a.CERT_TYPE");
        }
        return certManageMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CertManageAddParam certManageAddParam) {
        CertManage certManage = BeanUtil.toBean(certManageAddParam, CertManage.class);
        this.save(certManage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CertManageEditParam certManageEditParam) {
        CertManage certManage = this.queryEntity(certManageEditParam.getId());
        BeanUtil.copyProperties(certManageEditParam, certManage);
        this.updateById(certManage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CertManageIdParam> certManageIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(certManageIdParamList, CertManageIdParam::getId));
    }

    @Override
    public CertManage detail(CertManageIdParam certManageIdParam) {
        return this.queryEntity(certManageIdParam.getId());
    }

    @Override
    public CertManage queryEntity(String id) {
        CertManage certManage = this.getById(id);
        if (ObjectUtil.isEmpty(certManage)) {
            throw new CommonException("人员资质管理不存在，id值为：{}", id);
        }
        return certManage;
    }

    @Override
    public List<PersonVo> list(PersonParam personParam) {
        return certManageMapper.getPersonList(personParam);
    }

    @Override
    public SearchVo searchPage(CertSearchPageParam certSearchPageParam) {
        //不开启综合搜索，普通搜索模块
        QueryWrapper<CertManage> queryWrapper = new QueryWrapper<>();
        if (!certSearchPageParam.getIsOpen()) {
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getType())) {
                queryWrapper.lambda().eq(CertManage::getType, certSearchPageParam.getType());
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getCertType())) {
                queryWrapper.lambda().eq(CertManage::getCertType, certSearchPageParam.getCertType());
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getName())) {
                queryWrapper.lambda().like(CertManage::getName, certSearchPageParam.getName());
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getMajor())) {
                queryWrapper.lambda().and(q->q.like(CertManage::getMajor, certSearchPageParam.getMajor())
                        .or().like(CertManage::getMajor2,certSearchPageParam.getMajor()));
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getIdNumber())) {
                queryWrapper.lambda().like(CertManage::getIdNumber, certSearchPageParam.getIdNumber());
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getCertNo())) {
                queryWrapper.lambda().like(CertManage::getCertNo, certSearchPageParam.getCertNo());
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getCertSealNo())) {
                queryWrapper.lambda().like(CertManage::getCertSealNo, certSearchPageParam.getCertSealNo());
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getStartIssueDate()) && ObjectUtil.isNotEmpty(certSearchPageParam.getEndIssueDate())) {
                queryWrapper.lambda().between(CertManage::getIssueDate, certSearchPageParam.getStartIssueDate(), certSearchPageParam.getEndIssueDate());
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getStartValidity()) && ObjectUtil.isNotEmpty(certSearchPageParam.getEndValidity())) {
                queryWrapper.lambda().between(CertManage::getValidity, certSearchPageParam.getStartValidity(), certSearchPageParam.getEndValidity());
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getUnit())) {
                queryWrapper.lambda().like(CertManage::getUnit, certSearchPageParam.getUnit());
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getCertName())) {
                queryWrapper.lambda().like(CertManage::getCertName, certSearchPageParam.getCertName());
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getJobType())) {
                queryWrapper.lambda().like(CertManage::getJobType, certSearchPageParam.getJobType());
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getTechnicalTitle())) {
                queryWrapper.lambda().like(CertManage::getTechnicalTitle, certSearchPageParam.getTechnicalTitle());
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getCompanyName())) {
                queryWrapper.lambda().like(CertManage::getCompanyName, certSearchPageParam.getCompanyName());
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getCertStatus())) {
                queryWrapper.lambda().eq(CertManage::getCertStatus, certSearchPageParam.getCertStatus());
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getUserType())) {
                queryWrapper.lambda().eq(CertManage::getUserType, certSearchPageParam.getUserType());
            }
            if (ObjectUtil.isNotEmpty(certSearchPageParam.getSearchKey())) {
                queryWrapper.lambda().and(q -> q.like(CertManage::getCertNo, certSearchPageParam.getSearchKey())
                        .or().like(CertManage::getCertName, certSearchPageParam.getSearchKey())
                        .or().like(CertManage::getCertSealNo, certSearchPageParam.getSearchKey())
                        .or().like(CertManage::getCertType, certSearchPageParam.getSearchKey())
                        .or().like(CertManage::getCompanyName, certSearchPageParam.getSearchKey())
                        .or().like(CertManage::getIdNumber, certSearchPageParam.getSearchKey())
                        .or().like(CertManage::getMajor, certSearchPageParam.getSearchKey())
                        .or().like(CertManage::getMajor2, certSearchPageParam.getSearchKey())
                        .or().like(CertManage::getUnit, certSearchPageParam.getSearchKey())
                        .or().like(CertManage::getTechnicalTitle, certSearchPageParam.getSearchKey())
                        .or().like(CertManage::getName, certSearchPageParam.getSearchKey())
                        .or().like(CertManage::getJobType, certSearchPageParam.getSearchKey())
                );
            }
        } else {
            //开启综合搜索（注册类|培训类|职称类 证书同时满足情况）
            List<String> ids = getFilter(certSearchPageParam);
            if(CollUtil.isNotEmpty(ids)){
                queryWrapper.lambda().in(CertManage::getId,ids);
            }else{
                queryWrapper.lambda().in(CertManage::getId, Arrays.asList(""));
            }
        }
        //排序
        queryWrapper.lambda().orderByAsc(CertManage::getIdNumber).orderByAsc(CertManage::getType).orderByAsc(CertManage::getCertType);
        List<CertManageVo> list = BeanUtil.copyToList(this.list(queryWrapper),CertManageVo.class);
        if(CollUtil.isEmpty(list)){
            return null;
        }
        SearchVo searchVo = new SearchVo();
        searchVo.setReCertList(list.stream().filter(x->CertTypeEnum.REGISTER.getValue().equals(x.getType())).collect(Collectors.toList()));
        searchVo.setTrCertList(list.stream().filter(x->CertTypeEnum.TRAIN.getValue().equals(x.getType())).collect(Collectors.toList()));
        searchVo.setOtherCertList(list.stream().filter(x->CertTypeEnum.OTHER.getValue().equals(x.getType())).collect(Collectors.toList()));
        return searchVo;
    }

    @Override
    public SearchDetailVo searchDetail(String id) {
        CertManage certManage = this.queryEntity(id);
        List<CertManageVo> list = BeanUtil.copyToList(this.list(new QueryWrapper<CertManage>().lambda().eq(CertManage::getIdNumber,certManage.getIdNumber())),CertManageVo.class);
        SearchDetailVo searchDetailVo = new SearchDetailVo();
        searchDetailVo.setReCertList(list.stream().filter(x->CertTypeEnum.REGISTER.getValue().equals(x.getType())).collect(Collectors.toList()));
        searchDetailVo.setTrCertList(list.stream().filter(x->CertTypeEnum.TRAIN.getValue().equals(x.getType())).collect(Collectors.toList()));
        searchDetailVo.setOtherCertList(list.stream().filter(x->CertTypeEnum.OTHER.getValue().equals(x.getType())).collect(Collectors.toList()));
        UserInfo userInfo = BeanUtil.copyProperties(certManage,UserInfo.class);
        searchDetailVo.setUserInfo(userInfo);
        return searchDetailVo;
    }

    /**
     * 启动综合查询开关，框定同时满足 注册类|培训类|职称类 搜索的结果集 【三个的交集】
     * @param param
     * @return
     */
    public List<String> getFilter(CertSearchPageParam param){
        QueryWrapper<CertManage> query = new QueryWrapper<>();
        if(!param.getIsRe() && !param.getIsTr() && !param.getIsOther()){
            return null;
        }
        //注册类
        if(param.getIsRe()) {
            query.lambda().or(q1 -> {
                q1.eq(CertManage::getType, CertTypeEnum.REGISTER.getValue());
                if (ObjectUtil.isNotEmpty(param.getReCertType())) {
                    q1.eq(CertManage::getCertType, param.getReCertType());
                }
                if (ObjectUtil.isNotEmpty(param.getReMajor())) {
                    q1.and(q->q.like(CertManage::getMajor, param.getReMajor()).or().like(CertManage::getMajor2,param.getReMajor()));
                }
                if (ObjectUtil.isNotEmpty(param.getReUnit())) {
                    q1.like(CertManage::getUnit, param.getReUnit());
                }
                if (ObjectUtil.isNotEmpty(param.getReStartIssueDate()) && ObjectUtil.isNotEmpty(param.getReEndIssueDate())) {
                    q1.between(CertManage::getIssueDate, param.getReStartIssueDate(), param.getReEndIssueDate());
                }
                if (ObjectUtil.isNotEmpty(param.getReSearchKey())) {
                    q1.and(q11 -> q11.like(CertManage::getCertNo, param.getReSearchKey())
                            .or().like(CertManage::getCertName, param.getReSearchKey())
                            .or().like(CertManage::getCertSealNo, param.getReSearchKey())
                            .or().like(CertManage::getCertType, param.getReSearchKey())
                            .or().like(CertManage::getCompanyName, param.getReSearchKey())
                            .or().like(CertManage::getIdNumber, param.getReSearchKey())
                            .or().like(CertManage::getMajor, param.getReSearchKey())
                            .or().like(CertManage::getMajor2, param.getReSearchKey())
                            .or().like(CertManage::getUnit, param.getReSearchKey())
                            .or().like(CertManage::getTechnicalTitle, param.getReSearchKey())
                            .or().like(CertManage::getName, param.getReSearchKey())
                            .or().like(CertManage::getJobType, param.getReSearchKey()));
                }
            });
        }

        //培训类
        if(param.getIsTr()) {
            query.lambda().or(q2 -> {
                q2.eq(CertManage::getType, CertTypeEnum.TRAIN.getValue());
                if (ObjectUtil.isNotEmpty(param.getTrCertType())) {
                    q2.eq(CertManage::getCertType, param.getTrCertType());
                }
                if (ObjectUtil.isNotEmpty(param.getTrCertName())) {
                    q2.eq(CertManage::getCertName, param.getTrCertName());
                }
                if (ObjectUtil.isNotEmpty(param.getTrMajor())) {
                    q2.and(q->q.like(CertManage::getMajor, param.getReMajor()).or().like(CertManage::getMajor2,param.getReMajor()));
                }
                if (ObjectUtil.isNotEmpty(param.getTrUnit())) {
                    q2.like(CertManage::getUnit, param.getTrUnit());
                }
                if (ObjectUtil.isNotEmpty(param.getTrStartIssueDate()) && ObjectUtil.isNotEmpty(param.getTrEndIssueDate())) {
                    q2.between(CertManage::getIssueDate, param.getTrStartIssueDate(), param.getTrEndIssueDate());
                }
                if (ObjectUtil.isNotEmpty(param.getTrJobType())) {
                    q2.like(CertManage::getJobType, param.getTrJobType());
                }
                if (ObjectUtil.isNotEmpty(param.getTrTechnicalTitle())) {
                    q2.like(CertManage::getTechnicalTitle, param.getTrTechnicalTitle());
                }
                if (ObjectUtil.isNotEmpty(param.getTrSearchKey())) {
                    q2.and(q22 -> q22.like(CertManage::getCertNo, param.getTrSearchKey())
                            .or().like(CertManage::getCertName, param.getTrSearchKey())
                            .or().like(CertManage::getCertSealNo, param.getTrSearchKey())
                            .or().like(CertManage::getCertType, param.getTrSearchKey())
                            .or().like(CertManage::getCompanyName, param.getTrSearchKey())
                            .or().like(CertManage::getIdNumber, param.getTrSearchKey())
                            .or().like(CertManage::getMajor, param.getTrSearchKey())
                            .or().like(CertManage::getMajor2, param.getTrSearchKey())
                            .or().like(CertManage::getUnit, param.getTrSearchKey())
                            .or().like(CertManage::getTechnicalTitle, param.getTrSearchKey())
                            .or().like(CertManage::getName, param.getTrSearchKey())
                            .or().like(CertManage::getJobType, param.getTrSearchKey()));
                }
            });
        }

        //职称类
        if(param.getIsOther()) {
            query.lambda().or(q3 -> {
                q3.eq(CertManage::getType, CertTypeEnum.OTHER.getValue());
                if (ObjectUtil.isNotEmpty(param.getOtherCertType())) {
                    q3.eq(CertManage::getCertType, param.getOtherCertType());
                }
                if (ObjectUtil.isNotEmpty(param.getOtherCertName())) {
                    q3.eq(CertManage::getCertName, param.getOtherCertName());
                }
                if (ObjectUtil.isNotEmpty(param.getOtherMajor())) {
                    q3.and(q->q.like(CertManage::getMajor, param.getReMajor()).or().like(CertManage::getMajor2,param.getReMajor()));
                }
                if (ObjectUtil.isNotEmpty(param.getOtherUnit())) {
                    q3.like(CertManage::getUnit, param.getOtherUnit());
                }
                if (ObjectUtil.isNotEmpty(param.getOtherStartIssueDate()) && ObjectUtil.isNotEmpty(param.getOtherEndIssueDate())) {
                    q3.between(CertManage::getIssueDate, param.getOtherStartIssueDate(), param.getOtherEndIssueDate());
                }
                if (ObjectUtil.isNotEmpty(param.getOtherTechnicalTitle())) {
                    q3.like(CertManage::getTechnicalTitle, param.getOtherTechnicalTitle());
                }
                if (ObjectUtil.isNotEmpty(param.getOtherSearchKey())) {
                    q3.and(q33 -> q33.like(CertManage::getCertNo, param.getOtherSearchKey())
                            .or().like(CertManage::getCertName, param.getOtherSearchKey())
                            .or().like(CertManage::getCertSealNo, param.getOtherSearchKey())
                            .or().like(CertManage::getCertType, param.getOtherSearchKey())
                            .or().like(CertManage::getCompanyName, param.getOtherSearchKey())
                            .or().like(CertManage::getIdNumber, param.getOtherSearchKey())
                            .or().like(CertManage::getMajor, param.getOtherSearchKey())
                            .or().like(CertManage::getMajor2, param.getOtherSearchKey())
                            .or().like(CertManage::getUnit, param.getOtherSearchKey())
                            .or().like(CertManage::getTechnicalTitle, param.getOtherSearchKey())
                            .or().like(CertManage::getName, param.getOtherSearchKey())
                            .or().like(CertManage::getJobType, param.getOtherSearchKey()));
                }
            });
        }

        //查询【并集】
        List<CertManage> listCert = this.list(query);

        //过滤，获取【交集】
        List<String> filterIds = new ArrayList<>();
        List<String> registerList = listCert.stream().filter(x->CertTypeEnum.REGISTER.getValue().equals(x.getType())).map(x->x.getIdNumber()).collect(Collectors.toList());
        List<String> trainList = listCert.stream().filter(x->CertTypeEnum.TRAIN.getValue().equals(x.getType())).map(x->x.getIdNumber()).collect(Collectors.toList());
        List<String> otherList = listCert.stream().filter(x->CertTypeEnum.OTHER.getValue().equals(x.getType())).map(x->x.getIdNumber()).collect(Collectors.toList());
        for (CertManage cert:listCert) {
            Boolean isAdd = Boolean.TRUE;
            if(ObjectUtil.isEmpty(cert.getIdNumber())
                    || (param.getIsRe() && !registerList.contains(cert.getIdNumber()))
                    || (param.getIsTr() && !trainList.contains(cert.getIdNumber()))
                    || (param.getIsOther() && !otherList.contains(cert.getIdNumber())))
            {
                isAdd = Boolean.FALSE;
            }
            if(isAdd) filterIds.add(cert.getId());
        }
        return filterIds;
    }
}
