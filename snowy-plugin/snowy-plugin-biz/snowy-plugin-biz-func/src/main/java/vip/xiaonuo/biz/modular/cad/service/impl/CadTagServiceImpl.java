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
import vip.xiaonuo.biz.modular.cad.vo.CadTagVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cad.entity.CadTag;
import vip.xiaonuo.biz.modular.cad.mapper.CadTagMapper;
import vip.xiaonuo.biz.modular.cad.param.CadTagAddParam;
import vip.xiaonuo.biz.modular.cad.param.CadTagEditParam;
import vip.xiaonuo.biz.modular.cad.param.CadTagIdParam;
import vip.xiaonuo.biz.modular.cad.param.CadTagPageParam;
import vip.xiaonuo.biz.modular.cad.service.CadTagService;

import javax.annotation.Resource;
import java.util.List;

/**
 * CAD-批注会话聊天室表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/12/01 15:37
 **/

@DS("slave_1")
@Service
public class CadTagServiceImpl extends ServiceImpl<CadTagMapper, CadTag> implements CadTagService {

    @Resource
    private CadTagMapper cadTagMapper;

    @Override
    public Page<CadTag> page(CadTagPageParam cadTagPageParam) {
        QueryWrapper<CadTag> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(cadTagPageParam.getSortField(), cadTagPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cadTagPageParam.getSortOrder());
            queryWrapper.orderBy(true, cadTagPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cadTagPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CadTag::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CadTagAddParam cadTagAddParam) {
        CadTag cadTag = BeanUtil.toBean(cadTagAddParam, CadTag.class);
        this.save(cadTag);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CadTagEditParam cadTagEditParam) {
        CadTag cadTag = this.queryEntity(cadTagEditParam.getId());
        BeanUtil.copyProperties(cadTagEditParam, cadTag);
        this.updateById(cadTag);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CadTagIdParam> cadTagIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cadTagIdParamList, CadTagIdParam::getId));
    }

    @Override
    public CadTag detail(CadTagIdParam cadTagIdParam) {
        return this.queryEntity(cadTagIdParam.getId());
    }

    @Override
    public CadTag queryEntity(String id) {
        CadTag cadTag = this.getById(id);
        if(ObjectUtil.isEmpty(cadTag)) {
            throw new CommonException("CAD-批注会话聊天室表不存在，id值为：{}", id);
        }
        return cadTag;
    }

    @Override
    public List<CadTagVo> tagListByFileId(String fileId) {
        return cadTagMapper.tagListByFileId(fileId);
    }
}
