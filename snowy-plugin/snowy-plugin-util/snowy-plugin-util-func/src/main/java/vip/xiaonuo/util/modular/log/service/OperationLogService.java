package vip.xiaonuo.util.modular.log.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.enums.OperationLogEnum;
import vip.xiaonuo.util.modular.log.entity.OperationLogEntity;
import vip.xiaonuo.util.modular.log.param.OperationLogAddParam;
import vip.xiaonuo.util.modular.log.param.OperationLogEditParam;
import vip.xiaonuo.util.modular.log.param.OperationLogIdParam;
import vip.xiaonuo.util.modular.log.param.OperationLogPageParam;

import java.util.List;

/**
 * 操作日志Service接口
 *
 * @author pzh
 * @date  2025/02/13 16:48
 **/
public interface OperationLogService extends IService<OperationLogEntity> {

    /**
     * 获取操作日志分页
     *
     * @author pzh
     * @date  2025/02/13 16:48
     */
    Page<OperationLogEntity> page(OperationLogPageParam operationLogPageParam);

    /**
     * 添加操作日志
     *
     * @author pzh
     * @date  2025/02/13 16:48
     */
    void add(OperationLogAddParam operationLogAddParam);

    /**
     * 编辑操作日志
     *
     * @author pzh
     * @date  2025/02/13 16:48
     */
    void edit(OperationLogEditParam operationLogEditParam);

    /**
     * 删除操作日志
     *
     * @author pzh
     * @date  2025/02/13 16:48
     */
    void delete(List<OperationLogIdParam> operationLogIdParamList);

    /**
     * 获取操作日志详情
     *
     * @author pzh
     * @date  2025/02/13 16:48
     */
    OperationLogEntity detail(OperationLogIdParam operationLogIdParam);

    /**
     * 获取操作日志详情
     *
     * @author pzh
     * @date  2025/02/13 16:48
     **/
    OperationLogEntity queryEntity(String id);

    /**
     * 保存日志
     *
     * @author pzh
     * @date  2025/02/13 16:48
     * @param param 请求参数
     **/
    void saveLog(OperationLogEnum operationLogEnum,Object param);


    /**
     * 保存日志
     *
     * @author pzh
     * @date  2025/02/13 16:48
     * @param param 请求参数
     * @param obj1 修改前的对象
     * @param obj2 修改后的对象
     * @param excludeFields 需要忽略的字段（为空表示不忽略）
     **/
    void saveLog(OperationLogEnum operationLogEnum,Object param, Object obj1, Object obj2,String[] excludeFields);
}
