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
package vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.entity.HrUserApplyBusinessTrip;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.param.HrUserApplyBusinessTripAddParam;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.param.HrUserApplyBusinessTripEditParam;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.param.HrUserApplyBusinessTripIdParam;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.param.HrUserApplyBusinessTripPageParam;

import java.util.List;

/**
 * 用户出差申请表Service接口
 *
 * @author ljy
 * @date  2023/11/01 23:12
 **/
public interface HrUserApplyBusinessTripService extends IService<HrUserApplyBusinessTrip> {

    /**
     * 获取用户出差申请表分页
     *
     * @author ljy
     * @date  2023/11/01 23:12
     */
    Page<HrUserApplyBusinessTrip> page(HrUserApplyBusinessTripPageParam hrUserApplyBusinessTripPageParam);

    /**
     * 添加用户出差申请表
     *
     * @author ljy
     * @date  2023/11/01 23:12
     */
    void add(HrUserApplyBusinessTripAddParam hrUserApplyBusinessTripAddParam);

    /**
     * 编辑用户出差申请表
     *
     * @author ljy
     * @date  2023/11/01 23:12
     */
    void edit(HrUserApplyBusinessTripEditParam hrUserApplyBusinessTripEditParam);

    /**
     * 删除用户出差申请表
     *
     * @author ljy
     * @date  2023/11/01 23:12
     */
    void delete(List<HrUserApplyBusinessTripIdParam> hrUserApplyBusinessTripIdParamList);

    /**
     * 获取用户出差申请表详情
     *
     * @author ljy
     * @date  2023/11/01 23:12
     */
    HrUserApplyBusinessTrip detail(HrUserApplyBusinessTripIdParam hrUserApplyBusinessTripIdParam);

    /**
     * 获取用户出差申请表详情
     *
     * @author ljy
     * @date  2023/11/01 23:12
     **/
    HrUserApplyBusinessTrip queryEntity(String id);
}
