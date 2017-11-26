package es.dpstudios.boilerplate.services

import es.dpstudios.boilerplate.models.Accommodation
import es.dpstudios.boilerplate.models.Room
import es.dpstudios.boilerplate.repositories.AccommodationRepository
import es.dpstudios.boilerplate.repositories.RoomRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/accommodations")
@RestController
class AccommodationsService(private val accomodationsRepository: AccommodationRepository,
                            private val roomsRepository: RoomRepository) {

    @GetMapping()
    fun listAccommodations() : List<Accommodation> =
            accomodationsRepository.findAll()

    @GetMapping("/{id}")
    fun getAccommodation(@PathVariable(value = "id") id: Long) : Accommodation =
            accomodationsRepository.getOne(id)

    @PostMapping()
    fun createAccommodation(@Valid @RequestBody accommodation: Accommodation): Accommodation {
        return saveRooms(accomodationsRepository.save(accommodation))
    }

    @PutMapping("/{id}")
    fun updateAccommodation(@PathVariable(value = "id") id: Long,
                            @Valid @RequestBody accommodation: Accommodation): ResponseEntity<Accommodation> {
        return accomodationsRepository.findById(id).map { existingAccommodation ->
            val updated: Accommodation = existingAccommodation.copy(
                    name = accommodation.name,
                    lat = accommodation.lat,
                    lng = accommodation.lng,
                    rooms = accommodation.rooms)
            ResponseEntity.ok().body(saveRooms(accomodationsRepository.save(updated)))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteAccommodation(@PathVariable(value = "id") id: Long): ResponseEntity<Void> {
        return accomodationsRepository.findById(id).map { accommodation ->
            accomodationsRepository.delete(accommodation)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }

    private fun saveRooms(accommodation: Accommodation) : Accommodation {
        val rooms = mutableListOf<Room>()

        accommodation.rooms.orEmpty().map { room: Room ->
            room.accommodation = accommodation
            rooms.add(roomsRepository.save(room))
        }
        accommodation.rooms = rooms

        return accomodationsRepository.save(accommodation)
    }
}
