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
package vip.xiaonuo.biz.modular.emp.service.impl;

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
import vip.xiaonuo.biz.modular.emp.entity.BizEmpPerformance;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpTask;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpTaskFlw;
import vip.xiaonuo.biz.modular.emp.param.*;
import vip.xiaonuo.biz.modular.emp.service.BizEmpPerformanceService;
import vip.xiaonuo.biz.modular.emp.service.BizEmpTaskFlwService;
import vip.xiaonuo.biz.modular.emp.service.BizEmpTaskService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpAssList;
import vip.xiaonuo.biz.modular.emp.mapper.BizEmpAssListMapper;
import vip.xiaonuo.biz.modular.emp.service.BizEmpAssListService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务表-员工绩效考核-考核人员名单Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/26 10:40
 **/

@DS("slave_1")
@Service
public class BizEmpAssListServiceImpl extends ServiceImpl<BizEmpAssListMapper, BizEmpAssList> implements BizEmpAssListService {

    @Resource
    private BizEmpTaskFlwService bizEmpTaskFlwService;

    @Resource
    private BizEmpTaskService bizEmpTaskService;

    @Resource
    private BizEmpPerformanceService bizEmpPerformanceService;

    @Override
    public Page<BizEmpAssList> page(BizEmpAssListPageParam bizEmpAssListPageParam) {
        QueryWrapper<BizEmpAssList> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizEmpAssListPageParam.getUserId())) {
            queryWrapper.lambda().eq(BizEmpAssList::getUserId, bizEmpAssListPageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpAssListPageParam.getUserName())) {
            queryWrapper.lambda().like(BizEmpAssList::getUserName, bizEmpAssListPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(bizEmpAssListPageParam.getOrgId())) {
            queryWrapper.lambda().eq(BizEmpAssList::getOrgId, bizEmpAssListPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpAssListPageParam.getOrgName())) {
            queryWrapper.lambda().like(BizEmpAssList::getOrgName, bizEmpAssListPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(bizEmpAssListPageParam.getYear())) {
            queryWrapper.lambda().eq(BizEmpAssList::getYear, bizEmpAssListPageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(bizEmpAssListPageParam.getSeason())) {
            queryWrapper.lambda().eq(BizEmpAssList::getSeason, bizEmpAssListPageParam.getSeason());
        }
        //关键字
        if(ObjectUtil.isNotEmpty(bizEmpAssListPageParam.getSearchKey())){
            queryWrapper.and(q->q.lambda().like(BizEmpAssList::getUserName,bizEmpAssListPageParam.getSearchKey())
                    .like(BizEmpAssList::getOrgName,bizEmpAssListPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(bizEmpAssListPageParam.getSortField(), bizEmpAssListPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizEmpAssListPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizEmpAssListPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizEmpAssListPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizEmpAssList::getYear).orderByDesc(BizEmpAssList::getSeason).orderByAsc(BizEmpAssList::getUserId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EmpAssListBatchParam empAssListBatchParam) {
        List<BizEmpAssList> empAssLists = BeanUtil.copyToList(empAssListBatchParam.getEmpAssInfoList(), BizEmpAssList.class);
        empAssLists.forEach(x->{
            x.setYear(empAssListBatchParam.getYear());
            x.setSeason(empAssListBatchParam.getSeason());
        });
        //过滤已经加入到名单中的人
        List<BizEmpAssList> oldEmpAssLists = this.list(new QueryWrapper<BizEmpAssList>().lambda()
                .eq(BizEmpAssList::getYear,empAssListBatchParam.getYear())
                .eq(BizEmpAssList::getSeason,empAssListBatchParam.getSeason()));
        List<String> oldUserIds = oldEmpAssLists.stream().map(x->x.getUserId()).distinct().collect(Collectors.toList());
        List<BizEmpAssList> addList = empAssLists.stream().filter(x-> !oldUserIds.contains(x.getUserId())).collect(Collectors.toList());
        //添加新增的考核人员
        this.saveBatch(addList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizEmpAssListEditParam bizEmpAssListEditParam) {
        BizEmpAssList bizEmpAssList = this.queryEntity(bizEmpAssListEditParam.getId());
        BeanUtil.copyProperties(bizEmpAssListEditParam, bizEmpAssList);
        this.updateById(bizEmpAssList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizEmpAssListIdParam> bizEmpAssListIdParamList) {
        // 执行删除 (删除考核人员 同时 删除 当前年+当前季度+当前员工 的绩效目标、考核数据 )
        List<String> delIds = CollStreamUtil.toList(bizEmpAssListIdParamList, BizEmpAssListIdParam::getId);
        List<BizEmpAssList> delObjs = this.list(new QueryWrapper<BizEmpAssList>().lambda().in(BizEmpAssList::getId,delIds));
        // 依次删除
        for (BizEmpAssList obj:delObjs) {
            bizEmpTaskFlwService.remove(new QueryWrapper<BizEmpTaskFlw>().lambda()
                    .eq(BizEmpTaskFlw::getYear,obj.getYear())
                    .eq(BizEmpTaskFlw::getSeason,obj.getSeason())
                    .eq(BizEmpTaskFlw::getUserId,obj.getUserId()));

            bizEmpTaskService.remove(new QueryWrapper<BizEmpTask>().lambda()
                    .eq(BizEmpTask::getYear,obj.getYear())
                    .eq(BizEmpTask::getSeason,obj.getSeason())
                    .eq(BizEmpTask::getUserId,obj.getUserId()));

            bizEmpPerformanceService.remove(new QueryWrapper<BizEmpPerformance>().lambda()
                    .eq(BizEmpPerformance::getYear,obj.getYear())
                    .eq(BizEmpPerformance::getSeason,obj.getSeason())
                    .eq(BizEmpPerformance::getUserId,obj.getUserId()));

            this.removeById(obj);
        }
    }

    @Override
    public BizEmpAssList detail(BizEmpAssListIdParam bizEmpAssListIdParam) {
        return this.queryEntity(bizEmpAssListIdParam.getId());
    }

    @Override
    public BizEmpAssList queryEntity(String id) {
        BizEmpAssList bizEmpAssList = this.getById(id);
        if(ObjectUtil.isEmpty(bizEmpAssList)) {
            throw new CommonException("业务表-员工绩效考核-考核人员名单不存在，id值为：{}", id);
        }
        return bizEmpAssList;
    }
}
