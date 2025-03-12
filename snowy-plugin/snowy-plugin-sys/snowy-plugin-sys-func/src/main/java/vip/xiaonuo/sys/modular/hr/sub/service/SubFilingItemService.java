package vip.xiaonuo.sys.modular.hr.sub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFilingItem;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFilingItemAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFilingItemEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFilingItemIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFilingItemPageParam;

import java.util.List;

/**
 * 分包单位备案明细Service接口
 *
 * @author pzh
 * @date  2025/03/11 14:32
 **/
public interface SubFilingItemService extends IService<SubFilingItem> {

    /**
     * 获取分包单位备案明细分页
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
    Page<SubFilingItem> page(SubFilingItemPageParam subFilingItemPageParam);

    /**
     * 添加分包单位备案明细
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
    void add(SubFilingItemAddParam subFilingItemAddParam);

    /**
     * 编辑分包单位备案明细
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
    void edit(SubFilingItemEditParam subFilingItemEditParam);

    /**
     * 删除分包单位备案明细
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
    void delete(List<SubFilingItemIdParam> subFilingItemIdParamList);

    /**
     * 获取分包单位备案明细详情
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
    SubFilingItem detail(SubFilingItemIdParam subFilingItemIdParam);

    /**
     * 获取分包单位备案明细详情
     *
     * @author pzh
     * @date  2025/03/11 14:32
     **/
    SubFilingItem queryEntity(String id);
}
