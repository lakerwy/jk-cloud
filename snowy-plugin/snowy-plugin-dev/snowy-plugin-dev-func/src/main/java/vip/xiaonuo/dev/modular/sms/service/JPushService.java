//package vip.xiaonuo.dev.modular.sms.service;
//
//import cn.jpush.api.push.model.PushPayload;
//
//import java.util.List;
//
//public interface JPushService {
//    void sendToAliasList(List<String> alias, String notificationTitle, String msgTitle, String msgContent, String extras, boolean isAlias);
//
//    void sendToTagsList(List<String> tagsList, String notificationTitle, String msgTitle, String msgContent, String extras, boolean isAlias);
//
//    void sendToAllAndroid(String notificationTitle, String msgTitle, String msgContent, String extras);
//
//    void sendToAllIOS(String notificationTitle, String msgTitle, String msgContent, String extras,boolean isAlias);
//
//    void sendToAll(String notificationTitle, String msgTitle, String msgContent, String extras,boolean isAlias);
//
//    PushPayload buildPushObject_android_and_ios(String notificationTitle, String msgTitle, String msgContent, String extras,boolean isAlias);
//
//    PushPayload buildPushObject_all_aliasList_alertWithTitle(List<String> aliasList, String notificationTitle, String msgTitle, String msgContent, String extras, boolean isAlias);
//
//    PushPayload buildPushObject_all_tagList_alertWithTitle(List<String> tagsList, String notificationTitle, String msgTitle, String msgContent, String extras);
//
//    PushPayload buildPushObject_android_all_alertWithTitle(String notificationTitle, String msgTitle, String msgContent, String extras);
//
//    PushPayload buildPushObject_ios_all_alertWithTitle(String notificationTitle, String msgTitle, String msgContent, String extras);
//}
