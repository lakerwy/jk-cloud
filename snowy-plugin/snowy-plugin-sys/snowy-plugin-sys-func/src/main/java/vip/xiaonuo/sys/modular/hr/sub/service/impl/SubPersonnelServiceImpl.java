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

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.common.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.sub.entity.*;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubPersonnelMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.*;
import vip.xiaonuo.sys.modular.hr.sub.service.*;
import vip.xiaonuo.sys.modular.hr.sub.vo.SubPersonnelVo;
import vip.xiaonuo.sys.modular.position.param.SysPositionAddParam;
import vip.xiaonuo.sys.modular.position.service.SysPositionService;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

/**
 * HR-分公司-人事档案Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/21 11:14
 **/
@Service
public class SubPersonnelServiceImpl extends ServiceImpl<SubPersonnelMapper, SubPersonnel> implements SubPersonnelService {

    @Resource
    private SysPositionService sysPositionService;
    @Resource
    private SubPersonnelDegreeService subPersonnelDegreeService;
    @Resource
    private SubPersonnelCertService subPersonnelCertService;
    @Resource
    private SubPersonnelRegisterService subPersonnelRegisterService;
    @Resource
    private SubPersonnelTitleService subPersonnelTitleService;
    @Override
    public Page<SubPersonnel> page(SubPersonnelPageParam subPersonnelPageParam) {
        QueryWrapper<SubPersonnel> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(subPersonnelPageParam.getFileNo())) {
            queryWrapper.lambda().like(SubPersonnel::getFileNo, subPersonnelPageParam.getFileNo());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelPageParam.getName())) {
            queryWrapper.lambda().like(SubPersonnel::getName, subPersonnelPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelPageParam.getDeptId())) {
            queryWrapper.lambda().eq(SubPersonnel::getDeptId, subPersonnelPageParam.getDeptId());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelPageParam.getDept())) {
            queryWrapper.lambda().like(SubPersonnel::getDept, subPersonnelPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelPageParam.getDutyId())) {
            queryWrapper.lambda().eq(SubPersonnel::getDutyId, subPersonnelPageParam.getDutyId());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelPageParam.getDuty())) {
            queryWrapper.lambda().like(SubPersonnel::getDuty, subPersonnelPageParam.getDuty());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelPageParam.getStatus())) {
            queryWrapper.lambda().eq(SubPersonnel::getStatus, subPersonnelPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelPageParam.getSex())) {
            queryWrapper.lambda().eq(SubPersonnel::getSex, subPersonnelPageParam.getSex());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelPageParam.getIdNumber())) {
            queryWrapper.lambda().like(SubPersonnel::getIdNumber, subPersonnelPageParam.getIdNumber());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelPageParam.getPhone())) {
            queryWrapper.lambda().like(SubPersonnel::getPhone, subPersonnelPageParam.getPhone());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelPageParam.getStartHireDate()) && ObjectUtil.isNotEmpty(subPersonnelPageParam.getEndHireDate())) {
            queryWrapper.lambda().between(SubPersonnel::getHireDate, subPersonnelPageParam.getStartHireDate(), subPersonnelPageParam.getEndHireDate());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelPageParam.getEmployNature())) {
            queryWrapper.lambda().eq(SubPersonnel::getEmployNature, subPersonnelPageParam.getEmployNature());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelPageParam.getStartPositiveTime()) && ObjectUtil.isNotEmpty(subPersonnelPageParam.getEndPositiveTime())) {
            queryWrapper.lambda().between(SubPersonnel::getPositiveTime, subPersonnelPageParam.getStartPositiveTime(), subPersonnelPageParam.getEndPositiveTime());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelPageParam.getInsUnit())) {
            queryWrapper.lambda().like(SubPersonnel::getInsUnit, subPersonnelPageParam.getInsUnit());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(SubPersonnel::getName,subPersonnelPageParam.getSearchKey())
                    .or().like(SubPersonnel::getDept,subPersonnelPageParam.getSearchKey())
                    .or().like(SubPersonnel::getDuty,subPersonnelPageParam.getSearchKey())
                    .or().like(SubPersonnel::getInsUnit,subPersonnelPageParam.getSearchKey())
                    .or().like(SubPersonnel::getEmail,subPersonnelPageParam.getSearchKey())
                    .or().like(SubPersonnel::getPhone,subPersonnelPageParam.getSearchKey())
                    .or().like(SubPersonnel::getIdNumber,subPersonnelPageParam.getSearchKey())
                    .or().like(SubPersonnel::getFileNo,subPersonnelPageParam.getSearchKey()));
        }

        // 校验数据范围
