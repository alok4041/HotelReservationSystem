package org.cozy.HotelReservationSystem.service;

import org.cozy.HotelReservationSystem.model.Room;
import org.cozy.HotelReservationSystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // CREATE
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    // READ - All rooms
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // READ - Single room by ID
    public Room getRoomById(Integer id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
    }

    // UPDATE
    public Room updateRoom(Integer id, Room roomDetails) {
        Room room = getRoomById(id);
        room.setRoomNumber(roomDetails.getRoomNumber());
        room.setRoomType(roomDetails.getRoomType());
        room.setPricePerNight(roomDetails.getPricePerNight());
        room.setCapacity(roomDetails.getCapacity());
        room.setStatus(roomDetails.getStatus());
        return roomRepository.save(room);
    }

    // DELETE
    public void deleteRoom(Integer id) {
        roomRepository.deleteById(id);
    }
}