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
package vip.xiaonuo.dev.modular.supervisionPlatform.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.dev.modular.supervisionPlatform.entity.TBCorpperFormance;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBCorpperFormanceAddParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBCorpperFormanceEditParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBCorpperFormanceIdParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBCorpperFormancePageParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 市场监管公共服务平台-企业业绩技术指标信息表Service接口
 *
 * @author ljy
 * @date  2024/01/11 10:57
 **/
public interface TBCorpperFormanceService extends IService<TBCorpperFormance> {

    /**
     * 获取市场监管公共服务平台-企业业绩技术指标信息表分页
     *
     * @author ljy
     * @date  2024/01/11 10:57
     */
    Page<TBCorpperFormance> page(TBCorpperFormancePageParam tbcorpperformancePageParam);

    /**
     * 添加市场监管公共服务平台-企业业绩技术指标信息表
     *
     * @author ljy
     * @date  2024/01/11 10:57
     */
    void add(TBCorpperFormanceAddParam tbcorpperformanceAddParam);

    /**
     * 编辑市场监管公共服务平台-企业业绩技术指标信息表
     *
     * @author ljy
     * @date  2024/01/11 10:57
     */
    void edit(TBCorpperFormanceEditParam tbcorpperformanceEditParam);

    /**
     * 删除市场监管公共服务平台-企业业绩技术指标信息表
     *
     * @author ljy
     * @date  2024/01/11 10:57
     */
    void delete(List<TBCorpperFormanceIdParam> TBCorpperFormanceIdParamList);

    /**
     * 获取市场监管公共服务平台-企业业绩技术指标信息表详情
     *
     * @author ljy
     * @date  2024/01/11 10:57
     */
    TBCorpperFormance detail(TBCorpperFormanceIdParam tbcorpperformanceIdParam);

    /**
     * 获取市场监管公共服务平台-企业业绩技术指标信息表详情
     *
     * @author ljy
     * @date  2024/01/11 10:57
     **/
    TBCorpperFormance queryEntity(String id);


    /**
     * 下载用户导入模板
     *
     * @author ljy
     * @date 2024/01/12 10:30
     **/
    void downloadImportUserTemplate(HttpServletResponse response,Integer excelType) throws IOException;

    /**
     * 建筑监管市场数据导入
     * */
    void importRuralData(String fileId,Integer excelType);
}
