package vip.xiaonuo.sys.modular.hr.emp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.emp.entity.AttendWhiteList;
import vip.xiaonuo.sys.modular.hr.emp.param.AttendWhiteListAddParam;
import vip.xiaonuo.sys.modular.hr.emp.param.AttendWhiteListEditParam;
import vip.xiaonuo.sys.modular.hr.emp.param.AttendWhiteListIdParam;
import vip.xiaonuo.sys.modular.hr.emp.param.AttendWhiteListPageParam;

import java.util.List;

/**
 * 钉钉考勤-员工白名单Service接口
 *
 * @author pzh
 * @date  2025/03/06 11:34
 **/
public interface AttendWhiteListService extends IService<AttendWhiteList> {

    /**
     * 获取钉钉考勤-员工白名单分页
     *
     * @author pzh
     * @date  2025/03/06 11:34
     */
    Page<AttendWhiteList> page(AttendWhiteListPageParam attendWhiteListPageParam);

    /**
     * 添加钉钉考勤-员工白名单
     *
     * @author pzh
     * @date  2025/03/06 11:34
     */
    void add(AttendWhiteListAddParam attendWhiteListAddParam);

    /**
     * 编辑钉钉考勤-员工白名单
     *
     * @author pzh
     * @date  2025/03/06 11:34
     */
    void edit(AttendWhiteListEditParam attendWhiteListEditParam);

    /**
     * 删除钉钉考勤-员工白名单
     *
     * @author pzh
     * @date  2025/03/06 11:34
     */
    void delete(List<AttendWhiteListIdParam> attendWhiteListIdParamList);

    /**
     * 获取钉钉考勤-员工白名单详情
     *
     * @author pzh
     * @date  2025/03/06 11:34
     */
    AttendWhiteList detail(AttendWhiteListIdParam attendWhiteListIdParam);

    /**
     * 获取钉钉考勤-员工白名单详情
     *
     * @author pzh
     * @date  2025/03/06 11:34
     **/
    AttendWhiteList queryEntity(String id);
}
