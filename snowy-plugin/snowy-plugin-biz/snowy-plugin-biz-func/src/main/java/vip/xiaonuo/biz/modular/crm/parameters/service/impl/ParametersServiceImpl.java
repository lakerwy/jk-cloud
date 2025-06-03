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
package vip.xiaonuo.biz.modular.crm.parameters.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.crm.parameters.entity.BizCrmRecPlan;
import vip.xiaonuo.biz.modular.crm.parameters.param.*;
import vip.xiaonuo.biz.modular.crm.parameters.service.BizCrmRecPlanService;
import vip.xiaonuo.biz.modular.crm.parameters.vo.ParametersInfoVo;
import vip.xiaonuo.common.enums.CommonSnFormatEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.parameters.entity.Parameters;
import vip.xiaonuo.biz.modular.crm.parameters.mapper.ParametersMapper;
import vip.xiaonuo.biz.modular.crm.parameters.service.ParametersService;
import vip.xiaonuo.common.util.CommonSnUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务表-CRM-合同参数Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 15:50
 **/
@DS("slave_1")
@Service
public class ParametersServiceImpl extends ServiceImpl<ParametersMapper, Parameters> implements ParametersService {

    @Resource
    private ParametersMapper parametersMapper;

    @Resource
    private BizCrmRecPlanService bizCrmRecPlanService;

