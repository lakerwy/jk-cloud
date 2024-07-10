package vip.xiaonuo.oa.modular.attendance.holiday.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.oa.modular.attendance.client.WebServerClient;
import vip.xiaonuo.oa.modular.attendance.holiday.entity.HolidayBalance;
import vip.xiaonuo.oa.modular.attendance.holiday.mapper.HolidayBalanceMapper;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayBalanceAddParam;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayBalanceEditParam;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayBalanceIdParam;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayBalancePageParam;
import vip.xiaonuo.oa.modular.attendance.holiday.service.HolidayBalanceService;
import vip.xiaonuo.oa.modular.attendance.holiday.vo.HolidayVo;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 假期余额管理表Service接口实现类
 *
 * @author mayike
 * @date  2024/07/05 10:57
 **/
@Service
public class HolidayBalanceServiceImpl extends ServiceImpl<HolidayBalanceMapper, HolidayBalance> implements HolidayBalanceService {

    @Resource
    private WebServerClient webServerClient;

    @Override
    public Page<HolidayVo> page(HolidayBalancePageParam holidayBalancePageParam) {
        QueryWrapper<HolidayBalance> queryWrapper = new QueryWrapper<>();
//        if(ObjectUtil.isAllNotEmpty(holidayBalancePageParam.getSortField(), holidayBalancePageParam.getSortOrder())) {
//            CommonSortOrderEnum.validate(holidayBalancePageParam.getSortOrder());
//            queryWrapper.orderBy(true, holidayBalancePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
//                    StrUtil.toUnderlineCase(holidayBalancePageParam.getSortField()));
//        } else {
//            queryWrapper.lambda().orderByAsc(HolidayBalance::getId);
//        }
//        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
        if(StrUtil.isNotEmpty(holidayBalancePageParam.getUserName())){
            queryWrapper.like("su.`NAME`",holidayBalancePageParam.getUserName());
        }
        if(StrUtil.isNotEmpty(holidayBalancePageParam.getOrgName())){
            queryWrapper.like("so.`NAME`",holidayBalancePageParam.getOrgName());
        }
        queryWrapper.eq("hb.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("hr.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("su.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("su.USER_STATUS","ENABLE");
        queryWrapper.groupBy("su.ID","su.ORG_ID");
//        Page<HolidayVo> holidays = baseMapper.getUserHolidays(CommonPageRequest.defaultPage(), queryWrapper);
//        List<HolidayVo> records = holidays.getRecords();
        return baseMapper.getUserHolidays(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HolidayBalanceAddParam holidayBalanceAddParam) {
        HolidayBalance holidayBalance = BeanUtil.toBean(holidayBalanceAddParam, HolidayBalance.class);
        this.save(holidayBalance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HolidayBalanceEditParam holidayBalanceEditParam) {
        HolidayBalance holidayBalance = this.queryEntity(holidayBalanceEditParam.getId());
        BeanUtil.copyProperties(holidayBalanceEditParam, holidayBalance);
        this.updateById(holidayBalance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HolidayBalanceIdParam> holidayBalanceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(holidayBalanceIdParamList, HolidayBalanceIdParam::getId));
    }

    @Override
    public HolidayBalance detail(HolidayBalanceIdParam holidayBalanceIdParam) {
        return this.queryEntity(holidayBalanceIdParam.getId());
    }

    @Override
    public HolidayBalance queryEntity(String id) {
        HolidayBalance holidayBalance = this.getById(id);
        if(ObjectUtil.isEmpty(holidayBalance)) {
            throw new CommonException("假期余额管理表不存在，id值为：{}", id);
        }
        return holidayBalance;
    }

    @Override
    public void initUserHolidayData(int year,String id,List<String> userIds) {
        List<HolidayBalance> balances = new ArrayList<>();
        userIds.forEach(userId->{
            HolidayBalance holidayBalance = new HolidayBalance();
            holidayBalance.setUserId(userId);
            holidayBalance.setHolidayTypeId(id);
            holidayBalance.setBalance(BigDecimal.valueOf(-1));
            holidayBalance.setYear(year);
            balances.add(holidayBalance);
        });
        if(CollUtil.isNotEmpty(balances)){
            saveBatch(balances);
        }
    }

    @Override
    public void initData() {
        DateTime date = DateUtil.date();
        int year = date.year();
        List<String> userIds = getUserIds();
        List<HolidayBalance> balances = list();
        balances.forEach(balance->initUserHolidayData(year,balance.getUserId(),userIds));
    }

    @Override
    public List<String> getUserIds(){
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("parentId", 0);

        String orgResponse = webServerClient.getOrgByMap(mapParam);
        JSONObject orgJson = JSONUtil.parseObj(orgResponse);
        String orgId = orgJson.getJSONObject("data").getStr("id");
        // 获取组织下的所有子用户信息
        String childrenUserResponse = webServerClient.getChildrenUser(orgId);
        JSONObject userJson = JSONUtil.parseObj(childrenUserResponse);
        JSONArray jsonArray = userJson.getJSONArray("data");

        List<String> userIds = jsonArray.stream()
                .map(obj -> (JSONObject) obj)
                .map(user -> user.getStr("id"))
                .collect(Collectors.toList());

        return userIds;
    }

    @Override
    public void deleteByIds(List<String> ids) {
        remove(new LambdaQueryWrapper<HolidayBalance>().in(HolidayBalance::getUserId,ids));
    }

    @Override
    public List<HolidayVo> ownerHolidays() {
        QueryWrapper<HolidayBalance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("hb.USER_ID", StpLoginUserUtil.getLoginUser().getId());
        queryWrapper.eq("hb.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("hr.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("su.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("su.USER_STATUS","ENABLE");
        queryWrapper.groupBy("su.ID","su.ORG_ID");
//        Page<HolidayVo> holidays = baseMapper.getUserHolidays(CommonPageRequest.defaultPage(), queryWrapper);
//        List<HolidayVo> records = holidays.getRecords();
        Page<HolidayVo> holidays = baseMapper.getUserHolidays(CommonPageRequest.defaultPage(), queryWrapper);
        return holidays.getRecords();
    }
}
