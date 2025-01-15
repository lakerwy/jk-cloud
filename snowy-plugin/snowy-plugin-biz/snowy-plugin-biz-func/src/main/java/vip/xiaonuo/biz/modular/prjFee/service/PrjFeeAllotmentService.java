package vip.xiaonuo.biz.modular.prjFee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prjFee.entity.PrjFeeAllotment;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentAddParam;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentEditParam;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentIdParam;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentPageParam;
import vip.xiaonuo.biz.modular.prjFee.vo.PrjFeeAllotmentItemVo;

import java.util.List;

/**
 * 工程款拨付审批Service接口
 *
 * @author pzh
 * @date  2025/01/15 09:22
 **/
public interface PrjFeeAllotmentService extends IService<PrjFeeAllotment> {

    /**
     * 获取工程款拨付审批分页
     *
     * @author pzh
     * @date  2025/01/15 09:22
     */
    Page<PrjFeeAllotment> page(PrjFeeAllotmentPageParam prjFeeAllotmentPageParam);

    /**
     * 添加工程款拨付审批
     *
     * @author pzh
     * @date  2025/01/15 09:22
     */
    void add(PrjFeeAllotmentAddParam prjFeeAllotmentAddParam);

    /**
     * 编辑工程款拨付审批
     *
     * @author pzh
     * @date  2025/01/15 09:22
     */
    void edit(PrjFeeAllotmentEditParam prjFeeAllotmentEditParam);

    /**
     * 删除工程款拨付审批
     *
     * @author pzh
     * @date  2025/01/15 09:22
     */
    void delete(List<PrjFeeAllotmentIdParam> prjFeeAllotmentIdParamList);

    /**
     * 获取工程款拨付审批详情
     *
     * @author pzh
     * @date 2025/01/15 09:22
     */
    PrjFeeAllotmentItemVo detail(PrjFeeAllotmentIdParam prjFeeAllotmentIdParam);

    /**
     * 获取工程款拨付审批详情
     *
     * @author pzh
     * @date  2025/01/15 09:22
     **/
    PrjFeeAllotment queryEntity(String id);
}
