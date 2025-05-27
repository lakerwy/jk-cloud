package vip.xiaonuo.biz.modular.prj.mou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.mou.entity.BizPrjMou;
import vip.xiaonuo.biz.modular.prj.mou.param.*;
import vip.xiaonuo.biz.modular.prj.mou.vo.BizPrjMouVo;

import java.util.List;

/**
 * 项目承接备忘录Service接口
 *
 * @author yy
 * @date  2025/05/27 10:06
 **/
public interface BizPrjMouService extends IService<BizPrjMou> {

    /**
     * 获取项目承接备忘录分页
     *
     * @author yy
     * @date  2025/05/27 10:06
     */
    Page<BizPrjMou> page(BizPrjMouPageParam bizPrjMouPageParam);

    /**
     * 添加项目承接备忘录
     *
     * @author yy
     * @date  2025/05/27 10:06
     */
    void add(BizPrjMouAddParam bizPrjMouAddParam);

    /**
     * 编辑项目承接备忘录
     *
     * @author yy
     * @date  2025/05/27 10:06
     */
    void edit(BizPrjMouEditParam bizPrjMouEditParam);

    /**
     * 删除项目承接备忘录
     *
     * @author yy
     * @date  2025/05/27 10:06
     */
    void delete(List<BizPrjMouIdParam> bizPrjMouIdParamList);

    /**
     * 获取项目承接备忘录详情
     *
     * @author yy
     * @date  2025/05/27 10:06
     */
    BizPrjMou detail(BizPrjMouIdParam bizPrjMouIdParam);

    /**
     * 获取项目承接备忘录详情
     *
     * @author yy
     * @date  2025/05/27 10:06
     **/
    BizPrjMou queryEntity(String id);

    List<BizPrjMouVo> getListById(MouParam param);
}
