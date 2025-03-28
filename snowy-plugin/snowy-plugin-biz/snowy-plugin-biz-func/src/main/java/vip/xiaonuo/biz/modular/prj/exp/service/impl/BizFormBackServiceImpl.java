package vip.xiaonuo.biz.modular.prj.exp.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormCash;
import vip.xiaonuo.biz.modular.prj.exp.param.*;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormCashService;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormBackVo;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormCashTotal;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormBack;
import vip.xiaonuo.biz.modular.prj.exp.mapper.BizFormBackMapper;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormBackService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 费用报销（二期）- 归还登记Service接口实现类
 *
 * @author yong.ye
 * @date  2025/02/25 09:15
 **/
@Service
@DS("slave_1")
public class BizFormBackServiceImpl extends ServiceImpl<BizFormBackMapper, BizFormBack> implements BizFormBackService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private BizFormBackMapper bizFormBackMapper;

    @Resource
    private BizFormCashService bizFormCashService;


    @Override
    public Page<BizFormBackVo> page(BizFormBackPageParam bizFormBackPageParam) {
        QueryWrapper<BizFormBack> queryWrapper = getBizFormBackQueryWrapper(bizFormBackPageParam);
        //分页
        return bizFormBackMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFormBackAddParam bizFormBackAddParam) {
        BizFormBack bizFormBack = BeanUtil.toBean(bizFormBackAddParam, BizFormBack.class);
        //走审批流程
        String processInstanceId = bizFormBackAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizFormBack.setId(id);
            bizFormBack.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizFormBack.setModelId(modelId);
            bizFormBack.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_FORM_BACK.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizFormBack.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bizFormBack);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFormBackEditParam bizFormBackEditParam) {
        String id = bizFormBackEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizFormBackEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizFormBackEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizFormBackEditParam.setId(id);
            }
        }
        BizFormBack bizFormBack = this.queryEntity(bizFormBackEditParam.getId());
        BeanUtil.copyProperties(bizFormBackEditParam, bizFormBack);
        this.updateById(bizFormBack);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizFormBackIdParam> bizFormBackIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizFormBackIdParamList, BizFormBackIdParam::getId));
    }

    @Override
    public BizFormBack detail(BizFormBackIdParam bizFormBackIdParam) {
        return this.queryEntity(bizFormBackIdParam.getId());
    }

    @Override
    public BizFormBack queryEntity(String id) {
        BizFormBack bizFormBack = this.getById(id);
        if(ObjectUtil.isEmpty(bizFormBack)) {
            throw new CommonException("费用报销（二期）- 归还登记不存在，id值为：{}", id);
        }
        return bizFormBack;
    }

    @Override
    public BizFormCashTotal pageTotal(BizFormBackPageParam bizFormBackPageParam) {
        QueryWrapper<BizFormBack> queryWrapper = getBizFormBackQueryWrapper(bizFormBackPageParam);
        List<BizFormCash> queryList = bizFormBackMapper.getBackCashList(queryWrapper);
        return bizFormCashService.getBizFormCashTotal(queryList,null);
    }





    /**
     * 构造查询
     * @param bizFormBackPageParam
     * @return
     */
    private static QueryWrapper<BizFormBack> getBizFormBackQueryWrapper(BizFormBackPageParam bizFormBackPageParam) {
        QueryWrapper<BizFormBack> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.in("a.STATUS",FlwStatusEnum.COMPLETED_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(bizFormBackPageParam.getExpId())) {
            queryWrapper.eq("a.EXP_ID", bizFormBackPageParam.getExpId());
        }
        if(ObjectUtil.isNotEmpty(bizFormBackPageParam.getExpName())) {
            queryWrapper.like("a.EXP_NAME", bizFormBackPageParam.getExpName());
        }
        if(ObjectUtil.isNotEmpty(bizFormBackPageParam.getCashId())) {
            queryWrapper.eq("a.CASH_ID", bizFormBackPageParam.getCashId());
        }
        if(ObjectUtil.isNotEmpty(bizFormBackPageParam.getCashSn())) {
            queryWrapper.like("a.CASH_SN", bizFormBackPageParam.getCashSn());
        }
        if(ObjectUtil.isNotEmpty(bizFormBackPageParam.getBackType())) {
            queryWrapper.eq("a.BACK_TYPE", bizFormBackPageParam.getBackType());
        }
        if(ObjectUtil.isNotEmpty(bizFormBackPageParam.getStartBackDate()) && ObjectUtil.isNotEmpty(bizFormBackPageParam.getEndBackDate())) {
            queryWrapper.between("a.BACK_DATE", bizFormBackPageParam.getStartBackDate(), bizFormBackPageParam.getEndBackDate());
        }
        if(ObjectUtil.isNotEmpty(bizFormBackPageParam.getSn())) {
            queryWrapper.like("a.SN", bizFormBackPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizFormBackPageParam.getStatus())) {
            queryWrapper.eq("a.STATUS", bizFormBackPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(bizFormBackPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.SN", bizFormBackPageParam.getSearchKey())
                    .or().like("a.CASH_SN", bizFormBackPageParam.getSearchKey())
                    .or().like("a.EXP_NAME", bizFormBackPageParam.getSearchKey()));
        }

        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.in("b.ORG_ID", loginUserDataScope);
        } else {
            queryWrapper.eq("a.EXP_ID", StpUtil.getLoginIdAsString());
        }

        if(ObjectUtil.isAllNotEmpty(bizFormBackPageParam.getSortField(), bizFormBackPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizFormBackPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizFormBackPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizFormBackPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("b.ADVANCE_DATE").orderByDesc("b.SN");
        }
        return queryWrapper;
    }
}
