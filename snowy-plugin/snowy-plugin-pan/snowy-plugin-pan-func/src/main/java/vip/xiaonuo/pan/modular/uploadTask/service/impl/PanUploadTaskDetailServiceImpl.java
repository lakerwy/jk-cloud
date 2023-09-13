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
package vip.xiaonuo.pan.modular.uploadTask.service.impl;

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
import vip.xiaonuo.pan.modular.uploadTask.entity.PanUploadTaskDetail;
import vip.xiaonuo.pan.modular.uploadTask.mapper.PanUploadTaskDetailMapper;
import vip.xiaonuo.pan.modular.uploadTask.param.PanUploadTaskDetailAddParam;
import vip.xiaonuo.pan.modular.uploadTask.param.PanUploadTaskDetailEditParam;
import vip.xiaonuo.pan.modular.uploadTask.param.PanUploadTaskDetailIdParam;
import vip.xiaonuo.pan.modular.uploadTask.param.PanUploadTaskDetailPageParam;
import vip.xiaonuo.pan.modular.uploadTask.service.PanUploadTaskDetailService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 云盘-上传任务详情表Service接口实现类
 *
 * @author mayike
 * @date  2023/09/08 16:35
 **/
@Service
public class PanUploadTaskDetailServiceImpl extends ServiceImpl<PanUploadTaskDetailMapper, PanUploadTaskDetail> implements PanUploadTaskDetailService {

    @Resource
    PanUploadTaskDetailService panUploadTaskDetailService;
    @Override
    public Page<PanUploadTaskDetail> page(PanUploadTaskDetailPageParam panUploadTaskDetailPageParam) {
        QueryWrapper<PanUploadTaskDetail> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(panUploadTaskDetailPageParam.getSortField(), panUploadTaskDetailPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panUploadTaskDetailPageParam.getSortOrder());
            queryWrapper.orderBy(true, panUploadTaskDetailPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panUploadTaskDetailPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanUploadTaskDetail::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanUploadTaskDetailAddParam panUploadTaskDetailAddParam) {
        PanUploadTaskDetail panUploadTaskDetail = BeanUtil.toBean(panUploadTaskDetailAddParam, PanUploadTaskDetail.class);
        this.save(panUploadTaskDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanUploadTaskDetailEditParam panUploadTaskDetailEditParam) {
        PanUploadTaskDetail panUploadTaskDetail = this.queryEntity(panUploadTaskDetailEditParam.getId());
        BeanUtil.copyProperties(panUploadTaskDetailEditParam, panUploadTaskDetail);
        this.updateById(panUploadTaskDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanUploadTaskDetailIdParam> panUploadTaskDetailIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panUploadTaskDetailIdParamList, PanUploadTaskDetailIdParam::getId));
    }

    @Override
    public PanUploadTaskDetail detail(PanUploadTaskDetailIdParam panUploadTaskDetailIdParam) {
        return this.queryEntity(panUploadTaskDetailIdParam.getId());
    }

    @Override
    public PanUploadTaskDetail queryEntity(String id) {
        PanUploadTaskDetail panUploadTaskDetail = this.getById(id);
        if(ObjectUtil.isEmpty(panUploadTaskDetail)) {
            throw new CommonException("云盘-上传任务详情表不存在，id值为：{}", id);
        }
        return panUploadTaskDetail;
    }

    @Override
    public List<Integer> selectUploadedChunkNumList(String identifier) {
        return panUploadTaskDetailService.selectUploadedChunkNumList(identifier);
    }
}
