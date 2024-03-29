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
package vip.xiaonuo.sys.modular.dept.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.dept.entity.ExpenseNotice;
import vip.xiaonuo.sys.modular.dept.mapper.ExpenseNoticeMapper;
import vip.xiaonuo.sys.modular.dept.param.*;
import vip.xiaonuo.sys.modular.dept.service.ExpenseNoticeService;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门报销公示Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/27 09:04
 **/
@Service
public class ExpenseNoticeServiceImpl extends ServiceImpl<ExpenseNoticeMapper, ExpenseNotice> implements ExpenseNoticeService {

    @Resource
    private SysOrgService sysOrgService;

    /**
     * 默认 董事长办公室 组织Id
     */
    private static final String DSZ_BGS_ORG_ID = "1666651360364900353";

    @Override
    public Page<ExpenseNotice> page(ExpenseNoticePageParam expenseNoticePageParam) {
        QueryWrapper<ExpenseNotice> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(expenseNoticePageParam.getUnitName())) {
            queryWrapper.lambda().like(ExpenseNotice::getUnitName, expenseNoticePageParam.getUnitName());
        }
        if(ObjectUtil.isNotEmpty(expenseNoticePageParam.getTimeRange())) {
            queryWrapper.lambda().like(ExpenseNotice::getTimeRange, expenseNoticePageParam.getTimeRange());
        }
        if(ObjectUtil.isNotEmpty(expenseNoticePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(ExpenseNotice::getUnitName,expenseNoticePageParam.getSearchKey())
                    .or().like(ExpenseNotice::getTimeRange,expenseNoticePageParam.getSearchKey()));
        }
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(ExpenseNotice::getDataScopeId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(ExpenseNotice::getCreateUser, StpUtil.getLoginIdAsString());
        }
        if(ObjectUtil.isAllNotEmpty(expenseNoticePageParam.getSortField(), expenseNoticePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(expenseNoticePageParam.getSortOrder());
            queryWrapper.orderBy(true, expenseNoticePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(expenseNoticePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ExpenseNotice::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ExpenseNoticeAddParam expenseNoticeAddParam) {
        ExpenseNotice expenseNotice = BeanUtil.toBean(expenseNoticeAddParam, ExpenseNotice.class);
        if(ObjectUtil.isEmpty(expenseNotice.getDataScopeId())){
            expenseNotice.setDataScopeId(DSZ_BGS_ORG_ID);
        }
        this.save(expenseNotice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ExpenseNoticeEditParam expenseNoticeEditParam) {
        ExpenseNotice expenseNotice = this.queryEntity(expenseNoticeEditParam.getId());
        BeanUtil.copyProperties(expenseNoticeEditParam, expenseNotice);
        this.updateById(expenseNotice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ExpenseNoticeIdParam> expenseNoticeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(expenseNoticeIdParamList, ExpenseNoticeIdParam::getId));
    }

    @Override
    public ExpenseNotice detail(ExpenseNoticeIdParam expenseNoticeIdParam) {
        return this.queryEntity(expenseNoticeIdParam.getId());
    }

    @Override
    public ExpenseNotice queryEntity(String id) {
        ExpenseNotice expenseNotice = this.getById(id);
        if(ObjectUtil.isEmpty(expenseNotice)) {
            throw new CommonException("部门报销公示不存在，id值为：{}", id);
        }
        return expenseNotice;
    }

    @Override
    public void importNotice(ImportNotice input) {
        //直接导入，没有校验，匹配部门
        if(CollUtil.isEmpty(input.getImportData())){
            throw new CommonException("无有效导入数据");
        }
        //获取 组织架构数据
        QueryWrapper<SysOrg> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysOrg::getId,SysOrg::getName,SysOrg::getCode,SysOrg::getCategory);
        List<SysOrg> allOrgList = sysOrgService.list(queryWrapper);
        //匹配组织架构
        for(ExpenseNoticeAddParam addParam : input.getImportData()){
            SysOrg tmpOrg = allOrgList.stream().filter(x->x.getName().equals(addParam.getUnitName())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(tmpOrg)){
                addParam.setDataScopeId(tmpOrg.getId());
            }else {
                addParam.setDataScopeId(DSZ_BGS_ORG_ID);
                addParam.setMemo("经营单位与组织架构不匹配");
            }
        }
        List<ExpenseNotice> addList = BeanUtil.copyToList(input.getImportData(), ExpenseNotice.class);
        this.saveBatch(addList);
    }

    @Override
    public void delAll() {
        QueryWrapper<ExpenseNotice> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ExpenseNotice::getDeleteFlag,"NOT_DELETE");
        this.remove(queryWrapper);
    }
}
