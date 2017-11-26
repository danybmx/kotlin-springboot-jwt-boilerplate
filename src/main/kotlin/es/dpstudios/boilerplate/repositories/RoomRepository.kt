package es.dpstudios.boilerplate.repositories

import es.dpstudios.boilerplate.models.Room
import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository : JpaRepository<Room, Long>