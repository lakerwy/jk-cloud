package vip.xiaonuo.sys.modular.hr.school.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.sys.modular.hr.school.entity.SchoolResume;

import java.util.List;

/**
 * 校招 - （应聘者）简历库信息Mapper接口
 *
 * @author yong.ye
 * @date  2024/09/03 10:44
 **/
public interface SchoolResumeMapper extends BaseMapper<SchoolResume> {
    List<SchoolResume> getApprovedResumeList(@Param("name") String name);
}
