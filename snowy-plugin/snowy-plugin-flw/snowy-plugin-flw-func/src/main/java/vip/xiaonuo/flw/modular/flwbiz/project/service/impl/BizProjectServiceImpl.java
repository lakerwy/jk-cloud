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
package vip.xiaonuo.flw.modular.flwbiz.project.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.trans.service.impl.DictionaryTransService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.flw.modular.flwbiz.project.entity.BizProject;
import vip.xiaonuo.flw.modular.flwbiz.project.mapper.BizProjectMapper;
import vip.xiaonuo.flw.modular.flwbiz.project.param.*;
import vip.xiaonuo.flw.modular.flwbiz.project.service.BizProjectService;
import vip.xiaonuo.flw.modular.flwbiz.project.util.MyUtil;
import vip.xiaonuo.flw.modular.flwbiz.project.vo.BizProjectData;
import vip.xiaonuo.flw.modular.flwbiz.project.vo.BizProjectLikeVo;
import vip.xiaonuo.flw.modular.flwbiz.project.vo.BizProjectVo;
import vip.xiaonuo.flw.modular.relationtable.entity.FormRelationTable;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务表-立项申请Service接口实现类
 *
 * @author mayike
 * @date  2023/06/20 14:26
 **/
@DS("slave_1")
@Service
public class BizProjectServiceImpl extends ServiceImpl<BizProjectMapper, BizProject> implements BizProjectService {

    @Resource
    FormRelationTableService formRelationTableService;

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private BizProjectMapper bizProjectMapper;

