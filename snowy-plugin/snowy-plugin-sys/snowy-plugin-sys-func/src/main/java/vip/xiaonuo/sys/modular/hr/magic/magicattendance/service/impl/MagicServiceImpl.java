package vip.xiaonuo.sys.modular.hr.magic.magicattendance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moredian.oapi.DefaultOpenApiClient;
import com.moredian.oapi.IOpenApiClient;
import com.moredian.oapi.model.app.request.GetAppTokenRequest;
import com.moredian.oapi.model.app.response.GetAppTokenResponse;
import com.moredian.oapi.model.member.request.*;
import com.moredian.oapi.model.member.response.*;
import com.moredian.oapi.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.entity.HrMagicAttendanceCallback;
import vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.service.HrMagicAttendanceCallbackService;
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.entity.HrMagicConfig;
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.service.HrMagicConfigService;
import vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.entity.HrMagicUserRelation;
import vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.service.HrMagicUserRelationService;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.enums.HrMagicEnum;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicCallback;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicMemberParam;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicResult;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.service.MagicService;
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.HrUserAttendance;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendanceAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class MagicServiceImpl implements MagicService {

    @Resource
    private HrMagicConfigService hrMagicConfigService;
    @Resource
    private HrUserAttendanceService hrUserAttendanceService;
    @Resource
    private HrMagicAttendanceCallbackService hrMagicAttendanceCallbackService;
    @Resource
    private HrMagicUserRelationService hrMagicUserRelationService;


    @Override
    public String getAppToken( ){
        List<HrMagicConfig> hrMagicConfigList = hrMagicConfigService.list(new LambdaQueryWrapper<HrMagicConfig>()
                .eq(HrMagicConfig::getMagicConfigType, HrMagicEnum.GET_APP_TOKEN.getValue()));
        if(CollectionUtil.isEmpty(hrMagicConfigList)){
            return "";
        }
        HrMagicConfig hrMagicConfig = hrMagicConfigList.get(0);
        JSONObject jsonObject = new JSONObject(hrMagicConfig.getExtJson());
        GetAppTokenResponse response = new GetAppTokenResponse();
        try{
            IOpenApiClient client = new DefaultOpenApiClient(DefaultProfile.getProfile(hrMagicConfig.getMagicUrl()));
            GetAppTokenRequest request = new GetAppTokenRequest();
            request.setAppId(jsonObject.get("appId").toString());
            request.setAppKey(jsonObject.get("appKey").toString());
            response = client.getResponse(request);
        }catch (Exception e){
            log.error("魔点获取token失败：e"+e.toString());
        }
        return response.getData().getAppToken();
    }

    @Override
    public MagicResult attendanceCallback(MagicCallback magicCallback){
        log.info("attendanceCallback:"+JSONUtil.toJsonStr(magicCallback));

        /** 保存回调数据*/
        savehrMagicAttendanceCallback(magicCallback);
        attendanceMagic(magicCallback);
        return new MagicResult(HrMagicEnum.SUCESS_CODE.getValue(),HrMagicEnum.SUCESS_INFO.getValue());
    }

    private void savehrMagicAttendanceCallback(MagicCallback magicCallback){
        HrMagicAttendanceCallback hrMagicAttendanceCallback = new HrMagicAttendanceCallback();
        hrMagicAttendanceCallback.setCreateTime(DateUtil.date());
        hrMagicAttendanceCallback.setExtJson(JSONUtil.toJsonStr(magicCallback));
        hrMagicAttendanceCallback.setMagicPersonId(magicCallback.getData().getPersonId());
        hrMagicAttendanceCallbackService.save(hrMagicAttendanceCallback);
    }

    private void attendanceMagic(MagicCallback magicCallback){
        Date checkTime = new Date(magicCallback.getData().getRecognizeTime());

        List<HrMagicUserRelation> hrMagicUserRelationList = hrMagicUserRelationService.list(new LambdaQueryWrapper<HrMagicUserRelation>()
                .eq(HrMagicUserRelation::getMagicPersonId,magicCallback.getData().getPersonId()));
        if(CollectionUtil.isEmpty(hrMagicUserRelationList)){
            log.error("魔点打卡回调接口，未在建科反查到人员信息，请创建人员关联信息！magicPersonId:"+magicCallback.getData().getPersonId().toString());
            return;
        }

        HrUserAttendanceAddParam hrUserAttendanceAddParam = new HrUserAttendanceAddParam();
        hrUserAttendanceAddParam.setCheckTime(checkTime);
        hrUserAttendanceAddParam.setUserId(hrMagicUserRelationList.get(0).getJkUserId());
        hrUserAttendanceService.attendance(hrUserAttendanceAddParam);

        /*HrUserAttendance hrUserAttendance = new HrUserAttendance();
        hrUserAttendance.setUserId(magicCallback.getData().getPersonId() == null ? "测试": magicCallback.getData().getPersonId().toString());
        Date date = new Date(magicCallback.getData().getRecognizeTime());
        hrUserAttendance.setCheckTime(date);
        hrUserAttendance.setMachineCode(magicCallback.getData().getDeviceSn());
        hrUserAttendanceService.save(hrUserAttendance);*/
    }

    @Override
    public CreateMemberResponse magicMemberCreate(MagicMemberParam magicMemberParam){
        List<HrMagicConfig> hrMagicConfigList = hrMagicConfigService.list(new LambdaQueryWrapper<HrMagicConfig>()
                .eq(HrMagicConfig::getMagicConfigType, HrMagicEnum.MAGIC_MEMBER_CREATE.getValue()));
        CreateMemberResponse createMemberResponse = new CreateMemberResponse();
        if(CollectionUtil.isEmpty(hrMagicConfigList)){
            return createMemberResponse;
        }
        HrMagicConfig hrMagicConfig = hrMagicConfigList.get(0);
        try{
            IOpenApiClient client = new DefaultOpenApiClient(DefaultProfile.getProfile(hrMagicConfig.getMagicUrl()));
            CreateMemberRequest createMemberRequest = new CreateMemberRequest();
            createMemberRequest.setAccessToken(getAppToken());
            CreateMemberRequest.CreateMemberRequestBody createMemberRequestBody = new CreateMemberRequest.CreateMemberRequestBody();
            createMemberRequestBody.setMemberName(magicMemberParam.getMemberName());
            createMemberRequestBody.setTpId(magicMemberParam.getTpId());
            createMemberRequestBody.setShowFace(magicMemberParam.getShowFace());
            createMemberRequestBody.setVerifyFace(magicMemberParam.getVerifyFace());
            createMemberRequestBody.setMobile(magicMemberParam.getMobile());
            createMemberRequestBody.setNickName(magicMemberParam.getNickName());
            createMemberRequestBody.setJobNum(magicMemberParam.getJobNum());
            createMemberRequestBody.setSex(magicMemberParam.getSex());
            createMemberRequestBody.setBirthday(magicMemberParam.getBirthday().getTime());
            createMemberRequestBody.setEnterTime(magicMemberParam.getEnterTime().getTime());
            createMemberRequestBody.setEmail(magicMemberParam.getEmail());
            createMemberRequestBody.setTelephone(magicMemberParam.getTelephone());
            createMemberRequestBody.setPost(magicMemberParam.getPost());
            createMemberRequest.setBody(createMemberRequestBody);
            createMemberResponse = client.getResponse(createMemberRequest);
        }catch (Exception e){
            log.error("魔点创建人员失败："+e.toString());
        }
        return createMemberResponse;
    }

    @Override
    public DeleteMemberResponse deleteVerifyImg(Long memberId){
        List<HrMagicConfig> hrMagicConfigList = hrMagicConfigService.list(new LambdaQueryWrapper<HrMagicConfig>()
                .eq(HrMagicConfig::getMagicConfigType, HrMagicEnum.MAGIC_MEMBER_DELTE.getValue()));
        DeleteMemberResponse deleteMemberResponse = new DeleteMemberResponse();
        if(CollectionUtil.isEmpty(hrMagicConfigList)){
            return deleteMemberResponse;
        }
        try{
            HrMagicConfig hrMagicConfig = hrMagicConfigList.get(0);
            IOpenApiClient client = new DefaultOpenApiClient(DefaultProfile.getProfile(hrMagicConfig.getMagicUrl()));
            DeleteMemberRequest deleteMemberRequest = new DeleteMemberRequest();
            deleteMemberRequest.setAccessToken(getAppToken());
            DeleteMemberRequest.DeleteMemberRequestBody deleteMemberRequestBody = new DeleteMemberRequest.DeleteMemberRequestBody();
            deleteMemberRequestBody.setMemberId(memberId);
            deleteMemberRequest.setBody(deleteMemberRequestBody);
            deleteMemberResponse = client.getResponse(deleteMemberRequest);
        }catch (Exception e){
            log.error("魔点删除创建成员:"+e.toString());
        }
        return deleteMemberResponse;
    }

    @Override
    public UpdateMemberResponse magicMemberUpdate(MagicMemberParam magicMemberParam){
        List<HrMagicConfig> hrMagicConfigList = hrMagicConfigService.list(new LambdaQueryWrapper<HrMagicConfig>()
                .eq(HrMagicConfig::getMagicConfigType, HrMagicEnum.MAGIC_MENBER_UPDATE.getValue()));

        UpdateMemberResponse updateMemberResponse = new UpdateMemberResponse();
        if(CollectionUtil.isEmpty(hrMagicConfigList)){
            return updateMemberResponse;
        }
        HrMagicConfig hrMagicConfig = hrMagicConfigList.get(0);
        try {
            IOpenApiClient client = new DefaultOpenApiClient(DefaultProfile.getProfile(hrMagicConfig.getMagicUrl()));
            UpdateMemberRequest updateMemberRequest = new UpdateMemberRequest();
            updateMemberRequest.setAccessToken(getAppToken());
            UpdateMemberRequest.UpdateMemberRequestBody updateMemberRequestBody = new UpdateMemberRequest.UpdateMemberRequestBody();
            updateMemberRequestBody.setMemberId(magicMemberParam.getMemberId());
            updateMemberRequestBody.setMemberName(magicMemberParam.getMemberName());
            updateMemberRequestBody.setNickName(magicMemberParam.getNickName());
            updateMemberRequestBody.setJobNum(magicMemberParam.getJobNum());
            updateMemberRequestBody.setSex(magicMemberParam.getSex());
            updateMemberRequestBody.setBirthday(magicMemberParam.getBirthday() != null ? magicMemberParam.getBirthday().getTime() : 0);
            updateMemberRequestBody.setEnterTime(magicMemberParam.getEnterTime() != null ? magicMemberParam.getEnterTime().getTime() : 0);
            updateMemberRequestBody.setEmail(magicMemberParam.getEmail());
            updateMemberRequestBody.setTelephone(magicMemberParam.getTelephone());
            updateMemberRequestBody.setPost(magicMemberParam.getPost());
            updateMemberRequest.setBody(updateMemberRequestBody);
            updateMemberResponse = client.getResponse(updateMemberRequest);
        }catch (Exception e){
            log.error("魔点考勤更新成员失败："+e.toString());
        }

        return updateMemberResponse;
    }

    @Override
    public UpdateMemberVerifyImageResponse updateVerifyImage(File face, Long memberId){
        List<HrMagicConfig> hrMagicConfigList = hrMagicConfigService.list(new LambdaQueryWrapper<HrMagicConfig>()
                .eq(HrMagicConfig::getMagicConfigType, HrMagicEnum.MAGIC_MEMBER_UPDATE_IMAGE.getValue()));

        UpdateMemberVerifyImageResponse updateMemberVerifyImageResponse = new UpdateMemberVerifyImageResponse();
        if(CollectionUtil.isEmpty(hrMagicConfigList)){
            return updateMemberVerifyImageResponse;
        }
        HrMagicConfig hrMagicConfig = hrMagicConfigList.get(0);

        try {
            IOpenApiClient client = new DefaultOpenApiClient(DefaultProfile.getProfile(hrMagicConfig.getMagicUrl()));
            UpdateMemberVerifyImageRequest updateMemberVerifyImageRequest = new UpdateMemberVerifyImageRequest();
            updateMemberVerifyImageRequest.setAccessToken(getAppToken());
            UpdateMemberVerifyImageRequest.UpdateMemberVerifyImageRequestBody updateMemberVerifyImageRequestBody = new UpdateMemberVerifyImageRequest.UpdateMemberVerifyImageRequestBody();
            updateMemberVerifyImageRequestBody.setMemberId(memberId);
            updateMemberVerifyImageRequestBody.setFace(face);
            updateMemberVerifyImageRequest.setBody(updateMemberVerifyImageRequestBody);
            updateMemberVerifyImageResponse = client.getResponse(updateMemberVerifyImageRequest);
        }catch (Exception e){
            log.error("魔点考勤更新识别人脸图片失败："+e.toString());
        }

        return updateMemberVerifyImageResponse;
    }

    @Override
    public ListMemberResponse magicMemberList(Integer offset, Integer size){
        List<HrMagicConfig> hrMagicConfigList = hrMagicConfigService.list(new LambdaQueryWrapper<HrMagicConfig>()
                .eq(HrMagicConfig::getMagicConfigType, HrMagicEnum.MAGIC_MEMBER_LIST.getValue()));

        ListMemberResponse listMemberResponse = new ListMemberResponse();
        if(CollectionUtil.isEmpty(hrMagicConfigList)){
            return listMemberResponse;
        }
        HrMagicConfig hrMagicConfig = hrMagicConfigList.get(0);

        try{
            IOpenApiClient client = new DefaultOpenApiClient(DefaultProfile.getProfile(hrMagicConfig.getMagicUrl()));
            ListMemberRequest listMemberRequest = new ListMemberRequest();
            listMemberRequest.setAccessToken(getAppToken());
            ListMemberRequest.ListMemberRequestBody listMemberRequestBody = new ListMemberRequest.ListMemberRequestBody();
            listMemberRequestBody.setOffset(offset == null ? 0 : offset);
            listMemberRequestBody.setSize(size == null ? 100 : size);
            listMemberRequest.setBody(listMemberRequestBody);
            listMemberResponse = client.getResponse(listMemberRequest);
        }catch (Exception e){
            log.error("魔点考勤查询成员信息列表失败："+e.toString());
        }
        return listMemberResponse;
    }

}
