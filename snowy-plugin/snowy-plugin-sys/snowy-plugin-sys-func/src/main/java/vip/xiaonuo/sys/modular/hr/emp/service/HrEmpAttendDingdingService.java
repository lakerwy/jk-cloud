package vip.xiaonuo.sys.modular.hr.emp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.emp.entity.HrEmpAttendDingding;
import vip.xiaonuo.sys.modular.hr.emp.param.HrEmpAttendDingdingAddParam;
import vip.xiaonuo.sys.modular.hr.emp.param.HrEmpAttendDingdingEditParam;
import vip.xiaonuo.sys.modular.hr.emp.param.HrEmpAttendDingdingIdParam;
import vip.xiaonuo.sys.modular.hr.emp.param.HrEmpAttendDingdingPageParam;

import java.util.List;

/**
 * 员工月度考勤(钉钉)Service接口
 *
 * @author pzh
 * @date  2025/02/12 15:17
 **/
public interface HrEmpAttendDingdingService extends IService<HrEmpAttendDingding> {

    /**
     * 获取员工月度考勤(钉钉)分页
     *
     * @author pzh
     * @date  2025/02/12 15:17
     */
    Page<HrEmpAttendDingding> page(HrEmpAttendDingdingPageParam hrEmpAttendDingdingPageParam);

    /**
     * 添加员工月度考勤(钉钉)
     *
     * @author pzh
     * @date  2025/02/12 15:17
     */
    void add(HrEmpAttendDingdingAddParam hrEmpAttendDingdingAddParam);

    /**
     * 编辑员工月度考勤(钉钉)
     *
     * @author pzh
     * @date  2025/02/12 15:17
     */
    void edit(HrEmpAttendDingdingEditParam hrEmpAttendDingdingEditParam);

    /**
     * 删除员工月度考勤(钉钉)
     *
     * @author pzh
     * @date  2025/02/12 15:17
     */
    void delete(List<HrEmpAttendDingdingIdParam> hrEmpAttendDingdingIdParamList);

    /**
     * 获取员工月度考勤(钉钉)详情
     *
     * @author pzh
     * @date  2025/02/12 15:17
     */
    HrEmpAttendDingding detail(HrEmpAttendDingdingIdParam hrEmpAttendDingdingIdParam);

    /**
     * 获取员工月度考勤(钉钉)详情
     *
     * @author pzh
     * @date  2025/02/12 15:17
     **/
    HrEmpAttendDingding queryEntity(String id);
}
