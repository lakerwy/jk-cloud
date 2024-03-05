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
package vip.xiaonuo.sys.modular.hr.conference.rez.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceTimeDataParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.*;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceMsgEvent;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceTimeData;
import vip.xiaonuo.sys.modular.user.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * 会议模块-预约会议Service接口
 *
 * @author ljy
 * @date  2023/12/01 23:35
 **/
public interface ConferenceRezService extends IService<ConferenceRez> {

    /**
     * 获取会议模块-预约会议分页
     *
     * @author ljy
     * @date  2023/12/01 23:35
     */
    Page<ConferenceRez> page(ConferenceRezPageParam conferenceRezPageParam);

    /**
     * 添加会议模块-预约会议
     *
     * @author ljy
     * @date  2023/12/01 23:35
     */
    void add(ConferenceRezAddParam conferenceRezAddParam);

    /**
     * 编辑会议模块-预约会议
     *
     * @author ljy
     * @date  2023/12/01 23:35
     */
    void edit(ConferenceRezEditParam conferenceRezEditParam);

    /**
     * 删除会议模块-预约会议
     *
     * @author ljy
     * @date  2023/12/01 23:35
     */
    void delete(List<ConferenceRezIdParam> conferenceRezIdParamList);

    /**
     * 获取会议模块-预约会议详情
     *
     * @author ljy
     * @date  2023/12/01 23:35
     */
    ConferenceRez detail(ConferenceRezIdParam conferenceRezIdParam);

    /**
     * 获取会议模块-预约会议详情
     *
     * @author ljy
     * @date  2023/12/01 23:35
     **/
    ConferenceRez queryEntity(String id);

    /**
     * 查询可预约场景
     * */
    List<ConferenceTimeData> conferenceList(ConferenceTimeDataParam conferenceTimeDataParam);

    /**
     * 获取人员数据
     * */
    Map<String,String> getUserData();

    /** 获取部门数据  key orgId,value orgName*/
    Map<String,String> getOrgData();

    /** 获取职位数据  key postionId,value postionName*/
    Map<String,String> getPostionData();


    /** 发送开会消息 */
    OapiMessageCorpconversationAsyncsendV2Response sendConfMsg(ConferenceMsgEvent conferenceMsgEvent, String title, Integer type);

    void recordNotice(String rezId);

    Integer addTrue(ConferenceRezAddParam conferenceRezAddParam);

    void putRuanLian(RuanLianParam ruanLianParam)throws Exception;

    /** 参会人员*/
    void joinUser(List<SysUser> sysUserList,ConferenceMsgEvent conferenceMsgEvent);

    /** 释放会议*/
    void freeRez(String rezId);

    /** 释放会议*/
    void process(JSONObject bizData, String eventType, String eventId);
}
