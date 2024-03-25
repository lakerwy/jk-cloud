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
package vip.xiaonuo.sys.modular.hr.salary.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryPreview;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryPreviewAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryPreviewEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryPreviewIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryPreviewPageParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.MySalaryParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.SearchParam;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryPreviewVo;

import java.util.List;

/**
 * 工资系统-工资预览表Service接口
 *
 * @author yong.ye
 * @date  2024/03/22 10:26
 **/
public interface SalaryPreviewService extends IService<SalaryPreview> {

    /**
     * 获取工资系统-工资预览表分页
     *
     * @author yong.ye
     * @date  2024/03/22 10:26
     */
    Page<SalaryPreview> page(SalaryPreviewPageParam salaryPreviewPageParam);

    /**
     * 添加工资系统-工资预览表
     *
     * @author yong.ye
     * @date  2024/03/22 10:26
     */
    void add(SalaryPreviewAddParam salaryPreviewAddParam);

    /**
     * 编辑工资系统-工资预览表
     *
     * @author yong.ye
     * @date  2024/03/22 10:26
     */
    void edit(SalaryPreviewEditParam salaryPreviewEditParam);

    /**
     * 删除工资系统-工资预览表
     *
     * @author yong.ye
     * @date  2024/03/22 10:26
     */
    void delete(List<SalaryPreviewIdParam> salaryPreviewIdParamList);

    /**
     * 获取工资系统-工资预览表详情
     *
     * @author yong.ye
     * @date  2024/03/22 10:26
     */
    SalaryPreview detail(SalaryPreviewIdParam salaryPreviewIdParam);

    /**
     * 获取工资系统-工资预览表详情
     *
     * @author yong.ye
     * @date  2024/03/22 10:26
     **/
    SalaryPreview queryEntity(String id);

    /**
     * 月工资报表 - (未确认的)预览数据（自动生成）
     * @param
     * @return
     */
    List<SalaryPreviewVo> prePage(MySalaryParam param);

    /**
     * 获取所涉及月份的工资数据
     * @param type 工资类型
     * @param yearMonths 月份
     * @return
     */
    List<SalaryPreviewVo> getPreviewAll(String type,List<String> yearMonths);

    /**
     * 删除 工资表 已确认的数据 - 删除某年月数据
     * @param param
     */
    void deleteByMonth(SearchParam param);

    /**
     * 预览工资 - 确认
     * @param param
     */
    void preSure(SearchParam param);

    /**
     * 工资封存
     * @param param
     */
    void preArchive(SearchParam param);
}
