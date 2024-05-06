package vip.xiaonuo.dev.modular.server.entity;

import lombok.Data;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/3/8 11:22
 */
@Data
public class ExtMessage {
    String id;
    String data;
    int operateType; //操作类型 1、 新增  2、 删除 3、同步用户
}
