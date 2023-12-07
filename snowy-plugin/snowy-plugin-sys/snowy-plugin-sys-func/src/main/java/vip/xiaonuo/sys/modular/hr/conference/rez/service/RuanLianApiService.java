package vip.xiaonuo.sys.modular.hr.conference.rez.service;

import cn.hutool.json.JSONObject;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.RuanLianParam;

import java.io.IOException;

public interface RuanLianApiService {

    /** 获取软联人员信息*/
    JSONObject getuserinfo();

    /** 获取面板场景信息 */
    JSONObject getcjinfo();

    /** 预约会议*/
    JSONObject setmeeting(RuanLianParam ruanLianParam) throws IOException;

    /** 取消预约 */
    JSONObject delmeeting(String yyid);
}
