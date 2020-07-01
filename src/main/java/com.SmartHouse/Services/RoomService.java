package com.SmartHouse.Services;

import com.SmartHouse.Database.DBConnection;
import com.SmartHouse.Models.Room;
import java.util.List;

public class RoomService {

    private List<Room> rooms;

    public RoomService() {
        this.rooms = new DBConnection().getRooms();

    }

    public List<Room> getAllRooms(){

        return rooms;
    }

    public boolean deleteRoom(int roomId){
        for (int i =0; i<rooms.size(); i++){
            if (rooms.get(i).getRoomId()==roomId){
                rooms.remove(i);
                return new DBConnection().deleteRoom(roomId);
            }
        }
        return false;
    }
    public boolean addRoom(Room room){

        if (new DBConnection().addRoom(room)){
            return true;
        }
        return false;
    }
}
