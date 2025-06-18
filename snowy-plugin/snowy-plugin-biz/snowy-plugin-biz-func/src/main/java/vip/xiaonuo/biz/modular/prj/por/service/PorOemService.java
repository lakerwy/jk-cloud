package vip.xiaonuo.biz.modular.prj.por.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.por.entity.PorOem;
import vip.xiaonuo.biz.modular.prj.por.param.*;
import vip.xiaonuo.biz.modular.prj.por.vo.PisData;
import vip.xiaonuo.biz.modular.prj.por.vo.PorOemVo;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 经营费管理Service接口
 *
 * @author yy
 * @date  2025/06/03 09:56
 **/
public interface PorOemService extends IService<PorOem> {

    /**
     * 获取项目产值台账 - 项目管理 - 经营费管理分页
     *
     * @author yy
     * @date  2025/06/03 09:56
     */
    Page<PorOemVo> page(PorOemPageParam porOemPageParam);

    /**
     * 添加项目产值台账 - 项目管理 - 经营费管理
     *
     * @author yy
     * @date  2025/06/03 09:56
     */
    void add(PorOemAddParam porOemAddParam);

    /**
     * 编辑项目产值台账 - 项目管理 - 经营费管理
     *
     * @author yy
     * @date  2025/06/03 09:56
     */
    void edit(PorOemEditParam porOemEditParam);

    /**
     * 删除项目产值台账 - 项目管理 - 经营费管理
     *
     * @author yy
     * @date  2025/06/03 09:56
     */
    void delete(List<PorOemIdParam> porOemIdParamList);

    /**
     * 获取项目产值台账 - 项目管理 - 经营费管理详情
     *
     * @author yy
     * @date  2025/06/03 09:56
     */
    PorOemVo detail(PorOemIdParam porOemIdParam);

    /**
     * 获取项目产值台账 - 项目管理 - 经营费管理详情
     *
     * @author yy
     * @date  2025/06/03 09:56
     **/
    PorOem queryEntity(String id);

    List<PisData> getPisListByHtId(ContractParam param);
}
