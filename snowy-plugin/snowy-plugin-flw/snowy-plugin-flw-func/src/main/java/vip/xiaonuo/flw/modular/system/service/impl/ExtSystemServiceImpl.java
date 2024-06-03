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
package vip.xiaonuo.flw.modular.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.flw.modular.system.entity.ExtSystem;
import vip.xiaonuo.flw.modular.system.mapper.ExtSystemMapper;
import vip.xiaonuo.flw.modular.system.param.ExtSystemIdParam;
import vip.xiaonuo.flw.modular.system.param.ExtSystemPageParam;
import vip.xiaonuo.flw.modular.system.service.ExtSystemService;
import vip.xiaonuo.flw.modular.system.vo.ExtSystemApplyVo;
import vip.xiaonuo.flw.modular.system.vo.ExtSystemPlanVo;

/**
 * 各系统扩展数据表Service接口实现类
 *
 * @author hxw
 * @date  2024/05/30 16:01
 **/
@Service
public class ExtSystemServiceImpl extends ServiceImpl<ExtSystemMapper, ExtSystem> implements ExtSystemService {

    @Override
    public Page<ExtSystem> page(ExtSystemPageParam extSystemPageParam) {
        QueryWrapper<ExtSystem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(extSystemPageParam.getSortField(), extSystemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(extSystemPageParam.getSortOrder());
            queryWrapper.orderBy(true, extSystemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(extSystemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ExtSystem::getId);
        }

        //默认查询已审核
        queryWrapper.apply("JSON_EXTRACT(FROM_EXT_JSON, '$.status') = '1'");

        if(ObjectUtil.isNotEmpty(extSystemPageParam.getStartTime())){
            queryWrapper.lambda().ge(ExtSystem::getCreateTime,extSystemPageParam.getStartTime());
        }
        if(ObjectUtil.isNotEmpty(extSystemPageParam.getEndTime())){
            queryWrapper.lambda().le(ExtSystem::getCreateTime,extSystemPageParam.getEndTime());
        }
        //查询计划名称
        if(ObjectUtil.isNotEmpty(extSystemPageParam.getSearchKey())) {
            queryWrapper.apply("JSON_EXTRACT(FROM_EXT_JSON, '$.planName') like " + "'%"+ extSystemPageParam.getSearchKey() + "%'");
        }

        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public ExtSystemPlanVo planDetail(ExtSystemIdParam extSystemIdParam) {
        return this.queryEntity(extSystemIdParam.getId());
    }

    @Override
    public ExtSystemPlanVo queryEntity(String id) {
        ExtSystem extSystem = this.getOne(new QueryWrapper<ExtSystem>().lambda().apply("JSON_EXTRACT(FROM_EXT_JSON, '$.planId') =" + "'" + id + "'"));
        if(ObjectUtil.isEmpty(extSystem)) {
            throw new CommonException("各系统扩展数据表不存在，id值为：{}", id);
        }
        ExtSystemPlanVo extSystemPlanVo = new ExtSystemPlanVo();
        if(ObjectUtil.isNotEmpty(extSystem.getFromExtJson())){
            JSONObject jsonObject = JSONUtil.parseObj(extSystem.getFromExtJson());
            BeanUtil.copyProperties(jsonObject, extSystemPlanVo);
        }
        return extSystemPlanVo;
    }

    @Override
    public ExtSystemApplyVo applyDetail(ExtSystemIdParam extSystemIdParam) {
        ExtSystem extSystem = this.getOne(new QueryWrapper<ExtSystem>().lambda().apply("JSON_EXTRACT(FROM_EXT_JSON, '$.planId') =" + "'" + extSystemIdParam.getId() + "'"));
        if(ObjectUtil.isEmpty(extSystem)) {
            throw new CommonException("各系统扩展数据表不存在，id值为：{}", extSystemIdParam.getId());
        }
        ExtSystemApplyVo extSystemApplyVo = new ExtSystemApplyVo();
        if(ObjectUtil.isNotEmpty(extSystem.getFromExtJson())){
            JSONObject jsonObject = JSONUtil.parseObj(extSystem.getFromExtJson());
            BeanUtil.copyProperties(jsonObject, extSystemApplyVo);
        }
        return extSystemApplyVo;
    }

}
