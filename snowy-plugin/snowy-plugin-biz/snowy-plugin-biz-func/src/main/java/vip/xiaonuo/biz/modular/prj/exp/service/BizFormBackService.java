package vip.xiaonuo.biz.modular.prj.exp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormBack;
import vip.xiaonuo.biz.modular.prj.exp.param.*;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormBackVo;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormCashTotal;

import java.util.List;

/**
 * 费用报销（二期）- 归还登记Service接口
 *
 * @author yong.ye
 * @date  2025/02/25 09:15
 **/
public interface BizFormBackService extends IService<BizFormBack> {

    /**
     * 获取费用报销（二期）- 归还登记分页
     *
     * @author yong.ye
     * @date  2025/02/25 09:15
     */
    Page<BizFormBackVo> page(BizFormBackPageParam bizFormBackPageParam);

    /**
     * 添加费用报销（二期）- 归还登记
     *
     * @author yong.ye
     * @date  2025/02/25 09:15
     */
    void add(BizFormBackAddParam bizFormBackAddParam);

    /**
     * 编辑费用报销（二期）- 归还登记
     *
     * @author yong.ye
     * @date  2025/02/25 09:15
     */
    void edit(BizFormBackEditParam bizFormBackEditParam);

    /**
     * 删除费用报销（二期）- 归还登记
     *
     * @author yong.ye
     * @date  2025/02/25 09:15
     */
    void delete(List<BizFormBackIdParam> bizFormBackIdParamList);

    /**
     * 获取费用报销（二期）- 归还登记详情
     *
     * @author yong.ye
     * @date  2025/02/25 09:15
     */
    BizFormBack detail(BizFormBackIdParam bizFormBackIdParam);

    /**
     * 获取费用报销（二期）- 归还登记详情
     *
     * @author yong.ye
     * @date  2025/02/25 09:15
     **/
    BizFormBack queryEntity(String id);

    /**
     *
     * @param bizFormBackPageParam
     * @return
     */
    BizFormCashTotal pageTotal(BizFormBackPageParam bizFormBackPageParam);
}
