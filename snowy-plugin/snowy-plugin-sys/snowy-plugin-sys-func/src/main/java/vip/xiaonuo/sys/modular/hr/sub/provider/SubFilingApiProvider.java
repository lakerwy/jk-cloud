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
package vip.xiaonuo.sys.modular.hr.sub.provider;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.sys.api.SubFilingApi;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFilingItemEditParam;
import vip.xiaonuo.sys.modular.hr.sub.service.SubFilingService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分包单位备案API提供者
 *
 * @author pzh
 * @date  2025/03/11 17:32
 **/
@Slf4j
@Service
public class SubFilingApiProvider implements SubFilingApi {

    @Resource
    private SubFilingService subFilingService;

    @Override
    public void updateSubFilingItem(String id, String subFilingItemList) {
        log.info("更新-分包单位备案明细时,参数值 id:{},subFilingItemList:{}", id, subFilingItemList);
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = JSONUtil.parseArray(subFilingItemList);
        } catch (Exception e){
            log.info("更新-分包单位备案明细时,参数转换异常");
        }
        List<SubFilingItemEditParam> subFilingItemEditParamList = JSONUtil.toList(jsonArray, SubFilingItemEditParam.class);
        //更新-分包单位备案明细
        subFilingService.updateSubFilingItem(id, subFilingItemEditParamList);
    }
}
