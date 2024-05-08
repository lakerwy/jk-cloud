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
import vip.xiaonuo.bp.modular.platform.vo.ClarifyVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.bp.modular.platform.entity.Clarify;
import vip.xiaonuo.bp.modular.platform.mapper.ClarifyMapper;
import vip.xiaonuo.bp.modular.platform.param.ClarifyAddParam;
import vip.xiaonuo.bp.modular.platform.param.ClarifyEditParam;
import vip.xiaonuo.bp.modular.platform.param.ClarifyIdParam;
import vip.xiaonuo.bp.modular.platform.param.ClarifyPageParam;
import vip.xiaonuo.bp.modular.platform.service.ClarifyService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 招采管理 - 竞价澄清|询价澄清|招标澄清Service接口实现类
 *
 * @author yong.ye
 * @date  2024/05/07 16:37
 **/
@Service
@DS("slave_1")
public class ClarifyServiceImpl extends ServiceImpl<ClarifyMapper, Clarify> implements ClarifyService {

    @Resource
    private ClarifyMapper clarifyMapper;

    @Override
    public Page<ClarifyVo> page(ClarifyPageParam clarifyPageParam) {
        QueryWrapper<Clarify> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(clarifyPageParam.getType())) {
            queryWrapper.eq("a.TYPE", clarifyPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(clarifyPageParam.getTitle())) {
            queryWrapper.like("a.TITLE", clarifyPageParam.getTitle());
        }
        if(ObjectUtil.isNotEmpty(clarifyPageParam.getStartPublishTime()) && ObjectUtil.isNotEmpty(clarifyPageParam.getEndPublishTime())) {
            queryWrapper.between("a.PUBLISH_TIME", clarifyPageParam.getStartPublishTime(), clarifyPageParam.getEndPublishTime());
        }
        if(ObjectUtil.isAllNotEmpty(clarifyPageParam.getSortField(), clarifyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(clarifyPageParam.getSortOrder());
            queryWrapper.orderBy(true, clarifyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(clarifyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Clarify::getCreateTime);
        }
        return clarifyMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ClarifyAddParam clarifyAddParam) {
        Clarify clarify = BeanUtil.toBean(clarifyAddParam, Clarify.class);
        clarify.setPublishTime(new Date());
        //校验名称是否重复
        if(ObjectUtil.isNotEmpty(clarify.getTitle()) && ObjectUtil.isNotEmpty(clarify.getType())){
            int size = this.list(new QueryWrapper<Clarify>().lambda().eq(Clarify::getTitle,clarify.getTitle()).eq(Clarify::getType,clarify.getType())).size();
            if(size > 0){
                throw new CommonException("标题已存在！");
            }
        }
        this.save(clarify);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ClarifyEditParam clarifyEditParam) {
        Clarify clarify = this.queryEntity(clarifyEditParam.getId());
        BeanUtil.copyProperties(clarifyEditParam, clarify);
        //校验名称是否重复
        if(ObjectUtil.isNotEmpty(clarify.getTitle()) && ObjectUtil.isNotEmpty(clarify.getType())){
            int size = this.list(new QueryWrapper<Clarify>().lambda().eq(Clarify::getTitle,clarify.getTitle()).eq(Clarify::getType,clarify.getType()).ne(Clarify::getId,clarify.getId())).size();
            if(size > 0){
                throw new CommonException("标题已存在！");
            }
        }
        this.updateById(clarify);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ClarifyIdParam> clarifyIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(clarifyIdParamList, ClarifyIdParam::getId));
    }

    @Override
    public ClarifyVo detail(ClarifyIdParam clarifyIdParam) {
        return clarifyMapper.getDetail(clarifyIdParam.getId());
    }

    @Override
    public Clarify queryEntity(String id) {
        Clarify clarify = this.getById(id);
        if(ObjectUtil.isEmpty(clarify)) {
            throw new CommonException("招采管理 - 竞价澄清|询价澄清|招标澄清不存在，id值为：{}", id);
        }
        return clarify;
    }
}
