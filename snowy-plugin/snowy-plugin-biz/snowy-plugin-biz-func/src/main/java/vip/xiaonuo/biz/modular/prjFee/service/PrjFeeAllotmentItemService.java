package vip.xiaonuo.biz.modular.prjFee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prjFee.entity.PrjFeeAllotmentItem;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentItemAddParam;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentItemEditParam;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentItemIdParam;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentItemPageParam;

import java.util.List;

/**
 * 工程款拨付 - 款项拨付Service接口
 *
 * @author pzh
 * @date  2025/01/15 09:33
 **/
public interface PrjFeeAllotmentItemService extends IService<PrjFeeAllotmentItem> {

    /**
     * 获取工程款拨付 - 款项拨付分页
     *
     * @author pzh
     * @date  2025/01/15 09:33
     */
    Page<PrjFeeAllotmentItem> page(PrjFeeAllotmentItemPageParam prjFeeAllotmentItemPageParam);

    /**
     * 添加工程款拨付 - 款项拨付
     *
     * @author pzh
     * @date  2025/01/15 09:33
     */
    void add(PrjFeeAllotmentItemAddParam prjFeeAllotmentItemAddParam);

    /**
     * 编辑工程款拨付 - 款项拨付
     *
     * @author pzh
     * @date  2025/01/15 09:33
     */
    void edit(PrjFeeAllotmentItemEditParam prjFeeAllotmentItemEditParam);

    /**
     * 删除工程款拨付 - 款项拨付
     *
     * @author pzh
     * @date  2025/01/15 09:33
     */
    void delete(List<PrjFeeAllotmentItemIdParam> prjFeeAllotmentItemIdParamList);

    /**
     * 获取工程款拨付 - 款项拨付详情
     *
     * @author pzh
     * @date  2025/01/15 09:33
     */
    PrjFeeAllotmentItem detail(PrjFeeAllotmentItemIdParam prjFeeAllotmentItemIdParam);

    /**
     * 获取工程款拨付 - 款项拨付详情
     *
     * @author pzh
     * @date  2025/01/15 09:33
     **/
    PrjFeeAllotmentItem queryEntity(String id);
}
