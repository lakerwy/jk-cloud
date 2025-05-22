package vip.xiaonuo.biz.modular.bidFee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.bidFee.entity.BidFee;
import vip.xiaonuo.biz.modular.bidFee.param.*;
import vip.xiaonuo.biz.modular.bidFee.vo.BizBidFeeInfo;
import vip.xiaonuo.biz.modular.bidFee.vo.BizProjectVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 投标费用审批Service接口
 *
 * @author pzh
 * @date  2024/12/26 16:37
 **/
public interface BidFeeService extends IService<BidFee> {

    /**
     * 获取投标费用审批分页
     *
     * @author pzh
     * @date  2024/12/26 16:37
     */
    Page<BidFee> page(BidFeePageParam bidFeePageParam);

    /**
     * 添加投标费用审批
     *
     * @author pzh
     * @date  2024/12/26 16:37
     */
    void add(BidFeeAddParam bidFeeAddParam);

    /**
     * 编辑投标费用审批
     *
     * @author pzh
     * @date  2024/12/26 16:37
     */
    void edit(BidFeeEditParam bidFeeEditParam);

    /**
     * 删除投标费用审批
     *
     * @author pzh
     * @date  2024/12/26 16:37
     */
    void delete(List<BidFeeIdParam> bidFeeIdParamList);

    /**
     * 获取投标费用审批详情
     *
     * @author pzh
     * @date  2024/12/26 16:37
     */
    BidFee detail(BidFeeIdParam bidFeeIdParam);

    /**
     * 获取投标费用审批详情
     *
     * @author pzh
     * @date  2024/12/26 16:37
     **/
    BidFee queryEntity(String id);

    /**
     * 获取合同关联立项列表
     *
     * @author pzh
     * @date 2024/12/26 16:37
     **/
    Page<BizProjectVo> projectPage(BizProjectPageParam bizProjectPageParam);

    /**
     * excel报表生成打印版
     * @param excelParam
     */
    void genExcel(ExcelParam excelParam, HttpServletResponse response);

    /**
     *
     * @param bidFeeIdParam
     * @return
     */
    List<BizBidFeeInfo> getExpList(BidFeeIdParam bidFeeIdParam);
}
