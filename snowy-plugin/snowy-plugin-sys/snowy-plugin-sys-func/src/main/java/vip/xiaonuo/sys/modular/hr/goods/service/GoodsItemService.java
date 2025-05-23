package vip.xiaonuo.sys.modular.hr.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.goods.entity.GoodsItem;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsItemAddParam;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsItemEditParam;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsItemIdParam;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsItemPageParam;

import java.util.List;

/**
 * 物资领用申请（附表） - 物资清单+退回清单 Service接口
 *
 * @author yy
 * @date  2025/05/23 14:35
 **/
public interface GoodsItemService extends IService<GoodsItem> {

    /**
     * 获取物资领用申请（附表） - 物资清单+退回清单 分页
     *
     * @author yy
     * @date  2025/05/23 14:35
     */
    Page<GoodsItem> page(GoodsItemPageParam goodsItemPageParam);

    /**
     * 添加物资领用申请（附表） - 物资清单+退回清单 
     *
     * @author yy
     * @date  2025/05/23 14:35
     */
    void add(GoodsItemAddParam goodsItemAddParam);

    /**
     * 编辑物资领用申请（附表） - 物资清单+退回清单 
     *
     * @author yy
     * @date  2025/05/23 14:35
     */
    void edit(GoodsItemEditParam goodsItemEditParam);

    /**
     * 删除物资领用申请（附表） - 物资清单+退回清单 
     *
     * @author yy
     * @date  2025/05/23 14:35
     */
    void delete(List<GoodsItemIdParam> goodsItemIdParamList);

    /**
     * 获取物资领用申请（附表） - 物资清单+退回清单 详情
     *
     * @author yy
     * @date  2025/05/23 14:35
     */
    GoodsItem detail(GoodsItemIdParam goodsItemIdParam);

    /**
     * 获取物资领用申请（附表） - 物资清单+退回清单 详情
     *
     * @author yy
     * @date  2025/05/23 14:35
     **/
    GoodsItem queryEntity(String id);
}
