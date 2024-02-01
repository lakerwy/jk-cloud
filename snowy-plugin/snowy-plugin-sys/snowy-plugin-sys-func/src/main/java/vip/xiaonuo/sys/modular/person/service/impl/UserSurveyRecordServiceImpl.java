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
package vip.xiaonuo.sys.modular.person.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.person.entity.UserSurveyRecord;
import vip.xiaonuo.sys.modular.person.mapper.UserSurveyRecordMapper;
import vip.xiaonuo.sys.modular.person.param.UserSurveyRecordAddParam;
import vip.xiaonuo.sys.modular.person.param.UserSurveyRecordEditParam;
import vip.xiaonuo.sys.modular.person.param.UserSurveyRecordIdParam;
import vip.xiaonuo.sys.modular.person.param.UserSurveyRecordPageParam;
import vip.xiaonuo.sys.modular.person.service.UserSurveyRecordService;

import java.util.Date;
import java.util.List;

/**
 * 用户问卷收集-问题|意见-一问一答记录Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/01 09:53
 **/
@Service
public class UserSurveyRecordServiceImpl extends ServiceImpl<UserSurveyRecordMapper, UserSurveyRecord> implements UserSurveyRecordService {

    @Override
    public Page<UserSurveyRecord> page(UserSurveyRecordPageParam userSurveyRecordPageParam) {
        QueryWrapper<UserSurveyRecord> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(userSurveyRecordPageParam.getSortField(), userSurveyRecordPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(userSurveyRecordPageParam.getSortOrder());
            queryWrapper.orderBy(true, userSurveyRecordPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(userSurveyRecordPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(UserSurveyRecord::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(UserSurveyRecordAddParam userSurveyRecordAddParam) {
        UserSurveyRecord userSurveyRecord = BeanUtil.toBean(userSurveyRecordAddParam, UserSurveyRecord.class);
        userSurveyRecord.setOprName(StpLoginUserUtil.getLoginUser().getName());
        userSurveyRecord.setOprTime(new Date());
        this.save(userSurveyRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(UserSurveyRecordEditParam userSurveyRecordEditParam) {
        UserSurveyRecord userSurveyRecord = this.queryEntity(userSurveyRecordEditParam.getId());
        userSurveyRecord.setOprName(StpLoginUserUtil.getLoginUser().getName());
        userSurveyRecord.setOprTime(new Date());
        BeanUtil.copyProperties(userSurveyRecordEditParam, userSurveyRecord);
        this.updateById(userSurveyRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<UserSurveyRecordIdParam> userSurveyRecordIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(userSurveyRecordIdParamList, UserSurveyRecordIdParam::getId));
    }

    @Override
    public UserSurveyRecord detail(UserSurveyRecordIdParam userSurveyRecordIdParam) {
        return this.queryEntity(userSurveyRecordIdParam.getId());
    }

    @Override
    public UserSurveyRecord queryEntity(String id) {
        UserSurveyRecord userSurveyRecord = this.getById(id);
        if(ObjectUtil.isEmpty(userSurveyRecord)) {
            throw new CommonException("用户问卷收集-问题|意见-一问一答记录不存在，id值为：{}", id);
        }
        return userSurveyRecord;
    }
}
