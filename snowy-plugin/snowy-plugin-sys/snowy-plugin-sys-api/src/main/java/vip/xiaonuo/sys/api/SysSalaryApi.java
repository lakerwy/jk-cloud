package vip.xiaonuo.sys.api;

/**
 * 工资系统：对外暴露接口
 */
public interface SysSalaryApi {

    /**
     * 计算 工龄工资
     * @return
     */
    void computeSeniorityGZ();


    /**
     * 计算 生日福利
     * @return
     */
    void computeBirthdayGZ();

}
