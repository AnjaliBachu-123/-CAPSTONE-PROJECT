package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Room;
import com.service.RoomService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;

    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoom(@PathVariable int roomId) {
        logger.info("Received request to get room with ID: {}", roomId);
        try {
            Room room = roomService.getRoomById(roomId);
            logger.info("Successfully retrieved room: {}", room);
            return ResponseEntity.ok(room);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid room ID: {}", roomId, e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("Error retrieving room with ID: {}", roomId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Room>> getRoomsByLocation(@PathVariable String location) {
        logger.info("Received request to get rooms by location: {}", location);
        try {
            List<Room> rooms = roomService.getRoomsByLocation(location);
            logger.info("Successfully retrieved {} rooms at location: {}", rooms.size(), location);
            return ResponseEntity.ok(rooms);
        } catch (RuntimeException e) {
            logger.error("Error retrieving rooms at location: {}", location, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
//package com.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.entity.Room;
//import com.service.RoomService;
//
//@RestController
//@RequestMapping("/rooms")
//public class RoomController {
//    
//    @Autowired
//    private RoomService roomService;
//
//    @GetMapping("/{roomId}")
//    public ResponseEntity<Room> getRoom(@PathVariable int roomId) {
//        Room room = roomService.getRoomById(roomId);
//        return ResponseEntity.ok(room);
//    }
//
////    @GetMapping("/location/{location}")
////    public ResponseEntity<List<Room>> getRoomsByLocation(@PathVariable String location) {
////        List<Room> rooms = roomService.getRoomsByLocation(location);
////        return ResponseEntity.ok(rooms);
////    }
//    
//    @GetMapping("/location/{location}")
//    public ResponseEntity<List<Room>> getRoomsByLocation(@PathVariable String location) {
//        try {
//            List<Room> rooms = roomService.getRoomsByLocation(location);
//            if (rooms.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//            return ResponseEntity.ok(rooms);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//}
//
//
