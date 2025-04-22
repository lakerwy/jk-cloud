package vip.xiaonuo.sys.modular.hr.icManage.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.icManage.entity.IcManage;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageAddParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageEditParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageIdParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManagePageParam;

import java.util.List;

/**
 * 工商管理Service接口
 *
 * @author pzh
 * @date  2025/02/25 16:33
 **/
public interface IcManageService extends IService<IcManage> {

    /**
     * 获取工商管理分页
     *
     * @author pzh
     * @date  2025/02/25 16:33
     */
    Page<IcManage> page(IcManagePageParam icManagePageParam);

    /**
     * 添加工商管理
     *
     * @author pzh
     * @date  2025/02/25 16:33
     */
    void add(IcManageAddParam icManageAddParam);

    /**
     * 编辑工商管理
     *
     * @author pzh
     * @date  2025/02/25 16:33
     */
    void edit(IcManageEditParam icManageEditParam);

    /**
     * 删除工商管理
     *
     * @author pzh
     * @date  2025/02/25 16:33
     */
    void delete(List<IcManageIdParam> icManageIdParamList);

    /**
     * 获取工商管理详情
     *
     * @author pzh
     * @date  2025/02/25 16:33
     */
    IcManage detail(IcManageIdParam icManageIdParam);

    /**
     * 获取工商管理详情
     *
     * @author pzh
     * @date  2025/02/25 16:33
     **/
    IcManage queryEntity(String id);



    Boolean dealIcManage(JSONObject object);
}
