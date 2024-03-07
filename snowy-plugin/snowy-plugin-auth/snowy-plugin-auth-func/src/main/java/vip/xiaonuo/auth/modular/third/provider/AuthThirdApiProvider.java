package vip.xiaonuo.auth.modular.third.provider;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import vip.xiaonuo.auth.api.AuthThirdApi;
import vip.xiaonuo.auth.modular.third.entity.AuthThirdUser;
import vip.xiaonuo.auth.modular.third.service.AuthThirdService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yong.ye
 * @date 2023/6/14 17:08
 */
@Service
public class AuthThirdApiProvider implements AuthThirdApi {

    @Resource
    private AuthThirdService authThirdService;

    /**
     * 通过第三方登录类型+第三方用户Id,获取对应的系统用户Id
     * @param thirdId 第三方Id
     * @param category 类型
     * @return
     */
    @Override
    public String getUserIdByThirdId(String thirdId,String category) {
        AuthThirdUser thirdUser = authThirdService.getOne(new LambdaQueryWrapper<AuthThirdUser>().eq(AuthThirdUser::getThirdId, thirdId)
                .eq(AuthThirdUser::getCategory, category).last(" limit 1 "));
        return ObjectUtil.isEmpty(thirdUser) ? "" : thirdUser.getUserId();
    }

    @Override
    public String getThirdIdByUserId(String userId, String category) {
        AuthThirdUser thirdUser = authThirdService.getOne(new LambdaQueryWrapper<AuthThirdUser>().eq(AuthThirdUser::getUserId, userId)
                .eq(AuthThirdUser::getCategory, category).last(" limit 1 "));
        return ObjectUtil.isEmpty(thirdUser) ? "" : thirdUser.getThirdId();
    }
    @Override
    public String getUnionIdByUserId(String userId,String category){
        AuthThirdUser thirdUser = authThirdService.getOne(new LambdaQueryWrapper<AuthThirdUser>().eq(AuthThirdUser::getUserId, userId)
                .eq(AuthThirdUser::getCategory, category).last(" limit 1 "));
        return ObjectUtil.isNotEmpty(thirdUser) && StringUtils.isNotEmpty(thirdUser.getUnionId()) ? "" : thirdUser.getUnionId();
    }

    @Override
    public Boolean addAuthThird(String userId, String thirdId, String category, String name) {
        //为了避免微秒级重复调用（增加判断逻辑）
        List<AuthThirdUser> oldList = authThirdService.list(new QueryWrapper<AuthThirdUser>().lambda().eq(AuthThirdUser::getCategory,category).eq(AuthThirdUser::getUserId,userId).eq(AuthThirdUser::getThirdId,thirdId));
        //已存在相同记录，不再新增，直接返回
        if(CollUtil.isNotEmpty(oldList)){
            return true;
        }
        AuthThirdUser thirdUser = new AuthThirdUser();
        thirdUser.setThirdId(thirdId);
        thirdUser.setUserId(userId);
        thirdUser.setCategory(category);
        thirdUser.setName(name);
        return authThirdService.save(thirdUser);
    }

    /**
     * 当thirdId存在，而unionId不存在，修改
     * 当thirdId和unionId都不存在，新增
     * */
    @Override
    public Boolean updateAuthThird(String userId,String thirdId,String category,String name,String unionId){
        List<AuthThirdUser> oldList = authThirdService.list(new QueryWrapper<AuthThirdUser>().lambda().eq(AuthThirdUser::getCategory,category).eq(AuthThirdUser::getUserId,userId).eq(AuthThirdUser::getThirdId,thirdId));
        if(CollectionUtil.isNotEmpty(oldList) && StringUtils.isNotEmpty(oldList.get(0).getUnionId())){ return true; }

        AuthThirdUser thirdUser = CollectionUtil.isNotEmpty(oldList) ? oldList.get(0) : new AuthThirdUser();
        thirdUser.setThirdId(thirdId);
        thirdUser.setUserId(userId);
        thirdUser.setCategory(category);
        thirdUser.setName(name);
        thirdUser.setUnionId(unionId);
        return authThirdService.saveOrUpdate(thirdUser);
    }

    @Override
    public Boolean delAuthThird(String userId, String category) {
        return authThirdService.remove(new LambdaQueryWrapper<AuthThirdUser>().eq(AuthThirdUser::getUserId, userId)
                .eq(AuthThirdUser::getCategory, category));
    }

    @Override
    public List<Map<String, String>> getKeyValueByCategory(String category) {
        List<AuthThirdUser> list = authThirdService.list(new LambdaQueryWrapper<AuthThirdUser>().eq(AuthThirdUser::getCategory, category));
        return list.stream().map(item -> {
            Map<String, String> map = new HashMap<>();
            map.put("userId", item.getUserId());
            map.put("thirdId", item.getThirdId());
            return map;
        }).collect(Collectors.toList());
    }
}
