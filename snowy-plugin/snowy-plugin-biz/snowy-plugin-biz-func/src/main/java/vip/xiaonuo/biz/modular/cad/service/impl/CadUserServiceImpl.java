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
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.cad.entity.CadUserProfession;
import vip.xiaonuo.biz.modular.cad.param.*;
import vip.xiaonuo.biz.modular.cad.service.CadUserProfessionService;
import vip.xiaonuo.biz.modular.cad.vo.CadUserVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cad.entity.CadUser;
import vip.xiaonuo.biz.modular.cad.mapper.CadUserMapper;
import vip.xiaonuo.biz.modular.cad.service.CadUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CAD-用户表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/29 10:40
 **/
@DS("slave_1")
@Service
public class CadUserServiceImpl extends ServiceImpl<CadUserMapper, CadUser> implements CadUserService {

    @Resource
    private CadUserProfessionService cadUserProfessionService;
    @Resource
    private CadUserMapper cadUserMapper;

    @Override
    public Page<CadUserVo> page(CadUserPageParam cadUserPageParam) {
        Page<CadUserVo> result = new Page<>();
        QueryWrapper<CadUser> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(cadUserPageParam.getName())) {
            queryWrapper.lambda().like(CadUser::getName, cadUserPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(cadUserPageParam.getAccount())) {
            queryWrapper.lambda().like(CadUser::getAccount, cadUserPageParam.getAccount());
        }
        if(ObjectUtil.isNotEmpty(cadUserPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(CadUser::getName,cadUserPageParam.getSearchKey())
                    .or().like(CadUser::getAccount,cadUserPageParam.getSearchKey())
                    .or().like(CadUser::getMemo,cadUserPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(cadUserPageParam.getSortField(), cadUserPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cadUserPageParam.getSortOrder());
            queryWrapper.orderBy(true, cadUserPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cadUserPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CadUser::getAccount);
        }
        Page<CadUser> cadUserPage = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<CadUserVo> cadUserVos = BeanUtil.copyToList(cadUserPage.getRecords(), CadUserVo.class);
        if(CollUtil.isNotEmpty(cadUserVos)){
            //根据用户Id，获取其所属专业
            cadUserVos.forEach(x->x.setProfessionList(cadUserMapper.getByUserId(x.getId())));
        }
        result.setCurrent(cadUserPage.getCurrent());
        result.setPages(cadUserPage.getPages());
        result.setTotal(cadUserPage.getTotal());
        result.setSize(cadUserPageParam.getSize());
        result.setRecords(cadUserVos);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CadUserAddParam cadUserAddParam) {
        List<CadUser> oldCadUser = this.list(new QueryWrapper<CadUser>().lambda().eq(CadUser::getAccount,cadUserAddParam.getAccount()));
        if(ObjectUtil.isNotEmpty(oldCadUser)){
            throw new CommonException("{}，账号已存在",cadUserAddParam.getAccount());
        }
        CadUser cadUser = BeanUtil.toBean(cadUserAddParam, CadUser.class);
        this.save(cadUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CadUserEditParam cadUserEditParam) {
        CadUser cadUser = this.queryEntity(cadUserEditParam.getId());
        BeanUtil.copyProperties(cadUserEditParam, cadUser);
        this.updateById(cadUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CadUserIdParam> cadUserIdParamList) {
        // 执行删除
        List<String> ids = CollStreamUtil.toList(cadUserIdParamList, CadUserIdParam::getId);
        this.removeByIds(ids);
        // 删除用户同时删除 用户-专业对应关系
        cadUserProfessionService.remove(new QueryWrapper<CadUserProfession>().lambda().in(CadUserProfession::getCadUserId,ids));
    }

    @Override
    public CadUserVo detail(CadUserIdParam cadUserIdParam) {
        CadUser cadUser = this.queryEntity(cadUserIdParam.getId());
        CadUserVo cadUserVo = BeanUtil.copyProperties(cadUser, CadUserVo.class);
        cadUserVo.setProfessionList(cadUserMapper.getByUserId(cadUserVo.getId()));
        return cadUserVo;
    }

    @Override
    public CadUser queryEntity(String id) {
        CadUser cadUser = this.getById(id);
        if(ObjectUtil.isEmpty(cadUser)) {
            throw new CommonException("CAD-用户表不存在，id值为：{}", id);
        }
        return cadUser;
    }

    @Override
    public void addBatchUser(CadUserBatchAddParam cadUserBatchAddParam) {
        if(ObjectUtil.isNotEmpty(cadUserBatchAddParam.getCadUserAddParamList())){
            List<String> accounts = cadUserBatchAddParam.getCadUserAddParamList().stream().map(x->x.getAccount()).collect(Collectors.toList());
            List<CadUser> oldCadUser = this.list(new QueryWrapper<CadUser>().lambda().in(CadUser::getAccount,accounts));
            if(CollUtil.isNotEmpty(oldCadUser)){
                throw new CommonException("{}，账号已存在", StrUtil.join("，",oldCadUser.stream().map(x->x.getAccount()).distinct().collect(Collectors.toList())));
            }
            List<CadUser> newCadUser = BeanUtil.copyToList(cadUserBatchAddParam.getCadUserAddParamList(),CadUser.class);
            this.saveBatch(newCadUser);
        }
    }

    @Override
    public void setProfession(CadUserProfessionParam cadUserProfessionParam) {
        if(ObjectUtil.isNotEmpty(cadUserProfessionParam.getUserList())){
            //获取历史设置
            List<CadUserProfession> oldUserProfessions = cadUserProfessionService.list(
                    new QueryWrapper<CadUserProfession>().lambda().in(CadUserProfession::getCadUserId,cadUserProfessionParam.getUserList()));
            //待新增|待更新(关系表不存在)|待删除
            List<CadUserProfession> addList = new ArrayList<>();
            List<CadUserProfession> removeList = new ArrayList<>();
            for (String userId:cadUserProfessionParam.getUserList()) {
                List<CadUserProfession> tmps = oldUserProfessions.stream().filter(x->x.getCadUserId().equals(userId)).collect(Collectors.toList());
                //历史有设置记录，需处理(新增|删除)
                if(ObjectUtil.isNotEmpty(tmps)){
                    //历史设置的专业Ids
                    List<String> tmpProfessionIds = tmps.stream().map(x->x.getCadProfessionId()).collect(Collectors.toList());
                    List<String> tmpAddProfessionIds = cadUserProfessionParam.getProfessionList().stream().filter(x->!tmpProfessionIds.contains(x)).collect(Collectors.toList());
                    List<CadUserProfession> tmpRemoves = tmps.stream().filter(x->!cadUserProfessionParam.getProfessionList().contains(x.getCadProfessionId())).collect(Collectors.toList());
                    if(CollUtil.isNotEmpty(tmpAddProfessionIds)){
                        tmpAddProfessionIds.forEach(x->{
                            CadUserProfession cadUserProfession = new CadUserProfession();
                            cadUserProfession.setCadProfessionId(x);
                            cadUserProfession.setCadUserId(userId);
                            addList.add(cadUserProfession);
                        });
                    }
                    if(CollUtil.isNotEmpty(tmpRemoves)){
                        removeList.addAll(tmpRemoves);
                    }
                }else{
                    cadUserProfessionParam.getProfessionList().forEach(x->{
                        CadUserProfession cadUserProfession = new CadUserProfession();
                        cadUserProfession.setCadProfessionId(x);
                        cadUserProfession.setCadUserId(userId);
                        addList.add(cadUserProfession);
                    });
                }
            }
            if(CollUtil.isNotEmpty(addList)){
                cadUserProfessionService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                cadUserProfessionService.removeBatchByIds(removeList);
            }
        }
    }

    @Override
    public List<CadUser> userByProfession(String professionId) {
         return cadUserMapper.getUserByProfession(professionId);
    }

    @Override
    public void changePassword(CadChangePasswordParam cadChangePasswordParam) {
        //插件端校验密码，并修改
        CadUser cadUser = this.queryEntity(cadChangePasswordParam.getId());
        if(!cadUser.getPassword().equals(cadChangePasswordParam.getOldPassword())){
            throw new CommonException("原密码错误");
        }
        cadUser.setPassword(cadChangePasswordParam.getNewPassword());
        this.updateById(cadUser);
    }

    @Override
    public CadUser login(CadLoginParam cadLoginParam) {
        return this.getOne(new QueryWrapper<CadUser>().lambda()
                .eq(CadUser::getAccount,cadLoginParam.getAccount()).eq(CadUser::getPassword,cadLoginParam.getPassword()));
    }
}
