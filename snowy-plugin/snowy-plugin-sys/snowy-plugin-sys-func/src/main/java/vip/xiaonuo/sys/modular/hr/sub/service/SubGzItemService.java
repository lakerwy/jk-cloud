package vip.xiaonuo.sys.modular.hr.sub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubGzItem;
import vip.xiaonuo.sys.modular.hr.sub.param.SubGzItemAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubGzItemEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubGzItemIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubGzItemPageParam;

import java.util.List;

/**
 * 分公司-工资发放 - 工资|奖金明细Service接口
 *
 * @author yong.ye
 * @date  2025/02/06 09:11
 **/
public interface SubGzItemService extends IService<SubGzItem> {

    /**
     * 获取分公司-工资发放 - 工资|奖金明细分页
     *
     * @author yong.ye
     * @date  2025/02/06 09:11
     */
    Page<SubGzItem> page(SubGzItemPageParam subGzItemPageParam);

    /**
     * 添加分公司-工资发放 - 工资|奖金明细
     *
     * @author yong.ye
     * @date  2025/02/06 09:11
     */
    void add(SubGzItemAddParam subGzItemAddParam);

    /**
     * 编辑分公司-工资发放 - 工资|奖金明细
     *
     * @author yong.ye
     * @date  2025/02/06 09:11
     */
    void edit(SubGzItemEditParam subGzItemEditParam);

    /**
     * 删除分公司-工资发放 - 工资|奖金明细
     *
     * @author yong.ye
     * @date  2025/02/06 09:11
     */
    void delete(List<SubGzItemIdParam> subGzItemIdParamList);

    /**
     * 获取分公司-工资发放 - 工资|奖金明细详情
     *
     * @author yong.ye
     * @date  2025/02/06 09:11
     */
    SubGzItem detail(SubGzItemIdParam subGzItemIdParam);

    /**
     * 获取分公司-工资发放 - 工资|奖金明细详情
     *
     * @author yong.ye
     * @date  2025/02/06 09:11
     **/
    SubGzItem queryEntity(String id);
}
