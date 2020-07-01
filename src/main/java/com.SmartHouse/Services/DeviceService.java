package com.SmartHouse.Services;

import com.SmartHouse.Database.DBConnection;
import com.SmartHouse.Models.Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeviceService {

    private List<Device> devices;
    private HashMap<Integer, Integer> devicesForArduino;
    private List<Integer> devicesId;


    public DeviceService() {
        devices = new DBConnection().getDevices();
        this.devicesForArduino = new HashMap<>();
        this.devicesId = new DBConnection().getDevicesId();
    }

    public HashMap<Integer, Integer> getDevicesForArduino(){
        for (int i =0; i<devices.size(); i++){
            devicesForArduino.put(devices.get(i).getDeviceId(), devices.get(i).getDeviceStatus());
        }

        return devicesForArduino;
    }

    public List<Device> getAllDevices() {
        return devices;
    }

    public List<Device> getDevicesForSpecificRoom(int roomId){
        List<Device> devicesForRoom = new ArrayList<>();

        for (int i = 0; i < devices.size(); i++) {
            if (devices.get(i).getRoomId() == roomId) {

                devicesForRoom.add(devices.get(i));

            }
        }
        return devicesForRoom;
    }

    public Device getOneDevice(int roomId, int deviceId) {
        for (int i = 0; i < devices.size(); i++) {
            if ((devices.get(i).getDeviceId() == deviceId) && (devices.get(i).getRoomId() == roomId)) {
                return devices.get(i);
            }
        }
        return null;
    }

    public boolean addDevice(Device device) {
        if (new DBConnection().addDevice(device))
            return true;
        else
            return false;
    }


    public void updateDevice(HashMap<Integer,Integer> devicesStatus){
        for (int id: devicesStatus.keySet()){
            for (Integer i: devicesId){
                if (id == i){
                    new DBConnection().updateDevice(id, devicesStatus.get(id));
                }
            }
        }
    }

    public Device updateDeviceStatus(Device device) {

        for (int i = 0; i < devices.size(); i++) {
            if (devices.get(i).getDeviceId() == device.getDeviceId()) {
                devices.get(i).setDeviceStatus(device.getDeviceStatus());
                if (new DBConnection().updateDeviceStatus(devices.get(i)))
                    return devices.get(i);
            }
        }
        return null;
    }
    public boolean deleteDevice(int deviceId){
        for (int i =0; i<devices.size(); i++){
            if (devices.get(i).getDeviceId()==deviceId){
                devices.remove(i);
                return new DBConnection().deleteDevice(deviceId);
            }
        }
        return false;
    }
}
