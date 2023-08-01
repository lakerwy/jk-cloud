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
package vip.xiaonuo.sys.modular.hr.fileMoves.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FormRelationTableApi;
import vip.xiaonuo.sys.modular.hr.fileMoves.entity.PersonnelFileMoves;
import vip.xiaonuo.sys.modular.hr.fileMoves.service.PersonnelFileMovesService;
import vip.xiaonuo.sys.modular.hr.fileMoves.mapper.PersonnelFileMovesMapper;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesAddParam;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesEditParam;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesIdParam;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesPageParam;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.fileMoves.vo.PersonnelFileMovesVo;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务表-人员异动主表Service接口实现类
 *
 * @author mayike
 * @date  2023/07/10 16:56
 **/
@Service
public class PersonnelFileMovesServiceImpl extends ServiceImpl<PersonnelFileMovesMapper, PersonnelFileMoves> implements PersonnelFileMovesService {

    @Resource
    CommonCacheOperator commonCacheOperator;
    @Override
    public Page<PersonnelFileMoves> page(PersonnelFileMovesPageParam personnelFileMovesPageParam) {
        QueryWrapper<PersonnelFileMoves> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(personnelFileMovesPageParam.getSortField(), personnelFileMovesPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(personnelFileMovesPageParam.getSortOrder());
            queryWrapper.orderBy(true, personnelFileMovesPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(personnelFileMovesPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PersonnelFileMoves::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PersonnelFileMovesAddParam personnelFileMovesAddParam) {
        PersonnelFileMoves personnelFileMoves = BeanUtil.toBean(personnelFileMovesAddParam, PersonnelFileMoves.class);
        String processInstanceId = personnelFileMovesAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            personnelFileMoves.setId(id);
            personnelFileMoves.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            personnelFileMoves.setModelId(modelId);
            personnelFileMoves.setApplyTime(DateUtil.date());
            personnelFileMoves.setApplyUser(StpLoginUserUtil.getLoginUser().getName());
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            formRelationTableApi.save(modelId, BizCommonEnum.PERSONNEL_FILE_MOVES.getBizTable(),id,processInstanceId,dbName);
        }
        personnelFileMoves.setApplyUser(StpLoginUserUtil.getLoginUser().getName());
        personnelFileMoves.setApplyTime(DateUtil.date());
        this.save(personnelFileMoves);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PersonnelFileMovesEditParam personnelFileMovesEditParam) {
        String id = personnelFileMovesEditParam.getId();
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(personnelFileMovesEditParam.getProcessInstanceId())){
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            JSONObject formRelationTable = formRelationTableApi.getInfoByProcessInstanceId(personnelFileMovesEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id=formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                personnelFileMovesEditParam.setId(id);
            }
        }
        PersonnelFileMoves personnelFileMoves = this.queryEntity(personnelFileMovesEditParam.getId());
        BeanUtil.copyProperties(personnelFileMovesEditParam, personnelFileMoves);
        this.updateById(personnelFileMoves);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PersonnelFileMovesIdParam> personnelFileMovesIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(personnelFileMovesIdParamList, PersonnelFileMovesIdParam::getId));
    }

    @Override
    public PersonnelFileMoves detail(PersonnelFileMovesIdParam personnelFileMovesIdParam) {
//        return this.queryEntity(personnelFileMovesIdParam.getId());
        return baseMapper.selectById(personnelFileMovesIdParam.getId());
    }

    @Override
    public PersonnelFileMoves queryEntity(String id) {
        PersonnelFileMoves personnelFileMoves = this.getById(id);
        if(ObjectUtil.isEmpty(personnelFileMoves)) {
            throw new CommonException("业务表-人员异动主表不存在，id值为：{}", id);
        }
        return personnelFileMoves;
    }

    @Override
    public void deleteByFileId(String fileId) {
        baseMapper.deleteByFileId(fileId);
    }

    @Override
    public void deleteNewFileId(List<String> ids) {
        QueryWrapper<PersonnelFileMoves> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(PersonnelFileMoves::getFileId,ids);
        baseMapper.delete(queryWrapper);
    }

    @Override
    public Page<PersonnelFileMovesVo> pageList(PersonnelFileMovesPageParam personnelFileMovesPageParam) {
        QueryWrapper<PersonnelFileMoves> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(personnelFileMovesPageParam.getMoveType())){
            queryWrapper.lambda().eq(PersonnelFileMoves::getMoveType,personnelFileMovesPageParam.getMoveType());
        }
        if(ObjectUtil.isNotEmpty(personnelFileMovesPageParam.getSearchKey())){
            queryWrapper.and(wrapper ->wrapper.like("b.`NAME`",personnelFileMovesPageParam.getSearchKey())
                    .or().like("a.ORIG_DEPT",personnelFileMovesPageParam.getSearchKey())
                    .or().like("a.SN",personnelFileMovesPageParam.getSearchKey())
            );
        }
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");

        if(ObjectUtil.isAllNotEmpty(personnelFileMovesPageParam.getSortField(), personnelFileMovesPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(personnelFileMovesPageParam.getSortOrder());
            queryWrapper.orderBy(true, personnelFileMovesPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(personnelFileMovesPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(PersonnelFileMoves::getSn);
        }
        return baseMapper.pageList(CommonPageRequest.defaultPage(), queryWrapper);
    }
}
