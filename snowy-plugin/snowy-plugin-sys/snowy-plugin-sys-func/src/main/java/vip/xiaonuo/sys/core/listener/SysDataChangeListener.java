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
package vip.xiaonuo.sys.core.listener;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.SysDataTypeEnum;
import vip.xiaonuo.common.listener.CommonDataChangeListener;
import vip.xiaonuo.sys.modular.client.ImClient;
import vip.xiaonuo.sys.modular.hr.userHolidayData.entity.HrUserHolidayData;
import vip.xiaonuo.sys.modular.hr.userHolidayData.service.HrUserHolidayDataService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统模块数据变化侦听器
 *
 * @author xuyuxiang
 * @date 2023/3/3 10:44
 **/
@Component
public class SysDataChangeListener implements CommonDataChangeListener {

    @Resource
    HrUserHolidayDataService hrUserHolidayDataService;

    @Resource
    ImClient imClient;

    @Override
    public void doAddWithDataIdList(String dataType, List<String> dataIdList) {
        // 如果检测到机构增加，则将该机构加入到当前登录用户的数据范围缓存
        if(dataType.equals(SysDataTypeEnum.ORG.getValue())) {
            SaBaseLoginUser saBaseLoginUser = StpLoginUserUtil.getLoginUser();
            saBaseLoginUser.getDataScopeList().forEach(dataScope -> dataScope.getDataScope().addAll(dataIdList));
            saBaseLoginUser.setDataScopeList(saBaseLoginUser.getDataScopeList());
            // 重新缓存当前登录用户信息
            StpUtil.getTokenSession().set("loginUser", saBaseLoginUser);
        }
    }

    @Override
    public void doAddWithDataList(String dataType, JSONArray jsonArray) {
        // 此处可做额外处理
        if(dataType.equals(SysDataTypeEnum.USER.getValue())) {//如果存在新增人员,假期表新增数据
            jsonArray.forEach(json -> {
                JSONObject obj = JSONUtil.parseObj(json);
                HrUserHolidayData hrUserHolidayData = new HrUserHolidayData();
                String userId = ObjectUtil.isNotEmpty(obj.get("id")) ? obj.get("id").toString() : "";
                String name = ObjectUtil.isNotEmpty(obj.get("name")) ? obj.get("name").toString() : "";
                String orgId = ObjectUtil.isNotEmpty(obj.get("orgId")) ? obj.get("orgId").toString() : "";
                hrUserHolidayData.setUserId(userId);
                hrUserHolidayData.setUserName(name);
                hrUserHolidayData.setDeparId(orgId);
                hrUserHolidayDataService.save(hrUserHolidayData);
            });
        }
    }

    @Override
    public void doUpdateWithDataIdList(String dataType, List<String> dataIdList) {
        // 此处可做额外处理
    }

    @Override
    public void doUpdateWithDataList(String dataType, JSONArray jsonArray) {
        // 此处可做额外处理
        if(dataType.equals(SysDataTypeEnum.USER.getValue())){
            imClient.updateImUser(JSONUtil.parseObj(jsonArray.get(0)));
        }
    }

    @Override
    public void doDeleteWithDataIdList(String dataType, List<String> dataIdList) {
        // 此处可做额外处理
        // 删除人员数据，相应删除人员假期数据
        hrUserHolidayDataService.remove(new LambdaQueryWrapper<HrUserHolidayData>().in(HrUserHolidayData::getUserId,dataIdList));
    }
}
