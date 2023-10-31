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
package vip.xiaonuo.sys.modular.hr.userHolidayDataRec.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.entity.HrUserHolidayDataRec;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.param.HrUserHolidayDataRecAddParam;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.param.HrUserHolidayDataRecEditParam;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.param.HrUserHolidayDataRecIdParam;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.param.HrUserHolidayDataRecPageParam;

import java.util.List;

/**
 * 公司员工假期增减详情数据Service接口
 *
 * @author ljy
 * @date  2023/10/26 09:21
 **/
public interface HrUserHolidayDataRecService extends IService<HrUserHolidayDataRec> {

    /**
     * 获取公司员工假期增减详情数据分页
     *
     * @author ljy
     * @date  2023/10/26 09:21
     */
    Page<HrUserHolidayDataRec> page(HrUserHolidayDataRecPageParam hrUserHolidayDataRecPageParam);

    /**
     * 添加公司员工假期增减详情数据
     *
     * @author ljy
     * @date  2023/10/26 09:21
     */
    void add(HrUserHolidayDataRecAddParam hrUserHolidayDataRecAddParam);

    /**
     * 编辑公司员工假期增减详情数据
     *
     * @author ljy
     * @date  2023/10/26 09:21
     */
    void edit(HrUserHolidayDataRecEditParam hrUserHolidayDataRecEditParam);

    /**
     * 删除公司员工假期增减详情数据
     *
     * @author ljy
     * @date  2023/10/26 09:21
     */
    void delete(List<HrUserHolidayDataRecIdParam> hrUserHolidayDataRecIdParamList);

    /**
     * 获取公司员工假期增减详情数据详情
     *
     * @author ljy
     * @date  2023/10/26 09:21
     */
    HrUserHolidayDataRec detail(HrUserHolidayDataRecIdParam hrUserHolidayDataRecIdParam);

    /**
     * 获取公司员工假期增减详情数据详情
     *
     * @author ljy
     * @date  2023/10/26 09:21
     **/
    HrUserHolidayDataRec queryEntity(String id);
}
