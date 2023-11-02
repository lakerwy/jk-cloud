package vip.xiaonuo.pan.modular.fileRight.constant;

import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.pan.modular.file.constant.FsEventType;
import vip.xiaonuo.pan.modular.file.constant.OperationType;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
public class FileRight {

    /**
     * 无权限
     */
    public static final long RIGHT_NULL = 0L; //无权限
    /**
     * 可见
     */
    public static final long LIST = 1L; //列表可见
    /**
     * 下载
     */
    public static final long DOWNLOAD = 1L << 1; //下载
    /**
     * 创建文件夹
     */
    public static final long CREATE = 1L << 2; //创建文件夹
    /**
     * 编辑
     */
    public static final long EDIT = 1L << 3; //编辑
    /**
     * 移动
     */
    public static final long MOVE = 1L << 4; //移动
    /**
     * 删除
     */
    public static final long DELETE = 1L << 5; //删除
    /**
     * 预览
     */
    public static final long PREVIEW = 1L << 6; //预览
    /**
     * 文件上传
     */
    public static final long UPLOAD = 1L << 7; //文件上传
    /**
     * 复制
     */
    public static final long COPY = 1L << 8; //复制
    /**
     * 重命名
     */
    public static final long RENAME = 1L << 9; //重命名
    /**
     * 共享
     */
    public static final long SHARE = 1L << 10; //共享
    /**
     * 分享
     */
    public static final long SHARE_LINK = 1L << 11; //生成外链（分享）
    /**
     * 创建文件
     */
    public static final long CREATE_FILE = 1L << 12; //创建文件
    /**
     * 权限设置
     */
    public static final long RIGHT_SET = 1L << 13; //权限设置



    // 全部权限
    public static final long ALL_RIGHT = LIST | DOWNLOAD | CREATE | EDIT | MOVE | DELETE |
            UPLOAD | COPY | RENAME | PREVIEW | SHARE | SHARE_LINK | CREATE_FILE | RIGHT_SET;

