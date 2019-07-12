package com.ipaylinks.test.mock.server.api.czcb.bo.req;

public class CzcbReqPublicData {
    private String ip;
    private String osType;
    private String uuid;
    private String imei;
    private String phoneType;
    private String mac;
    private String address;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CzcbReqPublicData{" +
                "ip='" + ip + '\'' +
                ", osType='" + osType + '\'' +
                ", uuid='" + uuid + '\'' +
                ", imei='" + imei + '\'' +
                ", phoneType='" + phoneType + '\'' +
                ", mac='" + mac + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
