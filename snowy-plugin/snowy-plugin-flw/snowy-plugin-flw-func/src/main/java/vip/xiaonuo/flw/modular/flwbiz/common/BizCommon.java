package vip.xiaonuo.flw.modular.flwbiz.common;

public enum BizCommon {
    PROJECT("PROJECT","BIZ_PROJECT"), //立项申请业务
    PROJECT_REGISTER("PROJECT_REGISTER","BIZ_PROJECT_REGISTER"), //信息报备业务
    PROJECT_FUNDS("PROJECT_FUNDS","BIZ_PROJECT_FUNDS"); //项目拨付款业务

    String bizModel;
    String bizTable;

    BizCommon(String bizModel, String bizTable) {
        this.bizModel = bizModel;
        this.bizTable = bizTable;
    }

    public String getBizModel() {
        return bizModel;
    }

    public void setBizModel(String bizModel) {
        this.bizModel = bizModel;
    }

    public String getBizTable() {
        return bizTable;
    }

    public void setBizTable(String bizTable) {
        this.bizTable = bizTable;
    }
}
