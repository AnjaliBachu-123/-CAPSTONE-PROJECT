package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByLocation(String location);
}


