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
package vip.xiaonuo.biz.modular.inslibrary.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.inslibrary.entity.BizInsRecord;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsRecordAddParam;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsRecordEditParam;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsRecordIdParam;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsRecordPageParam;

import java.util.List;

/**
 * 业务表-产业生态链-资源库-拜访记录Service接口
 *
 * @author yong.ye
 * @date  2023/08/15 15:27
 **/
public interface BizInsRecordService extends IService<BizInsRecord> {

    /**
     * 获取业务表-产业生态链-资源库-拜访记录分页
     *
     * @author yong.ye
     * @date  2023/08/15 15:27
     */
    Page<BizInsRecord> page(BizInsRecordPageParam bizInsRecordPageParam);

    /**
     * 添加业务表-产业生态链-资源库-拜访记录
     *
     * @author yong.ye
     * @date  2023/08/15 15:27
     */
    void add(BizInsRecordAddParam bizInsRecordAddParam);

    /**
     * 编辑业务表-产业生态链-资源库-拜访记录
     *
     * @author yong.ye
     * @date  2023/08/15 15:27
     */
    void edit(BizInsRecordEditParam bizInsRecordEditParam);

    /**
     * 删除业务表-产业生态链-资源库-拜访记录
     *
     * @author yong.ye
     * @date  2023/08/15 15:27
     */
    void delete(List<BizInsRecordIdParam> bizInsRecordIdParamList);

    /**
     * 获取业务表-产业生态链-资源库-拜访记录详情
     *
     * @author yong.ye
     * @date  2023/08/15 15:27
     */
    BizInsRecord detail(BizInsRecordIdParam bizInsRecordIdParam);

    /**
     * 获取业务表-产业生态链-资源库-拜访记录详情
     *
     * @author yong.ye
     * @date  2023/08/15 15:27
     **/
    BizInsRecord queryEntity(String id);
}
