package vip.xiaonuo.biz.modular.prj.exp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormFee;
import vip.xiaonuo.biz.modular.prj.exp.param.*;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormFeeTotal;
import vip.xiaonuo.biz.modular.prj.exp.vo.ExpMonthTotal;

import java.util.List;

/**
 * 费用报销（二期）- 费用报销单Service接口
 *
 * @author yong.ye
 * @date  2025/02/24 15:14
 **/
public interface BizFormFeeService extends IService<BizFormFee> {

    /**
     * 获取费用报销（二期）- 费用报销单分页
     *
     * @author yong.ye
     * @date  2025/02/24 15:14
     */
    Page<BizFormFee> page(BizFormFeePageParam bizFormFeePageParam);

    /**
     * 添加费用报销（二期）- 费用报销单
     *
     * @author yong.ye
     * @date  2025/02/24 15:14
     */
    void add(BizFormFeeAddParam bizFormFeeAddParam);

    /**
     * 编辑费用报销（二期）- 费用报销单
     *
     * @author yong.ye
     * @date  2025/02/24 15:14
     */
    void edit(BizFormFeeEditParam bizFormFeeEditParam);

    /**
     * 删除费用报销（二期）- 费用报销单
     *
     * @author yong.ye
     * @date  2025/02/24 15:14
     */
    void delete(List<BizFormFeeIdParam> bizFormFeeIdParamList);

    /**
     * 获取费用报销（二期）- 费用报销单详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:14
     */
    BizFormFee detail(BizFormFeeIdParam bizFormFeeIdParam);

    /**
     * 获取费用报销（二期）- 费用报销单详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:14
     **/
    BizFormFee queryEntity(String id);

    /**
     *
     * @param bizFormFeePageParam
     * @return
     */
    BizFormFeeTotal pageTotal(BizFormFeePageParam bizFormFeePageParam);

    /**
     * 报销月度统计
     * @param expMonthParam
     * @return
     */
    List<ExpMonthTotal> monthTotal(ExpMonthParam expMonthParam);

    /**
     * 费用|差旅|归还 校验
     * @param checkParam
     */
    void check(ExpCheckParam checkParam);
}
