package vip.xiaonuo.biz.modular.prj.mou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.mou.entity.BizPrjMouItem;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouItemAddParam;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouItemEditParam;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouItemIdParam;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouItemPageParam;

import java.util.List;

/**
 * 项目承接备忘录 - 项目生产内部分工Service接口
 *
 * @author yy
 * @date  2025/06/06 11:43
 **/
public interface BizPrjMouItemService extends IService<BizPrjMouItem> {

    /**
     * 获取项目承接备忘录 - 项目生产内部分工分页
     *
     * @author yy
     * @date  2025/06/06 11:43
     */
    Page<BizPrjMouItem> page(BizPrjMouItemPageParam bizPrjMouItemPageParam);

    /**
     * 添加项目承接备忘录 - 项目生产内部分工
     *
     * @author yy
     * @date  2025/06/06 11:43
     */
    void add(BizPrjMouItemAddParam bizPrjMouItemAddParam);

    /**
     * 编辑项目承接备忘录 - 项目生产内部分工
     *
     * @author yy
     * @date  2025/06/06 11:43
     */
    void edit(BizPrjMouItemEditParam bizPrjMouItemEditParam);

    /**
     * 删除项目承接备忘录 - 项目生产内部分工
     *
     * @author yy
     * @date  2025/06/06 11:43
     */
    void delete(List<BizPrjMouItemIdParam> bizPrjMouItemIdParamList);

    /**
     * 获取项目承接备忘录 - 项目生产内部分工详情
     *
     * @author yy
     * @date  2025/06/06 11:43
     */
    BizPrjMouItem detail(BizPrjMouItemIdParam bizPrjMouItemIdParam);

    /**
     * 获取项目承接备忘录 - 项目生产内部分工详情
     *
     * @author yy
     * @date  2025/06/06 11:43
     **/
    BizPrjMouItem queryEntity(String id);
}
