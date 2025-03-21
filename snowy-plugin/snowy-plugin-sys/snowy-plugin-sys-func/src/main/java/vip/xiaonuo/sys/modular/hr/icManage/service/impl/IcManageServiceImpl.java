package vip.xiaonuo.sys.modular.hr.icManage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.api.IDingDingService;
import vip.xiaonuo.sys.modular.hr.icManage.entity.IcManage;
import vip.xiaonuo.sys.modular.hr.icManage.mapper.IcManageMapper;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageAddParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageEditParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageIdParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManagePageParam;
import vip.xiaonuo.sys.modular.hr.icManage.service.IcManageService;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 工商管理Service接口实现类
 *
 * @author pzh
 * @date  2025/02/25 16:33
 **/
@Slf4j
@Service
public class IcManageServiceImpl extends ServiceImpl<IcManageMapper, IcManage> implements IcManageService {

    @Value("${icManage.updateInformDeptIds}")
    private String updateInformDeptIds;

    @Resource
    IDingDingService dingDingService;

    @Resource
    SysOrgService sysOrgService;


    @Override
    public Page<IcManage> page(IcManagePageParam icManagePageParam) {
        QueryWrapper<IcManage> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(icManagePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(IcManage::getCompanyName,icManagePageParam.getSearchKey())
                    .or().like(IcManage::getBusinessScope,icManagePageParam.getSearchKey())
                    .or().like(IcManage::getLegalRepresentative,icManagePageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isNotEmpty(icManagePageParam.getType())){
            queryWrapper.lambda().eq(IcManage::getType,icManagePageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(icManagePageParam.getStartEstablishTime()) && ObjectUtil.isNotEmpty(icManagePageParam.getEndEstablishTime())) {
            queryWrapper.lambda().between(IcManage::getEstablishTime, icManagePageParam.getStartEstablishTime(), icManagePageParam.getEndEstablishTime());
        }
        if(ObjectUtil.isAllNotEmpty(icManagePageParam.getSortField(), icManagePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(icManagePageParam.getSortOrder());
            queryWrapper.orderBy(true, icManagePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(icManagePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(IcManage::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(IcManageAddParam icManageAddParam) {
        IcManage icManage = BeanUtil.toBean(icManageAddParam, IcManage.class);
        this.save(icManage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(IcManageEditParam icManageEditParam) {
        IcManage icManage = this.queryEntity(icManageEditParam.getId());
        String typeBefore = icManage.getType();// 变更前的类型
        BeanUtil.copyProperties(icManageEditParam, icManage);
        this.updateById(icManage);
        String typeAfter = icManage.getType();// 变更后的类型
        // 变更前后涉及分公司、子公司、总公司时,需要发送钉钉提醒
        String[] sendTypeArr = {"分公司", "子公司", "总公司"};
        if(Arrays.asList(sendTypeArr).contains(typeBefore) || Arrays.asList(sendTypeArr).contains(typeAfter)){
            // 发送钉钉提醒
            List<String> informUserIdList = this.getInformUserId();
//            List<String> informUserIdList = new ArrayList<>();
//            informUserIdList.add("1815553163921506305");
            if(CollUtil.isEmpty(informUserIdList)){
                return;
            }
            String msg = String.format("%s的工商信息有变更，请您及时查看！", icManage.getCompanyName());
            log.info("工商信息变更发送提醒,userIdList:{},msg:{}", String.join(",", informUserIdList), msg);
            dingDingService.batchSend(msg,informUserIdList);
        }
    }

    // 获取工商信息变更时,需要通知的人员Id
    private List<String> getInformUserId(){
        List<String> userIdList = new ArrayList<>();
        if(ObjectUtil.isEmpty(updateInformDeptIds)){
            log.error("工商信息变更发送提醒,未读取到部门Id配置!");
            return userIdList;
        }
        List<String> deptIdList = Arrays.asList(updateInformDeptIds.split(","));
        List<SysOrg> sysOrgList = sysOrgService.list(new LambdaQueryWrapper<SysOrg>().in(SysOrg::getId, deptIdList));
        if(CollUtil.isNotEmpty(sysOrgList)){
            userIdList = sysOrgList.stream().map(SysOrg::getDirectorId).filter(ObjectUtil::isNotEmpty).distinct().collect(Collectors.toList());
        }
        return userIdList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<IcManageIdParam> icManageIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(icManageIdParamList, IcManageIdParam::getId));
    }

    @Override
    public IcManage detail(IcManageIdParam icManageIdParam) {
        return this.queryEntity(icManageIdParam.getId());
    }

    @Override
    public IcManage queryEntity(String id) {
        IcManage icManage = this.getById(id);
        if(ObjectUtil.isEmpty(icManage)) {
            throw new CommonException("工商管理不存在，id值为：{}", id);
        }
        return icManage;
    }
}
