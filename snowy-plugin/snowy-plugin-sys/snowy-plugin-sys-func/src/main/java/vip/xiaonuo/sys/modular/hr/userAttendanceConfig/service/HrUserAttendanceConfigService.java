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
package vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.entity.HrUserAttendanceConfig;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.HrUserAttendanceConfigAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.HrUserAttendanceConfigEditParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.HrUserAttendanceConfigIdParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.HrUserAttendanceConfigPageParam;

import java.util.List;

/**
 * 打卡班次配置表Service接口
 *
 * @author ljy
 * @date  2023/09/07 13:07
 **/
public interface HrUserAttendanceConfigService extends IService<HrUserAttendanceConfig> {

    /**
     * 获取打卡班次配置表分页
     *
     * @author ljy
     * @date  2023/09/07 13:07
     */
    Page<HrUserAttendanceConfig> page(HrUserAttendanceConfigPageParam hrUserAttendanceConfigPageParam);

    /**
     * 添加打卡班次配置表
     *
     * @author ljy
     * @date  2023/09/07 13:07
     */
    void add(HrUserAttendanceConfigAddParam hrUserAttendanceConfigAddParam);

    /**
     * 编辑打卡班次配置表
     *
     * @author ljy
     * @date  2023/09/07 13:07
     */
    void edit(HrUserAttendanceConfigEditParam hrUserAttendanceConfigEditParam);

    /**
     * 删除打卡班次配置表
     *
     * @author ljy
     * @date  2023/09/07 13:07
     */
    void delete(List<HrUserAttendanceConfigIdParam> hrUserAttendanceConfigIdParamList);

    /**
     * 获取打卡班次配置表详情
     *
     * @author ljy
     * @date  2023/09/07 13:07
     */
    HrUserAttendanceConfig detail(HrUserAttendanceConfigIdParam hrUserAttendanceConfigIdParam);

    /**
     * 获取打卡班次配置表详情
     *
     * @author ljy
     * @date  2023/09/07 13:07
     **/
    HrUserAttendanceConfig queryEntity(String id);

    /**
     * 获取打卡班次配置名称列表
     *
     * @author ljy
     * @date  2023/09/21 10:07
     **/
    List<HrUserAttendanceConfig> attendanceConfigList(HrUserAttendanceConfigPageParam hrUserAttendanceConfigPageParam);

    /**
     * 批量添加打卡班次配置表
     *
     * @author ljy
     * @date  2023/09/21 11:07
     */
    String addList(List<HrUserAttendanceConfigAddParam> hrUserAttendanceConfigAddParamList);
}
