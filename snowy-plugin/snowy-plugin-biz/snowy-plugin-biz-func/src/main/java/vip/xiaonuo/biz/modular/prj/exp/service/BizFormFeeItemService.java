package vip.xiaonuo.biz.modular.prj.exp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormFeeItem;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeItemAddParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeItemEditParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeItemIdParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeItemPageParam;

import java.util.List;

/**
 * 费用报销（二期）- 费用报销单 - 报销明细Service接口
 *
 * @author yong.ye
 * @date  2025/02/24 15:16
 **/
public interface BizFormFeeItemService extends IService<BizFormFeeItem> {

    /**
     * 获取费用报销（二期）- 费用报销单 - 报销明细分页
     *
     * @author yong.ye
     * @date  2025/02/24 15:16
     */
    Page<BizFormFeeItem> page(BizFormFeeItemPageParam bizFormFeeItemPageParam);

    /**
     * 添加费用报销（二期）- 费用报销单 - 报销明细
     *
     * @author yong.ye
     * @date  2025/02/24 15:16
     */
    void add(BizFormFeeItemAddParam bizFormFeeItemAddParam);

    /**
     * 编辑费用报销（二期）- 费用报销单 - 报销明细
     *
     * @author yong.ye
     * @date  2025/02/24 15:16
     */
    void edit(BizFormFeeItemEditParam bizFormFeeItemEditParam);

    /**
     * 删除费用报销（二期）- 费用报销单 - 报销明细
     *
     * @author yong.ye
     * @date  2025/02/24 15:16
     */
    void delete(List<BizFormFeeItemIdParam> bizFormFeeItemIdParamList);

    /**
     * 获取费用报销（二期）- 费用报销单 - 报销明细详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:16
     */
    BizFormFeeItem detail(BizFormFeeItemIdParam bizFormFeeItemIdParam);

    /**
     * 获取费用报销（二期）- 费用报销单 - 报销明细详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:16
     **/
    BizFormFeeItem queryEntity(String id);
}
