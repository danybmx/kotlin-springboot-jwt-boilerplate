package es.dpstudios.boilerplate.models

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "rooms")
data class Room(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne()
    @JoinColumn(name = "accommodation_id")
    @JsonBackReference
    var accommodation: Accommodation? = null,

    @get: NotBlank
    var name: String? = null

)

