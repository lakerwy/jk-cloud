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
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cad.entity.CadFileHistory;
import vip.xiaonuo.biz.modular.cad.mapper.CadFileHistoryMapper;
import vip.xiaonuo.biz.modular.cad.param.CadFileHistoryAddParam;
import vip.xiaonuo.biz.modular.cad.param.CadFileHistoryEditParam;
import vip.xiaonuo.biz.modular.cad.param.CadFileHistoryIdParam;
import vip.xiaonuo.biz.modular.cad.param.CadFileHistoryPageParam;
import vip.xiaonuo.biz.modular.cad.service.CadFileHistoryService;

import java.util.List;

/**
 * CAD-文件历史版本记录表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/12/01 15:34
 **/

@DS("slave_1")
@Service
public class CadFileHistoryServiceImpl extends ServiceImpl<CadFileHistoryMapper, CadFileHistory> implements CadFileHistoryService {

    @Override
    public Page<CadFileHistory> page(CadFileHistoryPageParam cadFileHistoryPageParam) {
        QueryWrapper<CadFileHistory> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(cadFileHistoryPageParam.getSortField(), cadFileHistoryPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cadFileHistoryPageParam.getSortOrder());
            queryWrapper.orderBy(true, cadFileHistoryPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cadFileHistoryPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CadFileHistory::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CadFileHistoryAddParam cadFileHistoryAddParam) {
        CadFileHistory cadFileHistory = BeanUtil.toBean(cadFileHistoryAddParam, CadFileHistory.class);
        this.save(cadFileHistory);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CadFileHistoryEditParam cadFileHistoryEditParam) {
        CadFileHistory cadFileHistory = this.queryEntity(cadFileHistoryEditParam.getId());
        BeanUtil.copyProperties(cadFileHistoryEditParam, cadFileHistory);
        this.updateById(cadFileHistory);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CadFileHistoryIdParam> cadFileHistoryIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cadFileHistoryIdParamList, CadFileHistoryIdParam::getId));
    }

    @Override
    public CadFileHistory detail(CadFileHistoryIdParam cadFileHistoryIdParam) {
        return this.queryEntity(cadFileHistoryIdParam.getId());
    }

    @Override
    public CadFileHistory queryEntity(String id) {
        CadFileHistory cadFileHistory = this.getById(id);
        if(ObjectUtil.isEmpty(cadFileHistory)) {
            throw new CommonException("CAD-文件历史版本记录表不存在，id值为：{}", id);
        }
        return cadFileHistory;
    }

    @Override
    public List<CadFileHistory> listHisByFileId(String fileId) {
        return this.list(new QueryWrapper<CadFileHistory>().lambda()
                .eq(CadFileHistory::getFileId,fileId)
                .orderByDesc(CadFileHistory::getVersion));
    }

    @Override
    public CadFileHistory lastHisByFileId(String fileId) {
        return this.getOne(new QueryWrapper<CadFileHistory>().lambda()
                .eq(CadFileHistory::getFileId,fileId)
                .orderByDesc(CadFileHistory::getVersion)
                .last(" limit 1 "));
    }
}
