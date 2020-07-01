package com.SmartHouse.Models;

public class Device {

    private int deviceStatus;
    private String deviceName;
    private int deviceId;
    private int roomId;



    public Device() {
    }

    public Device(int deviceId, String deviceName, int deviceStatus,int roomId) {
        this.deviceStatus = deviceStatus;
        this.deviceName = deviceName;
        this.deviceId = deviceId;
        this.roomId = roomId;

    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(int deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }


}
