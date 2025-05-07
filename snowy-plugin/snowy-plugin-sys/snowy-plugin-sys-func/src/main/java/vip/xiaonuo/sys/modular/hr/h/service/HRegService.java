package vip.xiaonuo.sys.modular.hr.h.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.h.entity.HReg;
import vip.xiaonuo.sys.modular.hr.h.param.HRegAddParam;
import vip.xiaonuo.sys.modular.hr.h.param.HRegEditParam;
import vip.xiaonuo.sys.modular.hr.h.param.HRegIdParam;
import vip.xiaonuo.sys.modular.hr.h.param.HRegPageParam;

import java.util.List;

/**
 * 招待事项登记Service接口
 *
 * @author yy
 * @date  2025/05/06 16:11
 **/
public interface HRegService extends IService<HReg> {

    /**
     * 获取招待事项登记分页
     *
     * @author yy
     * @date  2025/05/06 16:11
     */
    Page<HReg> page(HRegPageParam hRegPageParam);

    /**
     * 添加招待事项登记
     *
     * @author yy
     * @date  2025/05/06 16:11
     */
    void add(HRegAddParam hRegAddParam);

    /**
     * 编辑招待事项登记
     *
     * @author yy
     * @date  2025/05/06 16:11
     */
    void edit(HRegEditParam hRegEditParam);

    /**
     * 删除招待事项登记
     *
     * @author yy
     * @date  2025/05/06 16:11
     */
    void delete(List<HRegIdParam> hRegIdParamList);

    /**
     * 获取招待事项登记详情
     *
     * @author yy
     * @date  2025/05/06 16:11
     */
    HReg detail(HRegIdParam hRegIdParam);

    /**
     * 获取招待事项登记详情
     *
     * @author yy
     * @date  2025/05/06 16:11
     **/
    HReg queryEntity(String id);
}
