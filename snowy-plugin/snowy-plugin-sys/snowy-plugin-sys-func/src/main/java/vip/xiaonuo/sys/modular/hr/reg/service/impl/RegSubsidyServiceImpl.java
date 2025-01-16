package vip.xiaonuo.sys.modular.hr.reg.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import vip.xiaonuo.sys.modular.hr.hrCenter.entity.PersonnelFileTitle;
import vip.xiaonuo.sys.modular.hr.hrCenter.service.PersonnelFileTitleService;
import vip.xiaonuo.sys.modular.hr.reg.entity.RegSubsidy;
import vip.xiaonuo.sys.modular.hr.reg.entity.RegSubsidyResult;
import vip.xiaonuo.sys.modular.hr.reg.mapper.RegSubsidyMapper;
import vip.xiaonuo.sys.modular.hr.reg.param.*;
import vip.xiaonuo.sys.modular.hr.reg.service.RegSubsidyResultService;
import vip.xiaonuo.sys.modular.hr.reg.service.RegSubsidyService;
import vip.xiaonuo.sys.modular.hr.reg.vo.PersonnelInfoVo;
import vip.xiaonuo.sys.modular.hr.reg.vo.RegCertVo;
import vip.xiaonuo.sys.modular.hr.salary.util.ComputeUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static vip.xiaonuo.common.consts.CacheConstant.ORG_FIRST_CODE_CACHE_KEY;

/**
 * 注册补贴（新版）-审批流Service接口实现类
 *
 * @author yong.ye
 * @date  2025/01/15 09:07
 **/
