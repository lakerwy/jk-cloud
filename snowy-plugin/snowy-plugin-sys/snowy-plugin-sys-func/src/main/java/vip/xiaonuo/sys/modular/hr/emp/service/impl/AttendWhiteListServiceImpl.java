package vip.xiaonuo.sys.modular.hr.emp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.util.CommonCryptogramUtil;
import vip.xiaonuo.common.util.CommonEmailUtil;
import vip.xiaonuo.sys.modular.hr.emp.entity.AttendWhiteList;
import vip.xiaonuo.sys.modular.hr.emp.mapper.AttendWhiteListMapper;
import vip.xiaonuo.sys.modular.hr.emp.param.AttendWhiteListAddParam;
import vip.xiaonuo.sys.modular.hr.emp.param.AttendWhiteListEditParam;
import vip.xiaonuo.sys.modular.hr.emp.param.AttendWhiteListIdParam;
import vip.xiaonuo.sys.modular.hr.emp.param.AttendWhiteListPageParam;
import vip.xiaonuo.sys.modular.hr.emp.service.AttendWhiteListService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.param.SysUserAddParam;

import java.util.List;

/**
 * 钉钉考勤-员工白名单Service接口实现类
 *
 * @author pzh
 * @date  2025/03/06 11:34
 **/
@Service
public class AttendWhiteListServiceImpl extends ServiceImpl<AttendWhiteListMapper, AttendWhiteList> implements AttendWhiteListService {

    @Override
    public Page<AttendWhiteList> page(AttendWhiteListPageParam attendWhiteListPageParam) {
        QueryWrapper<AttendWhiteList> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(attendWhiteListPageParam.getType())){
            queryWrapper.lambda().eq(AttendWhiteList::getType, attendWhiteListPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(attendWhiteListPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(AttendWhiteList::getUserName,attendWhiteListPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(attendWhiteListPageParam.getSortField(), attendWhiteListPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(attendWhiteListPageParam.getSortOrder());
            queryWrapper.orderBy(true, attendWhiteListPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(attendWhiteListPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(AttendWhiteList::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(AttendWhiteListAddParam attendWhiteListAddParam) {
        if (this.count(new LambdaQueryWrapper<AttendWhiteList>()
                .eq(AttendWhiteList::getUserId, attendWhiteListAddParam.getUserId())) > 0) {
            throw new CommonException("存在重复的人员，姓名为：{}，id为：{}",
                    attendWhiteListAddParam.getUserName(),attendWhiteListAddParam.getUserId());
        }
        AttendWhiteList attendWhiteList = BeanUtil.toBean(attendWhiteListAddParam, AttendWhiteList.class);
        this.save(attendWhiteList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(AttendWhiteListEditParam attendWhiteListEditParam) {
        if (this.count(new LambdaQueryWrapper<AttendWhiteList>()
                .eq(AttendWhiteList::getUserId, attendWhiteListEditParam.getUserId())
                .ne(AttendWhiteList::getId, attendWhiteListEditParam.getId())) > 0) {
            throw new CommonException("存在重复的人员，姓名为：{}，id为：{}",
                    attendWhiteListEditParam.getUserName(),attendWhiteListEditParam.getUserId());
        }
        AttendWhiteList attendWhiteList = this.queryEntity(attendWhiteListEditParam.getId());
        BeanUtil.copyProperties(attendWhiteListEditParam, attendWhiteList);
        this.updateById(attendWhiteList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<AttendWhiteListIdParam> attendWhiteListIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(attendWhiteListIdParamList, AttendWhiteListIdParam::getId));
    }

    @Override
    public AttendWhiteList detail(AttendWhiteListIdParam attendWhiteListIdParam) {
        return this.queryEntity(attendWhiteListIdParam.getId());
    }

    @Override
    public AttendWhiteList queryEntity(String id) {
        AttendWhiteList attendWhiteList = this.getById(id);
        if(ObjectUtil.isEmpty(attendWhiteList)) {
            throw new CommonException("钉钉考勤-员工白名单不存在，id值为：{}", id);
        }
        return attendWhiteList;
    }
}
