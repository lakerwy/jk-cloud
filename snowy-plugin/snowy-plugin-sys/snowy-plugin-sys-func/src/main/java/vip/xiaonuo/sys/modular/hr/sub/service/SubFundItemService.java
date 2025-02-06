package vip.xiaonuo.sys.modular.hr.sub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFundItem;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFundItemAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFundItemEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFundItemIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFundItemPageParam;

import java.util.List;

/**
 * 分公司-五险一金 - 社保|公积金明细Service接口
 *
 * @author yong.ye
 * @date  2025/02/06 09:13
 **/
public interface SubFundItemService extends IService<SubFundItem> {

    /**
     * 获取分公司-五险一金 - 社保|公积金明细分页
     *
     * @author yong.ye
     * @date  2025/02/06 09:13
     */
    Page<SubFundItem> page(SubFundItemPageParam subFundItemPageParam);

    /**
     * 添加分公司-五险一金 - 社保|公积金明细
     *
     * @author yong.ye
     * @date  2025/02/06 09:13
     */
    void add(SubFundItemAddParam subFundItemAddParam);

    /**
     * 编辑分公司-五险一金 - 社保|公积金明细
     *
     * @author yong.ye
     * @date  2025/02/06 09:13
     */
    void edit(SubFundItemEditParam subFundItemEditParam);

    /**
     * 删除分公司-五险一金 - 社保|公积金明细
     *
     * @author yong.ye
     * @date  2025/02/06 09:13
     */
    void delete(List<SubFundItemIdParam> subFundItemIdParamList);

    /**
     * 获取分公司-五险一金 - 社保|公积金明细详情
     *
     * @author yong.ye
     * @date  2025/02/06 09:13
     */
    SubFundItem detail(SubFundItemIdParam subFundItemIdParam);

    /**
     * 获取分公司-五险一金 - 社保|公积金明细详情
     *
     * @author yong.ye
     * @date  2025/02/06 09:13
     **/
    SubFundItem queryEntity(String id);
}
