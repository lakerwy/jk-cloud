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
package vip.xiaonuo.biz.modular.task.service.impl;

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
import vip.xiaonuo.biz.modular.task.entity.BizDeptAss;
import vip.xiaonuo.biz.modular.task.entity.BizDeptAssRecords;
import vip.xiaonuo.biz.modular.task.mapper.BizDeptAssRecordsMapper;
import vip.xiaonuo.biz.modular.task.param.*;
import vip.xiaonuo.biz.modular.task.service.BizDeptAssRecordsService;
import vip.xiaonuo.biz.modular.task.service.BizDeptAssService;
import vip.xiaonuo.biz.modular.task.service.SysOrgService;
import vip.xiaonuo.biz.modular.task.vo.BizSysOrgVo;
import vip.xiaonuo.biz.modular.task.vo.DeptAssRecordVo;
import vip.xiaonuo.biz.modular.task.vo.DeptAssScore;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 业务表-部门绩效考核-部门考核记录Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/08 17:20
 **/

@DS("slave_1")
@Service
public class BizDeptAssRecordsServiceImpl extends ServiceImpl<BizDeptAssRecordsMapper, BizDeptAssRecords> implements BizDeptAssRecordsService {

    @Resource
    private SysOrgService sysOrgService;
    @Resource
    private BizDeptAssService bizDeptAssService;
    @Resource
    private BizDeptAssRecordsMapper bizDeptAssRecordsMapper;

    @Override
    public IPage<DeptAssRecordVo> page(BizDeptAssRecordsPageParam bizDeptAssRecordsPageParam) {
        QueryWrapper<BizDeptAssRecords> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(bizDeptAssRecordsPageParam.getAssYear())) {
            queryWrapper.eq("a.ASS_YEAR", bizDeptAssRecordsPageParam.getAssYear());
        }
        if(ObjectUtil.isNotEmpty(bizDeptAssRecordsPageParam.getOrgId())) {
            queryWrapper.eq("a.ORG_ID", bizDeptAssRecordsPageParam.getOrgId());
        }
        if(ObjectUtil.isAllNotEmpty(bizDeptAssRecordsPageParam.getSortField(), bizDeptAssRecordsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizDeptAssRecordsPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizDeptAssRecordsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizDeptAssRecordsPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.ASS_YEAR").orderByAsc("a.ORG_ID").orderByDesc("a.CREATE_TIME");
        }
        IPage<DeptAssRecordVo> result = bizDeptAssRecordsMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
        List<BizSysOrgVo> orgList = sysOrgService.getOrgList();
        for (DeptAssRecordVo item:result.getRecords()) {
            Optional<BizSysOrgVo> org = orgList.stream().filter(x->x.getOrgId().equals(item.getOrgId())).findFirst();
            String orgName = org.isPresent() ? org.get().getOrgName() : "";
            item.setOrgName(orgName);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizDeptAssRecordsAddParam bizDeptAssRecordsAddParam) {
        BizDeptAssRecords bizDeptAssRecords = BeanUtil.toBean(bizDeptAssRecordsAddParam, BizDeptAssRecords.class);
        this.save(bizDeptAssRecords);
        //反更新部门年度考核分
        if(StrUtil.isNotEmpty(bizDeptAssRecordsAddParam.getAssId())) {
            DeptAssScore deptAssScore = bizDeptAssRecordsMapper.getDeptAssScore(bizDeptAssRecords.getAssId());
            BizDeptAss bizDeptAss = bizDeptAssService.queryEntity(bizDeptAssRecordsAddParam.getAssId());
            bizDeptAss.setAddScore(deptAssScore.getAddScore());
            bizDeptAss.setMinusScore(deptAssScore.getMinusScore());
            bizDeptAssService.updateById(bizDeptAss);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizDeptAssRecordsEditParam bizDeptAssRecordsEditParam) {
        BizDeptAssRecords bizDeptAssRecords = this.queryEntity(bizDeptAssRecordsEditParam.getId());
        BeanUtil.copyProperties(bizDeptAssRecordsEditParam, bizDeptAssRecords);
        this.updateById(bizDeptAssRecords);
        //反更新部门年度考核分
        if(StrUtil.isNotEmpty(bizDeptAssRecordsEditParam.getAssId())) {
            DeptAssScore deptAssScore = bizDeptAssRecordsMapper.getDeptAssScore(bizDeptAssRecords.getAssId());
            BizDeptAss bizDeptAss = bizDeptAssService.queryEntity(bizDeptAssRecordsEditParam.getAssId());
            bizDeptAss.setAddScore(deptAssScore.getAddScore());
            bizDeptAss.setMinusScore(deptAssScore.getMinusScore());
            bizDeptAssService.updateById(bizDeptAss);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizDeptAssRecordsIdParam> bizDeptAssRecordsIdParamList) {
        List<String> ids = CollStreamUtil.toList(bizDeptAssRecordsIdParamList, BizDeptAssRecordsIdParam::getId);
        //删除集合
        List<String> assIds = this.listByIds(ids).stream().map(x->x.getAssId()).collect(Collectors.toList());
        // 执行删除
        this.removeByIds(ids);

        //反更新部门年度考核分
        if(CollUtil.isNotEmpty(assIds)) {
            List<BizDeptAss> assList = new ArrayList<>();
            for (String assId:assIds) {
                DeptAssScore deptAssScore = bizDeptAssRecordsMapper.getDeptAssScore(assId);
                BizDeptAss bizDeptAss = bizDeptAssService.queryEntity(assId);
                bizDeptAss.setAddScore(deptAssScore.getAddScore());
                bizDeptAss.setMinusScore(deptAssScore.getMinusScore());
                assList.add(bizDeptAss);
            }
            bizDeptAssService.updateBatchById(assList);
        }
    }

    @Override
    public DeptAssRecordVo detail(BizDeptAssRecordsIdParam bizDeptAssRecordsIdParam) {
        DeptAssRecordVo recordVo = bizDeptAssRecordsMapper.getDetailById(bizDeptAssRecordsIdParam.getId());
        BizSysOrgVo org = sysOrgService.getOrgById(recordVo.getOrgId());
        recordVo.setOrgName(org.getOrgName());
        return recordVo;
    }

    @Override
    public BizDeptAssRecords queryEntity(String id) {
        BizDeptAssRecords bizDeptAssRecords = this.getById(id);
        if(ObjectUtil.isEmpty(bizDeptAssRecords)) {
            throw new CommonException("业务表-部门绩效考核-部门考核记录不存在，id值为：{}", id);
        }
        return bizDeptAssRecords;
    }

}
