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
package vip.xiaonuo.sys.modular.hr.personnel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SysLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.sys.modular.hr.hrCenter.entity.PersonnelFileCert;
import vip.xiaonuo.sys.modular.hr.hrCenter.service.PersonnelFileCertService;
import vip.xiaonuo.sys.modular.hr.fileMoves.entity.PersonnelFileMoves;
import vip.xiaonuo.sys.modular.hr.fileMoves.service.PersonnelFileMovesService;
import vip.xiaonuo.sys.modular.hr.hrCenter.entity.PersonnelFileRegister;
import vip.xiaonuo.sys.modular.hr.hrCenter.service.PersonnelFileRegisterService;
import vip.xiaonuo.sys.modular.hr.hrCenter.entity.PersonnelFileTitle;
import vip.xiaonuo.sys.modular.hr.hrCenter.service.PersonnelFileTitleService;
import vip.xiaonuo.sys.modular.hr.personnel.param.*;
import vip.xiaonuo.sys.modular.hr.personnel.vo.PersonnelInfo;
import vip.xiaonuo.sys.modular.hr.personnel.vo.PersonnelListVo;
import vip.xiaonuo.sys.modular.hr.personnel.mapper.PersonnelMapper;
import vip.xiaonuo.sys.modular.hr.personnel.service.PersonnelService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.personnel.entity.Personnel;
import vip.xiaonuo.sys.modular.hr.personnel.vo.PersonnelStatsVo;
import vip.xiaonuo.sys.modular.hr.personnel.vo.PersonnelVo;
import vip.xiaonuo.sys.modular.hr.salary.util.ComputeUtil;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;
import vip.xiaonuo.sys.modular.position.param.SysPositionAddParam;
import vip.xiaonuo.sys.modular.position.service.SysPositionService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.param.SysUserAddParam;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import static vip.xiaonuo.common.consts.CacheConstant.ORG_FIRST_CODE_CACHE_KEY;

/**
 * 业务表-人事档案Service接口实现类
 *
 * @author mayike
 * @date 2023/07/10 17:28
 **/
@Service
public class PersonnelServiceImpl extends ServiceImpl<PersonnelMapper, Personnel> implements PersonnelService {

    @Resource
    private PersonnelMapper personnelMapper;

    @Resource
    private PersonnelFileCertService personnelFileCertService;

    @Resource
    private PersonnelFileMovesService personnelFileMovesService;

    @Resource
    private PersonnelFileTitleService personnelFileTitleService;

    @Resource
    private PersonnelFileRegisterService personnelFileRegisterService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysPositionService sysPositionService;

