package es.dpstudios.boilerplate.repositories

import es.dpstudios.boilerplate.models.Accommodation
import org.springframework.data.jpa.repository.JpaRepository

interface AccommodationRepository : JpaRepository<Accommodation, Long>