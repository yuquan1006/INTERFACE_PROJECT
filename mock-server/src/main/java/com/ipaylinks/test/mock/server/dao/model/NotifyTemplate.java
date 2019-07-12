package com.ipaylinks.test.mock.server.dao.model;

public class NotifyTemplate {
    private Integer id;

    private String accessPoint;

    private String group;

    private String notifyUrl;

    private String notifyTemplateName;

    private String notifyTemplateDesc;

    private String status;

    private Integer sleepTime;

    private String notifyTemplateContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessPoint() {
        return accessPoint;
    }

    public void setAccessPoint(String accessPoint) {
        this.accessPoint = accessPoint == null ? null : accessPoint.trim();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group == null ? null : group.trim();
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl == null ? null : notifyUrl.trim();
    }

    public String getNotifyTemplateName() {
        return notifyTemplateName;
    }

    public void setNotifyTemplateName(String notifyTemplateName) {
        this.notifyTemplateName = notifyTemplateName == null ? null : notifyTemplateName.trim();
    }

    public String getNotifyTemplateDesc() {
        return notifyTemplateDesc;
    }

    public void setNotifyTemplateDesc(String notifyTemplateDesc) {
        this.notifyTemplateDesc = notifyTemplateDesc == null ? null : notifyTemplateDesc.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Integer sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getNotifyTemplateContent() {
        return notifyTemplateContent;
    }

    public void setNotifyTemplateContent(String notifyTemplateContent) {
        this.notifyTemplateContent = notifyTemplateContent == null ? null : notifyTemplateContent.trim();
    }

    public enum  StatusEnum {
        VALID("1", "生效的"),
        INVALID("0", "失效的");

        private String code;
        private String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        StatusEnum(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "StatusEnum{" +
                    "code='" + code + '\'' +
                    ", msg='" + msg + '\'' +
                    "} " + super.toString();
        }
    }
}