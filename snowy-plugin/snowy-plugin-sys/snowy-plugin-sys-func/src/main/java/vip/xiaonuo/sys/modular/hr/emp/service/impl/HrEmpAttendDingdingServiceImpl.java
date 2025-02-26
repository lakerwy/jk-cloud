package vip.xiaonuo.sys.modular.hr.emp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.OperationLogEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.emp.entity.HrEmpAttendDingding;
import vip.xiaonuo.sys.modular.hr.emp.mapper.HrEmpAttendDingdingMapper;
import vip.xiaonuo.sys.modular.hr.emp.param.HrEmpAttendDingdingAddParam;
import vip.xiaonuo.sys.modular.hr.emp.param.HrEmpAttendDingdingEditParam;
import vip.xiaonuo.sys.modular.hr.emp.param.HrEmpAttendDingdingIdParam;
import vip.xiaonuo.sys.modular.hr.emp.param.HrEmpAttendDingdingPageParam;
import vip.xiaonuo.sys.modular.hr.emp.service.HrEmpAttendDingdingService;
import vip.xiaonuo.util.modular.log.service.OperationLogService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工月度考勤(钉钉)Service接口实现类
 *
 * @author pzh
 * @date  2025/02/12 15:17
 **/
@Service
public class HrEmpAttendDingdingServiceImpl extends ServiceImpl<HrEmpAttendDingdingMapper, HrEmpAttendDingding> implements HrEmpAttendDingdingService {

    @Resource
    private OperationLogService operationLogService;

