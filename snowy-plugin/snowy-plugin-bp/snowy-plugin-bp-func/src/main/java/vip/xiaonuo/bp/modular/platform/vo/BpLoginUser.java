package vip.xiaonuo.bp.modular.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.bp.modular.recruit.supplier.entity.BpSupplier;
import vip.xiaonuo.bp.modular.recruit.supplier.vo.BpSupplierReturnVo;

/**
 * 招采平台-用户登录信息实体
 */

@Getter
@Setter
public class BpLoginUser {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 2)
    private String name;

    /** 账号 */
    @ApiModelProperty(value = "账号", position = 3)
    private String account;

    /** 密码 */
    @ApiModelProperty(value = "密码", position = 4)
    private String password;

    /** 用户状态 */
    @ApiModelProperty(value = "用户状态", position = 5)
    private String userStatus;

    /** 邮箱 */
    @ApiModelProperty(value = "邮箱", position = 6)
    private String email;

    /** 手机 */
    @ApiModelProperty(value = "手机", position = 7)
    private String phone;

    /** 当前用户所属供应商 */
    @ApiModelProperty(value = "当前用户所属供应商", position = 7)
    private String supplierId;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 9)
    private String tenantId;

    /** 当前用户所属供应商信息 */
    @ApiModelProperty(value = "当前用户所属供应商信息", position = 10)
    private BpSupplierReturnVo supplierVo;

}
