package vip.xiaonuo.oa.modular.attendance.apply.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.xiaonuo.oa.modular.attendance.apply.entity.Apply;
import vip.xiaonuo.oa.modular.attendance.apply.entity.ApplyJson;

/**
 * 考勤申请管理表Mapper接口
 *
 * @author mayike
 * @date  2024/07/15 09:56
 **/
public interface ApplyMapper<T extends ApplyJson> extends BaseMapper<Apply<T>> {
}
