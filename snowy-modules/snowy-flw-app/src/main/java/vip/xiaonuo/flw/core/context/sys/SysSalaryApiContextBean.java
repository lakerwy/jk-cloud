package vip.xiaonuo.flw.core.context.sys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.sys.api.SysSalaryApi;
import vip.xiaonuo.sys.feign.SysSalaryFeign;

@Slf4j
@RequiredArgsConstructor
@Component
public class SysSalaryApiContextBean implements SysSalaryApi {

    private final SysSalaryFeign sysSalaryFeign;
    @Override
    public void computeSeniorityGZ() {
        sysSalaryFeign.computeSeniorityGZ();
    }

    @Override
    public void computeBirthdayGZ() {
        sysSalaryFeign.computeBirthdayGZ();
    }
}
