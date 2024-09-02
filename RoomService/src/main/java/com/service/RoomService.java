package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Room;
import com.repository.RoomRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RoomService {

    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

    @Autowired
    private RoomRepository roomRepository;

    public Room getRoomById(int roomId) {
        logger.debug("Fetching room with ID: {}", roomId);
        
        // Validate that the roomId is greater than zero
        if (roomId <= 0) {
            logger.warn("Attempted to fetch room with invalid ID: {}", roomId);
            throw new IllegalArgumentException("Room ID must be greater than zero");
        }

        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> {
                logger.error("Room not found with ID: {}", roomId);
                return new RuntimeException("Room not found");
            });
        
        logger.debug("Fetched room details: {}", room);
        return room;
    }

    public List<Room> getRoomsByLocation(String location) {
        logger.debug("Fetching rooms at location: {}", location);
        List<Room> rooms = roomRepository.findByLocation(location);

        if (rooms.isEmpty()) {
            logger.warn("No rooms available at location: {}", location);
            throw new RuntimeException("No rooms available at this location");
        }

        logger.debug("Fetched {} rooms at location: {}", rooms.size(), location);
        return rooms;
    }
}
//package com.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.entity.Room;
//import com.repository.RoomRepository;
//
//@Service
//public class RoomService {
//    
//    @Autowired
//    private RoomRepository roomRepository;
//
//    public Room getRoomById(int roomId) {
//        return roomRepository.findById(roomId)
//            .orElseThrow(() -> new RuntimeException("Room not found"));
//    }
//
//    public List<Room> getRoomsByLocation(String location) {
//        List<Room> rooms = roomRepository.findByLocation(location);
//        if (rooms.isEmpty()) {
//            throw new RuntimeException("No rooms available at this location");
//        }
//        return rooms;
//    }
//}
//
//

