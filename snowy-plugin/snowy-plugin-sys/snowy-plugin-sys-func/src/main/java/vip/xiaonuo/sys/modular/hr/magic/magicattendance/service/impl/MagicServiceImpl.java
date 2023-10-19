package vip.xiaonuo.sys.modular.hr.magic.magicattendance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moredian.oapi.DefaultOpenApiClient;
import com.moredian.oapi.IOpenApiClient;
import com.moredian.oapi.model.app.request.GetAppTokenRequest;
import com.moredian.oapi.model.app.response.GetAppTokenResponse;
import com.moredian.oapi.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.entity.HrMagicConfig;
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.service.HrMagicConfigService;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.enums.HrMagicEnum;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicCallback;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicResult;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.service.MagicService;
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.HrUserAttendance;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class MagicServiceImpl implements MagicService {

    @Resource
    private HrMagicConfigService hrMagicConfigService;
    @Resource
    private HrUserAttendanceService hrUserAttendanceService;

    @Override
    public String getAppToken( ){
        List<HrMagicConfig> hrMagicConfigList = hrMagicConfigService.list(new LambdaQueryWrapper<HrMagicConfig>()
                .eq(HrMagicConfig::getMagicConfigType, HrMagicEnum.GET_APP_TOKEN.getValue()));
        if(CollectionUtil.isEmpty(hrMagicConfigList)){
            return "";
        }
        HrMagicConfig hrMagicConfig = hrMagicConfigList.get(0);
        JSONObject jsonObject = new JSONObject(hrMagicConfig.getExtJson());

        IOpenApiClient client = new DefaultOpenApiClient(DefaultProfile.getProfile(hrMagicConfig.getMagicUrl()));
        GetAppTokenRequest request = new GetAppTokenRequest();
        request.setAppId(jsonObject.get("appId").toString());
        request.setAppKey(jsonObject.get("appKey").toString());
        GetAppTokenResponse response = client.getResponse(request);
        return response.getData().getAppToken();
    }

    @Override
    public MagicResult attendanceCallback(MagicCallback magicCallback){

        log.info("magicCallback_callbackTag:"+magicCallback.getCallbackTag()+",magicCallback_callbackTag:"+magicCallback.getData());

        HrUserAttendance hrUserAttendance = new HrUserAttendance();
        hrUserAttendance.setUserId(magicCallback.getData().getPersonId() == null ? "测试": magicCallback.getData().getPersonId().toString());
        Date date = new Date(magicCallback.getData().getRecognizeTime());
        hrUserAttendance.setCheckTime(date);
        hrUserAttendance.setMachineCode(magicCallback.getData().getDeviceSn());
        hrUserAttendanceService.save(hrUserAttendance);


        return new MagicResult(HrMagicEnum.SUCESS_CODE.getValue(),HrMagicEnum.SUCESS_INFO.getValue());
    }
}
