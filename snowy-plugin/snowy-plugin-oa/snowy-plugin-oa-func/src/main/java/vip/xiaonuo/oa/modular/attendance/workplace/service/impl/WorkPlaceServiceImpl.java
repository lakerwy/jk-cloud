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
package vip.xiaonuo.oa.modular.attendance.workplace.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.oa.modular.attendance.workplace.entity.WorkPlace;
import vip.xiaonuo.oa.modular.attendance.workplace.mapper.WorkPlaceMapper;
import vip.xiaonuo.oa.modular.attendance.workplace.param.WorkPlaceAddParam;
import vip.xiaonuo.oa.modular.attendance.workplace.param.WorkPlaceEditParam;
import vip.xiaonuo.oa.modular.attendance.workplace.param.WorkPlaceIdParam;
import vip.xiaonuo.oa.modular.attendance.workplace.param.WorkPlacePageParam;
import vip.xiaonuo.oa.modular.attendance.workplace.service.WorkPlaceService;

import java.util.List;

/**
 * 考勤打卡位置表Service接口实现类
 *
 * @author mayike
 * @date  2024/06/11 11:58
 **/
@Service
public class WorkPlaceServiceImpl extends ServiceImpl<WorkPlaceMapper, WorkPlace> implements WorkPlaceService {

    @Override
    public Page<WorkPlace> page(WorkPlacePageParam workPlacePageParam) {
        QueryWrapper<WorkPlace> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(workPlacePageParam.getPlaceName())) {
            queryWrapper.lambda().like(WorkPlace::getPlaceName, workPlacePageParam.getPlaceName());
        }
        if(ObjectUtil.isAllNotEmpty(workPlacePageParam.getSortField(), workPlacePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(workPlacePageParam.getSortOrder());
            queryWrapper.orderBy(true, workPlacePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(workPlacePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(WorkPlace::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(WorkPlaceAddParam workPlaceAddParam) {
        checkWorkPlace(workPlaceAddParam.getPlaceName());
        WorkPlace workPlace = BeanUtil.toBean(workPlaceAddParam, WorkPlace.class);
        if(workPlace.getErrorRange() == null || workPlace.getErrorRange() == 0){
            workPlace.setErrorRange(200);
        }
        this.save(workPlace);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(WorkPlaceEditParam workPlaceEditParam) {
        checkWorkPlace(workPlaceEditParam.getPlaceName());
        WorkPlace workPlace = this.queryEntity(workPlaceEditParam.getId());
        BeanUtil.copyProperties(workPlaceEditParam, workPlace);
        if(workPlace.getErrorRange() == null || workPlace.getErrorRange() == 0){
            workPlace.setErrorRange(200);
        }
        this.updateById(workPlace);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<WorkPlaceIdParam> workPlaceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(workPlaceIdParamList, WorkPlaceIdParam::getId));
    }

    @Override
    public WorkPlace detail(WorkPlaceIdParam workPlaceIdParam) {
        return this.queryEntity(workPlaceIdParam.getId());
    }

    @Override
    public WorkPlace queryEntity(String id) {
        WorkPlace workPlace = this.getById(id);
        if(ObjectUtil.isEmpty(workPlace)) {
            throw new CommonException("考勤打卡位置表不存在，id值为：{}", id);
        }
        return workPlace;
    }

    private void checkWorkPlace(String placeName) {
        WorkPlace existWorkPlace = this.getOne(new LambdaQueryWrapper<WorkPlace>().eq(WorkPlace::getPlaceName, placeName));
        if(ObjectUtil.isNotEmpty(existWorkPlace)){
            throw new CommonException("名称为{}的地址已存在.", placeName);
        }
    }
}
