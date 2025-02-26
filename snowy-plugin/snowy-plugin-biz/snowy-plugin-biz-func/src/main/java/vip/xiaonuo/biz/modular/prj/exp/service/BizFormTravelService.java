package vip.xiaonuo.biz.modular.prj.exp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormTravel;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelAddParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelEditParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelIdParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelPageParam;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormTravelTotal;

import java.util.List;

/**
 * 费用报销（二期）- 差旅费用报销单Service接口
 *
 * @author yong.ye
 * @date  2025/02/24 15:17
 **/
public interface BizFormTravelService extends IService<BizFormTravel> {

    /**
     * 获取费用报销（二期）- 差旅费用报销单分页
     *
     * @author yong.ye
     * @date  2025/02/24 15:17
     */
    Page<BizFormTravel> page(BizFormTravelPageParam bizFormTravelPageParam);

    /**
     * 添加费用报销（二期）- 差旅费用报销单
     *
     * @author yong.ye
     * @date  2025/02/24 15:17
     */
    void add(BizFormTravelAddParam bizFormTravelAddParam);

    /**
     * 编辑费用报销（二期）- 差旅费用报销单
     *
     * @author yong.ye
     * @date  2025/02/24 15:17
     */
    void edit(BizFormTravelEditParam bizFormTravelEditParam);

    /**
     * 删除费用报销（二期）- 差旅费用报销单
     *
     * @author yong.ye
     * @date  2025/02/24 15:17
     */
    void delete(List<BizFormTravelIdParam> bizFormTravelIdParamList);

    /**
     * 获取费用报销（二期）- 差旅费用报销单详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:17
     */
    BizFormTravel detail(BizFormTravelIdParam bizFormTravelIdParam);

    /**
     * 获取费用报销（二期）- 差旅费用报销单详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:17
     **/
    BizFormTravel queryEntity(String id);

    /**
     * 统计
     * @param bizFormTravelPageParam
     * @return
     */
    BizFormTravelTotal pageTotal(BizFormTravelPageParam bizFormTravelPageParam);
}
