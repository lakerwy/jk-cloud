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
package vip.xiaonuo.bp.modular.platform.service.impl;

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
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.bp.modular.platform.entity.NoticeTz;
import vip.xiaonuo.bp.modular.platform.mapper.NoticeTzMapper;
import vip.xiaonuo.bp.modular.platform.param.NoticeTzAddParam;
import vip.xiaonuo.bp.modular.platform.param.NoticeTzEditParam;
import vip.xiaonuo.bp.modular.platform.param.NoticeTzIdParam;
import vip.xiaonuo.bp.modular.platform.param.NoticeTzPageParam;
import vip.xiaonuo.bp.modular.platform.service.NoticeTzService;

import java.util.Date;
import java.util.List;

/**
 * 招采管理 - 通知公告Service接口实现类
 *
 * @author yong.ye
 * @date  2024/05/07 16:34
 **/
@Service
@DS("slave_1")
public class NoticeTzServiceImpl extends ServiceImpl<NoticeTzMapper, NoticeTz> implements NoticeTzService {

    @Override
    public Page<NoticeTz> page(NoticeTzPageParam noticeTzPageParam) {
        QueryWrapper<NoticeTz> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(noticeTzPageParam.getTitle())) {
            queryWrapper.lambda().like(NoticeTz::getTitle, noticeTzPageParam.getTitle());
        }
        if(ObjectUtil.isNotEmpty(noticeTzPageParam.getStartPublishTime()) && ObjectUtil.isNotEmpty(noticeTzPageParam.getEndPublishTime())) {
            queryWrapper.lambda().between(NoticeTz::getPublishTime, noticeTzPageParam.getStartPublishTime(), noticeTzPageParam.getEndPublishTime());
        }
        if(ObjectUtil.isAllNotEmpty(noticeTzPageParam.getSortField(), noticeTzPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(noticeTzPageParam.getSortOrder());
            queryWrapper.orderBy(true, noticeTzPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(noticeTzPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(NoticeTz::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(NoticeTzAddParam noticeTzAddParam) {
        NoticeTz noticeTz = BeanUtil.toBean(noticeTzAddParam, NoticeTz.class);
        noticeTz.setPublishTime(new Date());
        //校验标题是否重名
        if(ObjectUtil.isNotEmpty(noticeTz.getTitle())){
            int size = this.list(new QueryWrapper<NoticeTz>().lambda().eq(NoticeTz::getTitle,noticeTz.getTitle())).size();
            if(size > 0){
                throw new CommonException("标题已存在！");
            }
        }
        this.save(noticeTz);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(NoticeTzEditParam noticeTzEditParam) {
        NoticeTz noticeTz = this.queryEntity(noticeTzEditParam.getId());
        BeanUtil.copyProperties(noticeTzEditParam, noticeTz);
        //校验标题是否重名
        if(ObjectUtil.isNotEmpty(noticeTz.getTitle())){
            int size = this.list(new QueryWrapper<NoticeTz>().lambda().eq(NoticeTz::getTitle,noticeTz.getTitle()).ne(NoticeTz::getId,noticeTz.getId())).size();
            if(size > 0){
                throw new CommonException("标题已存在！");
            }
        }
        this.updateById(noticeTz);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<NoticeTzIdParam> noticeTzIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(noticeTzIdParamList, NoticeTzIdParam::getId));
    }

    @Override
    public NoticeTz detail(NoticeTzIdParam noticeTzIdParam) {
        return this.queryEntity(noticeTzIdParam.getId());
    }

    @Override
    public NoticeTz queryEntity(String id) {
        NoticeTz noticeTz = this.getById(id);
        if(ObjectUtil.isEmpty(noticeTz)) {
            throw new CommonException("招采管理 - 通知公告不存在，id值为：{}", id);
        }
        return noticeTz;
    }
}
