package com.the.hugging.team.services;

import com.the.hugging.team.entities.Room;
import com.the.hugging.team.repositories.RoomRepository;

import java.util.List;

public class StorageService {
    private static StorageService INSTANCE = null;
    private final RoomRepository roomRepository = RoomRepository.getInstance();

    public static StorageService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StorageService();
        }

        return INSTANCE;
    }

    public List<Room> getAllRooms() {
        return roomRepository.getAll();
    }

    public Room addRoom(String name) {
        Room room = new Room();
        room.setName(name);
        roomRepository.save(room);
        return room;
    }

    public void setRoomName(Room room, String name) {
        room.setName(name);
        roomRepository.update(room);
    }

    public void deleteRoom(Room room) {
        roomRepository.delete(room);
    }
}
