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
package vip.xiaonuo.sys.modular.hr.conference.rezPeriod.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.entity.ConferenceRezPeriod;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.param.ConferenceRezPeriodAddParam;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.param.ConferenceRezPeriodEditParam;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.param.ConferenceRezPeriodIdParam;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.param.ConferenceRezPeriodPageParam;

import java.util.List;

/**
 * 会议预约-周期预约Service接口
 *
 * @author ljy
 * @date  2023/11/09 22:47
 **/
public interface ConferenceRezPeriodService extends IService<ConferenceRezPeriod> {

    /**
     * 获取会议预约-周期预约分页
     *
     * @author ljy
     * @date  2023/11/09 22:47
     */
    Page<ConferenceRezPeriod> page(ConferenceRezPeriodPageParam conferenceRezPeriodPageParam);

    /**
     * 添加会议预约-周期预约
     *
     * @author ljy
     * @date  2023/11/09 22:47
     */
    void add(ConferenceRezPeriodAddParam conferenceRezPeriodAddParam);

    /**
     * 编辑会议预约-周期预约
     *
     * @author ljy
     * @date  2023/11/09 22:47
     */
    void edit(ConferenceRezPeriodEditParam conferenceRezPeriodEditParam);

    /**
     * 删除会议预约-周期预约
     *
     * @author ljy
     * @date  2023/11/09 22:47
     */
    void delete(List<ConferenceRezPeriodIdParam> conferenceRezPeriodIdParamList);

    /**
     * 获取会议预约-周期预约详情
     *
     * @author ljy
     * @date  2023/11/09 22:47
     */
    ConferenceRezPeriod detail(ConferenceRezPeriodIdParam conferenceRezPeriodIdParam);

    /**
     * 获取会议预约-周期预约详情
     *
     * @author ljy
     * @date  2023/11/09 22:47
     **/
    ConferenceRezPeriod queryEntity(String id);
}
