package com.the.hugging.team.warehouse;

import com.the.hugging.team.entities.Room;
import com.the.hugging.team.entities.Shelf;
import com.the.hugging.team.repositories.RoomRepository;
import com.the.hugging.team.repositories.ShelfRepository;
import com.the.hugging.team.services.StorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;


public class StorageServiceTest {
    private static Room room = null;
    private  static Shelf shelf = null;
    @BeforeAll
    public static void setUp(){
       room =  RoomRepository.getInstance().getById(1).orElse(null);
       shelf = ShelfRepository.getInstance().getById(1).orElse(null);
    }

    @Test
    @DisplayName("Should add new room")
    void shouldAddNewRoom(){
        Room room = StorageService.getInstance().addRoom("Test");

        Assertions.assertTrue(StorageService.getInstance().getAllRooms().contains(room));
    }

    @Test
    @DisplayName("Should add new shelf")
    void shouldAddNewShelf(){
        Shelf shelf = StorageService.getInstance().addShelf("A1",room);

        Assertions.assertTrue(StorageService.getInstance().getAllShelves().contains(shelf));
    }

    @Test
    @DisplayName("Should set room name")
    void shouldSetRoomName(){
        StorageService.getInstance().setRoomName(room,"test");

        Assertions.assertEquals("test", Objects.requireNonNull(RoomRepository.getInstance().getById(1).orElse(null)).getName());
    }

    @Test
    @DisplayName("Should set shelf name")
    void shouldSetShelfName(){
        StorageService.getInstance().setShelfName(shelf,"test");

        Assertions.assertEquals("test",Objects.requireNonNull(RoomRepository.getInstance().getById(1).orElse(null).getName()));
    }
}
