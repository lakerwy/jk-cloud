package vip.xiaonuo.biz.modular.prjFee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prjFee.param.ExcelParam;
import vip.xiaonuo.biz.modular.prjFee.entity.PrjFeeAllotment;
import vip.xiaonuo.biz.modular.prjFee.param.*;
import vip.xiaonuo.biz.modular.prjFee.vo.PrjFeeAllotmentVo;
import vip.xiaonuo.biz.modular.prjFee.vo.PrjFeeHisVo;

import javax.servlet.http.HttpServletResponse;
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
    PrjFeeAllotmentVo detail(PrjFeeAllotmentIdParam prjFeeAllotmentIdParam);

    /**
     * 获取工程款拨付审批详情
     *
     * @author pzh
     * @date  2025/01/15 09:22
     **/
    PrjFeeAllotment queryEntity(String id);

    /**
     * 获取前期款项数据
     *
     * @author pzh
     * @date 2025/01/15 15:45
     */
    PrjFeeHisVo prjFeeHis(PrjFeeHisParam prjFeeHisParam);

    /**
     * 工程款拨付审批 - 生成打印Excel
     *
     * @author pzh
     * @date  2025/01/16 09:55
     */
    void genExcel(ExcelParam excelParam, HttpServletResponse response);
}
