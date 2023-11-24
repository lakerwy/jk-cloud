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
package vip.xiaonuo.sys.modular.hr.conference.userSign.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.conference.userSign.entity.ConferenceUserSign;
import vip.xiaonuo.sys.modular.hr.conference.userSign.param.ConferenceUserSignAddParam;
import vip.xiaonuo.sys.modular.hr.conference.userSign.param.ConferenceUserSignEditParam;
import vip.xiaonuo.sys.modular.hr.conference.userSign.param.ConferenceUserSignIdParam;
import vip.xiaonuo.sys.modular.hr.conference.userSign.param.ConferenceUserSignPageParam;

import java.util.List;

/**
 * 会议预约-签到表Service接口
 *
 * @author ljy
 * @date  2023/11/24 09:27
 **/
public interface ConferenceUserSignService extends IService<ConferenceUserSign> {

    /**
     * 获取会议预约-签到表分页
     *
     * @author ljy
     * @date  2023/11/24 09:27
     */
    Page<ConferenceUserSign> page(ConferenceUserSignPageParam conferenceUserSignPageParam);

    /**
     * 添加会议预约-签到表
     *
     * @author ljy
     * @date  2023/11/24 09:27
     */
    void add(ConferenceUserSignAddParam conferenceUserSignAddParam);

    /**
     * 编辑会议预约-签到表
     *
     * @author ljy
     * @date  2023/11/24 09:27
     */
    void edit(ConferenceUserSignEditParam conferenceUserSignEditParam);

    /**
     * 删除会议预约-签到表
     *
     * @author ljy
     * @date  2023/11/24 09:27
     */
    void delete(List<ConferenceUserSignIdParam> conferenceUserSignIdParamList);

    /**
     * 获取会议预约-签到表详情
     *
     * @author ljy
     * @date  2023/11/24 09:27
     */
    ConferenceUserSign detail(ConferenceUserSignIdParam conferenceUserSignIdParam);

    /**
     * 获取会议预约-签到表详情
     *
     * @author ljy
     * @date  2023/11/24 09:27
     **/
    ConferenceUserSign queryEntity(String id);
}
