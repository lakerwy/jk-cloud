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
package vip.xiaonuo.dev.feign;

import cn.hutool.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vip.xiaonuo.common.consts.FeignConstant;

import java.util.List;
import java.util.Map;

/**
 * 动态字段Feign接口
 *
 * @author yubaoshan
 * @date 2023/8/14 20:41
 */
@FeignClient(name= FeignConstant.WEB_APP, contextId = "DevDfcFeign")
public interface DevDfcFeign {

    /**
     * 通过数据源id与表明获得数据库表列表
     *
     * @author yubaoshan
     * @date 2023/8/14 20:41
     */
    @RequestMapping("/feign/dev/dfc/getTableFieldList")
    List<JSONObject> getTableFieldList(@RequestParam("dbsId") String dbsId, @RequestParam("tableName") String tableName);

    /**
     * 通过数据源id与表明获得数据库表列表
     *
     * @author yubaoshan
     * @date 2023/8/14 20:41
     */
    @RequestMapping("/feign/dev/dfc/addDingDingScheduleHandler")
    void addDingDingScheduleHandler(@RequestParam("mapParam") Map<String,Object> mapParam);

}
