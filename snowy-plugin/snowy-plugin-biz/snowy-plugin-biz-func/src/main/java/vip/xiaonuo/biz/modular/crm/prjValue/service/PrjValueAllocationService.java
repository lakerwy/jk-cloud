package vip.xiaonuo.biz.modular.crm.prjValue.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.prjValue.entity.PrjValueAllocation;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationAddParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationEditParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationIdParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationPageParam;

import java.util.List;

/**
 * 项目产值划分Service接口
 *
 * @author pzh
 * @date  2025/06/04 16:35
 **/
public interface PrjValueAllocationService extends IService<PrjValueAllocation> {

    /**
     * 获取项目产值划分分页
     *
     * @author pzh
     * @date  2025/06/04 16:35
     */
    Page<PrjValueAllocation> page(PrjValueAllocationPageParam prjValueAllocationPageParam);

    /**
     * 添加项目产值划分
     *
     * @author pzh
     * @date  2025/06/04 16:35
     */
    void add(PrjValueAllocationAddParam prjValueAllocationAddParam);

    /**
     * 编辑项目产值划分
     *
     * @author pzh
     * @date  2025/06/04 16:35
     */
    void edit(PrjValueAllocationEditParam prjValueAllocationEditParam);

    /**
     * 删除项目产值划分
     *
     * @author pzh
     * @date  2025/06/04 16:35
     */
    void delete(List<PrjValueAllocationIdParam> prjValueAllocationIdParamList);

    /**
     * 获取项目产值划分详情
     *
     * @author pzh
     * @date  2025/06/04 16:35
     */
    PrjValueAllocation detail(PrjValueAllocationIdParam prjValueAllocationIdParam);

    /**
     * 获取项目产值划分详情
     *
     * @author pzh
     * @date  2025/06/04 16:35
     **/
    PrjValueAllocation queryEntity(String id);
}
