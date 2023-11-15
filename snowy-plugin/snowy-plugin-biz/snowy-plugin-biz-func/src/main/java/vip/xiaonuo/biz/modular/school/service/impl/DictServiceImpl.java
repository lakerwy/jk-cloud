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
package vip.xiaonuo.biz.modular.school.service.impl;

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
import vip.xiaonuo.biz.modular.school.entity.Dict;
import vip.xiaonuo.biz.modular.school.mapper.DictMapper;
import vip.xiaonuo.biz.modular.school.param.DictAddParam;
import vip.xiaonuo.biz.modular.school.param.DictEditParam;
import vip.xiaonuo.biz.modular.school.param.DictIdParam;
import vip.xiaonuo.biz.modular.school.param.DictPageParam;
import vip.xiaonuo.biz.modular.school.service.DictService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统字典-自定义Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/15 08:51
 **/

@DS("slave_1")
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public Page<Dict> page(DictPageParam dictPageParam) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(dictPageParam.getType())) {
            queryWrapper.lambda().like(Dict::getType, dictPageParam.getType());
        }
        if(ObjectUtil.isAllNotEmpty(dictPageParam.getSortField(), dictPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(dictPageParam.getSortOrder());
            queryWrapper.orderBy(true, dictPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(dictPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Dict::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(DictAddParam dictAddParam) {
        Dict dict = BeanUtil.toBean(dictAddParam, Dict.class);
        this.save(dict);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(DictEditParam dictEditParam) {
        Dict dict = this.queryEntity(dictEditParam.getId());
        BeanUtil.copyProperties(dictEditParam, dict);
        this.updateById(dict);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<DictIdParam> dictIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(dictIdParamList, DictIdParam::getId));
    }

    @Override
    public Dict detail(DictIdParam dictIdParam) {
        return this.queryEntity(dictIdParam.getId());
    }

    @Override
    public Dict queryEntity(String id) {
        Dict dict = this.getById(id);
        if(ObjectUtil.isEmpty(dict)) {
            throw new CommonException("系统字典-自定义不存在，id值为：{}", id);
        }
        return dict;
    }

    @Override
    public List<Dict> getDictByType(String type) {
        return this.list(new QueryWrapper<Dict>().lambda().eq(Dict::getType,type));
    }

    @Override
    public Map<String, String> getAllDictKeyVale() {
        Map<String,String> keyValue = new HashMap<>();
        List<Dict> dictList = this.list();
        for (Dict item:dictList) {
            keyValue.put(item.getId(),item.getValue());
        }
        return keyValue;
    }

    @Override
    public List<String> getAllDictType() {
        return this.list().stream().map(Dict::getType).distinct().collect(Collectors.toList());
    }
}
