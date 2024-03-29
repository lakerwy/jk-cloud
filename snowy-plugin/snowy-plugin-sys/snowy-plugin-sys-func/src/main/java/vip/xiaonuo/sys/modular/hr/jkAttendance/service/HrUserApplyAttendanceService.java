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
package vip.xiaonuo.sys.modular.hr.jkAttendance.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.jkAttendance.entity.HrUserApplyAttendance;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrUserApplyAttendanceAddParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrUserApplyAttendanceEditParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrUserApplyAttendanceIdParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrUserApplyAttendancePageParam;

import java.util.List;

/**
 * 补卡或远程打卡Service接口
 *
 * @author ljy
 * @date  2023/09/12 09:17
 **/
public interface HrUserApplyAttendanceService extends IService<HrUserApplyAttendance> {

    /**
     * 获取补卡或远程打卡分页
     *
     * @author ljy
     * @date  2023/09/12 09:17
     */
    Page<HrUserApplyAttendance> page(HrUserApplyAttendancePageParam hrUserApplyAttendancePageParam);

    /**
     * 添加补卡或远程打卡
     *
     * @author ljy
     * @date  2023/09/12 09:17
     */
    void add(HrUserApplyAttendanceAddParam hrUserApplyAttendanceAddParam);

    /**
     * 重新提交打卡记录(补卡)
     *
     * @author ljy
     * @date  2023/09/19 15:17
     */
    void resubmitAttendanceApply(HrUserApplyAttendanceAddParam hrUserApplyAttendanceAddParam);

    /**
     * 编辑补卡或远程打卡
     *
     * @author ljy
     * @date  2023/09/12 09:17
     */
    void edit(HrUserApplyAttendanceEditParam hrUserApplyAttendanceEditParam);

    /**
     * 删除补卡或远程打卡
     *
     * @author ljy
     * @date  2023/09/12 09:17
     */
    void delete(List<HrUserApplyAttendanceIdParam> hrUserApplyAttendanceIdParamList);

    /**
     * 获取补卡或远程打卡详情
     *
     * @author ljy
     * @date  2023/09/12 09:17
     */
    HrUserApplyAttendance detail(HrUserApplyAttendanceIdParam hrUserApplyAttendanceIdParam);

    /**
     * 获取补卡或远程打卡详情
     *
     * @author ljy
     * @date  2023/09/12 09:17
     **/
    HrUserApplyAttendance queryEntity(String id);

}
