package com.the.hugging.team.warehouse;

import com.the.hugging.team.entities.Room;
import com.the.hugging.team.entities.Shelf;
import com.the.hugging.team.services.StorageService;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StorageServiceTest {
    private static Room newRoom;
    private static Shelf newShelf;
    private final StorageService storageService = StorageService.getInstance();

    @Test
    @Order(1)
    @DisplayName("Should add new room")
    void shouldAddNewRoom() {
        newRoom = storageService.addRoom("Test");

        Assertions.assertTrue(storageService.getAllRooms().contains(newRoom));
    }

    @Test
    @Order(2)
    @DisplayName("Should add new shelf")
    void shouldAddNewShelf() {
        newShelf = storageService.addShelf("A1", newRoom);

        Assertions.assertTrue(storageService.getAllShelves().contains(newShelf));
    }

    @Test
    @Order(3)
    @DisplayName("Should set room name")
    void shouldSetRoomName() {
        storageService.setRoomName(newRoom, "test");

        Assertions.assertEquals("test", newRoom.getName());
    }

    @Test
    @Order(4)
    @DisplayName("Should set shelf name")
    void shouldSetShelfName() {
        storageService.setShelfName(newShelf, "test");

        Assertions.assertEquals("test", newShelf.getName());
    }

    @Test
    @Order(5)
    @DisplayName("Should delete shelf")
    void shouldDeleteShelf() {
        storageService.deleteShelf(newShelf);
        Assertions.assertFalse(storageService.getAllShelves().contains(newShelf));
    }

    @Test
    @Order(6)
    @DisplayName("Should delete room")
    void shouldDeleteRoom() {
        storageService.deleteRoom(newRoom);

        Assertions.assertFalse(storageService.getAllRooms().contains(newRoom));
    }
}
