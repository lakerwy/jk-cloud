package vip.xiaonuo.biz.modular.prj.por.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.por.entity.PorSign;
import vip.xiaonuo.biz.modular.prj.por.param.*;
import vip.xiaonuo.biz.modular.prj.por.vo.PisData;
import vip.xiaonuo.biz.modular.prj.por.vo.PorSignVo;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 签字审图费管理Service接口
 *
 * @author yy
 * @date  2025/06/03 09:54
 **/
public interface PorSignService extends IService<PorSign> {

    /**
     * 获取项目产值台账 - 项目管理 - 签字审图费管理分页
     *
     * @author yy
     * @date  2025/06/03 09:54
     */
    Page<PorSignVo> page(PorSignPageParam porSignPageParam);

    /**
     * 添加项目产值台账 - 项目管理 - 签字审图费管理
     *
     * @author yy
     * @date  2025/06/03 09:54
     */
    void add(PorSignAddParam porSignAddParam);

    /**
     * 编辑项目产值台账 - 项目管理 - 签字审图费管理
     *
     * @author yy
     * @date  2025/06/03 09:54
     */
    void edit(PorSignEditParam porSignEditParam);

    /**
     * 删除项目产值台账 - 项目管理 - 签字审图费管理
     *
     * @author yy
     * @date  2025/06/03 09:54
     */
    void delete(List<PorSignIdParam> porSignIdParamList);

    /**
     * 获取项目产值台账 - 项目管理 - 签字审图费管理详情
     *
     * @author yy
     * @date  2025/06/03 09:54
     */
    PorSignVo detail(PorSignIdParam porSignIdParam);

    /**
     * 获取项目产值台账 - 项目管理 - 签字审图费管理详情
     *
     * @author yy
     * @date  2025/06/03 09:54
     **/
    PorSign queryEntity(String id);

    List<PisData> getListByHtId(ContractParam param);
}
