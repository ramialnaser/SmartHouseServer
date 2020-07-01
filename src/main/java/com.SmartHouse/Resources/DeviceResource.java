package com.SmartHouse.Resources;

import com.SmartHouse.Services.DeviceService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;


@Path("/devices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeviceResource {

    private DeviceService deviceService = new DeviceService();

    @GET
    public HashMap<Integer, Integer> getDevices(){
        return deviceService.getDevicesForArduino();
    }

    @PUT
    public void updateStatus(HashMap<Integer, Integer> devicesStatus){
        deviceService.updateDevice(devicesStatus);
    }
}