    /**
     * 权限校验：right(已有权限)/ checkRight权限校验值(所需权限) 拥有所需权限里面的所有权限才可以
     */
    public static boolean hasRight(long right, long checkRight) {
        return (right & checkRight) == checkRight;
    }



//    public static final long ACCESS = 1L << 8; //访问(老权限兼容项)
//
//    public static final long ADMIN = 1L << 9; //管理员(老权限兼容项)
//
//    public static final long FORBIDDEN = 1L << 29; //禁止访问
//
//    public static final long V_DELETE = 1L << 41; //虚拟删除，用于在父目录设置权限，子目录或文件删除了这个权限
//
//    public static final long SHOW = 1L << 42; //可见
//
//    public static final long SHOW_FILE = 1L << 43; //文件可见
//
//    public static final long PENETRATE = 1L << 44; //穿透权限（子目录有权限，父目录未设置独立权限，赋予穿透权限）
//
//    public static final long LOCK = 1L << 7; //锁定/解锁  暂未用到
//
//    public static final long COLLECT = 1L << 13; //收藏  暂未用到
//
//    public static final long DELETE_RECOVERY = 1L << 14; //删除恢复
//
//    public static final long SUBSCRIBE_DIR = 1L << 15; //文件夹订阅 暂未用到
//
//    public static final long SYNC = 1L << 17; //同步
//
//    public static final long CREATOR_VISIABLE = 1L << 18; //创建者可见
//
//    public static final long RENAME_FILE = 1L << 19; //重命名
//
//    public static final long DOWNLOAD_FILE = 1L << 20; //下载
//
//    public static final long MOVE_FILE = 1L << 21; //移动
//
//    public static final long COPY_FILE = 1L << 22; //复制
//
//    public static final long DELETE_FILE = 1L << 23; //删除
//
//    public static final long COLLECT_FILE = 1L << 24; //收藏 暂未用到
//
//    public static final long PRINT = 1L << 25; //打印
//
//    public static final long COMMENT = 1L << 26; //评论  暂未用到
//
//    public static final long DELETE_RECOVERY_FILE = 1L << 27; //文件恢复
//
//    public static final long SUBSCRIBE_FILE = 1L << 28; //文件订阅  暂未用到
//
//    public static final long PERSON_LABLE = 1L << 30; //个人标签(原群组应用) 暂未用到
//
//    public static final long SHARE_SEND = 1L << 31; //发送同事  暂未用到
//
//    public static final long CREATE_COOPERATE = 1L << 32; //创建协同  暂未用到
//
//    public static final long HISTORY_VERSION = 1L << 33; //历史版本(原离职交接)  暂未用到
//
//    public static final long GROUP_LABLE = 1L << 34; //群组标签(原群组回收站)  暂未用到
//
//    public static final long PUBLIC_FILE = 1L << 35; //公开文件/外发 ？ //
//
//    public static final long SAVE_TO = 1L << 36; //转存  暂未用到
//
//    public static final long FILE_CONTROL = 1L << 37; //文件完全控制(原协同应用),预留字段，暂无实际作用
//
//    public static final long DIR_CONTROL = 1L << 38; //文件夹完全控制(原分享应用),预留字段，暂未实现功能
//
//    public static final long FILE_MANAGER = 1L << 39; //成员管理  暂未用到
//
//    public static final long DIR_LOCK = 1L << 40; //锁定/解锁 暂未用到
//
//
//    // 全部权限：除 ADMIN ACCESS外的所有已经应用的权限
//    public static final long ALL_RIGHT = LIST | DOWNLOAD | CREATE | MOVE | DELETE | UPLOAD | COPY | COLLECT | DELETE_RECOVERY
//            | SUBSCRIBE_DIR | RENAME | SYNC | PREVIEW | CREATOR_VISIABLE | RENAME_FILE | EDIT | DOWNLOAD_FILE | MOVE_FILE | COPY_FILE
//            | DELETE_FILE | COLLECT_FILE | PRINT | LOCK | COMMENT | DELETE_RECOVERY_FILE | SUBSCRIBE_FILE | SHARE_LINK | SHARE_SEND
//            | CREATE_COOPERATE | FILE_MANAGER | PERSON_LABLE | GROUP_LABLE | HISTORY_VERSION | SHOW | SHOW_FILE;
//
//    //群组管理员
//    public static final long GROUP_MANAGER_RIGHT = LIST | DOWNLOAD | CREATE | MOVE | DELETE | UPLOAD | COPY | COLLECT | DELETE_RECOVERY
//            | SUBSCRIBE_DIR | RENAME | SYNC | PREVIEW | CREATOR_VISIABLE | RENAME_FILE | EDIT | DOWNLOAD_FILE | MOVE_FILE | COPY_FILE
//            | DELETE_FILE | COLLECT_FILE | PRINT | LOCK | COMMENT | DELETE_RECOVERY_FILE | SUBSCRIBE_FILE | SHARE_LINK | SHARE_SEND
//            | CREATE_COOPERATE | PERSON_LABLE | GROUP_LABLE | HISTORY_VERSION;
//
//    // 全部权限：除 ADMIN ACCESS外的未应用的权限字段
//    public static final long Right_NOT_USED = FILE_CONTROL | DIR_CONTROL;
//
//    // 前台界面没有设置,但又需要的权限
//    public static final long DEFAULT_RIGHT = COLLECT | COLLECT_FILE | PERSON_LABLE | DIR_LOCK | SYNC | PRINT | GROUP_LABLE;
//
//    // 完全控制等同全部权限一致
//    public static final long RIGHT_ALL_MANAGE = LIST | DOWNLOAD | CREATE | MOVE | DELETE | UPLOAD | COPY | COLLECT | DELETE_RECOVERY
//            | SUBSCRIBE_DIR | RENAME | SYNC | PREVIEW | CREATOR_VISIABLE | RENAME_FILE | EDIT | DOWNLOAD_FILE | MOVE_FILE | COPY_FILE
//            | DELETE_FILE | COLLECT_FILE | PRINT | LOCK | COMMENT | DELETE_RECOVERY_FILE | SUBSCRIBE_FILE | SHARE_LINK | SHARE_SEND
//            | CREATE_COOPERATE | FILE_MANAGER | PERSON_LABLE | GROUP_LABLE | FILE_CONTROL | DIR_CONTROL | HISTORY_VERSION | SHOW
//            | SHOW_FILE;
//
//    // 文件被锁定时，可以进行的操作
//    public static final long RIGHT_WHEN_LOCKED = PREVIEW | DOWNLOAD | COPY_FILE | DOWNLOAD_FILE | PRINT | COMMENT | SHARE_LINK | SHARE_SEND
//            | LOCK | COLLECT_FILE | SUBSCRIBE_FILE | PERSON_LABLE | GROUP_LABLE | HISTORY_VERSION | SHOW | SHOW_FILE | COPY;
//
//    public final static long RIGHT_READWRITE = LIST | CREATE | RENAME | RENAME_FILE | EDIT | MOVE_FILE | MOVE_FILE | DELETE | DELETE_FILE
//            | DOWNLOAD | DOWNLOAD_FILE | PREVIEW | SHOW | SHOW_FILE;
//
//    public final static long RIGHT_READOWNLOAD = LIST | DOWNLOAD | DOWNLOAD_FILE | PREVIEW | SHOW | SHOW_FILE;
//
//    public final static long RIGHT_READONLY = LIST | PREVIEW | DOWNLOAD | DOWNLOAD_FILE | SHOW | SHOW_FILE;
//
//    // PREVIEW暂时不要
//    public final static long RIGHT_APPLY_USER = LIST | SHOW | SHOW_FILE | DELETE | DELETE_FILE | UPLOAD | CREATE;
//
//    // 文件权限合集
//    public static final Long RIGHT_FILE = PREVIEW | CREATOR_VISIABLE | RENAME_FILE | EDIT | DOWNLOAD_FILE | MOVE_FILE | COPY_FILE
//            | DELETE_FILE | COLLECT_FILE | PRINT | LOCK | COMMENT | DELETE_RECOVERY_FILE | SUBSCRIBE_FILE | FILE_CONTROL | HISTORY_VERSION
//            | SHOW_FILE;
//
//    // 文件夹权限合集
//    public static final Long RIGHT_DIR = LIST | CREATE | RENAME | UPLOAD | DOWNLOAD | SYNC | MOVE | COPY | DELETE | COLLECT
//            | DELETE_RECOVERY | SUBSCRIBE_DIR | DIR_CONTROL | SHOW;
//
//    // 判断是否向上继承时，忽略的已配置权限
//    public final static long IGNORE_RIGHT = FileRight.ACCESS | FileRight.FORBIDDEN;
//
//    // 协同文件权限
//    public final static long RIGHT_COOP = PREVIEW | CREATOR_VISIABLE | RENAME_FILE | EDIT | DOWNLOAD_FILE | MOVE_FILE | COPY_FILE
//            | DELETE_FILE | PRINT | LOCK | COMMENT | HISTORY_VERSION | LIST | CREATE | RENAME | UPLOAD | DOWNLOAD | SYNC | MOVE | COPY
//            | DELETE | DIR_LOCK | SHARE_LINK | SHARE_SEND;
//
//    /**
//     * 文件夹权限与文件权限的映射关系
//     */
//    public final static Map<Long, Long> DIRRIGHT_TO_FILERIGHT = new HashMap<>();
//    static {
//        DIRRIGHT_TO_FILERIGHT.put(LIST, SHOW_FILE);
//        DIRRIGHT_TO_FILERIGHT.put(CREATE, PREVIEW | CREATOR_VISIABLE);
//        DIRRIGHT_TO_FILERIGHT.put(UPLOAD, EDIT | HISTORY_VERSION | LOCK);
//        DIRRIGHT_TO_FILERIGHT.put(DOWNLOAD, DOWNLOAD_FILE);
//        DIRRIGHT_TO_FILERIGHT.put(COPY, COPY_FILE);
//        DIRRIGHT_TO_FILERIGHT.put(MOVE, MOVE_FILE);
//        DIRRIGHT_TO_FILERIGHT.put(RENAME, RENAME_FILE);
//        DIRRIGHT_TO_FILERIGHT.put(DELETE, DELETE_FILE);
//        DIRRIGHT_TO_FILERIGHT.put(DELETE_RECOVERY, DELETE_RECOVERY_FILE);
//    }
//
//    /**
//     * 权限校验：right(已有权限)/ checkRight权限校验值(所需权限) 拥有所需权限里面的所有权限才可以
//     */
//    public static boolean hasRight(long right, long checkRight) {
//        // FORBIDDEN 权限
//        if (right == FORBIDDEN) {
//            return false;
//        }
//        // LIST 权限特殊处理
//        if (checkRight == LIST && right == ACCESS) {
//            return true;
//        }
//        // ACCESS 权限特殊处理
//        if (checkRight == ACCESS && (right == ACCESS || hasRightOnly(right, SHOW))) {
//            return true;
//        }
//        return (right & checkRight) == checkRight;
//    }
//
//    /**
//     * 权限校验：right(已有权限)/ checkRight权限校验值(所需权限) 拥有所需权限里面的任意一个权限即可
//     */
//    public static boolean hasRightAny(long right, long checkRight) {
//        if (checkRight == ACCESS && (right == ACCESS || hasRightOnly(right, SHOW))) {
//            return true;
//        }
//        return (right & checkRight) != 0;
//    }
//
//    /**
//     *
//     */
//    public static boolean hasRightOnly(long right, long checkRight) {
//        return (right & checkRight) == checkRight;
//    }
//
//    /**
//     * 文件被锁定时，是否支持该操作
//     *
//     * @Description
//     * @author Yang
//     * @return
//     */
//    public static boolean operationAccessWhenLock(OperationType opType) {
//        return hasRight(RIGHT_WHEN_LOCKED, getOperationRight(opType));
//    }
//
//    /**
//     * 文件被锁定时，是否可以进行的操作
//     *
//     * @Description
//     * @author Yang
//     * @return
//     */
//    public static boolean operationWhenLock(FsEventType fsEvent, UserFile file) {
//        return hasRight(RIGHT_WHEN_LOCKED, getRightByEventType(fsEvent, file));
//    }
//
//    /**
//     * 文件被锁定时，所给的权限是否可以操作该文件 right：想要进行的操作
//     *
//     * @Description
//     * @author Yang
//     * @return
//     */
//    public static boolean operationAccessWhenLock(long right) {
//        return hasRight(RIGHT_WHEN_LOCKED, right);
//    }
//
//    /**
//     * 获取操作定义所需的权限
//     */
//    public static Long getOperationRight(OperationType type) {
//        switch (type) {
//            case LIST:
//                return FileRight.LIST;
//            case SHOW_FILE:
//                return FileRight.SHOW_FILE;
//            case SHOW:
//                return FileRight.SHOW;
//            case DOWNLOAD:
//                return FileRight.DOWNLOAD;
//            case CREATE:
//                return FileRight.CREATE;
//            case MOVE:
//                return FileRight.MOVE;
//            case DELETE:
//                return FileRight.DELETE;
//            case UPLOAD:
//                return FileRight.UPLOAD;
//            case COPY:
//                return FileRight.COPY;
//            case COLLECT:
//                return FileRight.COLLECT;
//            case DELETE_RECOVERY:
//                return FileRight.DELETE_RECOVERY;
//            case SUBSCRIBE_FILE:
//                return FileRight.SUBSCRIBE_FILE;
//            case SUBSCRIBE_DIR:
//                return FileRight.SUBSCRIBE_DIR;
//            case RENAME:
//                return FileRight.RENAME;
//            case SYNC:
//                return FileRight.SYNC;
//            case PREVIEW:
//                return FileRight.PREVIEW;
//            case CREATOR_VISIABLE:
//                return FileRight.CREATOR_VISIABLE;
//            case RENAME_FILE:
//                return FileRight.RENAME_FILE;
//            case EDIT:
//                return FileRight.EDIT;
//            case DOWNLOAD_FILE:
//                return FileRight.DOWNLOAD_FILE;
//            case MOVE_FILE:
//                return FileRight.MOVE_FILE;
//            case COPY_FILE:
//                return FileRight.COPY_FILE;
//            case DELETE_FILE:
//                return FileRight.DELETE_FILE;
//            case COLLECT_FILE:
//                return FileRight.COLLECT_FILE;
//            case PRINT:
//                return FileRight.PRINT;
//            case LOCK:
//                return FileRight.LOCK;
//            case DELETE_RECOVERY_FILE:
//                return FileRight.DELETE_RECOVERY_FILE;
//            case FORBIDDEN:
//                return FileRight.FORBIDDEN;
//            case PERSON_LABLE:
//                return FileRight.PERSON_LABLE;
//            case SHARE_LINK:
//                return FileRight.SHARE_LINK;
//            case SHARE_SEND:
//                return FileRight.SHARE_SEND;
//            //保存的时候,需要校验分享发起人的权限
//            case SHARE_SEND_SAVE:
//                return FileRight.SHARE_SEND | FileRight.DOWNLOAD | FileRight.SHOW;
//            //保存的时候,需要校验分享发起人的权限
//            case SHARE_SEND_SAVE_FILE:
//                return FileRight.SHARE_SEND | FileRight.DOWNLOAD_FILE | FileRight.SHOW_FILE;
//            //保存的时候,需要校验分享发起人的权限
//            case SHARE_LINK_SAVE:
//                return FileRight.SHARE_LINK | FileRight.DOWNLOAD | FileRight.SHOW;
//            //保存的时候,需要校验分享发起人的权限
//            case SHARE_LINK_SAVE_FILE:
//                return FileRight.SHARE_LINK | FileRight.DOWNLOAD_FILE | FileRight.SHOW_FILE;
//            case CREATE_COOPERATE:
//                return FileRight.CREATE_COOPERATE;
//            case GROUP_LABLE:
//                return FileRight.GROUP_LABLE;
//            case FILE_CONTROL:
//                return FileRight.FILE_CONTROL;
//            case DIR_CONTROL:
//                return FileRight.DIR_CONTROL;
//            case HISTORY_VERSION:
//                return FileRight.HISTORY_VERSION;
//            case PUBLIC_FILE:
//                return FileRight.PUBLIC_FILE;
//            case ACCESS:
//                return FileRight.ACCESS;
//            case ADMIN:
//                return FileRight.ADMIN;
//            case ALL_RIGHT:
//                return FileRight.ALL_RIGHT;
//            default:
//                return 0L;
//        }
//    }
//
//    /**
//     *
//     * 根据事件类型获取对应需要的权限值
//     *
//     * @author Yang
//     * @param eventType
//     *            事件类型
//     * @param file
//     *            操作对象
//     * @return
//     */
//    public static long getRightByEventType(FsEventType eventType, UserFile file) {
//        switch (eventType) {
//            case PRE_LIST:
//                return FileRight.LIST;
//            case SEARCH_FILE:
//            case POST_LIST:
//                if (file.isDirectory()) {
//                    return FileRight.SHOW;
//                } else {
//                    return FileRight.SHOW_FILE;
//                }
//            case PRE_CREATE:
//                if (file.isDirectory()) {
//                    return FileRight.CREATE;
//                }
//                return FileRight.UPLOAD;
//
//            case PRE_STOR_CHANGE:
//            case REQUEST_UPLOAD:
//            case PRE_COMMIT:
//                return FileRight.UPLOAD;
//
//            case PRE_COPY:
//                if (file.isDirectory()) {
//                    return FileRight.COPY;
//                }
//                return FileRight.COPY_FILE;
//
//            case PRE_RESIZE:
//            case PRE_WRITE:
//            case PRE_CLOSE:
//            case PRE_EDIT_FILE:
//            case POST_MODIFYFILE:
//                return FileRight.EDIT;
//
//            case PRE_DELETE:
//                if (file.isDirectory()) {
//                    return FileRight.DELETE;
//                }
//                return FileRight.DELETE_FILE;
//
//            case PRE_MOVE:
//            case POST_MOVE_ACROSS_FS:
//                if (file.isDirectory()) {
//                    return FileRight.MOVE;
//                }
//                return FileRight.MOVE_FILE;
//            case PRE_RENAME:
//                if (file.isDirectory()) {
//                    return FileRight.RENAME;
//                }
//                return FileRight.RENAME_FILE;
//
//            case PRE_SHARE:
//            case SHARE_VIEW:
//            case POST_UPDATE_SHARE:
//                return FileRight.SHARE_LINK;
//
//            case PRE_LOCK:
//                return FileRight.LOCK;
//
//            case PRE_VIEW:
//            case PRE_READ:
//                return FileRight.PREVIEW;
//
//            case REQUEST_DWONLOAD:
//                if (file.isDirectory()) {
//                    return FileRight.DOWNLOAD;
//                }
//                return FileRight.DOWNLOAD_FILE;
//
//            case PRE_FULLTEXTSEARCH:
//                return FileRight.SHOW | FileRight.SHOW_FILE;
//
//            case PRE_ADD_FAVORITE:
//                if (file.isDirectory()) {
//                    return FileRight.COLLECT;
//                }
//                return FileRight.COLLECT_FILE;
//
//            case PRE_RECYCLE_RESTORE:
//                if (file.isDirectory()) {
//                    return FileRight.DELETE_RECOVERY;
//                }
//                return FileRight.DELETE_RECOVERY_FILE;
//
//            case PRE_SUBSCRIBE_FILE:
//                if (file.isDirectory()) {
//                    return FileRight.SUBSCRIBE_DIR;
//                }
//                return FileRight.SUBSCRIBE_FILE;
//
//            case PRE_COMMENT:
//                return FileRight.COMMENT;
//            case LIST_FAVORITE:
//                if (file.isDirectory()) {
//                    return FileRight.COLLECT;
//                }
//                return FileRight.COLLECT_FILE;
//
//            default:
//                return FileRight.ACCESS;
//        }
//    }
//
//    public static long log2(long right) {
//        long x = 0;
//        while (right > 1) {
//            right >>= 1;
//            x++;
//        }
//        return x;
//    }
//
//    /** 权限映射 */
//    public static long dirRight2FileRight(long right) {
//        //移除可见权限
//        //文件夹的可见权限不能继承下来
//        /*
//         * if ((right & SHOW) == SHOW) { right = right ^ SHOW; }
//         */
//        long newRight = right;
//        for (Map.Entry<Long, Long> entry : DIRRIGHT_TO_FILERIGHT.entrySet()) {
//            if ((newRight & entry.getKey()) == entry.getKey()) {
//                newRight ^= entry.getKey();
//                newRight |= entry.getValue();
//            }
//        }
//        return newRight;
//    }
   
}
