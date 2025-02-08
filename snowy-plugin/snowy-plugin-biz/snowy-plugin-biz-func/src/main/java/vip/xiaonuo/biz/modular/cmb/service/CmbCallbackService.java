package vip.xiaonuo.biz.modular.cmb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.cmb.entity.CmbCallback;
import vip.xiaonuo.biz.modular.cmb.param.CmbCallbackAddParam;
import vip.xiaonuo.biz.modular.cmb.param.CmbCallbackEditParam;
import vip.xiaonuo.biz.modular.cmb.param.CmbCallbackIdParam;
import vip.xiaonuo.biz.modular.cmb.param.CmbCallbackPageParam;

import java.util.List;

/**
 * 招商银行 - 回调记录Service接口
 *
 * @author pzh
 * @date  2025/02/08 10:09
 **/
public interface CmbCallbackService extends IService<CmbCallback> {

    /**
     * 获取招商银行 - 回调记录分页
     *
     * @author pzh
     * @date  2025/02/08 10:09
     */
    Page<CmbCallback> page(CmbCallbackPageParam cmbCallbackPageParam);

    /**
     * 添加招商银行 - 回调记录
     *
     * @author pzh
     * @date  2025/02/08 10:09
     */
    void add(CmbCallbackAddParam cmbCallbackAddParam);

    /**
     * 编辑招商银行 - 回调记录
     *
     * @author pzh
     * @date  2025/02/08 10:09
     */
    void edit(CmbCallbackEditParam cmbCallbackEditParam);

    /**
     * 删除招商银行 - 回调记录
     *
     * @author pzh
     * @date  2025/02/08 10:09
     */
    void delete(List<CmbCallbackIdParam> cmbCallbackIdParamList);

    /**
     * 获取招商银行 - 回调记录详情
     *
     * @author pzh
     * @date  2025/02/08 10:09
     */
    CmbCallback detail(CmbCallbackIdParam cmbCallbackIdParam);

    /**
     * 获取招商银行 - 回调记录详情
     *
     * @author pzh
     * @date  2025/02/08 10:09
     **/
    CmbCallback queryEntity(String id);
}
