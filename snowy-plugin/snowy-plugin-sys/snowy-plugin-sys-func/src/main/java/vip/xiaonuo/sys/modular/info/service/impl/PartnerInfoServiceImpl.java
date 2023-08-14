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
package vip.xiaonuo.sys.modular.info.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.core.util.BizTableCommon;
import vip.xiaonuo.dbs.core.util.CommonUtils;
import vip.xiaonuo.sys.modular.info.entity.PartnerFee;
import vip.xiaonuo.sys.modular.info.entity.PartnerPayment;
import vip.xiaonuo.sys.modular.info.param.*;
import vip.xiaonuo.sys.modular.info.service.PartnerFeeService;
import vip.xiaonuo.sys.modular.info.entity.PartnerInfo;
import vip.xiaonuo.sys.modular.info.mapper.PartnerInfoMapper;
import vip.xiaonuo.sys.modular.info.service.PartnerInfoService;
import vip.xiaonuo.sys.modular.info.service.PartnerPaymentService;
import vip.xiaonuo.sys.modular.info.vo.PartnerInfoVo;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.enums.SysOrgCategoryEnum;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;
import vip.xiaonuo.sys.modular.record.entity.PartnerRecord;
import vip.xiaonuo.sys.modular.record.service.PartnerRecordService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 合作伙伴-基本信息Service接口实现类
 *
 * @author mayike
 * @date  2023/06/26 14:04
 **/
@Service
public class PartnerInfoServiceImpl extends ServiceImpl<PartnerInfoMapper, PartnerInfo> implements PartnerInfoService {

    @Resource
    private PartnerFeeService partnerFeeService;

    @Resource
    private PartnerRecordService partnerRecordService;

    @Resource
    private PartnerInfoMapper partnerInfoMapper;

    @Resource
    private SysOrgService orgService;

    @Resource
    private PartnerPaymentService partnerPaymentService;

    private final String PARENT_ID = "1666256671493107714";

