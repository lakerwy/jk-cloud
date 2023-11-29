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
package vip.xiaonuo.sys.modular.hr.conference.record.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.conference.record.entity.ConferenceWorkArrange;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceWorkArrangeAddParam;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceWorkArrangeEditParam;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceWorkArrangeIdParam;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceWorkArrangePageParam;

import java.util.List;

/**
 * 工作部署Service接口
 *
 * @author ljy
 * @date  2023/11/28 16:31
 **/
public interface ConferenceWorkArrangeService extends IService<ConferenceWorkArrange> {

    /**
     * 获取工作部署分页
     *
     * @author ljy
     * @date  2023/11/28 16:31
     */
    Page<ConferenceWorkArrange> page(ConferenceWorkArrangePageParam conferenceWorkArrangePageParam);

    /**
     * 添加工作部署
     *
     * @author ljy
     * @date  2023/11/28 16:31
     */
    void add(ConferenceWorkArrangeAddParam conferenceWorkArrangeAddParam);

    /**
     * 编辑工作部署
     *
     * @author ljy
     * @date  2023/11/28 16:31
     */
    void edit(ConferenceWorkArrangeEditParam conferenceWorkArrangeEditParam);

    /**
     * 删除工作部署
     *
     * @author ljy
     * @date  2023/11/28 16:31
     */
    void delete(List<ConferenceWorkArrangeIdParam> conferenceWorkArrangeIdParamList);

    /**
     * 获取工作部署详情
     *
     * @author ljy
     * @date  2023/11/28 16:31
     */
    ConferenceWorkArrange detail(ConferenceWorkArrangeIdParam conferenceWorkArrangeIdParam);

    /**
     * 获取工作部署详情
     *
     * @author ljy
     * @date  2023/11/28 16:31
     **/
    ConferenceWorkArrange queryEntity(String id);
}
