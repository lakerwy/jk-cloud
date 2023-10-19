package vip.xiaonuo.sys.modular.hr.magic.magicattendance.service;


import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicCallback;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicResult;

public interface MagicService {

    /**
     * 获取Token
     * */
    String getAppToken();

    MagicResult attendanceCallback(MagicCallback magicCallback);
}
