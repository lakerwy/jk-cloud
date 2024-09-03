package vip.xiaonuo.sys.modular.hr.school.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.school.entity.SchoolDept;
import vip.xiaonuo.sys.modular.hr.school.param.*;

import java.util.List;

/**
 * 校招 - （应聘者）- 推送部门流程Service接口
 *
 * @author yong.ye
 * @date  2024/09/03 10:47
 **/
public interface SchoolDeptService extends IService<SchoolDept> {

    /**
     * 获取校招 - （应聘者）- 推送部门流程分页
     *
     * @author yong.ye
     * @date  2024/09/03 10:47
     */
    Page<SchoolDept> page(SchoolDeptPageParam schoolDeptPageParam);

    /**
     * 添加校招 - （应聘者）- 推送部门流程
     *
     * @author yong.ye
     * @date  2024/09/03 10:47
     */
    void add(SchoolDeptAddParam schoolDeptAddParam);

    /**
     * 编辑校招 - （应聘者）- 推送部门流程
     *
     * @author yong.ye
     * @date  2024/09/03 10:47
     */
    void edit(SchoolDeptEditParam schoolDeptEditParam);

    /**
     * 删除校招 - （应聘者）- 推送部门流程
     *
     * @author yong.ye
     * @date  2024/09/03 10:47
     */
    void delete(List<SchoolDeptIdParam> schoolDeptIdParamList);

    /**
     * 获取校招 - （应聘者）- 推送部门流程详情
     *
     * @author yong.ye
     * @date  2024/09/03 10:47
     */
    SchoolDept detail(SchoolDeptIdParam schoolDeptIdParam);

    /**
     * 获取校招 - （应聘者）- 推送部门流程详情
     *
     * @author yong.ye
     * @date  2024/09/03 10:47
     **/
    SchoolDept queryEntity(String id);
}
