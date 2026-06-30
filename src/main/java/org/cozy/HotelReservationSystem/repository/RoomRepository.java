package org.cozy.HotelReservationSystem.repository;

import org.cozy.HotelReservationSystem.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
}