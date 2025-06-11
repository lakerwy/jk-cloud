package vip.xiaonuo.sys.modular.hr.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.goods.entity.GoodsApply;
import vip.xiaonuo.sys.modular.hr.goods.param.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 物资领用申请Service接口
 *
 * @author yy
 * @date  2025/05/23 14:34
 **/
public interface GoodsApplyService extends IService<GoodsApply> {

    /**
     * 获取物资领用申请分页
     *
     * @author yy
     * @date  2025/05/23 14:34
     */
    Page<GoodsApply> page(GoodsApplyPageParam goodsApplyPageParam);

    /**
     * 添加物资领用申请
     *
     * @author yy
     * @date  2025/05/23 14:34
     */
    void add(GoodsApplyAddParam goodsApplyAddParam);

    /**
     * 编辑物资领用申请
     *
     * @author yy
     * @date  2025/05/23 14:34
     */
    void edit(GoodsApplyEditParam goodsApplyEditParam);

    /**
     * 删除物资领用申请
     *
     * @author yy
     * @date  2025/05/23 14:34
     */
    void delete(List<GoodsApplyIdParam> goodsApplyIdParamList);

    /**
     * 获取物资领用申请详情
     *
     * @author yy
     * @date  2025/05/23 14:34
     */
    GoodsApply detail(GoodsApplyIdParam goodsApplyIdParam);

    /**
     * 获取物资领用申请详情
     *
     * @author yy
     * @date  2025/05/23 14:34
     **/
    GoodsApply queryEntity(String id);

    void download(DownLoadParam param, HttpServletResponse response);
}
