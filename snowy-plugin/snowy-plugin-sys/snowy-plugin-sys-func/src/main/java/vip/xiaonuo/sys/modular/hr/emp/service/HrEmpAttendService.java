package vip.xiaonuo.sys.modular.hr.emp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.emp.entity.HrEmpAttend;
import vip.xiaonuo.sys.modular.hr.emp.param.*;
import vip.xiaonuo.sys.modular.hr.emp.vo.ImportResult;

import java.util.List;

/**
 * 员工月度考勤表 - 单独 - 非工资系统Service接口
 *
 * @author yong.ye
 * @date  2024/11/06 16:53
 **/
public interface HrEmpAttendService extends IService<HrEmpAttend> {

    /**
     * 获取员工月度考勤表 - 单独 - 非工资系统分页
     *
     * @author yong.ye
     * @date  2024/11/06 16:53
     */
    Page<HrEmpAttend> page(HrEmpAttendPageParam hrEmpAttendPageParam);

    /**
     * 添加员工月度考勤表 - 单独 - 非工资系统
     *
     * @author yong.ye
     * @date  2024/11/06 16:53
     */
    void add(HrEmpAttendAddParam hrEmpAttendAddParam);

    /**
     * 编辑员工月度考勤表 - 单独 - 非工资系统
     *
     * @author yong.ye
     * @date  2024/11/06 16:53
     */
    void edit(HrEmpAttendEditParam hrEmpAttendEditParam);

    /**
     * 删除员工月度考勤表 - 单独 - 非工资系统
     *
     * @author yong.ye
     * @date  2024/11/06 16:53
     */
    void delete(List<HrEmpAttendIdParam> hrEmpAttendIdParamList);

    /**
     * 获取员工月度考勤表 - 单独 - 非工资系统详情
     *
     * @author yong.ye
     * @date  2024/11/06 16:53
     */
    HrEmpAttend detail(HrEmpAttendIdParam hrEmpAttendIdParam);

    /**
     * 获取员工月度考勤表 - 单独 - 非工资系统详情
     *
     * @author yong.ye
     * @date  2024/11/06 16:53
     **/
    HrEmpAttend queryEntity(String id);

    /**
     * 导入校验
     * @param importParam
     * @return
     */
    ImportResult importCheck(ImportParam importParam);

    Boolean doImport(ImportParam importParam);
}