    @Override
    public Page<PartnerInfo> page(PartnerInfoPageParam partnerInfoPageParam) {
        QueryWrapper<PartnerInfo> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(partnerInfoPageParam.getPartnerStatus())) {
            queryWrapper.lambda().like(PartnerInfo::getPartnerStatus, partnerInfoPageParam.getPartnerStatus());
        }
        if(ObjectUtil.isNotEmpty(partnerInfoPageParam.getPartnerLevel())) {
            queryWrapper.lambda().like(PartnerInfo::getPartnerLevel, partnerInfoPageParam.getPartnerLevel());
        }
        if(ObjectUtil.isNotEmpty(partnerInfoPageParam.getVitalLevel())) {
            queryWrapper.lambda().like(PartnerInfo::getVitalLevel, partnerInfoPageParam.getVitalLevel());
        }
        if(ObjectUtil.isAllNotEmpty(partnerInfoPageParam.getSortField(), partnerInfoPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(partnerInfoPageParam.getSortOrder());
            queryWrapper.orderBy(true, partnerInfoPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(partnerInfoPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PartnerInfo::getOrgId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PartnerInfoAddParam partnerInfoAddParam) {
        String name = partnerInfoAddParam.getName();
        SysOrg one = orgService.getOne(new LambdaQueryWrapper<SysOrg>().eq(SysOrg::getParentId, PARENT_ID).eq(SysOrg::getName, name));
        SysOrg sysOrg = new SysOrg();
        if(ObjectUtil.isNull(one)){
            sysOrg.setCategory(SysOrgCategoryEnum.PARTNER.name());
            sysOrg.setParentId(PARENT_ID);
            sysOrg.setSortCode(99);
            sysOrg.setName(name);
            orgService.save(sysOrg);
        }
        PartnerInfo partnerInfo = BeanUtil.toBean(partnerInfoAddParam, PartnerInfo.class);
        partnerInfo.setOrgId(sysOrg.getId());
        partnerInfo.setSn(CommonUtils.getTableSn(BizTableCommon.SYS_PARTNER_INFO));
        List<PartnerFeeAddParam> partnerFeeAddParams = partnerInfoAddParam.getPartnerFees();
        List<PartnerPaymentAddParam> partnerPaymentAddParams = partnerInfoAddParam.getPartnerPayments();
        this.save(partnerInfo);
        if(CollUtil.isNotEmpty(partnerFeeAddParams)){
            //partnerFees.forEach(partnerFee -> partnerFee.setOrgId(partnerInfo.getOrgId()));
            List<PartnerFee> partnerFees = partnerFeeAddParams.stream().map(partnerFeeAddParam -> BeanUtil.toBean(partnerFeeAddParam, PartnerFee.class)).collect(Collectors.toList());
            partnerFees.forEach(partnerFee -> partnerFee.setInfoId(partnerInfo.getId()));
            partnerFeeService.saveBatch(partnerFees);
        }
        if(CollUtil.isNotEmpty(partnerPaymentAddParams)){
            List<PartnerPayment> partnerPayments = partnerPaymentAddParams.stream().map(partnerPaymentAddParam -> BeanUtil.toBean(partnerPaymentAddParam, PartnerPayment.class)).collect(Collectors.toList());
            partnerPayments.forEach(partnerPayment -> partnerPayment.setInfoId(partnerInfo.getId()));
            partnerPaymentService.saveBatch(partnerPayments);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PartnerInfoEditParam partnerInfoEditParam) {
        PartnerInfo partnerInfo = this.getById(partnerInfoEditParam.getId());
        if(ObjectUtil.isEmpty(partnerInfo)){
            partnerInfo = new PartnerInfo();
        }
        BeanUtil.copyProperties(partnerInfoEditParam, partnerInfo);
        saveOrUpdate(partnerInfo);
        //this.updateById(partnerInfo);
        List<PartnerFee> partnerFees = partnerInfoEditParam.getPartnerFees();
        handlePartnerFees(partnerInfo, partnerFees);
        List<PartnerPayment> partnerPayments = partnerInfoEditParam.getPartnerPayments();
        handlePartnerPayments(partnerInfo,partnerPayments);
    }

    private void handlePartnerFees(PartnerInfo partnerInfo, List<PartnerFee> partnerFees) {
        QueryWrapper<PartnerFee> wrapper = new QueryWrapper<>();
        wrapper.eq("INFO_ID", partnerInfo.getId());
        List<PartnerFee> list = partnerFeeService.list(wrapper);
//        partnerFeeService.remove(wrapper);
        if(CollUtil.isNotEmpty(partnerFees)){
            PartnerInfo finalPartnerInfo = partnerInfo;
            partnerFees.forEach(partnerFee -> partnerFee.setInfoId(finalPartnerInfo.getId()));
//            不能使用     saveOrUpdateBatch     逻辑删除导致批量主键冲突
//            partnerFeeService.saveOrUpdateBatch(partnerFees);
            Set<String> ids = new HashSet<>();
            if(CollUtil.isNotEmpty(list)){
                ids = list.stream().map(PartnerFee::getId).collect(Collectors.toSet());
            }
            //List<PartnerFee> savePartnerFees = new ArrayList<>();
            List<PartnerFee> updatePartnerFees = new ArrayList<>();
            updatePartnerFees = partnerFees.stream().filter(partnerFee -> StrUtil.isNotBlank(partnerFee.getId())).collect(Collectors.toList());
            Set<String> collect = updatePartnerFees.stream().map(PartnerFee::getId).collect(Collectors.toSet());
            ids.removeAll(collect);
            partnerFees.removeAll(updatePartnerFees);
            if(CollUtil.isNotEmpty(updatePartnerFees)){
                partnerFeeService.updateBatchById(updatePartnerFees);
            }
            if(CollUtil.isNotEmpty(partnerFees)){
                partnerFeeService.saveBatch(partnerFees);
            }
            if(CollUtil.isNotEmpty(ids)){
                partnerFeeService.removeByIds(ids);
            }
        }
    }

    private void handlePartnerPayments(PartnerInfo partnerInfo, List<PartnerPayment> partnerPayments) {
        QueryWrapper<PartnerPayment> wrapper = new QueryWrapper<>();
        wrapper.eq("INFO_ID", partnerInfo.getId());
        List<PartnerPayment> list = partnerPaymentService.list(wrapper);
//        partnerFeeService.remove(wrapper);
        if(CollUtil.isNotEmpty(partnerPayments)){
            PartnerInfo finalPartnerInfo = partnerInfo;
            partnerPayments.forEach(partnerPayment -> partnerPayment.setInfoId(finalPartnerInfo.getId()));
//            不能使用     saveOrUpdateBatch     逻辑删除导致批量主键冲突
//            partnerFeeService.saveOrUpdateBatch(partnerFees);
            Set<String> ids = new HashSet<>();
            if(CollUtil.isNotEmpty(list)){
                ids = list.stream().map(PartnerPayment::getId).collect(Collectors.toSet());
            }
            //List<PartnerFee> savePartnerFees = new ArrayList<>();
            List<PartnerPayment> updatePartnerPayments = new ArrayList<>();
            updatePartnerPayments = partnerPayments.stream().filter(partnerPayment -> StrUtil.isNotBlank(partnerPayment.getId())).collect(Collectors.toList());
            Set<String> collect = updatePartnerPayments.stream().map(PartnerPayment::getId).collect(Collectors.toSet());
            ids.removeAll(collect);
            partnerPayments.removeAll(updatePartnerPayments);
            if(CollUtil.isNotEmpty(updatePartnerPayments)){
                partnerPaymentService.updateBatchById(updatePartnerPayments);
            }
            if(CollUtil.isNotEmpty(partnerPayments)){
                partnerPaymentService.saveBatch(partnerPayments);
            }
            if(CollUtil.isNotEmpty(ids)){
                partnerPaymentService.removeByIds(ids);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PartnerInfoIdParam> partnerInfoIdParamList) {
        // 执行删除
        List<String> list = CollStreamUtil.toList(partnerInfoIdParamList, PartnerInfoIdParam::getId);
        List<PartnerInfo> partnerInfos = listByIds(list);
        List<String> orgIds = CollStreamUtil.toList(partnerInfos, PartnerInfo::getOrgId);
        this.removeByIds(list);
        //List<String> list = CollStreamUtil.toList(partnerInfoIdParamList, PartnerInfoIdParam::getId);
        QueryWrapper<PartnerFee> wrapper = new QueryWrapper<>();
        wrapper.in("INFO_ID",list);
        partnerFeeService.remove(wrapper);
        QueryWrapper<PartnerRecord> recordQueryWrapper = new QueryWrapper<>();
        recordQueryWrapper.in("INFO_ID",list);
        partnerRecordService.remove(recordQueryWrapper);
        QueryWrapper<PartnerPayment> paymentQueryWrapper = new QueryWrapper<>();
        paymentQueryWrapper.in("INFO_ID",list);
        partnerPaymentService.remove(paymentQueryWrapper);
        orgService.removeByIds(orgIds);
    }

    @Override
    public PartnerInfoVo detail(PartnerInfoIdParam partnerInfoIdParam) {
        PartnerInfo partnerInfo = this.queryEntity(partnerInfoIdParam.getId());
        PartnerInfoVo vo = BeanUtil.toBean(partnerInfo, PartnerInfoVo.class);
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("INFO_ID",partnerInfo.getId());
        List<PartnerFee> partnerFees = partnerFeeService.listByMap(queryMap);
        List<PartnerRecord> partnerRecords = partnerRecordService.listByMap(queryMap);
        List<PartnerPayment> partnerPayments = partnerPaymentService.listByMap(queryMap);
        vo.setPartnerFees(partnerFees);
        vo.setPartnerRecords(partnerRecords);
        vo.setPartnerPayments(partnerPayments);
        return vo;
    }

    @Override
    public PartnerInfo queryEntity(String id) {
        PartnerInfo partnerInfo = this.getById(id);
        if(ObjectUtil.isEmpty(partnerInfo)) {
            throw new CommonException("合作伙伴-基本信息不存在，id值为：{}", id);
        }
        return partnerInfo;
    }

    @Override
    public PartnerInfoVo getInfoById(PartnerInfoIdParam partnerInfoIdParam) {
        PartnerInfoVo vo = partnerInfoMapper.infoById(partnerInfoIdParam.getId());
        if(ObjectUtil.isEmpty(vo)) {
           // throw new CommonException("合作伙伴-基本信息不存在，id值为：{}", partnerInfoIdParam.getOrgId());
            return null;
        }
        return vo;
    }

    @Override
    public IPage<PartnerInfoVo> pageList(PartnerInfoPageParam partnerInfoPageParam) {
        QueryWrapper<PartnerInfo> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(partnerInfoPageParam.getPartnerStatus())) {
            queryWrapper.like("pi.PARTNER_STATUS", partnerInfoPageParam.getPartnerStatus());
        }
//        if(ObjectUtil.isNotEmpty(partnerInfoPageParam.getPartnerLevel())) {
//            queryWrapper.like("pi.PARTNER_STATUS", partnerInfoPageParam.getPartnerLevel());
//        }
        if(ObjectUtil.isNotEmpty(partnerInfoPageParam.getVitalLevel())) {
            queryWrapper.like("pi.PARTNER_LEVEL", partnerInfoPageParam.getVitalLevel());
        }
        if(ObjectUtil.isNotEmpty(partnerInfoPageParam.getSearchKey())){
            queryWrapper.or(wrapper ->wrapper.like("pi.NAME",partnerInfoPageParam.getSearchKey()));
        }
//        queryWrapper.eq("so.CATEGORY", SysOrgCategoryEnum.PARTNER.getValue());
//        queryWrapper.eq("so.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("pi.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isAllNotEmpty(partnerInfoPageParam.getSortField(), partnerInfoPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(partnerInfoPageParam.getSortOrder());
            queryWrapper.orderBy(true, partnerInfoPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(partnerInfoPageParam.getSortField()));
        } else {
//            queryWrapper.lambda().orderByAsc(PartnerInfo::getOrgId);
            queryWrapper.orderByDesc("pi.SN");
        }
        return partnerInfoMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public PartnerInfoVo getInfoByOrgId(PartnerInfoOrgIdParam partnerInfoOrgIdParam) {
       // PartnerInfo partnerInfo = this.queryEntity(partnerInfoIdParam.getId());
        QueryWrapper<PartnerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PartnerInfo::getOrgId, partnerInfoOrgIdParam.getOrgId());
        PartnerInfo partnerInfo = getOne(queryWrapper);
        if(ObjectUtil.isNotEmpty(partnerInfo)) {
            PartnerInfoVo vo = BeanUtil.toBean(partnerInfo, PartnerInfoVo.class);
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("INFO_ID", partnerInfo.getId());
            List<PartnerFee> partnerFees = partnerFeeService.listByMap(queryMap);
            List<PartnerRecord> partnerRecords = partnerRecordService.listByMap(queryMap);
            List<PartnerPayment> partnerPayments = partnerPaymentService.listByMap(queryMap);
            vo.setPartnerFees(partnerFees);
            vo.setPartnerRecords(partnerRecords);
            vo.setPartnerPayments(partnerPayments);
            return vo;
        }
        return null;
    }
}
