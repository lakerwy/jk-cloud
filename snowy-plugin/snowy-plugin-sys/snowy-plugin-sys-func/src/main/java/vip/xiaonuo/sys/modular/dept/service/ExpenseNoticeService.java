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
package vip.xiaonuo.sys.modular.dept.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.dept.entity.ExpenseNotice;
import vip.xiaonuo.sys.modular.dept.param.*;

import java.util.List;

/**
 * 部门报销公示Service接口
 *
 * @author yong.ye
 * @date  2024/03/27 09:04
 **/
public interface ExpenseNoticeService extends IService<ExpenseNotice> {

    /**
     * 获取部门报销公示分页
     *
     * @author yong.ye
     * @date  2024/03/27 09:04
     */
    Page<ExpenseNotice> page(ExpenseNoticePageParam expenseNoticePageParam);

    /**
     * 添加部门报销公示
     *
     * @author yong.ye
     * @date  2024/03/27 09:04
     */
    void add(ExpenseNoticeAddParam expenseNoticeAddParam);

    /**
     * 编辑部门报销公示
     *
     * @author yong.ye
     * @date  2024/03/27 09:04
     */
    void edit(ExpenseNoticeEditParam expenseNoticeEditParam);

    /**
     * 删除部门报销公示
     *
     * @author yong.ye
     * @date  2024/03/27 09:04
     */
    void delete(List<ExpenseNoticeIdParam> expenseNoticeIdParamList);

    /**
     * 获取部门报销公示详情
     *
     * @author yong.ye
     * @date  2024/03/27 09:04
     */
    ExpenseNotice detail(ExpenseNoticeIdParam expenseNoticeIdParam);

    /**
     * 获取部门报销公示详情
     *
     * @author yong.ye
     * @date  2024/03/27 09:04
     **/
    ExpenseNotice queryEntity(String id);

    /**
     * 导入校验结果
     * @param input
     * @return
     */
    void importNotice(ImportNotice input);


    /**
     * 删除全部数据
     */
    void delAll();
}
