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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cad.entity.CadStorage;
import vip.xiaonuo.biz.modular.cad.mapper.CadStorageMapper;
import vip.xiaonuo.biz.modular.cad.param.CadStorageAddParam;
import vip.xiaonuo.biz.modular.cad.param.CadStorageEditParam;
import vip.xiaonuo.biz.modular.cad.param.CadStorageIdParam;
import vip.xiaonuo.biz.modular.cad.param.CadStoragePageParam;
import vip.xiaonuo.biz.modular.cad.service.CadStorageService;

import java.util.List;

/**
 * CAD-提资|校审|成品文件存储目录结构Service接口实现类
 *
 * @author yong.ye
 * @date  2023/12/12 15:14
 **/
@Service
public class CadStorageServiceImpl extends ServiceImpl<CadStorageMapper, CadStorage> implements CadStorageService {

    @Override
    public Page<CadStorage> page(CadStoragePageParam cadStoragePageParam) {
        QueryWrapper<CadStorage> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(cadStoragePageParam.getSortField(), cadStoragePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cadStoragePageParam.getSortOrder());
            queryWrapper.orderBy(true, cadStoragePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cadStoragePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CadStorage::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CadStorageAddParam cadStorageAddParam) {
        CadStorage cadStorage = BeanUtil.toBean(cadStorageAddParam, CadStorage.class);
        this.save(cadStorage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CadStorageEditParam cadStorageEditParam) {
        CadStorage cadStorage = this.queryEntity(cadStorageEditParam.getId());
        BeanUtil.copyProperties(cadStorageEditParam, cadStorage);
        this.updateById(cadStorage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CadStorageIdParam> cadStorageIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cadStorageIdParamList, CadStorageIdParam::getId));
    }

    @Override
    public CadStorage detail(CadStorageIdParam cadStorageIdParam) {
        return this.queryEntity(cadStorageIdParam.getId());
    }

    @Override
    public CadStorage queryEntity(String id) {
        CadStorage cadStorage = this.getById(id);
        if(ObjectUtil.isEmpty(cadStorage)) {
            throw new CommonException("CAD-提资|校审|成品文件存储目录结构不存在，id值为：{}", id);
        }
        return cadStorage;
    }
}