    @Override
    public Page<Parameters> page(ParametersPageParam parametersPageParam) {
        QueryWrapper<Parameters> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(parametersPageParam.getCoveredArea())) {
            queryWrapper.ge(" CAST(COVERED_AREA AS SIGNED) ", parametersPageParam.getCoveredArea());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getBuildStorey())) {
            queryWrapper.ge(" CAST(BUILD_STOREY AS SIGNED) ", parametersPageParam.getBuildStorey());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getSteelSpan())) {
            queryWrapper.ge(" CAST(STEEL_SPAN AS SIGNED) ", parametersPageParam.getSteelSpan());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getWaterScale())) {
            queryWrapper.ge(" CAST(WATER_SCALE AS SIGNED) ", parametersPageParam.getWaterScale());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getWaterFar())) {
            queryWrapper.ge(" CAST(WATER_FAR AS SIGNED) ", parametersPageParam.getWaterFar());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getPdMax())) {
            queryWrapper.ge(" CAST(PD_MAX AS SIGNED) ", parametersPageParam.getPdMax());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getLandscapeArea())) {
            queryWrapper.ge(" CAST(LANDSCAPE_AREA AS SIGNED) ", parametersPageParam.getLandscapeArea());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getGarbageDScale())) {
            queryWrapper.ge(" CAST(GARBAGE_D_SCALE AS SIGNED) ", parametersPageParam.getGarbageDScale());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getPipeType())) {
            queryWrapper.lambda().eq(Parameters::getPipeType, parametersPageParam.getPipeType());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getPipeSize())) {
            queryWrapper.ge(" CAST(PIPE_SIZE AS SIGNED) ", parametersPageParam.getPipeSize());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getPipeSizeH())) {
            queryWrapper.ge(" CAST(PIPE_SIZE_H AS SIGNED) ", parametersPageParam.getPipeSizeH());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getBridgeSpan())) {
            queryWrapper.ge(" CAST(BRIDGE_SPAN AS SIGNED) ", parametersPageParam.getBridgeSpan());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getBridgeTLength())) {
            queryWrapper.ge(" CAST(BRIDGE_T_LENGTH AS SIGNED) ", parametersPageParam.getBridgeTLength());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getRoadLevels())) {
            queryWrapper.lambda().eq(Parameters::getRoadLevels, parametersPageParam.getRoadLevels());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getRoadTLength())) {
            queryWrapper.ge(" CAST(ROAD_T_LENGTH AS SIGNED) ", parametersPageParam.getRoadTLength());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getRoadRedWidth())) {
            queryWrapper.ge(" CAST(ROAD_RED_WIDTH AS SIGNED) ", parametersPageParam.getRoadRedWidth());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getTunnelSSize())) {
            queryWrapper.ge(" CAST(TUNNEL_S_SIZE AS SIGNED) ", parametersPageParam.getTunnelSSize());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getTunnelSSizeJ())) {
            queryWrapper.ge(" CAST(TUNNEL_S_SIZE_J AS SIGNED) ", parametersPageParam.getTunnelSSizeJ());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getPower())) {
            queryWrapper.ge(" CAST(POWER AS SIGNED) ", parametersPageParam.getPower());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getHeatsupplyArea())) {
            queryWrapper.ge(" CAST(HEATSUPPLY_AREA AS SIGNED) ", parametersPageParam.getHeatsupplyArea());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getHeatsorceSize())) {
            queryWrapper.ge(" CAST(HEATSORCE_SIZE AS SIGNED) ", parametersPageParam.getHeatsorceSize());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getPressurRate())) {
            queryWrapper.lambda().eq(Parameters::getPressurRate, parametersPageParam.getPressurRate());
        }
        if (ObjectUtil.isNotEmpty(parametersPageParam.getSearchKey())) {
            queryWrapper.lambda().and(q->q.like(Parameters::getSn, parametersPageParam.getSearchKey()).or()
                    .like(Parameters::getOpt, parametersPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(parametersPageParam.getSortField(), parametersPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(parametersPageParam.getSortOrder());
            queryWrapper.orderBy(true, parametersPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(parametersPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Parameters::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ParametersAddParam parametersAddParam) {
        Parameters parameters = BeanUtil.toBean(parametersAddParam, Parameters.class);
        //处理Sn编号
        Parameters tmp = this.getOne(new QueryWrapper<Parameters>().lambda().orderByDesc(Parameters::getCreateTime).last("limit 1"));
        String newSn = CommonSnUtil.GenerateSn(parametersAddParam.getSn(),tmp==null?"":tmp.getSn(), CommonSnFormatEnum.SNYYYYMM0001.getValue());
        parameters.setSn(newSn);
        parameters.setOpt(StpLoginUserUtil.getLoginUser().getName());
        parameters.setOptDate(new Date());
        this.save(parameters);
        if(CollUtil.isNotEmpty(parametersAddParam.getRecPlanList())){
            List<BizCrmRecPlan> items = BeanUtil.copyToList(parametersAddParam.getRecPlanList(), BizCrmRecPlan.class);
            items.forEach(x->{
                x.setParametersId(parameters.getId());
                x.setHetongId(parameters.getProjectId());
            });
            bizCrmRecPlanService.saveBatch(items);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ParametersEditParam parametersEditParam) {
        Parameters parameters = this.queryEntity(parametersEditParam.getId());
        BeanUtil.copyProperties(parametersEditParam, parameters);
        this.updateById(parameters);
        if(CollUtil.isNotEmpty(parametersEditParam.getRecPlanList())){
            //本次传入的数据
            List<BizCrmRecPlan> itemList = BeanUtil.copyToList(parametersEditParam.getRecPlanList(), BizCrmRecPlan.class);
            itemList.forEach(x->{
                x.setId(parameters.getId());
                x.setHetongId(parameters.getProjectId());
            });
            //获取历史数据
            List<BizCrmRecPlan> oldList = bizCrmRecPlanService.list(new QueryWrapper<BizCrmRecPlan>().lambda()
                    .eq(BizCrmRecPlan::getParametersId,parameters.getId()));
            List<String> oldIds = oldList.stream().map(BizCrmRecPlan::getId).collect(Collectors.toList());
            //待新增的
            List<BizCrmRecPlan> addList = itemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizCrmRecPlan> updateList = itemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(BizCrmRecPlan::getId).collect(Collectors.toList());
            List<BizCrmRecPlan> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizCrmRecPlanService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizCrmRecPlanService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizCrmRecPlanService.removeByIds(removeList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ParametersIdParam> parametersIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(parametersIdParamList, ParametersIdParam::getId));
    }

    @Override
    public Parameters detail(ParametersIdParam parametersIdParam) {
        return this.queryEntity(parametersIdParam.getId());
    }

    @Override
    public Parameters queryEntity(String id) {
        Parameters parameters = this.getById(id);
        if(ObjectUtil.isEmpty(parameters)) {
            throw new CommonException("业务表-CRM-合同参数不存在，id值为：{}", id);
        }
        return parameters;
    }

    @Override
    public IPage<ParametersInfoVo> pageList(ParametersInfoPageParam parametersInfoPageParam) {
        QueryWrapper<Parameters> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getCoveredArea())) {
            queryWrapper.ge(" CAST(a.COVERED_AREA AS SIGNED) ", parametersInfoPageParam.getCoveredArea());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getBuildStorey())) {
            queryWrapper.ge(" CAST(a.BUILD_STOREY AS SIGNED) ", parametersInfoPageParam.getBuildStorey());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getSteelSpan())) {
            queryWrapper.ge(" CAST(a.STEEL_SPAN AS SIGNED) ", parametersInfoPageParam.getSteelSpan());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getWaterScale())) {
            queryWrapper.ge(" CAST(a.WATER_SCALE AS SIGNED) ", parametersInfoPageParam.getWaterScale());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getWaterFar())) {
            queryWrapper.ge(" CAST(a.WATER_FAR AS SIGNED) ", parametersInfoPageParam.getWaterFar());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getPdMax())) {
            queryWrapper.ge(" CAST(a.PD_MAX AS SIGNED) ", parametersInfoPageParam.getPdMax());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getLandscapeArea())) {
            queryWrapper.ge(" CAST(a.LANDSCAPE_AREA AS SIGNED) ", parametersInfoPageParam.getLandscapeArea());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getGarbageDScale())) {
            queryWrapper.ge(" CAST(a.GARBAGE_D_SCALE AS SIGNED) ", parametersInfoPageParam.getGarbageDScale());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getPipeType())) {
            queryWrapper.eq("a.PIPE_TYPE", parametersInfoPageParam.getPipeType());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getPipeSize())) {
            queryWrapper.ge(" CAST(a.PIPE_SIZE AS SIGNED) ", parametersInfoPageParam.getPipeSize());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getPipeSizeH())) {
            queryWrapper.ge(" CAST(a.PIPE_SIZE_H AS SIGNED) ", parametersInfoPageParam.getPipeSizeH());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getBridgeSpan())) {
            queryWrapper.ge(" CAST(a.BRIDGE_SPAN AS SIGNED) ", parametersInfoPageParam.getBridgeSpan());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getBridgeTLength())) {
            queryWrapper.ge(" CAST(a.BRIDGE_T_LENGTH AS SIGNED) ", parametersInfoPageParam.getBridgeTLength());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getRoadLevels())) {
            queryWrapper.eq("a.ROAD_LEVELS", parametersInfoPageParam.getRoadLevels());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getRoadTLength())) {
            queryWrapper.ge(" CAST(a.ROAD_T_LENGTH AS SIGNED) ", parametersInfoPageParam.getRoadTLength());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getRoadRedWidth())) {
            queryWrapper.ge(" CAST(a.ROAD_RED_WIDTH AS SIGNED) ", parametersInfoPageParam.getRoadRedWidth());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getTunnelSSize())) {
            queryWrapper.ge(" CAST(a.TUNNEL_S_SIZE AS SIGNED) ", parametersInfoPageParam.getTunnelSSize());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getTunnelSSizeJ())) {
            queryWrapper.ge(" CAST(a.TUNNEL_S_SIZE_J AS SIGNED) ", parametersInfoPageParam.getTunnelSSizeJ());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getPower())) {
            queryWrapper.ge(" CAST(a.POWER AS SIGNED) ", parametersInfoPageParam.getPower());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getHeatsupplyArea())) {
            queryWrapper.ge(" CAST(a.HEATSUPPLY_AREA AS SIGNED) ", parametersInfoPageParam.getHeatsupplyArea());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getHeatsorceSize())) {
            queryWrapper.ge(" CAST(a.HEATSORCE_SIZE AS SIGNED) ", parametersInfoPageParam.getHeatsorceSize());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getPressurRate())) {
            queryWrapper.eq("a.PRESSUR_RATE", parametersInfoPageParam.getPressurRate());
        }
        queryWrapper.eq("a.DELETE_FLAG", "NOT_DELETE");

        /* 合同信息 */
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainSecondLevel())) {
            queryWrapper.eq("b.SECOND_LEVEL", parametersInfoPageParam.getMainSecondLevel());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainContractNo())) {
            queryWrapper.like("b.CONTRACT_NO", parametersInfoPageParam.getMainContractNo());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainProjectName())) {
            queryWrapper.like("b.PROJECT_NAME", parametersInfoPageParam.getMainProjectName());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainCounterparty())) {
            queryWrapper.like("b.COUNTERPARTY", parametersInfoPageParam.getMainCounterparty());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainSecondparty())) {
            queryWrapper.like("b.SECONDPARTY", parametersInfoPageParam.getMainSecondparty());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainCompanyOrDepartment())) {
            queryWrapper.like("b.COMPANY_OR_DEPARTMENT", parametersInfoPageParam.getMainCompanyOrDepartment());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainOwner())) {
            queryWrapper.like("b.OWNER", parametersInfoPageParam.getMainOwner());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainContractAmount())) {
            queryWrapper.ge(" CAST(b.CONTRACT_AMOUNT AS SIGNED) ", parametersInfoPageParam.getMainContractAmount());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainInvestScale())) {
            queryWrapper.ge(" CAST(b.INVEST_SCALE AS SIGNED) ", parametersInfoPageParam.getMainInvestScale());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainDescription())) {
            queryWrapper.like("b.DESCRIPTION", parametersInfoPageParam.getMainDescription());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainProjectLeader())) {
            queryWrapper.like("b.PROJECT_LEADER", parametersInfoPageParam.getMainProjectLeader());
        }

        if (ObjectUtil.isNotEmpty(parametersInfoPageParam.getSearchKey())) {
            queryWrapper.and(q->q.like("a.SN", parametersInfoPageParam.getSearchKey()).or()
                    .like("a.OPT", parametersInfoPageParam.getSearchKey()).or()
                    .like("b.SECOND_LEVEL", parametersInfoPageParam.getSearchKey()).or()
                    .like("b.CONTRACT_NO", parametersInfoPageParam.getSearchKey()).or()
                    .like("b.PROJECT_NAME", parametersInfoPageParam.getSearchKey()).or()
                    .like("b.COUNTERPARTY", parametersInfoPageParam.getSearchKey()).or()
                    .like("b.SECONDPARTY", parametersInfoPageParam.getSearchKey()).or()
                    .like("b.COMPANY_OR_DEPARTMENT", parametersInfoPageParam.getSearchKey()).or()
                    .like("b.DESCRIPTION", parametersInfoPageParam.getSearchKey()));
        }

        /* 排序 */
        if(ObjectUtil.isAllNotEmpty(parametersInfoPageParam.getSortField(), parametersInfoPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(parametersInfoPageParam.getSortOrder());
            queryWrapper.orderBy(true, parametersInfoPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(parametersInfoPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.SN");
        }

        return parametersMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public ParametersInfoVo detailById(ParametersIdParam parametersIdParam) {
        return parametersMapper.getDetailById(parametersIdParam.getId());
    }

    @Override
    public ParametersInfoVo detailByHetongId(ParametersHetongIdParam parametersHetongIdParam) {
        return parametersMapper.getDetailByHetongId(parametersHetongIdParam.getHetongId());
    }
}
