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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.info.entity.PartnerFee;
import vip.xiaonuo.sys.modular.info.param.*;
import vip.xiaonuo.sys.modular.info.service.PartnerFeeService;
import vip.xiaonuo.sys.modular.info.entity.PartnerInfo;
import vip.xiaonuo.sys.modular.info.mapper.PartnerInfoMapper;
import vip.xiaonuo.sys.modular.info.service.PartnerInfoService;
import vip.xiaonuo.sys.modular.info.vo.PartnerInfoVo;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.enums.SysOrgCategoryEnum;
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
        PartnerInfo partnerInfo = BeanUtil.toBean(partnerInfoAddParam, PartnerInfo.class);
        List<PartnerFeeAddParam> partnerFeeAddParams = partnerInfoAddParam.getPartnerFees();
        this.save(partnerInfo);
        if(CollUtil.isNotEmpty(partnerFeeAddParams)){
            //partnerFees.forEach(partnerFee -> partnerFee.setOrgId(partnerInfo.getOrgId()));
            List<PartnerFee> partnerFees = partnerFeeAddParams.stream().map(partnerFeeAddParam -> BeanUtil.toBean(partnerFeeAddParam, PartnerFee.class)).collect(Collectors.toList());
            partnerFees.forEach(partnerFee -> partnerFee.setOrgId(partnerInfo.getOrgId()));
            partnerFeeService.saveBatch(partnerFees);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PartnerInfoEditParam partnerInfoEditParam) {
        PartnerInfo partnerInfo = this.queryEntity(partnerInfoEditParam.getOrgId());
        BeanUtil.copyProperties(partnerInfoEditParam, partnerInfo);
        this.updateById(partnerInfo);
        List<PartnerFee> partnerFees = partnerInfoEditParam.getPartnerFees();
        QueryWrapper<PartnerFee> wrapper = new QueryWrapper<>();
        wrapper.eq("ORG_ID",partnerInfo.getOrgId());
       List<PartnerFee> list = partnerFeeService.list(wrapper);
//        partnerFeeService.remove(wrapper);
        if(CollUtil.isNotEmpty(partnerFees)){
            partnerFees.forEach(partnerFee -> partnerFee.setOrgId(partnerInfo.getOrgId()));
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
//            partnerFees.forEach(partnerFee -> {
//                if(StrUtil.isNotBlank(partnerFee.getId())){
//                    savePartnerFees.
//                }
//            });

        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PartnerInfoIdParam> partnerInfoIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(partnerInfoIdParamList, PartnerInfoIdParam::getOrgId));
        List<String> list = CollStreamUtil.toList(partnerInfoIdParamList, PartnerInfoIdParam::getOrgId);
        QueryWrapper<PartnerFee> wrapper = new QueryWrapper<>();
        wrapper.in("ORG_ID",list);
        partnerFeeService.remove(wrapper);
        QueryWrapper<PartnerRecord> recordQueryWrapper = new QueryWrapper<>();
        recordQueryWrapper.in("ORG_ID",list);
        partnerRecordService.remove(recordQueryWrapper);
    }

    @Override
    public PartnerInfoVo detail(PartnerInfoIdParam partnerInfoIdParam) {
        PartnerInfo partnerInfo = this.queryEntity(partnerInfoIdParam.getOrgId());
        PartnerInfoVo vo = BeanUtil.toBean(partnerInfo, PartnerInfoVo.class);
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("ORG_ID",partnerInfo.getOrgId());
        List<PartnerFee> partnerFees = partnerFeeService.listByMap(queryMap);
        List<PartnerRecord> partnerRecords = partnerRecordService.listByMap(queryMap);
        vo.setPartnerFees(partnerFees);
        vo.setPartnerRecords(partnerRecords);
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
        PartnerInfoVo vo = partnerInfoMapper.infoById(partnerInfoIdParam.getOrgId());
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
        if(ObjectUtil.isNotEmpty(partnerInfoPageParam.getPartnerLevel())) {
            queryWrapper.like("pi.PARTNER_STATUS", partnerInfoPageParam.getPartnerLevel());
        }
        if(ObjectUtil.isNotEmpty(partnerInfoPageParam.getVitalLevel())) {
            queryWrapper.like("pi.PARTNER_LEVEL", partnerInfoPageParam.getVitalLevel());
        }
        queryWrapper.eq("so.CATEGORY", SysOrgCategoryEnum.PARTNER.getValue());
        if(ObjectUtil.isAllNotEmpty(partnerInfoPageParam.getSortField(), partnerInfoPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(partnerInfoPageParam.getSortOrder());
            queryWrapper.orderBy(true, partnerInfoPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(partnerInfoPageParam.getSortField()));
        } else {
//            queryWrapper.lambda().orderByAsc(PartnerInfo::getOrgId);
            queryWrapper.orderByAsc("so.ID");
        }
        return partnerInfoMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }
}
