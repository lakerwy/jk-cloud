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
package vip.xiaonuo.biz.modular.teambition.taskInfoComment.service.impl;

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
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.entity.BizTaskComment;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.mapper.BizTaskCommentMapper;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.param.BizTaskCommentAddParam;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.param.BizTaskCommentEditParam;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.param.BizTaskCommentIdParam;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.param.BizTaskCommentPageParam;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.service.BizTaskCommentService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import java.util.List;

/**
 * 任务管理平台-任务评论Service接口实现类
 *
 * @author ljy
 * @date  2023/12/13 17:51
 **/
@DS("slave_1")
@Service
public class BizTaskCommentServiceImpl extends ServiceImpl<BizTaskCommentMapper, BizTaskComment> implements BizTaskCommentService {

    @Override
    public Page<BizTaskComment> page(BizTaskCommentPageParam bizTaskCommentPageParam) {
        QueryWrapper<BizTaskComment> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizTaskCommentPageParam.getTaskInfoId())) {
            queryWrapper.lambda().eq(BizTaskComment::getTaskInfoId, bizTaskCommentPageParam.getTaskInfoId());
        }
        if(ObjectUtil.isAllNotEmpty(bizTaskCommentPageParam.getSortField(), bizTaskCommentPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTaskCommentPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTaskCommentPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTaskCommentPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizTaskComment::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTaskCommentAddParam bizTaskCommentAddParam) {
        BizTaskComment bizTaskComment = BeanUtil.toBean(bizTaskCommentAddParam, BizTaskComment.class);
        this.save(bizTaskComment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTaskCommentEditParam bizTaskCommentEditParam) {
        BizTaskComment bizTaskComment = this.queryEntity(bizTaskCommentEditParam.getId());
        BeanUtil.copyProperties(bizTaskCommentEditParam, bizTaskComment);
        this.updateById(bizTaskComment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTaskCommentIdParam> bizTaskCommentIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizTaskCommentIdParamList, BizTaskCommentIdParam::getId));
    }

    @Override
    public BizTaskComment detail(BizTaskCommentIdParam bizTaskCommentIdParam) {
        return this.queryEntity(bizTaskCommentIdParam.getId());
    }

    @Override
    public BizTaskComment queryEntity(String id) {
        BizTaskComment bizTaskComment = this.getById(id);
        if(ObjectUtil.isEmpty(bizTaskComment)) {
            throw new CommonException("任务管理平台-任务评论不存在，id值为：{}", id);
        }
        return bizTaskComment;
    }
}
