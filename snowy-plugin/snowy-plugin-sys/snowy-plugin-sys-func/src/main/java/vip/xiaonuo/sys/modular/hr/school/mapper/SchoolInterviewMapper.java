package vip.xiaonuo.sys.modular.hr.school.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.sys.modular.hr.school.entity.SchoolInterview;
import vip.xiaonuo.sys.modular.hr.school.vo.SchoolInterviewVo;

/**
 * 校招 - （应聘者）- 面试入职流程Mapper接口
 *
 * @author yong.ye
 * @date  2024/09/03 10:46
 **/
public interface SchoolInterviewMapper extends BaseMapper<SchoolInterview> {

    Page<SchoolInterviewVo> getPageList(Page<SchoolInterviewVo> page, @Param(Constants.WRAPPER) Wrapper<SchoolInterview> queryWrapper);

    SchoolInterviewVo getDetail(String id);
}
