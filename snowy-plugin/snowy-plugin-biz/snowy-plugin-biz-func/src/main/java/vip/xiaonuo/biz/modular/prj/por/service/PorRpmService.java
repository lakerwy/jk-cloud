package vip.xiaonuo.biz.modular.prj.por.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.por.entity.PorRpm;
import vip.xiaonuo.biz.modular.prj.por.param.PorRpmAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorRpmEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorRpmIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorRpmPageParam;
import vip.xiaonuo.biz.modular.prj.por.vo.PorRpmVo;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 责任上缴费用管理Service接口
 *
 * @author yy
 * @date  2025/06/03 09:55
 **/
public interface PorRpmService extends IService<PorRpm> {

    /**
     * 获取项目产值台账 - 项目管理 - 责任上缴费用管理分页
     *
     * @author yy
     * @date  2025/06/03 09:55
     */
    Page<PorRpmVo> page(PorRpmPageParam porRpmPageParam);

    /**
     * 添加项目产值台账 - 项目管理 - 责任上缴费用管理
     *
     * @author yy
     * @date  2025/06/03 09:55
     */
    void add(PorRpmAddParam porRpmAddParam);

    /**
     * 编辑项目产值台账 - 项目管理 - 责任上缴费用管理
     *
     * @author yy
     * @date  2025/06/03 09:55
     */
    void edit(PorRpmEditParam porRpmEditParam);

    /**
     * 删除项目产值台账 - 项目管理 - 责任上缴费用管理
     *
     * @author yy
     * @date  2025/06/03 09:55
     */
    void delete(List<PorRpmIdParam> porRpmIdParamList);

    /**
     * 获取项目产值台账 - 项目管理 - 责任上缴费用管理详情
     *
     * @author yy
     * @date  2025/06/03 09:55
     */
    PorRpmVo detail(PorRpmIdParam porRpmIdParam);

    /**
     * 获取项目产值台账 - 项目管理 - 责任上缴费用管理详情
     *
     * @author yy
     * @date  2025/06/03 09:55
     **/
    PorRpm queryEntity(String id);
}
