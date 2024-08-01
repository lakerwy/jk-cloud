package vip.xiaonuo.biz.modular.prj.expense.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizFinCode;
import vip.xiaonuo.biz.modular.prj.expense.param.*;

import java.util.List;

/**
 * 财务管理 - 费用报销 - 财务收单编码库Service接口
 *
 * @author yong.ye
 * @date  2024/08/01 11:21
 **/
public interface BizFinCodeService extends IService<BizFinCode> {

    /**
     * 获取财务管理 - 费用报销 - 财务收单编码库分页
     *
     * @author yong.ye
     * @date  2024/08/01 11:21
     */
    Page<BizFinCode> page(BizFinCodePageParam bizFinCodePageParam);

    /**
     * 添加财务管理 - 费用报销 - 财务收单编码库
     *
     * @author yong.ye
     * @date  2024/08/01 11:21
     */
    void add(BizFinCodeAddParam bizFinCodeAddParam);

    /**
     * 编辑财务管理 - 费用报销 - 财务收单编码库
     *
     * @author yong.ye
     * @date  2024/08/01 11:21
     */
    void edit(BizFinCodeEditParam bizFinCodeEditParam);

    /**
     * 删除财务管理 - 费用报销 - 财务收单编码库
     *
     * @author yong.ye
     * @date  2024/08/01 11:21
     */
    void delete(List<BizFinCodeIdParam> bizFinCodeIdParamList);

    /**
     * 获取财务管理 - 费用报销 - 财务收单编码库详情
     *
     * @author yong.ye
     * @date  2024/08/01 11:21
     */
    BizFinCode detail(BizFinCodeIdParam bizFinCodeIdParam);

    /**
     * 获取财务管理 - 费用报销 - 财务收单编码库详情
     *
     * @author yong.ye
     * @date  2024/08/01 11:21
     **/
    BizFinCode queryEntity(String id);

    /**
     * 根据公司+编码+年+月获取新的财务编码
     * @param finCodeParam
     * @return
     */
    String genFinCode(FinCodeParam finCodeParam);
}
