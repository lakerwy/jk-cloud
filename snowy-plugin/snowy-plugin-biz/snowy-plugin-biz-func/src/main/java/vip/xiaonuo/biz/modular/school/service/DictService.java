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
package vip.xiaonuo.biz.modular.school.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.school.entity.Dict;
import vip.xiaonuo.biz.modular.school.param.DictAddParam;
import vip.xiaonuo.biz.modular.school.param.DictEditParam;
import vip.xiaonuo.biz.modular.school.param.DictIdParam;
import vip.xiaonuo.biz.modular.school.param.DictPageParam;

import java.util.List;
import java.util.Map;

/**
 * 系统字典-自定义Service接口
 *
 * @author yong.ye
 * @date  2023/11/15 08:51
 **/
public interface DictService extends IService<Dict> {

    /**
     * 获取系统字典-自定义分页
     *
     * @author yong.ye
     * @date  2023/11/15 08:51
     */
    Page<Dict> page(DictPageParam dictPageParam);

    /**
     * 添加系统字典-自定义
     *
     * @author yong.ye
     * @date  2023/11/15 08:51
     */
    void add(DictAddParam dictAddParam);

    /**
     * 编辑系统字典-自定义
     *
     * @author yong.ye
     * @date  2023/11/15 08:51
     */
    void edit(DictEditParam dictEditParam);

    /**
     * 删除系统字典-自定义
     *
     * @author yong.ye
     * @date  2023/11/15 08:51
     */
    void delete(List<DictIdParam> dictIdParamList);

    /**
     * 获取系统字典-自定义详情
     *
     * @author yong.ye
     * @date  2023/11/15 08:51
     */
    Dict detail(DictIdParam dictIdParam);

    /**
     * 获取系统字典-自定义详情
     *
     * @author yong.ye
     * @date  2023/11/15 08:51
     **/
    Dict queryEntity(String id);

    /**
     * 根据字典类型Type，获取字典集合
     * @param type
     * @return
     */
    List<Dict> getDictByType(String type);

    /**
     * 获取所有字典key-value集合
     * @return
     */
    Map<String,String> getAllDictKeyVale();

    /**
     * 获取所有字典类型
     * @return
     */
    List<String> getAllDictType();

}
