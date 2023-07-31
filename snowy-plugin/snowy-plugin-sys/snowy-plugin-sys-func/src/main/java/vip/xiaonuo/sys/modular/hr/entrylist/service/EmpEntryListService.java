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
package vip.xiaonuo.sys.modular.hr.entrylist.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.entrylist.entity.EmpEntryList;
import vip.xiaonuo.sys.modular.hr.entrylist.param.EmpEntryListAddParam;
import vip.xiaonuo.sys.modular.hr.entrylist.param.EmpEntryListEditParam;
import vip.xiaonuo.sys.modular.hr.entrylist.param.EmpEntryListIdParam;
import vip.xiaonuo.sys.modular.hr.entrylist.param.EmpEntryListPageParam;

import java.util.List;

/**
 * 入职名单Service接口
 *
 * @author yong.ye
 * @date  2023/07/31 09:25
 **/
public interface EmpEntryListService extends IService<EmpEntryList> {

    /**
     * 获取入职名单分页
     *
     * @author yong.ye
     * @date  2023/07/31 09:25
     */
    Page<EmpEntryList> page(EmpEntryListPageParam empEntryListPageParam);

    /**
     * 添加入职名单
     *
     * @author yong.ye
     * @date  2023/07/31 09:25
     */
    void add(EmpEntryListAddParam empEntryListAddParam);

    /**
     * 编辑入职名单
     *
     * @author yong.ye
     * @date  2023/07/31 09:25
     */
    void edit(EmpEntryListEditParam empEntryListEditParam);

    /**
     * 删除入职名单
     *
     * @author yong.ye
     * @date  2023/07/31 09:25
     */
    void delete(List<EmpEntryListIdParam> empEntryListIdParamList);

    /**
     * 获取入职名单详情
     *
     * @author yong.ye
     * @date  2023/07/31 09:25
     */
    EmpEntryList detail(EmpEntryListIdParam empEntryListIdParam);

    /**
     * 获取入职名单详情
     *
     * @author yong.ye
     * @date  2023/07/31 09:25
     **/
    EmpEntryList queryEntity(String id);
}
