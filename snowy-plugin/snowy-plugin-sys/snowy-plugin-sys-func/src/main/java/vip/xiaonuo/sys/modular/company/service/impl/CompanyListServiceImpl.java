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
package vip.xiaonuo.sys.modular.company.service.impl;

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
import vip.xiaonuo.sys.modular.company.entity.CompanyList;
import vip.xiaonuo.sys.modular.company.mapper.CompanyListMapper;
import vip.xiaonuo.sys.modular.company.param.CompanyListAddParam;
import vip.xiaonuo.sys.modular.company.param.CompanyListEditParam;
import vip.xiaonuo.sys.modular.company.param.CompanyListIdParam;
import vip.xiaonuo.sys.modular.company.param.CompanyListPageParam;
import vip.xiaonuo.sys.modular.company.service.CompanyListService;

import java.util.List;

/**
 * 建科公司列表(子公司、分公司)Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/07 15:04
 **/
@Service
public class CompanyListServiceImpl extends ServiceImpl<CompanyListMapper, CompanyList> implements CompanyListService {

    @Override
    public Page<CompanyList> page(CompanyListPageParam companyListPageParam) {
        QueryWrapper<CompanyList> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(companyListPageParam.getType())) {
            queryWrapper.lambda().eq(CompanyList::getType, companyListPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(companyListPageParam.getCompanyName())) {
            queryWrapper.lambda().like(CompanyList::getCompanyName, companyListPageParam.getCompanyName());
        }
        if(ObjectUtil.isNotEmpty(companyListPageParam.getCertNo())) {
            queryWrapper.lambda().like(CompanyList::getCertNo, companyListPageParam.getCertNo());
        }
        if(ObjectUtil.isNotEmpty(companyListPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(CompanyList::getCompanyName,companyListPageParam.getSearchKey())
                    .or().like(CompanyList::getCertNo,companyListPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(companyListPageParam.getSortField(), companyListPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(companyListPageParam.getSortOrder());
            queryWrapper.orderBy(true, companyListPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(companyListPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CompanyList::getType).orderByAsc(CompanyList::getIndexSort);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CompanyListAddParam companyListAddParam) {
        CompanyList companyList = BeanUtil.toBean(companyListAddParam, CompanyList.class);
        this.save(companyList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CompanyListEditParam companyListEditParam) {
        CompanyList companyList = this.queryEntity(companyListEditParam.getId());
        BeanUtil.copyProperties(companyListEditParam, companyList);
        this.updateById(companyList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CompanyListIdParam> companyListIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(companyListIdParamList, CompanyListIdParam::getId));
    }

    @Override
    public CompanyList detail(CompanyListIdParam companyListIdParam) {
        return this.queryEntity(companyListIdParam.getId());
    }

    @Override
    public CompanyList queryEntity(String id) {
        CompanyList companyList = this.getById(id);
        if(ObjectUtil.isEmpty(companyList)) {
            throw new CommonException("建科公司列表(子公司、分公司)不存在，id值为：{}", id);
        }
        return companyList;
    }
}
