package vip.xiaonuo.sys.modular.info.provider;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import vip.xiaonuo.sys.api.PartnerInfoApi;
import vip.xiaonuo.sys.modular.info.entity.PartnerInfo;
import vip.xiaonuo.sys.modular.info.service.PartnerInfoService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统模块综合API接口实现类
 *
 * @author pzh
 * @date 2024/12/27 16:50
 **/
@Service
public class PartnerInfoApiProvider implements PartnerInfoApi {

    @Resource
    private PartnerInfoService partnerInfoService;

    /**
     * 根据分公司区域获取合作伙伴
     **/
    @Override
    public List<JSONObject> getByRegion(String region) {
        List<PartnerInfo> list = partnerInfoService.list(new LambdaQueryWrapper<PartnerInfo>().eq(PartnerInfo::getRegion, region));
        if (ObjectUtil.isNotEmpty(list)) {
            return list.stream().map(JSONUtil::parseObj).collect(Collectors.toList());
        }
        return CollectionUtil.newArrayList();
    }
}
