package com.the.hugging.team.services;

import com.the.hugging.team.entities.Room;
import com.the.hugging.team.entities.Shelf;
import com.the.hugging.team.repositories.RoomRepository;
import com.the.hugging.team.repositories.ShelfRepository;

import java.util.List;

public class StorageService {
    private static StorageService INSTANCE = null;
    private final RoomRepository roomRepository = RoomRepository.getInstance();
    private final ShelfRepository shelfRepository = ShelfRepository.getInstance();

    public static StorageService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StorageService();
        }

        return INSTANCE;
    }

    public List<Room> getAllRooms() {
        return roomRepository.getAll();
    }

    public List<Shelf> getAllShelves() {
        return shelfRepository.getAll();
    }

    public Room addRoom(String name) {
        Room room = new Room();
        room.setName(name);
        roomRepository.save(room);
        return room;
    }

    public Shelf addShelf(String name, Room room) {
        Shelf shelf = new Shelf();
        shelf.setName(name);
        shelf.setRoom(room);
        shelfRepository.save(shelf);
        return shelf;
    }

    public void setRoomName(Room room, String name) {
        room.setName(name);
        roomRepository.update(room);
    }

    public void setShelfName(Shelf shelf, String name) {
        shelf.setName(name);
        shelfRepository.update(shelf);
    }

    public void deleteRoom(Room room) {
        roomRepository.delete(room);
    }

    public void deleteShelf(Shelf shelf) {
        shelfRepository.delete(shelf);
    }

    public List<Shelf> getShelvesByRoom(Room room) {
        return shelfRepository.getShelvesByRoom(room);
    }
}
