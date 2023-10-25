package vip.xiaonuo.sys.modular.hr.magic.magicattendance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moredian.oapi.DefaultOpenApiClient;
import com.moredian.oapi.IOpenApiClient;
import com.moredian.oapi.model.app.request.GetAppOrgAccessTokenRequest;
import com.moredian.oapi.model.app.request.GetAppTokenRequest;
import com.moredian.oapi.model.app.response.GetAppOrgAccessTokenResponse;
import com.moredian.oapi.model.app.response.GetAppTokenResponse;
import com.moredian.oapi.model.member.request.*;
import com.moredian.oapi.model.member.response.*;
import com.moredian.oapi.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.dev.api.DevConfigApi;
import vip.xiaonuo.dev.modular.file.enums.DevFileEngineTypeEnum;
import vip.xiaonuo.dev.modular.file.service.DevFileService;
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
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendanceAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


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
    @Resource
    private SysUserService sysUserService;
    @Resource
    private DevFileService devFileService;
    @Resource
    private DevConfigApi devConfigApi;

    @Override
    public String getAppToken( ){
        List<HrMagicConfig> hrMagicConfigList = hrMagicConfigService.list(new LambdaQueryWrapper<HrMagicConfig>()
                .eq(HrMagicConfig::getMagicConfigType, HrMagicEnum.GET_APP_TOKEN.getValue()));
        if(CollectionUtil.isEmpty(hrMagicConfigList)){
            return "";
        }
        HrMagicConfig hrMagicConfig = hrMagicConfigList.get(0);
        JSONObject jsonObject = new JSONObject(hrMagicConfig.getExtJson());
        GetAppTokenResponse  response = new GetAppTokenResponse();
        try{
            IOpenApiClient client = new DefaultOpenApiClient(DefaultProfile.getProfile(hrMagicConfig.getMagicUrl()));
            GetAppTokenRequest request = new GetAppTokenRequest();
            request.setAppId(jsonObject.get("appId").toString());
            request.setAppKey(jsonObject.get("appKey").toString());
            response = client.getResponse(request);
        }catch (Exception e){
            log.error("魔点获取token失败-1：e:"+e.toString()+",response:"+response.toString());
        }

        GetAppOrgAccessTokenResponse appResponse = new GetAppOrgAccessTokenResponse();
        try{
            IOpenApiClient client = new DefaultOpenApiClient(DefaultProfile.getProfile(hrMagicConfig.getMagicUrl()));
            GetAppOrgAccessTokenRequest request = new GetAppOrgAccessTokenRequest();
            request.setAppToken(StringUtils.isNotEmpty(response.getData().getAppToken())? response.getData().getAppToken() : "");
            request.setOrgId(jsonObject.get("orgId").toString());
            request.setOrgAuthKey(jsonObject.get("orgAuthKey").toString());
            appResponse = client.getResponse(request);
        }catch (Exception e){
            log.error("魔点获取token失败-2：e:"+e.toString()+",response:"+appResponse.toString());
        }
        return StringUtils.isNotEmpty(appResponse.getData().getAccessToken()) ? appResponse.getData().getAccessToken() : "";
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
        hrUserAttendanceAddParam.setMachineCode(magicCallback.getData().getDeviceSn());
        hrUserAttendanceService.attendance(hrUserAttendanceAddParam);
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
            CreateMemberRequest.CreateMemberRequestBody createMemberRequestBody = BeanUtil.toBean(magicMemberParam,CreateMemberRequest.CreateMemberRequestBody.class);
            createMemberRequest.setBody(createMemberRequestBody);
            createMemberResponse = client.getResponse(createMemberRequest);
        }catch (Exception e){
            log.error("魔点创建人员失败："+e.toString());
        }
        return createMemberResponse;
    }

    @Override
    public DeleteMemberResponse deleteVerifyImg(String userId){
        List<HrMagicConfig> hrMagicConfigList = hrMagicConfigService.list(new LambdaQueryWrapper<HrMagicConfig>()
                .eq(HrMagicConfig::getMagicConfigType, HrMagicEnum.MAGIC_MEMBER_DELTE.getValue()));
        DeleteMemberResponse deleteMemberResponse = new DeleteMemberResponse();
        if(CollectionUtil.isEmpty(hrMagicConfigList)){
            return deleteMemberResponse;
        }
        List<HrMagicUserRelation> hrMagicUserRelationList = hrMagicUserRelationService.list(new LambdaQueryWrapper<HrMagicUserRelation>()
                .eq(HrMagicUserRelation::getJkUserId,userId));
        if(CollectionUtil.isEmpty(hrMagicUserRelationList)){
            return deleteMemberResponse;
        }

        try{
            HrMagicConfig hrMagicConfig = hrMagicConfigList.get(0);
            IOpenApiClient client = new DefaultOpenApiClient(DefaultProfile.getProfile(hrMagicConfig.getMagicUrl()));
            DeleteMemberRequest deleteMemberRequest = new DeleteMemberRequest();
            deleteMemberRequest.setAccessToken(getAppToken());
            DeleteMemberRequest.DeleteMemberRequestBody deleteMemberRequestBody = new DeleteMemberRequest.DeleteMemberRequestBody();
            deleteMemberRequestBody.setMemberId(hrMagicUserRelationList.get(0).getMagicPersonId());
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
        List<HrMagicUserRelation> hrMagicUserRelationList = hrMagicUserRelationService.list(new LambdaQueryWrapper<HrMagicUserRelation>()
                .eq(HrMagicUserRelation::getJkUserId,magicMemberParam.getUserId()));
        if(CollectionUtil.isEmpty(hrMagicUserRelationList)){
            return updateMemberResponse;
        }

        HrMagicConfig hrMagicConfig = hrMagicConfigList.get(0);
        try {
            IOpenApiClient client = new DefaultOpenApiClient(DefaultProfile.getProfile(hrMagicConfig.getMagicUrl()));
            UpdateMemberRequest updateMemberRequest = new UpdateMemberRequest();
            updateMemberRequest.setAccessToken(getAppToken());
            UpdateMemberRequest.UpdateMemberRequestBody updateMemberRequestBody = BeanUtil.toBean(magicMemberParam,UpdateMemberRequest.UpdateMemberRequestBody.class);
            updateMemberRequestBody.setBirthday(magicMemberParam.getBirthday() != null ? magicMemberParam.getBirthday().getTime() : 0);
            updateMemberRequestBody.setEnterTime(magicMemberParam.getEnterTime() != null ? magicMemberParam.getEnterTime().getTime() : 0);
            updateMemberRequestBody.setMemberId(hrMagicUserRelationList.get(0).getMagicPersonId());
            updateMemberRequest.setBody(updateMemberRequestBody);
            updateMemberResponse = client.getResponse(updateMemberRequest);
        }catch (Exception e){
            log.error("魔点考勤更新成员失败："+e.toString());
        }
        return updateMemberResponse;
    }

    @Override
    public UpdateMemberVerifyImageResponse updateVerifyImage(MultipartFile face, String userId){
        List<HrMagicConfig> hrMagicConfigList = hrMagicConfigService.list(new LambdaQueryWrapper<HrMagicConfig>()
                .eq(HrMagicConfig::getMagicConfigType, HrMagicEnum.MAGIC_MEMBER_UPDATE_IMAGE.getValue()));

        UpdateMemberVerifyImageResponse updateMemberVerifyImageResponse = new UpdateMemberVerifyImageResponse();
        if(CollectionUtil.isEmpty(hrMagicConfigList)){
            return updateMemberVerifyImageResponse;
        }
        HrMagicConfig hrMagicConfig = hrMagicConfigList.get(0);
        List<HrMagicUserRelation> hrMagicUserRelationList = hrMagicUserRelationService.list(new LambdaQueryWrapper<HrMagicUserRelation>()
                .eq(HrMagicUserRelation::getJkUserId,userId));
        if(CollectionUtil.isEmpty(hrMagicUserRelationList)){
            updateMemberVerifyImageResponse.setResult(HrMagicEnum.FAIL_CODE.getValue());
            updateMemberVerifyImageResponse.setData(HrMagicEnum.JK_USER_FAIL_INFO.getValue());
            return updateMemberVerifyImageResponse;
        }
        try {
            String fileName = face.getOriginalFilename();
            File file = File.createTempFile(fileName, fileName.substring(fileName.lastIndexOf(".")));
            face.transferTo(file);
            IOpenApiClient client = new DefaultOpenApiClient(DefaultProfile.getProfile(hrMagicConfig.getMagicUrl()));
            UpdateMemberVerifyImageRequest updateMemberVerifyImageRequest = new UpdateMemberVerifyImageRequest();
            updateMemberVerifyImageRequest.setAccessToken(getAppToken());
            UpdateMemberVerifyImageRequest.UpdateMemberVerifyImageRequestBody updateMemberVerifyImageRequestBody = new UpdateMemberVerifyImageRequest.UpdateMemberVerifyImageRequestBody();
            updateMemberVerifyImageRequestBody.setMemberId(hrMagicUserRelationList.get(0).getMagicPersonId());
            updateMemberVerifyImageRequestBody.setFace(file);
            updateMemberVerifyImageRequest.setBody(updateMemberVerifyImageRequestBody);
            updateMemberVerifyImageResponse = client.getResponse(updateMemberVerifyImageRequest);
        }catch (Exception e){
            log.error("魔点考勤更新识别人脸图片失败："+e.toString());
        }
        return updateJiankVerifyImage(face,userId,updateMemberVerifyImageResponse);
    }
    public UpdateMemberVerifyImageResponse updateJiankVerifyImage(MultipartFile file, String userId,UpdateMemberVerifyImageResponse updateMemberVerifyImageResponse){
        if(!updateMemberVerifyImageResponse.getResult().equals(HrMagicEnum.SUCESS_CODE.getValue())){
            return updateMemberVerifyImageResponse;
        }
        try{
            /*byte[] bytes = Files.readAllBytes(Paths.get(file.toURI())); // 获取文件的字节数组
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), bytes);// 创建MultipartFile对象并设置文件内容*/
            String devFileId = devFileService.uploadReturnId(DevFileEngineTypeEnum.LOCAL.getValue(),file);
            SysUser user = sysUserService.queryEntity(userId);
            if(ObjectUtil.isNotEmpty(user)){
                user.setDevFileId(devFileId);
                sysUserService.updateById(user); }
        }catch (Exception e){
            log.error("建科云保存数据失败！");
            updateMemberVerifyImageResponse.setResult(HrMagicEnum.JK_FAIL_CODE.getValue());
            updateMemberVerifyImageResponse.setData(HrMagicEnum.JK_FAIL_INFO.getValue());
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

    @Override
    public String jkUserToMagic(){
        List<SysUser> userList = sysUserService.list();

        Map<String,HrMagicUserRelation> map = new HashMap<>();
        List<HrMagicUserRelation> hrMagicUserRelationList = hrMagicUserRelationService.list();
        hrMagicUserRelationList.forEach(magic -> map.put(magic.getJkUserId(),magic));

        List<HrMagicUserRelation> hrMagicUserRelations = new ArrayList<>();
       for(SysUser sysUser : userList){
           if(map.get(sysUser.getId()) != null){
               continue;
           }
           MagicMemberParam magicMemberParam = new MagicMemberParam();
           magicMemberParam.setMemberName(sysUser.getName());
           magicMemberParam.setNickName(sysUser.getName());
           magicMemberParam.setTpId(sysUser.getId());
           CreateMemberResponse createMemberResponse = magicMemberCreate(magicMemberParam);
           if(!createMemberResponse.getResult().equals(HrMagicEnum.SUCESS_CODE.getValue())){
               log.error("创建魔点人员失败！"+createMemberResponse.toString());
               continue;
           }
           /** 保存关系表 */
           HrMagicUserRelation hrMagicUserRelation = new HrMagicUserRelation();
           hrMagicUserRelation.setJkUserId(sysUser.getId());
           hrMagicUserRelation.setMagicPersonId(createMemberResponse.getData());
           hrMagicUserRelations.add(hrMagicUserRelation);
       }
        hrMagicUserRelationService.saveBatch(hrMagicUserRelations);

        return HrMagicEnum.SUCESS_CODE.getValue();
    }
}
