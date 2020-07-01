package com.SmartHouse.Resources;

import com.SmartHouse.Models.Device;
import com.SmartHouse.Models.Room;
import com.SmartHouse.Services.DeviceService;
import com.SmartHouse.Services.RoomService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResources {
    private RoomService roomService = new RoomService();
    private DeviceService deviceService = new DeviceService();

    @GET
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @POST
    public String addRoom(Room room) {
        if (roomService.addRoom(room)) {
            return "Room added";
        } else {
            return "Failed to add the room";
        }
    }

    @Path("/{roomId}")
    @DELETE
    public String deleteRoom(@PathParam("roomId") int roomId) {
        if (roomService.deleteRoom(roomId)) {
            return "Room deleted";
        } else {
            return "Failed to delete the room";
        }
    }

    @Path("/{roomId}")
    @GET
    public List<Device> getAllDevices(@PathParam("roomId") int roomId) {
        return deviceService.getDevicesForSpecificRoom(roomId);
    }

    @Path("/{roomId}")
    @POST
    public String addDevice(@PathParam("roomId") int roomId, Device device) {
        device.setRoomId(roomId);
        if (deviceService.addDevice(device)) {
            return "Device added";
        } else {
            return "Failed to add the device";
        }
    }

    @Path("/{roomId}/{deviceId}")
    @GET
    public Device getDevice(@PathParam("roomId") int roomId, @PathParam("deviceId") int deviceID) {
        return deviceService.getOneDevice(roomId,deviceID);
    }

    @Path("/{roomId}/{deviceId}")
    @PUT
    public Device updateDeviceStatus(@PathParam("roomId") int roomId, @PathParam("deviceId") int deviceID, Device device) {
        device.setDeviceId(deviceID);
        device.setRoomId(roomId);
        return deviceService.updateDeviceStatus(device);
    }

    @Path("/{roomId}/{deviceId}")
    @DELETE
    public String deleteDevice(@PathParam("deviceId") int deviceId) {
        if (deviceService.deleteDevice(deviceId)) {
            return "Device deleted";
        } else {
            return "Failed to delete the device";
        }
    }


}
