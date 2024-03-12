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
package vip.xiaonuo.sys.modular.hr.conference.config.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.conference.config.entity.ConferenceConfig;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceConfigAddParam;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceConfigEditParam;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceConfigIdParam;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceConfigPageParam;

import java.util.List;

/**
 * 会议预约-会议室数据Service接口
 *
 * @author ljy
 * @date  2023/11/05 23:12
 **/
public interface ConferenceConfigService extends IService<ConferenceConfig> {

    /**
     * 获取会议预约-会议室数据分页
     *
     * @author ljy
     * @date  2023/11/05 23:12
     */
    Page<ConferenceConfig> page(ConferenceConfigPageParam conferenceConfigPageParam);

    /**
     * 添加会议预约-会议室数据
     *
     * @author ljy
     * @date  2023/11/05 23:12
     */
    void add(ConferenceConfigAddParam conferenceConfigAddParam);

    /**
     * 编辑会议预约-会议室数据
     *
     * @author ljy
     * @date  2023/11/05 23:12
     */
    void edit(ConferenceConfigEditParam conferenceConfigEditParam);

    /**
     * 删除会议预约-会议室数据
     *
     * @author ljy
     * @date  2023/11/05 23:12
     */
    void delete(List<ConferenceConfigIdParam> conferenceConfigIdParamList);

    /**
     * 获取会议预约-会议室数据详情
     *
     * @author ljy
     * @date  2023/11/05 23:12
     */
    ConferenceConfig detail(ConferenceConfigIdParam conferenceConfigIdParam);

    /**
     * 获取会议预约-会议室数据详情
     *
     * @author ljy
     * @date  2023/11/05 23:12
     **/
    ConferenceConfig queryEntity(String id);

    String getIdForName(String name);
}