    @Resource
    private DictionaryTransService dictionaryTransService;
    @Override
    public Page<BizProject> page(BizProjectPageParam bizProjectPageParam) {
        QueryWrapper<BizProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getStartApplyTime()) && ObjectUtil.isNotEmpty(bizProjectPageParam.getEndApplyTime())) {
            queryWrapper.between("a.APPLY_TIME", bizProjectPageParam.getStartApplyTime(), bizProjectPageParam.getEndApplyTime());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getSn())) {
            queryWrapper.like("a.SN", bizProjectPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getName())) {
            queryWrapper.like("a.NAME", bizProjectPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getSource())) {
            queryWrapper.like("a.SOURCE", bizProjectPageParam.getSource());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.NAME",bizProjectPageParam.getSearchKey())
                    .or().like("a.SN",bizProjectPageParam.getSearchKey()));
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getIsHead())){
            queryWrapper.eq("a.IS_HEAD",bizProjectPageParam.getIsHead());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getType())){
            queryWrapper.eq("a.TYPE",bizProjectPageParam.getType());
        }
        queryWrapper.in("a.STATUS",FlwStatusEnum.ACTIVE_STATUS.getValue(),FlwStatusEnum.COMPLETED_STATUS.getValue());

        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.in("a.DATA_SCOPE_ID", loginUserDataScope);
        } else {
            queryWrapper.eq("a.CREATE_USER", StpUtil.getLoginIdAsString());
        }

        if(ObjectUtil.isAllNotEmpty(bizProjectPageParam.getSortField(), bizProjectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizProjectPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizProjectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizProjectPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.APPLY_TIME");
        }
        Page<BizProject> page = bizProjectMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
        page.getRecords().forEach(bizProject -> bizProject.setHeadText((String) commonCacheOperator.get("trans", "PROJECTCOME_" + bizProject.getHead())));
        return page;
    }

    //@Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizProjectAddParam bizProjectAddParam) {
        BizProject bizProject = BeanUtil.toBean(bizProjectAddParam, BizProject.class);
        String processInstanceId = bizProjectAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizProject.setId(id);
            bizProject.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizProject.setModelId(modelId);
            bizProject.setApplyTime(DateUtil.now());
            bizProject.setApplyUser(StpLoginUserUtil.getLoginUser().getName());
            //将流程实例和业务表关联起来
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            formRelationTableService.save(modelId, BizCommonEnum.PROJECT.getBizTable(),id,processInstanceId,dbName);
        }
        bizProject.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
        this.save(bizProject);
    }

    @DSTransactional
    @Override
    public void edit(BizProjectEditParam bizProjectEditParam) {
        String id = bizProjectEditParam.getId();
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizProjectEditParam.getProcessInstanceId())){
            FormRelationTable formRelationTable = formRelationTableService.getInfoByProcessInstanceId(bizProjectEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id=formRelationTable.getId();
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizProjectEditParam.setId(id);
            }
        }
        BizProject bizProject = this.queryEntity(id);
        checkRight(null,bizProject.getCreateUser());
        BeanUtil.copyProperties(bizProjectEditParam, bizProject);
        //修改部门时，更新dataScopeId
        if(ObjectUtil.isNotEmpty(bizProjectEditParam.get_deptId())){
            bizProject.setDataScopeId(bizProjectEditParam.get_deptId());
        }
        this.updateById(bizProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizProjectIdParam> bizProjectIdParamList) {
        bizProjectIdParamList.forEach(bizProjectIdParam -> checkRight(bizProjectIdParam.getId(),null));
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizProjectIdParamList, BizProjectIdParam::getId));
    }

    public void checkRight(String id,String createUser){
        if(StrUtil.isBlank(createUser)){
            BizProject bizProject = this.queryEntity(id);
            createUser = bizProject.getCreateUser();
        }
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        //仅可编辑自己的
        if(CollUtil.isEmpty(loginUserDataScope)){
            if(!StpLoginUserUtil.getLoginUser().getAccount().equals(createUser)){
                throw new CommonException("您没有权限编辑该条记录");
            }
        }else {
            if(!StpLoginUserUtil.checkRight()){
                throw new CommonException("您没有权限编辑该条记录");
            }
        }
    }

    @Override
    public BizProject detail(BizProjectIdParam bizProjectIdParam) {
        return bizProjectMapper.getDetail(bizProjectIdParam.getId());
    }

    @Override
    public BizProject queryEntity(String id) {
        BizProject bizProject = this.getById(id);
        if(ObjectUtil.isEmpty(bizProject)) {
            throw new CommonException("业务表-立项申请不存在，id值为：{}", id);
        }
        return bizProject;
    }

    @Override
    public Boolean checkName(CheckNameParam checkNameParam) {
        //缩小数据范围(流程状态+项目名称部分字段)
        String preName = MyUtil.getFirstStr(checkNameParam.getName());
        QueryWrapper<BizProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(BizProject::getId,BizProject::getName,BizProject::getProjectSn,BizProject::getStatus);
        queryWrapper.lambda().in(BizProject::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        queryWrapper.lambda().isNotNull(BizProject::getName);
        queryWrapper.lambda().ne(BizProject::getName,"");
        if(ObjectUtil.isNotEmpty(preName)){
            queryWrapper.lambda().like(BizProject::getName,preName);
        }
        List<BizProject> oldList = this.list(queryWrapper);
        if(CollUtil.isEmpty(oldList)){
            return false;
        }
        //循环校验
        String newName = MyUtil.removeSpcChar(checkNameParam.getName());
        oldList.forEach(item->{
            String oldName = MyUtil.removeSpcChar(item.getName());
            if(ObjectUtil.isNotEmpty(checkNameParam.getId())){  //修改
                if(oldName.equals(newName) && !item.getId().equals(checkNameParam.getId())){
                    throw new CommonException("项目名称存在重复，已存在名称为:{}",item.getName());
                }
            }else{ //新增
                if(oldName.equals(newName)){
                    throw new CommonException("项目名称存在重复，已存在名称为:{}",item.getName());
                }
            }
        });
        return false;
    }




    @Override
    public List<BizProjectLikeVo> checkName2(CheckNameParam checkNameParam) {
        List<BizProjectLikeVo> result = new ArrayList<>();
        //缩小数据范围(流程状态+项目名称部分字段)
        String preName = MyUtil.getFirstStr(checkNameParam.getName());
        QueryWrapper<BizProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(BizProject::getId,BizProject::getName,BizProject::getProjectSn,BizProject::getStatus);
        queryWrapper.lambda().in(BizProject::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        queryWrapper.lambda().isNotNull(BizProject::getName);
        queryWrapper.lambda().ne(BizProject::getName,"");
        if(ObjectUtil.isNotEmpty(preName)){
            queryWrapper.lambda().like(BizProject::getName,preName);
        }
        queryWrapper.lambda().orderByAsc(BizProject::getName);
        List<BizProject> oldList = this.list(queryWrapper);
        if(CollUtil.isNotEmpty(oldList)) {
            List<BizProjectData> checkList = BeanUtil.copyToList(oldList, BizProjectData.class);
            checkList.forEach(x->x.setNewName(MyUtil.removeSpcChar(x.getName())));
            //循环校验
            String tmpName = MyUtil.removeSpcChar(checkNameParam.getName());
            String tmpLikeName = MyUtil.getRandomSubstring2(tmpName,0.7f);
            List<BizProjectData> tmpLikeList = new ArrayList<>();
            if (ObjectUtil.isNotEmpty(checkNameParam.getId())){
                tmpLikeList = checkList.stream().filter(x->!x.getId().equals(checkNameParam.getId()) && x.getNewName().contains(tmpLikeName)).collect(Collectors.toList());
            }else{
                tmpLikeList = checkList.stream().filter(x->x.getNewName().contains(tmpLikeName)).collect(Collectors.toList());
            }
            result = BeanUtil.copyToList(tmpLikeList, BizProjectLikeVo.class);
        }
        return result;
    }


    @Override
    public Page<BizProjectVo> epcMpPage(BizProjectPageParam bizProjectPageParam) {
        QueryWrapper<BizProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.in("a.STATUS",FlwStatusEnum.COMPLETED_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getStartApplyTime()) && ObjectUtil.isNotEmpty(bizProjectPageParam.getEndApplyTime())) {
            queryWrapper.between("a.APPLY_TIME", bizProjectPageParam.getStartApplyTime(), bizProjectPageParam.getEndApplyTime());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getSn())) {
            queryWrapper.like("a.SN", bizProjectPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getName())) {
            queryWrapper.like("a.NAME", bizProjectPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getSource())) {
            queryWrapper.like("a.SOURCE", bizProjectPageParam.getSource());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.NAME",bizProjectPageParam.getSearchKey())
                    .or().like("a.SN",bizProjectPageParam.getSearchKey()));
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getIsHead())){
            queryWrapper.eq("a.IS_HEAD",bizProjectPageParam.getIsHead());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getType())){
            queryWrapper.eq("a.TYPE",bizProjectPageParam.getType());
        }
        if(ObjectUtil.isAllNotEmpty(bizProjectPageParam.getSortField(), bizProjectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizProjectPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizProjectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizProjectPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.SN");
        }
        return bizProjectMapper.getEpcMpPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

}
