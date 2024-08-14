package vip.xiaonuo.biz.modular.mapBiz.AuditLists.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.entity.BizAuditLists;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.entity.BizChartReviews;

import java.util.List;

/**
 * 单页展示的所有信息 - 聊天模式
 */
@Getter
@Setter
public class BizAuditInfoChatVo {
    @ApiModelProperty(value = "图审受理单")
    private BizChartReviews bizChartReviews;

    @ApiModelProperty(value = "设计校审基本信息")
    private BizAuditLists bizAuditLists;

    @ApiModelProperty(value = "校审记录列表 - 聊天模式")
    private List<ChatInfoVo> chatList;
}
