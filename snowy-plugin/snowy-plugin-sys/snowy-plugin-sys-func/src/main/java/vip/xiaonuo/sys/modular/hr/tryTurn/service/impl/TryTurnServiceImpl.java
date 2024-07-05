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
package vip.xiaonuo.sys.modular.hr.tryTurn.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.sys.modular.hr.tryTurn.entity.TryDuty;
import vip.xiaonuo.sys.modular.hr.tryTurn.entity.TryTurn;
import vip.xiaonuo.sys.modular.hr.tryTurn.mapper.TryTurnMapper;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryTurnAddParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryTurnEditParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryTurnIdParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryTurnPageParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.service.TryTurnService;
import vip.xiaonuo.sys.modular.hr.tryTurn.vo.TryTurnVo;

import javax.annotation.Resource;
import java.util.List;

/**
 * HR - 试用期转正考核Service接口实现类
 *
 * @author yong.ye
 * @date  2024/06/12 10:14
 **/
@Service
public class TryTurnServiceImpl extends ServiceImpl<TryTurnMapper, TryTurn> implements TryTurnService {

    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private TryTurnMapper tryTurnMapper;
    @Override
    public Page<TryTurnVo> page(TryTurnPageParam tryTurnPageParam) {
        QueryWrapper<TryTurn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.in("a.STATUS", FlwStatusEnum.ACTIVE_STATUS.getValue(),FlwStatusEnum.COMPLETED_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(tryTurnPageParam.getType())) {
            queryWrapper.eq("a.TYPE", tryTurnPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(tryTurnPageParam.getApplyType())){
            queryWrapper.eq("b.APPLY_TYPE",tryTurnPageParam.getApplyType());
        }
        if(ObjectUtil.isNotEmpty(tryTurnPageParam.getDutySn())){
            queryWrapper.like("b.SN", tryTurnPageParam.getDutySn());
        }
        if(ObjectUtil.isNotEmpty(tryTurnPageParam.getName())) {
            queryWrapper.like("c.NAME", tryTurnPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(tryTurnPageParam.getIdNumber())) {
            queryWrapper.like("c.ID_NUMBER", tryTurnPageParam.getIdNumber());
        }
        if(ObjectUtil.isNotEmpty(tryTurnPageParam.getDept())) {
            queryWrapper.like("c.DEPT", tryTurnPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(tryTurnPageParam.getDuty())) {
            queryWrapper.like("c.DUTY", tryTurnPageParam.getDuty());
        }
        if(ObjectUtil.isNotEmpty(tryTurnPageParam.getSn())) {
            queryWrapper.like("a.SN", tryTurnPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(tryTurnPageParam.getStatus())) {
            queryWrapper.eq("a.STATUS", tryTurnPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(tryTurnPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.SN",tryTurnPageParam.getSearchKey())
                    .or().like("c.NAME",tryTurnPageParam.getSearchKey())
                    .or().like("c.ID_NUMBER",tryTurnPageParam.getSearchKey())
                    .or().like("c.DEPT",tryTurnPageParam.getSearchKey())
                    .or().like("c.CONTRACT_UNIT",tryTurnPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(tryTurnPageParam.getSortField(), tryTurnPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tryTurnPageParam.getSortOrder());
            queryWrapper.orderBy(true, tryTurnPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tryTurnPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.SN");
        }
        return tryTurnMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TryTurnAddParam tryTurnAddParam) {
        TryTurn tryTurn = BeanUtil.toBean(tryTurnAddParam, TryTurn.class);
        //走审批流程
        String processInstanceId = tryTurnAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            tryTurn.setId(id);
            tryTurn.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            tryTurn.setModelId(modelId);
            tryTurn.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.HR_TRY_TURN.getBizTable(),id,processInstanceId,dbName);
        }else{
            tryTurn.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(tryTurn);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TryTurnEditParam tryTurnEditParam) {
        String id = tryTurnEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(tryTurnEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(tryTurnEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                tryTurnEditParam.setId(id);
            }
        }
        TryTurn tryTurn = this.queryEntity(tryTurnEditParam.getId());
        BeanUtil.copyProperties(tryTurnEditParam, tryTurn);
        this.updateById(tryTurn);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TryTurnIdParam> tryTurnIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tryTurnIdParamList, TryTurnIdParam::getId));
    }

    @Override
    public TryTurnVo detail(TryTurnIdParam tryTurnIdParam) {
        return tryTurnMapper.getDetail(tryTurnIdParam.getId());
    }

    @Override
    public TryTurn queryEntity(String id) {
        TryTurn tryTurn = this.getById(id);
        if(ObjectUtil.isEmpty(tryTurn)) {
            throw new CommonException("HR - 试用期转正考核不存在，id值为：{}", id);
        }
        return tryTurn;
    }
}
