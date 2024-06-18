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
package vip.xiaonuo.oa.modular.attendance.shift.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Shift;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftAddParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftEditParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftIdParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftPageParam;

import java.util.List;

/**
 * 班次表，用于存储班次的基本信息Service接口
 *
 * @author mayike
 * @date  2024/06/11 09:32
 **/
public interface ShiftService extends IService<Shift> {

    /**
     * 获取班次表，用于存储班次的基本信息分页
     *
     * @author mayike
     * @date  2024/06/11 09:32
     */
    Page<Shift> page(ShiftPageParam shiftPageParam);

    /**
     * 添加班次表，用于存储班次的基本信息
     *
     * @author mayike
     * @date  2024/06/11 09:32
     */
    void add(ShiftAddParam shiftAddParam);

    /**
     * 编辑班次表，用于存储班次的基本信息
     *
     * @author mayike
     * @date  2024/06/11 09:32
     */
    void edit(ShiftEditParam shiftEditParam);

    /**
     * 删除班次表，用于存储班次的基本信息
     *
     * @author mayike
     * @date  2024/06/11 09:32
     */
    void delete(List<ShiftIdParam> shiftIdParamList);

    /**
     * 获取班次表，用于存储班次的基本信息详情
     *
     * @author mayike
     * @date  2024/06/11 09:32
     */
    Shift detail(ShiftIdParam shiftIdParam);

    /**
     * 获取班次表，用于存储班次的基本信息详情
     *
     * @author mayike
     * @date  2024/06/11 09:32
     **/
    Shift queryEntity(String id);

    void setDefaultShift(ShiftIdParam shiftIdParam);
}