@Service
public class RegSubsidyServiceImpl extends ServiceImpl<RegSubsidyMapper, RegSubsidy> implements RegSubsidyService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private RegSubsidyResultService regSubsidyResultService;

    @Resource
    private RegSubsidyMapper regSubsidyMapper;

    @Resource
    PersonnelFileTitleService personnelFileTitleService;

    @Override
    public Page<RegSubsidy> page(RegSubsidyPageParam regSubsidyPageParam) {
        QueryWrapper<RegSubsidy> queryWrapper = new QueryWrapper<>();
        //审批中，审批通过
        queryWrapper.lambda().in(RegSubsidy::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(regSubsidyPageParam.getName())) {
            queryWrapper.lambda().like(RegSubsidy::getName, regSubsidyPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(RegSubsidy::getIdCardNumber, regSubsidyPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyPageParam.getFirstOrgName())) {
            queryWrapper.lambda().like(RegSubsidy::getFirstOrgName, regSubsidyPageParam.getFirstOrgName());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyPageParam.getSecondOrgName())) {
            queryWrapper.lambda().like(RegSubsidy::getSecondOrgName, regSubsidyPageParam.getSecondOrgName());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyPageParam.getStartHireDate()) && ObjectUtil.isNotEmpty(regSubsidyPageParam.getEndHireDate())) {
            queryWrapper.lambda().between(RegSubsidy::getHireDate, regSubsidyPageParam.getStartHireDate(), regSubsidyPageParam.getEndHireDate());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyPageParam.getCollege())) {
            queryWrapper.lambda().like(RegSubsidy::getCollege, regSubsidyPageParam.getCollege());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyPageParam.getMajor())) {
            queryWrapper.lambda().like(RegSubsidy::getMajor, regSubsidyPageParam.getMajor());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyPageParam.getCertLevel())) {
            queryWrapper.lambda().eq(RegSubsidy::getCertLevel, regSubsidyPageParam.getCertLevel());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyPageParam.getCertMajor())) {
            queryWrapper.lambda().like(RegSubsidy::getCertMajor, regSubsidyPageParam.getCertMajor());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyPageParam.getSn())) {
            queryWrapper.lambda().like(RegSubsidy::getSn, regSubsidyPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyPageParam.getStatus())) {
            queryWrapper.lambda().eq(RegSubsidy::getStatus, regSubsidyPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(RegSubsidy::getSn,regSubsidyPageParam.getSearchKey())
                    .or().like(RegSubsidy::getName,regSubsidyPageParam.getSearchKey())
                    .or().like(RegSubsidy::getIdCardNumber,regSubsidyPageParam.getSearchKey())
                    .or().like(RegSubsidy::getFirstOrgName,regSubsidyPageParam.getSearchKey())
                    .or().like(RegSubsidy::getSecondOrgName,regSubsidyPageParam.getSearchKey())
                    .or().like(RegSubsidy::getCertMajor,regSubsidyPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(regSubsidyPageParam.getSortField(), regSubsidyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(regSubsidyPageParam.getSortOrder());
            queryWrapper.orderBy(true, regSubsidyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(regSubsidyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(RegSubsidy::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(RegSubsidyAddParam regSubsidyAddParam) {
        RegSubsidy regSubsidy = BeanUtil.toBean(regSubsidyAddParam, RegSubsidy.class);
        //走审批流程
        String processInstanceId = regSubsidyAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            regSubsidy.setId(id);
            regSubsidy.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            regSubsidy.setModelId(modelId);
            regSubsidy.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.REG_SUBSIDY.getBizTable(),id,processInstanceId,dbName);
        }else{
            regSubsidy.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(regSubsidy);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(RegSubsidyEditParam regSubsidyEditParam) {
        String id = regSubsidyEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(regSubsidyEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(regSubsidyEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                regSubsidyEditParam.setId(id);
            }
        }
        RegSubsidy regSubsidy = this.queryEntity(regSubsidyEditParam.getId());
        BeanUtil.copyProperties(regSubsidyEditParam, regSubsidy);
        this.updateById(regSubsidy);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<RegSubsidyIdParam> regSubsidyIdParamList) {
        // 执行删除
        List<String> ids = CollStreamUtil.toList(regSubsidyIdParamList, RegSubsidyIdParam::getId);
        this.removeByIds(ids);
        if(CollUtil.isNotEmpty(ids)){
            regSubsidyResultService.remove(new LambdaQueryWrapper<RegSubsidyResult>().in(RegSubsidyResult::getRegSubsidyId,ids));
        }
    }

    @Override
    public RegSubsidy detail(RegSubsidyIdParam regSubsidyIdParam) {
        return this.queryEntity(regSubsidyIdParam.getId());
    }

    @Override
    public RegSubsidy queryEntity(String id) {
        RegSubsidy regSubsidy = this.getById(id);
        if(ObjectUtil.isEmpty(regSubsidy)) {
            throw new CommonException("注册补贴（新版）-审批流不存在，id值为：{}", id);
        }
        return regSubsidy;
    }

    @Override
    public Boolean check(RegParam regParam) {
        QueryWrapper<RegSubsidy> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RegSubsidy::getStatus,FlwStatusEnum.ACTIVE_STATUS.getValue());
        queryWrapper.lambda().eq(RegSubsidy::getPersonnelId, regParam.getPersonnelId());
        List<RegSubsidy> result = this.list(queryWrapper);
        return result.size() > 0 ;
    }

    @Override
    public List<RegCertVo> certList(RegParam regParam) {
        //先判断是否首次申请
        QueryWrapper<RegSubsidy> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(RegSubsidy::getStatus,FlwStatusEnum.ACTIVE_STATUS.getValue(),FlwStatusEnum.COMPLETED_STATUS.getValue());
        queryWrapper.lambda().eq(RegSubsidy::getPersonnelId, regParam.getPersonnelId());
        List<RegSubsidy> result = this.list(queryWrapper);
        if(CollUtil.isNotEmpty(result)){
            return null;
        }
        return regSubsidyMapper.getCertList(regParam.getPersonnelId());
    }

    @Override
    public List<PersonnelInfoVo> personnelList(PersonnelParam personnelParam) {
        String userType_ZB = "总部";
        List<PersonnelInfoVo> result = regSubsidyMapper.getPersonnelList(personnelParam);
       //处理特殊字段
        if(CollUtil.isNotEmpty(result)){
            //总部
            List<String> zbIds = result.stream().filter(x->userType_ZB.equals(x.getUserType())).map(x->x.getId()).distinct().collect(Collectors.toList());
            List<PersonnelFileTitle> personnelFileTitleList = new ArrayList<>();
            if(CollUtil.isNotEmpty(zbIds)) {
                personnelFileTitleList = personnelFileTitleService.list(new LambdaQueryWrapper<PersonnelFileTitle>().in(PersonnelFileTitle::getFileId, zbIds));
            }
            //处理数据
            for (PersonnelInfoVo item : result) {
                if(userType_ZB.equals(item.getUserType())){
                    PersonnelFileTitle tmpTitle = personnelFileTitleList.stream().filter(x -> item.getId().equals(x.getFileId())).findFirst().orElse(null);
                    if(ObjectUtil.isNotEmpty(tmpTitle)) {
                        item.setCertLevel(tmpTitle.getTitleLever());
                        item.setCertMajor(tmpTitle.getTitleProfession());
                    }
                }
                //一级部门
                if (ObjectUtil.isNotEmpty(item.getOrgId())) {
                    item.setFirstOrgId(ComputeUtil.objToString(commonCacheOperator.get(ORG_FIRST_CODE_CACHE_KEY + "orgId" + item.getOrgId())));
                    item.setFirstOrgName(ComputeUtil.objToString(commonCacheOperator.get(ORG_FIRST_CODE_CACHE_KEY + "orgName" + item.getOrgId())));
                }
            }
        }
        return result;
    }
}
