package vip.xiaonuo.biz.modular.deptFee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.deptFee.entity.DeptFee;
import vip.xiaonuo.biz.modular.deptFee.param.*;
import vip.xiaonuo.biz.modular.deptFee.vo.ImportResult;

import java.util.List;

/**
 * 部门费用管理Service接口
 *
 * @author pzh
 * @date  2025/06/05 14:56
 **/
public interface DeptFeeService extends IService<DeptFee> {

    /**
     * 获取部门费用管理分页
     *
     * @author pzh
     * @date  2025/06/05 14:56
     */
    Page<DeptFee> page(DeptFeePageParam deptFeePageParam);

    /**
     * 添加部门费用管理
     *
     * @author pzh
     * @date  2025/06/05 14:56
     */
    void add(DeptFeeAddParam deptFeeAddParam);

    /**
     * 编辑部门费用管理
     *
     * @author pzh
     * @date  2025/06/05 14:56
     */
    void edit(DeptFeeEditParam deptFeeEditParam);

    /**
     * 删除部门费用管理
     *
     * @author pzh
     * @date  2025/06/05 14:56
     */
    void delete(List<DeptFeeIdParam> deptFeeIdParamList);

    /**
     * 获取部门费用管理详情
     *
     * @author pzh
     * @date  2025/06/05 14:56
     */
    DeptFee detail(DeptFeeIdParam deptFeeIdParam);

    /**
     * 获取部门费用管理详情
     *
     * @author pzh
     * @date  2025/06/05 14:56
     **/
    DeptFee queryEntity(String id);

    /**
     * 导入部门费用 - 校验逻辑
     *
     * @author pzh
     * @date  2025/06/05 15:40
     **/
    ImportResult importCheck(ImportParam importParam);

    /**
     * 导入部门费用 - 确认导入
     *
     * @author pzh
     * @date  2025/06/05 15:40
     */
    Boolean doImport(ImportParam importParam);
}
