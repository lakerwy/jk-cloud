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
package vip.xiaonuo.oa.modular.attendance.leave.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.oa.modular.attendance.leave.entity.Holiday;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayAddParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayEditParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayIdParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayPageParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayAddParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayEditParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayIdParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayPageParam;
import vip.xiaonuo.oa.modular.attendance.leave.entity.Holiday;

import java.util.List;

/**
 * 公司员工假期数据表Service接口
 *
 * @author mayike
 * @date  2024/06/24 17:33
 **/
public interface HolidayService extends IService<Holiday> {

    /**
     * 获取公司员工假期数据表分页
     *
     * @author mayike
     * @date  2024/06/24 17:33
     */
    Page<Holiday> page(HolidayPageParam holidayPageParam);

    /**
     * 添加公司员工假期数据表
     *
     * @author mayike
     * @date  2024/06/24 17:33
     */
    void add(HolidayAddParam holidayAddParam);

    /**
     * 编辑公司员工假期数据表
     *
     * @author mayike
     * @date  2024/06/24 17:33
     */
    void edit(HolidayEditParam holidayEditParam);

    /**
     * 删除公司员工假期数据表
     *
     * @author mayike
     * @date  2024/06/24 17:33
     */
    void delete(List<HolidayIdParam> holidayIdParamList);

    /**
     * 获取公司员工假期数据表详情
     *
     * @author mayike
     * @date  2024/06/24 17:33
     */
    Holiday detail(HolidayIdParam holidayIdParam);

    /**
     * 获取公司员工假期数据表详情
     *
     * @author mayike
     * @date  2024/06/24 17:33
     **/
    Holiday queryEntity(String id);
}
