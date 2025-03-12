package vip.xiaonuo.sys.modular.hr.sub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFiling;
import vip.xiaonuo.sys.modular.hr.sub.param.*;
import vip.xiaonuo.sys.modular.hr.sub.vo.SubFilingVo;

import java.util.List;

/**
 * 分包单位备案Service接口
 *
 * @author pzh
 * @date  2025/03/11 14:32
 **/
public interface SubFilingService extends IService<SubFiling> {

    /**
     * 获取分包单位备案分页
     *
     * @author pzh
     * @date 2025/03/11 14:32
     */
    Page<SubFilingVo> page(SubFilingPageParam subFilingPageParam);


    /**
     * 获取分包单位备案列表
     *
     * @author pzh
     * @date 2025/03/11 14:32
     */
    List<SubFilingVo> getList(SubFilingListParam subFilingListParam);

    /**
     * 添加分包单位备案
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
    void add(SubFilingAddParam subFilingAddParam);

    /**
     * 编辑分包单位备案
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
    void edit(SubFilingEditParam subFilingEditParam);

    /**
     * 删除分包单位备案
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
    void delete(List<SubFilingIdParam> subFilingIdParamList);

    /**
     * 获取分包单位备案详情
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
    SubFiling detail(SubFilingIdParam subFilingIdParam);

    /**
     * 获取分包单位备案详情
     *
     * @author pzh
     * @date  2025/03/11 14:32
     **/
    SubFiling queryEntity(String id);

    /**
     * 更新分包单位备案明细
     *
     * @author pzh
     * @date  2025/03/11 14:32
     * @param  id 主表主键id
     * @param  subFilingItemList 副表明细数据
     **/
    void updateSubFilingItem(String id, List<SubFilingItemEditParam> subFilingItemList);

    /**
     * 分包单位备案-校验
     *
     * @author pzh
     * @date  2025/03/11 14:32
     **/
    void check(SubFilingCheckParam checkParam);
}
