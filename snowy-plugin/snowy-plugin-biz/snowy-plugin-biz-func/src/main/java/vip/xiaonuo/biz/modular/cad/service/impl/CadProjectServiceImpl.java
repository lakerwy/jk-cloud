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
package vip.xiaonuo.biz.modular.cad.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.cad.vo.CadProjectVo;
import vip.xiaonuo.biz.modular.cad.vo.CadSubItemVo;
import vip.xiaonuo.biz.modular.cad.vo.StageSubItemVo;
import vip.xiaonuo.biz.modular.cad.vo.SubItemUserVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cad.entity.CadProject;
import vip.xiaonuo.biz.modular.cad.mapper.CadProjectMapper;
import vip.xiaonuo.biz.modular.cad.param.CadProjectAddParam;
import vip.xiaonuo.biz.modular.cad.param.CadProjectEditParam;
import vip.xiaonuo.biz.modular.cad.param.CadProjectIdParam;
import vip.xiaonuo.biz.modular.cad.param.CadProjectPageParam;
import vip.xiaonuo.biz.modular.cad.service.CadProjectService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CAD-专业表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/29 10:36
 **/
@DS("slave_1")
@Service
public class CadProjectServiceImpl extends ServiceImpl<CadProjectMapper, CadProject> implements CadProjectService {

    @Resource
    private CadProjectMapper cadProjectMapper;
    @Override
    public Page<CadProjectVo> page(CadProjectPageParam cadProjectPageParam) {
        QueryWrapper<CadProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(cadProjectPageParam.getNo())) {
            queryWrapper.like("a.NO", cadProjectPageParam.getNo());
        }
        if(ObjectUtil.isNotEmpty(cadProjectPageParam.getName())) {
            queryWrapper.like("a.NAME", cadProjectPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(cadProjectPageParam.getManager())) {
            queryWrapper.like("b.NAME", cadProjectPageParam.getManager());
        }
        if(ObjectUtil.isNotEmpty(cadProjectPageParam.getUnit())) {
            queryWrapper.like("a.UNIT", cadProjectPageParam.getUnit());
        }
        if(ObjectUtil.isNotEmpty(cadProjectPageParam.getAddress())) {
            queryWrapper.like("a.ADDRESS", cadProjectPageParam.getAddress());
        }
        if(ObjectUtil.isNotEmpty(cadProjectPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.NO",cadProjectPageParam.getSearchKey())
                    .or().like("a.NAME",cadProjectPageParam.getSearchKey())
                    .or().like("b.NAME",cadProjectPageParam.getSearchKey())
                    .or().like("a.UNIT",cadProjectPageParam.getSearchKey())
                    .or().like("a.ADDRESS",cadProjectPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(cadProjectPageParam.getSortField(), cadProjectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cadProjectPageParam.getSortOrder());
            queryWrapper.orderBy(true, cadProjectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cadProjectPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CadProject::getId);
        }
        return cadProjectMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CadProjectAddParam cadProjectAddParam) {
        CadProject cadProject = BeanUtil.toBean(cadProjectAddParam, CadProject.class);
        this.save(cadProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CadProjectEditParam cadProjectEditParam) {
        CadProject cadProject = this.queryEntity(cadProjectEditParam.getId());
        BeanUtil.copyProperties(cadProjectEditParam, cadProject);
        this.updateById(cadProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CadProjectIdParam> cadProjectIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cadProjectIdParamList, CadProjectIdParam::getId));
    }

    @Override
    public CadProjectVo detail(CadProjectIdParam cadProjectIdParam) {
        return cadProjectMapper.getById(cadProjectIdParam.getId());
    }

    @Override
    public CadProject queryEntity(String id) {
        CadProject cadProject = this.getById(id);
        if(ObjectUtil.isEmpty(cadProject)) {
            throw new CommonException("CAD-专业表不存在，id值为：{}", id);
        }
        return cadProject;
    }

    @Override
    public List<StageSubItemVo> stageSubItem(String prjId) {
        List<StageSubItemVo> result = new ArrayList<>();
        List<CadSubItemVo> subItemVoList = cadProjectMapper.getSubItemByPrjId(prjId);
        Map<String,List<CadSubItemVo>> groupByStage = subItemVoList.stream().sorted(Comparator.comparing(x->x.getStageIndex())).collect(Collectors.groupingBy(x->x.getStageId()));
        for (Map.Entry<String,List<CadSubItemVo>> item:groupByStage.entrySet()) {
            StageSubItemVo stageSubItemVo = new StageSubItemVo();
            stageSubItemVo.setStageId(item.getKey());
            stageSubItemVo.setStageName(item.getValue().get(0).getStageName());
            stageSubItemVo.setSubItemList(item.getValue());
            result.add(stageSubItemVo);
        }
        return result;
    }

}
