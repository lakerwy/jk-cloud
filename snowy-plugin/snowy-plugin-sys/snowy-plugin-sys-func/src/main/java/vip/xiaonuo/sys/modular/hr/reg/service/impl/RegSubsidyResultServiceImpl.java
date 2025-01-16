package vip.xiaonuo.sys.modular.hr.reg.service.impl;

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
import vip.xiaonuo.sys.modular.hr.reg.entity.RegSubsidyResult;
import vip.xiaonuo.sys.modular.hr.reg.mapper.RegSubsidyResultMapper;
import vip.xiaonuo.sys.modular.hr.reg.param.*;
import vip.xiaonuo.sys.modular.hr.reg.service.RegSubsidyResultService;
import vip.xiaonuo.sys.modular.hr.reg.vo.RegResultVo;
import vip.xiaonuo.sys.modular.hr.salary.enums.YesOrNoEnum;

import javax.annotation.Resource;
import java.util.List;

/**
 * 注册补贴（新版）- 结果反馈Service接口实现类
 *
 * @author yong.ye
 * @date  2025/01/15 09:08
 **/
@Service
public class RegSubsidyResultServiceImpl extends ServiceImpl<RegSubsidyResultMapper, RegSubsidyResult> implements RegSubsidyResultService {

    @Resource
    private RegSubsidyResultMapper regSubsidyResultMapper;

    @Override
    public Page<RegResultVo> page(RegSubsidyResultPageParam regSubsidyResultPageParam) {
        QueryWrapper<RegSubsidyResult> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(regSubsidyResultPageParam.getLevel())) {
            queryWrapper.eq("a.LEVEL", regSubsidyResultPageParam.getLevel());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyResultPageParam.getType())) {
            queryWrapper.eq("a.TYPE", regSubsidyResultPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyResultPageParam.getMajor())) {
            queryWrapper.like("a.MAJOR", regSubsidyResultPageParam.getMajor());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyResultPageParam.getFeeType())) {
            queryWrapper.eq("a.FEE_TYPE", regSubsidyResultPageParam.getFeeType());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyResultPageParam.getBelongType())) {
            queryWrapper.like("a.BELONG_TYPE", regSubsidyResultPageParam.getBelongType());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyResultPageParam.getIsPass())) {
            queryWrapper.eq("a.IS_PASS", regSubsidyResultPageParam.getIsPass());
        }else{
            queryWrapper.eq("a.IS_PASS", YesOrNoEnum.YES.getValue());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyResultPageParam.getName())) {
            queryWrapper.like("a.NAME", regSubsidyResultPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyResultPageParam.getIdCardNumber())) {
            queryWrapper.like("a.ID_CARD_NUMBER", regSubsidyResultPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(regSubsidyResultPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.NAME",regSubsidyResultPageParam.getSearchKey())
                    .or().like("a.ID_CARD_NUMBER",regSubsidyResultPageParam.getSearchKey())
                    .or().like("a.MAJOR",regSubsidyResultPageParam.getSearchKey())
                    .or().like("b.SN",regSubsidyResultPageParam.getSearchKey())
                    .or().like("b.FIRST_ORG_NAME",regSubsidyResultPageParam.getSearchKey())
                    .or().like("b.SECOND_ORG_NAME",regSubsidyResultPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(regSubsidyResultPageParam.getSortField(), regSubsidyResultPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(regSubsidyResultPageParam.getSortOrder());
            queryWrapper.orderBy(true, regSubsidyResultPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(regSubsidyResultPageParam.getSortField()));
        } else {
            queryWrapper.orderByAsc("a.ID_CARD_NUMBER");
        }
        return regSubsidyResultMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(RegSubsidyResultAddParam regSubsidyResultAddParam) {
        RegSubsidyResult regSubsidyResult = BeanUtil.toBean(regSubsidyResultAddParam, RegSubsidyResult.class);
        this.save(regSubsidyResult);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(RegSubsidyResultEditParam regSubsidyResultEditParam) {
        RegSubsidyResult regSubsidyResult = this.queryEntity(regSubsidyResultEditParam.getId());
        BeanUtil.copyProperties(regSubsidyResultEditParam, regSubsidyResult);
        this.updateById(regSubsidyResult);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<RegSubsidyResultIdParam> regSubsidyResultIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(regSubsidyResultIdParamList, RegSubsidyResultIdParam::getId));
    }

    @Override
    public RegSubsidyResult detail(RegSubsidyResultIdParam regSubsidyResultIdParam) {
        return this.queryEntity(regSubsidyResultIdParam.getId());
    }

    @Override
    public RegSubsidyResult queryEntity(String id) {
        RegSubsidyResult regSubsidyResult = this.getById(id);
        if(ObjectUtil.isEmpty(regSubsidyResult)) {
            throw new CommonException("注册补贴（新版）- 结果反馈不存在，id值为：{}", id);
        }
        return regSubsidyResult;
    }

    @Override
    public List<RegSubsidyResult> listById(RegParam regParam) {
        QueryWrapper<RegSubsidyResult> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RegSubsidyResult::getPersonnelId,regParam.getPersonnelId());
        queryWrapper.lambda().eq(RegSubsidyResult::getIsPass, YesOrNoEnum.YES.getValue());
        return this.list(queryWrapper);
    }
}
