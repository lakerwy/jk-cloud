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
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.crm.parameters.param.*;
import vip.xiaonuo.biz.modular.crm.parameters.vo.ParametersInfoVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.parameters.entity.Parameters;
import vip.xiaonuo.biz.modular.crm.parameters.mapper.ParametersMapper;
import vip.xiaonuo.biz.modular.crm.parameters.service.ParametersService;

import javax.annotation.Resource;
import java.util.List;

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
    @Override
    public Page<Parameters> page(ParametersPageParam parametersPageParam) {
        QueryWrapper<Parameters> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(parametersPageParam.getCoveredArea())) {
            queryWrapper.lambda().like(Parameters::getCoveredArea, parametersPageParam.getCoveredArea());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getBuildStorey())) {
            queryWrapper.lambda().like(Parameters::getBuildStorey, parametersPageParam.getBuildStorey());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getSteelSpan())) {
            queryWrapper.lambda().like(Parameters::getSteelSpan, parametersPageParam.getSteelSpan());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getWaterScale())) {
            queryWrapper.lambda().like(Parameters::getWaterScale, parametersPageParam.getWaterScale());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getWaterFar())) {
            queryWrapper.lambda().like(Parameters::getWaterFar, parametersPageParam.getWaterFar());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getPdMax())) {
            queryWrapper.lambda().like(Parameters::getPdMax, parametersPageParam.getPdMax());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getLandscapeArea())) {
            queryWrapper.lambda().like(Parameters::getLandscapeArea, parametersPageParam.getLandscapeArea());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getGarbageDScale())) {
            queryWrapper.lambda().like(Parameters::getGarbageDScale, parametersPageParam.getGarbageDScale());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getPipeType())) {
            queryWrapper.lambda().eq(Parameters::getPipeType, parametersPageParam.getPipeType());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getPipeSize())) {
            queryWrapper.lambda().like(Parameters::getPipeSize, parametersPageParam.getPipeSize());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getBridgeSpan())) {
            queryWrapper.lambda().like(Parameters::getBridgeSpan, parametersPageParam.getBridgeSpan());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getBridgeTLength())) {
            queryWrapper.lambda().like(Parameters::getBridgeTLength, parametersPageParam.getBridgeTLength());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getRoadLevels())) {
            queryWrapper.lambda().eq(Parameters::getRoadLevels, parametersPageParam.getRoadLevels());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getRoadTLength())) {
            queryWrapper.lambda().like(Parameters::getRoadTLength, parametersPageParam.getRoadTLength());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getRoadRedWidth())) {
            queryWrapper.lambda().like(Parameters::getRoadRedWidth, parametersPageParam.getRoadRedWidth());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getTunnelSSize())) {
            queryWrapper.lambda().like(Parameters::getTunnelSSize, parametersPageParam.getTunnelSSize());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getPower())) {
            queryWrapper.lambda().like(Parameters::getPower, parametersPageParam.getPower());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getHeatsupplyArea())) {
            queryWrapper.lambda().like(Parameters::getHeatsupplyArea, parametersPageParam.getHeatsupplyArea());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getHeatsorceSize())) {
            queryWrapper.lambda().like(Parameters::getHeatsorceSize, parametersPageParam.getHeatsorceSize());
        }
        if(ObjectUtil.isNotEmpty(parametersPageParam.getPressurRate())) {
            queryWrapper.lambda().eq(Parameters::getPressurRate, parametersPageParam.getPressurRate());
        }
        if(ObjectUtil.isAllNotEmpty(parametersPageParam.getSortField(), parametersPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(parametersPageParam.getSortOrder());
            queryWrapper.orderBy(true, parametersPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(parametersPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Parameters::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ParametersAddParam parametersAddParam) {
        Parameters parameters = BeanUtil.toBean(parametersAddParam, Parameters.class);
        this.save(parameters);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ParametersEditParam parametersEditParam) {
        Parameters parameters = this.queryEntity(parametersEditParam.getId());
        BeanUtil.copyProperties(parametersEditParam, parameters);
        this.updateById(parameters);
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
            queryWrapper.like("a.COVERED_AREA", parametersInfoPageParam.getCoveredArea());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getBuildStorey())) {
            queryWrapper.like("a.BUILD_STOREY", parametersInfoPageParam.getBuildStorey());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getSteelSpan())) {
            queryWrapper.like("a.STEEL_SPAN", parametersInfoPageParam.getSteelSpan());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getWaterScale())) {
            queryWrapper.like("a.WATER_SCALE", parametersInfoPageParam.getWaterScale());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getWaterFar())) {
            queryWrapper.like("a.WATER_FAR", parametersInfoPageParam.getWaterFar());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getPdMax())) {
            queryWrapper.like("a.PD_MAX", parametersInfoPageParam.getPdMax());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getLandscapeArea())) {
            queryWrapper.like("a.LANDSCAPE_AREA", parametersInfoPageParam.getLandscapeArea());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getGarbageDScale())) {
            queryWrapper.like("a.GARBAGE_D_SCALE", parametersInfoPageParam.getGarbageDScale());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getPipeType())) {
            queryWrapper.eq("a.PIPE_TYPE", parametersInfoPageParam.getPipeType());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getPipeSize())) {
            queryWrapper.like("a.PIPE_SIZE", parametersInfoPageParam.getPipeSize());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getBridgeSpan())) {
            queryWrapper.like("a.BRIDGE_SPAN", parametersInfoPageParam.getBridgeSpan());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getBridgeTLength())) {
            queryWrapper.like("a.BRIDGE_T_LENGTH", parametersInfoPageParam.getBridgeTLength());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getRoadLevels())) {
            queryWrapper.eq("a.ROAD_LEVELS", parametersInfoPageParam.getRoadLevels());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getRoadTLength())) {
            queryWrapper.like("a.ROAD_T_LENGTH", parametersInfoPageParam.getRoadTLength());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getRoadRedWidth())) {
            queryWrapper.like("a.ROAD_RED_WIDTH", parametersInfoPageParam.getRoadRedWidth());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getTunnelSSize())) {
            queryWrapper.like("a.TUNNEL_S_SIZE", parametersInfoPageParam.getTunnelSSize());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getPower())) {
            queryWrapper.like("a.POWER", parametersInfoPageParam.getPower());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getHeatsupplyArea())) {
            queryWrapper.like("a.HEATSUPPLY_AREA", parametersInfoPageParam.getHeatsupplyArea());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getHeatsorceSize())) {
            queryWrapper.like("a.HEATSORCE_SIZE", parametersInfoPageParam.getHeatsorceSize());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getPressurRate())) {
            queryWrapper.eq("a.PRESSUR_RATE", parametersInfoPageParam.getPressurRate());
        }
        queryWrapper.eq("a.DELETE_FLAG", "NOT_DELETE");

        /* 合同信息 */
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainSecondLevel())) {
            queryWrapper.eq("b.mainSecondLevel", parametersInfoPageParam.getMainSecondLevel());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainContractNo())) {
            queryWrapper.like("b.mainContractNo", parametersInfoPageParam.getMainContractNo());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainProjectName())) {
            queryWrapper.like("b.mainProjectName", parametersInfoPageParam.getMainProjectName());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainCounterparty())) {
            queryWrapper.like("b.mainCounterparty", parametersInfoPageParam.getMainCounterparty());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainSecondparty())) {
            queryWrapper.like("b.mainSecondparty", parametersInfoPageParam.getMainSecondparty());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainCompanyOrDepartment())) {
            queryWrapper.like("b.mainCompanyOrDepartment", parametersInfoPageParam.getMainCompanyOrDepartment());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainOwner())) {
            queryWrapper.like("b.mainOwner", parametersInfoPageParam.getMainOwner());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainContractAmount())) {
            queryWrapper.eq("b.mainContractAmount", parametersInfoPageParam.getMainContractAmount());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainInvestScale())) {
            queryWrapper.like("b.mainInvestScale", parametersInfoPageParam.getMainInvestScale());
        }
        if(ObjectUtil.isNotEmpty(parametersInfoPageParam.getMainDescription())) {
            queryWrapper.like("b.mainDescription", parametersInfoPageParam.getMainDescription());
        }

        /* 排序 */
        if(ObjectUtil.isAllNotEmpty(parametersInfoPageParam.getSortField(), parametersInfoPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(parametersInfoPageParam.getSortOrder());
            queryWrapper.orderBy(true, parametersInfoPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(parametersInfoPageParam.getSortField()));
        } else {
            queryWrapper.orderByAsc("a.ID");
        }

        return parametersMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }
}
