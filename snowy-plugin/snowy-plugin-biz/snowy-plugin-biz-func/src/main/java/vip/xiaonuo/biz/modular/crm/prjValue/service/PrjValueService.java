package vip.xiaonuo.biz.modular.crm.prjValue.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.prjValue.entity.PrjValue;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAddParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueEditParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueIdParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValuePageParam;

import java.util.List;

/**
 * 生产院及子公司产值Service接口
 *
 * @author pzh
 * @date  2025/06/05 10:04
 **/
public interface PrjValueService extends IService<PrjValue> {

    /**
     * 获取生产院及子公司产值分页
     *
     * @author pzh
     * @date  2025/06/05 10:04
     */
    Page<PrjValue> page(PrjValuePageParam prjValuePageParam);

    /**
     * 添加生产院及子公司产值
     *
     * @author pzh
     * @date  2025/06/05 10:04
     */
    void add(PrjValueAddParam prjValueAddParam);

    /**
     * 编辑生产院及子公司产值
     *
     * @author pzh
     * @date  2025/06/05 10:04
     */
    void edit(PrjValueEditParam prjValueEditParam);

    /**
     * 删除生产院及子公司产值
     *
     * @author pzh
     * @date  2025/06/05 10:04
     */
    void delete(List<PrjValueIdParam> prjValueIdParamList);

    /**
     * 获取生产院及子公司产值详情
     *
     * @author pzh
     * @date  2025/06/05 10:04
     */
    PrjValue detail(PrjValueIdParam prjValueIdParam);

    /**
     * 获取生产院及子公司产值详情
     *
     * @author pzh
     * @date  2025/06/05 10:04
     **/
    PrjValue queryEntity(String id);
}
