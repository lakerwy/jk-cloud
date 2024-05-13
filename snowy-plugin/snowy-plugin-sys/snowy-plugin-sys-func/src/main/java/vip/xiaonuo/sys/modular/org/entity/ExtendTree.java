package vip.xiaonuo.sys.modular.org.entity;

import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/13 9:56
 */
@Data
public class ExtendTree<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private T id;

    /**
     * 父节点ID
     */
    private T parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 顺序 越小优先级越高 默认0
     */
    private int sort;

    /**
     * 扩展字段
     */
    private Map<String, Object> extra;

    private List<ExtendTree<T>> nodes;

    private List<SyncUser> syncUsers;

    public ExtendTree(T id, T parentId, String name, int sort, Map<String, Object> extra,List<ExtendTree<T>> nodes,List<SyncUser> syncUsers) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.sort = sort;
        this.extra = extra;
        this.nodes = nodes;
        this.syncUsers = syncUsers;
    }

    // 添加额外字段的方法
    public void addExtraField(String key, Object value) {
        if (extra == null) {
            extra = new HashMap<>();
        }
        extra.put(key, value);
    }

    // 获取额外字段的方法
    public Object getExtraField(String key) {
        return extra != null ? extra.get(key) : null;
    }


    @Data
    @AllArgsConstructor
    public static class SyncUser{
        String email;  //对应account
        String name;

        String oldId; //对应ID
    }

}