    @Override
    public Page<HrEmpAttendDingding> page(HrEmpAttendDingdingPageParam hrEmpAttendDingdingPageParam) {
        QueryWrapper<HrEmpAttendDingding> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrEmpAttendDingdingPageParam.getYearMonth())) {
            queryWrapper.lambda().eq(HrEmpAttendDingding::getYearMonth, hrEmpAttendDingdingPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendDingdingPageParam.getUserType())) {
            queryWrapper.lambda().eq(HrEmpAttendDingding::getUserType, hrEmpAttendDingdingPageParam.getUserType());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendDingdingPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(HrEmpAttendDingding::getIdCardNumber, hrEmpAttendDingdingPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendDingdingPageParam.getUserName())) {
            queryWrapper.lambda().like(HrEmpAttendDingding::getUserName, hrEmpAttendDingdingPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendDingdingPageParam.getCompany())) {
            queryWrapper.lambda().like(HrEmpAttendDingding::getCompany, hrEmpAttendDingdingPageParam.getCompany());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendDingdingPageParam.getUserId())) {
            queryWrapper.lambda().eq(HrEmpAttendDingding::getUserId, hrEmpAttendDingdingPageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendDingdingPageParam.getFirstOrgName())) {
            queryWrapper.lambda().like(HrEmpAttendDingding::getFirstOrgName, hrEmpAttendDingdingPageParam.getFirstOrgName());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendDingdingPageParam.getOrgName())) {
            queryWrapper.lambda().like(HrEmpAttendDingding::getOrgName, hrEmpAttendDingdingPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendDingdingPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(HrEmpAttendDingding::getUserName,hrEmpAttendDingdingPageParam.getSearchKey())
                    .or().like(HrEmpAttendDingding::getOrgName,hrEmpAttendDingdingPageParam.getSearchKey())
                    .or().like(HrEmpAttendDingding::getPosition,hrEmpAttendDingdingPageParam.getSearchKey())
                    .or().like(HrEmpAttendDingding::getIdCardNumber,hrEmpAttendDingdingPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(hrEmpAttendDingdingPageParam.getSortField(), hrEmpAttendDingdingPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrEmpAttendDingdingPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrEmpAttendDingdingPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrEmpAttendDingdingPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(HrEmpAttendDingding::getYearMonth).orderByAsc(HrEmpAttendDingding::getIdCardNumber);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrEmpAttendDingdingAddParam hrEmpAttendDingdingAddParam) {
        HrEmpAttendDingding hrEmpAttendDingding = BeanUtil.toBean(hrEmpAttendDingdingAddParam, HrEmpAttendDingding.class);
        // 判断是否存在
        long count = this.count(new LambdaQueryWrapper<HrEmpAttendDingding>()
                .eq(HrEmpAttendDingding::getUserId, hrEmpAttendDingdingAddParam.getUserId())
                .eq(HrEmpAttendDingding::getYearMonth, hrEmpAttendDingdingAddParam.getYearMonth())
        );
        if(count > 0) {
            throw new CommonException("用户ID：{}，姓名：{}，所属月份：{}，考勤数据已存在",
                    hrEmpAttendDingdingAddParam.getUserId(), hrEmpAttendDingdingAddParam.getUserName(),hrEmpAttendDingdingAddParam.getYearMonth());
        }
        hrEmpAttendDingding.setNote(DateUtil.now()+"：页面接口add新增");
        this.save(hrEmpAttendDingding);
        operationLogService.saveLog(OperationLogEnum.DINGTALK_ATTEND_INSERTE,hrEmpAttendDingdingAddParam,hrEmpAttendDingding.getId(),hrEmpAttendDingding.getYearMonth(),hrEmpAttendDingding.getUserId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrEmpAttendDingdingEditParam hrEmpAttendDingdingEditParam) {
        HrEmpAttendDingding hrEmpAttendDingding = this.queryEntity(hrEmpAttendDingdingEditParam.getId());
        HrEmpAttendDingding orinObj = BeanUtil.toBean(hrEmpAttendDingding, HrEmpAttendDingding.class);
        BeanUtil.copyProperties(hrEmpAttendDingdingEditParam, hrEmpAttendDingding);
        // 判断是否存在
        long count = this.count(new LambdaQueryWrapper<HrEmpAttendDingding>()
                .eq(HrEmpAttendDingding::getUserId, hrEmpAttendDingdingEditParam.getUserId())
                .eq(HrEmpAttendDingding::getYearMonth, hrEmpAttendDingdingEditParam.getYearMonth())
                .notIn(HrEmpAttendDingding::getId, hrEmpAttendDingdingEditParam.getId())
        );
        if(count > 0) {
            throw new CommonException("用户ID：{}，姓名：{}，所属月份：{}，考勤数据已存在",
                    hrEmpAttendDingdingEditParam.getUserId(), hrEmpAttendDingdingEditParam.getUserName(),hrEmpAttendDingdingEditParam.getYearMonth());
        }
        hrEmpAttendDingding.setNote(DateUtil.now()+"：页面接口edit编辑；"+(StrUtil.isNotBlank(orinObj.getNote())?orinObj.getNote():""));
        this.updateById(hrEmpAttendDingding);
        operationLogService.saveLog(OperationLogEnum.DINGTALK_ATTEND_UPDATE,hrEmpAttendDingdingEditParam,orinObj,
                hrEmpAttendDingding,new String[]{"note","updateTime","updateUser"},hrEmpAttendDingding.getYearMonth(),hrEmpAttendDingding.getUserId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrEmpAttendDingdingIdParam> hrEmpAttendDingdingIdParamList) {
        HrEmpAttendDingding hrEmpAttendDingding = this.queryEntity(hrEmpAttendDingdingIdParamList.get(0).getId());
        List<String> idList = CollStreamUtil.toList(hrEmpAttendDingdingIdParamList, HrEmpAttendDingdingIdParam::getId);
        List<HrEmpAttendDingding> list = this.list(new LambdaQueryWrapper<HrEmpAttendDingding>().in(HrEmpAttendDingding::getId, idList));
        List<String> userIdList = list.stream().map(HrEmpAttendDingding::getUserId).collect(Collectors.toList());
        String userIds = String.join(",", userIdList);
        // 执行删除
        this.removeByIds(idList);
//        hrEmpAttendDingdingIdParamList.forEach(idParam->
//            operationLogService.saveLog(OperationLogEnum.DINGTALK_ATTEND_DELETE,hrEmpAttendDingdingIdParamList,idParam.getId())
//        );
        operationLogService.saveLog(OperationLogEnum.DINGTALK_ATTEND_DELETE,hrEmpAttendDingdingIdParamList,null, hrEmpAttendDingding.getYearMonth(),userIds);
    }

    @Override
    public HrEmpAttendDingding detail(HrEmpAttendDingdingIdParam hrEmpAttendDingdingIdParam) {
        return this.queryEntity(hrEmpAttendDingdingIdParam.getId());
    }

    @Override
    public HrEmpAttendDingding queryEntity(String id) {
        HrEmpAttendDingding hrEmpAttendDingding = this.getById(id);
        if(ObjectUtil.isEmpty(hrEmpAttendDingding)) {
            throw new CommonException("员工月度考勤(钉钉)不存在，id值为：{}", id);
        }
        return hrEmpAttendDingding;
    }
}