    @Resource
    private SysOrgService sysOrgService;

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<Personnel> page(PersonnelPageParam personnelPageParam) {
        QueryWrapper<Personnel> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(personnelPageParam.getName())) {
            queryWrapper.lambda().like(Personnel::getName, personnelPageParam.getName());
        }
        if (ObjectUtil.isNotEmpty(personnelPageParam.getSex())) {
            queryWrapper.lambda().eq(Personnel::getSex, personnelPageParam.getSex());
        }
        if (ObjectUtil.isNotEmpty(personnelPageParam.getDept())) {
            queryWrapper.lambda().like(Personnel::getDept, personnelPageParam.getDept());
        }
        if (ObjectUtil.isNotEmpty(personnelPageParam.getDuty())) {
            queryWrapper.lambda().like(Personnel::getDuty, personnelPageParam.getDuty());
        }
        if (ObjectUtil.isNotEmpty(personnelPageParam.getStartHireDate()) && ObjectUtil.isNotEmpty(personnelPageParam.getEndHireDate())) {
            queryWrapper.lambda().between(Personnel::getHireDate, personnelPageParam.getStartHireDate(), personnelPageParam.getEndHireDate());
        }
        if (ObjectUtil.isNotEmpty(personnelPageParam.getStartPositiveTime()) && ObjectUtil.isNotEmpty(personnelPageParam.getEndPositiveTime())) {
            queryWrapper.lambda().between(Personnel::getPositiveTime, personnelPageParam.getStartPositiveTime(), personnelPageParam.getEndPositiveTime());
        }
        if(ObjectUtil.isNotEmpty(personnelPageParam.getStatus())){
            queryWrapper.lambda().eq(Personnel::getStatus,personnelPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(personnelPageParam.getEmployNature())){
            queryWrapper.lambda().eq(Personnel::getEmployNature,personnelPageParam.getEmployNature());
        }
        if(ObjectUtil.isNotEmpty(personnelPageParam.getSearchKey())){
            queryWrapper.and(q->q.lambda().like(Personnel::getName,personnelPageParam.getSearchKey())
                    .or().like(Personnel::getFileNo,personnelPageParam.getSearchKey())
                    .or().like(Personnel::getDept,personnelPageParam.getSearchKey())
                    .or().like(Personnel::getDuty,personnelPageParam.getSearchKey())
                    .or().like(Personnel::getIdNumber,personnelPageParam.getSearchKey())
                    .or().like(Personnel::getPhone,personnelPageParam.getSearchKey()));
        }

        if (ObjectUtil.isAllNotEmpty(personnelPageParam.getSortField(), personnelPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(personnelPageParam.getSortOrder());
            queryWrapper.orderBy(true, personnelPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(personnelPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Personnel::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PersonnelAddParam personnelAddParam) {
        //新增基本信息
        //签约信息
        //异动信息
        //职称信息
        //证书信息
        //新增岗位
        SysPositionAddParam positionAddParam = new SysPositionAddParam();
        positionAddParam.setName(personnelAddParam.getDuty());
        positionAddParam.setOrgId(personnelAddParam.getDeptId());
        positionAddParam.setCategory("LOW");
        positionAddParam.setSortCode(99);
        String dutyId = sysPositionService.newAdd(positionAddParam).getId();
        //基本信息
        Personnel personnel = BeanUtil.toBean(personnelAddParam, Personnel.class);
        personnel.setDutyId(dutyId);
        personnel.setStatus("0");
        //按照入职时间获取最后一条
        Long max = Long.parseLong(Objects.requireNonNull(list().stream().map(Personnel::getFileNo).filter(ObjectUtil::isNotEmpty).max(Comparator.naturalOrder()).orElse("600000")))+1;
        personnel.setFileNo(String.valueOf(max));

        /**
         * 先处理 SysUser表逻辑，反更新userId
         */
        String userId = "";
        SysUser user = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, personnelAddParam.getPhone()));
        if(ObjectUtil.isEmpty(user)){
            SysUserAddParam sysUserAddParam = new SysUserAddParam();
            sysUserAddParam.setAccount(personnelAddParam.getPhone());
            sysUserAddParam.setOrgId(personnelAddParam.getDeptId());
            sysUserAddParam.setName(personnelAddParam.getName());
            sysUserAddParam.setPhone(personnelAddParam.getPhone());
            sysUserAddParam.setPositionId(personnel.getDutyId());
            sysUserAddParam.setEmpNo(personnel.getFileNo());
            sysUserAddParam.setGender(StrUtil.isNotBlank(personnel.getSex())?personnel.getSex():"男");
            if(ObjectUtil.isNotEmpty(personnelAddParam.getHireDate())){
                sysUserAddParam.setEntryDate(DateUtil.formatDate(personnelAddParam.getHireDate()));
            }
            userId = sysUserService.addAndGrantRole(sysUserAddParam);
        }else {
            SysUserAddParam sysUserAddParam = new SysUserAddParam();
            sysUserAddParam.setOrgId(personnelAddParam.getDeptId());
            sysUserAddParam.setName(personnelAddParam.getName());
            sysUserAddParam.setPhone(personnelAddParam.getPhone());
            sysUserAddParam.setPositionId(personnel.getDutyId());
            sysUserAddParam.setEmpNo(personnel.getFileNo());
            if(ObjectUtil.isNotEmpty(personnel.getSex())){
                sysUserAddParam.setGender(personnel.getSex());
            }
            if(ObjectUtil.isNotEmpty(personnelAddParam.getHireDate())){
                sysUserAddParam.setEntryDate(DateUtil.formatDate(personnelAddParam.getHireDate()));
            }
            SysUser sysUser = BeanUtil.toBean(sysUserAddParam, SysUser.class);
            sysUser.setId(user.getId());
            sysUserService.updateById(sysUser);
            userId = user.getId();
        }
        personnel.setUserId(userId); //反更新用户Id
        this.save(personnel);

        // 处理学历、证书等
        if (CollUtil.isNotEmpty(personnelAddParam.getPersonnelFileCertList())) {
            List<PersonnelFileCert> collect = personnelAddParam.getPersonnelFileCertList().stream().map(personnelFileCertAddParam -> BeanUtil.toBean(personnelFileCertAddParam, PersonnelFileCert.class)).collect(Collectors.toList());
            collect.forEach(personnelFileCert -> personnelFileCert.setFileId(personnel.getId()));
            personnelFileCertService.saveBatch(collect);
        }
        if (CollUtil.isNotEmpty(personnelAddParam.getPersonnelFileMovesList())) {
            List<PersonnelFileMoves> collect = personnelAddParam.getPersonnelFileMovesList().stream().map(personnelFileMovesAddParam -> BeanUtil.toBean(personnelFileMovesAddParam, PersonnelFileMoves.class)).collect(Collectors.toList());
            collect.forEach(personnelFileMoves -> personnelFileMoves.setFileId(personnel.getId()));
            personnelFileMovesService.saveBatch(collect);
        }
        if (CollUtil.isNotEmpty(personnelAddParam.getPersonnelFileTitleList())) {
            List<PersonnelFileTitle> collect = personnelAddParam.getPersonnelFileTitleList().stream().map(personnelFileTitleAddParam -> BeanUtil.toBean(personnelFileTitleAddParam, PersonnelFileTitle.class)).collect(Collectors.toList());
            collect.forEach(personnelFileTitle -> personnelFileTitle.setFileId(personnel.getId()));
            personnelFileTitleService.saveBatch(collect);
        }
        if (CollUtil.isNotEmpty(personnelAddParam.getPersonnelFileRegisterList())) {
            List<PersonnelFileRegister> collect = personnelAddParam.getPersonnelFileRegisterList().stream().map(personnelFileRegisterAddParam -> BeanUtil.toBean(personnelFileRegisterAddParam, PersonnelFileRegister.class)).collect(Collectors.toList());
            collect.forEach(personnelFileRegister -> personnelFileRegister.setFileId(personnel.getId()));
            personnelFileRegisterService.saveBatch(collect);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PersonnelEditParam personnelEditParam) {
        Personnel personnel = this.queryEntity(personnelEditParam.getId());

        //编辑数据与源数据对比，判断职位是否需要新增 - 新增岗位
        String dutyId = personnel.getDutyId();
        if(!Objects.equals(personnel.getDeptId(), personnelEditParam.getDeptId()) || !Objects.equals(personnel.getDuty(), personnelEditParam.getDuty())){
            SysPositionAddParam positionAddParam = new SysPositionAddParam();
            positionAddParam.setName(personnelEditParam.getDuty());
            positionAddParam.setOrgId(personnelEditParam.getDeptId());
            positionAddParam.setCategory("LOW");
            positionAddParam.setSortCode(99);
            dutyId = sysPositionService.newAdd(positionAddParam).getId();
        }
        BeanUtil.copyProperties(personnelEditParam, personnel);
        personnelFileRegisterService.deleteByFileId(personnel.getId());
        personnelFileMovesService.deleteByFileId(personnel.getId());
        personnelFileTitleService.deleteByFileId(personnel.getId());
        personnelFileCertService.deleteByFileId(personnel.getId());
        personnel.setDutyId(dutyId);
        this.updateById(personnel);
        if (CollUtil.isNotEmpty(personnelEditParam.getPersonnelFileCertList())) {
            List<PersonnelFileCert> collect = personnelEditParam.getPersonnelFileCertList().stream().map(param -> BeanUtil.toBean(param, PersonnelFileCert.class)).collect(Collectors.toList());
            collect.forEach(personnelFileCert -> personnelFileCert.setFileId(personnel.getId()));
            personnelFileCertService.saveBatch(collect);
        }
        if (CollUtil.isNotEmpty(personnelEditParam.getPersonnelFileMovesList())) {
            List<PersonnelFileMoves> collect = personnelEditParam.getPersonnelFileMovesList().stream().map(param -> BeanUtil.toBean(param, PersonnelFileMoves.class)).collect(Collectors.toList());
            collect.forEach(personnelFileMoves -> personnelFileMoves.setFileId(personnel.getId()));
            personnelFileMovesService.saveBatch(collect);
        }
        if (CollUtil.isNotEmpty(personnelEditParam.getPersonnelFileTitleList())) {
            List<PersonnelFileTitle> collect = personnelEditParam.getPersonnelFileTitleList().stream().map(param -> BeanUtil.toBean(param, PersonnelFileTitle.class)).collect(Collectors.toList());
            collect.forEach(personnelFileTitle -> personnelFileTitle.setFileId(personnel.getId()));
            personnelFileTitleService.saveBatch(collect);
        }
        if (CollUtil.isNotEmpty(personnelEditParam.getPersonnelFileRegisterList())) {
            List<PersonnelFileRegister> collect = personnelEditParam.getPersonnelFileRegisterList().stream().map(param -> BeanUtil.toBean(param, PersonnelFileRegister.class)).collect(Collectors.toList());
            collect.forEach(personnelFileRegister -> personnelFileRegister.setFileId(personnel.getId()));
            personnelFileRegisterService.saveBatch(collect);
        }

        /**
         * 新增花名册时，反更新了userId，就无需根据手机号反查了
         */
        SysUser user = sysUserService.getById(personnel.getUserId());
        if(ObjectUtil.isNotEmpty(user)){
            if(ObjectUtil.isNotEmpty(personnel.getHireDate())){
                user.setEntryDate(DateUtil.formatDate(personnel.getHireDate()));
            }
            if(ObjectUtil.isNotEmpty(personnel.getDeptId())){
                user.setOrgId(personnel.getDeptId());
            }
            if(ObjectUtil.isNotEmpty(personnel.getDutyId())) {
                user.setPositionId(personnel.getDutyId());
            }
            if(ObjectUtil.isNotEmpty(personnel.getHireDate()) || ObjectUtil.isNotEmpty(personnel.getDutyId())) {
                sysUserService.updateById(user);
            }
        }

//        /** 增加一个逻辑：编辑时，若组织架构中不存在该账号，则新增 **/
//        SysUser user = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, personnel.getPhone()));
//        if(ObjectUtil.isEmpty(user)){
//            SysUserAddParam sysUserAddParam = new SysUserAddParam();
//            sysUserAddParam.setAccount(personnel.getPhone());
//            sysUserAddParam.setOrgId(personnel.getDeptId());
//            sysUserAddParam.setName(personnel.getName());
//            sysUserAddParam.setPhone(personnel.getPhone());
//            sysUserAddParam.setPositionId(personnel.getDutyId());
//            sysUserAddParam.setEmpNo(personnel.getFileNo());
//            sysUserAddParam.setGender(StrUtil.isNotBlank(personnel.getSex())?personnel.getSex():"男");
//            if(ObjectUtil.isNotEmpty(personnel.getHireDate())){
//                // 创建DateTimeFormatter对象，并定义格式
//                sysUserAddParam.setEntryDate(DateUtil.formatDate(personnel.getHireDate()));
//            }
//            sysUserService.addAndGrantRole(sysUserAddParam);
//        }else{
//            if(ObjectUtil.isNotEmpty(personnel.getHireDate())){
//                user.setEntryDate(DateUtil.formatDate(personnel.getHireDate()));
//            }
//            if(ObjectUtil.isNotEmpty(personnel.getDutyId())) {
//                user.setPositionId(personnel.getDutyId());
//            }
//            if(ObjectUtil.isNotEmpty(personnel.getHireDate()) || ObjectUtil.isNotEmpty(personnel.getDutyId())) {
//                sysUserService.updateById(user);
//            }
//        }
        //已修改为离职 ，删除系统账号【禁用】
        if(personnelEditParam.getStatus().equals("1")){
//            SysUser sysUser = sysUserService.getById(personnelEditParam.getSysUserId());
            SysUser sysUser = sysUserService.getById(personnel.getUserId());
            if(ObjectUtil.isNotEmpty(sysUser)) {
                sysUser.setUserStatus("DISABLED");
                sysUserService.updateById(sysUser);
            }
            PersonnelFileMoves personnelFileMoves = new PersonnelFileMoves();
            personnelFileMoves.setFileId(personnelEditParam.getId());
            personnelFileMoves.setMoveType("2");
            personnelFileMoves.setMoveItem("离职");
            personnelFileMoves.setStatus("1");
            //离职日期
            Date leaveDate = null;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                leaveDate = formatter.parse(personnelEditParam.getLeaveDate());
            } catch (Exception e) {
                leaveDate = new Date();
            }
            DateUtil.parse(personnelEditParam.getLeaveDate());
            personnelFileMoves.setLivetime(leaveDate);
            personnelFileMoves.setOptDate(new Date());
            personnelFileMoves.setOrigDept(personnelEditParam.getDept());
            personnelFileMoves.setOrigDuty(personnelEditParam.getDuty());
            personnelFileMoves.setApplyTime(new Date());
            personnelFileMoves.setApplyUser(personnelEditParam.getName());
            personnelFileMovesService.save(personnelFileMoves);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PersonnelIdParam> personnelIdParamList) {
        // 执行删除
        List<String> ids = CollStreamUtil.toList(personnelIdParamList, PersonnelIdParam::getId);
        List<Personnel> personals = listByIds(ids);
        this.removeByIds(ids);
        personnelFileCertService.deleteNewFileId(ids);
        personnelFileTitleService.deleteNewFileId(ids);
        personnelFileMovesService.deleteNewFileId(ids);
        personnelFileRegisterService.deleteNewFileId(ids);
        log.debug("测试删除----"+ids.toString());
//        Set<String> collect = personals.stream().map(Personnel::getPhone).collect(Collectors.toSet());
//        List<SysUser> list = sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getAccount, collect));
//        if(CollUtil.isNotEmpty(list)){
//            List<String> collect1 = list.stream().map(SysUser::getId).collect(Collectors.toList());
//            sysUserService.handleDelete(collect1);
//        }
        /**
         * 新增花名册时，反更新了userId，不用反查
         */
        List<String> userIdList = personals.stream().filter(x->ObjectUtil.isNotEmpty(x.getUserId())).map(Personnel::getUserId).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(userIdList)){
            sysUserService.handleDelete(userIdList);
        }
    }

    @Override
    public PersonnelVo detail(PersonnelIdParam personnelIdParam) {
        Personnel personnel = null;
        String id = null;
        if(ObjectUtil.isNotEmpty(personnelIdParam.getId())){
            id = personnelIdParam.getId();
            personnel = this.queryEntity(id);
        }else {
            if(ObjectUtil.isNotEmpty(personnelIdParam.getFileNo())){
                id = personnelIdParam.getFileNo();
            }else {
                id = StpLoginUserUtil.getLoginUser().getEmpNo();
            }
            personnel = getOne(new LambdaQueryWrapper<Personnel>().eq(Personnel::getFileNo, id));
        }
        if(ObjectUtil.isEmpty(personnel)){
            throw new CommonException("该员工信息不存在，id值为：{}",id);
        }
        PersonnelVo personnelVo = BeanUtil.toBean(personnel, PersonnelVo.class);
        if (ObjectUtil.isNotEmpty(personnelVo.getHireDate())) {
            //入职司龄
            personnelVo.setEnterage(dayComparePrecise(new Date(), personnelVo.getHireDate()));
        }
        if (ObjectUtil.isNotEmpty(personnelVo.getPositiveTime())) {
            //转正司龄
            if(!personnelVo.getPositiveTime().after(new Date())){
                personnelVo.setPositiveAge(dayComparePrecise(new Date(), personnelVo.getPositiveTime()));
            }
        }

        if (ObjectUtil.isNotEmpty(personnelVo.getBirth())) {
            //年纪
            personnelVo.setAge(Period.between(LocalDate.parse(DateUtil.formatDate(personnelVo.getBirth())),LocalDate.parse(DateUtil.formatDate(new Date()))).getYears());
        }
        SysOrg sysOrg = sysOrgService.getById(personnelVo.getDeptId());
        if(ObjectUtil.isNotEmpty(sysOrg)){
            personnelVo.setDept(sysOrg.getName());
            String directorId = sysOrg.getDirectorId();
            if(ObjectUtil.isNotEmpty(directorId)){
                SysLoginUser user = sysUserService.getUserById(directorId);
                if(ObjectUtil.isNotEmpty(user)){
                    personnelVo.setLeader(user.getName());
                    personnelVo.setDirector(user.getName());
                }
//                SysUser one = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, personnel.getPhone()).eq(SysUser::getDeleteFlag,"NOT_DELETE"));
                SysUser one = sysUserService.getById(personnel.getUserId());
                if(ObjectUtil.isNotEmpty(one)){
//                    personnelVo.setSysUserId(one.getId());
                    if(directorId.equals(one.getId())){
                        personnelVo.setIsLeader("1");
                    }
                }
            }
        }
//        if(StrUtil.isNotEmpty(personnelVo.getDutyId())){
//            SysPosition sysPosition = sysPositionService.getById(personnelVo.getDutyId());
//            personnelVo.setDuty(sysPosition.getName());
//        }

        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("FILE_ID", personnel.getId());
        columnMap.put("DELETE_FLAG", "NOT_DELETE");
        personnelVo.setPersonnelFileCertList(personnelFileCertService.listByMap(columnMap));
        personnelVo.setPersonnelFileMovesList(personnelFileMovesService.listByMap(columnMap));
        personnelVo.setPersonnelFileTitleList(personnelFileTitleService.listByMap(columnMap));
        List<PersonnelFileRegister> personnelFileRegisters = personnelFileRegisterService.listByMap(columnMap);
        personnelVo.setPersonnelFileRegisterList(personnelFileRegisters);
        //合同续签次数
        personnelVo.setRenewTime(personnelFileRegisters.size()>=1?personnelFileRegisters.size()-1:0);
        return personnelVo;
    }

    @Override
    public Personnel queryEntity(String id) {
        Personnel personnel = this.getById(id);
        if (ObjectUtil.isEmpty(personnel)) {
            throw new CommonException("业务表-人事档案不存在，id值为：{}", id);
        }
        return personnel;
    }

    @Override
    public Page<PersonnelListVo> pageList(PersonnelPageParam personnelPageParam) {
        QueryWrapper<PersonnelListVo> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(personnelPageParam.getName())) {
            queryWrapper.like("a.`NAME`", personnelPageParam.getName());
        }
        if (ObjectUtil.isNotEmpty(personnelPageParam.getSex())) {
            queryWrapper.eq("a.SEX", personnelPageParam.getSex());
        }
        if (ObjectUtil.isNotEmpty(personnelPageParam.getStartHireDate())) {
            queryWrapper.ge("a.HIRE_DATE", personnelPageParam.getStartHireDate());
        }
        if (ObjectUtil.isNotEmpty(personnelPageParam.getStartPositiveTime())) {
            queryWrapper.ge("a.POSITIVE_TIME", personnelPageParam.getStartPositiveTime());
        }
        if (ObjectUtil.isNotEmpty(personnelPageParam.getDept())) {
            queryWrapper.like("b.`NAME`", personnelPageParam.getDept());
        }
        if (ObjectUtil.isNotEmpty(personnelPageParam.getDuty())) {
            queryWrapper.like("c.`NAME`", personnelPageParam.getDuty());
        }
        if(ObjectUtil.isNotEmpty(personnelPageParam.getStatus())){
            queryWrapper.eq("a.`STATUS`", personnelPageParam.getStatus());
        }else {
            queryWrapper.eq("a.`STATUS`", "0");
        }
        queryWrapper.eq("a.DELETE_FLAG", "NOT_DELETE");

        if (ObjectUtil.isNotEmpty(personnelPageParam.getEmployNature())) {
            queryWrapper.eq("a.EMPLOY_NATURE", personnelPageParam.getEmployNature());
        }

        if (ObjectUtil.isAllNotEmpty(personnelPageParam.getSortField(), personnelPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(personnelPageParam.getSortOrder());
            queryWrapper.orderBy(true, personnelPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(personnelPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.FILE_NO");
        }
        Page<PersonnelListVo> pageList = personnelMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
        List<PersonnelListVo> records = pageList.getRecords();
        records.forEach(personnelListVo ->
        {
            if (ObjectUtil.isNotEmpty(personnelListVo.getHireDate())) {
                personnelListVo.setEnterage(dayComparePrecise(new Date(), personnelListVo.getHireDate()));
            }
            if (ObjectUtil.isNotEmpty(personnelListVo.getPositiveTime())) {
                if(!personnelListVo.getPositiveTime().after(new Date())){
                    personnelListVo.setPositiveAge(dayComparePrecise(new Date(), personnelListVo.getPositiveTime()));
                }
            }
        });
        return  pageList.setRecords(records);
    }

    private static String dayComparePrecise(Date date1, Date date2) {
        Period period = Period.between(LocalDate.parse(DateUtil.formatDate(date1)), LocalDate.parse(DateUtil.formatDate(date2)));

        return (period.getYears() + "年" +
                period.getMonths() + "月" +
                period.getDays() + "日").replaceAll("-","") ;
    }

    @Override
    public List<PersonnelStatsVo> getStatsResult(PersonnelStatsParam param){
         return  baseMapper.getStatsResult(param);
    }

    @Override
    public PersonnelVo info(PersonnelIdParam personnelIdParam) {

        Personnel personnel = null;
        String id = null;
        if(ObjectUtil.isNotEmpty(personnelIdParam.getId())){
            id = personnelIdParam.getId();
            personnel = this.queryEntity(id);
        }else {
            if(ObjectUtil.isNotEmpty(personnelIdParam.getFileNo())){
                id = personnelIdParam.getFileNo();
            }else {
                id = StpLoginUserUtil.getLoginUser().getEmpNo();
            }
            personnel = getOne(new LambdaQueryWrapper<Personnel>().eq(Personnel::getFileNo, id));
        }
        //Personnel personnel = this.queryEntity(personnelIdParam.getId());
        PersonnelVo personnelVo = BeanUtil.toBean(personnel, PersonnelVo.class);
//        SysUser one = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, personnel.getPhone()).eq(SysUser::getDeleteFlag,"NOT_DELETE"));
//        if(ObjectUtil.isNotEmpty(one)){
//            personnelVo.setSysUserId(one.getId());
//        }
        SysUser one = sysUserService.getById(personnel.getUserId());
        String deptId = personnel.getDeptId();
        SysOrg sysOrg = sysOrgService.getById(deptId);
        personnelVo.setIsLeader("0");
        if(ObjectUtil.isNotEmpty(sysOrg)){
            personnelVo.setDept(sysOrg.getName());
            String directorId = sysOrg.getDirectorId();
            if(ObjectUtil.isNotEmpty(directorId)){
                SysLoginUser user = sysUserService.getUserById(directorId);
                if(ObjectUtil.isNotEmpty(user)) {
                    personnelVo.setLeader(user.getName());
                    personnelVo.setDirector(user.getName());
                }
                if(ObjectUtil.isNotEmpty(one) && directorId.equals(one.getId())){
                    personnelVo.setIsLeader("1");
                }
            }
        }
//        SysPosition sysPosition = sysPositionService.getById(personnelVo.getDutyId());
//        if(ObjectUtil.isNotEmpty(sysPosition)){
//            personnelVo.setDuty(sysPosition.getName());
//        }
        //处理职称和证书
        List<PersonnelFileCert> certlist = personnelFileCertService.list(new QueryWrapper<PersonnelFileCert>().lambda().eq(PersonnelFileCert::getFileId, personnelVo.getId()));
        List<PersonnelFileTitle> titlelist = personnelFileTitleService.list(new QueryWrapper<PersonnelFileTitle>().lambda().eq(PersonnelFileTitle::getFileId, personnelVo.getId()));
        if(CollUtil.isNotEmpty(certlist)){
            personnelVo.setCertType(certlist.get(0).getCertType());
        }
        if(CollUtil.isNotEmpty(titlelist)){
            personnelVo.setTitleLever(titlelist.get(0).getTitleLever());
        }
        return personnelVo;
    }

    @Override
    public List<PersonnelInfo> infoList(PersonnelParam personnelParam) {
        List<PersonnelInfo> infoList = personnelMapper.infoList(personnelParam.getName(),personnelParam.getStatus());
        infoList.forEach(x->{
            x.setFirstOrgId(ComputeUtil.objToString(commonCacheOperator.get(ORG_FIRST_CODE_CACHE_KEY + "orgId" + x.getDeptId())));
            x.setFirstOrgName(ComputeUtil.objToString(commonCacheOperator.get(ORG_FIRST_CODE_CACHE_KEY + "orgName" + x.getDeptId())));
        });
        return infoList;
    }

    @Override
    public void syncResource(PersonnelSimpleParam personnelSimpleParam) {
        Personnel bean = BeanUtil.toBean(personnelSimpleParam, Personnel.class);
        update(bean,new UpdateWrapper<Personnel>().eq("USER_ID",personnelSimpleParam.getUserId()));
    }

    @Override
    public List<Map<String, Object>> getAllUser(){
        return this.listMaps(new LambdaQueryWrapper<Personnel>().select(Personnel::getIdNumber, Personnel::getName)
                .eq(Personnel::getStatus,"0")
        );
    }
}
