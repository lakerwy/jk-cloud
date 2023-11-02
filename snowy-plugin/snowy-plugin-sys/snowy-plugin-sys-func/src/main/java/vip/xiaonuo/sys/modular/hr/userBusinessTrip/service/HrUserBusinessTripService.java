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
package vip.xiaonuo.sys.modular.hr.userBusinessTrip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.userBusinessTrip.entity.HrUserBusinessTrip;
import vip.xiaonuo.sys.modular.hr.userBusinessTrip.param.*;

import java.util.List;

/**
 * 业务表-考勤-用户出差申请实体数据表Service接口
 *
 * @author ljy
 * @date  2023/11/02 09:03
 **/
public interface HrUserBusinessTripService extends IService<HrUserBusinessTrip> {

    /**
     * 获取业务表-考勤-用户出差申请实体数据表分页
     *
     * @author ljy
     * @date  2023/11/02 09:03
     */
    Page<HrUserBusinessTrip> page(HrUserBusinessTripPageParam hrUserBusinessTripPageParam);

    /**
     * 添加业务表-考勤-用户出差申请实体数据表
     *
     * @author ljy
     * @date  2023/11/02 09:03
     */
    void add(HrUserBusinessTripAddParam hrUserBusinessTripAddParam);

    /**
     * 编辑业务表-考勤-用户出差申请实体数据表
     *
     * @author ljy
     * @date  2023/11/02 09:03
     */
    void edit(HrUserBusinessTripEditParam hrUserBusinessTripEditParam);

    /**
     * 删除业务表-考勤-用户出差申请实体数据表
     *
     * @author ljy
     * @date  2023/11/02 09:03
     */
    void delete(List<HrUserBusinessTripIdParam> hrUserBusinessTripIdParamList);

    /**
     * 获取业务表-考勤-用户出差申请实体数据表详情
     *
     * @author ljy
     * @date  2023/11/02 09:03
     */
    HrUserBusinessTrip detail(HrUserBusinessTripIdParam hrUserBusinessTripIdParam);

    /**
     * 获取业务表-考勤-用户出差申请实体数据表详情
     *
     * @author ljy
     * @date  2023/11/02 09:03
     **/
    HrUserBusinessTrip queryEntity(String id);

    /**
     * 添加业务表-考勤-用户申请出差
     *
     * @author ljy
     * @date  2023/11/02 09:03
     */
    void addFlw(BusinessTripFlwAddParam businessTripFlwAddParam);

    /**
     * 添加业务表-考勤-用户申请出差
     *
     * @author ljy
     * @date  2023/11/02 09:03
     */
    void editFlw(BusinessTripFlwAddParam businessTripFlwAddParam);
}
