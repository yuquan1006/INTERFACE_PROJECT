package com.ipaylinks.test.mock.server.dao.model;

public class ResponseTemplate {
    private Integer id;

    private String accessPoint;

    private String group;

    private String responseTemplateName;

    private String responseTemplateDesc;

    private String status;

    private Integer sleepTime;

    private String responseTemplateContent;

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

    public String getResponseTemplateName() {
        return responseTemplateName;
    }

    public void setResponseTemplateName(String responseTemplateName) {
        this.responseTemplateName = responseTemplateName == null ? null : responseTemplateName.trim();
    }

    public String getResponseTemplateDesc() {
        return responseTemplateDesc;
    }

    public void setResponseTemplateDesc(String responseTemplateDesc) {
        this.responseTemplateDesc = responseTemplateDesc == null ? null : responseTemplateDesc.trim();
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

    public String getResponseTemplateContent() {
        return responseTemplateContent;
    }

    public void setResponseTemplateContent(String responseTemplateContent) {
        this.responseTemplateContent = responseTemplateContent == null ? null : responseTemplateContent.trim();
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