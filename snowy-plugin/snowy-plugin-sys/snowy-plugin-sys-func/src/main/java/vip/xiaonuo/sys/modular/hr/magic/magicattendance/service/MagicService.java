package vip.xiaonuo.sys.modular.hr.magic.magicattendance.service;


import com.moredian.oapi.model.member.response.*;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicCallback;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicMemberParam;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicResult;

import java.io.File;

public interface MagicService {

    /**
     * 获取Token
     * */
    String getAppToken();

    MagicResult attendanceCallback(MagicCallback magicCallback);

    /**
     * 魔点考勤创建成员
     * */
    CreateMemberResponse magicMemberCreate(MagicMemberParam magicMemberParam);

    /**
     * 魔点删除创建成员
     * */
    DeleteMemberResponse deleteVerifyImg(String userId);

    /**
     * 魔点删除更新成员信息
     * */
    UpdateMemberResponse magicMemberUpdate(MagicMemberParam magicMemberParam);

    /**
     * 更新识别人脸图片
     * */
    void updateVerifyImage(MultipartFile face, String userId);

    /**
     * 查询成员信息列表
     * */
    ListMemberResponse magicMemberList(Integer offset, Integer size);

    /**
     * 批量导入建科人员信息到魔点考勤
     * */
    String jkUserToMagic();
}
