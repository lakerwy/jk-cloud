package vip.xiaonuo.sys.core.listener;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.SysDataTypeEnum;
import vip.xiaonuo.common.listener.CommonDataChangeListener;
import vip.xiaonuo.sys.modular.client.ImClient;
import vip.xiaonuo.sys.modular.client.OaClient;
import javax.annotation.Resource;
import java.util.List;

/**
 * 系统模块数据变化侦听器
 *
 * @author xuyuxiang
 * @date 2023/3/3 10:44
 **/
@Component
@Slf4j
public class SysDataChangeListener implements CommonDataChangeListener {

    @Resource
    ImClient imClient;

    @Resource
    OaClient oaClient;

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
    }

    @Override
    public void doUpdateWithDataIdList(String dataType, List<String> dataIdList) {
        // 此处可做额外处理
    }

    @Override
    public void doUpdateWithDataList(String dataType, JSONArray jsonArray) {
        try {
            // 此处可做额外处理
            if(dataType.equals(SysDataTypeEnum.USER.getValue())){
                imClient.updateImUser(JSONUtil.parseObj(jsonArray.get(0)));
            }
            if(dataType.equals(SysDataTypeEnum.IMUSER.getValue())){
                //imClient.updateImUser(JSONUtil.parseObj(jsonArray.get(0)));
                imClient.tencentUpdateImUser(JSONUtil.parseObj(jsonArray.get(0)));
            }
            oaClient.init(JSONUtil.parseObj(jsonArray.get(0)).getStr("id"));
        }catch (Exception e){
            log.error("新增用户的发布事件异常---{}", e);
        }
    }

    @Override
    public void doDeleteWithDataIdList(String dataType, List<String> dataIdList) {
        // 此处可做额外处理
        // 删除人员数据，相应删除人员假期数据
        try {
            if(dataType.equals(SysDataTypeEnum.USER.getValue())){
                oaClient.deleteByIds(dataIdList);
            }
        }catch (Exception e){
            log.error("删除用户的后处理事件异常---{}", e);
        }
    }
}
