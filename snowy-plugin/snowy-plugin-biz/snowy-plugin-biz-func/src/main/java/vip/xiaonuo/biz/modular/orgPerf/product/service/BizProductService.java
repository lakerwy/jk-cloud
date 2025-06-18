package vip.xiaonuo.biz.modular.orgPerf.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.product.entity.BizProduct;
import vip.xiaonuo.biz.modular.orgPerf.product.param.*;
import vip.xiaonuo.common.pojo.ImportResult;

import java.util.List;

/**
 * 产值情况Service接口
 *
 * @author pzh
 * @date  2025/06/17 09:03
 **/
public interface BizProductService extends IService<BizProduct> {

    /**
     * 获取产值情况分页
     *
     * @author pzh
     * @date  2025/06/17 09:03
     */
    Page<BizProduct> page(BizProductPageParam bizProductPageParam);

    /**
     * 添加产值情况
     *
     * @author pzh
     * @date  2025/06/17 09:03
     */
    void add(BizProductAddParam bizProductAddParam);

    /**
     * 编辑产值情况
     *
     * @author pzh
     * @date  2025/06/17 09:03
     */
    void edit(BizProductEditParam bizProductEditParam);

    /**
     * 删除产值情况
     *
     * @author pzh
     * @date  2025/06/17 09:03
     */
    void delete(List<BizProductIdParam> bizProductIdParamList);

    /**
     * 获取产值情况详情
     *
     * @author pzh
     * @date  2025/06/17 09:03
     */
    BizProduct detail(BizProductIdParam bizProductIdParam);

    /**
     * 获取产值情况详情
     *
     * @author pzh
     * @date  2025/06/17 09:03
     **/
    BizProduct queryEntity(String id);

    /**
     * 导入产值情况
     *
     * @author pzh
     * @date  2025/06/17 09:03
     **/
    ImportResult doImport(ImportProductParam importParam);
}
