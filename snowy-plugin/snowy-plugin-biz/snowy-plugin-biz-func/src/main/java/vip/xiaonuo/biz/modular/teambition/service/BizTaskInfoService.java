/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.teambition.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.teambition.entity.BizTaskInfo;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskInfoAddParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskInfoEditParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskInfoIdParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskInfoPageParam;
import vip.xiaonuo.biz.modular.teambition.vo.TaskInfoMsg;

import java.util.List;
import java.util.Map;

/**
 * 任务主表Service接口
 *
 * @author ljy
 * @date  2023/12/08 09:03
 **/
public interface BizTaskInfoService extends IService<BizTaskInfo> {

    /**
     * 获取任务主表分页
     *
     * @author ljy
     * @date  2023/12/08 09:03
     */
    Page<BizTaskInfo> page(BizTaskInfoPageParam bizTaskInfoPageParam);

    /**
     * 添加任务主表
     *
     * @author ljy
     * @date  2023/12/08 09:03
     */
    void add(BizTaskInfoAddParam bizTaskInfoAddParam);

    /**
     * 编辑任务主表
     *
     * @author ljy
     * @date  2023/12/08 09:03
     */
    void edit(BizTaskInfoEditParam bizTaskInfoEditParam);

    /**
     * 删除任务主表
     *
     * @author ljy
     * @date  2023/12/08 09:03
     */
    void delete(List<BizTaskInfoIdParam> bizTaskInfoIdParamList);

    /**
     * 获取任务主表详情
     *
     * @author ljy
     * @date  2023/12/08 09:03
     */
    BizTaskInfo detail(BizTaskInfoIdParam bizTaskInfoIdParam);

    /**
     * 获取任务主表详情
     *
     * @author ljy
     * @date  2023/12/08 09:03
     **/
    BizTaskInfo queryEntity(String id);

    /** 项目管理通知*/
    void sendMsg(Map<String,String> map,List<String> receiverIdList);
}
