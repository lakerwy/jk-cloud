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
package vip.xiaonuo.sys.modular.hr.applyWorkOt.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.entity.HrApplyWorkOt;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.mapper.HrApplyWorkOtMapper;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.param.HrApplyWorkOtAddParam;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.param.HrApplyWorkOtEditParam;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.param.HrApplyWorkOtIdParam;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.param.HrApplyWorkOtPageParam;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.service.HrApplyWorkOtService;

import java.util.List;

/**
 * 考勤-申请加班数据表Service接口实现类
 *
 * @author ljy
 * @date  2023/10/29 19:57
 **/
@Service
public class HrApplyWorkOtServiceImpl extends ServiceImpl<HrApplyWorkOtMapper, HrApplyWorkOt> implements HrApplyWorkOtService {

    @Override
    public Page<HrApplyWorkOt> page(HrApplyWorkOtPageParam hrApplyWorkOtPageParam) {
        QueryWrapper<HrApplyWorkOt> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrApplyWorkOtPageParam.getApplyUserId())) {
            queryWrapper.lambda().eq(HrApplyWorkOt::getApplyUserId, hrApplyWorkOtPageParam.getApplyUserId());
        }
        if(ObjectUtil.isNotEmpty(hrApplyWorkOtPageParam.getProjectId())) {
            queryWrapper.lambda().eq(HrApplyWorkOt::getProjectId, hrApplyWorkOtPageParam.getProjectId());
        }
        if(ObjectUtil.isNotEmpty(hrApplyWorkOtPageParam.getProjectName())) {
            queryWrapper.lambda().like(HrApplyWorkOt::getProjectName, hrApplyWorkOtPageParam.getProjectName());
        }
        if(ObjectUtil.isAllNotEmpty(hrApplyWorkOtPageParam.getSortField(), hrApplyWorkOtPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrApplyWorkOtPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrApplyWorkOtPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrApplyWorkOtPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrApplyWorkOt::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrApplyWorkOtAddParam hrApplyWorkOtAddParam) {
        HrApplyWorkOt hrApplyWorkOt = BeanUtil.toBean(hrApplyWorkOtAddParam, HrApplyWorkOt.class);
        this.save(hrApplyWorkOt);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrApplyWorkOtEditParam hrApplyWorkOtEditParam) {
        HrApplyWorkOt hrApplyWorkOt = this.queryEntity(hrApplyWorkOtEditParam.getId());
        BeanUtil.copyProperties(hrApplyWorkOtEditParam, hrApplyWorkOt);
        this.updateById(hrApplyWorkOt);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrApplyWorkOtIdParam> hrApplyWorkOtIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrApplyWorkOtIdParamList, HrApplyWorkOtIdParam::getId));
    }

    @Override
    public HrApplyWorkOt detail(HrApplyWorkOtIdParam hrApplyWorkOtIdParam) {
        return this.queryEntity(hrApplyWorkOtIdParam.getId());
    }

    @Override
    public HrApplyWorkOt queryEntity(String id) {
        HrApplyWorkOt hrApplyWorkOt = this.getById(id);
        if(ObjectUtil.isEmpty(hrApplyWorkOt)) {
            throw new CommonException("考勤-申请加班数据表不存在，id值为：{}", id);
        }
        return hrApplyWorkOt;
    }
}
