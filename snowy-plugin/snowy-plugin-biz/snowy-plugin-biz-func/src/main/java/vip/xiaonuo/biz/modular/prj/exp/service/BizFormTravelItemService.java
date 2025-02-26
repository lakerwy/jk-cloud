package vip.xiaonuo.biz.modular.prj.exp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormTravelItem;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelItemAddParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelItemEditParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelItemIdParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelItemPageParam;

import java.util.List;

/**
 * 费用报销（二期）- 差旅费用报销单 - 报销明细Service接口
 *
 * @author yong.ye
 * @date  2025/02/24 15:19
 **/
public interface BizFormTravelItemService extends IService<BizFormTravelItem> {

    /**
     * 获取费用报销（二期）- 差旅费用报销单 - 报销明细分页
     *
     * @author yong.ye
     * @date  2025/02/24 15:19
     */
    Page<BizFormTravelItem> page(BizFormTravelItemPageParam bizFormTravelItemPageParam);

    /**
     * 添加费用报销（二期）- 差旅费用报销单 - 报销明细
     *
     * @author yong.ye
     * @date  2025/02/24 15:19
     */
    void add(BizFormTravelItemAddParam bizFormTravelItemAddParam);

    /**
     * 编辑费用报销（二期）- 差旅费用报销单 - 报销明细
     *
     * @author yong.ye
     * @date  2025/02/24 15:19
     */
    void edit(BizFormTravelItemEditParam bizFormTravelItemEditParam);

    /**
     * 删除费用报销（二期）- 差旅费用报销单 - 报销明细
     *
     * @author yong.ye
     * @date  2025/02/24 15:19
     */
    void delete(List<BizFormTravelItemIdParam> bizFormTravelItemIdParamList);

    /**
     * 获取费用报销（二期）- 差旅费用报销单 - 报销明细详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:19
     */
    BizFormTravelItem detail(BizFormTravelItemIdParam bizFormTravelItemIdParam);

    /**
     * 获取费用报销（二期）- 差旅费用报销单 - 报销明细详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:19
     **/
    BizFormTravelItem queryEntity(String id);
}
