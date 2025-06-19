package vip.xiaonuo.biz.modular.prj.por.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.por.entity.PorMsg;
import vip.xiaonuo.biz.modular.prj.por.mapper.PorMsgMapper;
import vip.xiaonuo.biz.modular.prj.por.param.PorMsgAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorMsgEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorMsgIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorMsgPageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorMsgService;
import vip.xiaonuo.dev.api.DevDictApi;
import vip.xiaonuo.dev.api.IDingDingService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目产值台账 - 收款提醒Service接口实现类
 *
 * @author yy
 * @date  2025/06/18 16:13
 **/
@Service
@DS("slave_1")
public class PorMsgServiceImpl extends ServiceImpl<PorMsgMapper, PorMsg> implements PorMsgService {

    @Resource
    DevDictApi devDictApi;

    @Resource
    IDingDingService dingDingService;

    @Override
    public Page<PorMsg> page(PorMsgPageParam porMsgPageParam) {
        QueryWrapper<PorMsg> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(porMsgPageParam.getPorProjectId())) {
            queryWrapper.lambda().eq(PorMsg::getPorProjectId, porMsgPageParam.getPorProjectId());
        }
        if(ObjectUtil.isNotEmpty(porMsgPageParam.getModelType())) {
            queryWrapper.lambda().eq(PorMsg::getModelType, porMsgPageParam.getModelType());
        }
        if(ObjectUtil.isNotEmpty(porMsgPageParam.getHetongId())) {
            queryWrapper.lambda().eq(PorMsg::getHetongId, porMsgPageParam.getHetongId());
        }
        if(ObjectUtil.isNotEmpty(porMsgPageParam.getObjId())) {
            queryWrapper.lambda().eq(PorMsg::getObjId, porMsgPageParam.getObjId());
        }
        if(ObjectUtil.isNotEmpty(porMsgPageParam.getIsDing())) {
            queryWrapper.lambda().eq(PorMsg::getIsDing, porMsgPageParam.getIsDing());
        }
        if(ObjectUtil.isNotEmpty(porMsgPageParam.getSubjectTitle())) {
            queryWrapper.lambda().like(PorMsg::getSubjectTitle, porMsgPageParam.getSubjectTitle());
        }
        if(ObjectUtil.isNotEmpty(porMsgPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(PorMsg::getSubjectTitle,porMsgPageParam.getSearchKey())
                    .or().like(PorMsg::getContent,porMsgPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(porMsgPageParam.getSortField(), porMsgPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porMsgPageParam.getSortOrder());
            queryWrapper.orderBy(true, porMsgPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porMsgPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(PorMsg::getModelType).orderByDesc(PorMsg::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PorMsgAddParam porMsgAddParam) {
        PorMsg porMsg = BeanUtil.toBean(porMsgAddParam, PorMsg.class);
        if(CollUtil.isNotEmpty(porMsgAddParam.getUserIdList())){
            // 发送站内信
            String category = "系统".equals(porMsgAddParam.getMsgType()) ? "SYS" : "BIZ";
            devDictApi.sendMessageWithContent(porMsgAddParam.getUserIdList(), category, porMsgAddParam.getSubjectTitle(), porMsgAddParam.getContent(), null);
            // 发送钉钉提醒
            dingDingService.batchSend(String.format("【%s】%s", porMsgAddParam.getSubjectTitle(), porMsgAddParam.getContent()),porMsgAddParam.getUserIdList());
        }
        this.save(porMsg);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PorMsgEditParam porMsgEditParam) {
        PorMsg porMsg = this.queryEntity(porMsgEditParam.getId());
        BeanUtil.copyProperties(porMsgEditParam, porMsg);
        this.updateById(porMsg);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PorMsgIdParam> porMsgIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(porMsgIdParamList, PorMsgIdParam::getId));
    }

    @Override
    public PorMsg detail(PorMsgIdParam porMsgIdParam) {
        return this.queryEntity(porMsgIdParam.getId());
    }

    @Override
    public PorMsg queryEntity(String id) {
        PorMsg porMsg = this.getById(id);
        if(ObjectUtil.isEmpty(porMsg)) {
            throw new CommonException("项目产值台账 - 收款提醒不存在，id值为：{}", id);
        }
        return porMsg;
    }
}
