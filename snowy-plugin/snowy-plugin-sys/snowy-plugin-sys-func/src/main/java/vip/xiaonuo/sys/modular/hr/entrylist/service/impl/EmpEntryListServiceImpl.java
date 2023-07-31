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
package vip.xiaonuo.sys.modular.hr.entrylist.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.date.DateUtil;
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
import vip.xiaonuo.sys.modular.hr.entrylist.entity.EmpEntryList;
import vip.xiaonuo.sys.modular.hr.entrylist.mapper.EmpEntryListMapper;
import vip.xiaonuo.sys.modular.hr.entrylist.param.EmpEntryListAddParam;
import vip.xiaonuo.sys.modular.hr.entrylist.param.EmpEntryListEditParam;
import vip.xiaonuo.sys.modular.hr.entrylist.param.EmpEntryListIdParam;
import vip.xiaonuo.sys.modular.hr.entrylist.param.EmpEntryListPageParam;
import vip.xiaonuo.sys.modular.hr.entrylist.service.EmpEntryListService;

import java.util.Date;
import java.util.List;

/**
 * 入职名单Service接口实现类
 *
 * @author yong.ye
 * @date  2023/07/31 09:25
 **/
@Service
public class EmpEntryListServiceImpl extends ServiceImpl<EmpEntryListMapper, EmpEntryList> implements EmpEntryListService {

    @Override
    public Page<EmpEntryList> page(EmpEntryListPageParam empEntryListPageParam) {
        QueryWrapper<EmpEntryList> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(empEntryListPageParam.getEmp())) {
            queryWrapper.lambda().eq(EmpEntryList::getEmp, empEntryListPageParam.getEmp());
        }
        if(ObjectUtil.isNotEmpty(empEntryListPageParam.getDept())) {
            queryWrapper.lambda().eq(EmpEntryList::getDept, empEntryListPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(empEntryListPageParam.getStartHireDate()) && ObjectUtil.isNotEmpty(empEntryListPageParam.getEndHireDate())) {
            queryWrapper.lambda().between(EmpEntryList::getHireDate, empEntryListPageParam.getStartHireDate(), empEntryListPageParam.getEndHireDate());
        }
        if(ObjectUtil.isNotEmpty(empEntryListPageParam.getPhone())) {
            queryWrapper.lambda().like(EmpEntryList::getPhone, empEntryListPageParam.getPhone());
        }
        if(ObjectUtil.isNotEmpty(empEntryListPageParam.getOffice())) {
            queryWrapper.lambda().like(EmpEntryList::getOffice, empEntryListPageParam.getOffice());
        }
        if(ObjectUtil.isAllNotEmpty(empEntryListPageParam.getSortField(), empEntryListPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(empEntryListPageParam.getSortOrder());
            queryWrapper.orderBy(true, empEntryListPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(empEntryListPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(EmpEntryList::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EmpEntryListAddParam empEntryListAddParam) {
        EmpEntryList empEntryList = BeanUtil.toBean(empEntryListAddParam, EmpEntryList.class);
        empEntryList.setOptDate(new Date());
        this.save(empEntryList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EmpEntryListEditParam empEntryListEditParam) {
        EmpEntryList empEntryList = this.queryEntity(empEntryListEditParam.getId());
        BeanUtil.copyProperties(empEntryListEditParam, empEntryList);
        this.updateById(empEntryList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EmpEntryListIdParam> empEntryListIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(empEntryListIdParamList, EmpEntryListIdParam::getId));
    }

    @Override
    public EmpEntryList detail(EmpEntryListIdParam empEntryListIdParam) {
        return this.queryEntity(empEntryListIdParam.getId());
    }

    @Override
    public EmpEntryList queryEntity(String id) {
        EmpEntryList empEntryList = this.getById(id);
        if(ObjectUtil.isEmpty(empEntryList)) {
            throw new CommonException("入职名单不存在，id值为：{}", id);
        }
        return empEntryList;
    }
}
