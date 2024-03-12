package vip.xiaonuo.sys.modular.hr.conference.DingDingRec;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.lettuce.core.dynamic.CommandCreationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import com.aliyun.tea.*;
import com.aliyun.teautil.models.*;
import com.aliyun.dingtalkcalendar_1_0.models.*;
import com.aliyun.teaopenapi.models.*;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DingdingCalendarUtil {

    @Resource
    SysUserService sysUserService;
    /**
     * 使用 Token 初始化账号Client
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dingtalkcalendar_1_0.Client createClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkcalendar_1_0.Client(config);
    }

    public List<SysUser> getCalendarPart(String unionId,String eventId,String xAcsDingtalkAccessToken) throws Exception{
        if(StringUtils.isEmpty(unionId) || StringUtils.isEmpty(eventId) || StringUtils.isEmpty(xAcsDingtalkAccessToken)){
            throw new CommonException("所有参数不能为空！");
        }
        com.aliyun.dingtalkcalendar_1_0.Client client = DingdingCalendarUtil.createClient();
        ListAttendeesHeaders listAttendeesHeaders = new ListAttendeesHeaders();
        listAttendeesHeaders.xAcsDingtalkAccessToken = xAcsDingtalkAccessToken;
        ListAttendeesRequest listAttendeesRequest = new ListAttendeesRequest()
                .setMaxResults(100);
        List<SysUser> sysUsers = new ArrayList<>();
        try {
            ListAttendeesResponse listAttendeesResponse = client.listAttendeesWithOptions(unionId, "primary", eventId, listAttendeesRequest, listAttendeesHeaders, new RuntimeOptions());
            List<String> userNameList = listAttendeesResponse.body.attendees.stream().map(attendees -> attendees.getDisplayName()).collect(Collectors.toList());
            sysUsers = sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getName,userNameList));
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("TeaException:获取日程参与人失败，err-code:"+err.code+",err-message:"+err.message);
            }
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("Exception:获取日程参与人失败，err-code:"+err.code+",err-message:"+err.message);
            }
        }
        return sysUsers;
    }
}
