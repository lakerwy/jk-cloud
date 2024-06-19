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
package vip.xiaonuo.oa.modular.attendance.record.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.oa.modular.attendance.record.entity.Record;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordAddParam;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordEditParam;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordIdParam;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordPageParam;
import vip.xiaonuo.oa.modular.attendance.record.vo.RecordPreVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 考勤记录表Service接口
 *
 * @author mayike
 * @date  2024/06/12 11:03
 **/
public interface RecordService extends IService<Record> {

    /**
     * 获取考勤记录表分页
     *
     * @author mayike
     * @date  2024/06/12 11:03
     */
    Page<Record> page(RecordPageParam recordPageParam);

    /**
     * 添加考勤记录表
     *
     * @author mayike
     * @date  2024/06/12 11:03
     */
    void add(RecordAddParam recordAddParam);

    /**
     * 编辑考勤记录表
     *
     * @author mayike
     * @date  2024/06/12 11:03
     */
    void edit(RecordEditParam recordEditParam);

    /**
     * 删除考勤记录表
     *
     * @author mayike
     * @date  2024/06/12 11:03
     */
    void delete(List<RecordIdParam> recordIdParamList);

    /**
     * 获取考勤记录表详情
     *
     * @author mayike
     * @date  2024/06/12 11:03
     */
    Record detail(RecordIdParam recordIdParam);

    /**
     * 获取考勤记录表详情
     *
     * @author mayike
     * @date  2024/06/12 11:03
     **/
    Record queryEntity(String id);

    RecordPreVo preCheck();

    void checkIn(RecordAddParam recordAddParam);
}
