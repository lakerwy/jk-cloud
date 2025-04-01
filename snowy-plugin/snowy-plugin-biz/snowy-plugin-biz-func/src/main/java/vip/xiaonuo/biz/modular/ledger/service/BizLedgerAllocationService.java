package vip.xiaonuo.biz.modular.ledger.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.ledger.entity.BizLedgerAllocation;
import vip.xiaonuo.biz.modular.ledger.param.*;
import vip.xiaonuo.biz.modular.ledger.vo.BizLedgerAllocationCountAmountVo;
import vip.xiaonuo.biz.modular.ledger.vo.BizLedgerAllocationRecentVo;

import java.util.List;

/**
 * 拨付台账Service接口
 *
 * @author pzh
 * @date  2025/03/18 10:43
 **/
public interface BizLedgerAllocationService extends IService<BizLedgerAllocation> {

    /**
     * 获取拨付台账分页
     *
     * @author pzh
     * @date  2025/03/18 10:43
     */
    Page<BizLedgerAllocation> page(BizLedgerAllocationPageParam bizLedgerAllocationPageParam);

    /**
     * 添加拨付台账
     *
     * @author pzh
     * @date  2025/03/18 10:43
     */
    void add(BizLedgerAllocationAddParam bizLedgerAllocationAddParam);

    /**
     * 编辑拨付台账
     *
     * @author pzh
     * @date  2025/03/18 10:43
     */
    void edit(BizLedgerAllocationEditParam bizLedgerAllocationEditParam);

    /**
     * 删除拨付台账
     *
     * @author pzh
     * @date  2025/03/18 10:43
     */
    void delete(List<BizLedgerAllocationIdParam> bizLedgerAllocationIdParamList);

    /**
     * 获取拨付台账详情
     *
     * @author pzh
     * @date  2025/03/18 10:43
     */
    BizLedgerAllocation detail(BizLedgerAllocationIdParam bizLedgerAllocationIdParam);

    /**
     * 获取拨付台账详情
     *
     * @author pzh
     * @date  2025/03/18 10:43
     **/
    BizLedgerAllocation queryEntity(String id);

    /**
     * 获取项目最近一次累计回款金额
     *
     * @author pzh
     * @date  2025/03/18 11:00
     **/
    BizLedgerAllocationRecentVo recent(BizLedgerAllocationRecentParam param);

    /**
     * 获取费用累计信息
     *
     * @author pzh
     * @date  2025/03/18 11:00
     **/
    BizLedgerAllocationCountAmountVo countAmount(BizLedgerAllocationPageParam bizLedgerAllocationPageParam);

    /**
     * 分包单位备案 - 导入
     *
     * @author pzh
     * @date  2025/03/31 10:30
     **/
    void importData(ImportLedgerAllocation input);
}