//        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
//        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
//            queryWrapper.lambda().in(SubPersonnel::getDataScopeId, loginUserDataScope);
//        } else {
//            queryWrapper.lambda().eq(SubPersonnel::getDataScopeId, StpUtil.getLoginIdAsString());
//        }

        if(ObjectUtil.isAllNotEmpty(subPersonnelPageParam.getSortField(), subPersonnelPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subPersonnelPageParam.getSortOrder());
            queryWrapper.orderBy(true, subPersonnelPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subPersonnelPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(SubPersonnel::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubPersonnelParam subPersonnelParam) {
        //新增岗位
        SysPositionAddParam positionAddParam = new SysPositionAddParam();
        positionAddParam.setName(subPersonnelParam.getDuty());
        positionAddParam.setOrgId(subPersonnelParam.getDeptId());
        positionAddParam.setCategory("LOW");
        positionAddParam.setSortCode(99);
        String dutyId = sysPositionService.newAdd(positionAddParam).getId();
        //处理分公司人员档案
        SubPersonnel subPersonnel = BeanUtil.toBean(subPersonnelParam, SubPersonnel.class);
        subPersonnel.setDutyId(dutyId);
        subPersonnel.setExtJson(JsonUtils.object2json(subPersonnelParam));
        this.save(subPersonnel);
        //学历
        if(CollUtil.isNotEmpty(subPersonnelParam.getSubDegrees())){
            List<SubPersonnelDegree> subDegrees = subPersonnelParam.getSubDegrees();
            subDegrees.forEach(x->x.setFileId(subPersonnel.getId()));
            subPersonnelDegreeService.saveBatch(subDegrees);
        }
        //签约
        if(CollUtil.isNotEmpty(subPersonnelParam.getSubRegisters())){
            List<SubPersonnelRegister> subRegisters = subPersonnelParam.getSubRegisters();
            subRegisters.forEach(x->x.setFileId(subPersonnel.getId()));
            subPersonnelRegisterService.saveBatch(subRegisters);
        }
        //证书
        if(CollUtil.isNotEmpty(subPersonnelParam.getSubCerts())){
            List<SubPersonnelCert> subCerts = subPersonnelParam.getSubCerts();
            subCerts.forEach(x->x.setFileId(subPersonnel.getId()));
            subPersonnelCertService.saveBatch(subCerts);
        }
        //职称
        if(CollUtil.isNotEmpty(subPersonnelParam.getSubTitles())){
            List<SubPersonnelTitle> subTitles = subPersonnelParam.getSubTitles();
            subTitles.forEach(x->x.setFileId(subPersonnel.getId()));
            subPersonnelTitleService.saveBatch(subTitles);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubPersonnelParam subPersonnelParam) {
        SubPersonnel subPersonnel = this.queryEntity(subPersonnelParam.getId());
        BeanUtil.copyProperties(subPersonnelParam, subPersonnel);
        subPersonnel.setExtJson(JsonUtils.object2json(subPersonnelParam));
        this.updateById(subPersonnel);
        subPersonnelRegisterService.remove(new QueryWrapper<SubPersonnelRegister>().lambda().eq(SubPersonnelRegister::getFileId,subPersonnelParam.getId()));
        subPersonnelDegreeService.remove(new QueryWrapper<SubPersonnelDegree>().lambda().eq(SubPersonnelDegree::getFileId,subPersonnelParam.getId()));
        subPersonnelCertService.remove(new QueryWrapper<SubPersonnelCert>().lambda().eq(SubPersonnelCert::getFileId,subPersonnelParam.getId()));
        subPersonnelTitleService.remove(new QueryWrapper<SubPersonnelTitle>().lambda().eq(SubPersonnelTitle::getFileId,subPersonnelParam.getId()));
        //学历
        if(CollUtil.isNotEmpty(subPersonnelParam.getSubDegrees())){
            List<SubPersonnelDegree> subDegrees = subPersonnelParam.getSubDegrees();
            subDegrees.forEach(x->{
                x.setFileId(subPersonnel.getId());
                x.setId(null);
            });
            subPersonnelDegreeService.saveBatch(subDegrees);
        }
        //签约
        if(CollUtil.isNotEmpty(subPersonnelParam.getSubRegisters())){
            List<SubPersonnelRegister> subRegisters = subPersonnelParam.getSubRegisters();
            subRegisters.forEach(x->{
                x.setFileId(subPersonnel.getId());
                x.setId(null);
            });
            subPersonnelRegisterService.saveBatch(subRegisters);
        }
        //证书
        if(CollUtil.isNotEmpty(subPersonnelParam.getSubCerts())){
            List<SubPersonnelCert> subCerts = subPersonnelParam.getSubCerts();
            subCerts.forEach(x-> {
                x.setFileId(subPersonnel.getId());
                x.setId(null);
            });
            subPersonnelCertService.saveBatch(subCerts);
        }
        //职称
        if(CollUtil.isNotEmpty(subPersonnelParam.getSubTitles())){
            List<SubPersonnelTitle> subTitles = subPersonnelParam.getSubTitles();
            subTitles.forEach(x->{
                x.setFileId(subPersonnel.getId());
                x.setId(null);
            });
            subPersonnelTitleService.saveBatch(subTitles);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubPersonnelIdParam> subPersonnelIdParamList) {
        // 执行删除
        List<String> ids = CollStreamUtil.toList(subPersonnelIdParamList, SubPersonnelIdParam::getId);
        this.removeByIds(ids);
        if(CollUtil.isNotEmpty(ids)) {
            subPersonnelRegisterService.remove(new QueryWrapper<SubPersonnelRegister>().lambda().in(SubPersonnelRegister::getFileId, ids));
            subPersonnelDegreeService.remove(new QueryWrapper<SubPersonnelDegree>().lambda().in(SubPersonnelDegree::getFileId, ids));
            subPersonnelCertService.remove(new QueryWrapper<SubPersonnelCert>().lambda().in(SubPersonnelCert::getFileId, ids));
            subPersonnelTitleService.remove(new QueryWrapper<SubPersonnelTitle>().lambda().in(SubPersonnelTitle::getFileId, ids));
        }
    }

    @Override
    public SubPersonnelVo detail(SubPersonnelIdParam subPersonnelIdParam) {
        SubPersonnel subPersonnel = this.queryEntity(subPersonnelIdParam.getId());
        SubPersonnelVo subPersonnelVo = BeanUtil.copyProperties(subPersonnel,SubPersonnelVo.class);
        //入职司龄
        if (ObjectUtil.isNotEmpty(subPersonnelVo.getHireDate())) {
            subPersonnelVo.setEnterage(dayComparePrecise(new Date(), subPersonnelVo.getHireDate()));
        }
        //转正司龄
        if (ObjectUtil.isNotEmpty(subPersonnelVo.getPositiveTime())) {
            if(!subPersonnelVo.getPositiveTime().after(new Date())){
                subPersonnelVo.setPositiveAge(dayComparePrecise(new Date(), subPersonnelVo.getPositiveTime()));
            }
        }
        //年纪
        if (ObjectUtil.isNotEmpty(subPersonnelVo.getBirth())) {
            subPersonnelVo.setAge(Period.between(LocalDate.parse(DateUtil.formatDate(subPersonnelVo.getBirth())),LocalDate.parse(DateUtil.formatDate(new Date()))).getYears());
        }
        subPersonnelVo.setSubRegisters(subPersonnelRegisterService.list(new QueryWrapper<SubPersonnelRegister>().lambda().eq(SubPersonnelRegister::getFileId,subPersonnelVo.getId())));
        subPersonnelVo.setSubDegrees(subPersonnelDegreeService.list(new QueryWrapper<SubPersonnelDegree>().lambda().eq(SubPersonnelDegree::getFileId,subPersonnelVo.getId())));
        subPersonnelVo.setSubCerts(subPersonnelCertService.list(new QueryWrapper<SubPersonnelCert>().lambda().eq(SubPersonnelCert::getFileId,subPersonnelVo.getId())));
        subPersonnelVo.setSubTitles(subPersonnelTitleService.list(new QueryWrapper<SubPersonnelTitle>().lambda().eq(SubPersonnelTitle::getFileId,subPersonnelVo.getId())));
        return subPersonnelVo;
    }

    @Override
    public SubPersonnel queryEntity(String id) {
        SubPersonnel subPersonnel = this.getById(id);
        if(ObjectUtil.isEmpty(subPersonnel)) {
            throw new CommonException("HR-分公司-人事档案不存在，id值为：{}", id);
        }
        return subPersonnel;
    }

    private static String dayComparePrecise(Date date1, Date date2) {
        Period period = Period.between(LocalDate.parse(DateUtil.formatDate(date1)), LocalDate.parse(DateUtil.formatDate(date2)));
        return (period.getYears() + "年" +
                period.getMonths() + "月" +
                period.getDays() + "日").replaceAll("-","") ;
    }
}
