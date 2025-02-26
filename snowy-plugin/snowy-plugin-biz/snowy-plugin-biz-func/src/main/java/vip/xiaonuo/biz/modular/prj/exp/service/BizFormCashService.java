package vip.xiaonuo.biz.modular.prj.exp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormCash;
import vip.xiaonuo.biz.modular.prj.exp.param.*;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormCashTotal;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormCashVo;
import vip.xiaonuo.biz.modular.prj.exp.vo.LjNoBackVo;

import java.util.List;

/**
 * 费用报销（二期）- 借支单Service接口
 *
 * @author yong.ye
 * @date  2025/02/24 15:12
 **/
public interface BizFormCashService extends IService<BizFormCash> {

    /**
     * 获取费用报销（二期）- 借支单分页
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    Page<BizFormCashVo> page(BizFormCashPageParam bizFormCashPageParam);

    /**
     * 添加费用报销（二期）- 借支单
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    void add(BizFormCashAddParam bizFormCashAddParam);

    /**
     * 编辑费用报销（二期）- 借支单
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    void edit(BizFormCashEditParam bizFormCashEditParam);

    /**
     * 删除费用报销（二期）- 借支单
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    void delete(List<BizFormCashIdParam> bizFormCashIdParamList);

    /**
     * 获取费用报销（二期）- 借支单详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    BizFormCash detail(BizFormCashIdParam bizFormCashIdParam);

    /**
     * 获取费用报销（二期）- 借支单详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     **/
    BizFormCash queryEntity(String id);

    /**
     *
     * @param bizFormCashPageParam
     * @return
     */
    BizFormCashTotal pageTotal(BizFormCashPageParam bizFormCashPageParam);


    /**
     * 对借支单，统计 借支余额  归还金额  未还金额 等信息
     * @param bizFormCashList
     * @param backStatus
     * @return
     */
    BizFormCashTotal getBizFormCashTotal(List<BizFormCash> bizFormCashList,String backStatus);

    /**
     * 借支人 累计 借支信息
     * @param ljNoBackParam
     * @return
     */
    LjNoBackVo ljNoBack(LjNoBackParam ljNoBackParam);

    /**
     * 校验
     * @param checkParam
     */
    void check(CashCheckParam checkParam);

    /**
     * 某人明细所有审核通过且借支余额不为0
     * @param selectParam
     * @return
     */
    List<BizFormCashVo> select(CashSelectParam selectParam);
}
