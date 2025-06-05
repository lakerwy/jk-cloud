package vip.xiaonuo.biz.modular.crm.prjValue.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.prjValue.entity.PrjValueAllocationItem;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationItemAddParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationItemEditParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationItemIdParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationItemPageParam;

import java.util.List;

/**
 * 项目产值划分明细Service接口
 *
 * @author pzh
 * @date  2025/06/04 16:48
 **/
public interface PrjValueAllocationItemService extends IService<PrjValueAllocationItem> {

    /**
     * 获取项目产值划分明细分页
     *
     * @author pzh
     * @date  2025/06/04 16:48
     */
    Page<PrjValueAllocationItem> page(PrjValueAllocationItemPageParam prjValueAllocationItemPageParam);

    /**
     * 添加项目产值划分明细
     *
     * @author pzh
     * @date  2025/06/04 16:48
     */
    void add(PrjValueAllocationItemAddParam prjValueAllocationItemAddParam);

    /**
     * 编辑项目产值划分明细
     *
     * @author pzh
     * @date  2025/06/04 16:48
     */
    void edit(PrjValueAllocationItemEditParam prjValueAllocationItemEditParam);

    /**
     * 删除项目产值划分明细
     *
     * @author pzh
     * @date  2025/06/04 16:48
     */
    void delete(List<PrjValueAllocationItemIdParam> prjValueAllocationItemIdParamList);

    /**
     * 获取项目产值划分明细详情
     *
     * @author pzh
     * @date  2025/06/04 16:48
     */
    PrjValueAllocationItem detail(PrjValueAllocationItemIdParam prjValueAllocationItemIdParam);

    /**
     * 获取项目产值划分明细详情
     *
     * @author pzh
     * @date  2025/06/04 16:48
     **/
    PrjValueAllocationItem queryEntity(String id);
}
